package de.ayesolutions.gogs.client;

import de.ayesolutions.gogs.client.model.AccessToken;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;

/**
 * HTTP REST client for Go Git Service.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class GogsClient {

    private final static Logger LOG = LoggerFactory.getLogger(GogsClient.class);

    /**
     * REST client configuration.
     */
    private ClientConfig clientConfig;

    /**
     * REST client.
     */
    private Client client;

    /**
     * REST web target.
     */
    private WebTarget webTarget;

    /**
     * Gogs access token.
     */
    private AccessToken accessToken;

    /**
     * default constructor.
     *
     * @param uri uri to your gogs instance. (with /api/v1).
     */
    public GogsClient(final URI uri) {
        this(uri, null);
    }

    /**
     * default constructor.
     *
     * @param uri      uri to your gogs instance. (with /api/v1).
     * @param username http username (fallback for token).
     * @param password http password (fallback for token).
     * @param token    application access token.
     */
    public GogsClient(final URI uri, final String username, final String password, final String token) {
        this(uri, new AccessToken(null, token, username, password));
    }

    /**
     * default constructor.
     *
     * @param uri         uri to your gogs instance. (with /api/v1)
     * @param accessToken access token.
     */
    public GogsClient(final URI uri, final AccessToken accessToken) {
        this.clientConfig = new ClientConfig();
        this.clientConfig
                .property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true)
                .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
        this.client = ClientBuilder.newClient(this.clientConfig);
        this.webTarget = client.target(uri);
        this.accessToken = accessToken;
    }


    public <T> T get(Class<T> clazz, String... path) {
        return get(clazz, null, path);
    }

    public <T> T get(Class<T> clazz, Map<String, String> parameters, String... path) {
        return request("GET", clazz, null, parameters, path);
    }

    public <T> T get(GenericType<T> clazz, String... path) {
        return get(clazz, null, path);
    }

    public <T> T get(GenericType<T> clazz, Map<String, String> parameters, String... path) {
        return request("GET", clazz, null, parameters, path);
    }

    public <T> T put(Class<T> clazz, Object data, String... path) {
        return request("PUT", clazz, data, null, path);
    }

    public <T> T post(GenericType<T> clazz, Object data, String... path) {
        return request("POST", clazz, data, null, path);
    }

    public <T> T post(Class<T> clazz, Object data, String... path) {
        return request("POST", clazz, data, null, path);
    }

    public void delete(String... path) {
        request("DELETE", Void.class, null, null, path);
    }

    public void delete(Object data, String... path) {
        request("DELETE", Void.class, data, null, path);
    }

    public <T> T patch(Class<T> clazz, Object data, String... path) {
        return request("PATCH", clazz, data, null, path);
    }

    public <T> T request(String method, Class<T> clazz, Object data, Map<String, String> parameters, String... path) {
        Response response = callRequest(method, data, parameters, path);
        handleStatusCode(response, String.join("/", path));
        return response.readEntity(clazz);
    }

    public <T> T request(String method, GenericType<T> clazz, Object data, Map<String, String> parameters, String... path) {
        Response response = callRequest(method, data, parameters, path);
        handleStatusCode(response, String.join("/", path));
        return response.readEntity(clazz);
    }

    public Response callRequest(String method, Object data, Map<String, String> parameters, String... path) {
        WebTarget webTarget = getWebTarget();

        // set rest path
        for (String part : path) {
            webTarget = webTarget.path(part);
        }

        // set http parameters
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                webTarget = webTarget.queryParam(key, parameters.get(key));
            }
        }

        // set authorization token
        Invocation.Builder builder = webTarget.request();
        if (getAccessToken() != null) {
            // TODO: test if all calls can be token authorization
            builder = builder.header("Authorization", getAccessToken().getTokenAuthorization());
        }

        LOG.debug("call service: " + method + " " + getWebTarget().getUri().toString() + "/" + String.join("/", path));

        // handle methods
        Response response = null;
        if (method.equals("GET")) {
            response = builder.get();
        } else if (method.equals("POST")) {
            response = builder.post(Entity.json(data));
        } else if (method.equals("PATCH")) {
            response = builder.method("PATCH", Entity.json(data));
        } else if (method.equals("DELETE")) {
            if (data == null) {
                response = builder.delete();
            } else {
                response = builder.method("DELETE", Entity.json(data));
            }
        } else if (method.equals("PUT")) {
            response = builder.put(Entity.json(data));
        }

        // check response
        if (response == null) {
            throw new GogsClientException("no response");
        }

        return response;
    }

    public void handleStatusCode(Response response, String endpoint) {
        // handle http code
        switch (response.getStatus()) {
            case 200:
            case 201:
            case 204:
                LOG.debug("call service: " + getWebTarget().getUri().toString() + "/" + endpoint + " success " + response.getStatus());
                break;
            default:
                String result = response.readEntity(String.class);
                LOG.error("call service: " + getWebTarget().getUri().toString() + "/" + endpoint + " failed " + response.getStatus());
                if (!result.isEmpty()) {
                    LOG.error(result);
                }
                throw new GogsClientException("api error code: " + response.getStatus() + System.lineSeparator() + result);
        }
    }

    public WebTarget getWebTarget() {
        return webTarget;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}
