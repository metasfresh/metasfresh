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
package org.compiere.apps;

import java.awt.Frame;

import org.compiere.model.Scriptlet;
import org.compiere.util.Env;

/**
 *  ScriptEditor
 */
public class ScriptEditor 
{
	/**
	 *  Start ScriptEditor
	 *
	 *  @param header   Title
	 *  @param script   ScriptCode
	 *  @param editable
	 *  @return updated Script
	 *  
	 *  @deprecated since 3.3.1
	 */
	public static String start (String header, String script, boolean editable, int WindowNo)
	{
		return start(null, header, script, editable, WindowNo);
	}
	
	/**
	 *  Start ScriptEditor
	 *
	 *  @param owner
	 *  @param header   Title
	 *  @param script   ScriptCode
	 *  @param editable
	 *  @return updated Script
	 */
	public static String start (Frame owner, String header, String script, boolean editable, int WindowNo)
	{
		Scriptlet scr = new Scriptlet (Scriptlet.VARIABLE, script, Env.getCtx(), WindowNo);
		String value = Env.getContext(Env.getCtx(), WindowNo, "Value");
		//TODO: generic editor for jsr223 script
		if (value != null && value.startsWith("groovy:"))
		{
			GroovyEditor gv = new GroovyEditor (owner, header, script, WindowNo);
			return gv.getScript();
		}
		else
		{
			BeanShellEditor se = new BeanShellEditor (owner, header, scr, WindowNo);
			return scr.getScript();
		}
	}   //  start

}   //  ScriptEditor
