package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@EqualsAndHashCode(doNotUseGetters = true)
public class Identifier
{
	private static final String DATETIME_PATTEN = "yyyyMMdd'T'HHmmssSSS";
	private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(DATETIME_PATTEN);

	/**
	 * Last timestamp handed out by {@link #toUniqueString()}; guarded by the class lock.
	 */
	private static LocalDateTime lastUniqueTimestamp = LocalDateTime.MIN;

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

	/**
	 * @return this identifier plus a timestamp suffix (yyyyMMdd'T'HHmmssSSS) that no other call in this JVM returns;
	 * a call in a millisecond already handed out gets the next free millisecond.
	 */
	public String toUniqueString() {return string + "_" + DATETIME_FORMAT.format(nextUniqueTimestamp());}

	private static synchronized LocalDateTime nextUniqueTimestamp()
	{
		final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
		final LocalDateTime next = now.isAfter(lastUniqueTimestamp) ? now : lastUniqueTimestamp.plus(1, ChronoUnit.MILLIS);
		lastUniqueTimestamp = next;
		return next;
	}

	public <ID extends RepoIdAware> ID toId(@NonNull final Class<ID> idClass)
	{
		return RepoIdAwares.ofObject(string, idClass);
	}

	public boolean isNullPlaceholder() {return string.equals("-");}
}
