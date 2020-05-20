/*
 * #%L
 * ch.vorburger.openshift
 * %%
 * Copyright (C) 2018 - 2018 Michael Vorburger
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package ch.vorburger.openshift.s2i.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

/**
 * Simplest possible HTTP Server in Java, without dependencies to any external
 * framework.
 *
 * This is example "how-to" show case server; do NOT use this for real!
 *
 * @author Michael Vorburger.ch
 */
@SuppressWarnings("restriction")
public class Server implements AutoCloseable {

    private static final int HTTP_OK_STATUS = 200;
    private static int index=0;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("\n*********Entered Server mian()");
        new Server();
        for (int i =0; i<10; i++) {
        	index ++;
        	index = index%10000;
        	Thread.sleep(1000);
        	System.out.println("index is: "+index);
        }
    }

    private final HttpServer httpServer;

    public Server() throws IOException {
        int port = 8080;
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", exchange -> {
            String response = "Hello World, OpenJDK http server example on Openshift. Print index: "+index;
            exchange.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        httpServer.start();
        System.out.println("\n*********started 'hello, world' web server on http://localhost:" + 8080);
        for (int i=0; i<10; i++) {
        	System.out.println("Invoke index --->"+index);
        	
        }
    }

    @Override
    public void close() {
        httpServer.stop(0);
    }
}
