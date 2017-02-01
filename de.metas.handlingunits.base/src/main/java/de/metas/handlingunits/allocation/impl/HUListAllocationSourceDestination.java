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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.IAllocationStrategyFactory;
import de.metas.handlingunits.allocation.spi.impl.AggregateHUTrxListener;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IProductStorage;

/**
 * An Allocation Source/Destination which has a list of HUs in behind. Ususally used to load from HUs.
 *
 * @author tsa
 *
 */
public class HUListAllocationSourceDestination implements IAllocationSource, IAllocationDestination
{
	// Services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IAllocationStrategyFactory allocationStrategyFactory = Services.get(IAllocationStrategyFactory.class);

	private final List<I_M_HU> sourceHUs;
	private final int lastIndex;
	private int currentIndex = -1;

	private I_M_HU currentHU = null;

	private boolean destroyEmptyHUs = false;

	private boolean createHUSnapshots = false; // by default, don't create
	private String snapshotId = null;

	private boolean storeCUQtyBeforeProcessing = true;

	public HUListAllocationSourceDestination(final Collection<I_M_HU> sourceHUs)
	{
		// NOTE: we don't need contextProvider because we are creating nothing
		// when needed HUContext from Request will be used
		// this.contextProvider = contextProvider;

		this.sourceHUs = new ArrayList<I_M_HU>(sourceHUs);
		lastIndex = sourceHUs.size() - 1;
	}

	/**
	 * Convenient constructor for a single HU
	 *
	 * @param contextProvider
	 * @param sourceHU
	 */
	public HUListAllocationSourceDestination(final I_M_HU sourceHU)
	{
		this(Collections.singletonList(sourceHU));

		Check.assumeNotNull(sourceHU, "sourceHU not null");
	}

	public boolean isDestroyEmptyHUs()
	{
		return destroyEmptyHUs;
	}

	/**
	 * Shall we destroy HUs which are empty after {@link #unloadAll(IHUContext)}.
	 *
	 * @param destroyEmptyHUs true if HUs shall be destroyed if they are empty
	 */
	public void setDestroyEmptyHUs(final boolean destroyEmptyHUs)
	{
		this.destroyEmptyHUs = destroyEmptyHUs;
	}

	/**
	 * Shall we create snapshots of all {@link I_M_HU}s (recursivelly), before touching them.
	 * 
	 * In case it's activated, a full snapshots will be taken before touching any of the underlying HUs.
	 * The snapshot ID will be accessible by {@link #getSnapshotId()}.
	 * 
	 * @param createHUSnapshots
	 */
	public void setCreateHUSnapshots(final boolean createHUSnapshots)
	{
		this.createHUSnapshots = createHUSnapshots;
	}

	public void setStoreCUQtyBeforeProcessing(final boolean storeCUQtyBeforeProcessing)
	{
		this.storeCUQtyBeforeProcessing = storeCUQtyBeforeProcessing;
	}

	@Override
	public String toString()
	{
		return "HUListAllocationSourceDestination["
				+ "\nHU Count: " + sourceHUs.size()
				+ "\nCurrent HU Index: " + currentIndex
				+ "\nHUs: " + sourceHUs
				+ "]";
	}

	@Override
	public IAllocationResult load(final IAllocationRequest request)
	{
		// do not need date for this moment
		final boolean allocation = true;
		return processRequest(request, allocation);
	}

	@Override
	public IAllocationResult unload(final IAllocationRequest request)
	{
		final boolean allocation = false; // allocation=false means "deallocation"
		return processRequest(request, allocation);
	}

	private IAllocationResult processRequest(final IAllocationRequest request, final boolean allocation)
	{
		if (storeCUQtyBeforeProcessing)
		{
			sourceHUs.forEach(hu -> {
				storeAggregateItemCuQty(request, hu);
			});
		}

		createHUSnapshotsIfRequired(request.getHUContext());

		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request.getQty());

		while (!result.isCompleted())
		{
			if (currentHU == null)
			{
				currentHU = nextHU();
				if (currentHU == null)
				{
					// we reached the last HU
					break;
				}
			}

			final IAllocationRequest currentRequest = AllocationUtils.createQtyRequestForRemaining(request, result);
			final IAllocationStrategy allocationStrategy = getAllocationStrategy(currentHU, allocation);
			final IAllocationResult currentResult = allocationStrategy.execute(currentHU, currentRequest);

			AllocationUtils.mergeAllocationResult(result, currentResult);

			// It seems that current HU is empty but we still have more qty to deallocate, we need to move forward to the next one
			if (currentResult.getQtyToAllocate().signum() != 0)
			{
				currentHU = null;
			}

		}

