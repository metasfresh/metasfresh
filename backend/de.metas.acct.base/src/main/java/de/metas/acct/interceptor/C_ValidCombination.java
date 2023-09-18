package de.metas.acct.interceptor;

import de.metas.acct.accounts.ValidCombinationService;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.ModelValidator;

@Interceptor(I_C_ValidCombination.class)
@RequiredArgsConstructor
public class C_ValidCombination
{
	private final ValidCombinationService validCombinationService;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_ValidCombination account)
	{
		if (!ValidCombinationService.isAvoidUpdatingValueDescriptionOnSave(account))
		{
			validCombinationService.validate(account);
			validCombinationService.updateValueDescription(account);
		}
	}
}
