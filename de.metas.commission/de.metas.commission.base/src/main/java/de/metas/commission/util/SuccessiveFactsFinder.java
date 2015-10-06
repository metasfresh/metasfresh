/**
 * 
 */
package de.metas.commission.util;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.Properties;

import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.MCAdvCommissionFact;

/**
 * Tool to find an old fact within a given instance.
 * 
 * @author ts
 * 
 */
public abstract class SuccessiveFactsFinder
{
	public List<I_C_AdvCommissionFact> retrieveSuccessive(
			final Properties ctx,
			final I_C_AdvCommissionFact searchFromFact,
			final String trxName)
	{
		final List<I_C_AdvCommissionFact> result = new ArrayList<I_C_AdvCommissionFact>();

		for (final I_C_AdvCommissionFact fact : MCAdvCommissionFact.retrieveFollowUpFacts(searchFromFact))
		{
			if (isOK(fact))
			{
				result.add(fact);
			}

			// recurse
			result.addAll(retrieveSuccessive(ctx, fact, trxName));
		}

		return result;
	}

	public abstract boolean isOK(I_C_AdvCommissionFact fact);
}