package de.metas.rest_api.invoice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.ImmutableList;
import de.metas.rest_api.common.MetasfreshId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.invoice.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class SalesInvoicePaymentStatus
{
	@NonNull
	MetasfreshId invoiceId;

	@NonNull
	String invoiceDocumentNumber;

	@NonNull
	BigDecimal openAmt;

	boolean isPaid;

	@ApiModelProperty("3-letter ISO-code of the open amount's currency, like EUR or CHF")
	@NonNull
	String currency;

	@ApiModelProperty("2-letter docstatus of this invoice; e.g. CO = Completed, RE = Reversed")
	@NonNull
	String docStatus;

	@Singular
	@JsonInclude(Include.NON_EMPTY)
	ImmutableList<SalesInvoicePayment> payments;
}
