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

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * This DAO's methods all use a standard ordering which is relevant if a list of items is returned, or (even more relevant) if only the first one out of many matching records is returned. This
 * standard ordering for I_M_HU_PI_Item_Product's is as follows:
 * <ul>
 * <li><code>AD_Client_ID DESC NULLS LAST</code>, i.e. not-system records are preferred</li>
 * <li><code>C_BPartner_ID DESC NULLS LAST</code>, i.e. records with a partner are preferred</li>
 * <li><code>IsAllowAnyProduct DESC</code>, i.e. records which allow any product are preferred</li>
 * <li><code>M_Product_ID DESC, NULLS LAST</code>, i.e. records with a product are preferred</li>
 * <li><code>ValidFrom DESC NULLS LAST</code>, i.e. move recent records are preferred</li>
 * </ul>
 */
public interface IHUPIItemProductDAO extends ISingletonService
{
	HUPIItemProduct getById(@NonNull HUPIItemProductId id);

	@NonNull I_M_HU_PI_Item_Product getRecordById(@NonNull HUPIItemProductId id);

	IHUPIItemProductQuery createHUPIItemProductQuery();

	List<I_M_HU_PI_Item_Product> retrievePIMaterialItemProducts(I_M_HU_PI_Item itemDef);

	I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(I_M_HU_PI_Item itemDef, I_M_Product product, ZonedDateTime date);

	/**
	 * Retrieves a I_M_HU_PI_Item_Product for the given <code>huItem</code>, <code>product</code> and <code>date</code>. If there are multiple records for the given parameters, they are ordered using
	 * this DAO's standard ordering (see class-javadoc) and the first one is returned.
	 * <p>
	 * Note that the {@code C_BPArtner_ID} is taken from the given {@code huItem}'s {@link I_M_HU}.
	 */
	@Nullable
	I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(I_M_HU_Item huItem, ProductId productId, ZonedDateTime date);

	/**
	 * Retrieves a I_M_HU_PI_Item_Product for the given <code>huItem</code>, <code>product</code> and <code>date</code>. If there are multiple records for the given parameters, they are ordered using
	 * this DAO's standard ordering (see class-javadoc) and the first one is returned. Attempt to match partner if available.
	 */
	@Nullable
	I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(@NonNull I_M_HU_PI_Item itemDef, @Nullable BPartnerId partnerId, @NonNull ProductId productId, @Nullable ZonedDateTime date);

	I_M_HU_PI_Item_Product retrieveVirtualPIMaterialItemProduct(Properties ctx);

	/**
	 * Retrieve material item product based on product and partner. Also, specify if infinite capacity is allowed or not. Generally, infinite capacities are OK only in orders, but not in material
	 * receipts etc.
	 *
	 * @param productId
	 * @param bpartner
	 * @param date                  date on which the item shall be valid
	 * @param huUnitType            (TU or LU)
	 * @param allowInfiniteCapacity if false, then the retrieved product is guaranteed to have <code>IsInfiniteCapacity</code> being <code>false</code>.
	 * @return
	 */
	I_M_HU_PI_Item_Product retrieveMaterialItemProduct(ProductId productId, BPartnerId bpartner, ZonedDateTime date, String huUnitType, boolean allowInfiniteCapacity);

	/**
	 * Similar to {@link #retrieveMaterialItemProduct(ProductId, BPartnerId, ZonedDateTime, String, boolean)}, but with the additional condition that the PIIP also has the given <code>packagingProduct</code>.<br>
	 * Currently, this is useful if a counter order line and a counter packaging line was created, and now the counter order line's PIIP needs to be updated to the one that matches both the order line and packaging line.
	 *
	 * @param productId
	 * @param bpartnerId
	 * @param date
	 * @param huUnitType
	 * @param allowInfiniteCapacity
	 * @param packagingProductId    optional, may be <code>null</code>. <br>
	 *                              If <code>null</code> then this method behaves like {@link #retrieveMaterialItemProduct(ProductId, BPartnerId, ZonedDateTime, String, boolean)}.
	 * @return
	 * @task https://metasfresh.atlassian.net/browse/FRESH-386
	 */
	I_M_HU_PI_Item_Product retrieveMaterialItemProduct(ProductId productId, BPartnerId bpartnerId, ZonedDateTime date, String huUnitType, boolean allowInfiniteCapacity, @Nullable ProductId packagingProductId);

	List<I_M_HU_PI_Item_Product> retrieveHUItemProducts(Properties ctx, IHUPIItemProductQuery queryVO, String trxName);

	/**
	 * @param ctx
	 * @param itemProducts
	 * @param queryVO
	 * @return true if any of <code>itemProducts</code> list is matching <code>queryVO</code>
	 */
	boolean matches(Properties ctx, Collection<I_M_HU_PI_Item_Product> itemProducts, IHUPIItemProductQuery queryVO);

	/**
	 * Check if given <code>queryVO</code> returns any result from database
	 *
	 * @param ctx
	 * @param queryVO
	 * @param trxName
	 * @return true if at least one {@link I_M_HU_PI_Item_Product} was found and matches our query
	 */
	boolean matches(Properties ctx, IHUPIItemProductQuery queryVO, String trxName);

	/**
	 * Retrieve all the M_HU_PI_Item_Product entries (active and inactive) for a certain product.
	 *
	 * @param product
	 * @return
	 */
	List<I_M_HU_PI_Item_Product> retrieveAllForProduct(I_M_Product product);

	/**
	 * Invoke {@link #retrieveTUs(Properties, ProductId, BPartnerId, boolean)} with {@code allowInfiniteCapacity = false}.
	 */
	List<I_M_HU_PI_Item_Product> retrieveTUs(Properties ctx, ProductId cuProductId, BPartnerId bpartnerId);

	/**
	 * Retrieve available {@link I_M_HU_PI_Item_Product}s for TUs which are matching our product and bpartner.
	 * <p>
	 * NOTE: the default bpartner's TU, if any, will be returned first.
	 */
	List<I_M_HU_PI_Item_Product> retrieveTUs(Properties ctx, ProductId cuProductId, BPartnerId bpartnerId, boolean allowInfiniteCapacity);

	Optional<I_M_HU_PI_Item_Product> retrieveDefaultForProduct(ProductId productId, BPartnerId bpartnerId, ZonedDateTime date);

	Optional<HUPIItemProductId> retrieveDefaultIdForProduct(ProductId productId, BPartnerId bpartnerId, ZonedDateTime date);
}
