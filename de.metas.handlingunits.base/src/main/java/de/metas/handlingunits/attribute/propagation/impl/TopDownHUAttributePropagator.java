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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.propagation.IHUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagatorFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitResult;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.MutableAttributeSplitRequest;
import de.metas.handlingunits.attribute.strategy.impl.NullSplitterStrategy;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;

public class TopDownHUAttributePropagator extends AbstractHUAttributePropagator
{
	@Override
	public String getPropagationType()
	{
		return X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown;
	}

	@Override
	public String getReversalPropagationType()
	{
		return X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp;
	}

	/**
	 * Sets the value to the given <code>attributeSet</code> and propagate it to the set's children as returned by {@link IAttributeStorage#getChildAttributeStorages()}.<br>
	 * To distribute the given <code>value</code>, it uses a {@link IAttributeSplitterStrategy}.
	 */
	@Override
	public void propagateValue(final IHUAttributePropagationContext propagationContext, final IAttributeStorage attributeSet, final Object value)
	{
		final IHUAttributePropagator ctxPropagator = propagationContext.getPropagator();
		Check.assume(ctxPropagator.isCompatible(this), "Propagator " + this.getClass() + " is compatible with " + ctxPropagator.getClass());

		setValue(propagationContext, attributeSet, value);
	}

	@Override
	public String toString()
	{
		return "TopDownHUAttributePropagator []";
	}

	/**
	 * First sets the value, then propagates it, using the {@link IAttributeSplitterStrategy} assigned to the given <code>addtributeSet</code> and attribute.
	 *
	 * @param propagationContext
	 * @param attributeSet
	 * @param value
	 */
	private void setValue(final IHUAttributePropagationContext propagationContext, final IAttributeStorage attributeSet, final Object value)
	{
		final I_M_Attribute attribute = propagationContext.getAttribute();
		if (propagationContext.isUpdateStorageValue()
				&& attributeSet.hasAttribute(attribute))
		{
			// NOTE: we assume that remaining value to be set on this level is same as given value because splitting does not modify the value on this level
			final Object valueRemaining = value;

			// Set the attribute value on this level
			setStorageValue(propagationContext, attributeSet, attribute, valueRemaining);
		}

		// Propagate down if necessary
		propagateToChildren(propagationContext, attributeSet, value);
	}

	/**
	 * Also see the javadoc at {@link #propagateValue(IHUAttributePropagationContext, IAttributeStorage, Object)}.
	 *
	 * @param attributeSet
	 * @param attribute
	 * @param value
	 */
	private void propagateToChildren(final IHUAttributePropagationContext propagationContext, final IAttributeStorage attributeSet, final Object value)
	{
		final I_M_Attribute attribute = propagationContext.getAttribute();
		final IAttributeSplitterStrategy splitterStrategy = getAttributeSplitterStrategy(attributeSet, attribute);
		final IHUAttributePropagatorFactory huAttributePropagatorFactory = getHUAttributePropagatorFactory();

		//
		// Fetch ALL Child attributes
		final boolean loadIfNeeded = true;
		final Collection<IAttributeStorage> childrenAttributeSetsAll = attributeSet.getChildAttributeStorages(loadIfNeeded);

		//
		// Check which of our child attributes are aware of propagation and build a list with them
		final List<IAttributeStorage> childrenAttributeSets = new ArrayList<IAttributeStorage>(childrenAttributeSetsAll.size());
		final List<IHUAttributePropagator> childrenAttributeSetPropagators = new ArrayList<IHUAttributePropagator>(childrenAttributeSetsAll.size());
		
		for (final IAttributeStorage childAttributeSet : childrenAttributeSetsAll)
		{
			IHUAttributePropagator childPropagator = propagationContext.getPropagator();

			//
			// Fallback to the child AS's propagator if the propagation context's propagator is null
			if (NullHUAttributePropagator.isNull(childPropagator))
			{
				childPropagator = huAttributePropagatorFactory.getPropagator(childAttributeSet, attribute);
			}

			//
			// We don't have a compatible attribute propagator at all
			if (!childPropagator.isCompatible(this))
			{
				continue;
			}

			//
			// We don't have a splitter strategy and we're in a reverse propagation
			if (NullSplitterStrategy.instance.equals(splitterStrategy)
					&& childAttributeSet.getPropagationType(attribute).equals(childPropagator.getReversalPropagationType()))
			{
				//
				// We don't propagate in this case because we'll only get null values
				continue;
			}

			childrenAttributeSets.add(childAttributeSet);
			childrenAttributeSetPropagators.add(childPropagator);
		}

		//
		// Create initial Split Request
		final MutableAttributeSplitRequest splitRequest = new MutableAttributeSplitRequest(attributeSet, childrenAttributeSets, attribute);
		splitRequest.setValueInitial(value);
		splitRequest.setValueToSplit(value);

		for (int i = 0; i < childrenAttributeSets.size(); i++)
		{
			final IAttributeStorage childAttributeSet = childrenAttributeSets.get(i);

			final IHUAttributePropagator propagator = childrenAttributeSetPropagators.get(i);

			splitRequest.setAttributeStorageCurrent(childAttributeSet);
			splitRequest.setAttributeStorageCurrentIndex(i);

			final IAttributeSplitResult result = splitterStrategy.split(splitRequest);

			final IHUAttributePropagationContext propagationContextToUse = propagationContext.cloneForPropagation(childAttributeSet, attribute, propagator);
			propagator.propagateValue(propagationContextToUse, childAttributeSet, result.getSplitValue());

			//
			//
			final Object remainingValue;
			if (childAttributeSet.hasAttribute(attribute))
			{
				final Object valueSet = childAttributeSet.getValue(attribute);
				if (Check.equals(valueSet, result.getSplitValue()))
				{
					remainingValue = result.getRemainingValue();
				}
				else
				{
					remainingValue = splitterStrategy.recalculateRemainingValue(result, valueSet);
				}
			}
			else
			{
				remainingValue = result.getRemainingValue();
			}

			//
			// Update split request
			splitRequest.setValueToSplit(remainingValue);
		}
	}

	private IAttributeSplitterStrategy getAttributeSplitterStrategy(final IAttributeStorage attributeSet, final I_M_Attribute attribute)
	{
		return attributeSet.retrieveSplitterStrategy(attribute);
	}
}
