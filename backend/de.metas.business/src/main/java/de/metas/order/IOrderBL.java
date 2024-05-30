/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.order;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.exceptions.PriceListNotFoundException;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.request.RequestTypeId;
import de.metas.tax.api.Tax;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_PriceList_Version;
import org.eevolution.api.PPCostCollectorId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IOrderBL extends ISingletonService
{
	I_C_Order getById(OrderId orderId);

	/**
	 * Sets price list if there is a price list for the given location and pricing system.
	 * <p>
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
	 */
	PriceListVersionId getPriceListVersion(I_C_Order order);

	BPartnerLocationAndCaptureId getShipToLocationId(I_C_Order order);

	/**
	 * Returns the given order's <code>AD_User</code>, or if set and <code>isDropShip = true</code> then returns the <code>DropShip_User</code>.
	 */
	I_AD_User getShipToUser(I_C_Order order);

	Optional<BPartnerContactId> getShipToContactId(I_C_Order order);

	/**
	 * @return the order's bill location <b>OR</b> falls back to the "general" contact ({@code C_Order.C_BParter_Location_ID}).
	 */
	@NonNull
	BPartnerLocationAndCaptureId getBillToLocationId(I_C_Order order);

	@Nullable
	BPartnerId getEffectiveBillPartnerId(@NonNull I_C_Order orderRecord);

	/**
	 * @return the order's bill contact <b>but</b> falls back to the "general" contact ({@code C_Order.AD_User_ID}) if possible.
	 * Be sure to first check with {@link #hasBillToContactId(I_C_Order)}.
	 */
	@NonNull BPartnerContactId getBillToContactId(I_C_Order order);

	/**
	 * Check if there is a price list for the given location and pricing system.
	 * <p>
	 * In case there is any error, this method will throw an exception.
	 * <p>
	 * NOTE: in case the bpartner location or the pricing system is not set, this method will skip the validation and no exception will be thrown.
	 */
	void checkForPriceList(I_C_Order order);

	/**
	 * Retrieve and set Bill_User_ID
	 *
	 * @return true if set
	 */
	boolean setBill_User_ID(I_C_Order order);

	List<I_C_Order> getByIds(@NonNull Collection<OrderId> orderIds);

	default List<de.metas.interfaces.I_C_OrderLine> getLinesByOrderId(@NonNull final OrderId orderId)
	{
		return getLinesByOrderIds(ImmutableSet.of(orderId));
	}

	List<de.metas.interfaces.I_C_OrderLine> getLinesByOrderIds(@NonNull Set<OrderId> orderIds);

	Map<OrderAndLineId, de.metas.interfaces.I_C_OrderLine> getLinesByIds(@NonNull Set<OrderAndLineId> orderAndLineIds);

	de.metas.interfaces.I_C_OrderLine getLineById(@NonNull OrderAndLineId orderAndLineId);

	/**
	 * Set the given order's pricing system and price list from the given <code>oder</code>'s
	 * <ul>
	 * <li><code>IsSOTrx</code> value
	 * <li><code>Bill_BPartner</code>'s pricing system
	 * <li>location's <code>C_Country</code>
	 * </ul>
	 *
	 * @param overridePricingSystem true if pricing system shall be set even if is already set
	 */
	void setM_PricingSystem_ID(I_C_Order order, boolean overridePricingSystem);

	PriceListId retrievePriceListId(I_C_Order order, PricingSystemId pricingSystemIdOverride);

	/**
	 * Sets Target Document Type based on {@link I_C_Order#isSOTrx()} (Standard Order or PO)
	 */
	void setDefaultDocTypeTargetId(I_C_Order order);

	void setPODocTypeTargetId(I_C_Order order, String poDocSubType);

	/**
	 * Set Target Sales Document Type.
	 */
	void setSODocTypeTargetId(I_C_Order order, final String soDocSubType);

	void setDocTypeTargetIdAndUpdateDescription(I_C_Order order, DocTypeId docTypeId);

	@Deprecated
	default void setDocTypeTargetIdAndUpdateDescription(final I_C_Order order, final int docTypeId)
	{
		setDocTypeTargetIdAndUpdateDescription(order, DocTypeId.ofRepoId(docTypeId));
	}

	/**
	 * Updates the addresses in the order lines from the order. Also sets the header info in the lines.
	 */
	void updateOrderLineAddressesFromOrder(I_C_Order order);

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
	 */
	void setBPLocation(I_C_Order order, I_C_BPartner bp);

	CurrencyPrecision getPricePrecision(I_C_Order order);

	CurrencyPrecision getAmountPrecision(I_C_Order order);

	CurrencyPrecision getTaxPrecision(I_C_Order order);

	/**
	 * Is Tax Included in Amount.
	 *
	 * @param tax optional
	 * @return if the given <code>tax</code> is not <code>null</code> and if is has {@link Tax#isWholeTax()} equals <code>true</code>, then true is returned. Otherwise, for the given
	 * <code>order</code> the value of {@link I_C_Order#isTaxIncluded()} is returned.
	 */
	boolean isTaxIncluded(I_C_Order order, Tax tax);

	/**
	 * Close given order line by setting the line's <code>QtyOrdered</code> to the current <code>QtyDelviered</code>.
	 * Also update the order line's reservation
	 * <p>
	 * This method is saving the order line.
	 * <p>
	 * This is the counter-part of {@link #reopenLine(I_C_OrderLine)}.
	 */
	void closeLine(I_C_OrderLine orderLine);

	/**
	 * Re-open closed line (i.e. set QtyOrdered=QtyEntered converted to product stocking UOM) and update reservations.
	 * <p>
	 * This method is saving the order line.
	 * <p>
	 * This is the counter-part of {@link #closeLine(I_C_OrderLine)}.
	 */
	void reopenLine(I_C_OrderLine orderLine);

	/**
	 * @return {@code true} if the order has a bill contact
	 * <b>OR</b> a "general" contact ({@code C_Order.AD_User_ID}) that is consistent with the order's Bill_BPartner.
	 */
	boolean hasBillToContactId(@NonNull I_C_Order order);

	/**
	 * Update and save the <code>C_Order</code> fields
	 * <ul>
	 * <li>QtyOrdered
	 * <li>QtyMoved
	 * <li>QtyInvoiced
	 * </ul>
	 * from the sums of the order's lines.
	 */
	void updateOrderQtySums(I_C_Order order);

	void updateDescriptionFromDocTypeTargetId(I_C_Order order);

	/**
	 * @return true if the order is a quotation, i.e. C_Order's (target-)docType's DocBaseType = SSO and DocSubType in ('OB' , 'ON' = Quotation or Proposal)
	 */
	boolean isSalesProposalOrQuotation(I_C_Order order);

	boolean isRequisition(@NonNull I_C_Order order);

	boolean isProFormaSO(@NonNull I_C_Order order);

	boolean isMediated(@NonNull I_C_Order order);

	boolean isPrepay(OrderId orderId);

	boolean isPrepay(I_C_Order order);

	void reserveStock(I_C_Order order, I_C_OrderLine... orderLines);

	@Nullable
	I_C_DocType getDocTypeOrNull(I_C_Order order);

	I_C_BPartner getBPartner(I_C_Order order);

	@Nullable
	I_C_BPartner getBPartnerOrNull(I_C_Order order);

	@Nullable
	ProjectId getProjectIdOrNull(OrderLineId orderLineId);

	Optional<RequestTypeId> getRequestTypeForCreatingNewRequestsAfterComplete(I_C_Order order);

	/**
	 * @return organization's timezone or system timezone; never returns null
	 */
	ZoneId getTimeZone(I_C_Order order);

	void validateHaddexOrder(I_C_Order order);

	void validateHaddexDate(I_C_Order order);

	boolean isHaddexOrder(I_C_Order order);

	void closeOrder(OrderId orderId);

	Optional<DeliveryViaRule> findDeliveryViaRule(@NonNull I_C_Order orderRecord);

	String getDocumentNoById(OrderId orderId);

	String getLocationEmail(OrderId ofRepoId);

	DocStatus getDocStatus(OrderId orderId);

	void save(I_C_Order order);

	void save(I_C_OrderLine orderLine);

	CurrencyId getCurrencyId(final OrderId orderId);

	Set<OrderAndLineId> getSOLineIdsByPOLineId(@NonNull OrderAndLineId purchaseOrderLineId);

	List<I_C_Order> getPurchaseOrdersBySalesOrderId(@NonNull OrderId salesOrderId);

	void updateIsOnConsignmentFromLines(OrderId orderId);

	/**
	 * @return {@code true} if metasfresh should use the default-billTo-location for {@code C_Order.C_BPartner_Location_ID}
	 */
	boolean isUseDefaultBillToLocationForBPartner(@NonNull I_C_Order order);

	static Money extractLineNetAmt(final I_C_OrderLine orderLine)
	{
		return Money.of(orderLine.getLineNetAmt(), CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));
	}

	static Quantity extractQtyEntered(final I_C_OrderLine orderLine)
	{
		final UomId uomId = UomId.ofRepoId(orderLine.getC_UOM_ID());
		return Quantitys.of(orderLine.getQtyEntered(), uomId);
	}

	de.metas.interfaces.I_C_OrderLine createOrderLine(I_C_Order order);

	void setProductId(
			@NonNull I_C_OrderLine orderLine,
			@NonNull ProductId productId,
			boolean setUOM);

	CurrencyConversionContext getCurrencyConversionContext(I_C_Order order);

	void deleteLineById(final OrderAndLineId orderAndLineId);

	Quantity getQtyEntered(I_C_OrderLine orderLine);

	boolean isCompleted(OrderId orderId);

	boolean isCompleted(I_C_Order order);

	boolean isDraftedOrInProgress(@NonNull I_C_Order order);

	@NonNull
	List<I_C_Order> getOrdersByQuery(@NonNull GetOrdersQuery query);

	void setPhysicalClearanceDate(@NonNull OrderId orderId, @Nullable Instant physicalClearanceDate);

	Optional<PPCostCollectorId> getPPCostCollectorId(@NonNull OrderLineId orderLineId);

	Map<OrderId, String> getDocumentNosByIds(@NonNull Collection<OrderId> orderIds);

	void setWeightFromLines(@NonNull I_C_Order order);

	@NonNull
	List<OrderId> getUnprocessedIdsBy(@NonNull ProductId productId);
}
