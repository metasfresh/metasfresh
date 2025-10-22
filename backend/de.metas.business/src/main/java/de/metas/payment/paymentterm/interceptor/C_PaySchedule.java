package de.metas.payment.paymentterm.interceptor;

import de.metas.payment.paymentterm.PaymentTermConstants;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_PaySchedule.class)
@Component
@RequiredArgsConstructor
public class C_PaySchedule
{
	private final @NonNull PaymentTermService paymentTermService;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_PaySchedule record, final ModelChangeType timing)
	{
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
		if (paymentTermService.hasPaymentTermBreaks(paymentTermId))
		{
			throw new AdempiereException(PaymentTermConstants.C_PAYMENTTERM_CantHaveBreaksAndSchedules);
		}
	}
}
