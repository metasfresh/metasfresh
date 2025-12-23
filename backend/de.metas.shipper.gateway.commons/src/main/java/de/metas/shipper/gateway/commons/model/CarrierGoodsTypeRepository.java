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
import de.metas.inoutcandidate.CarrierGoodsType;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Carrier_Goods_Type;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class CarrierGoodsTypeRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	final CCache<String, CarrierGoodsType> carrierGoodsTypesByExternalId = CCache.newLRUCache(I_Carrier_Goods_Type.Table_Name + "#by#M_Shipper_ID#ExternalId", 100, 0);
	final CCache<String, CarrierGoodsType> carrierGoodsTypesById = CCache.newLRUCache(I_Carrier_Goods_Type.Table_Name + "#byId", 100, 0);

	@NonNull
	public CarrierGoodsType getOrCreateGoodsType(@NonNull final ShipperId shipperId, @NonNull final String externalId, @NonNull final String name)
	{
		final CarrierGoodsType cachedGoodsTypeByExternalId = getCachedGoodsTypeByShipperExternalId(shipperId, externalId);
		if (cachedGoodsTypeByExternalId != null)
		{
			return cachedGoodsTypeByExternalId;
		}
		return createGoodsType(shipperId, externalId, name);
	}

	private CarrierGoodsType createGoodsType(final @NonNull ShipperId shipperId, @NonNull final String externalId, @NonNull final String name)
	{
		final I_Carrier_Goods_Type po = InterfaceWrapperHelper.newInstance(I_Carrier_Goods_Type.class);

		po.setM_Shipper_ID(shipperId.getRepoId());
		po.setExternalId(externalId);
		po.setName(name);

		InterfaceWrapperHelper.saveRecord(po);

		return fromRecord(po);
	}

	@Nullable
	private CarrierGoodsType getCachedGoodsTypeByShipperExternalId(@NonNull final ShipperId shipperId, @Nullable final String externalId)
	{
		if (externalId == null)
		{
			return null;
		}
		return carrierGoodsTypesByExternalId.getOrLoad(shipperId + externalId, () ->
				queryBL.createQueryBuilder(I_Carrier_Goods_Type.class)
						.addEqualsFilter(I_Carrier_Goods_Type.COLUMNNAME_M_Shipper_ID, shipperId)
						.addEqualsFilter(I_Carrier_Goods_Type.COLUMNNAME_ExternalId, externalId)
						.firstOptional()
						.map(CarrierGoodsTypeRepository::fromRecord)
						.orElse(null));
	}

	@Nullable
	public CarrierGoodsType getCachedGoodsTypeById(@Nullable final CarrierGoodsTypeId goodsTypeId)
	{
		if (goodsTypeId == null)
		{
			return null;
		}
		return carrierGoodsTypesById.getOrLoad(goodsTypeId.toString(), () ->
				queryBL.createQueryBuilder(I_Carrier_Goods_Type.class)
						.addEqualsFilter(I_Carrier_Goods_Type.COLUMNNAME_Carrier_Goods_Type_ID, goodsTypeId)
						.firstOptional()
						.map(CarrierGoodsTypeRepository::fromRecord)
						.orElse(null));
	}

	private static CarrierGoodsType fromRecord(@NotNull final I_Carrier_Goods_Type goodsType)
	{
		return CarrierGoodsType.builder()
				.id(CarrierGoodsTypeId.ofRepoId(goodsType.getCarrier_Goods_Type_ID()))
				.externalId(goodsType.getExternalId())
				.name(goodsType.getName())
				.build();
	}

}
