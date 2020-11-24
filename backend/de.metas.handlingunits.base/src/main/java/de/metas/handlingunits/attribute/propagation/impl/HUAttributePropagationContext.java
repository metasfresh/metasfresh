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

import java.util.Collections;
import java.util.Map;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.impl.DefaultAttributeValueContext;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Util;

import de.metas.handlingunits.attribute.propagation.IHUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import lombok.NonNull;

public final class HUAttributePropagationContext
		extends DefaultAttributeValueContext
		implements IHUAttributePropagationContext
{
	private final IHUAttributePropagationContext parent;
	private final IAttributeStorage attributeStorage;
	private final IHUAttributePropagator attributePropagator;
	private final I_M_Attribute attribute;

	private boolean updateStorageValue = true; // defaults
	private boolean valueUpdateInvocation = false;

	/**
	 * Created a new context which has no parent
	 *
	 * @param attributeStorage
	 * @param attributePropagator
	 * @param attribute
	 */
	public HUAttributePropagationContext(
			final IAttributeStorage attributeStorage,
			final IHUAttributePropagator attributePropagator,
			final I_M_Attribute attribute)
	{
		this(
				IHUAttributePropagationContext.NULL, // no parent context
				attributeStorage,
				attributePropagator,
				attribute,
				Collections.<String, Object> emptyMap() // parameters
		);
	}

	/**
	 * To be used by {@link #cloneForPropagation(IAttributeStorage, I_M_Attribute, IHUAttributePropagator)}.
	 *
	 * @param parent
	 * @param attributeStorage
	 * @param attributePropagator
	 * @param attribute
	 */
	private HUAttributePropagationContext(final IHUAttributePropagationContext parent,
			final IAttributeStorage attributeStorage,
			final IHUAttributePropagator attributePropagator,
			final I_M_Attribute attribute,
			final Map<String, Object> parameters)
	{
		super(parameters);

		this.parent = parent;
		this.attributeStorage = attributeStorage;
		this.attributePropagator = attributePropagator;
		this.attribute = attribute;
	}

	@Override
	public String toString()
	{
		return new StringBuilder(getClass().getSimpleName())
				.append("[")
				// .append("Parent=" .append(parent;
				.append(attributePropagator).append(", ")
				.append(attributeStorage).append(", ")
				.append(attribute).append(", ")
				.append("UpdateStorageValue=").append(updateStorageValue).append(", ")
				.append("ValueUpdateInvocation=").append(valueUpdateInvocation)
				.append("]")
				.toString();
	}

	@Override
	public IAttributeStorage getAttributeStorage()
	{
		return attributeStorage;
	}

	@Override
	public IHUAttributePropagator getPropagator()
	{
		return attributePropagator;
	}

	@Override
	public I_M_Attribute getAttribute()
	{
		return attribute;
	}

	@Override
	public AttributeCode getAttributeCode()
	{
		return AttributeCode.ofString(getAttribute().getValue());
	}

	@Override
	public IHUAttributePropagationContext getParent()
	{
		return parent;
	}

	@Override
	public void setUpdateStorageValue(final boolean updateStorageValue)
	{
		this.updateStorageValue = updateStorageValue;
	}

	@Override
	public boolean isUpdateStorageValue()
	{
		return updateStorageValue;
	}

	@Override
	public void setValueUpdateInvocation()
	{
		valueUpdateInvocation = true;
	}

	@Override
	public boolean isValueUpdateInvocation()
	{
		return valueUpdateInvocation;
	}

	@Override
	public boolean isValueUpdatedBefore()
	{
		return isValueUpdatedBefore(getAttributeCode());
	}

	@Override
	public boolean isValueUpdatedBefore(final AttributeCode attributeCode)
	{
		return getLastPropagatorOrNull(attributeCode) != null;
	}

	@Override
	public IHUAttributePropagator getLastPropagatorOrNull(@NonNull final AttributeCode attributeCode)
	{
		// Iterate up chain of parents, starting with the parent context. for each parent context, we check if the attribute was updated in that context
		// NOTE: we are skipping current node because we want to check if that attribute was updated before
		for (IHUAttributePropagationContext currentParentContext = parent; currentParentContext != null; currentParentContext = currentParentContext.getParent())
		{
			if (!currentParentContext.isValueUpdateInvocation())
			{
				continue;
			}

			final IAttributeStorage currentAttributeStorage = currentParentContext.getAttributeStorage();
			if (!currentAttributeStorage.equals(attributeStorage))
			{
				continue;
			}

			final AttributeCode currentAttributeCode = currentParentContext.getAttributeCode();
			if (!AttributeCode.equals(currentAttributeCode, attributeCode))
			{
				continue;
			}

			return currentParentContext.getPropagator();
		}

		return null;
	}

	@Override
	public IAttributeValueContext copy()
	{
		return cloneForPropagation(getAttributeStorage(), getAttribute(), getPropagator());
	}

	@Override
	public IHUAttributePropagationContext cloneForPropagation(final IAttributeStorage attributeStorage, final I_M_Attribute attribute, final IHUAttributePropagator propagatorToUse)
	{
		final HUAttributePropagationContext propagationContextClone = new HUAttributePropagationContext(this, attributeStorage, propagatorToUse, attribute, getParameters());
		return propagationContextClone;
	}

	@Override
	public boolean isExternalInput()
	{
		// NOTE: we consider External Input if this is the first context created (i.e. has no parents => no previous calls)
		return Util.same(getParent(), IHUAttributePropagationContext.NULL);
	}
}
