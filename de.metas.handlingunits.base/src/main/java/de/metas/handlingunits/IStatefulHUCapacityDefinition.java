package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.quantity.Quantity;

/**
 * An {@link IHUCapacityDefinition} which also contains qty storage level.
 *
 * @author tsa
 *
 */
public interface IStatefulHUCapacityDefinition extends IHUCapacityDefinition
{
	/**
	 * Add qty to this capacity.
	 *
	 * @param qtyToAdd
	 * @return qty which was actually added in <code>qtyToAdd</code>'s UOM. It'source it's in internal UOM.
	 */
	Quantity addQty(Quantity qtyToAdd);

	/**
	 * Add qty to this capacity.
	 *
	 * @param qtyToAdd
	 * @param allowCapacityOverload <ul>
	 *            <li><code>true</code> or <code>false</code> if you want to enforce it
	 *            <li><code>null</code> if you want to use the default one
	 *            </ul>
	 * @return qty which was actually added in <code>qtyToAdd</code>'s UOM. It'source it's in internal UOM.
	 */
	Quantity addQty(Quantity qtyToAdd, Boolean allowCapacityOverload);

	/**
	 * Remove qty from this capacity.
	 *
	 * @param qtyToRemove
	 * @return qty which was actually removed in <code>qtyToRemove</code>'s UOM. It'source it's in internal UOM.
	 */
	Quantity removeQty(Quantity qtyToRemove);

	/**
	 * Remove qty from this capacity.
	 *
	 * @param qtyToRemove
	 * @param allowNegativeCapacityOverride <ul>
	 *            <li><code>true</code> or <code>false</code> if you want to enforce it
	 *            <li><code>null</code> if you want to use the default one
	 *            </ul>
	 * @return qty which was actually removed in <code>qtyToRemove</code>'s UOM. It'source it's in internal UOM.
	 */
	Quantity removeQty(Quantity qtyToRemove, Boolean allowNegativeCapacityOverride);

	/**
	 *
	 * @return current loaded qty
	 */
	BigDecimal getQty();

	/**
	 *
	 * @return free qty (available to add to this capacity)
	 */
	BigDecimal getQtyFree();
}
