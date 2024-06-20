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

package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUDisplayNameBuilder;
import de.metas.handlingunits.IHUIterator;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.LUTUCUPair;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.impl.CopyHUsCommand.CopyHUsCommandBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.handlingunits.storage.impl.DefaultHUStorageFactory;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.Mutable;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

public class HandlingUnitsBL implements IHandlingUnitsBL
{
	private static final Logger logger = LogManager.getLogger(HandlingUnitsBL.class);

	private final IHUStorageFactory storageFactory = new DefaultHUStorageFactory();
	private final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
	private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	private final ThreadLocal<Boolean> loadInProgress = new ThreadLocal<>();

	@Override
	public IAutoCloseable huLoaderInProgress()
	{
		loadInProgress.set(true);
		return () -> loadInProgress.set(false);
	}

	@Override
	public boolean isHULoaderInProgress()
	{
		return CoalesceUtil.coalesceNotNull(loadInProgress.get(), false);
	}

	@Override
	public I_M_HU getById(@NonNull final HuId huId)
	{
		return handlingUnitsRepo.getById(huId);
	}

	@Override
	public List<I_M_HU> getByIds(@NonNull final Collection<HuId> huIds)
	{
		return handlingUnitsRepo.getByIds(huIds);
	}

	@Override
	public ImmutableMap<HuId, I_M_HU> getByIdsReturningMap(@NonNull final Collection<HuId> huIds)
	{
		final List<I_M_HU> hus = handlingUnitsRepo.getByIds(huIds);
		return Maps.uniqueIndex(hus, hu -> HuId.ofRepoId(hu.getM_HU_ID()));
	}

	@Override
	public IHUStorageFactory getStorageFactory()
	{
		return storageFactory;
	}

	@Override
	public IMutableHUContext createMutableHUContext()
	{
		return huContextFactory.createMutableHUContext();
	}

	@Override
	public IMutableHUContext createMutableHUContext(final IContextAware contextProvider)
	{
		return huContextFactory.createMutableHUContext(contextProvider);
	}

	@Override
	public IMutableHUContext createMutableHUContextForProcessing(final IContextAware contextProvider)
	{
		return huContextFactory.createMutableHUContextForProcessing(contextProvider);
	}

	@Override
	public IMutableHUContext createMutableHUContextForProcessing()
	{
		return huContextFactory.createMutableHUContextForProcessing(PlainContextAware.newWithThreadInheritedTrx());
	}

	@Override
	public IMutableHUContext createMutableHUContext(final Properties ctx, final @NonNull ClientAndOrgId clientAndOrgId)
	{
		return huContextFactory.createMutableHUContext(ctx, clientAndOrgId);
	}

	@Override
	public IMutableHUContext createMutableHUContext(final Properties ctx, final String trxName)
	{
		return huContextFactory.createMutableHUContext(ctx, trxName);
	}

	@Override
	public I_C_UOM getHandlingUOM(final I_M_Product product)
	{
		return productBL.getStockUOM(product);
	}

	@Override
	public I_C_UOM getC_UOM(final I_M_Transaction mtrx)
	{
		final ProductId productId = ProductId.ofRepoId(mtrx.getM_Product_ID());
		return productBL.getStockUOM(productId);
	}

	@Override
	public boolean destroyIfEmptyStorage(@NonNull final I_M_HU huToDestroy)
	{
		//
		// Create HU Context
		final IContextAware context = InterfaceWrapperHelper.getContextAware(huToDestroy);
		final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContext(context);

		//
		// Try to destroy given HU (or some of it's children)
		final Mutable<Boolean> destroyed = new Mutable<>(false);
		huTrxBL.createHUContextProcessorExecutor(huContextInitial)
				.run(huContext -> {
					if (destroyIfEmptyStorage(huContext, huToDestroy))
					{
						destroyed.setValue(true);
					}
					return null;
				});

		return destroyed.getValue();
	}

