package de.metas.order;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.freighcost.FreightCost;
import de.metas.freighcost.FreightCostContext;
import de.metas.freighcost.FreightCostRule;
import de.metas.freighcost.FreightCostService;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.BPartnerOrderParamsRepository.BPartnerOrderParamsQuery;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
public class OrderFreightCostsService
{

	private static final Logger logger = LogManager.getLogger(OrderFreightCostsService.class);

	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	private final FreightCostService freightCostService;
	private final BPartnerOrderParamsRepository bPartnerOrderParamsRepository;

	public OrderFreightCostsService(
			@NonNull final FreightCostService freightCostService,
			@NonNull final BPartnerOrderParamsRepository bPartnerOrderParamsRepository)
	{
		this.freightCostService = freightCostService;
		this.bPartnerOrderParamsRepository = bPartnerOrderParamsRepository;
	}

	public void addFreightRateLineIfNeeded(final I_C_Order order)
	{

		final DeliveryViaRule deliveryViaRule = DeliveryViaRule.ofCode(order.getDeliveryViaRule());
		final boolean isFreightCostNeeded = !deliveryViaRule.equals(DeliveryViaRule.Pickup);

		if (!isFreightCostNeeded)
		{
			return;
		}

		final FreightCostRule freightCostRule = FreightCostRule.ofCode(order.getFreightCostRule());
		final boolean isCustomFreightCost = freightCostRule.isFixPrice()
				|| FreightCostRule.FlatShippingFee.equals(freightCostRule);
		if (!isCustomFreightCost)
		{
			return;
		}

		final Money freightAmt = computeFreightRate(order).orElse(null);

		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		if (hasFreightCostLine(orderId))
		{
			return;
		}

		final FreightCostContext freightCostContext = extractFreightCostContext(order);
		final FreightCost freightCost = freightCostService.findBestMatchingFreightCost(freightCostContext);

		final I_C_OrderLine freightRateOrderLine = orderLineBL.createOrderLine(order);
		orderLineBL.setProductId(
				freightRateOrderLine,
				freightCost.getFreightCostProductId(),
				true);  // setUomFromProduct

		freightRateOrderLine.setQtyEntered(BigDecimal.ONE);
		freightRateOrderLine.setQtyOrdered(BigDecimal.ONE);

		freightRateOrderLine.setIsManualPrice(true);
		freightRateOrderLine.setIsPriceEditable(false);

		final CurrencyId orderCurrencyId = CurrencyId.ofRepoId(order.getC_Currency_ID());

		final Money convertedFreightAmt = convertFreightRate(freightAmt, orderCurrencyId);

		freightRateOrderLine.setPriceEntered(convertedFreightAmt.toBigDecimal());
		freightRateOrderLine.setPriceActual(convertedFreightAmt.toBigDecimal());

		freightRateOrderLine.setIsManualDiscount(true);
		freightRateOrderLine.setDiscount(BigDecimal.ZERO);

		ordersRepo.save(freightRateOrderLine);
	}

	private Money convertFreightRate(@Nullable final Money freightAmt, @NonNull final CurrencyId orderCurrencyId)
	{
		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

		if (freightAmt == null)
		{
			return Money.zero(orderCurrencyId);
		}

		final CurrencyId freightCostCurrencyId = freightAmt.getCurrencyId();

		if (freightCostCurrencyId.equals(orderCurrencyId))
		{
			return freightAmt;
		}

		final BigDecimal freightAmtConverted = currencyBL.convert(
				freightAmt.toBigDecimal(),
				freightAmt.getCurrencyId(),
				orderCurrencyId,
				Env.getClientId(),
				Env.getOrgId());

		if (freightAmtConverted == null)
		{
			throw new AdempiereException("Please, add a conversion between the following currencies: " + freightCostCurrencyId + ", " + orderCurrencyId);
		}

		final Money convertedFreightAmt = Money.of(freightAmtConverted, orderCurrencyId);

		return convertedFreightAmt;

	}

