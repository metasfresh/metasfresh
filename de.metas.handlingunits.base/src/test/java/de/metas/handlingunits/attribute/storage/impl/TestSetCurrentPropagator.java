package de.metas.handlingunits.attribute.storage.impl;

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


import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Attribute;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagator;
import de.metas.handlingunits.attribute.propagation.IHUAttributePropagatorFactory;
import de.metas.handlingunits.attribute.propagation.impl.HUAttributePropagationContext;
import de.metas.util.Services;

public class TestSetCurrentPropagator
{
	private AbstractAttributeStorage attributeStorage;

	/**
	 * Test attributePropagationContext hierarchy & behavior for both when originally setting it and when rolling it back.<br>
	 * Will fail the last check with {@link AdempiereException} because the parent is incompatible.
	 */
	@Test(expected = AdempiereException.class)
	public void testSetCurrentPropagationContext()
	{
		final HUTestHelper helper = new HUTestHelper();

		final I_M_Attribute attribute = helper.attr_CountryMadeIn;
		attributeStorage = (AbstractAttributeStorage)helper.createAttributeSetStorage();

		//
		// Just use the attribute's propagator (we don't really need it - mock)
		final IHUAttributePropagator attributePropagator = Services.get(IHUAttributePropagatorFactory.class).getPropagator(attributeStorage, attribute);

		//
		// Create original propagation context
		final IHUAttributePropagationContext huAttributePropagationContext = new HUAttributePropagationContext(attributeStorage, attributePropagator, attribute);
		attributeStorage.setCurrentPropagationContext(huAttributePropagationContext, true); // validateParentContext

		//
		// Create a child propagation context for it & set it in hierarchy
		final IHUAttributePropagationContext childHUAttributePropagationContext = huAttributePropagationContext.cloneForPropagation(attributeStorage, attribute, attributePropagator);
		attributeStorage.setCurrentPropagationContext(childHUAttributePropagationContext, true); // validateParentContext

		//
		// Make sure that the hierarchy is kept
		Assert.assertSame(huAttributePropagationContext, childHUAttributePropagationContext.getParent());

		//
		// Roll the childPropagationContext back & ensure that the original one is set
		attributeStorage.setCurrentPropagationContext(huAttributePropagationContext, false); // validateParentContext (rollback)
		Assert.assertSame(attributeStorage.getCurrentPropagationContextOrNull(), huAttributePropagationContext);

		//
		// Set the childPropagationContext back and make sure the test fails with exception if we want to rollback with validateParentContext=true
		//
		// First set the child back
		attributeStorage.setCurrentPropagationContext(childHUAttributePropagationContext, true);

		// Will fail: we cannot assign this context because the childHUAttributePropagationContext is NOT it's parent
		attributeStorage.setCurrentPropagationContext(huAttributePropagationContext, true);
	}

	/**
	 * After test execution (since the rest of the attributeStorage algorithms rely on it being normally rolled back), clear the propagation context for subsequent unit tests.
	 */
	@After
	public void clearPropagationContext()
	{
		attributeStorage.setCurrentPropagationContext(IHUAttributePropagationContext.NULL, false); // validateParentContext (we don't want validation)
	}
}
