package de.metas.calendar.simulation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
public final class CalendarSimulationId
{
	@NonNull String value;

	private CalendarSimulationId(@NonNull final String value)
	{
		this.value = value;
	}

	@JsonCreator
	public static CalendarSimulationId of(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid ID: " + value);
		}

		return new CalendarSimulationId(valueNorm);
	}

	public static CalendarSimulationId ofNullable(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? new CalendarSimulationId(valueNorm) : null;
	}

	public static CalendarSimulationId random()
	{
		return of(UUID.randomUUID().toString());
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return value;
	}

	public static boolean equals(@Nullable CalendarSimulationId id1, @Nullable CalendarSimulationId id2) {return Objects.equals(id1, id2);}
}
