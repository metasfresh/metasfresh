package de.metas.manufacturing.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_MobileUI_MFG_Config;

import javax.annotation.Nullable;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum ReceiveUnitType implements ReferenceListAwareEnum
{
	CU(X_MobileUI_MFG_Config.RECEIVEUNITTYPE_CU),
	TU(X_MobileUI_MFG_Config.RECEIVEUNITTYPE_TU),
	;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<ReceiveUnitType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@JsonCreator
	@NonNull
	public static ReceiveUnitType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static ReceiveUnitType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static Optional<ReceiveUnitType> optionalOfNullableCode(@Nullable final String code) {return index.optionalOfNullableCode(code);}

	@JsonValue
	public String toJson() {return code;}

	public boolean isTU() {return this == TU;}
}
