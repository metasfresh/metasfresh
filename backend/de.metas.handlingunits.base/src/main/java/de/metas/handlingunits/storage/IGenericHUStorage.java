package de.metas.handlingunits.storage;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Generic Handling Unit Storage.
 *
 * Implementations of this interface can be at any level of a given HU (e.g. HU Level, HU Item level etc).
 *
 * @author tsa
 *
 */
public interface IGenericHUStorage
{
	/**
	 *
	 * @return parent storage or null
	 */
	IGenericHUStorage getParentStorage();

	/**
	 * Add or removed given <code>qty</code> to storage.
	 *
	 * @param productId
	 * @param qty the qty to add (or to remove, if negative)
	 * @param uom qty's UOM
	 */
	void addQty(ProductId productId, BigDecimal qty, I_C_UOM uom);

	/**
	 * @return storage qty for <code>product</code> in <code>uom</code> unit of measure
	 */
	BigDecimal getQty(ProductId productId, I_C_UOM uom);

	default Quantity getQuantity(ProductId productId, I_C_UOM uom)
	{
		return Quantity.of(getQty(productId, uom), uom);
	}

	Optional<Quantity> getQuantity(ProductId productId);

	/**
	 *
	 * @return true if storage is empty
	 */
	boolean isEmpty();

	/**
	 *
	 * @param productId
	 * @return true if storage is empty for given product
	 */
	boolean isEmpty(ProductId productId);

	/**
	 * @return true if this is a virtual storage (i.e. a storage for a virtual HU, HU Item etc)
	 */
	boolean isVirtual();
}
