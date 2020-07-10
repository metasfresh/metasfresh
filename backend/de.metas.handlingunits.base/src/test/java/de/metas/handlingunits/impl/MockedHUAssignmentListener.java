package de.metas.handlingunits.impl;

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


import org.adempiere.util.lang.IReference;
import org.junit.Assert;

import de.metas.handlingunits.IHUAssignmentListener;
import de.metas.handlingunits.exceptions.HUNotAssignableException;
import de.metas.handlingunits.model.I_M_HU;

public class MockedHUAssignmentListener implements IHUAssignmentListener
{
	public static enum ExpectationType
	{
		Assign,
		UnAssign,
	}

	private ExpectationType expectationType = null;
	private I_M_HU expectedHU;
	private Object expectedModel;
	private boolean expectationMatched = false;

	@Override
	public void assertAssignable(final I_M_HU hu, final Object model, final String trxName) throws HUNotAssignableException
	{
		assertExpectation(ExpectationType.Assign, hu, model);
		// don't set expectationMatched, we will set it on onHUAssigned
	}

	@Override
	public void onHUAssigned(final I_M_HU hu, final Object model, final String trxName)
	{
		assertExpectation(ExpectationType.Assign, hu, model);
		expectationMatched = true;
	}

	@Override
	public void onHUUnassigned(final IReference<I_M_HU> huRef, final IReference<Object> modelRef, final String trxName)
	{
		final I_M_HU hu = huRef.getValue();
		final Object model = modelRef.getValue();
		assertExpectation(ExpectationType.UnAssign, hu, model);
		expectationMatched = true;
	}

	public void expectHUAssign(final I_M_HU hu, final Object model)
	{
		expectationType = ExpectationType.Assign;
		expectedHU = hu;
		expectedModel = model;
	}

	public void expectHUUnassign(final I_M_HU hu, final Object model)
	{
		expectationType = ExpectationType.UnAssign;
		expectedHU = hu;
		expectedModel = model;
	}

	private void assertExpectation(final ExpectationType expectationTypeActual, final I_M_HU actualHU, final Object actualModel)
	{
		Assert.assertEquals("Invalid ExpectationType", expectationType, expectationTypeActual);
		Assert.assertEquals("Invalid HU", expectedHU, actualHU);
		Assert.assertEquals("Invalid Model", expectedModel, actualModel);
	}

	public void assertExpectationsMatched()
	{
		Assert.assertTrue("Expectations shall be matched", expectationMatched);
	}
}
