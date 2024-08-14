package de.metas.copy_with_details.template;

import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;

public interface CopyTemplateCustomizer
{
	String getTableName();

	default ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName) {return ValueToCopy.NOT_SPECIFIED;}

	@NonNull
	default InSetPredicate<String> getChildTableNames() {return InSetPredicate.any();}
}
