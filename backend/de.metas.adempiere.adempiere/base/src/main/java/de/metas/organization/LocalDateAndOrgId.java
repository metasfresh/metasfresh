package de.metas.organization;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.function.Function;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LocalDateAndOrgId implements Comparable<LocalDateAndOrgId>
{
	@NonNull private final LocalDate localDate;
	@Getter @NonNull private final OrgId orgId;

	private LocalDateAndOrgId(final @NonNull LocalDate localDate, final @NonNull OrgId orgId)
	{
		this.localDate = localDate;
		this.orgId = orgId;
	}

	public static LocalDateAndOrgId ofLocalDate(@NonNull final LocalDate localDate, @NonNull final OrgId orgId)
	{
		return new LocalDateAndOrgId(localDate, orgId);
	}

	public static LocalDateAndOrgId ofTimestamp(@NonNull final Timestamp timestamp, @NonNull final OrgId orgId, @NonNull final Function<OrgId, ZoneId> orgMapper)
	{
		final LocalDate localDate = timestamp.toInstant().atZone(orgMapper.apply(orgId)).toLocalDate();
		return new LocalDateAndOrgId(localDate, orgId);
	}

	@Nullable
	public static LocalDateAndOrgId ofNullableTimestamp(@Nullable final Timestamp timestamp, @NonNull final OrgId orgId, @NonNull final Function<OrgId, ZoneId> orgMapper)
	{
		return timestamp != null ? ofTimestamp(timestamp, orgId, orgMapper) : null;
	}

	public Instant toInstant(@NonNull final Function<OrgId, ZoneId> orgMapper)
	{
		return localDate.atStartOfDay().atZone(orgMapper.apply(orgId)).toInstant();
	}

	public Instant toEndOfDayInstant(@NonNull final Function<OrgId, ZoneId> orgMapper)
	{
		return localDate.atTime(LocalTime.MAX).atZone(orgMapper.apply(orgId)).toInstant();
	}

	public LocalDate toLocalDate() {return localDate;}

	public Timestamp toTimestamp(@NonNull final Function<OrgId, ZoneId> orgMapper)
	{
		return Timestamp.from(toInstant(orgMapper));
	}

	@Override
	public int compareTo(final @Nullable LocalDateAndOrgId o)
	{
		return compareToByLocalDate(o);
	}

	/**
	 * In {@code LocalDateAndOrgId}, only the localDate is the actual data, while orgId is used to give context for reading & writing purposes.
	 * A calendar date is directly comparable to another one, without regard of "from which org has this date been extracted?"
	 * That's why a comparison by local date is enough to provide correct ordering, even with different {@code OrgId}s.
	 *
	 * @see #compareTo(LocalDateAndOrgId)
	 */
	private int compareToByLocalDate(final @Nullable LocalDateAndOrgId o)
	{
		if (o == null)
		{
			return 1;
		}
		return this.localDate.compareTo(o.localDate);
	}
}