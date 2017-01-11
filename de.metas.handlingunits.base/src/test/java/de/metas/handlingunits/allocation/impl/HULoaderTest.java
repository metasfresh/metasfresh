package de.metas.handlingunits.allocation.impl;

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

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_Transaction;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUAssert;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.MockedAllocationSourceDestination;
import de.metas.handlingunits.storage.IProductStorage;

/**
 *
 * NOTE: use http://www.xpathtester.com/test to test your XPaths
 *
 * @author tsa
 *
 */
// @SuppressWarnings("PMD.SingularField")
public class HULoaderTest extends AbstractHUTest
{

	@Override
	protected void initialize()
	{

	}


	@Test
	public void test_load_to_reversal()
	{
		//
		// Create incoming
		final I_M_Transaction mtrx = helper.createMTransaction(
				X_M_Transaction.MOVEMENTTYPE_VendorReceipts,
				pTomato,
				new BigDecimal(23));
		POJOWrapper.setInstanceName(mtrx, "Incoming trx");

		// // create and destroy instances only with a I_M_Transaction
		// // final List<I_M_HU> huPalets =
		// trxBL.transferIncomingToHUs(mtrx, huDefPalet);

		//
		// Create incoming mtrx reversal
		final I_M_Transaction mtrxReversal = helper.createMTransactionReversal(mtrx);
		POJOWrapper.setInstanceName(mtrxReversal, "Incoming trx reversal");
		Assert.assertThat("Reversal qty shall be original qty negated",
				mtrxReversal.getMovementQty(),
				Matchers.comparesEqualTo(mtrx.getMovementQty().negate()));

		//
		// Create incoming "source" and validate
		final MTransactionAllocationSourceDestination mtrxSource = new MTransactionAllocationSourceDestination(mtrx);
		HUAssert.assertStorageLevels(mtrxSource.getStorage(), "23", "23", "0"); // Qty Total/Allocated/Available

		//
		// Create reversal "destination" and validate
		final MTransactionAllocationSourceDestination mtrxReversalDestination = new MTransactionAllocationSourceDestination(mtrxReversal);
		HUAssert.assertStorageLevels(mtrxReversalDestination.getStorage(), "23", "0", "23"); // Qty Total/Allocated/Available

		final IMutableHUContext huContext = helper.getHUContext();

		final HULoader loader = new HULoader(mtrxSource, mtrxReversalDestination);

		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				mtrx.getM_Product(),
				mtrx.getMovementQty(),
				Services.get(IHandlingUnitsBL.class).getC_UOM(mtrx),
				helper.getTodayDate(),
				mtrx);

		// dumpStatusAfterTest = true;
		loader.load(request);

		//
		// Reload mtrx storage and validate it
		{
			final IProductStorage mtrxStorage2 = new MTransactionAllocationSourceDestination(mtrx)
					.getStorage();

			HUAssert.assertStorageLevels(mtrxStorage2, "23", "0", "23"); // Total/Qty/Free
		}

		//
		// Reload mtrx storage reversal and validate it
		{
			final IProductStorage mtrxReversalStorage2 = new MTransactionAllocationSourceDestination(mtrxReversal)
					.getStorage();

			HUAssert.assertStorageLevels(mtrxReversalStorage2, "23", "23", "0"); // Total/Qty/Free
		}

		HUAssert.assertAllStoragesAreValid();
	}

	// pure unit test
	@Test
	public void test_partialUnload()
	{
		//
		// Setup
		final IMutableHUContext huContext = helper.getHUContext();
		final Object referenceModel = helper.createDummyReferenceModel();
		final MockedAllocationSourceDestination source = new MockedAllocationSourceDestination();
		final MockedAllocationSourceDestination destination = new MockedAllocationSourceDestination();
		final HULoader loader = new HULoader(source, destination);

		//
		// Config
		final BigDecimal qtyRequest = new BigDecimal("10");
		source.setQtyToUnload(new BigDecimal("7"));
		destination.setQtyToLoad(MockedAllocationSourceDestination.ANY);
		loader.setAllowPartialUnloads(true);
		loader.setAllowPartialLoads(false); // shall not happen anyway

		//
		// Execute transfer
		final IAllocationRequest unloadRequest = AllocationUtils.createQtyRequest(
				huContext,
				pTomato,
				qtyRequest,
				pTomato.getC_UOM(),
				helper.getTodayDate(),
				referenceModel);
		final IAllocationResult result = loader.load(unloadRequest);

		//
		// Validate
		Assert.assertThat("Invalid QtyToAllocate",
				result.getQtyToAllocate(),
				Matchers.comparesEqualTo(new BigDecimal("0")));
		Assert.assertThat("Invalid QtyAllocated",
				result.getQtyAllocated(),
				Matchers.comparesEqualTo(new BigDecimal("7")));
	}

	// pure unit test
	@Test
	public void test_partialLoad()
	{
		//
		// Setup
		final IMutableHUContext huContext = helper.getHUContext();
		final Object referenceModel = helper.createDummyReferenceModel();
		final MockedAllocationSourceDestination source = new MockedAllocationSourceDestination();
		final MockedAllocationSourceDestination destination = new MockedAllocationSourceDestination();
		final HULoader loader = new HULoader(source, destination);

		//
		// Config
		final BigDecimal qtyRequest = new BigDecimal("10");
		source.setQtyToUnload(MockedAllocationSourceDestination.ANY);
		destination.setQtyToLoad(new BigDecimal("7"));
		loader.setAllowPartialUnloads(false);
		loader.setAllowPartialLoads(true);

		//
		// Execute transfer
		final IAllocationRequest unloadRequest = AllocationUtils.createQtyRequest(
				huContext,
				pTomato,
				qtyRequest,
				pTomato.getC_UOM(),
				helper.getTodayDate(),
				referenceModel);
		final IAllocationResult result = loader.load(unloadRequest);

		//
		// Validate
		Assert.assertThat("Invalid QtyToAllocate",
				result.getQtyToAllocate(),
				Matchers.comparesEqualTo(new BigDecimal(10 - 7)));
		Assert.assertThat("Invalid QtyAllocated",
				result.getQtyAllocated(),
				Matchers.comparesEqualTo(new BigDecimal(7)));
	}
}
