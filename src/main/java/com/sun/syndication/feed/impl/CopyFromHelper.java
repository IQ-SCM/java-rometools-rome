/*
 * Copyright 2004 Sun Microsystems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.sun.syndication.feed.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.syndication.feed.CopyFrom;

/**
 * @author Alejandro Abdelnur
 */
public class CopyFromHelper {
    private static final Object[] NO_PARAMS = new Object[0];

    private final Class<? extends CopyFrom<?>> beanInterfaceClass;
    private final Map<String, Class<?>> baseInterfaceMap; // ENTRIES(propertyName,interface.class)
    private final Map<Class<? extends CopyFrom<?>>, Class<?>> baseImplMap; // ENTRIES(interface.class,implementation.class)

    public CopyFromHelper(final Class<? extends CopyFrom<?>> beanInterfaceClass, final Map<String, Class<?>> basePropInterfaceMap, final Map<Class<? extends CopyFrom<?>>, Class<?>> basePropClassImplMap) {
        this.beanInterfaceClass = beanInterfaceClass;
        baseInterfaceMap = basePropInterfaceMap;
        baseImplMap = basePropClassImplMap;
    }

    public void copy(final Object target, final Object source) {
        try {
            final PropertyDescriptor[] pds = BeanIntrospector.getPropertyDescriptors(beanInterfaceClass);
            if (pds != null) {
                for (final PropertyDescriptor pd : pds) {
                    final String propertyName = pd.getName();
                    final Method pReadMethod = pd.getReadMethod();
                    final Method pWriteMethod = pd.getWriteMethod();
                    if (pReadMethod != null && pWriteMethod != null && // ensure
                                                                       // it has
                                                                       // getter
                                                                       // and
                                                                       // setter
                                                                       // methods
                            pReadMethod.getDeclaringClass() != Object.class && // filter
                                                                               // Object.class
                                                                               // getter
                                                                               // methods
                            pReadMethod.getParameterTypes().length == 0 && // filter
                                                                           // getter
                                                                           // methods
                                                                           // that
                                                                           // take
                                                                           // parameters
                            baseInterfaceMap.containsKey(propertyName)) { // only
                        // copies
                        // properties
                        // defined
                        // as
                        // copyFrom-able
                        Object value = pReadMethod.invoke(source, NO_PARAMS);
                        if (value != null) {
                            final Class<?> baseInterface = baseInterfaceMap.get(propertyName);
                            value = doCopy(value, baseInterface);
                            pWriteMethod.invoke(target, new Object[] { value });
                        }
                    }
                }
            }
        } catch (final Exception ex) {
            throw new RuntimeException("Could not do a copyFrom " + ex, ex);
        }
    }

    private CopyFrom<?> createInstance(final Class<? extends CopyFrom<?>> interfaceClass) throws Exception {
        if (baseImplMap.get(interfaceClass) == null) {
            return null;
        } else {
            return (CopyFrom<?>) baseImplMap.get(interfaceClass).newInstance();
        }
    }

    @SuppressWarnings("unchecked")
    private Object doCopy(Object value, final Class<?> baseInterface) throws Exception {
        if (value != null) {
            final Class<?> vClass = value.getClass();
            if (vClass.isArray()) {
                value = doCopyArray(value, baseInterface);
            } else if (value instanceof Collection) {
                value = doCopyCollection((Collection<?>) value, baseInterface);
            } else if (value instanceof Map) {
                value = doCopyMap((Map<Object, Object>) value, baseInterface);
            } else if (isBasicType(vClass)) {
                // value = value; // nothing to do here
                if (value instanceof Date) { // because Date it is not inmutable
                    value = ((Date) value).clone();
                }
            } else { // it goes CopyFrom
                if (value instanceof CopyFrom<?>) {
                    final CopyFrom<?> source = (CopyFrom<?>) value;
                    CopyFrom<?> target = createInstance(source.getInterface());
                    target = target == null ? (CopyFrom<?>) value.getClass().newInstance() : target;
                    target.copyFrom(source);
                    value = target;
                } else {
                    throw new Exception("unsupported class for 'copyFrom' " + value.getClass());
                }
            }
        }
        return value;
    }

    private Object doCopyArray(final Object array, final Class<?> baseInterface) throws Exception {
        final Class<?> elementClass = array.getClass().getComponentType();
        final int length = Array.getLength(array);
        final Object newArray = Array.newInstance(elementClass, length);
        for (int i = 0; i < length; i++) {
            final Object element = doCopy(Array.get(array, i), baseInterface);
            Array.set(newArray, i, element);
        }
        return newArray;
    }

    private Collection<Object> doCopyCollection(final Collection<?> collection, final Class<?> baseInterface) throws Exception {
        // expecting SETs or LISTs only, going default implementation of them
        final Collection<Object> newColl = collection instanceof Set ? new HashSet<Object>() : new ArrayList<Object>();
        final Iterator<?> i = collection.iterator();
        while (i.hasNext()) {
            newColl.add(doCopy(i.next(), baseInterface));
        }
        return newColl;
    }

    private Map<Object, Object> doCopyMap(final Map<Object, Object> map, final Class<?> baseInterface) throws Exception {
        final Map<Object, Object> newMap = new HashMap<Object, Object>();
        final Iterator<Entry<Object, Object>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            final Map.Entry<Object, Object> entry = entries.next();
            newMap.put(entry.getKey(), doCopy(entry.getValue(), baseInterface));
        }
        return newMap;
    }

    private static final Set<Class<?>> BASIC_TYPES = new HashSet<Class<?>>();

    static {
        BASIC_TYPES.add(Boolean.class);
        BASIC_TYPES.add(Byte.class);
        BASIC_TYPES.add(Character.class);
        BASIC_TYPES.add(Double.class);
        BASIC_TYPES.add(Float.class);
        BASIC_TYPES.add(Integer.class);
        BASIC_TYPES.add(Long.class);
        BASIC_TYPES.add(Short.class);
        BASIC_TYPES.add(String.class);
        BASIC_TYPES.add(Date.class);
    }

    private boolean isBasicType(final Class<?> vClass) {
        return BASIC_TYPES.contains(vClass);
    }

}
