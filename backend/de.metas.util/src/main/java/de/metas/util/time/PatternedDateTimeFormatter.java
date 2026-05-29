package de.metas.util.time;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode(of = "pattern")
public final class PatternedDateTimeFormatter
{
	@NonNull private final String pattern;
	@NonNull private final DateTimeFormatter formatter;

	private PatternedDateTimeFormatter(@NonNull final String pattern)
	{
		this.pattern = pattern;
		this.formatter = DateTimeFormatter.ofPattern(pattern);
	}

	@NonNull
	public static PatternedDateTimeFormatter ofPattern(@NonNull final String pattern)
	{
		return new PatternedDateTimeFormatter(pattern);
	}

	@Nullable
	public static PatternedDateTimeFormatter ofNullablePattern(@Nullable final String pattern)
	{
		final String patternNorm = StringUtils.trimBlankToNull(pattern);
		if (patternNorm == null)
		{
			return null;
		}

		return new PatternedDateTimeFormatter(patternNorm);
	}

	@Override
	@Deprecated
	public String toString() {return toPattern();}

	public String toPattern() {return pattern;}

	@Nullable
	public static String toPattern(@Nullable final PatternedDateTimeFormatter obj) {return obj != null ? obj.toPattern() : null;}

	@NonNull
	public LocalDate parseLocalDate(@NonNull final String valueStr)
	{
		return LocalDate.parse(valueStr, formatter);
	}
}
