package de.metas.invoicecandidate.internalbusinesslogic;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate.InvoiceCandidateBuilder;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.uom.UomIds;
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
		final UomId icUomId = UomIds.ofRecord(icRecord);

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

		// if the ordered qty is negated, then also negate the delivery data;
		// this is the case when we have ICs for material returns (both vendor and customer)
		final boolean negateDeliveryQtys = orderedData.getQty().signum() < 0;

		final DeliveredData deliveredData = DeliveredDataLoader.builder()
				.invoiceCandidateId(invoiceCandidateId)
				.soTrx(soTrx)
				.productId(productId)
				.icUomId(icUomId)
				.stockUomId(stockUomId)
				.deliveryQualityDiscount(qualityDiscountOverride)
				.negateQtys(negateDeliveryQtys)
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
			@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		if (invoiceCandidateRecord.getC_ILCandHandler_ID() > 0) // in unit tests there might be no handler; don't bother in those cases
		{
			// updating qty delivered; this part used to be in InvoiceCandInvalidupdater
			// 07814-IT2 only from now on we have the correct QtyDelivered
			// note that we need this data to be set before we attempt to compute the price, because the delivered qty and date of delivery might play a role.
			Services.get(IInvoiceCandidateHandlerBL.class).setDeliveredData(invoiceCandidateRecord);
		}

		final DeliveredData deliveredData = invoiceCandidate.getDeliveredData();
		if (invoiceCandidate.getSoTrx().isPurchase())
		{
			final ReceiptData receiptData = deliveredData.getReceiptData();

			final Percent qualityDiscountOverride = invoiceCandidate.getQualityDiscountOverride();
			invoiceCandidateRecord.setQualityDiscountPercent_Override(qualityDiscountOverride == null ? null : qualityDiscountOverride.toBigDecimal());

			invoiceCandidateRecord.setQtyWithIssues(receiptData.getQtysWithIssues().getStockQty().toBigDecimal());
			invoiceCandidateRecord.setQtyWithIssues_Effective(receiptData.computeQtysWithIssuesEffective(qualityDiscountOverride).getStockQty().toBigDecimal());

			// check if QualityDiscountPercent from the inout lines equals the effective quality-percent which we currently have
			final BigDecimal qualityDiscountPercentOld = invoiceCandidateRecord.getQualityDiscountPercent();
			final BigDecimal qualityDiscountPercentNew = receiptData.computeQualityDiscount().toBigDecimal();

			final boolean isQualityDiscountPercentChanged = qualityDiscountPercentOld.compareTo(qualityDiscountPercentNew) != 0;
			if (isQualityDiscountPercentChanged)
			{
				// so there was a change in the underlying inouts' qtysWithIssues => check if we need to set the InDispute-Flag back to true

				// update QualityDiscountPercent from the inout lines
				invoiceCandidateRecord.setQualityDiscountPercent(qualityDiscountPercentNew);

				// reset the qualityDiscountPercent_Override value, because it needs to be negotiated anew
				invoiceCandidateRecord.setQualityDiscountPercent_Override(null);

				if (qualityDiscountPercentNew.signum() > 0)
				{
					// the inOuts' indisputQqty changed and we (now) have effective qualityDiscountPercent > 0
					// set the IC to IsInDispute = true to make sure the qtywithissue-chage is dealt with
					invoiceCandidateRecord.setIsInDispute(true);
				}
			}
		}
		else
		{
			invoiceCandidateRecord.setQualityDiscountPercent_Override(null);
			invoiceCandidateRecord.setQtyWithIssues(null);
			invoiceCandidateRecord.setQtyWithIssues_Effective(null);
		}

		final ToInvoiceData toInvoiceData = invoiceCandidate.computeToInvoiceData();

		invoiceCandidateRecord.setQtyToInvoiceBeforeDiscount(toInvoiceData.getQtysRaw().getStockQty().toBigDecimal());
		invoiceCandidateRecord.setQtyToInvoice(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal());

		invoiceCandidateRecord.setQtyToInvoiceInUOM_Calc(toInvoiceData.getQtysCalc().getUOMQty().toBigDecimal());
		invoiceCandidateRecord.setQtyToInvoiceInUOM(toInvoiceData.getQtysEffective().getUOMQty().toBigDecimal());
	}
}
