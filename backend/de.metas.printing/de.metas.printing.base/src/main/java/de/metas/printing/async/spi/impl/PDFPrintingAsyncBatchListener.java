package de.metas.printing.async.spi.impl;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.async.spi.IAsyncBatchListener;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_AD_Archive;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Listener for notifying the user when a PDF printing was done
 * <ul>
 * when the workpackage was processed, the listener is triggered
 * </ul>
 * <ul>
 * checks first if the workpackage was notified
 * </ul>
 * <ul>
 * if the Wp needs to be notified, build a message with a summary and a link to the newly created printing queue
 * </ul>
 * 
 * @author cg
 *
 */
public class PDFPrintingAsyncBatchListener implements IAsyncBatchListener
{
	// services
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IPrintingDAO dao = Services.get(IPrintingDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

	private static final AdMessageKey MSG_Event_PDFGenerated = AdMessageKey.of("PDFPrintingAsyncBatchListener_PrintJob_Done");
	private static final AdWindowId WINDOW_ID_PrintingQueue = AdWindowId.ofRepoId(540165); // FIXME: hardcoded

	@Override
	public void createNotice(final I_C_Async_Batch asyncBatch)
	{
		if(asyncBatchBL.isAsyncBatchTypeInternalName(asyncBatch, Printing_Constants.C_Async_Batch_InternalName_PDFPrinting))
		{

			final I_C_Queue_WorkPackage wp = asyncBatch.getLastProcessed_WorkPackage();
			if (wp != null && wp.getC_Queue_WorkPackage_ID() > 0)
			{
				final I_C_Queue_WorkPackage notifiableWP = asyncBatchBL.notify(asyncBatch, wp);

				if (notifiableWP != null)
				{
					final List<I_C_Print_Job_Instructions> printJobInstructions = queueDAO.retrieveAllItems(notifiableWP, I_C_Print_Job_Instructions.class);
					for (final I_C_Print_Job_Instructions pji : printJobInstructions)
					{
						final Iterator<I_C_Print_Job_Line> jobLines = dao.retrievePrintJobLines(pji);
						final SeenPrintPackages seenPrintPackages = collectSeenPrintPackages(jobLines);
						sendUserNotifications(notifiableWP, seenPrintPackages, asyncBatch);
					}
				}
			}

		}
	}

	/**
	 * collects in a map already seen packages
	 */
	private SeenPrintPackages collectSeenPrintPackages(final Iterator<I_C_Print_Job_Line> jobLines)
	{
		final SeenPrintPackages seenPackages = new SeenPrintPackages();

		for (final I_C_Print_Job_Line jobLine : IteratorUtils.asIterable(jobLines))
		{
			final int printPackageId = jobLine.getC_Print_Package_ID();
			if (printPackageId <= 0)
			{
				continue;
			}

			seenPackages.incrementCounterForPrintPackageId(printPackageId);
		}

		return seenPackages;
	}

	/**
	 * trigger notification event for processed workpackage
	 */
	private void sendUserNotifications(
			final I_C_Queue_WorkPackage notifiableWP,
			final SeenPrintPackages seenPrintPackages,
			final I_C_Async_Batch asyncBatch)
	{
		if (seenPrintPackages.isEmpty())
		{
			return;
		}

		final INotificationBL notificationBL = Services.get(INotificationBL.class);
		final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

		// create notes for print packages
		final int countExpected = asyncBatch.getCountExpected();
		for (final int printPackageId : seenPrintPackages.getPrintPackageIds())
		{
			final TableRecordReference printPackageRef = TableRecordReference.of(I_C_Print_Package.Table_Name, printPackageId);
			final I_AD_Archive lastArchive = archiveBL.getLastArchive(printPackageRef).orElse(null);
			if(lastArchive == null)
			{
				continue;
			}

			final I_C_Printing_Queue printingQueueItem = dao.retrievePrintingQueue(lastArchive);
			if (printingQueueItem == null)
			{
				continue;
			}
			final TableRecordReference printingQueueItemRef = TableRecordReference.of(printingQueueItem);

			final I_C_Queue_WorkPackage_Notified workpackageNotified = asyncBatchDAO.fetchWorkPackagesNotified(notifiableWP);
			Check.assumeNotNull(workpackageNotified, "Workpackage notified record is null!");

			notificationBL.send(UserNotificationRequest.builder()
					.topic(Printing_Constants.USER_NOTIFICATIONS_TOPIC)
					.recipientUserId(UserId.ofRepoId(notifiableWP.getCreatedBy()))
					.contentADMessage(MSG_Event_PDFGenerated)
					.contentADMessageParam(notifiableWP.getBatchEnqueuedCount())
					.contentADMessageParam(countExpected)
					.contentADMessageParam(seenPrintPackages.getCounter(printPackageId))
					.contentADMessageParam(printingQueueItemRef)
					.targetAction(TargetRecordAction.ofRecordAndWindow(printingQueueItemRef, WINDOW_ID_PrintingQueue))
					.build());

			asyncBatchBL.markWorkpackageNotified(workpackageNotified);

			// set printing queue to processed
			printingQueueItem.setProcessed(true);
			InterfaceWrapperHelper.save(printingQueueItem);
		}
	}

	@lombok.ToString
	private static class SeenPrintPackages
	{
		private final Map<Integer, Integer> countersByPrintPackageId = new HashMap<>();

		public boolean isEmpty()
		{
			return countersByPrintPackageId.isEmpty();
		}

		public Set<Integer> getPrintPackageIds()
		{
			return countersByPrintPackageId.keySet();
		}

		public int getCounter(final int printPackageId)
		{
			return countersByPrintPackageId.getOrDefault(printPackageId, 0);
		}

		public void incrementCounterForPrintPackageId(final int printPackageId)
		{
			final int counter = countersByPrintPackageId.getOrDefault(printPackageId, 0);
			countersByPrintPackageId.put(printPackageId, counter + 1);
		}
	}

}
