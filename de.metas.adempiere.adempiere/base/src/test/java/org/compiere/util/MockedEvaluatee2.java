package org.compiere.util;

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

import org.junit.Ignore;

@Ignore
public class MockedEvaluatee2 extends MockedEvaluatee implements Evaluatee2
{
	private final Map<String, String> mapOld = new HashMap<String, String>();

	public boolean hasVariableReturn = true;

	@Override
	public boolean has_Variable(String variableName)
	{
		return hasVariableReturn;
	}

	@Override
	public String get_ValueOldAsString(String variableName)
	{
		return mapOld.get(variableName);
	}

	public void put(String variableName, String value, String valueOld)
	{
		this.put(variableName, value);
		mapOld.put(variableName, valueOld);
	}
}
