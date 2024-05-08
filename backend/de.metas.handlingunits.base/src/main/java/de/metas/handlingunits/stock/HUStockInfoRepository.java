package de.metas.handlingunits.stock;

import java.util.stream.Stream;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Stock_Detail_V;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.stock.HUStockInfoQuery.HUStockInfoSingleQuery;
import de.metas.handlingunits.stock.HUStockInfoQuery.HUStockInfoSingleQuery.AttributeValue;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class HUStockInfoRepository
{
	public Stream<HUStockInfo> getByQuery(@NonNull final HUStockInfoQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_HU_Stock_Detail_V> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Stock_Detail_V.class)
				.setJoinOr()
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions, true);

		for (final HUStockInfoSingleQuery singleQuery : query.getSingleQueries())
		{
			final ICompositeQueryFilter<I_M_HU_Stock_Detail_V> filter = queryBL.createCompositeQueryFilter(I_M_HU_Stock_Detail_V.class)
					.addOnlyActiveRecordsFilter();

			if (singleQuery.getProductId() != null)
			{
				filter.addEqualsFilter(I_M_HU_Stock_Detail_V.COLUMNNAME_M_Product_ID, singleQuery.getProductId());
			}

			if (singleQuery.getAttributeId() != null)
			{
				filter.addEqualsFilter(I_M_HU_Stock_Detail_V.COLUMNNAME_M_Attribute_ID, singleQuery.getAttributeId());
			}

			if (AttributeValue.DONT_FILTER.equals(singleQuery.getAttributeValue()))
			{
				// nothing
			}
			else if (AttributeValue.NOT_EMPTY.equals(singleQuery.getAttributeValue()))
			{
				filter.addNotNull(I_M_HU_Stock_Detail_V.COLUMN_AttributeValue);
			}

			queryBuilder.filter(filter);
		}

		return queryBuilder
				.create()
				.iterateAndStream()
				.map(this::ofRecord);
	}

	private HUStockInfo ofRecord(@NonNull final I_M_HU_Stock_Detail_V record)
	{
		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
		final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

		final ITranslatableString huStatus = adReferenceDAO.retrieveListNameTranslatableString(X_M_HU.HUSTATUS_AD_Reference_ID, record.getHUStatus());

		return HUStockInfo.builder()
				.attributeId(AttributeId.ofRepoIdOrNull(record.getM_Attribute_ID()))
				.attributeValue(record.getAttributeValue())
				.bPartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.huAttributeRepoId(record.getM_HU_Attribute_ID())
				.huId(HuId.ofRepoId(record.getM_HU_ID()))
				.huStatus(huStatus)
				.huStorageRepoId(record.getM_HU_Storage_ID())
				.locatorId(extractLocatorId(record))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qty(Quantity.of(record.getQty(), uomsRepo.getById(record.getC_UOM_ID())))
				.build();

	}

	private LocatorId extractLocatorId(final I_M_HU_Stock_Detail_V record)
	{
		final int locatorRepoId = record.getM_Locator_ID();
		return Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(locatorRepoId);
	}

}
