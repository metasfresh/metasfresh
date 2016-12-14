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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequestBuilder;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.util.HUTopLevel;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.logging.LogManager;

/**
 * Aggregates given {@link IShipmentScheduleWithHU}s (see {@link #add(IShipmentScheduleWithHU)}) and creates the shipment line (see {@link #createShipmentLine()}).
 *
 * @author tsa
 *
 */
/* package */class ShipmentLineBuilder
{
	//
	// Services
	private static final Logger logger = LogManager.getLogger(ShipmentLineBuilder.class);
	private final transient IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final transient IHUShipmentAssignmentBL huShipmentAssignmentBL = Services.get(IHUShipmentAssignmentBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	/**
	 * Shipment on which the new shipment line will be created
	 */
	private final I_M_InOut currentShipment;

	//
	// Shipment Line attributes
	private IHUContext huContext;
	private I_M_Product product = null;
	private int attributeSetInstanceId;
	private Object attributesAggregationKey = null;
	private int orderLineId = -1;
	private I_C_UOM uom = null;
	private BigDecimal qtyEntered = BigDecimal.ZERO;
	// note that we maintain both QtyEntered and MovementQty to avoid rounding/conversion issues
	private BigDecimal movementQty = BigDecimal.ZERO;

	//
	// Candidates which were added to this builder
	private final List<IShipmentScheduleWithHU> candidates = new ArrayList<>();
	private final List<IShipmentScheduleWithHU> candidatesRO = Collections.unmodifiableList(candidates);

	/** Loading Units(LUs)/Transport Units(TUs) to assign to the shipment line that will be created */
	private final Set<HUTopLevel> husToAssign = new TreeSet<>();
	private Set<Integer> alreadyAssignedTUIds = null; // to be configured by called

	//
	// Manual packing materials related:
	private boolean manualPackingMaterial = false;
	private int manualPackingMaterial_QtyTU = 0;
	private I_M_HU_PI_Item_Product manualPackingMaterial_huPIItemProduct = null;
	private final Set<Integer> manualPackingMaterial_seenShipmentScheduleIds = new HashSet<>();

	/**
	 *
	 * @param shipment shipment on which the new shipment line will be created
	 */
	public ShipmentLineBuilder(final I_M_InOut shipment)
	{
		super();
		Check.assumeNotNull(shipment, "shipment not null");
		currentShipment = shipment;
	}

	/**
	 *
	 * @return true if there are no candidates appended so far
	 */
	public boolean isEmpty()
	{
		return candidates.isEmpty();
	}

	public boolean canCreateShipmentLine()
	{
		if (isEmpty())
		{
			return false;
		}

		// Disallow creating shipment lines with negative or ZERO qty
		if (qtyEntered.signum() <= 0)
		{
			return false;
		}

		return true;
	}

	/**
	 * Checks if we can append given <code>candidate</code> to {@link #currentShipmentLine}.
	 *
	 * @param candidate
	 * @return true if we can append to current shipment line
	 */
	public boolean canAdd(final IShipmentScheduleWithHU candidate)
	{
		// If there were no candidates added so far, obviously we allow our first candidate
		if (isEmpty())
		{
			return true;
		}

		// Check: HU context
		if(!Objects.equals(huContext, candidate.getHUContext()))
		{
			return false;
		}

		// Check: same product
		if (product.getM_Product_ID() != candidate.getM_Product_ID())
		{
			return false;
		}

		// Check: ASI
		if(attributeSetInstanceId != candidate.getM_AttributeSetInstance_ID())
		{
			return false;
		}

		// Check: same attributes aggregation key
		if(!Objects.equals(this.attributesAggregationKey, candidate.getAttributesAggregationKey()))
		{
			return false;
		}

		// Check: same Order Line
		// NOTE: this is also EDI requirement
		if (orderLineId != candidate.getC_OrderLine_ID())
		{
			return false;
		}

		// Else, we can allow this candidate to be added here
		return true;
	}

	public void add(final IShipmentScheduleWithHU candidate)
	{
		Check.assumeNotNull(candidate, "candidate not null");
		if (isEmpty())
		{
			init(candidate);
		}
		append(candidate);
	}

	/**
	 * Initialize shipment line's fields (without Qtys and UOM)
	 *
	 * @param candidate
	 */
	private void init(final IShipmentScheduleWithHU candidate)
	{
		Check.assume(isEmpty(), "builder shall be empty");

		huContext = candidate.getHUContext();

		//
		// Product, ASI, UOM (retrieved from Shipment Schedule)
		product = candidate.getM_Product();
		attributeSetInstanceId = candidate.getM_AttributeSetInstance_ID();
		attributesAggregationKey = candidate.getAttributesAggregationKey();
		uom = shipmentScheduleBL.getC_UOM_For_ShipmentLine(candidate.getM_ShipmentSchedule());

		//
		// Order Line Link (retrieved from current Shipment)
		orderLineId = candidate.getC_OrderLine_ID();
	}

	private void append(final IShipmentScheduleWithHU candidate)
	{
		Check.assume(canAdd(candidate), "candidate {} can be added to shipment line builder", candidate);

		logger.trace("Adding candidate to {}: {}", this, candidate);

		final BigDecimal qtyToAdd = candidate.getQtyPicked();
		if (qtyToAdd.signum() <= 0)
		{
			Loggables.get().addLog("IShipmentScheduleWithHU {0} has QtyPicked={1}", candidate, qtyToAdd);
		}
		movementQty = movementQty.add(qtyToAdd); // NOTE: we assume qtyToAdd is in stocking UOM

		final I_C_UOM qtyToAddUOM = candidate.getQtyPickedUOM(); // OK: shall be the stocking UOM, i.e. the UOM of QtyPicked

		// Convert qtyToAdd (from candidate) to shipment line's UOM
		final BigDecimal qtyToAddConverted = uomConversionBL.convertQty(product,
				qtyToAdd, // Qty
				qtyToAddUOM, // From UOM
				uom // To UOM
				);

		qtyEntered = qtyEntered.add(qtyToAddConverted);

		//
		// Enqueue candidate's LU/TU to list of HUs to be assigned
		appendHUsFromCandidate(candidate);

		//
		// Add current candidate to the list of candidates that will compose the generated shipment line
		candidates.add(candidate);
	}

	/**
	 * Gets LU or TU (if LU was not found) from candidate and append it to {@link #husToAssign} set.
	 *
	 * When we will generate the shipment line, we will link those HUs to the generated shipment line (see {@link #createShipmentLine()}).
	 *
	 * @param candidate
	 */
	private void appendHUsFromCandidate(final IShipmentScheduleWithHU candidate)
	{
		I_M_HU topLevelHU = null;

		//
		// Append LU if any
		final I_M_HU luHU = candidate.getM_LU_HU();
		if (luHU != null && luHU.getM_HU_ID() > 0)
		{
			topLevelHU = luHU;
		}

		//
		// No LU found, append TU if any (if there was no LU)
		final I_M_HU tuHU = candidate.getM_TU_HU();
		if (topLevelHU == null &&
				tuHU != null && tuHU.getM_HU_ID() > 0)
		{
			// Guard: our TU shall be a top level HU
			if (!handlingUnitsBL.isTopLevel(tuHU))
			{
				throw new HUException("Candidate's TU is not a top level and there was no LU: " + candidate);
			}

			topLevelHU = tuHU;
		}

		if (luHU != null || tuHU != null)
		{
			final I_M_HU vhu = candidate.getVHU();
			final HUTopLevel huToAssign = new HUTopLevel(topLevelHU, luHU, tuHU, vhu);
			husToAssign.add(huToAssign);
		}

		//
		// Sum up QtyTU from shipment schedule (in case we are no generating HUs)
		// Make sure we are not considering a shipment schedule more than once.
		final int shipmentScheduleId = candidate.getM_ShipmentSchedule().getM_ShipmentSchedule_ID();
		if (manualPackingMaterial_seenShipmentScheduleIds.add(shipmentScheduleId))
		{
			final int qtyTU = candidate.getM_ShipmentSchedule().getQtyToDeliver_TU().intValue();
			manualPackingMaterial_QtyTU = manualPackingMaterial_QtyTU + qtyTU;
			manualPackingMaterial_huPIItemProduct = Services.get(IHUShipmentScheduleBL.class).getM_HU_PI_Item_Product(candidate.getM_ShipmentSchedule());
		}
	}

	public I_M_InOutLine createShipmentLine()
	{
		if (isEmpty())
		{
			throw new AdempiereException("Cannot create shipment line when no candidates were added");
		}

		final I_M_InOutLine shipmentLine = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class, currentShipment);
		shipmentLine.setAD_Org_ID(currentShipment.getAD_Org_ID());
		shipmentLine.setM_InOut(currentShipment);

		//
		// Line Warehouse & Locator (retrieved from current Shipment)
		{
			final I_M_Warehouse warehouse = currentShipment.getM_Warehouse();
			final I_M_Locator locator = warehouseBL.getDefaultLocator(warehouse);
			shipmentLine.setM_Locator(locator);
		}

		//
		// Product & ASI (retrieved from Shipment Schedule)
		shipmentLine.setM_Product(product);

		// 08811
		// Copy ASI instead of copying its ID
		if (attributeSetInstanceId > 0)
		{
			final I_M_AttributeSetInstance oldASI = InterfaceWrapperHelper.create(Env.getCtx(), attributeSetInstanceId, I_M_AttributeSetInstance.class, ITrx.TRXNAME_None);
			final I_M_AttributeSetInstance newASI = Services.get(IAttributeDAO.class).copy(oldASI);
			shipmentLine.setM_AttributeSetInstance(newASI);
		}
		else
		{
			final I_M_AttributeSetInstance newASI = Services.get(IAttributeSetInstanceBL.class).createASI(product);
			shipmentLine.setM_AttributeSetInstance(newASI);
		}

		//
		// Order Line Link (retrieved from current Shipment)
		shipmentLine.setC_OrderLine_ID(orderLineId);

		//
		// Qty Entered and UOM
		shipmentLine.setC_UOM(uom);
		shipmentLine.setQtyEntered(qtyEntered);

		//
		// Set MovementQty by converting QtyEntered to stocking UOM
		{
			// Don't do conversions. The movementQty which we summed up already contains exactly what we need (in the stocking-UOM!)
			// final I_C_UOM storageUOM = productBL.getStockingUOM(product);
			// final BigDecimal movementQty = uomConversionBL.convertQty(product,
			// qtyEntered,
			// uom, // From UOM
			// storageUOM // To UOM
			// );
			shipmentLine.setQtyCU_Calculated(movementQty);
			shipmentLine.setMovementQty(movementQty);
		}

		//
		// Update packing materials info
		shipmentLine.setIsManualPackingMaterial(manualPackingMaterial);
		shipmentLine.setQtyTU_Override(BigDecimal.valueOf(manualPackingMaterial_QtyTU));
		shipmentLine.setM_HU_PI_Item_Product_Override(manualPackingMaterial_huPIItemProduct);
		shipmentLine.setM_HU_PI_Item_Product_Calculated(manualPackingMaterial_huPIItemProduct); // FIXME: figure out how to actually fetch it from collected HUs, but for now it's fine because it
																								// covers most of the cases

		//
		// Save Shipment Line
		InterfaceWrapperHelper.save(shipmentLine);

		//
		// Notify candidates that we have a shipment line
		for (final IShipmentScheduleWithHU candidate : getCandidates())
		{
			candidate.setM_InOutLine(shipmentLine);
		}

		//
		// Create HU Assignments
		createShipmentLineHUAssignments(shipmentLine);

		return shipmentLine;
	}

	/**
	 * Assign collected LU/TU pairs.
	 *
	 * @param shipmentLine
	 */
	private final void createShipmentLineHUAssignments(final I_M_InOutLine shipmentLine)
	{
		//
		// Assign Handling Units to shipment line
		boolean haveHUAssigments = false;
		for (final HUTopLevel huToAssign : husToAssign)
		{
			// transfer packing materials only if this TU was not already assigned to other document line (partial TUs case)
			final boolean isTransferPackingMaterials = alreadyAssignedTUIds.add(huToAssign.getM_TU_HU_ID());

			huShipmentAssignmentBL.assignHU(shipmentLine, huToAssign, isTransferPackingMaterials);
			transferAttributesToShipmentLine(shipmentLine, huToAssign.getM_HU_TopLevel());
			haveHUAssigments = true;
		}

		// Guard: while generating shipment line from candidates, we shall have HUs for them
		if (!haveHUAssigments && !manualPackingMaterial)
		{
			throw new HUException("No HUs to assign. This could be a possible issue."
					+ "\n @M_InOutLine_ID@: " + shipmentLine
					+ "\n @M_HU_ID@: " + husToAssign);
		}
	}

	private final void transferAttributesToShipmentLine(final I_M_InOutLine shipmentLine, final I_M_HU hu)
	{
		//
		// Transfer attributes from HU to receipt line's ASI
		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor(huContext);
		executor.run(new IHUContextProcessor()
		{
			@Override
			public IMutableAllocationResult process(final IHUContext huContext)
			{
				final IHUTransactionAttributeBuilder trxAttributesBuilder = executor.getTrxAttributesBuilder();
				final IAttributeStorageFactory attributeStorageFactory = trxAttributesBuilder.getAttributeStorageFactory();
				final IAttributeStorage huAttributeStorageFrom = attributeStorageFactory.getAttributeStorage(hu);
				final IAttributeStorage receiptLineAttributeStorageTo = attributeStorageFactory.getAttributeStorage(shipmentLine);

				final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
				final IHUStorage huStorageFrom = storageFactory.getStorage(hu);

				final IHUAttributeTransferRequestBuilder requestBuilder = new HUAttributeTransferRequestBuilder(huContext)
						.setProduct(product)
						.setQty(shipmentLine.getMovementQty())
						.setUOM(product.getC_UOM())
						.setAttributeStorageFrom(huAttributeStorageFrom)
						.setAttributeStorageTo(receiptLineAttributeStorageTo)
						.setHUStorageFrom(huStorageFrom);

				final IHUAttributeTransferRequest request = requestBuilder.create();
				trxAttributesBuilder.transferAttributes(request);

				return NULL_RESULT;
			}
		});
	}

	public List<IShipmentScheduleWithHU> getCandidates()
	{
		return candidatesRO;
	}

	/**
	 * Sets if shipment line shall be flagged as manual packing materials
	 *
	 * @param manualPackingMaterial
	 * @see I_M_InOutLine#setIsManualPackingMaterial(boolean)
	 */
	public void setManualPackingMaterial(final boolean manualPackingMaterial)
	{
		this.manualPackingMaterial = manualPackingMaterial;
	}

	/**
	 * Sets a online {@link Set} which contains the list of TU Ids which were already assigned.
	 *
	 * This set will be updated by this builder when TUs are assigned.
	 *
	 * When this shipment line will try to assign an TU which is on this list, it will set the {@link I_M_HU_Assignment#setIsTransferPackingMaterials(boolean)} to <code>false</code>.
	 *
	 * @param alreadyAssignedTUIds
	 */
	public void setAlreadyAssignedTUIds(final Set<Integer> alreadyAssignedTUIds)
	{
		this.alreadyAssignedTUIds = alreadyAssignedTUIds;
	}
}
