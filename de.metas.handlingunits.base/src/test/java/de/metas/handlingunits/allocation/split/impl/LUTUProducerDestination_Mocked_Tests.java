package de.metas.handlingunits.allocation.split.impl;

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

import static org.junit.Assert.assertSame;

import org.adempiere.util.Services;
import org.junit.Test;

import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTransactionBL;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

public class LUTUProducerDestination_Mocked_Tests
{
	@Mocked
	TUProducerDestination tuProducerDestination;

	@Mocked
	IAllocationRequest allocationRequest;

	@Mocked
	IHUTransactionBL huTransactionBL;

	@Mocked
	Services services;

	@Mocked
	IHUTransaction luTransaction;

	@Mocked
	I_M_HU luHU;

	@Mocked
	I_M_HU_PI_Item luItemPI;

	@Mocked
	IAllocationResult tuAllocationResult;

	@Mocked
	IMutableAllocationResult expectedReturnValueAllocationResult;

	@Mocked
	AllocationUtils allocationUtils;

	/**
	 * Verifies that {@link LUTUProducerDestination#loadHU(I_M_HU, IAllocationRequest)} creates a {@link IHUTransaction} for the given LU handling unit,<br>
	 * and that this transaction is also added to the method's return value (task 06748).
	 */
	@Test
	public void testCreatesTransactionForLoadUnit()
	{
		// @formatter:off
		new Expectations() {{

			Services.get(IHUTransactionBL.class);
			result = huTransactionBL;

			huTransactionBL.createLUTransactionForAttributeTransfer(luHU, luItemPI, allocationRequest);
			result = luTransaction;

			tuProducerDestination.load(allocationRequest);
			result = tuAllocationResult;

			AllocationUtils.createMutableAllocationResult(allocationRequest);
			result = expectedReturnValueAllocationResult;
	    }};
	    // @formatter:on

		final LUTUProducerDestination testee = new LUTUProducerDestination();
		testee.setTUProducer(luHU, tuProducerDestination);
		testee.setLUItemPI(luItemPI);
		testee.addHUToAllocate(luHU);

		final IAllocationResult result = testee.loadHU(luHU, allocationRequest);
		assertSame("Invalid IAllocationResult; was expecting the preconfigured mock", expectedReturnValueAllocationResult, result);

		// @formatter:off
		new Verifications()	{{
				// we expect that the LU-specific transaction was added to the result
				expectedReturnValueAllocationResult.addTransaction(luTransaction);

				// ..and we expect that the tu-producer's result is also merged into the result
				AllocationUtils.mergeAllocationResult(expectedReturnValueAllocationResult, tuAllocationResult);
		}};
		// @formatter:on
	}
}
