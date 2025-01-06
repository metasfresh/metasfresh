/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.cockpit.stock;

import de.metas.material.cockpit.model.I_MD_Stock_From_HUs_V;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Service;

import static java.math.BigDecimal.ZERO;

@Service
public class HUStockService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final StockDataUpdateRequestHandler dataUpdateRequestHandler;

	public HUStockService(@NonNull final StockDataUpdateRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	public void createAndHandleDataUpdateRequests(@NonNull final PInstanceId pinstanceId)
	{
		final ResetStockPInstanceId resetStockPInstanceId = ResetStockPInstanceId.ofPInstanceId(pinstanceId);
		final StockChangeSourceInfo info = StockChangeSourceInfo.ofResetStockPInstanceId(resetStockPInstanceId);

		queryBL
				.createQueryBuilder(I_MD_Stock_From_HUs_V.class)
				.addNotEqualsFilter(I_MD_Stock_From_HUs_V.COLUMNNAME_QtyOnHandChange, ZERO)
				.create()
				.list()
				.forEach(stockFromHUs -> {
					final StockDataUpdateRequest dataUpdateRequest = createDataUpdatedRequest(
							stockFromHUs,
							info);

					dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
				});
	}

	@NonNull
	private StockDataUpdateRequest createDataUpdatedRequest(
			@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord,
			@NonNull final StockChangeSourceInfo stockDataUpdateRequestSourceInfo)
	{
		final StockDataRecordIdentifier recordIdentifier = toStockDataRecordIdentifier(huBasedDataRecord);

		final ProductId productId = ProductId.ofRepoId(huBasedDataRecord.getM_Product_ID());
		final Quantity qtyInStorageUOM = Quantitys.of(huBasedDataRecord.getQtyOnHandChange(), UomId.ofRepoId(huBasedDataRecord.getC_UOM_ID()));
		final Quantity qtyInProductUOM = uomConversionBL.convertToProductUOM(qtyInStorageUOM, productId);

		return StockDataUpdateRequest.builder()
				.identifier(recordIdentifier)
				.onHandQtyChange(qtyInProductUOM.toBigDecimal())
				.sourceInfo(stockDataUpdateRequestSourceInfo)
				.build();
	}

	@NonNull
	private static StockDataRecordIdentifier toStockDataRecordIdentifier(@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord)
	{
		return StockDataRecordIdentifier.builder()
				.clientId(ClientId.ofRepoId(huBasedDataRecord.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(huBasedDataRecord.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoId(huBasedDataRecord.getM_Warehouse_ID()))
				.productId(ProductId.ofRepoId(huBasedDataRecord.getM_Product_ID()))
				.storageAttributesKey(AttributesKey.ofString(huBasedDataRecord.getAttributesKey()))
				.build();
	}
}
