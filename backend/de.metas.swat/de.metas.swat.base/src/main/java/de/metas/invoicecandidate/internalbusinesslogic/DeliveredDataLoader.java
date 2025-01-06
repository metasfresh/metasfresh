package de.metas.invoicecandidate.internalbusinesslogic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_M_InOut;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
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

@Value
public class DeliveredDataLoader
{
	private static final Logger logger = LogManager.getLogger(DeliveredDataLoader.class);

	IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	UomId stockUomId;

	UomId icUomId;

	ProductId productId;

	InvoiceCandidateId invoiceCandidateId;

	SOTrx soTrx;

	Boolean negateQtys;

	/**
	 * always empty, if soTrx; sometimes set if poTrx
	 */
	Optional<Percent> deliveryQualityDiscount;

	/**
	 * This can be set from the {@code C_Invoice_Candidate}'s current qtyDelivered and
	 * will be used in case there are no assigned inout lines.
	 * <p>
	 * Background:
	 * <li>in some business cases we may have a QtyDelivered but no receipt lines at all (like commission settlement ICs).
	 * <li>in these cases, {@link IInvoiceCandidateHandler#setDeliveredData(de.metas.invoicecandidate.model.I_C_Invoice_Candidate)} might delivered quantities that are not related to inout lines.
	 * <li>these quantities need to end up in the IC's "deliveredData".
	 */
	@NonNull
	StockQtyAndUOMQty defaultQtyDelivered;

	@lombok.Builder
	private DeliveredDataLoader(
			@NonNull final UomId stockUomId,
			@NonNull final UomId icUomId,
			@NonNull final ProductId productId,
			@Nullable final InvoiceCandidateId invoiceCandidateId,
			@NonNull final SOTrx soTrx,
			@NonNull final Boolean negateQtys,
			@NonNull final Optional<Percent> deliveryQualityDiscount,
			@Nullable final StockQtyAndUOMQty defaultQtyDelivered)
	{
		this.stockUomId = stockUomId;
		this.icUomId = icUomId;
		this.productId = productId;
		this.invoiceCandidateId = invoiceCandidateId;
		this.soTrx = soTrx;
		this.negateQtys = negateQtys;
		this.deliveryQualityDiscount = deliveryQualityDiscount;
		this.defaultQtyDelivered = coalesceNotNull(defaultQtyDelivered, StockQtyAndUOMQtys.createZero(productId, icUomId));
	}

	public DeliveredData loadDeliveredQtys()
	{
		final DeliveredData.DeliveredDataBuilder result = DeliveredData.builder();

		final List<I_C_InvoiceCandidate_InOutLine> icIolAssociationRecords = loadInvoiceCandidateInOutLines();
		if (soTrx.isPurchase())
		{
			result.receiptData(loadReceiptQualityData(icIolAssociationRecords));
		}
		if (soTrx.isSales())
		{
			result.shipmentData(loadShipmentData(icIolAssociationRecords));
		}
		return result.build();
	}

	private List<I_C_InvoiceCandidate_InOutLine> loadInvoiceCandidateInOutLines()
	{
		final List<I_C_InvoiceCandidate_InOutLine> icIolAssociationRecords;
		if (invoiceCandidateId == null)
		{
			icIolAssociationRecords = ImmutableList.of();
		}
		else
		{
			icIolAssociationRecords = invoiceCandDAO.retrieveICIOLAssociationsFor(invoiceCandidateId);
		}
		return icIolAssociationRecords;
	}

	private ShipmentData loadShipmentData(@NonNull final List<I_C_InvoiceCandidate_InOutLine> icIolAssociationRecords)
	{
		final ImmutableList<DeliveredQtyItem> deliveredQtyItems = loadDeliveredQtyItems(icIolAssociationRecords);

		final ShipmentData.ShipmentDataBuilder result = ShipmentData.builder()
				.productId(productId)
				.deliveredQtyItems(deliveredQtyItems);

		if (deliveredQtyItems.isEmpty())
		{
			return result
					.qtyInStockUom(defaultQtyDelivered.getStockQty())
					.qtyNominal(defaultQtyDelivered.getUOMQtyNotNull())
					.build();
		}

		Quantity qtyInStockUom = Quantitys.zero(stockUomId);
		Quantity qtyNominal = Quantitys.zero(icUomId);
		Quantity qtyCatch = Quantitys.zero(icUomId);

		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);

