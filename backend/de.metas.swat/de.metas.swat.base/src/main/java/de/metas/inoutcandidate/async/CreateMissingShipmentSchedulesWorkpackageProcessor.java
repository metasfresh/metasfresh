package de.metas.inoutcandidate.async;

import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.slf4j.Logger;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Workpackage used to create missing shipment schedules.
 *
 * @author tsa
 *
 */
public class CreateMissingShipmentSchedulesWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(CreateMissingShipmentSchedulesWorkpackageProcessor.class);

	/**
	 * Schedules a new "create missing shipment schedules" run, <b>unless</b> the processor is disabled or all scheds would be created later.<br>
	 * See {@link IShipmentScheduleBL#allMissingSchedsWillBeCreatedLater()}.
	 *
	 * @param ctxAware if it has a not-null trxName, then the workpackage will be marked as ready for processing when given transaction is committed.
	 */
	public static final void scheduleIfNotPostponed(final IContextAware ctxAware)
	{
		if (Services.get(IShipmentScheduleBL.class).allMissingSchedsWillBeCreatedLater())
		{
			logger.debug("Not scheduling WP because", CreateMissingShipmentSchedulesWorkpackageProcessor.class);
			return;
		}

		// don't try to enqueue it if is not active
		if (!Services.get(IQueueDAO.class).isWorkpackageProcessorEnabled(CreateMissingShipmentSchedulesWorkpackageProcessor.class))
		{
			logger.debug("Not scheduling WP because this workpackage processor is disabled: {}", CreateMissingShipmentSchedulesWorkpackageProcessor.class);
			return;
		}

		final Properties ctx = ctxAware.getCtx();

		Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, CreateMissingShipmentSchedulesWorkpackageProcessor.class)
				.newBlock()
				.setContext(ctx)
				.newWorkpackage()
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
