package de.metas.payment.paymentterm.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Optional;

public class PaymentTermMap
{
	private final ImmutableMap<PaymentTermId, PaymentTerm> paymentTermsById;

	PaymentTermMap(final Collection<PaymentTerm> paymentTerms)
	{
		paymentTermsById = Maps.uniqueIndex(paymentTerms, PaymentTerm::getId);
	}

	@NonNull
	public PaymentTerm getById(@NonNull final PaymentTermId paymentTermId)
	{
		return getByIdIfExists(paymentTermId)
				.orElseThrow(() -> new AdempiereException("No active payment term found for " + paymentTermId));
	}

	public Optional<PaymentTerm> getByIdIfExists(@NonNull final PaymentTermId paymentTermId)
	{
		return Optional.ofNullable(paymentTermsById.get(paymentTermId));
	}

}
