package de.metas.material.cockpit.view.mainrecord;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.util.NumberUtils.stripTrailingDecimalZeros;

import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.metas.Profiles;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
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
@Profile(Profiles.PROFILE_App) // the event handler is also just on this profile
public class MainDataRequestHandler
{
	public void handleDataUpdateRequest(@NonNull final UpdateMainDataRequest dataUpdateRequest)
	{
		synchronized (MainDataRequestHandler.class)
		{
			final I_MD_Cockpit dataRecord = retrieveOrCreateDataRecord(dataUpdateRequest.getIdentifier());
			updateDataRecordWithRequestQtys(dataRecord, dataUpdateRequest);
			save(dataRecord);
		}
	}

	private static I_MD_Cockpit retrieveOrCreateDataRecord(@NonNull final MainDataRecordIdentifier identifier)
	{
		final IQuery<I_MD_Cockpit> query = identifier.createQueryBuilder().create();

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

	private static void updateDataRecordWithRequestQtys(
			final I_MD_Cockpit dataRecord,
			final UpdateMainDataRequest dataUpdateRequest)
	{
		// was QtyMaterialentnahme
		dataRecord.setQtyMaterialentnahme(
				stripTrailingDecimalZeros(dataRecord.getQtyMaterialentnahme().add(dataUpdateRequest.getDirectMovementQty())));

		// this column was not in the old data model
		dataRecord.setQtyOnHandCount(stripTrailingDecimalZeros(
				dataRecord.getQtyOnHandCount().add(dataUpdateRequest.getCountedQty())));

		// was PMM_QtyPromised_OnDate
		dataRecord.setPMM_QtyPromised_OnDate(stripTrailingDecimalZeros(
				dataRecord.getPMM_QtyPromised_OnDate().add(dataUpdateRequest.getOfferedQty())));

		// this column was not in the old data model
		dataRecord.setQtyStockChange(stripTrailingDecimalZeros(
				dataRecord.getQtyStockChange().add(dataUpdateRequest.getOnHandQtyChange())));

		// was QtyOrdered_OnDate => sum of RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V.QtyReserved_Purchase => ol.QtyReserved of purchaseOrders
		dataRecord.setQtyReserved_Purchase(stripTrailingDecimalZeros(
				dataRecord.getQtyReserved_Purchase().add(dataUpdateRequest.getReservedPurchaseQty())));

		// was QtyReserved_OnDate
		dataRecord.setQtyReserved_Sale(stripTrailingDecimalZeros(
				dataRecord.getQtyReserved_Sale().add(dataUpdateRequest.getReservedSalesQty())));

		// was Fresh_QtyMRP
		dataRecord.setQtyRequiredForProduction(stripTrailingDecimalZeros(
				dataRecord.getQtyRequiredForProduction().add(dataUpdateRequest.getRequiredForProductionQty())));

		// was Fresh_QtyOnHand_OnDate
		dataRecord.setQtyOnHandEstimate(
				stripTrailingDecimalZeros(
						dataRecord.getQtyOnHandCount()
								.add(dataRecord.getQtyStockChange())
								.subtract(dataRecord.getQtyMaterialentnahme())));

		// was Fresh_QtyPromised
		dataRecord.setQtyAvailableToPromise(stripTrailingDecimalZeros(
				dataRecord.getQtyOnHandEstimate()
						.add(dataRecord.getQtyReserved_Purchase())
						.subtract(dataRecord.getQtyReserved_Sale())));
	}
}
