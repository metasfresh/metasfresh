package de.metas.inout.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

import de.metas.inout.IInOutDAO;

public class InOutDAO implements IInOutDAO
{
	@Override
	public List<I_M_InOutLine> retrieveLines(final I_M_InOut inOut)
	{
		return retrieveLines(inOut, I_M_InOutLine.class);
	}

	@Override
	public List<I_M_InOutLine> retrieveAllLines(final I_M_InOut inOut)
	{
		final boolean retrieveAll = true;
		return retrieveLines(inOut, retrieveAll, I_M_InOutLine.class);
	}

	@Override
	public <T extends I_M_InOutLine> List<T> retrieveLines(final I_M_InOut inOut, final Class<T> inoutLineClass)
	{
		final boolean retrieveAll = false;
		return retrieveLines(inOut, retrieveAll, inoutLineClass);
	}

	private <T extends I_M_InOutLine> List<T> retrieveLines(final I_M_InOut inOut,
			final boolean retrieveAll,
			final Class<T> inoutLineClass)
	{
		Check.assumeNotNull(inOut, "inOut not null");
		Check.assumeNotNull(inoutLineClass, "inoutLineClass not null");

		final IQueryBuilder<I_M_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_InOutLine.class, inOut)
				.addEqualsFilter(I_M_InOutLine.COLUMN_M_InOut_ID, inOut.getM_InOut_ID());

		if (!retrieveAll)
		{
			queryBuilder.addOnlyActiveRecordsFilter();
		}

		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_Line)
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);

		final List<T> inoutLines = queryBuilder
				.create()
				.list(inoutLineClass);

		// Optimization: set M_InOut link
		for (final I_M_InOutLine inoutLine : inoutLines)
		{
			inoutLine.setM_InOut(inOut);
		}
		return inoutLines;
	}

	@Override
	public List<I_M_InOutLine> retrieveLinesForOrderLine(final I_C_OrderLine orderLine)
	{
		return retrieveLinesForOrderLine(orderLine, I_M_InOutLine.class);
	}

	@Override
	public <T extends I_M_InOutLine> List<T> retrieveLinesForOrderLine(final I_C_OrderLine orderLine, final Class<T> clazz)
	{
		final IQueryBuilder<I_M_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_InOutLine.class, orderLine)
				.addEqualsFilter(I_M_InOutLine.COLUMN_C_OrderLine_ID, orderLine.getC_OrderLine_ID())
				// .filterByClientId()
				.addOnlyActiveRecordsFilter();
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);

		return queryBuilder.create()
				.list(clazz);
	}

	@Override
	public <T extends I_M_InOutLine> List<T> retrieveLinesWithoutOrderLine(final I_M_InOut inOut, final Class<T> clazz)
	{
		final IQueryBuilder<I_M_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_InOutLine.class, inOut)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOut.getM_InOut_ID())
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, null)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);

		return queryBuilder.create()
				.list(clazz);
	}

	@Override
	public IQueryBuilder<I_M_InOutLine> createUnprocessedShipmentLinesQuery(final Properties ctx)
	{
		// + " AND io.DocStatus IN ('DR', 'IP','WC')"
		// + " AND io.IsSOTrx='Y'"
		// + " AND iol.AD_Client_ID=?";

		final IQueryBuilder<I_M_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_InOut.class, ctx, ITrx.TRXNAME_None)
				.addInArrayOrAllFilter(I_M_InOut.COLUMNNAME_DocStatus,
						DocAction.STATUS_Drafted,  // task: 07448: we also need to consider drafted shipments, because that's the customer workflow, and qty in a drafted InOut don'T couln'T at picked
						// anymore, because they are already in a shipper-transportation
						DocAction.STATUS_InProgress,
						DocAction.STATUS_WaitingConfirmation)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_IsSOTrx, true)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.andCollectChildren(I_M_InOutLine.COLUMN_M_InOut_ID, I_M_InOutLine.class);

		return queryBuilder;
	}

	@Override
	public IQueryBuilder<I_M_InOutLine> retrieveAllReferencingLinesBuilder(final I_M_InOutLine packingMaterialLine)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_InOutLine.class, packingMaterialLine)
				// .addOnlyActiveRecordsFilter() add all, also inactive ones
				.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_M_PackingMaterial_InOutLine_ID, packingMaterialLine.getM_InOutLine_ID())
				.orderBy().addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID).endOrderBy();

	}

	@Override
	public List<Integer> retrieveLinesWithQualityIssues(final I_M_InOut inOut)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<de.metas.inout.model.I_M_InOutLine> queryBuilder = queryBL
				.createQueryBuilder(de.metas.inout.model.I_M_InOutLine.class, inOut)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOut.getM_InOut_ID())
				.addNotEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_QualityDiscountPercent, null)
				.addCompareFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_QualityDiscountPercent, Operator.GREATER, Env.ZERO);

		return queryBuilder
				.create()
				.listIds();
	}
}
