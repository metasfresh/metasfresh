package de.metas.cucumber.stepdefs;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

@Value
@Builder
public class ValueAndName
{
	@NonNull String value;
	@NonNull String name;

	public static ValueAndName ofValue(@NonNull final String value) {return builder().value(value).name(value).build();}

	public static ValueAndName ofName(@NonNull final String name) {return builder().value(name).name(name).build();}

	public static ValueAndName ofValueAndName(@NonNull final String value, @NonNull final String name) {return builder().value(value).name(name).build();}

	@Nullable
	public static Optional<ValueAndName> ofNullableValueAndName(@Nullable final String value, @Nullable final String name)
	{
		if (value == null && name == null)
		{
			return Optional.empty();
		}
		final ValueAndNameBuilder builder = builder();
		if (value != null)
		{
			builder.value(value);
		}
		if (name != null)
		{
			builder.name(name);
		}
		return Optional.of(builder.build());
	}

	public static ValueAndName unique() {return unique(null);}

	public static ValueAndName unique(@Nullable final String prefix)
	{
		final String prefixNorm = prefix != null ? prefix + "_" : "";
		final String name = prefixNorm + Instant.now().toString();
		return ofName(name);
	}
}
