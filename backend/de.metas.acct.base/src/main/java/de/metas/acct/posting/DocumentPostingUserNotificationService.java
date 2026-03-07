package de.metas.acct.posting;

import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.event.Topic;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DocumentPostingUserNotificationService
{
	private static final Topic NOTIFICATIONS_TOPIC = Topic.distributed("de.metas.acct.UserNotifications");

	@NonNull private final INotificationBL userNotifications = Services.get(INotificationBL.class);

	public void notifyPostingError(
			@NonNull final UserId recipientUserId,
			@NonNull final TableRecordReference documentRef,
			@NonNull final Exception exception)
	{
		notifyPostingError(recipientUserId, documentRef, AdempiereException.extractMessage(exception));
	}

	public void notifyPostingError(@NonNull final String message, final DocumentPostMultiRequest postRequests)
	{
		final List<UserNotificationRequest> notifications = postRequests.stream()
				.map(postRequest -> toUserNotificationRequestOrNull(postRequest, message))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		userNotifications.sendAfterCommit(notifications);
	}

	public void notifyPostingError(
			@NonNull final UserId recipientUserId,
			@NonNull final TableRecordReference documentRef,
			@NonNull final String message)
	{
		userNotifications.sendAfterCommit(toUserNotificationRequest(recipientUserId, documentRef, message));
	}

	private static UserNotificationRequest toUserNotificationRequestOrNull(
			@NonNull final DocumentPostRequest postRequest,
			@NonNull final String message)
	{
		final UserId recipientUserId = postRequest.getOnErrorNotifyUserId();
		if (recipientUserId == null) {return null;}

		return UserNotificationRequest.builder()
				.topic(NOTIFICATIONS_TOPIC)
				.recipientUserId(recipientUserId)
				.contentPlain(message)
				.targetAction(UserNotificationRequest.TargetRecordAction.of(postRequest.getRecord()))
				.build();
	}

	private static UserNotificationRequest toUserNotificationRequest(
			@NonNull final UserId recipientUserId,
			@NonNull final TableRecordReference documentRef,
			@NonNull final String message)
	{
		return UserNotificationRequest.builder()
				.topic(NOTIFICATIONS_TOPIC)
				.recipientUserId(recipientUserId)
				.contentPlain(message)
				.targetAction(UserNotificationRequest.TargetRecordAction.of(documentRef))
				.build();
	}

}
