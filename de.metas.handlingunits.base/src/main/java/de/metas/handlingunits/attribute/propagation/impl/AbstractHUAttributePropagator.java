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


import org.slf4j.Logger;
import de.metas.logging.LogManager;

import java.util.Objects;

import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.util.Check;
import org.compiere.model.I_M_Attribute;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IAttributeValueListener;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagatorFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;

public abstract class AbstractHUAttributePropagator implements IHUAttributePropagator
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	private IHUAttributePropagatorFactory factory;

	@Override
	public final void setHUAttributePropagatorFactory(final IHUAttributePropagatorFactory factory)
	{
		Check.assumeNotNull(factory, "factory not null");
		this.factory = factory;
	}

	protected final IHUAttributePropagatorFactory getHUAttributePropagatorFactory()
	{
		Check.assumeNotNull(factory, "factory not null");
		return factory;
	}

	/**
	 * Compares the given <code>otherPropagator</code>'s propagation with this instance's propagation type and returns <code>true</code> if they are equal.
	 */
	@Override
	public boolean isCompatible(final IHUAttributePropagator otherPropagator)
	{
		// The parent propagator and "this" have compatible propagation types
		if (Objects.equals(otherPropagator.getPropagationType(), getPropagationType()))
		{
			return true;
		}

		return false;
	}

	/**
	 * Directly set the attribute's value to the given storage without any propagation. Note that this might cause {@link IAttributeValueListener}s to be fired
	 *
	 * @param attributeValueContext context in which listeners set attribute value
	 * @param attributeSet the attribute storage which contains the {@link IAttributeValue} to modify.
	 * @param attribute the attribute whose {@link IAttributeValue} within the <code>attributeSet</code> shall be changes
	 * @param newValue the <code>IAttributeValue</code>'s new value
	 *
	 * @see IAttributeValue#setValue(Object)
	 */
	protected final void setStorageValue(final IAttributeValueContext attributeValueContext, final IAttributeStorage attributeSet, final I_M_Attribute attribute, final Object newValue)
	{
		final IAttributeValue attributeValue = attributeSet.getAttributeValue(attribute);

		logger.debug("Setting INTERNAL attribute Value={} for {} on {}", new Object[] { newValue, attribute, attributeSet });
		attributeValue.setValue(attributeValueContext, newValue);
	}
}
