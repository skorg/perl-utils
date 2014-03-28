package org.scriptkitty.perl.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResourceBundleFactory extends java.util.ResourceBundle.Control
{
    //~ Static fields/initializers

    private static String BUNDLED_STATIC = "org.scriptkitty.perl.internal.StaticContentManager";

    private static Logger logger = LoggerFactory.getLogger(ResourceBundleFactory.class);

    private static ResourceBundleFactory self = new ResourceBundleFactory();

    //~ Constructors

    private ResourceBundleFactory()
    {
    }

    //~ Methods

    public static java.util.ResourceBundle getBundle(String bundle)
    {
        return java.util.ResourceBundle.getBundle(bundle, self);
    }

    @Override public List<String> getFormats(String baseName)
    {
        return Arrays.asList("xml");
    }

    @Override public java.util.ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
        throws InstantiationException
    {
        if ("xml".equals(format))
        {
            return createBundleDelegate(baseName, loader);
        }

        return null;
    }

    private StaticBundleDelegate createBundleDelegate(String baseName, ClassLoader loader) throws InstantiationException
    {
        return new StaticBundleDelegate(getContentProvider(baseName, loader, BUNDLED_STATIC));

//      IContentProvider provider = getContentProvider(loader, buildClassName(INTERPRETER_PROVIDER));
//      if (provider == null)
//      {
//          provider = getContentProvider(loader, buildClassName(STATIC_PROVIDER));
//      }
//
//      return new BundleDelegate(provider);
    }

    private StaticContentProvider<?> getContentProvider(String baseName, ClassLoader loader, String className) throws InstantiationException
    {
        try
        {
            Class<IContentManager> clazz = (Class<IContentManager>) loader.loadClass(className);
            IContentManager manager = clazz.newInstance();

            return manager.getContentProvider(baseName);
        }
        catch (InstantiationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            logger.trace("failed to loadClass: [{}] - {}", className, e);
            throw new InstantiationException(e.getMessage());
        }

//        return null;
    }

    //~ Inner Classes

    private class StaticBundleDelegate extends java.util.ResourceBundle
    {
        private StaticContentProvider<?> provider;

        public StaticBundleDelegate(StaticContentProvider<?> provider)
        {
            this.provider = provider;
        }

        @Override public Enumeration<String> getKeys()
        {
            return Collections.enumeration(provider.getKeys());
        }

        @Override protected Object handleGetObject(String key)
        {
            return provider.get(key);
        }
    }
}
