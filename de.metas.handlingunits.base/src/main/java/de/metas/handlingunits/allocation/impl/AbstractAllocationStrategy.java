package de.metas.handlingunits.allocation.impl;

import java.math.BigDecimal;

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

import java.util.Arrays;
import java.util.Collections;

import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTransactionAttribute;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.IAllocationStrategyFactory;
import de.metas.handlingunits.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.logging.LogManager;

public abstract class AbstractAllocationStrategy implements IAllocationStrategy
{
	// Services
	protected final transient Logger logger = LogManager.getLogger(getClass());
	protected final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	/** NOTE: keep it private and use {@link #getHandlingUnitsDAO()} to get it */
	private IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	/** true if outbound transaction (i.e. deallocation); false if inbound transaction (i.e. allocation) */
	private final boolean outTrx;
	private IAllocationStrategyFactory factory;

	/**
	 *
	 * @param outTrx <code>true</code> if outbound transaction (i.e. deallocation); false if inbound transaction (i.e. allocation)
	 */
	public AbstractAllocationStrategy(final boolean outTrx)
	{
		this.outTrx = outTrx;
	}

	/**
	 * Set the factory that shall be used when a new {@link IAllocationStrategy} instance needs to be created from this instance.
	 *
	 * NOTE: don't call it directly.
	 *
	 * @param factory
	 */
	/* package */ final void setAllocationStrategyFactory(final IAllocationStrategyFactory factory)
	{
		this.factory = factory;
		handlingUnitsDAO = factory.getHandlingUnitsDAO();
	}

	protected final IHUStorageFactory getHUStorageFactory(final IAllocationRequest request)
	{
		final IHUContext huContext = request.getHUContext();
		return huContext.getHUStorageFactory();
	}

	protected final IAllocationStrategyFactory getAllocationStrategyFactory()
	{
		return factory;
	}

	protected final IHandlingUnitsDAO getHandlingUnitsDAO()
	{
		return handlingUnitsDAO;
	}

	/**
	 * Return an item storage to be used to derive the "actual" allocation request.
	 * See {@link #createActualRequest(I_M_HU_Item, IAllocationRequest)}.
	 * This method uses {@link IHUStorageFactory#getStorage(I_M_HU_Item)}, but can be overridden.
	 * 
	 * @param item
	 * @param request
	 * @return
	 */
	protected IHUItemStorage getHUItemStorage(final I_M_HU_Item item, final IAllocationRequest request)
	{
		final IHUItemStorage storage = getHUStorageFactory(request).getStorage(item);
		return storage;
	}

	/**
	 * Creates the actual request for allocation/deallocation that shall be used on given HU Item.
	 * The actual request is computed based on item's current capacity and load.
	 *
	 * @param item
	 * @param request
	 * @return actual request to use used for allocation/deallocation be the {@link #allocateOnVirtualMaterialItem(I_M_HU_Item, IAllocationRequest)}.
	 */
	protected final IAllocationRequest createActualRequest(final I_M_HU_Item item, final IAllocationRequest request)
	{
		final IHUItemStorage storage = getHUItemStorage(item, request);

		final IAllocationRequest requestActual;

		// Create Actual De-Allocation Request
		if (outTrx)
		{
			requestActual = storage.requestQtyToDeallocate(request);
		}
		// Create Actual Allocation Request
		else
		{
			requestActual = storage.requestQtyToAllocate(request);
		}

		return requestActual;
	}

	protected final IAllocationResult allocateOnVirtualMaterialItem(final I_M_HU_Item vhuItem, final IAllocationRequest request)
	{
		//
		// Create Actual Allocation Request depending on vhuItem's (remaining) capacity
		final IAllocationRequest requestActual = createActualRequest(vhuItem, request);
		if (requestActual.isZeroQty())
		{
			return AllocationUtils.nullResult();
		}

		final boolean itemOfAggregateHU = handlingUnitsBL.isAggregateHU(vhuItem.getM_HU());
		final BigDecimal qtyToAllocate = request.getQty();
		final BigDecimal qtyAllocated = requestActual.getQty();

		//
		// Create HU Transaction Candidate
		final IHUTransaction trx = createHUTransaction(requestActual, vhuItem);

		//
		// Create Allocation Result
		final IAllocationResult result = AllocationUtils.createQtyAllocationResult(
				qtyToAllocate,
				qtyAllocated,
				Arrays.asList(trx), // trxs
				Collections.<IHUTransactionAttribute> emptyList() // attributeTrxs
		);
		return result;
	}

	/**
	 * Create a new virtual HU and link it to <code>parentItem</code>.
	 *
	 * @param huContext
	 * @param request
	 * @param parentItem
	 * @return newly created Virtual Handling Unit (VHU)
	 */
	protected final I_M_HU createVHU(final IHUContext huContext, final IAllocationRequest request, final I_M_HU_Item parentItem)
	{
		final I_M_HU_PI vhuPI = handlingUnitsDAO.retrieveVirtualPI(huContext.getCtx());

		final IHUBuilder vhuBuilder = AllocationUtils.createHUBuilder(request);

		vhuBuilder.setM_HU_Item_Parent(parentItem);

		final I_M_HU vhu = vhuBuilder.create(vhuPI);
		return vhu;
	}

	/**
	 * Create transaction candidate which when it will be processed it will change the storage quantity.
	 *
	 * @param requestActual request (used to get Product, UOM, Date, Qty etc)
	 * @param vhuItem Virtual HU item on which we actually allocated/deallocated
	 * @return transaction candidate
	 */
	private final IHUTransaction createHUTransaction(final IAllocationRequest requestActual,
			final I_M_HU_Item vhuItem)
	{
		//
		// Find out first HU Item which is not pure virtual
		I_M_HU_Item itemFirstNotPureVirtual = vhuItem;
		while (itemFirstNotPureVirtual != null && handlingUnitsBL.isPureVirtual(itemFirstNotPureVirtual))
		{
			final I_M_HU parentHU = itemFirstNotPureVirtual.getM_HU();
			itemFirstNotPureVirtual = getHandlingUnitsDAO().retrieveParentItem(parentHU);
		}
		Check.assumeNotNull(itemFirstNotPureVirtual, "itemFirstNotPureVirtual not null"); // shall not happen

		final Object referencedModel = AllocationUtils.getReferencedModel(requestActual);
		final Quantity qtyTrx = AllocationUtils.getQuantity(requestActual, outTrx);

		final IHUTransaction trx = new HUTransaction(
				referencedModel,
				itemFirstNotPureVirtual, // HU Item
				vhuItem, // VHU Item
				requestActual.getProduct(), // Product
				qtyTrx, // Qty/UOM
				requestActual.getDate() // Date
		);

		return trx;
	}

	/**
	 *
	 * @return true if outbound transaction (i.e. deallocation); false if inbound transaction (i.e. allocation)
	 */
	protected final boolean isOutTrx()
	{
		return outTrx;
	}
}
