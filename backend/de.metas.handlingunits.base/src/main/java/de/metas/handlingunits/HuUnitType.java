package de.metas.handlingunits;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum HuUnitType implements ReferenceListAwareEnum
{
	/** Loading Unit (e.g. Palete) */
	LU(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit),
	/** Transport Unit (e.g. box) */
	TU(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),
	/** Virtual (e.g. a bunch of units measured in Kg) */
	VHU(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<HuUnitType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@JsonCreator
	public static HuUnitType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@JsonCreator
	public static HuUnitType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	@JsonValue
	public String toJson() {return getCode();}
}
