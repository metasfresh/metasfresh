package de.metas.invoicecandidate.internalbusinesslogic;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate.InvoiceCandidateBuilder;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;

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
	private static final Logger logger = LogManager.getLogger(InvoiceCandidateRecordService.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	public InvoiceCandidate ofRecord(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final InvoiceCandidateBuilder result = InvoiceCandidate.builder();

		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());
		final ProductId productId = ProductId.ofRepoId(icRecord.getM_Product_ID());
		final UomId stockUomId = productBL.getStockUOMId(productId);
		final boolean stocked = productBL.isStocked(productId);

		final UomId icUomId = UomId.ofRepoId(icRecord.getC_UOM_ID());

		// might be null if the IC was just created from an inout line
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(icRecord.getC_Currency_ID());

		final SOTrx soTrx = SOTrx.ofBoolean(icRecord.isSOTrx());

		result
				.id(invoiceCandidateId)
				.soTrx(soTrx)
				.uomId(icUomId)
				.product(new InvoiceCandidateProduct(productId, stocked))
				.invoiceRule(invoiceCandBL.getInvoiceRule(icRecord));

		if (!isNull(icRecord, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceInUOM_Override))
		{
			result.qtyToInvoiceOverrideInUom(icRecord.getQtyToInvoiceInUOM_Override());
		}
		if (!isNull(icRecord, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override))
		{
			final BigDecimal qtyToInvoiceOverrideInStockUom =                //
					icRecord.getQtyToInvoice_Override()
							.subtract(icRecord.getQtyToInvoice_OverrideFulfilled());

			result.qtyToInvoiceOverrideInStockUom(qtyToInvoiceOverrideInStockUom);
		}

		// purchase specialities
		Optional<Percent> qualityDiscountOverride = Optional.empty();
		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.ofNullableCodeOrNominal(icRecord.getInvoicableQtyBasedOn());
		if (soTrx.isPurchase())
		{
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
				.productBL(productBL)
				.build()
				.loadOrderedQtys();

		final DeliveredData deliveredData = DeliveredDataLoader.builder()
				.invoiceCandDAO(invoiceCandDAO)
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

		final Optional<PickedData> pickedData = loadPickedQtys(productId,
															   ShipmentScheduleId.ofRepoIdOrNull(icRecord.getM_ShipmentSchedule_ID()),
															   stockUomId,
															   icUomId);

		return result
				.orderedData(orderedData)
				.deliveredData(deliveredData)
				.invoicedData(invoicedData)
				.pickedData(pickedData.orElseGet(() -> {
					final StockQtyAndUOMQty defaultPickedData = StockQtyAndUOMQtys.create(icRecord.getQtyPicked(), productId, icRecord.getQtyPickedInUOM(), icUomId);
					return PickedData.of(defaultPickedData);
				}))
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
			final InvoicableQtyBasedOn invoicableQtyBasedOn = invoiceCandidate.getInvoicableQtyBasedOn();

			icRecord.setQualityDiscountPercent_Override(qualityDiscountOverride == null ? null : qualityDiscountOverride.toBigDecimal());

			icRecord.setQtyWithIssues(
					receiptData
							.getQtysWithIssues(invoicableQtyBasedOn)
							.getStockQty().toBigDecimal());

			icRecord.setQtyWithIssues_Effective(
					receiptData
							.computeQtysWithIssuesEffective(qualityDiscountOverride, invoicableQtyBasedOn)
							.getStockQty().toBigDecimal());

			// check if QualityDiscountPercent from the inout lines equals the effective quality-percent which we currently have
			final BigDecimal qualityDiscountPercentOld = icRecord.getQualityDiscountPercent();
			final BigDecimal qualityDiscountPercentNew = receiptData.computeQualityDiscount(invoicableQtyBasedOn).toBigDecimal();

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
					logger.debug("Set IsIndispute=true because QualityDiscountPercent={}", qualityDiscountPercentNew);
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

		if (icRecord.getQtyWithIssues_Effective().signum() != 0)
		{
			icRecord.setQtyToInvoiceBeforeDiscount(toInvoiceData.getQtysRaw().getStockQty().toBigDecimal());
		}
		else
		{
			icRecord.setQtyToInvoiceBeforeDiscount(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal());
		}
		icRecord.setQtyToInvoice(toInvoiceData.getQtysEffective().getStockQty().toBigDecimal());

		icRecord.setQtyToInvoiceInUOM_Calc(toInvoiceData.getQtysCalc().getUOMQtyNotNull().toBigDecimal());
		icRecord.setQtyToInvoiceInUOM(toInvoiceData.getQtysEffective().getUOMQtyNotNull().toBigDecimal());
	}

	@NonNull
	private Optional<PickedData> loadPickedQtys(
			@NonNull final ProductId productId,
			@Nullable final ShipmentScheduleId shipmentScheduleId,
			@NonNull final UomId stockUomId,
			@NonNull final UomId icUomId)
	{
		if (shipmentScheduleId == null)
		{
			return Optional.empty();
		}

		final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulePA.getById(shipmentScheduleId);

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);

		if (Check.isEmpty(qtyPickedRecords))
		{
			return Optional.empty();
		}

		final Quantity qtyPickedStockUOM = qtyPickedRecords
				.stream()
				.map(I_M_ShipmentSchedule_QtyPicked::getQtyPicked)
				.reduce(BigDecimal::add)
				.map(qtyPickedStockUOMSum -> Quantitys.create(qtyPickedStockUOMSum, stockUomId))
				.orElseGet(() -> Quantitys.create(BigDecimal.ZERO, stockUomId));

		final Quantity qtyPickedInUOM = Quantitys.create(qtyPickedStockUOM, UOMConversionContext.of(productId), icUomId);

		final Quantity qtyPickedCatch = qtyPickedRecords
				.stream()
				.filter(qtyPickedRecord -> qtyPickedRecord.getQtyDeliveredCatch() != null && qtyPickedRecord.getCatch_UOM_ID() > 0)
				.map(qtyPickedRecord -> Quantitys.create(qtyPickedRecord.getQtyDeliveredCatch(), UomId.ofRepoId(qtyPickedRecord.getCatch_UOM_ID())))
				.reduce(Quantity::add)
				.filter(qtyPickedCatchSum -> qtyPickedCatchSum.toBigDecimal().signum() != 0)
				.orElse(null);

		return Optional.of(PickedData.builder()
								   .productId(productId)
								   .qtyPicked(qtyPickedStockUOM)
								   .qtyPickedInUOM(qtyPickedInUOM)
								   .qtyCatch(qtyPickedCatch)
								   .build());
	}
}
