/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//chain/src/java/org/apache/commons/chain/config/ConfigRegisterRule.java,v 1.1 2003/08/11 04:44:17 craigmcc Exp $
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

package org.apache.commons.chain.config;


import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;


/**
 * <p>Digester rule that will cause the top-most element on the Digester
 * stack (if it is a {@link Command} to be registered with the next-to-top
 * element on the Digester stack (if it is a {@link Catalog} or {@link Chain}).
 * To be registered with a {@link Catalog}, the top-most element must contain
 * a value for the specified attribute that contains the name under which
 * it should be registered.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2003/08/11 04:44:17 $
 */
class ConfigRegisterRule extends Rule {


    // ----------------------------------------------------------- Constructors


    /**
     * <p>Construct a new instance of this rule that looks for an attribute
     * with the specified name.</p>
     *
     * @param nameAttribute Name of the attribute containing the name under
     *  which this command should be registered
     */
    public ConfigRegisterRule(String nameAttribute) {
        super();
        this.nameAttribute = nameAttribute;
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * <p>The name of the attribute under which we can retrieve the name
     * this command should be registered with.</p>
     */
    private String nameAttribute = null;


    // --------------------------------------------------------- Public Methods


    /**
     * <p>Register the top {@link Command} if appropriate.</p>
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param attributes The attribute list of this element
     */
    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {

        // Is the top object a Command?
        Object top = digester.peek(0);
        if ((top == null) ||
            !(top instanceof Command)) {
            return;
        }
        Command command = (Command) top;

        // Is the next object a Catalog or a Chain?
        Object next = digester.peek(1);
        if (next == null) {
            return;
        }

        // Register the top element appropriately
        if (next instanceof Catalog) {
            Catalog catalog = (Catalog) next;
            String nameValue = attributes.getValue(nameAttribute);
            if (nameValue != null) {
                ((Catalog) next).addCommand(nameValue, command);
            }
        } else if (next instanceof Chain) {
            ((Chain) next).addCommand(command);
        }

    }


}