package de.metas.handlingunits.storage.impl;

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
import java.time.ZonedDateTime;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Assume;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.StaticHUAssert;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import org.junit.jupiter.api.Test;

public abstract class AbstractProductStorageTest extends AbstractHUTest
{
	protected I_M_Product product;
	private ZonedDateTime date;

	/**
	 * Create Storage
	 *
	 * @param qtyStr qty
	 * @param reversal true if it's a reversal transaction
	 * @param outboundTrx true if it's a outbound transaction
	 * @return
	 */
	protected abstract IProductStorage createStorage(final String qtyStr, final boolean reversal, final boolean outboundTrx);

	@Override
	protected void initialize()
	{
		product = pTomato;
		date = helper.getTodayZonedDateTime();
	}

	@Test
	public void test_addRemoveQty_inboundTrx()
	{
		// NOTE: add Less, Exact or More shall behave the same because storage is already full

		final boolean reversal = false;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("10", reversal, outboundTrx);

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");

		//
		// Add 3items
		{
			final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("3"));
			final IAllocationRequest requestActual = storage.addQty(requestInitial);

			// Validate actual request
			assertRequestedQty(requestActual, "0");
			// Validate storage status (Total/Qty/Free)
			StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");
		}

		//
		// Remove 3items
		{
			final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("3"));
			final IAllocationRequest requestActual = storage.removeQty(requestInitial);

