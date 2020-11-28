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
import java.util.List;

import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

/**
 * Implementations of this interface are handling document lines which are NOT about packing materials but which provide packing material informations.
 *
 * Based on these (regular) lines, the {@link IPackingMaterialDocumentLine} will be created
 *
 * @author tsa
 */
public interface IPackingMaterialDocumentLineSource
{
	/**
	 *
	 * @return packing materials list contained in this source line
	 */
	List<I_M_HU_PackingMaterial> getM_HU_PackingMaterials();

	/**
	 *
	 * @return amount of packing materials
	 */
	BigDecimal getQty();

}
