package de.ayesolutions.gogs.client;

import de.ayesolutions.gogs.client.model.AccessToken;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Base64;
import java.util.List;

/**
 * Java REST client for Go Git Service.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class GogsClient {

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
     * @param uri uri to your gogs instance. (with /api/v1)
     */
    public GogsClient(final URI uri) {
        this(uri, null);
    }

    /**
     * default constructor.
     *
     * @param uri         uri to your gogs instance. (with /api/v1)
     * @param accessToken access token.
     */
    public GogsClient(final URI uri, final AccessToken accessToken) {
        this.clientConfig = new ClientConfig();
        this.client = ClientBuilder.newClient(this.clientConfig);
        this.webTarget = client.target(uri);
        this.accessToken = accessToken;
    }

    /**
     * create new access token for user.
     *
     * @param uri      uri to your gogs instance. (with /api/v1)
     * @param username gogs username.
     * @param password gogs password.
     * @param name     name of access token.
     * @return new generated access token.
     */
    public static AccessToken createToken(final URI uri, final String username, final String password,
                                          final String name) {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target(uri);

        Entity<AccessToken> entity = Entity.json(new AccessToken(name, null));
        Response response = webTarget
                .path("users")
                .path(username)
                .path("tokens")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Basic " + new String(generateAuthorizationValue(username, password)))
                .post(entity);

        if (response.getStatus() != 201) {
            throw new GogsClientException(GogsClientException.createMessage(response));
        }

        return response.readEntity(AccessToken.class);
    }

    /**
     * list all user access tokens.
     *
     * @param uri      uri to your gogs instance. (with /api/v1)
     * @param username gogs username.
     * @param password gogs password.
     * @return list of user access tokens.
     */
    public static List<AccessToken> listAccessTokens(final URI uri, final String username, final String password) {
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        WebTarget webTarget = client.target(uri);

        Response response = webTarget
                .path("users")
                .path(username)
                .path("tokens")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Basic " + new String(generateAuthorizationValue(username, password)))
                .get();

        if (response.getStatus() != 200) {
            throw new GogsClientException(GogsClientException.createMessage(response));
        }

        return response.readEntity(new GenericType<List<AccessToken>>() {
        });
    }

    /**
     * generate new base64 encoded basic authorization.
     *
     * @param username username.
     * @param password password.
     * @return base64 encoded authorization.
     */
    private static byte[] generateAuthorizationValue(final String username, final String password) {
        return Base64.getEncoder().encode((username + ":" + password).getBytes());
    }

    public WebTarget getWebTarget() {
        return webTarget;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}
