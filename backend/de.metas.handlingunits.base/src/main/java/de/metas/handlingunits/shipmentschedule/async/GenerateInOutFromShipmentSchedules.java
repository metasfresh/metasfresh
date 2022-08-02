package de.metas.handlingunits.shipmentschedule.async;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.ILatchStragegy;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Generate Shipments from given shipment schedules by processing enqueued work packages.<br>
 * This usually happens on the server site, in an asynchronous manner.<br>
 * See {@link #processWorkPackage(I_C_Queue_WorkPackage, String)}.
 * Note: the enqeueing part is done by {@link de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer ShipmentScheduleEnqueuer}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * task http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29#Summary
 */
public class GenerateInOutFromShipmentSchedules extends WorkpackageProcessorAdapter
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(GenerateInOutFromShipmentSchedules.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final ShipmentScheduleWithHUService shipmentScheduleWithHUService = SpringContextHolder.instance.getBean(ShipmentScheduleWithHUService.class);

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
		final ImmutableMap<ShipmentScheduleId, BigDecimal> scheduleId2QtyToDeliverOverride = extractScheduleId2QtyToDeliverOverride(parameters);
		
		final List<ShipmentScheduleWithHU> shipmentSchedulesWithHU = retrieveCandidates(scheduleId2QtyToDeliverOverride);
		if (shipmentSchedulesWithHU.isEmpty())
		{
			// this is a frequent case and we received no complaints so far. So don't throw an exception, just log it
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
				// Think about HUs which are linked to multiple shipments: you will not see them in Aggregation POS because are already assigned, but u are not able to create shipment from them again.
				.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
				.createShipments(shipmentSchedulesWithHU);

		Loggables.addLog("Generated: {}", result);
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

	@NonNull
	private ImmutableMap<ShipmentScheduleId, BigDecimal> extractScheduleId2QtyToDeliverOverride(@NonNull final IParams parameters)
	{
		final ImmutableMap.Builder<ShipmentScheduleId, BigDecimal> result = ImmutableMap.builder();
		final Collection<String> parameterNames = parameters.getParameterNames();
		for (final String parameterName : parameterNames)
		{
			if (parameterName.startsWith(ShipmentScheduleWorkPackageParameters.PARAM_PREFIX_QtyToDeliver_Override))
			{
				final BigDecimal qtyToDeliverOverride = parameters.getParameterAsBigDecimal(parameterName);
				final String shipmentScheduleIdStr = parameterName.substring(ShipmentScheduleWorkPackageParameters.PARAM_PREFIX_QtyToDeliver_Override.length());
				result.put(
						ShipmentScheduleId.ofRepoId(Integer.parseInt(shipmentScheduleIdStr)),
						qtyToDeliverOverride);
			}
		}
		return result.build();
	}

	/**
	 * Returns an instance of {@link CreateShipmentLatch}.
	 * <p>
	 * task http://dewiki908/mediawiki/index.php/09216_Async_-_Need_SPI_to_decide_if_packets_can_be_processed_in_parallel_of_not_%28106397206117%29
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
	private List<ShipmentScheduleWithHU> retrieveCandidates(@NonNull final ImmutableMap<ShipmentScheduleId, BigDecimal> scheduleId2QtyToDeliverOverride)
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = retrieveShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}

		final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse = getParameters()
				.getParameterAsEnum(ShipmentScheduleWorkPackageParameters.PARAM_QuantityType, M_ShipmentSchedule_QuantityTypeToUse.class)
				.orElseThrow(() -> new AdempiereException("Parameter " + ShipmentScheduleWorkPackageParameters.PARAM_QuantityType + " not provided"));

		final boolean onTheFlyPickToPackingInstructions = getParameters()
				.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsOnTheFlyPickToPackingInstructions);

		return shipmentScheduleWithHUService.createShipmentSchedulesWithHU(
				shipmentSchedules,
				quantityTypeToUse,
				onTheFlyPickToPackingInstructions,
				scheduleId2QtyToDeliverOverride);
	}

	private List<I_M_ShipmentSchedule> retrieveShipmentSchedules()
	{
		final I_C_Queue_WorkPackage workpackage = getC_Queue_WorkPackage();
		final boolean skipAlreadyProcessedItems = false; // yes, we want items whose queue packages were already processed! This is a workaround, but we need it that way.
		// Background: otherwise, after we did a partial delivery on a shipment schedule, we cannot deliver the rest, because the sched is already within a processed work package.
		// Note that it's the customer's declared responsibility to to verify the shipments
		// FIXME: find a better solution. If nothing else, then "split" the undelivered remainder of a partially delivered schedule off into a new schedule (we do that with ICs too).
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queueDAO.createElementsQueryBuilder(
				workpackage,
				I_M_ShipmentSchedule.class,
				skipAlreadyProcessedItems,
				ITrx.TRXNAME_ThreadInherited);

		queryBuilder.orderBy()
				.addColumn(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey, Direction.Ascending, Nulls.Last)
				.addColumn(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);

		return queryBuilder
				.create()
				.list();
	}
}
