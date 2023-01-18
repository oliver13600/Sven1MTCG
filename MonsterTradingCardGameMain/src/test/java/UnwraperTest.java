import Server.RequestContext;
import Server.Unwrapper;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UnwraperTest {

    @Test
    public void testReadHttpHeaderInvalid() throws IOException {
        // Test an invalid request with missing parts
        String request = "GET /index.html\n" +
                "User-Agent: curl/7.1\n" +
                "Accept-Language: en-US\n" +
                "Content-Type: text/html\n" +
                "Content-Length: 10\n" +
                "\n";
        BufferedReader reader = new BufferedReader(new StringReader(request));
        Unwrapper unwrapper = new Unwrapper(reader);
        RequestContext rc = unwrapper.readHttpHeader(reader);
        assertNull(rc);
    }

    @Test
    public void testReadHttpHeader() throws Exception {
        String input = "POST /path HTTP/1.1\n" +
                "Content-Length: 10\n" +
                "Accept: application/json\n" +
                "\n";
        BufferedReader reader = new BufferedReader(new StringReader(input));
        Unwrapper unwrapper = new Unwrapper(reader);
        RequestContext request = unwrapper.readHttpHeader(reader);
        assertEquals("POST", request.getHttp_method());
        assertEquals("/path", request.getRequested());
        assertEquals("HTTP/1.1", request.getHttp_version());
    }

    @Test
    void testUnwrap() {
        RequestContext request;
        BufferedReader reader = new BufferedReader(new StringReader(
                "GET /messages/cards HTTP/1.1\r\n" +
                        "Host: localhost\r\n" +
                        "Key: value\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Content-Length: 8\r\n" +
                        "\r\n" +
                        "{id:123}"));

        Map<String, String> result = new HashMap<>();
        result.put("host:","localhost");
        result.put("key:","value");
        result.put("content-type:","application/json");
        result.put("content-length:","8");

        Unwrapper wrapper = new Unwrapper(reader);
        request = wrapper.unwarp();

        assertEquals("GET", request.getHttp_method());
        assertEquals("/messages/cards", request.getRequested());
        assertEquals("HTTP/1.1", request.getHttp_version());
        assertEquals(result, request.getHeader_values());
        assertEquals("{id:123}", request.getPayload());
    }


}
