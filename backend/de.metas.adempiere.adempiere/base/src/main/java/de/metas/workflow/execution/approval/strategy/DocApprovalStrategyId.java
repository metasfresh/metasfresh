package de.metas.workflow.execution.approval.strategy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_C_Doc_Approval_Strategy;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class DocApprovalStrategyId implements RepoIdAware
{
	public static final DocApprovalStrategyId DEFAULT_ID = new DocApprovalStrategyId(1);
	int repoId;

	private DocApprovalStrategyId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Doc_Approval_Strategy.COLUMNNAME_C_Doc_Approval_Strategy_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	@JsonCreator
	public static DocApprovalStrategyId ofRepoId(final int repoId)
	{
		if (repoId == DEFAULT_ID.repoId)
		{
			return DEFAULT_ID;
		}
		return new DocApprovalStrategyId(repoId);
	}

	@Nullable
	public static DocApprovalStrategyId ofRepoIdOrNull(@Nullable final Integer repoId) {return repoId != null && repoId > 0 ? ofRepoId(repoId) : null;}

	@Nullable
	public static DocApprovalStrategyId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? ofRepoId(repoId) : null;}

	public static Optional<DocApprovalStrategyId> optionalOfRepoId(@Nullable final Integer repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	public static int toRepoId(@Nullable final DocApprovalStrategyId DocApprovalStrategyId)
	{
		return DocApprovalStrategyId != null ? DocApprovalStrategyId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final DocApprovalStrategyId o1, @Nullable final DocApprovalStrategyId o2)
	{
		return Objects.equals(o1, o2);
	}
}
