package de.metas.ui.web.material.cockpit.v2.reservation;

import lombok.Getter;
import lombok.NonNull;

public enum SupplyType
{
	ON_HAND("OH"),
	PLANNED_SUPPLY("PS");

	@Getter
	private final String code;

	SupplyType(@NonNull final String code) {this.code = code;}

	@NonNull
	public static SupplyType ofCode(@NonNull final String code)
	{
		for (final SupplyType value : values())
		{
			if (value.code.equals(code))
			{
				return value;
			}
		}
		throw new IllegalArgumentException("No SupplyType for code: " + code);
	}

	@NonNull
	public static SupplyType ofNullableCode(final String code)
	{
		if (code == null || code.isEmpty())
		{
			return ON_HAND;
		}
		return ofCode(code);
	}
}
