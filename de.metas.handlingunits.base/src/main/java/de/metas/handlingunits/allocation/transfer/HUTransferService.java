package de.metas.handlingunits.allocation.transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import com.google.common.base.Preconditions;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilderCoreEngine;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

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
 * This class contains business logic run by clients when they transform HUs.
 * Use {@link #get(Properties)} or {@link #get(IHUContext)} to obtain an instance.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh-webui/issues/181
 *
 */
public class HUTransferService
{
	private final IHUContext huContext;

	private HUTransferService(final IHUContext ctx)
	{
		this.huContext = ctx;
	}

	public static HUTransferService get(final IHUContext ctx)
	{
		return new HUTransferService(ctx);
	}

	/**
	 * Uses {@link IHUContextFactory#createMutableHUContext(Properties, String)} with the given {@code ctx} and returns a new {@link HUTransferService} instance with that huContext.
	 * 
	 * @param ctx
	 * @return
	 */
	public static HUTransferService get(final Properties ctx)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		return get(huContextFactory.createMutableHUContext(ctx));
	}

	private IAllocationRequest createCUAllocationRequest(
			final IHUContext huContext,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final BigDecimal cuQty)
	{

		//
		// Create allocation request for the quantity user entered
		final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(huContext, cuProduct, cuQty, cuUOM, SystemTime.asTimestamp());

		if (allocationRequest.isZeroQty())
		{
			throw new AdempiereException("@QtyCU@ shall be greather than zero");
		}

		// task 09717
		// make sure the attributes are initialized in case of multiple row selection, also
		// TODO: do we need this?
		// huReceiptScheduleBL.setInitialAttributeValueDefaults(allocationRequest, ImmutableList.of(receiptSchedule));

		return allocationRequest;
	}

	/**
	 * Split selected CU to a new CU.
	 *
	 * @param cuRow
	 * @param qtyCU
	 */
	public List<I_M_HU> splitCU_To_NewCU(
			final I_M_HU cuHU,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final BigDecimal qtyCU)
	{
		Preconditions.checkNotNull(cuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(cuProduct, "Param 'cuProduct' may not be null");
		Preconditions.checkNotNull(cuUOM, "Param 'cuUOM' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");

		final HUProducerDestination destination = HUProducerDestination.ofVirtualPI();

		HUSplitBuilderCoreEngine
				.of(
						huContext,
						cuHU,
						huContext -> createCUAllocationRequest(huContext, cuProduct, cuUOM, qtyCU),
						destination)
				.withPropagateHUValues()
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		return destination.getCreatedHUs();
	}

	/**
	 * Split selected CU to an existing TU.
	 *
	 * @param cuRow
	 * @param qtyCU quantity to split
	 * @param tuHU
	 */
	public void splitCU_To_ExistingTU(
			final I_M_HU cuHU,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final BigDecimal qtyCU,
			final I_M_HU tuHU)
	{
		Preconditions.checkNotNull(cuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(cuProduct, "Param 'cuProduct' may not be null");
		Preconditions.checkNotNull(cuUOM, "Param 'cuUOM' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");

		final HUListAllocationSourceDestination destination = HUListAllocationSourceDestination.of(tuHU);

		HUSplitBuilderCoreEngine
				.of(huContext,
						cuHU,
						huContext -> createCUAllocationRequest(huContext, cuProduct, cuUOM, qtyCU),
						destination)
				.withPropagateHUValues()
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();
	}

	/**
	 * Split selected CU to new top level TUs
	 *
	 * @param cuRow cu row to split
	 * @param qtyCU quantity CU to split
	 * @param tuPIItemProductId to TU
	 * @param isOwnPackingMaterials
	 */
	public List<I_M_HU> splitCU_To_NewTUs(
			final I_M_HU cuHU,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final BigDecimal qtyCU,
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final boolean isOwnPackingMaterials)
	{

		Preconditions.checkNotNull(cuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(cuProduct, "Param 'cuProduct' may not be null");
		Preconditions.checkNotNull(cuUOM, "Param 'cuUOM' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");
		Preconditions.checkNotNull(tuPIItemProduct, "Param 'tuPIItemProduct' may not be null");

		final LUTUProducerDestination destination = new LUTUProducerDestination();
		destination.setTUPI(tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI());
		destination.setIsHUPlanningReceiptOwnerPM(isOwnPackingMaterials);
		destination.setNoLU();

		HUSplitBuilderCoreEngine
				.of(huContext,
						cuHU,
						huContext -> createCUAllocationRequest(huContext, cuProduct, cuUOM, qtyCU),
						destination)
				.withPropagateHUValues()
				.withTuPIItem(tuPIItemProduct.getM_HU_PI_Item())
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		return destination.getCreatedHUs();
	}

	/**
	 * Split a given number of TUs from current selected TU line to new TUs.
	 *
	 * @param tuRow
	 * @param qtyTU
	 * @param tuPIItemProductId
	 * @param isOwnPackingMaterials
	 */
	public List<I_M_HU> splitTU_To_NewTUs(
			final I_M_HU tuHU, final BigDecimal qtyTU //
			, final I_M_HU_PI_Item_Product tuPIItemProduct //
			, final boolean isOwnPackingMaterials //
	)
	{
		return splitTU_To_NewLU(tuHU, qtyTU, tuPIItemProduct, null, isOwnPackingMaterials);
	}

	/**
	 * Split TU to new LU (only one LU!).
	 *
	 * @param tuRow
	 * @param QtyTU
	 * @param tuPIItemProductId
	 * @param luPIItemId
	 * @param isOwnPackingMaterials
	 */
	public List<I_M_HU> splitTU_To_NewLU( //
			final I_M_HU tuHU //
			, final BigDecimal qtyTU //
			, final I_M_HU_PI_Item_Product tuPIItemProduct //
			, final I_M_HU_PI_Item luPIItem //
			, final boolean isOwnPackingMaterials //
	)
	{
		Preconditions.checkNotNull(tuHU, "Param 'tuHU' may not be null");
		Preconditions.checkNotNull(qtyTU, "Param 'qtyTU' may not be null");

		final List<IHUProductStorage> productStorages = retrieveAllProductStorages(tuHU);

		// TODO cases to cover:
		// 1. cuRows.isEmpty() for whatever reason: in this case, the TU shall be destroyed already, so this method can't be called with such a TU; throw an exception
		// 2. cuRows.size() == 1: create source and destination, and create a request with a CU-qty of qtyTU * tuRow.getQtyCU() etc
		// 3. cuRows.size() > 1: for the first cuRow, do 2.; then, for the following cuRows, basically do what action_SplitCU_To_ExistingTU() does

		final List<I_M_HU> createdTUs;

		// deal with the first of potentially many cuHUs and their storages
		{
			final IHUProductStorage firstProductStorage = productStorages.get(0);

			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			final BigDecimal qtyOfStorage = Preconditions.checkNotNull(firstProductStorage.getQty(), "Qty of firstProductStorage may not be null; firstProductStorage=%s", firstProductStorage);
			final BigDecimal qtyCUperTU;
			if (handlingUnitsBL.isAggregateHU(tuHU))
			{
				final I_M_HU_Item tuHUsParentItem = tuHU.getM_HU_Item_Parent(); // can't be null because if is was isAggregateHU() would return false.
				final BigDecimal representedTUsCount = tuHUsParentItem.getQty();
				Preconditions.checkState(representedTUsCount.signum() > 0, "Param 'tuHU' is an aggregate HU whose M_HU_Item_Parent has a qty of %s; tuHU=%s; tuHU's M_HU_Item_Parent=%s", representedTUsCount, tuHU, tuHUsParentItem);
				qtyCUperTU = qtyOfStorage.divide(representedTUsCount);
			}
			else
			{
				qtyCUperTU = qtyOfStorage;
			}
			final I_M_Product cuProduct = Preconditions.checkNotNull(firstProductStorage.getM_Product(), "M_Product of firstProductStorage may not be null; firstProductStorage=%s", firstProductStorage);
			final I_C_UOM cuUOM = Preconditions.checkNotNull(firstProductStorage.getC_UOM(), "C_UOM of firstProductStorage may not be null; firstProductStorage=%s", firstProductStorage);

			final LUTUProducerDestination destination = new LUTUProducerDestination();
			destination.setTUPI(tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI());
			if (luPIItem == null)
			{
				destination.setNoLU();
			}
			else
			{
				destination.setLUItemPI(luPIItem);
				destination.setLUPI(luPIItem.getM_HU_PI_Version().getM_HU_PI());
			}
			destination.setIsHUPlanningReceiptOwnerPM(isOwnPackingMaterials);

			destination.addTUCapacity(cuProduct, tuPIItemProduct.getQty(), cuUOM); // explicitly declaring capacity to make sure that all aggregate HUs have it

			HUSplitBuilderCoreEngine
					.of(huContext,
							tuHU,
							huContext -> createCUAllocationRequest(huContext, cuProduct, cuUOM, qtyTU.multiply(qtyCUperTU)),
							destination)
					.withPropagateHUValues()
					.withTuPIItem(tuPIItemProduct.getM_HU_PI_Item())
					.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
					.performSplit();

			createdTUs = destination.getCreatedHUs();
		}

		// if productStorages has more than one element, then iterate the remaining rows
		for (int i = 1; i < productStorages.size(); i++)
		{
			final IHUProductStorage currentHuProductStorage = productStorages.get(i);

			final BigDecimal qtyCU = Preconditions.checkNotNull(currentHuProductStorage.getQty(), "Qty of currentHuProductStorage=%s may not be null", currentHuProductStorage);
			createdTUs.forEach(createdTU -> {
				splitCU_To_ExistingTU(currentHuProductStorage.getM_HU(), currentHuProductStorage.getM_Product(), currentHuProductStorage.getC_UOM(), qtyCU, createdTU);
			});
		}

		return createdTUs;
	}

	private List<IHUProductStorage> retrieveAllProductStorages(final I_M_HU tuHU)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final List<IHUProductStorage> productStorages = new ArrayList<>();

		if (handlingUnitsBL.isAggregateHU(tuHU))
		{
			productStorages.addAll(storageFactory.getStorage(tuHU).getProductStorages());
		}
		else
		{
			handlingUnitsDAO.retrieveIncludedHUs(tuHU)
					.forEach(cuHU -> {
						productStorages.addAll(storageFactory.getStorage(cuHU).getProductStorages());
					});
		}
		Preconditions.checkState(!productStorages.isEmpty(), "The list of productStorages below the given 'tuHU' may not be empty; tuHU=%s", tuHU);
		return productStorages;
	}

	public void splitTU_To_ExistingLU(
			final I_M_HU tuHU //
			, final BigDecimal qtyTU //
			, final I_M_HU luHU)
	{
		Preconditions.checkNotNull(tuHU, "Param 'tuHU' may not be null");
		Preconditions.checkNotNull(qtyTU, "Param 'qtyTU' may not be null");
		Preconditions.checkNotNull(luHU, "Param 'luHU' may not be null");

		final List<IHUProductStorage> productStorages = retrieveAllProductStorages(tuHU);

		for (final IHUProductStorage productStorage : productStorages)
		{
			final HUListAllocationSourceDestination destination = HUListAllocationSourceDestination.of(luHU);

			// Transfer Qty
			HUSplitBuilderCoreEngine
					.of(huContext,
							tuHU,
							huContext -> createCUAllocationRequest(huContext, productStorage.getM_Product(), productStorage.getC_UOM(), productStorage.getQty()),
							destination)
					.withPropagateHUValues()
					.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
					.performSplit();
		}
	}
}
