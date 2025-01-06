package de.metas.invoicecandidate.modelvalidator;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_InvoiceSchedule;
import org.springframework.stereotype.Component;

@Interceptor(I_C_InvoiceSchedule.class)
@Callout(I_C_InvoiceSchedule.class)
@Component
public class C_InvoiceSchedule
{

	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE },
			ifColumnsChanged = {
					I_C_InvoiceSchedule.COLUMNNAME_InvoiceDistance,
					I_C_InvoiceSchedule.COLUMNNAME_InvoiceFrequency,
					I_C_InvoiceSchedule.COLUMNNAME_InvoiceDay,
					I_C_InvoiceSchedule.COLUMNNAME_InvoiceWeekDay,
					I_C_InvoiceSchedule.COLUMNNAME_IsAmount,
					I_C_InvoiceSchedule.COLUMNNAME_Amt
			})
	public void invalidateICs(@NonNull final I_C_InvoiceSchedule invoiceSchedule)
	{
		invoiceCandBL.invalidateForInvoiceSchedule(invoiceSchedule);
	}

	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = I_C_InvoiceSchedule.COLUMNNAME_InvoiceFrequency)
	@CalloutMethod(columnNames = I_C_InvoiceSchedule.COLUMNNAME_InvoiceFrequency)
	public void setInvoiceDistance(@NonNull final I_C_InvoiceSchedule invoiceSchedule)
	{
		if (X_C_InvoiceSchedule.INVOICEFREQUENCY_TwiceMonthly.equals(invoiceSchedule.getInvoiceFrequency()))
		{
			// actually, invoiceDistance is not validated in this case, but for the UI's sake, we set it to one
			invoiceSchedule.setInvoiceDistance(1);
		}
	}
}
