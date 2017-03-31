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


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.model.I_M_HU;

public class HUAssignmentBLTest
{
	private Properties ctx;
	private String trxName;
	private IContextAware contextProvider;

	private IHUAssignmentBL huAssignmentBL;
	private IHUAssignmentDAO huAssignmentDAO;

	private I_Test record;
	private I_M_HU hu;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		//
		// Make sure Main handling units interceptor is registered
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new de.metas.handlingunits.model.validator.Main());

		//
		// BL under test
		huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		//
		// Setup ctx and trxName
		ctx = Env.getCtx();
		trxName = ITrx.TRXNAME_None;
		contextProvider = PlainContextAware.newWithTrxName(ctx, trxName);

		//
		// Create a dummy record
		record = InterfaceWrapperHelper.newInstance(I_Test.class, contextProvider);
		InterfaceWrapperHelper.save(record);

		//
		// Create a dummy HU
		hu = createHU();
	}

	private I_M_HU createHU()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, contextProvider);

		InterfaceWrapperHelper.save(hu);
		return hu;
	}

	/**
	 * Assert {@link #hu} is the only assigned HU to {@link #record}
	 */
	private void assertHUAssigned()
	{
		final List<I_M_HU> husAssignedActual = huAssignmentDAO.retrieveTopLevelHUsForModel(record);
		final List<I_M_HU> husAssignedExpected = Arrays.asList(hu);

		Assert.assertEquals("Assigned HUs does not match", husAssignedExpected, husAssignedActual);
	}

	/**
	 * Assert there are no HUs assigned to {@link #record}.
	 */
	private void assertNoHUsAssigned()
	{
		final List<I_M_HU> husAssignedActual = huAssignmentDAO.retrieveTopLevelHUsForModel(record);
		final List<I_M_HU> husAssignedExpected = Collections.emptyList();

		Assert.assertEquals("Assigned HUs does not match", husAssignedExpected, husAssignedActual);
	}

	/**
	 * Simple test to validate that assignment is made correctly and listeners are triggered
	 */
	@Test
	public void testAssign()
	{
		assertNoHUsAssigned();

		final MockedHUAssignmentListener listener = new MockedHUAssignmentListener();
		huAssignmentBL.registerHUAssignmentListener(listener);

		listener.expectHUAssign(hu, record);

		huAssignmentBL.assignHU(record, hu, trxName);

		listener.assertExpectationsMatched();

		assertHUAssigned();
	}

	/**
	 * Simple test to validate that un-assignment is made correctly and listeners are triggered
	 */
	@Test
	public void testUnassign()
	{
		assertNoHUsAssigned();

		//
		// Make sure HU is assigned to our record
		huAssignmentBL.assignHUs(record, Arrays.asList(hu), trxName);
		assertHUAssigned();

		final MockedHUAssignmentListener listener = new MockedHUAssignmentListener();
		huAssignmentBL.registerHUAssignmentListener(listener);

		listener.expectHUUnassign(hu, record);

		huAssignmentBL.unassignHUs(record, Arrays.asList(hu), trxName);

		listener.assertExpectationsMatched();

		assertNoHUsAssigned();
	}

}
