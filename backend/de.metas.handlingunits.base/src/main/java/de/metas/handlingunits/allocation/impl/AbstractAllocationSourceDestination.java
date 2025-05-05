package de.metas.handlingunits.allocation.impl;

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
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.logging.LogManager;
import de.metas.util.Check;

/**
 * Base class which provides both the features of {@link IAllocationSource} and {@link IAllocationDestination}.
 *
 */
public abstract class AbstractAllocationSourceDestination implements IAllocationSource, IAllocationDestination
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	private final IProductStorage storage;
	private final Object referenceModel;
	private final I_M_HU_Item huItem;

	/**
	 * Creates a new instance.
	 *
	 * @param storage the storage where the loading qtys are removed {{@link #unload(IAllocationRequest)} or added {@link #load(IAllocationRequest)}.
	 * @param huItem may be <code>null</code>
	 * @param referenceModel
	 */
	public AbstractAllocationSourceDestination(final IProductStorage storage,
			final I_M_HU_Item huItem,
			final Object referenceModel)
	{
		super();

		Check.assumeNotNull(storage, "storage not null");
		this.storage = storage;

		Check.assumeNotNull(referenceModel, "referenceModel not null");
		this.referenceModel = referenceModel;

		//
		// Sets huItem:
		if (huItem != null)
		{
			this.huItem = huItem;
		}
		else if (InterfaceWrapperHelper.isInstanceOf(referenceModel, I_M_HU_Item.class))
		{
			// FIXME: we need a clean way to specify M_HU_Item
			final AdempiereException ex = new AdempiereException("HU Item was not specified but the referenced model it's a HU Item. Assuming huItem=referencedModel");
			logger.warn(ex.getLocalizedMessage(), ex);

			this.huItem = InterfaceWrapperHelper.create(referenceModel, I_M_HU_Item.class);
		}
		else
		{
			this.huItem = null;
		}

	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "\nstorage=" + storage
				+ "\nreferenceModel=" + referenceModel
				+ "]";
	}

	@Override
	public IAllocationResult load(final IAllocationRequest request)
	{
		if (request.getQty().signum() == 0)
		{
			return AllocationUtils.nullResult();
		}

		final IAllocationRequest requestActual = storage.addQty(request);

		//
		// Create Allocation result
		final boolean outTrx = false;
		final IAllocationResult result = createAllocationResult(request, requestActual, outTrx);

		//
		// Return the result
		return result;
	}

	@Override
	public IAllocationResult unload(final IAllocationRequest request)
	{
		final IAllocationRequest requestActual = storage.removeQty(request);

		final boolean outTrx = true;
		return createAllocationResult(request, requestActual, outTrx);
	}

	private IAllocationResult createAllocationResult(
			final IAllocationRequest request,
			final IAllocationRequest requestActual,
			final boolean outTrx)
	{
		
		final IHUTransactionCandidate trx = createHUTransaction(requestActual, outTrx);
		
		return AllocationUtils.createQtyAllocationResult(
				request.getQty(), // qtyToAllocate
				requestActual.getQty(), // qtyAllocated
				Arrays.asList(trx), // trxs
				Collections.<IHUTransactionAttribute> emptyList() // attributeTrxs
				);
	}

	private IHUTransactionCandidate createHUTransaction(final IAllocationRequest request, final boolean outTrx)
	{
		final HUTransactionCandidate trx = new HUTransactionCandidate(getReferenceModel(),
				getM_HU_Item(),
				getVHU_Item(),
				request,
				outTrx);
		return trx;
	}

	public IProductStorage getStorage()
	{
		return storage;
	}

	private I_M_HU_Item getM_HU_Item()
	{
		return huItem;
	}

	private I_M_HU_Item getVHU_Item()
	{
		// TODO: implement: get VHU Item or create it
		return huItem;
	}

	public Object getReferenceModel()
	{
		return referenceModel;
	}

	@Override
	public List<IPair<IAllocationRequest, IAllocationResult>> unloadAll(final IHUContext huContext)
	{
		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext,
				storage.getProductId(), // product
				storage.getQty(), // qty
				huContext.getDate() // date
		);

		final IAllocationResult result = unload(request);

		return Collections.singletonList(ImmutablePair.of(request, result));
	}
}
