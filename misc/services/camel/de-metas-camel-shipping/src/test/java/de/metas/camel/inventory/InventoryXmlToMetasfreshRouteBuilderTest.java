package de.metas.camel.inventory;

import de.metas.camel.test.endpoints.CaptureLastMessage;
import de.metas.camel.test.endpoints.DoNothing;
import lombok.Builder;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

public class InventoryXmlToMetasfreshRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_FROM_ENDPOINT = "direct:mockInput";

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new InventoryXmlToMetasfreshRouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Builder(builderMethodName = "adviceRoute", builderClassName = "$AdviceRouteBuilder")
	private void adviceRouteBuilder(
			final Processor localStorage,
			final Processor toMetasfresh) throws Exception
	{
		AdviceWithRouteBuilder.adviceWith(context, InventoryXmlToMetasfreshRouteBuilder.ROUTE_ID,
				advice -> {
					advice.replaceFromWith(MOCK_FROM_ENDPOINT);

					advice.interceptSendToEndpoint(InventoryXmlToMetasfreshRouteBuilder.LOCAL_STORAGE_URL)
							.skipSendToOriginalEndpoint()
							.process(localStorage);

					advice.interceptSendToEndpoint(InventoryXmlToMetasfreshRouteBuilder.METASFRESH_EP_DATA_IMPORT)
							.skipSendToOriginalEndpoint()
							.process(toMetasfresh);
				});
	}

	@Test
	public void test() throws Exception
	{
		final var localStorage = new DoNothing();
		final var toMetasfresh = new CaptureLastMessage();

		adviceRoute()
				.localStorage(localStorage)
				.toMetasfresh(toMetasfresh)
				.build();

		context.start();

		template.sendBody(MOCK_FROM_ENDPOINT, this.getClass().getResourceAsStream("siro_lagescan_v1.xml"));
		assertThat(localStorage.getCalled()).isEqualTo(1);
		assertThat(toMetasfresh.getCalled()).isEqualTo(1);
		assertThat(toMetasfresh.getLastMessageBody())
				.isEqualTo("warehouseValue1;H201-15-03;20.08.2020;B-15020111;10;1;01.06.2021;45-15;M\r\n");
	}

	@Test
	public void test_nobestbeforedate() throws Exception
	{
		final var localStorage = new DoNothing();
		final var toMetasfresh = new CaptureLastMessage();

		adviceRoute()
				.localStorage(localStorage)
				.toMetasfresh(toMetasfresh)
				.build();

		context.start();

		template.sendBody(MOCK_FROM_ENDPOINT, this.getClass().getResourceAsStream("lagerscan_nobestbeforedate.xml"));
		assertThat(localStorage.getCalled()).isEqualTo(1);
		assertThat(toMetasfresh.getCalled()).isEqualTo(1);
		assertThat(toMetasfresh.getLastMessageBody())
				.isEqualTo("warehouseValue1;H68-01-01;23.09.2020;3-123456;5100;37623;;;M\r\n");
	}
}
