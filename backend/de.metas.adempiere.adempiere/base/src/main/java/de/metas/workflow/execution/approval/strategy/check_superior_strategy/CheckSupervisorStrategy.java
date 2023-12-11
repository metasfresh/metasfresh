package de.metas.workflow.execution.approval.strategy.check_superior_strategy;

import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public interface CheckSupervisorStrategy
{
	@Value
	@Builder
	class GetSupervisorsRequest
	{
		@NonNull UserId userId;
		@NonNull UserId documentOwnerId;
		@Nullable Money amountToApprove;
		@NonNull ClientAndOrgId clientAndOrgId;
		@NonNull LocalDate evaluationDate;
	}

	List<UserId> getSupervisors(@NonNull GetSupervisorsRequest request);
}
