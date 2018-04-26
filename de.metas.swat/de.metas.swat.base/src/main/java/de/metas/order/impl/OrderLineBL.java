package de.metas.order.impl;

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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.limit.PriceLimitRuleResult;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.MTax;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.product.IProductDAO;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxBL;
import de.metas.util.IColorRepository;
import lombok.NonNull;

public class OrderLineBL implements IOrderLineBL
{

	private static final Logger logger = LogManager.getLogger(OrderLineBL.class);

	public static final String SYSCONFIG_CountryAttribute = "de.metas.swat.CountryAttribute";

	private static final String SYSCONFIG_NoPriceConditionsColorName = "de.metas.order.NoPriceConditionsColorName";

	private static final String MSG_COUNTER_DOC_MISSING_MAPPED_PRODUCT = "de.metas.order.CounterDocMissingMappedProduct";

	private static final String MSG_NoPricingConditionsError = "de.metas.order.NoPricingConditionsError";

	private I_C_UOM getUOM(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_UOM uom = orderLine.getC_UOM();
		if (uom == null)
		{
			throw new AdempiereException("Order line has no UOM")
					.setParameter("orderLine", orderLine);
		}
		return uom;
	}

	@Override
	public Quantity getQtyEntered(final org.compiere.model.I_C_OrderLine orderLine)
	{
		final BigDecimal qty = orderLine.getQtyEntered();
		final I_C_UOM uom = getUOM(orderLine);
		return Quantity.of(qty, uom);
	}

	@Override
	public void setTaxAmtInfo(final I_C_OrderLine ol)
	{
		final int taxId = ol.getC_Tax_ID();
		if (taxId <= 0)
		{
			ol.setTaxAmtInfo(BigDecimal.ZERO);
			return;
		}

		final boolean taxIncluded = isTaxIncluded(ol);
		final BigDecimal lineAmout = ol.getLineNetAmt();
		final int taxPrecision = getPrecision(ol);

		final I_C_Tax tax = MTax.get(Env.getCtx(), taxId);

		final ITaxBL taxBL = Services.get(ITaxBL.class);
		final BigDecimal taxAmtInfo = taxBL.calculateTax(tax, lineAmout, taxIncluded, taxPrecision);
		ol.setTaxAmtInfo(taxAmtInfo);
	}

