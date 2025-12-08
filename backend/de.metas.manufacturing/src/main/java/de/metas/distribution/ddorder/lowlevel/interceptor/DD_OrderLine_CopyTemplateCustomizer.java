package de.metas.distribution.ddorder.lowlevel.interceptor;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Component;

@Component
public class DD_OrderLine_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_DD_OrderLine.Table_Name;}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.none();}

}
