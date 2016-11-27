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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import de.metas.script.ScriptEngineFactory;

/**
 * ScriptEditor
 */
public class ScriptEditor
{
	/**
	 * Start ScriptEditor
	 *
	 * @param owner
	 * @param title Title
	 * @param script ScriptCode
	 * @param editable
	 * @return updated Script
	 */
	public static String start(final Frame owner, final String title, final String script, final boolean editable, final int windowNo)
	{
		final String value = Env.getContext(Env.getCtx(), windowNo, "Value");
		final String scriptEngineName = ScriptEngineFactory.extractEngineNameFromRuleValue(value);
		// TODO: generic editor for jsr223 script
		if (scriptEngineName == null || "groovy".equals(scriptEngineName))
		{
			final GroovyEditor gv = new GroovyEditor(owner, title, script, windowNo);
			return gv.getScript();
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @ScriptEngine@: " + scriptEngineName);
		}
	}
}
