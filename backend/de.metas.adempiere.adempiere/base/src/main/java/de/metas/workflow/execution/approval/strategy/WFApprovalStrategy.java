package de.metas.workflow.execution.approval.strategy;

import de.metas.document.DocBaseType;
import de.metas.document.engine.IDocument;
import de.metas.money.Money;
import de.metas.notification.UserNotificationRequest.TargetAction;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.workflow.WFResponsible;
import de.metas.workflow.execution.WFActivityId;
import de.metas.workflow.execution.WFProcessId;
import de.metas.workflow.execution.WorkflowExecutionContext;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

public interface WFApprovalStrategy
{
	Response approve(@NonNull WFApprovalStrategy.Request request);

	@Value
	@Builder
	class Request
	{
		@NonNull WorkflowExecutionContext context;
		@NonNull TableRecordReference documentRef;
		@NonNull UserId documentOwnerId;
		@NonNull ClientAndOrgId clientAndOrgId;
		@Nullable String documentNo;
		@Nullable DocBaseType docBaseType;

		@Nullable Money amountToApprove;

		@NonNull UserId workflowInvokerId;
		@NonNull WFResponsible responsible;
		@Nullable WFProcessId wfProcessId;
		@Nullable WFActivityId wfActivityId;

		public IDocument getDocument() {return context.getDocument(documentRef);}
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode
	@ToString
	class Response
	{
		public enum Type
		{APPROVED, REJECTED, PENDING, FORWARD_TO}

		@NonNull private final WFApprovalStrategy.Response.Type type;
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

		public <R> R map(@NonNull final WFApprovalStrategy.Response.CaseMapper<R> mapper)
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
