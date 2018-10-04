/**
 *
 */
package de.metas.async.api;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.util.ISingletonService;

/**
 * @author cg
 *
 */
public interface IAsyncBatchBL extends ISingletonService
{
	String AD_SYSCONFIG_ASYNC_BOILERPLATE_ID = "de.metas.async.api.IAsyncBatchBL.BoilerPlate_ID";

	public static String ENQUEUED = "Enqueued";
	
	/**
	 * @return {@link I_C_Async_Batch} builder
	 */
	IAsyncBatchBuilder newAsyncBatch();

	/**
	 * update first enqueued, last enqueued and count Enqueued
	 * 
	 * @param workPackage
	 */
	int increaseEnqueued(final I_C_Queue_WorkPackage workPackage);

	int decreaseEnqueued(I_C_Queue_WorkPackage workPackage);

	/**
	 * update last processed and count processed
	 * 
	 * @param workPackage
	 */
	void increaseProcessed(final I_C_Queue_WorkPackage workPackage);

	/**
	 * Update the processed-flag based on first enqueued, last enqueued, last processed, count enqueued and count processed.
	 *
	 * NOTE: this method it is also saving the updated batch.
	 *
	 * @param asyncBatch
	 */
	void updateProcessed(final I_C_Async_Batch asyncBatch);

	/**
	 * Enqueue batch for the de.metas.async.processor.impl.CheckProcessedAsynBatchWorkpackageProcessor processor. Call
	 * {@link IWorkPackageQueue#enqueueWorkPackage(de.metas.async.model.I_C_Queue_Block, String)} with priority = <code>null</code>. This is OK because we assume that there is a dedicated queue/thread
	 * for CheckProcessedAsynBatchWorkpackageProcessor
	 *
	 * @param asyncBatch
	 */
	void enqueueAsyncBatch(I_C_Async_Batch asyncBatch);

	/**
	 * check if the keep alive time has expired for the current batch
	 * 
	 * @param asyncBatch
	 * @return
	 */
	boolean keepAliveTimeExpired(I_C_Async_Batch asyncBatch);

	/**
	 * create notification records in async batch has notification of type WPP
	 * 
	 * @param workPackage
	 */
	void createNotificationRecord(I_C_Queue_WorkPackage workPackage);

	/**
	 * check is the given workpackage can be notified
	 * if there is one below it, that can be notified, return that
	 * 
	 * @param asyncBatch
	 * @param workpackage
	 * @return workpackage
	 */
	I_C_Queue_WorkPackage notify(I_C_Async_Batch asyncBatch, I_C_Queue_WorkPackage workpackage);

	/**
	 * mark workpackage as notified
	 * 
	 * @param workpackageNotified
	 */
	void markWorkpackageNotified(I_C_Queue_WorkPackage_Notified workpackageNotified);

}
