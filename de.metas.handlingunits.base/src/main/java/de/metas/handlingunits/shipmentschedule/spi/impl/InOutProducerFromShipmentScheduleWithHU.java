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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.compiere.process.DocAction;

import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.impl.HUShipmentPackingMaterialLinesBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipping.model.I_M_ShipperTransportation;

/**
 * Create Shipments from {@link IShipmentScheduleWithHU} records.
 *
 * @author tsa
 *
 */
public class InOutProducerFromShipmentScheduleWithHU implements IInOutProducerFromShipmentScheduleWithHU
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
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);

	private final InOutGenerateResult result;
	private final IAggregationKeyBuilder<I_M_ShipmentSchedule> shipmentScheduleKeyBuilder;
	private final IAggregationKeyBuilder<IShipmentScheduleWithHU> huShipmentScheduleKeyBuilder;

	private ITrxItemProcessorContext processorCtx;

	private I_M_InOut currentShipment;
	private ShipmentLineBuilder currentShipmentLineBuilder;

	/**
	 * All candidates for {@link #currentShipment}
	 */
	private List<IShipmentScheduleWithHU> currentCandidates;

	/**
	 * Last processed item
	 */
	private IShipmentScheduleWithHU lastItem;

	//
	// 07113: constant to know if the document shall be completed or not. Null means "don't process"
	private String processShipmentDocAction = DocAction.ACTION_Complete;

	private boolean createPackingLines = false;
	private boolean manualPackingMaterial = false;
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
	public boolean isSameChunk(final IShipmentScheduleWithHU item)
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
	public void newChunk(final IShipmentScheduleWithHU sched)
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
	private I_M_InOut getCreateShipmentHeader(final IShipmentScheduleWithHU candidate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = candidate.getM_ShipmentSchedule();

		final Timestamp movementDate = SystemTime.asTimestamp();

		//
		// Search for existing shipment to consolidate on
		I_M_InOut shipment = null;
		if (shipmentScheduleBL.isSchedAllowsConsolidate(shipmentSchedule))
		{
			shipment = huShipmentScheduleBL.getOpenShipmentScheduleOrNull(candidate, movementDate);
		}

		//
		// If there was no shipment found, create a new one now
		if (shipment == null)
		{
			shipment = createShipmentHeader(candidate, movementDate);
		}
		return shipment;
	}

	/**
	 * NOTE: KEEP IN SYNC WITH {@link de.metas.handlingunits.shipmentschedule.api.impl.HUShipmentScheduleBL#getOpenShipmentScheduleOrNull(IShipmentScheduleWithHU)}
	 *
	 * @param candidate
	 * @param movementDate
	 * @return shipment
	 */
	private I_M_InOut createShipmentHeader(final IShipmentScheduleWithHU candidate, final Timestamp movementDate)
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

	private void createUpdateShipmentLine(final IShipmentScheduleWithHU candidate)
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

			final List<IShipmentScheduleWithHU> candidates = currentShipmentLineBuilder.getCandidates();
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
			if (!Check.isEmpty(processShipmentDocAction, true))
			{
				docActionBL.processEx(currentShipment, processShipmentDocAction, null);
			}
			result.addInOut(currentShipment);

			//
			// Iterate all candidates which were added on this shipment and notify them about the generated shipment
			for (final IShipmentScheduleWithHU candidate : currentCandidates)
			{
				candidate.setM_InOut(currentShipment);
			}

			//
			// Iterate all candidates and update shipment schedule's HU related Qtys
			final Set<Integer> seenM_ShipmentSchedule_IDs = new HashSet<>();
			for (final IShipmentScheduleWithHU candidate : currentCandidates)
			{
				final I_M_ShipmentSchedule shipmentSchedule = candidate.getM_ShipmentSchedule();
				final int shipmentScheduleId = shipmentSchedule.getM_ShipmentSchedule_ID();
				if (!seenM_ShipmentSchedule_IDs.add(shipmentScheduleId))
				{
					// already seen this shipment schedule
					continue;
				}

				huShipmentScheduleBL.updateHUDeliveryQuantities(shipmentSchedule);

				// save the shipment schedule using current transaction
				InterfaceWrapperHelper.save(shipmentSchedule, processorCtx.getTrxName());
			}
			Loggables.get().addLog("Shipment {0} was created;\nIShipmentScheduleWithHUs: {1}", currentShipment, currentCandidates);
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
	public void process(final IShipmentScheduleWithHU item) throws Exception
	{
		createUpdateShipmentLine(item);
		lastItem = item;
	}

	@Override
	public InOutGenerateResult getResult()
	{
		return result;
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU setProcessShipmentsDocAction(final String docAction)
	{
		processShipmentDocAction = docAction;
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
	public String toString()
	{
		return "InOutProducerFromShipmentSchedule [result=" + result
				+ ", shipmentScheduleKeyBuilder=" + shipmentScheduleKeyBuilder + ", huShipmentScheduleKeyBuilder=" + huShipmentScheduleKeyBuilder
				+ ", processorCtx=" + processorCtx + ", currentShipment=" + currentShipment
				+ ", currentShipmentLineBuilder=" + currentShipmentLineBuilder + ", currentCandidates=" + currentCandidates
				+ ", lastItem=" + lastItem + "]";
	}
}
