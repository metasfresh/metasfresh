package de.metas.camel.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ImmutableList;

import de.metas.camel.metasfresh_data_import.MetasfreshCsvImportFormat;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
import lombok.NonNull;

/*
 * #%L
 * de-metas-camel-shipping
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

class InventoryJsonToCsvMapProcessor implements Processor
{
	private static final Log log = LogFactory.getLog(InventoryJsonToCsvMapProcessor.class);

	/**
	 * MULTI_HU
	 * NOTE to developers: Please keep in sync with list reference "HUAggregationType" {@code AD_Reference_ID=540976}
	 */
	private static final String HU_AGGREGATION_TYPE = "M";

	private final MetasfreshCsvImportFormat csvImportFormat;
	private final ProductCodesToExclude productCodesToExclude;

	public InventoryJsonToCsvMapProcessor(
			@NonNull final MetasfreshCsvImportFormat csvImportFormat,
			@NonNull final ProductCodesToExclude productCodesToExclude)
	{
		this.csvImportFormat = csvImportFormat;
		this.productCodesToExclude = productCodesToExclude;
	}

	@Override
	public void process(final Exchange exchange)
	{
		final JsonInventory inventory = exchange.getIn().getBody(JsonInventory.class);

		final List<Map<String, Object>> csv = toCSV(inventory);

		exchange.getIn().setBody(csv);
		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, csv.size());
	}

	private ImmutableList<Map<String, Object>> toCSV(@Nullable final JsonInventory inventory)
	{
		if (inventory == null || inventory.isEmpty())
		{
			return ImmutableList.of();
		}

		return inventory.getLines()
				.stream()
				.filter(this::checkAllowToExportAndLog)
				.map(this::toCSV)
				.collect(ImmutableList.toImmutableList());
	}

	private boolean checkAllowToExportAndLog(final JsonInventoryLine line)
	{
		if (productCodesToExclude.isProductCodeExcluded(line.getProductValue()))
		{
			log.info("Line excluded because product is on exclude list: " + line);
			return false;
		}
		else
		{
			return true;
		}
	}

	private Map<String, Object> toCSV(final JsonInventoryLine line)
	{
		final Map<String, Object> map = new HashMap<>();

		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_WarehouseValue, line.getWarehouseValue());
		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_LocatorValue, line.getLocatorValue());
		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_InventoryDate, line.getInventoryDate());
		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_ProductValue, line.getProductValue());
		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_QtyCount, line.getQtyCount());
		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_ExternalLineId, line.getExternalLineId());
		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_BestBeforeDate, line.getBestbeforeDate());
		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_LotNumber, line.getLotNumber());
		putCsvCell(map, MetasfreshInventoryCsvConstants.COLUMNNAME_HUAggregationType, HU_AGGREGATION_TYPE);
		return map;
	}

	private void putCsvCell(
			@NonNull final Map<String, Object> csvRow,
			@NonNull final String columnName,
			@Nullable final Object value)
	{
		if (!csvImportFormat.hasColumn(columnName))
		{
			return;
		}

		csvRow.put(columnName, csvImportFormat.formatValue(columnName, value));
	}
}
