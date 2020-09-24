package de.metas.camel.inventory;

import com.google.common.collect.ImmutableList;
import de.metas.camel.metasfresh_data_import.MetasfreshCsvImportFormat;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public InventoryJsonToCsvMapProcessor(@NonNull final MetasfreshCsvImportFormat csvImportFormat)
	{
		this.csvImportFormat = csvImportFormat;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonInventory inventory = exchange.getIn().getBody(JsonInventory.class);

		if (inventory.isEmpty())
		{
			log.debug("exchange.body is empty! -> nothing to do!");
			exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, 0);
			return;
		}

		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, inventory.getSize());
		exchange.getIn().setBody(toCSV(inventory));
	}

	private List<Map<String, Object>> toCSV(final JsonInventory inventory)
	{
		return inventory.getLines()
				.stream()
				.map(this::toCSV)
				.collect(ImmutableList.toImmutableList());
	}

	private Map<String, Object> toCSV(final JsonInventoryLine line)
	{
		System.out.println("line=" + line);
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
		csvRow.put(columnName, csvImportFormat.formatValue(columnName, value));
	}

}
