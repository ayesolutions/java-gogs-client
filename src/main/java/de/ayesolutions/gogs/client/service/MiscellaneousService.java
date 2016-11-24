package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.model.Markdown;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * miscellaneous server functions.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public final class MiscellaneousService extends BaseService {

    /**
     * default constructor.
     *
     * @param client gogs client.
     */
    public MiscellaneousService(final GogsClient client) {
        super(client);
    }

    /**
     * render markdown.
     *
     * GET /api/v1/markdown
     * Response 200, 500
     *
     * @param markdown markdown definition.
     * @return html rendered markdown.
     */
    public String renderMarkdown(final Markdown markdown) {
        Entity<Markdown> entity = Entity.json(markdown);
        Response response = getClient().getWebTarget()
                .path("markdown")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(entity);

        return handleResponse(response, String.class, 200);
    }

    /**
     * render markdown.
     *
     * GET /api/v1/markdown/raw
     * Response 200, 500
     *
     * @param data text markdown.
     * @return html rendered markdown.
     */
    public String renderMarkdownRaw(final String data) {
        Entity<String> entity = Entity.text(data);
        Response response = getClient().getWebTarget()
                .path("markdown")
                .path("raw")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .post(entity);

        return handleResponse(response, String.class, 200);
    }
}
