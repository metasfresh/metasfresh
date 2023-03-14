/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.cockpit.view.mainrecord;

import com.google.common.annotations.VisibleForTesting;
import de.metas.Profiles;
import de.metas.common.util.CoalesceUtil;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static de.metas.util.NumberUtils.stripTrailingDecimalZeros;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
			saveRecord(dataRecord);
		}
	}

	public void handleStockUpdateRequest(@NonNull final UpdateMainStockDataRequest updateMainStockDataRequest)
	{
		synchronized (MainDataRequestHandler.class)
		{
			final I_MD_Cockpit dataRecord = retrieveOrCreateDataRecord(updateMainStockDataRequest.getIdentifier());

			dataRecord.setMDCandidateQtyStock(updateMainStockDataRequest.getQtyStockCurrent());
			saveRecord(dataRecord);
		}
	}

	@VisibleForTesting
	static I_MD_Cockpit retrieveOrCreateDataRecord(@NonNull final MainDataRecordIdentifier identifier)
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
		newDataRecord.setM_Warehouse_ID(NumberUtils.asInteger(identifier.getWarehouseId(), -1));

		return newDataRecord;
	}

	private static void updateDataRecordWithRequestQtys(
			final I_MD_Cockpit dataRecord,
			final UpdateMainDataRequest dataUpdateRequest)
	{
		// was QtyMaterialentnahme
		dataRecord.setQtyMaterialentnahme(
				stripTrailingDecimalZeros(dataRecord.getQtyMaterialentnahme().add(dataUpdateRequest.getDirectMovementQty())));

		// was PMM_QtyPromised_OnDate
		dataRecord.setPMM_QtyPromised_OnDate(stripTrailingDecimalZeros(
				dataRecord.getPMM_QtyPromised_OnDate().add(dataUpdateRequest.getOfferedQty())));

		// this column was not in the old data model
		dataRecord.setQtyStockChange(stripTrailingDecimalZeros(
				dataRecord.getQtyStockChange().add(dataUpdateRequest.getOnHandQtyChange())));

		// was QtyOrdered_OnDate => sum of RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V.QtyReserved_Purchase => ol.QtyReserved of purchaseOrders
		dataRecord.setQtySupply_PurchaseOrder(computeSum(dataRecord.getQtySupply_PurchaseOrder(), dataUpdateRequest.getQtySupplyPurchaseOrder()));
		// was QtyReserved_OnDate, was QtyReserved_Sale
		dataRecord.setQtyDemand_SalesOrder(computeSum(dataRecord.getQtyDemand_SalesOrder(), dataUpdateRequest.getQtyDemandSalesOrder()));

		dataRecord.setQtySupply_DD_Order(computeSum(dataRecord.getQtySupply_DD_Order(), dataUpdateRequest.getQtySupplyDDOrder()));
		dataRecord.setQtyDemand_DD_Order(computeSum(dataRecord.getQtyDemand_DD_Order(), dataUpdateRequest.getQtyDemandDDOrder()));

		dataRecord.setQtySupply_PP_Order(computeSum(dataRecord.getQtySupply_PP_Order(), dataUpdateRequest.getQtySupplyPPOrder()));
		// was Fresh_QtyMRP, was QtyRequiredForProduction
		dataRecord.setQtyDemand_PP_Order(computeSum(dataRecord.getQtyDemand_PP_Order(), dataUpdateRequest.getQtyDemandPPOrder()));

		dataRecord.setQtySupplyRequired(CoalesceUtil.firstPositiveOrZero(computeSum(dataRecord.getQtySupplyRequired(), dataUpdateRequest.getQtySupplyRequired())));

		updateQtyStockEstimateColumns(dataRecord, dataUpdateRequest);

		dataRecord.setQtyInventoryCount(computeSum(dataRecord.getQtyInventoryCount(), dataUpdateRequest.getQtyInventoryCount()));
		dataRecord.setQtyInventoryTime(TimeUtil.asTimestamp(TimeUtil.max(TimeUtil.asInstant(dataRecord.getDateGeneral()),
																		 dataUpdateRequest.getQtyInventoryTime())));

		dataRecord.setQtySupplySum(computeQtySupply_Sum(dataRecord));
		dataRecord.setQtyDemandSum(computeQtyDemand_Sum(dataRecord));
	}

	private static void updateQtyStockEstimateColumns(
			@NonNull final I_MD_Cockpit dataRecord, 
			@NonNull final UpdateMainDataRequest dataUpdateRequest)
	{
		if (dataUpdateRequest.getQtyStockEstimateCount() != null)
		{
			dataRecord.setQtyStockEstimateTime(TimeUtil.asTimestamp(dataUpdateRequest.getQtyStockEstimateTime()));
		}
		else
		{
			dataRecord.setQtyStockEstimateTime(null);
		}
		dataRecord.setQtyStockEstimateCount(CoalesceUtil.coalesceNotNull(dataUpdateRequest.getQtyStockEstimateCount(), BigDecimal.ZERO));

		final Integer qtyStockEstimateSeqNo = dataUpdateRequest.getQtyStockEstimateSeqNo();
		if (qtyStockEstimateSeqNo == null || qtyStockEstimateSeqNo == 0)
		{
			dataRecord.setQtyStockEstimateSeqNo(99999);
		}
		else
		{
			dataRecord.setQtyStockEstimateSeqNo(qtyStockEstimateSeqNo);
		}
	}

	/**
	 * Computes sum of all pending demand quantities
	 *
	 * @param dataRecord I_MD_Cockpit
	 * @return dataRecord.QtyDemand_SalesOrder + dataRecord.QtyDemand_PP_Order + dataRecord.QtyDemand_DD_Order
	 */
	@NonNull
	private static BigDecimal computeQtyDemand_Sum(@NonNull final I_MD_Cockpit dataRecord)
	{
		return dataRecord.getQtyDemand_SalesOrder()
				.add(dataRecord.getQtyDemand_PP_Order())
				.add(dataRecord.getQtyDemand_DD_Order());
	}

	/**
	 * Computes sum of all pending supply quantities
	 *
	 * @param dataRecord I_MD_Cockpit
	 * @return dataRecord.QtySupply_PP_Order + dataRecord.QtySupply_PurchaseOrder + dataRecord.QtySupply_DD_Order
	 */
	@NonNull
	private static BigDecimal computeQtySupply_Sum(@NonNull final I_MD_Cockpit dataRecord)
	{
		return dataRecord.getQtySupply_PurchaseOrder()
				.add(dataRecord.getQtySupply_PP_Order())
				.add(dataRecord.getQtySupply_DD_Order());
	}

	/**
	 * The quantity required according to material disposition that is not yet addressed by purchase order, production-receipt or distribution order.
	 *
	 * @param dataRecord I_MD_Cockpit
	 * @return dataRecord.QtySupplyRequired - dataRecord.QtySupplySum
	 */
	public static BigDecimal computeQtySupplyToSchedule(@NonNull final I_MD_Cockpit dataRecord)
	{
		return CoalesceUtil.firstPositiveOrZero(
				dataRecord.getQtySupplyRequired().subtract(dataRecord.getQtySupplySum()));
	}

	/**
	 * Current pending supplies minus pending demands
	 *
	 * @param dataRecord I_MD_Cockpit
	 * @return dataRecord.QtyStockCurrent + dataRecord.QtySupplySum - dataRecord.QtyDemandSum
	 */
	public static BigDecimal computeQtyExpectedSurplus(@NonNull final I_MD_Cockpit dataRecord)
	{
		return dataRecord.getQtySupplySum().subtract(dataRecord.getQtyDemandSum());
	}

	@NonNull
	private static BigDecimal computeSum(@NonNull final BigDecimal... args)
	{
		final BigDecimal sum = Stream.of(args)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);

		return stripTrailingDecimalZeros(sum);
	}
}
