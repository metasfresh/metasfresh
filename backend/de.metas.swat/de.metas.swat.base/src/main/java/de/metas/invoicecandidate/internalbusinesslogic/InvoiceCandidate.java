package de.metas.invoicecandidate.internalbusinesslogic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate.ToInvoiceExclOverride.InvoicedQtys;
import de.metas.invoicecandidate.internalbusinesslogic.ToInvoiceData.ToInvoiceDataBuilder;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.order.InvoiceRule;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static java.math.BigDecimal.ZERO;

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

@Data
public class InvoiceCandidate
{
	private static final Logger logger = LogManager.getLogger(InvoiceCandidate.class);

	private final InvoiceCandidateId id;

	private final SOTrx soTrx;

	private final InvoiceCandidateProduct product;

	private final UomId uomId;

	private final UomId priceUomId;

	private final OrderedData orderedData;

	private final DeliveredData deliveredData;

	private final InvoicedData invoicedData;

	@Setter(AccessLevel.NONE)
	private InvoiceRule invoiceRule;

	@Setter(AccessLevel.NONE)
	private InvoicableQtyBasedOn invoicableQtyBasedOn;

	@Setter(AccessLevel.NONE)
	private BigDecimal qtyToInvoiceOverrideInStockUom;

	@Setter(AccessLevel.NONE)
	private Percent qualityDiscountOverride;

	@Builder(toBuilder = true)
	@JsonCreator
	private InvoiceCandidate(
			@JsonProperty("id") @NonNull final InvoiceCandidateId id,
			@JsonProperty("soTrx") @NonNull final SOTrx soTrx,
			@JsonProperty("product") @NonNull final InvoiceCandidateProduct product,
			@JsonProperty("uomId") @NonNull final UomId uomId,
			@JsonProperty("orderedData") @NonNull final OrderedData orderedData,
			@JsonProperty("deliveredData") @NonNull final DeliveredData deliveredData,
			@JsonProperty("invoicedData") @Nullable final InvoicedData invoicedData, // can be null if the IC is very new
			@JsonProperty("invoicableQtyBasedOn") @NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn,
			@JsonProperty("invoiceRule") @NonNull final InvoiceRule invoiceRule,
			@JsonProperty("priceUomId") @Nullable final UomId priceUomId,
			@JsonProperty("qualityDiscountOverride") @Nullable final Percent qualityDiscountOverride,
			@JsonProperty("qtyToInvoiceOverrideInStockUom") @Nullable final BigDecimal qtyToInvoiceOverrideInStockUom)
	{
		this.id = id;
		this.soTrx = soTrx;
		this.product = product;
		this.uomId = uomId;
		this.orderedData = orderedData;
		this.deliveredData = deliveredData;
		this.invoicedData = invoicedData;
		this.invoicableQtyBasedOn = invoicableQtyBasedOn;
		this.invoiceRule = invoiceRule;

		this.priceUomId = coalesce(priceUomId, uomId);

		this.qualityDiscountOverride = qualityDiscountOverride;
		this.qtyToInvoiceOverrideInStockUom = qtyToInvoiceOverrideInStockUom;

		validate();
	}

	/**
	 * This is more of a guard; InvoiceCandidateRepository should have done it this way.
	 */
	private void validate()
	{
		Check.assumeEquals(uomId, orderedData.getQty().getUomId(), "orderedData.qty needs to have this instance's UOM; this={}", this);

		if (soTrx.isSales())
		{
			final ShipmentData shippedData = Check.assumeNotNull(deliveredData.getShipmentData(), "If soTrx={}, then shipmentData may not be null", soTrx);
			Check.assumeEquals(uomId, shippedData.getQtyNominal().getUomId(), "deliveredData.qtyNominal needs to have this instance's UOM; this={}", this);
			if (shippedData.getQtyCatch() != null)
			{
				Check.assumeEquals(uomId, shippedData.getQtyCatch().getUomId(), "deliveredData.qtyCatch needs to have this instance's UOM; this={}", this);
			}

			Check.assumeNull(deliveredData.getReceiptData(), "If soTrx={}, then receiptData needs to be null", soTrx);
		}
		if (soTrx.isPurchase())
		{
			Check.assumeNull(deliveredData.getShipmentData(), "If soTrx={}, then shipmentData needs to be null", soTrx);
			Check.assumeNotNull(deliveredData.getReceiptData(), "If soTrx={}, then receiptData may not be null", soTrx);
		}
	}

