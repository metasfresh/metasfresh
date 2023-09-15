package de.metas.acct.interceptor;

import de.metas.acct.accounts.ValidCombinationService;
import de.metas.acct.api.AcctSchemaElementType;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Activity;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Activity.class)
@Component
@RequiredArgsConstructor
public class C_Activity
{
	private final ValidCombinationService validCombinationService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_C_Activity.COLUMNNAME_Value, I_C_Activity.COLUMNNAME_Name })
	public void updateValidCombinations(final I_C_Activity record)
	{
		validCombinationService.updateValueDescriptionByElementType(AcctSchemaElementType.Activity, record.getC_Activity_ID());
	}
}
