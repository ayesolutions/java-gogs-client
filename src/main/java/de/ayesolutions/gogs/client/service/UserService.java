package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gogs user service call class.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public final class UserService extends BaseService {

    /**
     * default constructor.
     *
     * @param client gogs client.
     */
    public UserService(final GogsClient client) {
        super(client);
    }

    /**
     * list all user access tokens.
     * <p>
     * GET /api/v1/users/:username/tokens
     *
     * @param username username.
     * @return list of user access tokens.
     * @throws GogsClientException
     */
    public List<AccessToken> listAccessTokens(final String username) throws GogsClientException {
        Response response = getClient().getWebTarget()
                .path("users").path(username).path("tokens")
                .request().header("Authorization", getClient().getAccessToken().getBasicAuthorization())
                .get();

        if (response == null) {
            throw new GogsClientException("no response");
        }

        if (!(response.getStatus() == 200)) {
            throw new GogsClientException("unknown error");
        }

        return response.readEntity(new GenericType<List<AccessToken>>() {
        });
    }

    /**
     * create new access token for user.
     * <p>
     * POST /api/v1/users/:username/tokens
     *
     * @param name name of access token.
     * @return new generated access token.
     * @throws GogsClientException
     */
    public AccessToken createToken(final String username, final String name) throws GogsClientException {
        Response response = getClient().getWebTarget()
                .path("users").path(username).path("tokens")
                .request().header("Authorization", getClient().getAccessToken().getBasicAuthorization())
                .post(Entity.json(new AccessToken(name, null)));

        if (response == null) {
            throw new GogsClientException("no response");
        }

        if (!(response.getStatus() == 201)) {
            throw new GogsClientException("unknown error");
        }

        return response.readEntity(AccessToken.class);
    }

    /**
     * search for users.
     * <p>
     * GET /api/v1/users/search
     *
     * @param query search string.
     * @return list of users.
     * @throws GogsClientException
     */
    public UserSearchResult search(final String query) throws GogsClientException {
        return search(query, 0);
    }

    /**
     * search for users.
     * <p>
     * GET /api/v1/users/search
     *
     * @param query search string.
     * @param limit limit number of search result.
     * @return list of users.
     * @throws GogsClientException
     */
    public UserSearchResult search(final String query, final int limit) throws GogsClientException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("q", query);
        parameters.put("limit", String.valueOf(limit));

        return getClient().get(UserSearchResult.class, parameters, "users", "search");
    }

    /**
     * get user info for specified user.
     * <p>
     * GET /api/v1/users/:username
     *
     * @param username name of user.
     * @return user info.
     * @throws GogsClientException
     */
    public User getInfo(final String username) throws GogsClientException {
        return getClient().get(User.class, "users", username);
    }

    /**
     * get public key list of specified user.
     * <p>
     * GET /api/v1/users/:username/keys
     *
     * @param username name of user.
     * @return list of public keys.
     * @throws GogsClientException
     */
    public List<PublicKey> listPublicKeys(final String username) throws GogsClientException {
        return getClient().get(new GenericType<List<PublicKey>>() {
        }, "users", username, "keys");
    }

    /**
     * get user list of users who follows specified user.
     * <p>
     * GET /api/v1/users/:username/followers
     *
     * @param username name of user.
     * @return list of followers.
     * @throws GogsClientException
     */
    public List<User> listFollowers(final String username) throws GogsClientException {
        return getClient().get(new GenericType<List<User>>() {
        }, "users", username, "followers");
    }

    /**
     * get user list of users who follows signed in user.
     * <p>
     * GET /api/v1/user/followers
     *
     * @return list of followers.
     * @throws GogsClientException
     */
    public List<User> listFollowers() throws GogsClientException {
        return getClient().get(new GenericType<List<User>>() {
        }, "user", "followers");
    }

    /**
     * get user list of specified user that follow others.
     * <p>
     * GET /api/v1/users/following
     *
     * @param username name of user.
     * @return list of users.
     * @throws GogsClientException
     */
    public List<User> listFollowing(final String username) throws GogsClientException {
        return getClient().get(new GenericType<List<User>>() {
        }, "user", username, "following");
    }

    /**
     * get user list of signed in user that follow others.
     * <p>
     * GET /api/v1/user/following
     *
     * @return list of users.
     * @throws GogsClientException
     */
    public List<User> listFollowing() throws GogsClientException {
        return getClient().get(new GenericType<List<User>>() {
        }, "user", "following");
    }

    /**
     * check if specified user follow another user.
     * <p>
     * GET /api/v1/users/:username/following/:target
     *
     * @param username       name of user to check.
     * @param targetUsername following user to check.
     * @throws GogsClientException
     */
    public void checkFollowing(final String username, final String targetUsername) throws GogsClientException {
        getClient().get(Void.class, "users", username, "following", targetUsername);
    }

    /**
     * check if signed in user follow another user.
     * <p>
     * GET /api/v1/user/following/:target
     *
     * @param targetUsername following user to check.
     * @throws GogsClientException
     */
    public void checkFollowing(final String targetUsername) throws GogsClientException {
        getClient().get(Void.class, "user", "following", targetUsername);
    }

    /**
     * get signed in user info.
     * <p>
     * GET /api/v1/user
     *
     * @return user info.
     * @throws GogsClientException
     */
    public User getUser() throws GogsClientException {
        return getClient().get(User.class, "user");
    }

    /**
     * get registered user emails.
     * <p>
     * GET /api/v1/user/emails
     *
     * @return list of emails.
     * @throws GogsClientException
     */
    public List<Email> listUserEmails() throws GogsClientException {
        return getClient().get(new GenericType<List<Email>>() {
        }, "user", "emails");
    }

    /**
     * add new email address and return all registered emails.
     * <p>
     * PUT /api/v1/user/emails
     *
     * @param emailList list of emails to add.
     * @return list of emails.
     * @throws GogsClientException
     */
    public List<Email> addEmail(final EmailList emailList) throws GogsClientException {
        return getClient().post(new GenericType<List<Email>>() {
        }, emailList, "user", "emails");
    }

    /**
     * delete user email.
     * <p>
     * DELETE /api/v1/user/emails
     *
     * @param emailList email list to delete.
     * @throws GogsClientException
     */
    public void deleteEmail(final EmailList emailList) throws GogsClientException {
        getClient().delete(emailList, "user", "emails");
    }

    /**
     * follow specified user.
     * <p>
     * PUT /api/v1/user/following/:target
     *
     * @param username name of user to follow.
     * @throws GogsClientException
     */
    public void follow(final String username) throws GogsClientException {
        getClient().put(Void.class, null, "user", "following", username);
    }

    /**
     * unfollow specified user.
     * <p>
     * DELETE /api/v1/user/following/:target
     *
     * @param username name of user to unfollow.
     * @throws GogsClientException
     */
    public void unfollow(final String username) throws GogsClientException {
        getClient().delete("user", "following", username);
    }

    /**
     * get public key list of signed in user.
     * <p>
     * GET /api/v1/user/keys
     *
     * @return list of public keys.
     * @throws GogsClientException
     */
    public List<PublicKey> listPublicKeys() throws GogsClientException {
        return getClient().get(new GenericType<List<PublicKey>>() {
        }, "user", "keys");
    }

    /**
     * add new public key to signed in user.
     * <p>
     * POST /api/v1/user/keys
     *
     * @param publicKey public key.
     * @return added public key.
     * @throws GogsClientException
     */
    public PublicKey addPublicKey(final PublicKey publicKey) throws GogsClientException {
        return getClient().post(PublicKey.class, publicKey, "user", "keys");
    }

    /**
     * get public key of signed in user.
     * <p>
     * GET /api/v1/user/keys/:id
     *
     * @param id public key is.
     * @return public key.
     * @throws GogsClientException
     */
    public PublicKey getPublicKey(final String id) throws GogsClientException {
        return getClient().get(PublicKey.class, "user", "keys", id);
    }

    /**
     * delete public key of signed in user.
     * <p>
     * DELETE /api/v1/user/keys/:id
     *
     * @param publicKeyId public key id for deletion.
     * @throws GogsClientException
     */
    public void deletePublicKey(final String publicKeyId) throws GogsClientException {
        getClient().delete("user", "keys", publicKeyId);
    }
}
