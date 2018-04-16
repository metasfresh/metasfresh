package de.metas.order;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Properties;

import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.interfaces.I_C_OrderLine;

public interface IOrderLineBL extends ISingletonService
{

	// task 08002
	public static final String DYNATTR_DoNotRecalculatePrices = IOrderLineBL.class.getName() + "#DoNotRecalcualtePrices";

	/**
	 * Creates a new order line using the given {@code order} as header.
	 *
	 * @param order
	 * @return
	 */
	I_C_OrderLine createOrderLine(org.compiere.model.I_C_Order order);

	/**
	 * Creates a new order line using the given {@code order} as header.
	 *
	 * @param order
	 * @return
	 */
	<T extends I_C_OrderLine> T createOrderLine(org.compiere.model.I_C_Order order, final Class<T> orderLineClass);

	/**
	 * Set Defaults from Order. Does not set C_Order_ID. Also invoked by the createOrderLine methods
	 *
	 * @param order order
	 */
	void setOrder(org.compiere.model.I_C_OrderLine ol, I_C_Order order);

	void setPrices(I_C_OrderLine ol);

	/**
	 * See {@link #setPricesIfNotIgnored(Properties, I_C_OrderLine, int, BigDecimal, BigDecimal, boolean, String)}.
	 *
	 * @param ctx
	 * @param ol
	 * @param usePriceUOM
	 * @param trxName
	 */
	void setPricesIfNotIgnored(Properties ctx, I_C_OrderLine ol, boolean usePriceUOM, String trxName);

	/**
	 *
	 * @param ctx
	 * @param priceListId
	 * @param ol
	 * @param qtyEntered the quantity (which is in the given <code>ol</code>'s <code>C_UOM</code>) that is used to compute the price per one
	 * @param factor an additional factor to use when computing the LineNetAmt
	 * @param usePriceUOM if true, then the UOM of the M_ProductPrice record will be used
	 * @param trxName_NOTUSED not used
	 */
	void setPricesIfNotIgnored(Properties ctx, I_C_OrderLine ol, int priceListId, BigDecimal qtyEntered, BigDecimal factor, boolean usePriceUOM, String trxName);

	void setTaxAmtInfoIfNotIgnored(Properties ctx, I_C_OrderLine ol, String trxName);

	void setShipperIfNotIgnored(Properties ctx, I_C_OrderLine ol, boolean force, String trxName);

	void ignore(int orderLineId);

	void unignore(int orderLineId);

	/**
	 * Same as {@link #calculatePriceActual(I_C_OrderLine, int)}, but returns without doing anything if the given line's ID is on our {@link #ignore(int)} list.
	 */
	void calculatePriceActualIfNotIgnored(I_C_OrderLine ol, int precision);

	/**
	 * Calculate and set PriceActual from PriceEntered and Discount.
	 * <p>
	 * <b>Note: does not touch the PriceUOM</b>
	 *
	 * @param orderLine
	 * @param optional, if <code>>= 0</code> then the result will be rounded to this precision. Otherwise the precision of the order's price list will be used.
	 */
	void calculatePriceActual(I_C_OrderLine orderLine, int precision);

	/**
	 * Utility method to subtract the given <code>discount</code> (in percent!) from the given <code>priceEntered</code> and return the result.
	 *
	 * @param baseAmount
	 * @param discount the discount to subtract in percent (between 0 and 100). Example: 10
	 * @param precision the precision of the expected result (relevant for rounding)
	 * @return
	 */
	BigDecimal subtractDiscount(BigDecimal baseAmount, BigDecimal discount, int precision);

	BigDecimal calculateDiscountFromPrices(BigDecimal priceEntered, BigDecimal priceActual, int precision);

	BigDecimal calculatePriceEnteredFromPriceActualAndDiscount(BigDecimal priceActual, BigDecimal discount, int precision);

	/**
	 * Retrieves the {@code M_ProductPrice} for the given {@code orderLine}'s {@code M_Product_ID} and {@code M_PriceList_Version_ID} and returns that pp's {@code C_TaxCategory_ID}.
	 * <p>
	 * This method assumes that
	 * <ul>
	 * <li>orderLine.getM_Product_ID() > 0
	 * <li>orderLine.getM_PriceList_Version_ID() > 0
	 * <li>a M_ProductPrice exists for the orderLine's product and PLV
	 * </ul>
	 *
	 * @param orderLine
	 * @return C_TaxCategory_ID
	 * @see org.adempiere.util.Check#assume(boolean, String, Object...)
	 *
	 * @throws ProductNotOnPriceListException if the product's pricing info could not be retrieved.
	 */
	int getC_TaxCategory_ID(org.compiere.model.I_C_OrderLine orderLine);

