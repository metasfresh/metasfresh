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

package de.metas.payment.paymentterm.interceptor;

import de.metas.payment.paymentterm.PaymentTermConstants;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxListenerManager;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Interceptor(I_C_PaySchedule.class)
@Component
@RequiredArgsConstructor
public class C_PaySchedule
{
	@NonNull private final PaymentTermService paymentTermService;
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_PaySchedule record)
	{
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
		if (paymentTermService.hasPaymentTermBreaks(paymentTermId)) {throw new AdempiereException(PaymentTermConstants.MSG_ComplexTermConflict);}
	}

	@NonNull private static final ThreadLocal<Set<PaymentTermId>> to_validate_paymentTermIds = ThreadLocal.withInitial(HashSet::new);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void validatePaySchedules(@NonNull final I_C_PaySchedule record)
	{
		final Set<PaymentTermId> idsToValidate = to_validate_paymentTermIds.get();
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
		final ITrxListenerManager listenerManager = trxManager.getTrxListenerManager(InterfaceWrapperHelper.getTrxName(record));

		if (idsToValidate.add(paymentTermId))
		{

			listenerManager.runBeforeCommit(() -> {

				final boolean isValid = paymentTermService.validate(paymentTermId);
				paymentTermService.setPaymentTermIsValidAndSave(paymentTermId, isValid);

			});

			listenerManager.runAfterCommit(to_validate_paymentTermIds::remove);
		}

	}
}