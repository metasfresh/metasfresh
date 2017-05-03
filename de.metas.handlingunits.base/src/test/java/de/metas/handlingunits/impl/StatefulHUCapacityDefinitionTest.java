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


import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IStatefulHUCapacityDefinition;
import de.metas.quantity.Quantity;

public class StatefulHUCapacityDefinitionTest extends AbstractHUTest
{

	private I_C_UOM uomUnknown;

	@Override
	protected void initialize()
	{
		uomUnknown = helper.createUOM("UnknownUOM");
	}

	@Test
	public void addQty()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(qtyTotal, pTomato, uomEach, allowNegativeCapacity);
		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, BigDecimal.ZERO);

		addQtyAndTest(capacity, uomEach, "3", "3");
		assertCapacityLevels(capacity, "10", "3", "7");

		addQtyAndTest(capacity, uomEach, "5", "5");
		assertCapacityLevels(capacity, "10", "8", "2");

		addQtyAndTest(capacity, uomEach, "4", "2");
		assertCapacityLevels(capacity, "10", "10", "0");
	}

	@Test
	public void addQty_InfiniteCapacity()
	{
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(pTomato, uomEach);
		Assert.assertTrue("Shall be infinite capacity: " + capacityTotal, capacityTotal.isInfiniteCapacity());

		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, BigDecimal.ZERO);

		addQtyAndTest(capacity, uomEach, "3", "3");
		assertQty(capacity, "3");

		addQtyAndTest(capacity, uomEach, "5", "5");
		assertQty(capacity, "8");

		addQtyAndTest(capacity, uomEach, "4", "4");
		assertQty(capacity, "12");
	}

	@Test
	public void addQty_Zero()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(qtyTotal, pTomato, uomEach, allowNegativeCapacity);
		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, BigDecimal.ZERO);

		// NOTE: usually this shall thrown an exception
		// because there is no conversion defined between Each and Unknown
		// but Zero case is handled specially and we don't reach that point
		addQtyAndTest(capacity, uomUnknown, "0", "0");
		assertCapacityLevels(capacity, "10", "0", "10");
	}

	@Test(expected = AdempiereException.class)
	public void addQty_NegativeQty()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(qtyTotal, pTomato, uomEach, allowNegativeCapacity);
		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, BigDecimal.ZERO);

		addQtyAndTest(capacity, uomEach, "-10", "DOES NOT MATTER");
	}

	/**
	 * Tests the case when QtyFree is negative. In this case no quantity shall be added.
	 *
	 * @task http://dewiki908/mediawiki/index.php/07758_Tweak_of_Issueing_Method_Folie_%28100023269700%29 - found while testing that task
	 */
	@Test
	public void addQty_HavingNegativeQtyFree()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final BigDecimal qty = new BigDecimal("13"); // > qtyTotal
		final boolean allowNegativeCapacity = false;
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(qtyTotal, pTomato, uomEach, allowNegativeCapacity);
		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, qty);

		// Make sure our configuration is right
		assertCapacityLevels(capacity, "10", "13", "-3");

		// Try adding one more item
		// NOTE: because qtyFree is negative we expect nothing to be added
		addQtyAndTest(capacity, uomEach, "1", "0");
		assertCapacityLevels(capacity, "10", "13", "-3");
	}

	@Test
	public void removeQty()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(qtyTotal, pTomato, uomEach, allowNegativeCapacity);
		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, qtyTotal);

		removeQtyAndTest(capacity, uomEach, "3", "3");
		assertCapacityLevels(capacity, "10", "7", "3");

		removeQtyAndTest(capacity, uomEach, "5", "5");
		assertCapacityLevels(capacity, "10", "2", "8");

		removeQtyAndTest(capacity, uomEach, "4", "2");
		assertCapacityLevels(capacity, "10", "0", "10");
	}

	@Test
	public void removeQty_Zero()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(qtyTotal, pTomato, uomEach, allowNegativeCapacity);
		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, BigDecimal.ZERO);

		// NOTE: usually this shall thrown an exception
		// because there is no conversion defined between Each and Unknown
		// but Zero case is handled specially and we don't reach that point
		removeQtyAndTest(capacity, uomUnknown, "0", "0");
		assertCapacityLevels(capacity, "10", "0", "10");
	}

	@Test(expected = AdempiereException.class)
	public void removeQty_NegativeQty()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(qtyTotal, pTomato, uomEach, allowNegativeCapacity);
		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, BigDecimal.ZERO);

		removeQtyAndTest(capacity, uomEach, "-10", "DOES NOT MATTER");
	}

	@Test
	public void removeQty_InfiniteCapacity()
	{
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(pTomato, uomEach);
		Assert.assertTrue("Shall be infinite capacity: " + capacityTotal, capacityTotal.isInfiniteCapacity());

		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, BigDecimal.ZERO);

		removeQtyAndTest(capacity, uomEach, "3", "3");
		assertQty(capacity, "-3");

		removeQtyAndTest(capacity, uomEach, "5", "5");
		assertQty(capacity, "-8");

		removeQtyAndTest(capacity, uomEach, "4", "4");
		assertQty(capacity, "-12");
	}

	@Test
	public void removeQty_AllowNegativeQty()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = true;
		final HUCapacityDefinition capacityTotal = new HUCapacityDefinition(qtyTotal, pTomato, uomEach, allowNegativeCapacity);
		final IStatefulHUCapacityDefinition capacity = new StatefulHUCapacityDefinition(capacityTotal, qtyTotal);

		removeQtyAndTest(capacity, uomEach, "3", "3");
		assertCapacityLevels(capacity, "10", "7", "3");

		removeQtyAndTest(capacity, uomEach, "5", "5");
		assertCapacityLevels(capacity, "10", "2", "8");

		removeQtyAndTest(capacity, uomEach, "4", "4");
		assertCapacityLevels(capacity, "10", "-2", "12");
	}

	private void addQtyAndTest(final IStatefulHUCapacityDefinition capacity,
			final I_C_UOM uom,
			final String qtyToAddStr,
			final String qtyAddedExpectedStr)
	{
		final Quantity qtyToAdd = new Quantity(new BigDecimal(qtyToAddStr), uom);
		final Quantity qtyAdded = capacity.addQty(qtyToAdd);

		// Validate Quantity Value and UOM
		Assert.assertThat("Invalid Added Qty: " + capacity,
				qtyAdded.getQty(),
				Matchers.comparesEqualTo(new BigDecimal(qtyAddedExpectedStr)));
		Assert.assertEquals("Invalid Added UOM: ", uom, qtyAdded.getUOM());

		// Validate Source Quantity Value and UOM
		Assert.assertEquals("Invalid Added Source UOM: ", capacity.getC_UOM(), qtyAdded.getSourceUOM());
	}

	private void removeQtyAndTest(final IStatefulHUCapacityDefinition capacity,
			final I_C_UOM uom,
			final String qtyToRemoveStr,
			final String qtyRemovedExpectedStr)
	{
		final Quantity qtyToRemove = new Quantity(new BigDecimal(qtyToRemoveStr), uom);
		final Quantity qtyRemoved = capacity.removeQty(qtyToRemove);

		// Validate Quantity Value and UOM
		Assert.assertThat("Invalid Removed Qty: " + capacity,
				qtyRemoved.getQty(),
				Matchers.comparesEqualTo(new BigDecimal(qtyRemovedExpectedStr)));

		// Validate Quantity Value and UOM
		Assert.assertEquals("Invalid Removed Source UOM: ", capacity.getC_UOM(), qtyRemoved.getSourceUOM());
	}

	private void assertCapacityLevels(final IStatefulHUCapacityDefinition capacity,
			final String qtyCapacityExpectedStr,
			final String qtyExpectedStr,
			final String qtyFreeExpectedStr)
	{
		Assert.assertThat("Invalid Qty Capacity/Total: " + capacity,
				capacity.getCapacity(),
				Matchers.comparesEqualTo(new BigDecimal(qtyCapacityExpectedStr)));

		assertQty(capacity, qtyExpectedStr);

		Assert.assertThat("Invalid Qty Free: " + capacity,
				capacity.getQtyFree(),
				Matchers.comparesEqualTo(new BigDecimal(qtyFreeExpectedStr)));
	}

	private void assertQty(final IStatefulHUCapacityDefinition capacity, final String qtyExpectedStr)
	{
		Assert.assertThat("Invalid Qty: " + capacity,
				capacity.getQty(),
				Matchers.comparesEqualTo(new BigDecimal(qtyExpectedStr)));
	}
}