	void updatePrices(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Sets the product ID and optionally also the UOM.
	 * <p>
	 * Important note: what we do <b>not</b> set there is the price-UOM because that one is set only together with the price.
	 *
	 * @param orderLine
	 * @param M_Product_ID
	 * @param setUomFromProduct
	 */
	void setM_Product_ID(I_C_OrderLine orderLine, int M_Product_ID, boolean setUomFromProduct);

	/**
	 * Gets the corresponding pricelist version for the order line. If it is not set in the order line then fallback to order with:
	 * <ul>
	 * <li>the C_Order's price list</li>
	 * <li>the date being taken from order/orderLine's DatePromised date</li>
	 * </ul>
	 *
	 * @param orderLine
	 * @return
	 */
	I_M_PriceList_Version getPriceListVersion(I_C_OrderLine orderLine);

	/**
	 * Updates the given <code>ol</code>'s {@link org.compiere.model.I_C_OrderLine#COLUMNNAME_LineNetAmt LineNetAmt}
	 *
	 * @param ol
	 * @param qtyEntered the order-quantity in the "customer's (stocking) UOM"...i.e. a customer might order 20PCE whereas the M_Product's own stocking-UOM in ADempiere is KG. In this case,
	 *            <code>qtyEntered</code> is 20. Note that the <code>LineNetAmt</code> is computed from this qty only after is was converted to the <b>price-UOM</b> (see
	 *            {@link #convertQtyEnteredToPriceUOM(org.compiere.model.I_C_OrderLine)}).
	 *
	 * @param factor additional factor for the <code>LineNetAmt</code> calculation..if you don't know what to do with it, use BigDecimal.ONE.
	 */
	void updateLineNetAmt(I_C_OrderLine ol, BigDecimal qtyEntered, BigDecimal factor);

	/**
	 * Update the given <code>ol</code>'s {@link org.compiere.model.I_C_OrderLine#COLUMNNAME_QtyReserved QtyReserved}<br>
	 * Do <b>not</b> save the order line.
	 *
	 * @param ol
	 * @task http://dewiki908/mediawiki/index.php/09358_OrderLine-QtyReserved_sometimes_not_updated_%28108061810375%29
	 */
	void updateQtyReserved(I_C_OrderLine ol);

	/**
	 * Checks if the order line has different pricing and stocking UOMs. If so, returns the line's <code>QtyEntered</code> converted to the pricing UOM (<code>C_OrderLine.Price_UOM_ID</code>).
	 * Generally used for internal pricing calculations.
	 * <p>
	 * Note: the method does <b>not</b> change the given <code>orderLine</code>.
	 *
	 * @param orderLine
	 * @return
	 */
	BigDecimal convertQtyEnteredToPriceUOM(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Calculates the given <code>orderLine</code>'s <code>QtyOrdered</code> by converting it's <code>QtyEntered</code> value from the orderLine's UOM to the UOM of the orderLine's product.<br>
	 * If the given orderLine has no product or no UOM set, then method returns <code>QtyEntered</code>.
	 * <p>
	 * Note: the method does <b>not</b> change the given <code>orderLine</code>.
	 *
	 * @param orderLine
	 * @return
	 */
	BigDecimal convertQtyEnteredToInternalUOM(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Is Tax Included in Amount. Calls {@link IOrderBL#isTaxIncluded(org.compiere.model.I_C_Order, org.compiere.model.I_C_Tax)} for the given <code>orderLine</code>'s <code>C_Tax</code> and
	 * <code>C_Order</code>.
	 *
	 * @return true if tax calculated
	 */
	boolean isTaxIncluded(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Calls {@link IOrderBL#getPrecision(org.compiere.model.I_C_Order)} for the given <code>orderLine</code>'s <code>C_Order</code>.
	 *
	 * @param orderLine
	 * @return
	 */
	int getPrecision(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Copy the details from the original order line into the new order line of the counter document
	 *
	 * @param line
	 * @param fromLine
	 */
	void copyOrderLineCounter(org.compiere.model.I_C_OrderLine line, org.compiere.model.I_C_OrderLine fromLine);

	/**
	 * Filter out the cases when an orderline from an order shall not be copied in the counter document.
	 * For the time being, make sure the packaging material lines are not copied.
	 * The reason for this is that the packing instructions are not the same for both orgs and we cannot use them inter-org.
	 * Later in the life of the counter document the packing material lines will be created based on the correct data from the new org.
	 *
	 * @param fromLine
	 * @return true if the line shall be copied and false if not
	 */
	boolean isAllowedCounterLineCopy(org.compiere.model.I_C_OrderLine fromLine);

	/**
	 * Task #3835
	 * If the de.metas.order.NoPriceConditionsColorName sysconfig is set and the orderLine.M_DiscountSchemaBreak_ID is not set, the orderLine.NoPriceConditionsColor_ID will be set to the colourId corresponding with the name provided in the sys config.
	 * If the de.metas.order.NoPriceConditionsColorName sysconfig is set and the orderLine.M_DiscountSchemaBreak_ID is set, the orderLine.NoPriceConditionsColor_ID will be set to null because a color warning is not needed.
	 * If the sys config is not set, do nothing because the functionality is not needed
	 * 
	 * @param orderLine
	 */
	void updateNoPriceConditionsColor(I_C_OrderLine orderLine);
}
