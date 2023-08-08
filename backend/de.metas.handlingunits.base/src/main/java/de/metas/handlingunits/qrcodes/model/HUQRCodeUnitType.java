package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public enum HUQRCodeUnitType implements ReferenceListAwareEnum
{
	LU(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit),
	TU(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),
	VHU(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI), // not sure if this is legit in real world
	;

	private static final ReferenceListAwareEnums.ValuesIndex<HUQRCodeUnitType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@JsonCreator
	public static HUQRCodeUnitType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public String getShortDisplayName() {return code;}

	@JsonValue
	public String toJson() {return getCode();}
}
