package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.AbstractGogsTest;
import de.ayesolutions.gogs.client.model.Status;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class BuildServiceTest extends AbstractGogsTest {
    @Test
    @Ignore
    public void listStatuses() throws Exception {
        BuildService service = new BuildService(API_USER);
        Status status = new Status();
        status.setDescription("build successful");
        status.setState("success");
        status.setContext("gogs-user/default");
        status.setUrl("http://localhost/build/1");

        String sha = UUID.randomUUID().toString();
        status = service.createStatus(USERNAME_USER, "default", sha, status);
        Assert.assertEquals(sha, status.getId());
        Assert.assertEquals("build successful", status.getDescription());
        Assert.assertEquals("success", status.getState());
        Assert.assertEquals("gogs-user/default", status.getContext());
        Assert.assertEquals("http://localhost/build/1", status.getUrl());

        status = service.createStatus(USERNAME_UNKNOWN, "default", sha, status);

        List<Status> statusList = service.listStatuses(USERNAME_USER, "default");
        Assert.assertTrue(statusList.size() > 0);

        statusList = service.listStatuses(USERNAME_UNKNOWN, "default");
        Assert.assertTrue(statusList.size() == 0);
    }

    @Override
    public void createDummyObjects() {

    }
}