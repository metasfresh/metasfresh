/**
 * 
 */
package de.metas.printing.async.spi.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.async.spi.IAsyncBatchListener;
import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
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

	private int printingQueueWindowId = 540165;

	public static final Topic TOPIC_PDFPrinting = Topic.builder()
			.setName("de.metas.printing.async.ProcessedEvents")
			.setType(Type.REMOTE)
			.build();

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
						final Map<Integer, Integer> seenPackages = collectSeenPackages(jobLines); 
						createandPostEvents(notifiableWP, seenPackages, asyncBatch);
					}
				}
			}

		}
	}

	/**
	 * collects in a map aleardy seen packages
	 * @param jobLines
	 * @return
	 */
	private Map<Integer, Integer> collectSeenPackages(final Iterator<I_C_Print_Job_Line> jobLines)
	{
		final Map<Integer, Integer> seenPackages = new HashMap<Integer, Integer>();
		
		for (final I_C_Print_Job_Line jobLine : IteratorUtils.asIterable(jobLines))
		{
			if (jobLine.getC_Print_Package_ID() <= 0)
			{
				continue;
			}

			final int key = jobLine.getC_Print_Package_ID();
			if (seenPackages.containsKey(key))
			{

				final int value = seenPackages.get(key);
				seenPackages.put(key, value + 1);

				// already printed
				continue;
			}

			seenPackages.put(jobLine.getC_Print_Package_ID(), 1);
		}
		
		return seenPackages;
	}
	
	/**
	 *	trigger notification event for processed workpackage 
	 * @param notifiableWP
	 * @param seenPackages
	 * @param asyncBatch
	 */
	private void createandPostEvents(final I_C_Queue_WorkPackage notifiableWP, final Map<Integer, Integer> seenPackages, final I_C_Async_Batch asyncBatch)
	{
		final int expected = asyncBatch.getCountExpected();
		final Properties ctx = InterfaceWrapperHelper.getCtx(notifiableWP);

		if (seenPackages.isEmpty())
		{
			return ;
		}
		
		// create notes for print packages
		for (final Integer packageId : seenPackages.keySet())
		{
			final String sql = " AND Record_Id = " + packageId + " AND AD_Table_ID = " + Services.get(IADTableDAO.class).retrieveTableId(I_C_Print_Package.Table_Name);
			final List<I_AD_Archive> archivesList = Services.get(IArchiveDAO.class).retrieveArchives(ctx, sql);

			if (archivesList.isEmpty())
			{
				continue;
			}

			final I_C_Printing_Queue pq = dao.retrievePrintingQueue(archivesList.get(0));
			if (pq == null)
			{
				continue;
			}

			final I_C_Queue_WorkPackage_Notified workpackageNotified = asyncBatchDAO.fetchWorkPackagesNotified(notifiableWP);
			Check.assumeNotNull(workpackageNotified, "Workpackage notified record is null!");

			final Event event = Event.builder()
					.setDetailADMessage(MSG_Event_PDFGenerated, notifiableWP.getBatchEnqueuedCount(), expected, seenPackages.get(packageId), TableRecordReference.of(pq))
					.addRecipient_User_ID(notifiableWP.getCreatedBy())
					.setRecord(TableRecordReference.of(pq))
					.setSuggestedWindowId(printingQueueWindowId)
					.build();

			Services.get(IEventBusFactory.class)
					.getEventBus(TOPIC_PDFPrinting)
					.postEvent(event);

			asyncBatchBL.markWorkpackageNotified(workpackageNotified);

			// set printing queue to processed
			pq.setProcessed(true);
			InterfaceWrapperHelper.save(pq);
		}
	}

}
