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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTransactionAttribute;
import de.metas.handlingunits.impl.HUTransaction;

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

	private final List<IHUTransaction> transactions = new ArrayList<IHUTransaction>();
	private final List<IHUTransaction> transactionsRO = Collections.unmodifiableList(transactions);
	
	private final List<IHUTransactionAttribute> attributeTransactions = new ArrayList<IHUTransactionAttribute>();
	private final List<IHUTransactionAttribute> attributeTransactionsRO = Collections.unmodifiableList(attributeTransactions);

	/**
	 * 
	 * @param qtyToAllocate the qty that shall be allocated. This quantity is subsequently reduced.
	 */
	public MutableAllocationResult(final BigDecimal qtyToAllocate)
	{
		Check.assume(qtyToAllocate.signum() >= 0, "qty >= 0 ({})");

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
		for (final IHUTransaction transaction : transactions)
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
	public void substractAllocatedQty(final BigDecimal qtyAllocated)
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
	public void addTransaction(final IHUTransaction trx)
	{
		transactions.add(trx);
	}

	@Override
	public void addTransactions(final List<IHUTransaction> trxs)
	{
		trxs.forEach(trx -> addTransaction(trx));
	}

	@Override
	public List<IHUTransaction> getTransactions()
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
		final Map<ArrayKey, IHUTransaction> transactionsAggregateMap = new HashMap<>();

		final List<IHUTransaction> notAggregated = new ArrayList<>();

		for (final IHUTransaction trx : transactions)
		{
			if (trx.getCounterpart() != null)
			{
				// we don't want to aggregate paired trxCandidates because we want to discard the trxCandidates this method was called with.
				// unless we don't have to, we don't want to delve into those intricacies...
				notAggregated.add(trx);

			}

			// note that we use the ID if we can, because we don't want this to fail e.g. because of two different versions of the "same" VHU-item
			final ArrayKey key = Util.mkKey(
					// trxCandidate.getCounterpart(),
					trx.getDate(),
					trx.getHUStatus(),
					// trxCandidate.getM_HU(), just delegates to HU_Item
					trx.getM_HU_Item() == null ? -1 : trx.getM_HU_Item().getM_HU_Item_ID(),
					trx.getM_Locator() == null ? -1 : trx.getM_Locator().getM_Locator_ID(),
					trx.getProduct() == null ? -1 : trx.getProduct().getM_Product_ID(),
					// trxCandidate.getQuantity(),
					trx.getReferencedModel() == null ? -1: TableRecordReference.of(trx.getReferencedModel()),
					// trxCandidate.getVHU(), just delegates to VHU_Item
					trx.getVHU_Item() == null ? -1 : trx.getVHU_Item().getM_HU_Item_ID(),
					trx.isSkipProcessing());

			transactionsAggregateMap.merge(key,
					trx,
					(existingCand, newCand) -> {

						final HUTransaction mergedCandidate = new HUTransaction(existingCand.getReferencedModel(),
								existingCand.getM_HU_Item(),
								existingCand.getVHU_Item(),
								existingCand.getProduct(),
								existingCand.getQuantity().add(newCand.getQuantity()),
								existingCand.getDate(),
								existingCand.getM_Locator(),
								existingCand.getHUStatus());

						if (existingCand.isSkipProcessing())
						{
							mergedCandidate.setSkipProcessing();
						}

						return mergedCandidate;
					});

		}

		transactions.clear();
		transactions.addAll(notAggregated);
		transactions.addAll(transactionsAggregateMap.values());
	}
}
