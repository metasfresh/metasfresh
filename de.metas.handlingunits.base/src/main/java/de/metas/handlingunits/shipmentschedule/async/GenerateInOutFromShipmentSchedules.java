package de.metas.handlingunits.shipmentschedule.async;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.async.api.IQueueDAO;
import de.metas.async.exceptions.WorkpackageSkipRequestException;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.ILatchStragegy;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.LULoader;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer.ShipmentScheduleWorkPackageParameters;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleQtyPickedProductStorage;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.logging.LogManager;
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
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	//
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	//
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	//
	private final transient Logger logger = LogManager.getLogger(getClass());

	private static final String MSG_NoQtyPicked = "MSG_NoQtyPicked";

	public GenerateInOutFromShipmentSchedules()
	{
		super();
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage_NOTUSED, final String localTrxName_NOTUSED)
	{
		// Create candidates
		final List<ShipmentScheduleWithHU> candidates = retrieveCandidates();
		if (candidates.isEmpty())
		{
			// this is a frequent case and we received no complaints so far. So don't throw an exception, just log it
			Loggables.get().addLog("No unprocessed candidates were found");
		}

		final IParams parameters = getParameters();
		final boolean isCompleteShipments = parameters.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsCompleteShipments);
		final boolean isShipmentDateToday = parameters.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsShipmentDateToday);
		final boolean isUseQtyPicked = parameters.getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsUseQtyPicked);
		final boolean createPackingLines;
		final boolean manualPackingMaterial;
		if (isUseQtyPicked)
		{
			// In case of using the qty Picked entries, the logic will be similar with shipment creation from HU, therefore the packinglines and manualPackingMaterial flags will be disabled (task FRESH-251)
			createPackingLines = false; // the packing lines shall only be created when the shipments are completed
			manualPackingMaterial = false; // use the HUs!
		}
		else
		{
			createPackingLines = true; // task 08138: the packing lines shall be created directly, and shall be user-editable.
			manualPackingMaterial = true;
		}

		final InOutGenerateResult result = Services.get(IHUShipmentScheduleBL.class)
				.createInOutProducerFromShipmentSchedule()
				.setProcessShipments(isCompleteShipments)
				.setCreatePackingLines(createPackingLines)
				.setManualPackingMaterial(manualPackingMaterial)
				.computeShipmentDate(isShipmentDateToday)
				// Fail on any exception, because we cannot create just a part of those shipments.
				// Think about HUs which are linked to multiple shipments: you will not see then in Aggregation POS because are already assigned, but u are not able to create shipment from them again.
				.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
				.createShipments(candidates);
		Loggables.get().addLog("Generated: {}", result);

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
	 * Creates the {@link IShipmentScheduleWithHU}s for which we will create the shipment(s).
	 *
	 * Note that required and missing handling units are creates on the fly.
	 *
	 * @param workpackage
	 * @param trxName
	 * @return
	 */
	private final List<ShipmentScheduleWithHU> retrieveCandidates()
	{
		final IHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();

		final List<ShipmentScheduleWithHU> candidates = new ArrayList<>();
		final Iterator<I_M_ShipmentSchedule> schedules = retriveShipmentSchedules();
		while (schedules.hasNext())
		{
			final I_M_ShipmentSchedule schedule = schedules.next();
			if (!isEligible(schedule))
			{
				continue;
			}

			// task 08959: skip invalid schedules, check again in 10 seconds
			// the system will eventually have updated them for us.
			// Note that this way, the sched might differ from what
			// the user selected, but on the other hand, if a user enqueued invalid records, they shouldn't be too surprised.
			if (shipmentSchedulePA.isInvalid(schedule))
			{
				throw WorkpackageSkipRequestException.createWithTimeout("Shipment schedule needs to be updated first: " + schedule, 10000);
			}

			final List<ShipmentScheduleWithHU> scheduleCandidates = createCandidates(huContext, schedule);
			candidates.addAll(scheduleCandidates);
		}

		//
		// Sort our candidates
		Collections.sort(candidates, new ShipmentScheduleWithHUComparator());

		return candidates;
	}

	/**
	 * Checks if given shipment schedule is eligible for shipment generation
	 *
	 * @param schedule
	 * @return true if it's eligible
	 */
	private boolean isEligible(final I_M_ShipmentSchedule schedule)
	{
		//
		// Skip already processed schedules
		if (schedule.isProcessed())
		{
			return false;
		}
		return true;
	}

	private Iterator<I_M_ShipmentSchedule> retriveShipmentSchedules()
	{
		final I_C_Queue_WorkPackage workpackage = getC_Queue_WorkPackage();
		final boolean skipAlreadyProcessedItems = false; // yes, we want items whose queue packages were already processed! This is a workaround, but we need it that way.
		// Background: otherwise, after we did a partial delivery on a shipment schedule, we cannot deliver the rest, because the sched is already within a processed work package.
		// Note that it's the customer's declared responsibility to to verify the shipments
		// FIXME: find a better solution. If nothing else, then "split" the undelivered remainder of a partially delivered schedule off into a new schedule (we do that with ICs too).
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queueDAO.createElementsQueryBuilder(workpackage, I_M_ShipmentSchedule.class, skipAlreadyProcessedItems, ITrx.TRXNAME_ThreadInherited);

		queryBuilder.orderBy()
				.addColumn(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_HeaderAggregationKey, Direction.Ascending, Nulls.Last)
				.addColumn(de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);

		final List<I_M_ShipmentSchedule> schedules = queryBuilder
				.create()
				.list();
		return schedules.iterator();
	}

	/**
	 * Create {@link IShipmentScheduleWithHU} (i.e. candidates) for given <code>schedule</code>.
	 *
	 * NOTE: this method will create missing LUs before.
	 *
	 * @return one single candidate if there are no {@link I_M_ShipmentSchedule_QtyPicked} for the given schedule. One candidate per {@link I_M_ShipmentSchedule_QtyPicked} otherwise.
	 */
	private List<ShipmentScheduleWithHU> createCandidates(
			final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule schedule)
	{
		//
		// Load all QtyPicked records that have no InOutLine yet
		List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = retrieveQtyPickedRecords(schedule);

		final boolean isUseQtyPicked = getParameters().getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsUseQtyPicked);
		if (qtyPickedRecords.isEmpty())
		{
			if (isUseQtyPicked)
			{
				// the parameter insists that we use qtyPicked records, but there aren't any
				// => nothing to do, basically

				// If we got no qty picked records just because they were already delivered,
				// don't fail this workpackage but just log the issue (task 09048)
				final boolean wereDelivered = shipmentScheduleAllocDAO.retrievePickedAndDeliveredRecordsQuery(schedule).create().match();
				if (wereDelivered)
				{
					Loggables.get().withLogger(logger, Level.INFO).addLog("Skipped shipment schedule because it was already delivered: {}", schedule);
					return Collections.emptyList();
				}
				Loggables.get().withLogger(logger, Level.WARN).addLog("Shipment schedule has no I_M_ShipmentSchedule_QtyPicked records (or these records have inactive HUs); M_ShipmentSchedule={}", schedule);
				final String errorMsg = Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(schedule), MSG_NoQtyPicked);
				throw new AdempiereException(errorMsg);
			}

			// There are no picked qtys for the given shipment schedule, so we will ship as is (without any handling units)
			final BigDecimal qtyToDeliver = shipmentScheduleEffectiveValuesBL.getQtyToDeliver(schedule);
			final ShipmentScheduleWithHU candidate = //
					ShipmentScheduleWithHU.ofShipmentScheduleWithoutHu(schedule, qtyToDeliver);

			return Collections.singletonList(candidate);
		}

		//
		// Create necessary LUs (if any)
		createLUs(schedule);

		// retrieve the qty picked entries again, some new ones might have been created on LU creation
		qtyPickedRecords = retrieveQtyPickedRecords(schedule);

		//
		// Iterate all QtyPicked records and create candidates from them
		final List<ShipmentScheduleWithHU> candidates = new ArrayList<>(qtyPickedRecords.size());
		for (final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked qtyPickedRecord : qtyPickedRecords)
		{
			final I_M_ShipmentSchedule_QtyPicked qtyPickedRecordHU = InterfaceWrapperHelper.create(qtyPickedRecord, I_M_ShipmentSchedule_QtyPicked.class);

			// guard: Skip inactive records.
			if (!qtyPickedRecordHU.isActive())
			{
				Loggables.get().withLogger(logger, Level.INFO).addLog("Skipped inactive qtyPickedRecordHU={}", qtyPickedRecordHU);
				continue;
			}

			// Considering only those lines which have an LU
			// NOTE: this shall not happen because we already created the LUs
			// Task FRESH-251 : In case the qty Picked are used, only add the LU if it was required by the qty picked
			if (qtyPickedRecordHU.getM_LU_HU_ID() <= 0 && !isUseQtyPicked)
			{
				final HUException ex = new HUException("Record shall have LU set: " + qtyPickedRecord);
				logger.warn(ex.getLocalizedMessage() + " [Skipped]", ex);
				Loggables.get().addLog("WARN: {} [Skipped]", ex.getLocalizedMessage());
				continue;
			}

			//
			// Create ShipmentSchedule+HU candidate and add it to our list
			final ShipmentScheduleWithHU candidate = //
					ShipmentScheduleWithHU.ofShipmentScheduleQtyPickedWithHuContext(qtyPickedRecordHU, huContext);
			candidates.add(candidate);
		}

		return candidates;
	}

	/**
	 *
	 * @param schedule
	 * @return records that do not have an {@link I_M_InOutLine} assigned to them and that also have
	 *         <ul>
	 *         <li>either no HU assigned to them, or</li>
	 *         <li>HUs which are already picked or shipped assigned to them</li>
	 *         </ul>
	 *
	 *         Hint: also take a look at {@link #isPickedOrShippedOrNoHU(I_M_ShipmentSchedule_QtyPicked)}.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/759
	 * @task https://github.com/metasfresh/metasfresh/issues/1174
	 */
	private List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedRecords(final I_M_ShipmentSchedule schedule)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> unshippedHUs = shipmentScheduleAllocDAO.retrievePickedNotDeliveredRecords(schedule, I_M_ShipmentSchedule_QtyPicked.class)
				.stream()
				.filter(r -> isPickedOrShippedOrNoHU(r))
				.collect(ImmutableList.toImmutableList());

		// if we have an "undone" picking, i.e. positive and negative values sum up to zero, then return an empty list
		// this supports the case that something went wrong with picking, and the user needs to get out the shipment asap
		final BigDecimal qtySumOfUnshippedHUs = unshippedHUs.stream()
				.map(I_M_ShipmentSchedule_QtyPicked::getQtyPicked)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (qtySumOfUnshippedHUs.signum() <= 0)
		{
			return ImmutableList.of();
		}

		return unshippedHUs;
	}

	/**
	 * Returns {@code true} if there is either no HU assigned to the given {@code schedQtyPicked} or if that HU is either picked or shipped.
	 * If you don't see why it could possibly be already shipped, please take a look at issue <a href="https://github.com/metasfresh/metasfresh/issues/1174">#1174</a>.
	 *
	 * @param schedQtyPicked
	 * @return
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1174
	 */
	private boolean isPickedOrShippedOrNoHU(final I_M_ShipmentSchedule_QtyPicked schedQtyPicked)
	{
		final I_M_HU huToVerify;
		if (schedQtyPicked.getVHU_ID() >= 0)
		{
			huToVerify = schedQtyPicked.getVHU();
		}
		else if (schedQtyPicked.getM_TU_HU_ID() >= 0)
		{
			huToVerify = schedQtyPicked.getM_TU_HU();
		}
		else if (schedQtyPicked.getM_LU_HU_ID() >= 0)
		{
			huToVerify = schedQtyPicked.getM_LU_HU();
		}
		else
		{
			return true;
		}

		if (huToVerify == null)
		{
			return true; // this *might* happen with our "minidumps" there we don't have the HU data in our DB
		}

		final String huStatus = huToVerify.getHUStatus();
		return X_M_HU.HUSTATUS_Picked.equals(huStatus) || X_M_HU.HUSTATUS_Shipped.equals(huStatus);
	}

	/**
	 * Create LUs for given shipment schedule.
	 *
	 * After calling this method, all our TUs from QtyPicked records shall have an LU.
	 *
	 * @param schedule
	 */
	private void createLUs(final I_M_ShipmentSchedule schedule)
	{
		// Don't generate any HUs if we are in QuickShipment mode,
		// because in that mode we are creating shipments without and HUs

		// in case of using the isUseQtyPicked, create the LUs

		final boolean isUseQtyPicked = getParameters().getParameterAsBool(ShipmentScheduleWorkPackageParameters.PARAM_IsUseQtyPicked);

		if (HUConstants.isQuickShipment() && !isUseQtyPicked)
		{
			return;
		}

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = shipmentScheduleAllocDAO.retrievePickedNotDeliveredRecords(schedule, I_M_ShipmentSchedule_QtyPicked.class);

		//
		// Case: this shipment schedule line was not picked at all
		// => generate LUs for the whole Qty
		if (qtyPickedRecords.isEmpty())
		{
			createLUsForQtyToDeliver(schedule);
		}
		//
		// Case: this shipment schedule line was at least partial picked
		// => take all TUs which does not have an LU and add them to LUs
		else if (!isUseQtyPicked)
		{
			createLUsForTUs(schedule, qtyPickedRecords);
		}

	}

	/**
	 * Take all TUs from <code>qtyPickedRecords</code> which does not have an LU and create/add them to LUs
	 *
	 * @param schedule
	 * @param qtyPickedRecords
	 */
	private void createLUsForTUs(final I_M_ShipmentSchedule schedule, final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords)
	{
		//
		// Create HUContext from "schedule" because we want to get the Ctx and TrxName from there
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(schedule);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		//
		// Create our LU Loader. This will help us to aggregate TUs on corresponding LUs
		final LULoader luLoader = new LULoader(huContext);

		//
		// Iterate QtyPicked records
		for (final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord : qtyPickedRecords)
		{
			// refresh because it might be that a previous LU creation to update this record too
			InterfaceWrapperHelper.refresh(qtyPickedRecord);

			// Skip inactive lines
			if (!qtyPickedRecord.isActive())
			{
				continue;
			}

			// Skip lines without TUs
			if (qtyPickedRecord.getM_TU_HU_ID() <= 0)
			{
				continue;
			}

			// Skip lines with ZERO Qty
			if (qtyPickedRecord.getQtyPicked().signum() == 0)
			{
				continue;
			}

			// Skip lines which already have LUs created
			if (qtyPickedRecord.getM_LU_HU_ID() > 0)
			{
				continue;
			}

			final I_M_HU tuHU = qtyPickedRecord.getM_TU_HU();
			luLoader.addTU(tuHU);

			// NOTE: after TU was added to an LU we expect this qtyPickedRecord to be updated and M_LU_HU_ID to be set
			// Also, if there more then one QtyPickedRecords for tuHU, all those shall be updated
			// see de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleHUTrxListener.huParentChanged(I_M_HU, I_M_HU_Item)
		}
	}

	/**
	 * Create LUs for the whole QtyToDeliver from shipment schedule.
	 *
	 * Note: this method is not checking current QtyPicked records (because we assume there are none).
	 *
	 * @param schedule
	 */
	private void createLUsForQtyToDeliver(final I_M_ShipmentSchedule schedule)
	{
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(schedule);
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		//
		// Create Allocation Request: whole Qty to Deliver
		final BigDecimal qtyToDeliver = shipmentScheduleEffectiveValuesBL.getQtyToDeliver(schedule);
		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				schedule.getM_Product(),
				qtyToDeliver,
				shipmentScheduleBL.getUomOfProduct(schedule),      // uom
				SystemTime.asDate(),
				schedule,      // reference model
				false // forceQtyAllocation
		);

		//
		// Create Allocation Source & Destination
		final IAllocationSource allocationSource = createAllocationSource(schedule);
		final ILUTUProducerAllocationDestination allocationDestination = createLUTUProducerDestination(schedule);
		if (allocationDestination == null)
		{
			return;
		}

		//
		// Execute transfer
		final IAllocationResult result = HULoader.of(allocationSource, allocationDestination)
				.setAllowPartialLoads(false)
				.setAllowPartialUnloads(false)
				.load(request);
		Check.assume(result.isCompleted(), "Result shall be completed: {}", result);

		// NOTE: at this point we shall have QtyPicked records with M_LU_HU_ID set
	}

	private IAllocationSource createAllocationSource(final I_M_ShipmentSchedule schedule)
	{
		final ShipmentScheduleQtyPickedProductStorage shipmentScheduleQtyPickedStorage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		final GenericAllocationSourceDestination source = new GenericAllocationSourceDestination(shipmentScheduleQtyPickedStorage, schedule);

		return source;
	}

	private ILUTUProducerAllocationDestination createLUTUProducerDestination(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final I_M_HU_LUTU_Configuration lutuConfiguration = huShipmentScheduleBL.deriveM_HU_LUTU_Configuration(schedule);
		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		lutuConfigurationFactory.save(lutuConfiguration);

		final ILUTUProducerAllocationDestination luProducerDestination = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		//
		// Make sure we have our LU PI configured
		if (luProducerDestination.isNoLU())
		{
			throw new HUException("No Loading Unit found for TU: " + luProducerDestination.getTUPI()
					+ "\n@M_ShipmentSchedule_ID@: " + schedule
					+ "\n@Destination@: " + luProducerDestination);
		}

		return luProducerDestination;
	}
}
