package de.metas.inoutcandidate.async;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Properties;
import java.util.Set;

/**
 * Workpackage used to create missing shipment schedules.
 *
 * @author tsa
 */
public class CreateMissingShipmentSchedulesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(CreateMissingShipmentSchedulesWorkpackageProcessor.class);

	private static final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private static final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private static final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	public static void scheduleIfNotPostponed(final IContextAware ctxAware)
	{
		final I_C_Async_Batch asyncBatch = null;
		_scheduleIfNotPostponed(ctxAware, asyncBatch);
	}

	public static void scheduleIfNotPostponed(@NonNull final Object model)
	{
		final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);

		final I_C_Async_Batch asyncBatch = asyncBatchBL
				.getAsyncBatchId(model)
				.map(asyncBatchBL::getAsyncBatchById)
				.orElse(null);

		_scheduleIfNotPostponed(InterfaceWrapperHelper.getContextAware(model), asyncBatch);
	}

	/**
	 * Schedules a new "create missing shipment schedules" run, <b>unless</b> the processor is disabled or all scheds would be created later.<br>
	 * See {@link IShipmentScheduleBL#allMissingSchedsWillBeCreatedLater()}.
	 *
	 * @param ctxAware if it has a not-null trxName, then the workpackage will be marked as ready for processing when given transaction is committed.
	 */
	private static void _scheduleIfNotPostponed(final IContextAware ctxAware, @Nullable final I_C_Async_Batch asyncBatch)
	{
		if (shipmentScheduleBL.allMissingSchedsWillBeCreatedLater())
		{
			logger.debug("Not scheduling WP because IShipmentScheduleBL.allMissingSchedsWillBeCreatedLater() returned true: {}", CreateMissingShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return;
		}

		// don't try to enqueue it if is not active
		if (!queueDAO.isWorkpackageProcessorEnabled(CreateMissingShipmentSchedulesWorkpackageProcessor.class))
		{
			logger.debug("Not scheduling WP because this workpackage processor is disabled: {}", CreateMissingShipmentSchedulesWorkpackageProcessor.class.getSimpleName());
			return;
		}

		final Properties ctx = ctxAware.getCtx();

		workPackageQueueFactory.getQueueForEnqueuing(ctx, CreateMissingShipmentSchedulesWorkpackageProcessor.class)
				.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.setC_Async_Batch(asyncBatch)
				.bindToTrxName(ctxAware.getTrxName())
				.build();
	}

	// services
	private final transient IShipmentScheduleHandlerBL inOutCandHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);

		final Set<ShipmentScheduleId> shipmentScheduleIds = inOutCandHandlerBL.createMissingCandidates(ctx);

		// After shipment schedules where created, invalidate them because we want to make sure they are up2date.
		final IShipmentScheduleInvalidateBL invalidSchedulesService = Services.get(IShipmentScheduleInvalidateBL.class);
		invalidSchedulesService.flagForRecompute(shipmentScheduleIds);

		Loggables.addLog("Created " + shipmentScheduleIds.size() + " candidates");
		return Result.SUCCESS;
	}

}
