package de.ayesolutions.gogs.client;

import javax.ws.rs.core.Response;

/**
 * default gogs service exception.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class GogsClientException extends RuntimeException {

    /**
     * default constructor.
     *
     * @param message error message.
     */
    public GogsClientException(String message) {
        super(message);
    }

    /**
     * create simple message from response.
     *
     * @param response response object.
     * @return localized string.
     */
    public static String createMessage(Response response) {
        // TODO: localization
        return "gogs service call " + response.getLocation() + " returns " + response.getStatus();
    }
}
