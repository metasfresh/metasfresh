package de.metas.ui.web.material.cockpit.v2.reservation;

import lombok.Getter;
import lombok.NonNull;

public enum AvailabilityType
{
	AVAILABLE("A"),
	RESERVED("R");

	@Getter
	private final String code;

	AvailabilityType(@NonNull final String code) {this.code = code;}

	@NonNull
	public static AvailabilityType ofCode(@NonNull final String code)
	{
		for (final AvailabilityType value : values())
		{
			if (value.code.equals(code))
			{
				return value;
			}
		}
		throw new IllegalArgumentException("No AvailabilityType for code: " + code);
	}
}
