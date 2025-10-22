package de.metas.payment.paymentterm.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;

import java.util.Collection;
import java.util.Optional;

public class PaymentTermMap
{
	private final ImmutableMap<PaymentTermId, PaymentTerm> paymentTermsById;

	PaymentTermMap(final Collection<PaymentTerm> paymentTerms)
	{
		paymentTermsById = Maps.uniqueIndex(paymentTerms, PaymentTerm::getId);
	}

	public Optional<PaymentTerm> getById(final PaymentTermId id)
	{
		return Optional.ofNullable(paymentTermsById.get(id));
	}
}
