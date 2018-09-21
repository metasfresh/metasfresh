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

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Builds the display name of a TU configuration.
 *
 * The built name will look like: QtyMovedTU / QtyOrderedTU x [ PI Item Product Display Name ].
 *
 * If the Qtys are not set the display name will display only "PI Item Product" part.
 *
 * @author tsa
 *
 */
public interface IHUPIItemProductDisplayNameBuilder
{
	/**
	 * Build the display name.
	 *
	 * @return
	 */
	String build();

	/**
	 * Build only the diplay name which is related to {@link I_M_HU_PI_Item_Product} (i.e. without quantities string etc).
	 *
	 * Even if {@link I_M_HU_PI_Item_Product#getName()} is not empty this method will build the string from scretch.
	 *
	 * @return
	 */
	String buildItemProductDisplayName();

	IHUPIItemProductDisplayNameBuilder setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPIItemProduct);
	
	IHUPIItemProductDisplayNameBuilder setM_HU_PI_Item_Product(final HUPIItemProductId id);

	IHUPIItemProductDisplayNameBuilder setQtyTUPlanned(final BigDecimal qtyTUPlanned);

	IHUPIItemProductDisplayNameBuilder setQtyTUPlanned(final int qtyTUPlanned);

	IHUPIItemProductDisplayNameBuilder setQtyTUMoved(final BigDecimal qtyTUMoved);

	IHUPIItemProductDisplayNameBuilder setQtyTUMoved(int qtyTUMoved);

	/**
	 * Sets Qty/HU. i.e. it will override the capacity that is displayed
	 * 
	 * @param qtyPerHU
	 */
	IHUPIItemProductDisplayNameBuilder setQtyCapacity(BigDecimal qtyPerHU);

	IHUPIItemProductDisplayNameBuilder setShowAnyProductIndicator(boolean showAnyProductIndicator);

}
