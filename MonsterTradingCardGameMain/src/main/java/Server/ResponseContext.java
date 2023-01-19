package Server;


// Store request context
public class ResponseContext {


    private String http_version;
    private String status;
    private String server;
    private String contentType;
    private int contentLength;
    private String payload;

    public ResponseContext(String status) {
        http_version = "HTTP/1.1";
        this.status = status;
        server = "mtcg-server";
        contentType = "application/json";
        contentLength = 0;
        payload = "";
    }

    public String getHttp_version() {
        return http_version;
    }

    public void setHttp_version(String http_version) {
        this.http_version = http_version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
        contentLength = payload.length();
    }
}
