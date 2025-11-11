/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.payment.paymentterm.repository;

import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.repository.impl.PaymentTermLoaderAndSaver;
import de.metas.util.ISingletonService;
import de.metas.util.lang.Percent;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

public interface IPaymentTermRepository extends ISingletonService
{
	@NonNull
	PaymentTerm getById(PaymentTermId paymentTermId);

	@NonNull
	Optional<PaymentTermId> firstIdOnly(@NonNull PaymentTermQuery build);

	// this method is implemented after a code block from MOrder.beforeSave()
	@NonNull
	Optional<PaymentTermId> getDefaultPaymentTermId();

	/**
	 * Convenience method that thorws an exception if no term is found.
	 */
	@NonNull
	PaymentTermId retrievePaymentTermIdNotNull(@NonNull PaymentTermQuery build);

	PaymentTermLoaderAndSaver newLoaderAndSaver();

	@Nullable
	PaymentTermId getOrCreateDerivedPaymentTerm(
			@Nullable PaymentTermId basePaymentTermId,
			@Nullable Percent discount);
}
