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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

public class CompositeEvaluatee implements Evaluatee2
{
	public static final CompositeEvaluatee of(final Evaluatee... evaluatees)
	{
		Check.assumeNotEmpty(evaluatees, "evaluatees not empty");
		
		final CompositeEvaluatee composite = new CompositeEvaluatee(evaluatees[0]);
		for (int i = 1; i < evaluatees.length; i++)
		{
			composite.addEvaluatee(evaluatees[i]);
		}
		return composite;
	}

	private List<Evaluatee> sources = new ArrayList<Evaluatee>();

	public CompositeEvaluatee(Evaluatee source)
	{
		addEvaluatee(source);
	}

	public CompositeEvaluatee addEvaluatee(Evaluatee source)
	{
		Check.assume(source != null, "source is null");

		sources.add(source);
		return this;
	}

	@Override
	public String get_ValueAsString(String variableName)
	{
		for (Evaluatee source : sources)
		{
			String value = source.get_ValueAsString(variableName);
			if (!Check.isEmpty(value))
				return value;
		}
		return null;
	}

	@Override
	public boolean has_Variable(String variableName)
	{
		for (Evaluatee source : sources)
		{
			if (Evaluator.hasVariable(source, variableName))
				return true;
		}

		return false;
	}

	@Override
	public String get_ValueOldAsString(String variableName)
	{
		//
		// This logic applies only to first Evaluatee source
		Evaluatee source = sources.get(0);
		if (source instanceof Evaluatee2)
		{
			return ((Evaluatee2)source).get_ValueOldAsString(variableName);
		}

		return null;
	}
}
