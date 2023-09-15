package de.metas.acct.interceptor;

import de.metas.acct.accounts.ValidCombinationService;
import de.metas.acct.api.AcctSchemaElementType;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Campaign.class)
@Component
@RequiredArgsConstructor
public class C_Campaign
{
	private final ValidCombinationService validCombinationService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_C_Campaign.COLUMNNAME_Value, I_C_Campaign.COLUMNNAME_Name })
	public void updateValidCombinations(final I_C_Campaign record)
	{
		validCombinationService.updateValueDescriptionByElementType(AcctSchemaElementType.Campaign, record.getC_Campaign_ID());
	}
}
