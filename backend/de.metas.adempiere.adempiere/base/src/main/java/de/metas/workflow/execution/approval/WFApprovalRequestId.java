package de.metas.workflow.execution.approval;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class WFApprovalRequestId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static WFApprovalRequestId ofRepoId(final int repoId) {return new WFApprovalRequestId(repoId);}

	@Nullable
	public static WFApprovalRequestId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new WFApprovalRequestId(repoId) : null;}

	public static int toRepoId(@Nullable final WFApprovalRequestId id) {return id != null ? id.getRepoId() : -1;}

	private WFApprovalRequestId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_WF_Approval_Request_ID");
	}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}

	public static boolean equals(@Nullable final WFApprovalRequestId id1, @Nullable final WFApprovalRequestId id2) {return Objects.equals(id1, id2);}
}
