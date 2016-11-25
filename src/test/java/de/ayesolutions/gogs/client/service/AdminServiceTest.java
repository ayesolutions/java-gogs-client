package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.AbstractGogsTest;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.Organization;
import de.ayesolutions.gogs.client.model.Team;
import de.ayesolutions.gogs.client.model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class AdminServiceTest extends AbstractGogsTest {

    private AdminService service = new AdminService(API_ADMIN);

    private static Organization dummyOrganization;

    private static String organizationName = UUID.randomUUID().toString();

    private static User dummyUser;

    private static String userId = UUID.randomUUID().toString().substring(0, 10);

    private static String teamId;

    private static String teamName = String.valueOf(Math.random());

    private static Team dummyTeam;

    @Override
    public void createDummyObjects() {

    }

    @BeforeClass
    public static void setUp() {
        organizationName = UUID.randomUUID().toString();

        dummyOrganization = new Organization();
        dummyOrganization.setUsername(organizationName);
        dummyOrganization.setFullName(organizationName + " - test");
        dummyOrganization.setDescription("test organization");
        dummyOrganization.setWebsite("http://localhost");
        dummyOrganization.setLocation("somewhere");

        dummyUser = new User();
        dummyUser.setUsername(userId);
        dummyUser.setPassword("pass");
        dummyUser.setEmail(userId + "@aye-solutions.de");
        dummyUser.setFullName(userId + " - test");

        dummyTeam = new Team();
        dummyTeam.setName(teamName);
        dummyTeam.setDescription("test team");
        dummyTeam.setPermission("admin");

        AdminService service = new AdminService(API_ADMIN);

        Organization organization = service.createOrganization(USERNAME_ADMIN, dummyOrganization);
        Assert.assertNotNull(organization);
        checkOrganization(organization);

        OrganizationService organizationService = new OrganizationService(API_USER);
        teamId = organizationService.listTeams(organizationName).get(0).getId().toString();
    }

    @Test(expected = GogsClientException.class)
    public void createOrganizationInvalid() {
        Assert.assertNull(service.createOrganization(USERNAME_UNKNOWN, dummyOrganization));
    }

    @Test
    public void createUser() throws Exception {
        User user = service.createUser(dummyUser);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(userId, user.getUsername());
        Assert.assertEquals(userId + "@aye-solutions.de", user.getEmail());
        Assert.assertEquals(userId + " - test", user.getFullName());
        Assert.assertTrue(user.getPassword() == null || user.getPassword().isEmpty());

        service.deleteUser(userId);
    }

    @Test
    public void updateUser() throws Exception {
        User user = service.createUser(dummyUser);
        Assert.assertNotNull(user.getId());

        user = service.updateUser(userId, user);
        Assert.assertNotNull(user.getId());

        service.deleteUser(userId);
    }

    @Test
    public void deleteUser() throws Exception {
        User user = service.createUser(dummyUser);
        Assert.assertNotNull(user.getId());

        service.deleteUser(userId);
    }

    @Test(expected = GogsClientException.class)
    public void deleteUserInvalid() {
        service.deleteUser(userId);
    }

    @Test
    public void createTeam() throws Exception {
        Team team = service.createTeam(organizationName, dummyTeam);
        Assert.assertNotNull(team.getId());
        Assert.assertEquals(teamName, team.getName());
        Assert.assertEquals("test team", team.getDescription());
        Assert.assertEquals("admin", team.getPermission());
    }

    @Test
    public void addTeamMember() throws Exception {
        service.addTeamMember(teamId, USERNAME_USER);
        service.deleteTeamMember(teamId, USERNAME_USER);
    }

    @Test(expected = GogsClientException.class)
    public void addTeamMemberInvalid() throws Exception {
        service.addTeamMember(teamId, USERNAME_UNKNOWN);
    }

    @Test
    public void deleteTeamMember() throws Exception {
        service.addTeamMember(teamId, USERNAME_USER);
        service.deleteTeamMember(teamId, USERNAME_USER);
    }

    @Test(expected = GogsClientException.class)
    public void deleteTeamMemberInvalid() throws Exception {
        service.deleteTeamMember(teamId, USERNAME_UNKNOWN);
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
}