		return result;
	}

	private void storeAggregateItemCuQty(final IAllocationRequest request, I_M_HU hu)
	{
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			final BigDecimal cuQty;

			final I_M_HU_Item haItem = hu.getM_HU_Item_Parent();
			final BigDecimal aggregateHUQty = haItem.getQty();
			if (aggregateHUQty.signum() <= 0)
			{
				cuQty = BigDecimal.ZERO;
			}
			else
			{
				final IHUStorage storage = request.getHUContext().getHUStorageFactory().getStorage(hu);
				final BigDecimal storageQty = storage == null ? BigDecimal.ZERO : storage.getQtyForProductStorages();

				cuQty = storageQty.divide(aggregateHUQty, 2, RoundingMode.HALF_UP); // scale=2 or more because we want to assert that it's a "round" number
				Check.errorIf(cuQty.stripTrailingZeros().scale() > 0,
						"cuQty={} needs to be a natural number; storageQty={}, aggregateHUQty={}, haItem={}",
						cuQty, storageQty, aggregateHUQty, haItem);
			}
			request.getHUContext().setProperty(AggregateHUTrxListener.mkItemCuQtyPropertyKey(haItem), cuQty);
		}
		else
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			handlingUnitsDAO.retrieveIncludedHUs(hu).forEach(includedHU -> storeAggregateItemCuQty(request, includedHU));
		}
	}

	/**
	 *
	 * @param hu
	 * @param allocation allocation(true) / deallocation(false) flag
	 * @return strategy
	 */
	protected IAllocationStrategy getAllocationStrategy(final I_M_HU hu, final boolean allocation)
	{
		if (allocation)
		{
			return allocationStrategyFactory.getAllocationStrategy(hu);
		}
		else
		{
			return allocationStrategyFactory.getDeallocationStrategy(hu);
		}
	}

	private I_M_HU nextHU()
	{
		currentIndex++;
		if (currentIndex > lastIndex)
		{
			return null;
		}
		return sourceHUs.get(currentIndex);
	}

	@Override
	public List<IPair<IAllocationRequest, IAllocationResult>> unloadAll(final IHUContext huContext)
	{
		createHUSnapshotsIfRequired(huContext);

		final List<IPair<IAllocationRequest, IAllocationResult>> result = new ArrayList<IPair<IAllocationRequest, IAllocationResult>>();

		while (true)
		{
			if (currentHU == null)
			{
				currentHU = nextHU();
				if (currentHU == null)
				{
					// we reached the last HU
					break;
				}
			}

			final List<IPair<IAllocationRequest, IAllocationResult>> huResult = unloadAll(huContext, currentHU);
			result.addAll(huResult);
			currentHU = null;
		}

		return result;
	}

	private List<IPair<IAllocationRequest, IAllocationResult>> unloadAll(final IHUContext huContext, final I_M_HU hu)
	{
		final List<IPair<IAllocationRequest, IAllocationResult>> result = new ArrayList<IPair<IAllocationRequest, IAllocationResult>>();

		final HUIteratorListenerAdapter huIteratorListener = new HUIteratorListenerAdapter()
		{
			/**
			 * Create an allocation request which will empty the given product storage.
			 *
			 * @param productStorage
			 * @return allocation request
			 */
			private IAllocationRequest createAllocationRequest(final IProductStorage productStorage)
			{
				final I_M_Product product = productStorage.getM_Product();
				final BigDecimal qty = productStorage.getQty();
				final I_C_UOM uom = productStorage.getC_UOM();
				final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext,
						product,
						qty,
						uom,
						getHUIterator().getDate() // date
				);
				return request;
			}

			@Override
			public Result beforeHUItemStorage(final IMutable<IHUItemStorage> itemStorage)
			{
				// don't go downstream because we don't care what's there
				return Result.SKIP_DOWNSTREAM;
			}

			@Override
			public Result afterHUItemStorage(final IHUItemStorage itemStorage)
			{
				final Date date = getHUIterator().getDate();
				final I_M_HU_Item huItem = itemStorage.getM_HU_Item();
				final I_M_HU_Item referenceModel = huItem;

				for (final IProductStorage productStorage : itemStorage.getProductStorages(date))
				{
					final IAllocationRequest productStorageRequest = createAllocationRequest(productStorage);

					final IAllocationSource productStorageAsSource = new GenericAllocationSourceDestination(
							productStorage,
							huItem,
							referenceModel);
					final IAllocationResult productStorageResult = productStorageAsSource.unload(productStorageRequest);
					result.add(ImmutablePair.of(productStorageRequest, productStorageResult));
				}
				return Result.CONTINUE;
			}
		};

		final HUIterator iterator = new HUIterator();
		iterator.setDate(huContext.getDate());
		iterator.setStorageFactory(huContext.getHUStorageFactory());
		iterator.setListener(huIteratorListener);
		iterator.iterate(hu);

		return result;
	}

	@Override
	public void loadComplete(final IHUContext huContext)
	{
		// nothing
	}

	@Override
	public void unloadComplete(final IHUContext huContext)
	{
		performDestroyEmptyHUsIfNeeded(huContext);
	}

	private void performDestroyEmptyHUsIfNeeded(final IHUContext huContext)
	{
		if (!destroyEmptyHUs)
		{
			return;
		}

		for (final I_M_HU hu : sourceHUs)
		{
			// Skip it if already destroyed
			if (handlingUnitsBL.isDestroyed(hu))
			{
				continue;
			}

			handlingUnitsBL.destroyIfEmptyStorage(huContext, hu);
		}
	}

	/**
	 * Gets the snapshot ID in case {@link #setCreateHUSnapshots(boolean)} was activated.
	 * 
	 * @return
	 *         <ul>
	 *         <li>Snapshot ID
	 *         <li><code>null</code> if snapshots were not activated or there was NO need to take a snapshot (i.e. nobody asked this object to change the underlying HUs)
	 *         </ul>
	 */
	public String getSnapshotId()
	{
		return snapshotId;
	}

	private final void createHUSnapshotsIfRequired(final IHUContext huContext)
	{
		if (!createHUSnapshots)
		{
			return;
		}

		// Make sure no snapshots was already created
		// shall not happen
		if (snapshotId != null)
		{
			throw new IllegalStateException("Snapshot was already created: " + snapshotId);
		}

		this.snapshotId = Services.get(IHUSnapshotDAO.class)
				.createSnapshot()
				.setContext(huContext)
				.addModels(sourceHUs)
				.createSnapshots()
				.getSnapshotId();
	}
}
