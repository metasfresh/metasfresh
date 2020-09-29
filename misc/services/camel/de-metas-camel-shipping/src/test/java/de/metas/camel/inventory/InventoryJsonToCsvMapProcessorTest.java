package de.metas.camel.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import javax.annotation.Nullable;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.camel.metasfresh_data_import.MetasfreshCsvImportFormat;
import de.metas.camel.metasfresh_data_import.MetasfreshCsvImportFormat.MetasfreshCsvImportFormatBuilder;
import de.metas.camel.shipping.RouteBuilderCommonUtil;

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

public class InventoryJsonToCsvMapProcessorTest
{
	private MetasfreshCsvImportFormatBuilder csvImportFormat()
	{
		return MetasfreshCsvImportFormat.builder()
				.skipHeaderRecord(true)
				.delimiter(";")
				.newLine("\r\n");
	}

	private DefaultExchange createExchange(@Nullable final JsonInventory jsonInventory)
	{
		final DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
		exchange.getIn().setBody(jsonInventory);
		return exchange;
	}

	private ImmutableList<Map<String, Object>> getCSV(final DefaultExchange exchange)
	{
		@SuppressWarnings("unchecked")
		final ImmutableList<Map<String, Object>> csv = (ImmutableList<Map<String, Object>>)exchange.getIn().getBody();
		return csv;
	}

	@Test
	public void nullBody()
	{
		final InventoryJsonToCsvMapProcessor processor = new InventoryJsonToCsvMapProcessor(
				csvImportFormat()
						.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_ProductValue)
						.build(),
				ProductCodesToExclude.EMPTY);
		final DefaultExchange exchange = createExchange(null);
		processor.process(exchange);

		assertThat(getCSV(exchange)).isEmpty();
		assertThat(exchange.getIn().getHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS)).isEqualTo(0);
	}

	@Test
	public void emptyJsonInventory()
	{
		final InventoryJsonToCsvMapProcessor processor = new InventoryJsonToCsvMapProcessor(
				csvImportFormat()
						.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_ProductValue)
						.build(),
				ProductCodesToExclude.EMPTY);
		final DefaultExchange exchange = createExchange(JsonInventory.builder().build());
		processor.process(exchange);

		assertThat(getCSV(exchange)).isEmpty();
		assertThat(exchange.getIn().getHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS)).isEqualTo(0);
	}

	@Test
	public void partiallyExcludeSomeProducts()
	{
		final InventoryJsonToCsvMapProcessor processor = new InventoryJsonToCsvMapProcessor(
				csvImportFormat()
						.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_ProductValue)
						.build(),
				ProductCodesToExclude.ofCommaSeparatedString("P1,P2"));

		final DefaultExchange exchange = createExchange(JsonInventory.builder()
				.line(JsonInventoryLine.builder().productValue("P1").build())
				.line(JsonInventoryLine.builder().productValue("P2").build())
				.line(JsonInventoryLine.builder().productValue("P3").build())
				.build());

		processor.process(exchange);

		assertThat(getCSV(exchange))
				.containsExactly(ImmutableMap.<String, Object> builder()
						.put(MetasfreshInventoryCsvConstants.COLUMNNAME_ProductValue, "P3")
						.build());
		assertThat(exchange.getIn().getHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS)).isEqualTo(1);
	}

	@Test
	public void excludeAll()
	{
		final InventoryJsonToCsvMapProcessor processor = new InventoryJsonToCsvMapProcessor(
				csvImportFormat()
						.stringColumn(MetasfreshInventoryCsvConstants.COLUMNNAME_ProductValue)
						.build(),
				ProductCodesToExclude.ofCommaSeparatedString("P1"));

		final DefaultExchange exchange = createExchange(JsonInventory.builder()
				.line(JsonInventoryLine.builder().productValue("P1").build())
				.build());

		processor.process(exchange);

		assertThat(getCSV(exchange)).isEmpty();
		assertThat(exchange.getIn().getHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS)).isEqualTo(0);
	}

}
