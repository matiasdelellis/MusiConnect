package ar.com.matiasdelellis.musiconnect;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends ActionBarActivity {
    private Button connectButton;
    private EditText ipInput;

    private SimpleConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        connectButton = (Button) findViewById(R.id.btnConnect);
        ipInput = (EditText) findViewById(R.id.inputIP);

        connection = new SimpleConnection();

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection.Connect(ipInput.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendPlay (View view) {
        if (!connection.sendMessage("play"))
            Toast.makeText(getApplicationContext(), "Play Failed", Toast.LENGTH_LONG).show();
    }

    public void sendStop (View view) {
        if (!connection.sendMessage("stop"))
            Toast.makeText(getApplicationContext(), "Stop Failed", Toast.LENGTH_LONG).show();
    }

    public void sendNext (View view) {
        if (!connection.sendMessage("next"))
            Toast.makeText(getApplicationContext(), "Next Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
