package org.adempiere.ad.validationRule.impl;

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

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.util.Check;
import org.compiere.util.Evaluatee;

public class EvaluateeValidationContext implements IValidationContext
{
	private final Evaluatee evaluatee;

	public EvaluateeValidationContext(final Evaluatee evaluatee)
	{
		Check.assumeNotNull(evaluatee, "evaluatee not null");
		this.evaluatee = evaluatee;
	}

	/**
	 * @return null
	 */
	@Override
	public String getTableName()
	{
		return null;
	}

	@Override
	public String get_ValueAsString(final String variableName)
	{
		return evaluatee.get_ValueAsString(variableName);
	}

	@Override
	public Integer get_ValueAsInt(final String variableName, final Integer defaultValue)
	{
		return evaluatee.get_ValueAsInt(variableName, defaultValue);
	}

	@Override
	public Boolean get_ValueAsBoolean(final String variableName, final Boolean defaultValue)
	{
		return evaluatee.get_ValueAsBoolean(variableName, defaultValue);
	}

}
