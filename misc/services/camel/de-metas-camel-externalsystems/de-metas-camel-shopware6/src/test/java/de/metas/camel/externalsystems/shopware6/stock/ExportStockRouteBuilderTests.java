/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.api.model.stock.JsonStock;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Getter;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;

import static de.metas.camel.externalsystems.shopware6.stock.ExportStockRouteBuilder.CREATE_JSON_STOCK_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.stock.ExportStockRouteBuilder.EXPORT_STOCK_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.stock.ExportStockRouteBuilder.EXPORT_STOCK_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class ExportStockRouteBuilderTests extends CamelTestSupport
{
	private static final String JSON_EXTERNAL_SYSTEM_REQUEST = "10_JsonExternalSystemRequest.json";
	private static final String JSON_STOCK = "20_JsonStock.json";

	private final static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final ProcessLogger processLogger = Mockito.mock(ProcessLogger.class);
		return new ExportStockRouteBuilder(processLogger);
	}

	@Test
	void happyFlow() throws Exception
	{
		final MockCreateJsonStockProcessor mockCreateJsonStockProcessor = new MockCreateJsonStockProcessor();
		final MockExportStockProcessor mockExportStockProcessor = new MockExportStockProcessor();

		//given
		prepareRouteForTesting(mockCreateJsonStockProcessor, mockExportStockProcessor);

		context.start();

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = mapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + EXPORT_STOCK_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertThat(mockCreateJsonStockProcessor.called).isEqualTo(1);
		assertThat(mockExportStockProcessor.called).isEqualTo(1);
	}

	private void prepareRouteForTesting(
			final MockCreateJsonStockProcessor mockCreateJsonStockProcessor,
			final MockExportStockProcessor mockExportStockProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, EXPORT_STOCK_ROUTE_ID,
							  advice -> advice.weaveById(CREATE_JSON_STOCK_PROCESSOR_ID)
									  .after()
									  .process(mockCreateJsonStockProcessor));

		AdviceWith.adviceWith(context, EXPORT_STOCK_ROUTE_ID,
							  advice -> advice.weaveById(EXPORT_STOCK_PROCESSOR_ID)
									  .replace()
									  .process(mockExportStockProcessor));
	}

	private static class MockCreateJsonStockProcessor implements Processor
	{
		@Getter
		private int called = 0;

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			called++;

			final JsonStock jsonStock = exchange.getIn().getBody(JsonStock.class);

			final InputStream expectedJsonStockIS = this.getClass().getResourceAsStream(JSON_STOCK);
			final JsonStock expectedJsonStock = mapper
					.readValue(expectedJsonStockIS, JsonStock.class);

			assertThat(jsonStock.getStock()).isEqualTo(expectedJsonStock.getStock());
		}
	}

	private static class MockExportStockProcessor implements Processor
	{
		@Getter
		private int called = 0;

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			called++;
		}
	}
}
