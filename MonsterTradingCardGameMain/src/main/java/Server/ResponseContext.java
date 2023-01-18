package Server;


// Store request context
public class ResponseContext {


    public String getHttp_version() {
        return http_version;
    }

    public void setHttp_version(String http_version) {
        this.http_version = http_version;
    }

    private String http_version;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    private String server;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    private String contentType;

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    private int contentLength;

    public String getPayload() {
        return payload;
    }

    private String payload;

    public ResponseContext(String status){
        http_version = "HTTP/1.1";
        this.status = status;
        server = "mtcg-server";
        contentType = "application/json";
        contentLength = 0;
        payload = "";
    }

    public void setPayload(String payload){
        this.payload = payload;
        contentLength = payload.length();
    }
}
