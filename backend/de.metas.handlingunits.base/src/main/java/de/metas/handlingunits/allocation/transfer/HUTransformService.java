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

package de.metas.handlingunits.allocation.transfer;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.spi.impl.AggregateHUTrxListener;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilderCoreEngine;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.CoalesceUtil;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;

import static java.math.BigDecimal.ZERO;

/**
 * This class contains business logic run by clients when they transform HUs.
 * Use {@link #newInstance(IHUContext)} to obtain an instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 * task https://github.com/metasfresh/metasfresh-webui/issues/181
 */
public class HUTransformService
{
	public static HUTransformService newInstance()
	{
		return builder().build();
	}

	public static HUTransformService newInstance(@NonNull final IHUContext huContext)
	{
		return builderForHUcontext().huContext(huContext).build();
	}

	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUDocumentFactoryService huDocumentFactoryService = Services.get(IHUDocumentFactoryService.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	//
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IHUContext huContext;
	private final ImmutableList<TableRecordReference> referencedObjects;

	/**
	 * Uses {@link IHUContextFactory#createMutableHUContext(Properties, String)} with the given {@code ctx} and {@code trxName} and returns a new {@link HUTransformService} instance with that huContext.
	 *
	 * @param ctx               optional
	 * @param trxName           optional
	 * @param referencedObjects Optional; the given list contains references that can be turned into {@link IHUDocument}s using the {@link IHUDocumentFactoryService}.
	 *                          They may be assigned to HUs that are given as parameters to this service's methods.
	 *                          It's required to use this method if the service works on HUs that are assigned to other records such as {@link I_M_ReceiptSchedule}s, because otherwise. those assignments are not updated correctly.
	 */
	@Builder
	private HUTransformService(
			@Nullable final Properties ctx,
			@Nullable final String trxName,
			@Nullable final EmptyHUListener emptyHUListener,
			@Nullable final List<TableRecordReference> referencedObjects)
	{
		this.referencedObjects = referencedObjects != null ? ImmutableList.copyOf(referencedObjects) : ImmutableList.of();

		final Properties effectiveCtx = ctx != null ? ctx : Env.getCtx();
		final String effectiveTrxName = CoalesceUtil.coalesce(trxName, ITrx.TRXNAME_ThreadInherited);
		final IMutableHUContext mutableHUContext = Services.get(IHUContextFactory.class).createMutableHUContext(effectiveCtx, effectiveTrxName);
		if (emptyHUListener != null)
		{
			mutableHUContext.addEmptyHUListener(emptyHUListener);
		}
		huContext = mutableHUContext;
	}

	/**
	 * When running unit tests, then use this builder to get your instance. Pass the HUTestHelper's getHUContext() result to it, to avoid transactional trouble.
	 *
	 */
	@Builder(builderClassName = "BuilderForHUcontext", builderMethodName = "builderForHUcontext")
	private HUTransformService(@NonNull final IHUContext huContext,
			@Nullable final List<TableRecordReference> referencedObjects)
	{
		this.referencedObjects = referencedObjects != null ? ImmutableList.copyOf(referencedObjects) : ImmutableList.of();
		this.huContext = huContext;
	}

	private List<TableRecordReference> getReferencedObjects()
	{
		// TODO: in case there were no referenced objects, i think we shall search & retrieve all the referenced documents/lines
		return referencedObjects;
	}

	private IAllocationRequest createCUAllocationRequest(
			@NonNull final IHUContext huContext,
			@NonNull final ProductId cuProductId,
			@NonNull final Quantity qtyCU,
			final boolean forceAllocation)
	{
		//
		// Create allocation request for the quantity user entered
		final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(huContext,
				cuProductId,
				qtyCU,
				SystemTime.asZonedDateTime(),
				null, // referenced model
				forceAllocation);

		if (allocationRequest.isZeroQty())
		{
			throw new AdempiereException("@QtyCU@ shall be greather than zero");
		}

		return allocationRequest;
	}

	public BigDecimal getMaximumQtyTU(@NonNull final I_M_HU tu)
	{
		if (handlingUnitsBL.isAggregateHU(tu))
		{
			return handlingUnitsDAO.retrieveParentItem(tu).getQty();
		}
		return BigDecimal.ONE;
	}

	public Quantity getMaximumQtyCU(@NonNull final I_M_HU cu, @NonNull final I_C_UOM uom)
	{

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IHUStorage storage = storageFactory.getStorage(cu);

		return storage.getQtyForProductStorages(uom);
	}

	/**
	 * Takes a quantity out of a TU <b>or</b> splits one CU into two.
	 *
	 * @param cuHU  the currently selected source CU line
	 * @param qtyCU the CU-quantity to take out or split
	 * @return the newly created CU.
	 */
	public List<I_M_HU> cuToNewCU(
			@NonNull final I_M_HU cuHU,
			@NonNull final Quantity qtyCU)
	{
		Check.assume(qtyCU.signum() > 0, "Paramater qtyCU={} needs to be >0; (source-)cuHU={}", qtyCU, cuHU);
		return cuToNewCU0(cuHU, qtyCU, false);
	}

	/**
	 * @return the now-standalone CUs, also if it was already standalone to start with.
	 */
	private List<I_M_HU> cuToNewCU0(
			@NonNull final I_M_HU cuOrAggregateHU,
			@NonNull final Quantity qtyCU,
			final boolean keepCUsUnderSameParent)
	{
		Check.assume(qtyCU.signum() > 0, "Paramater qtyCU={} needs to be >0; (source-)cuOrAggregateHU={}", qtyCU, cuOrAggregateHU);

		final boolean qtyCuExceedsCuHU = qtyCU.compareTo(getMaximumQtyCU(cuOrAggregateHU, qtyCU.getUOM())) >= 0;
		final boolean huIsCU = !handlingUnitsBL.isAggregateHU(cuOrAggregateHU);

		if (qtyCuExceedsCuHU && huIsCU)
		{
			// deal with the complete cuHU, i.e. no partial quantity will remain at the source.
			final I_M_HU_Item cuParentItem = handlingUnitsDAO.retrieveParentItem(cuOrAggregateHU);
			if (cuParentItem == null)
			{
				// the caller wants to process the complete cuHU, but there is nothing to do because the cuHU is not attached to a parent.
				return ImmutableList.of(cuOrAggregateHU);
			}
			else
			{
				if (!keepCUsUnderSameParent)
				{
					// detach cuHU from its parent
					setParent(cuOrAggregateHU, null,
							// before
							localHuContext -> {
								final I_M_HU oldTuHU = handlingUnitsDAO.retrieveParent(cuOrAggregateHU);
								final I_M_HU oldLuHU = oldTuHU == null ? null : handlingUnitsDAO.retrieveParent(cuOrAggregateHU);
								updateAllocation(oldLuHU, oldTuHU, cuOrAggregateHU, qtyCU, true, localHuContext);
							},
							// after
							localHuContext -> {
								final I_M_HU newTuHU = handlingUnitsDAO.retrieveParent(cuOrAggregateHU);
								final I_M_HU newLuHU = newTuHU == null ? null : handlingUnitsDAO.retrieveParent(cuOrAggregateHU);
								updateAllocation(newLuHU, newTuHU, cuOrAggregateHU, qtyCU, false, localHuContext);
							});
				}
				return ImmutableList.of(cuOrAggregateHU);
			}
		}

		// we split even if cuOrAggregateHU's qty is equalt to qtyCU, because we want a CU without packaging; not an aggregated TU
		final HUProducerDestination destination = HUProducerDestination.ofVirtualPI();
		final IHUProductStorage singleProductStorage = getSingleProductStorage(cuOrAggregateHU);
		HUSplitBuilderCoreEngine.builder()
				.huContextInitital(huContext)
				.huToSplit(cuOrAggregateHU)
				.requestProvider(huContext -> createCUAllocationRequest(
						huContext,
						singleProductStorage.getProductId(),
						qtyCU,
						false) // forceAllocation = false; no need, because destination has no capacity constraints
				)
				.destination(destination)
				.build()
				.withPropagateHUValues()
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		final List<I_M_HU> createdHUs = destination.getCreatedHUs();
		if (keepCUsUnderSameParent)
		{
			final I_M_HU parentOfSourceCU = handlingUnitsDAO.retrieveParent(cuOrAggregateHU);
			if (parentOfSourceCU != null)
			{
				addCUsToTU(createdHUs, parentOfSourceCU);
			}
		}

		return createdHUs;
	}

	private IHUProductStorage getSingleProductStorage(@NonNull final I_M_HU cuHU)
	{
		final List<IHUProductStorage> storages = huContext.getHUStorageFactory().getStorage(cuHU).getProductStorages();
		Check.errorUnless(storages.size() == 1, "Param' cuHU' needs to have *one* storage; storages={}; cuHU={};", storages, cuHU);
		return storages.get(0);
	}

	/**
	 * Similar to {@link #cuToNewTUs(I_M_HU, Quantity, I_M_HU_PI_Item_Product, boolean)} , but the destination TU already exists
	 * <p>
	 * <b>Important:</b> the user is allowed to exceed the TU capacity which was configured in metasfresh! No new TUs will be created.<br>
	 * That's because if a user manages to squeeze something into a box in reality, it is mandatory that he/she can do the same in metasfresh, no matter what the master data says.
	 * <p>
	 *
	 * @param sourceCuHU the source CU to be split or joined
	 * @param qtyCU      the CU-quantity to join or split
	 * @param targetTuHU the target TU
	 * @return the CUs that were created
	 */
	public List<I_M_HU> cuToExistingTU(
			@NonNull final I_M_HU sourceCuHU,
			@NonNull final Quantity qtyCU,
			@NonNull final I_M_HU targetTuHU)
	{
		// NOTE: because this method does several non-atomic operations it is important to glue them together in a transaction
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> cuToExistingTU_InTrx(sourceCuHU, qtyCU, targetTuHU));
	}

