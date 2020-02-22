package de.metas.quantity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.product.ProductId;

public class BucketTest
{

	private I_C_UOM uomUnknown;
	private ProductId pTomatoId;
	private I_C_UOM uomEach;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		uomUnknown = BusinessTestHelper.createUOM("UnknownUOM");
		uomEach = BusinessTestHelper.createUomEach();

		final I_M_Product pTomato = BusinessTestHelper.createProduct("tomato", uomEach);
		pTomatoId = ProductId.ofRepoId(pTomato.getM_Product_ID());
	}

	@Test
	public void addQty()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final Capacity capacityTotal = Capacity.createCapacity(qtyTotal, pTomatoId, uomEach, allowNegativeCapacity);
		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, BigDecimal.ZERO);

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
		final Capacity capacityTotal = Capacity.createInfiniteCapacity(pTomatoId, uomEach);
		assertThat(capacityTotal.isInfiniteCapacity()).isTrue();

		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, BigDecimal.ZERO);

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
		final Capacity capacityTotal = Capacity.createCapacity(qtyTotal, pTomatoId, uomEach, allowNegativeCapacity);
		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, BigDecimal.ZERO);

		// NOTE: usually this shall thrown an exception
		// because there is no conversion defined between Each and Unknown
		// but Zero case is handled specially and we don't reach that point
		addQtyAndTest(capacity, uomUnknown, "0", "0");
		assertCapacityLevels(capacity, "10", "0", "10");
	}

	@Test
	public void addQty_NegativeQty()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final Capacity capacityTotal = Capacity.createCapacity(qtyTotal, pTomatoId, uomEach, allowNegativeCapacity);
		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, BigDecimal.ZERO);

		assertThatThrownBy(() -> addQtyAndTest(capacity, uomEach, "-10", "DOES NOT MATTER"))
				.isInstanceOf(AdempiereException.class);
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
		final Capacity capacityTotal = Capacity.createCapacity(qtyTotal, pTomatoId, uomEach, allowNegativeCapacity);
		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, qty);

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
		final Capacity capacityTotal = Capacity.createCapacity(qtyTotal, pTomatoId, uomEach, allowNegativeCapacity);
		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, qtyTotal);

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
		final Capacity capacityTotal = Capacity.createCapacity(qtyTotal, pTomatoId, uomEach, allowNegativeCapacity);
		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, BigDecimal.ZERO);

		// NOTE: usually this shall thrown an exception
		// because there is no conversion defined between Each and Unknown
		// but Zero case is handled specially and we don't reach that point
		removeQtyAndTest(capacity, uomUnknown, "0", "0");
		assertCapacityLevels(capacity, "10", "0", "10");
	}

	@Test
	public void removeQty_NegativeQty()
	{
		final BigDecimal qtyTotal = new BigDecimal("10");
		final boolean allowNegativeCapacity = false;
		final Capacity capacityTotal = Capacity.createCapacity(qtyTotal, pTomatoId, uomEach, allowNegativeCapacity);
		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, BigDecimal.ZERO);

		assertThatThrownBy(() -> removeQtyAndTest(capacity, uomEach, "-10", "DOES NOT MATTER"))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void removeQty_InfiniteCapacity()
	{
		final Capacity capacityTotal = Capacity.createInfiniteCapacity(pTomatoId, uomEach);
		assertThat(capacityTotal.isInfiniteCapacity()).isTrue();

		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, BigDecimal.ZERO);

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
		final Capacity capacityTotal = Capacity.createCapacity(qtyTotal, pTomatoId, uomEach, allowNegativeCapacity);
		final Bucket capacity = Bucket.createBucketWithCapacityAndQty(capacityTotal, qtyTotal);

		removeQtyAndTest(capacity, uomEach, "3", "3");
		assertCapacityLevels(capacity, "10", "7", "3");

		removeQtyAndTest(capacity, uomEach, "5", "5");
		assertCapacityLevels(capacity, "10", "2", "8");

		removeQtyAndTest(capacity, uomEach, "4", "4");
		assertCapacityLevels(capacity, "10", "-2", "12");
	}

	private void addQtyAndTest(final Bucket capacity,
			final I_C_UOM uom,
			final String qtyToAddStr,
			final String qtyAddedExpectedStr)
	{
		final Quantity qtyToAdd = new Quantity(new BigDecimal(qtyToAddStr), uom);
		final Quantity qtyAdded = capacity.addQty(qtyToAdd);

		// Validate Quantity Value and UOM
		assertThat(qtyAdded.toBigDecimal()).isEqualByComparingTo(qtyAddedExpectedStr);
		assertThat(qtyAdded.getUOM()).isEqualTo(uom);

		// Validate Source Quantity Value and UOM
		assertThat(qtyAdded.getSourceUOM()).isEqualTo(capacity.getC_UOM());
	}

	private void removeQtyAndTest(final Bucket capacity,
			final I_C_UOM uom,
			final String qtyToRemoveStr,
			final String qtyRemovedExpectedStr)
	{
		final Quantity qtyToRemove = new Quantity(new BigDecimal(qtyToRemoveStr), uom);
		final Quantity qtyRemoved = capacity.removeQty(qtyToRemove);

		// Validate Quantity Value and UOM
		assertThat(qtyRemoved.toBigDecimal()).as("Invalid Removed Qty: " + capacity).isEqualByComparingTo(qtyRemovedExpectedStr);

		// Validate Quantity Value and UOM
		assertThat(qtyRemoved.getSourceUOM()).isEqualTo(capacity.getC_UOM());
	}

	private void assertCapacityLevels(final Bucket capacity,
			final String qtyCapacityExpectedStr,
			final String qtyExpectedStr,
			final String qtyFreeExpectedStr)
	{
		assertThat(capacity.getCapacity()).isEqualByComparingTo(qtyCapacityExpectedStr);
		assertQty(capacity, qtyExpectedStr);
		assertThat(capacity.getQtyFree()).isEqualByComparingTo(qtyFreeExpectedStr);
	}

	private void assertQty(final Bucket capacity, final String qtyExpectedStr)
	{
		assertThat(capacity.getQty()).isEqualByComparingTo(qtyExpectedStr);
	}
}
