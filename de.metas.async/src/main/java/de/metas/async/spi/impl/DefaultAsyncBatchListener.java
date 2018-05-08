/**
 *
 */
package de.metas.async.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Set;

import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.NotificationType;
import org.adempiere.user.api.UserNotificationsConfig;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;

import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.spi.IAsyncBatchListener;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;

/**
 * @author cg
 *
 */
public class DefaultAsyncBatchListener implements IAsyncBatchListener
{
	private static final String MSG_ASYNC_PROCESSED = "Notice_Async_Processed";

	@Override
	public void createNotice(final I_C_Async_Batch asyncBatch)
	{
		final I_C_Async_Batch_Type asyncBatchType = loadOutOfTrx(asyncBatch.getC_Async_Batch_Type_ID(), I_C_Async_Batch_Type.class);
		if (!IAsyncBatchDAO.ASYNC_BATCH_TYPE_DEFAULT.equals(asyncBatchType.getInternalName()))
		{
			return;
		}

		final UserNotificationsConfig notificationsConfig = createUserNotificationsConfigOrNull(asyncBatch.getCreatedBy(), asyncBatchType);
		if (notificationsConfig == null)
		{
			return;
		}

		final INotificationBL notificationBL = Services.get(INotificationBL.class);
		notificationBL.notifyRecipient(
				UserNotificationRequest.builder()
						.notificationsConfig(notificationsConfig)
						.subjectADMessage(MSG_ASYNC_PROCESSED)
						.subjectADMessageParam(asyncBatch.getName())
						.contentADMessage(MSG_ASYNC_PROCESSED)
						.contentADMessageParam(asyncBatch.getName())
						.targetRecord(TableRecordReference.of(asyncBatch))
						.build());
	}

	private static UserNotificationsConfig createUserNotificationsConfigOrNull(final int recipientUserId, final I_C_Async_Batch_Type asyncBatchType)
	{
		final Set<NotificationType> notificationTypes = extractNotificationTypes(asyncBatchType);
		if (notificationTypes.isEmpty())
		{
			return null;
		}

		final IUserBL userBL = Services.get(IUserBL.class);
		return userBL.getUserNotificationsConfig(recipientUserId).deriveWithNotificationTypes(notificationTypes);
	}

	private static Set<NotificationType> extractNotificationTypes(final I_C_Async_Batch_Type asyncBatchType)
	{
		final ImmutableSet.Builder<NotificationType> notificationTypes = ImmutableSet.<NotificationType> builder();
		if (asyncBatchType.isSendMail())
		{
			notificationTypes.add(NotificationType.EMail);
		}
		else if (asyncBatchType.isSendNotification())
		{
			notificationTypes.add(NotificationType.Notice);
		}
		return notificationTypes.build();
	}

}
