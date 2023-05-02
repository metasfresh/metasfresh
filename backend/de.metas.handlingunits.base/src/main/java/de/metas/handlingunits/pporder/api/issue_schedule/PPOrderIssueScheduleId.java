package de.metas.handlingunits.pporder.api.issue_schedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

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

	public static PPOrderIssueScheduleId ofString(@NonNull final String idStr)
	{
		try
		{
			return ofRepoId(Integer.parseInt(idStr));
		}
		catch (final Exception ex)
		{
			final AdempiereException metasfreshEx = new AdempiereException("Invalid id: `" + idStr + "`");
			metasfreshEx.addSuppressed(ex);
			throw metasfreshEx;
		}
	}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final PPOrderIssueScheduleId id1, @Nullable final PPOrderIssueScheduleId id2) {return Objects.equals(id1, id2);}
}