	@Override
	public boolean destroyIfEmptyStorage(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU huToDestroy)
	{
		trxManager.assertTrxNotNull(huContext);

		//
		// Check given HU and also iterate downstream and destroy all HUs which are empty
		final HUIterator iterator = new HUIterator();
		iterator.setHUContext(huContext);
		iterator.setEnableStorageIteration(false); // we don't care about storages, only HU hierarchy is important for us
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result afterHU(final I_M_HU currentHU)
			{
				final IHUIterator huIterator = getHUIterator();
				final IHUStorageFactory storageFactory = huIterator.getStorageFactoryToUse();
				final IHUStorage storage = storageFactory.getStorage(currentHU);
				if (storage.isEmpty())
				{
					huContext.getEmptyHUListeners().forEach(l -> l.beforeDestroyEmptyHu(currentHU));

					//
					// In case the current HU is the one that we got as parameter (i.e. huToDestroy)
					// then we need to check if its parent becomes empty after our removal and in that case, destroy it.
					// else do nothing because destroying is performed recursively here.
					final boolean destroyOldParentIfEmptyStorage = huIterator.getDepth() == IHUIterator.DEPTH_STARTING_HU;

					//
					// Take out currentHU from its parent
					huTrxBL.unlinkFromParentBeforeDestroy(huContext, currentHU, destroyOldParentIfEmptyStorage);
					//
					// Mark current HU as destroyed
					markDestroyed(huContext, currentHU);
				}
				return Result.CONTINUE;
			}

		});
		iterator.iterateInTrx(Collections.singleton(huToDestroy));

		return isDestroyed(huToDestroy);
	}

	@Override
	public void markDestroyed(@NonNull final IHUContext huContext, final I_M_HU hu)
	{
		if (huContext.isDontDestroyHu(HuId.ofRepoId(hu.getM_HU_ID())))
		{
			logger.info("markDestroyed - the given M_HU_ID={} is temporarily protected from destruction; -> nothing to do", hu.getM_HU_ID());
			return;
		}
		huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Destroyed);
		hu.setIsActive(false);
		handlingUnitsRepo.saveHU(hu);
	}

	@Override
	public void markDestroyed(final IHUContext huContext, final Collection<I_M_HU> hus)
	{
		// services
		huTrxBL.createHUContextProcessorExecutor(huContext)
				.run(huContext1 -> {
					for (final I_M_HU hu : hus)
					{
						if (isDestroyedRefreshFirst(hu))
						{
							continue;
						}
						markDestroyed(huContext1, hu);
					}

					return IHUContextProcessor.NULL_RESULT;
				});
	}

	@Override
	public boolean isDestroyed(final I_M_HU hu)
	{
		return hu.getHUStatus().equals(X_M_HU.HUSTATUS_Destroyed);
	}

	@Override
	public boolean isDestroyedRefreshFirst(final I_M_HU hu) throws AdempiereException
	{
		Check.assume(!InterfaceWrapperHelper.hasChanges(hu), "hu was saved: {}", hu);
		InterfaceWrapperHelper.refresh(hu);

		return isDestroyed(hu);
	}

	@Override
	public String getDisplayName(final I_M_HU hu)
	{
		// NOTE: please keep this implementation as light as possible

		return buildDisplayName(hu)
				.build();
	}

	@Override
	public IHUDisplayNameBuilder buildDisplayName(final I_M_HU hu)
	{
		return new HUDisplayNameBuilder(hu);
	}

	@Override
	public boolean isVirtual(final I_M_HU hu)
	{
		if (hu == null)
		{
			return false;
		}

		final HuPackingInstructionsVersionId piVersionId = HuPackingInstructionsVersionId.ofRepoId(hu.getM_HU_PI_Version_ID());
		return piVersionId.isVirtual();
	}

	@Override
	public boolean isVirtual(final I_M_HU_PI huPI)
	{
		if (huPI == null)
		{
			return false;
		}

		return HuPackingInstructionsId.isVirtualRepoId(huPI.getM_HU_PI_ID());
	}

	@Override
	public boolean isVirtual(@Nullable final I_M_HU_PI_Item piItem)
	{
		return piItem != null && HuPackingInstructionsItemId.isVirtualRepoId(piItem.getM_HU_PI_Item_ID());
	}

	@Override
	public boolean isNoPI(final I_M_HU_PI_Item piItem)
	{
		if (piItem == null)
		{
			return true;
		}

		return HuPackingInstructionsItemId.isTemplateRepoId(piItem.getM_HU_PI_Item_ID());
	}

	@Override
	public boolean isLoadingUnit(final I_M_HU hu)
	{
		if (hu == null)
		{
			return false;
		}

		final String huUnitType = getHU_UnitType(hu);
		return X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType);
	}

	@Override
	public boolean isTransportUnitOrAggregate(final I_M_HU hu)
	{
		return isAggregateHU(hu) || isTransportUnit(hu);
	}

	@Override
	public boolean isTransportUnit(final I_M_HU hu)
	{
		final boolean strict = true; // consider TU only what is TU only
		return isTransportUnit(hu, strict);
	}

	@Override
	public boolean isTransportUnitOrVirtual(final I_M_HU hu)
	{
		final boolean strict = false; // consider VirtualHU as TU too
		return isTransportUnit(hu, strict);
	}

	private boolean isTransportUnit(final I_M_HU hu, final boolean strict)
	{
		if (hu == null)
		{
			return false;
		}

		final String huUnitType = getHU_UnitType(hu);
		if (huUnitType == null)
		{
			return false;
		}

		//
		// Case: given HU is a pure TU
		if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType))
		{
			return true;
		}

		// If we are asked to strictly validate TUs and we reached this point, well our HU is not a TU
		if (strict)
		{
			return false;
		}

		// We can consider Virtual HUs as a substitute for TUs
		return isVirtual(hu);
	}

	@Nullable
	@Override
	public String getHU_UnitType(@NonNull final I_M_HU_PI pi)
	{
		final I_M_HU_PI_Version piVersion = handlingUnitsRepo.retrievePICurrentVersion(pi);
		if (piVersion == null)
		{
			return null;
		}

		return piVersion.getHU_UnitType();
	}

	@Override
	@NonNull
	public String getHU_UnitType(@NonNull final HuPackingInstructionsId piId)
	{
		return handlingUnitsRepo.retrievePICurrentVersion(piId).getHU_UnitType();
	}

	@Nullable
	@Override
	public String getHU_UnitType(@NonNull final I_M_HU hu)
	{
		final I_M_HU_PI_Version piVersion = getPIVersion(hu);
		if (piVersion == null) // could happen while the HU is building
		{
			return null;
		}

		return piVersion.getHU_UnitType();
	}

	@Override
	public boolean isVirtual(final I_M_HU_Item huItem)
	{
		if (huItem == null)
		{
			return false;
		}

		return HuPackingInstructionsItemId.isVirtualRepoId(huItem.getM_HU_PI_Item_ID());
	}

	@Override
	public boolean isPureVirtual(final I_M_HU_Item huItem)
	{
		if (!isVirtual(huItem))
		{
			return false;
		}

		final I_M_HU parentHU = huItem.getM_HU();
		if (parentHU == null || parentHU.getM_HU_ID() <= 0)
		{
			return false;
		}

		final I_M_HU_Item parentItem = handlingUnitsRepo.retrieveParentItem(parentHU);
		if (parentItem == null || parentItem.getM_HU_Item_ID() <= 0)
		{
			return false;
		}

		final String parentItemType = getItemType(parentItem);
		// noinspection RedundantIfStatement
		if (!X_M_HU_PI_Item.ITEMTYPE_Material.equals(parentItemType))
		{
			return false;
		}

		return true;

	}

	@Override
	public boolean isPureVirtual(final I_M_HU hu)
	{
		final boolean isVirtualHU = isVirtual(hu);
		if (!isVirtualHU)
		{
			return false;
		}

		final List<I_M_HU_Item> huItems = handlingUnitsRepo.retrieveItems(hu);
		if (huItems.size() == 0)
		{
			return false; // we don't care
		}

		//
		// HU must be fully pure virtual (for all of it's items)
		for (final I_M_HU_Item virtualItemCandidate : huItems)
		{
			if (!isPureVirtual(virtualItemCandidate))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public String getItemType(@NonNull final I_M_HU_Item huItem)
	{
		final String itemType = huItem.getItemType();
		if (itemType != null)
		{
			return itemType;
		}

		// FIXME: remove it after we migrate all HUs and we have M_HU_Item.ItemType always set
		final I_M_HU_PI_Item piItem = getPIItem(huItem);
		if (piItem != null)
		{
			return piItem.getItemType();
		}
		throw new HUException("Failed getting ItemType for " + huItem + " because neither ItemType nor M_HU_PI_Item_ID is set");
	}

	@Override
	public boolean isTopLevel(@NonNull final I_M_HU hu)
	{
		return handlingUnitsRepo.retrieveParentItem(hu) == null;
	}

	@Override
	public boolean isAnonymousHuPickedOnTheFly(@NonNull final I_M_HU hu)
	{
		// this was done in extreme haste.
		final List<I_M_ShipmentSchedule_QtyPicked> scheduleQtyPickeds = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(hu);

		for (final I_M_ShipmentSchedule_QtyPicked scheduleQtyPicked : scheduleQtyPickeds)
		{
			if (scheduleQtyPicked.isAnonymousHuPickedOnTheFly())
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public I_M_HU getTopLevelParent(@NonNull final I_M_HU hu)
	{
		final TopLevelHusQuery query = TopLevelHusQuery.builder()
				.hus(ImmutableList.of(hu))
				.includeAll(false)
				.build();
		return getTopLevelHUs(query).get(0);
	}

	@Override
	public I_M_HU getTopLevelParent(@NonNull final HuId huId)
	{
		final I_M_HU hu = getById(huId);

		return getTopLevelParent(hu);
	}

	@Override
	public ImmutableSet<HuId> getTopLevelHUs(final @NonNull Collection<HuId> huIds)
	{
		final List<I_M_HU> hus = getByIds(huIds);
		final TopLevelHusQuery query = TopLevelHusQuery.builder()
				.hus(ImmutableList.copyOf(hus))
				.includeAll(false)
				.build();

		return getTopLevelHUs(query).stream()
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public List<I_M_HU> getTopLevelHUs(@NonNull final TopLevelHusQuery request)
	{
		final Set<Integer> seenM_HU_IDs = new HashSet<>();
		final List<I_M_HU> husResult = new ArrayList<>();

		for (final I_M_HU hu : request.getHus())
		{
			// Navigate maximum maxDepth levels and try to get the top level parent
			int maxDepth = 100;

			I_M_HU parent = hu;
			while (parent != null
					// don't go up any further if...
					&& seenM_HU_IDs.add(parent.getM_HU_ID()) // we already reached this parent from another low-level-HU
					&& request.getFilter().test(parent) // our filter rejects the HU
			)
			{
				final I_M_HU parentNew = handlingUnitsRepo.retrieveParent(parent);
				final boolean parentIsTopLevel = parentNew == null;

				if (request.isIncludeAll() || parentIsTopLevel)
				{
					husResult.add(parent);
				}
				parent = parentNew;

				maxDepth--;
				Check.errorIf(maxDepth <= 0, "We navigated more than {} levels deep to get the top level of hu={}. It seems like a data error", maxDepth, hu);
			}
		}
		return husResult;
	}

	@Override
	public LUTUCUPair getTopLevelParentAsLUTUCUPair(@NonNull final I_M_HU hu)
	{
		if (isLoadingUnit(hu))
		{
			return LUTUCUPair.ofLU(hu);
		}
		else if (isTransportUnit(hu))
		{
			@SuppressWarnings("UnnecessaryLocalVariable") final I_M_HU tuHU = hu;
			final I_M_HU luHU = getLoadingUnitHU(tuHU);
			return LUTUCUPair.ofTU(tuHU, luHU);
		}
		else // virtual or aggregate
		{
			@SuppressWarnings("UnnecessaryLocalVariable") final I_M_HU vhu = hu;
			final I_M_HU tuHU = getTransportUnitHU(vhu);
			final I_M_HU luHU = tuHU != null ? getLoadingUnitHU(tuHU) : null;
			return LUTUCUPair.ofVHU(vhu, tuHU, luHU);
		}
	}

	@Override
	public @Nullable
	I_M_HU getLoadingUnitHU(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		I_M_HU lastLU = null;
		I_M_HU currentHU = hu;
		int iterationsCount = 0;
		while (currentHU != null)
		{
			//
			// Avoid cycles
			if (iterationsCount >= 100)
			{
				throw new IllegalStateException("We navigated " + iterationsCount + " levels deep to get the top level LU of " + hu + ". It seems like a data error");
			}
			iterationsCount++;

			//
			// If current HU is a Loading Unit, remember it
			if (isLoadingUnit(currentHU))
			{
				lastLU = currentHU;
			}

			//
			// Navigate one level up
			currentHU = handlingUnitsRepo.retrieveParent(currentHU);
		}

		// Make sure our LU does not have a parent
		// NOTE: shall not happen, but if it does have a parent it seems to be a configuration issue/data inconsistency
		if (lastLU != null && lastLU.getM_HU_Item_Parent_ID() > 0)
		{
			final AdempiereException ex = new AdempiereException("While searching for LU of " + hu + " we found an LU which is included in another handling unit."
					+ "\nThis shall not happen."
					+ "\nWe consider our LU to be NULL.");
			logger.warn(ex.getLocalizedMessage(), ex);

			lastLU = null;
		}

		return lastLU;
	}

	@Override
	public I_M_HU getTransportUnitHU(@NonNull final I_M_HU hu)
	{
		// corner case: top level VHU
		if (isTopLevel(hu) && isVirtual(hu))
		{
			return null;
		}

		I_M_HU lastTU = null;
		I_M_HU currentHU = hu;
		int iterationsCount = 0;
		while (currentHU != null)
		{
			//
			// Avoid cycles
			if (iterationsCount >= 100)
			{
				throw new IllegalStateException("We navigated " + iterationsCount + " levels deep to get the TU of " + hu + ". It seems like a data error.");
			}
			iterationsCount++;

			//
			// If current HU is a Transport Unit or VirtualHU, remember it
			if (isTransportUnitOrVirtual(currentHU))
			{
				lastTU = currentHU;
			}

			//
			// Navigate one level up
			currentHU = handlingUnitsRepo.retrieveParent(currentHU);
		}

		return lastTU;
	}

	@Override
	public boolean isAggregateHU(final I_M_HU hu)
	{
		if (hu == null)
		{
			return false;
		}

		final I_M_HU_Item parentItem = hu.getM_HU_Item_Parent();
		if (parentItem == null)
		{
			return false;
		}

		return X_M_HU_Item.ITEMTYPE_HUAggregate.equals(parentItem.getItemType());
	}

	@Override
	public QtyTU getTUsCount(@NonNull final I_M_HU tuOrAggregatedTU)
	{
		// NOTE: we assume the HU is an TU

		final I_M_HU_Item parentItem = handlingUnitsRepo.retrieveParentItem(tuOrAggregatedTU);
		if (parentItem != null && X_M_HU_Item.ITEMTYPE_HUAggregate.equals(parentItem.getItemType()))
		{
			return QtyTU.ofBigDecimal(parentItem.getQty());
		}
		else
		{
			return QtyTU.ONE;
		}
	}

	@Override
	public HuPackingInstructionsId getPackingInstructionsId(@NonNull final I_M_HU hu)
	{
		final HuPackingInstructionsVersionId piVersionId = HuPackingInstructionsVersionId.ofRepoId(hu.getM_HU_PI_Version_ID());
		return getPackingInstructionsId(piVersionId);
	}

	@Override
	public HuPackingInstructionsId getEffectivePackingInstructionsId(@NonNull final I_M_HU hu)
	{
		final HuPackingInstructionsVersionId huPackingInstructionsVersionId = Optional.ofNullable(getEffectivePIVersion(hu))
				.map(I_M_HU_PI_Version::getM_HU_PI_Version_ID)
				.map(HuPackingInstructionsVersionId::ofRepoId)
				.orElse(HuPackingInstructionsVersionId.VIRTUAL);

		return getPackingInstructionsId(huPackingInstructionsVersionId);
	}

	private HuPackingInstructionsId getPackingInstructionsId(@NonNull final HuPackingInstructionsVersionId piVersionId)
	{
		final HuPackingInstructionsId knownPackingInstructionsId = piVersionId.getKnownPackingInstructionsIdOrNull();
		if (knownPackingInstructionsId != null)
		{
			return knownPackingInstructionsId;
		}
		else
		{
			final I_M_HU_PI_Version piVersion = handlingUnitsRepo.retrievePIVersionById(piVersionId);
			return HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID());
		}
	}

	@Override
	public HuPackingInstructionsId getPackingInstructionsId(@NonNull final HuPackingInstructionsItemId piItemId)
	{
		final I_M_HU_PI_Item piItem = handlingUnitsRepo.getPackingInstructionItemById(piItemId);
		final HuPackingInstructionsVersionId piVersionId = HuPackingInstructionsVersionId.ofRepoId(piItem.getM_HU_PI_Version_ID());
		return getPackingInstructionsId(piVersionId);
	}

	@Nullable
	@Override
	public I_M_HU_PI getPI(final I_M_HU hu)
	{
		final I_M_HU_PI_Version piVersion = getPIVersion(hu);
		return piVersion != null ? getPI(piVersion) : null;
	}

	@Override
	public I_M_HU_PI getPI(@NonNull final I_M_HU_PI_Version piVersion)
	{
		return piVersion.getM_HU_PI();
	}

	@Override
	public I_M_HU_PI getPI(@NonNull final HuPackingInstructionsId id) {return handlingUnitsRepo.getPackingInstructionById(id);}

	@Override
	public String getPIName(@NonNull final HuPackingInstructionsId id)
	{
		return getPI(id).getName();
	}

	@Override
	public I_M_HU_PI getPI(@NonNull final HUPIItemProductId huPIItemProductId)
	{
		final I_M_HU_PI_Item_Product huPIItemProduct = huPIItemProductDAO.getRecordById(huPIItemProductId);
		final HuPackingInstructionsItemId packingInstructionsItemId = HuPackingInstructionsItemId.ofRepoId(huPIItemProduct.getM_HU_PI_Item_ID());
		return getPI(packingInstructionsItemId);
	}

	@Override
	public I_M_HU_PI_Version getPIVersion(final I_M_HU hu)
	{
		final HuPackingInstructionsVersionId piVersionId = HuPackingInstructionsVersionId.ofRepoId(hu.getM_HU_PI_Version_ID());
		return handlingUnitsRepo.retrievePIVersionById(piVersionId);
	}

	@Nullable
	@Override
	public I_M_HU_PI_Item getPIItem(@NonNull final I_M_HU_Item huItem)
	{
		final HuPackingInstructionsItemId piItemId = HuPackingInstructionsItemId.ofRepoIdOrNull(huItem.getM_HU_PI_Item_ID());
		return piItemId != null
				? handlingUnitsRepo.getPackingInstructionItemById(piItemId)
				: null;
	}

	@Override
	public I_M_HU_PI getPI(@NonNull final HuPackingInstructionsItemId piItemId)
	{
		final I_M_HU_PI_Item piItem = handlingUnitsRepo.getPackingInstructionItemById(piItemId);
		return getPI(piItem);
	}

	@Override
	public I_M_HU_PI getPI(@NonNull final I_M_HU_PI_Item piItem)
	{
		final HuPackingInstructionsVersionId piVersionId = HuPackingInstructionsVersionId.ofRepoId(piItem.getM_HU_PI_Version_ID());
		return getPI(piVersionId);
	}

	@Override
	public I_M_HU_PI getPI(@NonNull final HuPackingInstructionsVersionId piVersionId)
	{
		final I_M_HU_PI_Version piVersion = handlingUnitsRepo.retrievePIVersionById(piVersionId);
		return getPI(piVersion);
	}

	@NonNull
	@Override
	public I_M_HU_PI getIncludedPI(@NonNull final I_M_HU_Item huItem)
	{
		final I_M_HU_PI_Item piItem = getPIItem(huItem);
		if (piItem == null)
		{
			throw new AdempiereException("No PI Item defined for " + huItem);
		}

		return getIncludedPI(piItem);
	}

	@Override
	public @NonNull I_M_HU_PI getIncludedPI(@NonNull final I_M_HU_PI_Item piItem)
	{
		return handlingUnitsRepo.getIncludedPI(piItem);
	}

	@Override
	@Nullable
	public I_M_HU_PI_Version getEffectivePIVersion(final I_M_HU hu)
	{
		if (!isAggregateHU(hu))
		{
			return getPIVersion(hu);
		}
		else
		{
			// note: if hu is an aggregate HU, then there won't be an NPE here.
			final I_M_HU_PI_Item parentPIItem = getPIItem(hu.getM_HU_Item_Parent());
			if (parentPIItem == null)
			{
				// this is the case while the aggregate HU is still "under construction" by the HUBuilder and LUTU producer.
				return IHUBuilder.BUILDER_INVOCATION_HU_PI_VERSION.getValue(hu);
			}

			final HuPackingInstructionsId includedPIId = HuPackingInstructionsId.ofRepoId(parentPIItem.getIncluded_HU_PI_ID());
			return handlingUnitsRepo.retrievePICurrentVersionOrNull(includedPIId);
		}
	}

	@Override
	public @Nullable
	I_M_HU_PI getEffectivePI(final I_M_HU hu)
	{
		if (!isAggregateHU(hu))
		{
			return getPI(hu);
		}

		// note: if hu is an aggregate HU, then there won't be an NPE here.
		final I_M_HU_PI_Item parentPIItem = getPIItem(hu.getM_HU_Item_Parent());
		if (parentPIItem == null)
		{
			return null; // this is the case while the aggregate HU is still "under construction" by the HUBuilder and LUTU producer.
		}

		return getIncludedPI(parentPIItem);
	}

	@Override
	public HUType getHUUnitType(@NonNull final I_M_HU hu)
	{
		final I_M_HU_PI_Version huPIVersion = getEffectivePIVersion(hu);
		if (huPIVersion == null)
		{
			throw new AdempiereException("Cannot determine M_HU_PI_Version of " + hu);
		}

		final HUType huType = HUType.ofCodeOrNull(huPIVersion.getHU_UnitType());
		if (huType == null)
		{
			throw new AdempiereException("Cannot determine HUType of " + huPIVersion);
		}

		return huType;
	}

	@Override
	public ImmutableSet<HuId> getVHUIds(@NonNull final HuId huId)
	{
		final List<I_M_HU> vhus = getVHUs(huId);
		return extractHuIds(vhus);
	}

	private static ImmutableSet<HuId> extractHuIds(final Collection<I_M_HU> hus)
	{
		return hus.stream()
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public ImmutableSet<HuId> getVHUIds(@NonNull final Set<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<I_M_HU> allVHUs = new ArrayList<>();
		for (final I_M_HU hu : getByIds(huIds))
		{
			final List<I_M_HU> vhus = getVHUs(hu);
			allVHUs.addAll(vhus);
		}

		return extractHuIds(allVHUs);
	}

	@Override
	public List<I_M_HU> getVHUs(@NonNull final HuId huId)
	{
		final I_M_HU hu = getById(huId);
		return getVHUs(hu);
	}

	@Override
	public List<I_M_HU> getVHUs(@NonNull final I_M_HU hu)
	{
		if (isVirtual(hu))
		{
			return ImmutableList.of(hu);
		}

		final List<I_M_HU> vhus = new ArrayList<>();
		new HUIterator()
				.setEnableStorageIteration(false)
				.setListener(new HUIteratorListenerAdapter()
				{
					@Override
					public Result afterHU(final I_M_HU currentHu)
					{
						if (isVirtual(currentHu))
						{
							vhus.add(currentHu);
						}
						return Result.CONTINUE;
					}
				}).iterate(hu);

		return vhus;
	}

	@Override
	public IHUQueryBuilder createHUQueryBuilder()
	{
		return handlingUnitsRepo.createHUQueryBuilder();
	}

	@Override
	public AttributesKey getAttributesKeyForInventory(@NonNull final I_M_HU hu)
	{
		final IAttributeStorageFactory attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);
		return getAttributesKeyForInventory(attributeStorage);
	}

	@Override
	public AttributesKey getAttributesKeyForInventory(@NonNull final IAttributeSet attributeSet)
	{
		final Predicate<I_M_Attribute> nonWeightRelatedAttr =
				attribute -> !Weightables.isWeightableAttribute(AttributeCode.ofString(attribute.getValue()));
		final ImmutableAttributeSet requestedAttributeSet = ImmutableAttributeSet.createSubSet(attributeSet, nonWeightRelatedAttr);

		return AttributesKeys
				.createAttributesKeyFromAttributeSet(requestedAttributeSet)
				.orElse(AttributesKey.NONE);
	}

	@Override
	public void setHUStatus(@NonNull final Collection<I_M_HU> hus, @NonNull final String huStatus)
	{
		if (hus.isEmpty())
		{
			return;
		}

		setHUStatus(hus, createMutableHUContext(), huStatus);
	}

	@Override
	public void setHUStatus(@NonNull final Collection<I_M_HU> hus, @NonNull final IHUContext huContext, @NonNull final String huStatus)
	{
		if (hus.isEmpty())
		{
			return;
		}

		hus.forEach(hu -> {
			huStatusBL.setHUStatus(huContext, hu, huStatus);
			handlingUnitsRepo.saveHU(hu);
		});
	}

	@Override
	public void setHUStatus(@NonNull final I_M_HU hu, @NonNull final IContextAware contextProvider, @NonNull final String huStatus)
	{
		final IHUContext huContext = createMutableHUContext(contextProvider);

		huStatusBL.setHUStatus(huContext, hu, huStatus);

		handlingUnitsRepo.saveHU(hu);
	}

	@Override
	public boolean isEmptyStorage(@NonNull final I_M_HU hu)
	{
		return getStorageFactory()
				.streamHUProductStorages(hu)
				.allMatch(IProductStorage::isEmpty);
	}

	@Override
	public CopyHUsResponse copyAsPlannedHUs(@NonNull final Collection<HuId> huIdsToCopy)
	{
		return copyAsPlannedHUs()
				.huIdsToCopy(huIdsToCopy)
				.build()
				.execute();
	}

	@Override
	public CopyHUsCommandBuilder copyAsPlannedHUs()
	{
		return CopyHUsCommand.builder();
	}

	@Override
	public I_M_HU copyAsPlannedHU(@NonNull final HuId huId)
	{
		return copyAsPlannedHUs(ImmutableList.of(huId)).getSingleNewHU();
	}

	@Override
	public AttributeSetInstanceId createASIFromHUAttributes(@NonNull final ProductId productId, @NonNull final HuId huId)
	{
		final I_M_HU hu = getById(huId);
		return createASIFromHUAttributes(productId, hu);
	}

	@Override
	public AttributeSetInstanceId createASIFromHUAttributes(@NonNull final ProductId productId, @NonNull final I_M_HU hu)
	{
		final ImmutableAttributeSet attributes = getImmutableAttributeSet(hu);

		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASIWithASFromProductAndInsertAttributeSet(productId, attributes);
		return AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
	}

	@Override
	public ImmutableAttributeSet getImmutableAttributeSet(@NonNull final I_M_HU hu)
	{
		return attributeStorageFactoryService.createHUAttributeStorageFactory().getImmutableAttributeSet(hu);
	}

	@Override
	public List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(
			@NonNull final HuPackingInstructionsId packingInstructionsId,
			@Nullable final String huUnitType,
			@Nullable final BPartnerId bpartnerId)
	{
		return handlingUnitsRepo.retrieveParentPIItemsForParentPI(packingInstructionsId, huUnitType, bpartnerId);
	}

	@Override
	public I_M_HU_PI_Item getPackingInstructionItemById(final HuPackingInstructionsItemId piItemId)
	{
		return handlingUnitsRepo.getPackingInstructionItemById(piItemId);
	}

	@Override
	public void setClearanceStatusRecursively(
			@NonNull final HuId huId,
			@NonNull final ClearanceStatusInfo clearanceStatusInfo)
	{
		final I_M_HU hu = handlingUnitsRepo.getById(huId);

		if (hu == null)
		{
			throw new AdempiereException("Hu with ID: " + huId.getRepoId() + " does not exist!");
		}

		hu.setClearanceStatus(clearanceStatusInfo.getClearanceStatus().getCode());
		hu.setClearanceNote(clearanceStatusInfo.getClearanceNote());

		final InstantAndOrgId clearanceDate = clearanceStatusInfo.getClearanceDate();
		hu.setClearanceDate(clearanceDate != null ? clearanceDate.toTimestamp() : null);

		handlingUnitsRepo.saveHU(hu);

		handlingUnitsRepo.retrieveIncludedHUs(hu)
				.forEach(includedHU -> setClearanceStatusRecursively(HuId.ofRepoId(includedHU.getM_HU_ID()), clearanceStatusInfo));
	}

	@Override
	public boolean isHUHierarchyCleared(@NonNull final HuId huId)
	{
		return isWholeHierarchyCleared(getTopLevelParent(huId));
	}

	@Override
	@NonNull
	public ITranslatableString getClearanceStatusCaption(@NonNull final ClearanceStatus clearanceStatus)
	{
		return adReferenceDAO.retrieveListNameTranslatableString(X_M_HU.CLEARANCESTATUS_AD_Reference_ID, clearanceStatus.getCode());
	}

	private boolean isWholeHierarchyCleared(@NonNull final I_M_HU hu)
	{
		if (!isCleared(hu))
		{
			return false;
		}

		final List<I_M_HU> includedHUs = handlingUnitsRepo.retrieveIncludedHUs(hu);

		for (final I_M_HU includedHU : includedHUs)
		{
			final boolean isIncludedHierarchyCleared = isWholeHierarchyCleared(includedHU);

			if (!isIncludedHierarchyCleared)
			{
				return false;
			}
		}
		return true;
	}

	private boolean isCleared(final I_M_HU hu)
	{
		return Check.isBlank(hu.getClearanceStatus()) ||
				ClearanceStatus.Cleared.getCode().equals(hu.getClearanceStatus());
	}

	@Override
	public LocatorId getLocatorId(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsRepo.getById(huId);
		return IHandlingUnitsBL.extractLocatorId(hu);
	}

	@Override
	public Optional<HuId> getFirstHuIdByExternalLotNo(final String externalLotNo)
	{
		return handlingUnitsRepo.getFirstHuIdByExternalLotNo(externalLotNo);
	}

	@Override
	public List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(
			@NonNull final I_M_HU_PI huPI,
			@Nullable final String huUnitType,
			@Nullable final BPartnerId bpartnerId)
	{
		return handlingUnitsRepo.retrieveParentPIItemsForParentPI(huPI, huUnitType, bpartnerId);
	}

	@Override
	public void reactivateDestroyedHU(@NonNull final I_M_HU hu, @NonNull final IContextAware contextProvider)
	{
		if (!isDestroyed(hu))
		{
			logger.debug("reactivateDestroyedHU called for a non destroyed HU! M_HU_ID={}", hu);
			return;
		}
		final IHUContext huContext = createMutableHUContext(contextProvider);

		final boolean allStoragesAreEmpty = getStorageFactory().getStorage(hu).isEmpty();

		if (allStoragesAreEmpty)
		{
			final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
			huAttributes.setSaveOnChange(true);

			final IWeightable huWeight = Weightables.wrap(huAttributes);
			huWeight.setWeightNet(BigDecimal.ZERO);
		}

		huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);
		hu.setIsActive(true);
		handlingUnitsRepo.saveHU(hu);
	}

}
