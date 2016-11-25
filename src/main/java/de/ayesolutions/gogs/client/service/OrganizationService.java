package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.model.Organization;
import de.ayesolutions.gogs.client.model.Team;

import javax.ws.rs.core.GenericType;
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
     *
     * @return list of organizations.
     */
    public List<Organization> listOrganisations() {
        return getClient().get(new GenericType<List<Organization>>() {
        }, "user", "orgs");
    }

    /**
     * get organization list of specified user.
     * <p>
     * GET /api/v1/users/:username/orgs
     *
     * @param username name of user.
     * @return list of organizations.
     */
    public List<Organization> listOrganisations(final String username) {
        return getClient().get(new GenericType<List<Organization>>() {
        }, "users", username, "orgs");
    }

    /**
     * get specified organization.
     * <p>
     * GET /api/v1/orgs/:orgname
     *
     * @param organizationName name of organization.
     * @return organization.
     */
    public Organization getOrganization(final String organizationName) {
        return getClient().get(Organization.class, "orgs", organizationName);
    }

    /**
     * update organization information.
     * <p>
     * PATCH /api/v1/orgs/:orgname
     *
     * @param organizationName name of organization.
     * @return organization.
     */
    public Organization updateOrganization(final String organizationName, final Organization organization) {
        return getClient().patch(Organization.class, organization, "orgs", organizationName);
    }

    /**
     * get list of teams from organization.
     * <p>
     * GET /api/v1/orgs/:orgname/teams
     *
     * @param organizationName name of organization.
     * @return list of teams.
     */
    public List<Team> listTeams(final String organizationName) {
        return getClient().get(new GenericType<List<Team>>() {
        }, "orgs", organizationName, "teams");
    }
}
