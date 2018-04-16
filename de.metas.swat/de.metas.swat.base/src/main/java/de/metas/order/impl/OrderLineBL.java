package de.metas.order.impl;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.MPriceList;
import org.compiere.model.MTax;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.product.IProductDAO;
import de.metas.tax.api.ITaxBL;
import de.metas.util.IColorRepository;
import lombok.NonNull;

public class OrderLineBL implements IOrderLineBL
{

	private static final Logger logger = LogManager.getLogger(OrderLineBL.class);

	public static final String SYSCONFIG_CountryAttribute = "de.metas.swat.CountryAttribute";

	private static final String SYSCONFIG_NoPriceConditionsColorName = "de.metas.order.NoPriceConditionsColorName";

	private final Set<Integer> ignoredOlIds = new HashSet<>();

	public static final String CTX_DiscountSchema = "DiscountSchema";

	private static final String MSG_COUNTER_DOC_MISSING_MAPPED_PRODUCT = "de.metas.order.CounterDocMissingMappedProduct";

	@Override
	public void setPricesIfNotIgnored(final Properties ctx,
			final I_C_OrderLine orderLine,
			final int priceListId,
			final BigDecimal qtyEntered,
			final BigDecimal factor,
			boolean usePriceUOM,
			final String trxName_NOTUSED)
	{
		// FIXME refactor and/or keep in sync with #updatePrices

		if (ignoredOlIds.contains(orderLine.getC_OrderLine_ID()))
		{
			return;
		}

		final int productId = orderLine.getM_Product_ID();
		final int bPartnerId = orderLine.getC_BPartner_ID();
		if (productId <= 0 || bPartnerId <= 0)
		{
			return;
		}

		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		//
		// Calculate Pricing Result
		final BigDecimal priceQty = convertToPriceUOM(qtyEntered, orderLine);
		final IEditablePricingContext pricingCtx = createPricingContext(orderLine, priceListId, priceQty);
		pricingCtx.setConvertPriceToContextUOM(!usePriceUOM);

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			throw new ProductNotOnPriceListException(pricingCtx, orderLine.getLine());
		}

		if (pricingResult.getC_PaymentTerm_ID() > 0)
		{
			orderLine.setC_PaymentTerm_Override_ID(pricingResult.getC_PaymentTerm_ID());
		}

		//
		// PriceList
		final BigDecimal priceListStdOld = orderLine.getPriceList_Std();
		final BigDecimal priceList = pricingResult.getPriceList();
		orderLine.setPriceList_Std(priceList);
		if (priceListStdOld.compareTo(priceList) != 0)
		{
			orderLine.setPriceList(priceList);
		}

