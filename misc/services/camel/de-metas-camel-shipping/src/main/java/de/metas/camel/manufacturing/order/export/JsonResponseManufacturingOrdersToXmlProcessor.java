package de.metas.camel.manufacturing.order.export;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ImmutableList;

import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.filemaker.FIELD;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.METADATA;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.filemaker.ROW;
import de.metas.common.manufacturing.JsonRequestSetOrderExportStatus;
import de.metas.common.manufacturing.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.manufacturing.JsonResponseManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseManufacturingOrderBOMLine;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersBulk;
import de.metas.common.manufacturing.Outcome;
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

class JsonResponseManufacturingOrdersToXmlProcessor implements Processor
{
	private static final String XML_FILENAME_PREFIX = "vorkonfektioniertist";

	/** version 3 */
	private static final METADATA METADATA = de.metas.common.filemaker.METADATA.builder()
			.field(FIELD.builder().name("_artikel_bezeichnung").build())
			.field(FIELD.builder().name("_artikel_geschmacksrichtung").build())
			.field(FIELD.builder().name("_artikel_gewicht_1_stueck").build())
			.field(FIELD.builder().name("_artikel_hinweistext").build())
			.field(FIELD.builder().name("_artikel_menge").build())
			.field(FIELD.builder().name("_artikel_nummer").build())
			.field(FIELD.builder().name("_artikel_verpackungsgroesse").build())
			.field(FIELD.builder().name("_bestellung_nummer").build())
			.field(FIELD.builder().name("_bestellung_position_id").build())
			.field(FIELD.builder().name("_empfaenger_ansprechpartner").build())
			.field(FIELD.builder().name("_empfaenger_email").build())
			.field(FIELD.builder().name("_empfaenger_firma").build())
			.field(FIELD.builder().name("_empfaenger_hausnummer").build())
			.field(FIELD.builder().name("_empfaenger_land").build())
			.field(FIELD.builder().name("_empfaenger_ort").build())
			.field(FIELD.builder().name("_empfaenger_plz").build())
			.field(FIELD.builder().name("_empfaenger_strasse").build())
			.field(FIELD.builder().name("_empfaenger_telefon_muss_bei_express").build())
			.field(FIELD.builder().name("_empfaenger_zustellinfo").build())
			.field(FIELD.builder().name("_stueckliste_anmerkung").build())
			.field(FIELD.builder().name("_stueckliste_artikelnummer").build())
			.field(FIELD.builder().name("_stueckliste_datum").build())
			.field(FIELD.builder().name("_stueckliste_id").build())
			.field(FIELD.builder().name("_stueckliste_mutter_oder_tochter").build())
			.field(FIELD.builder().name("_stueckliste_zeitstempel").build())
			.build();

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void process(@NonNull final Exchange exchange)
	{
		final JsonResponseManufacturingOrdersBulk ordersResponse = exchange.getIn().getBody(JsonResponseManufacturingOrdersBulk.class);
		final var transactionKey = ordersResponse.getTransactionKey();
		final var ordersList = ordersResponse.getItems();

		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, ordersList.size());
		if (ordersList.isEmpty())
		{
			log.debug("JsonResponseManufacturingOrdersBulk.items is empty; -> nothing to do");
			return;
		}

		log.debug("process method called; bulkResponse with " + ordersList.size() + " items");

		final FMPXMLRESULT xml = toXML(exchange.getContext(), ordersList);

		// might be un-OKed later, if e.g. uploading the XML fails
		final var feedback = createExportStatusOK(ordersResponse);

