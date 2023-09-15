package de.metas.acct.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.IAccountBL;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Org;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Org.class)
@Component
public class AD_Org
{
	private final IAccountBL accountBL = Services.get(IAccountBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_AD_Org.COLUMNNAME_Value, I_AD_Org.COLUMNNAME_Name })
	public void updateValidCombinations(final I_AD_Org record)
	{
		accountBL.updateValueDescriptionByElementTypes(ImmutableSet.of(AcctSchemaElementType.Organization, AcctSchemaElementType.OrgTrx), record.getAD_Org_ID());
	}
}
