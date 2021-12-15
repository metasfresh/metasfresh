package de.metas.handlingunits;

import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum HUUnitType implements ReferenceListAwareEnum
{
	LU(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit),
	TU(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),
	CU(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI),
	;

	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<HUUnitType> index = ReferenceListAwareEnums.index(values());

	public static HUUnitType ofCode(@NonNull final String code) {return index.ofCode(code);}
}
