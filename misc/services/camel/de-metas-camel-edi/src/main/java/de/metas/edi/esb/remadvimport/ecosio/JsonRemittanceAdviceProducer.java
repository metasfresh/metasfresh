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

import at.erpel.schemas._1p0.documents.BusinessEntityType;
import at.erpel.schemas._1p0.documents.CustomerType;
import at.erpel.schemas._1p0.documents.DocumentType;
import at.erpel.schemas._1p0.documents.FurtherIdentificationType;
import at.erpel.schemas._1p0.documents.extensions.edifact.REMADVExtensionType;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdvice;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdviceLine;
import de.metas.common.rest_api.v1.remittanceadvice.RemittanceAdviceType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.DOCUMENT_ZONE_ID;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.GLN_PREFIX;

@Builder
@Value
public class JsonRemittanceAdviceProducer
{
	@NonNull
	DocumentType document;

	@Nullable
	String sendDate;

	public JsonRemittanceAdvice getRemittanceAdviceWithLines(@NonNull final List<JsonRemittanceAdviceLine> lines)
	{
		if (document.getDocumentExtension() == null
				|| document.getDocumentExtension().getDocumentExtension() == null
				|| document.getDocumentExtension().getDocumentExtension().getREMADVExtension() == null)
		{
			throw new RuntimeException("Missing required document extension! doc: " + document);
		}

		final REMADVExtensionType remadvDocExtension = document.getDocumentExtension().getDocumentExtension().getREMADVExtension();
		final String currencyISOCode = document.getDocumentCurrency().value();

		return JsonRemittanceAdvice.builder()
				.remittanceAdviceType(RemittanceAdviceType.INBOUND)

				.senderId(getSenderId())
				.recipientId(getRecipientId())

				.sendDate(sendDate)
				.documentDate(getDocumentDate())
				.documentNumber(document.getDocumentNumber())

				.paymentDiscountAmountSum(asBigDecimal(remadvDocExtension.getPaymentDiscountAmount()).orElse(null))
				.serviceFeeAmount(asBigDecimal(remadvDocExtension.getComissionAmount()).orElse(null))
				.remittedAmountSum(asBigDecimal(remadvDocExtension.getAmountRemitted())
										   .orElseThrow(() -> new RuntimeException("Total remitted amount missing!" + document)))

				.remittanceAmountCurrencyISO(currencyISOCode)
				.serviceFeeCurrencyISO(currencyISOCode)

				.lines(lines)
				.build();
	}

	@NonNull
	private String getSenderId()
	{
		final CustomerType customerType = document.getCustomer();

		if (customerType == null)
		{
			throw new RuntimeException("No customer found for document! doc: " + document);
		}

		return getGLNFromBusinessEntityType(customerType);
	}

	@NonNull
	private String getRecipientId()
	{
		return getGLNFromBusinessEntityType(document.getSupplier());
	}

	private String getGLNFromBusinessEntityType(@NonNull final BusinessEntityType businessEntityType)
	{
		if (!StringUtils.isEmpty(businessEntityType.getGLN()))
		{
			return GLN_PREFIX + businessEntityType.getGLN();
		}

		final String gln = businessEntityType.getFurtherIdentification()
				.stream()
				.filter(furtherIdentificationType -> furtherIdentificationType.getIdentificationType().equals("GLN"))
				.findFirst()
				.map(FurtherIdentificationType::getValue)
				.orElseThrow(() -> new RuntimeException("No GLN found for businessEntity!businessEntity: " + businessEntityType));

		return GLN_PREFIX + gln;
	}

	private String getDocumentDate()
	{
		return document.getDocumentDate()
				.toGregorianCalendar()
				.toZonedDateTime()
				.withZoneSameLocal(ZoneId.of(DOCUMENT_ZONE_ID))
				.toInstant()
				.toString();
	}

	private Optional<BigDecimal> asBigDecimal(@Nullable final String value)
	{
		if (StringUtils.isEmpty(value))
		{
			return Optional.empty();
		}

		try
		{
			final BigDecimal bigDecimalValue = new BigDecimal(value);
			return Optional.of(bigDecimalValue.abs());
		}
		catch (final Exception e)
		{

			return Optional.empty();
		}
	}
}
