package de.metas.handlingunits.shipmentschedule.api.impl;

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


import java.math.BigDecimal;

import org.junit.Assume;

import de.metas.handlingunits.StaticHUAssert;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.handlingunits.storage.impl.AbstractProductStorageTest;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import org.junit.jupiter.api.Test;

public class ShipmentScheduleQtyPickedProductStorageTest extends AbstractProductStorageTest
{
	private ShipmentScheduleHelper shipmentScheduleHelper;

	@Override
	protected void initialize()
	{
		super.initialize();

		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
	}

	@Test
	public void test_initialQty()
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("100"), new BigDecimal("7"));

		final ShipmentScheduleQtyPickedProductStorage storage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		StaticHUAssert.assertStorageLevels(storage,
				"100", // Qty Total
				"93", // Qty
				"7" // Qty Free
		);
	}

	@Override
	protected IProductStorage createStorage(final String qtyStr, final boolean reversal, final boolean outboundTrx)
	{
		// We are not supporting outboundTrx
		Assume.assumeTrue(!outboundTrx);

		// We are not supporting reversal transactions
		Assume.assumeTrue(!reversal);

		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal(qtyStr), new BigDecimal("0"));
		final ShipmentScheduleQtyPickedProductStorage storage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		return storage;
	}
}
