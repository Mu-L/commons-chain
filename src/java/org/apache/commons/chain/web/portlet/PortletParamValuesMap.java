/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//chain/src/java/org/apache/commons/chain/web/portlet/PortletParamValuesMap.java,v 1.1 2003/08/11 04:44:17 craigmcc Exp $
 * $Revision: 1.1 $
 * $Date: 2003/08/11 04:44:17 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */


package org.apache.commons.chain.web.portlet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.portlet.PortletRequest;


/**
 * <p>Private implementation of <code>Map</code> for portlet parameter
 * name-values[].</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2003/08/11 04:44:17 $
 */

final class PortletParamValuesMap implements Map {


    public PortletParamValuesMap(PortletRequest request) {
        this.request = request;
    }


    private PortletRequest request = null;


    public void clear() {
        throw new UnsupportedOperationException();
    }


    public boolean containsKey(Object key) {
        return (request.getParameter(key(key)) != null);
    }


    public boolean containsValue(Object value) {
        Iterator values = values().iterator();
        while (values.hasNext()) {
            if (value.equals(values.next())) {
                return (true);
            }
        }
        return (false);
    }


    public Set entrySet() {
        Set set = new HashSet();
        Enumeration keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            set.add(request.getParameterValues((String) keys.nextElement()));
        }
        return (set);
    }


    public boolean equals(Object o) {
        return (request.equals(o));
    }


    public Object get(Object key) {
        return (request.getParameterValues(key(key)));
    }


    public int hashCode() {
        return (request.hashCode());
    }


    public boolean isEmpty() {
        return (size() < 1);
    }


    public Set keySet() {
        Set set = new HashSet();
        Enumeration keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            set.add(keys.nextElement());
        }
        return (set);
    }


    public Object put(Object key, Object value) {
        throw new UnsupportedOperationException();
    }


    public void putAll(Map map) {
        throw new UnsupportedOperationException();
    }


    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }


    public int size() {
        int n = 0;
        Enumeration keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            keys.nextElement();
            n++;
        }
        return (n);
    }


    public Collection values() {
        List list = new ArrayList();
        Enumeration keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            list.add(request.getParameterValues((String) keys.nextElement()));
        }
        return (list);
    }


    private String key(Object key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (key instanceof String) {
            return ((String) key);
        } else {
            return (key.toString());
        }
    }


}