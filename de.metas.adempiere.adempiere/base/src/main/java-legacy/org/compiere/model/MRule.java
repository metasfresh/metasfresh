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
 * Contributor(s): Carlos Ruiz - globalqss                                    *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CCache;
import org.compiere.util.Util;

/**
 *	Persistent Rule Model
 *  @author Carlos Ruiz
 *  @version $Id: MRule.java
 */
public class MRule extends X_AD_Rule
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9166262780531877045L;
	
	//global or login context variable prefix
	public final static String GLOBAL_CONTEXT_PREFIX = "G_";
	//window context variable prefix
	public final static String WINDOW_CONTEXT_PREFIX = "W_";
	//method call arguments prefix
	public final static String ARGUMENTS_PREFIX = "A_";
	//process parameters prefix
	public final static String PARAMETERS_PREFIX = "P_";
	
	public static final String SCRIPT_PREFIX = "@script:";

	/**
	 * 	Get Rule from Cache
	 *	@param ctx context
	 *	@param AD_Rule_ID id
	 *	@return MRule
	 */
	public static MRule get (Properties ctx, int AD_Rule_ID)
	{
		Integer key = new Integer (AD_Rule_ID);
		MRule retValue = (MRule) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MRule (ctx, AD_Rule_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**
	 * 	Get Rule from Cache
	 *	@param ctx context
	 *	@param ruleValue case sensitive rule Value
	 *	@return Rule
	 */
	public static MRule get (Properties ctx, String ruleValue)
	{
		if (ruleValue == null)
			return null;
		Iterator<MRule> it = s_cache.values().iterator();
		while (it.hasNext())
		{
			MRule retValue = (MRule)it.next();
			if (ruleValue.equals(retValue.getValue()))
				return retValue;
		}
		//
		final String whereClause = "Value=?";
		MRule retValue = new Query(ctx,I_AD_Rule.Table_Name,whereClause,null)
		.setParameters(ruleValue)
		.setOnlyActiveRecords(true)
		.first();
		
		if (retValue != null)
		{
			Integer key = new Integer (retValue.getAD_Rule_ID());
			s_cache.put (key, retValue);
		}
		return retValue;
	}	//	get
	
	/**
	 * 	Get Model Validation Login Rules
	 *	@param ctx context
	 *	@return Rule
	 */
	public static List<MRule> getModelValidatorLoginRules (Properties ctx)
	{
		final String whereClause = "EventType=?";
		List<MRule> rules = new Query(ctx,I_AD_Rule.Table_Name,whereClause,null)
		.setParameters(EVENTTYPE_ModelValidatorLoginEvent)
		.setOnlyActiveRecords(true)
		.list();
		if (rules != null && rules.size() > 0)
			return rules;
		else
			return null;
	}	//	getModelValidatorLoginRules

	/**	Cache						*/
	private static CCache<Integer,MRule> s_cache = new CCache<Integer,MRule>("AD_Rule", 20);
	
	/* Engine Manager */
	private ScriptEngineManager factory = null;
	/* The Engine */
	ScriptEngine engine = null;
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Rule_ID id
	 *	@param trxName transaction
	 */
	public MRule (Properties ctx, int AD_Rule_ID, String trxName)
	{
		super (ctx, AD_Rule_ID, trxName);
	}	//	MRule

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MRule (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRule
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		// Validate format for scripts
		// must be engine:name
		// where engine can be groovy, jython or beanshell
		if (getRuleType().equals(RULETYPE_JSR223ScriptingAPIs))
		{
			String engineName = getEngineName();
			if (engineName == null || 
					(!   (engineName.equalsIgnoreCase("groovy")
							|| engineName.equalsIgnoreCase("jython") 
							|| engineName.equalsIgnoreCase("beanshell"))))
			{
				throw new AdempiereException("@WrongScriptValue@");
			}
		}
		return true;
	}	//	beforeSave
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer ("MRule[");
		sb.append (get_ID()).append ("-").append (getValue()).append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Script Engine for this rule
	 *	@return ScriptEngine
	 */
	public ScriptEngine getScriptEngine() {
		factory = new ScriptEngineManager();
		String engineName = getEngineName();
		if (engineName != null)
			engine = factory.getEngineByName(getEngineName());
		return engine;
	}

	public String getEngineName() {
		int colonPosition = getValue().indexOf(":");
		if (colonPosition < 0)
			return null;
		return getValue().substring(0, colonPosition);
	}
	
	/**************************************************************************
	 *	Set Context ctx to the engine based on windowNo
	 *  @param engine ScriptEngine
	 *  @param ctx context
	 *  @param windowNo window number
	 */
	public static void setContext(ScriptEngine engine, Properties ctx, int windowNo) {
		Enumeration<Object> en = ctx.keys();
		while (en.hasMoreElements())
		{
			String key = en.nextElement().toString();
			//  filter
			if (key == null || key.length() == 0
					|| key.startsWith("P")              //  Preferences
					|| (key.indexOf('|') != -1 && !key.startsWith(String.valueOf(windowNo)))    //  other Window Settings
					|| (key.indexOf('|') != -1 && key.indexOf('|') != key.lastIndexOf('|')) //other tab
			)
				continue;
			Object value = ctx.get(key);
			if (value != null) {
				if (value instanceof Boolean)
					engine.put(convertKey(key, windowNo), ((Boolean)value).booleanValue());
				else if (value instanceof Integer)
					engine.put(convertKey(key, windowNo), ((Integer)value).intValue());
				else if (value instanceof Double)
					engine.put(convertKey(key, windowNo), ((Double)value).doubleValue());
				else
					engine.put(convertKey(key, windowNo), value);
			}
		}
	}

	/**
	 *  Convert Key
	 *  # -> _
	 *  @param key
	 * @param m_windowNo 
	 *  @return converted key
	 */
	public static String convertKey (String key, int m_windowNo)
	{
		String k = m_windowNo + "|";
		if (key.startsWith(k))
		{
			String retValue = WINDOW_CONTEXT_PREFIX + key.substring(k.length());
			retValue = Util.replace(retValue, "|", "_");
			return retValue;
		}
		else
		{
			String retValue = null;
			if (key.startsWith("#"))
				retValue = GLOBAL_CONTEXT_PREFIX + key.substring(1);
			else
				retValue = key;
			retValue = Util.replace(retValue, "#", "_");
			return retValue;
		}
	}   //  convertKey

}	//	MRule