package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@EqualsAndHashCode(doNotUseGetters = true)
public class Identifier
{
	@NonNull private final String string;

	private Identifier(@NonNull final String string)
	{
		this.string = string;
	}

	@Nullable
	public static Identifier ofNullableString(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null ? new Identifier(stringNorm) : null;
	}

	@NonNull
	public static Identifier ofNullableStringOrUnique(@Nullable final String string, @Nullable String uniquePrefix)
	{
		final Identifier id = ofNullableString(string);
		return id != null ? id : unique(uniquePrefix);
	}

	@NonNull
	public static Identifier unique(@Nullable final String prefix)
	{
		final String prefixNorm = prefix != null ? prefix + "_" : "";
		return ofString(prefixNorm + Instant.now().toString());
	}

	@JsonCreator
	@NonNull
	public static Identifier ofString(@NonNull final String string)
	{
		final Identifier id = ofNullableString(string);
		if (id == null)
		{
			throw new IllegalArgumentException("Invalid identifier: " + string);
		}
		return id;
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return string;}

	public String toUniqueString() {return string + "_" + Instant.now().toString();}
}
