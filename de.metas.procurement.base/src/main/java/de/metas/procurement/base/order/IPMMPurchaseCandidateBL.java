package de.metas.procurement.base.order;

import java.math.BigDecimal;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.util.ISingletonService;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IPMMPurchaseCandidateBL extends ISingletonService
{
	/**
	 * Sets QtyPromised fields.
	 *
	 * NOTE:
	 * <ul>
	 * <li>this method is NOT saving the candidate
	 * <li>this method is NOT changing the QtyToOrder fields
	 * </ul>
	 *
	 * @param candidate
	 * @param qtyPromised
	 * @param qtyPromisedTU
	 */
	void setQtyPromised(I_PMM_PurchaseCandidate candidate, BigDecimal qtyPromised, BigDecimal qtyPromisedTU);

	/**
	 * Adds QtyOrdered/QtyOrderedTU and reset the QtyToOrder fields.
	 *
	 * NOTE: this method is not saving the candidate
	 *
	 * @param candidate
	 * @param qtyOrdered
	 * @param qtyOrderedTU
	 * @see #resetQtyToOrder(I_PMM_PurchaseCandidate)
	 */
	void addQtyOrderedAndResetQtyToOrder(I_PMM_PurchaseCandidate candidate, BigDecimal qtyOrdered, BigDecimal qtyOrderedTU);

	/**
	 * Subtracts from QtyOrdered/QtyOrderedTU.
	 *
	 * NOTE
	 * <ul>
	 * <li>this method is NOT saving the candidate
	 * <li>this method is NOT changing the QtyToOrder fields
	 * </ul>
	 *
	 * @param candidate
	 * @param qtyOrdered
	 * @param qtyOrderedTU
	 */
	void subtractQtyOrdered(I_PMM_PurchaseCandidate candidate, BigDecimal qtyOrdered, BigDecimal qtyOrderedTU);

	/**
	 * Updates QtyToOrder = QtyToOrderTU x TU capacity
	 * 
	 * @param candidate
	 */
	void updateQtyToOrderFromQtyToOrderTU(I_PMM_PurchaseCandidate candidate);

	/**
	 * Sets QtyToOrder fields to ZERO.
	 * 
	 * @param candidate
	 */
	void resetQtyToOrder(final I_PMM_PurchaseCandidate candidate);

	/**
	 * @return candidate wrapped to {@link IPMMPricingAware}
	 */
	IPMMPricingAware asPMMPricingAware(I_PMM_PurchaseCandidate candidate);

	/**
	 * @param candidate
	 * @return M_HU_PI_Item_Product_Override if set, M_HU_PI_Item_Product otherwise
	 */
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Effective(I_PMM_PurchaseCandidate candidate);
	
	/**
	 * @param candidate
	 * @return M_HU_PI_Item_Product_Override_ID if set, M_HU_PI_Item_Product_ID otherwise
	 */
	int getM_HU_PI_Item_Product_Effective_ID(I_PMM_PurchaseCandidate candidate);
}
