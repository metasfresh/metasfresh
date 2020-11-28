package de.metas.banking;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.payment.PaymentId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
@ToString
public final class BankStatementLineReferenceList implements Iterable<BankStatementLineReference>
{
	public static BankStatementLineReferenceList of(@NonNull final Collection<BankStatementLineReference> collection)
	{
		return !collection.isEmpty() ? new BankStatementLineReferenceList(collection) : EMPTY;
	}

	public static Collector<BankStatementLineReference, ?, BankStatementLineReferenceList> collector()
	{
		return GuavaCollectors.collectUsingListAccumulator(BankStatementLineReferenceList::of);
	}

	public static final BankStatementLineReferenceList EMPTY = new BankStatementLineReferenceList(ImmutableList.of());

	private final ImmutableList<BankStatementLineReference> list;

	private BankStatementLineReferenceList(@NonNull final Collection<BankStatementLineReference> collection)
	{
		this.list = ImmutableList.copyOf(collection);
	}

	@Override
	public Iterator<BankStatementLineReference> iterator()
	{
		return list.iterator();
	}

	public Stream<BankStatementLineReference> stream()
	{
		return list.stream();
	}

	public ImmutableSet<PaymentId> getPaymentIds()
	{
		return list.stream()
				.map(BankStatementLineReference::getPaymentId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public ImmutableSet<BankStatementLineId> getBankStatementLineIds()
	{
		return list.stream()
				.map(BankStatementLineReference::getBankStatementLineId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public ImmutableSet<BankStatementLineRefId> getBankStatementLineRefIds()
	{
		return list.stream()
				.map(BankStatementLineReference::getBankStatementLineRefId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}
}
