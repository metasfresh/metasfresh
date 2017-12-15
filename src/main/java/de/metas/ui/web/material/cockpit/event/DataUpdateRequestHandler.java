package de.metas.ui.web.material.cockpit.event;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.model.I_MD_Cockpit;
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
public class DataUpdateRequestHandler
{
	public void handleDataUpdateRequest(@NonNull final DataUpdateRequest dataUpdateRequest)
	{
		final I_MD_Cockpit dataRecord = retrieveOrCreateDataRecord(dataUpdateRequest.getIdentifier());

		updateDataRecordWithRequestQtys(dataRecord, dataUpdateRequest);

		save(dataRecord);
	}

	private I_MD_Cockpit retrieveOrCreateDataRecord(@NonNull final DataRecordIdentifier identifier)
	{
		final IQuery<I_MD_Cockpit> query = createQueryForIdentifier(identifier);

		final I_MD_Cockpit existingDataRecord = query.firstOnly(I_MD_Cockpit.class);
		if (existingDataRecord != null)
		{
			return existingDataRecord;
		}

		final I_MD_Cockpit newDataRecord = newInstance(I_MD_Cockpit.class);
		newDataRecord.setM_Product_ID(identifier.getProductDescriptor().getProductId());
		newDataRecord.setAttributesKey(identifier.getProductDescriptor().getStorageAttributesKey().getAsString());
		newDataRecord.setDateGeneral(TimeUtil.asTimestamp(identifier.getDate()));
		newDataRecord.setPP_Plant_ID(identifier.getPlantId());

		return newDataRecord;
	}

	private IQuery<I_MD_Cockpit> createQueryForIdentifier(@NonNull final DataRecordIdentifier identifier)
	{
		final ProductDescriptor productDescriptor = identifier.getProductDescriptor();

		final AttributesKey attributesKey = productDescriptor.getStorageAttributesKey();
		attributesKey.assertNotAllOrOther();

		final IQueryBuilder<I_MD_Cockpit> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Cockpit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Cockpit.COLUMN_M_Product_ID, productDescriptor.getProductId())
				.addEqualsFilter(I_MD_Cockpit.COLUMN_AttributesKey, attributesKey.getAsString())
				.addEqualsFilter(I_MD_Cockpit.COLUMN_DateGeneral, identifier.getDate());

		if (identifier.getPlantId() > 0)
		{
			queryBuilder.addEqualsFilter(I_MD_Cockpit.COLUMN_PP_Plant_ID, identifier.getPlantId());
		}
		else
		{
			queryBuilder.addEqualsFilter(I_MD_Cockpit.COLUMN_PP_Plant_ID, null);
		}

		return queryBuilder.create();
	}

	private void updateDataRecordWithRequestQtys(
			final I_MD_Cockpit dataRecord,
			final DataUpdateRequest dataUpdateRequest)
	{
		// was QtyMaterialentnahme
		dataRecord.setQtyMaterialentnahme(dataRecord.getQtyMaterialentnahme().add(dataUpdateRequest.getDirectMovementQty()));

		// this column was not in the old data model
		dataRecord.setQtyOnHandCount(dataRecord.getQtyOnHandCount().add(dataUpdateRequest.getCountedQty()));

		// was PMM_QtyPromised_OnDate
		dataRecord.setPMM_QtyPromised_OnDate(dataRecord.getPMM_QtyPromised_OnDate().add(dataUpdateRequest.getOfferedQty()));

		// this column was not in the old data model
		dataRecord.setQtyStockChange(dataRecord.getQtyStockChange().add(dataUpdateRequest.getOnHandQtyChange()));

		// was QtyOrdered_OnDate => sum of RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V.QtyReserved_Purchase => ol.QtyReserved of purchaseOrders
		dataRecord.setQtyReserved_Purchase(dataRecord.getQtyReserved_Purchase().add(dataUpdateRequest.getReservedPurchaseQty()));

		// was QtyReserved_OnDate
		dataRecord.setQtyReserved_Sale(dataRecord.getQtyReserved_Sale().add(dataUpdateRequest.getReservedSalesQty()));

		// was Fresh_QtyMRP
		dataRecord.setQtyRequiredForProduction(dataRecord.getQtyRequiredForProduction().add(dataUpdateRequest.getRequiredForProductionQty()));

		// was Fresh_QtyOnHand_OnDate
		dataRecord.setQtyOnHandEstimate(
				dataRecord.getQtyOnHandCount()
						.add(dataRecord.getQtyStockChange())
						.subtract(dataRecord.getQtyMaterialentnahme()));

		// was Fresh_QtyPromised
		dataRecord.setQtyAvailableToPromise(
				dataRecord.getQtyOnHandEstimate()
						.add(dataRecord.getQtyReserved_Purchase())
						.subtract(dataRecord.getQtyReserved_Sale()));

	}
}
