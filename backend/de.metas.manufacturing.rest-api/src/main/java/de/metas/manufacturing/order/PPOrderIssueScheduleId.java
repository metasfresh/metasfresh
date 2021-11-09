package de.metas.manufacturing.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class PPOrderIssueScheduleId implements RepoIdAware
{
	int repoId;

	private PPOrderIssueScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Order_IssueSchedule_ID");
	}

	@JsonCreator
	public static PPOrderIssueScheduleId ofRepoId(final int repoId) {return new PPOrderIssueScheduleId(repoId);}

	public static PPOrderIssueScheduleId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new PPOrderIssueScheduleId(repoId) : null;}

	public static int toRepoId(final PPOrderIssueScheduleId id) {return id != null ? id.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final PPOrderIssueScheduleId id1, @Nullable final PPOrderIssueScheduleId id2) {return Objects.equals(id1, id2);}
}
