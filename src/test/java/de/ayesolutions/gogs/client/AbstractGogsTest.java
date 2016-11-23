package de.ayesolutions.gogs.client;

import de.ayesolutions.gogs.client.model.AccessToken;

import java.net.URI;
import java.util.UUID;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class AbstractGogsTest {

    protected final static URI API_URI = URI.create("http://helios.asnet.aye-solutions.de:13001/api/v1");

    protected final static URI API_URI_INVALID = URI.create("http://helios.asnet.aye-solutions.de:13001/apii/v1");

    protected final static GogsClient API_ADMIN = new GogsClient(API_URI, new AccessToken(null, "5a855f3bcccb41275c9508552e5d8a11321c21df", "gogs-admin", "pass"));

    protected final static GogsClient API_USER = new GogsClient(API_URI, new AccessToken(null, "486632a6c6fd07d9e4b0aad8c30c0c834d469a91", "gogs-user", "pass"));

    protected final static GogsClient API_UNAUTHORIZED = new GogsClient(API_URI);

    protected final static GogsClient API_INVALID = new GogsClient(API_URI_INVALID, new AccessToken(null, "4867d9e4b0aad8c30c0c834d469a91", "dd", "daa"));

    protected final static String USERNAME_ADMIN = "gogs-admin";

    protected final static String USERNAME_USER = "gogs-user";

    protected final static String PASSWORD = "pass";

    protected String generateGUID() {
        return UUID.randomUUID().toString();
    }
}
