package in.pecule.nologin.Activity;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import in.pecule.nologin.R;

public class QRScanActivity  extends CaptureActivity {
    @Override
    protected CompoundBarcodeView initializeContent() {
        setContentView(R.layout.activity_qrscan);
        return (CompoundBarcodeView)findViewById(R.id.zxing_barcode_scanner);
    }
}