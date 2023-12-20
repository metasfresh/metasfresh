package de.metas.handlingunits.report.labels;

import de.metas.handlingunits.model.X_M_HU_Label_Config;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum HULabelSourceDocType implements ReferenceListAwareEnum
{
	Manufacturing(X_M_HU_Label_Config.HU_SOURCEDOCTYPE_Manufacturing),
	MaterialReceipt(X_M_HU_Label_Config.HU_SOURCEDOCTYPE_MaterialReceipt),
	Picking(X_M_HU_Label_Config.HU_SOURCEDOCTYPE_Picking),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<HULabelSourceDocType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@Nullable
	public static HULabelSourceDocType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static HULabelSourceDocType ofCode(@NonNull final String code) {return index.ofCode(code);}
}
