package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.AbstractGogsTest;
import de.ayesolutions.gogs.client.model.Markdown;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class MiscellaneousServiceTest extends AbstractGogsTest {

    private MiscellaneousService service = new MiscellaneousService(API_USER);
    ;

    private Markdown dummyMarkdown;

    @Override
    public void createDummyObjects() {
        dummyMarkdown = new Markdown();
        dummyMarkdown.setContext("ayesolutions/java-ci-example");
        dummyMarkdown.setMode("html");
        dummyMarkdown.setText("# test");
    }

    @Test
    public void renderMarkdown() throws Exception {
        String content = service.renderMarkdown(dummyMarkdown);

        Assert.assertNotNull(content);
        Assert.assertTrue(content.startsWith("<h1>test</h1>"));
    }

    @Test
    public void renderMarkdownRaw() throws Exception {
        String content = service.renderMarkdownRaw("# test");

        Assert.assertNotNull(content);
        Assert.assertTrue(content.startsWith("<h1>test</h1>"));
    }
}