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

package de.metas.handlingunits;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.impl.CopyHUsCommand.CopyHUsCommandBuilder;
import de.metas.handlingunits.impl.CopyHUsResponse;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.ITranslatableString;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

public interface IHandlingUnitsBL extends ISingletonService
{
	/**
	 * Supposed to be called only from the interal HULoader.
	 */
	IAutoCloseable huLoaderInProgress();

	/**
	 * @return {@code true} if the HULoader is currently doing its thing within this thread.
	 */
	boolean isHULoaderInProgress();

	I_M_HU getById(HuId huId);

	List<I_M_HU> getByIds(Collection<HuId> huIds);

	List<I_M_HU> getVHUs(HuId huId);

	HUType getHUUnitType(@NonNull I_M_HU hu);

	ImmutableSet<HuId> getVHUIds(HuId huId);

	ImmutableSet<HuId> getVHUIds(Set<HuId> huIds);

	List<I_M_HU> getVHUs(I_M_HU hu);

	IHUQueryBuilder createHUQueryBuilder();

	ImmutableMap<HuId, I_M_HU> getByIdsReturningMap(@NonNull Collection<HuId> huIds);

	List<I_M_HU> getBySelectionId(@NonNull PInstanceId selectionId);

	Set<HuId> getHuIdsBySelectionId(@NonNull PInstanceId selectionId);

	/**
	 * @return default storage factory
	 */
	IHUStorageFactory getStorageFactory();

	IMutableHUContext createMutableHUContext();

	/**
	 * @return mutable HU context for <code>contextProvider</code>
	 */
	IMutableHUContext createMutableHUContext(IContextAware contextProvider);

	/**
	 * Create HU Context.
	 * <p>
	 * Compared with {@link #createMutableHUContext(IContextAware)} this method optimizes the HU Context for HU generation/processing (i.e. enable caching etc etc).
	 *
	 * @return mutable HU context for <code>contextProvider</code>
	 */
	IMutableHUContext createMutableHUContextForProcessing(IContextAware contextProvider);

	IMutableHUContext createMutableHUContextForProcessing();

	/**
	 * Creates a mutable context with the given <code>ctx</code> (may not be <code>null</code>) and <code>trxName=ITrx.TRXNAME_None</code>.
	 */
	IMutableHUContext createMutableHUContext(Properties ctx, final @NonNull ClientAndOrgId clientAndOrgId);

	/**
	 * Creates a mutable context with the given <code>ctx</code> and <code>trxName</code>.
	 */
	IMutableHUContext createMutableHUContext(Properties ctx, String trxName);

	I_C_UOM getHandlingUOM(I_M_Product product);

	I_C_UOM getC_UOM(I_M_Transaction mtrx);

	/**
	 * @return true if HU was destroyed
	 */
	boolean isDestroyed(I_M_HU hu);

	/**
	 * Refresh HU first before checking if it's destroyed
	 *
	 * @return true if HU was destroyed
	 * @throws AdempiereException if hu has local changes
	 */
	boolean isDestroyedRefreshFirst(I_M_HU hu) throws AdempiereException;

	/**
	 * Build a user friendly display name of given HU.
	 * <p>
	 * If u want more options, please use {@link #buildDisplayName(I_M_HU)}.
	 *
	 * @return display name (user friendly) of given HU
	 */
	String getDisplayName(I_M_HU hu);

	IHUDisplayNameBuilder buildDisplayName(I_M_HU hu);

	/**
	 * @param hu may be {@code null}
	 * @return true if this is a virtual HU
	 */
	boolean isVirtual(I_M_HU hu);

	/**
	 * @return {@code true} if the given {@code huItems}'s {@code M_HU_PI_Item_ID} is the "virtual" one, see {@link IHandlingUnitsDAO#retrieveVirtualPIItem(Properties)}.
	 */
	boolean isVirtual(I_M_HU_Item huItem);

	/**
	 * Checks if given <code>huItem</code> is part of a pure virtual HU.
	 * <p>
	 * A HU is considered pure virtual when:
	 * <ul>
	 * <li>{@link #isVirtual(I_M_HU)}
	 * <li>and its parent HU Item ({@link I_M_HU#getM_HU_Item_Parent()}) is a material line (i.e. {@link #getItemType(I_M_HU_Item)} is {@link X_M_HU_PI_Item#ITEMTYPE_Material})
	 * </ul>
	 * <p>
	 * e.g.
	 * <ul>
	 * <li>a VHU on a palet is virtual but it's NOT PURE virtual (i.e. it's parent HU Item is an item of type {@link X_M_HU_PI_Item#ITEMTYPE_HandlingUnit})
	 * <li>a VHU, top level, is virtual but it's NOT PURE virtual
	 * <li>a VHU linked to an material HU Item IS PURE virtual
	 * </ul>
	 *
	 * @return true if this item is part of a pure virtual HU.
	 */
	boolean isPureVirtual(I_M_HU_Item huItem);

