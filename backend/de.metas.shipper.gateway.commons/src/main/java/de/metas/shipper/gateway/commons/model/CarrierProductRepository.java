/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.model;

import de.metas.cache.CCache;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class CarrierProductRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<String, CarrierProduct> carrierProductsByExternalId = CCache.newLRUCache(I_Carrier_Product.Table_Name + "#by#M_Shipper_ID#ExternalId", 100, 0);
	private final CCache<String, CarrierProduct> carrierProductsById = CCache.newLRUCache(I_Carrier_Product.Table_Name + "#byId", 100, 0);

	private static CarrierProduct fromProductRecord(@NotNull final I_Carrier_Product product)
	{
		return CarrierProduct.builder()
				.id(CarrierProductId.ofRepoId(product.getCarrier_Product_ID()))
				.code(product.getExternalId())
				.name(product.getName())
				.build();
	}

	@NonNull
	public CarrierProduct getOrCreateCarrierProduct(@NonNull final ShipperId shipperId, @NonNull final String code, @NonNull final String name)
	{
		final CarrierProduct cachedShipperProductByCode = getCachedShipperProductByCode(shipperId, code);
		if (cachedShipperProductByCode != null)
		{
			return cachedShipperProductByCode;
		}
		return createShipperProduct(shipperId, code, name);
	}

	@Nullable
	private CarrierProduct getCachedShipperProductByCode(@NonNull final ShipperId shipperId, @Nullable final String code)
	{
		if (code == null)
		{
			return null;
		}
		return carrierProductsByExternalId.getOrLoad(shipperId + code, () ->
				queryBL.createQueryBuilder(I_Carrier_Product.class)
						.addEqualsFilter(I_Carrier_Product.COLUMNNAME_M_Shipper_ID, shipperId)
						.addEqualsFilter(I_Carrier_Product.COLUMNNAME_ExternalId, code)
						.firstOptional()
						.map(CarrierProductRepository::fromProductRecord)
						.orElse(null));
	}

	@Nullable
	public CarrierProduct getCachedShipperProductById(@Nullable final CarrierProductId productId)
	{
		if (productId == null)
		{
			return null;
		}
		return carrierProductsById.getOrLoad(productId.toString(), () ->
				queryBL.createQueryBuilder(I_Carrier_Product.class)
						.addEqualsFilter(I_Carrier_Product.COLUMNNAME_Carrier_Product_ID, productId)
						.firstOptional()
						.map(CarrierProductRepository::fromProductRecord)
						.orElse(null));
	}

	@NonNull
	private CarrierProduct createShipperProduct(@NonNull final ShipperId shipperId, @NonNull final String code, @NonNull final String name)
	{
		final I_Carrier_Product po = InterfaceWrapperHelper.newInstance(I_Carrier_Product.class);
		po.setM_Shipper_ID(shipperId.getRepoId());
		po.setExternalId(code);
		po.setName(name);
		InterfaceWrapperHelper.saveRecord(po);
		return fromProductRecord(po);
	}

}
