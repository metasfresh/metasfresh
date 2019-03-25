package de.metas.material.cockpit.stock.process;

import static java.math.BigDecimal.ZERO;

import lombok.NonNull;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.model.I_MD_Stock_From_HUs_V;
import de.metas.material.cockpit.stock.StockChangeSourceInfo;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockDataUpdateRequest;
import de.metas.material.cockpit.stock.StockDataUpdateRequestHandler;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-material-cockpit
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

/**
 * Reset the {@link I_MD_Stock} table.
 * May be run in parallel to "normal" production stock changes.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MD_Stock_Update_From_M_HUs extends JavaProcess
{
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final StockDataUpdateRequestHandler dataUpdateRequestHandler = Adempiere.getBean(StockDataUpdateRequestHandler.class);

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final List<I_MD_Stock_From_HUs_V> huBasedDataRecords = retrieveHuData();

		addLog("Retrieved {} MD_Stock_From_HUs_V records", huBasedDataRecords.size());

		createAndHandleDataUpdateRequests(huBasedDataRecords);
		addLog("Created and handled DataUpdateRequests for all MD_Stock_From_HUs_V records");

		return MSG_OK;
	}

	private List<I_MD_Stock_From_HUs_V> retrieveHuData()
	{
		addLog("Performing a select for Records to correct on MD_Stock_From_HUs_V");
		final List<I_MD_Stock_From_HUs_V> huBasedDataRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock_From_HUs_V.class)
				.addNotEqualsFilter(I_MD_Stock_From_HUs_V.COLUMNNAME_QtyOnHandChange, ZERO)
				.create()
				.list();
		return huBasedDataRecords;
	}

	private void createAndHandleDataUpdateRequests(
			@NonNull final List<I_MD_Stock_From_HUs_V> huBasedDataRecords)
	{
		final StockChangeSourceInfo info = StockChangeSourceInfo.ofResetStockAdPinstanceId(getProcessInfo().getPinstanceId().getRepoId());

		for (final I_MD_Stock_From_HUs_V huBasedDataRecord : huBasedDataRecords)
		{
			final StockDataUpdateRequest dataUpdateRequest = createDataUpdatedRequest(
					huBasedDataRecord,
					info);
			addLog("Handling corrective dataUpdateRequest={}", dataUpdateRequest);
			dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
		}
	}

	private StockDataUpdateRequest createDataUpdatedRequest(
			@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord,
			@NonNull final StockChangeSourceInfo stockDataUpdateRequestSourceInfo)
	{
		final StockDataRecordIdentifier recordIdentifier = createDataRecordIdentifier(huBasedDataRecord);

		final ProductId productId = ProductId.ofRepoId(huBasedDataRecord.getM_Product_ID());
		final Quantity qtyInStorageUOM = Quantity.of(huBasedDataRecord.getQtyOnHandChange(), huBasedDataRecord.getC_UOM());
		final Quantity qtyInProductUOM = uomConversionBL.convertToProductUOM(qtyInStorageUOM, productId);

		final StockDataUpdateRequest dataUpdateRequest = StockDataUpdateRequest.builder()
				.identifier(recordIdentifier)
				.onHandQtyChange(qtyInProductUOM.getAsBigDecimal())
				.sourceInfo(stockDataUpdateRequestSourceInfo)
				.build();
		return dataUpdateRequest;
	}

	private static StockDataRecordIdentifier createDataRecordIdentifier(
			@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord)
	{
		final ProductDescriptor productDescriptor = ProductDescriptor
				.forProductAndAttributes(
						huBasedDataRecord.getM_Product_ID(),
						AttributesKey.ofString(huBasedDataRecord.getAttributesKey()));

		final StockDataRecordIdentifier recordIdentifier = StockDataRecordIdentifier
				.builder()
				.clientId(huBasedDataRecord.getAD_Client_ID())
				.orgId(huBasedDataRecord.getAD_Org_ID())
				.productDescriptor(productDescriptor)
				.warehouseId(huBasedDataRecord.getM_Warehouse_ID())
				.build();
		return recordIdentifier;
	}
}
