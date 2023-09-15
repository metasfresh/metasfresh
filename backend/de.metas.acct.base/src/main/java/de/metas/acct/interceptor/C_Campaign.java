package de.metas.acct.interceptor;

import de.metas.acct.api.IAccountBL;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Campaign.class)
@Component
public class C_Campaign
{
	private final IAccountBL accountBL = Services.get(IAccountBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_C_Campaign.COLUMNNAME_Value, I_C_Campaign.COLUMNNAME_Name })
	public void updateValidCombinations(final I_C_Campaign record)
	{
		accountBL.updateValueDescription(I_C_ValidCombination.COLUMNNAME_C_Campaign_ID + "=" + record.getC_Campaign_ID());
	}
}