	@Override
	public void setShipper(final I_C_OrderLine ol)
	{
		final org.compiere.model.I_C_Order order = ol.getC_Order();

		final int orderShipperId = order.getM_Shipper_ID();
		if (orderShipperId > 0)
		{
			logger.debug("Setting M_Shipper_ID={} from {}", orderShipperId, order);
			ol.setM_Shipper_ID(orderShipperId);
		}
		else
		{
			logger.debug("Looking for M_Shipper_ID via ship-to-bpartner of {}", order);

			final int bPartnerID = order.getC_BPartner_ID();
			if (bPartnerID <= 0)
			{
				logger.warn("{} has no ship-to-bpartner", order);
				return;
			}

			final I_M_Shipper shipper = Services.get(IBPartnerDAO.class).retrieveShipper(bPartnerID, ITrx.TRXNAME_None);
			if (shipper == null)
			{
				// task 07034: nothing to do
				return;
			}

			final int bPartnerShipperId = shipper.getM_Shipper_ID();

			logger.debug("Setting M_Shipper_ID={} from ship-to-bpartner", bPartnerShipperId);
			ol.setM_Shipper_ID(bPartnerShipperId);
		}
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
	public int getC_TaxCategory_ID(final org.compiere.model.I_C_OrderLine orderLine)
	{
		// In case we have a charge, use the tax category from charge
		if (orderLine.getC_Charge_ID() > 0)
		{
			return orderLine.getC_Charge().getC_TaxCategory_ID();
		}

		return OrderLinePriceCalculator.builder()
				.request(OrderLinePriceUpdateRequest.of(orderLine))
				.orderLineBL(this)
				.build()
				.computeTaxCategoryId();
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
			@NonNull final I_C_OrderLine ol,
			@NonNull final BigDecimal qtyEntered)
	{
		Check.assumeNotNull(qtyEntered, "Param qtyEntered not null. Param ol={}", ol);

		updateLineNetAmt(ol, Quantity.of(qtyEntered, getUOM(ol)));
	}

	void updateLineNetAmt(
			@NonNull final I_C_OrderLine ol,
			@NonNull final Quantity qty)
	{
		final Quantity qtyInPriceUOM = convertToPriceUOM(qty, ol);

		final I_C_Order order = ol.getC_Order();
		final int priceListId = order.getM_PriceList_ID();
		final int precision = Services.get(IPriceListBL.class).getPricePrecision(priceListId);

		BigDecimal lineNetAmt = qtyInPriceUOM.getQty().multiply(ol.getPriceActual());
		if (lineNetAmt.scale() > precision)
		{
			lineNetAmt = lineNetAmt.setScale(precision, RoundingMode.HALF_UP);
		}

		logger.debug("Setting LineNetAmt={} to {}", lineNetAmt, ol);
		ol.setLineNetAmt(lineNetAmt);
	}

	@Override
	public void updatePrices(final org.compiere.model.I_C_OrderLine orderLine)
	{
		updatePrices(OrderLinePriceUpdateRequest.builder()
				.orderLine(orderLine)
				.resultUOM(ResultUOM.PRICE_UOM_IF_ORDERLINE_IS_NEW)
				.build());
	}

	@Override
	public void updatePrices(final OrderLinePriceUpdateRequest request)
	{
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
	public void updatePriceActual(final I_C_OrderLine orderLine, final int precision)
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

	/**
	 * task 07080
	 *
	 * @param orderLine
	 * @param order
	 * @return
	 */
	static Timestamp getPriceDate(
			final org.compiere.model.I_C_OrderLine orderLine,
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

	@Override
	public BigDecimal convertQtyEnteredToPriceUOM(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final Quantity qtyEntered = getQtyEntered(orderLine);
		return convertToPriceUOM(qtyEntered, orderLine).getQty();
	}

	@Override
	public BigDecimal convertQtyEnteredToInternalUOM(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
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

	Quantity convertToPriceUOM(@NonNull final Quantity qtyEntered, @NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final I_C_OrderLine orderLineExt = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);
		final I_C_UOM priceUOM = orderLineExt.getPrice_UOM();
		if (priceUOM == null || priceUOM.getC_UOM_ID() <= 0)
		{
			return qtyEntered;
		}

		final int qtyUOMId = qtyEntered.getUOM().getC_UOM_ID();
		if (qtyUOMId == priceUOM.getC_UOM_ID())
		{
			return qtyEntered;
		}

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IUOMConversionContext conversionCtx = uomConversionBL.createConversionContext(orderLine.getM_Product_ID());
		return uomConversionBL.convertQuantityTo(qtyEntered, conversionCtx, priceUOM);
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

		final int discountSchemaBreakId = orderLine.getM_DiscountSchemaBreak_ID();

		if (discountSchemaBreakId > 0)
		{
			// the discountSchemaBreak was eventually set. The color warning is no longer needed
			orderLine.setNoPriceConditionsColor_ID(-1);

			return;
		}

		final int colorId = getNoPriceConditionsColorId();

		if (colorId > 0)
		{
			orderLine.setNoPriceConditionsColor_ID(colorId);
		}

	}

	private int getNoPriceConditionsColorId()
	{
		final String colorName = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_NoPriceConditionsColorName, "-");

		return Services.get(IColorRepository.class).getColorIdByName(colorName);
	}

	@Override
	public void failForMissingPricingConditions(final de.metas.adempiere.model.I_C_Order order)
	{
		final boolean mandatoryPricingConditions = isMandatoryPricingConditions();

		if (!mandatoryPricingConditions)
		{
			// nothing to do
			return;
		}

		final List<I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order);

		final boolean existsOrderLineWithNoPricingConditions = orderLines
				.stream()
				.anyMatch(orderLine -> hasNoPricingConditions(orderLine));

		if (existsOrderLineWithNoPricingConditions)
		{
			final ITranslatableString translatableMsg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NoPricingConditionsError);

			throw new AdempiereException(translatableMsg.translate(Env.getAD_Language()));
		}
	}

	private boolean isMandatoryPricingConditions()
	{
		final int noPriceConditionsColorId = getNoPriceConditionsColorId();

		return noPriceConditionsColorId > 0;
	}

	private boolean hasNoPricingConditions(final I_C_OrderLine orderLine)
	{
		return orderLine.getM_DiscountSchemaBreak_ID() <= 0;
	}

	@Override
	public int getC_PaymentTerm_ID(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		int paymentTermOverrideId = orderLine.getC_PaymentTerm_Override_ID();
		return paymentTermOverrideId > 0 ? paymentTermOverrideId : orderLine.getC_Order().getC_PaymentTerm_ID();
	}

	@Override
	public PriceLimitRuleResult computePriceLimit(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		return OrderLinePriceCalculator.builder()
				.request(OrderLinePriceUpdateRequest.of(orderLine))
				.orderLineBL(this)
				.build()
				.computePriceLimit();
	}

}
