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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet.Builder;
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
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequestBuilder;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.util.HUTopLevel;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.logging.LogManager;
import lombok.NonNull;

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

	private Object attributesAggregationKey = null;
	private int orderLineId = -1;
	private I_C_UOM uom = null;

	private BigDecimal qtyEntered = BigDecimal.ZERO;
	// note that we maintain both QtyEntered and MovementQty to avoid rounding/conversion issues
	private BigDecimal movementQty = BigDecimal.ZERO;

	/** Candidates which were added to this builder */
	private final List<ShipmentScheduleWithHU> candidates = new ArrayList<>();

	/** Loading Units(LUs)/Transport Units(TUs) to assign to the shipment line that will be created */
	private final Set<HUTopLevel> husToAssign = new TreeSet<>();
	private Set<Integer> alreadyAssignedTUIds = null; // to be configured by called

	//
	// Manual packing materials related:
	private boolean manualPackingMaterial = false;

	private final TreeSet<I_M_HU_PI_Item_Product> packingMaterial_huPIItemProduct = new TreeSet<>(Comparator.comparing(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID));

	final TreeSet<IAttributeValue> attributeValues = //
			new TreeSet<>(Comparator.comparing(av -> av.getM_Attribute().getM_Attribute_ID()));

	/**
	 *
	 * @param shipment shipment on which the new shipment line will be created
	 */
	public ShipmentLineBuilder(@NonNull final I_M_InOut shipment)
	{
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
	public boolean canAdd(final ShipmentScheduleWithHU candidate)
	{
		// If there were no candidates added so far, obviously we allow our first candidate
		if (isEmpty())
		{
			return true;
		}

		// Check: HU context
		if (!Objects.equals(huContext, candidate.getHUContext()))
		{
			return false;
		}

		// Check: same product
		if (product.getM_Product_ID() != candidate.getM_Product_ID())
		{
			return false;
		}

		// Check: same attributes aggregation key
		if (!Objects.equals(this.attributesAggregationKey, candidate.getAttributesAggregationKey()))
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

	public void add(@NonNull final ShipmentScheduleWithHU candidate)
	{
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
	private void init(@NonNull final ShipmentScheduleWithHU candidate)
	{
		Check.assume(isEmpty(), "builder shall be empty");

		huContext = candidate.getHUContext();

		//
		// Product, ASI, UOM (retrieved from Shipment Schedule)
		product = candidate.getM_Product();
		attributeValues.addAll(candidate.getAttributeValues());
		attributesAggregationKey = candidate.getAttributesAggregationKey();
		uom = shipmentScheduleBL.getUomOfProduct(candidate.getM_ShipmentSchedule());

		//
		// Order Line Link (retrieved from current Shipment)
		orderLineId = candidate.getC_OrderLine_ID();
	}

	private void append(@NonNull final ShipmentScheduleWithHU candidate)
	{
		Check.assume(canAdd(candidate), "candidate {} can be added to shipment line builder", candidate);
		attributeValues.addAll(candidate.getAttributeValues()); // because of canAdd()==true, we may assume that it's all fine

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

		packingMaterial_huPIItemProduct.add(candidate.retrieveM_HU_PI_Item_ProductOrNull());

		//
		// Add current candidate to the list of candidates that will compose the generated shipment line
		candidates.add(candidate);
	}

	/**
	 * Gets LU or TU (if LU was not found) from candidate and append it to {@link #husToAssign} set.
	 *
	 * When we will generate the shipment line, we will link those HUs to the generated shipment line (see {@link #createShipmentLine()}).
	 */
	private void appendHUsFromCandidate(@NonNull final ShipmentScheduleWithHU candidate)
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

		final I_M_AttributeSetInstance newASI;
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		if (attributeValues.isEmpty())
		{
			newASI = attributeSetInstanceBL.createASI(product);
		}
		else
		{
			final Builder attributeSetBuilder = ImmutableAttributeSet.builder();
			for (final IAttributeValue attributeValue : attributeValues)
			{
				attributeSetBuilder.attributeValue(
						attributeValue.getM_Attribute(),
						attributeValue.getValue());
			}
			newASI = attributeSetInstanceBL.createASIFromAttributeSet(attributeSetBuilder.build());
		}
		shipmentLine.setM_AttributeSetInstance(newASI);

		//
		// Order Line Link (retrieved from current Shipment)
		shipmentLine.setC_OrderLine_ID(orderLineId);

		//
		// Qty Entered and UOM
		shipmentLine.setC_UOM(uom);
		shipmentLine.setQtyEntered(qtyEntered);

		// Set MovementQty
		{
			// Don't do conversions. The movementQty which we summed up already contains exactly what we need (in the stocking-UOM!)
			shipmentLine.setQtyCU_Calculated(movementQty);
			shipmentLine.setMovementQty(movementQty);
		}

		// Update packing materials info, if there is "one" info
		shipmentLine.setIsManualPackingMaterial(manualPackingMaterial);

		// https://github.com/metasfresh/metasfresh/issues/3503
		if (packingMaterial_huPIItemProduct != null && packingMaterial_huPIItemProduct.size() == 1)
		{
			final I_M_HU_PI_Item_Product piipForShipmentLine = packingMaterial_huPIItemProduct.iterator().next();
			shipmentLine.setM_HU_PI_Item_Product_Override(piipForShipmentLine); // this field is currently displayed in the swing client, so we set it, even if it's redundant
			shipmentLine.setM_HU_PI_Item_Product_Calculated(piipForShipmentLine);
		}
		else
		{
			Loggables.get()
					.withLogger(logger, Level.INFO)
					.addLog("Not setting the shipment line's M_HU_PI_Item_Product, because the assigned HUs have different ones; huPIItemProducts={}",
							packingMaterial_huPIItemProduct);
		}
		//
		// Save Shipment Line
		InterfaceWrapperHelper.save(shipmentLine);

		//
		// Notify candidates that we have a shipment line
		for (final ShipmentScheduleWithHU candidate : getCandidates())
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
		executor.run((IHUContextProcessor)huContext -> {

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

			return IHUContextProcessor.NULL_RESULT;
		});
	}

	public List<ShipmentScheduleWithHU> getCandidates()
	{
		return ImmutableList.copyOf(candidates);
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
