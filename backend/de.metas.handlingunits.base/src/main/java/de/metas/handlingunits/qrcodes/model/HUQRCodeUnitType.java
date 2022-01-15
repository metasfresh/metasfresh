package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public enum HUQRCodeUnitType implements ReferenceListAwareEnum
{
	LU("LU"),
	TU("TU"),
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
