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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.uom.api.Quantity;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

/**
 * Implementors are used to specify how much of a given product fits into "something", such as TU.
 * <p>
 * Hint: use {@link IHUCapacityBL} to get an instance.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUCapacityDefinition
{
	BigDecimal INFINITY = Quantity.QTY_INFINITE;

	/**
	 * Signals the logic to use the default capacity.
	 */
	BigDecimal DEFAULT = new BigDecimal(Long.MAX_VALUE);

	/**
	 * @return true, if capacity is unlimited
	 */
	boolean isInfiniteCapacity();

	/**
	 *
	 * @return true if negative capacity is allowed
	 */
	boolean isAllowNegativeCapacity();

	/**
	 * Will throw "Assumption Failure" if capacity is unlimited (should never be called without first checking with {@link #isInfiniteCapacity()} first)
	 *
	 * @return capacity
	 */
	BigDecimal getCapacity();

	I_M_Product getM_Product();

	/**
	 * @return uom
	 */
	I_C_UOM getC_UOM();
}
