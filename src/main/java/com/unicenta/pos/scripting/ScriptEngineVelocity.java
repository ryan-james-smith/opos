//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2018 uniCenta & previous Openbravo POS works
//    https://unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.unicenta.pos.scripting;

import com.unicenta.pos.forms.AppView;
// import com.unicenta.pos.util.ScriptletUtil;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 *
 * @author adrianromero
 * Created on 5 de marzo de 2007, 19:57
 *
 * @author ryan-james-smith
 * Updated for velocity-engine-core v2.3 on 24 Apr 2023
 *
 */
class ScriptEngineVelocity implements ScriptEngine {

    private static VelocityEngine m_ve = null;

    private VelocityContext c = null;
    private AppView m_App;


    /** Creates a new instance of ScriptEngineVelocity */
    public ScriptEngineVelocity() throws ScriptException {

        if (m_ve == null) {
            // Inicializo Velocity
            m_ve = new VelocityEngine();
            // ve.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM, this);

    //        m_ve.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
    //        // m_ve.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
    //        // m_ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    //        m_ve.setProperty("class.resource.loader.class", "com.unicenta.pos.forms.SystemResourceLoader");
    //        m_ve.setProperty("class.resource.loader.description", "Velocity Resource Loader");
    //        m_ve.setProperty("class.resource.loader.appresources", this);

            m_ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogSystem");
            m_ve.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
            m_ve.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
            try {
                m_ve.init();
            } catch (Exception e) {
                throw new ScriptException("Cannot initialize Velocity Engine", e);
            }
        }
         c = new VelocityContext();

// Add JG uniCenta - see com.unicenta.pos.ScriptletUtil
//         this.put("scriptletutil", new ScriptletUtil());

    }

    @Override
    public void put(String key, Object value) {
        c.put(key, value);
    }
    @Override
    public Object get(String key) {
        return c.get(key);
    }

    @Override
    public Object eval(String src) throws ScriptException {
        if (m_ve == null) {
            throw new ScriptException("Velocity engine not initialized.");
        } else {
            Writer w = new StringWriter();
            try {
                if (m_ve.evaluate(c, w, "log", new StringReader(src))) {
                    return w.toString();
                } else {
                    throw new ScriptException("Velocity engine unexpected error.");
                }
            } catch (ParseErrorException e) {
                throw new ScriptException(e.getMessage(), e);
            } catch (MethodInvocationException e) {
                throw new ScriptException(e.getMessage(), e);
            } catch (Exception e) {
                throw new ScriptException(e.getMessage(), e);
            }
        }
    }
}