		exchange.getIn().setBody(xml);
		exchange.getIn().setHeader(Exchange.FILE_NAME, XML_FILENAME_PREFIX + "_" + transactionKey + ".xml");
		exchange.getIn().setHeader(ManufacturingOrdersExportFeedbackProcessor.FEEDBACK_POJO, feedback);
	}

	private static FMPXMLRESULT toXML(
			final CamelContext context,
			final @NonNull List<JsonResponseManufacturingOrder> ordersList)
	{
		final var propertiesComponent = context.getPropertiesComponent();
		final String databaseName = context.resolvePropertyPlaceholders("{{manufacturing-orders.FMPXMLRESULT.DATABASE.NAME}}");

		return CommonUtil.createFmpxmlresultBuilder(databaseName, ordersList.size())
				.metadata(METADATA)
				.resultset(toXML_RESULTSET(ordersList, propertiesComponent))
				.build();
	}

	private static RESULTSET toXML_RESULTSET(
			@NonNull final List<JsonResponseManufacturingOrder> ordersList,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final var resultSet = RESULTSET.builder().found(ordersList.size());
		for (final JsonResponseManufacturingOrder order : ordersList)
		{
			resultSet.row(toXML_FinishedGoodROW(order, propertiesComponent));

			for (final JsonResponseManufacturingOrderBOMLine component : order.getComponents())
			{
				resultSet.row(toXML_ComponentROW(order, component, propertiesComponent));
			}
		}

		return resultSet.build();
	}

	private static ROW toXML_FinishedGoodROW(
			@NonNull final JsonResponseManufacturingOrder order,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final String bomProductNo = CommonUtil.extractProductNo(propertiesComponent, order.getFinishGoodProduct(), order.getOrgCode());
		final String bomProductName = order.getFinishGoodProduct().getName();
		final BigDecimal bomProductWeight = order.getFinishGoodProduct().getWeight();

		final var valuesByName = new HashMap<String, String>();
		valuesByName.put("_artikel_bezeichnung", bomProductName);
		valuesByName.put("_artikel_gewicht_1_stueck", bomProductWeight != null ? bomProductWeight.toString() : "");
		valuesByName.put("_artikel_menge", order.getQtyToProduce().getQty().toString());
		valuesByName.put("_artikel_nummer", bomProductNo);
		valuesByName.put("_stueckliste_anmerkung", order.getDescription());
		valuesByName.put("_stueckliste_artikelnummer", bomProductNo);
		valuesByName.put("_stueckliste_id", String.valueOf(order.getOrderId().getValue()));
		valuesByName.put("_stueckliste_mutter_oder_tochter", MainProductOrComponent.MAIN_PRODUCT.getCode());

		return METADATA.createROW(valuesByName);
	}

	private static ROW toXML_ComponentROW(
			@NonNull final JsonResponseManufacturingOrder order,
			@NonNull final JsonResponseManufacturingOrderBOMLine bomLine,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final String productNo = CommonUtil.extractProductNo(propertiesComponent, bomLine.getProduct(), order.getOrgCode());
		final String productName = bomLine.getProduct().getName();
		final BigDecimal productWeight = bomLine.getProduct().getWeight();

		final String bomProductNo = CommonUtil.extractProductNo(propertiesComponent, order.getFinishGoodProduct(), order.getOrgCode());

		final HashMap<String, String> valuesByName = new HashMap<>();
		valuesByName.put("_artikel_bezeichnung", productName);
		valuesByName.put("_artikel_gewicht_1_stueck", productWeight != null ? productWeight.toString() : "");
		valuesByName.put("_artikel_menge", bomLine.getQty().getQty().toString());
		valuesByName.put("_artikel_nummer", productNo);
		valuesByName.put("_stueckliste_anmerkung", order.getDescription());
		valuesByName.put("_stueckliste_artikelnummer", bomProductNo);
		valuesByName.put("_stueckliste_id", String.valueOf(order.getOrderId().getValue()));
		valuesByName.put("_stueckliste_mutter_oder_tochter", MainProductOrComponent.COMPONENT.getCode());

		return METADATA.createROW(valuesByName);
	}

	private static JsonRequestSetOrdersExportStatusBulk createExportStatusOK(final JsonResponseManufacturingOrdersBulk ordersResponse)
	{
		return JsonRequestSetOrdersExportStatusBulk.builder()
				.transactionKey(ordersResponse.getTransactionKey())
				.items(ordersResponse.getItems()
						.stream()
						.map(order -> createExportStatusOK(order))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static JsonRequestSetOrderExportStatus createExportStatusOK(final JsonResponseManufacturingOrder order)
	{
		return JsonRequestSetOrderExportStatus.builder()
				.orderId(order.getOrderId())
				.outcome(Outcome.OK) // might be un-OKed later, if e.g. uploading the XML fails
				.build();
	}
}
