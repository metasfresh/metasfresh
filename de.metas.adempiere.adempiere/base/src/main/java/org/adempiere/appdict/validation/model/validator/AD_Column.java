package org.adempiere.appdict.validation.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Column;
import org.compiere.model.ModelValidator;

@Interceptor(I_AD_Column.class)
public class AD_Column
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = { I_AD_Column.COLUMNNAME_Updated, I_AD_Column.COLUMNNAME_UpdatedBy })
	public void disallowLogging(final I_AD_Column column)
	{
		column.setIsAllowLogging(false);
	}

}