			// Validate actual request
			assertRequestedQty(requestActual, "3");
			// Validate storage status (Total/Qty/Free)
			StaticHUAssert.assertStorageLevels(storage, "10", "7", "3");
		}

		//
		// Add 1item
		{
			final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("1"));
			final IAllocationRequest requestActual = storage.addQty(requestInitial);

			// Validate actual request
			assertRequestedQty(requestActual, "1");
			// Validate storage status (Total/Qty/Free)
			StaticHUAssert.assertStorageLevels(storage, "10", "8", "2");
		}

		//
		// Add 5item => 2items added
		{
			final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("5"));
			final IAllocationRequest requestActual = storage.addQty(requestInitial);

			// Validate actual request
			assertRequestedQty(requestActual, "2");
			// Validate storage status (Total/Qty/Free)
			StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");
		}

		//
		// Remove 13item
		//
		// If Negative Storage is allowed => all 13items shall be removed
		if (storage.isAllowNegativeStorage())
		{
			final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("13"));
			final IAllocationRequest requestActual = storage.removeQty(requestInitial);

			// Validate actual request
			assertRequestedQty(requestActual, "13");
			// Validate storage status (Total/Qty/Free)
			StaticHUAssert.assertStorageLevels(storage, "10", "-3", "13");
		}
		// If Negative Storage is not allowed => 10items removed
		else
		{
			final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("13"));
			final IAllocationRequest requestActual = storage.removeQty(requestInitial);

			// Validate actual request
			assertRequestedQty(requestActual, "10");
			// Validate storage status (Total/Qty/Free)
			StaticHUAssert.assertStorageLevels(storage, "10", "0", "10");
		}
	}

	@Test
	public void test_addQty_Less_inboundTrx_reversalTrx()
	{
		final boolean reversal = true;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("-10", reversal, outboundTrx);

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "0", "10");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("3"));
		final IAllocationRequest requestActual = storage.addQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "3");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "3", "7");
	}

	@Test
	public void test_addQty_Exact_inboundTrx_reversalTrx()
	{
		final boolean reversal = true;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("-10", reversal, outboundTrx);

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "0", "10");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("10"));
		final IAllocationRequest requestActual = storage.addQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "10");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");
	}

	@Test
	public void test_addQty_Less_outboundTrx()
	{
		final boolean reversal = false;
		final boolean outboundTrx = true;
		final IProductStorage storage = createStorage("-10", reversal, outboundTrx);

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "0", "10");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("3"));
		final IAllocationRequest requestActual = storage.addQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "3");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "3", "7");
	}

	@Test
	public void test_addQty_More_inboundTrx_reversalTrx()
	{
		final boolean reversal = true;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("-10", reversal, outboundTrx);

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "0", "10");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("17"));
		final IAllocationRequest requestActual = storage.addQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "10");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");
	}

	@Test
	public void test_addQty_More_outboundTrx()
	{
		final boolean reversal = false;
		final boolean outboundTrx = true;
		final IProductStorage storage = createStorage("-10", reversal, outboundTrx);

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "0", "10");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("17"));
		final IAllocationRequest requestActual = storage.addQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "10");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");
	}

	@Test
	public void test_removeQty_Less_inboundTrx()
	{
		final boolean reversal = false;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("10", reversal, outboundTrx);

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("3"));
		final IAllocationRequest requestActual = storage.removeQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "3");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "7", "3");
	}

	@Test
	public void test_removeQty_Exact_inboundTrx()
	{
		final boolean reversal = false;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("10", reversal, outboundTrx);

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("10"));
		final IAllocationRequest requestActual = storage.removeQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "10");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "0", "10");
	}

	@Test
	public void test_removeQty_More_inboundTrx_NegativeStorageNotAllowed()
	{
		final boolean reversal = false;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("10", reversal, outboundTrx);
		Assume.assumeTrue(!storage.isAllowNegativeStorage());

		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("17"));
		final IAllocationRequest requestActual = storage.removeQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "10");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "0", "10");
	}

	@Test
	public void test_removeQty_More_inboundTrx_NegativeStorageAllowed()
	{
		final boolean reversal = false;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("10", reversal, outboundTrx);
		//
		Assume.assumeTrue("storage allows negative storage", storage.isAllowNegativeStorage());
		final boolean forceQtyAllocation = false;
		//
		test_removeQty_More_inboundTrx_NegativeStorageAllowed(storage, forceQtyAllocation);
	}

	@Test
	public void test_removeQty_More_inboundTrx_NegativeStorageAllowed_ForceQtyAllocation()
	{
		final boolean reversal = false;
		final boolean outboundTrx = false;
		final IProductStorage storage = createStorage("10", reversal, outboundTrx);
		//
		// Assume.assumeTrue(storage.isAllowNegativeStorage()); // nop, does not matter
		assumeProductStorageIsConsideringForceQtyAllocation(storage);
		final boolean forceQtyAllocation = true;
		//
		test_removeQty_More_inboundTrx_NegativeStorageAllowed(storage, forceQtyAllocation);
	}

	private void test_removeQty_More_inboundTrx_NegativeStorageAllowed(final IProductStorage storage, final boolean forceQtyAllocation)
	{
		//
		// Initial storage checking (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "10", "0");

		//
		// Perform deallocation transaction
		final IAllocationRequest requestInitial = createAllocationRequest(new BigDecimal("17"), forceQtyAllocation);
		final IAllocationRequest requestActual = storage.removeQty(requestInitial);

		//
		// Validate actual request
		assertRequestedQty(requestActual, "17");

		//
		// Validate storage status (Total/Qty/Free)
		StaticHUAssert.assertStorageLevels(storage, "10", "-7", "17");
	}

	protected void assertRequestedQty(final IAllocationRequest request, final String expectedQtyStr)
	{
		Assert.assertThat("Invalid requested qty - " + request,
				request.getQty(),
				Matchers.comparesEqualTo(new BigDecimal(expectedQtyStr)));

	}

	protected IAllocationRequest createAllocationRequest(final BigDecimal qty)
	{
		final boolean forceQtyAllocation = false;
		return createAllocationRequest(qty, forceQtyAllocation);
	}

	protected IAllocationRequest createAllocationRequest(final BigDecimal qty, final boolean forceQtyAllocation)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		final I_C_UOM uom = uomDAO.getById(product.getC_UOM_ID());

		final Quantity quantity = new Quantity(qty, uom);
		final IMutableHUContext huContext = helper.getHUContext();
		return AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setDate(date)
				.setFromReferencedModel(null)
				.setProduct(product)
				.setQuantity(quantity)
				.setForceQtyAllocation(forceQtyAllocation)
				.create();
	}

	protected final void assumeProductStorageIsConsideringForceQtyAllocation(final IProductStorage productStorage)
	{
		final boolean considerForceQtyAllocationFromRequest;
		if (productStorage instanceof AbstractProductStorage)
		{
			considerForceQtyAllocationFromRequest = ((AbstractProductStorage)productStorage).isConsiderForceQtyAllocationFromRequest();
		}
		else
		{
			// we assume it's considered
			considerForceQtyAllocationFromRequest = true;
		}

		Assume.assumeTrue("Product storage is considering the Request's ForceQtyAllocation flag", considerForceQtyAllocationFromRequest);
	}
}
