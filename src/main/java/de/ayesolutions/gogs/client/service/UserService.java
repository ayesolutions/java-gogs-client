package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/**
 * Gogs user service call class.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class UserService extends BaseService {

    /**
     * default constructor.
     *
     * @param client gogs client.
     */
    public UserService(final GogsClient client) {
        super(client);
    }

    /**
     * create new user.
     * <p>
     * POST /api/v1/admin/users
     * Response 201, 422, 500
     *
     * @param user user.
     * @return created user.
     */
    public User createUser(final User user) {
        Response response = getClient().getWebTarget()
                .path("admin").path("users")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(user));

        return handleResponse(response, User.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * update user information.
     * <p>
     * PATCH /api/v1/admin/users/:username
     * Response 200, 404, 422, 500
     *
     * @param user user
     * @return updated user.
     */
    public User updateUser(final String username, final User user) {
        Response response = getClient().getWebTarget()
                .path("admin").path("users").path(username)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .method("PATCH", Entity.json(user));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, User.class, Response.Status.OK.getStatusCode());
    }

    /**
     * delete user.
     * <p>
     * DELETE /api/v1/admin/users/:username
     * Response 204, 404, 422, 500
     *
     * @return true if successful otherwise false.
     */
    public Boolean deleteUser(final String username) {
        Response response = getClient().getWebTarget()
                .path("admin").path("users").path(username)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .delete();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        }

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }

    /**
     * list all user access tokens.
     * <p>
     * PUT /api/v1/users/:username/tokens (BASIC AUTHORIZATION)
     * Reponse 200, 500
     *
     * @param username username.
     * @return list of user access tokens.
     */
    public List<AccessToken> listAccessTokens(final String username) {
        if (getClient().getAccessToken() == null) {
            throw new GogsClientException("no access token available");
        }

        Response response = getClient().getWebTarget()
                .path("users").path(username).path("tokens")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", getClient().getAccessToken().getBasicAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<AccessToken>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * create new access token for user.
     * <p>
     * POST /api/v1/users/:username/tokens (BASIC AUTHORIZATION)
     * Reponse 201, 500
     *
     * @param name name of access token.
     * @return new generated access token.
     */
    public AccessToken createToken(final String username, final String name) {
        if (getClient().getAccessToken() == null) {
            throw new GogsClientException("no access token available");
        }

        Entity<AccessToken> entity = Entity.json(new AccessToken(name, null));
        Response response = getClient().getWebTarget()
                .path("users").path(username).path("tokens")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", getClient().getAccessToken().getBasicAuthorization())
                .post(entity);

        return handleResponse(response, AccessToken.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * search for users.
     * <p>
     * GET /api/v1/users/search (UNAUTHORIZED AND BASIC AUTHORIZATION)
     * Reponse 200, 500
     *
     * @param query search string
     * @return list of users
     */
    public UserSearchResult search(final String query) {
        return search(query, 0);
    }

    /**
     * search for users.
     * <p>
     * GET /api/v1/users/search (UNAUTHORIZED AND BASIC AUTHORIZATION)
     * Reponse 200, 500
     *
     * @param query search string.
     * @param limit limit number of search result.
     * @return list of users.
     */
    public UserSearchResult search(final String query, final int limit) {
        WebTarget webTarget = getClient().getWebTarget()
                .path("users").path("search");

        webTarget = webTarget.queryParam("q", query);

        if (limit > 0) {
            webTarget = webTarget.queryParam("limit", limit);
        }

        Invocation.Builder builder = webTarget.request();
        if (getClient().getAccessToken() != null) {
            builder.header("Authorization", getClient().getAccessToken().getBasicAuthorization());
        }

        Response response = builder.get();

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new GogsClientException(GogsClientException.createMessage(response));
        }

        UserSearchResult result = response.readEntity(UserSearchResult.class);

        return result;
    }

    /**
     * get user info for specified user.
     * <p>
     * GET /api/v1/users/:username (UNAUTHORIZED AND BASIC AUTHORIZATION)
     * Response 200, 404, 500
     *
     * @param username name of user.
     * @return user info.
     */
    public User getInfo(final String username) {
        WebTarget webTarget = getClient().getWebTarget()
                .path("users").path(username);

        Invocation.Builder builder = webTarget.request();
        if (getClient().getAccessToken() != null) {
            builder.header("Authorization", getClient().getAccessToken().getBasicAuthorization());
        }

        Response response = builder.get();

        return handleResponse(response, User.class, Response.Status.OK.getStatusCode());
    }

    /**
     * get public key list of specified user.
     * <p>
     * GET /api/v1/users/:username/keys (TOKEN AUTHORIZATION)
     * Response 200, 404, 500
     *
     * @param username name of user.
     * @return list of public keys.
     */
    public List<PublicKey> listPublicKeys(final String username) {
        Response response = getClient().getWebTarget()
                .path("users").path(username).path("keys")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return Collections.emptyList();
        }

        return handleResponse(response, new GenericType<List<PublicKey>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * get user list of users who follows specified user.
     * <p>
     * GET /api/v1/users/:username/followers (TOKEN AUTHORIZATION)
     * Response 200, 404, 500
     *
     * @param username name of user.
     * @return list of followers.
     */
    public List<User> listFollowers(final String username) {
        Response response = getClient().getWebTarget()
                .path("users").path(username).path("followers")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return Collections.emptyList();
        }

        return handleResponse(response, new GenericType<List<User>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * get user list of users who follows signed in user.
     * <p>
     * GET /api/v1/user/followers (TOKEN AUTHORIZATION)
     * Response 200, 500
     *
     * @return list of followers.
     */
    public List<User> listFollowers() {
        Response response = getClient().getWebTarget()
                .path("user").path("followers")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<User>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * get user list of specified user that follow others.
     * <p>
     * GET /api/v1/users/following (TOKEN AUTHORIZATION)
     * Response 200, 404, 500
     *
     * @param username name of user.
     * @return list of users.
     */
    public List<User> listFollowing(final String username) {
        Response response = getClient().getWebTarget()
                .path("users").path(username).path("following")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return Collections.emptyList();
        }

        return handleResponse(response, new GenericType<List<User>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * get user list of signed in user that follow others.
     * <p>
     * GET /api/v1/user/following (TOKEN AUTHORIZATION)
     * Response 200, 500
     *
     * @return list of users.
     */
    public List<User> listFollowing() {
        Response response = getClient().getWebTarget()
                .path("user").path("following")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<User>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * check if specified user follow another user.
     * <p>
     * GET /api/v1/users/:username/following/:target (TOKEN AUTHORIZATION)
     * Response 204, 404, 500
     *
     * @param username       name of user to check.
     * @param targetUsername following user to check.
     * @return true if username follow targetUsername otherwise false.
     */
    public Boolean checkFollowing(final String username, final String targetUsername) {
        Response response = getClient().getWebTarget()
                .path("users").path(username).path("following").path(targetUsername)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        }

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }

    /**
     * check if signed in user follow another user.
     * <p>
     * GET /api/v1/user/following/:target (TOKEN AUTHORIZATION)
     * Response 204, 404, 500
     *
     * @param targetUsername following user to check.
     * @return true if username follow targetUsername otherwise false.
     */
    public Boolean checkFollowing(final String targetUsername) {
        Response response = getClient().getWebTarget()
                .path("user").path("following").path(targetUsername)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        }

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }

    /**
     * get signed in user info.
     * <p>
     * GET /api/v1/user (TOKEN AUTHORIZATION)
     * Response 200, 400
     *
     * @return user info.
     */
    public User getUser() {
        Response response = getClient().getWebTarget()
                .path("user").request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, User.class, Response.Status.OK.getStatusCode());
    }

    /**
     * get registered user emails.
     * <p>
     * GET /api/v1/user/emails
     * Response 200, 500
     *
     * @return list of emails.
     */
    public List<Email> listUserEmails() {
        Response response = getClient().getWebTarget()
                .path("user").path("emails")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<Email>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * add new email address and return all registered emails.
     * <p>
     * PUT /api/v1/user/emails
     * Response 201, 422, 500
     *
     * @param emailList list of emails to add.
     * @return list of emails.
     */
    public List<Email> addEmail(final EmailList emailList) {
        Response response = getClient().getWebTarget()
                .path("user").path("emails")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(emailList));

        return handleResponse(response, new GenericType<List<Email>>() {
        }, Response.Status.CREATED.getStatusCode());
    }

    /**
     * delete user email.
     * <p>
     * DELETE /api/v1/user/emails
     * Response 204, 500
     *
     * @param emailList email list to delete.
     * @return true if successful deleted.
     */
    public Boolean deleteEmail(final EmailList emailList) {
        Response response = getClient().getWebTarget()
                .path("user").path("emails")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .method("DELETE", Entity.json(emailList));

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }

    /**
     * follow specified user.
     * <p>
     * PUT /api/v1/user/following/:target
     * Response 204, 404, 500
     *
     * @param username name of user to follow.
     * @return true if successful deleted otherwise false.
     */
    public Boolean follow(String username) {
        Response response = getClient().getWebTarget()
                .path("user").path("following").path(username)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .put(Entity.text(""));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        }

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }

    /**
     * unfollow specified user.
     * <p>
     * DELETE /api/v1/user/following/:target
     * Response 204, 404, 500
     *
     * @param username name of user to unfollow.
     * @return true if successful deleted otherwise false.
     */
    public Boolean unfollow(String username) {
        Response response = getClient().getWebTarget()
                .path("user").path("following").path(username)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .delete();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        }

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }

    /**
     * get public key list of signed in user.
     * <p>
     * GET /api/v1/user/keys
     * Response 200, 500
     *
     * @return list of public keys.
     */
    public List<PublicKey> listPublicKeys() {
        Response response = getClient().getWebTarget()
                .path("user").path("keys")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<PublicKey>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * add new public key to signed in user.
     * <p>
     * POST /api/v1/user/keys
     * Response 200, 500
     *
     * @param publicKey public key.
     * @return added public key.
     */
    public PublicKey addPublicKey(PublicKey publicKey) {
        Response response = getClient().getWebTarget()
                .path("user").path("keys")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(publicKey));

        return handleResponse(response, PublicKey.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * get public key of signed in user.
     * <p>
     * GET /api/v1/user/keys/:id
     * Response 200, 404, 500
     *
     * @param id public key is.
     * @return public key.
     */
    public PublicKey getPublicKey(String id) {
        Response response = getClient().getWebTarget()
                .path("user").path("keys").path(id)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, PublicKey.class, Response.Status.OK.getStatusCode());
    }

    /**
     * delete public key of signed in user.
     * <p>
     * DELETE /api/v1/user/keys/:id
     * Response 204, 403, 500
     *
     * @param id public key id for deletion.
     */
    public Boolean deletePublicKey(String id) {
        Response response = getClient().getWebTarget()
                .path("user").path("keys").path(id)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .delete();

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }
}
