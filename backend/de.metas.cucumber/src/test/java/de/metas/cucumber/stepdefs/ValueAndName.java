package de.metas.cucumber.stepdefs;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class ValueAndName
{
	@NonNull String value;
	@NonNull String name;

	public static ValueAndName ofValue(@NonNull final String value) {return builder().value(value).name(value).build();}

	public static ValueAndName ofName(@NonNull final String name) {return builder().value(name).name(name).build();}

	public static ValueAndName ofValueAndName(@NonNull final String value, @NonNull final String name) {return builder().value(value).name(name).build();}

	public static ValueAndName unique() {return unique(null);}

	public static ValueAndName unique(@Nullable final String prefix)
	{
		String prefixNorm = prefix != null ? prefix + "_" : "";
		final String name = prefixNorm + Instant.now().toString();
		return ofName(name);
	}
}
