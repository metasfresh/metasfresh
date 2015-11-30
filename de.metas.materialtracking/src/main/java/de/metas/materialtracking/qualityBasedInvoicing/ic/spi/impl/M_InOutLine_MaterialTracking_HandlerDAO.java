package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;
import org.compiere.process.DocAction;

import de.metas.materialtracking.model.I_M_InOutLine;

/*
 * #%L
 * de.metas.materialtracking
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

public class M_InOutLine_MaterialTracking_HandlerDAO
{

	/*package */ boolean isInvoiceable(final Object model)
	{
		final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final ICompositeQueryFilter<I_M_InOutLine> filter = createFilter(ctx, trxName);
		return filter.accept(inOutLine);
	}

	/*package */ Iterator<I_M_InOutLine> retrieveAllModelsWithMissingCandidates(
			final Properties ctx,
			final int limit,
			final String trxName)
	{
		final ICompositeQueryFilter<I_M_InOutLine> filters = createFilter(ctx, trxName);

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// @formatter:off
		return queryBL.createQueryBuilder(I_M_InOutLine.class, ctx, trxName)
				.filter(filters)
			.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOut_ID)
				.endOrderBy()
			.create()
			.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
			.setOption(IQuery.OPTION_IteratorBufferSize, limit + 1) // load them all at once
			.setLimit(limit)
			.iterate(I_M_InOutLine.class);
		// @formatter:on
	}

	private ICompositeQueryFilter<I_M_InOutLine> createFilter(final Properties ctx, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_M_InOutLine> filters = queryBL.createCompositeQueryFilter(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_IsInvoiceCandidate, false)
				.addNotEqualsFilter(I_M_InOutLine.COLUMNNAME_M_Material_Tracking_ID, null)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, null)
				.addOnlyContextClient(ctx);

		// if the inout was reversed, and there is no IC yet, don't bother creating one
		final IQuery<I_M_InOut> inoutQuery = queryBL.createQueryBuilder(I_M_InOut.class, ctx, trxName)
				.addNotEqualsFilter(I_M_InOut.COLUMNNAME_DocStatus, DocAction.STATUS_Reversed)
				.create();
		filters.addInSubQueryFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, I_M_InOut.COLUMNNAME_M_InOut_ID, inoutQuery);
		return filters;
	}

}
