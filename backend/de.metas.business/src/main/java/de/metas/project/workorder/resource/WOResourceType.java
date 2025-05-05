package de.metas.project.workorder.resource;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_Project_WO_Resource;

import javax.annotation.Nullable;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum WOResourceType implements ReferenceListAwareEnum
{
	MACHINE(X_C_Project_WO_Resource.RESOURCETYPE_Machine),
	HUMAN(X_C_Project_WO_Resource.RESOURCETYPE_HumanResource),
	;

	@NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<WOResourceType> index = ReferenceListAwareEnums.index(values());

	public static WOResourceType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static Optional<WOResourceType> optionalOfNullableCode(@Nullable final String code) {return index.optionalOfNullableCode(code);}
}
