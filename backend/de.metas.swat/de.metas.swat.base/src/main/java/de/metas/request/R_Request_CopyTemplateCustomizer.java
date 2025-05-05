package de.metas.request;

import de.metas.callcenter.model.I_R_Request;
import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.I_R_RequestUpdates;
import org.springframework.stereotype.Component;

@Component
public class R_Request_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_R_Request.Table_Name;}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.only(I_R_RequestUpdates.Table_Name);}
}
