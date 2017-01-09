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
import java.util.Properties;

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
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.compiere.process.DocAction;
import org.slf4j.Logger;

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
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleQtyPickedProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.logging.LogManager;

/**
 * Generate Shipments from given shipment schedules by processing enqueued work packages.<br>
 * This usually happens on the server site, in an asynchronous manner.<br>
 * See {@link #processWorkPackage(I_C_Queue_WorkPackage, String)}.
 * Note: the enqeueing part is done by {@link de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleEnqueuer ShipmentScheduleEnqueuer}.
 *
 * @author tsa
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

	public static final String PARAM_IsUseQtyPicked = "IsUseQtyPicked";
	public static final String PARAM_IsCompleteShipments = "IsCompleteShipments";

	public GenerateInOutFromShipmentSchedules()
	{
		super();
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		// Create candidates
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final IHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, ITrx.TRXNAME_ThreadInherited);
		final List<IShipmentScheduleWithHU> candidates = createCandidates(huContext, workpackage, ITrx.TRXNAME_ThreadInherited);
		if (candidates.isEmpty())
		{
			// this is a frequent case and we received no complaints so far. So don't throw an exception, just log it
			Loggables.get().addLog("No unprocessed candidates were found");
		}

		//
		// Generate shipments
		final GenerateInOutFromHU shipmentGenerator = new GenerateInOutFromHU();
		shipmentGenerator.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance);

		final String shipmentDocDocAction;
		if (getParameters().getParameterAsBool(PARAM_IsCompleteShipments))
		{
			shipmentDocDocAction = DocAction.ACTION_Complete;
		}
		else
		{
			shipmentDocDocAction = null; // let the document as it is, don't complete it
		}

		boolean createPackingLines = true; // task 08138: the packing lines shall be created directly, and shall be user-editable.
		boolean manualPackingMaterial = true;

		final boolean isUseQtyPicked = getParameters().getParameterAsBool(PARAM_IsUseQtyPicked);
		// task FRESH-251
		// In case of using the qty Picked entries, the logic will be similar with shipment creation from HU, therefore the packinglines and manualPackingMaterial flags will be disabled
		if (isUseQtyPicked)
		{
			createPackingLines = false; // the packing lines shall only be created when the shipments are completed
			manualPackingMaterial = false; // use the HUs!

		}
		shipmentGenerator.generateInOuts(ctx, candidates.iterator(), shipmentDocDocAction, createPackingLines, manualPackingMaterial, ITrx.TRXNAME_ThreadInherited);

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
	private final List<IShipmentScheduleWithHU> createCandidates(final IHUContext huContext, final I_C_Queue_WorkPackage workpackage, final String trxName)
	{
		final List<IShipmentScheduleWithHU> candidates = new ArrayList<>();

		final Iterator<I_M_ShipmentSchedule> schedules = retriveShipmentSchedules(workpackage, trxName);
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

			final List<IShipmentScheduleWithHU> scheduleCandidates = createCandidates(huContext, schedule);
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

	private Iterator<I_M_ShipmentSchedule> retriveShipmentSchedules(final I_C_Queue_WorkPackage workpackage, final String trxName)
	{
		final boolean skipAlreadyProcessedItems = false; // yes, we want items whose queue packages were already processed! This is a workaround, but we need it that way.
		// Background: otherwise, after we did a partial delivery on a shipment schedule, we cannot deliver the rest, because the sched is already within a processed work package.
		// Note that it's the customer's declared responsibility to to verify the shipments
		// FIXME: find a better solution. If nothing else, then "split" the undelivered remainder of a partially delivered schedule off into a new schedule (we do that with ICs too).
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queueDAO.createElementsQueryBuilder(workpackage, I_M_ShipmentSchedule.class, skipAlreadyProcessedItems, trxName);

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
	 * @param schedule
	 * @return one single candidate if there are no {@link I_M_ShipmentSchedule_QtyPicked} for the given schedule. One candidate per {@link I_M_ShipmentSchedule_QtyPicked} otherwise.
	 */
	private List<IShipmentScheduleWithHU> createCandidates(final IHUContext huContext, final I_M_ShipmentSchedule schedule)
	{
		//
		// Load all QtyPicked records that have no InOutLine yet
		List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = shipmentScheduleAllocDAO.retrievePickedNotDeliveredRecords(schedule, I_M_ShipmentSchedule_QtyPicked.class);

		final boolean isUseQtyPicked = getParameters().getParameterAsBool(PARAM_IsUseQtyPicked);

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
					Loggables.get().addLog("Skipped shipment schedule because it was already delivered: " + schedule);
					return Collections.emptyList();
				}

				final String errorMsg = Services.get(IMsgBL.class).getMsg(InterfaceWrapperHelper.getCtx(schedule), MSG_NoQtyPicked);
				throw new AdempiereException(errorMsg);
			}

			// There are no picked qtys for the given shipment schedule, so we will ship as is (without any handling units)
			final BigDecimal qtyToDeliver = shipmentScheduleEffectiveValuesBL.getQtyToDeliver(schedule);
			final IShipmentScheduleWithHU candidate = new ShipmentScheduleWithHU(huContext, schedule, qtyToDeliver);
			return Collections.singletonList(candidate);
		}

		//
		// Create necessary LUs (if any)
		createLUs(schedule);

		// retrieve the qty picked entries again, some new ones might have been created on LU creation
		qtyPickedRecords = shipmentScheduleAllocDAO.retrievePickedNotDeliveredRecords(schedule, I_M_ShipmentSchedule_QtyPicked.class);

		//
		// Iterate all QtyPicked records and create candidates from them
		final List<IShipmentScheduleWithHU> candidates = new ArrayList<>(qtyPickedRecords.size());
		for (final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked qtyPickedRecord : qtyPickedRecords)
		{
			final I_M_ShipmentSchedule_QtyPicked qtyPickedRecordHU = InterfaceWrapperHelper.create(qtyPickedRecord, I_M_ShipmentSchedule_QtyPicked.class);

			// guard: Skip inactive records
			if (!qtyPickedRecordHU.isActive())
			{
				continue;
			}

			// Considering only those lines which have an LU
			// NOTE: this shall not happen because we already created the LUs
			// Task FRESH-251 : In case the qty Picked are used, only add the LU if it was required by the qty picked
			if (qtyPickedRecordHU.getM_LU_HU_ID() <= 0 && !isUseQtyPicked)
			{
				final HUException ex = new HUException("Record shall have LU set: " + qtyPickedRecord);
				logger.warn(ex.getLocalizedMessage() + " [Skipped]", ex);
				continue;
			}

			//
			// Create ShipmentSchedule+HU candidate and add it to our list
			final IShipmentScheduleWithHU candidate = new ShipmentScheduleWithHU(huContext, qtyPickedRecordHU);
			candidates.add(candidate);
		}

		return candidates;
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

		final boolean isUseQtyPicked = getParameters().getParameterAsBool(PARAM_IsUseQtyPicked);

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
				shipmentScheduleBL.getC_UOM(schedule),      // uom
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
		final HULoader loader = new HULoader(allocationSource, allocationDestination);
		loader.setAllowPartialLoads(false);
		loader.setAllowPartialUnloads(false);
		final IAllocationResult result = loader.load(request);
		Check.assume(result.isCompleted(), "Result shall be completed: {}", result);

		// NOTE: at this point we shall have QtyPicked records with M_LU_HU_ID set
	}

	private IAllocationSource createAllocationSource(final I_M_ShipmentSchedule schedule)
	{
		final ShipmentScheduleQtyPickedProductStorage shipmentScheduleQtyPickedStorage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		final GenericAllocationSourceDestination source = new GenericAllocationSourceDestination(shipmentScheduleQtyPickedStorage, schedule);

		return source;
	}

	private ILUTUProducerAllocationDestination createLUTUProducerDestination(final I_M_ShipmentSchedule schedule)
	{
		Check.assumeNotNull(schedule, "schedule not null");

		final I_M_HU_LUTU_Configuration lutuConfiguration = huShipmentScheduleBL.getM_HU_LUTU_Configuration(schedule);
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
