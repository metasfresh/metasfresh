/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.allocation.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.spi.impl.AggregateHUTrxListener;
import de.metas.handlingunits.allocation.strategy.AllocationStrategyFactory;
import de.metas.handlingunits.allocation.strategy.AllocationStrategyType;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An Allocation Source/Destination which has a list of HUs in behind. Usually used to load from HUs.
 *
 * @author tsa
 *
 */
public class HUListAllocationSourceDestination implements IAllocationSource, IAllocationDestination
{
	/** @return single HU allocation source/destination */
	public static HUListAllocationSourceDestination of(@NonNull final I_M_HU hu)
	{
		return new HUListAllocationSourceDestination(ImmutableList.of(hu), AllocationStrategyType.DEFAULT);
	}

	public static HUListAllocationSourceDestination of(
			@NonNull final I_M_HU hu,
			@NonNull final AllocationStrategyType allocationStrategyType)
	{
		return new HUListAllocationSourceDestination(ImmutableList.of(hu), allocationStrategyType);
	}

	public static HUListAllocationSourceDestination ofHUId(@NonNull final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsRepo = Services.get(IHandlingUnitsBL.class);
		final I_M_HU hu = handlingUnitsRepo.getById(huId);
		return new HUListAllocationSourceDestination(ImmutableList.of(hu), AllocationStrategyType.DEFAULT);
	}

	/** @return multi-HUs allocation source/destination */
	public static HUListAllocationSourceDestination of(final Collection<I_M_HU> sourceHUs)
	{
		return new HUListAllocationSourceDestination(ImmutableList.copyOf(sourceHUs), AllocationStrategyType.DEFAULT);
	}

	// Services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final AllocationStrategyFactory allocationStrategyFactory = SpringContextHolder.instance.getBean(AllocationStrategyFactory.class);

	private final ImmutableList<I_M_HU> sourceHUs;
	private final int lastIndex;
	private int currentIndex = -1;

	private final AllocationStrategyType allocationStrategyType;

	private I_M_HU currentHU = null;

	private boolean destroyEmptyHUs = false;

	private boolean createHUSnapshots = false; // by default, don't create
	private String snapshotId = null;

	/** see {@link #setStoreCUQtyBeforeProcessing(boolean)} */
	private boolean storeCUQtyBeforeProcessing = true;

	private HUListAllocationSourceDestination(
			@NonNull final ImmutableList<I_M_HU> sourceHUs,
			@NonNull final AllocationStrategyType allocationStrategyType)
	{
		this.sourceHUs = sourceHUs;
		lastIndex = sourceHUs.size() - 1;

		this.allocationStrategyType = allocationStrategyType;
		this.storeCUQtyBeforeProcessing = computeStoreCUQtyBeforeProcessing(allocationStrategyType);
	}

