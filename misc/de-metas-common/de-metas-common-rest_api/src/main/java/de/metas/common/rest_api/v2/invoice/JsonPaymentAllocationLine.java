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
import de.metas.common.rest_api.v2.SwaggerDocConstants;
import de.metas.common.util.NumberUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPaymentAllocationLine
{
	@NonNull
<<<<<<< HEAD
	@Schema(required = true,
			description = "Identifier of the Invoice in question. Can be\n"
					+ "* a plain `<C_Invoice.C_Invoice_ID>`\n"
					+ "* or something like `doc-<C_Invoice.documentNo>`"
					+ "* or something like `ext-<C_Invoice.ExternalId>`")
=======
	@ApiModelProperty(position = 10, required = true,
			dataType = "java.lang.String",
			value = SwaggerDocConstants.INVOICE_IDENTIFIER_DOC)
>>>>>>> a76b8c750bd (create C_Invoice_Review (#15076))
	String invoiceIdentifier;

	@Schema
	@Nullable
	String docBaseType;

	@Schema
	@Nullable
	String docSubType;

	@Schema
	@Nullable
	BigDecimal amount;

	@Schema
	@Nullable
	BigDecimal discountAmt;

	@Schema
	@Nullable
	BigDecimal writeOffAmt;

	@JsonIgnore
	public boolean isAtLeastOneAmtSet()
	{
		return amount != null || discountAmt != null || writeOffAmt != null;
	}

	@JsonIgnore
	@NonNull
	public BigDecimal getTotalAmt()
	{
		BigDecimal totalAmt = BigDecimal.ZERO;
		if (amount != null)
		{
			totalAmt = totalAmt.add(amount);
		}

		if (discountAmt != null)
		{
			totalAmt = totalAmt.add(discountAmt);
		}

		if (writeOffAmt != null)
		{
			totalAmt = totalAmt.add(writeOffAmt);
		}

		return totalAmt;
	}

	@JsonIgnore
	@NonNull
	public InvoiceIdentifier getInvIdentifier()
	{
		return InvoiceIdentifier.builder()
				.invoiceIdentifier(invoiceIdentifier)
				.docBaseType(docBaseType)
				.docSubType(docSubType)
				.build();
	}

	@JsonIgnore
	@NonNull
	public JsonPaymentAllocationLine aggregate(@NonNull final JsonPaymentAllocationLine line)
	{
		if (!getInvIdentifier().equals(line.getInvIdentifier()))
		{
			throw new RuntimeException("JsonPaymentAllocationLines must share the same InvoiceIdentifier in order to be able to aggregate!"
											   + " this.InvoiceIdentifier=" + this.getInvIdentifier()
											   + " lineToAggregate.InvoiceIdentifier=" + line.getInvIdentifier());
		}

		return JsonPaymentAllocationLine.builder()
				.invoiceIdentifier(invoiceIdentifier)
				.docBaseType(docBaseType)
				.docSubType(docSubType)
				.amount(NumberUtils.sumNullSafe(amount, line.amount))
				.discountAmt(NumberUtils.sumNullSafe(discountAmt, line.discountAmt))
				.writeOffAmt(NumberUtils.sumNullSafe(writeOffAmt, line.writeOffAmt))
				.build();
	}

	@Value
	@Builder
	public static class InvoiceIdentifier
	{
		@NonNull
		String invoiceIdentifier;
		@Nullable
		String docBaseType;
		@Nullable
		String docSubType;
	}
}
