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
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import org.compiere.model.I_M_ProductPrice;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Query VO to be used when filtering on {@link I_M_HU_PI_Item_Product}.
 *
 * @author tsa
 */
public interface IHUPIItemProductQuery
{
	int getM_HU_PI_Item_ID();

	void setM_HU_PI_Item_ID(final int huPIItemId);

	int getM_Product_ID();

	Set<ProductId> getOnlyProductIds();

	void setOnlyProductIds(Collection<ProductId> productIds);

	void setM_Product_ID(final int productId);

	default void setProductId(final ProductId productId)
	{
		setM_Product_ID(ProductId.toRepoId(productId));
	}

	int getC_BPartner_ID();

	/**
	 * Match only those {@link I_M_HU_PI_Item_Product}s which are about this partner or any bpartner.
	 */
	void setC_BPartner_ID(final int bpartnerId);

	default void setBPartnerId(@Nullable final BPartnerId bpartnerId)
	{
		setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
	}

	/**
	 * Match only those {@link I_M_HU_PI_Item_Product}s which belong to a {@link I_M_ProductPrice} of this PLV.
	 */
	void setPriceListVersionId(@Nullable final PriceListVersionId priceListVersionId);

	@Nullable
	PriceListVersionId getPriceListVersionId();

	void setDate(@Nullable ZonedDateTime date);

	ZonedDateTime getDate();

	boolean isAllowAnyProduct();

	void setAllowAnyProduct(final boolean allowAnyProduct);

	boolean isAllowInfiniteCapacity();

	void setAllowInfiniteCapacity(final boolean allowInfiniteCapacity);

	String getHU_UnitType();

	void setHU_UnitType(final String huUnitType);

	boolean isAllowVirtualPI();

	void setAllowVirtualPI(final boolean allowVirtualPI);

	boolean isOneConfigurationPerPI();

	/**
	 * If true, it will retain only one configuration (i.e. {@link I_M_HU_PI_Item_Product}) for each distinct {@link I_M_HU_PI} found.
	 */
	void setOneConfigurationPerPI(final boolean oneConfigurationPerPI);

	boolean isAllowDifferentCapacities();

	/**
	 * In case {@link #isOneConfigurationPerPI()}, if <code>allowDifferentCapacities</code> is true, it will retain one configuration for each distinct {@link I_M_HU_PI} <b>AND</b>
	 * {@link I_M_HU_PI_Item_Product#getQty()}.
	 * </ul>
	 */
	void setAllowDifferentCapacities(boolean allowDifferentCapacities);

	// 08393
	// the possibility to create the query for any partner (not just for a given one or null)

	boolean isAllowAnyPartner();

	void setAllowAnyPartner(final boolean allowAnyPartner);

	/**
	 * task: https://metasfresh.atlassian.net/browse/FRESH-386
	 */
	// @formatter:off
	void setM_Product_Packaging_ID(int packagingProductId);
	int getM_Product_Packaging_ID();
	// @formatter:on

	// @formatter:off
	void setDefaultForProduct(final boolean defaultForProduct);
	boolean isDefaultForProduct();
	// @formatter:on
}
