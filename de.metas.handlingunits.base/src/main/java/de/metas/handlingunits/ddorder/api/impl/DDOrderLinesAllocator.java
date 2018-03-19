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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.api.IDDOrderMovementBuilder;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/**
 * Allocate {@link IHUProductStorage}s to a set of given {@link I_DD_OrderLine_Alternative}s and generate material movements.
 *
 * @author al
 *
 */
public class DDOrderLinesAllocator
{
	public static DDOrderLinesAllocator newInstance()
	{
		return new DDOrderLinesAllocator();
	}

	// Services
	private static final transient Logger logger = LogManager.getLogger(DDOrderLinesAllocator.class);
	private final transient IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);
	private final transient IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
	private final transient IHUDDOrderBL huDDOrderBL = Services.get(IHUDDOrderBL.class);
	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final transient IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	// Parameters
	private final Date movementDate = SystemTime.asDayTimestamp();
	private int locatorToIdOverride = -1;
	private boolean failIfCannotAllocate = false; // default=false for backward compatibility
	private boolean doDirectMovements = false;

	// State
	private ImmutableList<DDOrderLineToAllocate> _ddOrderLinesToAllocate;
	private final Map<Integer, IDDOrderMovementBuilder> ddOrderId2ShipmentMovementBuilder = new HashMap<>();
	private final Map<Integer, IDDOrderMovementBuilder> ddOrderId2ReceiptMovementBuilder = new HashMap<>();
	private final Set<Integer> huIdsWithPackingMaterialsTransferedShipment = new HashSet<>();
	private final Set<Integer> huIdsWithPackingMaterialsTransferedReceipt = new HashSet<>();

	private DDOrderLinesAllocator()
	{
	}

	public DDOrderLinesAllocator setDDOrderLine(@NonNull final I_DD_OrderLine ddOrderLine)
	{
		setDDOrderLines(ImmutableList.of(ddOrderLine));
		return this;
	}

	public DDOrderLinesAllocator setDDOrderLines(final Collection<I_DD_OrderLine> ddOrderLines)
	{
		Check.assumeNotEmpty(ddOrderLines, "ddOrderLines not empty");

		// Sort distribution order lines by date promised (lowest date first)
		final List<I_DD_OrderLine> ddOrderLinesSorted = new ArrayList<>(ddOrderLines);
		Collections.sort(ddOrderLinesSorted, Comparator.comparing(I_DD_OrderLine::getDatePromised));

		// Create list with alternatives; keep the sorting
		final List<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt = extractDD_OrderLine_Or_Alternatives(ddOrderLinesSorted);
		setDDOrderLinesOrAlternatives(ddOrderLinesOrAlt);

		return this;
	}

	private List<I_DD_OrderLine_Or_Alternative> extractDD_OrderLine_Or_Alternatives(final Collection<I_DD_OrderLine> ddOrderLines)
	{
		// Create list with alternatives; preserve the same sorting
		final List<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt = new ArrayList<>();
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			final I_DD_OrderLine_Or_Alternative ddOrderLineConv = InterfaceWrapperHelper.create(ddOrderLine, I_DD_OrderLine_Or_Alternative.class);
			ddOrderLinesOrAlt.add(ddOrderLineConv);

			final List<I_DD_OrderLine_Alternative> alternatives = ddOrderDAO.retrieveAllAlternatives(ddOrderLine);
			final List<I_DD_OrderLine_Or_Alternative> alternativesConv = InterfaceWrapperHelper.createList(alternatives, I_DD_OrderLine_Or_Alternative.class);
			ddOrderLinesOrAlt.addAll(alternativesConv);
		}

		return ddOrderLinesOrAlt;
	}

	public DDOrderLinesAllocator setDDOrderLinesOrAlternatives(final Collection<I_DD_OrderLine_Or_Alternative> ddOrderLinesOrAlt)
	{
		Check.assumeNotEmpty(ddOrderLinesOrAlt, "ddOrderLinesOrAlt not empty");

		_ddOrderLinesToAllocate = ddOrderLinesOrAlt.stream()
				.map(DDOrderLineToAllocate::new)
				.collect(ImmutableList.toImmutableList());

		return this;
	}

	private ImmutableList<DDOrderLineToAllocate> getDDOrderLines()
	{
		Check.assumeNotNull(_ddOrderLinesToAllocate, "_ddOrderLinesToAllocate shall be set");
		return _ddOrderLinesToAllocate;
	}

	private ImmutableList<DDOrderLineToAllocate> getDDOrderLinesForProduct(final int productId)
	{
		return getDDOrderLines()
				.stream()
				.filter(ddOrderLineToAllocate -> ddOrderLineToAllocate.getProductId() == productId)
				.collect(ImmutableList.toImmutableList());
	}

	public DDOrderLinesAllocator setLocatorToIdOverride(final int locatorToId)
	{
		locatorToIdOverride = locatorToId;
		return this;
	}

	public DDOrderLinesAllocator setFailIfCannotAllocate(final boolean failIfCannotAllocate)
	{
		this.failIfCannotAllocate = failIfCannotAllocate;
		return this;
	}

	/**
	 * @param doDirectMovements true if we shall do direct movements (e.g. skip the InTransit warehouse)
	 */
	public DDOrderLinesAllocator setDoDirectMovements(final boolean doDirectMovements)
	{
		this.doDirectMovements = doDirectMovements;
		return this;
	}

	/**
	 * Process allocations and create material movement documents
	 */
	public void processWithinOwnTrx()
	{
		Services.get(ITrxManager.class).run(localTrx -> process());
	}

	private void process()
	{
		// Clean previous state
		ddOrderId2ShipmentMovementBuilder.clear();
		ddOrderId2ReceiptMovementBuilder.clear();
		huIdsWithPackingMaterialsTransferedShipment.clear();
		huIdsWithPackingMaterialsTransferedReceipt.clear();

		//
		// Add lines to allocate to shipment and receipt builders
		getDDOrderLines().forEach(this::process);

		//
		// After creating and loading movement builders, process shipments first
		processMovementBuilders(ddOrderId2ShipmentMovementBuilder);

		//
		// After creating and loading movement builders, process receipts second
		processMovementBuilders(ddOrderId2ReceiptMovementBuilder);
	}

	private static void processMovementBuilders(final Map<Integer, IDDOrderMovementBuilder> builders)
	{
		for (final IDDOrderMovementBuilder movementBuilder : builders.values())
		{
			movementBuilder.process();
		}
		builders.clear();
	}

	private final void process(final DDOrderLineToAllocate ddOrderLineToAllocate)
	{
		// make sure we are running in transaction
		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		//
		// Get/create the movement builder (one for each DD_Order)
		final I_DD_OrderLine ddOrderLine = ddOrderLineToAllocate.getDD_OrderLine();
		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();

		final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = ddOrderLineToAllocate.getDD_OrderLine_Or_Alternative();
		final Quantity movementQty = ddOrderLineToAllocate.getQtyToShipScheduled();
		final List<I_M_HU> hus = new ArrayList<>(ddOrderLineToAllocate.getM_HUs());

		//
		// If there was nothing accumulated then there is no point to generate any movement
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

		if (doDirectMovements)
		{
			// Generate direct movement: source locator -> destination locator
			final IDDOrderMovementBuilder movementBuilder = getMovementBuilder(ddOrderId2ReceiptMovementBuilder, ddOrder);
			final I_M_MovementLine movementLine = movementBuilder.addMovementLineDirect(ddOrderLineOrAlt, movementQty.getQty(), movementQty.getUOM());
			assignHUsToMovementLine(huIdsWithPackingMaterialsTransferedReceipt, hus, movementLine);
		}
		else
		{
			// Generate movement (shipment): source locator -> in transit
			{
				final IDDOrderMovementBuilder movementBuilder = getMovementBuilder(ddOrderId2ShipmentMovementBuilder, ddOrder);
				final I_M_MovementLine movementLine = movementBuilder.addMovementLineShipment(ddOrderLineOrAlt, movementQty.getQty(), movementQty.getUOM());
				assignHUsToMovementLine(huIdsWithPackingMaterialsTransferedShipment, hus, movementLine);
			}

			// Generate movement (receipt): in transit -> destination locator
			{
				final IDDOrderMovementBuilder movementBuilder = getMovementBuilder(ddOrderId2ReceiptMovementBuilder, ddOrder);
				final I_M_MovementLine movementLine = movementBuilder.addMovementLineReceipt(ddOrderLineOrAlt, movementQty.getQty(), movementQty.getUOM());
				assignHUsToMovementLine(huIdsWithPackingMaterialsTransferedReceipt, hus, movementLine);
			}
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
		return ddOrderId2MovementBuilder.computeIfAbsent(ddOrder.getDD_Order_ID(), ddOrderId -> createMovementBuilder(ddOrder));
	}

	private IDDOrderMovementBuilder createMovementBuilder(final I_DD_Order ddOrder)
	{
		final IDDOrderMovementBuilder movementBuilder = ddOrderBL.createMovementBuilder();
		movementBuilder.setDD_Order(ddOrder);
		movementBuilder.setMovementDate(movementDate);
		movementBuilder.setLocatorToIdOverride(locatorToIdOverride);
		return movementBuilder;
	}

	public DDOrderLinesAllocator allocateHU(@NonNull final I_M_HU hu)
	{
		allocateHUs(ImmutableList.of(hu));
		return this;
	}

	public DDOrderLinesAllocator allocateHUs(final List<I_M_HU> hus)
	{
		huContextFactory.createMutableHUContext()
				.getHUStorageFactory()
				.streamHUProductStorages(hus)
				.forEach(this::allocateHUProductStorage);
		return this;
	}

	public DDOrderLinesAllocator allocateHUProductStorages(@NonNull final Iterable<IHUProductStorage> huProductStorages)
	{
		huProductStorages.forEach(this::allocateHUProductStorage);
		return this;
	}

	public DDOrderLinesAllocator allocateHUProductStorage(@NonNull final IHUProductStorage huProductStorage)
	{
		final int productId = huProductStorage.getM_Product_ID();
		final ImmutableList<DDOrderLineToAllocate> ddOrderLinesToAllocate = getDDOrderLinesForProduct(productId);

		// No DD Order Lines were found for our Product
		// Shall not happen, but ignore it for now.
		if (ddOrderLinesToAllocate.isEmpty())
		{
			new HUException("No DD Order Lines where found for our product"
					+ "\n @M_Product_ID@: " + huProductStorage.getM_Product()
					+ "\n HUProductStorage: " + huProductStorage)
							.throwOrLogWarning(failIfCannotAllocate, logger);
			return this;
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

		return this;
	}
}
