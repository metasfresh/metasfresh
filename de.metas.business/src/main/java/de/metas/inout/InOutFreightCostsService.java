package de.metas.inout;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.adempiere.service.OrgId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.freighcost.FreightCost;
import de.metas.freighcost.FreightCostContext;
import de.metas.freighcost.FreightCostRule;
import de.metas.freighcost.FreightCostService;
import de.metas.freighcost.spi.IFreightCostFreeEvaluator;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
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
public class InOutFreightCostsService
{
	private static final Logger logger = LogManager.getLogger(InOutFreightCostsService.class);
	private final IInOutDAO inoutsRepo = Services.get(IInOutDAO.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

	private final FreightCostService freightCostService;
	private final ImmutableList<IFreightCostFreeEvaluator> freightCostFreeEvaluators;

	public InOutFreightCostsService(
			@NonNull final FreightCostService freightCostService,
			@NonNull final Optional<List<IFreightCostFreeEvaluator>> freightCostFreeEvaluators)
	{
		this.freightCostService = freightCostService;
		this.freightCostFreeEvaluators = freightCostFreeEvaluators.isPresent()
				? ImmutableList.copyOf(freightCostFreeEvaluators.get())
				: ImmutableList.of();
	}

	private FreightCostContext extractFreightCostContext(final I_M_InOut shipment)
	{
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final BPartnerId shipToBPartnerId = BPartnerId.ofRepoIdOrNull(shipment.getC_BPartner_ID());

		final BPartnerLocationId shipToBPLocationId = BPartnerLocationId.ofRepoIdOrNull(shipToBPartnerId, shipment.getC_BPartner_Location_ID());
		final CountryId shipToCountryId = shipToBPLocationId != null
				? bpartnerBL.getBPartnerLocationCountryId(shipToBPLocationId)
				: null;

		return FreightCostContext.builder()
				.shipFromOrgId(OrgId.ofRepoId(shipment.getC_Order_ID()))
				.shipToBPartnerId(shipToBPartnerId)
				.shipToCountryId(shipToCountryId)
				.shipperId(ShipperId.ofRepoIdOrNull(shipment.getM_Shipper_ID()))
				.date(TimeUtil.asLocalDate(shipment.getMovementDate()))
				.freightCostRule(FreightCostRule.ofNullableCodeOr(shipment.getFreightCostRule(), FreightCostRule.FreightIncluded))
				.deliveryViaRule(DeliveryViaRule.ofNullableCodeOr(shipment.getDeliveryViaRule(), DeliveryViaRule.Pickup))
				.build();
	}

	public boolean hasFreightCostLine(final I_M_InOut inout)
	{
		for (final I_M_InOutLine inoutLine : inoutsRepo.retrieveLines(inout))
		{
			final ProductId productId = ProductId.ofRepoIdOrNull(inoutLine.getM_Product_ID());

			if (productId != null && freightCostService.isFreightCostProduct(productId))
			{
				return true;
			}
		}

		return false;
	}

	public Optional<Money> computeFreightRate(@NonNull final InOutId inoutId)
	{
		final I_M_InOut inOut = inoutsRepo.getById(inoutId);
		final FreightCostContext freightCostContext = extractFreightCostContext(inOut);

		if (freightCostService.checkIfFree(freightCostContext))
		{
			return Optional.empty();
		}

		final Money shipmentValueAmt = computeShipmentValueAmount(inOut).orElse(null);
		if (shipmentValueAmt == null)
		{
			return Optional.empty();
		}

		final FreightCost freightCost = freightCostService.retrieveFor(freightCostContext);
		final Money freightRate = freightCost.getFreightRate(
				freightCostContext.getShipperId(),
				freightCostContext.getShipToCountryId(),
				freightCostContext.getDate(),
				shipmentValueAmt);
		return Optional.of(freightRate);
	}

	private Optional<Money> computeShipmentValueAmount(@NonNull final I_M_InOut inout)
	{
		boolean atLeastOneIsFree = false;
		Money shipmentValueAmt = null;
		for (final I_M_InOutLine inoutLine : inoutsRepo.retrieveLines(inout))
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inoutLine.getC_OrderLine_ID());
			if (orderLineId == null)
			{
				return Optional.empty();
			}

			final I_C_OrderLine orderLine = ordersRepo.getOrderLineById(orderLineId);
			final OrderId orderId = OrderId.ofRepoId(orderLine.getC_Order_ID());
			final I_C_Order order = ordersRepo.getById(orderId);
			final FreightCostRule freightCostRule = FreightCostRule.ofCode(order.getFreightCostRule());

			if (FreightCostRule.FreightIncluded.equals(freightCostRule))
			{
				logger.debug("{} is free because it belongs to {} which has freight cost rule 'frieght included' ({})",
						inoutLine, order, FreightCostRule.FreightIncluded);

				atLeastOneIsFree = true;
				break;
			}
			else if (FreightCostRule.FlatShippingFee.equals(freightCostRule))
			{
				if (isFreightCostFree(inoutLine))
				{
					atLeastOneIsFree = true;
					break;
				}

				final CurrencyId currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
				final BigDecimal priceActual = orderLine.getPriceActual();
				final BigDecimal qty = inoutLine.getMovementQty();
				final Money lineValueAmt = Money.of(priceActual.multiply(qty), currencyId);
				shipmentValueAmt = shipmentValueAmt != null
						? shipmentValueAmt.add(lineValueAmt)
						: lineValueAmt;
			}
		}

		if (atLeastOneIsFree)
		{
			logger.debug("At least one shipment line is free for M_InOut_ID {}", inout);
			return Optional.empty();
		}
		else
		{
			return Optional.ofNullable(shipmentValueAmt);
		}

	}

	private boolean isFreightCostFree(final I_M_InOutLine inoutLine)
	{
		for (final IFreightCostFreeEvaluator freightCostFreeEvaluator : freightCostFreeEvaluators)
		{
			if (freightCostFreeEvaluator.isFreightCostFree(inoutLine))
			{
				logger.debug("{} is free because {} sais so", inoutLine, freightCostFreeEvaluator);
				return true;
			}
		}

		return false;
	}
}
