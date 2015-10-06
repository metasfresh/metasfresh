package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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

import org.compiere.util.Util;

import de.metas.dunning.exception.DunningException;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunningAggregator;
import de.metas.dunning.spi.IDunningAggregator.Result;

public class CompositeDunningAggregator
{
	private final List<IDunningAggregator> dunningAggregators = new ArrayList<IDunningAggregator>();
	private final IDunningAggregator defaultDunningAggregator = new DefaultDunningAggregator();

	public void addAggregator(IDunningAggregator aggregator)
	{
		dunningAggregators.add(aggregator);
	}

	public boolean isNewDunningDoc(I_C_Dunning_Candidate candidate)
	{
		Result finalResult = null;
		for (IDunningAggregator agg : dunningAggregators)
		{
			final Result result = agg.isNewDunningDoc(candidate);
			if (result == Result.I_FUCKING_DONT_CARE)
			{
				continue;
			}

			if (result == null)
			{
				finalResult = result;
			}

			if (!Util.same(result, finalResult))
			{
				throw new DunningException("Confusing verdict for aggregators: " + dunningAggregators);
			}
		}

		if (finalResult == null)
		{
			finalResult = defaultDunningAggregator.isNewDunningDoc(candidate);
		}

		return toBoolean(finalResult);
	}

	public boolean isNewDunningDocLine(I_C_Dunning_Candidate candidate)
	{
		if (dunningAggregators.isEmpty())
		{
			throw new DunningException("No child " + IDunningAggregator.class + " registered");
		}

		Result finalResult = null;
		for (IDunningAggregator agg : dunningAggregators)
		{
			final Result result = agg.isNewDunningDocLine(candidate);
			if (result == Result.I_FUCKING_DONT_CARE)
			{
				continue;
			}

			if (result == null)
			{
				finalResult = result;
			}

			if (!Util.same(result, finalResult))
			{
				throw new DunningException("Confusing verdict for aggregators: " + dunningAggregators);
			}
		}

		if (finalResult == null)
		{
			finalResult = defaultDunningAggregator.isNewDunningDocLine(candidate);
		}

		return toBoolean(finalResult);
	}

	private static final boolean toBoolean(final Result result)
	{
		if (result == Result.YES)
		{
			return true;
		}
		else if (result == Result.NO)
		{
			return false;
		}
		else
		{
			throw new IllegalArgumentException("Result shall be YES or NO: " + result);
		}
	}
}
