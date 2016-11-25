package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.Organization;
import de.ayesolutions.gogs.client.model.Team;
import de.ayesolutions.gogs.client.model.User;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class AdminService extends BaseService {

    public AdminService(GogsClient client) {
        super(client);
    }

    /**
     * create new user.
     * <p>
     * POST /api/v1/admin/users
     *
     * @param user user.
     * @return created user.
     * @throws GogsClientException
     */
    public User createUser(final User user) throws GogsClientException {
        return getClient().post(User.class, user, "admin", "users");
    }

    /**
     * update user information.
     * <p>
     * PATCH /api/v1/admin/users/:username
     *
     * @param username username.
     * @param user     user
     * @return updated user.
     * @throws GogsClientException
     */
    public User updateUser(final String username, final User user) throws GogsClientException {
        return getClient().patch(User.class, user, "admin", "users", username);
    }

    /**
     * delete user.
     * <p>
     * DELETE /api/v1/admin/users/:username
     *
     * @param username username.
     * @return true if successful otherwise false.
     * @throws GogsClientException
     */
    public void deleteUser(final String username) throws GogsClientException {
        getClient().delete("admin", "users", username);
    }

    /**
     * create new organization to specified user.
     * <p>
     * POST /api/v1/admin/users/:username/orgs
     *
     * @param username     name of user.
     * @param organization organization.
     * @return created organization.
     * @throws GogsClientException
     */
    public Organization createOrganization(final String username, final Organization organization) throws GogsClientException {
        return getClient().post(Organization.class, organization, "admin", "users", username, "orgs");
    }

    /**
     * create new team.
     * <p>
     * POST /api/v1/admin/orgs/:orgname/teams
     *
     * @param organizationName organization name.
     * @param team             team.
     * @return created team.
     * @throws GogsClientException
     */
    public Team createTeam(final String organizationName, final Team team) throws GogsClientException {
        return getClient().post(Team.class, team, "admin", "orgs", organizationName, "teams");
    }

    /**
     * add team member to team.
     * <p>
     * PUT /api/v1/admin/teams/:teamId/members/:username
     *
     * @param teamId   team id.
     * @param username username.
     * @return true if successful otherwise false.
     * @throws GogsClientException
     */
    public void addTeamMember(final String teamId, final String username) throws GogsClientException {
        getClient().put(Void.class, null, "admin", "teams", teamId, "members", username);
    }

    /**
     * delete team member from team.
     * <p>
     * DELETE /api/v1/admin/teams/:teamId/members/:username
     *
     * @param teamId   team id.
     * @param username username.
     * @return true if successful otherwise false.
     * @throws GogsClientException
     */
    public void deleteTeamMember(final String teamId, final String username) throws GogsClientException {
        getClient().delete("admin", "teams", teamId, "members", username);
    }
}
