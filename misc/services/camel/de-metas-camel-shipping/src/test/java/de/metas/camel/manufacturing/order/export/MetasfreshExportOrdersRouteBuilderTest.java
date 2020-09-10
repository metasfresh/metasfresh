package de.metas.camel.manufacturing.order.export;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import de.metas.camel.test.endpoints.CaptureLastMessage;
import de.metas.camel.test.endpoints.FailOnCall;
import de.metas.camel.test.endpoints.SingleResult;
import de.metas.common.manufacturing.JsonRequestSetOrderExportStatus;
import de.metas.common.manufacturing.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.manufacturing.JsonResponseManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersBulk;
import de.metas.common.manufacturing.Outcome;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.common.shipping.JsonProduct;
import lombok.Builder;
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

public class MetasfreshExportOrdersRouteBuilderTest extends CamelTestSupport
{
	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new MetasfreshExportOrdersRouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Builder(builderMethodName = "adviceRoute", builderClassName = "$AdviceRouteBuilder")
	private void adviceRouteBuilder(
			@NonNull final Processor fromMetasfresh,
			@NonNull final Processor uploadXmlTo,
			@NonNull final Processor feedbackToMetasfresh) throws Exception
	{
		AdviceWithRouteBuilder.adviceWith(
				context, MetasfreshExportOrdersRouteBuilder.ROUTE_ID,
				a -> a.interceptSendToEndpoint("http://baseURL/manufacturing/orders")
						.skipSendToOriginalEndpoint()
						.process(fromMetasfresh));

		AdviceWithRouteBuilder.adviceWith(context, MetasfreshExportOrdersRouteBuilder.UPLOAD_ROUTE,
				a -> a.interceptSendToEndpoint("log:manufacturing-order-upload-dummy")
						.skipSendToOriginalEndpoint()
						.process(uploadXmlTo));

		AdviceWithRouteBuilder.adviceWith(context, MetasfreshExportOrdersRouteBuilder.FEEDBACK_ROUTE,
				a -> a.interceptSendToEndpoint("http://baseURL/manufacturing/orders/exportStatus")
						.skipSendToOriginalEndpoint()
						.process(feedbackToMetasfresh));
	}

	@Test
	void noManufacturingOrders() throws Exception
	{
		final var fromMetasfresh = SingleResult.ofJson(JsonResponseManufacturingOrdersBulk.builder()
				.transactionKey("74dcbcf6-265d-41b3-bb15-1cde4793684a")
				.hasMoreItems(false)
				.build());
		final var uploadXml = new CaptureLastMessage();
		final var feedbackToMetasfresh = new CaptureLastMessage();

		adviceRoute()
				.fromMetasfresh(fromMetasfresh)
				.uploadXmlTo(uploadXml)
				.feedbackToMetasfresh(feedbackToMetasfresh)
				.build();

		final NotifyBuilder notify = new NotifyBuilder(context)
				.whenDone(1)
				.create();

		context.start();
		assertThat(notify.matchesWaitTime()).isTrue();

		assertThat(fromMetasfresh.getCalled()).as("fromMetasfresh shall be called once").isEqualTo(1);
		assertThat(uploadXml.getCalled()).isEqualTo(0);
		assertThat(feedbackToMetasfresh.getCalled()).isEqualTo(0);
	}