		final ArrayList<DeliveredQtyItem> deliveredQtyItemsWithCatch = new ArrayList<>();
		final ArrayList<DeliveredQtyItem> deliveredQtyItemsWithoutCatch = new ArrayList<>();
		for (final DeliveredQtyItem deliveredQtyItem : deliveredQtyItems)
		{
			if (!deliveredQtyItem.isCompletedOrClosed())
			{
				continue; // we didn't want to fallback to defaultQtyDelivered, even if all the shipped items are reversed. In that case we want to arrive at zero.
			}
			qtyInStockUom = Quantitys.add(conversionCtx,
										  qtyInStockUom,
										  deliveredQtyItem.getQtyInStockUom());

			qtyNominal = Quantitys.add(conversionCtx,
									   qtyNominal,
									   coalesceNotNull(deliveredQtyItem.getQtyOverride(), deliveredQtyItem.getQtyNominal()));

			final Quantity qtyCatchEffective = coalesce(
					deliveredQtyItem.getQtyOverride(),
					deliveredQtyItem.getQtyCatch());
			if (qtyCatchEffective == null)
			{
				deliveredQtyItemsWithoutCatch.add(deliveredQtyItem);
			}
			else
			{
				deliveredQtyItemsWithCatch.add(deliveredQtyItem);
				qtyCatch = Quantitys.add(conversionCtx,
										 qtyCatch,
										 qtyCatchEffective);
			}
		}

