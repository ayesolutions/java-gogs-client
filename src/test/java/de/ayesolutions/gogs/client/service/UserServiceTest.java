package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.AbstractGogsTest;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class UserServiceTest extends AbstractGogsTest {

    @Test
    public void createToken() throws Exception {
        String uuid = generateGUID();
        UserService service = new UserService(API_USER);
        AccessToken accessToken = service.createToken(USERNAME_USER, uuid);
        Assert.assertEquals(uuid, accessToken.getName());
        Assert.assertTrue(accessToken.getSha1().length() > 0);

        uuid = generateGUID();
        service = new UserService(API_USER);
        accessToken = service.createToken(USERNAME_ADMIN, uuid);
        Assert.assertEquals(uuid, accessToken.getName());
        Assert.assertTrue(accessToken.getSha1().length() > 0);
    }

    @Test(expected = GogsClientException.class)
    public void createTokenInvalid() throws Exception {
        String uuid = generateGUID();
        UserService service = new UserService(API_INVALID);
        service.createToken(USERNAME_USER, uuid);
    }

    @Test(expected = GogsClientException.class)
    public void createTokenInvalid2() throws Exception {
        String uuid = generateGUID();
        UserService service = new UserService(API_UNAUTHORIZED);
        service.createToken(USERNAME_USER, uuid);
    }

    @Test
    public void getAccessTokens() throws Exception {
        UserService service = new UserService(API_USER);
        List<AccessToken> accessTokenList = service.listAccessTokens(USERNAME_USER);
        Assert.assertTrue(accessTokenList.size() > 0);

        AccessToken accessToken = accessTokenList.get(0);
        Assert.assertTrue(accessToken.getName().length() > 0);
        Assert.assertTrue(accessToken.getSha1().length() > 0);

        accessTokenList = service.listAccessTokens(USERNAME_ADMIN);
        Assert.assertTrue(accessTokenList.size() > 0);

        accessTokenList = service.listAccessTokens(USERNAME_UNKNOWN);
        Assert.assertTrue(accessTokenList.size() > 0);
    }

    @Test(expected = GogsClientException.class)
    public void getAccessTokensInvalid() throws Exception {
        UserService service = new UserService(API_UNAUTHORIZED);
        service.listAccessTokens(USERNAME_USER);
    }

    @Test(expected = GogsClientException.class)
    public void getAccessTokensInvalidUri() throws Exception {
        UserService service = new UserService(API_INVALID);
        service.listAccessTokens(USERNAME_USER);
    }

    @Test
    public void search() throws Exception {
        UserService service = new UserService(API_UNAUTHORIZED);
        UserSearchResult result = service.search(USERNAME_USER, 1);
        User user = result.getData().get(0);
        Assert.assertEquals(USERNAME_USER, user.getUsername());
        Assert.assertEquals("", user.getEmail());

        service = new UserService(API_USER);
        result = service.search("gogs");
        Assert.assertEquals(2, result.getData().size());
        Assert.assertTrue(result.isOk());

        result = service.search(USERNAME_USER);
        checkUser(result.getData().get(0));

        result = service.search(USERNAME_USER, 1);
        Assert.assertEquals(1, result.getData().size());

        service = new UserService(API_USER);
        result = service.search("\\dsdd[]*\\");
        Assert.assertEquals(0, result.getData().size());
        Assert.assertTrue(result.isOk());
    }

    @Test(expected = GogsClientException.class)
    public void searchInvalid() throws Exception {
        UserService service = new UserService(API_INVALID);
        service.search("gogs");
    }

    @Test
    public void getInfo() throws Exception {
        UserService service = new UserService(API_USER);
        User user = service.getInfo(USERNAME_USER);
        checkUser(user);
    }

    @Test
    public void addPublicKey() throws Exception {
        String uuid = UUID.randomUUID().toString();
        PublicKey publicKey = new PublicKey();
        publicKey.setTitle(uuid);
        publicKey.setKey(SSH_KEY);

        UserService service = new UserService(API_USER);
        publicKey = service.addPublicKey(publicKey);

        Assert.assertTrue(service.deletePublicKey(publicKey.getId().toString()));
    }

    @Test
    public void getPublicKey() throws Exception {
        String uuid = UUID.randomUUID().toString();
        PublicKey publicKey = new PublicKey();
        publicKey.setTitle(uuid);
        publicKey.setKey(SSH_KEY);

        UserService service = new UserService(API_USER);
        publicKey = service.addPublicKey(publicKey);

        publicKey = service.getPublicKey(publicKey.getId().toString());

        Assert.assertTrue(service.deletePublicKey(publicKey.getId().toString()));

        Assert.assertNull(service.getPublicKey("0"));
    }

    @Test
    public void listPublicKeys() throws Exception {
        String uuid = UUID.randomUUID().toString();
        PublicKey publicKey = new PublicKey();
        publicKey.setTitle(uuid);
        publicKey.setKey(SSH_KEY);

        UserService service = new UserService(API_USER);
        publicKey = service.addPublicKey(publicKey);

        List<PublicKey> publicKeyList = service.listPublicKeys();
        Assert.assertEquals(1, publicKeyList.size());

        publicKeyList = service.listPublicKeys(USERNAME_USER);
        Assert.assertEquals(1, publicKeyList.size());

        publicKeyList = service.listPublicKeys(USERNAME_UNKNOWN);
        Assert.assertEquals(0, publicKeyList.size());

        Assert.assertTrue(service.deletePublicKey(publicKey.getId().toString()));
    }

    @Test
    public void deletePublicKey() throws Exception {
        String uuid = UUID.randomUUID().toString();
        PublicKey publicKey = new PublicKey();
        publicKey.setTitle(uuid);
        publicKey.setKey(SSH_KEY);

        UserService service = new UserService(API_USER);
        publicKey = service.addPublicKey(publicKey);

        Assert.assertNotNull(publicKey);

        Assert.assertTrue(service.deletePublicKey(publicKey.getId().toString()));
    }

    @Test
    public void listPublicKeysInvalid() throws Exception {
        UserService service = new UserService(API_USER);
        List<PublicKey> publicKeyList = service.listPublicKeys(USERNAME_UNKNOWN);
        Assert.assertEquals(0, publicKeyList.size());
    }

    @Test
    public void listFollowing() throws Exception {
        UserService service = new UserService(API_ADMIN);
        Assert.assertTrue(service.follow(USERNAME_USER));

        List<User> userList = service.listFollowing();
        Assert.assertEquals(1, userList.size());
        Assert.assertEquals(USERNAME_USER, userList.get(0).getUsername());

        userList = service.listFollowing(USERNAME_ADMIN);
        Assert.assertEquals(1, userList.size());
        Assert.assertEquals(USERNAME_USER, userList.get(0).getUsername());

        userList = service.listFollowing(USERNAME_UNKNOWN);
        Assert.assertEquals(0, userList.size());

        Assert.assertTrue(service.unfollow(USERNAME_USER));
    }

    @Test
    public void listFollowers() throws Exception {
        UserService service = new UserService(API_ADMIN);
        Assert.assertTrue(service.follow(USERNAME_USER));

        service = new UserService(API_USER);
        List<User> userList = service.listFollowers();
        Assert.assertEquals(1, userList.size());
        Assert.assertEquals(USERNAME_ADMIN, userList.get(0).getUsername());

        userList = service.listFollowers(USERNAME_USER);
        Assert.assertEquals(1, userList.size());
        Assert.assertEquals(USERNAME_ADMIN, userList.get(0).getUsername());

        userList = service.listFollowers(USERNAME_UNKNOWN);
        Assert.assertEquals(0, userList.size());

        service = new UserService(API_ADMIN);
        Assert.assertTrue(service.unfollow(USERNAME_USER));
    }

    @Test
    public void checkFollowing() throws Exception {
        UserService service = new UserService(API_USER);
        Assert.assertTrue(service.follow(USERNAME_ADMIN));

        Assert.assertTrue(service.checkFollowing(USERNAME_ADMIN));
        Assert.assertTrue(service.checkFollowing(USERNAME_USER, USERNAME_ADMIN));

        Assert.assertFalse(service.checkFollowing(USERNAME_UNKNOWN));
        Assert.assertFalse(service.checkFollowing(USERNAME_USER, USERNAME_UNKNOWN));

        Assert.assertTrue(service.unfollow(USERNAME_ADMIN));
    }

    @Test
    public void getUser() throws Exception {
        UserService service = new UserService(API_USER);
        User user = service.getUser();
        checkUser(user);
    }

    @Test
    public void listUserEmails() throws Exception {
        UserService service = new UserService(API_USER);
        List<Email> emailList = service.listUserEmails();
        Assert.assertEquals(1, emailList.size());
        Assert.assertEquals("info@aye-solutions.de", emailList.get(0).getEmail());
        Assert.assertTrue(emailList.get(0).isVerified());
        Assert.assertTrue(emailList.get(0).isPrimary());
    }

    @Test
    public void addEmail() throws Exception {
        EmailList emailList = new EmailList();
        emailList.getEmails().add("test@aye-solutions.de");

        UserService service = new UserService(API_USER);
        List<Email> newEmailList = service.addEmail(emailList);

        Assert.assertEquals(1, newEmailList.size());
        Assert.assertEquals("test@aye-solutions.de", newEmailList.get(0).getEmail());

        Assert.assertTrue(service.deleteEmail(emailList));
    }

    @Test
    public void deleteEmail() throws Exception {
        EmailList emailList = new EmailList();
        emailList.getEmails().add("test@aye-solutions.de");

        UserService service = new UserService(API_USER);
        service.addEmail(emailList);

        Assert.assertTrue(service.deleteEmail(emailList));
    }

    @Test
    public void follow() throws Exception {
        UserService service = new UserService(API_USER);
        Assert.assertTrue(service.follow(USERNAME_ADMIN));
        Assert.assertTrue(service.unfollow(USERNAME_ADMIN));

        Assert.assertFalse(service.follow(USERNAME_UNKNOWN));
    }

    @Test
    public void unfollow() throws Exception {
        UserService service = new UserService(API_USER);
        Assert.assertTrue(service.unfollow(USERNAME_ADMIN));
        Assert.assertTrue(service.unfollow(USERNAME_ADMIN));

        Assert.assertFalse(service.unfollow(USERNAME_UNKNOWN));
    }

    private void checkUser(User user) {
        Assert.assertEquals(USERNAME_USER, user.getUsername());
        Assert.assertEquals("info@aye-solutions.de", user.getEmail());
        Assert.assertEquals("Gogs User", user.getFullName());
        Assert.assertTrue(!user.getAvatarUrl().isEmpty() && user.getAvatarUrl().startsWith(GRAVATAR_URL));
        Assert.assertTrue(user.getId() != 0);
    }
}