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

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonRemittanceAdviceLine.JsonRemittanceAdviceLineBuilder.class)
public class JsonRemittanceAdviceLine
{
	@Schema(required = true,
			description = "This translates to InvoiceIdentifier")
	@NonNull
	String invoiceIdentifier;

	@Schema(required = true,
			description = "This translates to RemittanceAmt")
	@NonNull
	BigDecimal remittedAmount;

	@Schema(description = "This translates to InvoiceDate")
	@Nullable
	String dateInvoiced;

	@Schema(description= "This translates to Service_BPartner_ID")
	@Nullable
	String bpartnerIdentifier;

	@Schema(description = "This translates to ExternalInvoiceDocBaseType")
	@Nullable
	String invoiceBaseDocType;

	@Schema(description = "This translates as InvoiceGrossAmount")
	@Nullable
	BigDecimal invoiceGrossAmount;

	@Schema(description = "This translates to PaymentDiscountAmt")
	@Nullable
	BigDecimal paymentDiscountAmount;

	@Schema(description = "This translates to ServiceFeeAmount")
	@Nullable
	BigDecimal serviceFeeAmount;

	@Schema(description = "This translates to ServiceFeeVatRate")
	@Nullable
	BigDecimal serviceFeeVatRate;

	@JsonIgnoreProperties(ignoreUnknown = true) // the annotation to ignore properties should be set on the deserializer method (on the builder), and not on the base class
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonRemittanceAdviceLineBuilder
	{
	}

}
