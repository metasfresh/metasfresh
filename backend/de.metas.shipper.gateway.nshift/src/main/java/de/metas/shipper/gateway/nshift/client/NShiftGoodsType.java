/*
 * #%L
 * de.metas.shipper.gateway.nshift
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

package de.metas.shipper.gateway.nshift.client;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder
public class NShiftGoodsType
{
	// FIXME: This is a temporary, hardcoded solution. random selection from configured https://demo.shipmentserver.com:8080/ShipServer/{actorId}/Products
	// https://demo.shipmentserver.com:8080/ShipServer/{actorId}/Products
	private static final ImmutableMap<Integer, NShiftGoodsType> GOODS_TYPES_BY_PROD_CONCEPT_ID = ImmutableMap.<Integer, NShiftGoodsType>builder()
			.put(2757, NShiftGoodsType.builder()
					.goodsTypeID(5)
					.goodsTypeKey1("Packet")
					.goodsTypeKey2("")
					.goodsTypeName("Packet")
					.build()) // DHL - domestic
			.put(2758, NShiftGoodsType.builder()
					.goodsTypeID(5)
					.goodsTypeKey1("Packet")
					.goodsTypeKey2("")
					.goodsTypeName("Packet")
					.build()) // DHL - euroconnect
			.put(3399, NShiftGoodsType.builder()
					.goodsTypeID(39)
					.goodsTypeKey1("PA")
					.goodsTypeKey2("")
					.goodsTypeName("Paket")
					.build()) // NightStarExpress
			.build();

	@Nullable
	private static NShiftGoodsType findByProdConceptId(final int prodConceptId)
	{
		return GOODS_TYPES_BY_PROD_CONCEPT_ID.get(prodConceptId);
	}

	@NonNull
	public static NShiftGoodsType getByProdConceptId(final int prodConceptId)
	{
		final NShiftGoodsType goodsType = findByProdConceptId(prodConceptId);
		if (goodsType == null)
		{
			throw new AdempiereException("No NShiftGoodsType found for prodConceptId=" + prodConceptId)
					.setParameter("prodConceptId", prodConceptId)
					.setParameter("availableProdConceptIds", GOODS_TYPES_BY_PROD_CONCEPT_ID.keySet());
		}
		return goodsType;
	}

	@NonNull Integer goodsTypeID;
	@NonNull String goodsTypeKey1;
	@NonNull String goodsTypeKey2;
	@NonNull String goodsTypeName;
}