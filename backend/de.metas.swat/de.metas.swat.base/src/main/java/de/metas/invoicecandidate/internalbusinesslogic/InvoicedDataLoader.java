package de.metas.invoicecandidate.internalbusinesslogic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class InvoicedDataLoader
{
	@NonNull
	Set<InvoiceCandidateId> invoiceCandidateIds;

	@NonNull
	Map<InvoiceCandidateId, CurrencyId> currencyIds;

	@NonNull
	Map<InvoiceCandidateId, UomId> stockUomIds;

	@NonNull
	Map<InvoiceCandidateId, UomId> icUomIds;

	@NonNull
	Map<InvoiceCandidateId, ProductId> productIds;

	public Map<InvoiceCandidateId, InvoicedData> loadInvoicedData()
	{
		final RawData rawData = loadRawData();

		final ImmutableMap.Builder<InvoiceCandidateId, InvoicedData> result = ImmutableMap.builder();

		for (final InvoiceCandidateId invoiceCandidateId : invoiceCandidateIds)
		{
			final CurrencyId currencyId = currencyIds.get(invoiceCandidateId);

			// as in deliveryData, load it from external records!
			// will be used to later store the "resulting "sums" in the record itself

			final ProductId productId = productIds.get(invoiceCandidateId);

			final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);

			final UomId stockUomId = stockUomIds.get(invoiceCandidateId);

			Quantity qty = Quantitys.zero(icUomIds.get(invoiceCandidateId));
			Quantity qtyInStockUom = Quantitys.zero(stockUomId);

			Money netAmount = Money.zero(currencyId);

			final ImmutableList<I_C_Invoice_Line_Alloc> ilas = rawData
					.getInvoiceCandidateId2IlaRecords()
					.get(invoiceCandidateId.getRepoId());

			for (final I_C_Invoice_Line_Alloc ila : ilas)
			{
				// we don't need to check the invoice's DocStatus. If the ila is there, we count it.
				final Quantity qtyInvoiced = extractQtyInvoiced(ila);
				if(qtyInvoiced != null)
				{
					qty = Quantitys.add(conversionCtx, qty, qtyInvoiced);
				}

				qtyInStockUom = Quantitys.add(conversionCtx,
						qtyInStockUom,
						Quantitys.of(ila.getQtyInvoiced(), stockUomId));

				final I_C_InvoiceLine invoiceLineRecord = rawData
						.getInvoiceLineId2InvoiceLineRecord()
						.get(ila.getC_InvoiceLine_ID());

				final I_C_Invoice invoiceRecord = rawData
						.getInvoiceId2InvoiceRecord()
						.get(invoiceLineRecord.getC_Invoice_ID());

				final Money currentNetAmount = Money.of(
						invoiceLineRecord.getLineNetAmt(),
						CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID()));
				netAmount = netAmount.add(currentNetAmount);
			}

			final InvoicedData invoicedData = InvoicedData.builder()
					.qtys(StockQtyAndUOMQty.builder()
							.productId(productId)
							.stockQty(qtyInStockUom)
							.uomQty(qty)
							.build())
					.netAmount(netAmount)
					.build();
			result.put(invoiceCandidateId, invoicedData);
		}

		return result.build();
	}

	private static Quantity extractQtyInvoiced(final I_C_Invoice_Line_Alloc ila)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(ila.getC_UOM_ID());
		
		return uomId != null
				? Quantitys.of(ila.getQtyInvoicedInUOM(), uomId)
				: null;
	}

	private RawData loadRawData()
	{
		final List<I_C_Invoice_Line_Alloc> ilaRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateIds)
				.create()
				.list();

		final ImmutableListMultimap<Integer, I_C_Invoice_Line_Alloc> invoiceCandidateId2IlaRecords = Multimaps.index(ilaRecords, I_C_Invoice_Line_Alloc::getC_Invoice_Candidate_ID);

		final ImmutableList<Integer> invoiceLineRepoIds = ilaRecords.stream()
				.map(ilaRecord -> ilaRecord.getC_InvoiceLine_ID())
				.distinct()
				.collect(ImmutableList.toImmutableList());

		final List<I_C_InvoiceLine> invoiceLineRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_InvoiceLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_InvoiceLine.COLUMN_C_InvoiceLine_ID, invoiceLineRepoIds)
				.create()
				.list();

		final ImmutableMap<Integer, I_C_InvoiceLine> invoiceLineId2InvoiceLineRecord = Maps.uniqueIndex(invoiceLineRecords, I_C_InvoiceLine::getC_InvoiceLine_ID);

		final ImmutableList<Integer> invoiceRepoIds = invoiceLineRecords.stream()
				.map(I_C_InvoiceLine::getC_Invoice_ID)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		final List<I_C_Invoice> invoiceRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Invoice.COLUMN_C_Invoice_ID, invoiceRepoIds)
				.create()
				.list();

		final ImmutableMap<Integer, I_C_Invoice> invoiceId2InvoiceRecord = Maps.uniqueIndex(invoiceRecords, I_C_Invoice::getC_Invoice_ID);

		return new RawData(invoiceId2InvoiceRecord, invoiceLineId2InvoiceLineRecord, invoiceCandidateId2IlaRecords);
	}

	@Value
	private static class RawData
	{
		ImmutableMap<Integer, I_C_Invoice> invoiceId2InvoiceRecord;

		ImmutableMap<Integer, I_C_InvoiceLine> invoiceLineId2InvoiceLineRecord;

		ImmutableListMultimap<Integer, I_C_Invoice_Line_Alloc> invoiceCandidateId2IlaRecords;
	}
}
