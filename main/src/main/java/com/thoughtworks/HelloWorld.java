package com.thoughtworks;

import com.google.common.base.Joiner;
import com.thoughtworks.extensionpoint.TextProvider;
import org.osgi.framework.*;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.util.tracker.ServiceTracker;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HelloWorld {
    private static Framework framework;

    public static void main(String[] args) throws Exception {
        System.out.println("staring hello world");
        launchFramework();
        initExtensionPoint();
        doTests(framework.getBundleContext());
    }

    private static void doTests(BundleContext context) throws Exception {
        String[] bundles = {
                "file:extension-a/build/libs/extension-a-1.0.0.jar",
                "file:extension-b/build/libs/extension-b-1.0.0.jar" };

        for (String bundleName: bundles) {
            try {
                Bundle bundle = context.installBundle(bundleName);
                if (bundle != null) {
                    bundle.start();
                } else {
                    System.err.println("Unable to install bundle " + bundleName);
                }
            } catch (BundleException e) {
                System.err.println("Unable to load bundle " + bundleName);
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("List of bundles:");
        for (Bundle bundle: context.getBundles()) {
            System.out.println("  " + bundle.getSymbolicName() + " " + bundle.getVersion() + ": " + stateToString(bundle.getState()));
            if (bundle.getRegisteredServices()!=null) {
                System.out.println("   Services:");
                for (ServiceReference s : bundle.getRegisteredServices()) {
                    System.out.println("    " + Joiner.on(',').join((String[])s.getProperty("objectClass")));
                }
            }
        }

        System.out.println();
        System.out.println("Calling services:");
        Collection<ServiceReference<TextProvider>> refs = context.getServiceReferences(TextProvider.class, null);
        for (ServiceReference<TextProvider> ref: refs) {
            TextProvider textProvider = context.getService(ref);
//            System.out.println("  " + textProvider.text());
        }
    }

    private static String stateToString(int state) {
        switch (state) {
            case Bundle.UNINSTALLED: return "uninstalled";
            case Bundle.INSTALLED: return "installed";
            case Bundle.RESOLVED: return "resolved";
            case Bundle.STARTING: return "starting";
            case Bundle.STOPPING: return "stopping";
            case Bundle.ACTIVE: return "active";
            default: return "unknown";
        }
    }

    private static void initExtensionPoint() {
        ServiceTracker<TextProvider, TextProvider> extensions
                = new ServiceTracker<>(framework.getBundleContext(), TextProvider.class, new ExtensionPoint());
        extensions.open(true);
    }

    private static void launchFramework() {
        ServiceLoader<FrameworkFactory> loader = ServiceLoader.load(FrameworkFactory.class);
        Iterator<FrameworkFactory> iterator = loader.iterator();
        if (iterator.hasNext()) {
            framework = iterator.next().newFramework(getFrameworkConfig());
            try {
                framework.start();
            } catch (BundleException e) {
            }
        }
    }

    private static Map<String, String> getFrameworkConfig() {
        HashMap<String, String> config = new HashMap<>();
        config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA, "com.thoughtworks.extensionpoint");
        try {
            config.put(Constants.FRAMEWORK_STORAGE, File.createTempFile("osgi", "launcher").getParent());
        } catch (IOException e) {
            System.out.println("creating storage error");
        }
        return config;
    }
}

