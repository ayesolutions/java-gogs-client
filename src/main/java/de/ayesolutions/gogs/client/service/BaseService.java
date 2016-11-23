package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;

/**
 * base gogs service class.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class BaseService {

    private GogsClient client;

    protected BaseService(GogsClient client) {
        this.client = client;
    }

    public GogsClient getClient() {
        return client;
    }
}
