package de.metas.handlingunits.attribute.propagation;

import org.adempiere.mm.attributes.spi.IAttributeValueCallout;

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


import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

/**
 * Implementors are responsible to propagate the values of {@link IAttributeValue} instances through a HU hierarchy.
 * Note: {@link IAttributeValueCallout} on the contrary are in charge of dealing with changes of different attributes of the same HU.
 *
 */
public interface IHUAttributePropagator
{
	/**
	 * Sets {@link IHUAttributePropagatorFactory}.
	 *
	 * NOTE: don't call it directly. It's called by API.
	 *
	 * @param factory
	 */
	void setHUAttributePropagatorFactory(IHUAttributePropagatorFactory factory);

	/**
	 * Check if this {@link IHUAttributePropagator} is compatible with it's parent. See the implementing subclasses' javadocs for implementation details.
	 *
	 * @param otherPropagator the propagator to compare with
	 * @return
	 */
	boolean isCompatible(IHUAttributePropagator otherPropagator);

	/**
	 * @return propagation type (see {@link X_M_HU_PI_Attribute}'s PROPAGATIONTYPE_*)
	 */
	String getPropagationType();

	/**
	 * @return reversal propagation type (see {@link X_M_HU_PI_Attribute}'s PROPAGATIONTYPE_*)
	 */
	String getReversalPropagationType();

	/**
	 * Set and propagate attribute value. 
	 * Note that propagation only happens if the respective attribute exists in the propagation target. If it doesn't exists, it is <b>not</b> created on the fly
	 *
	 * @param propagationContext
	 * @param attributeSet the attributeset to which the given <code>value</code> shall be set.
	 * @param value
	 */
	void propagateValue(IHUAttributePropagationContext propagationContext, IAttributeStorage attributeSet, Object value);
}
