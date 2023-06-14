package de.metas.shipping;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class M_ShipperTransportation_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	@Override
	public String getTableName() {return I_M_ShipperTransportation.Table_Name;}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.none();}
}