		result
				.qtyInStockUom(qtyInStockUom)
				.qtyNominal(qtyNominal);
		if (!deliveredQtyItemsWithCatch.isEmpty())
		{
			result.qtyCatch(qtyCatch);
		}
		return result.build();
	}

	private ReceiptData loadReceiptQualityData(@NonNull final List<I_C_InvoiceCandidate_InOutLine> icIolAssociationRecords)
	{
		if (icIolAssociationRecords.isEmpty())
		{
			return ReceiptData.builder()
					.productId(productId)
					.qtyTotalInStockUom(defaultQtyDelivered.getStockQty())
					.qtyTotalNominal(defaultQtyDelivered.getUOMQtyNotNull())
					.qtyWithIssuesInStockUom(Quantitys.zero(productId))
					.qtyWithIssuesNominal(Quantitys.zero(icUomId))
					.build();
		}

		final ImmutableList<DeliveredQtyItem> shippedQtyItems = loadDeliveredQtyItems(icIolAssociationRecords);

		Quantity qtyTotalInStockUom = Quantitys.zero(stockUomId);
		Quantity qtyTotalNominal = Quantitys.zero(icUomId);
		Quantity qtyTotalCatch = Quantitys.zero(icUomId);

		Quantity qtyWithIssuesInStockUom = Quantitys.zero(stockUomId);
		Quantity qtyWithIssuesNominal = Quantitys.zero(icUomId);
		Quantity qtyWithIssuesCatch = Quantitys.zero(icUomId);

		final ArrayList<DeliveredQtyItem> deliveredQtyItemsWithCatch = new ArrayList<>();
		final ArrayList<DeliveredQtyItem> deliveredQtyItemsWithoutCatch = new ArrayList<>();
		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);

		for (final DeliveredQtyItem deliveredQtyItem : shippedQtyItems)
		{
			if (!deliveredQtyItem.isCompletedOrClosed())
			{
				continue; // we didn't want to fallback to defaultQtyDelivered, even if all the shipped items are reversed. In that case we want to arrive at zero.
			}

			final Quantity currentQtyInStockUom = deliveredQtyItem.getQtyInStockUom();
			final Quantity currentQtyNominal = coalesce(deliveredQtyItem.getQtyOverride(), deliveredQtyItem.getQtyNominal());
			final Quantity currentQtyCatch = coalesce(deliveredQtyItem.getQtyOverride(), deliveredQtyItem.getQtyCatch());

			qtyTotalInStockUom = Quantitys.add(conversionCtx, qtyTotalInStockUom, currentQtyInStockUom);
			qtyTotalNominal = Quantitys.add(conversionCtx, qtyTotalNominal, currentQtyNominal);

			if (currentQtyCatch != null)
			{
				deliveredQtyItemsWithCatch.add(deliveredQtyItem);
				qtyTotalCatch = Quantitys.add(conversionCtx, qtyTotalCatch, currentQtyCatch);
			}
			else
			{
				deliveredQtyItemsWithoutCatch.add(deliveredQtyItem);
			}

			if (deliveredQtyItem.isInDispute())
			{
				qtyWithIssuesInStockUom = Quantitys.add(conversionCtx, qtyWithIssuesInStockUom, currentQtyInStockUom);
				qtyWithIssuesNominal = Quantitys.add(conversionCtx, qtyWithIssuesNominal, currentQtyNominal);
				if (currentQtyCatch != null)
				{
					qtyWithIssuesCatch = Quantitys.add(conversionCtx, qtyWithIssuesCatch, currentQtyCatch);
				}

			}
		}

		return ReceiptData.builder()
				.productId(productId)
				.qtyTotalInStockUom(qtyTotalInStockUom)
				.qtyTotalNominal(qtyTotalNominal)
				.qtyTotalCatch(qtyTotalCatch)
				.qtyWithIssuesInStockUom(qtyWithIssuesInStockUom)
				.qtyWithIssuesNominal(qtyWithIssuesNominal)
				.qtyWithIssuesCatch(qtyWithIssuesCatch)
				.build();
	}

	private ImmutableList<DeliveredQtyItem> loadDeliveredQtyItems(@NonNull final List<I_C_InvoiceCandidate_InOutLine> icIolAssociationRecords)
	{
		final Builder<DeliveredQtyItem> result = ImmutableList.builder();

		for (final I_C_InvoiceCandidate_InOutLine icIolAssociationRecord : icIolAssociationRecords)
		{
			final InOutLineId inoutLineId = InOutLineId.ofRepoIdOrNull(icIolAssociationRecord.getM_InOutLine_ID());

			if (inoutLineId == null)
			{
				continue;
			}

			final I_M_InOutLine inoutLine = inOutDAO.getLineByIdInTrx(inoutLineId, I_M_InOutLine.class);

			final I_M_InOut inOut = inOutDAO.getById(InOutId.ofRepoId(inoutLine.getM_InOut_ID()));

			final boolean inoutCompletedOrClosed = inOut.isActive() && DocStatus.ofCode(inOut.getDocStatus()).isCompletedOrClosed();

			final DeliveredQtyItem.DeliveredQtyItemBuilder deliveredQtyItem = DeliveredQtyItem.builder()
					.inDispute(inoutLine.isInDispute())
					.completedOrClosed(inoutCompletedOrClosed);

			final Quantity qtyInStockUom = Quantitys
					.of(
							icIolAssociationRecord.getQtyDelivered(),
							stockUomId)
					.negateIf(negateQtys);
			deliveredQtyItem.qtyInStockUom(qtyInStockUom);

			final UomId deliveryUomId = UomId.optionalOfRepoId(icIolAssociationRecord.getC_UOM_ID()).orElse(stockUomId);
			final Quantity qtyNominal = Quantitys
					.of(
							icIolAssociationRecord.getQtyDeliveredInUOM_Nominal(),
							deliveryUomId)
					.negateIf(negateQtys);
			deliveredQtyItem.qtyNominal(qtyNominal);

			if (!isNull(icIolAssociationRecord, I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDeliveredInUOM_Catch))
			{
				final Quantity qtyCatch = Quantitys
						.of(
								icIolAssociationRecord.getQtyDeliveredInUOM_Catch(),
								deliveryUomId)
						.negateIf(negateQtys);
				deliveredQtyItem.qtyCatch(qtyCatch);
			}

			if (!isNull(icIolAssociationRecord, I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDeliveredInUOM_Override))
			{
				final Quantity qtyOverride = Quantitys
						.of(
								icIolAssociationRecord.getQtyDeliveredInUOM_Override(),
								deliveryUomId)
						.negateIf(negateQtys);
				deliveredQtyItem.qtyOverride(qtyOverride);
			}
			result.add(deliveredQtyItem.build());
		}
		return result.build();
	}
}
