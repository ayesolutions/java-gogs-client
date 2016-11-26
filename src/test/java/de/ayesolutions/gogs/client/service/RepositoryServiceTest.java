package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.AbstractGogsTest;
import de.ayesolutions.gogs.client.model.CreateRepository;
import de.ayesolutions.gogs.client.model.Repository;
import de.ayesolutions.gogs.client.model.WebHook;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class RepositoryServiceTest extends AbstractGogsTest {

    private RepositoryService service = new RepositoryService(API_USER);

    private WebHook dummyWebHook;

    private CreateRepository dummyCreateRepository;

    private static String REPOSITORY_NAME = UUID.randomUUID().toString();

    @Override
    public void createDummyObjects() {
        dummyCreateRepository = new CreateRepository();
        dummyCreateRepository.setName(REPOSITORY_NAME);
        dummyCreateRepository.setDescription(REPOSITORY_NAME + " - desc");
        dummyCreateRepository.setAutoInit(true);
        dummyCreateRepository.setGitIgnores("Java");
        dummyCreateRepository.setLicense("Apache License 2.0");
        dummyCreateRepository.setPrivateRepository(false);
        dummyCreateRepository.setReadme("Default");

        dummyWebHook = new WebHook("gogs", "http://localhost/build/1", "json");
        dummyWebHook.setActive(true);
        dummyWebHook.getEvents().add("create");
        dummyWebHook.getEvents().add("push");
    }

    @Test
    public void listRepositories() throws Exception {
        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        List<Repository> repositoryList = service.listRepositories();
        Assert.assertEquals(1, repositoryList.size());

        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);
    }

    @Test
    public void createRepository() throws Exception {
        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);
    }

    @Test
    @Ignore
    public void createOrganizationRepository() throws Exception {

    }

    @Test
    public void search() throws Exception {
        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        List<Repository> repositoryList = service.search(REPOSITORY_NAME, 0, 1);
        Assert.assertEquals(1, repositoryList.size());

        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);
    }

    @Test
    @Ignore
    public void migrate() throws Exception {

    }

    @Test
    public void getRepository() throws Exception {
        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        repository = service.getRepository(USERNAME_USER, REPOSITORY_NAME);
        Assert.assertNotNull(repository);

        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);
    }

    @Test
    public void deleteRepository() throws Exception {
        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);
    }

    @Test
    public void listWebHooks() throws Exception {
        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        WebHook webHook = service.createWebHook(USERNAME_USER, REPOSITORY_NAME, dummyWebHook);
        Assert.assertNotNull(webHook.getId());

        List<WebHook> webHookList = service.listWebHooks(USERNAME_USER, REPOSITORY_NAME);
        Assert.assertEquals(1, webHookList.size());

        service.deleteWebHook(USERNAME_USER, REPOSITORY_NAME, webHook.getId());
        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);
    }

    @Test
    public void createWebHook() throws Exception {
        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        WebHook webHook = service.createWebHook(USERNAME_USER, REPOSITORY_NAME, dummyWebHook);
        Assert.assertNotNull(webHook.getId());

        Assert.assertNull(service.createWebHook(USERNAME_UNKNOWN, REPOSITORY_NAME, dummyWebHook));

        service.deleteWebHook(USERNAME_USER, REPOSITORY_NAME, webHook.getId());
        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);
    }

    @Test
    public void updateWebHook() throws Exception {
        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        WebHook webHook = service.createWebHook(USERNAME_USER, REPOSITORY_NAME, dummyWebHook);
        Assert.assertNotNull(webHook);

        Assert.assertNotNull(service.updateWebHook(USERNAME_USER, REPOSITORY_NAME, webHook));

        service.deleteWebHook(USERNAME_USER, REPOSITORY_NAME, webHook.getId());
        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);
    }

    @Test
    public void deleteWebHook() throws Exception {
        RepositoryService service = new RepositoryService(API_USER);

        Repository repository = service.createRepository(dummyCreateRepository);
        Assert.assertNotNull(repository);

        WebHook webHook = service.createWebHook(USERNAME_USER, REPOSITORY_NAME, dummyWebHook);
        Assert.assertNotNull(webHook.getId());

        service.deleteWebHook(USERNAME_USER, REPOSITORY_NAME, webHook.getId());
        service.deleteRepository(USERNAME_USER, REPOSITORY_NAME);

    }

    @Test
    @Ignore
    public void addCollaborator() throws Exception {

    }

    @Test
    @Ignore
    public void getRawFile() throws Exception {

    }

    @Test
    @Ignore
    public void getArchive() throws Exception {

    }

    @Test
    @Ignore
    public void listBranches() throws Exception {

    }

    @Test
    @Ignore
    public void getBranch() throws Exception {

    }

    @Test
    @Ignore
    public void addPublicKey() throws Exception {

    }

    @Test
    @Ignore
    public void getDeployKeys() throws Exception {

    }

    @Test
    @Ignore
    public void addDeployKey() throws Exception {

    }

    @Test
    @Ignore
    public void getDeployKey() throws Exception {

    }

    @Test
    @Ignore
    public void deleteDeployKey() throws Exception {

    }

    @Test
    @Ignore
    public void getEditorConfig() throws Exception {

    }

    @Test
    @Ignore
    public void addTeamRepository() throws Exception {

    }

    @Test
    @Ignore
    public void deleteTeamRepository() throws Exception {
        Assert.assertTrue(false);
    }
}