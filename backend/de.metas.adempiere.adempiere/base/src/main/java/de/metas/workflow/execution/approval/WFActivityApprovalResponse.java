package de.metas.workflow.execution.approval;

import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class WFActivityApprovalResponse
{
	public enum Type
	{APPROVED, REJECTED, PENDING, FORWARD_TO}

	@NonNull private final Type type;
	@Nullable private final UserId forwardToUserId;
	@Nullable private final UserNotificationRequest.TargetAction notificationTargetAction;

	public static final WFActivityApprovalResponse APPROVED = new WFActivityApprovalResponse(Type.APPROVED, null, null);
	public static final WFActivityApprovalResponse REJECTED = new WFActivityApprovalResponse(Type.REJECTED, null, null);
	public static final WFActivityApprovalResponse PENDING = new WFActivityApprovalResponse(Type.PENDING, null, null);

	private static final AdWindowId WINDOW_ID_MyApprovals = AdWindowId.ofRepoId(541736);
	private static final UserNotificationRequest.TargetAction TARGET_MyApprovalsView = UserNotificationRequest.TargetViewAction.openNewView(WINDOW_ID_MyApprovals);

	public static WFActivityApprovalResponse forwardTo(@NonNull final UserId userId) {return new WFActivityApprovalResponse(Type.FORWARD_TO, userId, TARGET_MyApprovalsView);}

	public interface CaseMapper<R>
	{
		R approved();

		R rejected();

		R pending();

		R forwardTo(@NonNull UserId userId, @Nullable UserNotificationRequest.TargetAction notificationTargetAction);
	}

	public <R> R map(@NonNull final CaseMapper<R> mapper)
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
