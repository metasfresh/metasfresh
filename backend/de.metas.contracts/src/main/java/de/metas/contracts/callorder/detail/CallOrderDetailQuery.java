/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.detail;

import de.metas.contracts.callorder.summary.model.CallOrderSummaryId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.order.OrderLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

@Value
public class CallOrderDetailQuery
{
	@NonNull
	CallOrderSummaryId summaryId;

	@Nullable
	OrderLineId orderLineId;

	@Nullable
	InOutLineId inOutLineId;

	@Nullable
	InvoiceAndLineId invoiceAndLineId;

	@Builder
	public CallOrderDetailQuery(
			@NonNull final CallOrderSummaryId summaryId,
			@Nullable final OrderLineId orderLineId,
			@Nullable final InOutLineId inOutLineId,
			@Nullable final InvoiceAndLineId invoiceAndLineId)
	{
		final long nonNullSources = Stream.of(orderLineId, inOutLineId, invoiceAndLineId)
				.filter(Objects::nonNull)
				.count();

		if (nonNullSources == 0)
		{
			throw new AdempiereException("One of orderLineId, inOutLineId, invoiceLineId must be provided!");
		}

		if (nonNullSources > 1)
		{
			throw new AdempiereException("Only one of orderLineId, inOutLineId, invoiceLineId must be provided!");
		}

		this.summaryId = summaryId;
		this.orderLineId = orderLineId;
		this.inOutLineId = inOutLineId;
		this.invoiceAndLineId = invoiceAndLineId;
	}
}