	public InvoiceCandidate changeQtyBasedOn(@NonNull final InvoicableQtyBasedOn newValue)
	{
		this.invoicableQtyBasedOn = newValue;
		return this;
	}

	public InvoiceCandidate changeInvoiceRule(@NonNull final InvoiceRule newValue)
	{
		this.invoiceRule = newValue;
		return this;
	}

	public InvoiceCandidate changeQualityDiscountOverride(@NonNull final Percent qualityDiscountOverride)
	{
		this.qualityDiscountOverride = qualityDiscountOverride;
		return this;
	}

	public ToInvoiceData computeToInvoiceData()
	{
		final ToInvoiceExclOverride toInvoiceExclOverride = computeToInvoiceExclOverride();

		final StockQtyAndUOMQty toInvoiceExclOverrideCalc = toInvoiceExclOverride.getQtysCalc();

		final ToInvoiceDataBuilder result = ToInvoiceData.builder()
				.qtysRaw(toInvoiceExclOverride.getQtysRaw())
				.qtysCalc(toInvoiceExclOverrideCalc);

		final StockQtyAndUOMQty qtysEffective;

		if (qtyToInvoiceOverrideInStockUom == null)
		{
			qtysEffective = toInvoiceExclOverrideCalc;
		}
		else
		{
			final boolean overrideExceedsDelivered = qtyToInvoiceOverrideInStockUom.compareTo(toInvoiceExclOverrideCalc.getStockQty().toBigDecimal()) > 0;

			if (overrideExceedsDelivered)
			{
				logger.debug("qtyToInvoiceOverrideInStockUom={} is > deliveredQtysCalcInStockUom={}; -> going to use qtyToInvoiceOverride",
						qtyToInvoiceOverrideInStockUom, toInvoiceExclOverrideCalc.getStockQty().toBigDecimal());
				final StockQtyAndUOMQty qtysToInvoice = StockQtyAndUOMQtys.createWithUomQtyUsingConversion(qtyToInvoiceOverrideInStockUom, product.getId(), uomId);
				final Quantity qtyDelivered = toInvoiceExclOverrideCalc.getUOMQtyNotNull();

				final boolean deliveredInUomExceedsOverride = qtyDelivered
						.compareTo(qtysToInvoice.getUOMQtyNotNull()) > 0;
				if (deliveredInUomExceedsOverride)
				{
					logger.debug("qtyDeliveredInUom={} is > qtyToInvoiceInUom={}; -> going to use qtyDelivered instead of override, for the UOM-qty",
							qtyDelivered.toBigDecimal(), qtysToInvoice.getUOMQtyNotNull().toBigDecimal());

					final StockQtyAndUOMQty qtysToIvoiceWithAdjustedQty = qtysToInvoice.toBuilder().uomQty(qtyDelivered).build();
					qtysEffective = qtysToIvoiceWithAdjustedQty;
				}
				else
				{
					qtysEffective = qtysToInvoice;
				}
			}
			else if (InvoicableQtyBasedOn.NominalWeight.equals(invoicableQtyBasedOn))
			{
				logger.debug("qtyToInvoiceOverrideInStockUom={} is <= deliveredQtysCalcInStockUom={} and invoicableQtyBasedOn=NominalWeight; -> going to use qtyToInvoiceOverride",
						qtyToInvoiceOverrideInStockUom, toInvoiceExclOverrideCalc.getStockQty().toBigDecimal());
				final StockQtyAndUOMQty qtysToInvoice = StockQtyAndUOMQtys.createWithUomQtyUsingConversion(qtyToInvoiceOverrideInStockUom, product.getId(), uomId);
				qtysEffective = qtysToInvoice;
			}
			else if (InvoiceRule.Immediate.equals(invoiceRule))
			{
				logger.debug("qtyToInvoiceOverrideInStockUom={} is <= deliveredQtysCalcInStockUom={} and invoicableQtyBasedOn=CatchWeight and invoiceRule=Immediate; -> going to use qtyToInvoiceOverride",
						qtyToInvoiceOverrideInStockUom, toInvoiceExclOverrideCalc.getStockQty().toBigDecimal());
				final StockQtyAndUOMQty qtysToInvoice = StockQtyAndUOMQtys.createWithUomQtyUsingConversion(qtyToInvoiceOverrideInStockUom, product.getId(), uomId);
				qtysEffective = qtysToInvoice;
			}
			else
			{
				logger.debug("qtyToInvoiceOverrideInStockUom={} is <= deliveredQtysCalcInStockUom={} and invoicableQtyBasedOn=CatchWeight; -> going to sum up actually shipped stockQtys and their respective catchQtys",
						qtyToInvoiceOverrideInStockUom, toInvoiceExclOverrideCalc.getStockQty().toBigDecimal());

				StockQtyAndUOMQty qtysToInvoice = StockQtyAndUOMQtys.createZero(product.getId(), uomId);
				BigDecimal remainingQtyOverride = qtyToInvoiceOverrideInStockUom.setScale(12, RoundingMode.UNNECESSARY);

				if (deliveredData.getShipmentData() != null)
				{
					// there is shipment-data and qtyToInvoiceOverride <= qtyDelivered and it's catchWeight; -> we need to get the appropriate fraction
					final List<DeliveredQtyItem> deliveredQtyItems = deliveredData.getShipmentData().getDeliveredQtyItems();

					for (final DeliveredQtyItem deliveredQtyItem : deliveredQtyItems)
					{
						final Quantity itemQtyInStockUom = deliveredQtyItem.getQtyInStockUom();
						final Quantity itemUomQty = coalesce(
								deliveredQtyItem.getQtyOverride(),
								deliveredQtyItem.getQtyCatch(),
								deliveredQtyItem.getQtyNominal()/*can happen if the product is to be invoiced in catch weight, but no catch weight at all was entered by any means*/
						);

						final boolean allocateCompleteItem = remainingQtyOverride.compareTo(itemQtyInStockUom.toBigDecimal()) >= 0;
						if (allocateCompleteItem)
						{
							final StockQtyAndUOMQty augent = StockQtyAndUOMQty.builder()
									.productId(product.getId())
									.stockQty(itemQtyInStockUom)
									.uomQty(itemUomQty).build();
							qtysToInvoice = StockQtyAndUOMQtys.add(qtysToInvoice, augent);

							remainingQtyOverride = remainingQtyOverride.subtract(itemQtyInStockUom.toBigDecimal());
						}
						else
						{
							// allocate partial item

							final BigDecimal fraction = remainingQtyOverride.divide(itemQtyInStockUom.toBigDecimal(), RoundingMode.HALF_UP);
							final Quantity partialItemUomQty = itemUomQty.multiply(fraction);

							logger.debug("remainingQtyOverride={} is < itemQtyInStockUom={}; -> for this last item, we use rule-of-3 to get the partial catchQty={}",
									remainingQtyOverride, itemQtyInStockUom.toBigDecimal(), partialItemUomQty);

							final StockQtyAndUOMQty augent = StockQtyAndUOMQtys.create(
									remainingQtyOverride, product.getId(),
									partialItemUomQty.toBigDecimal(), partialItemUomQty.getUomId());
							qtysToInvoice = StockQtyAndUOMQtys.add(qtysToInvoice, augent);

							remainingQtyOverride = ZERO;

						}

						if (remainingQtyOverride.signum() <= 0)
						{
							break;
						}
					}
					logger.debug("Iterated over {} deliveredQtyItems; resulting qtysEffective={}", deliveredQtyItems.size(), qtysToInvoice);
				}
				qtysEffective = qtysToInvoice;

			}
		}

		result.qtysEffective(qtysEffective);

		final Quantity qtyInPriceUom = Quantitys.create(
				qtysEffective.getUOMQtyNotNull(),
				UOMConversionContext.of(product.getId()),
				priceUomId);

		return result
				.qtyInPriceUom(qtyInPriceUom)
				.build();

	}

