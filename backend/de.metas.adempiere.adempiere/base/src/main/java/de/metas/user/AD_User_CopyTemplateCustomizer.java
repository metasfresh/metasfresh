package de.metas.user;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.I_AD_User;
import org.springframework.stereotype.Component;

@Component
public class AD_User_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_AD_User.Table_Name;}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.none();}
}
