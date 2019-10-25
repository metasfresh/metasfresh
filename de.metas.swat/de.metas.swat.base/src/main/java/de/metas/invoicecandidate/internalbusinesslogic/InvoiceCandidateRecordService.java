package de.metas.invoicecandidate.internalbusinesslogic;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate.InvoiceCandidateBuilder;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

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

@Service
public class InvoiceCandidateRecordService
{
	public InvoiceCandidate ofRecord(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final InvoiceCandidateBuilder result = InvoiceCandidate.builder();

		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final IProductBL productBL = Services.get(IProductBL.class);

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());
		final ProductId productId = ProductId.ofRepoId(icRecord.getM_Product_ID());
		final UomId stockUomId = productBL.getStockUOMId(productId);
		final UomId icUomId = UomId.ofRepoId(icRecord.getC_UOM_ID());

		// might be null if the IC was just created from an inout line
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(icRecord.getC_Currency_ID());

		final SOTrx soTrx = SOTrx.ofBoolean(icRecord.isSOTrx());

		result
				.id(invoiceCandidateId)
				.soTrx(soTrx)
				.uomId(icUomId)
				.productId(productId)
				.invoiceRule(invoiceCandBL.getInvoiceRule(icRecord));

		if (!isNull(icRecord, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override))
		{
			final BigDecimal qtyToInvoiceOverrideInStockUom =				//
					icRecord.getQtyToInvoice_Override()
							.subtract(icRecord.getQtyToInvoice_OverrideFulfilled());

			result.qtyToInvoiceOverrideInStockUom(qtyToInvoiceOverrideInStockUom);
		}

		// purchase specialities
		Optional<Percent> qualityDiscountOverride = Optional.empty();
		InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.fromRecordString(icRecord.getInvoicableQtyBasedOn());
		if (soTrx.isPurchase())
		{
			invoicableQtyBasedOn = InvoicableQtyBasedOn.NominalWeight; // for purchase candidates it's *always* nominal weight
			if (!isNull(icRecord, I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override))
			{
				qualityDiscountOverride = Optional.of(Percent.of(icRecord.getQualityDiscountPercent_Override()));
			}
		}
		result
				.qualityDiscountOverride(qualityDiscountOverride.orElse(null))
				.invoicableQtyBasedOn(invoicableQtyBasedOn);

		// load ordered, delivered and invoiced data
		final OrderedData orderedData = OrderedDataLoader.builder()
				.invoiceCandidateRecord(icRecord)
				.stockUomId(stockUomId)
				.build()
				.loadOrderedQtys();

		final DeliveredData deliveredData = DeliveredDataLoader.builder()
				.invoiceCandidateId(invoiceCandidateId)
				.soTrx(soTrx)
				.productId(productId)
				.icUomId(icUomId)
				.stockUomId(stockUomId)
				.deliveryQualityDiscount(qualityDiscountOverride)
				.negateQtys(orderedData.isNegative())
				.defaultQtyDelivered(StockQtyAndUOMQtys.create(icRecord.getQtyDelivered(), productId, icRecord.getQtyDeliveredInUOM(), icUomId))
				.build()
				.loadDeliveredQtys();

		final InvoicedData invoicedData;
		if (currencyId != null)
		{
			invoicedData = InvoicedDataLoader.builder()
					.icUomIds(ImmutableMap.of(invoiceCandidateId, icUomId))
					.stockUomIds(ImmutableMap.of(invoiceCandidateId, stockUomId))
					.productIds(ImmutableMap.of(invoiceCandidateId, productId))
					.currencyIds(ImmutableMap.of(invoiceCandidateId, currencyId))
					.invoiceCandidateIds(ImmutableSet.of(invoiceCandidateId))
					.build()
					.loadInvoicedData()
					.get(invoiceCandidateId);
		}
		else
		{
			invoicedData = null;
		}
		return result
				.orderedData(orderedData)
				.deliveredData(deliveredData)
				.invoicedData(invoicedData)
				.build();
	}

	public void updateRecord(
			@NonNull final InvoiceCandidate invoiceCandidate,
			@NonNull final I_C_Invoice_Candidate icRecord)
	{
		try
		{
			updateRecord0(invoiceCandidate, icRecord);
		}
		catch (final RuntimeException e)
		{
			// log, enrich info and rethrow
			Loggables.get().addLog("Caught {} updating icRecord={} from invoiceCandidate={}", e.getClass().getSimpleName(), icRecord, invoiceCandidate);
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("icRecord", icRecord)
					.setParameter("invoiceCandidate", invoiceCandidate);
		}
	}

	private void updateRecord0(
			@NonNull final InvoiceCandidate invoiceCandidate,
			@NonNull final I_C_Invoice_Candidate icRecord)
	{
		// (receipt) quality discount
		if (invoiceCandidate.getSoTrx().isPurchase())
		{
			final DeliveredData deliveredData = invoiceCandidate.getDeliveredData();
			final ReceiptData receiptData = deliveredData.getReceiptData();

			final Percent qualityDiscountOverride = invoiceCandidate.getQualityDiscountOverride();
			icRecord.setQualityDiscountPercent_Override(qualityDiscountOverride == null ? null : qualityDiscountOverride.toBigDecimal());

			icRecord.setQtyWithIssues(receiptData.getQtysWithIssues().getStockQty().toBigDecimal());
			icRecord.setQtyWithIssues_Effective(receiptData.computeQtysWithIssuesEffective(qualityDiscountOverride).getStockQty().toBigDecimal());

			// check if QualityDiscountPercent from the inout lines equals the effective quality-percent which we currently have
			final BigDecimal qualityDiscountPercentOld = icRecord.getQualityDiscountPercent();
			final BigDecimal qualityDiscountPercentNew = receiptData.computeQualityDiscount().toBigDecimal();

			final boolean isQualityDiscountPercentChanged = qualityDiscountPercentOld.compareTo(qualityDiscountPercentNew) != 0;
			if (isQualityDiscountPercentChanged)
			{
				// so there was a change in the underlying inouts' qtysWithIssues => check if we need to set the InDispute-Flag back to true

				// update QualityDiscountPercent from the inout lines
				icRecord.setQualityDiscountPercent(qualityDiscountPercentNew);

				// reset the qualityDiscountPercent_Override value, because it needs to be negotiated anew
				icRecord.setQualityDiscountPercent_Override(null);

				if (qualityDiscountPercentNew.signum() > 0)
				{
					// the inOuts' indisputQqty changed and we (now) have effective qualityDiscountPercent > 0
					// set the IC to IsInDispute = true to make sure the qtywithissue-chage is dealt with
					icRecord.setIsInDispute(true);
				}
			}
		}
		else
		{
			icRecord.setQualityDiscountPercent_Override(null);
			icRecord.setQtyWithIssues(null);
			icRecord.setQtyWithIssues_Effective(null);
		}

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		icRecord.setQtyToInvoiceBeforeDiscount(toInvoiceData.getQtysRaw().getStockQty().toBigDecimal());
		icRecord.setQtyToInvoice(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal());

		icRecord.setQtyToInvoiceInUOM_Calc(toInvoiceData.getQtysCalc().getUOMQtyNotNull().toBigDecimal());
		icRecord.setQtyToInvoiceInUOM(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal());
	}
}
