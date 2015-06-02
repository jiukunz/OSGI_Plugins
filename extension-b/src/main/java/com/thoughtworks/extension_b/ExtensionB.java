package com.thoughtworks.extension_b;

import com.thoughtworks.extensionpoint.TextProvider;

public class ExtensionB implements TextProvider {
    @Override
    public String text() {
        return "Extension B";
    }
}