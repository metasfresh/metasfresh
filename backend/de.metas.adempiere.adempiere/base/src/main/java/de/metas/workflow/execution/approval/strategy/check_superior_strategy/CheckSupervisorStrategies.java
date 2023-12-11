package de.metas.workflow.execution.approval.strategy.check_superior_strategy;

import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CheckSupervisorStrategies
{
	@NonNull private final DoNotCheckStrategy doNotCheck = new DoNotCheckStrategy();
	@NonNull private final FirstMatchingSupervisorStrategy firstMatching = new FirstMatchingSupervisorStrategy();
	@NonNull private final AllMatchingSupervisorsStrategy allMatching = new AllMatchingSupervisorsStrategy();

	public CheckSupervisorStrategy getStrategy(@NonNull final CheckSupervisorStrategyType type)
	{
		return switch (type)
		{
			case DoNotCheck -> doNotCheck;
			case FirstMatching -> firstMatching;
			case AllMathing -> allMatching;
		};
	}
}
