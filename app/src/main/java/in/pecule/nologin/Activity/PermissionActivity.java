package in.pecule.nologin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import in.pecule.nologin.BO.User;
import in.pecule.nologin.R;
import in.pecule.nologin.Util.SessionManager;

public class PermissionActivity extends AppCompatActivity {
    private Socket socket;
    {
        try{
            socket= IO.socket("https://makarandkate.herokuapp.com/");
        }catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        sm=new SessionManager(this);
        String url=getIntent().getStringExtra("url");
        User user=sm.getLogin();
        user.setUrl(url);
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.e(getClass().getCanonicalName(), "Connected to server");
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... arg0) {
                Log.e(getClass().getCanonicalName(), "Disconnected from server");
            }

        });
        socket.connect();
        socket.emit("scan",user.toJson());
        Toast.makeText(this,"Done",Toast.LENGTH_LONG);
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        //finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
