package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_DD_NetworkDistribution;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_HU_Status;
import de.metas.handlingunits.model.X_M_HU_Item;

public interface IHandlingUnitsDAO extends ISingletonService
{
	void saveHU(I_M_HU hu);

	void delete(I_M_HU hu);

	I_M_HU_PI retrieveNoPI(Properties ctx);

	I_M_HU_PI_Item retrieveNoPIItem(Properties ctx);

	/**
	 * Gets Virtual PI
	 *
	 * @param ctx
	 * @return virtual PI; never return null
	 */
	I_M_HU_PI retrieveVirtualPI(Properties ctx);

	I_M_HU_PI_Item retrieveVirtualPIItem(Properties ctx);

	int getNo_HU_PI_ID();

	int getNo_HU_PI_Item_ID();

	int getVirtual_HU_PI_ID();

	int getVirtual_HU_PI_Item_ID();

	/**
	 * Create a new HU builder using the given {@code huContext}. Set the builder's {@code date} to the {@code huContext}'s date.
	 * 
	 * @param huContext
	 * @return
	 */
	IHUBuilder createHUBuilder(IHUContext huContext);

	// Handling Unit Retrieval

	/**
	 * Gets parent {@link I_M_HU}
	 *
	 * @param hu
	 * @return parent HU or null
	 */
	I_M_HU retrieveParent(final I_M_HU hu);

	I_M_HU_Item retrieveParentItem(I_M_HU hu);

	void setParentItem(I_M_HU hu, I_M_HU_Item parentItem);

	/**
	 * Creates and saves a {@link I_M_HU_Item} for the given {@code hu}, using the given {@code piItem} as its template.
	 *
	 * @param hu
	 * @param piItem
	 * @return created HU item
	 */
	I_M_HU_Item createHUItem(I_M_HU hu, I_M_HU_PI_Item piItem);

	/**
	 * Similar to {@link #createHUItem(I_M_HU, I_M_HU_PI_Item)}, but does not use any {@link I_M_HU_PI_Item} as template.<br>
	 * Instead, the new item is created with {@link X_M_HU_Item#ITEMTYPE_HUAggregate} as its {@link I_M_HU_Item#COLUMN_ItemType}.
	 * 
	 * @param hu
	 * @return
	 */
	I_M_HU_Item createAggregateHUItem(I_M_HU hu);

	List<I_M_HU_Item> retrieveItems(final I_M_HU hu);

	I_M_HU_Item retrieveItem(I_M_HU hu, I_M_HU_PI_Item piItem);

