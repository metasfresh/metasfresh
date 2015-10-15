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


import org.adempiere.util.Check;

/**
 * Singleton implementation of {@link Evaluatee2}
 *
 * @author tsa
 *
 */
public final class SingletonEvaluatee implements Evaluatee2
{
	public static final SingletonEvaluatee of(final String variableName, final Object value)
	{
		return new SingletonEvaluatee(variableName, value);
	}

	private final String variableName;
	private final String value;

	private SingletonEvaluatee(final String variableName, final Object value)
	{
		super();
		Check.assumeNotEmpty(variableName, "variableName not empty");
		this.variableName = variableName;
		this.value = value == null ? null : String.valueOf(value);
	}

	@Override
	public String get_ValueAsString(final String variableName)
	{
		if (!this.variableName.equals(variableName))
		{
			return null;
		}
		return value;
	}

	@Override
	public boolean has_Variable(final String variableName)
	{
		return this.variableName.equals(variableName);
	}

	@Override
	public String get_ValueOldAsString(final String variableName)
	{
		return null;
	}

}
