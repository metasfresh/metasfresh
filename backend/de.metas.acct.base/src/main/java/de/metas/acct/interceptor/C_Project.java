package de.metas.acct.interceptor;

import de.metas.acct.accounts.ValidCombinationService;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.ValidCombinationQuery;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Project;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Project.class)
@Component
@RequiredArgsConstructor
public class C_Project
{
	private final ValidCombinationService validCombinationService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_C_Project.COLUMNNAME_Value, I_C_Project.COLUMNNAME_Name })
	public void updateValidCombinations(final I_C_Project record)
	{
		validCombinationService.scheduleUpdateDescriptionAfterCommit(ValidCombinationQuery.ofElementTypeAndValue(AcctSchemaElementType.Project, record.getC_Project_ID()));
	}
}
