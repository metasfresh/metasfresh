package de.metas.handlingunits.ddorder.api.impl;

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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderMovementBuilder;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;

import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.storage.IHUProductStorage;

/**
 * Allocate {@link IHUProductStorage}s to a set of given {@link I_DD_OrderLine_Alternative}s.
 *
 * @author al
 *
 */
/* package */class DDOrderLinesAllocator
{
	// Services
	private static final transient Logger logger = LogManager.getLogger(DDOrderLinesAllocator.class);
	private final transient IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
	private final transient IHUDDOrderBL huDDOrderBL = Services.get(IHUDDOrderBL.class);
	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	// Parameters
	private final Date movementDate = SystemTime.asDayTimestamp();

	// State
	private List<DDOrderLineToAllocate> _ddOrderLinesToAllocate;
	private final Map<Integer, IDDOrderMovementBuilder> ddOrderId2ShipmentMovementBuilder = new HashMap<>();
	private final Map<Integer, IDDOrderMovementBuilder> ddOrderId2ReceiptMovementBuilder = new HashMap<>();
	private final Set<Integer> huIdsWithPackingMaterialsTransferedShipment = new HashSet<>();
	private final Set<Integer> huIdsWithPackingMaterialsTransferedReceipt = new HashSet<>();

	public DDOrderLinesAllocator()
	{
		super();
	}

	public void setDDOrderLines(final Collection<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt)
	{
		Check.assumeNotEmpty(ddOrderLinesOrAlt, "ddOrderLines not empty");

		_ddOrderLinesToAllocate = new ArrayList<DDOrderLineToAllocate>();
		for (final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt : ddOrderLinesOrAlt)
		{
			final DDOrderLineToAllocate ddOrderLineToAllocate = new DDOrderLineToAllocate(ddOrderLineOrAlt);
			_ddOrderLinesToAllocate.add(ddOrderLineToAllocate);
		}
	}

	private List<DDOrderLineToAllocate> getDDOrderLines()
	{
		Check.assumeNotNull(_ddOrderLinesToAllocate, "_ddOrderLinesToAllocate shall be set");
		return _ddOrderLinesToAllocate;
	}

	/**
	 * Process allocations and create material movement documents
	 */
	public void process()
	{
		//
		// Clean previous state
		ddOrderId2ShipmentMovementBuilder.clear();
		ddOrderId2ReceiptMovementBuilder.clear();
		huIdsWithPackingMaterialsTransferedShipment.clear();
		huIdsWithPackingMaterialsTransferedReceipt.clear();

		//
		// Add lines to allocate to shipment and receipt builders
		for (final DDOrderLineToAllocate ddOrderLineToAllocate : getDDOrderLines())
		{
			process(ddOrderLineToAllocate);
		}

		//
		// After creating and loading movement builders, process shipments first
		processMovementBuilders(ddOrderId2ShipmentMovementBuilder);

		//
		// After creating and loading movement builders, process receipts second
		processMovementBuilders(ddOrderId2ReceiptMovementBuilder);
	}

	private void processMovementBuilders(final Map<Integer, IDDOrderMovementBuilder> builders)
	{
		for (final IDDOrderMovementBuilder movementBuilder : builders.values())
		{
			movementBuilder.process();
		}
		builders.clear();
	}

	private final void process(final DDOrderLineToAllocate ddOrderLineToAllocate)
	{
		// TODO: make sure we are running in transaction !!!

		//
		// Get/create the movement builder (one for each DD_Order)
		final I_DD_OrderLine ddOrderLine = ddOrderLineToAllocate.getDD_OrderLine();
		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();

		final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = ddOrderLineToAllocate.getDD_OrderLine_Or_Alternative();
		final Quantity movementQty = ddOrderLineToAllocate.getQtyToShipScheduled();
		final List<I_M_HU> hus = new ArrayList<>(ddOrderLineToAllocate.getM_HUs());

		//
		// If there was nothing cumulated there is no point to generate any movement
		if (movementQty.signum() == 0 && hus.isEmpty())
		{
			return;
		}

		//
		// Make sure DD Order is completed
		ddOrderBL.completeDDOrderIfNeeded(ddOrderLine);

		//
		// Make sure given HUs are no longer assigned to this DD Order Line
		huDDOrderBL.unassignHUs(ddOrderLine, hus);

		//
		// Generate movement (shipment)
		{
			final IDDOrderMovementBuilder movementBuilder = getMovementBuilder(ddOrderId2ShipmentMovementBuilder, ddOrder);
			final I_M_MovementLine movementLine = movementBuilder.addMovementLineShipment(ddOrderLineOrAlt, movementQty.getQty(), movementQty.getUOM());
			assignHUsToMovementLine(huIdsWithPackingMaterialsTransferedShipment, hus, movementLine);
		}

		//
		// Generate movement (receipt)
		{
			final IDDOrderMovementBuilder movementBuilder = getMovementBuilder(ddOrderId2ReceiptMovementBuilder, ddOrder);
			final I_M_MovementLine movementLine = movementBuilder.addMovementLineReceipt(ddOrderLineOrAlt, movementQty.getQty(), movementQty.getUOM());
			assignHUsToMovementLine(huIdsWithPackingMaterialsTransferedReceipt, hus, movementLine);
		}
	}

	private void assignHUsToMovementLine(final Set<Integer> huIdsWithPackingMaterialsTransfered, final List<I_M_HU> hus, final I_M_MovementLine movementLine)
	{
		if (hus == null || hus.isEmpty())
		{
			return;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(movementLine);
		for (final I_M_HU hu : hus)
		{
			final int huId = hu.getM_HU_ID();
			final boolean isTransferPackingMaterials;
			if (huIdsWithPackingMaterialsTransfered.add(huId))
			{
				isTransferPackingMaterials = true;
			}
			else
			{
				isTransferPackingMaterials = false;
			}
			huMovementBL.assignHU(movementLine, hu, isTransferPackingMaterials, trxName);
		}
	}

	private final IDDOrderMovementBuilder getMovementBuilder(final Map<Integer, IDDOrderMovementBuilder> ddOrderId2MovementBuilder, final I_DD_Order ddOrder)
	{
		final int ddOrderId = ddOrder.getDD_Order_ID();
		IDDOrderMovementBuilder movementBuilder = ddOrderId2MovementBuilder.get(ddOrderId);
		if (movementBuilder == null)
		{
			movementBuilder = ddOrderBL.createMovementBuilder();
			movementBuilder.setDD_Order(ddOrder);
			movementBuilder.setMovementDate(movementDate);
			ddOrderId2MovementBuilder.put(ddOrderId, movementBuilder);
		}

		return movementBuilder;
	}

	/**
	 * Create allocation for given <code>huProductStorage</code>
	 *
	 * @param huProductStorage
	 */
	public void allocate(final IHUProductStorage huProductStorage)
	{
		Check.assumeNotNull(huProductStorage, "huProductStorage not null");

		final I_M_Product product = huProductStorage.getM_Product();
		final List<DDOrderLineToAllocate> ddOrderLinesToAllocate = getDDOrderLinesForProduct(product);

		// No DD Order Lines were found for our Product
		// Shall not happen, but ignore it for now.
		if (ddOrderLinesToAllocate.isEmpty())
		{
			final HUException ex = new HUException("No DD Order Lines where found for our product"
					+ "\n @M_Product_ID@: " + product
					+ "\n HUProductStorage: " + huProductStorage);
			logger.warn(ex.getLocalizedMessage(), ex);
			return;
		}

		final I_M_HU hu = huProductStorage.getM_HU();
		Quantity qtyToAllocateRemaining = new Quantity(huProductStorage.getQty(), huProductStorage.getC_UOM());

		DDOrderLineToAllocate lastDDOrderLineToAllocate = null;
		for (final DDOrderLineToAllocate ddOrderLineToAllocate : ddOrderLinesToAllocate)
		{
			lastDDOrderLineToAllocate = ddOrderLineToAllocate;

			//
			// Check if there is more to allocate
			if (qtyToAllocateRemaining.isZero())
			{
				break;
			}

			//
			// Check if this line is fully shipped
			if (ddOrderLineToAllocate.isFullyShipped())
			{
				continue;
			}

			//
			// Allocate
			final boolean forceAllocation = false;
			final Quantity qtyShipped = ddOrderLineToAllocate.addQtyShipped(hu, qtyToAllocateRemaining, forceAllocation);

			//
			// Decrease our remaining quantity (from HU Product Storage) that we a struggling to allocate to existing DD Order Lines/Alternatives
			qtyToAllocateRemaining = qtyToAllocateRemaining.subtract(qtyShipped);
		}

		//
		// Allocate entire remaining qty on the last line
		if (!qtyToAllocateRemaining.isZero() && lastDDOrderLineToAllocate != null)
		{
			final boolean forceAllocation = true;
			lastDDOrderLineToAllocate.addQtyShipped(hu, qtyToAllocateRemaining, forceAllocation);
		}
	}

	private List<DDOrderLineToAllocate> getDDOrderLinesForProduct(final I_M_Product product)
	{
		final List<DDOrderLineToAllocate> linesMatchingProduct = new ArrayList<>();
		for (final DDOrderLineToAllocate ddOrderLineToAllocate : getDDOrderLines())
		{
			final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = ddOrderLineToAllocate.getDD_OrderLine_Or_Alternative();
			if (ddOrderLineOrAlt.getM_Product_ID() != product.getM_Product_ID())
			{
				continue;
			}
			linesMatchingProduct.add(ddOrderLineToAllocate);
		}
		return linesMatchingProduct;
	}
}
