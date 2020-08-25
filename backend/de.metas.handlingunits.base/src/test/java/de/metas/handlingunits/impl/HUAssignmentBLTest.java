package de.metas.handlingunits.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.validator.DD_Order;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.util.Services;

public class HUAssignmentBLTest
{
	private Properties ctx;
	private String trxName;
	private IContextAware contextProvider;

	private IHUAssignmentBL huAssignmentBL;
	private IHUAssignmentDAO huAssignmentDAO;

	private I_Test record;
	private I_M_HU hu;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		//
		// Make sure Main handling units interceptor is registered
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new de.metas.handlingunits.model.validator.Main(new PickingBOMService()));
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new DD_Order());

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
		record = newInstance(I_Test.class, contextProvider);
		saveRecord(record);

		//
		// Virtual PI
		final I_M_HU_PI virtualPI = newInstance(I_M_HU_PI.class);
		virtualPI.setM_HU_PI_ID(HuPackingInstructionsId.VIRTUAL.getRepoId());
		saveRecord(virtualPI);
		//
		final I_M_HU_PI_Version virtualPIVersion = newInstance(I_M_HU_PI_Version.class);
		virtualPIVersion.setM_HU_PI_ID(virtualPI.getM_HU_PI_ID());
		virtualPIVersion.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		virtualPIVersion.setIsCurrent(true);
		saveRecord(virtualPIVersion);

		//
		// Create a dummy HU
		hu = createHU();
	}

	private I_M_HU createHU()
	{
		final I_M_HU hu = newInstance(I_M_HU.class, contextProvider);
		hu.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		saveRecord(hu);
		return hu;
	}

	/**
	 * Assert {@link #hu} is the only assigned HU to {@link #record}
	 */
	private void assertHUAssigned()
	{
		final List<I_M_HU> husAssignedActual = huAssignmentDAO.retrieveTopLevelHUsForModel(record);
		assertThat(husAssignedActual).containsExactly(hu);
	}

	/**
	 * Assert there are no HUs assigned to {@link #record}.
	 */
	private void assertNoHUsAssigned()
	{
		final List<I_M_HU> husAssignedActual = huAssignmentDAO.retrieveTopLevelHUsForModel(record);
		assertThat(husAssignedActual).isEmpty();
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
