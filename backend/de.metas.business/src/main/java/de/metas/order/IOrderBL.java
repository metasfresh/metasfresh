package de.metas.order;

import java.time.ZoneId;
import java.util.Properties;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.exceptions.PriceListNotFoundException;
import de.metas.project.ProjectId;
import de.metas.util.ISingletonService;

public interface IOrderBL extends ISingletonService
{
	I_C_Order getById(OrderId orderId);

	/**
	 * Sets price list if there is a price list for the given location and pricing system.
	 *
	 * This method does nothing if:
	 * <ul>
	 * <li>pricing system is not set
	 * <li>partner location is not set
	 * </ul>
	 *
	 * @throws PriceListNotFoundException if no price list was found
	 */
	void setPriceList(I_C_Order order);

	/**
	 * Gets the corresponding priceListVersion for the given <code>order</code>, using
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

	BPartnerLocationId getShipToLocationId(I_C_Order order);

	/**
	 * Returns the given order's <code>AD_User</code>, or if set and <code>isDropShip = true</code> then returns the <code>DropShip_User</code>.
	 *
	 * @param order
	 * @return
	 */
	I_AD_User getShipToUser(I_C_Order order);

	BPartnerLocationId getBillToLocationIdOrNull(I_C_Order order);

	BPartnerContactId getBillToContactId(I_C_Order order);

	/**
	 * Check if there is a price list for the given location and pricing system.
	 *
	 * In case there is any error, this method will throw an exception.
	 *
	 * NOTE: in case the bpartner location or the pricing system is not set, this method will skip the validation and no exception will be thrown.
	 *
	 * @param order
	 */
	void checkForPriceList(I_C_Order order);

	/**
	 * Retrieve and set Bill_User_ID
	 *
	 * @return true if set
	 */
	boolean setBill_User_ID(I_C_Order order);

	/**
	 * Set the given order's pricing system and price list from the given <code>oder</code>'s
	 * <ul>
	 * <li><code>IsSOTrx</code> value
	 * <li><code>Bill_BPartner</code>'s pricing system, see {@link org.adempiere.bpPartner.service.IBPartnerDAO#retrievePricingSystemId(Properties, int, boolean, String)}
	 * <li>location's <code>C_Country</code>, see {@link #retrievePriceListId(I_C_Order)}
	 * </ul>
	 *
	 * @param order
	 * @param overridePricingSystem true if pricing system shall be set even if is already set
	 */
	void setM_PricingSystem_ID(I_C_Order order, boolean overridePricingSystem);

	PriceListId retrievePriceListId(I_C_Order order, PricingSystemId pricingSystemIdOverride);

	/**
	 * Set Target Sales Document Type.
	 * This method is also setting IsSOTrx to true.
	 *
	 * @param order
	 * @param soDocSubType sales DocSubType
	 */
	void setDocTypeTargetId(I_C_Order order, String soDocSubType);

	/**
	 * Sets Target Document Type based on {@link I_C_Order#isSOTrx()} (Standard Order or PO)
	 *
	 * @param order
	 */
	void setDocTypeTargetId(I_C_Order order);

	void setDocTypeTargetIdAndUpdateDescription(I_C_Order order, DocTypeId docTypeId);

	@Deprecated
	default void setDocTypeTargetIdAndUpdateDescription(I_C_Order order, int docTypeId)
	{
		setDocTypeTargetIdAndUpdateDescription(order, DocTypeId.ofRepoId(docTypeId));
	}

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
	DeliveryViaRule evaluateOrderDeliveryViaRule(I_C_Order order);

	/**
	 * Set Business Partner Defaults & Details. SOTrx should be set.
	 *
	 * @param bp business partner
	 */
	void setBPartner(I_C_Order order, I_C_BPartner bp);

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

	CurrencyPrecision getPricePrecision(I_C_Order order);

	CurrencyPrecision getAmountPrecision(I_C_Order order);

	CurrencyPrecision getTaxPrecision(I_C_Order order);

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
	 * Close given order line by setting the line's <code>QtyOrdered</code> to the current <code>QtyDelviered</code>.
	 * Also update the order line's reservation via {@link IOrderPA#reserveStock(I_C_Order, I_C_OrderLine...)}.
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

	void updateDescriptionFromDocTypeTargetId(I_C_Order order);

	/**
	 * @return true if the order is a quotation, i.e. C_Order's (target-)docType's DocBaseType = SSO and DocSubType in ('OB' , 'ON' = Quotation or Proposal)
	 */
	boolean isQuotation(I_C_Order order);

	boolean isPrepay(OrderId orderId);

	boolean isPrepay(I_C_Order order);

	void reserveStock(I_C_Order order, I_C_OrderLine... orderLines);

	I_C_DocType getDocTypeOrNull(I_C_Order order);

	I_C_BPartner getBPartner(I_C_Order order);

	I_C_BPartner getBPartnerOrNull(I_C_Order order);

	ProjectId getProjectIdOrNull(OrderLineId orderLineId);

	/** @return organization's timezone or system timezone; never returns null */
	ZoneId getTimeZone(I_C_Order order);
}
