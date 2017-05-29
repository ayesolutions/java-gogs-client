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

    private OrganizationService service = new OrganizationService(API_USER);

    private static Organization dummyOrganization;

    private static String organizationName = UUID.randomUUID().toString();

    @Override
    public void createDummyObjects() {

    }

    @BeforeClass
    public static void setUp() {
        checkTestServer();

        dummyOrganization = new Organization();
        dummyOrganization.setUsername(organizationName);
        dummyOrganization.setFullName(organizationName + " - test");
        dummyOrganization.setDescription("test organization");
        dummyOrganization.setWebsite("http://localhost");
        dummyOrganization.setLocation("somewhere");

        AdminService service = new AdminService(API_ADMIN);
        Organization organization = service.createOrganization(USERNAME_USER, dummyOrganization);
        Assert.assertNotNull(organization);
    }

    @Test
    public void listOrganisations() throws Exception {
        List<Organization> organizationList = service.listOrganisations();
        Assert.assertTrue(organizationList.size() > 0);

        organizationList = service.listOrganisations(USERNAME_ADMIN);
        Assert.assertEquals(0, organizationList.size());
    }

    @Test
    public void listOrganisationsInvalid() throws Exception {
        Assert.assertTrue(service.listOrganisations(USERNAME_UNKNOWN).size() == 0);
    }

    @Test
    public void getOrganization() throws Exception {
        Organization organization = service.getOrganization(organizationName);
        Assert.assertNotNull(organization);
    }

    @Test
    public void getOrganizationInvalid() throws Exception {
        Assert.assertNull(service.getOrganization(USERNAME_UNKNOWN));
    }

    @Test
    public void updateOrganization() throws Exception {
        Organization organization = service.getOrganization(organizationName);
        Assert.assertNotNull(organization);
        Assert.assertNotNull(service.updateOrganization(organization.getUsername(), organization));

    }

    @Test
    public void updateOrganizationInvalid() throws Exception {
        Assert.assertNull(service.updateOrganization(USERNAME_UNKNOWN, dummyOrganization));
    }

    @Test
    public void listTeams() throws Exception {
        List<Team> teamList = service.listTeams(organizationName);
        Assert.assertEquals(1, teamList.size());
    }
}