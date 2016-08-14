package de.metas.ui.web.window.util;

import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee2;

import de.metas.printing.esb.base.util.Check;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class GlobalContextEvaluatee implements Evaluatee2
{
	public static final transient GlobalContextEvaluatee instance = new GlobalContextEvaluatee();

	public GlobalContextEvaluatee()
	{
		super();
	}

	@Override
	public boolean has_Variable(String variableName)
	{
		if (variableName == null || variableName.isEmpty())
		{
			return false;
		}
		else if (variableName.startsWith("#"))                                                  // Env, global var
		{
			return true;
		}
		else if (variableName.startsWith("$"))                                                  // Env, global accounting var
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String get_ValueAsString(String variableName)
	{
		final String value;
		if (variableName.startsWith("#"))                                                 // Env, global var
		{
			value = Env.getContext(Env.getCtx(), variableName);
		}
		else if (variableName.startsWith("$"))                                                 // Env, global accounting var
		{
			value = Env.getContext(Env.getCtx(), variableName);
		}
		else
		{
			return null;
		}

		//
		// Use some default value
		if (Check.isEmpty(value))
		{
			// FIXME hardcoded
			if (variableName.startsWith("$Element_"))
			{
				return DisplayType.toBooleanString(false);
			}
		}

		return value;
	}

	@Override
	public String get_ValueOldAsString(String variableName)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
