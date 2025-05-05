package de.metas.workflow.execution.approval.strategy.check_superior_strategy;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_Doc_Approval_Strategy_Line;

import javax.annotation.Nullable;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum CheckSupervisorStrategyType implements ReferenceListAwareEnum
{
	DoNotCheck(X_C_Doc_Approval_Strategy_Line.SUPERVISORCHECKSTRATEGY_DoNotCheck),
	FirstMatching(X_C_Doc_Approval_Strategy_Line.SUPERVISORCHECKSTRATEGY_FirstMatching),
	AllMathing(X_C_Doc_Approval_Strategy_Line.SUPERVISORCHECKSTRATEGY_AllMatching),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<CheckSupervisorStrategyType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static CheckSupervisorStrategyType ofCode(@NonNull String code) {return index.ofCode(code);}

	public static Optional<CheckSupervisorStrategyType> optionalOfNullableCode(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}
}
