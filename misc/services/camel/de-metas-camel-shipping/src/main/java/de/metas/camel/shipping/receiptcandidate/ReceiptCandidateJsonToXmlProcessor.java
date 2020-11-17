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

package de.metas.camel.shipping.receiptcandidate;

import de.metas.camel.shipping.FeedbackProzessor;
import de.metas.camel.shipping.JsonToXmlProcessorCommonUtil;
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
import de.metas.common.shipping.receiptcandidate.JsonResponseReceiptCandidate;
import de.metas.common.shipping.receiptcandidate.JsonResponseReceiptCandidates;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.format.DateTimeFormatter;

public class ReceiptCandidateJsonToXmlProcessor implements Processor
{
	public static final METADATA METADATA = de.metas.common.filemaker.METADATA.builder()
			.field(FIELD.builder().name("_anlieferung_position_id").build())
			.field(FIELD.builder().name("_anlieferung_nummer").build())
			.field(FIELD.builder().name("_anlieferung_datum").build())
			.field(FIELD.builder().name("_anlieferung_zeitstempel").build())
			.field(FIELD.builder().name("_anlieferung_mhd_charge").build())
			.field(FIELD.builder().name("_anlieferung_mhd_ablauf_datum").build())
			.field(FIELD.builder().name("_artikel_nummer").build())
			.field(FIELD.builder().name("_artikel_bezeichnung").build())
			.field(FIELD.builder().name("_artikel_menge").build())
			.field(FIELD.builder().name("_artikel_gewicht_1_stueck").build())
			.field(FIELD.builder().name("_artikel_geschmacksrichtung").build())
			.field(FIELD.builder().name("_artikel_verpackungsgroesse").build())
			.build();

	private final Log log = LogFactory.getLog(ReceiptCandidateJsonToXmlProcessor.class);

	@Override
	public void process(@NonNull final Exchange exchange)
	{
		final JsonResponseReceiptCandidates scheduleList = exchange.getIn().getBody(JsonResponseReceiptCandidates.class);

		final var items = scheduleList.getItems();
		exchange.getIn().setHeader(RouteBuilderCommonUtil.NUMBER_OF_ITEMS, items.size());
		if (items.isEmpty())
		{
			log.debug("jsonResponseReceiptCandidates.items is empty; -> nothing to do");
			return;
		}

		log.debug("process method called; scheduleList with " + items.size() + " items");
		final FMPXMLRESULTBuilder builder = JsonToXmlProcessorCommonUtil
				.createFmpxmlresultBuilder(exchange, items.size())
				.metadata(METADATA);

		final var resultsBuilder = JsonRequestCandidateResults.builder()
				.transactionKey(scheduleList.getTransactionKey());

		final var resultSet = RESULTSET.builder().found(items.size());
		for (final JsonResponseReceiptCandidate item : items)
		{
			final var row = createROW(item);
			resultSet.row(row);

			resultsBuilder.item(JsonRequestCandidateResult.builder()
					.outcome(Outcome.OK) // might be un-OKed later, if e.g. uploading the XML fails
					.scheduleId(item.getId())
					.build());
		}
		final var xmlPojo = builder.resultset(resultSet.build()).build();
		exchange.getIn().setBody(xmlPojo);
		exchange.getIn().setHeader(Exchange.FILE_NAME, "anlieferung_" + scheduleList.getTransactionKey() + ".xml");
		exchange.getIn().setHeader(FeedbackProzessor.FEEDBACK_POJO, resultsBuilder.build());
	}

	private ROW createROW(@NonNull final JsonResponseReceiptCandidate item)
	{
		final var row = ROW.builder();

		row.col(COL.of(Integer.toString(item.getId().getValue()))); // _anlieferung_position_id
		row.col(COL.of(item.getOrderDocumentNo())); // _anlieferung_nummer

		final var dateOrdered = item.getDateOrdered();
		row.col(COL.of(dateOrdered.toLocalDate().format(DateTimeFormatter.ofPattern("d.M.yyyy")))); // _anlieferung_datum
		row.col(COL.of(dateOrdered.format(DateTimeFormatter.ofPattern("d.M.yyyy k:mm:ss")))); // _anlieferung_zeitstempel

		row.col(COL.of(null)); // _anlieferung_mhd_charge
		row.col(COL.of(null)); // _anlieferung_mhd_ablauf_datum

		final var product = item.getProduct();
		row.col(COL.of(product.getProductNo())); // _artikel_nummer
		row.col(COL.of(product.getName())); // _artikel_bezeichnung
		row.col(COL.of(item.getQuantities().get(0).getQty().toString())); // _artikel_menge
		row.col(COL.of(product.getWeight().toString())); // _artikel_gewicht_1_stueck
		if (item.getAttributeSetInstance() != null)
		{
			row.col(COL.of(item.getAttributeSetInstance().getValueStr("FLAVOR"))); // _artikel_geschmacksrichtung
		}
		row.col(COL.of(product.getPackageSize())); // _artikel_verpackungsgroesse

		return row.build();
	}
}
