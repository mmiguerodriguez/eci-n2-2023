package example;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jatyc.lib.Typestate;

@Typestate("FileClient")
public class FileClient {
  private Socket socket;
  protected OutputStream out;
  protected BufferedReader in;
  protected List<Integer> bytes;

  public boolean start() {
    try {
      socket = new Socket("localhost", 1234);
      out = socket.getOutputStream();
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public void request(String filename) throws Exception {
    System.out.println("[FILECLIENT] Making a request with filename: " + filename);
    bytes = new ArrayList<>();

    out.write("REQUEST\n".getBytes());
    String command = filename + "\n";
    out.write(command.getBytes());
  }

  public boolean readByte() throws IOException {
    int byteRead = in.read();
    System.out.println("[FILECLIENT] Read byte: " + byteRead);

    if (byteRead == 0) return false;

    bytes.add(byteRead);
    return true;
  }

  public void close() throws Exception {
    System.out.println("[FILECLIENT] Closing client socket");

    out.write("CLOSE\n".getBytes());
    out.close();
    in.close();
    socket.close();
  }

  public static void main(String[] args) throws Exception {
    FileClient client = new FileClient();
    if (client.start()) {
      System.out.println("[FILECLIENT] File client started!");

      client.request("test.txt");
      while (client.readByte()) { }
      client.close();
    } else {
      System.out.println("Could not start client!");
    }
  }
}
