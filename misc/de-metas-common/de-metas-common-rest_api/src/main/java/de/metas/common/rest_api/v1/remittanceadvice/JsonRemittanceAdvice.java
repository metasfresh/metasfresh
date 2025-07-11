/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v1.remittanceadvice;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonRemittanceAdvice.JsonRemittanceAdviceBuilder.class)
public class JsonRemittanceAdvice
{
	@Schema(description = "This translates to AD_Org_ID")
	@Nullable
	String orgCode;

	@Schema(required = true,
			description = "This translates to Source_BPartner_ID")
	@NonNull
	String senderId;

	@Schema(required = true,
			description = "This translates to Destintion_BPartner_ID")
	@NonNull
	String recipientId;

	@Schema(description = "This translates to ExternalDocumentNo")
	@NonNull
	String documentNumber;

	@Schema(description = "This translates to SendAt, using DateTimeFormatter ISO_INSTANT e.g.2017-11-22T00:00:00Z")
	@Nullable
	String sendDate;

	@Schema(description = "This translates to DateDoc, using DateTimeFormatter ISO_INSTANT. "
					+ "If not provided current date is taken ")
	@Nullable
	String documentDate;

	@Schema(required = true,
			description = "RemittanceAdviceType as INBOUND/OUTBOUND (This translates to C_DocType_ID)")
	@NonNull
	RemittanceAdviceType remittanceAdviceType;

	@Schema(required = true,
			description = "This translates as RemittanceAmt")
	@NonNull
	BigDecimal remittedAmountSum;

	@Schema(required = true,
			type = "java.math.BigDecimal",
			description = "This translates as RemittanceAmt_Currency_ID")
	@NonNull
	String remittanceAmountCurrencyISO;

	@Schema(description = "This translates as ServiceFeeAmount")
	BigDecimal serviceFeeAmount;

	@Schema(description = "This translates as ServiceFeeAmount_Currency_ID")
	@Nullable
	String serviceFeeCurrencyISO;

	@Schema(description = "This translates as PaymentDiscountAmountSum")
	@Nullable
	BigDecimal paymentDiscountAmountSum;

	@Schema(description = "This translates as AdditionalNotes")
	@Nullable
	String additionalNotes;

	@Schema(description = "This translates as each entry in RemittanceAdviceLine table for a remittanceAdvice document")
	@NonNull
	List<JsonRemittanceAdviceLine> lines;

	@JsonIgnoreProperties(ignoreUnknown = true) // the annotation to ignore properties should be set on the deserializer method (on the builder), and not on the base class
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonRemittanceAdviceBuilder
	{
	}
}
