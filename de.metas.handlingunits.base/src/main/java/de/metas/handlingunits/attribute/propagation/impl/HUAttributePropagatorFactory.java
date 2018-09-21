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


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagatorFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.util.Check;

public class HUAttributePropagatorFactory implements IHUAttributePropagatorFactory
{
	private final Map<String, IHUAttributePropagator> propagators = new ConcurrentHashMap<String, IHUAttributePropagator>();

	public HUAttributePropagatorFactory()
	{
		// Register defaults
		registerPropagator(new TopDownHUAttributePropagator());
		registerPropagator(new BottomUpHUAttributePropagator());
		registerPropagator(new NoPropagationHUAttributePropagator());
	}

	@Override
	public void registerPropagator(final IHUAttributePropagator propagator)
	{
		propagator.setHUAttributePropagatorFactory(this);
		propagators.put(propagator.getPropagationType(), propagator);
	}

	@Override
	public IHUAttributePropagator getPropagator(final IAttributeStorage attributeSet, final I_M_Attribute attribute)
	{
		final String propagationType = attributeSet.getPropagationType(attribute);
		return getPropagator(propagationType);
	}

	@Override
	public IHUAttributePropagator getPropagator(final String propagationType)
	{
		Check.assumeNotNull(propagationType, "propagationType not null");

		final IHUAttributePropagator propagator = propagators.get(propagationType);
		if (propagator == null)
		{
			throw new IllegalStateException("No propagator was found for type: " + propagationType);
		}

		return propagator;
	}

	@Override
	public IHUAttributePropagator getReversalPropagator(final String propagationType)
	{
		final IHUAttributePropagator propagator = getPropagator(propagationType);
		return getReversalPropagator(propagator);
	}

	@Override
	public IHUAttributePropagator getReversalPropagator(final IHUAttributePropagator propagator)
	{
		final String reversalPropagationType = propagator.getReversalPropagationType();
		return getPropagator(reversalPropagationType);
	}
}
