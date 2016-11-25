package de.ayesolutions.gogs.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * model class for web hook data.
 *
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public class WebHook {

    private Long id;

    private String type;

    private String url;

    private Map<String, String> config = new HashMap<>();

    private List<String> events = new ArrayList<>();

    private Boolean active;

    @JsonProperty("created_at")
    private Date created;

    @JsonProperty("updated_at")
    private Date updated;

    /**
     * default constructor.
     */
    public WebHook() {
    }

    /**
     * constructor with required parameters.
     *
     * @param type        webhook type (gogs or slack)
     * @param url         trigger url on event
     * @param contentType content type (json or form)
     */
    public WebHook(final String type, final String url, final String contentType) {
        this.type = type;
        this.config.put("url", url);
        this.config.put("content_type", contentType);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebHook webHook = (WebHook) o;

        if (id != null ? !id.equals(webHook.id) : webHook.id != null) return false;
        if (type != null ? !type.equals(webHook.type) : webHook.type != null) return false;
        if (url != null ? !url.equals(webHook.url) : webHook.url != null) return false;
        if (config != null ? !config.equals(webHook.config) : webHook.config != null) return false;
        if (events != null ? !events.equals(webHook.events) : webHook.events != null) return false;
        if (active != null ? !active.equals(webHook.active) : webHook.active != null) return false;
        if (created != null ? !created.equals(webHook.created) : webHook.created != null) return false;
        return updated != null ? updated.equals(webHook.updated) : webHook.updated == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (config != null ? config.hashCode() : 0);
        result = 31 * result + (events != null ? events.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WebHook{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", config=" + config +
                ", events=" + events +
                ", active=" + active +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
