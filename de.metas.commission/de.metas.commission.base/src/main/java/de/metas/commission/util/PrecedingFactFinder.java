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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionInstance;
import de.metas.commission.model.MCAdvCommissionFact;

/**
 * Tool to find an old fact within a given instance. Used by 'recordInstanceTrigger()'.
 * 
 * @author ts
 * 
 */
public abstract class PrecedingFactFinder
{
	public final I_C_AdvCommissionFact retrievePrecedingFirst(
			final Properties ctx,
			final I_C_AdvCommissionInstance instance,
			final I_C_AdvCommissionFact searchFromFact,
			final Map<ArrayKey, I_C_AdvCommissionFact> nonUsablePOs,
			final String trxName)
	{
		final List<I_C_AdvCommissionFact> result = retrievePreceding(ctx, instance, searchFromFact, nonUsablePOs, true, trxName);

		Check.assume(result.size() <= 1, "Size<=1 of result=" + result + " for " + instance + ", " + searchFromFact + " and nonUsablePOs=" + nonUsablePOs);
		if (result.isEmpty())
		{
			return null;
		}
		return result.get(0);
	}

	public final List<I_C_AdvCommissionFact> retrievePrecedingAll(
			final Properties ctx,
			final I_C_AdvCommissionInstance instance,
			final I_C_AdvCommissionFact searchFromFact,
			final Map<ArrayKey, I_C_AdvCommissionFact> nonUsablePOs,
			final String trxName)
	{
		return retrievePreceding(ctx, instance, searchFromFact, nonUsablePOs, false, trxName);
	}

	/**
	 * 
	 * @param ctx
	 * @param instance
	 * @param searchFromFact
	 * @param nonUsablePOs map PO-key -> fact. If a PO's key is included, it means that facts are not eligible to be returned.
	 * @param onlyReturnFirst if <code>true</code>, then the search is stopped with the first fact that is found.
	 * @param trxName
	 * @return
	 */
	private List<I_C_AdvCommissionFact> retrievePreceding(
			final Properties ctx,
			final I_C_AdvCommissionInstance instance,
			final I_C_AdvCommissionFact searchFromFact,
			final Map<ArrayKey, I_C_AdvCommissionFact> nonUsablePOs,
			final boolean onlyReturnFirst,
			final String trxName)
	{
		final List<I_C_AdvCommissionFact> precedingFacts = retrievePreceding(ctx, instance, searchFromFact, trxName);

		if (precedingFacts.isEmpty())
		{
			// we reached the begin of the chain of facts for this instance
			return Collections.emptyList();
		}

		Collections.reverse(precedingFacts);

		final List<I_C_AdvCommissionFact> result = new ArrayList<I_C_AdvCommissionFact>();

		for (final I_C_AdvCommissionFact currentFact : precedingFacts)
		{
			final ArrayKey currentPOKey = Util.mkKey(currentFact.getAD_Table_ID(), currentFact.getRecord_ID());
			if (nonUsablePOs != null && nonUsablePOs.containsKey(currentPOKey))
			{
				// currentFact's PO already occurred in a reversal fact
				continue;
			}

			if (isOK(currentFact))
			{
				if (onlyReturnFirst)
				{
					return Collections.singletonList(currentFact);
				}
				else
				{
					result.add(currentFact);
				}
			}

			// currentFact is a reversal fact
			if (nonUsablePOs != null)
			{
				nonUsablePOs.put(currentPOKey, currentFact);
			}
		}

		// all preceding facts are reversals
		// recurse to find a non-reversal fact
		for (final I_C_AdvCommissionFact precedingReversalFact : precedingFacts)
		{
			final List<I_C_AdvCommissionFact> recurseResult = retrievePreceding(ctx, instance, precedingReversalFact, nonUsablePOs, onlyReturnFirst, trxName);

			result.addAll(recurseResult);

			if (onlyReturnFirst && !recurseResult.isEmpty())
			{
				return recurseResult;
			}
		}

		return result;
	}

	/**
	 * Utiliy method that can be used also by implementors.
	 * 
	 * @param ctx
	 * @param instance
	 * @param searchFromFact
	 * @param trxName
	 * @return
	 */
	protected List<I_C_AdvCommissionFact> retrievePreceding(
			final Properties ctx,
			final I_C_AdvCommissionInstance instance,
			final I_C_AdvCommissionFact searchFromFact,
			final String trxName)
	{

		final List<MCAdvCommissionFact> precedingFacts =
				MCAdvCommissionFact.mkQuery(ctx, trxName)
						.setAdvCommissionInstanceId(instance.getC_AdvCommissionInstance_ID())
						.setFollowUpSubstring(Integer.toString(searchFromFact.getC_AdvCommissionFact_ID()))
						.list();

		final List<I_C_AdvCommissionFact> result = new ArrayList<I_C_AdvCommissionFact>();
		result.addAll(precedingFacts);
		return result;
	}

	public abstract boolean isOK(I_C_AdvCommissionFact fact);

}