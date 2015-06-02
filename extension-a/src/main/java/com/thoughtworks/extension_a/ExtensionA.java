package com.thoughtworks.extension_a;

import com.thoughtworks.extensionpoint.TextProvider;

public class ExtensionA implements TextProvider {
    @Override
    public String text() {
        return "Extension A";
    }
}
