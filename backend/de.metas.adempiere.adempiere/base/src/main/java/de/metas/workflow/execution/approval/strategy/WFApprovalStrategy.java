package de.metas.workflow.execution.approval.strategy;

import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.workflow.WFResponsible;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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
		@NonNull TableRecordReference documentRef;
		@NonNull UserId documentOwnerId;
		@NonNull ClientAndOrgId clientAndOrgId;

		@Nullable Money amountToApprove;

		@NonNull UserId workflowInvokerId;
		@NonNull WFResponsible responsible;
	}

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode
	@ToString
	class Response
	{
		public enum Type
		{APPROVED, REJECTED, PENDING, FORWARD_TO}

		@NonNull WFApprovalStrategy.Response.Type type;
		@Nullable UserId forwardToUserId;

		public static final Response APPROVED = new Response(Type.APPROVED, null);
		public static final Response REJECTED = new Response(Type.REJECTED, null);
		public static final Response PENDING = new Response(Type.PENDING, null);

		public static Response forwardTo(@NonNull final UserId userId)
		{
			return new Response(Type.FORWARD_TO, userId);
		}

		public interface CaseMapper<R>
		{
			R approved();

			R rejected();

			R pending();

			R forwardTo(@NonNull UserId userId);
		}

		public <R> R map(@NonNull final WFApprovalStrategy.Response.CaseMapper<R> mapper)
		{
			return switch (type)
			{
				case APPROVED -> mapper.approved();
				case REJECTED -> mapper.rejected();
				case PENDING -> mapper.pending();
				case FORWARD_TO -> mapper.forwardTo(Check.assumeNotNull(forwardToUserId, "forwardToUserId is set: {}", this));
			};
		}
	}
}
