package de.metas.inoutcandidate.qty_reservation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SupplyType implements ReferenceListAwareEnum
{
	ON_HAND("OH"),
	PLANNED_SUPPLY("PS");

	private static final ReferenceListAwareEnums.ValuesIndex<SupplyType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@NonNull
	@JsonCreator
	public static SupplyType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@JsonValue
	public String toJson()
	{
		return code;
	}

	public boolean isPlannedSupply()
	{
		return this == PLANNED_SUPPLY;
	}
}
