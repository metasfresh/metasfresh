package org.adempiere.inout.shipment.impl;

/*
 * #%L
 * de.metas.swat.base
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


import static org.adempiere.test.UnitTestTools.daysBefore;
import static org.adempiere.test.UnitTestTools.serviceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.inout.shipment.ShipmentParams;
import org.adempiere.test.UnitTestTools;
import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import de.metas.adempiere.test.POTest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;

/**
 * Tests for {@link ShipmentBL#retrieveOlsAndScheds(org.adempiere.inout.shipment.ShipmentParams, String)}.
 * 
 * @author ts
 * 
 */
public class ShipmentBLRetrieveTest
{

	private final static String TRX_NAME = "trxName";

	private void expectRetrieveAll(final Map<String, Object> mocks,
			final List<OlAndSched> emptyList)
	{
		final IShipmentSchedulePA shipmentSchedulePA = serviceMock(
				IShipmentSchedulePA.class, mocks);
		expect(shipmentSchedulePA.retrieveForShipmentRun(false, TRX_NAME))
				.andReturn(emptyList);
	}

	private void expectRetrieveForBPartner(final ShipmentParams params,
			final Map<String, Object> mocks, final List<OlAndSched> emptyList)
	{

		final IShipmentSchedulePA shipmentSchedulePA = serviceMock(
				IShipmentSchedulePA.class, mocks);
		expect(
				shipmentSchedulePA
						.retrieveOlAndSchedsForBPartner(params.getBPartnerId(),
								params.isIncludeWhenIncompleteInOutExists(), TRX_NAME))
				.andReturn(emptyList);
	}

	private void invoke(final ShipmentParams params,
			final Map<String, Object> mocks)
	{

		replay(mocks.values().toArray());
		final ShipmentBL shipmentBL = new ShipmentBL();
		shipmentBL.retrieveOlsAndScheds(POTest.CTX, params, TRX_NAME);
		verify(mocks.values().toArray());
	}

	private void invokeBPartner(final ShipmentParams params)
	{

		final Map<String, Object> mocks = new HashMap<String, Object>();

		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();

		expectRetrieveForBPartner(params, mocks, emptyList);

		invoke(params, mocks);
	}

	private void invokeBPArtnerPreferred(final ShipmentParams params)
	{
		final Map<String, Object> mocks = new HashMap<String, Object>();

		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();

		expectRetrieveForBPartner(params, mocks, emptyList);

		expectUpdate(params, mocks, emptyList);

		invoke(params, mocks);
	}

	@Test
	public void retrieveBPartner0()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setBPartnerId(23);
		params.setPreferBPartner(false);

		invokeBPartner(params);
	}

	@Test
	public void retrieveBPartner1()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setBPartnerId(23);
		params.setPreferBPartner(false);
		params.setMovementDate(new Timestamp(System.currentTimeMillis()));

		invokeBPartner(params);
	}

	//@Test
	public void retrieveBPartnerPrefered0()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setBPartnerId(23);
		params.setPreferBPartner(true);

		invokeBPArtnerPreferred(params);
	}

	/**
	 * no bpartner selected, but preferred=true (should be ignored).
	 */
	@Test
	public void retrieveBPartnerPrefered1()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setBPartnerId(0);
		params.setPreferBPartner(true);

		final Map<String, Object> mocks = new HashMap<String, Object>();
		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();
		expectRetrieveAll(mocks, emptyList);

		invoke(params, mocks);
	}

	/**
	 * preferred bpartner selected, movement date=today
	 */
	//@Test
	public void retrieveBPartnerPrefered2()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setBPartnerId(23);
		params.setPreferBPartner(true);
		params.setMovementDate(SystemTime.asTimestamp());

		invokeBPArtnerPreferred(params);
	}

	/**
	 * movementDate = today, no bpartner selected
	 */
	@Test
	public void retrieveMovementDate0()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setMovementDate(SystemTime.asTimestamp());
		params.setPreferBPartner(false);

		final Map<String, Object> mocks = new HashMap<String, Object>();
		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();
		expectRetrieveAll(mocks, emptyList);

		invoke(params, mocks);
	}

	/**
	 * movementDate != today, no BPartner selected
	 */
	//@Test
	public void retrieveMovementDate1()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setMovementDate(daysBefore(1));
		params.setPreferBPartner(false);

		final Map<String, Object> mocks = new HashMap<String, Object>();

		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();

		expectRetrieveAll(mocks, emptyList);

		expectUpdate(params, mocks, emptyList);

		invoke(params, mocks);
	}

	/**
	 * movementDate != today, one BPartner selected
	 */
	//@Test
	public void retrieveMovementDate2()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setMovementDate(UnitTestTools.daysBefore(1));
		params.setPreferBPartner(false);
		params.setBPartnerId(23);

		final Map<String, Object> mocks = new HashMap<String, Object>();

		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();

		expectRetrieveAll(mocks, emptyList);

		expectUpdate(params, mocks, emptyList);

		invoke(params, mocks);
	}

	/**
	 * movementDate != today, one BPartner selected and preferred
	 */
	//@Test
	public void retrieveMovementDate3()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setMovementDate(daysBefore(1));
		params.setPreferBPartner(true);
		params.setBPartnerId(23);

		final Map<String, Object> mocks = new HashMap<String, Object>();

		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();

		expectRetrieveForBPartner(params, mocks, emptyList);

		expectUpdate(params, mocks, emptyList);

		invoke(params, mocks);
	}

	/**
	 * movementDate != today, no BPartner selected but preferred=true (prefered should thus be ignored)
	 */
	//@Test
	public void retrieveMovementDate4()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setMovementDate(daysBefore(1));
		params.setPreferBPartner(true);
		params.setBPartnerId(0);

		final Map<String, Object> mocks = new HashMap<String, Object>();

		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();

		expectRetrieveAll(mocks, emptyList);

		expectUpdate(params, mocks, emptyList);

		invoke(params, mocks);
	}

	/**
	 * test with selected order line ids and movement date=today
	 */
	@Test
	public void retrieveSelectedOrders0()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setMovementDate(SystemTime.asTimestamp());

		final Set<Integer> selectedOls = new HashSet<Integer>();
		params.setSelectedOrderLineIds(selectedOls);

		final Map<String, Object> mocks = new HashMap<String, Object>();

		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();

		expectRetrieveAll(mocks, emptyList);
		invoke(params, mocks);
	}

	/**
	 * test with selected order line ids and movement date=yesterday
	 */
	//@Test
	public void retrieveSelectedOrders1()
	{

		final ShipmentParams params = new ShipmentParams();
		params.setMovementDate(daysBefore(1));

		final Set<Integer> selectedOls = new HashSet<Integer>();
		params.setSelectedOrderLineIds(selectedOls);

		final Map<String, Object> mocks = new HashMap<String, Object>();

		final List<OlAndSched> emptyList = new ArrayList<OlAndSched>();

		expectRetrieveAll(mocks, emptyList);

		expectUpdate(params, mocks, emptyList);

		invoke(params, mocks);
	}

	private void expectUpdate(final ShipmentParams params,
			final Map<String, Object> mocks, final List<OlAndSched> emptyList)
	{
		final IShipmentScheduleBL shipmentScheduleBL = serviceMock(
				IShipmentScheduleBL.class, mocks);
		shipmentScheduleBL.updateSchedules(POTest.CTX, emptyList, false, params
				.getMovementDate(), null, TRX_NAME);
	}

}
