package example;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jatyc.lib.Typestate;

@Typestate("FileClient2")
public class FileClient2 extends FileClient {
  public boolean readLine() throws Exception {
    int readByte = in.read();
    while (readByte != '\n') {
      System.out.println("[FILECLIENT2] Read byte: " + readByte);
      bytes.add(readByte);
      if (readByte == 0) return false; // Server sends EOF
      readByte = in.read();
    }

    System.out.println("[FILECLIENT2] Line read.");

    return true;
  }
  public static void main(String[] args) throws Exception {
    FileClient2 client = new FileClient2();
    if (client.start()) {
      System.out.println("[FILECLIENT2] File client started!");

      client.request("test.txt");
      while (client.readLine()) { }
      client.close();
    } else {
      System.out.println("Could not start client!");
    }
  }
}
