package de.metas.organization;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.function.Function;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class LocalDateAndOrgId
{
	@NonNull private final LocalDate localDate;
	@NonNull private final OrgId orgId;

	private LocalDateAndOrgId(final @NonNull LocalDate localDate, final @NonNull OrgId orgId)
	{
		this.localDate = localDate;
		this.orgId = orgId;
	}

	public static LocalDateAndOrgId ofLocalDate(@NonNull final LocalDate localDate, @NonNull final OrgId orgId)
	{
		return new LocalDateAndOrgId(localDate, orgId);
	}

	public Instant toInstant(@NonNull final Function<OrgId, ZoneId> orgMapper)
	{
		return localDate.atStartOfDay().atZone(orgMapper.apply(orgId)).toInstant();
	}

	public Instant toEndOfDayInstant(@NonNull final Function<OrgId, ZoneId> orgMapper)
	{
		return localDate.atTime(LocalTime.MAX).atZone(orgMapper.apply(orgId)).toInstant();
	}
}
