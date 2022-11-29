/**
 *
 */
package de.metas.async.api;

import com.google.common.collect.Multimap;
import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.model.I_C_Queue_WorkPackage_Notified;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.ImmutablePair;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * @author cg
 *
 */
public interface IAsyncBatchBL extends ISingletonService
{
	String AD_SYSCONFIG_ASYNC_BOILERPLATE_ID = "de.metas.async.api.IAsyncBatchBL.BoilerPlate_ID";

	String ENQUEUED = "Enqueued";

	/**
	 * @return {@link I_C_Async_Batch} builder
	 */
	IAsyncBatchBuilder newAsyncBatch();

	/**
	 * update first enqueued, last enqueued and count Enqueued
	 */
	int increaseEnqueued(final I_C_Queue_WorkPackage workPackage);

	int decreaseEnqueued(I_C_Queue_WorkPackage workPackage);

	/**
	 * update last processed and count processed
	 */
	void increaseProcessed(final I_C_Queue_WorkPackage workPackage);

	/**
	 * Update the processed-flag based on first enqueued, last enqueued, last processed, count enqueued and count processed.
	 *
	 * NOTE: this method it is also saving the updated batch.
	 *
	 * @return {@code true} iff the respective record was updated to {@code processed='Y'};
	 */
	boolean updateProcessedOutOfTrx(AsyncBatchId asyncBatchId);

	/**
	 * Enqueue batch for the de.metas.async.processor.impl.CheckProcessedAsynBatchWorkpackageProcessor processor. Call
	 * {@link IWorkPackageQueue#enqueueWorkPackage(I_C_Queue_WorkPackage, IWorkpackagePrioStrategy)} with priority = <code>null</code>.
	 * This is OK because we assume that there is a dedicated queue/thread
	 * for CheckProcessedAsynBatchWorkpackageProcessor
	 */
	void enqueueAsyncBatch(AsyncBatchId asyncBatchId);

	/**
	 * check if the keep alive time has expired for the current batch
	 */
	boolean keepAliveTimeExpired(AsyncBatchId asyncBatchId);

	/**
	 * create notification records in async batch has notification of type WPP
	 */
	void createNotificationRecord(I_C_Queue_WorkPackage workPackage);

	/**
	 * check is the given workpackage can be notified
	 * if there is one below it, that can be notified, return that
	 *
	 * @return workpackage
	 */
	I_C_Queue_WorkPackage notify(I_C_Async_Batch asyncBatch, I_C_Queue_WorkPackage workpackage);

	/**
	 * mark workpackage as notified
	 */
	void markWorkpackageNotified(I_C_Queue_WorkPackage_Notified workpackageNotified);

	/**
	 * @return the async batch id that is associated with the given modelRecord, either via record-(DB-)column or via dynamic attrribute.
	 */
	Optional<AsyncBatchId> getAsyncBatchId(Object modelRecord);

	/**
	 * Creates a new C_Async_Batch and sets the given modelRecord's column to reference it.
	 *
	 * @param asyncBatchInternalName see {@link Async_Constants}
	 */
	@NonNull <T> ImmutablePair<AsyncBatchId, T> assignPermAsyncBatchToModelIfMissing(
			@NonNull T modelRecord,
			@NonNull String asyncBatchInternalName);

	/**
	 * Creates new a C_Async_Batch if needed and sets the temporary dynamic attribute for the given modelRecords to reference it.
	 * Those modelRecords that already reference any async batch - also a different one - retain it.
	 *
	 * @param asyncBatchInternalName see {@link Async_Constants}
	 * @see org.adempiere.model.InterfaceWrapperHelper#setDynAttribute(Object, String, Object).
	 */
	@NonNull <T> Multimap<AsyncBatchId, T> assignTempAsyncBatchToModelsIfMissing(
			@NonNull List<T> model,
			@NonNull String asyncBatchInternalName);

	IAutoCloseable assignTempAsyncBatchIdToModel(@NonNull Object model, @Nullable AsyncBatchId asyncBatchId);

	I_C_Async_Batch getAsyncBatchById(AsyncBatchId asyncBatchId);

	AsyncBatchId newAsyncBatch(String asyncBatchType);

	Optional<String> getAsyncBatchTypeInternalName(@NonNull final I_C_Async_Batch asyncBatch);

	boolean isAsyncBatchTypeInternalName(@NonNull I_C_Async_Batch asyncBatch, @NonNull String expectedInternalName);

	Optional<AsyncBatchType> getAsyncBatchType(@NonNull I_C_Async_Batch asyncBatch);

	AsyncBatchType getAsyncBatchTypeById(@NonNull AsyncBatchTypeId asyncBatchTypeId);

	Duration getTimeUntilProcessedRecheck(I_C_Async_Batch asyncBatch);
}
