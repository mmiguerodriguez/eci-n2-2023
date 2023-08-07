package example;

import java.net.*;
import java.io.*;
import jatyc.lib.Typestate;

@Typestate("FileServer")
public class FileServer {
  private Socket socket;
  protected OutputStream out;
  protected BufferedReader in;
  protected String lastFilename;
  protected String lastCommand;

  // We will hardcode the file bytes
  // File represents: abcde\nabcde
  protected byte[] fileData = {97, 98, 99, 100, 101, 10, 97, 98, 99, 100, 101};
  protected int currentFileIdx = 0;

  public boolean start(Socket s) {
    try {
      System.out.println("[FILESERVER] Starting File Server");
      socket = s;
      out = socket.getOutputStream();
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean hasRequest() throws Exception {
    String command = in.readLine();
    if (command != null) {
      lastCommand = command;
      return true;
    }
    return false;
  }

  public boolean hasClosed() {
    return lastCommand.equals("CLOSE");
  }

  public boolean hasFile() throws Exception {
    String fileName = in.readLine();

    System.out.println("[FILESERVER] Checking if file exists: " + fileName);

    if(fileName != null) {
      // Resets pointer to the beginning of the file.
      lastFilename = fileName;
      currentFileIdx = 0;

      // Checks if the file exists in the CWD.
      File file = new File("tp/src/main/resources/" + fileName);
      return file.exists();
    }
    return false;
  }

  public boolean eof() {
    return currentFileIdx == fileData.length;
  }

  public void sendZeroByte() throws IOException {
    System.out.println("[FILESERVER] Sending Zero Byte");

    byte[] zeroByte = {0};
    out.write(zeroByte);
  }

  public void sendByte() throws IOException {
    System.out.println("[FILESERVER] Sending one byte to client");

    // Write the current byte and increment the counter
    out.write(fileData, currentFileIdx, 1);
    currentFileIdx ++;
  }

  public void close() throws Exception {
    System.out.println("[FILESERVER] Closing Server Socket");
    out.close();
    in.close();
    socket.close();
  }

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(1234);
    while (true) {
      new FileServerThread(serverSocket.accept()).start();
    }
  }
}
