package de.metas.handlingunits.allocation.transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilderCoreEngine;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
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

	/**
	 * When running unit tests, then use this method to get your instance. Pass the HUTestHelper's getHUContext() result to it, to avoid transactional trouble.
	 * 
	 * @param ctx
	 * @return
	 */
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
			final BigDecimal cuQty,
			final boolean forceAllocation)
	{

		//
		// Create allocation request for the quantity user entered
		final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(huContext,
				cuProduct,
				cuQty,
				cuUOM,
				SystemTime.asTimestamp(),
				null, // referenced model
				forceAllocation);

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

	public BigDecimal getMaximumQtyTU(final I_M_HU tu)
	{
		Preconditions.checkNotNull(tu, "Param 'tu' may not be null");
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		if (handlingUnitsBL.isAggregateHU(tu))
		{
			return handlingUnitsDAO.retrieveParentItem(tu).getQty();
		}
		return BigDecimal.ONE;
	}

	public BigDecimal getMaximumQtyCU(final I_M_HU cu)
	{
		Preconditions.checkNotNull(cu, "Param 'cu' may not be null");
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IHUStorage storage = storageFactory.getStorage(cu);

		return storage.getQtyForProductStorages();
	}

	/**
	 * Split selected CU to a new CU.
	 *
	 * @param cuRow
	 * @param qtyCU
	 */
	public List<I_M_HU> cuToNewCU(
			final I_M_HU cuHU,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final BigDecimal qtyCU)
	{
		Preconditions.checkNotNull(cuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(cuProduct, "Param 'cuProduct' may not be null");
		Preconditions.checkNotNull(cuUOM, "Param 'cuUOM' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");

		if (qtyCU.compareTo(getMaximumQtyCU(cuHU)) >= 0)
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			if (handlingUnitsDAO.retrieveParentItem(cuHU) == null)
			{
				// the caller wants to process the complete cuHU, but there is nothing to do because the cuHU is not attached to a parent.
				return Collections.emptyList();
			}
			else
			{
				setParent(cuHU, null);
				return ImmutableList.of(cuHU);
			}
		}

		final HUProducerDestination destination = HUProducerDestination.ofVirtualPI();

		HUSplitBuilderCoreEngine
				.of(
						huContext,
						cuHU,
						// forceAllocation = false; no need, because destination has no capacity constraints
						huContext -> createCUAllocationRequest(huContext, cuProduct, cuUOM, qtyCU, false),
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
	public void cuToExistingTU(
			final I_M_HU cuHU,
			final BigDecimal qtyCU,
			final I_M_HU tuHU)
	{
		Preconditions.checkNotNull(cuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final IAllocationDestination destination;
		if (handlingUnitsBL.isAggregateHU(tuHU))
		{
			// we will load directly to the given tuHU which is actually a VHU
			destination = HUListAllocationSourceDestination.of(tuHU);
		}
		else
		{
			// we are later going to attach something as a child to the given 'tuHU'
			if (qtyCU.compareTo(getMaximumQtyCU(cuHU)) >= 0)
			{
				// we will attach the whole cuHU to tuHU and thus not load/split anything
				destination = null;
			}
			else
			{
				// we will load qtCU do a new VHU (a new CU) and then attach that new CU to 'tuHU'
				destination = HUProducerDestination.ofVirtualPI();
			}
		}

		if (destination != null)
		{
			final List<IHUProductStorage> storages = huContext.getHUStorageFactory().getStorage(cuHU).getProductStorages();
			Check.errorUnless(storages.size() == 1, "Param' cuHU' needs to have *one* storage; storages={}; cuHU={};", storages, cuHU);
			HUSplitBuilderCoreEngine
					.of(
							huContext,
							cuHU,
							// forceAllocation = true; we don't want to get bothered by capacity constraint, even if the destination *probably* doesn't have any to start with
							huContext -> createCUAllocationRequest(huContext,
									storages.get(0).getM_Product(),
									storages.get(0).getC_UOM(),
									qtyCU,
									true),
							destination)
					.withPropagateHUValues()
					.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
					.performSplit();
		}

		if (handlingUnitsBL.isAggregateHU(tuHU))
		{
			return; // we are done; no attaching
		}

		// we attach the
		final List<I_M_HU> childCUs;
		if (destination == null)
		{
			childCUs = ImmutableList.of(cuHU);
		}
		else
		{
			childCUs = ((HUProducerDestination)destination).getCreatedHUs(); // i think there will be just one, but no need to bother

		}

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		// get *the* MI HU_Item of 'tuHU'. There must be exactly one, otherwise, tuHU wouldn't exist here in the first place.
		final List<I_M_HU_Item> tuMaterialItem = handlingUnitsDAO.retrieveItems(tuHU)
				.stream()
				.filter(piItem -> X_M_HU_PI_Item.ITEMTYPE_Material.equals(piItem.getItemType()))
				.collect(Collectors.toList());
		Check.errorUnless(tuMaterialItem.size() == 1, "Param 'tuHU' does not have one 'MI' item; tuHU={}", tuHU);

		// finally do the attaching
		childCUs.forEach(cu -> {
			setParent(cu, tuMaterialItem.get(0));
		});
	}

	public void tuToExistingLU(
			final I_M_HU tuHU //
			, final BigDecimal qtyTU //
			, final I_M_HU luHU)
	{
		Preconditions.checkNotNull(tuHU, "Param 'tuHU' may not be null");
		Preconditions.checkNotNull(qtyTU, "Param 'qtyTU' may not be null");
		Preconditions.checkNotNull(luHU, "Param 'luHU' may not be null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final List<I_M_HU> tuHUsToAttachToLU;
		if (qtyTU.compareTo(getMaximumQtyTU(tuHU)) >= 0)
		{
			// qtyTU is so large that the complete tuHU will be dealt with
			if (handlingUnitsBL.isAggregateHU(tuHU))
			{
				// de-aggregate tuHU. we only want to add "real" TUs.
				// It might be the case that luHU only has "real" HUs already and in that case, we might be able to add an aggregate TU..
				// but it make this BL more complicated and i'm not sure we need it, or that it is even a good thing in terms of predictability for the user.
				tuHUsToAttachToLU = tuToNewTUs(tuHU, qtyTU, tuHU.isHUPlanningReceiptOwnerPM());
			}
			else
			{
				// just move huTU as-is
				tuHUsToAttachToLU = ImmutableList.of(tuHU);
			}
		}
		else
		{
			// create one or many new TUs for qtyTU
			tuHUsToAttachToLU = tuToNewTUs(tuHU, qtyTU, tuHU.isHUPlanningReceiptOwnerPM());
		}

		tuHUsToAttachToLU.forEach(tuToAttach -> {

			final I_M_HU_PI piOfChildHU = tuToAttach.getM_HU_PI_Version().getM_HU_PI();

			final I_M_HU_PI_Item parentPIItem = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(luHU, piOfChildHU, huContext);
			Check.errorIf(parentPIItem == null, "parentPIItem==null for parentHU={} and piOfChildHU={}", luHU, piOfChildHU);

			final I_M_HU_Item parentItem = handlingUnitsDAO.createHUItemIfNotExists(luHU, parentPIItem).getLeft();

			setParent(tuToAttach, parentItem);
		});
	}

	/**
	 * Split selected CU to new top level TUs
	 *
	 * @param cuRow cu row to split
	 * @param qtyCU quantity CU to split
	 * @param tuPIItemProductId to TU
	 * @param isOwnPackingMaterials
	 */
	public List<I_M_HU> cuToNewTUs(
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
						// forceAllocation = false; we want to create as many new TUs as are implied by the cuQty and the TUs' capacity
						huContext -> createCUAllocationRequest(huContext, cuProduct, cuUOM, qtyCU, false),
						destination)
				.withPropagateHUValues()
				.withTuPIItem(tuPIItemProduct.getM_HU_PI_Item())
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		return destination.getCreatedHUs();
	}

	/**
	 * Split a given number of TUs from current TU line to new TUs. This also has the effect of "de-aggregating" the given {@code sourceTuHU}.
	 * 
	 *
	 * @param tuHU the source TU to split from.
	 * @param qtyTU
	 * @param tuPIItemProduct
	 * @param isOwnPackingMaterials
	 */
	public List<I_M_HU> tuToNewTUs(
			final I_M_HU sourceTuHU,
			final BigDecimal qtyTU,
			final boolean isOwnPackingMaterials)
	{
		Preconditions.checkNotNull(sourceTuHU, "Param 'sourceTuHU' may not be null");
		Preconditions.checkNotNull(qtyTU, "Param 'qtyTU' may not be null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		if (qtyTU.compareTo(getMaximumQtyTU(sourceTuHU)) >= 0) // the caller wants to process the entire sourceTuHU
		{
			if (handlingUnitsDAO.retrieveParentItem(sourceTuHU) == null) // ..but there sourceTuHU is not attached to a parent, so there isn't anything to do at all.)
			{
				return Collections.emptyList();
			}
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			if (!handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				setParent(sourceTuHU, null);
				return ImmutableList.of(sourceTuHU);
			}
		}

		// note: as of now an aggregated TU needs a parent, so also if the user just wants fully to remove an aggregate TU from it's parent, we still need to split it.
		return tuToTopLevelHUs(sourceTuHU, qtyTU, null, isOwnPackingMaterials);
	}

	private void setParent(final I_M_HU childHU, final I_M_HU_Item parentItem)
	{
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		huTrxBL.createHUContextProcessorExecutor(huContext)
				.run(new IHUContextProcessor()
				{
					@Override
					public IMutableAllocationResult process(final IHUContext localHuContext)
					{
						Preconditions.checkNotNull(localHuContext, "Param 'localHuContext' may not be null");
						Services.get(ITrxManager.class).assertTrxNotNull(localHuContext);

						// Take it out from its parent
						huTrxBL.setParentHU(localHuContext,
								parentItem, // might be null
								childHU,
								true // destroyOldParentIfEmptyStorage
						);
						return NULL_RESULT; // we don't care about the result
					}
				});
	}

	/**
	 * Create a new LU-hierarchy and transfer stuff from the given {@code sourceTuHU}. The PI of the new TUs that are created below the new LU is determined from the given {@code sourceTuHU}.
	 * 
	 * @param sourceTuHU
	 * @param qtyTU
	 * @param luPI
	 * @param isOwnPackingMaterials
	 * @return
	 */
	public List<I_M_HU> tuToNewLUs(
			final I_M_HU sourceTuHU,
			final BigDecimal qtyTU,
			final I_M_HU_PI_Item luPIItem,
			final boolean isOwnPackingMaterials)
	{
		Preconditions.checkNotNull(sourceTuHU, "Param 'tuHU' may not be null");
		Preconditions.checkNotNull(qtyTU, "Param 'qtyTU' may not be null");
		Preconditions.checkNotNull(luPIItem, "Param 'luPI' may not be null");

		if (qtyTU.compareTo(getMaximumQtyTU(sourceTuHU)) >= 0 // the complete sourceTuHU shall be processed
				&& getMaximumQtyTU(sourceTuHU).compareTo(luPIItem.getQty()) <= 0 // the complete sourceTuHU fits onto one pallet
		)
		{
			// don't split; just create a new LU and "move" the TU
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

			// create the new LU
			final I_M_HU newLuHU = handlingUnitsDAO
					.createHUBuilder(huContext)
					.setC_BPartner(sourceTuHU.getC_BPartner())
					.setC_BPartner_Location_ID(sourceTuHU.getC_BPartner_Location_ID())
					.setM_Locator(sourceTuHU.getM_Locator())
					.setHUPlanningReceiptOwnerPM(isOwnPackingMaterials)
					.create(luPIItem.getM_HU_PI_Version());

			// store the old parent-item of sourceTuHU
			final I_M_HU_Item oldParentItemOfSourceTuHU = handlingUnitsDAO.retrieveParentItem(sourceTuHU); // needed in case sourceTuHU is an aggregate

			final I_M_HU_Item newParentItemOfSourceTuHU;

			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			if (handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				// get the existing HA-item from newLuHU
				newParentItemOfSourceTuHU = handlingUnitsDAO.retrieveItems(newLuHU).get(0);
				Check.errorUnless(X_M_HU_Item.ITEMTYPE_HUAggregate.equals(handlingUnitsBL.getItemType(newParentItemOfSourceTuHU)),
						"newLuHU's first M_HU_Item is not aggregate; newLuHU={}; first M_HU_Item={}", newLuHU, newParentItemOfSourceTuHU);
			}
			else
			{
				// create the new parent-item that will link sourceTuHU with newLuHU
				final I_M_HU_PI piOfChildHU = sourceTuHU.getM_HU_PI_Version().getM_HU_PI();
				final I_M_HU_PI_Item parentPIItem = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(newLuHU, piOfChildHU, huContext);
				Check.errorIf(parentPIItem == null, "parentPIItem==null for parentHU={} and piOfChildHU={}", newLuHU, piOfChildHU);
				newParentItemOfSourceTuHU = handlingUnitsDAO.createHUItemIfNotExists(newLuHU, parentPIItem).getLeft();
			}

			// assign sourceTuHU to newLuHU
			setParent(sourceTuHU, newParentItemOfSourceTuHU);

			// update the
			if (handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				final I_M_HU_Item haItemOfLU = handlingUnitsDAO.retrieveItems(newLuHU).get(0);
				haItemOfLU.setQty(oldParentItemOfSourceTuHU.getQty());
				haItemOfLU.setM_HU_PI_Item(oldParentItemOfSourceTuHU.getM_HU_PI_Item());
				InterfaceWrapperHelper.save(haItemOfLU);
			}

			return ImmutableList.of(newLuHU);
		}

		return tuToTopLevelHUs(sourceTuHU, qtyTU, luPIItem, isOwnPackingMaterials);
	}

	/**
	 * Split TU to top-level (either LU or new TU)
	 *
	 * @param sourceTuHU the source TU from which we split
	 * @param qtyTU the TU qty which we split. This is one factor to the actual cuQty
	 * @param tuPI the packing instruction for the TU level of the new hierarchy which we create.
	 * @param luPIItem may be {@code null}. If null, then the resulting top level HU will be a TU
	 * @param isOwnPackingMaterials
	 */
	private List<I_M_HU> tuToTopLevelHUs(
			final I_M_HU sourceTuHU,
			final BigDecimal qtyTU,
			final I_M_HU_PI_Item luPIItem,
			final boolean isOwnPackingMaterials)
	{
		Preconditions.checkNotNull(sourceTuHU, "Param 'tuHU' may not be null");
		Preconditions.checkNotNull(qtyTU, "Param 'qtyTU' may not be null");

		final List<IHUProductStorage> productStorages = retrieveAllProductStorages(sourceTuHU);

		final List<I_M_HU> createdTUs;

		// deal with the first of potentially many cuHUs and their storages
		{
			final IHUProductStorage firstProductStorage = productStorages.get(0);

			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

			final BigDecimal qtyOfStorage = Preconditions.checkNotNull(firstProductStorage.getQty(), "Qty of firstProductStorage may not be null; firstProductStorage=%s", firstProductStorage);

			final BigDecimal sourceQtyCUperTU; // will be used to get the overall cuQty to transfer, by multiplying with the given qtyTU
			if (handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				final I_M_HU_Item tuHUsParentItem = handlingUnitsDAO.retrieveParentItem(sourceTuHU); // can't be null because if is was isAggregateHU() would return false.
				final BigDecimal representedTUsCount = tuHUsParentItem.getQty();
				Preconditions.checkState(representedTUsCount.signum() > 0, "Param 'tuHU' is an aggregate HU whose M_HU_Item_Parent has a qty of %s; tuHU=%s; tuHU's M_HU_Item_Parent=%s", representedTUsCount, sourceTuHU, tuHUsParentItem);
				sourceQtyCUperTU = qtyOfStorage.divide(representedTUsCount);
			}
			else
			{
				sourceQtyCUperTU = qtyOfStorage;
			}
			final I_M_Product cuProduct = Preconditions.checkNotNull(firstProductStorage.getM_Product(), "M_Product of firstProductStorage may not be null; firstProductStorage=%s", firstProductStorage);
			final I_C_UOM cuUOM = Preconditions.checkNotNull(firstProductStorage.getC_UOM(), "C_UOM of firstProductStorage may not be null; firstProductStorage=%s", firstProductStorage);

			final LUTUProducerDestination destination = new LUTUProducerDestination();

			final I_M_HU_PI tuPI = handlingUnitsBL.getEffectivePIVersion(sourceTuHU).getM_HU_PI();
			destination.setTUPI(tuPI);
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

			final IHUPIItemProductDAO piipDAO = Services.get(IHUPIItemProductDAO.class);

			final List<I_M_HU_PI_Item> materialItems = handlingUnitsDAO
					.retrievePIItems(tuPI, sourceTuHU.getC_BPartner()).stream()
					.filter(i -> X_M_HU_PI_Item.ITEMTYPE_Material.equals(i.getItemType()))
					.collect(Collectors.toList());

			final I_M_HU_PI_Item_Product piip = piipDAO.retrievePIMaterialItemProduct(materialItems.get(0), sourceTuHU.getC_BPartner(), cuProduct, SystemTime.asDate());
			destination.addTUCapacity(cuProduct, piip.getQty(), cuUOM); // explicitly declaring capacity to make sure that all aggregate HUs have it

			HUSplitBuilderCoreEngine
					.of(huContext,
							sourceTuHU,
							// forceAllocation = false; we want to create as many new top level HUs as are implied by the cuQty and the HUs' capacity
							huContext -> createCUAllocationRequest(huContext, cuProduct, cuUOM, qtyTU.multiply(sourceQtyCUperTU), false),
							destination)
					.withPropagateHUValues()
					.withTuPIItem(piip.getM_HU_PI_Item()) // TODO if we already have the piip here, then add it directly, and not the M_HU_PI_Item
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
				cuToExistingTU(currentHuProductStorage.getM_HU(), qtyCU, createdTU);
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
}
