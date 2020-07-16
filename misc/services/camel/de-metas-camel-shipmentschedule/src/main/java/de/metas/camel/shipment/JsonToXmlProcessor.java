/*
 * #%L
 * de-metas-camel-shipmentschedule
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

package de.metas.camel.shipment;

import de.metas.common.filemaker.DATABASE;
import de.metas.common.filemaker.FIELD;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.FMPXMLRESULT.FMPXMLRESULTBuilder;
import de.metas.common.filemaker.METADATA;
import de.metas.common.filemaker.PRODUCT;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidate;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidates;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JsonToXmlProcessor implements Processor
{
	public static final METADATA METADATA = de.metas.common.filemaker.METADATA.builder()
			.field(FIELD.builder().name("_bestellung_nummer").build())
			.field(FIELD.builder().name("_bestellung_datum").build())
			.field(FIELD.builder().name("_bestellung_zeitstempel").build())
			.field(FIELD.builder().name("_artikel_nummer").build())
			.field(FIELD.builder().name("_artikel_bezeichnung").build())
			.field(FIELD.builder().name("_artikel_menge").build())
			.field(FIELD.builder().name("_artikel_gewicht_1_stueck").build())
			.field(FIELD.builder().name("_artikel_geschmacksrichtung").build())
			.field(FIELD.builder().name("_artikel_verpackungsgroesse").build())
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

	private final Log log = LogFactory.getLog(JsonToXmlProcessor.class);

	@Override
	public void process(@NonNull final Exchange exchange)
	{
		final JsonResponseShipmentCandidates scheduleList = exchange.getIn().getBody(JsonResponseShipmentCandidates.class);
		log.info("process method called; scheduleList=" + scheduleList);

		final FMPXMLRESULTBuilder builder = FMPXMLRESULT.builder()
				.errorCode("0")
				.product(new PRODUCT())
				.database(DATABASE.builder()
						.name("databaseName")// TODO set from application.properties
						.records(Integer.toString(scheduleList.getItems().size()))
						.build())
				.metadata(METADATA);

		for (final JsonResponseShipmentCandidate item : scheduleList.getItems())
		{
			// TODO
		}
		exchange.getIn().setBody(builder.build());
		//	exchange.getIn().setHeader(Exchange.FILE_NAME, fileName);
	}
}
