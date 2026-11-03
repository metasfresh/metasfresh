package de.metas.payment.paymentterm.repository.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import lombok.ToString;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ToString
class PaymentTermMap
{
	public static final PaymentTermMap EMPTY = new PaymentTermMap(ImmutableList.of());

	private final ImmutableMap<PaymentTermId, PaymentTerm> byId;

	private PaymentTermMap(final List<PaymentTerm> list)
	{
		this.byId = Maps.uniqueIndex(list, PaymentTerm::getId);
	}

	public static PaymentTermMap ofList(final List<PaymentTerm> list)
	{
		return list.isEmpty() ? EMPTY : new PaymentTermMap(list);
	}

	public Optional<PaymentTerm> getById(final PaymentTermId id)
	{
		return Optional.ofNullable(byId.get(id));
	}

	public Set<PaymentTermId> getPaymentTermIdsByIsAllowOverrideDueDate(final boolean isAllowOverrideDueDate)
	{
		return byId.values().stream()
				.filter(pt -> pt.isAllowOverrideDueDate() == isAllowOverrideDueDate)
				.map(PaymentTerm::getId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
