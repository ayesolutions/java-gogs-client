package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.AbstractGogsTest;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.Markdown;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
@RunWith(JUnit4.class)
public class MiscellaneousServiceTest extends AbstractGogsTest {

    @Test
    public void renderMarkdown() throws Exception {
        Markdown markdown = new Markdown();
        markdown.setContext("ayesolutions/java-ci-example");
        markdown.setMode("html");
        markdown.setText("# test");

        MiscellaneousService service = new MiscellaneousService(API_ADMIN);
        String content = service.renderMarkdown(markdown);

        Assert.assertNotNull(content);
        Assert.assertTrue(content.startsWith("<h1>test</h1>"));
    }

    @Test(expected = GogsClientException.class)
    public void renderMarkdownInvalid() throws Exception {
        Markdown markdown = new Markdown();
        markdown.setContext("ayesolutions/java-ci-example");
        markdown.setMode("html");
        markdown.setText("# test");

        MiscellaneousService service = new MiscellaneousService(API_INVALID);
        service.renderMarkdown(markdown);
    }

    @Test
    public void renderMarkdownRaw() throws Exception {
        MiscellaneousService service = new MiscellaneousService(API_ADMIN);
        String content = service.renderMarkdownRaw("# test");

        Assert.assertNotNull(content);
        Assert.assertTrue(content.startsWith("<h1>test</h1>"));
    }

    @Test(expected = GogsClientException.class)
    public void renderMarkdownRawInvalid() throws Exception {
        MiscellaneousService service = new MiscellaneousService(API_INVALID);
        service.renderMarkdownRaw("# test");
    }
}