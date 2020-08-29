package org.neonsis.picshare.common.converter.impl;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.neonsis.picshare.common.annotation.converter.ConvertAsURL;
import org.neonsis.picshare.common.converter.ModelConverter;
import org.neonsis.picshare.common.converter.UrlConverter;
import org.neonsis.picshare.exception.ConfigException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class DefaultModelConverter implements ModelConverter {

    @Inject
    private UrlConverter urlConverter;

    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        try {
            D result = destinationClass.newInstance();
            copyProperties(result, Objects.requireNonNull(source, "Source can't be null"));
            return result;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException ex) {
            throw new ConfigException(String.format("Can't convert object from %s to %s: %s", source.getClass(), destinationClass, ex.getMessage()), ex);
        }
    }

    @Override
    public <S, D> List<D> convertList(List<S> source, Class<D> destinationClass) {
        List<D> result = new ArrayList<>(source.size());
        for (S item : source) {
            result.add(convert(item, destinationClass));
        }
        return result;
    }

    private <S, D> void copyProperties(D result, S source) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PropertyUtilsBean propertyUtils = BeanUtilsBean.getInstance().getPropertyUtils();
        PropertyDescriptor[] propertyDescriptors = propertyUtils.getPropertyDescriptors(result);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String name = propertyDescriptor.getName();
            if (!"class".equals(name) && propertyUtils.isReadable(source, name) && propertyUtils.isWriteable(result, name)) {
                Object value = propertyUtils.getProperty(source, name);
                if (value != null) {
                    Object convertedValue = convertValue(propertyUtils, result, name, value);
                    propertyUtils.setProperty(result, name, convertedValue);
                }
            }
        }
    }

    private <D> Object convertValue(PropertyUtilsBean propertyUtils, D result, String name, Object value)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> destinationClass = propertyUtils.getPropertyType(result, name);
        if (destinationClass.isPrimitive() || value.getClass().isPrimitive()) {
            return value;
        } else if (isConvertAsUrlPresent(result, name)) {
            return urlConverter.convert(String.valueOf(value));
        } else if (value.getClass() != destinationClass) {
            return convert(value, destinationClass);
        }
        return value;
    }

    private <D> boolean isConvertAsUrlPresent(D result, String name) {
        Field field = null;
        Class<?> cl = result.getClass();
        while(cl != null) {
            try {
                field = cl.getDeclaredField(name);
            } catch (NoSuchFieldException ex) {
                // do nothing
            }
            cl = cl.getSuperclass();
        }
        return field != null && field.isAnnotationPresent(ConvertAsURL.class);
    }

}
