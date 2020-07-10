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
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_DD_NetworkDistribution;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.IPair;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface IHandlingUnitsDAO extends ISingletonService
{
	IQueryOrderBy queryOrderBy = Services.get(IQueryBL.class)
			.createQueryOrderByBuilder(I_M_HU_Item.class)
			.addColumn(I_M_HU_Item.COLUMN_M_HU_Item_ID, Direction.Ascending, Nulls.Last)
			.createQueryOrderBy();

	Map<String, Integer> ITEM_TYPE_ORDERING = ImmutableMap.of(
			X_M_HU_Item.ITEMTYPE_Material, 1,
			X_M_HU_Item.ITEMTYPE_HandlingUnit, 2,
			X_M_HU_Item.ITEMTYPE_HUAggregate, 3,
			X_M_HU_Item.ITEMTYPE_PackingMaterial, 4);

	/**
	 * Specifies that material items shall be first, followed by HU-items, HU--aggregate-items and finally packing material items.
	 * The ordering of HU-items before HU-aggregate-items is important when we deallocate from HUs, because we only want to "touch" the aggregate VHU if we need to.
	 */
	Comparator<I_M_HU_Item> HU_ITEMS_COMPARATOR = Comparator
			.<I_M_HU_Item, Integer>comparing(
					item -> ITEM_TYPE_ORDERING.get(Services.get(IHandlingUnitsBL.class).getItemType(item)))
			.thenComparing(
					queryOrderBy.getComparator(I_M_HU_Item.class));

	I_M_HU getByIdOutOfTrx(HuId huId);

	I_M_HU getById(HuId huId);

	ClientAndOrgId getClientAndOrgId(@NonNull HuId huId);

	List<I_M_HU> getByIds(Collection<HuId> huIds);

	List<I_M_HU> getByIdsOutOfTrx(Collection<HuId> huIds);

	void saveHU(I_M_HU hu);

	void saveHUItem(I_M_HU_Item huItem);

	void delete(I_M_HU hu);

	I_M_HU_PI_Item retrievePackingItemTemplatePIItem(Properties ctx);

	I_M_HU_PI getPackingInstructionById(HuPackingInstructionsId id);

	I_M_HU_PI_Item getPackingInstructionItemById(@NonNull HuPackingInstructionsItemId piItemId);

	/**
	 * Gets Virtual PI
	 *
	 * @return virtual PI; never return null
	 */
	I_M_HU_PI retrieveVirtualPI(Properties ctx);

	I_M_HU_PI_Item retrieveVirtualPIItem(Properties ctx);

	/**
	 * Create a new HU builder using the given {@code huContext}. Set the builder's {@code date} to the {@code huContext}'s date.
	 */
	IHUBuilder createHUBuilder(IHUContext huContext);

	// Handling Unit Retrieval

	/**
	 * Gets parent {@link I_M_HU}
	 *
	 * @param hu may not be {@code null}
	 * @return parent HU or null
	 */
	I_M_HU retrieveParent(final I_M_HU hu);

	/**
	 * @param hu may not be {@code null}
	 * @return parent M_HU_ID or -1
	 */
	int retrieveParentId(I_M_HU hu);

	/**
	 * Actually returns {@link I_M_HU#getM_HU_Item_Parent()}, but in a potentially DB decoupled fashion.
	 */
	I_M_HU_Item retrieveParentItem(I_M_HU hu);

	void setParentItem(I_M_HU hu, @Nullable I_M_HU_Item parentItem);

	/**
	 * Creates and saves a {@link I_M_HU_Item} for the given {@code hu}, using the given {@code piItem} as its template.
	 *
	 * @return created HU item
	 */
	I_M_HU_Item createHUItem(I_M_HU hu, I_M_HU_PI_Item piItem);

	/**
	 * Similar to {@link #createHUItem(I_M_HU, I_M_HU_PI_Item)}, but do not use any {@link I_M_HU_PI_Item} as template.<br>
	 * Instead, create new item with {@link X_M_HU_Item#ITEMTYPE_HUAggregate} as its {@link I_M_HU_Item#COLUMN_ItemType}.
	 *
	 * @param hu the HU which the new item shall reference.
	 */
	I_M_HU_Item createAggregateHUItem(I_M_HU hu);

	I_M_HU_Item createChildHUItem(I_M_HU hu);

	/**
	 * Retrieve items that reference the given {@code hu}, ordered by {@link #HU_ITEMS_COMPARATOR}.
	 */
	List<I_M_HU_Item> retrieveItems(final I_M_HU hu);

	I_M_HU_Item retrieveItem(I_M_HU hu, I_M_HU_PI_Item piItem);

	List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item);

	List<I_M_HU> retrieveIncludedHUs(@NonNull I_M_HU hu);

	// Handling Unit PI Retrieval

	List<I_M_HU_PI_Item> retrievePIItems(final I_M_HU_PI handlingUnitPI, final BPartnerId bpartnerId);

	/**
	 * Retrieve (active) {@link I_M_HU_PI_Item}s for the given parameters.
	 *
	 * @param version    mandatory. Only return items that reference this version.
	 * @param bpartnerId optional. If not {@code null}, then exclude items with {@link X_M_HU_Item#ITEMTYPE_HandlingUnit} that have a different {@link I_M_HU_PI_Item#COLUMNNAME_C_BPartner_ID}.
	 */
	List<I_M_HU_PI_Item> retrievePIItems(final I_M_HU_PI_Version version, final BPartnerId bpartnerId);

	/**
	 * Retrieve all {@link I_M_HU_PI_Item}s (active or inactive) for given M_HU_PI_Version.
	 */
	List<I_M_HU_PI_Item> retrieveAllPIItems(I_M_HU_PI_Version piVersion);

	/**
	 * @return current PI Version; never return null
	 */
	I_M_HU_PI_Version retrievePICurrentVersion(final I_M_HU_PI pi);

	HuPackingInstructionsVersionId retrievePICurrentVersionId(final I_M_HU_PI pi);

	HuPackingInstructionsVersionId retrievePICurrentVersionId(final HuPackingInstructionsId piId);

	/**
	 * @return current PI Version or null
	 */
	I_M_HU_PI_Version retrievePICurrentVersionOrNull(I_M_HU_PI pi);

	I_M_HU_PI_Version retrievePICurrentVersionOrNull(final HuPackingInstructionsId piId);

	I_M_HU_PI_Version retrievePIVersionById(final HuPackingInstructionsVersionId id);

	List<I_M_HU_PI_Item> retrievePIItemsForPackingMaterial(final I_M_HU_PackingMaterial pm);

	@Nullable
	I_M_HU_PI retrievePIDefaultForPicking();

	/**
	 * Retrieve ALL PI Versions (active, not-active, current, not-current).
	 */
	List<I_M_HU_PI_Version> retrieveAllPIVersions(I_M_HU_PI pi);

	/**
	 * @return all available (i.e. active) HU PIs from system
	 */
	List<I_M_HU_PI> retrieveAvailablePIs(Properties ctx);

	Iterator<I_M_HU> retrieveTopLevelHUsForLocator(final I_M_Locator locator);

	/**
	 * @param huUnitType optional, may be {@code null} or empty. If given, then only return items whose {@link I_M_HU_PI_Version} has the given {@link I_M_HU_PI_Version#COLUMN_HU_UnitType}.
	 * @return unique {@link I_M_HU_PI_Item}s of the selected {@link I_M_HU_PI}'s parent PI
	 */
	List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(I_M_HU_PI huPI, String huUnitType, BPartnerId bpartnerId);

	/**
	 * For the given {@code parentHU} and {@code piOfChildHU}, retrieve the PI item (with type HU) that can be used to link child and parent.
	 */
	I_M_HU_PI_Item retrieveParentPIItemForChildHUOrNull(I_M_HU parentHU, I_M_HU_PI piOfChildHU, IContextAware ctx);

	/**
	 * Retrieve first parent item if more are defined.
	 */
	I_M_HU_PI_Item retrieveDefaultParentPIItem(@NonNull I_M_HU_PI huPI, @Nullable String huUnitType, @Nullable BPartnerId bpartnerId);

	/**
	 * Retrieves the default LU.
	 *
	 * @return default LU or <code>null</code>.
	 */
	I_M_HU_PI retrieveDefaultLUOrNull(Properties ctx, int adOrgId);

	/**
	 * @return packing material or null
	 */
	I_M_HU_PackingMaterial retrievePackingMaterial(I_M_HU_PI pi, BPartnerId bpartnerId);

	/**
	 * @return packing material or null
	 */
	I_M_HU_PackingMaterial retrievePackingMaterial(I_M_HU_PI_Version piVersion, BPartnerId bpartnerId);

	/**
	 * Retrieves packing material of given HU.
	 */
	// TODO: i think we shall drop this method because is no longer valid!!!
	I_M_HU_PackingMaterial retrievePackingMaterial(final I_M_HU hu);

	I_M_HU retrieveVirtualHU(I_M_HU_Item itemMaterial);

	List<I_M_HU> retrieveVirtualHUs(I_M_HU_Item itemMaterial);

	List<I_M_HU> retrieveHUsForWarehouse(Properties ctx, WarehouseId warehouseId, String trxName);

	List<I_M_HU> retrieveHUsForWarehouses(Properties ctx, Collection<WarehouseId> warehouseIds, String trxName);

	IHUQueryBuilder createHUQueryBuilder();

	/**
	 * Retrieve the packing materials of the given {@code hu}.<br>
	 * Also takes into account the case that the given {@code hu} is an aggregate VHU (gh #460).
	 * <p>
	 * NOTE
	 * <ul>
	 * <li>this method will return packing material(s) of this HU only and not for its included HUs.</li>
	 * </ul>
	 *
	 * @return packing material and quantity pairs
	 */
	List<IPair<I_M_HU_PackingMaterial, Integer>> retrievePackingMaterialAndQtys(I_M_HU hu);

	/**
	 * The special network distribution that is defined for empties (gebinde) It contains lines that link the non-empties warehouses with the empties ones that the packing materials shall be moved to
	 * when empty
	 *
	 * @param product (NOT USED); here just in case the requirements will change later and there will be gebinde network distributions based on product
	 */
	I_DD_NetworkDistribution retrieveEmptiesDistributionNetwork(Properties ctx, I_M_Product product, String trxName);

	/**
	 * @return all available (i.e. active) HU PIs from system, for the given org_id and for org 0
	 */
	List<I_M_HU_PI> retrieveAvailablePIsForOrg(Properties ctx, int adOrgId);

	/**
	 * Create or return a <b>HU</b> item. Other item types generally exist already, or should not exist.
	 *
	 * @return a pair of the item that was created or retrieved on the left and a boolean that is {@code true} if the item was created and {@code false} if it was retrieved.
	 */
	IPair<I_M_HU_Item, Boolean> createHUItemIfNotExists(I_M_HU hu, I_M_HU_PI_Item piItem);

	/**
	 * Retrieve the aggregated item of the given HU if it has one.
	 *
	 * @return the aggregated item or null.
	 */
	I_M_HU_Item retrieveAggregatedItemOrNull(I_M_HU hu, I_M_HU_PI_Item piItem);

	/**
	 * Retrieve all the child HUs of the given item, both active and not active
	 */
	List<I_M_HU> retrieveChildHUsForItem(I_M_HU_Item parentItem);

	/**
	 * Get the warehouses of the hus' organization , excluding those which currently contain the given HUs
	 */
	List<I_M_Warehouse> retrieveWarehousesWhichContainNoneOf(List<I_M_HU> hus);

	List<I_M_HU> retrieveByIds(Collection<HuId> huIds);

	void setReservedByHUIds(final Collection<HuId> huIds, boolean reserved);

	@NonNull
	I_M_HU_PI getIncludedPI(@NonNull I_M_HU_PI_Item piItem);

	void save(@NonNull I_M_HU_PI huPi);
}
