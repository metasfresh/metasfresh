package de.metas.handlingunits.allocation.spi.impl;

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

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMPrecision;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.spi.impl.WeightTareAttributeValueCallout;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This listener plays an important role for aggregate HUs.
 * It's fired after a complete load and does the following (only with aggregate VHUs):
 * <li>Use the CU-per-TU qty that was computed earlier and was stored in the {@link IHUContext} (see {@link #mkItemCuQtyPropertyKey(I_M_HU_Item)}) and update the HA item's {@code Qty} column.
 * <li>Update the item storage's <b>tare</b> value using business logic from {@link WeightTareAttributeValueCallout} and pushing up the change
 * <li>Preserve the original CU-per-TU qty by splitting off partial quantities from the aggregate HU into a "real" HU.
 *
 * @author metas-dev <dev@metasfresh.com>
 *         task: https://github.com/metasfresh/metasfresh/issues/460
 */
public class AggregateHUTrxListener implements IHUTrxListener
{
	public static final AggregateHUTrxListener INSTANCE = new AggregateHUTrxListener();

	/**
	 * Makes sure the {@link #afterLoad(IHUContext, List)} is not called recurrently.
	 */
	private final ThreadLocal<Boolean> withinMethod = new ThreadLocal<>();
	private final HUQRCodesService huqrCodesService;

	/**
	 * Creates a key used to put and get the CU quantity per HA item.
	 */
	@NonNull
	public static String mkItemCuQtyPropertyKey(@NonNull final I_M_HU_Item haItem)
	{
		return AggregateHUTrxListener.class.getSimpleName() + "_" + I_M_HU_Item.Table_Name + "_ID_" + haItem.getM_HU_Item_ID() + "_CU_QTY";
	}

	/**
	 * Creates a key used to put and get the qty of TUs to split off this HA item.
	 */
	@NonNull
	public static String mkQtyTUsToSplitPropertyKey(@NonNull final I_M_HU_Item haItem)
	{
		return AggregateHUTrxListener.class.getSimpleName() + "_" + I_M_HU_Item.Table_Name + "_ID_" + haItem.getM_HU_Item_ID() + "_QTY_TUS_TO_SPLIT";
	}

	private AggregateHUTrxListener()
	{
		this.huqrCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	}

	/**
	 * Performs the steps described in the class-javadoc.
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

		// If we split exactly N TUs (integer), there's no need for all the dance to figure out the new correct # of TUs for the qty, and splitting the extra qty.
		// We shall just update the new qty of TUs in this aggregate TU.
		final BigDecimal qtyTUsToSplit = huContext.getProperty(AggregateHUTrxListener.mkQtyTUsToSplitPropertyKey(item));
		if (qtyTUsToSplit != null && qtyTUsToSplit.signum() != 0)
		{
			final BigDecimal newTuQty = item.getQty().subtract(qtyTUsToSplit);
			item.setQty(newTuQty);
			InterfaceWrapperHelper.save(item);
		}
		else
		{
			final IHUTransactionCandidate trx = itemId2Trx.get(item.getM_HU_Item_ID());
			final BigDecimal storageQty = storage.getQty(trx.getProductId(), trx.getQuantity().getUOM());

			// get the new TU quantity, which as TUs go needs to be an integer
			final BigDecimal newTuQty = storageQty.divide(cuQtyBeforeLoad,
					0,
					RoundingMode.FLOOR);

			item.setQty(newTuQty);
			InterfaceWrapperHelper.save(item);

			// find out if we need to perform a split in order to preserve the former CU-per-TU quantity
			final IUOMDAO uomDao = Services.get(IUOMDAO.class);
			final UOMPrecision precision = uomDao.getStandardPrecision(trx.getQuantity().getUomId());

			final BigDecimal storageQtyOfCompleteTUs = newTuQty.multiply(cuQtyBeforeLoad);
			final BigDecimal qtyToSplit = storageQty.subtract(storageQtyOfCompleteTUs);

			final BigDecimal errorMargin = NumberUtils.getErrorMarginForScale(precision.toInt());
			if (qtyToSplit.compareTo(errorMargin) > 0)
			{
				// the *actual* newTuQty would not be a natural number, so we need to initiate another split now
				final I_M_HU_PI_Item splitHUPIItem = Services.get(IHandlingUnitsBL.class).getPIItem(item);

				// create a handling unit item
				@SuppressWarnings("ConstantConditions")
				final I_M_HU_Item splitHUParentItem = handlingUnitsDAO.createHUItemIfNotExists(item.getM_HU(), splitHUPIItem).getLeft();

				// the source is the aggregate item's aggregate VHU
				final HUListAllocationSourceDestination source = HUListAllocationSourceDestination.of(handlingUnitsDAO.retrieveIncludedHUs(item));
				source.setStoreCUQtyBeforeProcessing(false); // don't try it, it will probably fail

				// the destination is a new HU that shall be attached as a sibling of the aggregate VHU
				final I_M_HU_PI includedPI = handlingUnitsDAO.getIncludedPI(splitHUPIItem);
				final HUProducerDestination destination = HUProducerDestination.of(includedPI);

				destination.setParent_HU_Item(splitHUParentItem);
				final HULoader loader = HULoader.of(source, destination)
						.setForceLoad(true)
				// note for dev: forceLoad is needed here, because if qty is greater than PI qty, we will split the expected 1 TU into 2, and that is wrong logically.
				// see details in https://github.com/metasfresh/metasfresh/issues/6808#issuecomment-642414037
				;

				// Create allocation request
				final IAllocationRequest request = AllocationUtils.createQtyRequest(
						huContext,
						trx.getProductId(),
						Quantity.of(qtyToSplit, trx.getQuantity().getUOM()),
						huContext.getDate());
				loader.load(request);

				final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(item);
				if (includedHUs.size() == 1) {
					huqrCodesService.propagateQrForSplitHUs(includedHUs.get(0), destination.getCreatedHUs());
				}
			}
		}

		// TODO: i think we can move this shit or something better into a model interceptor that is fired when item.qty is changed
		{
			// update the tare of our aggregate VHU (*if* its storage has such a thing)
			final I_M_HU aggregateVHU = handlingUnitsDAO.retrieveIncludedHUs(item).get(0);
			final IAttributeStorage aggregateVHUAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(aggregateVHU);

			final IWeightable aggregateVHUWeightable = Weightables.wrap(aggregateVHUAttributeStorage);
			final AttributeCode aggregateVHUWeightTareAttribute = aggregateVHUWeightable.getWeightTareAttribute();
			if (aggregateVHUAttributeStorage.hasAttribute(aggregateVHUWeightTareAttribute))
			{
				final BigDecimal tareOfAggregateVHU = WeightTareAttributeValueCallout.calculateWeightTare(aggregateVHU);
				aggregateVHUAttributeStorage.setValue(aggregateVHUWeightTareAttribute, tareOfAggregateVHU);
				aggregateVHUAttributeStorage.pushUp();
			}
		}
	}
}
