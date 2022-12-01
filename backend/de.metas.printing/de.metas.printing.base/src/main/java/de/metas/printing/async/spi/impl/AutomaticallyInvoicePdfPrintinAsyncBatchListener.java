package de.metas.printing.async.spi.impl;

import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.spi.IAsyncBatchListener;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.printing.Printing_Constants;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * Listener for notifying the user when a invoice PDF concatenating was done
 * <ul>
 * when the workpackage was processed, the listener is triggered
 * </ul>
 */
public class AutomaticallyInvoicePdfPrintinAsyncBatchListener implements IAsyncBatchListener
{
	private static final AdMessageKey MSG_Event_PDFGenerated = AdMessageKey.of("AutomaticallyInvoicePdfPrintinAsyncBatchListener_Pdf_Done");
	private static final AdWindowId WINDOW_ID_C_Async_Batch = AdWindowId.ofRepoId(540231);

	// services
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	@Override
	public void createNotice(@NonNull final I_C_Async_Batch asyncBatch)
	{
		if(asyncBatchBL.isAsyncBatchTypeInternalName(asyncBatch, Async_Constants.C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting))
		{
			sendUserNotifications(asyncBatch);
		}
	}

	private void sendUserNotifications(@NonNull final I_C_Async_Batch asyncBatch)
	{
		final TableRecordReference asyncBatchItemRef = TableRecordReference.of(asyncBatch);
		notificationBL.send(UserNotificationRequest.builder()
									.topic(Printing_Constants.USER_NOTIFICATIONS_TOPIC)
									.recipientUserId(UserId.ofRepoId(asyncBatch.getCreatedBy()))
									.contentADMessage(MSG_Event_PDFGenerated)
									.contentADMessageParam(asyncBatch.getCountProcessed())
									.targetAction(TargetRecordAction.ofRecordAndWindow(asyncBatchItemRef, WINDOW_ID_C_Async_Batch))
									.build());

	}
}
