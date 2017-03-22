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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;

import com.google.common.annotations.VisibleForTesting;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.ILatchStragegy;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.inout.event.InOutProcessedEventBus;
import de.metas.inoutcandidate.api.InOutGenerateResult;

/**
 * Generates Shipment document from given loading units (LUs).
 * <p>
 * Note: this processor is used both
 * <ul>
 * <li>directly by the <code>de.metas.async</code> framework, when shipments are created from the aggregation POS terminal</li>
 * <li>indirectly from {@link GenerateInOutFromShipmentSchedules}, when shipments are created directly from a shipment schedule</li>
 * <ul>
 *
 * @author tsa
 *
 */
public class GenerateInOutFromHU extends WorkpackageProcessorAdapter
{
	private ITrxItemExceptionHandler trxItemExceptionHandler = null;

	private InOutGenerateResult inoutGenerateResult = null;

	/**
	 * Create and enqueue a workpackage for given handling units. Created workpackage will be marked as ready for processing.
	 *
	 * @param ctx
	 * @param hus handling units to enqueue
	 * @return created workpackage.
	 */
	public static final I_C_Queue_WorkPackage enqueueWorkpackage(final Properties ctx, final List<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_HU_ID@");
		}

		return Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, GenerateInOutFromHU.class)
				.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.addElements(hus)
				.build();
	}

	public void setTrxItemExceptionHandler(final ITrxItemExceptionHandler trxItemExceptionHandler)
	{
		this.trxItemExceptionHandler = trxItemExceptionHandler;
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workpackage);
		final IHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, ITrx.TRXNAME_ThreadInherited);
		final Iterator<IShipmentScheduleWithHU> candidates = retrieveCandidates(huContext, workpackage, ITrx.TRXNAME_ThreadInherited);

		// 07113: At this point, we only need the shipment drafted
		final String docActionNone = null;
		final boolean createPackingLines = false; // the packing lines shall only be created when the shipments are completed
		final boolean manualPackingMaterial = false; // use the HUs!

		// Fail on any exception, because we cannot create just a part of those shipments.
		// Think about HUs which are linked to multiple shipments: you will not see then in Aggregation POS because are already assigned, but u are not able to create shipment from them again.
		setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance);

		inoutGenerateResult = generateInOuts(ctx, candidates, docActionNone, createPackingLines, manualPackingMaterial, ITrx.TRXNAME_ThreadInherited);
		Loggables.get().addLog("Generated " + inoutGenerateResult.toString());

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
	 * Gets the {@link InOutGenerateResult} created by {@link #processWorkPackage(I_C_Queue_WorkPackage, String)}.
	 *
	 * @return shipment generation result; never return null
	 */
	public InOutGenerateResult getInOutGenerateResult()
	{
		Check.assumeNotNull(inoutGenerateResult, "workpackage shall be processed first");
		return inoutGenerateResult;
	}

	/**
	 *
	 * @param ctx
	 * @param candidates
	 * @param processShipmentsDocAction parameter is passed to the inOutProducer. See {@link IInOutProducerFromShipmentScheduleWithHU#setProcessShipmentsDocAction(String)}
	 * @param createPackingLines parameter is passed to the inOutProducer. See {@link IInOutProducerFromShipmentScheduleWithHU#setCreatePackingLines(boolean)}
	 * @param trxName
	 * @return
	 */
	public InOutGenerateResult generateInOuts(final Properties ctx,
			final Iterator<IShipmentScheduleWithHU> candidates,
			final String processShipmentsDocAction,
			final boolean createPackingLines,
			final boolean manualPackingMaterial,
			final String trxName)
	{
		final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

		//
		// Create shipment producer
		final IInOutProducerFromShipmentScheduleWithHU inoutProducer = huShipmentScheduleBL.createInOutProducerFromShipmentSchedule();
		final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		inoutProducer
				.setProcessShipmentsDocAction(processShipmentsDocAction)
				.setCreatePackingLines(createPackingLines)
				.setManualPackingMaterial(manualPackingMaterial);

		//
		// Create shipment producer batch executor
		final ITrxItemProcessorExecutorService executorService = trxItemProcessorExecutorService;

		final ITrx trx = trxManager.getTrx(trxName);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(ctx, trx);
		final ITrxItemProcessorExecutor<IShipmentScheduleWithHU, InOutGenerateResult> executor = executorService.createExecutor(processorCtx, inoutProducer);

		if (trxItemExceptionHandler != null)
		{
			executor.setExceptionHandler(trxItemExceptionHandler);
		}

		//
		// Process candidates
		final InOutGenerateResult result = executor.execute(candidates);

		//
		// Send notifications
		InOutProcessedEventBus.newInstance()
				.queueEventsUntilTrxCommit(trxName)
				.notify(result.getInOuts());

		return result;
	}

	private List<I_M_HU> retriveWorkpackageHUs(final I_C_Queue_WorkPackage workpackage, final String trxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final List<I_M_HU> hus = queueDAO.retrieveItems(workpackage, I_M_HU.class, trxName);
		return hus;
	}

	@VisibleForTesting
	public Iterator<IShipmentScheduleWithHU> retrieveCandidates(final IHUContext huContext, final I_C_Queue_WorkPackage workpackage, final String trxName)
	{
		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final List<IShipmentScheduleWithHU> result = new ArrayList<>();

		final List<I_M_HU> hus = retriveWorkpackageHUs(workpackage, trxName);
		if (hus.isEmpty())
		{
			Loggables.get().addLog("No HUs found");
			return result.iterator();
		}

		//
		// Iterate HUs and collect candidates from them
		for (final I_M_HU hu : hus)
		{
			// Make sure we are dealing with an top level HU
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				throw new HUException("HU " + hu + " shall be top level");
			}

			//
			// Retrieve and create candidates from shipment schedule QtyPicked assignments
			final List<IShipmentScheduleWithHU> candidatesForHU = new ArrayList<>();
			final List<I_M_ShipmentSchedule_QtyPicked> ssQtyPickedList = huShipmentScheduleDAO.retriveQtyPickedNotDeliveredForTopLevelHU(hu);
			for (final I_M_ShipmentSchedule_QtyPicked ssQtyPicked : ssQtyPickedList)
			{
				if (!ssQtyPicked.isActive())
				{
					continue;
				}

				// NOTE: we allow negative Qtys too because they shall be part of a bigger transfer and overall qty can be positive
				// if (ssQtyPicked.getQtyPicked().signum() <= 0)
				// {
				// continue;
				// }

				final IShipmentScheduleWithHU candidate = new ShipmentScheduleWithHU(huContext, ssQtyPicked);
				candidatesForHU.add(candidate);
			}

			//
			// Add the candidates for current HU to the list of all collected candidates
			result.addAll(candidatesForHU);

			// Log if there were no candidates created for current HU.
			if (candidatesForHU.isEmpty())
			{
				Loggables.get().addLog("No eligible " + I_M_ShipmentSchedule_QtyPicked.Table_Name + " records found for " + handlingUnitsBL.getDisplayName(hu));
			}
		}

		//
		// Sort result
		Collections.sort(result, new ShipmentScheduleWithHUComparator());

		// TODO: make sure all shipment schedules are valid

		return result.iterator();
	}
}