	/**
	 * A pure virtual HU will have {@link IHandlingUnitsBL#isPureVirtual(I_M_HU_Item)} true for all of the HU's items.
	 *
	 * @return true if the HU is considered a "pure virtual" HU
	 */
	boolean isPureVirtual(I_M_HU hu);

	/**
	 * Destroy given HU or some of it's children which are empty.
	 * <b>NOTE: for a full description of everything this method does, consult the javadoc of {@link #destroyIfEmptyStorage(IHUContext, I_M_HU)}.</b>
	 *
	 * @return true if given HU was fully destroyed now or it was already destroyed
	 * @see #destroyIfEmptyStorage(IHUContext, I_M_HU)
	 */
	boolean destroyIfEmptyStorage(I_M_HU hu);

	/**
	 * Destroy given HU or some of it's children which are empty.
	 * <p>
	 * NOTE 1: if you are not calling this method from a code chunk which is injected to be executed inside HU API (which could be your case!) please consider using
	 * {@link #destroyIfEmptyStorage(I_M_HU)}.
	 * <p>
	 * NOTE 2: the destroyed HUs are also saved.
	 * Additionally, this method does:
	 * <ul>
	 * <li>destroys any of it's HU children which have empty storage
	 * <li>if HU's parent remains empty after this HU is destroyed, it will destroy the parent also (recursively to the top).
	 * </ul>
	 *
	 * @return true if given HU was fully destroyed now or it was already destroyed
	 */
	boolean destroyIfEmptyStorage(IHUContext huContext, I_M_HU hu);

	/**
	 * Gets HU Item Type.
	 * <p>
	 * <b>Important:</b> HU items that were created prior to https://github.com/metasfresh/metasfresh/issues/460 might have an empty
	 * {@link I_M_HU_Item#COLUMN_ItemType}. So unless you know what you do, please use this method rather than {@link I_M_HU_Item#getItemType()}, because otherwise you might stumble over an old/pre-existing item and get wrong results.
	 *
	 * @see I_M_HU_PI_Item#getItemType()
	 */
	String getItemType(I_M_HU_Item huItem);

	/**
	 * Gets top level parent of given HU (i.e. the top of hierarchy) or given HU if that HU does not have a parent
	 *
	 * @return top level parent; never return null
	 */
	I_M_HU getTopLevelParent(I_M_HU hu);

	I_M_HU getTopLevelParent(HuId huId);

	ImmutableSet<HuId> getTopLevelHUs(@NonNull Collection<HuId> huIds);

	/**
	 * Gets top level HUs of given HUs (i.e. the top of hierarchy).
	 *
	 * @param query see {@link TopLevelHusQuery}.
	 * @return top level HUs; never return {@code null}
	 */
	List<I_M_HU> getTopLevelHUs(TopLevelHusQuery query);

	CopyHUsResponse copyAsPlannedHUs(@NonNull Collection<HuId> huIdsToCopy);

	CopyHUsCommandBuilder copyAsPlannedHUs();

	I_M_HU copyAsPlannedHU(@NonNull HuId huId);

	AttributeSetInstanceId createASIFromHUAttributes(@NonNull ProductId productId, @NonNull HuId huId);

	AttributeSetInstanceId createASIFromHUAttributes(@NonNull ProductId productId, @NonNull I_M_HU hu);

	ImmutableAttributeSet getImmutableAttributeSet(@NonNull I_M_HU hu);

	List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(
			@NonNull HuPackingInstructionsId packingInstructionsId,
			@Nullable String huUnitType,
			@Nullable BPartnerId bpartnerId);

	I_M_HU_PI_Item getPackingInstructionItemById(HuPackingInstructionsItemId piItemId);

	@NonNull
	WarehouseId getWarehouseIdForHuId(@NonNull HuId huId);

	Optional<HuId> getFirstHuIdByExternalLotNo(String externalLotNo);

	List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(
			@NonNull I_M_HU_PI huPI,
			@Nullable String huUnitType,
			@Nullable BPartnerId bpartnerId);

	void reactivateDestroyedHU(@NonNull I_M_HU hu, @NonNull IContextAware contextProvider);

