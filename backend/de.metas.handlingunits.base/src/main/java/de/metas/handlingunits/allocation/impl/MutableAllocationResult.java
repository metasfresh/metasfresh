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
import java.util.Collections;
import java.util.List;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Allocation result that be created as an empty one and then can be altered by the code which dioes the allocating.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */class MutableAllocationResult extends AbstractAllocationResult implements IMutableAllocationResult
{
	private final BigDecimal qtyToAllocateInitial;
	private BigDecimal qtyToAllocate;

	private final List<IHUTransactionCandidate> transactions = new ArrayList<>();
	private final List<IHUTransactionCandidate> transactionsRO = Collections.unmodifiableList(transactions);

	private final List<IHUTransactionAttribute> attributeTransactions = new ArrayList<>();
	private final List<IHUTransactionAttribute> attributeTransactionsRO = Collections.unmodifiableList(attributeTransactions);

	/**
	 *
	 * @param qtyToAllocate the qty that shall be allocated. This quantity is subsequently reduced.
	 */
	public MutableAllocationResult(@NonNull final BigDecimal qtyToAllocate)
	{
		Check.errorIf(qtyToAllocate.signum() < 0, "The given qtyToAllocate={} needs to be >= 0", qtyToAllocate);

		this.qtyToAllocateInitial = qtyToAllocate;
		this.qtyToAllocate = qtyToAllocate;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MutableAllocationResult ["
				+ "\n qtyToAllocateInitial=" + qtyToAllocateInitial
				+ "\n qtyToAllocate=" + qtyToAllocate
				+ "\n qtyAllocated=" + getQtyAllocated());

		sb.append("\n transactions(" + transactions.size() + "):");
		for (final IHUTransactionCandidate transaction : transactions)
		{
			sb.append("\n\t").append(transaction);
		}

		sb.append("\n attribute transactions(" + attributeTransactions.size() + "):");
		for (final IHUTransactionAttribute attributeTransaction : attributeTransactions)
		{
			sb.append("\n\t").append(attributeTransaction);
		}

		sb.append("\n]");

		return sb.toString();
	}

	@Override
	public IMutableAllocationResult copy()
	{
		final MutableAllocationResult resultNew = new MutableAllocationResult(qtyToAllocateInitial);

		resultNew.qtyToAllocate = qtyToAllocate;
		resultNew.transactions.addAll(transactions);
		resultNew.attributeTransactions.addAll(attributeTransactions);

		return resultNew;
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(qtyToAllocate)
				.append(transactions)
				.append(attributeTransactions)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final MutableAllocationResult other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(qtyToAllocate, other.qtyToAllocate)
				.append(transactions, other.transactions)
				.append(attributeTransactions, other.attributeTransactions)
				.isEqual();
	}

	@Override
	public boolean isCompleted()
	{
		return qtyToAllocate.signum() == 0;
	}

	@Override
	public void subtractAllocatedQty(@NonNull final BigDecimal qtyAllocated)
	{
		final BigDecimal qtyToAllocateNew = qtyToAllocate.subtract(qtyAllocated);
		Check.assume(qtyToAllocateNew.signum() >= 0,
				"Cannot allocate {} when qtyToAllocate is {}", qtyAllocated, qtyToAllocate);

		qtyToAllocate = qtyToAllocateNew;
	}

	@Override
	public BigDecimal getQtyToAllocate()
	{
		return qtyToAllocate;
	}

	@Override
	public BigDecimal getQtyAllocated()
	{
		return qtyToAllocateInitial.subtract(qtyToAllocate);
	}

	@Override
	public void addTransaction(final IHUTransactionCandidate trx)
	{
		transactions.add(trx);
	}

	@Override
	public void addTransactions(final List<IHUTransactionCandidate> trxs)
	{
		trxs.forEach(trx -> addTransaction(trx));
	}

	@Override
	public List<IHUTransactionCandidate> getTransactions()
	{
		return transactionsRO;
	}

	@Override
	public void addAttributeTransaction(final IHUTransactionAttribute attributeTrx)
	{
		attributeTransactions.add(attributeTrx);
	}

	@Override
	public void addAttributeTransactions(final List<IHUTransactionAttribute> attributeTrxs)
	{
		attributeTransactions.addAll(attributeTrxs);
	}

	@Override
	public List<IHUTransactionAttribute> getAttributeTransactions()
	{
		return attributeTransactionsRO;
	}

	@Override
	public void aggregateTransactions()
	{
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		final List<IHUTransactionCandidate> aggregateTransactions = huTrxBL.aggregateTransactions(transactions);

		transactions.clear();
		transactions.addAll(aggregateTransactions);
	}
}
