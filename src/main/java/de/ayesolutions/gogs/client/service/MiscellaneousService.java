package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.Markdown;

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
     *
     * @param markdown markdown definition.
     * @return html rendered markdown.
     * @throws GogsClientException
     */
    public String renderMarkdown(final Markdown markdown) throws GogsClientException {
        return getClient().post(String.class, markdown, "markdown");
    }

    /**
     * render markdown.
     *
     * GET /api/v1/markdown/raw
     *
     * @param data text markdown.
     * @return html rendered markdown.
     * @throws GogsClientException
     */
    public String renderMarkdownRaw(final String data) throws GogsClientException {
        return getClient().post(String.class, data, "markdown", "raw");
    }
}
