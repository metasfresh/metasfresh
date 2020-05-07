package de.metas.handlingunits.ddorder.api.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order;

import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.handlingunits.model.I_DD_OrderLine_HU_Candidate;
import de.metas.handlingunits.model.I_M_HU;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUDDOrderDAO implements IHUDDOrderDAO
{
	@Override
	public IQueryFilter<I_M_HU> getHUsNotAlreadyScheduledToMoveFilter()
	{
		final Properties ctx = Env.getCtx();
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_M_HU> filter = queryBL.createCompositeQueryFilter(I_M_HU.class);

		// HU Filter: only those which were not already assigned to a DD_OrderLine
		{
			final IQuery<I_DD_OrderLine_HU_Candidate> ddOrderLineHUCandidatesQuery = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class, ctx, ITrx.TRXNAME_ThreadInherited)
					.addOnlyActiveRecordsFilter()
					.create();
			filter.addNotInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_DD_OrderLine_HU_Candidate.COLUMN_M_HU_ID, ddOrderLineHUCandidatesQuery);
		}

		return filter;
	}

	@Override
	public void addToHUsScheduledToMove(final I_DD_OrderLine ddOrderline, final I_M_HU hu)
	{
		final I_DD_OrderLine_HU_Candidate assignment = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_HU_Candidate.class, ddOrderline);
		assignment.setAD_Org_ID(ddOrderline.getAD_Org_ID());
		assignment.setDD_OrderLine(ddOrderline);
		assignment.setM_HU(hu);
		InterfaceWrapperHelper.save(assignment);
	}

	@Override
	public void addToHUsScheduledToMove(final I_DD_OrderLine ddOrderline, final Collection<I_M_HU> hus)
	{
		for (final I_M_HU hu : hus)
		{
			addToHUsScheduledToMove(ddOrderline, hu);
		}
	}

	@Override
	public final List<Integer> retrieveHUIdsScheduledToMove(final Properties ctx, final Set<Integer> ddOrderLineIds)
	{
		if (ddOrderLineIds.isEmpty())
		{
			return Collections.emptyList();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_DD_OrderLine_HU_Candidate.COLUMN_DD_OrderLine_ID, ddOrderLineIds)
				//
				// Collect HUs from those candidates
				.andCollect(I_DD_OrderLine_HU_Candidate.COLUMN_M_HU_ID)
				.addOnlyActiveRecordsFilter()
				//
				// Fetch M_HU_IDs
				.create()
				.listIds();
	}

	@Override
	public void clearHUsScheduledToMoveList(final org.eevolution.model.I_DD_OrderLine ddOrderline)
	{
		Check.assumeNotNull(ddOrderline, "ddOrderline not null");

		//
		// Create a query to select ALL candidate assignments for given DD_OrderLine
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_DD_OrderLine_HU_Candidate> query = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class, ddOrderline)
				.addInArrayOrAllFilter(I_DD_OrderLine_HU_Candidate.COLUMN_DD_OrderLine_ID, ddOrderline.getDD_OrderLine_ID());

		// Remove selected assignments
		removeFromHUsScheduledToMoveList(query);
	}

	@Override
	public void clearHUsScheduledToMoveList(final I_DD_Order ddOrder)
	{
		Check.assumeNotNull(ddOrder, "ddOrder not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_DD_OrderLine_HU_Candidate> query = queryBL.createQueryBuilder(org.eevolution.model.I_DD_OrderLine.class, ddOrder)
				.addEqualsFilter(I_DD_OrderLine.COLUMN_DD_Order_ID, ddOrder.getDD_Order_ID())
				//
				// Collect all DD_OrderLine_HU_Candidate for our DD_OrderLines
				.andCollectChildren(I_DD_OrderLine_HU_Candidate.COLUMN_DD_OrderLine_ID, I_DD_OrderLine_HU_Candidate.class);

		removeFromHUsScheduledToMoveList(query);
	}

	@Override
	public void removeFromHUsScheduledToMoveList(final org.eevolution.model.I_DD_OrderLine ddOrderline, final Collection<I_M_HU> hus)
	{
		Check.assumeNotNull(ddOrderline, "ddOrderline not null");

		//
		// Extract the M_HU_IDs
		if (hus == null || hus.isEmpty())
		{
			return;
		}
		final Set<Integer> huIds = new HashSet<>();
		for (final I_M_HU hu : hus)
		{
			final int huId = hu.getM_HU_ID();
			huIds.add(huId);
		}
		if (huIds.isEmpty())
		{
			return;
		}

		//
		// Create the query to select the lines we want to remove
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_DD_OrderLine_HU_Candidate> query = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class, ddOrderline)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMN_DD_OrderLine_ID, ddOrderline.getDD_OrderLine_ID())
				.addInArrayOrAllFilter(I_DD_OrderLine_HU_Candidate.COLUMN_M_HU_ID, huIds);

		// Remove selected assignments
		removeFromHUsScheduledToMoveList(query);
	}

	private final void removeFromHUsScheduledToMoveList(final IQueryBuilder<I_DD_OrderLine_HU_Candidate> query)
	{
		//
		// Delete all matching records
		query
				.create()
				.deleteDirectly();
	}

}
