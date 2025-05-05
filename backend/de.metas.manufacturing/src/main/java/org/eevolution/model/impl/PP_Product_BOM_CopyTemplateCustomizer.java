package org.eevolution.model.impl;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.springframework.stereotype.Component;

@Component
public class PP_Product_BOM_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_PP_Product_BOM.Table_Name;}

	@Override
	@NonNull
	public InSetPredicate<String> getChildTableNames() {return InSetPredicate.only(I_PP_Product_BOMLine.Table_Name);}
}
