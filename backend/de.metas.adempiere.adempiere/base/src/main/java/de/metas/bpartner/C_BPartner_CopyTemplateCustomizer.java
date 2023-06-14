package de.metas.bpartner;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.springframework.stereotype.Component;

@Component
public class C_BPartner_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName()
	{
		return I_C_BPartner.Table_Name;
	}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.only(I_C_BP_PrintFormat.Table_Name, I_C_BPartner_CreditLimit.Table_Name);}
}
