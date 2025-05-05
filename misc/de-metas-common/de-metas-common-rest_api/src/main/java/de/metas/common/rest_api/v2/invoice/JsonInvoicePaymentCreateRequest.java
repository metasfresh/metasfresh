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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Value
@Builder
@Jacksonized
public class JsonInvoicePaymentCreateRequest
{
	@Schema(required = true,
			description = "Identifier of the bPartner in question. Can be\n"
					+ "* a plain `<C_BPartner_ID>`\n"
					+ "* or something like `ext-<I_S_ExternalReference.ExternalSystem>-<I_S_ExternalReference.ExternalReference>`\n")
	@NonNull
	String bpartnerIdentifier;

	@Schema(required = true)
	@NonNull
	String currencyCode;

	@NonNull
	@Schema(required = true, //
			description = "Specifies the direction of the payment: Inbound or Outbound.")
	@Builder.Default
	JsonPaymentDirection type = JsonPaymentDirection.INBOUND;

	@Schema(description = "Optional, to specify the `AD_Org_ID`.\n"
			+ "This property needs to be set to the `AD_Org.Value` of an organisation that the invoking user is allowed to access\n"
			+ "or the invoking user needs to belong to an organisation, which is then used.")
	@Nullable
	String orgCode;

	@Schema(required = true)
	@Nullable
	String targetIBAN;

	@Schema(required = true, //
			description = "An external identifier for the payment being posted to metasfresh. Translates to `C_Payment.ExternalId`")
	@Nullable
	String externalPaymentId;

	@Schema(description = "If this is sent, it is used for both `accounting date` and `payment date`.")
	@Nullable
	LocalDate transactionDate;

	@Schema(description = "List of payment allocations")
	@Nullable
	@JsonProperty("lines")
	List<JsonPaymentAllocationLine> lines;

	@JsonIgnore
	public BigDecimal getAmount()
	{
		return getAmount(JsonPaymentAllocationLine::getAmount);
	}

	@JsonIgnore
	public BigDecimal getDiscountAmt()
	{
		return getAmount(JsonPaymentAllocationLine::getDiscountAmt);
	}

	@JsonIgnore
	public BigDecimal getWriteOffAmt()
	{
		return getAmount(JsonPaymentAllocationLine::getWriteOffAmt);
	}

	@JsonIgnore
	public LocalDate getTransactionDateOr(@NonNull final LocalDate defaultDate)
	{
		return transactionDate != null ? transactionDate : defaultDate;
	}

	@NonNull
	private BigDecimal getAmount(final Function<JsonPaymentAllocationLine, BigDecimal> lineToPayAmt)
	{
		final List<JsonPaymentAllocationLine> lines = getLines();
		return lines == null
				? BigDecimal.ZERO
				: lines.stream().map(lineToPayAmt).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	@JsonIgnore
	public ImmutableMap<JsonPaymentAllocationLine.InvoiceIdentifier, JsonPaymentAllocationLine> getAggregatedLines()
	{
		if (lines == null)
		{
			return ImmutableMap.of();
		}

		final ImmutableListMultimap<JsonPaymentAllocationLine.InvoiceIdentifier, JsonPaymentAllocationLine> invoiceIdentifier2Allocation = lines.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(JsonPaymentAllocationLine::getInvIdentifier, Function.identity()));

		return invoiceIdentifier2Allocation.keySet()
				.stream()
				.map(invoiceIdentifier -> {
					final List<JsonPaymentAllocationLine> lines = invoiceIdentifier2Allocation.get(invoiceIdentifier);

					return lines.stream().reduce(JsonPaymentAllocationLine::aggregate).orElse(null);
				})
				.filter(Objects::nonNull)
				.collect(ImmutableMap.toImmutableMap(JsonPaymentAllocationLine::getInvIdentifier, Function.identity()));
	}
}
