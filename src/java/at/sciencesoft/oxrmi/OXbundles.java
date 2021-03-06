/*
 *
 *    OPEN-XCHANGE legal information
 *
 *    All intellectual property rights in the Software are protected by
 *    international copyright laws.
 *
 *
 *    In some countries OX, OX Open-Xchange, open xchange and OXtender
 *    as well as the corresponding Logos OX Open-Xchange and OX are registered
 *    trademarks of the Open-Xchange, Inc. group of companies.
 *    The use of the Logos is not covered by the GNU General Public License.
 *    Instead, you are allowed to use these Logos according to the terms and
 *    conditions of the Creative Commons License, Version 2.5, Attribution,
 *    Non-commercial, ShareAlike, and the interpretation of the term
 *    Non-commercial applicable to the aforementioned license is published
 *    on the web site http://www.open-xchange.com/EN/legal/index.html.
 *
 *    Please make sure that third-party modules and libraries are used
 *    according to their respective licenses.
 *
 *    Any modifications to this package must retain all copyright notices
 *    of the original copyright holder(s) for the original code used.
 *
 *    After any such modifications, the original and derivative code shall remain
 *    under the copyright of the copyright holder(s) and/or original author(s)per
 *    the Attribution and Assignment Agreement that can be located at
 *    http://www.open-xchange.com/EN/developer/. The contributing author shall be
 *    given Attribution for the derivative code and a license granting use.
 *
 *     Copyright (C) 2004-2006 Open-Xchange, Inc.
 *     Mail: info@open-xchange.com
 *
 *
 *     This program is free software; you can redistribute it and/or modify it
 *     under the terms of the GNU General Public License, Version 2 as published
 *     by the Free Software Foundation.
 *
 *     This program is distributed in the hope that it will be useful, but
 *     WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *     or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *     for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc., 59
 *     Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */
package at.sciencesoft.oxrmi;

import com.openexchange.control.console.AbstractConsoleHandler;
import com.openexchange.control.console.ListBundles;
import java.util.List;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

// this class orginates from the original OX source

/**
 * {@link ListBundles} - The console handler for <code>&quot;listbundles&quot;</code> command.
 *
 * @author <a href="mailto:sebastian.kauss@open-xchange.com">Sebastian Kauss</a>
 */
public final class OXbundles extends AbstractConsoleHandler {

    // protected String bundleName;
    /**
     * Initializes a new {@link ListBundles} with specified arguments and performs {@link #listBundles() list bundles}.
     *
     * @param args The command-line arguments
     */
    public OXbundles() throws Exception {
        String[] args = new String[0];
        try {
            init(args, true);
            listBundles();
        } catch (final Exception e) {
             throw e;
        } finally {
            try {
                close();
            } catch (final Exception e) {
                throw e;
            }
        }
    }

    public OXbundleInfo[] getBundleList() {
        return bundleList;
    }

    private void listBundles() throws Exception {
        final ObjectName objectName = getObjectName();
        final MBeanServerConnection mBeanServerConnection = getMBeanServerConnection();
        final List<Map<String, String>> _bundleList = (List<Map<String, String>>) mBeanServerConnection.invoke(
                objectName,
                "list",
                new Object[]{},
                new String[]{});

        if (_bundleList.size() > 0) {
            bundleList = new OXbundleInfo[_bundleList.size()];
            for (int a = 0; a < _bundleList.size(); a++) {
                final Map<String, String> data = _bundleList.get(a);
                bundleList[a] = new OXbundleInfo(data.get("bundlename"),data.get("status"));
            }
        } else {
            bundleList = null;
        }
    }

    

    @Override
    protected void showHelp() {
        // System.out.println("listbundles (-h <jmx host> -p <jmx port> -l (optional) <jmx login> -pw (optional) <jmx password>)");
    }

    @Override
    protected void exit() {
        // System.exit(1);
    }

    @Override
    protected String[] getParameter() {
        String[] DEFAULT_PARAMETER = {"-h", "-p", "-l", "-pw"};
        return DEFAULT_PARAMETER;
    }

    private OXbundleInfo[] bundleList;
}
