package de.metas.procurement.base.order;

import java.math.BigDecimal;

import org.adempiere.util.ISingletonService;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

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
	 * Sets QtyPromised fields and update QtyToOrder fields.
	 *
	 * NOTE: this method is not saving the candidate
	 *
	 * @param candidate
	 * @param qtyPromised
	 * @param qtyPromisedTU
	 */
	void setQtyPromisedAndUpdate(I_PMM_PurchaseCandidate candidate, BigDecimal qtyPromised, BigDecimal qtyPromisedTU);

	/**
	 * Adds QtyOrdered/QtyOrderedTU and update the QtyToOrder fields.
	 *
	 * NOTE: this method is not saving the candidate
	 *
	 * @param candidate
	 * @param qtyOrdered
	 * @param qtyOrderedTU
	 */
	void addQtyOrderedAndUpdate(I_PMM_PurchaseCandidate candidate, BigDecimal qtyOrdered, BigDecimal qtyOrderedTU);

	/**
	 * Subtracts QtyOrdered/QtyOrderedTU and update the QtyToOrder fields.
	 *
	 * NOTE: this method is not saving the candidate
	 *
	 * @param candidate
	 * @param qtyOrdered
	 * @param qtyOrderedTU
	 */
	void subtractQtyOrderedAndUpdate(I_PMM_PurchaseCandidate candidate, BigDecimal qtyOrdered, BigDecimal qtyOrderedTU);
}
