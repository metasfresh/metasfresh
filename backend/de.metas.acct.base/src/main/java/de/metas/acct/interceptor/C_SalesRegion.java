package de.metas.acct.interceptor;

import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.IAccountBL;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_SalesRegion;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_SalesRegion.class)
@Component
public class C_SalesRegion
{
	private final IAccountBL accountBL = Services.get(IAccountBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_C_SalesRegion.COLUMNNAME_Value, I_C_SalesRegion.COLUMNNAME_Name })
	public void updateValidCombinations(final I_C_SalesRegion record)
	{
		accountBL.updateValueDescriptionByElementType(AcctSchemaElementType.SalesRegion, record.getC_SalesRegion_ID());
	}
}
