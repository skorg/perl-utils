package org.scriptkitty.perl.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.transform.stream.StreamSource;


abstract class StaticContentProvider<T>
{
    //~ Static fields/initializers

    private static final boolean logErrors = Boolean.parseBoolean(
        System.getProperty("org.scriptkitty.perl.internal.logUnmarshallErrors", Boolean.FALSE.toString()));

    private static final Logger logger = LoggerFactory.getLogger(StaticContentProvider.class);
    
    //~ Instance fields

    private Map<String, T> contentMap;

    //~ Methods

    StaticContentProvider(String baseName) throws InstantiationException
    {
        Class<T> contentClass = getContentClass(); 
        
        try(InputStreamReader reader = new InputStreamReader(contentClass.getResourceAsStream(baseName + ".xml")))
        {
            Collection<T> collection = unmarshal(contentClass, reader);
            this.contentMap = new HashMap<>(collection.size());

            for (T object : collection)
            {
                String key = getKey(object);
                contentMap.put(key, object);
            }
        }
        catch (IOException e)
        {
            throw new InstantiationError(e.getMessage());
        }
    }

    final T get(String key)
    {
        return contentMap.get(key);
    }

    final Set<String> getKeys()
    {
        return contentMap.keySet();
    }

    protected abstract Class<T> getContentClass();

    protected abstract String getKey(T object);

    protected final <O> Collection<O> unmarshal(Class<O> clazz, Reader reader) throws InstantiationException
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(new Class[] { Wrapper.class, clazz });
            Unmarshaller unmarshaller = context.createUnmarshaller();

            if (logErrors)
            {
                unmarshaller.setEventHandler(new ValidationEventHandler()
                    {
                        @Override public boolean handleEvent(ValidationEvent event)
                        {
                            String message = event.getMessage();
                            Throwable cause = event.getLinkedException();

                            if (cause != null)
                            {
                                logger.warn(cause.toString());
                            }
                            else if (message != null)
                            {
                                logger.warn(message);
                            }

                            return true;
                        }
                    });
            }

            return unmarshaller.unmarshal(new StreamSource(reader), Wrapper.class).getValue().cast(clazz);
        }
        catch (JAXBException e)
        {
            logger.error("an unexpected unmarshalling error occurred", e);
            throw new InstantiationException(e.getMessage());
        }
    }

    //~ Inner Classes

    private static class Wrapper
    {
        @XmlAnyElement(lax = true)
        private List<?> wrapped;

        public <O> Collection<O> cast(Class<O> clazz)
        {
            Collection<O> collection = new ArrayList<>(wrapped.size());
            for (Object obj : wrapped)
            {
                collection.add(clazz.cast(obj));
            }

            return collection;
        }
    }
}
