package de.metas.inout;

import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.request.RequestTypeId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_R_Request;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

/**
 * Generic API regarding {@link I_M_InOut} and it's lines, transactions, allocations etc.
 *
 * @author tsa
 */
public interface IInOutBL extends ISingletonService
{
	I_M_InOut getById(@NonNull InOutId inoutId);

	void save(I_M_InOut inout);

	List<I_M_InOutLine> getLines(@NonNull I_M_InOut inout);

	/**
	 * @return the quantity, with {@link StockQtyAndUOMQty#getUOMQtyOpt()} being present <b>only</b> if the given line effectively has a catch quantity.
	 */
	StockQtyAndUOMQty getStockQtyAndCatchQty(I_M_InOutLine inoutLine);

	/**
	 * @return return movementQty and qtyEntered.
	 */
	StockQtyAndUOMQty getStockQtyAndQtyInUOM(I_M_InOutLine inoutLine);

	List<I_M_InOutLine> getLines(@NonNull InOutId inoutId);

	/**
	 * Create the pricing context for the given inoutline The pricing context contains information about <code>M_PricingSystem</code> and <code>M_PriceList</code> (among other infos, ofc)
	 * <p>
	 * When picking the pricing system, first check if the given <code>inOutLine</code>'s <code>M_InOut</code>'s bpartner has an pricingsystem set directly in its <code>C_BPartner</code> record that matches the <code>M_InOut.IsSOTrx</code>.
	 * <p>
	 * If the bpartner's <code>C_BPartner.M_PricingSystem_ID</code> (for <code>M_InOut.IsSOTrx='Y'</code>) or <code>C_BPartner.PO_PricingSystem_ID</code> (for <code>M_InOut.IsSOTrx='N'</code>) is <code>NULL</code>,<br>
	 * <i>and</i> if <code>M_OnOut.MovementType</code> is a "returning-type" (see {@link #isReturnMovementType(String)}),<br>
	 * then also check for the opposite pricing system of the BPartner.
	 * <p>
	 * For example, if a bpartner is both customer and vendor, has <b>no</b> <code>M_PricingSystem</code>,<br>
	 * but does have a <code>PO_PricingSystem</code> set in its <code>C_BPartner</code> record,<br>
	 * <b>and</b> if the <code>M_InOut</code> in question is a customer return (that means <code>M_InOut.IsSOTrx='Y'</code>),<br>
	 * then go with the customer's <code>PO_PricingSystem</code>.
	 * <p>
	 * After the pricing system is picked, look for the fitting price list using <code>M_InOut.C_BPartner_Location_ID</code>.
	 *
	 * @return the pricing context, populated with the information that was found (pricing system, price list, discount, currency)
	 * @throws AdempiereException in case there is no price list to fit the given <code>inOutLine</code>.
	 */
	IPricingContext createPricingCtx(org.compiere.model.I_M_InOutLine inOutLine);

	IPricingResult getProductPrice(org.compiere.model.I_M_InOutLine inOutLine);

	/**
	 * @return the pricing system fir for the inout,
	 * Otherwise, throws exception when throwEx = true and return null if it is false
	 */
	@Nullable
	I_M_PricingSystem getPricingSystem(I_M_InOut inOut, boolean throwEx);

	/**
	 * Checks if given inout is a true reversal.
	 * <p>
	 * A reversal is the document which reverses the effect of original document.
	 *
	 * @return true if given inout is a true reversal.
	 */
	boolean isReversal(I_M_InOut inout);

	/**
	 * Checks if given inout line is a true reversal.
	 * <p>
	 * A reversal is the document which reverses the effect of original document.
	 *
	 * @return true if given inout line is a true reversal.
	 */
	boolean isReversal(org.compiere.model.I_M_InOutLine inoutLine);

	/**
	 * Create a new (drafted/i.e. not saved) inout line.
	 *
	 * @return new inout line
	 */
	I_M_InOutLine newInOutLine(I_M_InOut inout);

	/**
	 * Create a new (drafted/i.e. not saved) inout line.
	 *
	 * @param inout line's model class to use
	 * @return new inout line
	 */
	<T extends I_M_InOutLine> T newInOutLine(I_M_InOut inout, Class<T> modelClass);

	/**
	 * @return <ul>
	 * <li>true if Customer Shipment or Returns
	 * <li>false if Vendor Receipts or Returns
	 * </ul>
	 */
	boolean getSOTrxFromMovementType(String movementType);

	/**
	 * @return true if Customer or Vendor Returns
	 */
	boolean isReturnMovementType(String movementType);

	BigDecimal negateIfReturnMovmenType(I_M_InOutLine iol, BigDecimal qty);

	/**
	 * Sort the given inOut's lines by their referenced order lines and configured sorting criteria.
	 *
	 * @return sorted lines
	 */
	List<I_M_InOutLine> sortLines(I_M_InOut inOut);

	/**
	 * Delete all {@link I_M_MatchInv}s for given {@link I_M_InOut}.
	 */
	void deleteMatchInvs(I_M_InOut inout);

	/**
	 * Delete all {@link I_M_MatchInv}s for given {@link I_M_InOutLine}.
	 */
	void deleteMatchInvsForInOutLine(I_M_InOutLine iol);

	/**
	 * @return the given <code>iol</code>'s <code>MovementQty</code> or its negation, based on the inOut's <code>MovementType</code>.
	 */
	BigDecimal getEffectiveStorageChange(I_M_InOutLine iol);

	/**
	 * Refresh all the lines for the given shipment in the Shipment Statistics view window
	 */
	void invalidateStatistics(I_M_InOut inout);

	/**
	 * Refresh the line for the given shipment line in the Shipment Statistics view window
	 */
	void invalidateStatistics(I_M_InOutLine inoutLine);

	I_C_Order getOrderByInOutLine(I_M_InOutLine inoutLine);

	Optional<RequestTypeId> getRequestTypeForCreatingNewRequestsAfterComplete(I_M_InOut inOut);

	I_R_Request createRequestFromInOut(I_M_InOut inOut);

	LocalDate retrieveMovementDate(I_M_InOut inOut);

	void updateDescriptionAndDescriptionBottomFromDocType(@NonNull I_M_InOut inOut);

	String getLocationEmail(InOutId ofRepoId);

	StockQtyAndUOMQty extractInOutLineQty(I_M_InOutLine inOutLineRecord, InvoicableQtyBasedOn invoicableQtyBasedOn);
}
