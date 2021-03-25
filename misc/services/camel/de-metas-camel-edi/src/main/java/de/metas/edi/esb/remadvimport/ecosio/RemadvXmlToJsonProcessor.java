/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.edi.esb.remadvimport.ecosio;

import at.erpel.schemas._1p0.documents.DocumentType;
import at.erpel.schemas._1p0.documents.DocumentTypeType;
import at.erpel.schemas._1p0.documents.ItemListType;
import at.erpel.schemas._1p0.documents.ListLineItemType;
import at.erpel.schemas._1p0.documents.extensions.edifact.REMADVListLineItemExtensionType;
import at.erpel.schemas._1p0.messaging.header.DateTimeType;
import at.erpel.schemas._1p0.messaging.message.ErpelMessageType;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceRequest;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdvice;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdviceLine;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.DOCUMENT_ZONE_ID;

public class RemadvXmlToJsonProcessor implements Processor
{
	private static final Logger logger = Logger.getLogger(RemadvXmlToJsonProcessor.class.getName());

	@Override
	public void process(final Exchange exchange)
	{
		final String fileName = exchange.getIn().getHeader(Exchange.FILE_NAME_ONLY, String.class);

		final ErpelMessageType remittanceMessage = exchange.getIn().getBody(ErpelMessageType.class);

		final JsonCreateRemittanceAdviceRequest createRemittanceAdviceRequest = buildJsonCreateRemittanceAdviceRequest(remittanceMessage, fileName);

		exchange.getIn().setBody(createRemittanceAdviceRequest);
		exchange.getIn().setHeader(EcosioRemadvConstants.NUMBER_OF_ITEMS, createRemittanceAdviceRequest.getRemittanceAdviceList().size());
	}

	@NonNull
	private JsonCreateRemittanceAdviceRequest buildJsonCreateRemittanceAdviceRequest(@NonNull final ErpelMessageType remittanceMessage, @NonNull final String filename)
	{
		try
		{
			final ImmutableList<DocumentType> remittanceDocumentTypes = remittanceMessage.getDocument()
					.stream()
					.filter(documentType -> DocumentTypeType.REMITTANCE_ADVICE.equals(documentType.getDocumentType()))
					.collect(ImmutableList.toImmutableList());

			final String sendDate = extractSendDateOrNull(remittanceMessage);

			if (remittanceDocumentTypes.isEmpty())
			{
				throw new RuntimeException("Empty document!");
			}

			final ImmutableList<JsonRemittanceAdvice> remittanceAdvices = remittanceDocumentTypes
					.stream()
					.map(documentType -> buildJsonRemittanceAdvice(documentType, sendDate))
					.collect(ImmutableList.toImmutableList());

			return JsonCreateRemittanceAdviceRequest.builder()
					.remittanceAdviceList(remittanceAdvices)
					.build();
		}
		catch (final RuntimeException e)
		{
			logger.log(Level.SEVERE, "*** ERROR buildJsonCreateRemittanceAdviceRequest: encountered for filename: " + filename, e);
			throw e;
		}
	}

	@NonNull
	private JsonRemittanceAdvice buildJsonRemittanceAdvice(@NonNull final DocumentType document, @Nullable final String sendDate)
	{
		final List<JsonRemittanceAdviceLine> lines = extractLinesFromDocument(document);

		final JsonRemittanceAdviceProducer jsonRemittanceAdviceProducer = JsonRemittanceAdviceProducer.builder()
				.sendDate(sendDate)
				.document(document)
				.build();

		return jsonRemittanceAdviceProducer.getRemittanceAdviceWithLines(lines);
	}

	@NonNull
	private ImmutableList<JsonRemittanceAdviceLine> extractLinesFromDocument(@NonNull final DocumentType document)
	{
		final Supplier<RuntimeException> noLinesExSupplier = () -> new RuntimeException("No lines found on document! Doc: " + document);

		final ItemListType lines = document.getDetails()
				.getItemList()
				.stream()
				.findFirst()
				.orElseThrow(noLinesExSupplier);

		if (CollectionUtils.isEmpty(lines.getListLineItem()))
		{
			throw noLinesExSupplier.get();
		}

		return lines.getListLineItem()
				.stream()
				.map(this::extractLineInfo)
				.collect(ImmutableList.toImmutableList());
	}

	private JsonRemittanceAdviceLine extractLineInfo(@NonNull final ListLineItemType lineItemType)
	{
		final Supplier<RuntimeException> missingMandatoryDataExSupplier =
				() -> new RuntimeException("Missing mandatory data! ListLineItemType: " + lineItemType);

		if (lineItemType.getListLineItemExtension() == null
				|| lineItemType.getListLineItemExtension().getListLineItemExtension() == null)
		{
			throw missingMandatoryDataExSupplier.get();
		}

		final REMADVListLineItemExtensionType remadvLineItemExtension = lineItemType.getListLineItemExtension().getListLineItemExtension().getREMADVListLineItemExtension();

		if (remadvLineItemExtension == null)
		{
			throw missingMandatoryDataExSupplier.get();
		}

		return JsonRemittanceAdviceLineProducer.of(remadvLineItemExtension).getLine();
	}

	@Nullable
	private String extractSendDateOrNull(@NonNull final ErpelMessageType erpelMessageType)
	{
		if (erpelMessageType.getErpelBusinessDocumentHeader() == null)
		{
			return null;
		}

		final DateTimeType sendDate = erpelMessageType.getErpelBusinessDocumentHeader().getInterchangeHeader().getDateTime();

		final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return LocalDateTime.parse(sendDate.getDate() + " " + sendDate.getTime(), dateTimeFormatter)
				.atZone(ZoneId.of(DOCUMENT_ZONE_ID))
				.toInstant()
				.toString();
	}
}
