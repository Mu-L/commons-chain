/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//chain/apps/mailreader/src/java/org/apache/commons/chain/mailreader/commands/Attic/LocaleCommand.java,v 1.1 2004/03/27 03:58:02 husted Exp $
 * $Revision: 1.1 $
 * $Date: 2004/03/27 03:58:02 $
 *
 * Copyright 2000-2004 Apache Software Foundation
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
 */
package org.apache.commons.chain.mailreader.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import java.util.Locale;

/**
 * Change Locale according to input values, country and language.
 */
public class LocaleCommand implements Command {

    static boolean isBlank(String string) {
        return ((string==null) || (string.trim().length()==0));
    }

    public boolean execute(Context context) {

        MailReader app = (MailReader) context;
        Context input = app.getInput();
        String country = (String) input.get(MailReader.PN_COUNTRY);
        String language = (String) input.get(MailReader.PN_LANGUAGE);

        Locale locale = Locale.getDefault();
        if ((!isBlank(language)) && (!isBlank(country))) {
            locale = new Locale(language, country);
        }
        else if (!isBlank(language)) {
            locale = new Locale(language, "");
        }
        app.setLocale(locale);

        return false;
    }
}