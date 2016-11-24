package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.AbstractGogsTest;
import de.ayesolutions.gogs.client.model.Organization;
import de.ayesolutions.gogs.client.model.Team;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class OrganizationServiceTest extends AbstractGogsTest {

    private static String organizationName;

    private static String teamId;

    @BeforeClass
    public static void setUp() {
        organizationName = UUID.randomUUID().toString();

        OrganizationService service = new OrganizationService(API_ADMIN);

        Organization organization = new Organization();
        organization.setUsername(organizationName);
        organization.setFullName(organizationName + " - test");
        organization.setDescription("test organization");
        organization.setWebsite("http://localhost");
        organization.setLocation("somewhere");

        organization = service.createOrganization(USERNAME_ADMIN, organization);
        Assert.assertNotNull(organization);
        checkOrganization(organization);

        organization = service.createOrganization(USERNAME_UNKNOWN, organization);
        Assert.assertNull(organization);

        teamId = service.listTeams(organizationName).get(0).getId().toString();
    }

    @Test
    public void listOrganisations() throws Exception {
        OrganizationService service = new OrganizationService(API_ADMIN);

        List<Organization> organizationList = service.listOrganisations();
        Assert.assertTrue(organizationList.size() > 0);

        organizationList = service.listOrganisations(USERNAME_USER);
        Assert.assertTrue(organizationList.size() == 0);

        organizationList = service.listOrganisations(USERNAME_UNKNOWN);
        Assert.assertEquals(0, organizationList.size());

        // TODO: organization delete method not implemented
    }

    @Test
    public void getOrganization() throws Exception {
        OrganizationService service = new OrganizationService(API_ADMIN);

        Organization organization = service.getOrganization(organizationName);
        Assert.assertNotNull(organization);

        organization = service.getOrganization(USERNAME_UNKNOWN);
        Assert.assertNull(organization);

        // TODO: organization delete method not implemented
    }

    @Test
    public void updateOrganization() throws Exception {
        OrganizationService service = new OrganizationService(API_ADMIN);

        Organization organization = service.getOrganization(organizationName);
        Assert.assertNotNull(organization);

        organization = service.updateOrganization(organization.getUsername(), organization);
        checkOrganization(organization);

        Assert.assertNull(service.updateOrganization(USERNAME_UNKNOWN, organization));

        // TODO: organization delete method not implemented
    }

    @Test
    public void listTeams() throws Exception {
        OrganizationService service = new OrganizationService(API_ADMIN);

        List<Team> teamList = service.listTeams(organizationName);
        Assert.assertTrue(teamList.size() > 0);
    }

    @Test
    public void createTeam() throws Exception {
        OrganizationService service = new OrganizationService(API_ADMIN);

        String teamName = String.valueOf(Math.random());

        Team team = new Team();
        team.setName(teamName);
        team.setDescription("test team");
        team.setPermission("admin");

        team = service.createTeam(organizationName, team);
        Assert.assertNotNull(team.getId());
        Assert.assertEquals(teamName, team.getName());
        Assert.assertEquals("test team", team.getDescription());
        Assert.assertEquals("admin", team.getPermission());
    }

    @Test
    public void addTeamMember() throws Exception {
        OrganizationService service = new OrganizationService(API_ADMIN);
        Assert.assertTrue(service.addTeamMember(teamId, USERNAME_USER));
        Assert.assertFalse(service.addTeamMember(teamId, USERNAME_UNKNOWN));
        Assert.assertTrue(service.deleteTeamMember(teamId, USERNAME_USER));
        Assert.assertFalse(service.deleteTeamMember(teamId, USERNAME_UNKNOWN));
    }

    @Test
    public void deleteTeamMember() throws Exception {
        OrganizationService service = new OrganizationService(API_ADMIN);
        Assert.assertTrue(service.addTeamMember(teamId, USERNAME_USER));
        Assert.assertTrue(service.deleteTeamMember(teamId, USERNAME_USER));
    }

    public static void checkOrganization(Organization organization) {
        Assert.assertNotNull(organization.getId());
        Assert.assertNotNull(organization.getUsername());
        Assert.assertEquals(organization.getUsername() + " - test", organization.getFullName());
        Assert.assertEquals("test organization", organization.getDescription());
        Assert.assertTrue(organization.getAvatarUrl().startsWith("http://helios.asnet.aye-solutions.de:13001/"));
        Assert.assertEquals("somewhere", organization.getLocation());
        Assert.assertEquals("http://localhost", organization.getWebsite());
    }

    public static Organization findOrganization(List<Organization> organizationList, String username) {
        for (Organization organization : organizationList) {
            if (organization.getUsername().equals(username)) {
                return organization;
            }
        }
        return null;
    }
}