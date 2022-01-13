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

package de.metas.common.rest_api.v2.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
@JsonDeserialize(builder = JsonOrderPaymentCreateRequest.JsonOrderPaymentCreateRequestBuilder.class)
public class JsonOrderPaymentCreateRequest
{
	@JsonProperty("orgCode")
	@NonNull
	String orgCode;

	@JsonProperty("bpartnerIdentifier")
	@NonNull
	String bpartnerIdentifier;

	@JsonProperty("orderIdentifier")
	@NonNull
	String orderIdentifier;

	@JsonProperty("currencyCode")
	@NonNull
	String currencyCode;

	@JsonProperty("amount")
	@NonNull
	BigDecimal amount;

	@JsonProperty("externalPaymentId")
	@Nullable
	String externalPaymentId;

	@JsonProperty("writeOffAmt")
	@Nullable
	BigDecimal writeOffAmt;

	@JsonProperty("discountAmt")
	@Nullable
	BigDecimal discountAmt;

	@JsonProperty("docBaseType")
	@Nullable
	BigDecimal docBaseType;

	@JsonProperty("docSubType")
	@Nullable
	String docSubType;

	@JsonProperty("targetIBAN")
	@Nullable
	String targetIBAN;

	@JsonProperty("transactionDate")
	@Nullable
	LocalDate transactionDate;
}