package de.metas.handlingunits.shipmentschedule.async;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.ILatchStragegy;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.QtyToDeliverMap;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleAndJobSchedulesCollection;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Generate Shipments from given shipment schedules by processing enqueued work packages.<br>
 * This usually happens on the server site, asynchronously.<br>
 * See {@link #processWorkPackage(I_C_Queue_WorkPackage, String)}.
 * Note: the enqueueing part is done by {@link de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer ShipmentScheduleEnqueuer}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @implSpec <a href="http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29#Summary">task</a>
 */
public class GenerateInOutFromShipmentSchedules extends WorkpackageProcessorAdapter
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(GenerateInOutFromShipmentSchedules.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final ShipmentScheduleWithHUService shipmentScheduleWithHUService = SpringContextHolder.instance.getBean(ShipmentScheduleWithHUService.class);
	private final PickingJobScheduleService pickingJobScheduleService = SpringContextHolder.instance.getBean(PickingJobScheduleService.class);

	private ShipmentScheduleAndJobSchedulesCollection _shipmentScheduleAndJobSchedulesCollection = null; // lazy

	@NonNull
	public static CalculateShippingDateRule computeShippingDateRule(@Nullable final Boolean isShipDateToday)
	{
		return Boolean.TRUE.equals(isShipDateToday)
				? CalculateShippingDateRule.FORCE_SHIPMENT_DATE_TODAY
				: CalculateShippingDateRule.NONE;
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage_NOTUSED, final String localTrxName_NOTUSED)
	{
		final IParams parameters = getParameters();

		// Create candidates
		final QtyToDeliverMap scheduleId2QtyToDeliverOverride = QtyToDeliverMap.fromJson(parameters.getParameterAsString(ShipmentScheduleWorkPackageParameters.PARAM_QtyToDeliver_Override));

		final List<ShipmentScheduleWithHU> shipmentSchedulesWithHU = retrieveCandidates(scheduleId2QtyToDeliverOverride);
		if (shipmentSchedulesWithHU.isEmpty())
		{
			// this is a frequent case,
			// but can be business as usual when a user just wants to close some lines without picking anything.
			// So don't throw an exception, just log it.
			Loggables.withLogger(logger, Level.DEBUG).addLog("No unprocessed candidates were found");
		}

		final boolean isCompleteShipments = parameters.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments);
		final boolean isShipmentDateToday = parameters.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday);

		final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse = parameters
				.getParameterAsEnum(ShipmentScheduleWorkPackageParameters.PARAM_QuantityType, M_ShipmentSchedule_QuantityTypeToUse.class)
				.orElseThrow(() -> new AdempiereException("Parameter " + ShipmentScheduleWorkPackageParameters.PARAM_QuantityType + " not provided"));

		final boolean onlyUsePicked = quantityTypeToUse.isOnlyUsePicked();

		final boolean isCreatePackingLines = !onlyUsePicked;

		final CalculateShippingDateRule calculateShippingDateRule = computeShippingDateRule(isShipmentDateToday);

		final ImmutableMap<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleId2ExternalInfo = extractScheduleId2ExternalInfo(parameters);

		final InOutGenerateResult result = shipmentScheduleBL
				.createInOutProducerFromShipmentSchedule()
				.setProcessShipments(isCompleteShipments)
				.setCreatePackingLines(isCreatePackingLines)
				.computeShipmentDate(calculateShippingDateRule)
				.setScheduleIdToExternalInfo(scheduleId2ExternalInfo)
				// Fail on any exception, because we cannot create just a part of those shipments.
				// Think about HUs that are linked to multiple shipments: you will not see them in Aggregation POS because they are already assigned, but you're not able to create a shipment from them again.
				.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
				.createShipments(shipmentSchedulesWithHU);
		Loggables.addLog("Generated: {}", result);

		final boolean isCloseShipmentSchedules = parameters.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsCloseShipmentSchedules);
		if (isCloseShipmentSchedules)
		{
			closeSchedules();
		}

		return Result.SUCCESS;
	}

	private ImmutableMap<ShipmentScheduleId, ShipmentScheduleExternalInfo> extractScheduleId2ExternalInfo(@NonNull final IParams parameters)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, ShipmentScheduleExternalInfo> result = ImmutableMap.builder();
		final Collection<String> parameterNames = parameters.getParameterNames();
		for (final String parameterName : parameterNames)
		{
			if (parameterName.startsWith(ShipmentScheduleWorkPackageParameters.PARAM_PREFIX_AdvisedShipmentDocumentNo))
			{
				final String advisedDocumentNo = parameters.getParameterAsString(parameterName);
				final String shipmentScheduleIdStr = parameterName.substring(ShipmentScheduleWorkPackageParameters.PARAM_PREFIX_AdvisedShipmentDocumentNo.length());
				result.put(
						ShipmentScheduleId.ofRepoId(Integer.parseInt(shipmentScheduleIdStr)),
						ShipmentScheduleExternalInfo.builder().documentNo(advisedDocumentNo).build());
			}
		}
		return result.build();
	}

	/**
	 * Returns an instance of {@link CreateShipmentLatch}.
	 * <p>
	 *
	 * @implSpec <a href="http://dewiki908/mediawiki/index.php/09216_Async_-_Need_SPI_to_decide_if_packets_can_be_processed_in_parallel_of_not_%28106397206117%29">task</a>
	 */
	@Override
	public ILatchStragegy getLatchStrategy()
	{
		return CreateShipmentLatch.INSTANCE;
	}

	/**
	 * Retrieves the {@link ShipmentScheduleWithHU}s for which we will create the shipment(s).
	 * <p>
	 * Note that required and missing handling units can be "picked" on the fly.
	 */
	private List<ShipmentScheduleWithHU> retrieveCandidates(@NonNull final QtyToDeliverMap scheduleId2QtyToDeliverOverride)
	{
		final ShipmentScheduleAndJobSchedulesCollection schedules = getShipmentScheduleAndJobSchedulesCollection();
		if (schedules.isEmpty())
		{
			return ImmutableList.of();
		}

		final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse = getParameters()
				.getParameterAsEnum(ShipmentScheduleWorkPackageParameters.PARAM_QuantityType, M_ShipmentSchedule_QuantityTypeToUse.class)
				.orElseThrow(() -> new AdempiereException("Parameter " + ShipmentScheduleWorkPackageParameters.PARAM_QuantityType + " not provided"));

		final boolean onTheFlyPickToPackingInstructions = getParameters().getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsOnTheFlyPickToPackingInstructions);
		final boolean isCloseShipmentSchedules = getParameters().getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsCloseShipmentSchedules);
		final boolean isFailIfNoPickedHUs = !isCloseShipmentSchedules;

		return shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				PrepareForShipmentSchedulesRequest.builder()
						.schedules(schedules)
						.onlyLUIds(getOnlyLUIds())
						.quantityTypeToUse(quantityTypeToUse)
						.onTheFlyPickToPackingInstructions(onTheFlyPickToPackingInstructions)
						.qtyToDeliverOverrides(scheduleId2QtyToDeliverOverride)
						.isFailIfNoPickedHUs(isFailIfNoPickedHUs)
						.build()
		);
	}

	private ShipmentScheduleAndJobSchedulesCollection getShipmentScheduleAndJobSchedulesCollection()
	{
		ShipmentScheduleAndJobSchedulesCollection result = this._shipmentScheduleAndJobSchedulesCollection;
		if (result == null)
		{
			result = this._shipmentScheduleAndJobSchedulesCollection = retrieveShipmentScheduleAndJobSchedulesCollection();
		}
		return result;
	}

	private ShipmentScheduleAndJobSchedulesCollection retrieveShipmentScheduleAndJobSchedulesCollection()
	{
		final boolean skipAlreadyProcessedItems = false; // yes, we want items whose queue packages were already processed! This is a workaround, but we need it that way.
		// Background: otherwise, after we did a partial delivery on a shipment schedule, we cannot deliver the rest, because the shipment schedule is already within a processed work package.
		// Note that it's the customer's declared responsibility to to verify the shipments
		// FIXME: find a better solution. If nothing else, then "split" the undelivered remainder of a partially delivered schedule off into a new schedule (we do that with ICs too).
		final TableRecordReferenceSet recordRefs = queueDAO.retrieveQueueElementRecordRefs(getQueueWorkPackageId(), skipAlreadyProcessedItems);

		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = recordRefs.getRecordIdsByTableName(I_M_ShipmentSchedule.Table_Name, ShipmentScheduleId::ofRepoId);
		final ImmutableSet<PickingJobScheduleId> jobScheduleIds = recordRefs.getRecordIdsByTableName(I_M_Picking_Job_Schedule.Table_Name, PickingJobScheduleId::ofRepoId);

		return pickingJobScheduleService.getShipmentScheduleAndJobSchedulesCollection(shipmentScheduleIds, jobScheduleIds);
	}

	private Set<HuId> getOnlyLUIds()
	{
		final IParams parameters = getParameters();
		return RepoIdAwares.ofCommaSeparatedSet(
				parameters.getParameterAsString(ShipmentScheduleWorkPackageParameters.PARAM_OnlyLUIds),
				HuId.class
		);
	}

	private void closeSchedules()
	{
		final ShipmentScheduleAndJobSchedulesCollection schedules = getShipmentScheduleAndJobSchedulesCollection();

		pickingJobScheduleService.markAsProcessed(schedules.getJobScheduleIds());

		final HashSet<ShipmentScheduleId> shipmentScheduleIdsToClose = new HashSet<>();
		shipmentScheduleIdsToClose.addAll(schedules.getShipmentScheduleIdsWithoutJobSchedules());
		shipmentScheduleIdsToClose.addAll(pickingJobScheduleService.getShipmentScheduleIdsWithAllJobSchedulesProcessedOrMissing(schedules.getShipmentScheduleIdsWithJobSchedules()));

		shipmentScheduleBL.closeShipmentSchedules(shipmentScheduleIdsToClose);
	}

}
