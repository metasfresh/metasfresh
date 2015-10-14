package de.metas.adempiere.service;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.model.I_M_FreightCostDetail;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

public interface IOrderBL extends ISingletonService
{

	/**
	 * Checks if {@link I_C_Order#FREIGHTCOSTRULE_Versandkostenpauschale} is selected as the order's freight cost rule. If yes, it checks if there are {@link I_M_FreightCostDetail} records for the
	 * given BPartner, Location and Shipper.
	 * <p>
	 * <b>Important: Implementors may not assume that the given order is instanceof PO.<b>
	 * 
	 * @param order
	 * @return
	 * 
	 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
	 */
	String checkFreightCost(Properties ctx, I_C_Order order, boolean nullIfOk, String trxName);

	String setPricingSystemId(I_C_Order order, boolean nullIfOk, String trxName);

	/**
	 * Sets price list if there is a price list for the given location and pricing system.
	 * 
	 * @param mTab
	 * @return
	 */
	String setPriceList(I_C_Order order, boolean nullIfOk, int pricingSysId, String trxName);

	/**
	 * Gets the corresponding pricelist version for the given <code>order</code>, using
	 * <ul>
	 * <li>the order's <code>M_PriceList_ID</code> and</li>
	 * <li>the date being taken from order/order's <code>DatePromised</code>, falling back to <code>DateOrdered</code>, if <code>DatePromised</code> is <code>null</code></li>
	 * </ul>
	 * Note: if the given order is <code>null</code>, then the method returns <code>null</code>; also note that there is sort of a sibling method in IOrderLineBL.
	 * 
	 * @param order
	 * @return
	 */
	I_M_PriceList_Version getPriceListVersion(I_C_Order order);

	/**
	 * Returns the given order's <code>C_BPartner</code>, or if set and <code>isDropShip = true</code> then returns the <code>DropShip_Partner</code>.
	 * 
	 * @param order
	 * @return
	 */
	I_C_BPartner getShipToPartner(I_C_Order order);

	/**
	 * Returns the given order's <code>C_BPartner_Location</code>, or if set and <code>isDropShip = true</code> then returns the <code>DropShip_Location</code>.
	 * 
	 * @param order
	 * @return
	 */
	I_C_BPartner_Location getShipToLocation(I_C_Order order);

	/**
	 * Returns the given order's <code>AD_User</code>, or if set and <code>isDropShip = true</code> then returns the <code>DropShip_User</code>.
	 * 
	 * @param order
	 * @return
	 */
	I_AD_User getShipToUser(I_C_Order order);

	/**
	 * Returns the given order's <code>C_BPartner_Location</code>, or if set then returns the <code>Bill_Location</code>.
	 * 
	 * @param order
	 * @return
	 */
	I_C_BPartner_Location getBillToLocation(I_C_Order order);

	/**
	 * Check if there is a price list for the given location and pricing system.
	 * 
	 * @param mTab
	 * @return
	 */
	String checkForPriceList(I_C_Order order, boolean nullIfOk, String trxName);

	/**
	 * Retrieve total tax amount for this order
	 * 
	 * @param order
	 * @param trxName
	 * @return
	 */
	BigDecimal retrieveTaxAmt(I_C_Order order);

	boolean updateFreightAmt(Properties ctx, I_C_Order order, String trxName);

	/**
	 * Retrieve and set Bill_User_ID
	 * 
	 * @return true if set
	 */
	public boolean setBill_User_ID(org.compiere.model.I_C_Order order);

	void setM_PricingSystem_ID(I_C_Order order);

	/**
	 * 
	 * @param order
	 * @return M_PriceList_ID
	 * @see "<a href='http://dewiki908/mediawiki/index.php/Produktzulassung_Land_%282009_0027_G9%29'>(2009_0027_G9)<a>"
	 */
	int retrievePriceListId(I_C_Order order);

	void setDocTypeTargetId(I_C_Order order, String docSubType);

	/**
	 * Please, if you change this, also change {@link org.compiere.model.MOrder.setC_DocTypeTarget_ID()}
	 * 
	 * @param order
	 */
	void setDocTypeTargetId(I_C_Order order);

	int retrieveDocTypeId(int clientId, int adOrgId, String docSubType);

	/**
	 * Updates the addresses in the order lines from the order. Also sets the header info in the lines.
	 * 
	 * @param order
	 */
	void updateAddresses(I_C_Order order);

	/**
	 * retrieve deliveryVIaRule from order if the rule is already set, is retrieving the one set in order, if not, retrieves the deliveryViaRule from partner
	 * 
	 * @param order
	 * @return
	 */
	String evaluateOrderDeliveryViaRule(I_C_Order order);

	void setBPartner(I_C_Order order, I_C_BPartner bp);

	void setOrder(I_C_OrderLine orderLine, I_C_Order order, String trxName);

	void setProduct(I_C_OrderLine orderLine, I_M_Product product);

	/**
	 * Attempts to set the <code>Bill_Location_ID</code> in the given <code>order</code>. If the bill location is found, also set the bill partner accordingly. First tries to use the order's BPartner
	 * & Bill location, then look into the order's BP_Relations for it. Note that this method does not save the given order.
	 * 
	 * @param order the order whose <code>Bill_Location_ID</code> and bill partner ID will be set, if they are found.
	 * @return <code>true</code> if the bill location (and bill partner) were found and the order by subsequently changed.
	 */
	boolean setBillLocation(I_C_Order order);

	/**
	 * Set C_BPartner_Location in order
	 * 
	 * @param order
	 * @param bp
	 */
	void setBPLocation(I_C_Order order, I_C_BPartner bp);

	/**
	 * Get Currency Precision
	 * 
	 * @param order
	 * @return precision
	 */
	int getPrecision(I_C_Order order);

	/**
	 * Is Tax Included in Amount.
	 * 
	 * @param order
	 * @param tax optional
	 * @return if the given <code>tax</code> is not <code>null</code> and if is has {@link I_C_Tax#isWholeTax()} equals <code>true</code>, then true is returned. Otherwise, for the given
	 *         <code>order</code> the value of {@link I_C_Order#isTaxIncluded()} is returned.
	 */
	boolean isTaxIncluded(I_C_Order order, I_C_Tax tax);

	/**
	 * Close given order line (i.e. set QtyOrdered=QtyDelivered) and update reservations.
	 * 
	 * This method is saving the order line.
	 * 
	 * This is the counter-part of {@link #reopenLine(I_C_OrderLine)}.
	 * 
	 * @param orderLine
	 */
	void closeLine(I_C_OrderLine orderLine);

	/**
	 * Re-open closed line (i.e. set QtyOrdered=QtyEntered converted to product stocking UOM) and update reservations.
	 * 
	 * This method is saving the order line.
	 * 
	 * This is the counter-part of {@link #closeLine(I_C_OrderLine)}.
	 * 
	 * @param orderLine
	 */
	void reopenLine(I_C_OrderLine orderLine);

	/**
	 * Update and save the <code>C_Order</code> fields
	 * <ul>
	 * <li>QtyOrdered
	 * <li>QtyMoved
	 * <li>QtyInvoiced
	 * </ul>
	 * from the sums of the order's lines.
	 * 
	 * @param orderLine
	 * @task http://dewiki908/mediawiki/index.php/09285_add_deliver_and_invoice_status_to_order_window
	 */
	void updateOrderQtySums(I_C_Order order);

}
