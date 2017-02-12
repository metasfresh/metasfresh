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

import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Assert;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class ShipmentScheduleQtyPickedExpectations extends AbstractHUExpectation<Object>
{
	// services
	public final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);

	// expectations
	final List<ShipmentScheduleQtyPickedExpectation<ShipmentScheduleQtyPickedExpectations>> expectations = new ArrayList<>();
	private I_M_ShipmentSchedule shipmentSchedule;
	private BigDecimal qtyPicked;

	public ShipmentScheduleQtyPickedExpectations()
	{
		super(null);
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected_ShipmentSchedule(final String message)
	{
		if (shipmentSchedule == null)
		{
			return this;
		}

		return assertExpected_ShipmentSchedule(message, shipmentSchedule);
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected_ShipmentSchedule(final String message, final I_M_ShipmentSchedule shipmentSchedule)
	{
		Check.assumeNotNull(shipmentSchedule, "shipmentSchedule not null");

		final String prefix = (message == null ? "" : message)
				+ "\n Shipment Schedule: " + shipmentSchedule
				+ "\n\nInvalid ";

		if (qtyPicked != null)
		{
			final BigDecimal qtyPickedActual = shipmentScheduleAllocBL.getQtyPicked(shipmentSchedule);
			assertEquals(prefix + "QtyPicked", qtyPicked, qtyPickedActual);
		}

		return this;
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

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = Services.get(IShipmentScheduleAllocDAO.class).retrievePickedNotDeliveredRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);

		return assertExpected(message, qtyPickedRecords);
	}

	public ShipmentScheduleQtyPickedExpectations assertExpected_ShipmentScheduleWithHUs(final String message, final List<IShipmentScheduleWithHU> candidates)
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

	public ShipmentScheduleQtyPickedExpectations assertExpected_ShipmentScheduleWithHUs(final String message, final Iterator<IShipmentScheduleWithHU> candidates)
	{
		Assert.assertNotNull(message + " candidates not null", candidates);

		final List<IShipmentScheduleWithHU> candidatesList = IteratorUtils.toList(candidates);
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
