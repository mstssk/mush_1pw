package com.example.mush_1pw;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


/**
 * Not support restore activity after destroyed.
 */
public class Mush1Pw extends Activity {
    private static final String TAG = Mush1Pw.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Intent intent = Intent.makeMainActivity(ComponentName.createRelative("com.agilebits.onepassword", ".activity.LoginActivity"));
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (!"com.adamrocker.android.simeji.ACTION_INTERCEPT".equalsIgnoreCase(getIntent().getAction())) {
            return;
        }

        ClipboardManager clipboard = getSystemService(ClipboardManager.class);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip == null || clip.getItemCount() < 1) {
            return;
        }
        CharSequence resultText = clip.getItemAt(0).getText();
        clipboard.setPrimaryClip(ClipData.newPlainText(null, null));
        if (!TextUtils.isEmpty(resultText)) {
            final Intent intent = new Intent();
            intent.putExtra("replace_key", resultText);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
