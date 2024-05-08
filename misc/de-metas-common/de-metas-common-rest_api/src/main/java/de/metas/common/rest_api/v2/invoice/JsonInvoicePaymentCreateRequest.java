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

package de.metas.common.rest_api.v2.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Value
@Builder
@JsonDeserialize(builder = JsonInvoicePaymentCreateRequest.JsonInvoicePaymentCreateRequestBuilder.class)
public class JsonInvoicePaymentCreateRequest
{
	@ApiModelProperty(required = true, //
			dataType = "java.lang.String", //
			value = "Identifier of the bPartner in question. Can be\n"
					+ "* a plain `<C_BPartner_ID>`\n"
					+ "* or something like `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n")
	@NonNull
	String bpartnerIdentifier;

	@ApiModelProperty(required = true, //
			dataType = "java.lang.String")
	@NonNull
	String currencyCode;

	@ApiModelProperty(value = "Optional, to specify the `AD_Org_ID`.\n"
			+ "This property needs to be set to the `AD_Org.Value` of an organisation that the invoking user is allowed to access\n"
			+ "or the invoking user needs to belong to an organisation, which is then used.")
	@Nullable
	String orgCode;

	@ApiModelProperty(required = true, //
			dataType = "java.lang.String")
	@Nullable
	String targetIBAN;

	@ApiModelProperty(required = true, //
			dataType = "java.lang.String", //
			value = "An external identifier for the payment being posted to metasfresh. Translates to `C_Payment.ExternalId`")
	@Nullable
	String externalPaymentId;

	@ApiModelProperty(dataType = "java.time.LocalDate",
			value = "If this is sent, it is used for both `accounting date` and `payment date`.")
	@Nullable
	LocalDate transactionDate;

	@ApiModelProperty(value = "List of payment allocations")
	@Nullable
	@JsonProperty("lines")
	List<JsonPaymentAllocationLine> lines;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonInboundPaymentInfoBuilder
	{
	}

	public BigDecimal getAmount()
	{
		return getAmount(JsonPaymentAllocationLine::getAmount);
	}

	public BigDecimal getDiscountAmt()
	{
		return getAmount(JsonPaymentAllocationLine::getDiscountAmt);
	}

	public BigDecimal getWriteOffAmt()
	{
		return getAmount(JsonPaymentAllocationLine::getWriteOffAmt);
	}

	private BigDecimal getAmount(final Function<JsonPaymentAllocationLine, BigDecimal> lineToPayAmt)
	{

		final List<JsonPaymentAllocationLine> lines = getLines();
		return lines == null ? BigDecimal.ZERO : lines.stream().map(lineToPayAmt).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