	@Builder
	@Value
	class TopLevelHusQuery
	{
		/**
		 * May be empty, but not {@code null}
		 */
		@NonNull
		Collection<I_M_HU> hus;

		/**
		 * If {@code true} then e.g. for a CU, not only the LU will be returned, but also the intermediate TU and the CU itself.<br>
		 * If {@code false}, then any hu will only be returned if it is a top level HU itself.
		 */
		boolean includeAll;

		/**
		 * Optional; if not provided, then every hu passes the default filter.<br>
		 * If the filter returns {@code false} for a given HU, then neither that HU or its parents will be added to the result.
		 */
		@Default
		@NonNull Predicate<I_M_HU> filter = hu -> true;
	}

	/**
	 * Gets top level parent of given HU (i.e. the top of hierarchy) or given HU if that HU does not have a parent.
	 *
	 * @return top level parent; never return null; more preciselly:
	 * <ul>
	 * <li>if given HU is a VHU, then returned LUTUCU pair will have: VHU=given HU, TU=parent TU, LU=parent LU(top level)
	 * <li>if given HU is a TU, then returned LUTUCU pair will have: VHU=null, TU=given HU, LU=parent LU(top level)
	 * <li>if given HU is a LU, then returned LUTUCU pair will have: VHU=null, TU=null, LU=given HU(top level)
	 * </ul>
	 */
	LUTUCUPair getTopLevelParentAsLUTUCUPair(I_M_HU hu);

	/**
	 * Determines if the handling unit is a loading unit (type {@link X_M_HU_PI_Version#HU_UNITTYPE_LoadLogistiqueUnit} )
	 *
	 * @param hu maybe be {@code null}. In that case, {@code false} is returned.
	 * @return true if loading unit (LU)
	 */
	boolean isLoadingUnit(I_M_HU hu);

	/**
	 * Determines if the handling unit is strictly a transport unit (a.k.a. trade unit, type {@link X_M_HU_PI_Version#HU_UNITTYPE_TransportUnit} ).
	 *
	 * @return true if transport unit (TU)
	 */
	boolean isTransportUnit(I_M_HU hu);

	/**
	 * Determines if the handling unit is a transport unit (a.k.a. trade unit, type {@link X_M_HU_PI_Version#HU_UNITTYPE_TransportUnit} ) or is a virtual Handling Unit which can be stored on a LU.
	 *
	 * @return true if transport unit (TU) or virtual handling unit
	 */
	boolean isTransportUnitOrVirtual(I_M_HU hu);

	/**
	 * @return true if the HU is a TU or an aggregated TU
	 */
	boolean isTransportUnitOrAggregate(I_M_HU hu);

	/**
	 * Checks if given handling unit is top level (i.e. it has no parents)
	 *
	 * @return true if is a top level handling unit
	 */
	boolean isTopLevel(I_M_HU hu);

	/**
	 * If an HU is picked "on-the-fly" or "anonymous" then it is added to a Shipment even though the user did not actually pick that particular HU. This is done to keep the metasfresh stock quantity near the real quantity and avoid some inventory effort for users that don't want to use metasfresh's picking.
	 *
	 * @param hu hu to check if it is picked on the fly
	 * @return true if it is picked on the fly; false otherwise
	 * @see the following 2 methods:
	 * - de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService#createShipmentSchedulesWithHUForQtyToDeliver
	 * - de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService#pickHUsOnTheFly
	 */
	@SuppressWarnings("JavadocReference")
	boolean isAnonymousHuPickedOnTheFly(@NonNull final I_M_HU hu);

	/**
	 * Gets top level LU of given HU.
	 * <p>
	 * If given HU is a loading unit then given HU will be returned.
	 *
	 * @return LU handling unit or null.
	 */
	@Nullable
	I_M_HU getLoadingUnitHU(I_M_HU hu);

	/**
	 * Get the TU of the given (included) HU.
	 *
	 * @return TU handling unit or null.
	 */
	@Nullable
	I_M_HU getTransportUnitHU(I_M_HU hu);

	/**
	 * Returns the {@link I_M_HU_PI_Version#COLUMNNAME_HU_UnitType} value of the given <code>pi</code>'s version.
	 *
	 * @return unit type
	 */
	@Nullable
	String getHU_UnitType(I_M_HU_PI pi);

	@NonNull
	String getHU_UnitType(@NonNull HuPackingInstructionsId piId);

	/**
	 * Returns the {@link I_M_HU_PI_Version#COLUMNNAME_HU_UnitType} value of the given <code>hu</code>'s.
	 *
	 * @return unit type
	 */
	@Nullable
	String getHU_UnitType(I_M_HU hu);

