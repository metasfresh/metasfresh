package de.metas.acct.interceptor;

import de.metas.acct.api.IAccountBL;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.ModelValidator;

@Interceptor(I_C_ValidCombination.class)
public class C_ValidCombination
{
	private final IAccountBL accountBL;

	public C_ValidCombination(@NonNull final IAccountBL accountBL)
	{
		this.accountBL = accountBL;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_ValidCombination account)
	{
		accountBL.setValueDescription(account);
		accountBL.validate(account);
	}
}
