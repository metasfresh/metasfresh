package de.metas.copy_with_details.template;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.copy.ValueToCopy;

@EqualsAndHashCode
@ToString
@Builder
public final class CopyTemplateColumn
{
	@Getter @NonNull private final String columnName;
	@Getter @NonNull private final ValueToCopy valueToCopy;

}
