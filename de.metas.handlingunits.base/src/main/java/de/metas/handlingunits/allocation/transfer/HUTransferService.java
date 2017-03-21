package de.metas.handlingunits.allocation.transfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
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
import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
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
	private List<TableRecordReference> referencedObjects = Collections.emptyList();

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

	/**
	 * Optional; the given list contains references that can be turned into {@link IHUDocument} using the {@link IHUDocumentFactoryService}.
	 * They may be assigned to HUs that are given as parameters to this service's methods.
	 * It's required to use this method if the service works on HUs that are assigned to other records such as {@link I_M_ReceiptSchedule}s, because otherwise. those assignements are not updated correctly.
	 * 
	 * @param referencedObjects
	 * @return
	 */
	public HUTransferService withReferencedObjects(final List<TableRecordReference> referencedObjects)
	{
		this.referencedObjects = Preconditions.checkNotNull(referencedObjects, "Param 'referencedOjects' may noot be null");
		return this;
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
	 * Takes a quantity out of a TU <b>or</b> to splits one CU into two.
	 * 
	 * @param cuHU the currently selected source CU line
	 * @param qtyCU the CU-quantity to take out or split
	 */
	public List<I_M_HU> cuToNewCU(
			final I_M_HU cuHU,
			final BigDecimal qtyCU)
	{
		Preconditions.checkNotNull(cuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");

		if (qtyCU.compareTo(getMaximumQtyCU(cuHU)) >= 0)
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			final I_M_HU_Item cuParentItem = handlingUnitsDAO.retrieveParentItem(cuHU);
			if (cuParentItem == null)
			{
				// the caller wants to process the complete cuHU, but there is nothing to do because the cuHU is not attached to a parent.
				return Collections.emptyList();
			}
			else
			{
				// detach cuHU from its parent

				setParent(cuHU, null,
						// before
						localHuContext -> {
							final I_M_HU oldTuHU = handlingUnitsDAO.retrieveParent(cuHU);
							final I_M_HU oldLuHU = oldTuHU == null ? null : handlingUnitsDAO.retrieveParent(cuHU);
							updateAllocation(oldLuHU, oldTuHU, cuHU, qtyCU, true, localHuContext);
						},
						// after
						localHuContext -> {
							final I_M_HU newTuHU = handlingUnitsDAO.retrieveParent(cuHU);
							final I_M_HU newLuHU = newTuHU == null ? null : handlingUnitsDAO.retrieveParent(cuHU);
							updateAllocation(newLuHU, newTuHU, cuHU, qtyCU, false, localHuContext);
						});
				return ImmutableList.of(cuHU);
			}
		}

		final HUProducerDestination destination = HUProducerDestination.ofVirtualPI();
		final IHUProductStorage singleProductStorage = getSingleProductStorage(cuHU);
		HUSplitBuilderCoreEngine.of(huContext, cuHU,
				// forceAllocation = false; no need, because destination has no capacity constraints
				huContext ->

				createCUAllocationRequest(huContext, singleProductStorage.getM_Product(), singleProductStorage.getC_UOM(), qtyCU, false),
				destination)
				.withPropagateHUValues()
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		return destination.getCreatedHUs();
	}

	private IHUProductStorage getSingleProductStorage(I_M_HU cuHU)
	{
		final List<IHUProductStorage> storages = huContext.getHUStorageFactory().getStorage(cuHU).getProductStorages();
		Check.errorUnless(storages.size() == 1, "Param' cuHU' needs to have *one* storage; storages={}; cuHU={};", storages, cuHU);
		return storages.get(0);
	}

	/**
	 * Similar to {@link #cuToNewTUs(I_M_HU, BigDecimal, I_M_HU_PI_Item_Product, boolean)} , but the destination TU already exists
	 * <p>
	 * <b>Important:</b> the user is allowed to exceed the TU capacity which was configured in metasfresh! No new TUs will be created.<br>
	 * That's because if a user manages to squeeze something into a box in reality, it is mandatory that he/she can do the same in metasfresh, no matter what the master data says.
	 * <p>
	 * 
	 * @param sourceCuHU the source CU to be split or joined
	 * @param qtyCU the CU-quantity to join or split
	 * @param targetTuHU the target TU
	 */
	public void cuToExistingTU(
			final I_M_HU sourceCuHU,
			final BigDecimal qtyCU,
			final I_M_HU targetTuHU)
	{
		Preconditions.checkNotNull(sourceCuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final IAllocationDestination destination;
		if (handlingUnitsBL.isAggregateHU(targetTuHU))
		{
			// we will load directly to the given tuHU which is actually a VHU
			destination = HUListAllocationSourceDestination.of(targetTuHU);
		}
		else
		{
			// we are later going to attach something as a child to the given 'tuHU'
			if (qtyCU.compareTo(getMaximumQtyCU(sourceCuHU)) >= 0)
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

		final IHUProductStorage singleProductStorage = getSingleProductStorage(sourceCuHU);

		if (destination != null)
		{
			HUSplitBuilderCoreEngine
					.of(
							huContext,
							sourceCuHU,
							// forceAllocation = true; we don't want to get bothered by capacity constraint, even if the destination *probably* doesn't have any to start with
							huContext -> createCUAllocationRequest(huContext,
									singleProductStorage.getM_Product(),
									singleProductStorage.getC_UOM(),
									qtyCU,
									true),
							destination)
					.withPropagateHUValues()
					.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
					.performSplit();
		}

		if (handlingUnitsBL.isAggregateHU(targetTuHU))
		{
			return; // we are done; no attaching
		}

		// we attach the
		final List<I_M_HU> childCUs;
		if (destination == null)
		{
			childCUs = ImmutableList.of(sourceCuHU);
		}
		else
		{
			// destination must be the HUProducerDestination we created further up, otherwise we would already have returned
			childCUs = ((HUProducerDestination)destination).getCreatedHUs(); // i think there will be just one, but no need to bother
		}

		// get *the* MI HU_Item of 'tuHU'. There must be exactly one, otherwise, tuHU wouldn't exist here in the first place.
		final List<I_M_HU_Item> tuMaterialItem = handlingUnitsDAO.retrieveItems(targetTuHU)
				.stream()
				.filter(piItem -> X_M_HU_PI_Item.ITEMTYPE_Material.equals(piItem.getItemType()))
				.collect(Collectors.toList());
		Check.errorUnless(tuMaterialItem.size() == 1, "Param 'tuHU' does not have one 'MI' item; tuHU={}", targetTuHU);

		// finally do the attaching

		// iterate the child CUs and set their parent item
		childCUs.forEach(newChildCU -> {
			setParent(newChildCU,
					tuMaterialItem.get(0),

					// before the childHU's parent item is set,
					localHuContext -> {
						final I_M_HU oldParentTU = handlingUnitsDAO.retrieveParent(sourceCuHU);
						final I_M_HU oldParentLU = oldParentTU == null ? null : handlingUnitsDAO.retrieveParent(oldParentTU);
						updateAllocation(oldParentLU, oldParentTU, sourceCuHU, qtyCU, true, localHuContext);
					},

					// after the childHU's parent item is set,
					localHuContext -> {
						final I_M_HU newParentTU = handlingUnitsDAO.retrieveParent(newChildCU);
						final I_M_HU newParentLU = newParentTU == null ? null : handlingUnitsDAO.retrieveParent(newParentTU);
						updateAllocation(newParentLU, newParentTU, newChildCU, qtyCU, false, localHuContext);
					});
		});
	}

	/**
	 * 
	 * @param luHU
	 * @param tuHU
	 * @param cuHU if {@code null}, then all cuHus of the given tuHU are iterated.
	 * @param qtyCU ignored if cuHU is {@code null} may be null, then it's also ignored. If ignored, then this method uses the respective CU's storage's Qty instead.
	 * @param negateQtyCU
	 * @param localHuContext
	 */
	private void updateAllocation(final I_M_HU luHU,
			final I_M_HU tuHU,
			final I_M_HU cuHU,
			final BigDecimal qtyCU,
			final boolean negateQtyCU,
			final IHUContext localHuContext)
	{
		final List<I_M_HU> cuHUsToUse;
		if (cuHU == null)
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			cuHUsToUse = handlingUnitsDAO.retrieveIncludedHUs(tuHU);
		}
		else
		{
			cuHUsToUse = ImmutableList.of(cuHU);
		}

		final IHUDocumentFactoryService huDocumentFactoryService = Services.get(IHUDocumentFactoryService.class);

		for (final I_M_HU currentCuHU : cuHUsToUse)
		{
			final IHUProductStorage singleProductStorage = getSingleProductStorage(currentCuHU);

			final BigDecimal factor = negateQtyCU ? BigDecimal.ONE.negate() : BigDecimal.ONE;
			final BigDecimal qtyToUse;
			if (cuHU == null || qtyCU == null)
			{
				qtyToUse = singleProductStorage.getQty().multiply(factor);
			}
			else
			{
				qtyToUse = qtyCU.multiply(factor);
			}

			for (final TableRecordReference ref : referencedObjects)
			{
				final List<IHUDocument> huDocuments = huDocumentFactoryService.createHUDocuments(localHuContext.getCtx(), ref.getTableName(), ref.getRecord_ID());
				for (final IHUDocument huDocument : huDocuments)
				{
					final List<IHUDocumentLine> huDocumentLines = huDocument.getLines();
					final Optional<IHUDocumentLine> huDocumentLine = huDocumentLines.stream()
							.filter(l -> l.getM_Product() != null && l.getM_Product().getM_Product_ID() == singleProductStorage.getM_Product().getM_Product_ID())
							.findFirst();
					if (huDocumentLine.isPresent())
					{
						final IHUAllocations huAllocations = huDocumentLine.get().getHUAllocations();
						huAllocations.allocate(luHU, tuHU, currentCuHU, qtyToUse, singleProductStorage.getC_UOM(), false);
					}
				}
			}
		}
	}

	/**
	 * Similar to {@link #TU_To_NewLUs}, but the destination LU already exists (selectable as process parameter).<br>
	 * <b>Important:</b> the user is allowed to exceed the LU TU-capacity which was configured in metasfresh! No new LUs will be created.<br>
	 * That's because if a user manages to jenga another box onto a loaded pallet in reality, it is mandatory that he/she can do the same in metasfresh, no matter what the master data says.
	 * <p>
	 * <b>Also, please note that an aggregate TU is "de-aggregated" before it is added to the LU.</b>
	 * 
	 * @param sourceTuHU the source TU to process. Can be an aggregated HU and therefore represent many homogeneous TUs
	 * @param qtyTU the number of TUs to join or split one the target LU
	 * @param luHU the target LU
	 */
	public void tuToExistingLU(
			final I_M_HU sourceTuHU //
			, final BigDecimal qtyTU //
			, final I_M_HU luHU)
	{
		Preconditions.checkNotNull(sourceTuHU, "Param 'tuHU' may not be null");
		Preconditions.checkNotNull(qtyTU, "Param 'qtyTU' may not be null");
		Preconditions.checkNotNull(luHU, "Param 'luHU' may not be null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final List<I_M_HU> tuHUsToAttachToLU;
		if (qtyTU.compareTo(getMaximumQtyTU(sourceTuHU)) >= 0)
		{
			// qtyTU is so large that the complete tuHU will be dealt with
			if (handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				// de-aggregate tuHU. we only want to add "real" TUs.
				// It might be the case that luHU only has "real" HUs already and in that case, we might be able to add an aggregate TU..
				// but it make this BL more complicated and i'm not sure we need it, or that it is even a good thing in terms of predictability for the user.
				tuHUsToAttachToLU = tuToNewTUs(sourceTuHU, qtyTU, sourceTuHU.isHUPlanningReceiptOwnerPM());
			}
			else
			{
				// just move huTU as-is
				tuHUsToAttachToLU = ImmutableList.of(sourceTuHU);
			}
		}
		else
		{
			// create one or many new TUs for qtyTU
			tuHUsToAttachToLU = tuToNewTUs(sourceTuHU, qtyTU, sourceTuHU.isHUPlanningReceiptOwnerPM());
		}

		tuHUsToAttachToLU.forEach(tuToAttach -> {

			final I_M_HU_PI piOfChildHU = tuToAttach.getM_HU_PI_Version().getM_HU_PI();

			final I_M_HU_PI_Item parentPIItem = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(luHU, piOfChildHU, huContext);
			Check.errorIf(parentPIItem == null, "parentPIItem==null for parentHU={} and piOfChildHU={}", luHU, piOfChildHU);

			final I_M_HU_Item parentItem = handlingUnitsDAO.createHUItemIfNotExists(luHU, parentPIItem).getLeft();

			setParent(tuToAttach,
					parentItem,
					localHuContext -> {
						// before
						final I_M_HU oldParentLU = handlingUnitsDAO.retrieveParent(tuToAttach);
						updateAllocation(oldParentLU, tuToAttach, null, null, true, localHuContext);
					},
					localHuContext -> {
						final I_M_HU newParentLU = handlingUnitsDAO.retrieveParent(tuToAttach);
						updateAllocation(newParentLU, tuToAttach, null, null, true, localHuContext);
					});
		});
	}

	/**
	 * Creates one or more TUs (depending on the given quantity and the TU capacity) and joins, splits and/or distributes the source CU to them.<br>
	 * If the user goes with the full quantity of the source CU and if the source CU fits into one TU, then it remains unchanged.
	 * 
	 * @param cuHU the currently selected source CU line
	 * @param qtyCU the CU-quantity to join or split
	 * @param tuPIItemProduct the PI item product to specify both the PI and capacity of the target TU
	 * @param isOwnPackingMaterials
	 */
	public List<I_M_HU> cuToNewTUs(
			final I_M_HU cuHU,
			final BigDecimal qtyCU,
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final boolean isOwnPackingMaterials)
	{
		Preconditions.checkNotNull(cuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");
		Preconditions.checkNotNull(tuPIItemProduct, "Param 'tuPIItemProduct' may not be null");

		final LUTUProducerDestination destination = new LUTUProducerDestination();
		destination.setTUPI(tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI());
		destination.setIsHUPlanningReceiptOwnerPM(isOwnPackingMaterials);
		destination.setNoLU();

		final List<IHUProductStorage> storages = huContext.getHUStorageFactory().getStorage(cuHU).getProductStorages();
		Check.errorUnless(storages.size() == 1, "Param' cuHU' needs to have *one* storage; storages={}; cuHU={};", storages, cuHU);
		HUSplitBuilderCoreEngine
				.of(huContext,
						cuHU,
						// forceAllocation = false; we want to create as many new TUs as are implied by the cuQty and the TUs' capacity
						huContext -> createCUAllocationRequest(huContext, storages.get(0).getM_Product(), storages.get(0).getC_UOM(), qtyCU, false),
						destination)
				.withPropagateHUValues()
				.withTuPIItem(tuPIItemProduct.getM_HU_PI_Item())
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		return destination.getCreatedHUs();
	}

	/**
	 * Takes a TU off a LU or splits one TU into two. This also has the effect of "de-aggregating" the given {@code sourceTuHU}.<br>
	 * The resulting TUs will always have the same PI as the source TU.
	 * 
	 * @param sourceTuHU he source TU to process. Can be an aggregated HU and therefore represent many homogeneous TUs
	 * @param qtyTU the number of TUs to take off or split
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

		// get cuHU's old parent (if any) for later usage, before the changes start

		if (qtyTU.compareTo(getMaximumQtyTU(sourceTuHU)) >= 0) // the caller wants to process the entire sourceTuHU
		{
			if (handlingUnitsDAO.retrieveParentItem(sourceTuHU) == null) // ..but there sourceTuHU is not attached to a parent, so there isn't anything to do at all.)
			{
				return Collections.emptyList();
			}
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			if (!handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				setParent(sourceTuHU, null,
						localHuContext -> {

							final I_M_HU oldParentLU = handlingUnitsDAO.retrieveParent(sourceTuHU);
							updateAllocation(oldParentLU, sourceTuHU, sourceTuHU, null, true, localHuContext);
						},
						localHuContext -> {

							final I_M_HU newParentLU = handlingUnitsDAO.retrieveParent(sourceTuHU);
							updateAllocation(newParentLU, sourceTuHU, sourceTuHU, null, false, localHuContext);
						});
				return ImmutableList.of(sourceTuHU);
			}
		}

		// note: as of now an aggregated TU needs a parent, so also if the user just wants fully to remove an aggregate TU from it's parent, we still need to split it.
		return tuToTopLevelHUs(sourceTuHU, qtyTU, null, isOwnPackingMaterials);
	}

	private void setParent(final I_M_HU childHU,
			final I_M_HU_Item parentItem,
			final Consumer<IHUContext> beforeParentChange,
			final Consumer<IHUContext> afterParentChange)
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

						beforeParentChange.accept(localHuContext);

						// Take it out from its parent
						huTrxBL.setParentHU(localHuContext,
								parentItem, // might be null
								childHU,
								true // destroyOldParentIfEmptyStorage
						);

						afterParentChange.accept(localHuContext);

						return NULL_RESULT; // we don't care about the result
					}
				});
	}

	/**
	 * Creates a new LU and joins or splits a source TU to it. If the user goes with the full quantity of the (aggregate) source TU(s), and if if all fits on one LU, then the source remains unchanged and is only joined.<br>
	 * Otherwise, the source is split and distributed over many LUs.
	 * 
	 * @param sourceTuHU the source TU line to process. Can be an aggregated HU and therefore represent many homogeneous TUs.
	 * @param qtyTU the number of TUs to join or split onto the destination LU(s).
	 * @param luPIItem the LU's PI item (with type "HU") that specifies both the LUs' PI and the number of TUs that fit on one LU.
	 * @param isOwnPackingMaterials
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

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		if (qtyTU.compareTo(getMaximumQtyTU(sourceTuHU)) >= 0 // the complete sourceTuHU shall be processed
				&& getMaximumQtyTU(sourceTuHU).compareTo(luPIItem.getQty()) <= 0 // the complete sourceTuHU fits onto one pallet
		)
		{
			// don't split; just create a new LU and "move" the TU

			// create the new LU
			final I_M_HU newLuHU = handlingUnitsDAO
					.createHUBuilder(huContext)
					.setC_BPartner(sourceTuHU.getC_BPartner())
					.setC_BPartner_Location_ID(sourceTuHU.getC_BPartner_Location_ID())
					.setM_Locator(sourceTuHU.getM_Locator())
					.setHUPlanningReceiptOwnerPM(isOwnPackingMaterials)
					.create(luPIItem.getM_HU_PI_Version());

			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

			// get or create the new parent item
			final I_M_HU_Item newParentItemOfSourceTuHU;
			{
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
			}

			// store the old parent-item of sourceTuHU
			final I_M_HU_Item oldParentItemOfSourceTuHU = handlingUnitsDAO.retrieveParentItem(sourceTuHU); // needed in case sourceTuHU is an aggregate

			// assign sourceTuHU to newLuHU
			setParent(sourceTuHU,
					newParentItemOfSourceTuHU,
					localHuContext -> {
						final I_M_HU oldParentLu = handlingUnitsDAO.retrieveParent(sourceTuHU);
						updateAllocation(oldParentLu, sourceTuHU, null, null, true, localHuContext);
					},
					localHuContext -> {
						final I_M_HU newParentLu = handlingUnitsDAO.retrieveParent(sourceTuHU);
						updateAllocation(newParentLu, sourceTuHU, null, null, false, localHuContext);
					});

			// update the haItemOfLU if needed
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
					.withTuPIItem(piip.getM_HU_PI_Item())
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
