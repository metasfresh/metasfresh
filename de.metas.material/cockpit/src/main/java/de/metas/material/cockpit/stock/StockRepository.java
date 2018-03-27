package de.metas.material.cockpit.stock;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Service;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class StockRepository
{
	public void handleDataUpdateRequest(@NonNull final StockDataUpdateRequest dataUpdateRequest)
	{
		final I_MD_Stock dataRecord = retrieveOrCreateDataRecord(dataUpdateRequest.getIdentifier());

		updateDataRecordWithRequestQtys(dataRecord, dataUpdateRequest);
		save(dataRecord);
		
		// TODO: fire stock changed event (on after commit!)
	}

	private I_MD_Stock retrieveOrCreateDataRecord(@NonNull final StockDataRecordIdentifier identifier)
	{
		final IQuery<I_MD_Stock> query = createQueryForIdentifier(identifier);

		final I_MD_Stock existingDataRecord = query.firstOnly(I_MD_Stock.class);
		if (existingDataRecord != null)
		{
			return existingDataRecord;
		}

		final I_MD_Stock newDataRecord = newInstance(I_MD_Stock.class);
		newDataRecord.setM_Product_ID(identifier.getProductDescriptor().getProductId());
		newDataRecord.setAttributesKey(identifier.getProductDescriptor().getStorageAttributesKey().getAsString());
		newDataRecord.setM_Warehouse_ID(identifier.getWarehouseId());

		return newDataRecord;
	}

	private IQuery<I_MD_Stock> createQueryForIdentifier(@NonNull final StockDataRecordIdentifier identifier)
	{
		final ProductDescriptor productDescriptor = identifier.getProductDescriptor();

		final AttributesKey attributesKey = productDescriptor.getStorageAttributesKey();
		attributesKey.assertNotAllOrOther();

		final IQueryBuilder<I_MD_Stock> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Stock.COLUMN_M_Product_ID, productDescriptor.getProductId())
				.addEqualsFilter(I_MD_Stock.COLUMN_AttributesKey, attributesKey.getAsString())
				.addEqualsFilter(I_MD_Stock.COLUMN_M_Warehouse_ID, identifier.getWarehouseId());

		return queryBuilder.create();
	}

	private void updateDataRecordWithRequestQtys(
			final I_MD_Stock dataRecord,
			final StockDataUpdateRequest dataUpdateRequest)
	{
		final BigDecimal newQtyOnHand = dataRecord.getQtyOnHand().add(dataUpdateRequest.getOnHandQtyChange());
		dataRecord.setQtyOnHand(NumberUtils.stripTrailingDecimalZeros(newQtyOnHand));
	}
}
