package de.metas.order.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.X_C_OrderLine;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.limit.PriceLimitRuleContext;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

class OrderLinePriceCalculator
{
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);

	private final OrderLineBL orderLineBL;
	private final OrderLinePriceUpdateRequest request;

	@Builder
	private OrderLinePriceCalculator(
			@NonNull final OrderLinePriceUpdateRequest request,
			@NonNull final OrderLineBL orderLineBL)
	{
		this.orderLineBL = orderLineBL;
		this.request = request;

	}

	public void updateOrderLine()
	{
		final I_C_OrderLine orderLine = request.getOrderLine();

		// Product was not set yet. There is no point to calculate the prices
		if (orderLine.getM_Product_ID() <= 0)
		{
			return;
		}

		//
		// Calculate Pricing Result
		final IEditablePricingContext pricingCtx = createPricingContext();
		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			throw new ProductNotOnPriceListException(pricingCtx, orderLine.getLine());
		}

		if (pricingResult.getC_PaymentTerm_ID() > 0)
		{
			orderLine.setC_PaymentTerm_Override_ID(pricingResult.getC_PaymentTerm_ID());
		}

		final int pricePrecision = pricingResult.getPrecision();
		final BigDecimal priceEntered = extractPriceEntered(pricingResult);
		BigDecimal discount = extractDiscount(pricingResult, pricingCtx.isSOTrx());
		BigDecimal priceActual = orderLineBL.calculatePriceActualFromPriceEnteredAndDiscount(priceEntered, discount, pricePrecision);
		BigDecimal priceLimit = pricingResult.getPriceLimit();

		//
		// Apply price limit restrictions
		if (isApplyPriceLimitRestrictions(pricingResult))
		{
			final PriceLimitRuleResult priceLimitResult = pricingBL.computePriceLimit(PriceLimitRuleContext.builder()
					.pricingContext(pricingCtx)
					.priceLimit(priceLimit)
					.priceActual(priceActual)
					.paymentTermId(orderLineBL.getC_PaymentTerm_ID(orderLine))
					.build());
			if (priceLimitResult.isApplicable())
			{
				priceLimit = priceLimitResult.getPriceLimit();
			}
			if (priceLimitResult.isBelowPriceLimit(priceActual))
			{
				priceActual = priceLimit;

				if (priceEntered.signum() != 0)
				{
					discount = orderLineBL.calculateDiscountFromPrices(priceEntered, priceActual, pricePrecision);
				}
			}
		}

		//
		// Prices
		orderLine.setPriceEntered(priceEntered);
		orderLine.setDiscount(discount);
		orderLine.setPriceActual(priceActual);
		orderLine.setPriceLimit(priceLimit);
		orderLine.setPriceList(pricingResult.getPriceList());
		orderLine.setPriceStd(pricingResult.getPriceStd());
		orderLine.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID()); // 07090: when setting a priceActual, we also need to specify a PriceUOM

		//
		// C_Currency_ID, M_PriceList_Version_ID
		orderLine.setC_Currency_ID(pricingResult.getC_Currency_ID());
		orderLine.setM_PriceList_Version_ID(pricingResult.getM_PriceList_Version_ID());

		orderLine.setIsPriceEditable(pricingResult.isPriceEditable());
		orderLine.setIsDiscountEditable(pricingResult.isDiscountEditable());
		orderLine.setEnforcePriceLimit(pricingResult.isEnforcePriceLimit());

		orderLine.setM_DiscountSchemaBreak_ID(pricingResult.getM_DiscountSchemaBreak_ID());
		orderLine.setBase_PricingSystem_ID(pricingResult.getM_DiscountSchemaBreak_BasePricingSystem_ID());

		//
		//
		if (request.isUpdateLineNetAmt())
		{
			if (request.getQty() != null)
			{
				orderLineBL.updateLineNetAmt(orderLine, request.getQty());
			}
			else
			{
				orderLineBL.updateLineNetAmt(orderLine, orderLineBL.getQtyEntered(orderLine));
			}
		}
	}

	public IEditablePricingContext createPricingContext()
	{
		final I_C_OrderLine orderLine = request.getOrderLine();
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();

		final boolean isSOTrx = order.isSOTrx();
		final int productId = orderLine.getM_Product_ID();
		int bpartnerId = orderLine.getC_BPartner_ID();
		if (bpartnerId <= 0)
		{
			bpartnerId = order.getC_BPartner_ID();
		}

		final Timestamp date = OrderLineBL.getPriceDate(orderLine, order);

		final BigDecimal qtyInPriceUOM;
		if (request.getQty() != null)
		{
			final Quantity qtyOverride = request.getQty();
			qtyInPriceUOM = orderLineBL.convertToPriceUOM(qtyOverride, orderLine).getQty();
		}
		else
		{
			qtyInPriceUOM = orderLineBL.convertQtyEnteredToPriceUOM(orderLine);
		}

		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				productId,
				bpartnerId,
				orderLine.getPrice_UOM_ID(),  // task 06942
				qtyInPriceUOM,
				isSOTrx);
		pricingCtx.setPriceDate(date);

		// 03152: setting the 'ol' to allow the subscription system to compute the right price
		pricingCtx.setReferencedObject(orderLine);

		if (request.getPriceListId() > 0)
		{
			pricingCtx.setM_PriceList_ID(request.getPriceListId());
		}
		else
		{
			final IOrderBL orderBL = Services.get(IOrderBL.class);
			final int priceListId = orderBL.retrievePriceListId(order);
			pricingCtx.setM_PriceList_ID(priceListId);
		}

		final int countryId = getCountryIdOrZero(orderLine);
		pricingCtx.setC_Country_ID(countryId);

		pricingCtx.setConvertPriceToContextUOM(isConvertPriceToContextUOM(request, pricingCtx.isConvertPriceToContextUOM()));

		//
		// Don't calculate the discount in case we are dealing with a percentage discount compensation group line (task 3149)
		if (orderLine.isGroupCompensationLine()
				&& X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount.equals(orderLine.getGroupCompensationType())
				&& X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent.equals(orderLine.getGroupCompensationAmtType()))
		{
			pricingCtx.setDisallowDiscount(true);
		}

		return pricingCtx;
	}

	private static int getCountryIdOrZero(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		if (orderLine.getC_BPartner_Location_ID() <= 0)
		{
			return 0;
		}

		final I_C_BPartner_Location bPartnerLocation = orderLine.getC_BPartner_Location();
		if (bPartnerLocation.getC_Location_ID() <= 0)
		{
			return 0;
		}

		final int countryId = bPartnerLocation.getC_Location().getC_Country_ID();
		return countryId;
	}

	private static boolean isConvertPriceToContextUOM(final OrderLinePriceUpdateRequest request, final boolean defaultValue)
	{
		final ResultUOM resultUOM = request.getResultUOM();
		if (resultUOM == null)
		{
			return defaultValue;
		}
		else if (resultUOM == ResultUOM.PRICE_UOM)
		{
			return false;
		}
		else if (resultUOM == ResultUOM.PRICE_UOM_IF_ORDERLINE_IS_NEW)
		{
			final boolean convertToPriceUOM = InterfaceWrapperHelper.isNew(request.getOrderLine());
			return !convertToPriceUOM;
		}
		else if (resultUOM == ResultUOM.CONTEXT_UOM)
		{
			return true;
		}
		else
		{
			throw new AdempiereException("ResultPriceUOM not supported: " + resultUOM);
		}
	}

	private boolean isApplyPriceLimitRestrictions(final IPricingResult pricingResult)
	{
		return request.isApplyPriceLimitRestrictions() && pricingResult.isEnforcePriceLimit();
	}

	private BigDecimal extractPriceEntered(final IPricingResult pricingResult)
	{
		if (isAllowChangingPriceEntered())
		{
			return pricingResult.getPriceStd();
		}
		else
		{
			final I_C_OrderLine orderLine = request.getOrderLine();
			return orderLine.getPriceEntered();
		}
	}

	private boolean isAllowChangingPriceEntered()
	{
		final I_C_OrderLine orderLine = request.getOrderLine();
		if (orderLine.isManualPrice())
		{
			return false;
		}
		if (request.isUpdatePriceEnteredAndDiscountOnlyIfNotAlreadySet() && orderLine.getPriceEntered().signum() != 0) // task 06727
		{
			return false;
		}

		return true;
	}

	private BigDecimal extractDiscount(final IPricingResult pricingResult, final boolean isSOTrx)
	{
		if (isAllowChangingDiscount(isSOTrx))
		{
			return pricingResult.getDiscount();
		}
		else
		{
			final I_C_OrderLine orderLine = request.getOrderLine();
			return orderLine.getDiscount();
		}
	}

	private boolean isAllowChangingDiscount(final boolean isSOTrx)
	{
		if (!isSOTrx)
		{
			return true;
		}

		final I_C_OrderLine orderLine = request.getOrderLine();
		return !orderLine.isManualDiscount();
	}

	public int computeTaxCategoryId()
	{
		final IPricingContext pricingCtx = createPricingContext();

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			final I_C_OrderLine orderLine = request.getOrderLine();
			throw new ProductNotOnPriceListException(pricingCtx, orderLine.getLine());
		}

		return pricingResult.getC_TaxCategory_ID();
	}

	public IPricingResult computePrices()
	{
		final IEditablePricingContext pricingCtx = createPricingContext();
		return pricingBL.calculatePrice(pricingCtx);
	}

	public PriceLimitRuleResult computePriceLimit()
	{
		final I_C_OrderLine orderLine = request.getOrderLine();

		return pricingBL.computePriceLimit(PriceLimitRuleContext.builder()
				.pricingContext(createPricingContext())
				.priceLimit(orderLine.getPriceLimit())
				.priceActual(orderLine.getPriceActual())
				.paymentTermId(orderLineBL.getC_PaymentTerm_ID(orderLine))
				.build());
	}
}
