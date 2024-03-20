package de.metas.workflow.execution.approval.strategy;

import de.metas.currency.ICurrencyBL;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.execution.approval.strategy.check_superior_strategy.CheckSupervisorStrategies;
import de.metas.workflow.execution.approval.strategy.check_superior_strategy.CheckSupervisorStrategy;
import de.metas.workflow.execution.approval.strategy.check_superior_strategy.CheckSupervisorStrategyType;
import de.metas.workflow.execution.approval.strategy.type_handlers.DocApprovalStrategyTypeHandlers;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.compiere.Adempiere;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocApprovalStrategyService
{
	@NonNull private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	@NonNull private final DocApprovalStrategyRepository repository;
	@NonNull private final DocApprovalStrategyTypeHandlers typeHandlers;
	@NonNull private final CheckSupervisorStrategies checkSupervisorStrategies;

	public static DocApprovalStrategyService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new DocApprovalStrategyService(
				new DocApprovalStrategyRepository(),
				DocApprovalStrategyTypeHandlers.newInstanceForUnitTesting(),
				new CheckSupervisorStrategies()
		);
	}

	@Value
	@Builder
	public static class GetUsersToApproveRequest
	{
		@NonNull LocalDate evaluationDate;
		@NonNull DocApprovalStrategyId docApprovalStrategyId;
		@NonNull ClientAndOrgId clientAndOrgId;
		@Nullable Money amountToApprove;
		@NonNull UserId workflowInvokerId;
		@NonNull WFResponsible workflowResponsible;
		@NonNull UserId documentOwnerId;
		@Nullable UserId requestorId;
		@Nullable UserId projectManagerId;
	}

	public List<UserId> getUsersToApprove(final @NotNull GetUsersToApproveRequest request)
	{
		final DocApprovalStrategy strategy = repository.getById(request.getDocApprovalStrategyId());
		final UsersToApproveList usersToApproveList = UsersToApproveList.empty();
		for (final DocApprovalStrategyLine strategyLine : strategy.getLinesInOrder())
		{
			usersToApproveList.addAll(getUsersToApprove(request, strategyLine));
		}

		return usersToApproveList.toList();
	}

	private UsersToApproveList getUsersToApprove(@NonNull final GetUsersToApproveRequest request, final DocApprovalStrategyLine strategyLine)
	{
		if (!isMatching(request, strategyLine))
		{
			return UsersToApproveList.empty();
		}

		//
		// Get users to approve
		final UsersToApproveList userIdsToApprove = typeHandlers.getHandler(strategyLine.getType()).getUsersToApprove(request, strategyLine);

		//
		// Expand users to approve with their supervisors, if required
		for (final UserId approverId : userIdsToApprove.toList())
		{
			userIdsToApprove.addAll(getSupervisorIds(approverId, strategyLine.getCheckSupervisorStrategyType(), request));
		}

		userIdsToApprove.remove(request.getWorkflowInvokerId()); // remove workflow invoker because it makes so sense "to be asked to approve something you triggered"

		return userIdsToApprove;
	}

	private boolean isMatching(@NonNull final GetUsersToApproveRequest request, final DocApprovalStrategyLine strategyLine)
	{
		if (strategyLine.getIsProjectManagerSet().isPresent())
		{
			final boolean isProjectedManagerExpectedToBeSet = strategyLine.getIsProjectManagerSet().isTrue();
			final boolean isProjectManagerSet = request.getProjectManagerId() != null;
			if (isProjectManagerSet != isProjectedManagerExpectedToBeSet)
			{
				return false;
			}
		}

		final Money minimumAmountThatRequiresApproval = strategyLine.getMinimumAmountThatRequiresApproval();
		if (minimumAmountThatRequiresApproval != null && minimumAmountThatRequiresApproval.signum() > 0)
		{
			final Money amountToApprove = request.getAmountToApprove();
			if (amountToApprove == null)
			{
				return false;
			}

			final Money amountToApproveConv = currencyBL.convert(amountToApprove, minimumAmountThatRequiresApproval.getCurrencyId(), request.getEvaluationDate(), request.getClientAndOrgId());
			//noinspection RedundantIfStatement
			if (amountToApproveConv.isLessThan(minimumAmountThatRequiresApproval))
			{
				return false;
			}
		}

		return true;
	}

	private List<UserId> getSupervisorIds(
			@NonNull final UserId userId,
			@NonNull final CheckSupervisorStrategyType checkSupervisorStrategyType,
			@NonNull final GetUsersToApproveRequest context)
	{
		return checkSupervisorStrategies
				.getStrategy(checkSupervisorStrategyType)
				.getSupervisors(CheckSupervisorStrategy.GetSupervisorsRequest.builder()
						.userId(userId)
						.documentOwnerId(context.getDocumentOwnerId())
						.amountToApprove(context.getAmountToApprove())
						.clientAndOrgId(context.getClientAndOrgId())
						.evaluationDate(context.getEvaluationDate())
						.build());
	}
}
