package de.metas.handlingunits.allocation.spi.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.mm.attributes.spi.impl.WeightTareAttributeValueCallout;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.IWeightableFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUItemStorage;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * This listener plays an important role for aggregate HUs.
 * It's fired after a complete load and does the following (only with aggregate VHUs):
 * <li>Use the CU-per-TU qty that was computed earlier and was stored in the {@link IHUContext} (see {@link #mkItemCuQtyPropertyKey(I_M_HU_Item)}) and update the HA item's {@code Qty} column.
 * <li>Update the item storage's <b>tare</b> value using business logic from {@link WeightTareAttributeValueCallout} and pushing up the change
 * <li>Preserve the original CU-per-TU qty by splitting off partial quantities from the aggregate HU into a "real" HU.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/460
 */
public class AggregateHUTrxListener implements IHUTrxListener
{
	public static final AggregateHUTrxListener INSTANCE = new AggregateHUTrxListener();

	/**
	 * Makes sure the {@link #afterLoad(IHUContext, List)} is not called recurrently.
	 */
	private final ThreadLocal<Boolean> withinMethod = new ThreadLocal<>();

	/**
	 * Creates a key used to put and get the CU quantity per HA item.
	 * 
	 * @param haItem
	 * @return
	 */
	public static String mkItemCuQtyPropertyKey(final I_M_HU_Item haItem)
	{
		return AggregateHUTrxListener.class.getSimpleName() + "_" + I_M_HU_Item.Table_Name + "_ID_" + haItem.getM_HU_Item_ID() + "_CU_QTY";
	}

	private AggregateHUTrxListener()
	{
	}

	/**
	 * Performs the steps described in that class javadoc.
	 */
	@Override
	public void afterLoad(final IHUContext huContext, final List<IAllocationResult> loadResults)
	{
		if (withinMethod.get() != null && withinMethod.get())
		{
			return;
		}

		try
		{
			withinMethod.set(true);
			afterLoad0(huContext, loadResults);
		}
		finally
		{
			withinMethod.set(false);
		}
	}

	private void afterLoad0(final IHUContext huContext, final List<IAllocationResult> loadResults)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final Map<Integer, I_M_HU_Item> itemId2AggregateItem = new HashMap<>();
		final Map<Integer, IHUTransactionCandidate> itemId2Trx = new HashMap<>();

		loadResults.stream().flatMap(result -> result.getTransactions().stream())
				.filter(trx -> handlingUnitsBL.isAggregateHU(trx.getVHU()))

				.peek(trx -> itemId2Trx.put(trx.getVHU().getM_HU_Item_Parent().getM_HU_Item_ID(), trx))

				.map(trx -> trx.getVHU().getM_HU_Item_Parent())
				.forEach(item -> itemId2AggregateItem.put(item.getM_HU_Item_ID(), item));

		itemId2AggregateItem.values()
				.forEach(item -> updateItemQtyAndSplitIfNeeded(huContext, itemId2Trx, item));
	}

	private void updateItemQtyAndSplitIfNeeded(final IHUContext huContext, final Map<Integer, IHUTransactionCandidate> itemId2Trx, final I_M_HU_Item item)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		// this is the former cuQty, before the load from which this listener was called.
		final BigDecimal cuQtyBeforeLoad = huContext.getProperty(AggregateHUTrxListener.mkItemCuQtyPropertyKey(item));

		if (cuQtyBeforeLoad == null || cuQtyBeforeLoad.signum() <= 0)
		{
			return; // nothing to do
		}

		final IHUItemStorage storage = huContext.getHUStorageFactory().getStorage(item);
		if (storage == null)
		{
			return; // nothing to do
		}

		final IHUTransactionCandidate trx = itemId2Trx.get(item.getM_HU_Item_ID());
		final BigDecimal storageQty = storage.getQty(trx.getProduct(), trx.getQuantity().getUOM());

		// get the new TU quantity, which as TUs go needs to be an integer
		final BigDecimal newTuQty = storageQty.divide(cuQtyBeforeLoad,
				0,
				RoundingMode.FLOOR);

		item.setQty(newTuQty);
		InterfaceWrapperHelper.save(item);

		// find out if we need to perform a split in order to preserve the former CU-per-TU quantity
		final BigDecimal splitQty = computeSplitQty(storageQty, cuQtyBeforeLoad);

		if (splitQty.signum() != 0)
		{
			// the *actual* newTuQty would not be a natural number, so we need to initiate another split now
			final I_M_HU_PI_Item splitHUPIItem = Services.get(IHandlingUnitsBL.class).getPIItem(item);

			// create a handling unit item
			final I_M_HU_Item splitHUParentItem = handlingUnitsDAO.createHUItemIfNotExists(item.getM_HU(), splitHUPIItem).getLeft();

			// the source is the aggregate item's aggregate VHU
			final HUListAllocationSourceDestination source = HUListAllocationSourceDestination.of(handlingUnitsDAO.retrieveIncludedHUs(item));
			source.setStoreCUQtyBeforeProcessing(false); // don't try it, it will probably fail

			// the destination is a new HU that shall be attached as a sibling of the aggregate VHU
			final HUProducerDestination destination = HUProducerDestination.of(splitHUPIItem.getIncluded_HU_PI());

			destination.setParent_HU_Item(splitHUParentItem);
			final HULoader loader = HULoader.of(source, destination);

			// Create allocation request
			final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext, trx.getProduct(), splitQty, trx.getQuantity().getUOM(), huContext.getDate());
			loader.load(request);
		}

		// TODO: i think we can move this shit or something better into a model interceptor that is fired when item.qty is changed
		{
			// update the tare of our aggregate VHU (*if* its storage has such a thing)
			final I_M_HU aggregateVHU = handlingUnitsDAO.retrieveIncludedHUs(item).get(0);
			final IAttributeStorage aggregateVHUAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(aggregateVHU);

			final IWeightable aggregateVHUWeightable = Services.get(IWeightableFactory.class).createWeightableOrNull(aggregateVHUAttributeStorage);
			final I_M_Attribute aggregateVHUWeightTareAttribute = aggregateVHUWeightable.getWeightTareAttribute();
			if (aggregateVHUAttributeStorage.hasAttribute(aggregateVHUWeightTareAttribute))
			{
				final BigDecimal tareOfAggregateVHU = WeightTareAttributeValueCallout.calculateWeightTare(aggregateVHU);
				aggregateVHUAttributeStorage.setValue(aggregateVHUWeightTareAttribute, tareOfAggregateVHU);
				aggregateVHUAttributeStorage.pushUp();
			}
		}
	}

	/**
	 * Returns the quantity that needs to be split off the current storage quantity in order to achieve the same CU-per-TU quantity which the aggregate HU in question used to have before the loading operation which lead to this listener being called.
	 * 
	 * @param storageQty the current qty re have in the storage
	 * @param cuQtyBeforeLoad the former CU-per-TU qty, before the loading took place
	 * @return
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1203
	 */
	@VisibleForTesting
	/* package */ BigDecimal computeSplitQty(final BigDecimal storageQty, final BigDecimal cuQtyBeforeLoad)
	{
		return storageQty.remainder(cuQtyBeforeLoad);
	}
}
