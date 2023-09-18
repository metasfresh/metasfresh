package de.metas.acct.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.accounts.ValidCombinationService;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.ValidCombinationQuery;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Org;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Org.class)
@Component
@RequiredArgsConstructor
public class AD_Org
{
	private final ValidCombinationService validCombinationService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_AD_Org.COLUMNNAME_Value, I_AD_Org.COLUMNNAME_Name })
	public void updateValidCombinations(final I_AD_Org record)
	{
		validCombinationService.scheduleUpdateDescriptionAfterCommit(
				ValidCombinationQuery.ofElementTypesAndValue(
						ImmutableSet.of(AcctSchemaElementType.Organization, AcctSchemaElementType.OrgTrx),
						record.getAD_Org_ID()
				)
		);
	}
}