	/**
	 * @return a result where already invoiced quantities are subtracted
	 */
	private ToInvoiceExclOverride computeToInvoiceExclOverride()
	{
		final ToInvoiceExclOverride qtyToInvoice;
		switch (invoiceRule)
		{
			case AfterDelivery:
			case OrderCompletelyDelivered:
			case CustomerScheduleAfterDelivery:
				if (product.isStocked())
				{
					qtyToInvoice = computeInvoicableQtysDelivered();
				}
				else
				{
					qtyToInvoice = computeDeliveredOrOrdered();
				}
				break;
			case Immediate:
				qtyToInvoice = computeDeliveredOrOrdered();
				break;
			case AfterOrderDelivered:
				qtyToInvoice = computeDeliveredOrOrderedIfOrderComplete();
				break;
			default:
				throw new AdempiereException("Unsupported invoiceRule=" + invoiceRule);
		}

		if (invoicedData != null)
		{
			// subtract the qty that was already invoiced
			return new ToInvoiceExclOverride(
					ToInvoiceExclOverride.InvoicedQtys.SUBTRACTED,
					qtyToInvoice.getQtysRaw().subtract(invoicedData.getQtys()),
					qtyToInvoice.getQtysCalc().subtract(invoicedData.getQtys()));
		}
		else
		{
			return new ToInvoiceExclOverride(
					ToInvoiceExclOverride.InvoicedQtys.SUBTRACTED,
					qtyToInvoice.getQtysRaw(),
					qtyToInvoice.getQtysCalc());
		}
	}

