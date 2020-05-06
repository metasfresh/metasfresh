package de.metas.invoice.interceptor;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.util.Services;

@Validator(I_C_InvoiceLine.class)
@Component
public class C_InvoiceLine
{
	/**
	 * Set QtyInvoicedInPriceUOM, just to make sure is up2date.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setQtyInvoicedInPriceUOM(final I_C_InvoiceLine invoiceLine)
	{
		Services.get(IInvoiceLineBL.class).setQtyInvoicedInPriceUOM(invoiceLine);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void updateIsReadOnly(final I_C_InvoiceLine invoiceLine)
	{
		Services.get(IInvoiceBL.class).updateInvoiceLineIsReadOnlyFlags(InterfaceWrapperHelper.create(invoiceLine.getC_Invoice(), I_C_Invoice.class), invoiceLine);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE,
	}, ifColumnsChanged = I_C_InvoiceLine.COLUMNNAME_C_OrderLine_ID)
	public void setIsPackagingMaterial(final I_C_InvoiceLine invoiceLine)
	{
		if(invoiceLine.getC_OrderLine() == null)
		{
			// in case the c_orderline_id is removed, make sure the flag is on false. The user can set it on true, manually
			invoiceLine.setIsPackagingMaterial(false);
			return;
		}

		final de.metas.interfaces.I_C_OrderLine ol = InterfaceWrapperHelper.create(invoiceLine.getC_OrderLine(), de.metas.interfaces.I_C_OrderLine.class);

		invoiceLine.setIsPackagingMaterial(ol.isPackagingMaterial());
	}
}
