package de.metas.phonecall;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.I_C_Phonecall_Schema_Version;
import org.compiere.model.I_C_Phonecall_Schema_Version_Line;
import org.springframework.stereotype.Component;

@Component
public class C_Phonecall_Schema_Version_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_C_Phonecall_Schema_Version.Table_Name;}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.only(I_C_Phonecall_Schema_Version_Line.Table_Name);}
}