	private static boolean computeStoreCUQtyBeforeProcessing(@NonNull final AllocationStrategyType allocationStrategyType)
	{
		if (AllocationStrategyType.UNIFORM.equals(allocationStrategyType))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean isDestroyEmptyHUs()
	{
		return destroyEmptyHUs;
	}

	/**
	 * Shall we destroy HUs which are empty after {@link #unloadAll(IHUContext)}.
	 *
	 * @param destroyEmptyHUs true if HUs shall be destroyed if they are empty
	 * @return
	 */
	public HUListAllocationSourceDestination setDestroyEmptyHUs(final boolean destroyEmptyHUs)
	{
		this.destroyEmptyHUs = destroyEmptyHUs;
		return this;
	}

	/**
	 * Shall we create snapshots of all {@link I_M_HU}s (recursivelly), before touching them.
	 *
	 * In case it's activated, a full snapshots will be taken before touching any of the underlying HUs.
	 * The snapshot ID will be accessible by {@link #getSnapshotId()}.
	 *
	 * @param createHUSnapshots
	 * @return
	 */
	public HUListAllocationSourceDestination setCreateHUSnapshots(final boolean createHUSnapshots)
	{
		this.createHUSnapshots = createHUSnapshots;
		return this;
	}

	/**
	 * For this instance's HUs their included HUs (if any), this setter specifies whether this instance will make sure that for every aggregate HU, the current CU-per-TU quantity is stored in the given {@code request}'s {@link IHUContext}.
	 * <p>
	 * This information (if stored by this instance) can later be used by {@link AggregateHUTrxListener} to adjust the {@code HA} item's {@code Qty} or split off a partial TU.
	 * If you don't want this behavior (e.g. gh #943: because we are adjusting the HU's storage from the net weight), then just call this method with {@code false}. If you don't use this setter, the default will be {@code true}.
	 *
	 * @param storeCUQtyBeforeProcessing
	 * @return
	 */
	public HUListAllocationSourceDestination setStoreCUQtyBeforeProcessing(final boolean storeCUQtyBeforeProcessing)
	{
		this.storeCUQtyBeforeProcessing = storeCUQtyBeforeProcessing;
		return this;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("HUs Count", sourceHUs.size())
				.add("Current HU index", currentIndex)
				.add("HUs", sourceHUs)
				.toString();
	}

	@Override
	public IAllocationResult load(final IAllocationRequest request)
	{
		return processRequest(request, AllocationDirection.INBOUND_ALLOCATION);
	}

	@Override
	public IAllocationResult unload(final IAllocationRequest request)
	{
		return processRequest(request, AllocationDirection.OUTBOUND_DEALLOCATION);
	}

	private IAllocationResult processRequest(
			@NonNull final IAllocationRequest request,
			@NonNull final AllocationDirection direction)
	{
		if (storeCUQtyBeforeProcessing)
		{
			// store the CU qtys in memory, so at the end of the load we can check if they changed.
			sourceHUs.forEach(hu -> {
				storeAggregateItemCuQty(request, hu);
			});
		}

		createHUSnapshotsIfRequired(request.getHuContext());

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
			final IAllocationStrategy allocationStrategy = allocationStrategyFactory.createAllocationStrategy(direction, allocationStrategyType);
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

	/**
	 * See {@link #setStoreCUQtyBeforeProcessing(boolean)}.
	 *
	 * @param request
	 * @param hu
	 */
	private void storeAggregateItemCuQty(final IAllocationRequest request, final I_M_HU hu)
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
				final IHUStorage storage = request.getHuContext().getHUStorageFactory().getStorage(hu);
				final BigDecimal storageQty = storage == null ? BigDecimal.ZERO : storage.getQtyForProductStorages(request.getC_UOM()).toBigDecimal();

				// gh #1237: cuQty does *not* have to be a an "integer" number.
				// If the overall HU's storage is not always integer and given that the aggregate's TU qty is always an integer, cuQty can't be an integer at any times either
				final I_C_UOM storageUOM = storage.getC_UOMOrNull();
				Check.errorIf(storageUOM == null, "The storage of M_HU_ID={} (an aggregate HU) has a null UOM (i.e. contains substorages with incompatible UOMs)", hu.getM_HU_ID()); // can't be null, because in aggregate-HU country, storages are uniform and therefore all have the same UOM

				final int scale = storageUOM.getStdPrecision();
				cuQty = storageQty.divide(aggregateHUQty,
						scale,
						RoundingMode.FLOOR);
			}
			request.getHuContext().setProperty(AggregateHUTrxListener.mkItemCuQtyPropertyKey(haItem), cuQty);
		}
		else
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			handlingUnitsDAO.retrieveIncludedHUs(hu).forEach(includedHU -> storeAggregateItemCuQty(request, includedHU));
		}
	}

	@Nullable
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

		final List<IPair<IAllocationRequest, IAllocationResult>> result = new ArrayList<>();

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
		final List<IPair<IAllocationRequest, IAllocationResult>> result = new ArrayList<>();

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
				final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext,
						productStorage.getProductId(),
						productStorage.getQty(),
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
				final ZonedDateTime date = getHUIterator().getDate();
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

		snapshotId = Services.get(IHUSnapshotDAO.class)
				.createSnapshot()
				.setContext(huContext)
				.addModels(sourceHUs)
				.createSnapshots()
				.getSnapshotId();
	}
}
