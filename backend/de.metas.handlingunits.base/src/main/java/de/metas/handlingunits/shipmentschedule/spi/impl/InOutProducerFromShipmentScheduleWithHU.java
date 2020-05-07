package de.metas.handlingunits.shipmentschedule.spi.impl;

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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.impl.HUShipmentPackingMaterialLinesBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inout.event.InOutProcessedEventBus;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipping.model.I_M_ShipperTransportation;
import lombok.NonNull;

/**
 * Create Shipments from {@link ShipmentScheduleWithHU} records.
 *
 * @author tsa
 *
 */
public class InOutProducerFromShipmentScheduleWithHU
		implements IInOutProducerFromShipmentScheduleWithHU, ITrxItemChunkProcessor<ShipmentScheduleWithHU, InOutGenerateResult>
{
	//
	// Services
	private final transient IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final transient IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final transient IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final transient IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final transient IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	//
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);

	private final InOutGenerateResult result;
	private final IAggregationKeyBuilder<I_M_ShipmentSchedule> shipmentScheduleKeyBuilder;
	private final IAggregationKeyBuilder<ShipmentScheduleWithHU> huShipmentScheduleKeyBuilder;

	private ITrxItemProcessorContext processorCtx;
	private ITrxItemExceptionHandler trxItemExceptionHandler = FailTrxItemExceptionHandler.instance;

	private I_M_InOut currentShipment;
	private ShipmentLineBuilder currentShipmentLineBuilder;

	/**
	 * All candidates for {@link #currentShipment}
	 */
	private List<ShipmentScheduleWithHU> currentCandidates;

	/**
	 * Last processed item
	 */
	private ShipmentScheduleWithHU lastItem;

	//
	// 07113: constant to know if the document shall be completed or not.
	private boolean processShipments = true;

	private boolean createPackingLines = false;
	private boolean manualPackingMaterial = false;
	private boolean shipmentDateToday = false;

	/**
	 * A list of TUs which are assigned to different shipment lines.
	 *
	 * This list is shared between all shipment lines from all shipments which are produced by this producer.
	 *
	 * In this way, we {@link I_M_HU_Assignment#setIsTransferPackingMaterials(boolean)} to <code>true</code> only on first assignment.
	 */
	private final Set<Integer> tuIdsAlreadyAssignedToShipmentLine = new HashSet<>();

	public InOutProducerFromShipmentScheduleWithHU(final InOutGenerateResult result)
	{
		super();

		this.result = result;

		shipmentScheduleKeyBuilder = shipmentScheduleBL.mkShipmentHeaderAggregationKeyBuilder();
		huShipmentScheduleKeyBuilder = huShipmentScheduleBL.mkHUShipmentScheduleHeaderAggregationKeyBuilder();
	}

	@Override
	public InOutGenerateResult createShipments(final List<ShipmentScheduleWithHU> candidates)
	{
		final InOutProcessedEventBus shipmentGeneratedNotifications = InOutProcessedEventBus.newInstance()
				.queueEventsUntilCurrentTrxCommit();

		try
		{
			final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
			final InOutGenerateResult result = trxItemProcessorExecutorService
					.<ShipmentScheduleWithHU, InOutGenerateResult> createExecutor()
					.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
					.setProcessor(this)
					.setExceptionHandler(trxItemExceptionHandler)
					.process(candidates);

			//
			// Send notifications
			shipmentGeneratedNotifications.notify(result.getInOuts());

			return result;
		}
		catch (final Exception ex)
		{
			final String sourceInfo = extractSourceInfo(candidates);
			shipmentGeneratedNotifications.notifyShipmentError(sourceInfo, ex.getLocalizedMessage());

			// propagate
			throw AdempiereException.wrapIfNeeded(ex)
					.markUserNotified();
		}

	}

	private static String extractSourceInfo(final List<ShipmentScheduleWithHU> candidates)
	{
		return candidates.stream()
				.map(candidate -> extractSourceInfo(candidate))
				.distinct()
				.collect(Collectors.joining(", "));
	}

	private static String extractSourceInfo(final ShipmentScheduleWithHU candidate)
	{
		final I_M_HU luHU = candidate.getM_LU_HU();
		if (luHU != null)
		{
			return luHU.getValue();
		}

		final I_M_HU tuHU = candidate.getM_TU_HU();
		if (tuHU != null)
		{
			return tuHU.getValue();
		}

		// default, shall not reach this point
		return "#" + candidate.getM_ShipmentSchedule().getM_ShipmentSchedule_ID();
	}

	@Override
	public boolean isSameChunk(final ShipmentScheduleWithHU item)
	{
		Check.assumeNotNull(item, "Param item' is not null");

		//
		// If there is no last item (i.e. this is the first item), consider it as a new chunk
		if (lastItem == null)
		{
			return false;
		}

		final I_M_ShipmentSchedule sched = item.getM_ShipmentSchedule();
		final I_M_ShipmentSchedule lastSched = lastItem.getM_ShipmentSchedule();

		return shipmentScheduleKeyBuilder.isSame(sched, lastSched) &&
				huShipmentScheduleKeyBuilder.isSame(item, lastItem);
	}

	@Override
	public void newChunk(final ShipmentScheduleWithHU sched)
	{
		currentShipment = getCreateShipmentHeader(sched);
		currentShipmentLineBuilder = null;
		currentCandidates = new ArrayList<>();
	}

	/**
	 * If the schedule allows consolidation, try to retrieve the fist open shipment found matching requested transportation. If none is found, create a new shipment header.
	 *
	 * @param candidate
	 * @return shipment (header)
	 */
	private I_M_InOut getCreateShipmentHeader(final ShipmentScheduleWithHU candidate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = candidate.getM_ShipmentSchedule();

		final Timestamp shipmentDate = calculateShipmentDate(shipmentSchedule, shipmentDateToday);

		//
		// Search for existing shipment to consolidate on
		I_M_InOut shipment = null;
		if (shipmentScheduleBL.isSchedAllowsConsolidate(shipmentSchedule))
		{
			shipment = huShipmentScheduleBL.getOpenShipmentOrNull(candidate, shipmentDate);
		}
		//
		// If there was no shipment found, create a new one now
		if (shipment == null)
		{
			shipment = createShipmentHeader(candidate, shipmentDate);
		}
		return shipment;
	}

	@VisibleForTesting
	static Timestamp calculateShipmentDate(final @NonNull I_M_ShipmentSchedule schedule, final boolean isShipmentDateToday)
	{
		final Timestamp today = SystemTime.asDayTimestamp();

		if (isShipmentDateToday)
		{
			return today;
		}

		final Timestamp deliveryDateEffective = Services.get(IShipmentScheduleEffectiveBL.class).getDeliveryDate(schedule);

		if (deliveryDateEffective == null)
		{
			return today;
		}

		if (deliveryDateEffective.before(today))
		{
			return today;
		}

		return deliveryDateEffective;
	}

	/**
	 * NOTE: KEEP IN SYNC WITH {@link de.metas.handlingunits.shipmentschedule.api.impl.HUShipmentScheduleBL#getOpenShipmentOrNull(ShipmentScheduleWithHU)}
	 *
	 * @param candidate
	 * @param movementDate
	 * @return shipment
	 */
	private I_M_InOut createShipmentHeader(final ShipmentScheduleWithHU candidate, final Timestamp movementDate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = candidate.getM_ShipmentSchedule();

		final I_M_InOut shipment = InterfaceWrapperHelper.newInstance(I_M_InOut.class, processorCtx);
		shipment.setAD_Org_ID(shipmentSchedule.getAD_Org_ID());

		//
		// Document Type
		{
			final int docTypeId = docTypeDAO.getDocTypeId(processorCtx.getCtx(),
					X_C_DocType.DOCBASETYPE_MaterialDelivery,
					shipmentSchedule.getAD_Client_ID(),
					shipmentSchedule.getAD_Org_ID(),
					ITrx.TRXNAME_NoneNotNull);
			shipment.setC_DocType_ID(docTypeId);
			shipment.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerShipment);
			shipment.setIsSOTrx(true);
		}

		//
		// BPartner, Location & Contact
		{
			shipment.setC_BPartner_ID(shipmentScheduleEffectiveValuesBL.getC_BPartner_ID(shipmentSchedule));
			shipment.setC_BPartner_Location_ID(shipmentScheduleEffectiveValuesBL.getBPartnerLocation(shipmentSchedule).getC_BPartner_Location_ID());
			shipment.setAD_User_ID(shipmentScheduleEffectiveValuesBL.getAD_User_ID(shipmentSchedule));
		}

		//
		// Document Dates
		{
			shipment.setMovementDate(movementDate);
			shipment.setDateAcct(movementDate);
		}

		//
		// Warehouse
		{
			shipment.setM_Warehouse_ID(shipmentScheduleEffectiveValuesBL.getWarehouseId(shipmentSchedule));
		}

		//
		// C_Order reference
		{
			final I_C_Order order = shipmentSchedule.getC_Order();
			if (order != null && order.getC_Order_ID() > 0)
			{
				shipment.setDateOrdered(order.getDateOrdered());
				shipment.setC_Order_ID(order.getC_Order_ID()); // TODO change if partner allow consolidation too
				shipment.setPOReference(order.getPOReference());
			}
		}

		//
		// M_ShipperTransportation (07973: set it on the shipment so that it can be re-used in aggregation)
		{
			final List<I_M_HU> hus = new ArrayList<>();
			hus.add(candidate.getVHU());
			hus.add(candidate.getM_TU_HU());
			hus.add(candidate.getM_LU_HU());
			final I_M_ShipperTransportation shipperTransportation = huShipperTransportationBL.getCommonM_ShipperTransportationOrNull(hus);
			if (shipperTransportation != null)
			{
				shipment.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());
			}
		}

		//
		// Save Shipment Header
		InterfaceWrapperHelper.save(shipment);

		return shipment;
	}

	/**
	 * If {@link #currentShipmentLineBuilder} is set and it can create a shipment line then:
	 * <ul>
	 * <li>create the shipment line
	 * <li>update {@link #currentCandidates}
	 * <li>reset the current builder (set it to null)
	 * </ul>
	 */
	private void createShipmentLineIfAny()
	{
		if (currentShipmentLineBuilder == null)
		{
			return;
		}

		if (currentShipmentLineBuilder.canCreateShipmentLine())
		{
			currentShipmentLineBuilder.createShipmentLine();

			final List<ShipmentScheduleWithHU> candidates = currentShipmentLineBuilder.getCandidates();
			currentCandidates.addAll(candidates);
		}

		currentShipmentLineBuilder = null;
	}

	@Override
	public void completeChunk()
	{
		//
		// Create current shipment line (if any)
		createShipmentLineIfAny();

		//
		// Process shipment (if is not empty)
		if (!isCurrentShipmentEmpty())
		{
			final HUShipmentPackingMaterialLinesBuilder packingMaterialLinesBuilder = huInOutBL.createHUShipmentPackingMaterialLinesBuilder(currentShipment);

			// because this is the time when we are actually generating the shipment lines,
			// we want to initialize overrides values too
			packingMaterialLinesBuilder.setUpdateOverrideValues(true);

			// task 08138
			if (createPackingLines)
			{
				packingMaterialLinesBuilder.setOverrideExistingPackingMaterialLines(true); // delete existing packing material lines, if any
				packingMaterialLinesBuilder.build();
			}
			else
			{
				packingMaterialLinesBuilder.collectPackingMaterialsAndUpdateShipmentLines();
			}

			//
			// Process current shipment
			// 07113: if the CompleteShipment is false, we will have them only drafted (as default)
			if (processShipments)
			{
				docActionBL.processEx(currentShipment, IDocument.ACTION_Complete, null);
			}
			result.addInOut(currentShipment);

			//
			// Iterate all candidates which were added on this shipment and notify them about the generated shipment
			for (final ShipmentScheduleWithHU candidate : currentCandidates)
			{
				candidate.setM_InOut(currentShipment);
			}

			//
			// Iterate all candidates and update shipment schedule's HU related Qtys
			final Set<Integer> seenM_ShipmentSchedule_IDs = new HashSet<>();
			for (final ShipmentScheduleWithHU candidate : currentCandidates)
			{
				final I_M_ShipmentSchedule shipmentSchedule = candidate.getM_ShipmentSchedule();
				final int shipmentScheduleId = shipmentSchedule.getM_ShipmentSchedule_ID();
				if (!seenM_ShipmentSchedule_IDs.add(shipmentScheduleId))
				{
					// already seen this shipment schedule
					continue;
				}

				// save the shipment schedule using current transaction
				InterfaceWrapperHelper.save(shipmentSchedule, processorCtx.getTrxName());
			}
			Loggables.get().addLog("Shipment {0} was created;\nShipmentScheduleWithHUs: {1}", currentShipment, currentCandidates);
		}
		else
		{
			Loggables.get().addLog("Shipment {0} would be empty, so deleting it again", currentShipment);
			InterfaceWrapperHelper.delete(currentShipment);
		}

		resetCurrentShipment();
	}

	/**
	 * @return true if current shipment is empty (i.e. there were no candidates considered for it)
	 */
	private final boolean isCurrentShipmentEmpty()
	{
		return currentCandidates == null || currentCandidates.isEmpty();
	}

	private void resetCurrentShipment()
	{
		currentShipmentLineBuilder = null;
		currentShipment = null;
		currentCandidates = null;
	}

	@Override
	public final void cancelChunk()
	{
		// NOTE: no need to delete current shipment if not null because when this method is called the API is preparing to rollback
		resetCurrentShipment();
	}

	@Override
	public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setTrxItemExceptionHandler(@NonNull final ITrxItemExceptionHandler trxItemExceptionHandler)
	{
		this.trxItemExceptionHandler = trxItemExceptionHandler;
		return this;
	}

	@Override
	public void process(final ShipmentScheduleWithHU item) throws Exception
	{
		updateShipmentDate(currentShipment, item);
		createUpdateShipmentLine(item);
		lastItem = item;
	}

	private void updateShipmentDate(@NonNull final I_M_InOut shipment, @NonNull final ShipmentScheduleWithHU candidate)
	{
		final I_M_ShipmentSchedule schedule = candidate.getM_ShipmentSchedule();
		final Timestamp candidateShipmentDate = calculateShipmentDate(schedule, shipmentDateToday);

		// the shipment was created before but wasn't yet completed;
		if (isShipmentDeliveryDateBetterThanMovementDate(shipment, candidateShipmentDate))
		{
			shipment.setMovementDate(candidateShipmentDate);
			shipment.setDateAcct(candidateShipmentDate);

			InterfaceWrapperHelper.save(shipment);
		}
	}

	@VisibleForTesting
	static boolean isShipmentDeliveryDateBetterThanMovementDate(final @NonNull I_M_InOut shipment, final @NonNull Timestamp shipmentDeliveryDate)
	{
		final Timestamp today = SystemTime.asDayTimestamp();
		final Timestamp movementDate = shipment.getMovementDate();

		final boolean isCandidateInThePast = shipmentDeliveryDate.before(today);
		final boolean isMovementDateInThePast = movementDate.before(today);
		final boolean isCandidateSoonerThanMovementDate = movementDate.after(shipmentDeliveryDate);

		if (isCandidateInThePast)
		{
			return false;
		}

		if (isMovementDateInThePast)
		{
			return true;
		}

		else if (isCandidateSoonerThanMovementDate)
		{
			return true;
		}

		return false;
	}

	private void createUpdateShipmentLine(final ShipmentScheduleWithHU candidate)
	{
		//
		// If we cannot add this "candidate" to current shipment line builder
		// then create shipment line (if any) and reset the builder
		if (currentShipmentLineBuilder != null && !currentShipmentLineBuilder.canAdd(candidate))
		{
			createShipmentLineIfAny();
			// => currentShipmentLineBuilder = null;
		}

		//
		// If we don't have an active shipment line builder
		// then create one
		if (currentShipmentLineBuilder == null)
		{
			currentShipmentLineBuilder = new ShipmentLineBuilder(currentShipment);
			currentShipmentLineBuilder.setManualPackingMaterial(manualPackingMaterial);
			currentShipmentLineBuilder.setAlreadyAssignedTUIds(tuIdsAlreadyAssignedToShipmentLine);

		}

		//
		// Add current "candidate"
		currentShipmentLineBuilder.add(candidate);
	}

	@Override
	public InOutGenerateResult getResult()
	{
		return result;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setProcessShipments(final boolean processShipments)
	{
		this.processShipments = processShipments;
		return this;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setCreatePackingLines(final boolean createPackingLines)
	{
		this.createPackingLines = createPackingLines;
		return this;
	}

	@Override
	public InOutProducerFromShipmentScheduleWithHU setManualPackingMaterial(final boolean manualPackingMaterial)
	{
		this.manualPackingMaterial = manualPackingMaterial;
		return this;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU computeShipmentDate(boolean forceDateToday)
	{
		this.shipmentDateToday = forceDateToday;
		return this;
	}

	@Override
	public String toString()
	{
		return "InOutProducerFromShipmentSchedule [result=" + result
				+ ", shipmentScheduleKeyBuilder=" + shipmentScheduleKeyBuilder + ", huShipmentScheduleKeyBuilder=" + huShipmentScheduleKeyBuilder
				+ ", processorCtx=" + processorCtx + ", currentShipment=" + currentShipment
				+ ", currentShipmentLineBuilder=" + currentShipmentLineBuilder + ", currentCandidates=" + currentCandidates
				+ ", lastItem=" + lastItem + "]";
	}

}