	private ToInvoiceExclOverride computeDeliveredOrOrderedIfOrderComplete()
	{
		if (!orderedData.isOrderFullyDelivered())
		{
			return new ToInvoiceExclOverride(InvoicedQtys.NOT_SUBTRACTED,
					StockQtyAndUOMQtys.createZero(product.getId(), uomId),
					StockQtyAndUOMQtys.createZero(product.getId(), uomId));
		}

		return computeDeliveredOrOrdered();
	}

	/**
	 * @return the "bigger" quantity of either the ordered or delivered quantity.
	 */
	private ToInvoiceExclOverride computeDeliveredOrOrdered()
	{
		final Quantity orderedInStockUom = orderedData.getQtyInStockUom();
		final boolean negativeQtyOrdered = orderedInStockUom.signum() <= 0;

		final Quantity qtyToInvoiceInStockUomRaw;
		final Quantity qtyToInvoiceRaw;
		final Quantity qtyToInvoiceInStockUomCalc;
		final Quantity qtyToInvoiceCalc;

		final ToInvoiceExclOverride invoicableQtyDelivered = computeInvoicableQtysDelivered();

		final Quantity qtyDeliveredRaw = invoicableQtyDelivered.getQtysRaw().getUOMQtyOpt().get();
		final Quantity qtyDeliveredCalc = invoicableQtyDelivered.getQtysCalc().getUOMQtyOpt().get();
		final Quantity qtyDeliveredInStockUomRaw = invoicableQtyDelivered.getQtysRaw().getStockQty();
		final Quantity qtyDeliveredInStockUomCalc = invoicableQtyDelivered.getQtysCalc().getStockQty();

		//
		// Case: we deal with a negative quantity ordered (e.g. returns, "reversals", manual invoice candidates with negative Qty)
		if (negativeQtyOrdered)
		{
			qtyToInvoiceRaw = orderedData.getQty().min(qtyDeliveredRaw);
			qtyToInvoiceInStockUomRaw = orderedInStockUom.min(qtyDeliveredInStockUomRaw);

			qtyToInvoiceCalc = orderedData.getQty().min(qtyDeliveredCalc);
			qtyToInvoiceInStockUomCalc = orderedInStockUom.min(qtyDeliveredInStockUomCalc);
		}
		//
		// Standard Case: we deal with positive quantity ordered
		else
		{
			qtyToInvoiceRaw = orderedData.getQty().max(qtyDeliveredRaw);
			qtyToInvoiceInStockUomRaw = orderedInStockUom.max(qtyDeliveredInStockUomRaw);

			qtyToInvoiceCalc = orderedData.getQty().max(qtyDeliveredCalc);
			qtyToInvoiceInStockUomCalc = orderedInStockUom.max(qtyDeliveredInStockUomCalc);
		}

		final StockQtyAndUOMQty qtysToInvoiceRaw = StockQtyAndUOMQty.builder()
				.productId(product.getId())
				.stockQty(qtyToInvoiceInStockUomRaw)
				.uomQty(qtyToInvoiceRaw)
				.build();
		final StockQtyAndUOMQty qtysToInvoiceCalc = StockQtyAndUOMQty.builder()
				.productId(product.getId())
				.stockQty(qtyToInvoiceInStockUomCalc)
				.uomQty(qtyToInvoiceCalc)
				.build();

		return new ToInvoiceExclOverride(
				ToInvoiceExclOverride.InvoicedQtys.NOT_SUBTRACTED,
				qtysToInvoiceRaw, qtysToInvoiceCalc);
	}

