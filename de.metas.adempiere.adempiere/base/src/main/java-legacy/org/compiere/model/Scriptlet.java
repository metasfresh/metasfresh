/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.compiere.util.Env;
import org.slf4j.Logger;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.ParseException;
import bsh.Parser;
import de.metas.logging.LogManager;
import de.metas.script.ScriptExecutor;

/**
 *  Script Model
 *
 *  @author     Jorg Janke
 *  @version    $Id: Scriptlet.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class Scriptlet
{
	/**
	 *  Run Script
	 *  @param variable
	 *  @param script
	 *  @param ctx
	 *  @param WindowNo Included Window variables
	 *  @return result
	 */
	static Object run (String variable, String script, Properties ctx, int WindowNo)
	{
		Scriptlet scr = new Scriptlet (variable, script, ctx, WindowNo);
		scr.execute();
		return scr.getResult(false);
	}   //  run

	private int m_windowNo;

	/**
	 *  Constructor
	 */
	public Scriptlet()
	{
		this(VARIABLE, "", Env.getCtx(), 0);
	}   //  Scriptlet

	/** Default Result Variable Name    */
	public static final String      VARIABLE = "result";

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(Scriptlet.class);

	/**
	 *  Full Constructor
	 *
	 *  @param variable Variable Name
	 *  @param script The Script
	 *  @param prop Environment
	 *  @param WindowNo Included Window variables
	 */
	public Scriptlet (String variable, String script, Properties prop, int WindowNo)
	{
		m_windowNo = WindowNo;
		setVariable(variable);
		setScript(script);
		setEnvironment(prop, WindowNo);
	}   //  Scriptlet

	/**
	 *  Full Constructor
	 *
	 *  @param variable Variable Name
	 *  @param script The Script
	 *  @param ctx Environment
	 */
	public Scriptlet (String variable, String script, HashMap<String,Object> ctx)
	{
		setVariable(variable);
		setScript(script);
		setEnvironment(ctx);
	}   //  Scriptlet

	/** Variable			*/
	private String      			m_variable;
	/** Script				*/
	private String      			m_script;
	/** Context				*/
	private HashMap<String,Object>	m_ctx;
	/** Result				*/
	private Object      			m_result;

	/*************************************************************************/

	/**
	 *  Execute Script
	 *  Loads environment and saves result
	 *  @return null or Exception
	 */
	public Exception execute()
	{
		m_result = null;
		if (m_variable == null || m_variable.length() == 0 || m_script == null || m_script.length() == 0)
		{
			IllegalArgumentException e = new IllegalArgumentException("No variable/script");
			log.warn(e.toString());
			return e;
		}
		Interpreter i = new Interpreter();
		loadEnvironment(i);
		try
		{
			log.info(m_script);
			i.eval(m_script);
		}
		catch (Exception e)
		{
			log.warn(e.toString());
			return e;
		}
		try
		{
			m_result = i.get (m_variable);
			log.info("Result (" + m_result.getClass().getName() + ") " + m_result);
		}
		catch (Exception e)
		{
			log.warn("Result - " + e);
			if (e instanceof NullPointerException)
				e = new IllegalArgumentException("Result Variable not found - " + m_variable);
			return e;
		}
		return null;
	}   //  execute

	public void validate() throws ParseException
	{
		Parser parser = new Parser(new StringReader(m_script));
		 while( !parser.Line()/*eof*/ )
		 {}
		
	}
	
	/**
	 *  Set Environment for Interpreter
	 *
	 *  @param i Interpreter
	 */
	private void loadEnvironment (Interpreter i)
	{
		if (m_ctx == null)
			return;
		Iterator<String> it = m_ctx.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			//
			// If key contains ".", skip it - teo_sarca BF [ 2031461 ] 
			if (key.indexOf(".") >= 0)
				continue;
			//
			Object value = m_ctx.get(key);
			try
			{
				if (value instanceof Boolean)
					i.set(key, ((Boolean)value).booleanValue());
				else if (value instanceof Integer)
					i.set(key, ((Integer)value).intValue());
				else if (value instanceof Double)
					i.set(key, ((Double)value).doubleValue());
				else
					i.set(key, value);
			}
			catch (EvalError ee)
			{
				log.error("", ee);
			}
		}
	}   //  setEnvironment

	/*************************************************************************/

	/**
	 *  Get Variable
	 *  @return variable
	 */
	public String getVariable()
	{
		return m_variable;
	}   //  getVariable

	/**
	 *  Set Variable
	 *  @param variable - if null set to VARIABLE
	 */
	public void setVariable(String variable)
	{
		if (variable == null || variable.length() == 0)
			m_variable = VARIABLE;
		else
			m_variable = variable;
	}

	/**
	 *  Set Script
	 *  @param script
	 */
	public void setScript(String script)
	{
		if (script == null)
			m_script = "";
		else
			m_script = script;
	}   //  setScript

	/**
	 *  Get Script
	 *  @return script
	 */
	public String getScript()
	{
		return m_script;
	}   //  getScript

	/**
	 *  Set Environment
	 *  @param prop
	 *  @param WindowNo included Window variables
	 */
	public void setEnvironment (Properties prop, int WindowNo)
	{
		if (prop == null)
			prop = Env.getCtx();

		m_ctx = new HashMap<String,Object>();
		//  Convert properties to HashMap
		Enumeration<?> en = prop.keys();
		while (en.hasMoreElements())
		{
			String key = en.nextElement().toString();
			//  filter
			if (key == null || key.length() == 0
				|| key.startsWith("P")              //  Preferences
				|| (key.indexOf('|') != -1 && !key.startsWith(String.valueOf(WindowNo)))    //  other Window Settings
				|| (key.indexOf('|') != -1 && key.indexOf('|') != key.lastIndexOf('|')) //other tab
				)
				continue;

			Object value = prop.get(key);
			setEnvironment (key, value);
		}

	}   //  setEnvironment

	/**
	 *  Set Environment key to value
	 *
	 *  @param key variable name ('#' will be converted to '_')
	 *  @param stringValue try to convert to Object
	 */
	public void setEnvironment (String key, String stringValue)
	{
		if (key == null || key.length() == 0)
			return;
	//	log.debug( "Scriptlet.setEnvironment " + key, stringValue);
		if (stringValue == null)
		{
			m_ctx.remove(key);
			return;
		}

		//  Boolean
		if (stringValue.equals("Y"))
		{
			m_ctx.put(convertKey(key), new Boolean(true));
			return;
		}
		if (stringValue.equals("N"))
		{
			m_ctx.put(convertKey(key), new Boolean(false));
			return;
		}

		//  Timestamp
		Timestamp timeValue = null;
		try
		{
			timeValue = Timestamp.valueOf(stringValue);
			m_ctx.put(convertKey(key), timeValue);
			return;
		}
		catch (Exception e) {}

		//  Numeric
		Integer intValue = null;
		try {
			intValue = Integer.valueOf(stringValue);
		} catch (Exception e) {}
		Double doubleValue = null;
		try {
			doubleValue = Double.valueOf(stringValue);
		} catch (Exception e) {}
		if (doubleValue != null)
		{
			if (intValue != null)
			{
				double di = Double.parseDouble(intValue.toString());
				//  the numbers are the same -> integer
				if (Double.compare(di, doubleValue.doubleValue()) == 0)
				{
					m_ctx.put(convertKey(key), intValue);
					return;
				}
			}
			m_ctx.put(convertKey(key), doubleValue);
			return;
		}
		if (intValue != null)
		{
			m_ctx.put(convertKey(key), intValue);
			return;
		}
		m_ctx.put(convertKey(key), stringValue);
	}   //  SetEnvironment

	/**
	 *  Set Environment key to value
	 *
	 *  @param key variable name ('#' will be vonverted to '_')
	 *  @param value
	 */
	public void setEnvironment (String key, Object value)
	{
		if (key != null && key.length() > 0)
		{
		//	log.debug( "Scriptlet.setEnvironment " + key, value);
			if (value == null)
				m_ctx.remove(key);
			else
				m_ctx.put(convertKey(key), value);
		}
	}   //  SetEnvironment

	/**
	 *  Convert Key
	 *  # -> _
	 *  @param key
	 *  @return converted key
	 */
	private String convertKey (String key)
	{
		return ScriptExecutor.convertToEngineKey(key, m_windowNo);
	}   //  convertKey

	/**
	 *  Set Environment
	 *  @param ctx
	 */
	public void setEnvironment (HashMap<String,Object> ctx)
	{
		if (ctx == null)
			m_ctx = new HashMap<String,Object>();
		else
			m_ctx = ctx;
	}   //  setEnvironment

	/**
	 *  Get Environment
	 *  @return environment
	 */
	public HashMap<String,Object> getEnvironment()
	{
		return m_ctx;
	}   //  getEnvironment

	
	/**************************************************************************
	 *  Get Result
	 *  @param runIt if true, execute script
	 *  @return result or null
	 */
	public Object getResult (boolean runIt)
	{
		if (runIt) 
			execute();
		return m_result;
	}   //  getResult

	/**
	 *  String Representation incl. Result
	 *  @return Scipt
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(m_variable);
		sb.append(" { ").append(m_script).append(" } = ").append(getResult(true));
		return sb.toString();
	}   //  toString

}   //  Scriptlet
