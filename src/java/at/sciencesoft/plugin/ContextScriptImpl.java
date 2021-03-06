/*
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
package at.sciencesoft.plugin;

import at.sciencesoft.oxrmi.OXcontext;
import at.sciencesoft.oxrmi.Param;
import at.sciencesoft.oxrmi.ParamMap;
import at.sciencesoft.util.ExecuteScript;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author <a href="mailto:peter.sauer@sciencesoft.at">Peter Sauer</a>
 */
public class ContextScriptImpl implements ContextIface {

    public ContextScriptImpl(HashMap<Plugin.ACTION, Script> actionMap, String errorPath, String msgPath) {
        this.actionMap = actionMap;
        this.errorPath = errorPath;
        this.msgPath = msgPath;
    }

    public ResultSet create(boolean isPost,boolean isSuccess, int contextID, String contextName, String[] mapping, long quota, String accessCombination, String loginName, String displayName,
            String firstName, String lastName, String pwd, String email, String lang, String timeZone, ParamMap contextInfos,ResultSet rs,OXcontext context) {
        Script script = actionMap.get(Plugin.ACTION.CREATE);
        boolean call = false;
        if (script != null) {
            if (!isPost) {
                if ((script.getCallLevel() == Script.CALL.BEFORE || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            } else {
                if ((script.getCallLevel() == Script.CALL.AFTER || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            }
        }

        if (call) {
            ArrayList<String> al = new ArrayList<String>();
            al.add(script.getScript());
            al.add(Plugin.PLUGIN.CONTEXT.name());
            al.add(Plugin.ACTION.CREATE.name());
            al.add("" + (isPost ? 1 : 0));
            al.add("" + (isSuccess ? 1 : 0));
            al.add("" + contextID);
            al.add("\"" + contextName + "\"");
            StringBuffer buf = new StringBuffer();
            if (mapping != null) {
                for (int i = 0; i < mapping.length; ++i) {
                    if (i != 0) {
                        buf.append(',');
                    }
                    buf.append(mapping[i]);
                }
            }
            al.add("\"" + buf.toString() + "\"");
            al.add("" + quota);
            al.add("" + accessCombination);
            al.add("\"" + loginName + "\"");
            al.add("\"" + displayName + "\"");
            al.add("\"" + firstName + "\"");
            al.add("\"" + lastName + "\"");
            al.add("\"" + pwd + "\"");
            al.add("\"" + email + "\"");
            al.add("\"" + lang + "\"");
            al.add("\"" + timeZone + "\"");
            return ExecuteScript.call(al.toArray(new String[0]), errorPath, msgPath);
        }
        return new ResultSet(true);
    }

    public ResultSet change(boolean isPost,boolean isSuccess, int contextID,ParamMap changes,ParamMap contextInfos, boolean downgrade, ResultSet rs) {
        if(changes == null) {
            return new ResultSet(true);
        }
        Script script = actionMap.get(Plugin.ACTION.CHANGE);
        boolean call = false;
        if (script != null) {
            if (!isPost) {
                if ((script.getCallLevel() == Script.CALL.BEFORE || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            } else {
                if ((script.getCallLevel() == Script.CALL.AFTER || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            }
        }

        if (call) {
            ArrayList<String> al = new ArrayList<String>();
            al.add(script.getScript());
            al.add(Plugin.PLUGIN.CONTEXT.name());
            al.add(Plugin.ACTION.CHANGE.name());
            al.add("" + (isPost ? 1 : 0));
            al.add("" + (isSuccess ? 1 : 0));
            al.add("" + contextID);
            Iterator<Param> iter = changes.getIterator();
            while(iter.hasNext()) {
                Param p = iter.next();
                al.add(p.name());
                al.add("\"" + changes.toString(p) + "\"");
            }
           
            return ExecuteScript.call(al.toArray(new String[0]), errorPath, msgPath);
        }
        return new ResultSet(true);
    }

    public ResultSet delete(boolean isPost,boolean isSuccess, int contextID, ResultSet rs) {
        Script script = actionMap.get(Plugin.ACTION.DELETE);
        boolean call = false;
        if (script != null) {
            if (!isPost) {
                if ((script.getCallLevel() == Script.CALL.BEFORE || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            } else {
                if ((script.getCallLevel() == Script.CALL.AFTER || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            }
        }

        if (call) {
            ArrayList<String> al = new ArrayList<String>();
            al.add(script.getScript());
            al.add(Plugin.PLUGIN.CONTEXT.name());
            al.add(Plugin.ACTION.DELETE.name());
            al.add("" + (isPost ? 1 : 0));
            al.add("" + (isSuccess ? 1 : 0));
            al.add("" + contextID);
            return ExecuteScript.call(al.toArray(new String[0]), errorPath, msgPath);
        }
        return new ResultSet(true);
    }

    public ResultSet enable(boolean isPost,boolean isSuccess, int contextID, ResultSet rs) {
        Script script = actionMap.get(Plugin.ACTION.ENABLE);
        boolean call = false;
        if (script != null) {
            if (!isPost) {
                if ((script.getCallLevel() == Script.CALL.BEFORE || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            } else {
                if ((script.getCallLevel() == Script.CALL.AFTER || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            }
        }
        if (call) {
            ArrayList<String> al = new ArrayList<String>();
            al.add(script.getScript());
            al.add(Plugin.PLUGIN.CONTEXT.name());
            al.add(Plugin.ACTION.ENABLE.name());
            al.add("" + (isPost ? 1 : 0));
            al.add("" + (isSuccess ? 1 : 0));
            al.add("" + contextID);
            return ExecuteScript.call(al.toArray(new String[0]), errorPath, msgPath);
        }
        return new ResultSet(true);
    }

    public ResultSet disable(boolean isPost,boolean isSuccess, int contextID, ResultSet rs) {
        Script script = actionMap.get(Plugin.ACTION.DISABLE);
        boolean call = false;
        if (script != null) {
            if (!isPost) {
                if ((script.getCallLevel() == Script.CALL.BEFORE || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            } else {
                if ((script.getCallLevel() == Script.CALL.AFTER || script.getCallLevel() == Script.CALL.BOTH)) {
                    call = true;
                }
            }
        }

        if (call) {
            ArrayList<String> al = new ArrayList<String>();
            al.add(script.getScript());
            al.add(Plugin.PLUGIN.CONTEXT.name());
            al.add(Plugin.ACTION.DISABLE.name());
            al.add("" + (isPost ? 1 : 0));
            al.add("" + (isSuccess ? 1 : 0));
            al.add("" + contextID);
            return ExecuteScript.call(al.toArray(new String[0]), errorPath, msgPath);
        }
        return new ResultSet(true);
    }

    public boolean checkGUIflag(FIELD field) {
        return false;
    }

    public String getVersion() {
         return "0.1";
    }

    private HashMap<Plugin.ACTION, Script> actionMap;
    private String errorPath;
    private String msgPath;
}