	private List<I_M_HU> cuToExistingTU_InTrx(
			@NonNull final I_M_HU sourceCuHU,
			@NonNull final Quantity qtyCU,
			@NonNull final I_M_HU targetTuHU)
	{
		final IAllocationDestination destination;
		final boolean isTargetAggregatedTU = handlingUnitsBL.isAggregateHU(targetTuHU);
		if (isTargetAggregatedTU)
		{
			// we will load directly to the given tuHU which is actually a VHU
			destination = HUListAllocationSourceDestination.of(targetTuHU);
		}
		else
		{
			// we are later going to attach something as a child to the given 'tuHU'
			if (qtyCU.compareTo(getMaximumQtyCU(sourceCuHU, qtyCU.getUOM())) >= 0)
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
			HUSplitBuilderCoreEngine.builder()
					.huContextInitital(huContext)
					.huToSplit(sourceCuHU)
					.requestProvider(huContext -> createCUAllocationRequest(huContext,
							singleProductStorage.getProductId(),
							qtyCU,
							true /* forceAllocation */))
					.destination(destination)
					.build()
					.withPropagateHUValues()
					.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
					.performSplit();
		}

		if (isTargetAggregatedTU)
		{
			return ImmutableList.of(); // we are done; no attaching
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

		addCUsToTU(childCUs, targetTuHU);

		return childCUs;
	}

	/**
	 * Update {@link IHUAllocations}. Currently know examples are receipt schedule allocations and shipment schedule allocations.
	 *
	 * @param cuHU  if {@code null}, then all cuHus of the given tuHU are iterated.
	 * @param qtyCU ignored if cuHU is {@code null} may be null, then it's also ignored. If ignored, then this method uses the respective CU's storage's Qty instead.
	 */
	private void updateAllocation(
			@Nullable final I_M_HU luHU,
			@Nullable final I_M_HU tuHU,
			@Nullable final I_M_HU cuHU,
			@Nullable final Quantity qtyCU,
			final boolean negateQtyCU,
			final IHUContext localHuContext)
	{
		final List<I_M_HU> cuHUsToUse;
		if (cuHU == null)
		{
			Check.assumeNotNull(tuHU, "tuHU shall not be null");
			cuHUsToUse = handlingUnitsDAO.retrieveIncludedHUs(tuHU);
		}
		else
		{
			cuHUsToUse = ImmutableList.of(cuHU);
		}

		for (final I_M_HU currentCuHU : cuHUsToUse)
		{
			final IHUProductStorage singleProductStorage = getSingleProductStorage(currentCuHU);

			final BigDecimal factor = negateQtyCU ? BigDecimal.ONE.negate() : BigDecimal.ONE;
			final Quantity qtyToUse;
			if (cuHU == null || qtyCU == null)
			{
				qtyToUse = singleProductStorage.getQty().multiply(factor);
			}
			else
			{
				qtyToUse = qtyCU.multiply(factor);
			}

			final Quantity catchWeightOrNull;
			final IAttributeStorage attributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(currentCuHU);
			final IWeightable weightable = Weightables.wrap(attributeStorage);
			if (weightable != null)
			{
				catchWeightOrNull = Quantity.of(weightable.getWeightNet().multiply(factor), weightable.getWeightNetUOM());
			}
			else
			{
				catchWeightOrNull = null;
			}

			for (final TableRecordReference ref : getReferencedObjects())
			{
				final List<IHUDocument> huDocuments = huDocumentFactoryService.createHUDocuments(localHuContext.getCtx(), ref.getTableName(), ref.getRecord_ID());
				for (final IHUDocument huDocument : huDocuments)
				{
					final List<IHUDocumentLine> huDocumentLines = huDocument.getLines();
					final Optional<IHUDocumentLine> huDocumentLine = huDocumentLines.stream()
							.filter(l -> l.getProductId() != null && Objects.equals(l.getProductId(), singleProductStorage.getProductId()))
							.findFirst();
					if (huDocumentLine.isPresent())
					{
						final StockQtyAndUOMQty qtyToAllocate = StockQtyAndUOMQtys.createConvert(
								qtyToUse,
								huDocumentLine.get().getProductId(),
								catchWeightOrNull);

						final IHUAllocations huAllocations = huDocumentLine.get().getHUAllocations();
						huAllocations.allocate(luHU, tuHU, currentCuHU, qtyToAllocate, false/* deleteOldTUAllocations */);
					}
				}
			}
		}
	}

	/**
	 * Similar to {@link #tuToNewLUs}, but the destination LU already exists (selectable as process parameter).<br>
	 * <b>Important:</b> the user is allowed to exceed the LU TU-capacity which was configured in metasfresh! No new LUs will be created.<br>
	 * That's because if a user manages to jenga another box onto a loaded pallet in reality, it is mandatory that he/she can do the same in metasfresh, no matter what the master data says.
	 * <p>
	 * <b>Also, please note that an aggregate TU is "de-aggregated" before it is added to the LU.</b>
	 *
	 * @param sourceTuHU the source TU to process. Can be an aggregated HU and therefore represent many homogeneous TUs
	 * @param qtyTU      the number of TUs to join or split one the target LU
	 * @param luHU       the target LU
	 * @return the TUs that were added to {@code luHU}
	 */
	public List<I_M_HU> tuToExistingLU(
			@NonNull final I_M_HU sourceTuHU,
			@NonNull final BigDecimal qtyTU,
			@NonNull final I_M_HU luHU)
	{
		final List<I_M_HU> tuHUsToAttachToLU;

		final boolean qtyTuExceedsSourceTuHU = qtyTU.compareTo(getMaximumQtyTU(sourceTuHU)) >= 0;
		if (qtyTuExceedsSourceTuHU)
		{
			// qtyTU is so large that the complete tuHU will be dealt with
			if (handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				// de-aggregate tuHU. we only want to add "real" TUs.
				// It might be the case that luHU only has "real" HUs already and in that case, we might be able to add an aggregate TU..
				// but it make this BL more complicated and i'm not sure we need it, or that it is even a good thing in terms of predictability for the user.
				tuHUsToAttachToLU = tuToNewTUs(sourceTuHU, qtyTU);
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
			tuHUsToAttachToLU = tuToNewTUs(sourceTuHU, qtyTU);
		}

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		tuHUsToAttachToLU.forEach(tuToAttach -> {
			final I_M_HU_PI piOfChildHU = handlingUnitsBL.getPI(tuToAttach);

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
						updateAllocation(newParentLU, tuToAttach, null, null, false, localHuContext);
					});
		});

		return tuHUsToAttachToLU;
	}

