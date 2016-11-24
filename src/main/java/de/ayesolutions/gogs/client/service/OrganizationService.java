package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.model.Organization;
import de.ayesolutions.gogs.client.model.Team;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

/**
 * gogs organization service call class.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class OrganizationService extends BaseService {

    /**
     * default constructor.
     *
     * @param client gogs client.
     */
    public OrganizationService(final GogsClient client) {
        super(client);
    }

    /**
     * get organization list of signed in user.
     * <p>
     * GET /api/v1/user/orgs
     * Response 200, 500
     *
     * @return list of organizations.
     */
    public List<Organization> listOrganisations() {
        Response response = getClient().getWebTarget()
                .path("user").path("orgs")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<Organization>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * get organization list of specified user.
     * <p>
     * GET /api/v1/users/:username/orgs
     * Response 200, 404, 500
     *
     * @param username name of user.
     * @return list of organizations.
     */
    public List<Organization> listOrganisations(final String username) {
        Response response = getClient().getWebTarget()
                .path("users").path(username).path("orgs")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return Collections.emptyList();
        }

        return handleResponse(response, new GenericType<List<Organization>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * create new organization to specified user.
     * <p>
     * POST /api/v1/admin/users/:username/orgs
     * Response 201, 404, 422, 500
     *
     * @param username     name of user.
     * @param organization organization.
     * @return created organization.
     */
    public Organization createOrganization(final String username, final Organization organization) {
        Response response = getClient().getWebTarget()
                .path("admin").path("users").path(username).path("orgs")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(organization));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, Organization.class, Response.Status.CREATED.getStatusCode());
    }


    /**
     * get specified organization.
     * <p>
     * GET /api/v1/orgs/:orgname
     * Response 200, 404, 500
     *
     * @param name name of organization.
     * @return organization.
     */
    public Organization getOrganization(String name) {
        Response response = getClient().getWebTarget()
                .path("orgs").path(name)
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, Organization.class, Response.Status.OK.getStatusCode());
    }

    /**
     * update organization information.
     * <p>
     * PATCH /api/v1/orgs/:orgname
     * Response 200, 404, 500
     *
     * @param name name of organization.
     * @return organization.
     */
    public Organization updateOrganization(String name, Organization organization) {
        Response response = getClient().getWebTarget()
                .path("orgs").path(name)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .method("PATCH", Entity.json(organization));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return null;
        }

        return handleResponse(response, Organization.class, Response.Status.OK.getStatusCode());
    }

    /**
     * get list of teams from organization.
     * <p>
     * GET /api/v1/orgs/:orgname/teams
     * Response 200, 500
     *
     * @param name name of organization.
     * @return list of teams.
     */
    public List<Team> listTeams(String name) {
        Response response = getClient().getWebTarget()
                .path("orgs").path(name).path("teams")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .get();

        return handleResponse(response, new GenericType<List<Team>>() {
        }, Response.Status.OK.getStatusCode());
    }

    /**
     * create new team.
     * <p>
     * POST /api/v1/admin/orgs/:orgname/teams
     * Response 201, 422, 500
     *
     * @param organizationName organization name.
     * @param team             team.
     * @return created team.
     */
    public Team createTeam(final String organizationName, final Team team) {
        Response response = getClient().getWebTarget()
                .path("admin").path("orgs").path(organizationName).path("teams")
                .request()
                .header("Authorization", getClient().getAccessToken().getTokenAuthorization())
                .post(Entity.json(team));

        return handleResponse(response, Team.class, Response.Status.CREATED.getStatusCode());
    }

    /**
     * add team member to team.
     * <p>
     * PUT /api/v1/admin/teams/:teamId/members/:username
     * Response 204, 404, 422, 500
     *
     * @param teamId   team id.
     * @param username username.
     * @return true if successful otherwise false.
     */
    public Boolean addTeamMember(final String teamId, final String username) {
        Response response = getClient().getWebTarget()
                .path("admin").path("teams").path(teamId).path("members").path(username)
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
     * delete team member from team.
     * <p>
     * DELETE /api/v1/admin/teams/:teamId/members/:username
     * Response 204, 404, 500
     *
     * @param teamId   team id.
     * @param username username.
     * @return true if successful otherwise false.
     */
    public Boolean deleteTeamMember(final String teamId, final String username) {
        Response response = getClient().getWebTarget()
                .path("admin").path("teams").path(teamId).path("members").path(username)
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
