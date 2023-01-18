package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("start server...");
        try {
            ServerSocket listener = new ServerSocket(10001, 5);
            System.out.println("Waiting for clients...");
            System.out.println();

            //Create Fixed Thread Pool
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            while (true) {
                Socket socket = listener.accept();
                //System.out.println("** New client arrived: **");
                executorService.execute(() -> {
                    // Try reader and writer
                    try ( BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                        // Request data
                        RequestContext request;
                        // Unwrap
                        Unwrapper wrapper = new Unwrapper(reader);
                        request = wrapper.unwarp();
                        // Print Request
                        if (request != null){
                            System.out.println("** Client - Start **");
                            System.out.println("** Header: **");
                            System.out.println("    " + request.getHttp_method() + " " + request.getRequested() + " " + request.getHttp_version());
                            for (Map.Entry<String, String> entry : request.getHeader_values().entrySet()) {
                                System.out.println("    " + entry.getKey() + " " + entry.getValue());
                            }
                            System.out.println("** Body: **");
                            System.out.println(request.getPayload());
                            System.out.println("-------------------------------------------");
                        }
                        // Handle response
                        ResponseHandler responder = new ResponseHandler(writer);
                        responder.response(request);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //System.out.println("** Client gone. **");
                    //System.out.println("");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}