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
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.spi.impl.AggregateHUTrxListener;
import de.metas.handlingunits.allocation.strategy.AllocationStrategyType;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilderCoreEngine;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.attribute.IHUAttributesBL;
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
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
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

	// services
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUDocumentFactoryService huDocumentFactoryService = Services.get(IHUDocumentFactoryService.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final SpringContextHolder.Lazy<HUQRCodesService> huQRCodesService;
	private final SpringContextHolder.Lazy<QRCodeConfigurationService> qrCodeConfigurationService;

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
			@Nullable final HUQRCodesService huQRCodesService,
			@Nullable final Properties ctx,
			@Nullable final String trxName,
			@Nullable final EmptyHUListener emptyHUListener,
			@Nullable final List<TableRecordReference> referencedObjects)
	{
		this.huQRCodesService = SpringContextHolder.lazyBean(HUQRCodesService.class, huQRCodesService);
		this.qrCodeConfigurationService = SpringContextHolder.lazyBean(QRCodeConfigurationService.class, null);

		this.referencedObjects = referencedObjects != null ? ImmutableList.copyOf(referencedObjects) : ImmutableList.of();

		final IMutableHUContext mutableHUContext = createHUContext(ctx, trxName);
		if (emptyHUListener != null)
		{
			mutableHUContext.addEmptyHUListener(emptyHUListener);
		}

		huContext = createHUContext(ctx, trxName);
	}

	private static IMutableHUContext createHUContext(final @Nullable Properties ctx, final @Nullable String trxName)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

		final Properties effectiveCtx = ctx != null ? ctx : Env.getCtx();
		final String effectiveTrxName = CoalesceUtil.coalesceNotNull(trxName, ITrx.TRXNAME_ThreadInherited);
		return huContextFactory.createMutableHUContext(effectiveCtx, effectiveTrxName);
	}

	/**
	 * When running unit tests, then use this builder to get your instance. Pass the HUTestHelper's getHUContext() result to it, to avoid transactional trouble.
	 */
	@Builder(builderClassName = "BuilderForHUcontext", builderMethodName = "builderForHUcontext")
	private HUTransformService(@NonNull final IHUContext huContext,
							   @Nullable final List<TableRecordReference> referencedObjects)
	{
		this.huQRCodesService = SpringContextHolder.lazyBean(HUQRCodesService.class);
		this.qrCodeConfigurationService = SpringContextHolder.lazyBean(QRCodeConfigurationService.class, null);

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
		return handlingUnitsBL.getTUsCount(tu).toBigDecimal();
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
		Check.assume(qtyCU.signum() > 0, "Parameter qtyCU={} needs to be >0; (source-)cuOrAggregateHU={}", qtyCU, cuOrAggregateHU);

		final boolean qtyCuExceedsCuHU = qtyCU.compareTo(getMaximumQtyCU(cuOrAggregateHU, qtyCU.getUOM())) >= 0;
		final boolean huIsCU = !handlingUnitsBL.isAggregateHU(cuOrAggregateHU);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IHUStorage storage = storageFactory.getStorage(cuOrAggregateHU);

		final I_C_UOM huUOM = storage.getC_UOMOrNull();
		final UomId huUOMId = UomId.ofRepoId(huUOM == null ? -1 : huUOM.getC_UOM_ID());
		final boolean isSameUOM = qtyCU.getUomId().equals(huUOMId);

		if (qtyCuExceedsCuHU && huIsCU && isSameUOM)
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
					setParent(
							cuOrAggregateHU,
							null,
							true,
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

		// we split even if cuOrAggregateHU's qty is equal to qtyCU, because we want a CU without packaging; not an aggregated TU
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
				// sourceTuHU is also the parent for the new TU we will create, so make sure to destroy it. It will receive the new TU a child a few lines below.
				try (final IAutoCloseable ignore = huContext.temporarilyDontDestroyHU(HuId.ofRepoId(luHU.getM_HU_ID())))
				{
					tuHUsToAttachToLU = tuToNewTUs(sourceTuHU, qtyTU);
				}
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

		tuHUsToAttachToLU.forEach(tuToAttach -> {
			final I_M_HU_PI piOfChildHU = handlingUnitsBL.getPI(tuToAttach);

			final I_M_HU_PI_Item parentPIItem = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(luHU, piOfChildHU, huContext);
			if (parentPIItem == null)
			{
				throw new AdempiereException("LU `" + handlingUnitsBL.getDisplayName(luHU) + "` cannot stack TU `" + handlingUnitsBL.getDisplayName(tuToAttach) + "` because there is no link between them.")
						.setParameter("tuToAttach", tuToAttach)
						.setParameter("piOfChildHU", piOfChildHU)
						.setParameter("luHU", luHU);
			}

			final I_M_HU_Item parentItem = handlingUnitsDAO.createHUItemIfNotExists(luHU, parentPIItem).getLeft();

			setParent(tuToAttach,
					parentItem,
					true,
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
	 * @param qtyCU                 optional; the CU-quantity to join or split. If {@code null}, then the whole CU is consumed.
	 * @param tuPIItemProduct       the PI item product to specify both the PI and capacity of the target TU
	 * @param isOwnPackingMaterials indicates if the packaging material (e.g. palox boxes) are owned by to us (true) or by respective bPartner (false).
	 */
	public List<I_M_HU> cuToNewTUs(
			@NonNull final I_M_HU cuHU,
			@Nullable final Quantity qtyCU,
			@NonNull final I_M_HU_PI_Item_Product tuPIItemProduct,
			final boolean isOwnPackingMaterials)
	{
		final LUTUProducerDestination destination = new LUTUProducerDestination();
		destination.setTUPI(tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI());
		destination.setIsHUPlanningReceiptOwnerPM(isOwnPackingMaterials);

		// gh #1759: explicitly take the capacity from the tuPIItemProduct which the user selected
		final ProductId productId = ProductId.ofRepoId(tuPIItemProduct.getM_Product_ID());
		final I_C_UOM uom = IHUPIItemProductBL.extractUOMOrNull(tuPIItemProduct);
		final Capacity capacity = huCapacityBL.getCapacity(tuPIItemProduct, productId, uom);
		destination.addCUPerTU(capacity);

		destination.setNoLU();

		final List<IHUProductStorage> storages = huContext.getHUStorageFactory().getStorage(cuHU).getProductStorages();
		Check.errorUnless(storages.size() == 1, "Param' cuHU' needs to have *one* storage; storages={}; cuHU={};", storages, cuHU);
		final IHUProductStorage singleCUStorage = storages.get(0);

		final Quantity qtyCUToUse = CoalesceUtil.coalesceSuppliersNotNull(() -> qtyCU, singleCUStorage::getQty);

		HUSplitBuilderCoreEngine.builder()
				.huContextInitital(huContext)
				.huToSplit(cuHU)
				// forceAllocation = false; we want to create as many new TUs as are implied by the qtyCU and the TUs' capacity
				.requestProvider(huContext -> createCUAllocationRequest(huContext, singleCUStorage.getProductId(), qtyCUToUse, false))
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
			if (handlingUnitsDAO.retrieveParentItem(sourceTuHU) == null) // ..but the sourceTuHU is not attached to a parent, so there isn't anything to do at all.)
			{
				return ImmutableList.of(sourceTuHU);
			}
			if (!handlingUnitsBL.isAggregateHU(sourceTuHU))
			{
				setParent(
						sourceTuHU,
						null,
						true,
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

	/**
	 * Extract a given number of TUs from an LU or TU/AggregatedTU.
	 */
	@lombok.Value
	public static class HUsToNewTUsRequest
	{
		@NonNull List<I_M_HU> sourceHUs;
		@NonNull QtyTU qtyTU;
		@Nullable ProductId expectedProductId;

		public static HUsToNewTUsRequest forSourceHuAndQty(@NonNull final I_M_HU sourceHU, final int qtyTU)
		{
			return HUsToNewTUsRequest.builder().sourceHU(sourceHU).qtyTU(qtyTU).build();
		}

		@lombok.Builder
		private HUsToNewTUsRequest(
				@Singular("sourceHU") @NonNull final List<I_M_HU> sourceHUs,
				@NonNull final QtyTU qtyTU,
				@Nullable final ProductId expectedProductId)
		{
			Check.assume(qtyTU.isPositive(), "qtyTU shall be positive");
			this.sourceHUs = sourceHUs;
			this.expectedProductId = expectedProductId;
			this.qtyTU = qtyTU;
		}

		public static class HUsToNewTUsRequestBuilder
		{
			public HUsToNewTUsRequestBuilder qtyTU(final int qtyTU) {return qtyTU(QtyTU.ofInt(qtyTU));}

			public HUsToNewTUsRequestBuilder qtyTU(final QtyTU qtyTU)
			{
				this.qtyTU = qtyTU;
				return this;
			}
		}
	}

	/**
	 * Extract a given amount of TUs from an LU or TU/AggregatedTU.
	 */
	public List<I_M_HU> husToNewTUs(@NonNull final HUsToNewTUsRequest newTUsRequest)
	{
		int qtyTuLeft = newTUsRequest.getQtyTU().toInt();

		final ImmutableList.Builder<I_M_HU> result = ImmutableList.builder();
		for (final I_M_HU sourceHU : newTUsRequest.getSourceHUs())
		{
			final List<I_M_HU> currentResult = huToNewTUs(sourceHU, qtyTuLeft, newTUsRequest.getExpectedProductId());
			result.addAll(currentResult);

			qtyTuLeft -= currentResult.size();
			if (qtyTuLeft <= 0)
			{
				break;
			}
		}
		return result.build();
	}

	private List<I_M_HU> huToNewTUs(
			@NonNull final I_M_HU sourceHU,
			final int qtyTU,
			@Nullable final ProductId expectedProductId)
	{
		if (handlingUnitsBL.isLoadingUnit(sourceHU))
		{
			final boolean keepLuAsParent = false; // not only extract the new TUs, but also make them "free" standalone TUs.
			final HashSet<HuId> alreadyExtractedTUIds = new HashSet<>(); // not really needed because keepLuAsParent=false
			return luExtractTUs(sourceHU, qtyTU, keepLuAsParent, alreadyExtractedTUIds, expectedProductId);
		}
		else if (handlingUnitsBL.isTransportUnitOrAggregate(sourceHU))
		{
			if (expectedProductId != null && !huContext.getHUStorageFactory().isSingleProductStorageMatching(sourceHU, expectedProductId))
			{
				return ImmutableList.of();
			}
			
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
			@NonNull final Set<HuId> alreadyExtractedTUIds,
			@Nullable final ProductId expectedProductId)
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

			if (expectedProductId != null && !huContext.getHUStorageFactory().isSingleProductStorageMatching(tu, expectedProductId))
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
					setParent(
							tu,
							null,
							true,
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
	 * @param parentItem may be {@code null} if the childHU in question is removed from it's parent HU.
	 */
	private void setParent(
			@NonNull final I_M_HU childHU,
			@Nullable final I_M_HU_Item parentItem,
			final boolean failIfAggregatedTU,
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
							huTrxBL.setParentHU(IHUTrxBL.ChangeParentHURequest.builder()
									.huContext(localHuContext)
									.parentHUItem(parentItem) // might be null
									.hu(childHU)
									.destroyOldParentIfEmptyStorage(true)
									.failIfAggregateTU(failIfAggregatedTU)
									.build());

							afterParentChange.accept(localHuContext);

							return IHUContextProcessor.NULL_RESULT; // we don't care about the result
						});
	}

	/**
	 * Creates a new LU and joins or splits a source TU to it. If the user goes with the full quantity of the (aggregate) source TU(s), and if if all fits on one LU, then the source remains unchanged and is only joined.<br>
	 * Otherwise, the source is split and distributed over many LUs.
	 *
	 * @param sourceTuHU the source TU line to process. Can be an aggregated HU and therefore represent many homogeneous TUs.
	 * @param qtyTU      the number of TUs to join or split onto the destination LU(s).
	 * @param luPIItem   the LU's PI item (with type "HU") that specifies both the LUs' PI and the number of TUs that fit on one LU.
	 */
	public List<I_M_HU> tuToNewLUs(
			@NonNull final I_M_HU sourceTuHU,
			@NonNull final BigDecimal qtyTU,
			@NonNull final I_M_HU_PI_Item luPIItem,
			final boolean isOwnPackingMaterials)
	{
		final BigDecimal qtyTU_of_sourceTuHU = getMaximumQtyTU(sourceTuHU);
		if (qtyTU.compareTo(qtyTU_of_sourceTuHU) >= 0 // the complete sourceTuHU shall be processed
				&& qtyTU_of_sourceTuHU.compareTo(luPIItem.getQty()) <= 0 // the complete sourceTuHU fits onto one pallet
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
					.setHUClearanceStatusInfo(ClearanceStatusInfo.ofHU(sourceTuHU))
					.setIsExternalProperty(sourceTuHU.isExternalProperty())
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
					if (parentPIItem == null)
					{
						throw new AdempiereException("LU `" + handlingUnitsBL.getDisplayName(newLuHU) + "` cannot stack TU `" + handlingUnitsBL.getDisplayName(sourceTuHU) + "` because there is no link between them.")
								.setParameter("sourceTuHU", sourceTuHU)
								.setParameter("piOfChildHU", piOfChildHU)
								.setParameter("newLuHU", newLuHU);
					}

					newParentItemOfSourceTuHU = handlingUnitsDAO.createHUItemIfNotExists(newLuHU, parentPIItem).getLeft();
				}
			}

			// store the old parent-item of sourceTuHU
			final I_M_HU_Item oldParentItemOfSourceTuHU = handlingUnitsDAO.retrieveParentItem(sourceTuHU); // needed in case sourceTuHU is an aggregate

			// assign sourceTuHU to newLuHU
			setParent(sourceTuHU,
					newParentItemOfSourceTuHU,
					false, // sourceTuHU might be an aggregated HU, so don't fail
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

			huAttributesBL.updateHUAttribute(newLuHU, sourceTuHU, AttributeConstants.ATTR_LotNumber);

			return ImmutableList.of(newLuHU);
		}
		else
		{
			return tuToTopLevelHUs(sourceTuHU, qtyTU, luPIItem, isOwnPackingMaterials);
		}
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
			@Nullable final I_M_HU_PI_Item luPIItem,
			final boolean newPackingMaterialsAreOurOwn)
	{
		final List<IHUProductStorage> productStorages = retrieveAllProductStoragesOfTU(sourceTuHU);

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
			for (final I_M_HU createdTU : createdTUs)
			{
				cuToExistingTU(currentHuProductStorage.getM_HU(), qtyCU, createdTU);
			}
		}

		return createdTUs;
	}

	private List<IHUProductStorage> retrieveAllProductStoragesOfTU(final I_M_HU tuHU)
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
					.forEach(cuHU -> productStorages.addAll(storageFactory.getStorage(cuHU).getProductStorages()));
		}
		Preconditions.checkState(!productStorages.isEmpty(), "The list of productStorages below the given 'tuHU' may not be empty; tuHU=%s", tuHU);
		return productStorages;
	}

	@Value
	public static class HUsToNewCUsRequest
	{
		@NonNull ImmutableList<I_M_HU> sourceHUs;
		@NonNull ProductId productId;
		@NonNull Quantity qtyCU;

		/**
		 * if true, the extract CUs, but don't let them end up stand alone;
		 * instead, keep them as separate VHU under the same parent that the respective source CU had.
		 */
		boolean keepNewCUsUnderSameParent;

		/**
		 * only the VHUs allowed by this policy are split in order to get our new CUs
		 */
		@NonNull ReservedHUsPolicy reservedVHUsPolicy;

		@lombok.Builder(toBuilder = true)
		private HUsToNewCUsRequest(
				@Singular("sourceHU") @NonNull final ImmutableList<I_M_HU> sourceHUs,
				@NonNull final ProductId productId,
				@NonNull final Quantity qtyCU,
				@Nullable final Boolean keepNewCUsUnderSameParent,
				@Nullable final ReservedHUsPolicy reservedVHUsPolicy)
		{
			Check.assumeNotEmpty(sourceHUs, "sourceHUs is not empty");

			this.sourceHUs = sourceHUs;
			this.qtyCU = qtyCU;
			this.productId = productId;
			this.keepNewCUsUnderSameParent = CoalesceUtil.coalesceNotNull(keepNewCUsUnderSameParent, false);
			this.reservedVHUsPolicy = CoalesceUtil.coalesceNotNull(reservedVHUsPolicy, ReservedHUsPolicy.CONSIDER_ALL);

			Check.assume(qtyCU.signum() > 0, "Paramater qtyCU={} needs to be >0; this={}", qtyCU, this);
		}
	}

	public I_M_HU huToNewSingleCU(@NonNull final HUsToNewCUsRequest newCURequest)
	{
		Check.assume(newCURequest.getSourceHUs().size() == 1, "Only one source HU expected: {}", newCURequest);
		final List<I_M_HU> splitCUs = husToNewCUs(newCURequest);
		return CollectionUtils.singleElement(splitCUs);
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

	/**
	 * If the given HU is aggregated, split out one TU and assign the given QR-code to it. Otherwise do nothing and jsut return the given {@code huId}.
	 */
	@NonNull
	public HuId extractIfAggregatedByQRCode(@NonNull final HuId huId, @NonNull final HUQRCode huQRCode)
	{
		huQRCodesService.get().assertQRCodeAssignedToHU(huQRCode, huId);

		final I_M_HU hu = handlingUnitsBL.getById(huId);

		if (handlingUnitsBL.isAggregateHU(hu))
		{
            final HuId extractedTUId = splitOutTUFromAggregated(hu);

            final boolean ensureSingleAssignment = !qrCodeConfigurationService.get().isOneQrCodeForAggregatedHUsEnabledFor(hu);
            if (ensureSingleAssignment)
            {
                huQRCodesService.get().removeAssignment(huQRCode, ImmutableSet.of(huId));
            }
            huQRCodesService.get().assign(huQRCode, extractedTUId, ensureSingleAssignment);

			return extractedTUId;
		}
		else
		{
			return huId;
		}
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
		// reminder: one "aggregated" TU can represent many TUs
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

			final List<I_M_HU> extractedTUs = luExtractTUs(sourceLU, numberOfTUsToExtract, keepLuAsParent, alreadyExtractedTUIds, null);

			// There are no real TUs in the LU. Extract the virtual HUs (CUs) directly, if found
			if (extractedTUs.isEmpty())
			{
				extractedCUs.addAll(extractCUsDirectlyFromLU(sourceLU, qtyCUsRemaining));
			}

			else
			{
				final I_M_HU extractedTU = CollectionUtils.singleElement(extractedTUs);
				final HUsToNewCUsRequest singleSourceTuRequest = singleSourceLuRequest.toBuilder()
						.clearSourceHUs()
						.sourceHU(extractedTU)
						.qtyCU(qtyCUsRemaining).build();

				final List<I_M_HU> cusFromTU = tuExtractCUs(singleSourceTuRequest);

				extractedCUs.addAll(cusFromTU);

				qtyCUsRemaining = qtyCUsRemaining.subtract(calculateTotalQtyOfCUs(cusFromTU, qtyCUsRemaining.getUOM()));
			}

		}
		return extractedCUs.build();
	}

	private ImmutableList<I_M_HU> extractCUsDirectlyFromLU(@NonNull final I_M_HU sourceLU, @NonNull Quantity qtyCUsRemaining)
	{
		if (qtyCUsRemaining.signum() <= 0)
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<I_M_HU> extractedCUs = new ImmutableList.Builder<>();
		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(sourceLU);

		for (final I_M_HU hu : includedHUs)
		{
			if (qtyCUsRemaining.signum() <= 0)
			{
				break;
			}

			if (!handlingUnitsBL.isVirtual(hu))
			{
				// Skip all that is not a VHU
				continue;
			}

			final Quantity qtyCUsAvailable = getMaximumQtyCU(hu, qtyCUsRemaining.getUOM());
			if (qtyCUsAvailable.signum() <= 0)
			{
				continue;
			}
			final Quantity qtyToExtract = qtyCUsAvailable.min(qtyCUsRemaining);

			final List<I_M_HU> newCUs = cuToNewCU(hu, qtyToExtract);

			extractedCUs.addAll(newCUs);
			qtyCUsRemaining = qtyCUsRemaining.subtract(qtyToExtract);
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
			if (!singleSourceTuRequest.getReservedVHUsPolicy().isConsiderVHU(cu))
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

	public void addCUsToTU(
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
		childCUs.forEach(childCU -> setParent(childCU,
				tuMaterialItem,
				true,
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
				}));
	}

	@NonNull
	public HuId extractToTopLevel(@NonNull final HuId huId, @Nullable final HUQRCode huQRCode)
	{
		if (huQRCode != null)
		{
			huQRCodesService.get().assertQRCodeAssignedToHU(huQRCode, huId);
		}

		final I_M_HU hu = handlingUnitsBL.getById(huId);
		if (handlingUnitsBL.isTopLevel(hu))
		{
			return huId;
		}
		else if (handlingUnitsBL.isAggregateHU(hu))
		{
            if (huQRCode != null)
			{
                return extractIfAggregatedByQRCode(huId, huQRCode);
			}
            else
            {
                return splitOutTU(hu);
            }
		}
		else
		{
			huTrxBL.extractHUFromParentIfNeeded(hu);
			return huId;
		}
	}

	@NonNull
	public Set<HuId> extractFromAggregatedByQrCode(
			@NonNull final HuId aggregatedHuId,
			@Nullable final HUQRCode huQRCode,
			@NonNull final QtyTU qtyTU,
			@Nullable final HuPackingInstructionsItemId newLUPackingItem)
	{
		final I_M_HU hu = handlingUnitsBL.getById(aggregatedHuId);

		if (!handlingUnitsBL.isAggregateHU(hu))
		{
			throw new AdempiereException("extractFromAggregatedByQrCode called for a non aggregated HU!")
					.appendParametersToMessage()
					.setParameter("huId", aggregatedHuId);
		}

		final Consumer<ImmutableSet<HuId>> propagateQRConsumer;
		if (huQRCode != null)
		{
			if (!qrCodeConfigurationService.get().isOneQrCodeForAggregatedHUsEnabledFor(hu))
			{
				throw new AdempiereException("extractFromAggregatedByQrCode cannot be performed as OneQrCodeForAggregatedHUs is not enabled!")
						.appendParametersToMessage()
						.setParameter("huId", aggregatedHuId);
			}

			huQRCodesService.get().assertQRCodeAssignedToHU(huQRCode, aggregatedHuId);
			propagateQRConsumer = (newHUIds) ->  huQRCodesService.get().assign(huQRCode, newHUIds);
		}
		else
		{
			propagateQRConsumer = (newHUIds) -> {};
		}

		if (newLUPackingItem != null)
		{
			final I_M_HU_PI_Item newLUPIItem = handlingUnitsBL.getPackingInstructionItemById(newLUPackingItem);
			final List<I_M_HU> newLUs = tuToNewLUs(hu, qtyTU.toBigDecimal(), newLUPIItem, false);
			final ImmutableSet<HuId> newAggreagtedHUIds = newLUs.stream()
					.map(handlingUnitsDAO::retrieveIncludedHUs)
					.flatMap(Collection::stream)
					.map(I_M_HU::getM_HU_ID)
					.map(HuId::ofRepoId)
					.collect(ImmutableSet.toImmutableSet());
			propagateQRConsumer.accept(newAggreagtedHUIds);
			return newLUs.stream()
					.map(I_M_HU::getM_HU_ID)
					.map(HuId::ofRepoId)
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			final ImmutableSet<HuId> splitHuIds = splitOutTUsFromAggregated(hu, qtyTU);
			propagateQRConsumer.accept(splitHuIds);
			return splitHuIds;
		}
	}

	@NonNull
	public HuId splitOutTU(@NonNull final I_M_HU hu)
	{
		final I_M_HU extractedTU = splitOutTURecord(hu);
		return HuId.ofRepoId(extractedTU.getM_HU_ID());
	}

	@NonNull
	public I_M_HU splitOutTURecord(@NonNull final I_M_HU hu)
	{
		final List<I_M_HU> extractedTUs = tuToNewTUs(hu, QtyTU.ONE.toBigDecimal());
		return CollectionUtils.singleElement(extractedTUs);
	}

    @NonNull
    private HuId splitOutTUFromAggregated(@NonNull final I_M_HU hu)
    {
        final Set<HuId> extractedTUIds = splitOutTUsFromAggregated(hu, QtyTU.ONE);
        return CollectionUtils.singleElement(extractedTUIds);
    }

    @NonNull
    private ImmutableSet<HuId> splitOutTUsFromAggregated(@NonNull final I_M_HU hu, @NonNull final QtyTU qtyTU)
    {
        final QtyTU availableNrOfTUs = handlingUnitsBL.getTUsCount(hu);
        if (qtyTU.isGreaterThan(availableNrOfTUs))
        {
            throw new AdempiereException("Not enough TUs available!")
                    .appendParametersToMessage()
                    .setParameter("qtyTUToMove", qtyTU.toInt())
                    .setParameter("availableQtyTU", availableNrOfTUs.toInt());
        }

        final List<I_M_HU> extractedTUs = HUTransformService.newInstance().tuToNewTUs(hu, qtyTU.toBigDecimal());
        return extractedTUs.stream()
                .map(I_M_HU::getM_HU_ID)
                .map(HuId::ofRepoId)
                .collect(ImmutableSet.toImmutableSet());
    }

	public HuId tusToNewLU(
			@NonNull final List<I_M_HU> tusOrVhus,
			@Nullable final HuPackingInstructionsItemId newLUPIItemId)
	{
		return tusToLU(tusOrVhus, null, newLUPIItemId);
	}

	public HuId tusToExistingLU(
			@NonNull final List<I_M_HU> tusOrVhus,
			@Nullable final I_M_HU existingLU)
	{
        return trxManager.callInThreadInheritedTrx(() ->tusToLU(tusOrVhus, existingLU, null));
	}

	private HuId tusToLU(
			@NonNull final List<I_M_HU> tusOrVhus,
			@Nullable final I_M_HU existingLU,
			@Nullable final HuPackingInstructionsItemId newLUPIItemId)
	{
		I_M_HU lu = existingLU;

		for (final I_M_HU tu : tusOrVhus)
		{

			if (lu == null)
			{
				final I_M_HU_PI_Item newLUPIItem = handlingUnitsBL.getPackingInstructionItemById(Objects.requireNonNull(newLUPIItemId));

				final List<I_M_HU> createdLUs = tuToNewLUs(
						tu,
						QtyTU.ONE.toBigDecimal(),
						newLUPIItem,
						false);
				lu = CollectionUtils.singleElement(createdLUs);
			}
			else
			{
				tuToExistingLU(tu, QtyTU.ONE.toBigDecimal(), lu);
			}
		}

		if (lu == null)
		{
			// shall not happen
			throw new AdempiereException("No LU was created");
		}

		return HuId.ofRepoId(lu.getM_HU_ID());
	}

    @NonNull
    public ImmutableList<I_M_HU> cusToExistingTU(
            @NonNull final List<I_M_HU> sourceCuHUs,
            @NonNull final I_M_HU targetTuHU)
    {
        final ImmutableList.Builder<I_M_HU> resultCollector = ImmutableList.builder();
        sourceCuHUs.forEach(sourceCU -> {
            final Quantity quantity = getSingleProductStorage(sourceCU).getQtyInStockingUOM();
            resultCollector.addAll(cuToExistingTU(sourceCU, quantity, targetTuHU));
        });

        return resultCollector.build();
    }

    public void cusToExistingCU(@NonNull final List<I_M_HU> sourceCuHUs, @NonNull final I_M_HU targetCU)
    {
        final ProductId targetHUProductId = getSingleProductStorage(targetCU).getProductId();

        sourceCuHUs.forEach(sourceCU -> cuToExistingCU(sourceCU, targetCU, targetHUProductId));
    }

    public void cuToExistingCU(
            @NonNull final I_M_HU sourceCuHU,
            @NonNull final I_M_HU targetHU,
            @NonNull final ProductId targetHUProductId)
    {
        trxManager.runInThreadInheritedTrx(() -> cuToExistingCU_InTrx(sourceCuHU, targetHU, targetHUProductId));
    }

    private void cuToExistingCU_InTrx(
            @NonNull final I_M_HU sourceCuHU,
            @NonNull final I_M_HU targetHU,
            @NonNull final ProductId targetHUProductId)
    {
        final IMutableHUContext huContextWithOrgId = huContextFactory.createMutableHUContext(InterfaceWrapperHelper.getContextAware(targetHU));

        final IAllocationSource source = HUListAllocationSourceDestination
                .of(sourceCuHU, AllocationStrategyType.UNIFORM)
                .setDestroyEmptyHUs(true);
        final IAllocationDestination destination = HUListAllocationSourceDestination.of(targetHU, AllocationStrategyType.UNIFORM);

        final IHUProductStorage sourceProductStorage = getSingleProductStorage(sourceCuHU);

        Check.assume(sourceProductStorage.getProductId().equals(targetHUProductId), "Source and Target HU productId must match!");

        HULoader.of(source, destination)
                .load(AllocationUtils.builder()
                        .setHUContext(huContextWithOrgId)
                        .setDateAsToday()
                        .setProduct(sourceProductStorage.getProductId())
                        .setQuantity(sourceProductStorage.getQtyInStockingUOM())
                        .setFromReferencedModel(sourceCuHU)
                        .setForceQtyAllocation(true)
                        .create());
    }
}
