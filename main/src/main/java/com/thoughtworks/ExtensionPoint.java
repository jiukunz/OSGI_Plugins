package com.thoughtworks;


import com.thoughtworks.extensionpoint.TextProvider;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class ExtensionPoint implements ServiceTrackerCustomizer<TextProvider, TextProvider> {


    @Override
    public TextProvider addingService(ServiceReference<TextProvider> serviceReference) {

        BundleContext context = serviceReference.getBundle().getBundleContext();

        System.out.println("extension point: extension from " + context.getBundle().getSymbolicName());
        TextProvider service = context.getService(serviceReference);
        System.out.println("service.text:" + service.text());
        return service;
    }

    @Override
    public void modifiedService(ServiceReference<TextProvider> serviceReference, TextProvider textProvider) {

    }

    @Override
    public void removedService(ServiceReference<TextProvider> serviceReference, TextProvider textProvider) {

    }
}
