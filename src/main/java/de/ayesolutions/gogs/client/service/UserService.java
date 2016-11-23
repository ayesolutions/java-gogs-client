package de.ayesolutions.gogs.client.service;

import de.ayesolutions.gogs.client.GogsClient;
import de.ayesolutions.gogs.client.GogsClientException;
import de.ayesolutions.gogs.client.model.UserSearchResult;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class UserService extends BaseService {

    public UserService(final GogsClient client) {
        super(client);
    }

    public UserSearchResult search(String query) {
        return search(query, 0);
    }

    public UserSearchResult search(String query, int limit) {
        WebTarget webTarget = getClient().getWebTarget()
                .path("users")
                .path("search");

        if (limit > 0) {
            webTarget.queryParam("limit", limit);
        }

        Invocation.Builder builder = webTarget.queryParam("q", query).request();

        if (getClient().getAccessToken() != null) {
            builder.header("Authorization", getClient().getAccessToken().getBasicAuthorization());
        }

        Response response = builder.get();

        if (response.getStatus() != 200) {
            throw new GogsClientException(GogsClientException.createMessage(response));
        }

        return response.readEntity(UserSearchResult.class);
    }
}
