package de.metas.workflow.execution.approval.strategy;

import de.metas.document.approval_strategy.DocApprovalStrategyId;
import de.metas.document.engine.IDocument;
import de.metas.money.Money;
import de.metas.notification.UserNotificationRequest.TargetAction;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.execution.WFActivityId;
import de.metas.workflow.execution.WFProcessId;
import de.metas.workflow.execution.WorkflowExecutionContext;
import de.metas.workflow.execution.approval.WFApprovalRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.LocalDate;

public interface WFApprovalStrategyHandler
{
	Response approve(@NonNull WFApprovalStrategyHandler.Request request);

	@Value
	@Builder
	class Request
	{
		@NonNull WorkflowExecutionContext context;
		@NonNull TableRecordReference documentRef;
		@NonNull UserId documentOwnerId;
		@NonNull ClientAndOrgId clientAndOrgId;
		@NonNull WFApprovalRequest.DocumentInfo documentInfo;
		@Nullable DocApprovalStrategyId docApprovalStrategyId;

		@Nullable Money amountToApprove;

		@NonNull UserId workflowInvokerId;
		@NonNull WFResponsible responsible;
		@Nullable WFProcessId wfProcessId;
		@Nullable WFActivityId wfActivityId;

		public LocalDate getEvaluationTimeAsLocalDate() {return context.getEvaluationTimeAsLocalDate();}

		public IDocument getDocument() {return context.getDocument(documentRef);}

		public ClientId getClientId() {return clientAndOrgId.getClientId();}

		public OrgId getOrgId() {return clientAndOrgId.getOrgId();}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode
	@ToString
	class Response
	{
		public enum Type
		{APPROVED, REJECTED, PENDING, FORWARD_TO}

		@NonNull private final WFApprovalStrategyHandler.Response.Type type;
		@Nullable private final UserId forwardToUserId;
		@Nullable private final TargetAction notificationTargetAction;

		public static final Response APPROVED = new Response(Type.APPROVED, null, null);
		public static final Response REJECTED = new Response(Type.REJECTED, null, null);
		public static final Response PENDING = new Response(Type.PENDING, null, null);

		public static Response forwardTo(@NonNull final UserId userId) {return new Response(Type.FORWARD_TO, userId, null);}

		public static Response forwardTo(@NonNull final UserId userId, @NonNull TargetAction notificationTargetAction) {return new Response(Type.FORWARD_TO, userId, notificationTargetAction);}

		public interface CaseMapper<R>
		{
			R approved();

			R rejected();

			R pending();

			R forwardTo(@NonNull UserId userId, @Nullable TargetAction notificationTargetAction);
		}

		public <R> R map(@NonNull final WFApprovalStrategyHandler.Response.CaseMapper<R> mapper)
		{
			return switch (type)
			{
				case APPROVED -> mapper.approved();
				case REJECTED -> mapper.rejected();
				case PENDING -> mapper.pending();
				case FORWARD_TO -> mapper.forwardTo(Check.assumeNotNull(forwardToUserId, "forwardToUserId is set: {}", this), notificationTargetAction);
			};
		}
	}
}
