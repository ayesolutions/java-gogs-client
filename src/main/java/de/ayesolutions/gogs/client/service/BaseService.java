package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.GogsClientException;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

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

    public void handleResponse(Response response, int statusCode) {
        if (response.getStatus() != statusCode) {
            String message = response.readEntity(String.class);

            throw new GogsClientException("gogs error " + response.getStatus() + " " + message);
        }
    }

    public <T> T handleResponse(Response response, GenericType<T> clazz, int statusCode) {
        handleResponse(response, statusCode);
        return response.readEntity(clazz);
    }

    public <T> T handleResponse(Response response, Class<T> clazz, int statusCode) {
        handleResponse(response, statusCode);
        return response.readEntity(clazz);
    }
}
