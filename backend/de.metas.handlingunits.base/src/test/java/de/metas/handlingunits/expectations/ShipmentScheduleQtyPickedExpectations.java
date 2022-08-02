package de.metas.handlingunits.expectations;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.util.lang.IContextAware;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Assert;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Check;
import de.metas.util.Services;

public class ShipmentScheduleQtyPickedExpectations extends AbstractHUExpectation<Object>
{
	public static ShipmentScheduleQtyPickedExpectations newInstance()
	{
		return new ShipmentScheduleQtyPickedExpectations();
	}

	// services
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

	// expectations
	private final ArrayList<ShipmentScheduleQtyPickedExpectation<ShipmentScheduleQtyPickedExpectations>> expectations = new ArrayList<>();
	private I_M_ShipmentSchedule shipmentSchedule;
	private BigDecimal qtyPicked;

	public ShipmentScheduleQtyPickedExpectations()
	{
		super(null);
	}

	/**
	 * Asserts only the expectations for this instance's included shipment schedule. You might rather want to call {@link #assertExpected(String)}.
	 *
	 * @param message
	 * @return
	 */
	public ShipmentScheduleQtyPickedExpectations assertExpected_ShipmentSchedule(final String message)
	{
		if (shipmentSchedule == null)
		{
			return this;
		}

		final String prefix = (message == null ? "" : message)
				+ "\n Shipment Schedule: " + shipmentSchedule
				+ "\n\nInvalid ";

		if (qtyPicked != null)
		{
			final BigDecimal qtyPickedActual = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineQty(shipmentSchedule);

			assertEquals(prefix + "QtyPicked", qtyPicked, qtyPickedActual);
		}

		return this;
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected()
	{
		final String message = null;
		return assertExpected(message);
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected(final String message)
	{
		assertExpected_ShipmentSchedule(message);

		int index = 0;
		for (final ShipmentScheduleQtyPickedExpectation<ShipmentScheduleQtyPickedExpectations> expectation : expectations)
		{
			expectation.assertExpected("" + message + " (index=" + index + ")");
			index++;
		}

		return this;
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected(final String message, final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords)
	{
		assertExpected_ShipmentSchedule(message);

		Assert.assertNotNull(message + "qtyPickedRecords not null", qtyPickedRecords);

		final int count = qtyPickedRecords.size();
		final int expectedCount = expectations.size();
		Assert.assertEquals(message + " records count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final String prefix = (message == null ? "" : message)
					+ "\n Record Index: " + (i + 1) + "/" + count;

			expectations.get(i).assertExpected(prefix, qtyPickedRecords.get(i));
		}

		return this;
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected_PickedButNotDelivered(final String message)
	{
		Check.assumeNotNull(shipmentSchedule, "shipmentSchedule not null");

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = shipmentScheduleAllocDAO.retrieveNotOnShipmentLineRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);

		return assertExpected(message, qtyPickedRecords);
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected_ShipmentScheduleWithHUs(
			final String message,
			final List<ShipmentScheduleWithHU> candidates)
	{
		Assert.assertNotNull(message + " candidates not null", candidates);

		final int count = candidates.size();
		final int expectedCount = expectations.size();

		Assert.assertEquals(message + " lines count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final String prefix = (message == null ? "" : message)
					+ "\n Index: " + (i + 1) + "/" + count;

			expectations.get(i).assertExpected_ShipmentScheduleWithHU(prefix, candidates.get(i));
		}

		return this;
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected_ShipmentScheduleWithHUs(
			final String message,
			final Iterator<ShipmentScheduleWithHU> candidates)
	{
		Assert.assertNotNull(message + " candidates not null", candidates);

		final List<ShipmentScheduleWithHU> candidatesList = IteratorUtils.toList(candidates);
		return assertExpected_ShipmentScheduleWithHUs(message, candidatesList);
	}

	public List<I_M_ShipmentSchedule_QtyPicked> createM_ShipmentSchedule_QtyPickeds(final IContextAware context)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> result = new ArrayList<>();
		for (final ShipmentScheduleQtyPickedExpectation<ShipmentScheduleQtyPickedExpectations> expectation : expectations)
		{
			final I_M_ShipmentSchedule_QtyPicked record = expectation.createM_ShipmentSchedule_QtyPicked(context);
			result.add(record);
		}

		return result;
	}

	public ShipmentScheduleQtyPickedExpectation<ShipmentScheduleQtyPickedExpectations> newShipmentScheduleQtyPickedExpectation()
	{
		final ShipmentScheduleQtyPickedExpectation<ShipmentScheduleQtyPickedExpectations> expectation = new ShipmentScheduleQtyPickedExpectation<>(this);
		if (shipmentSchedule != null)
		{
			expectation.shipmentSchedule(shipmentSchedule);
		}
		expectations.add(expectation);

		return expectation;
	}

	public ShipmentScheduleQtyPickedExpectation<ShipmentScheduleQtyPickedExpectations> shipmentScheduleQtyPickedExpectation(final int index)
	{
		return expectations.get(index);
	}

	public ShipmentScheduleQtyPickedExpectations shipmentSchedule(final ShipmentScheduleId shipmentScheduleId)
	{
		return shipmentSchedule(shipmentSchedulesRepo.getById(shipmentScheduleId));
	}

	public ShipmentScheduleQtyPickedExpectations shipmentSchedule(final I_M_ShipmentSchedule shipmentSchedule)
	{
		this.shipmentSchedule = shipmentSchedule;
		return this;
	}

	public ShipmentScheduleQtyPickedExpectations qtyPicked(final BigDecimal qtyPicked)
	{
		this.qtyPicked = qtyPicked;
		return this;
	}

	public ShipmentScheduleQtyPickedExpectations qtyPicked(final String qtyPickedStr)
	{
		return qtyPicked(new BigDecimal(qtyPickedStr));
	}

}