	public boolean hasFreightCostLine(@NonNull final OrderId orderId)
	{
		for (final I_C_OrderLine orderLine : ordersRepo.retrieveOrderLines(orderId))
		{
			final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
			if (productId != null && freightCostService.isFreightCostProduct(productId))
			{
				return true;
			}
		}

		return false;
	}

	private FreightCostContext extractFreightCostContext(@NonNull final I_C_Order orderRecord)
	{
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final BPartnerId shipToBPartnerId = BPartnerId.ofRepoIdOrNull(orderRecord.getC_BPartner_ID());

		final BPartnerLocationId shipToBPLocationId = BPartnerLocationId.ofRepoIdOrNull(shipToBPartnerId, orderRecord.getC_BPartner_Location_ID());
		final CountryId shipToCountryId = shipToBPLocationId != null
				? bpartnerBL.getBPartnerLocationCountryId(shipToBPLocationId)
				: null;

		final FreightCostRule freightCostRule = FreightCostRule.ofNullableCodeOr(orderRecord.getFreightCostRule(), FreightCostRule.FreightIncluded);

		final ShipperId shipperId = coalesceSuppliers(
				() -> ShipperId.ofRepoIdOrNull(orderRecord.getM_Shipper_ID()),
				() -> retrievePartnerShipperId(orderRecord));

		return FreightCostContext.builder()
				.shipFromOrgId(OrgId.ofRepoId(orderRecord.getC_Order_ID()))
				.shipToBPartnerId(shipToBPartnerId)
				.shipToCountryId(shipToCountryId)
				.shipperId(shipperId)
				.date(TimeUtil.asLocalDate(orderRecord.getDateOrdered()))
				.freightCostRule(freightCostRule)
				.deliveryViaRule(DeliveryViaRule.ofNullableCodeOr(orderRecord.getDeliveryViaRule(), DeliveryViaRule.Pickup))
				.manualFreightAmt(freightCostRule.isFixPrice() ? extractManualFreightAmtOrNull(orderRecord) : null)
				.build();
	}

	private ShipperId retrievePartnerShipperId(@NonNull final I_C_Order orderRecord)
	{
		final BPartnerId shipBPartnerId = BPartnerId.ofRepoIdOrNull(orderRecord.getC_BPartner_ID());
		final BPartnerId billBPartnerId = BPartnerId.ofRepoIdOrNull(firstGreaterThanZero(
				orderRecord.getBill_BPartner_ID(),
				orderRecord.getC_BPartner_ID()));

		final BPartnerOrderParamsQuery query = BPartnerOrderParamsQuery.builder()
				.shipBPartnerId(shipBPartnerId)
				.billBPartnerId(billBPartnerId)
				.soTrx(SOTrx.ofBoolean(orderRecord.isSOTrx()))
				.build();

		final BPartnerOrderParams params = bPartnerOrderParamsRepository.getBy(query);
		return params.getShipperId().orElse(null);
	}

	private Money extractManualFreightAmtOrNull(final I_C_Order order)
	{
		final BigDecimal freightAmtBD = order.getFreightAmt();

		// Consider ZERO as not provided.
		// If user really wants to set ZERO freight cost, he/she shall use FreightIncluded rule.
		if (freightAmtBD.signum() == 0)
		{
			return null;
		}

		return Money.of(freightAmtBD, CurrencyId.ofRepoId(order.getC_Currency_ID()));
	}

	public void updateFreightAmt(@NonNull final I_C_Order order)
	{
		final Money freightRate = computeFreightRate(order).orElse(null);
		order.setFreightAmt(freightRate != null ? freightRate.toBigDecimal() : BigDecimal.ZERO);
	}

