package de.metas.handlingunits.attribute.propagation;

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


import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;

public interface IHUAttributePropagationContext extends IAttributeValueContext
{
	IHUAttributePropagationContext NULL = null;

	/**
	 * @return parent context or null (i.e create a hierarchy of context calls)
	 */
	IHUAttributePropagationContext getParent();

	/**
	 * @return {@link IAttributeStorage} of this context
	 */
	IAttributeStorage getAttributeStorage();

	/**
	 * @return propagator which will be used further
	 */
	IHUAttributePropagator getPropagator();

	/**
	 * @return attribute used to be propagated on
	 */
	I_M_Attribute getAttribute();

	/**
	 * @return true if <code>attribute</code>'s value shall be changed on this storage too and {@code false} if it shall only be propagated. 
	 */
	boolean isUpdateStorageValue();

	/**
	 * Force propagator to not update initial storage value if <code>updateStorageValue=false</code>
	 *
	 * @param updateStorageValue
	 */
	void setUpdateStorageValue(boolean updateStorageValue);

	/**
	 * Mark that in this context we have set the attribute value. We use this mark to avoid recursion. Physically sets <code>valueUpdateInvocation=true</code> permanently for this context.
	 */
	void setValueUpdateInvocation();

	/**
	 * @return true if the context is configured to avoid setting the value multiple times for the same {@link I_M_Attribute} / {@link IAttributeStorage} combination (avoid recursion)
	 */
	boolean isValueUpdateInvocation();

	/**
	 * @return true if the current-in-context {@link I_M_Attribute} was already updated for this {@link IAttributeStorage}
	 */
	boolean isValueUpdatedBefore();

	/**
	 * Iterates the chain for parent contexts (starting with <code>this</code> instance's parent) and checks for each parent context if.
	 * <ul>
	 * <li>the parent context's {@link IHUAttributePropagationContext#isValueUpdateInvocation()} method returns <code>true</code> (also see {@link #setValueUpdateInvocation()})</li>
	 * <li><code>this</code> context's attribute storage is equal to the parent context's storage</li>
	 * <li>the given <code>attribute</code>'s ID is the same as the parent context's attribute's ID</li>
	 * <ul>
	 * If all three conditions are met, then the method returns <code>true</code>.
	 *
	 * @param attribute the attribute to check against. May or may not be the same as <code>this</code> context's own attribute.
	 *
	 * @return true if the given {@link I_M_Attribute} was already updated for this {@link IAttributeStorage}
	 */
	boolean isValueUpdatedBefore(I_M_Attribute attribute);

	/**
	 * @param attribute
	 * @return last propagator used for propagating given attribute
	 */
	IHUAttributePropagator getLastPropagatorOrNull(I_M_Attribute attribute);

	/**
	 * Create a clone of a context with it's <code>parent=this</code>, given propagator, and with <code>updateStorageValue=true</code>
	 *
	 * @param propagatorToUse
	 * @return propagationContext which was cloned
	 */
	IHUAttributePropagationContext cloneForPropagation(IAttributeStorage attributeStorage, I_M_Attribute attribute, IHUAttributePropagator propagatorToUse);

	/**
	 * @return true if this context (for changing an attribute value) was created from outside of this attribute storages hierarchy (i.e. User Input, Attribute Transfer etc)
	 */
	boolean isExternalInput();
}
