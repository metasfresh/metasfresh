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

import de.metas.invoice.InvoiceId;
import de.metas.order.paymentschedule.InvoicePayScheduleValidator;
import de.metas.payment.paymentterm.PaymentTermService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_InvoicePaySchedule.class)
@Component
@RequiredArgsConstructor
public class C_InvoicePaySchedule
{
	private final @NonNull PaymentTermService paymentTermService;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE } , ifColumnsChanged = {I_C_InvoicePaySchedule.COLUMNNAME_DueAmt})
	public void afterSave(final I_C_InvoicePaySchedule record)
	{
		final InvoicePayScheduleValidator validator = new InvoicePayScheduleValidator(paymentTermService);
		validator.validate(InvoiceId.ofRepoId(record.getC_Invoice_ID()));
	}
}