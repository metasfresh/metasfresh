package de.metas.handlingunits.allocation.transfer;

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

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

/**
 * Split definition for Transport Unit / Loading Unit.
 */
public interface IHUSplitDefinition
{
	/**
	 *
	 * @return Transport Unit PI
	 */
	I_M_HU_PI getTuPI();

	/**
	 * @return Loading Unit PI Item
	 */
	I_M_HU_PI_Item getLuPIItem();

	/**
	 * @return Loading Unit PI
	 */
	I_M_HU_PI getLuPI();

	/**
	 * @return Customer Unit Product
	 */
	I_M_Product getCuProduct();

	/**
	 * @return Customer Unit UOM
	 */
	I_C_UOM getCuUOM();

	/**
	 * e.g Tomatoes per IFCO
	 *
	 * @return Customer Unit per Trading Unit (TU capacity)
	 */
	BigDecimal getCuPerTU();

	/**
	 * e.g IFCOs per Palette
	 *
	 * @return Trading Unit per Loading Unit (LU capacity)
	 */
	BigDecimal getTuPerLU();

	/**
	 * @return Max Allowed LUs (e.g how many LUs can be split with this definition)
	 */
	BigDecimal getMaxLUToAllocate();
}
