package de.metas.workflow.execution.approval.strategy.check_superior_strategy;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.NonNull;

import java.util.List;

class DoNotCheckStrategy implements CheckSupervisorStrategy
{
	@Override
	public List<UserId> getSupervisors(@NonNull final GetSupervisorsRequest request)
	{
		return ImmutableList.of();
	}
}
