package de.metas.invoice.service.impl;

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
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import com.google.common.collect.ImmutableSet;

import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.MatchInvId;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class MatchInvDAO implements IMatchInvDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_MatchInv getById(final int matchInvId)
	{
		Check.assumeGreaterThanZero(matchInvId, "matchInvId");
		return InterfaceWrapperHelper.load(matchInvId, I_M_MatchInv.class);
	}

	@Override
	public List<I_M_MatchInv> retrieveForInvoiceLine(final I_C_InvoiceLine il)
	{
		return retrieveForInvoiceLineQuery(il)
				.create()
				.list();
	}

	@Override
	public IQueryBuilder<I_M_MatchInv> retrieveForInvoiceLineQuery(final I_C_InvoiceLine il)
	{
		return queryBL.createQueryBuilder(I_M_MatchInv.class, il)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID, il.getC_InvoiceLine_ID())
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_M_MatchInv.COLUMN_M_MatchInv_ID)
				.endOrderBy()
		//
		;
	}

	@Override
	public List<I_M_MatchInv> retrieveForInOutLine(final I_M_InOutLine iol)
	{
		final IQueryBuilder<I_M_MatchInv> queryBuilder = queryBL.createQueryBuilder(I_M_MatchInv.class, iol)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID, iol.getM_InOutLine_ID())
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_MatchInv.COLUMN_M_MatchInv_ID);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public Set<MatchInvId> retrieveIdsProcessedButNotPostedForInOutLines(@NonNull final Set<InOutLineId> inoutLineIds)
	{
		if (inoutLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL
				.createQueryBuilder(I_M_MatchInv.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_MatchInv.COLUMN_M_InOutLine_ID, inoutLineIds)
				.addEqualsFilter(I_M_MatchInv.COLUMN_Processed, true)
				.addNotEqualsFilter(I_M_MatchInv.COLUMN_Posted, true)
				.create()
				.listIds(MatchInvId::ofRepoId);
	}

	@Override
	public Set<MatchInvId> retrieveIdsProcessedButNotPostedForInvoiceLines(@NonNull final Set<InvoiceLineId> invoiceLineIds)
	{
		if (invoiceLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL
				.createQueryBuilder(I_M_MatchInv.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_M_MatchInv.COLUMN_C_InvoiceLine_ID, invoiceLineIds)
				.addEqualsFilter(I_M_MatchInv.COLUMN_Processed, true)
				.addNotEqualsFilter(I_M_MatchInv.COLUMN_Posted, true)
				.create()
				.listIds(MatchInvId::ofRepoId);
	}

	@Override
	public List<I_M_MatchInv> retrieveForInOut(final I_M_InOut inout)
	{
		return queryBL.createQueryBuilder(I_M_InOutLine.class, inout)
				.addEqualsFilter(I_M_InOutLine.COLUMN_M_InOut_ID, inout.getM_InOut_ID())
				// .addOnlyActiveRecordsFilter() // all of them because maybe we want to delete them
				//
				.andCollectChildren(I_M_MatchInv.COLUMN_M_InOutLine_ID, I_M_MatchInv.class)
				// .addOnlyActiveRecordsFilter() // all of them because maybe we want to delete them
				.orderBy()
				.addColumn(I_M_MatchInv.COLUMN_M_MatchInv_ID)
				.endOrderBy()
				//
				.create()
				.list(I_M_MatchInv.class);
	}

	@Override
	public StockQtyAndUOMQty retrieveQtysInvoiced(
			@NonNull final I_M_InOutLine iol,
			@NonNull final StockQtyAndUOMQty initialQtys)
	{
		final List<I_M_MatchInv> matchInvRecords = queryBL.createQueryBuilder(I_M_MatchInv.class, iol)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID, iol.getM_InOutLine_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final ProductId productId = ProductId.ofRepoId(iol.getM_Product_ID());
		StockQtyAndUOMQty result = initialQtys;

		for (final I_M_MatchInv matchInvRecord : matchInvRecords)
		{
			final StockQtyAndUOMQty matchInvQtys = StockQtyAndUOMQtys
					.create(
							matchInvRecord.getQty(), productId,
							matchInvRecord.getQtyInUOM(), UomId.ofRepoIdOrNull(matchInvRecord.getC_UOM_ID()));
			result = StockQtyAndUOMQtys.add(result, matchInvQtys);
		}

		return result;
	}

	@Override
	public StockQtyAndUOMQty retrieveQtyMatched(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		final ProductId resultProductId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());

		StockQtyAndUOMQty result = StockQtyAndUOMQtys.createZero(resultProductId, UomId.ofRepoId(invoiceLine.getC_UOM_ID()));

		final List<I_M_MatchInv> matchInvRecords = queryBL.createQueryBuilder(I_M_MatchInv.class, invoiceLine)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
		for (final I_M_MatchInv matchInvRecord : matchInvRecords)
		{
			final ProductId productId = ProductId.ofRepoId(matchInvRecord.getM_Product_ID());
			final StockQtyAndUOMQty matchInvRecordQtys = StockQtyAndUOMQtys.create(
					matchInvRecord.getQty(), productId,
					matchInvRecord.getQtyInUOM(), UomId.ofRepoIdOrNull(matchInvRecord.getC_UOM_ID()));
			result = StockQtyAndUOMQtys.add(result, matchInvRecordQtys);

		}
		return result;
	}

	@Override
	public boolean hasMatchInvs(final I_C_InvoiceLine invoiceLine, final I_M_InOutLine inoutLine, final String trxName)
	{
		Check.assumeNotNull(invoiceLine, "invoiceLine not null");
		final int invoiceLineId = invoiceLine.getC_InvoiceLine_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLine);

		Check.assumeNotNull(inoutLine, "inoutLine not null");
		final int inoutLineId = inoutLine.getM_InOutLine_ID();

		return queryBL.createQueryBuilder(I_M_MatchInv.class, ctx, trxName)
				.addEqualsFilter(I_M_MatchInv.COLUMN_C_InvoiceLine_ID, invoiceLineId)
				.addEqualsFilter(I_M_MatchInv.COLUMN_M_InOutLine_ID, inoutLineId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}
}
