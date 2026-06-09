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

import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_PaymentTerm_Break.class)
@Component
@RequiredArgsConstructor
public class C_PaymentTerm_Break
{
	@NonNull private final PaymentTermService paymentTermService;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_C_PaymentTerm_Break record)
	{
		paymentTermService.validateBeforeCommit(PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(final I_C_PaymentTerm_Break record)
	{
		paymentTermService.validateBeforeCommit(PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID()));
	}
}