	boolean isVirtual(@Nullable I_M_HU_PI_Item piItem);

	/**
	 * @return true if given <code>piItem</code> is null or is NoPI
	 */
	boolean isNoPI(I_M_HU_PI_Item piItem);

	/**
	 * @return true if given HU PI is not null and is Virtual PI
	 */
	boolean isVirtual(I_M_HU_PI huPI);

	/**
	 * Marks the hu as destroyed, but doesn't handle the storages
	 */
	void markDestroyed(IHUContext huContext, I_M_HU hu);

	void saveHU(I_M_HU hu);

	/**
	 * Marks all HUs as destroyed, but doesn't handle the storages.
	 *
	 * @param hus HUs to mark as destroyed
	 */
	void markDestroyed(IHUContext huContext, Collection<I_M_HU> hus);

	/**
	 * Checks if the given {@code hu} is a aggregate HU.<br>
	 * An aggregate HU is on a virtual HU located below an LU that represents a whole number of TUs at once.
	 *
	 * @param hu optional, may be {@code null}.
	 * @return {@code true} if the given {@code hu} is not {@code null} and if it also has a {@code M_HU_Item_Parent} with {@code ItemType} being {@link X_M_HU_Item#ITEMTYPE_HUAggregate}.
	 */
	boolean isAggregateHU(I_M_HU hu);

	QtyTU getTUsCount(final I_M_HU tuOrAggregatedTU);

	HuPackingInstructionsId getPackingInstructionsId(@NonNull I_M_HU hu);

	HuPackingInstructionsId getEffectivePackingInstructionsId(@NonNull I_M_HU hu);

	@Nullable
	I_M_HU_PI getPI(I_M_HU hu);

	I_M_HU_PI getPI(@NonNull I_M_HU_PI_Version piVersion);

	I_M_HU_PI getPI(@NonNull HuPackingInstructionsId id);

	String getPIName(@NonNull HuPackingInstructionsId id);

	I_M_HU_PI getPI(@NonNull HUPIItemProductId huPIItemProductId);

	I_M_HU_PI_Version getPIVersion(I_M_HU hu);

	@Nullable
	I_M_HU_PI_Item getPIItem(I_M_HU_Item huItem);

	I_M_HU_PI getPI(@NonNull HuPackingInstructionsItemId piItemId);

	HuPackingInstructionsId getPackingInstructionsId(@NonNull HuPackingInstructionsItemId piItemId);

	I_M_HU_PI getPI(@NonNull I_M_HU_PI_Item piItem);

	I_M_HU_PI getPI(@NonNull HuPackingInstructionsVersionId piVersionId);

	@NonNull
	I_M_HU_PI getIncludedPI(@NonNull I_M_HU_Item huItem);

	@NonNull
	I_M_HU_PI getIncludedPI(@NonNull I_M_HU_PI_Item piItem);

	/**
	 * If the given {@code hu} is a aggregate HU, return the PI version of the HUs that are <i>represented</i> within the aggregate HU.<br>
	 * Otherwise, return the given {@code hu}'s own/direct PI version.
	 */
	I_M_HU_PI_Version getEffectivePIVersion(I_M_HU hu);

	/**
	 * If the given {@code hu} is a aggregate HU, return the PI of the HUs that are <i>represented</i> within the aggregate HU.<br>
	 * Otherwise, return the given {@code hu}'s own/direct PI.
	 */
	@Nullable
	I_M_HU_PI getEffectivePI(I_M_HU hu);

	@Nullable
	I_M_HU_PI getEffectivePI(@NonNull HuId huId);

	@Nullable
	static BPartnerId extractBPartnerIdOrNull(final I_M_HU hu)
	{
		return BPartnerId.ofRepoIdOrNull(hu.getC_BPartner_ID());
	}

	@Nullable
	static I_C_BPartner extractBPartnerOrNull(final I_M_HU hu)
	{
		final BPartnerId bpartnerId = extractBPartnerIdOrNull(hu);
		return bpartnerId != null
				? Services.get(IBPartnerDAO.class).getById(bpartnerId)
				: null;
	}

	@Nullable
	static I_C_BPartner_Location extractBPartnerLocationOrNull(final I_M_HU hu)
	{
		final BPartnerLocationId bpartnerLocationId = extractBPartnerLocationIdOrNull(hu);
		return bpartnerLocationId != null
				? Services.get(IBPartnerDAO.class).getBPartnerLocationByIdEvenInactive(bpartnerLocationId)
				: null;
	}

