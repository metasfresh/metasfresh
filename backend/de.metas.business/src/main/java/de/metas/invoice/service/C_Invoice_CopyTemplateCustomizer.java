package de.metas.invoice.service;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

@Component
public class C_Invoice_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_C_Invoice.Table_Name;}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.only(I_C_InvoiceLine.Table_Name);}
}
