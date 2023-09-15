package de.metas.acct.interceptor;

import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.IAccountBL;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_Product.class)
@Component
public class M_Product
{
	private final IAccountBL accountBL = Services.get(IAccountBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_M_Product.COLUMNNAME_Value, I_M_Product.COLUMNNAME_Name })
	public void updateValidCombinations(final I_M_Product record)
	{
		accountBL.updateValueDescriptionByElementType(AcctSchemaElementType.Product, record.getM_Product_ID());
	}
}
