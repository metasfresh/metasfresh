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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTransactionAttribute;

@Immutable
/* package */class AllocationResult extends AbstractAllocationResult
{
	private final BigDecimal qtyToAllocate;
	private final BigDecimal qtyAllocated;
	private final boolean completed;
	private final List<IHUTransaction> transactions;
	private final List<IHUTransactionAttribute> attributeTransactions;

	public AllocationResult(final BigDecimal qtyToAllocate,
			final BigDecimal qtyAllocated,
			final List<IHUTransaction> trxs,
			final List<IHUTransactionAttribute> attributeTrxs)
	{
		this.qtyToAllocate = qtyToAllocate;
		this.qtyAllocated = qtyAllocated;
		this.completed = qtyToAllocate.compareTo(qtyAllocated) == 0;
		this.attributeTransactions = Collections.unmodifiableList(new ArrayList<IHUTransactionAttribute>(attributeTrxs));
		this.transactions = Collections.unmodifiableList(new ArrayList<IHUTransaction>(trxs));
	}

	@Override
	public String toString()
	{
		return "AllocationResult ["
				+ "completed=" + completed
				+ ", qtyToAllocate=" + qtyToAllocate
				+ ", qtyAllocated=" + qtyAllocated
				+ ", transactions(" + transactions.size() + ")=" + transactions
				+ ", attribute transactions(" + attributeTransactions.size() + ")=" + attributeTransactions
				+ "]";
	}

	@Override
	public boolean isCompleted()
	{
		return completed;
	}

	@Override
	public BigDecimal getQtyToAllocate()
	{
		return qtyToAllocate;
	}

	@Override
	public BigDecimal getQtyAllocated()
	{
		return qtyAllocated;
	}

	@Override
	public List<IHUTransaction> getTransactions()
	{
		return ImmutableList.copyOf(transactions);
	}

	@Override
	public List<IHUTransactionAttribute> getAttributeTransactions()
	{
		return ImmutableList.copyOf(attributeTransactions);
	}
}
