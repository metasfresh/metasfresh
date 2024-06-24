package de.metas.pricing;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;
import org.springframework.stereotype.Component;

@Component
public class M_ProductPrice_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_M_ProductPrice.Table_Name;}

	@Override
	public ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName)
	{
		if (I_M_ProductPrice.COLUMNNAME_IsInvalidPrice.equals(columnName))
		{
			return ValueToCopy.explicitValueToSet(Boolean.TRUE);
		}
		else
		{
			return ValueToCopy.NOT_SPECIFIED;
		}
	}
}
