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

package de.metas.camel.shipping.shipmentcandidate;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.metas.camel.shipping.CommonUtil;
import de.metas.camel.shipping.FeedbackProzessor;
import de.metas.camel.shipping.RouteBuilderCommonUtil;
import de.metas.common.filemaker.COL;
import de.metas.common.filemaker.FIELD;
import de.metas.common.filemaker.FMPXMLRESULT.FMPXMLRESULTBuilder;
import de.metas.common.filemaker.METADATA;
import de.metas.common.filemaker.RESULTSET;
import de.metas.common.filemaker.ROW;
import de.metas.common.shipping.JsonRequestCandidateResult;
import de.metas.common.shipping.JsonRequestCandidateResults;
import de.metas.common.shipping.Outcome;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidate;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidates;
import lombok.NonNull;

public class ShipmentCandidateJsonToXmlProcessor implements Processor
{
	public static final METADATA METADATA = de.metas.common.filemaker.METADATA.builder()
			.field(FIELD.builder().name("_bestellung_position_id").build())
			.field(FIELD.builder().name("_bestellung_nummer").build())
			.field(FIELD.builder().name("_bestellung_datum").build())
			.field(FIELD.builder().name("_bestellung_zeitstempel").build())
			.field(FIELD.builder().name("_artikel_nummer").build())
			.field(FIELD.builder().name("_artikel_bezeichnung").build())
			.field(FIELD.builder().name("_artikel_menge").build())
			.field(FIELD.builder().name("_artikel_gewicht_1_stueck").build())
			.field(FIELD.builder().name("_artikel_geschmacksrichtung").build())
			.field(FIELD.builder().name("_artikel_verpackungsgroesse").build())
			.field(FIELD.builder().name("_artikel_hinweistext").build())
			.field(FIELD.builder().name("_empfaenger_firma").build())
			.field(FIELD.builder().name("_empfaenger_ansprechpartner").build())
			.field(FIELD.builder().name("_empfaenger_strasse").build())
			.field(FIELD.builder().name("_empfaenger_hausnummer").build())
			.field(FIELD.builder().name("_empfaenger_zustellinfo").build())
			.field(FIELD.builder().name("_empfaenger_plz").build())
			.field(FIELD.builder().name("_empfaenger_ort").build())
			.field(FIELD.builder().name("_empfaenger_land").build())
			.field(FIELD.builder().name("_empfaenger_email").build())
			.field(FIELD.builder().name("_empfaenger_telefon_muss_bei_express").build())
			.build();

	private final Log log = LogFactory.getLog(ShipmentCandidateJsonToXmlProcessor.class);

	@Override
	public void process(@NonNull final Exchange exchange)
	{
		final JsonResponseShipmentCandidates scheduleList = exchange.getIn().getBody(JsonResponseShipmentCandidates.class);

		final var items = scheduleList.getItems();
		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, items.size());
		if (items.isEmpty())
		{
			log.debug("jsonResponseReceiptCandidates.items is empty; -> nothing to do");
			return;
		}

		log.debug("process method called; scheduleList with " + items.size() + " items");
		final String databaseName = exchange.getContext().resolvePropertyPlaceholders("{{shipment-candidate.FMPXMLRESULT.DATABASE.NAME}}");
		final FMPXMLRESULTBuilder builder = CommonUtil
				.createFmpxmlresultBuilder(databaseName, items.size())
				.metadata(METADATA);

		final var resultsBuilder = JsonRequestCandidateResults.builder()
				.transactionKey(scheduleList.getTransactionKey());

		final var propertiesComponent = exchange.getContext().getPropertiesComponent();

		final var resultSet = RESULTSET.builder().found(items.size());
		for (final JsonResponseShipmentCandidate item : items)
		{
			final var row = createROW(item, propertiesComponent);
			resultSet.row(row);

			resultsBuilder.item(JsonRequestCandidateResult.builder()
					.outcome(Outcome.OK) // might be un-OKed later, if e.g. uploading the XML fails
					.scheduleId(item.getId())
					.build());
		}
		exchange.getIn().setBody(builder
				.resultset(resultSet.build())
				.build());
		exchange.getIn().setHeader(Exchange.FILE_NAME, "bestellung_" + scheduleList.getTransactionKey() + ".xml");
		exchange.getIn().setHeader(FeedbackProzessor.FEEDBACK_POJO, resultsBuilder.build());
	}

	private ROW createROW(
			@NonNull final JsonResponseShipmentCandidate item,
			@NonNull final PropertiesComponent propertiesComponent)
	{
		final var row = ROW.builder();

		row.col(COL.of(Integer.toString(item.getId().getValue()))); // _bestellung_position_id
		row.col(COL.of(item.getOrderDocumentNo())); // _bestellung_nummer

		final var dateOrdered = item.getDateOrdered();
		row.col(COL.of(dateOrdered.toLocalDate().format(DateTimeFormatter.ofPattern("d.M.yyyy")))); // _bestellung_datum
		row.col(COL.of(dateOrdered.format(DateTimeFormatter.ofPattern("d.M.yyyy k:mm:ss")))); // _bestellung_zeitstempel

		final var product = item.getProduct();
		final var productNo = CommonUtil.extractProductNo(propertiesComponent, product, item.getOrgCode());

		row.col(COL.of(productNo)); // _artikel_nummer
		row.col(COL.of(product.getName())); // _artikel_bezeichnung
		row.col(COL.of(item.getQuantities().get(0).getQty().toString())); // _artikel_menge
		row.col(COL.of(product.getWeight().toString())); // _artikel_gewicht_1_stueck
		if (item.getAttributeSetInstance() != null) // _artikel_geschmacksrichtung
		{
			row.col(COL.of(item.getAttributeSetInstance().getValueStr("FLAVOR")));
		}
		else
		{
			row.col(COL.of(null));
		}
		row.col(COL.of(product.getPackageSize())); // _artikel_verpackungsgroesse
		row.col(COL.of(product.getDocumentNote())); // _artikel_hinweistext

		final var customer = item.getCustomer();
		if (Objects.equals(customer.getCompanyName(), customer.getContactName()))  // _empfaenger_firma
		{
			row.col(COL.of(null));
		}
		else
		{
			row.col(COL.of(customer.getCompanyName()));
		}
		row.col(COL.of(customer.getContactName())); // _empfaenger_ansprechpartner
		row.col(COL.of(customer.getStreet())); // _empfaenger_strasse
		row.col(COL.of(customer.getStreetNo())); // _empfaenger_hausnummer
		row.col(COL.of(null)); // _empfaenger_zustellinfo
		row.col(COL.of(customer.getPostal())); // _empfaenger_plz
		row.col(COL.of(customer.getCity())); // _empfaenger_ort
		row.col(COL.of(customer.getCountryCode())); // _empfaenger_land
		row.col(COL.of(customer.getContactEmail())); // _empfaenger_email
		row.col(COL.of(customer.getContactPhone())); // _empfaenger_telefon_muss_bei_express

		return row.build();
	}
}
