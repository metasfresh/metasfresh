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

import de.metas.currency.CurrencyPrecision;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList_Version;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IOrderLineBL extends ISingletonService
{
	// task 08002
	String DYNATTR_DoNotRecalculatePrices = IOrderLineBL.class.getName() + "#DoNotRecalcualtePrices";

	List<I_C_OrderLine> getByOrderIds(final Set<OrderId> orderIds);

	Quantity getQtyEntered(org.compiere.model.I_C_OrderLine orderLine);

	Quantity getQtyOrdered(OrderAndLineId orderAndLineId);

	Quantity getQtyOrdered(I_C_OrderLine orderLine);

	Quantity getQtyToDeliver(OrderAndLineId orderAndLineId);

	/**
	 * Creates a new order line using the given {@code order} as header.
	 */
	I_C_OrderLine createOrderLine(org.compiere.model.I_C_Order order);

	/**
	 * Creates a new order line using the given {@code order} as header.
	 */
	<T extends I_C_OrderLine> T createOrderLine(org.compiere.model.I_C_Order order, final Class<T> orderLineClass);

	/**
	 * Set Defaults from Order. Does not set C_Order_ID. Also invoked by the createOrderLine methods
	 */
	void setOrder(org.compiere.model.I_C_OrderLine ol, I_C_Order order);

	void setTaxAmtInfo(I_C_OrderLine ol);

	void setShipper(I_C_OrderLine ol);

	/**
	 * Calculate and set PriceActual from PriceEntered and Discount.
	 * <p>
	 * <b>Note: does not touch the PriceUOM</b>
	 *
	 * @param precision, if <code>>= 0</code> then the result will be rounded to this precision. Otherwise the precision of the order's price list will be used.
	 */
	void updatePriceActual(I_C_OrderLine orderLine, CurrencyPrecision precision);

	/**
	 * Utility method to subtract the given <code>discount</code> (in percent!) from the given <code>priceEntered</code> and return the result.
	 *
	 * @param discount   the discount to subtract in percent (between 0 and 100). Example: 10
	 * @param precision  the precision of the expected result (relevant for rounding)
	 * @deprecated Use {@link Percent#subtractFromBase(BigDecimal, int)}
	 */
	@Deprecated
	default BigDecimal subtractDiscount(final BigDecimal baseAmount, final BigDecimal discount, final int precision)
	{
		return Percent.of(discount).subtractFromBase(baseAmount, precision);
	}

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
	 * @return C_TaxCategory_ID
	 * @throws ProductNotOnPriceListException if the product's pricing info could not be retrieved.
	 * @see de.metas.util.Check#assume(boolean, String, Object...)
	 */
	TaxCategoryId getTaxCategoryId(org.compiere.model.I_C_OrderLine orderLine);

	void updatePrices(org.compiere.model.I_C_OrderLine orderLine);

	void updatePrices(OrderLinePriceUpdateRequest request);

	IPricingResult computePrices(OrderLinePriceUpdateRequest request);

	PriceLimitRuleResult computePriceLimit(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Sets the product ID and optionally also the UOM.
	 * <p>
	 * Important note: what we do <b>not</b> set there is the price-UOM because that one is set only together with the price.
	 */
	void setProductId(org.compiere.model.I_C_OrderLine orderLine, ProductId productId, boolean setUomFromProduct);

	/**
	 * Gets the corresponding pricelist version for the order line. If it is not set in the order line then fallback to order with:
	 * <ul>
	 * <li>the C_Order's price list</li>
	 * <li>the date being taken from order/orderLine's DatePromised date</li>
	 * </ul>
	 */
	@Nullable
	I_M_PriceList_Version getPriceListVersion(I_C_OrderLine orderLine);

	void updateLineNetAmtFromQtyEntered(org.compiere.model.I_C_OrderLine orderLine);

	void updateLineNetAmtFromQty(Quantity qty, org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Update the given <code>ol</code>'s {@link org.compiere.model.I_C_OrderLine#COLUMNNAME_QtyReserved QtyReserved}<br>
	 * Do <b>not</b> save the order line.
	 *
	 * Task http://dewiki908/mediawiki/index.php/09358_OrderLine-QtyReserved_sometimes_not_updated_%28108061810375%29
	 */
	void updateQtyReserved(I_C_OrderLine ol);

	/**
	 * Checks if the order line has different pricing and stocking UOMs. If so, returns the line's <code>QtyEntered</code> converted to the pricing UOM (<code>C_OrderLine.Price_UOM_ID</code>).
	 * Generally used for internal pricing calculations.
	 * <p>
	 * Note: the method does <b>not</b> change the given <code>orderLine</code>.
	 */
	Quantity convertQtyEnteredToPriceUOM(org.compiere.model.I_C_OrderLine orderLine);

	Quantity convertQtyToPriceUOM(Quantity qty, org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Convert the given quantity to the orderLine's {@code C_UOM_ID}.
	 */
	Quantity convertQtyToUOM(Quantity qty, org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Calculates the given <code>orderLine</code>'s <code>QtyOrdered</code> by converting it's <code>QtyEntered</code> value from the orderLine's UOM to the UOM of the orderLine's product.<br>
	 * If the given orderLine has no product or no UOM set, then method returns <code>QtyEntered</code>.
	 * <p>
	 * Note: the method does <b>not</b> change the given <code>orderLine</code>.
	 */
	Quantity convertQtyEnteredToStockUOM(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Is Tax Included in Amount. Calls {@link IOrderBL#isTaxIncluded(org.compiere.model.I_C_Order, org.compiere.model.I_C_Tax)} for the given <code>orderLine</code>'s <code>C_Tax</code> and
	 * <code>C_Order</code>.
	 *
	 * @return true if tax calculated
	 */
	boolean isTaxIncluded(org.compiere.model.I_C_OrderLine orderLine);

	CurrencyPrecision getPricePrecision(org.compiere.model.I_C_OrderLine orderLine);

	CurrencyPrecision getAmountPrecision(org.compiere.model.I_C_OrderLine orderLine);

	CurrencyPrecision getTaxPrecision(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * Copy the details from the original order line into the new order line of the counter document
	 */
	void copyOrderLineCounter(org.compiere.model.I_C_OrderLine line, org.compiere.model.I_C_OrderLine fromLine);

	/**
	 * Filter out the cases when an orderline from an order shall not be copied in the counter document.
	 * For the time being, make sure the packaging material lines are not copied.
	 * The reason for this is that the packing instructions are not the same for both orgs and we cannot use them inter-org.
	 * Later in the life of the counter document the packing material lines will be created based on the correct data from the new org.
	 *
	 * @return true if the line shall be copied and false if not
	 */
	boolean isAllowedCounterLineCopy(org.compiere.model.I_C_OrderLine fromLine);

	ProductPrice getCostPrice(org.compiere.model.I_C_OrderLine orderLine);

	ProductPrice getPriceActual(org.compiere.model.I_C_OrderLine orderLine);

	PaymentTermId getPaymentTermId(org.compiere.model.I_C_OrderLine orderLine);

	Map<OrderAndLineId, Quantity> getQtyToDeliver(Collection<OrderAndLineId> orderAndLineIds);

	void updateProductDescriptionFromProductBOMIfConfigured(org.compiere.model.I_C_OrderLine orderLine);

	void updateProductDocumentNote(I_C_OrderLine orderLine);

	BigDecimal computeQtyNetPriceFromOrderLine(org.compiere.model.I_C_OrderLine orderLine, Quantity qty);

	void save(org.compiere.model.I_C_OrderLine orderLine);

	CurrencyPrecision extractPricePrecision(org.compiere.model.I_C_OrderLine olRecord);

	void setBPLocation(I_C_OrderLine orderLine);

	boolean isCatchWeight(@NonNull org.compiere.model.I_C_OrderLine orderLine);
}
