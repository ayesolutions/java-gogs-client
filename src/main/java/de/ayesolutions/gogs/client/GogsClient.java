package de.ayesolutions.gogs.client;

import de.ayesolutions.gogs.client.model.AccessToken;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;

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
        this.clientConfig.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        this.client = ClientBuilder.newClient(this.clientConfig);
        this.webTarget = client.target(uri);
        this.accessToken = accessToken;
    }

    public WebTarget getWebTarget() {
        return webTarget;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}
