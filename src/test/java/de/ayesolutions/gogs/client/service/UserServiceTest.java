package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.AbstractGogsTest;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.User;
import de.ayesolutions.gogs.client.model.UserSearchResult;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class UserServiceTest extends AbstractGogsTest {

    @Test
    public void search() throws Exception {
        UserService service = new UserService(API_UNAUTHORIZED);
        UserSearchResult result = service.search("gogs-user", 1);
        User user = result.getData().get(0);
        Assert.assertEquals("gogs-user", user.getUsername());
        Assert.assertEquals("", user.getEmail());

        service = new UserService(API_USER);
        result = service.search("gogs");
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getData());
        Assert.assertEquals(2, result.getData().size());
        Assert.assertTrue(result.isOk());

        result = service.search("gogs-user");
        user = result.getData().get(0);
        Assert.assertEquals("gogs-user", user.getUsername());
        Assert.assertEquals("info@aye-solutions.de", user.getEmail());
        Assert.assertEquals("Gogs User", user.getFullName());
        Assert.assertTrue(!user.getAvatarUrl().isEmpty() && user.getAvatarUrl().startsWith("https://secure.gravatar.com/avatar/"));
        Assert.assertTrue(user.getId() != 0);

        result = service.search("gogs-user", 1);
        Assert.assertEquals(1, result.getData().size());
    }

    @Test(expected = GogsClientException.class)
    public void searchInvalid() throws Exception {
        UserService service = new UserService(API_INVALID);
        service.search("gogs");
    }

    @Test
    public void searchInvalid2() throws Exception {
        UserService service = new UserService(API_USER);
        UserSearchResult result = service.search("\\dsdd[]*\\");
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.getData().size());
        Assert.assertTrue(result.isOk());
    }
}