	@Test
	void oneManufacturingOrder() throws Exception
	{
		final var fromMetasfresh = SingleResult.ofJson(JsonResponseManufacturingOrdersBulk.builder()
				.transactionKey("transactionKey1")
				.hasMoreItems(false)
				.item(JsonResponseManufacturingOrder.builder()
						.orderId(JsonMetasfreshId.of(1))
						.orgCode("001")
						.documentNo("docNo1")
						.description("description1")
						.dateOrdered(LocalDate.parse("2020-09-10").atStartOfDay(ZoneId.of("Europe/Berlin")))
						.dateStartSchedule(LocalDate.parse("2020-09-11").atStartOfDay(ZoneId.of("Europe/Berlin")))
						.finishGoodProduct(JsonProduct.builder()
								.productNo("productNo1")
								.name("product name")
								.description("product description")
								.packageSize("packageSize")
								.weight(new BigDecimal("1"))
								.documentNote("product document note")
								.build())
						.qtyToProduce(JsonQuantity.builder()
								.qty(new BigDecimal("6.66"))
								.uomCode("KGM")
								.build())
						.build())
				.build());
		final var uploadXml = new CaptureLastMessage();
		final var feedbackToMetasfresh = new CaptureLastMessage();

		adviceRoute()
				.fromMetasfresh(fromMetasfresh)
				.uploadXmlTo(uploadXml)
				.feedbackToMetasfresh(feedbackToMetasfresh)
				.build();

		final NotifyBuilder notify = new NotifyBuilder(context)
				.wereSentTo("http://baseURL/manufacturing/orders/exportStatus")
				.whenDone(1)
				.create();

		context.start();
		assertThat(notify.matchesWaitTime()).isTrue();

		assertThat(fromMetasfresh.getCalled()).as("normalHttpResult shall be called once").isEqualTo(1);

		assertThat(uploadXml.getCalled()).isEqualTo(1);
		// TODO: assertThat(uploadXml.getLastMessageBody()).isEqualTo("<FMPXMLRESULT><ERRORCODE>0</ERRORCODE><PRODUCT BUILD=\"04-29-2019\" NAME=\"FileMaker\" VERSION=\"ProAdvanced 18.0.1\"/><DATABASE DATEFORMAT=\"D.m.yyyy\" LAYOUT=\"xml\" NAME=\"databaseName\" RECORDS=\"1\" TIMEFORMAT=\"k:mm:ss \"/><METADATA><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_artikel_bezeichnung\" TYPE=\"TEXT\"/><FIELD
		// EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_artikel_geschmacksrichtung\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_artikel_gewicht_1_stueck\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_artikel_hinweistext\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_artikel_menge\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\"
		// NAME=\"_artikel_nummer\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_artikel_verpackungsgroesse\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_bestellung_nummer\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_bestellung_position_id\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_ansprechpartner\"
		// TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_email\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_firma\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_hausnummer\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_land\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\"
		// NAME=\"_empfaenger_ort\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_plz\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_strasse\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_telefon_muss_bei_express\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_empfaenger_zustellinfo\"
		// TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_stueckliste_anmerkung\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_stueckliste_artikelnummer\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_stueckliste_datum\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_stueckliste_id\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\"
		// MAXREPEAT=\"1\" NAME=\"_stueckliste_mutter_oder_tochter\" TYPE=\"TEXT\"/><FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"_stueckliste_zeitstempel\" TYPE=\"TEXT\"/></METADATA><RESULTSET FOUND=\"1\"><ROW><COL><DATA>product name</DATA><valueAsString>product
		// name</valueAsString><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA>1</DATA><valueAsString>1</valueAsString><valueAsInteger>1</valueAsInteger><valueAsBigDecimal>1</valueAsBigDecimal></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA>6.66</DATA><valueAsString>6.66</valueAsString><valueAsInteger/><valueAsBigDecimal>6.66</valueAsBigDecimal></COL><COL><DATA>1-productNo1</DATA><valueAsString>1-productNo1</valueAsString><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA>description1</DATA><valueAsString>description1</valueAsString><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA>1-productNo1</DATA><valueAsString>1-productNo1</valueAsString><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA>1</DATA><valueAsString>1</valueAsString><valueAsInteger>1</valueAsInteger><valueAsBigDecimal>1</valueAsBigDecimal></COL><COL><DATA>Mutter</DATA><valueAsString>Mutter</valueAsString><valueAsInteger/><valueAsBigDecimal/></COL><COL><DATA/><valueAsString/><valueAsInteger/><valueAsBigDecimal/></COL></ROW></RESULTSET></FMPXMLRESULT>");

		assertThat(feedbackToMetasfresh.getCalled()).as("postEndpoint shall be called once").isEqualTo(1);
		assertThat(feedbackToMetasfresh.getLastMessageBody(JsonRequestSetOrdersExportStatusBulk.class))
				.isEqualToComparingFieldByField(JsonRequestSetOrdersExportStatusBulk.builder()
						.transactionKey("transactionKey1")
						.item(JsonRequestSetOrderExportStatus.builder()
								.orderId(JsonMetasfreshId.of(1))
								.outcome(Outcome.OK)
								.build())
						.build());
	}

	@Test
	void test_uploadFail() throws Exception
	{
		final var fromMetasfresh = SingleResult.ofJson(JsonResponseManufacturingOrdersBulk.builder()
				.transactionKey("transactionKey1")
				.hasMoreItems(false)
				.item(JsonResponseManufacturingOrder.builder()
						.orderId(JsonMetasfreshId.of(1))
						.orgCode("001")
						.documentNo("docNo1")
						.description("description1")
						.dateOrdered(LocalDate.parse("2020-09-10").atStartOfDay(ZoneId.of("Europe/Berlin")))
						.dateStartSchedule(LocalDate.parse("2020-09-11").atStartOfDay(ZoneId.of("Europe/Berlin")))
						.finishGoodProduct(JsonProduct.builder()
								.productNo("productNo1")
								.name("product name")
								.description("product description")
								.packageSize("packageSize")
								.weight(new BigDecimal("1"))
								.documentNote("product document note")
								.build())
						.qtyToProduce(JsonQuantity.builder()
								.qty(new BigDecimal("6.66"))
								.uomCode("KGM")
								.build())
						.build())
				.build());
		final var uploadXml = FailOnCall.withGenericFileOperationFailedException("test exception");
		final var feedbackToMetasfresh = new CaptureLastMessage();

		adviceRoute()
				.fromMetasfresh(fromMetasfresh)
				.uploadXmlTo(uploadXml)
				.feedbackToMetasfresh(feedbackToMetasfresh)
				.build();

		final NotifyBuilder notify = new NotifyBuilder(context)
				.wereSentTo("http://baseURL/manufacturing/orders/exportStatus")
				.whenDone(1)
				.waitTime(Long.MAX_VALUE)
				.create();

		context.start();
		assertThat(notify.matchesWaitTime()).isTrue();

		assertThat(fromMetasfresh.getCalled()).as("normalHttpResult shall be called once").isEqualTo(1);

		assertThat(uploadXml.getCalled()).isEqualTo(1);

		assertThat(feedbackToMetasfresh.getCalled()).isEqualTo(1);
		assertThat(feedbackToMetasfresh.getLastMessageBody(JsonRequestSetOrdersExportStatusBulk.class))
				.isEqualToComparingFieldByField(JsonRequestSetOrdersExportStatusBulk.builder()
						.transactionKey("transactionKey1")
						.error(uploadXml.getJsonError())
						.item(JsonRequestSetOrderExportStatus.builder()
								.orderId(JsonMetasfreshId.of(1))
								.outcome(Outcome.ERROR)
								.error(null)
								.build())
						.build());
	}
}
