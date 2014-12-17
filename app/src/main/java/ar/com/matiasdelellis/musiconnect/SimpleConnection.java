package ar.com.matiasdelellis.musiconnect;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by matias on 15/12/14.
 */
public class SimpleConnection {
    private Socket   client;
    private DataOutputStream dOutput;
    private DataInputStream dInput;

    private int iPORT = 5500;

    public boolean Connect (String ipConnection)
    {
        try {
            client = new Socket(ipConnection, iPORT);

            dOutput = new DataOutputStream(client.getOutputStream());
            dInput = new DataInputStream(client.getInputStream());
        }
        catch (Exception e) {
            Log.e("Error connect()", "" + e);
            return false;
        }
        return true;
    }

    public boolean sendMessage (String message) {
        byte[] data = message.getBytes();
        try {
            //dOutput.writeInt(data.length);
            dOutput.write(data);
            dOutput.flush();
        }
        catch (Exception e) {
            Log.e("SndMenssage() ERROR -> ", "" + e);
            closeSocket();
            return false;
        }
        return true;
    }

    public String getMessage() {
        String message = null;
        try {
            // Read the data and return it
            client.setSoTimeout(3000);
            int len = dInput.readInt();
            // Check length. If it is less zero or more than 50mb
            // it's very likely we got invalid data
            if (len < 0 || len > 52428800) {
                throw new IOException("Invalid data length");
            }

            byte[] data = new byte[len];
            dInput.readFully(data, 0, len);
            message = new String(data, "UTF-8");
        }
        catch (SocketTimeoutException e) {
            Log.e("GetMessage Timeout -> ", "" + e);
        }
        catch (IOException e) {
            Log.e("GetMessage() ERROR -> ", "" + e);
        }
        return message;
    }

    protected void closeSocket () {
        try {
            dOutput.close();
            client.close();
        }
        catch (IOException e) {
            Log.e("CloseSocket() ERROR -> ", "" + e);
        }
    }

    public boolean isConnected () {
        if (client == null ||
            dOutput == null ||
            !client.isConnected() ||
            client.isClosed()) {
            return false;
        }
        else {
            return true;
        }
    }
}
