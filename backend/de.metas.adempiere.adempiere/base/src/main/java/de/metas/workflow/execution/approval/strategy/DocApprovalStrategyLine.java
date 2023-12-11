package de.metas.workflow.execution.approval.strategy;

import de.metas.workflow.execution.approval.strategy.check_superior_strategy.CheckSupervisorStrategyType;
import de.metas.workflow.execution.approval.strategy.type_handlers.DocApprovalStrategyType;
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

	@NonNull DocApprovalStrategyType type;
	@Nullable JobId jobId;
	@NonNull CheckSupervisorStrategyType checkSupervisorStrategyType;

	//
	// Condition
	@NonNull OptionalBoolean isProjectManagerSet;
	@Nullable Money minimumAmountThatRequiresApproval;

	@Builder
	private DocApprovalStrategyLine(
			@NonNull final SeqNo seqNo,
			@NonNull final DocApprovalStrategyType type,
			@Nullable final JobId jobId,
			@Nullable CheckSupervisorStrategyType checkSupervisorStrategyType,
			@Nullable final OptionalBoolean isProjectManagerSet,
			@Nullable final Money minimumAmountThatRequiresApproval)
	{
		this.seqNo = seqNo;
		this.type = type;
		this.checkSupervisorStrategyType = checkSupervisorStrategyType != null ? checkSupervisorStrategyType : CheckSupervisorStrategyType.DoNotCheck;
		this.jobId = jobId;
		this.isProjectManagerSet = isProjectManagerSet != null ? isProjectManagerSet : OptionalBoolean.UNKNOWN;
		this.minimumAmountThatRequiresApproval = minimumAmountThatRequiresApproval;
	}

	@NonNull
	public JobId getJobIdNotNull() {return Check.assumeNotNull(jobId, "job shall be set for {}", this);}
}
