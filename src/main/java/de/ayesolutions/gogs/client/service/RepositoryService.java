package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.model.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class RepositoryService extends BaseService {

    /**
     * default constructor.
     *
     * @param client gogs client.
     */
    public RepositoryService(GogsClient client) {
        super(client);
    }

    /**
     * list all repository for signed in user.
     * <p>
     * GET /api/v1/user/repos
     * Response 200, 500
     *
     * @return list of repositories.
     */
    public List<Repository> listRepositories() {
        Response response = getClient().getWebTarget()
                .path("user").path("repos")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<Repository>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * create user repository.
     * <p>
     * POST /api/v1/user/repos
     * Response 201, 422, 500
     *
     * @param repository repository.
     * @return created repository.
     */
    public Repository createRepository(final CreateRepository repository) {
        Response response = getClient().getWebTarget()
                .path("user").path("repos")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(repository));

        return handleResponse(response, Repository.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * create organization repository.
     * <p>
     * POST /api/v1/org/:orgname/repos
     * Response 201, 404, 422, 500
     *
     * @param organizationName organization name.
     * @param repository       repository.
     * @return created repository.
     */
    public Repository createOrganizationRepository(final String organizationName, final CreateRepository repository) {
        Response response = getClient().getWebTarget()
                .path("org").path(organizationName).path("repos")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(repository));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, Repository.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * create new repository to specified user.
     * <p>
     * POST /api/v1/admin/users/:username/repos
     * Response 201, 404, 422, 500
     *
     * @param username   name of user.
     * @param repository repository.
     * @return created repository.
     */
    public Repository createRepository(final String username, final Repository repository) {
        Response response = getClient().getWebTarget()
                .path("admin").path("users").path(username).path("repos")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(repository));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, Repository.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * search for repositories.
     * <p>
     * GET /api/v1/repos/search
     * Response 200, 500
     *
     * @param query  query string.
     * @param userId user id. (default for all 0)
     * @param limit  limit value result. (default 10)
     * @return search result of found repositories.
     */
    public List<Repository> search(final String query, final long userId, final int limit) {
        Response response = getClient().getWebTarget()
                .path("user").path("repos")
                .queryParam("q", query)
                .queryParam("uid", userId)
                .queryParam("limit", limit)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<Repository>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * migrate existing repository to gogs account.
     * <p>
     * POST /api/v1/repos/migrate
     * Response 201, 403, 422, 500
     *
     * @param repository repository.
     * @return migrated repository.
     */
    public Repository migrate(final MigrationRepository repository) {
        Response response = getClient().getWebTarget()
                .path("repos").path("migrate")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(repository));

        return handleResponse(response, Repository.class, Response.Status.OK.getStatusCode());
    }

    /**
     * get repository.
     * <p>
     * GET /api/v1/repos/:username/:reponame
     * Response 200, 404, 422, 500
     *
     * @param username       user name.
     * @param repositoryName repository name.
     * @return repository.
     */
    public Repository getRepository(final String username, final String repositoryName) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, Repository.class, Response.Status.OK.getStatusCode());
    }

    /**
     * delete repository from user.
     * <p>
     * DELETE /api/v1/repos/:username/:reponame
     * Response 204, 404, 422, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @return true if success otherwise false.
     */
    public boolean deleteRepository(final String username, final String repositoryName) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName)
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
     * get list of web hooks from repository.
     * <p>
     * GET /api/v1/repos/:username/:reponame/hooks
     * Response 200, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @return list of web hooks.
     */
    public List<WebHook> listWebHooks(final String username, final String repositoryName) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("hooks")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<WebHook>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * create new web hook for repository.
     * <p>
     * POST /api/v1/repos/:username/:reponame/hooks
     * Response 201, 422, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param webHook        web hook.
     * @return created web hook.
     */
    public WebHook createWebHook(final String username, final String repositoryName, final WebHook webHook) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("hooks")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(webHook));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, WebHook.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * update web hook information.
     * <p>
     * PATCH /api/v1/repos/:username/:reponame/hooks/:id
     * Response 200, 404, 422, 500
     *
     * @param username       username.
     * @param repositoryName repository name
     * @param webHook        web hook.
     * @return updated web hook.
     */
    public WebHook updateWebHook(final String username, final String repositoryName, final WebHook webHook) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("hooks").path(webHook.getId().toString())
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .method("PATCH", Entity.json(webHook));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, WebHook.class, Response.Status.OK.getStatusCode());
    }

    /**
     * delete web hook from repository.
     * <p>
     * DELETE /api/v1/repos/:username/:reponame/hooks/:id
     * Response 204, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param webHookId      web hook id.
     * @return true if success otherwise false.
     */
    public boolean deleteWebHook(final String username, final String repositoryName, final long webHookId) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("hooks").path(String.valueOf(webHookId))
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
     * add new collaborator to repository.
     * <p>
     * PUT /api/v1/repos/:username/:reponame/collaborator/:id
     * Response 204, 422, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param collaborator   collaborator.
     * @return added collaborator.
     */
    public boolean addCollaborator(final String username, final String repositoryName, final String collaboratorId,
                                   final Collaborator collaborator) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("collaborator").path(collaboratorId)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .put(Entity.json(collaborator));

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }

    /**
     * get raw content of file in repository.
     * <p>
     * GET /api/v1/repos/:username/:reponame/raw/:path
     * Response
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param path           path to file.
     * @return data byte array.
     */
    public byte[] getRawFile(final String username, final String repositoryName, final String path) {
        throw new UnsupportedOperationException("receive binary data currently not supported");
    }

    /**
     * get repository archive.
     * <p>
     * GET /api/v1/repos/:username/:reponame/archive/:path
     * Response
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param path           path to archive.
     * @return data byte array.
     */
    public byte[] getArchive(final String username, final String repositoryName, final String path) {
        throw new UnsupportedOperationException("receive binary data currently not supported");
    }

    /**
     * list branches of repository.
     * <p>
     * GET /api/v1/repos/:username/:reponame/branches
     * Response 200, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @return list of branches.
     */
    public List<Branch> listBranches(final String username, final String repositoryName) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("branches")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<Branch>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * get specified branch.
     * <p>
     * GET /api/v1/repos/:username/:reponame/branches/:id
     * Response 200, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param branchId       branch id.
     * @return repository branch.
     */
    public Branch getBranch(final String username, final String repositoryName, final String branchId) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("branches").path(branchId)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, Branch.class, Response.Status.OK.getStatusCode());
    }

    /**
     * add new public key to specified user.
     * <p>
     * POST /api/v1/admin/users/:username/keys
     * Response 201, 404, 422, 500
     *
     * @param username  name of user.
     * @param publicKey public key.
     * @return added public key.
     */
    public PublicKey addPublicKey(final String username, final PublicKey publicKey) {
        Response response = getClient().getWebTarget()
                .path("admin").path("users").path(username).path("keys")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(publicKey));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, PublicKey.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * get list of deployment keys from repository.
     * <p>
     * GET /api/v1/repos/:username/:reponame/keys
     * Response 200, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @return list of deployment keys.
     */
    public List<PublicKey> getDeployKeys(final String username, final String repositoryName) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("keys")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<PublicKey>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * add new deployment key to repository.
     * <p>
     * POST /api/v1/repos/:username/:reponame/keys
     * Response 201, 422, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param publicKey      public key.
     * @return added public key.
     */
    public PublicKey addDeployKey(final String username, final String repositoryName, final PublicKey publicKey) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("keys")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(publicKey));

        return handleResponse(response, PublicKey.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * get specified deployment key.
     * <p>
     * GET /api/v1/repos/:username/:reponame/keys/:id
     * Response 200, 404, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param deployKeyId    deployment key id.
     * @return deployment key.
     */
    public PublicKey getDeployKey(final String username, final String repositoryName, final String deployKeyId) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("keys").path(deployKeyId)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, PublicKey.class, Response.Status.OK.getStatusCode());
    }

    /**
     * delete deployment key.
     * <p>
     * DELETE /api/v1/repos/:username/:reponame/keys/:id
     * Response 204, 403, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param deployKeyId    deployment key id.
     * @return true if success otherwise false.
     */
    public boolean deleteDeployKey(final String username, final String repositoryName, final String deployKeyId) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("keys").path(deployKeyId)
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
     * get editor configuration from repository.
     * <p>
     * GET /api/v1/repos/:username/:reponame/editorconfig/:path
     * Response 200, 404, 500
     *
     * @param username       username.
     * @param repositoryName repository.
     * @param path           path to editor configuration.
     * @return data byte array.
     */
    public EditorDefinition getEditorConfig(final String username, final String repositoryName, final String path) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("editorconfig").path(path)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, EditorDefinition.class, Response.Status.OK.getStatusCode());
    }

    /**
     * add team to another repository.
     * <p>
     * PUT /api/v1/admin/teams/:teamId/repos/:reponame
     * Response 204, 404, 500
     *
     * @param teamId         team id.
     * @param repositoryName repository name.
     */
    public boolean addTeamRepository(final String teamId, final String repositoryName) {
        Response response = getClient().getWebTarget()
                .path("admin").path("teams").path(teamId).path("repos").path(repositoryName)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .put(Entity.text(""));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        }

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }

    /**
     * delete team from repository.
     * <p>
     * DELETE /api/v1/admin/teams/:teamId/repos/:reponame
     * Response 204, 404, 500
     *
     * @param teamId         team id.
     * @param repositoryName repository name.
     * @return true if successful otherwise false.
     */
    public boolean deleteTeamRepository(final String teamId, final String repositoryName) {
        Response response = getClient().getWebTarget()
                .path("admin").path("teams").path(teamId).path("repos").path(repositoryName)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .delete();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return false;
        }

        handleResponse(response, Response.Status.NO_CONTENT.getStatusCode());

        return true;
    }
}
