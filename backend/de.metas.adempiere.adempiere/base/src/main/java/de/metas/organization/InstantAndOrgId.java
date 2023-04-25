package de.metas.organization;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Function;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InstantAndOrgId implements Comparable<InstantAndOrgId>
{
	@NonNull private final Instant instant;
	@NonNull private final OrgId orgId;

	private InstantAndOrgId(@NonNull final Instant instant, @NonNull final OrgId orgId)
	{
		this.instant = instant;
		this.orgId = orgId;
	}

	public static InstantAndOrgId ofInstant(@NonNull final Instant instant, @NonNull final OrgId orgId)
	{
		return new InstantAndOrgId(instant, orgId);
	}

	public static InstantAndOrgId ofTimestamp(@NonNull final java.sql.Timestamp timestamp, @NonNull final OrgId orgId)
	{
		return new InstantAndOrgId(timestamp.toInstant(), orgId);
	}

	public static InstantAndOrgId ofTimestamp(@NonNull final java.sql.Timestamp timestamp, final int orgRepoId)
	{
		return new InstantAndOrgId(timestamp.toInstant(), OrgId.ofRepoId(orgRepoId));
	}

	public @NonNull OrgId getOrgId() {return orgId;}

	public Instant toInstant() {return instant;}

	public ZonedDateTime toZonedDateTime(@NonNull final ZoneId zoneId) {return instant.atZone(zoneId);}

	public ZonedDateTime toZonedDateTime(@NonNull final Function<OrgId, ZoneId> orgMapper) {return instant.atZone(orgMapper.apply(orgId));}

	public java.sql.Timestamp toTimestamp() {return java.sql.Timestamp.from(instant);}

	@Override
	public int compareTo(@NonNull final InstantAndOrgId other) {return this.instant.compareTo(other.instant);}
}
