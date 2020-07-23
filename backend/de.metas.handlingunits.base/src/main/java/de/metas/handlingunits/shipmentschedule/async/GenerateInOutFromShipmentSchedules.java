package de.metas.handlingunits.shipmentschedule.async;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.ILatchStragegy;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUComparator;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.CreateCandidatesRequest;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.CreateCandidatesRequest.CreateCandidatesRequestBuilder;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.ShipmentSchedulesMDC;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Generate Shipments from given shipment schedules by processing enqueued work packages.<br>
 * This usually happens on the server site, in an asynchronous manner.<br>
 * See {@link #processWorkPackage(I_C_Queue_WorkPackage, String)}.
 * Note: the enqeueing part is done by {@link de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer ShipmentScheduleEnqueuer}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/07042_Simple_InOut-Creation_from_shipment-schedule_%28109342691288%29#Summary
 */
public class GenerateInOutFromShipmentSchedules extends WorkpackageProcessorAdapter
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(GenerateInOutFromShipmentSchedules.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final ShipmentScheduleWithHUService shipmentScheduleWithHUService = SpringContextHolder.instance.getBean(ShipmentScheduleWithHUService.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage_NOTUSED, final String localTrxName_NOTUSED)
	{
		// Create candidates
		final List<ShipmentScheduleWithHU> shipmentSchedulesWithHU = retrieveCandidates();
		if (shipmentSchedulesWithHU.isEmpty())
		{
			// this is a frequent case and we received no complaints so far. So don't throw an exception, just log it
			Loggables.withLogger(logger, Level.DEBUG).addLog("No unprocessed candidates were found");
		}

		final IParams parameters = getParameters();
		final boolean isCompleteShipments = parameters.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments);
		final boolean isShipmentDateToday = parameters.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday);
		final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse = parameters.getParameterAsEnum(ShipmentScheduleWorkPackageParameters.PARAM_QuantityType, M_ShipmentSchedule_QuantityTypeToUse.class)
				.orElseThrow(() -> new AdempiereException("Parameter " + ShipmentScheduleWorkPackageParameters.PARAM_QuantityType + " not provided"));

		final boolean onlyUsePicked = quantityTypeToUse.isOnlyUsePicked();

		final boolean isCreatPackingLines = !onlyUsePicked;

		final InOutGenerateResult result = shipmentScheduleBL
				.createInOutProducerFromShipmentSchedule()
				.setProcessShipments(isCompleteShipments)
				.setCreatePackingLines(isCreatPackingLines)
				.computeShipmentDate(isShipmentDateToday)
				// Fail on any exception, because we cannot create just a part of those shipments.
				// Think about HUs which are linked to multiple shipments: you will not see them in Aggregation POS because are already assigned, but u are not able to create shipment from them again.
				.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
				.createShipments(shipmentSchedulesWithHU);
		Loggables.addLog("Generated: {}", result);

		return Result.SUCCESS;
	}

	/**
	 * Returns an instance of {@link CreateShipmentLatch}.
	 *
	 * @task http://dewiki908/mediawiki/index.php/09216_Async_-_Need_SPI_to_decide_if_packets_can_be_processed_in_parallel_of_not_%28106397206117%29
	 */
	@Override
	public ILatchStragegy getLatchStrategy()
	{
		return CreateShipmentLatch.INSTANCE;
	}

	/**
	 * Retrieves the {@link IShipmentScheduleWithHU}s for which we will create the shipment(s).
	 *
	 * Note that required and missing handling units can be "picked" on the fly.
	 */
	private final List<ShipmentScheduleWithHU> retrieveCandidates()
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = retriveShipmentSchedules();
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}

		final IHUContext huContext = huContextFactory.createMutableHUContext();

		final M_ShipmentSchedule_QuantityTypeToUse quantityTypeToUse = getParameters()
				.getParameterAsEnum(ShipmentScheduleWorkPackageParameters.PARAM_QuantityType, M_ShipmentSchedule_QuantityTypeToUse.class)
				.orElseThrow(() -> new AdempiereException("Parameter " + ShipmentScheduleWorkPackageParameters.PARAM_QuantityType + " not provided"));

		final CreateCandidatesRequestBuilder requestBuilder = CreateCandidatesRequest.builder()
				.huContext(huContext)
				.quantityType(quantityTypeToUse);

		final ArrayList<ShipmentScheduleWithHU> candidates = new ArrayList<>();

		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			final ImmutableList<ShipmentScheduleWithHU> scheduleCandidates = createCandidatesForSched(requestBuilder, shipmentSchedule);
			candidates.addAll(scheduleCandidates);
		}

		// Sort our candidates
		Collections.sort(candidates, new ShipmentScheduleWithHUComparator());
		return candidates;
	}

	private ImmutableList<ShipmentScheduleWithHU> createCandidatesForSched(
			@NonNull final CreateCandidatesRequestBuilder requestBuilder,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (shipmentSchedule.isProcessed())
		{
			return ImmutableList.of();
		}

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		try (final MDCCloseable mdcRestorer = ShipmentSchedulesMDC.putShipmentScheduleId(shipmentScheduleId))
		{
			final CreateCandidatesRequest request = requestBuilder
					.shipmentScheduleId(shipmentScheduleId)
					.build();

			return shipmentScheduleWithHUService.createShipmentSchedulesWithHU(request);
		}
	}

	private List<I_M_ShipmentSchedule> retriveShipmentSchedules()
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

		final List<I_M_ShipmentSchedule> schedules = queryBuilder
				.create()
				.list();
		return schedules;
	}
}