	@Nullable
	static BPartnerLocationId extractBPartnerLocationIdOrNull(final I_M_HU hu)
	{
		return BPartnerLocationId.ofRepoIdOrNull(hu.getC_BPartner_ID(), hu.getC_BPartner_Location_ID());
	}

	LocatorId getLocatorId(HuId huId);

	static LocatorId extractLocatorId(final I_M_HU hu)
	{
		final LocatorId locatorId = extractLocatorIdOrNull(hu);
		if (locatorId == null)
		{
			throw new HUException("Warehouse Locator shall be set for: " + hu);
		}
		return locatorId;
	}

	@Nullable
	static LocatorId extractLocatorIdOrNull(final I_M_HU hu)
	{
		final int locatorRepoId = hu.getM_Locator_ID();
		return Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(locatorRepoId);
	}

	static I_M_Locator extractLocator(final I_M_HU hu)
	{
		final I_M_Locator locator = extractLocatorOrNull(hu);
		if (locator == null)
		{
			throw new HUException("Warehouse Locator shall be set for: " + hu);
		}
		return locator;
	}

	@Nullable
	static I_M_Locator extractLocatorOrNull(final I_M_HU hu)
	{
		final int locatorRepoId = hu.getM_Locator_ID();
		return locatorRepoId > 0
				? InterfaceWrapperHelper.create(Services.get(IWarehouseDAO.class).getLocatorByRepoId(locatorRepoId), I_M_Locator.class)
				: null;
	}

	static WarehouseId extractWarehouseId(final I_M_HU hu)
	{
		final WarehouseId warehouseId = extractWarehouseIdOrNull(hu);
		if (warehouseId == null)
		{
			throw new HUException("Warehouse Locator shall be set for: " + hu);
		}

		return warehouseId;
	}

	static I_M_Warehouse extractWarehouse(final I_M_HU hu)
	{
		final I_M_Warehouse warehouse = extractWarehouseOrNull(hu);
		if (warehouse == null)
		{
			throw new HUException("Warehouse Locator shall be set for: " + hu);
		}
		return warehouse;
	}

	@Nullable
	static WarehouseId extractWarehouseIdOrNull(final I_M_HU hu)
	{
		final int locatorRepoId = hu.getM_Locator_ID();
		if (locatorRepoId <= 0)
		{
			return null;
		}

		return Services.get(IWarehouseDAO.class).getWarehouseIdByLocatorRepoId(locatorRepoId);
	}

	@Nullable
	static I_M_Warehouse extractWarehouseOrNull(final I_M_HU hu)
	{
		final WarehouseId warehouseId = extractWarehouseIdOrNull(hu);
		return warehouseId != null
				? InterfaceWrapperHelper.create(Services.get(IWarehouseDAO.class).getById(warehouseId), I_M_Warehouse.class)
				: null;
	}

	@Nullable
	static I_M_HU_PI_Item_Product extractPIItemProductOrNull(@NonNull final I_M_HU hu)
	{
		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNull(hu.getM_HU_PI_Item_Product_ID());
		return piItemProductId != null
				? Services.get(IHUPIItemProductDAO.class).getRecordById(piItemProductId)
				: null;
	}

	AttributesKey getAttributesKeyForInventory(@NonNull I_M_HU hu);

	AttributesKey getAttributesKeyForInventory(@NonNull IAttributeSet attributeSet);

	void setHUStatus(@NonNull Collection<I_M_HU> hus, @NonNull String huStatus);

	void setHUStatus(@NonNull Collection<I_M_HU> hus, @NonNull IHUContext huContext, @NonNull String huStatus);

	void setHUStatus(I_M_HU hu, IContextAware contextProvider, String huStatus);

	boolean isEmptyStorage(I_M_HU hu);

	void setClearanceStatusRecursively(final HuId huId, final ClearanceStatusInfo statusInfo);

	ITranslatableString getClearanceStatusCaption(ClearanceStatus clearanceStatus);

	void setClearanceStatusRecursively(@NonNull I_M_HU hu, @NonNull ClearanceStatusInfo clearanceStatusInfo);

	void setClearanceStatusRecursively(
			@NonNull Collection<I_M_HU> hus,
			@NonNull ClearanceStatusInfo clearanceStatusInfo,
			@Nullable Predicate<I_M_HU> filter);

	boolean isHUHierarchyCleared(@NonNull final HuId huId);

	ClientAndOrgId getClientAndOrgId(@NonNull final HuId huId);
}
