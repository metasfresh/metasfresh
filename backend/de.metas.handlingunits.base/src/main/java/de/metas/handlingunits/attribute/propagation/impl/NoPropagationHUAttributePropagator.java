package de.metas.handlingunits.attribute.propagation.impl;

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


import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.propagation.IHUAttributePropagationContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

public class NoPropagationHUAttributePropagator extends AbstractHUAttributePropagator
{
	/**
	 * @return {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_NoPropagation}.
	 */
	@Override
	public String getPropagationType()
	{
		return X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation;
	}

	/**
	 * @return {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_NoPropagation}.
	 */
	@Override
	public String getReversalPropagationType()
	{
		return getPropagationType(); // same
	}

	/**
	 * Gets the the attribute specified within the given <code>propagationContext</code> and calls {@link #setStorageValue(IAttributeStorage, I_M_Attribute, Object)} with that attribute, the given
	 * <code>attributeSet</code> and the given <code>value</code>. Does no propagation (neither up nor down).
	 *
	 */
	@Override
	public void propagateValue(final IHUAttributePropagationContext propagationContext, final IAttributeStorage attributeSet, final Object value)
	{
		//
		// Just set the value and nothing more
		final I_M_Attribute attribute = propagationContext.getAttribute();

		if (propagationContext.isUpdateStorageValue()
				&& attributeSet.hasAttribute(attribute))
		{
			setStorageValue(propagationContext, attributeSet, attribute, value);
		}
	}

	@Override
	public String toString()
	{
		return "NoPropagationHUAttributePropagator []";
	}

}
