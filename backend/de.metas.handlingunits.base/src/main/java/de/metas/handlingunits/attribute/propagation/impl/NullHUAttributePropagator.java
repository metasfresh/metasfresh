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


import org.compiere.util.Util;

import de.metas.handlingunits.attribute.propagation.IHUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

/**
 * {@link IHUAttributePropagator} which does nothing.
 *
 * @author tsa
 *
 */
public final class NullHUAttributePropagator extends AbstractHUAttributePropagator
{
	public static final transient NullHUAttributePropagator instance = new NullHUAttributePropagator();

	public static boolean isNull(final IHUAttributePropagator propagator)
	{
		return propagator == null
				|| Util.same(propagator, instance);
	}

	private NullHUAttributePropagator()
	{
		super();
	}

	/**
	 * Always returns <code>true</code>.
	 */
	@Override
	public boolean isCompatible(final IHUAttributePropagator otherPropagator)
	{
		return true;
	}

	/**
	 * Always returns {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_NoPropagation}.
	 */
	@Override
	public String getPropagationType()
	{
		return X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation;
	}

	/**
	 * Always returns {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_NoPropagation}.
	 */
	@Override
	public String getReversalPropagationType()
	{
		return getPropagationType(); // same
	}

	/**
	 * Does nothing
	 */
	@Override
	public void propagateValue(final IHUAttributePropagationContext propagationContext, final IAttributeStorage attributeSet, final Object value)
	{
		// nothing
		// we are not throwing exception because it's perfectly OK to do nothing
	}
}
