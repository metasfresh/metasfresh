package org.adempiere.model;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;
import org.springframework.stereotype.Component;

@Component
public class C_OrderLine_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName)
	{
		return CopyTemplateCustomizer.super.extractValueToCopy(poInfo, columnName);
	}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.none();}
}
