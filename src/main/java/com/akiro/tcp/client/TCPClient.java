package com.akiro.tcp.client;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author siva
 */
public class TCPClient {

  public static void main(String[] args) {
    vertxClient();
  }

  public static void vertxClient() {
    Vertx vertx = Vertx.vertx();
    NetClientOptions options = new NetClientOptions().setConnectTimeout(10000).
        setReconnectAttempts(10).
        setReconnectInterval(500)
        .setLogActivity(true);

    NetClient client = vertx.createNetClient(options);
    client.connect(10000, "saas.theakiro.com", res -> {
      if (res.succeeded()) {
        System.out.println("Connected!");
        NetSocket socket = res.result();
        Map<String, String> map = new HashMap<>();
        map.put("sn", "9011770000000");
        map.put("GunSN", "T090117705180001");
        map.put("GunID", "2");
        map.put("UserID", "4455450000000000");
        map.put("UserType", "0");
        map.put("Balance1", "6666");
        map.put("AuthorizeTime", "16-08-2022 18:22");
        map.put("StartTime", "16-08-2022 18:22");
        map.put("ChgMode", "2");
        map.put("ChgModePara", "99999");
        map.put("MeterStart", "75.78");
        map.put("SocStart", "53");
        map.put("ChgSuc", "0");
        map.put("EndTime", "16-08-2022 18:54");
        map.put("StopType", "1");
        map.put("StopReason", "1010");
        map.put("ChgTime", "31.25");
        map.put("SocEnd", "71");
        map.put("MeterEnd", "85.8");
        map.put("KwhTotal", "10.02");
        map.put("Kwh48Part", "0");
        map.put("CostPower", "0");
        map.put("CostService", "0");
        map.put("CostOccupy", "0");
        map.put("OccupyStartTime", "0");
        map.put("OccupyEndTime", "0");
        map.put("OccupyTime", "0");
        map.put("PayState", "0");
        map.put("PayTime", "0");
        socket.write(Json.encode(map), handler -> {
          if (handler.succeeded()) {
            System.out.println("message sent successfully");
          }
        });
      } else {
        System.out.println("Failed to connect: " + res.cause().getMessage());
      }
    });
  }

  public static void rawSocketClient() {

    String hostname = "saas.theakiro.com";
    int port = 10000;

    try (Socket socket = new Socket(hostname, port)) {
      Map<String, String> map = new HashMap<>();
      map.put("sn", "9011770000000");
      map.put("GunSN", "T090117705180001");
      map.put("GunID", "2");
      map.put("UserID", "4455450000000000");
      map.put("UserType", "0");
      map.put("Balance1", "6666");
      map.put("AuthorizeTime", "16-08-2022 18:22");
      map.put("StartTime", "16-08-2022 18:22");
      map.put("ChgMode", "2");
      map.put("ChgModePara", "99999");
      map.put("MeterStart", "75.78");
      map.put("SocStart", "53");
      map.put("ChgSuc", "0");
      map.put("EndTime", "16-08-2022 18:54");
      map.put("StopType", "1");
      map.put("StopReason", "1010");
      map.put("ChgTime", "31.25");
      map.put("SocEnd", "71");
      map.put("MeterEnd", "85.8");
      map.put("KwhTotal", "10.02");
      map.put("Kwh48Part", "0");
      map.put("CostPower", "0");
      map.put("CostService", "0");
      map.put("CostOccupy", "0");
      map.put("OccupyStartTime", "0");
      map.put("OccupyEndTime", "0");
      map.put("OccupyTime", "0");
      map.put("PayState", "0");
      map.put("PayTime", "0");
      String json = Json.encode(map);
      System.out.println("Json" + json);
      sendToServer(socket, json);
      System.out.println(receiveFromServer(socket));
      close(socket);
    } catch (UnknownHostException ex) {

      System.out.println("Server not found: " + ex.getMessage());

    } catch (IOException ex) {

      System.out.println("I/O error: " + ex.getMessage());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static void sendToServer(Socket socket, String msg) throws Exception {
    //create output stream attached to socket
    PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    //send msg to server
    outToServer.print(msg);
    outToServer.flush();
  }

  static String receiveFromServer(Socket socket) throws Exception {
    //create input stream attached to socket
    BufferedReader inFromServer = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));
    //read line from server
    String res = inFromServer.readLine(); // if connection closes on server end, this throws java.net.SocketException
    return res;
  }

  static void close(Socket socket) throws IOException {
    socket.close();
  }
}
