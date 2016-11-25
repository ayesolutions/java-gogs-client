package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.model.Status;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * gogs build service service call class.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class BuildService extends BaseService {

    /**
     * default constructor.
     *
     * @param client
     */
    public BuildService(GogsClient client) {
        super(client);
    }

    /**
     * get list of build states of repository.
     * <p>
     * GET /api/v1/repos/:username/:reponame/statuses
     * Response 200, 404, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @return list of states.
     */
    public List<Status> listStatuses(String username, String repositoryName) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("statuses")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, new GenericType<List<Status>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * create new build status for repository.
     * <p>
     * POST /api/v1/repos/:username/:reponame/statuses/:sha
     * Response 201, 404, 500
     *
     * @param username       username.
     * @param repositoryName repository name.
     * @param status         status.
     * @return created status.
     */
    public Status createStatus(String username, String repositoryName, String sha, Status status) {
        Response response = getClient().getWebTarget()
                .path("repos").path(username).path(repositoryName).path("statuses")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, Status.class, Response.Status.CREATED.getStatusCode());
    }
}