	List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item);

	List<I_M_HU> retrieveIncludedHUs(I_M_HU hu);

	// Handling Unit PI Retrieval

	List<I_M_HU_PI_Item> retrievePIItems(final I_M_HU_PI handlingUnitPI, final I_C_BPartner partner);

	/**
	 * Retrieve (active) {@link I_M_HU_PI_Item}s for the given parameters.
	 * 
	 * @param version mandatory. Only return items that reference this version.
	 * @param partner optional. If not {@code null}, then exclude items with {@link X_M_HU_Item#ITEMTYPE_HandlingUnit} that have a different {@link I_M_HU_PI_Item#COLUMN_C_BPartner_ID}.
	 * @return
	 */
	List<I_M_HU_PI_Item> retrievePIItems(final I_M_HU_PI_Version version, final I_C_BPartner partner);

	/**
	 * Retrieve all {@link I_M_HU_PI_Item}s (active or inactive) for given M_HU_PI_Version.
	 *
	 * @param ctx
	 * @param huPIVersionId M_HU_PI_Version_ID
	 * @param trxName
	 * @return
	 */
	List<I_M_HU_PI_Item> retrieveAllPIItems(I_M_HU_PI_Version piVersion);

	/**
	 * @param pi
	 * @return current PI Version; never return null
	 */
	I_M_HU_PI_Version retrievePICurrentVersion(final I_M_HU_PI pi);

	/**
	 * @param pi
	 * @return current PI Version or null
	 */
	I_M_HU_PI_Version retrievePICurrentVersionOrNull(I_M_HU_PI pi);

	List<I_M_HU_PI_Item> retrievePIItemsForPackingMaterial(final I_M_HU_PackingMaterial pm);

	/**
	 * Retrieve ALL PI Versions (active, not-active, current, not-current).
	 *
	 * @param pi
	 * @return
	 */
	List<I_M_HU_PI_Version> retrieveAllPIVersions(I_M_HU_PI pi);

	/**
	 * @param ctx
	 * @return all available (i.e. active) HU PIs from system
	 */
	List<I_M_HU_PI> retrieveAvailablePIs(Properties ctx);

	Iterator<I_M_HU> retrieveTopLevelHUsForLocator(final I_M_Locator locator);

	/**
	 * @param huPI
	 * @param huUnitType
	 * @param bpartner
	 * @return unique {@link I_M_HU_PI_Item}s of the selected {@link I_M_HU_PI}'s parent PI
	 */
	List<I_M_HU_PI_Item> retrieveParentPIItemsForParentPI(I_M_HU_PI huPI, String huUnitType, I_C_BPartner bpartner);

	/**
	 * For the given {@code parentHU} and {@code piOfChildHU}, retrieve the PI item (with type HU) that can be used to link child and parent.
	 * 
	 * @param parentHU
	 * @param piOfChildHU
	 * @param ctx
	 * @return
	 */
	I_M_HU_PI_Item retrieveParentPIItemForChildHUOrNull(I_M_HU parentHU, I_M_HU_PI piOfChildHU, IContextAware ctx);

	/**
	 * Retrieve first parent item if more are defined.
	 *
	 * @param huPI
	 * @param huUnitType
	 * @param bpartner
	 * @return
	 */
	I_M_HU_PI_Item retrieveDefaultParentPIItem(I_M_HU_PI huPI, String huUnitType, I_C_BPartner bpartner);

	/**
	 * Retrieves the default LU.
	 * 
	 * @param ctx
	 * @param adOrgId
	 * @return default LU or <code>null</code>.
	 */
	I_M_HU_PI retrieveDefaultLUOrNull(Properties ctx, int adOrgId);

	/**
	 *
	 * @param pi
	 * @param bpartner
	 * @return packing material or null
	 */
	I_M_HU_PackingMaterial retrievePackingMaterial(I_M_HU_PI pi, I_C_BPartner bpartner);

	/**
	 *
	 * @param piVersion
	 * @param bpartner
	 * @return packing material or null
	 */
	I_M_HU_PackingMaterial retrievePackingMaterial(I_M_HU_PI_Version piVersion, I_C_BPartner bpartner);

	/**
	 * Retrieves packing material of given HU.
	 *
	 * @param hu
	 * @return
	 */
	// TODO: i think we shall drop this method because is no longer valid!!!
	I_M_HU_PackingMaterial retrievePackingMaterial(final I_M_HU hu);

	I_M_HU retrieveVirtualHU(I_M_HU_Item itemMaterial);

	List<I_M_HU> retrieveVirtualHUs(I_M_HU_Item itemMaterial);

	List<I_M_HU> retrieveHUsForWarehouse(Properties ctx, int warehouseId, String trxName);

	List<I_M_HU> retrieveHUsForWarehouses(Properties ctx, Collection<Integer> warehouseIds, String trxName);

	List<I_M_HU> retrieveHUsForWarehousesAndProductId(Properties ctx, Collection<Integer> warehouseIds, int productId, String trxName);

	IHUQueryBuilder createHUQueryBuilder();

	/**
	 * Retrieve the packing materials of a given HU.
	 *
	 * NOTE
	 * <ul>
	 * <li>this method will return packing material(s) of this HU only and not for it's included HUs.</li>
	 * </ul>
	 *
	 * @param hu
	 * @return packing material and quantity pairs
	 */
	List<IPair<I_M_HU_PackingMaterial, Integer>> retrievePackingMaterialAndQtys(I_M_HU hu);

	/**
	 * The special network distribution that is defined for empties (gebinde) It contains lines that link the non-empties warehouses with the empties ones that the packing materials shall be moved to
	 * when empty
	 *
	 * @param ctx
	 *
	 * @param product (NOT USED); here just in case the requirements will change later and there will be gebinde network distributions based on product
	 *
	 * @param trxName
	 * @return
	 */
	I_DD_NetworkDistribution retrieveEmptiesDistributionNetwork(Properties ctx, I_M_Product product, String trxName);

	/**
	 *
	 * Retrieve the HU Status entry that fits the value given as parameter
	 *
	 * @param ctx
	 * @param huStatusValue
	 * @return {@link I_M_HU_Status} or <code>null</code>.
	 */
	I_M_HU_Status retrieveHUStatus(Properties ctx, String huStatusValue);

	/**
	 * @param ctx
	 * @param adOrgId
	 * @return all available (i.e. active) HU PIs from system, for the given org_id and for org 0
	 */
	List<I_M_HU_PI> retrieveAvailablePIsForOrg(Properties ctx, int adOrgId);

	/**
	 * 
	 * @param hu
	 * @param piItem
	 * @return a pair of the item that was created or retrieved on the left and a boolean that is {@code true} if the item was created and {@code false} if it was retrieved.
	 */
	IPair<I_M_HU_Item, Boolean> createHUItemIfNotExists(I_M_HU hu, I_M_HU_PI_Item piItem);
}
