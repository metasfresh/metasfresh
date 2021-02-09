/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.common.rest_api.remittanceadvice;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	@ApiModelProperty(dataType = "java.lang.String",
			value = "This translates to AD_Org_ID")
	String orgCode;

	@ApiModelProperty(required = true,
			dataType = "java.lang.String",
			value = "This translates to Source_BPartner_ID")
	@NonNull
	String senderId;

	@ApiModelProperty(required = true,
			dataType = "java.lang.String",
			value = "This translates to Destintion_BPartner_ID")
	@NonNull
	String recipientId;

	@ApiModelProperty(dataType = "java.lang.String",
			value = "This translates to ExternalDocumentNo")
	String documentNumber;

	@ApiModelProperty(dataType = "java.lang.String",
			value = "This translates to SendAt, using DateTimeFormatter ISO_INSTANT e.g.2017-11-22T00:00:00Z")
	String sendDate;

	@ApiModelProperty(dataType = "java.lang.String",
			value = "This translates to DateDoc, using DateTimeFormatter ISO_INSTANT. "
					+ "If not provided current date is taken ")
	String documentDate;

	@ApiModelProperty(required = true,
			dataType = "RemittanceAdviceType as INBOUND/OUTBOUND",
			value = "This translates to C_DocType_ID")
	@NonNull
	RemittanceAdviceType remittanceAdviceType;

	@ApiModelProperty(required = true,
			dataType = "java.math.BigDecimal",
			value = "This translates as RemittanceAmt")
	@NonNull
	BigDecimal remittedAmountSum;

	@ApiModelProperty(required = true,
			dataType = "java.math.BigDecimal",
			value = "This translates as RemittanceAmt_Currency_ID")
	@NonNull
	String remittanceAmountCurrencyISO;

	@ApiModelProperty(dataType = "java.math.BigDecimal",
			value = "This translates as ServiceFeeAmount")
	BigDecimal serviceFeeAmount;

	@ApiModelProperty(dataType = "java.lang.String",
			value = "This translates as ServiceFeeAmount_Currency_ID")
	String serviceFeeCurrencyISO;

	@ApiModelProperty(dataType = "java.math.BigDecimal",
			value = "This translates as PaymentDiscountAmountSum")
	BigDecimal paymentDiscountAmountSum;

	@ApiModelProperty(dataType = "java.lang.String",
			value = "This translates as AdditionalNotes")
	String additionalNotes;

	@ApiModelProperty(dataType = "List<JsonRemittanceAdviceLine>",
			value = "This translates as each entry in RemittanceAdviceLine table for a remittanceAdvice document")
	List<JsonRemittanceAdviceLine> lines;

	@JsonIgnoreProperties(ignoreUnknown = true) // the annotation to ignore properties should be set on the deserializer method (on the builder), and not on the base class
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonRemittanceAdviceBuilder
	{
	}
}
