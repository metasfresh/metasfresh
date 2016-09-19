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


import java.util.Collection;

import org.adempiere.util.Check;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.IAttributeValueListener;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

public class BottomUpHUAttributePropagator extends AbstractHUAttributePropagator
{
	/**
	 * @return {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_BottomUp}.
	 */
	@Override
	public String getPropagationType()
	{
		return X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp;
	}

	/**
	 * @return {@link X_M_HU_PI_Attribute#PROPAGATIONTYPE_TopDown}.
	 */
	@Override
	public String getReversalPropagationType()
	{
		return X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown;
	}

	/**
	 * Gets the the attribute specified within the given <code>propagationContext</code> and calls {@link #setStorageValue(IAttributeStorage, I_M_Attribute, Object)} with that attribute, the given
	 * <code>attributeSet</code> and the given <code>value</code>. Note that this might cause {@link IAttributeValueListener}s to be fired. Then propagates up to the given <code>attributeSet</code>'s
	 * parent.<br>
	 * After having set the value to the current level, the method propagates it to the parent level, using the current attribute's {@link IAttributeAggregationStrategy}, by aggregating its own
	 * attribute storage value as well as its siblings' values (for the given <code>propagationContext</code>'s attribute).
	 *
	 *
	 */
	@Override
	public void propagateValue(final IHUAttributePropagationContext propagationContext, final IAttributeStorage attributeSet, final Object value)
	{
		final IHUAttributePropagator ctxPropagator = propagationContext.getPropagator();
		Check.assume(ctxPropagator.isCompatible(this),
				"Propagator {} is compatible with {}", this.getClass(), ctxPropagator.getClass());

		setValue(propagationContext, attributeSet, value);
	}

	private void setValue(final IHUAttributePropagationContext propagationContext, final IAttributeStorage attributeSet, final Object value)
	{
		//
		// Set value on this attribute
		final I_M_Attribute attribute = propagationContext.getAttribute();
		if (propagationContext.isUpdateStorageValue()
				&& attributeSet.hasAttribute(attribute))
		{
			setStorageValue(propagationContext, attributeSet, attribute, value);
		}

		// Propagate up if necessary
		propagateToParent(propagationContext, attributeSet, value);
	}

	private void propagateToParent(final IHUAttributePropagationContext propagationContext, final IAttributeStorage attributeSet, final Object value)
	{
		// Get aggregation strategy on this level
		// TODO: not sure yet, but i think we shall use parent's aggregation strategy
		final I_M_Attribute attribute = propagationContext.getAttribute();
		final IAttributeAggregationStrategy aggregationStrategy = attributeSet.retrieveAggregationStrategy(attribute);

		final IAttributeStorage parentAttributeSet = attributeSet.getParentAttributeStorage();
		// There is no parent => no point to aggregate child attributes
		if (NullAttributeStorage.instance.equals(parentAttributeSet))
		{
			return;
		}

		// Parent does not have our attribute => nothing to aggregate
		if (!parentAttributeSet.hasAttribute(attribute))
		{
			return;
		}

		IHUAttributePropagator parentPropagator = propagationContext.getPropagator();

		//
		// Fallback to the parent AS's propagator if the propagation context's propagator is null
		if (NullHUAttributePropagator.isNull(parentPropagator))
		{
			parentPropagator = getHUAttributePropagatorFactory().getPropagator(parentAttributeSet, attribute);
		}

		//
		// We don't have a compatible attribute propagator at all
		if (!parentPropagator.isCompatible(this))
		{
			return;
		}

		//
		// We don't have an aggregation strategy and we're in a reverse propagation
		if (NullAggregationStrategy.instance.equals(aggregationStrategy)
				&& parentAttributeSet.getPropagationType(attribute).equals(parentPropagator.getReversalPropagationType()))
		{
			//
			// We don't propagate in this case because we'll only get null values
			return;
		}

		//
		// Initialize the new value with parent's seed value
		final Object parentValueNew = aggregateNewParentValue(aggregationStrategy,
				parentAttributeSet,
				parentAttributeSet.getValueInitial(attribute),
				attributeSet,
				value,
				attribute);

		//
		// Propagate up
		final IHUAttributePropagationContext propagationContextToUse = propagationContext.cloneForPropagation(parentAttributeSet, attribute, parentPropagator);
		parentPropagator.propagateValue(propagationContextToUse, parentAttributeSet, parentValueNew);
	}

	/**
	 * Aggregates new parent value recursively among children (i.e if a child doesn't have an attribute, look deeper in that child's children).
	 *
	 * @param aggregationStrategy
	 * @param parentAttributeSet
	 * @param parentValueInitial
	 * @param attributeSet
	 * @param value
	 * @param attribute
	 * @return
	 */
	private final Object aggregateNewParentValue(final IAttributeAggregationStrategy aggregationStrategy,
			final IAttributeStorage parentAttributeSet,
			final Object parentValueInitial,
			final IAttributeStorage attributeSet,
			final Object value,
			final I_M_Attribute attribute)
	{
		Object parentValueNew = parentValueInitial;

		// Run through all current level attribute sets (including the one from parameters) and aggregate each to the current value
		final boolean loadIfNeeded = true;
		final Collection<IAttributeStorage> sameLevelAttributeSets = parentAttributeSet.getChildAttributeStorages(loadIfNeeded);
		for (final IAttributeStorage levelAttributeSet : sameLevelAttributeSets)
		{
			//
			// Do not try to aggregate from a storage which doesn't have that attribute. instead, try it's children
			if (!levelAttributeSet.hasAttribute(attribute))
			{
				parentValueNew = aggregateNewParentValue(aggregationStrategy, levelAttributeSet, parentValueNew, attributeSet, value, attribute);

				continue;
			}

			//
			// For the same-level attribute set, aggregate the given value and skip recalculation
			if (levelAttributeSet.getId().equals(attributeSet.getId()))
			{
				parentValueNew = aggregationStrategy.aggregate(attribute, parentValueNew, value);
			}
			//
			// For the others, just aggregate their value
			else
			{
				final Object levelAttributeValue = levelAttributeSet.getValue(attribute);
				parentValueNew = aggregationStrategy.aggregate(attribute, parentValueNew, levelAttributeValue);
			}
		}

		return parentValueNew;
	}

	@Override
	public String toString()
	{
		return "BottomUpHUAttributePropagator []";
	}
}
