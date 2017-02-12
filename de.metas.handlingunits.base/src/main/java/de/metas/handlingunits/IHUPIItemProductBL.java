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

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;

public interface IHUPIItemProductBL extends ISingletonService
{
	List<I_M_HU_PI_Item_Product> getCompatibleItemDefProducts(I_M_HU_PI_Version version, I_M_Product product);

	I_M_HU_PI_Item_Product getCompatibleItemDefProduct(I_M_HU_PI_Version version, I_M_Product product);

	/**
	 * @param version
	 * @param product
	 * @return <code>true</code> if product is available in the version (or IsAllowAnyProduct), false otherwise
	 */
	boolean isCompatibleProduct(I_M_HU_PI_Version version, I_M_Product product);

	void deleteForItem(I_M_HU_PI_Item packingInstructionsItem);

	boolean isCompatibleProduct(I_M_HU hu, I_M_Product product);

	/**
	 * Returns <code>true</code> if the given <code>piip</code> is the "virtual" one, i.e. the one referencing the virtual packing instruction.
	 *
	 * @param piip
	 * @return
	 */
	boolean isVirtualHUPIItemProduct(I_M_HU_PI_Item_Product piip);

	/**
	 * @return builder used to create the display name
	 */
	IHUPIItemProductDisplayNameBuilder buildDisplayName();

	/**
	 * Builds and set Name and Description field.
	 *
	 * Name will be build using {@link IHUPIItemProductDisplayNameBuilder#buildItemProductDisplayName()} via {@link #buildDisplayName()}.
	 *
	 * @param itemProduct
	 * @see #buildDisplayName()
	 */
	void setNameAndDescription(I_M_HU_PI_Item_Product itemProduct);

}
