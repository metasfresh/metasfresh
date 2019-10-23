package de.metas.adempiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.HashMap;
import java.util.Map;

public class ADHyperlink
{
	public static enum Action
	{
		ShowWindow
	};

	private Action action;
	private Map<String, String> params = new HashMap<String, String>();

	public ADHyperlink(Action action, Map<String, String> params)
	{
		super();
		this.action = action;
		this.params = params;
	}

	public Action getAction()
	{
		return action;
	}

	public Map<String, String> getParameters()
	{
		return params;
	}
}