	/**
	 * Creates one or more TUs (depending on the given quantity and the TU capacity) and joins, splits and/or distributes the source CU to them.<br>
	 * If the user goes with the full quantity of the source CU and if the source CU fits into one TU, then it remains unchanged.
	 *
	 * @param cuHU                  the currently selected source CU line
	 * @param qtyCU                 the CU-quantity to join or split
	 * @param tuPIItemProduct       the PI item product to specify both the PI and capacity of the target TU
	 * @param isOwnPackingMaterials
	 */
	public List<I_M_HU> cuToNewTUs(
			final I_M_HU cuHU,
			final Quantity qtyCU,
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final boolean isOwnPackingMaterials)
	{
		Preconditions.checkNotNull(cuHU, "Param 'cuHU' may not be null");
		Preconditions.checkNotNull(qtyCU, "Param 'qtyCU' may not be null");
		Preconditions.checkNotNull(tuPIItemProduct, "Param 'tuPIItemProduct' may not be null");

		final LUTUProducerDestination destination = new LUTUProducerDestination();
		destination.setTUPI(tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI());
		destination.setIsHUPlanningReceiptOwnerPM(isOwnPackingMaterials);

		// gh #1759: explicitly take the capacity from the tuPIItemProduct which the user selected
		final ProductId productId = ProductId.ofRepoId(tuPIItemProduct.getM_Product_ID());
		final I_C_UOM uom = IHUPIItemProductBL.extractUOMOrNull(tuPIItemProduct);
		final Capacity capacity = Services.get(IHUCapacityBL.class).getCapacity(tuPIItemProduct, productId, uom);
		destination.addCUPerTU(capacity);

		destination.setNoLU();

		final List<IHUProductStorage> storages = huContext.getHUStorageFactory().getStorage(cuHU).getProductStorages();
		Check.errorUnless(storages.size() == 1, "Param' cuHU' needs to have *one* storage; storages={}; cuHU={};", storages, cuHU);
		HUSplitBuilderCoreEngine.builder()
				.huContextInitital(huContext)
				.huToSplit(cuHU)
				// forceAllocation = false; we want to create as many new TUs as are implied by the cuQty and the TUs' capacity
				.requestProvider(huContext -> createCUAllocationRequest(huContext, storages.get(0).getProductId(), qtyCU, false))
				.destination(destination)
				.build()
				.withPropagateHUValues()
				.withTuPIItem(tuPIItemProduct.getM_HU_PI_Item())
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		return destination.getCreatedHUs();
	}

