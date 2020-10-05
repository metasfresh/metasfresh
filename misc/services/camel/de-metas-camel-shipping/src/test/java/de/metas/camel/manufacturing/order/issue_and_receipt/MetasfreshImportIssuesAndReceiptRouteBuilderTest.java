package de.metas.camel.manufacturing.order.issue_and_receipt;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;

import de.metas.camel.test.endpoints.CaptureLastMessage;
import de.metas.camel.test.endpoints.DoNothing;
import de.metas.camel.test.endpoints.SingleResult;
import de.metas.common.manufacturing.JsonRequestHULookup;
import de.metas.common.manufacturing.JsonRequestIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonRequestReceiveFromManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonResponseReceiveFromManufacturingOrder;
import de.metas.common.manufacturing.Outcome;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.util.time.SystemTime;
import lombok.Builder;

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

public class MetasfreshImportIssuesAndReceiptRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_FROM_ENDPOINT = "direct:mockInput";
	private static final String MOCK_XML_TO_JSON_ENDPOINT = "mock:xmlToJsonResult";

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new MetasfreshImportIssuesAndReceiptRouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	public void beforeEach(ExtensionContext context)
	{
		SystemTime.resetTimeSource();
	}

	@Override
	public void afterTestExecution(final ExtensionContext context)
	{
		SystemTime.resetTimeSource();
	}

	@Builder(builderMethodName = "adviceRoute", builderClassName = "$AdviceRouteBuilder")
	private void adviceRouteBuilder(
			final Processor localStorage,
			final Processor toMetasfresh) throws Exception
	{
		AdviceWithRouteBuilder.adviceWith(context, MetasfreshImportIssuesAndReceiptRouteBuilder.ROUTE_ID,
				advice -> {
					advice.replaceFromWith(MOCK_FROM_ENDPOINT);

					advice.interceptSendToEndpoint(MetasfreshImportIssuesAndReceiptRouteBuilder.LOCAL_STORAGE_URL)
							.skipSendToOriginalEndpoint()
							.process(localStorage);

					advice.weaveById(MetasfreshImportIssuesAndReceiptRouteBuilder.XML_TO_JSON_PROCESSOR_ID)
							.after()
							.log(LoggingLevel.INFO, "json: ${body}")
							.to(MOCK_XML_TO_JSON_ENDPOINT);

					advice.interceptSendToEndpoint(MetasfreshImportIssuesAndReceiptRouteBuilder.METASFRESH_EP_REPORT)
							.skipSendToOriginalEndpoint()
							.process(toMetasfresh);
				});
	}

	@Test
	public void regularFile() throws Exception
	{
		final ZonedDateTime currentTime = ZonedDateTime.parse("2020-09-10T16:13:53.157+02:00");
		SystemTime.setFixedTimeSource(currentTime);

		final var localStorage = new DoNothing();
		final var toMetasfresh = new CaptureLastMessage()
				.andThen(SingleResult.ofJson(JsonResponseManufacturingOrdersReport.builder()
						.transactionKey("trx1")
						.receipt(JsonResponseReceiveFromManufacturingOrder.builder().requestId("1").outcome(Outcome.OK).build())
						.issue(JsonResponseIssueToManufacturingOrder.builder().requestId("2").outcome(Outcome.OK).build())
						.issue(JsonResponseIssueToManufacturingOrder.builder().requestId("3").outcome(Outcome.OK).build())
						.issue(JsonResponseIssueToManufacturingOrder.builder().requestId("4").outcome(Outcome.OK).build())
						.issue(JsonResponseIssueToManufacturingOrder.builder().requestId("5").outcome(Outcome.OK).build())
						.issue(JsonResponseIssueToManufacturingOrder.builder().requestId("6").outcome(Outcome.OK).build())
						.issue(JsonResponseIssueToManufacturingOrder.builder().requestId("7").outcome(Outcome.OK).build())
						.issue(JsonResponseIssueToManufacturingOrder.builder().requestId("8").outcome(Outcome.OK).build())
						.build()));

		adviceRoute()
				.localStorage(localStorage)
				.toMetasfresh(toMetasfresh)
				.build();

		context.start();

		final InputStream xml = this.getClass().getResourceAsStream("siro_vorkonfektioniertist_v2.xml");

		template.sendBody(MOCK_FROM_ENDPOINT, xml);

		assertThat(localStorage.getCalled()).isEqualTo(1);
		assertThat(toMetasfresh.getCalled()).isEqualTo(1);
		assertThat(toMetasfresh.getLastMessageBody(JsonRequestManufacturingOrdersReport.class))
				.isEqualToComparingFieldByField(JsonRequestManufacturingOrdersReport.builder()
						.receipt(JsonRequestReceiveFromManufacturingOrder.builder()
								.requestId("1")
								.orderId(JsonMetasfreshId.of(12345))
								.qtyToReceiveInStockUOM(new BigDecimal(100))
								.date(currentTime)
								.lotNumber(null)
								.bestBeforeDate(LocalDate.parse("2021-07-01"))
								.build())
						.issue(JsonRequestIssueToManufacturingOrder.builder()
								.requestId("2")
								.orderId(JsonMetasfreshId.of(12345))
								.qtyToIssueInStockUOM(new BigDecimal("100"))
								.date(currentTime)
								.productNo("51.10.43")
								.handlingUnit(JsonRequestHULookup.builder()
										.lotNumber("48-23")
										.bestBeforeDate(LocalDate.parse("2021-07-01"))
										.build())
								.build())
						.issue(JsonRequestIssueToManufacturingOrder.builder()
								.requestId("3")
								.orderId(JsonMetasfreshId.of(12345))
								.qtyToIssueInStockUOM(new BigDecimal("100"))
								.date(currentTime)
								.productNo("51.10.91")
								.handlingUnit(JsonRequestHULookup.builder()
										.lotNumber("48-55")
										.bestBeforeDate(LocalDate.parse("2021-07-01"))
										.build())
								.build())
						.issue(JsonRequestIssueToManufacturingOrder.builder()
								.requestId("4")
								.orderId(JsonMetasfreshId.of(12345))
								.qtyToIssueInStockUOM(new BigDecimal("100"))
								.date(currentTime)
								.productNo("51.10.96")
								.handlingUnit(JsonRequestHULookup.builder()
										.lotNumber("49-17")
										.bestBeforeDate(LocalDate.parse("2021-07-01"))
										.build())
								.build())
						.issue(JsonRequestIssueToManufacturingOrder.builder()
								.requestId("5")
								.orderId(JsonMetasfreshId.of(12345))
								.qtyToIssueInStockUOM(new BigDecimal("100"))
								.date(currentTime)
								.productNo("51.10.66")
								.handlingUnit(JsonRequestHULookup.builder()
										.lotNumber("33-11")
										.bestBeforeDate(LocalDate.parse("2021-07-01"))
										.build())
								.build())
						.issue(JsonRequestIssueToManufacturingOrder.builder()
								.requestId("6")
								.orderId(JsonMetasfreshId.of(12345))
								.qtyToIssueInStockUOM(new BigDecimal("100"))
								.date(currentTime)
								.productNo("51.10.39")
								.handlingUnit(JsonRequestHULookup.builder()
										.lotNumber(null)
										.bestBeforeDate(null)
										.build())
								.build())
						.issue(JsonRequestIssueToManufacturingOrder.builder()
								.requestId("7")
								.orderId(JsonMetasfreshId.of(12345))
								.qtyToIssueInStockUOM(new BigDecimal("100"))
								.date(currentTime)
								.productNo("52.23.05")
								.handlingUnit(JsonRequestHULookup.builder()
										.lotNumber("88-55")
										.bestBeforeDate(LocalDate.parse("2021-07-01"))
										.build())
								.build())
						.issue(JsonRequestIssueToManufacturingOrder.builder()
								.requestId("8")
								.orderId(JsonMetasfreshId.of(12345))
								.qtyToIssueInStockUOM(new BigDecimal("100"))
								.date(currentTime)
								.productNo("51.21.03")
								.handlingUnit(JsonRequestHULookup.builder()
										.lotNumber(null)
										.bestBeforeDate(LocalDate.parse("2021-07-01"))
										.build())
								.build())
						.build());

		assertMockEndpointsSatisfied();
	}
}
