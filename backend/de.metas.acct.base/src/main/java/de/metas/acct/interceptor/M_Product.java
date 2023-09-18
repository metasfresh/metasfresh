package de.metas.acct.interceptor;

import de.metas.acct.accounts.ValidCombinationService;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.ValidCombinationQuery;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_Product.class)
@Component
@RequiredArgsConstructor
public class M_Product
{
	private final ValidCombinationService validCombinationService;

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_M_Product.COLUMNNAME_Value, I_M_Product.COLUMNNAME_Name })
	public void updateValidCombinations(final I_M_Product record)
	{
		validCombinationService.scheduleUpdateDescriptionAfterCommit(ValidCombinationQuery.ofElementTypeAndValue(AcctSchemaElementType.Product, record.getM_Product_ID()));
	}
}
