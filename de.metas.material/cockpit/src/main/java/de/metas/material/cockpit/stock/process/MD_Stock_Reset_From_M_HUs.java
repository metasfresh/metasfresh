package de.metas.material.cockpit.stock.process;

import lombok.NonNull;

import java.sql.SQLException;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.lang.Mutable;
import org.compiere.Adempiere;
import org.compiere.util.DB;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.model.I_MD_Stock_From_HUs_V;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockDataUpdateRequest;
import de.metas.material.cockpit.stock.StockDataUpdateRequestHandler;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
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
public class MD_Stock_Reset_From_M_HUs extends JavaProcess
{
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final StockDataUpdateRequestHandler dataUpdateRequestHandler = Adempiere.getBean(StockDataUpdateRequestHandler.class);

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final List<I_MD_Stock_From_HUs_V> huBasedDataRecords = truncateStockTableAndRetrieveHuData();

		addLog("Retrieved {} MD_Stock_From_HUs_V records", huBasedDataRecords.size());

		createAndHandleDataUpdateRequests(huBasedDataRecords);
		addLog("Created and handled DataUpdateRequest for all MD_Stock_From_HUs_V records");

		return MSG_OK;
	}

	/**
	 * Truncate an retrieve within one transaction.
	 * That way, the new stock are data is from the time the table was emptied.
	 * New stock changes that occur when the select runs won't be lost.
	 */
	private List<I_MD_Stock_From_HUs_V> truncateStockTableAndRetrieveHuData()
	{
		final Mutable<List<I_MD_Stock_From_HUs_V>> result = new Mutable<>();

		Services.get(ITrxManager.class).run(() -> {
			addLog("Truncating MD_Stock table");
			try
			{
				DB
						.prepareStatement("TRUNCATE TABLE " + I_MD_Stock.Table_Name, ITrx.TRXNAME_ThreadInherited)
						.execute();
			}
			catch (final SQLException e)
			{
				throw DBException.wrapIfNeeded(e);
			}

			addLog("Performing a full select on MD_Stock_From_HUs_V");
			final List<I_MD_Stock_From_HUs_V> huBasedDataRecords = Services.get(IQueryBL.class)
					.createQueryBuilder(I_MD_Stock_From_HUs_V.class)
					.create()
					.list();
			result.setValue(huBasedDataRecords);
		});
		return result.getValue();
	}

	private void createAndHandleDataUpdateRequests(
			@NonNull final List<I_MD_Stock_From_HUs_V> huBasedDataRecords)
	{
		for (final I_MD_Stock_From_HUs_V huBasedDataRecord : huBasedDataRecords)
		{
			final StockDataUpdateRequest dataUpdateRequest = createDataUpdatedRequest(huBasedDataRecord);
			dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
		}
	}

	private StockDataUpdateRequest createDataUpdatedRequest(
			@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord)
	{
		final StockDataRecordIdentifier recordIdentifier = createDataRecordIdentifier(huBasedDataRecord);

		final ProductId productId = ProductId.ofRepoId(huBasedDataRecord.getM_Product_ID());
		final Quantity qtyInStorageUOM = Quantity.of(huBasedDataRecord.getQtyOnHand(), huBasedDataRecord.getC_UOM());
		final Quantity qtyInStockingUOM = uomConversionBL.convertToProductUOM(qtyInStorageUOM, productId);

		final StockDataUpdateRequest dataUpdateRequest = StockDataUpdateRequest.builder()
				.identifier(recordIdentifier)
				.onHandQtyChange(qtyInStockingUOM.getAsBigDecimal())
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

		final StockDataRecordIdentifier recordIdentifier = StockDataRecordIdentifier.builder()
				.productDescriptor(productDescriptor)
				.warehouseId(huBasedDataRecord.getM_Warehouse_ID())
				.build();
		return recordIdentifier;
	}
}
