package de.metas.scannable_code.format.repository.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ScannableCode_Format_Part;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_ScannableCode_Format_Part.class)
@Component
public class C_ScannableCode_Format_Part
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_C_ScannableCode_Format_Part.COLUMNNAME_EndNo)
	public void beforeSave(final I_C_ScannableCode_Format_Part record)
	{
		// An empty End No means "open end" (read up to the next part / to the end of the code). Keep it blank (NULL) instead of 0,
		// so a typed 0 and an empty field look the same. The generated setter takes an int, hence setValue.
		if (record.getEndNo() == 0 && InterfaceWrapperHelper.getValueOrNull(record, I_C_ScannableCode_Format_Part.COLUMNNAME_EndNo) != null)
		{
			InterfaceWrapperHelper.setValue(record, I_C_ScannableCode_Format_Part.COLUMNNAME_EndNo, null);
		}
	}
}
