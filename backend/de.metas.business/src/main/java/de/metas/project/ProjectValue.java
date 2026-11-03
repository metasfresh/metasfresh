package de.metas.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode
public class ProjectValue
{
	@NonNull private final String value;

	private ProjectValue(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("ProjectValue is not valid: " + value);
		}

		this.value = valueNorm;
	}

	public static ProjectValue ofString(@NonNull final String value)
	{
		return new ProjectValue(value);
	}

	@JsonCreator
	public static ProjectValue ofNullableString(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? new ProjectValue(valueNorm) : null;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return value;
	}

	@NonNull
	@JsonValue
	public String getAsString()
	{
		return value;
	}
}
