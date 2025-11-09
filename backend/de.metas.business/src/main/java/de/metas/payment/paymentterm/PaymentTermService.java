package de.metas.payment.paymentterm;

import com.google.common.collect.ImmutableSet;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class PaymentTermService
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);

	@NonNull
	public PaymentTerm getById(@NonNull final PaymentTermId paymentTermId)
	{
		return paymentTermRepository.getById(paymentTermId);
	}

	@Nullable
	public PaymentTermId getOrCreateDerivedPaymentTerm(@Nullable final PaymentTermId basePaymentTermId, @Nullable final Percent discount)
	{
		return paymentTermRepository.getOrCreateDerivedPaymentTerm(basePaymentTermId, discount);
	}

	@NonNull
	public Percent getPaymentTermDiscount(PaymentTermId paymentTermId)
	{
		return paymentTermRepository.getById(paymentTermId).getDiscount();
	}

	public void validateNow(@NonNull final PaymentTermId paymentTermId)
	{
		validateNow(ImmutableSet.of(paymentTermId));
	}

	public void validateBeforeCommit(final PaymentTermId paymentTermId)
	{
		trxManager.accumulateAndProcessBeforeCommit(
				"PaymentTermService.validateBeforeCommit",
				Collections.singleton(paymentTermId),
				this::validateNow
		);
	}

	private void validateNow(@NonNull final Collection<PaymentTermId> paymentTermIds)
	{
		paymentTermRepository.newLoaderAndSaver()
				.syncStateToDatabase(ImmutableSet.copyOf(paymentTermIds));
	}
}