	private Optional<Money> computeFreightRate(final I_C_Order salesOrder)
	{
		if (!salesOrder.isSOTrx())
		{
			return Optional.empty();
		}
		if (salesOrder.getC_BPartner_ID() <= 0)
		{
			return Optional.empty(); // order is not yet completely set up
		}

		final FreightCostContext freightCostContext = extractFreightCostContext(salesOrder);
		if (freightCostService.checkIfFree(freightCostContext))
		{
			return Optional.empty();
		}

		final FreightCostRule freightCostRule = freightCostContext.getFreightCostRule();
		if (freightCostRule == FreightCostRule.FlatShippingFee)
		{
			final OrderId orderId = OrderId.ofRepoIdOrNull(salesOrder.getC_Order_ID());
			final Money shipmentValueAmt = orderId != null
					? computeShipmentValueAmt(orderId).orElse(null)
					: null;
			if (shipmentValueAmt == null)
			{
				return Optional.empty();
			}

			final FreightCost freightCost = freightCostService.findBestMatchingFreightCost(freightCostContext);
			final Money freightRate = freightCost.getFreightRate(
					freightCostContext.getShipperId(),
					freightCostContext.getShipToCountryId(),
					freightCostContext.getDate(),
					shipmentValueAmt);
			return Optional.of(freightRate);
		}
		else if (freightCostRule == FreightCostRule.FixPrice)
		{
			// get the 'freightcost' product and return its price
			final FreightCost freightCost = freightCostService.findBestMatchingFreightCost(freightCostContext);

			final Money freightAmt = freightCostContext.getManualFreightAmt();
			if (freightAmt != null)
			{
				return Optional.of(freightAmt);
			}

			final IEditablePricingContext pricingContext = pricingBL.createInitialContext(
					freightCost.getOrgId().getRepoId(),
					freightCost.getFreightCostProductId().getRepoId(),
					freightCostContext.getShipToBPartnerId().getRepoId(),
					0,
					BigDecimal.ONE,
					SOTrx.SALES.toBoolean());

			final CountryId countryId = getCountryIdOrNull(salesOrder);

			pricingContext.setCountryId(countryId);
			pricingContext.setFailIfNotCalculated();
			pricingContext.setPricingSystemId(PricingSystemId.ofRepoIdOrNull(salesOrder.getM_PricingSystem_ID()));
			pricingContext.setPriceListId(PriceListId.ofRepoIdOrNull(salesOrder.getM_PriceList_ID()));
			pricingContext.setCurrencyId(CurrencyId.ofRepoId(salesOrder.getC_Currency_ID()));

			final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);
			final Money freightRate = Money.of(pricingResult.getPriceStd(), pricingResult.getCurrencyId());
			return Optional.of(freightRate);
		}
		else
		{
			logger.debug("Freigt cost is not computed because of FreightCostRule={}", freightCostRule);
			return Optional.empty();
		}
	}

	private CountryId getCountryIdOrNull(@NonNull final I_C_Order salesOrder)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final int locationRecordId = salesOrder.getC_BPartner_Location_ID();

		if (locationRecordId <= 0)
		{
			return null;
		}

		final int bpartnerRecordId = salesOrder.getC_BPartner_ID();
		final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoId(bpartnerRecordId, locationRecordId);

		final CountryId countryId = bpartnerDAO.retrieveBPartnerLocationCountryId(bpLocationId);

		return countryId;
	}

	private Optional<Money> computeShipmentValueAmt(@NonNull final OrderId orderId)
	{
		Money shipmentValueAmt = null;
		for (final I_C_OrderLine orderLine : ordersRepo.retrieveOrderLines(orderId))
		{
			final BigDecimal qty = orderLine.getQtyOrdered();
			final BigDecimal priceActual = orderLine.getPriceActual();
			final CurrencyId currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());

			final Money lineValueAmt = Money.of(priceActual.multiply(qty), currencyId);

			shipmentValueAmt = shipmentValueAmt != null
					? shipmentValueAmt.add(lineValueAmt)
					: lineValueAmt;
		}

		return Optional.ofNullable(shipmentValueAmt);
	}

	public boolean isFreightCostOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		return productId != null && freightCostService.isFreightCostProduct(productId);
	}

}
