/*
 * #%L
 * de.metas.business
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

package de.metas.material.replenish;

import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Replenish;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.ad.dao.IQueryOrderBy.Direction.Ascending;
import static org.adempiere.ad.dao.IQueryOrderBy.Nulls.First;
import static org.adempiere.ad.dao.IQueryOrderBy.Nulls.Last;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ReplenishInfoRepository
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Convenience method
	 */
	public ReplenishInfo getBy(@NonNull final MaterialDescriptor materialDescriptor)
	{
		return getBy(ReplenishInfo.Identifier.of(materialDescriptor.getWarehouseId(), materialDescriptor.getLocatorId(), ProductId.ofRepoId(materialDescriptor.getProductId())));
	}

	@NonNull
	public ReplenishInfo getBy(@NonNull final ReplenishInfo.Identifier identifier)
	{
		final ProductId productId = identifier.getProductId();
		final I_M_Replenish replenishRecord = getRecordByIdentifier(identifier).orElse(null);

		final UomId uomId = productBL.getStockUOMId(productId);

		final StockQtyAndUOMQty min;
		final StockQtyAndUOMQty max;
		final boolean highPriority;
		if (replenishRecord == null)
		{
			min = StockQtyAndUOMQtys.createZero(productId, uomId);
			max = StockQtyAndUOMQtys.createZero(productId, uomId);
			highPriority = false;
		}
		else
		{
			min = StockQtyAndUOMQtys.createConvert(replenishRecord.getLevel_Min(), productId, uomId);

			max = NumberUtils.zeroToNull(replenishRecord.getLevel_Max()) == null ? min : StockQtyAndUOMQtys.createConvert(replenishRecord.getLevel_Max(), productId, uomId);
			highPriority = replenishRecord.isHighPriority();
		}

		return ReplenishInfo.builder()
				.identifier(identifier)
				.min(min)
				.max(max)
				.highPriority(highPriority)
				.build();
	}

	public void save(@NonNull final ReplenishInfo replenishInfo)
	{
		final I_M_Replenish replenishRecord = getRecordByIdentifier(replenishInfo.getIdentifier())
				.orElseGet(() -> initNewRecord(replenishInfo.getIdentifier()));

		final BigDecimal levelMin = replenishInfo.getMin().getStockQty().toBigDecimal();
		final BigDecimal levelMax = replenishInfo.getMax().getStockQty().toBigDecimal();

		replenishRecord.setLevel_Min(levelMin);
		replenishRecord.setLevel_Max(levelMax.compareTo(levelMin) ==0 ? null : levelMax);

		saveRecord(replenishRecord);
	}

	@NonNull
	private I_M_Replenish initNewRecord(@NonNull final ReplenishInfo.Identifier identifier)
	{
		final I_M_Replenish replenishRecord = InterfaceWrapperHelper.newInstance(I_M_Replenish.class);
		replenishRecord.setM_Product_ID(identifier.getProductId().getRepoId());
		replenishRecord.setM_Warehouse_ID(identifier.getWarehouseId().getRepoId());

		return replenishRecord;
	}

	@NonNull
	private Optional<I_M_Replenish> getRecordByIdentifier(@NonNull final ReplenishInfo.Identifier identifier)
	{
		// if locator has been provided, give preference to replenish rules that are specific to that locator
		final IQueryOrderBy locatorPreferenceOrderBy = queryBL.createQueryOrderByBuilder(I_M_Replenish.class)
				.addColumn(I_M_Replenish.COLUMNNAME_M_Locator_ID, Ascending, identifier.getLocatorId() != null ? Last : First)
				.createQueryOrderBy();
		return queryBL.createQueryBuilder(I_M_Replenish.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Replenish.COLUMNNAME_M_Product_ID, identifier.getProductId())
				.addEqualsFilter(I_M_Replenish.COLUMNNAME_M_Warehouse_ID, identifier.getWarehouseId())
				.addInArrayFilter(I_M_Replenish.COLUMNNAME_M_Locator_ID, identifier.getLocatorId(), null)
				.create()
				.setOrderBy(locatorPreferenceOrderBy)
				.firstOnlyOptional(I_M_Replenish.class);
	}
}
