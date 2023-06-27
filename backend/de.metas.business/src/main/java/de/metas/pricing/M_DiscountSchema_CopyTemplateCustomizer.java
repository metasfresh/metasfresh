package de.metas.pricing;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.springframework.stereotype.Component;

@Component
public class M_DiscountSchema_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_M_DiscountSchema.Table_Name;}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.only(I_M_DiscountSchemaBreak.Table_Name, I_M_DiscountSchemaLine.Table_Name);}
}
