package com.thoughtworks.extension_b;

import com.thoughtworks.extensionpoint.TextProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Hashtable;

public class Activitor implements BundleActivator{
    private ServiceRegistration<TextProvider> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        Hashtable<String, Object> config = new Hashtable<String, Object>();
        registration = context.registerService(TextProvider.class, new ExtensionB(), config);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
    }
}
