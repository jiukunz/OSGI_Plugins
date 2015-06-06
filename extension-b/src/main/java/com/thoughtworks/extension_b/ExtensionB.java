package com.thoughtworks.extension_b;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.thoughtworks.extensionpoint.TextProvider;

public class ExtensionB implements TextProvider {
    @Override
    public View text(Context context) {
        Button button = new Button(context);
        button.setId(10000);
        button.setText("Extension B");

        Log.d("extension point", "add button " + "Extension B");
        return button;
    }
}
