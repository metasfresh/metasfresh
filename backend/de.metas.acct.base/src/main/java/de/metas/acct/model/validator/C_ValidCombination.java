package de.metas.acct.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.ModelValidator;

import de.metas.acct.api.IAccountBL;
import de.metas.util.Services;

@Interceptor(I_C_ValidCombination.class)
public class C_ValidCombination
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_ValidCombination account)
	{
		final IAccountBL accountBL = Services.get(IAccountBL.class);
		accountBL.setValueDescription(account);
		accountBL.validate(account);
	}
}
