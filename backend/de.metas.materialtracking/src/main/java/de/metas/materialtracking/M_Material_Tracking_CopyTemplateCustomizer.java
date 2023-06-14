package de.metas.materialtracking;

import com.google.common.collect.ImmutableSet;
import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.materialtracking.ch.lagerkonf.interfaces.I_M_Material_Tracking;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;
import org.springframework.stereotype.Component;

@Component
public class M_Material_Tracking_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	private final static ImmutableSet<String> COLUMNS_TO_SUFFIX = ImmutableSet.of(de.metas.materialtracking.model.I_M_Material_Tracking.COLUMNNAME_Lot);
	private final static String CLONED_SUFFIX = "_cloned";

	@Override
	public String getTableName() {return I_M_Material_Tracking.Table_Name;}

	@Override
	public ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName)
	{
		return COLUMNS_TO_SUFFIX.contains(columnName)
				? ValueToCopy.appendSuffix(CLONED_SUFFIX)
				: ValueToCopy.NOT_SPECIFIED;
	}

	@Override
	public @NonNull InSetPredicate<String> getChildTableNames() {return InSetPredicate.none();}
}
