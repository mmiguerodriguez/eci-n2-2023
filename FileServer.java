import java.net.*;
import java.io.*;
import jatyc.lib.Typestate;

@Typestate("FileServer")
public class FileServer {
  private Socket socket;
  protected OutputStream out;
  protected BufferedReader in;
  protected String lastFilename;

  // We will hardcode the file bytes
  protected byte[] fileData = {1, 2, 3, 4, 5};
  protected int currentFileIdx = 0;

  public boolean start(Socket s) {
    try {
      socket = s;
      out = socket.getOutputStream();
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public RequestType hasRequest() throws Exception {
    String command = in.readLine();
    if (command != null) {
      if (command.equals("REQUEST") {
        return RequestType.FILE_REQUEST;
      } else if (command.equals("CLOSE"))){
        return RequestType.CLOSE;
      } else {
        return RequestType.UNK;
      }
    } else {
      return RequestType.UNK;
    }
  }

  public boolean hasFile() throws Exception {
    String fileName = in.readline();
    if(fileName != null) {
      lastFilename = fileName;
      currentFileIdx = 0;
      // Create a File object representing the file
      File file = new File(fileName);
      return file.exists();
    }
    return false;
  }

  public boolean eof() {
    return currentFileIdx == fileData.length;
  }

  public void sendZeroByte() {
    byte[] zeroByte = {0};
    out.write(zeroByte);
  }

  public void sendByte() {
    // Write the current byte and increment the counter
    out.write(fileData, currentFileIdx, 1);
    currentFileIdx ++;
  }

  // Lo llama el file server thread,.
  // Solo se puede llamar si el cliente previamente me envio el request CLOSE
  public void close() throws Exception {
    socket.close();
    in.close();
    out.close();
  }

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(1234);
    while (true) {
      new FileServerThread(serverSocket.accept()).start();
    }
  }
}
