package de.metas.document.approval_strategy;

import de.metas.job.JobId;
import de.metas.money.Money;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class DocApprovalStrategyLine
{
	@NonNull SeqNo seqNo;
	@NonNull DocApprovalStrategyLineType type;
	@NonNull OptionalBoolean isProjectManagerSet;
	@Nullable JobId jobId;
	@Nullable Money minimumAmountThatRequiresApproval;

	@Builder
	private DocApprovalStrategyLine(
			@NonNull final SeqNo seqNo,
			@NonNull final DocApprovalStrategyLineType type,
			@NonNull final OptionalBoolean isProjectManagerSet,
			@Nullable final JobId jobId,
			@Nullable final Money minimumAmountThatRequiresApproval)
	{
		this.seqNo = seqNo;
		this.type = type;
		this.isProjectManagerSet = isProjectManagerSet;
		this.jobId = jobId;
		this.minimumAmountThatRequiresApproval = minimumAmountThatRequiresApproval;
	}

	@NonNull
	public JobId getJobIdNotNull() {return Check.assumeNotNull(jobId, "job shall be set for {}", this);}
}
