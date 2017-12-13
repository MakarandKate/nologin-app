package in.pecule.nologin.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import in.pecule.nologin.R;
import in.pecule.nologin.Util.SessionManager;

public class MainActivity extends AppCompatActivity {
    Button buttonStart;
    SessionManager sm;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = new SessionManager(this);
        sm.checkLogin();
        mContext = this;
        buttonStart = (Button) findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (sm.isLoggedIn()) {
                IntentIntegrator integrator = new IntentIntegrator((Activity) mContext);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("SCANNING QR CODE");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.setCaptureActivity(QRScanActivity.class);
                integrator.initiateScan();
            }
            }
        });


        String nodeKey="", nodeAddress="", interfaceKey="", interfaceAddress="";

        Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();
        try{
            Log.e("DATA", appLinkData.toString());
            String uriDecode = Uri.decode(appLinkData.toString());
            Uri uri = Uri.parse(uriDecode);
            nodeKey = uri.getQueryParameter("nodeKey");
            Log.e("nodeKey", nodeKey);
            nodeAddress = uri.getQueryParameter("nodeAddress");
            Log.e("nodeAddress", nodeAddress);
            interfaceKey = uri.getQueryParameter("interfaceKey");
            Log.e("interfaceKey", interfaceKey);
            interfaceAddress = uri.getQueryParameter("interfaceAddress");
            Log.e("interfaceAddress", interfaceAddress);
        }catch (Exception e){
            // App Opened Normally......
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(mContext,PermissionActivity.class);
                intent.putExtra("url",result.getContents());
                startActivity(intent);
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}