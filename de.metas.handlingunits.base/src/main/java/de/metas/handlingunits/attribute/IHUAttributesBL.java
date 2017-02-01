package de.metas.handlingunits.attribute;

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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;

public interface IHUAttributesBL extends ISingletonService
{

	/**
	 * Gets underlying {@link I_M_HU} from given <code>attributeSet</code>.
	 *
	 * If there is no underlying HU, this method will throw an exception
	 *
	 * @param attributeSet
	 * @return underlying HU; never return <code>null</code>
	 * @throws IllegalArgumentException if there is no underlying HU
	 */
	I_M_HU getM_HU(IAttributeSet attributeSet);

	/**
	 * Gets underlying {@link I_M_HU} from given <code>attributeSet</code>.
	 *
	 * If there is no underlying HU, this method will return <code>null</code>.
	 *
	 * @param attributeSet
	 * @return underlying HU or <code>null</code> if given <code>attributeSet</code> does not support HUs.
	 */
	I_M_HU getM_HU_OrNull(IAttributeSet attributeSet);

	/**
	 * Iterates the HU-tree of the given HU and sets the given attribute to the given attributeValue.
	 *
	 * @param hu
	 * @param attribute
	 * @param attributeValue
	 * @param onlyHUStatus may be <code>null</code> or empty. Otherwise, only HUs with the given status are updated. However, all HUs are iterated.
	 */
	void updateHUAttributeRecursive(
			I_M_HU hu,
			I_M_Attribute attribute,
			Object attributeValue,
			String onlyHUStatus);
}
