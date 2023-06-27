package de.metas.material.planning.pporder;

import de.metas.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class UserInstructions
{
	private final String description;

	@Nullable
	public static UserInstructions ofNullableString(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null
				? new UserInstructions(stringNorm)
				: null;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return description;
	}
}
