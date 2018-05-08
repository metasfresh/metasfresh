/**
 * 
 */
package de.metas.printing.async.spi.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_AD_Archive;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.async.spi.IAsyncBatchListener;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Printing_Queue;

/**
 * Listener for notifying the user when a PDF printing was done
 * <ul>
 * when the workpackage was processed, the listener is triggered
 * </ul>
 * <ul>
 * checks first if the workpackage was notified
 * </ul>
 * <ul>
 * if the Wp needs to be notified, build a message with a summary and a link to the newlly cretaed printing queue
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

	private static final String MSG_Event_PDFGenerated = "PDFPrintingAsyncBatchListener_PrintJob_Done";
	private static int WINDOW_ID_PrintingQueue = 540165; // FIXME: hardcoded

	@Override
	public void createNotice(final I_C_Async_Batch asyncBatch)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(asyncBatch);
		final String asyncBathTypeName = asyncBatch.getC_Async_Batch_Type().getInternalName();

		if (Printing_Constants.C_Async_Batch_InternalName_PDFPrinting.equals(asyncBathTypeName))
		{

			final I_C_Queue_WorkPackage wp = asyncBatch.getLastProcessed_WorkPackage();
			if (wp != null && wp.getC_Queue_WorkPackage_ID() > 0)
			{
				final I_C_Queue_WorkPackage notifiableWP = asyncBatchBL.notify(asyncBatch, wp);

				if (notifiableWP != null)
				{
					final List<I_C_Print_Job_Instructions> printJobInstructions = queueDAO.retrieveItems(notifiableWP, I_C_Print_Job_Instructions.class, trxName);
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
	 * 
	 * @param notifiableWP
	 * @param seenPackages
	 * @param asyncBatch
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
		final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);

		// create notes for print packages
		final Properties ctx = InterfaceWrapperHelper.getCtx(notifiableWP);
		final int countExpected = asyncBatch.getCountExpected();
		for (final int printPackageId : seenPrintPackages.getPrintPackageIds())
		{
			final ITableRecordReference printPackageRef = TableRecordReference.of(I_C_Print_Package.Table_Name, printPackageId);
			final List<I_AD_Archive> archivesList = archiveDAO.retrieveLastArchives(ctx, printPackageRef, 1);
			if (archivesList.isEmpty())
			{
				continue;
			}
			final I_AD_Archive lastArchive = archivesList.get(0);

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
					.recipientUserId(notifiableWP.getCreatedBy())
					.contentADMessage(MSG_Event_PDFGenerated)
					.contentADMessageParam(notifiableWP.getBatchEnqueuedCount())
					.contentADMessageParam(countExpected)
					.contentADMessageParam(seenPrintPackages.getCounter(printPackageId))
					.contentADMessageParam(printingQueueItemRef)
					.targetRecord(printingQueueItemRef)
					.targetADWindowId(WINDOW_ID_PrintingQueue)
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

		public int getCounter(int printPackageId)
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
