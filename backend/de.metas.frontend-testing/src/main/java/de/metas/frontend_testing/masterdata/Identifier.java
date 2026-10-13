package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(doNotUseGetters = true)
public class Identifier
{
	@NonNull private final String string;

	private Identifier(@NonNull final String string)
	{
		this.string = string;
	}

	@JsonCreator
	@NonNull
	public static Identifier ofString(@NonNull final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			throw new IllegalArgumentException("Invalid identifier: " + string);
		}
		return new Identifier(stringNorm);
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return string;}
}
