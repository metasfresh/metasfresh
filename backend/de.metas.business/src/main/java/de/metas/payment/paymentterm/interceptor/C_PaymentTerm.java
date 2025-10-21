/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermConstants;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_PaymentTerm.class)
@Component
@RequiredArgsConstructor
public class C_PaymentTerm
{
	private  final @NonNull  PaymentTermService paymentTermService;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE}, ifColumnsChanged = I_C_PaymentTerm.COLUMNNAME_IsComplex)
	public void assertValidComplexPaymentTerm(@NonNull final I_C_PaymentTerm record)
	{
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());

		if (record.isComplex() && paymentTermService.hasPaySchedule(paymentTermId))
		{
			throw new AdempiereException(PaymentTermConstants.MSG_ComplexTermConflict)
					.appendParametersToMessage()
					.setParameter("PaymentTerm", record.getName());
		}

		final PaymentTerm paymentTerm = paymentTermService.getById(paymentTermId);

		if (record.isComplex() && paymentTerm.getSortedBreaks().isEmpty())
		{
			throw new AdempiereException(PaymentTermConstants.C_PAYMENTTERM_BREAK_DoNotExist)
					.appendParametersToMessage()
					.setParameter("PaymentTerm", record.getName());
		}
	}
}