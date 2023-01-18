package Server;


import java.util.HashMap;
import java.util.Map;

// Store request context
public class RequestContext {

    private String http_method;
    private String requested;
    private String http_version;
    private Map<String, String> header_values;
    private String payload;

    public RequestContext() {
        header_values = new HashMap<>();
    }

    public String getHttp_method() {
        return http_method;
    }

    public void setHttp_method(String http_method) {
        this.http_method = http_method;
    }

    public String getRequested() {
        return requested;
    }

    public void setRequested(String requested) {
        this.requested = requested;
    }

    public String getHttp_version() {
        return http_version;
    }

    public void setHttp_version(String http_version) {
        this.http_version = http_version;
    }

    public Map<String, String> getHeader_values() {
        return header_values;
    }

    public void setHeader_values(Map<String, String> header_values) {
        this.header_values = header_values;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void addHeaderValues(String key, String value) {
        header_values.put(key, value);
    }

    public int getContentLength() {
        if (header_values != null && header_values.containsKey("content-length:")) {
            return Integer.parseInt(header_values.get("content-length:"));
        }
        return 0;
    }
}
