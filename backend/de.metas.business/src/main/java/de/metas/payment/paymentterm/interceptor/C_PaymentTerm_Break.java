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

import com.google.common.collect.ImmutableList;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermConstants;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_PaymentTerm_Break.class)
@Component
public class C_PaymentTerm_Break
{
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_C_PaymentTerm_Break.COLUMNNAME_Percent)
	public void assertTotalPercentageUnderLimit(@NonNull final I_C_PaymentTerm_Break record)
	{
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
		final PaymentTermBreakId currentBreakIdToExclude = PaymentTermBreakId.ofRepoIdOrNull(paymentTermId, record.getC_PaymentTerm_Break_ID());
		final ImmutableList<PaymentTermBreak> allBreaksForTerm = paymentTermRepository.retrievePaymentTermBreaksList(paymentTermId);

		if (allBreaksForTerm.isEmpty())
		{
			return;
		}

		final Percent otherBreaksTotalPercent = allBreaksForTerm
				.stream()
				.filter(b -> !PaymentTermBreakId.equals(b.getId(), currentBreakIdToExclude))
				.map(PaymentTermBreak::getPercent)
				.reduce(Percent.ZERO, Percent::add);

		final int currentRecordPercent = record.getPercent();
		final int totalPercent = otherBreaksTotalPercent.toInt() + currentRecordPercent;

		if (totalPercent > 100)
		{
			throw new AdempiereException(PaymentTermConstants.C_PAYMENTTERM_BREAK_TotalPercentTooHigh, totalPercent);
		}
	}
}