package de.metas.acct.interceptor;

import de.metas.acct.api.IAccountBL;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Project.class)
@Component
public class C_Project
{
	private final IAccountBL accountBL = Services.get(IAccountBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_C_Project.COLUMNNAME_Value, I_C_Project.COLUMNNAME_Name })
	public void updateValidCombinations(final I_C_Project record)
	{
		accountBL.updateValueDescription(I_C_ValidCombination.COLUMNNAME_C_Project_ID + "=" + record.getC_Project_ID());
	}
}
