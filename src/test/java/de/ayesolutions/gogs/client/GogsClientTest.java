package de.ayesolutions.gogs.client;

import de.ayesolutions.gogs.client.model.AccessToken;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import java.net.URI;
import java.util.List;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class GogsClientTest extends AbstractGogsTest {

    @Test
    public void createToken() throws Exception {
        String uuid = generateGUID();
        AccessToken accessToken = GogsClient.createToken(API_URI, USERNAME_USER, PASSWORD, uuid);
        Assert.assertNotNull(accessToken);
        Assert.assertEquals(uuid, accessToken.getName());
        Assert.assertTrue(accessToken.getSha1().length() > 0);
    }

    @Test(expected = GogsClientException.class)
    public void createTokenInvalid() throws Exception {
        String uuid = generateGUID();
        GogsClient.createToken(API_URI, "meep", PASSWORD, uuid);
    }

    @Test
    public void getAccessTokens() throws Exception {
        List<AccessToken> accessTokenList = GogsClient.listAccessTokens(API_URI, USERNAME_USER, PASSWORD);
        Assert.assertNotNull(accessTokenList);
        Assert.assertTrue(accessTokenList.size() > 0);

        AccessToken accessToken = accessTokenList.get(0);
        Assert.assertNotNull(accessToken);
        Assert.assertTrue(accessToken.getName().length() > 0);
        Assert.assertTrue(accessToken.getSha1().length() > 0);
    }

    @Test(expected = GogsClientException.class)
    public void getAccessTokensInvalidUsername() throws Exception {
        GogsClient.listAccessTokens(API_URI, "meep", PASSWORD);
    }

    @Test(expected = ProcessingException.class)
    public void getAccessTokensInvalidUri() throws Exception {
        GogsClient.listAccessTokens(URI.create("xx"), USERNAME_USER, PASSWORD);
    }

    @Test(expected = ProcessingException.class)
    public void getAccessTokensInvalidUri2() throws Exception {
        GogsClient.listAccessTokens(URI.create("http://localhost:12345"), USERNAME_USER, PASSWORD);
    }
}