package de.metas.order;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.OrgId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProductPricing;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.freighcost.FreightCost;
import de.metas.freighcost.FreightCostContext;
import de.metas.freighcost.FreightCostRule;
import de.metas.freighcost.FreightCostService;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
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
	private static final String MSG_NO_FREIGHT_COST_DETAIL = "freightCost.Order.noFreightCostDetail";

	private static final Logger logger = LogManager.getLogger(OrderFreightCostsService.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final FreightCostService freightCostService;

	public OrderFreightCostsService(
			@NonNull final FreightCostService freightCostService)
	{
		this.freightCostService = freightCostService;
	}

	public void evalAddFreightCostLine(final I_C_Order order)
	{
		final FreightCostRule freightCostRule = FreightCostRule.ofCode(order.getFreightCostRule());
		final boolean isCustomFreightCost = FreightCostRule.FixPrice.equals(freightCostRule)
				|| FreightCostRule.FreightIncluded.equals(freightCostRule);

		final BigDecimal freightAmt = order.getFreightAmt();
		if (freightAmt.signum() != 0)
		{
			if (isPrepayOrder(order) || isCustomFreightCost)
			{
				if (!hasFreightCostLine(order))
				{
					final FreightCostContext freightCostContext = extractFreightCostContext(order);
					final FreightCost freightCost = freightCostService.retrieveFor(freightCostContext);
					final MOrderLine newOl = new MOrderLine(order);
					newOl.setM_Product_ID(freightCost.getFreightCostProductId().getRepoId());

					final MProductPricing pp = new MProductPricing(
							freightCost.getFreightCostProductId().getRepoId(),
							order.getBill_Location_ID(),
							BigDecimal.ONE,
							order.isSOTrx());
					pp.setM_PriceList_ID(order.getM_PriceList_ID());
					final BigDecimal priceList = pp.getPriceList();
					newOl.setPriceList(priceList);
					InterfaceWrapperHelper.create(newOl, I_C_OrderLine.class).setPriceStd(pp.getPriceStd());

					newOl.setQty(BigDecimal.ONE);
					newOl.setPrice(freightAmt);
					newOl.setProcessed(true);
					newOl.saveEx();

					// reload order lines. otherwise the cached lines (without the new one) might be used
					order.getLines(true, null);
					order.reserveStock(null, ImmutableList.of(newOl));
					order.calculateTaxTotal();
				}
			}
		}
	}

	private boolean isPrepayOrder(final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoId(order.getC_DocType_ID());
		return Services.get(IDocTypeBL.class).isPrepay(docTypeId);
	}

	public boolean hasFreightCostLine(final I_C_Order order)
	{
		for (final I_C_OrderLine orderLine : ordersRepo.retrieveOrderLines(order))
		{
			final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());

			if (productId != null && freightCostService.isFreightCostProduct(productId))
			{
				return true;
			}
		}

		return false;
	}

	private FreightCostContext extractFreightCostContext(final I_C_Order order)
	{
		final BPartnerId shipToBPartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());

		final BPartnerLocationId shipToBPLocationId = BPartnerLocationId.ofRepoIdOrNull(shipToBPartnerId, order.getC_BPartner_Location_ID());
		final CountryId shipToCountryId = shipToBPLocationId != null
				? bpartnerBL.getBPartnerLocationCountryId(shipToBPLocationId)
				: null;

		return FreightCostContext.builder()
				.shipFromOrgId(OrgId.ofRepoId(order.getC_Order_ID()))
				.shipToBPartnerId(shipToBPartnerId)
				.shipToCountryId(shipToCountryId)
				.shipperId(ShipperId.ofRepoIdOrNull(order.getM_Shipper_ID()))
				.date(TimeUtil.asLocalDate(order.getDateOrdered()))
				.freightCostRule(FreightCostRule.ofNullableCodeOr(order.getFreightCostRule(), FreightCostRule.FreightIncluded))
				.deliveryViaRule(DeliveryViaRule.ofNullableCodeOr(order.getDeliveryViaRule(), DeliveryViaRule.Pickup))
				.build();
	}

	public void checkFreightCost(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			logger.debug("{} is not a sales order", order);
			return;
		}

		final FreightCostContext freightCostContext = extractFreightCostContext(order);
		if (freightCostContext.getShipToBPartnerId() == null
				|| freightCostContext.getShipToCountryId() == null
				|| freightCostContext.getShipperId() == null)
		{
			logger.debug("Can't check cause freight cost info is not yet complete for {}", order);
			return;
		}

		if (freightCostService.checkIfFree(freightCostContext))
		{
			logger.debug("No freight cost for {}", order);
			return;
		}

		freightCostService.retrieveFor(freightCostContext);
	}

	public void updateFreightAmt(@NonNull final I_C_Order order)
	{
		final BigDecimal freightCostAmt = computeFreightCostForOrder(order);
		order.setFreightAmt(freightCostAmt);
	}

	private BigDecimal computeFreightCostForOrder(final I_C_Order order)
	{
		final FreightCostContext freightCostContext = extractFreightCostContext(order);
		if (freightCostService.checkIfFree(freightCostContext))
		{
			return BigDecimal.ZERO;
		}

		final FreightCostRule freightCostRule = freightCostContext.getFreightCostRule();
		if (freightCostRule == FreightCostRule.Versandkostenpauschale)
		{
			// sum up the order's value and return the appropriate freight cost
			final BigDecimal freightCostBase = computeFreightCostBase(order);

			final FreightCost freightCost = freightCostService.retrieveFor(freightCostContext);
			return freightCost.getFreightAmt(
					freightCostContext.getShipperId(),
					freightCostContext.getShipToCountryId(),
					freightCostContext.getDate(),
					freightCostBase);
		}
		else if (freightCostRule == FreightCostRule.FixPrice)
		{
			// get the 'freightcost' product and return its price
			final FreightCost freightCost = freightCostService.retrieveFor(freightCostContext);

			final MProductPricing pp = new MProductPricing(
					freightCost.getFreightCostProductId().getRepoId(),
					order.getC_BPartner_ID(),
					BigDecimal.ONE,
					order.isSOTrx());
			pp.setM_PriceList_ID(order.getM_PriceList_ID());

			return pp.getPriceList();
		}
		else
		{
			logger.debug("Freigt cost is not computed because of FreightCostRule={}", freightCostRule);
			return BigDecimal.ZERO;
		}
	}

	private BigDecimal computeFreightCostBase(final I_C_Order order)
	{
		BigDecimal orderValue = BigDecimal.ZERO;
		for (final I_C_OrderLine poLine : ordersRepo.retrieveOrderLines(order))
		{
			final I_C_OrderLine ol = poLine;

			final BigDecimal qty = poLine.getQtyOrdered();
			orderValue = orderValue.add(ol.getPriceActual().multiply(qty));
		}
		return orderValue;
	}

	public boolean isFreightCostOrderLine(@NonNull final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		return productId != null && freightCostService.isFreightCostProduct(productId);
	}

}