		//
		// PriceLimit, PriceStd, Price_UOM_ID
		orderLine.setPriceLimit(pricingResult.getPriceLimit());
		orderLine.setPriceStd(pricingResult.getPriceStd());
		orderLine.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID()); // 07090: when setting a priceActual, we also need to specify a PriceUOM

		//
		// Set PriceEntered
		if (orderLine.getPriceEntered().signum() == 0 && !orderLine.isManualPrice())  // task 06727
		{
			// priceEntered is not set, so set it from the PL
			orderLine.setPriceEntered(pricingResult.getPriceStd());
		}

		//
		// Discount
		if (orderLine.getDiscount().signum() == 0 && !orderLine.isManualDiscount())   // task 06727
		{
			// pp.getDiscount is the discount between priceList and priceStd
			// -> useless for us
			// metas: Achtung, Rabatt wird aus PriceList und PriceStd ermittelt, nicht aus Discount Schema
			orderLine.setDiscount(pricingResult.getDiscount());
		}

		//
		// Calculate PriceActual from PriceEntered and Discount
		calculatePriceActual(orderLine, pricingResult.getPrecision());

		//
		// C_Currency_ID, Price_UOM_ID(again?), M_PriceList_Version_ID
		orderLine.setC_Currency_ID(pricingResult.getC_Currency_ID());
		orderLine.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID()); // task 06942
		orderLine.setM_PriceList_Version_ID(pricingResult.getM_PriceList_Version_ID());

		orderLine.setIsPriceEditable(pricingResult.isPriceEditable());
		orderLine.setIsDiscountEditable(pricingResult.isDiscountEditable());

		orderLine.setM_DiscountSchemaBreak_ID(pricingResult.getM_DiscountSchemaBreak_ID());

		updateLineNetAmt(orderLine, qtyEntered, factor);
	}

	@Override
	public void setTaxAmtInfoIfNotIgnored(final Properties ctx, final I_C_OrderLine ol, final String trxName)
	{
		if (ignoredOlIds.contains(ol.getC_OrderLine_ID()))
		{
			return;
		}

		final int taxId = ol.getC_Tax_ID();
		if (taxId <= 0)
		{
			ol.setTaxAmtInfo(BigDecimal.ZERO);
			return;
		}

		final boolean taxIncluded = isTaxIncluded(ol);
		final BigDecimal lineAmout = ol.getLineNetAmt();
		final int taxPrecision = getPrecision(ol);

		final I_C_Tax tax = MTax.get(ctx, taxId);

		final ITaxBL taxBL = Services.get(ITaxBL.class);
		final BigDecimal taxAmtInfo = taxBL.calculateTax(tax, lineAmout, taxIncluded, taxPrecision);

		ol.setTaxAmtInfo(taxAmtInfo);
	}

	@Override
	public void setPrices(final I_C_OrderLine ol)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);
		final boolean usePriceUOM = false;
		setPricesIfNotIgnored(ctx, ol, usePriceUOM, trxName);
	}

	@Override
	public void setPricesIfNotIgnored(
			final Properties ctx,
			final I_C_OrderLine ol,
			final boolean usePriceUOM,
			final String trxName)
	{
		if (ignoredOlIds.contains(ol.getC_OrderLine_ID()))
		{
			return;
		}
		final org.compiere.model.I_C_Order order = ol.getC_Order();

		final int productId = ol.getM_Product_ID();
		final int priceListId = order.getM_PriceList_ID();

		if (priceListId <= 0 || productId <= 0)
		{
			return;
		}

		// 06278 : If we have a UOM in product price, we use that one.
		setPricesIfNotIgnored(ctx, ol, priceListId, ol.getQtyEntered(), BigDecimal.ONE, usePriceUOM, trxName);
	}

	@Override
	public void setShipperIfNotIgnored(final Properties ctx, final I_C_OrderLine ol, final boolean force, final String trxName)
	{
		if (ignoredOlIds.contains(ol.getC_OrderLine_ID()))
		{
			return;
		}

		final org.compiere.model.I_C_Order order = ol.getC_Order();

		if (!force && ol.getM_Shipper_ID() > 0)
		{
			logger.debug("Nothing to do: force=false and M_Shipper_ID=" + ol.getM_Shipper_ID());
			return;
		}

		final int orderShipperId = order.getM_Shipper_ID();
		if (orderShipperId > 0)
		{
			logger.info("Setting M_Shipper_ID=" + orderShipperId + " from " + order);
			ol.setM_Shipper_ID(orderShipperId);
		}
		else
		{
			logger.debug("Looking for M_Shipper_ID via ship-to-bpartner of " + order);

			final int bPartnerID = order.getC_BPartner_ID();
			if (bPartnerID <= 0)
			{
				logger.warn(order + " has no ship-to-bpartner");
				return;
			}

			final I_M_Shipper shipper = Services.get(IBPartnerDAO.class).retrieveShipper(bPartnerID, null);
			if (shipper == null)
			{
				// task 07034: nothing to do
				return;
			}

			final int bPartnerShipperId = shipper.getM_Shipper_ID();

			logger.info("Setting M_Shipper_ID=" + bPartnerShipperId + " from ship-to-bpartner");
			ol.setM_Shipper_ID(bPartnerShipperId);
		}
	}

	@Override
	public void calculatePriceActualIfNotIgnored(final I_C_OrderLine ol, final int precision)
	{
		if (ignoredOlIds.contains(ol.getC_OrderLine_ID()))
		{
			return;
		}
		calculatePriceActual(ol, precision);
	}

	@Override
	public BigDecimal subtractDiscount(final BigDecimal baseAmount, final BigDecimal discount, final int precision)
	{
		final BigDecimal multiplier = Env.ONEHUNDRED.subtract(discount).divide(Env.ONEHUNDRED, precision * 3, RoundingMode.HALF_UP);
		final BigDecimal result = baseAmount.multiply(multiplier).setScale(precision, RoundingMode.HALF_UP);
		return result;
	}

	@Override
	public BigDecimal calculateDiscountFromPrices(final BigDecimal priceEntered, final BigDecimal priceActual, final int precision)
	{
		if (priceEntered.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		BigDecimal discount = priceEntered.subtract(priceActual)
				.divide(priceEntered, 12, RoundingMode.HALF_UP)
				.multiply(Env.ONEHUNDRED);
		if (discount.scale() > 2)
		{
			discount = discount.setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		return discount;
	}

	@Override
	public BigDecimal calculatePriceEnteredFromPriceActualAndDiscount(final BigDecimal priceActual, final BigDecimal discount, final int precision)
	{
		final BigDecimal multiplier = Env.ONEHUNDRED.add(discount).divide(Env.ONEHUNDRED, 12, RoundingMode.HALF_UP);
		return priceActual.multiply(multiplier).setScale(precision, RoundingMode.HALF_UP);
	}

	@Override
	public void ignore(final int orderLineId)
	{
		ignoredOlIds.add(orderLineId);
	}

	@Override
	public void unignore(final int orderLineId)
	{
		ignoredOlIds.remove(orderLineId);
	}

	@Override
	public int getC_TaxCategory_ID(final org.compiere.model.I_C_OrderLine orderLine)
	{
		// In case we have a charge, use the tax category from charge
		if (orderLine.getC_Charge_ID() > 0)
		{
			return orderLine.getC_Charge().getC_TaxCategory_ID();
		}

		final IPricingContext pricingCtx = createPricingContext(orderLine);
		final IPricingResult pricingResult = Services.get(IPricingBL.class).calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			throw new ProductNotOnPriceListException(pricingCtx, orderLine.getLine());
		}
		return pricingResult.getC_TaxCategory_ID();
	}

	/**
	 * Creates a pricing context with the given orderLine's <code>Price_UOM</code> and with the given order's <code>QtyEntered</code> already being converted to that priceUOM.
	 * <p>
	 * Also assumes that the given line's order has a {@code M_PriceList_ID}
	 */
	private IEditablePricingContext createPricingContext(org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderLine.getC_Order();

		final int priceListId;
		if (order.getM_PriceList_ID() > 0)
		{
			priceListId = order.getM_PriceList_ID();
		}
		else
		{
			// gh #936: if order.getM_PriceList_ID is 0, then attempt to get the priceListId from the BL.
			final IOrderBL orderBL = Services.get(IOrderBL.class);
			priceListId = orderBL.retrievePriceListId(order);
		}

		final BigDecimal priceQty = convertQtyEnteredToPriceUOM(orderLine);

		return createPricingContext(orderLine, priceListId, priceQty);
	}

	/**
	 * Creates a pricing context with the given <code>orderLine</code>'s bPartner, date, product, IsSOTrx and <code>Price_UOM</code>
	 *
	 * @param orderLine
	 * @param priceListId
	 * @param qty the for the price. <b>WARNING:</b> this qty might be in any UOM!
	 * @return
	 */
	private IEditablePricingContext createPricingContext(
			final org.compiere.model.I_C_OrderLine orderLine,
			final int priceListId,
			final BigDecimal qty)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final org.compiere.model.I_C_Order order = orderLine.getC_Order();

		final boolean isSOTrx = order.isSOTrx();

		final int productId = orderLine.getM_Product_ID();

		int bPartnerId = orderLine.getC_BPartner_ID();
		if (bPartnerId <= 0)
		{
			bPartnerId = order.getC_BPartner_ID();
		}

		final Timestamp date = getPriceDate(orderLine, order);

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);

		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				productId,
				bPartnerId,
				ol.getPrice_UOM_ID(),  // task 06942
				qty,
				isSOTrx);
		pricingCtx.setPriceDate(date);

		// 03152: setting the 'ol' to allow the subscription system to compute the right price
		pricingCtx.setReferencedObject(orderLine);

		pricingCtx.setM_PriceList_ID(priceListId);

		final int countryId = getCountryIdOrZero(orderLine);
		pricingCtx.setC_Country_ID(countryId);

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

	/**
	 * task 07080
	 *
	 * @param orderLine
	 * @param order
	 * @return
	 */
	private Timestamp getPriceDate(final org.compiere.model.I_C_OrderLine orderLine,
			final org.compiere.model.I_C_Order order)
	{
		Timestamp date = orderLine.getDatePromised();
		// if null, then get date promised from order
		if (date == null)
		{
			date = order.getDatePromised();
		}
		// still null, then get date ordered from order line
		if (date == null)
		{
			date = orderLine.getDateOrdered();
		}
		// still null, then get date ordered from order
		if (date == null)
		{
			date = order.getDateOrdered();
		}
		return date;
	}

	private int getCountryIdOrZero(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
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

	@Override
	public I_C_OrderLine createOrderLine(final org.compiere.model.I_C_Order order)
	{
		final I_C_OrderLine ol = newInstance(I_C_OrderLine.class, order);
		ol.setC_Order(order);
		setOrder(ol, order);

		if (order.isSOTrx() && order.isDropShip())
		{
			int C_BPartner_ID = order.getDropShip_BPartner_ID() > 0 ? order.getDropShip_BPartner_ID() : order.getC_BPartner_ID();
			ol.setC_BPartner_ID(C_BPartner_ID);

			final I_C_BPartner_Location deliveryLocation = Services.get(IOrderBL.class).getShipToLocation(order);
			int C_BPartner_Location_ID = deliveryLocation != null ? deliveryLocation.getC_BPartner_Location_ID() : -1;

			ol.setC_BPartner_Location_ID(C_BPartner_Location_ID);

			int AD_User_ID = order.getDropShip_User_ID() > 0 ? order.getDropShip_User_ID() : order.getAD_User_ID();
			ol.setAD_User_ID(AD_User_ID);
		}
		return ol;
	}

	@Override
	public void setOrder(final org.compiere.model.I_C_OrderLine ol, final org.compiere.model.I_C_Order order)
	{
		ol.setAD_Org_ID(order.getAD_Org_ID());
		final boolean isDropShip = order.isDropShip();
		final int C_BPartner_ID = isDropShip && order.getDropShip_BPartner_ID() > 0 ? order.getDropShip_BPartner_ID() : order.getC_BPartner_ID();
		ol.setC_BPartner_ID(C_BPartner_ID);

		final int C_BPartner_Location_ID = isDropShip && order.getDropShip_Location_ID() > 0 ? order.getDropShip_Location_ID() : order.getC_BPartner_Location_ID();
		ol.setC_BPartner_Location_ID(C_BPartner_Location_ID);

		// metas: begin: copy AD_User_ID
		final de.metas.interfaces.I_C_OrderLine oline = InterfaceWrapperHelper.create(ol, de.metas.interfaces.I_C_OrderLine.class);
		final int AD_User_ID = isDropShip && order.getDropShip_User_ID() > 0 ? order.getDropShip_User_ID() : order.getAD_User_ID();
		oline.setAD_User_ID(AD_User_ID);
		// metas: end

		oline.setM_PriceList_Version_ID(0); // the current PLV might be add or'd with the new order's PL.

		ol.setM_Warehouse_ID(order.getM_Warehouse_ID());
		ol.setDateOrdered(order.getDateOrdered());
		ol.setDatePromised(order.getDatePromised());
		ol.setC_Currency_ID(order.getC_Currency_ID());

		// Don't set Activity, etc as they are overwrites
	}

	@Override
	public <T extends I_C_OrderLine> T createOrderLine(org.compiere.model.I_C_Order order, Class<T> orderLineClass)
	{
		final I_C_OrderLine orderLine = createOrderLine(order);
		return InterfaceWrapperHelper.create(orderLine, orderLineClass);
	}

	@Override
	public void updateLineNetAmt(
			final I_C_OrderLine ol,
			final BigDecimal qtyEntered,
			final BigDecimal factor)
	{
		Check.assumeNotNull(qtyEntered, "Param qtyEntered not null. Param ol={}", ol);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final I_C_Order order = ol.getC_Order();
		final int priceListId = order.getM_PriceList_ID();

		//
		// We need to get the quantity in the pricing's UOM (if different)
		final BigDecimal convertedQty = convertToPriceUOM(qtyEntered, ol);

		// this code has been borrowed from
		// org.compiere.model.CalloutOrder.amt
		final int stdPrecision = MPriceList.getStandardPrecision(ctx, priceListId);

		BigDecimal lineNetAmt = convertedQty.multiply(factor.multiply(ol.getPriceActual()));
		if (lineNetAmt.scale() > stdPrecision)
		{
			lineNetAmt = lineNetAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);
		}
		logger.debug("Setting LineNetAmt={} to {}", lineNetAmt, ol);
		ol.setLineNetAmt(lineNetAmt);

	}

	@Override
	public void updatePrices(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_OrderLine orderLineToUse = create(orderLine, I_C_OrderLine.class);
		updatePrices0(orderLineToUse);
	}

	private void updatePrices0(final I_C_OrderLine orderLine)
	{
		// FIXME refactor and/or keep in sync with #setPricesIfNotIgnored

		// Product was not set yet. There is no point to calculate the prices
		if (orderLine.getM_Product_ID() <= 0)
		{
			return;
		}

		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		//
		// Calculate Pricing Result
		final IEditablePricingContext pricingCtx = createPricingContext(orderLine);
		final boolean userPriceUOM = InterfaceWrapperHelper.isNew(orderLine);
		pricingCtx.setConvertPriceToContextUOM(!userPriceUOM);

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			throw new ProductNotOnPriceListException(pricingCtx, orderLine.getLine());
		}

		if (pricingResult.getC_PaymentTerm_ID() > 0)
		{
			orderLine.setC_PaymentTerm_Override_ID(pricingResult.getC_PaymentTerm_ID());
		}

		//
		// PriceList
		final BigDecimal priceListStdOld = orderLine.getPriceList_Std();
		final BigDecimal priceList = pricingResult.getPriceList();
		orderLine.setPriceList_Std(priceList);
		if (priceListStdOld.compareTo(priceList) != 0)
		{
			orderLine.setPriceList(priceList);
		}

		//
		// PriceLimit, PriceStd, Price_UOM_ID
		orderLine.setPriceLimit(pricingResult.getPriceLimit());
		orderLine.setPriceStd(pricingResult.getPriceStd());
		orderLine.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID()); // 07090: when setting a priceActual, we also need to specify a PriceUOM

		//
		// Set PriceEntered and PriceActual only if IsManualPrice=N
		if (!orderLine.isManualPrice())
		{
			orderLine.setPriceEntered(pricingResult.getPriceStd());
			orderLine.setPriceActual(pricingResult.getPriceStd());
		}

		//
		// Discount
		// NOTE: Subscription prices do not work with Purchase Orders.
		if (pricingCtx.isSOTrx())
		{
			if (!orderLine.isManualDiscount())
			{
				// Override discount only if is not manual
				// Note: only the sales order widnow has the field 'isManualDiscount'
				orderLine.setDiscount(pricingResult.getDiscount());
			}
		}
		else
		{
			orderLine.setDiscount(pricingResult.getDiscount());
		}

		//
		// Calculate PriceActual from PriceEntered and Discount
		calculatePriceActual(orderLine, pricingResult.getPrecision());

		//
		// C_Currency_ID, Price_UOM_ID(again?), M_PriceList_Version_ID
		orderLine.setC_Currency_ID(pricingResult.getC_Currency_ID());
		orderLine.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID()); // task 06942
		orderLine.setM_PriceList_Version_ID(pricingResult.getM_PriceList_Version_ID());

		orderLine.setIsPriceEditable(pricingResult.isPriceEditable());
		orderLine.setIsDiscountEditable(pricingResult.isDiscountEditable());
		orderLine.setEnforcePriceLimit(pricingResult.isEnforcePriceLimit());

		orderLine.setM_DiscountSchemaBreak_ID(pricingResult.getM_DiscountSchemaBreak_ID());

		//
		// UI
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final int WindowNo = GridTabWrapper.getWindowNo(orderLine);
		Env.setContext(ctx, WindowNo, CTX_DiscountSchema, pricingResult.isUsesDiscountSchema());
	}

	@Override
	public void updateQtyReserved(final I_C_OrderLine orderLine)
	{
		if (orderLine == null)
		{
			logger.debug("Given orderLine is NULL; returning");
			return; // not our business
		}

		// make two simple checks that work without loading additional stuff
		if (orderLine.getM_Product_ID() <= 0)
		{
			logger.debug("Given orderLine {} has M_Product_ID<=0; setting QtyReserved=0.", orderLine);
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}
		if (orderLine.getQtyOrdered().signum() <= 0)
		{
			logger.debug("Given orderLine {} has QtyOrdered<=0; setting QtyReserved=0.", orderLine);
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}

		final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		final I_C_Order order = orderLine.getC_Order();

		if (!docActionBL.isDocumentStatusOneOf(order,
				IDocument.STATUS_InProgress, IDocument.STATUS_Completed, IDocument.STATUS_Closed))
		{
			logger.debug("C_Order {} of given orderLine {} has DocStatus {}; setting QtyReserved=0.",
					new Object[] { order, orderLine, order.getDocStatus() });
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}
		if (order.getC_DocType_ID() > 0 && docTypeBL.isProposal(order.getC_DocType()))
		{
			logger.debug("C_Order {} of given orderLine {} has C_DocType {} which is a proposal; setting QtyReserved=0.",
					new Object[] { order, orderLine, order.getC_DocType() });
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}

		if (!orderLine.getM_Product().isStocked())
		{
			logger.debug("Given orderLine {} has M_Product {} which is not stocked; setting QtyReserved=0.",
					new Object[] { orderLine, orderLine.getM_Product() });
			orderLine.setQtyReserved(BigDecimal.ZERO);
			return;
		}

		final BigDecimal qtyReservedRaw = orderLine.getQtyOrdered().subtract(orderLine.getQtyDelivered());
		final BigDecimal qtyReserved = BigDecimal.ZERO.max(qtyReservedRaw); // not less than zero

		logger.debug("Given orderLine {} has QtyOrdered={} and QtyDelivered={}; setting QtyReserved={}.",
				new Object[] { orderLine, orderLine.getQtyOrdered(), orderLine.getQtyDelivered(), qtyReserved });
		orderLine.setQtyReserved(qtyReserved);
	}

	@Override
	public void calculatePriceActual(final I_C_OrderLine orderLine, final int precision)
	{
		final BigDecimal discount = orderLine.getDiscount();
		final BigDecimal priceEntered = orderLine.getPriceEntered();

		BigDecimal priceActual;
		if (priceEntered.signum() == 0)
		{
			priceActual = priceEntered;
		}
		else
		{
			final int precisionToUse;
			if (precision >= 0)
			{
				precisionToUse = precision;
			}
			else
			{
				// checks to avoid unexplained NPEs
				Check.errorIf(orderLine.getC_Order_ID() <= 0, "Optional 'precision' param was not set but param 'orderLine' {} has no order", orderLine);
				final I_C_Order order = orderLine.getC_Order();
				Check.errorIf(order.getM_PriceList_ID() <= 0, "Optional 'precision' param was not set but the order of param 'orderLine' {} has no price list", orderLine);

				precisionToUse = order.getM_PriceList().getPricePrecision();
			}
			priceActual = subtractDiscount(priceEntered, discount, precisionToUse);
		}

		orderLine.setPriceActual(priceActual);
	}

	@Override
	public void setM_Product_ID(final I_C_OrderLine orderLine, int M_Product_ID, boolean setUOM)
	{
		if (setUOM)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
			final I_M_Product product = InterfaceWrapperHelper.create(ctx, M_Product_ID, I_M_Product.class, ITrx.TRXNAME_None);
			orderLine.setM_Product(product);
			orderLine.setC_UOM_ID(product.getC_UOM_ID());
		}
		else
		{
			orderLine.setM_Product_ID(M_Product_ID);
		}
		orderLine.setM_AttributeSetInstance_ID(0);
	}	// setM_Product_ID

	@Override
	public I_M_PriceList_Version getPriceListVersion(I_C_OrderLine orderLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);

		if (orderLine.getM_PriceList_Version_ID() > 0)
		{
			return InterfaceWrapperHelper.create(ctx, orderLine.getM_PriceList_Version_ID(), I_M_PriceList_Version.class, trxName);
		}
		else
		{
			// If the line doesn't have a pricelist version, take the one from order.
			final I_C_Order order = orderLine.getC_Order();

			final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it
			return Services.get(IPriceListDAO.class).retrievePriceListVersionOrNull(
					order.getM_PriceList(),
					getPriceDate(orderLine, order),
					processedPLVFiltering);
		}
	}

	@Override
	public BigDecimal convertQtyEnteredToPriceUOM(final org.compiere.model.I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "orderLine not null");

		final BigDecimal qtyEntered = orderLine.getQtyEntered();

		final I_C_OrderLine orderLineToUse = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);
		final BigDecimal qtyInPriceUOM = convertToPriceUOM(qtyEntered, orderLineToUse);
		return qtyInPriceUOM;
	}

	@Override
	public BigDecimal convertQtyEnteredToInternalUOM(final org.compiere.model.I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "orderLine not null");

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);

		final BigDecimal qtyEntered = orderLine.getQtyEntered();

		if (orderLine.getM_Product_ID() <= 0 || orderLine.getC_UOM_ID() <= 0)
		{
			return qtyEntered;
		}

		final BigDecimal qtyOrdered = uomConversionBL.convertToProductUOM(ctx, orderLine.getM_Product(), orderLine.getC_UOM(), qtyEntered);
		return qtyOrdered;
	}

	/**
	 * Converts the given <code>qtyEntered</code> from the given <code>orderLine</code>'s <code>C_UOM</code> to the order line's <code>Price_UOM</code>.
	 * If the given <code>orderLine</code> doesn't have both UOMs set, the method just returns the given <code>qtyEntered</code>.
	 *
	 * @param qtyEntered
	 * @param orderLine
	 * @return
	 */
	private BigDecimal convertToPriceUOM(final BigDecimal qtyEntered, final I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(qtyEntered, "qtyEntered not null");

		final I_C_UOM qtyUOM = orderLine.getC_UOM();
		if (qtyUOM == null || qtyUOM.getC_UOM_ID() <= 0)
		{
			return qtyEntered;
		}

		final I_C_UOM priceUOM = orderLine.getPrice_UOM();
		if (priceUOM == null || priceUOM.getC_UOM_ID() <= 0)
		{
			return qtyEntered;
		}

		if (qtyUOM.getC_UOM_ID() == priceUOM.getC_UOM_ID())
		{
			return qtyEntered;
		}

		final org.compiere.model.I_M_Product product = orderLine.getM_Product();
		final BigDecimal qtyInPriceUOM = Services.get(IUOMConversionBL.class).convertQty(product, qtyEntered, qtyUOM, priceUOM);
		return qtyInPriceUOM;
	}

	@Override
	public boolean isTaxIncluded(final org.compiere.model.I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "orderLine not null");

		final I_C_Tax tax = orderLine.getC_Tax();

		final org.compiere.model.I_C_Order order = orderLine.getC_Order();
		return Services.get(IOrderBL.class).isTaxIncluded(order, tax);
	}

	@Override
	public int getPrecision(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();
		return Services.get(IOrderBL.class).getPrecision(order);
	}

	@Override
	public boolean isAllowedCounterLineCopy(final org.compiere.model.I_C_OrderLine fromLine)
	{
		final de.metas.interfaces.I_C_OrderLine ol = InterfaceWrapperHelper.create(fromLine, de.metas.interfaces.I_C_OrderLine.class);

		if (ol.isPackagingMaterial())
		{
			// DO not copy the line if it's packing material. The packaging lines will be created later
			return false;
		}

		return true;
	}

	@Override
	public void copyOrderLineCounter(final org.compiere.model.I_C_OrderLine line, final org.compiere.model.I_C_OrderLine fromLine)
	{
		final de.metas.interfaces.I_C_OrderLine ol = InterfaceWrapperHelper.create(fromLine, de.metas.interfaces.I_C_OrderLine.class);

		if (ol.isPackagingMaterial())
		{
			// do nothing! the packaging lines will be created later
			return;
		}

		// link the line with the one from the counter document
		line.setRef_OrderLine_ID(fromLine.getC_OrderLine_ID());

		if (line.getM_Product_ID() > 0)      // task 09700
		{
			final IProductDAO productDAO = Services.get(IProductDAO.class);
			final IMsgBL msgBL = Services.get(IMsgBL.class);

			final I_AD_Org org = line.getAD_Org();
			final org.compiere.model.I_M_Product lineProduct = line.getM_Product();

			if (lineProduct.getAD_Org_ID() != 0)
			{
				// task 09700 the product from the original order is org specific, so we need to substitute it with the product from the counter-org.
				final org.compiere.model.I_M_Product counterProduct = productDAO.retrieveMappedProductOrNull(lineProduct, org);
				if (counterProduct == null)
				{
					final String msg = msgBL.getMsg(InterfaceWrapperHelper.getCtx(line),
							MSG_COUNTER_DOC_MISSING_MAPPED_PRODUCT,
							new Object[] { lineProduct.getValue(), lineProduct.getAD_Org().getName(), org.getName() });
					throw new AdempiereException(msg);
				}
				line.setM_Product(counterProduct);
			}
		}

	}

	@Override
	public void updateNoPriceConditionsColor(final I_C_OrderLine orderLine)
	{

		final String colorName = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_NoPriceConditionsColorName, "-");

		final int discountSchemaBreakId = orderLine.getM_DiscountSchemaBreak_ID();

		if (discountSchemaBreakId > 0)
		{
			// the discountSchemaBreak was eventually set. The color warning is no longer needed
			orderLine.setNoPriceConditionsColor_ID(-1);
			
			return;
		}

		final int colorId = getNoPriceConditionsColorId(colorName);
		
		if (colorId > 0)
		{
			orderLine.setNoPriceConditionsColor_ID(colorId);
		}

	}

	private int getNoPriceConditionsColorId(final String name)
	{
		return Services.get(IColorRepository.class).getColorIdByName(name);
	}

}