	private ToInvoiceExclOverride computeInvoicableQtysDelivered()
	{
		final StockQtyAndUOMQty qtysToInvoiceRaw;
		final StockQtyAndUOMQty qtysToInvoiceCalc;

		if (soTrx.isSales())
		{
			qtysToInvoiceRaw = deliveredData.getShipmentData().computeInvoicableQtyDelivered(invoicableQtyBasedOn);
			qtysToInvoiceCalc = qtysToInvoiceRaw;
		}
		else
		{
			qtysToInvoiceRaw = deliveredData.getReceiptData().getQtysTotal(invoicableQtyBasedOn);
			qtysToInvoiceCalc = deliveredData.getReceiptData().computeInvoicableQtyDelivered(qualityDiscountOverride, invoicableQtyBasedOn);
		}
		logger.debug("IsSales={} -> return delivered quantity={}", soTrx.isSales(), qtysToInvoiceCalc);
		return new ToInvoiceExclOverride(
				ToInvoiceExclOverride.InvoicedQtys.NOT_SUBTRACTED,
				qtysToInvoiceRaw, qtysToInvoiceCalc);
	}

	public StockQtyAndUOMQty computeQtysDelivered()
	{
		final StockQtyAndUOMQty qtysDelivered;

		if (soTrx.isSales())
		{
			qtysDelivered = deliveredData.getShipmentData().computeInvoicableQtyDelivered(invoicableQtyBasedOn);
		}
		else
		{
			qtysDelivered = deliveredData.getReceiptData().getQtysTotal(invoicableQtyBasedOn);
		}
		return qtysDelivered;
	}

	@lombok.Value
	public static class ToInvoiceExclOverride
	{
		enum InvoicedQtys
		{
			SUBTRACTED, NOT_SUBTRACTED
		}

		InvoicedQtys invoicedQtys;

		/**
		 * Excluding possible receipt quantities with quality issues
		 */
		StockQtyAndUOMQty qtysRaw;

		/**
		 * Computed including possible receipt quality issues, not overridden by a possible qtyToInvoice override.
		 */
		StockQtyAndUOMQty qtysCalc;

		private ToInvoiceExclOverride(
				@NonNull final InvoicedQtys invoicedQtys,
				@NonNull final StockQtyAndUOMQty qtysRaw,
				@NonNull final StockQtyAndUOMQty qtysCalc)
		{
			this.qtysRaw = qtysRaw;
			this.qtysCalc = qtysCalc;
			this.invoicedQtys = invoicedQtys;

			StockQtyAndUOMQtys.assumeCommonProductAndUom(qtysRaw, qtysCalc);
		}
	}
}
