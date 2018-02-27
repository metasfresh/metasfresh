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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;

public class AllocationResultExpectation extends AbstractHUExpectation<Object>
{

	private BigDecimal _qtyAllocated;
	private BigDecimal _qtyToAllocate;
	private Boolean _completed;
	private Boolean _zeroAllocated;
	private List<HUTransactionExpectation<AllocationResultExpectation>> huTransactionExpectations = null;

	public AllocationResultExpectation()
	{
		super(
				null // parentExpectation
		);
	}

	public AllocationResultExpectation assertExpected(final IAllocationResult result)
	{
		final String message = null;
		return assertExpected(message, result);
	}

	public AllocationResultExpectation assertExpected(final String message, final IAllocationResult result)
	{
		final String prefix = (message == null ? "" : message)
				+ "\nAllocationResult: " + result
				+ "\n"
				+ "\nInvalid ";

		Assert.assertNotNull(prefix + "allocation result not null", result);

		if (_qtyAllocated != null)
		{
			assertEquals(prefix + "QtyAllocated", _qtyAllocated, result.getQtyAllocated());
		}
		if (_qtyToAllocate != null)
		{
			assertEquals(prefix + "QtyToAllocate", _qtyToAllocate, result.getQtyToAllocate());
		}
		if (_completed != null)
		{
			Assert.assertEquals(prefix + "Completed", _completed, result.isCompleted());
		}
		if (_zeroAllocated != null)
		{
			Assert.assertEquals(prefix + "ZeroAllocated", _zeroAllocated, result.isZeroAllocated());
		}
		if (huTransactionExpectations != null)
		{
			assertExpectedHUTransactions(prefix + "HU Transactions", result.getTransactions());
		}

		return this;
	}

	private AllocationResultExpectation assertExpectedHUTransactions(final String message, final List<IHUTransactionCandidate> transactions)
	{
		final int count = transactions.size();
		final int expectedCount = huTransactionExpectations.size();

		Assert.assertEquals(message + " HU Items count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final IHUTransactionCandidate transaction = transactions.get(i);

			final String prefix = (message == null ? "" : message)
					+ "\n HUTransaction Index: " + (i + 1) + "/" + count;

			huTransactionExpectations.get(i).assertExpected(prefix, transaction);
		}

		return this;
	}

	public AllocationResultExpectation qtyAllocated(final BigDecimal qtyAllocated)
	{
		_qtyAllocated = qtyAllocated;
		return this;
	}

	public AllocationResultExpectation qtyAllocated(final String qtyAllocatedStr)
	{
		return qtyAllocated(new BigDecimal(qtyAllocatedStr));
	}

	public AllocationResultExpectation qtyToAllocate(final BigDecimal qtyToAllocate)
	{
		_qtyToAllocate = qtyToAllocate;
		return this;
	}

	public AllocationResultExpectation qtyToAllocate(final String qtyToAllocateStr)
	{
		return qtyToAllocate(new BigDecimal(qtyToAllocateStr));
	}

	public AllocationResultExpectation completed(final boolean completed)
	{
		_completed = completed;
		return this;
	}

	public void zeroAllocated(final boolean zeroAllocated)
	{
		_zeroAllocated = zeroAllocated;
	}

	public HUTransactionExpectation<AllocationResultExpectation> newHUTransactionExpectation()
	{
		final HUTransactionExpectation<AllocationResultExpectation> expectation = new HUTransactionExpectation<>(this);
		if (huTransactionExpectations == null)
		{
			huTransactionExpectations = new ArrayList<>();
		}
		huTransactionExpectations.add(expectation);
		return expectation;
	}

	/**
	 * Gets {@link HUTransactionExpectation} by given <code>index</code>.
	 *
	 * NOTE: index starts from ZERO.
	 *
	 * @param index
	 * @return {@link HUTransactionExpectation}; never return null
	 */
	public HUTransactionExpectation<AllocationResultExpectation> huTransactionExpectation(final int index)
	{
		return huTransactionExpectations.get(index);
	}

	public AllocationResultExpectation noHUTransactions()
	{
		huTransactionExpectations = new ArrayList<>();
		return this;
	}
}