	/**
	 * Takes a TU off a LU or splits one aggregate TU into two. This has the effect of "de-aggregating" the given {@code sourceTuHU}.<br>
	 * The resulting TUs will always have the same PI as the source TU.
	 *
	 * @param sourceTuHU he source TU to process. Can be an aggregated HU and therefore represent many homogeneous TUs
	 * @param qtyTU      the number of TUs to take off or split
	 */
	public List<I_M_HU> tuToNewTUs(
			@NonNull final I_M_HU sourceTuHU,
			@NonNull final BigDecimal qtyTU)
	{
		if (qtyTU.compareTo(getMaximumQtyTU(sourceTuHU)) >= 0) // the caller wants to process the entire sourceTuHU
		{
			if (handlingUnitsDAO.retrieveParentItem(sourceTuHU) == null) // ..but there sourceTuHU is not attached to a parent, so there isn't anything to do at all.)
			{
				return ImmutableList.of(sourceTuHU);
			}
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

		return tuToTopLevelHUs(sourceTuHU, qtyTU,
				null, // luPIItem is null => top level HU is a TU
				false // newPackingMaterialsAreOurOwn doesn't matter because no new packaging material is required
		);
	}

	@lombok.Value
	public static class HUsToNewTUsRequest
	{
		List<I_M_HU> sourceHUs;

		int qtyTU;

		public static HUsToNewTUsRequest forSourceHuAndQty(@NonNull I_M_HU sourceHU, int qtyTU)
		{
			return HUsToNewTUsRequest.builder().sourceHU(sourceHU).qtyTU(qtyTU).build();
		}

		@lombok.Builder
		private HUsToNewTUsRequest(
				@Singular("sourceHU") @NonNull final List<I_M_HU> sourceHUs,
				final int qtyTU)
		{
			this.sourceHUs = sourceHUs;

			Preconditions.checkArgument(qtyTU > 0, "Parameter qtyTU=%s needs to be grreater than zero", qtyTU);
			this.qtyTU = qtyTU;
		}
	}

	/**
	 * Extract a given amount of TUs from an LU or TU/AggregatedTU.
	 */
	public List<I_M_HU> husToNewTUs(@NonNull final HUsToNewTUsRequest newTUsRequest)
	{
		int qtyTuLeft = newTUsRequest.getQtyTU();

		final ImmutableList.Builder<I_M_HU> result = ImmutableList.builder();
		for (final I_M_HU sourceHU : newTUsRequest.getSourceHUs())
		{
			final List<I_M_HU> currentResult = huToNewTUs(sourceHU, qtyTuLeft);
			result.addAll(currentResult);

			qtyTuLeft -= currentResult.size();
			if (qtyTuLeft <= 0)
			{
				break;
			}
		}
		return result.build();
	}

	private List<I_M_HU> huToNewTUs(@NonNull final I_M_HU sourceHU, final int qtyTU)
	{
		if (handlingUnitsBL.isLoadingUnit(sourceHU))
		{
			final boolean keepLuAsParent = false; // not only extract the new TUs, but also make them "free" standalone TUs.
			final HashSet<HuId> alreadyExtractedTUIds = new HashSet<>(); // not really needed because keepLuAsParent=false
			return luExtractTUs(sourceHU, qtyTU, keepLuAsParent, alreadyExtractedTUIds);
		}
		else if (handlingUnitsBL.isTransportUnitOrAggregate(sourceHU))
		{
			return tuToNewTUs(sourceHU, BigDecimal.valueOf(qtyTU));
		}
		else
		{
			throw new HUException("Cannot extract TUs from CU").setParameter("sourceHU", sourceHU);
		}
	}

	/**
	 * Extract a given number of TUs from an LU.
	 *
	 * @param sourceLU              LU from where the TUs shall be extracted
	 * @param qtyTU                 how many TUs to extract
	 * @param keepSourceLuAsParent  if true, the TUs will be created as necessary, but they will remain children of the given {@code sourceLU}.
	 * @param alreadyExtractedTUIds needed if {@code keepSourceLuAsParent == true}
	 * @return extracted TUs
	 */
	private List<I_M_HU> luExtractTUs(
			@NonNull final I_M_HU sourceLU,
			final int qtyTU,
			final boolean keepSourceLuAsParent,
			@NonNull final Set<HuId> alreadyExtractedTUIds)
	{
		// how many TUs we still have to extract
		int qtyTUsRemaining = Check.assumeGreaterThanZero(qtyTU, "qtyTU");

		final ImmutableList.Builder<I_M_HU> extractedTUs = new ImmutableList.Builder<>();

		// if keepSourceLuAsParent==true, then includedHUs probably contains TUs we already extracted; that's why we need alreadyExtractedTUIds.
		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(sourceLU);
		for (final I_M_HU tu : includedHUs)
		{
			if (qtyTUsRemaining <= 0)
			{
				break;
			}
			if (alreadyExtractedTUIds.contains(HuId.ofRepoId(tu.getM_HU_ID())))
			{
				continue;
			}

			if (handlingUnitsBL.isAggregateHU(tu))
			{
				final int qtyTUsAvailable = getMaximumQtyTU(tu).intValueExact();
				if (qtyTUsAvailable <= 0)
				{
					continue;
				}

				final int qtyTUsToExtract = Math.min(qtyTUsRemaining, qtyTUsAvailable);
				final List<I_M_HU> newTUs;
				if (keepSourceLuAsParent)
				{
					newTUs = tuToExistingLU(tu, BigDecimal.valueOf(qtyTUsToExtract), sourceLU);
				}
				else
				{
					newTUs = tuToNewTUs(tu, BigDecimal.valueOf(qtyTUsToExtract));
				}

				extractedTUs.addAll(newTUs);
				qtyTUsRemaining -= newTUs.size();
			}
			else if (handlingUnitsBL.isVirtual(tu))
			{
				// Skip VHUs which are directly set on LU
				continue;
			}
			else
			{
				if (!keepSourceLuAsParent)
				{
					// move the single TU out of sourceLU
					setParent(tu, null,
							// beforeParentChange
							localHuContext -> {
								final I_M_HU cu = null;
								updateAllocation(sourceLU, tu, cu, null, true, localHuContext);
							},
							// afterParentChange
							localHuContext -> {
								final I_M_HU newParentLU = null;
								final I_M_HU cu = null;
								updateAllocation(newParentLU, tu, cu, null, false, localHuContext);
							});
				}
				extractedTUs.add(tu);
				qtyTUsRemaining--;
			}
		} // each TU

		final ImmutableList<I_M_HU> result = extractedTUs.build();

		result.stream() // add the extracted TUs' IDs to our set
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.forEach(alreadyExtractedTUIds::add);

		return result;
	}

	/**
	 * @param childHU
	 * @param parentItem         may be {@code null} if the childHU in question is removed from it's parent HU.
	 * @param beforeParentChange
	 * @param afterParentChange
	 */
	private void setParent(
			@NonNull final I_M_HU childHU,
			@Nullable final I_M_HU_Item parentItem,
			@NonNull final Consumer<IHUContext> beforeParentChange,
			@NonNull final Consumer<IHUContext> afterParentChange)
	{
		final int parentItemId = parentItem == null ? 0 : parentItem.getM_HU_Item_ID();
		if (childHU.getM_HU_Item_Parent_ID() == parentItemId)
		{
			// Nothing to do. Note that IHUTrxBL.setParentHU() won't do anything either.
			// But by returning even before we call it, we avoid applying 'beforeParentChange' and 'afterParentChange'.
			// That way, we avoid one useless -1/+1 pair of allocations
			return;
		}

		huTrxBL
				.createHUContextProcessorExecutor(huContext)
				.run(
						localHuContext -> {
							Preconditions.checkNotNull(localHuContext, "Param 'localHuContext' may not be null");
							trxManager.assertTrxNotNull(localHuContext);

							beforeParentChange.accept(localHuContext);

							// Take it out from its parent
							huTrxBL.setParentHU(localHuContext,
									parentItem, // might be null
									childHU,
									true // destroyOldParentIfEmptyStorage
							);

							afterParentChange.accept(localHuContext);

							return IHUContextProcessor.NULL_RESULT; // we don't care about the result
						});
	}

	/**
	 * Creates a new LU and joins or splits a source TU to it. If the user goes with the full quantity of the (aggregate) source TU(s), and if if all fits on one LU, then the source remains unchanged and is only joined.<br>
	 * Otherwise, the source is split and distributed over many LUs.
	 *
	 * @param sourceTuHU            the source TU line to process. Can be an aggregated HU and therefore represent many homogeneous TUs.
	 * @param qtyTU                 the number of TUs to join or split onto the destination LU(s).
	 * @param luPIItem              the LU's PI item (with type "HU") that specifies both the LUs' PI and the number of TUs that fit on one LU.
	 * @param isOwnPackingMaterials
	 */
	public List<I_M_HU> tuToNewLUs(
			@NonNull final I_M_HU sourceTuHU,
			@NonNull final BigDecimal qtyTU,
			@NonNull final I_M_HU_PI_Item luPIItem,
			final boolean isOwnPackingMaterials)
	{
		if (qtyTU.compareTo(getMaximumQtyTU(sourceTuHU)) >= 0 // the complete sourceTuHU shall be processed
				&& getMaximumQtyTU(sourceTuHU).compareTo(luPIItem.getQty()) <= 0 // the complete sourceTuHU fits onto one pallet
		)
		{
			// don't split; just create a new LU and "move" the TU

			// create the new LU
			final I_M_HU newLuHU = handlingUnitsDAO
					.createHUBuilder(huContext)
					.setBPartnerId(IHandlingUnitsBL.extractBPartnerIdOrNull(sourceTuHU))
					.setC_BPartner_Location_ID(sourceTuHU.getC_BPartner_Location_ID())
					.setLocatorId(IHandlingUnitsBL.extractLocatorId(sourceTuHU))
					.setHUPlanningReceiptOwnerPM(isOwnPackingMaterials)
					.setHUStatus(sourceTuHU.getHUStatus()) // gh #1975: when creating a new parent-LU inherit the source's status
					.create(luPIItem.getM_HU_PI_Version());

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
					final I_M_HU_PI piOfChildHU = handlingUnitsBL.getPI(sourceTuHU);
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

			// update the huItemOfLU if needed
			if (handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				final I_M_HU_Item huItemOfLU = handlingUnitsDAO.retrieveItems(newLuHU).get(0);
				huItemOfLU.setQty(oldParentItemOfSourceTuHU.getQty());
				huItemOfLU.setM_HU_PI_Item(handlingUnitsBL.getPIItem(oldParentItemOfSourceTuHU));
				InterfaceWrapperHelper.save(huItemOfLU);
			}

			return ImmutableList.of(newLuHU);
		}

		return tuToTopLevelHUs(sourceTuHU, qtyTU, luPIItem, isOwnPackingMaterials);
	}

	/**
	 * Split TU to top-level (either LU or new TU)
	 *
	 * @param sourceTuHU                   the source TU from which we split
	 * @param qtyTU                        the TU qty which we split. This is one factor to the actual cuQty
	 * @param luPIItem                     the packing instruction for the TU level of the new hierarchy which we create. may be {@code null}. If null, then the resulting top level HU will be a TU
	 * @param newPackingMaterialsAreOurOwn if we create a new LU to put the given {@code sourceTuHU} onto, then this flag decides whether that LU's packaging material is owned by us or not.
	 */
	private List<I_M_HU> tuToTopLevelHUs(
			@NonNull final I_M_HU sourceTuHU,
			@NonNull final BigDecimal qtyTU,
			final I_M_HU_PI_Item luPIItem,
			final boolean newPackingMaterialsAreOurOwn)
	{
		final List<IHUProductStorage> productStorages = retrieveAllProductStorages(sourceTuHU);

		final List<I_M_HU> createdTUs;

		// deal with the first of potentially many cuHUs and their storages
		{
			final IHUProductStorage firstProductStorage = productStorages.get(0);

			final BigDecimal qtyOfStorage = firstProductStorage.getQty().toBigDecimal();

			final BigDecimal sourceQtyCUperTU; // will be used to get the overall cuQty to transfer, by multiplying with the given qtyTU
			if (handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				final I_M_HU_Item tuHUsParentItem = handlingUnitsDAO.retrieveParentItem(sourceTuHU); // can't be null because if is was isAggregateHU() would return false.
				final BigDecimal representedTUsCount = tuHUsParentItem.getQty();
				Preconditions.checkState(representedTUsCount.signum() > 0, "Param 'tuHU' is an aggregate HU whose M_HU_Item_Parent has a qty of %s; tuHU=%s; tuHU's M_HU_Item_Parent=%s", representedTUsCount, sourceTuHU, tuHUsParentItem);

				final int uomPrecision = firstProductStorage.getC_UOM().getStdPrecision();
				// always round floor for a more stable split calculation
				sourceQtyCUperTU = qtyOfStorage.divide(representedTUsCount, uomPrecision, RoundingMode.FLOOR);
				// since we do full TU split: store how many TUs we split off, so that we update the storage qty by subtracting this number.
				final I_M_HU_Item item = sourceTuHU.getM_HU_Item_Parent();
				huContext.setProperty(AggregateHUTrxListener.mkQtyTUsToSplitPropertyKey(item), qtyTU);
			}
			else
			{
				sourceQtyCUperTU = qtyOfStorage;
			}
			final ProductId cuProductId = Preconditions.checkNotNull(firstProductStorage.getProductId(), "M_Product of firstProductStorage may not be null; firstProductStorage=%s", firstProductStorage);
			final I_C_UOM cuUOM = Preconditions.checkNotNull(firstProductStorage.getC_UOM(), "C_UOM of firstProductStorage may not be null; firstProductStorage=%s", firstProductStorage);

			final LUTUProducerDestination destination = new LUTUProducerDestination();

			final I_M_HU_PI tuPI = handlingUnitsBL.getEffectivePIVersion(sourceTuHU).getM_HU_PI();
			destination.setTUPI(tuPI);
			if (luPIItem == null)
			{
				destination.setNoLU();
				destination.setIsHUPlanningReceiptOwnerPM(sourceTuHU.isHUPlanningReceiptOwnerPM());
			}
			else
			{
				destination.setLUItemPI(luPIItem);
				destination.setLUPI(luPIItem.getM_HU_PI_Version().getM_HU_PI());
				destination.setIsHUPlanningReceiptOwnerPM(newPackingMaterialsAreOurOwn);
			}

			final I_M_HU_PI_Item materialItem = handlingUnitsDAO
					.retrievePIItems(tuPI, BPartnerId.ofRepoIdOrNull(sourceTuHU.getC_BPartner_ID()))
					.stream()
					.filter(i -> X_M_HU_PI_Item.ITEMTYPE_Material.equals(i.getItemType()))
					.findFirst().orElse(null);
			if (materialItem == null)
			{
				throw new HUException("@NotFound@ @M_HU_PI_Item_ID@")
						.setParameter("tuPI", tuPI);
			}

			destination.addCUPerTU(cuProductId, sourceQtyCUperTU, cuUOM); // explicitly declaring capacity to make sure that all aggregate HUs have it

			HUSplitBuilderCoreEngine.builder()
					.huContextInitital(huContext)
					.huToSplit(sourceTuHU)
					.requestProvider(huContext -> createCUAllocationRequest(huContext, cuProductId, Quantity.of(qtyTU.multiply(sourceQtyCUperTU), cuUOM), false))
					.destination(destination)
					.build()
					.withPropagateHUValues()
					.withTuPIItem(materialItem)

					// we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
					.withAllowPartialUnloads(true)

					// without this, if sourceTuHU is aggregate, then the system might try to get another TU packing material from the empties warehouse, because a new M_HU record is created
					.withAutomaticallyMovePackingMaterials(false)
					.performSplit();

			createdTUs = destination.getCreatedHUs();
		}

		// if productStorages has more than one element, then iterate the remaining rows
		for (int i = 1; i < productStorages.size(); i++)
		{
			final IHUProductStorage currentHuProductStorage = productStorages.get(i);

			final Quantity qtyCU = currentHuProductStorage.getQty();
			createdTUs.forEach(createdTU -> cuToExistingTU(currentHuProductStorage.getM_HU(), qtyCU, createdTU));
		}

		return createdTUs;
	}

	private List<IHUProductStorage> retrieveAllProductStorages(final I_M_HU tuHU)
	{
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();

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

	@lombok.Value
	public static class HUsToNewCUsRequest
	{
		List<I_M_HU> sourceHUs;

		Quantity qtyCU;

		/**
		 * if true, the extract CUs, but don't let them end up stand alone;
		 * instead, keep them as separate VHU under the same parent that the respective source CU had.
		 */
		boolean keepNewCUsUnderSameParent;

		/**
		 * if true, then only unreserved VHUs are split on order to get our new CUs
		 */
		boolean onlyFromUnreservedHUs;

		/**
		 * Mandatory, to cover the case of HUs with multiple products
		 */
		ProductId productId;

		@lombok.Builder(toBuilder = true)
		private HUsToNewCUsRequest(
				@Singular("sourceHU") @NonNull final List<I_M_HU> sourceHUs,
				@NonNull ProductId productId,
				@NonNull final Quantity qtyCU,
				@Nullable final Boolean keepNewCUsUnderSameParent,
				@Nullable final Boolean onlyFromUnreservedHUs)
		{
			Check.assumeNotEmpty(sourceHUs, "sourceHUs is not empty");

			this.sourceHUs = sourceHUs;
			this.qtyCU = qtyCU;
			this.productId = productId;
			this.keepNewCUsUnderSameParent = CoalesceUtil.coalesce(keepNewCUsUnderSameParent, false);
			this.onlyFromUnreservedHUs = CoalesceUtil.coalesce(onlyFromUnreservedHUs, false);

			Check.assume(qtyCU.signum() > 0, "Paramater qtyCU={} needs to be >0; this={}", qtyCU, this);
		}
	}

	/**
	 * Creates new CUs (vhus) according to the given request
	 */
	public List<I_M_HU> husToNewCUs(@NonNull final HUsToNewCUsRequest newCUsRequest)
	{
		Quantity qtyCuLeft = newCUsRequest.getQtyCU();

		final ImmutableList.Builder<I_M_HU> result = ImmutableList.builder();
		for (final I_M_HU sourceHU : newCUsRequest.getSourceHUs())
		{
			final HUsToNewCUsRequest singleHuRequest = newCUsRequest.toBuilder()
					.clearSourceHUs()
					.sourceHU(sourceHU)
					.qtyCU(qtyCuLeft)
					.build();

			final List<I_M_HU> currentResult = huToNewCUs(singleHuRequest);
			result.addAll(currentResult);

			qtyCuLeft = qtyCuLeft.subtract(calculateTotalQtyOfCUs(currentResult, qtyCuLeft.getUOM()));

			if (qtyCuLeft.signum() <= 0)
			{
				break;
			}
		}
		return result.build();
	}

	private List<I_M_HU> huToNewCUs(@NonNull final HUsToNewCUsRequest singleSourceHuRequest)
	{
		final I_M_HU sourceHU = CollectionUtils.singleElement(singleSourceHuRequest.getSourceHUs());

		if (handlingUnitsBL.isLoadingUnit(sourceHU))
		{
			return luExtractCUs(singleSourceHuRequest);
		}
		else if (handlingUnitsBL.isTransportUnit(sourceHU))
		{
			return tuExtractCUs(singleSourceHuRequest);
		}
		else
		{
			return cuToNewCU(sourceHU, singleSourceHuRequest.getQtyCU());
		}
	}

	private List<I_M_HU> luExtractCUs(@NonNull final HUsToNewCUsRequest singleSourceLuRequest)
	{
		Preconditions.checkArgument(singleSourceLuRequest.getQtyCU().signum() > 0, "qtyCU > 0");

		final ImmutableList.Builder<I_M_HU> extractedCUs = new ImmutableList.Builder<>();

		Quantity qtyCUsRemaining = singleSourceLuRequest.getQtyCU(); // how many CUs we still have to extract

		final I_M_HU sourceLU = CollectionUtils.singleElement(singleSourceLuRequest.getSourceHUs());

		// in this number, aggregate HUs count according to the number of TUs that they represent.
		final int logicalNumberOfIncludedHUs = handlingUnitsDAO
				.retrieveIncludedHUs(sourceLU)
				.stream()
				.map(this::getMaximumQtyTU)
				.reduce(ZERO, BigDecimal::add)
				.intValueExact();

		final Set<HuId> alreadyExtractedTUIds = new HashSet<>();

		for (int i = 0; i < logicalNumberOfIncludedHUs; i++)
		{
			if (qtyCUsRemaining.signum() <= 0)
			{
				break;
			}

			final int numberOfTUsToExtract = 1; // we extract only one TU at a time because we don't know how many CUs we will get out of each TU.
			final boolean keepLuAsParent = true; // we need a "dedicated" TU, but it shall remain with sourceLU.
			final I_M_HU extractedTU = CollectionUtils.singleElement(luExtractTUs(sourceLU, numberOfTUsToExtract, keepLuAsParent, alreadyExtractedTUIds));

			final HUsToNewCUsRequest singleSourceTuRequest = singleSourceLuRequest.toBuilder()
					.clearSourceHUs()
					.sourceHU(extractedTU)
					.qtyCU(qtyCUsRemaining).build();

			final List<I_M_HU> cusFromTU = tuExtractCUs(singleSourceTuRequest);

			extractedCUs.addAll(cusFromTU);

			qtyCUsRemaining = qtyCUsRemaining.subtract(calculateTotalQtyOfCUs(cusFromTU, qtyCUsRemaining.getUOM()));
		}
		return extractedCUs.build();
	}

	private List<I_M_HU> tuExtractCUs(@NonNull final HUsToNewCUsRequest singleSourceTuRequest)
	{
		final ImmutableList.Builder<I_M_HU> extractedCUs = new ImmutableList.Builder<>();
		Quantity qtyCUsRemaining = singleSourceTuRequest.getQtyCU();

		final I_M_HU sourceTU = CollectionUtils.singleElement(singleSourceTuRequest.getSourceHUs());

		for (final I_M_HU cu : handlingUnitsDAO.retrieveIncludedHUs(sourceTU))
		{
			if (singleSourceTuRequest.isOnlyFromUnreservedHUs() && cu.isReserved())
			{
				continue; // the request only wants us to use unreserved source HUs
			}
			final IHUProductStorage productStorageOrNull = handlingUnitsBL
					.getStorageFactory()
					.getStorage(cu)
					.getProductStorageOrNull(singleSourceTuRequest.getProductId());
			if (productStorageOrNull == null)
			{
				continue; // cu doesn't have the product we are looking for
			}

			if (qtyCUsRemaining.signum() <= 0)
			{
				break;
			}

			final List<I_M_HU> newCUs = cuToNewCU0(
					cu,
					qtyCUsRemaining,
					singleSourceTuRequest.isKeepNewCUsUnderSameParent());

			extractedCUs.addAll(newCUs);

			qtyCUsRemaining = qtyCUsRemaining.subtract(calculateTotalQtyOfCUs(newCUs, qtyCUsRemaining.getUOM()));
		}
		return extractedCUs.build();
	}

	private Quantity calculateTotalQtyOfCUs(@NonNull final List<I_M_HU> cus, @NonNull final I_C_UOM uom)
	{
		Quantity totalQtyCU = Quantity.zero(uom);

		for (final I_M_HU cu : cus)
		{
			final Quantity qtyToAdd = getMaximumQtyCU(cu, uom);

			totalQtyCU = totalQtyCU.add(qtyToAdd);
		}
		return totalQtyCU;
	}

	private void addCUsToTU(
			@NonNull final List<I_M_HU> childCUs,
			@NonNull final I_M_HU targetTU)
	{
		// get *the* MI HU_Item of 'tuHU'. There must be exactly one, otherwise, tuHU wouldn't exist here in the first place.
		final I_M_HU_Item tuMaterialItem = handlingUnitsDAO.retrieveItems(targetTU)
				.stream()
				.filter(piItem -> X_M_HU_PI_Item.ITEMTYPE_Material.equals(piItem.getItemType()))
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Param 'tuHU' does not have one 'MI' item; tuHU=" + targetTU)));

		// finally do the attaching

		// iterate the child CUs and set their parent item
		childCUs.forEach(childCU -> {
			setParent(childCU,
					tuMaterialItem,

					// before the newChildCU's parent item is set,
					localHuContext -> {
						final I_M_HU oldParentTU = handlingUnitsDAO.retrieveParent(childCU);
						final I_M_HU oldParentLU = oldParentTU == null ? null : handlingUnitsDAO.retrieveParent(oldParentTU);
						updateAllocation(oldParentLU, oldParentTU, childCU, null, true, localHuContext);
					},

					// after the newChildCU's parent item is set,
					localHuContext -> {
						final I_M_HU newParentTU = handlingUnitsDAO.retrieveParent(childCU);
						final I_M_HU newParentLU = newParentTU == null ? null : handlingUnitsDAO.retrieveParent(newParentTU);
						updateAllocation(newParentLU, newParentTU, childCU, null, false, localHuContext);
					});
		});
	}
}
