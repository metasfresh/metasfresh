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

import de.metas.contracts.FlatrateTermId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

@Value
public class UpsertCallOrderDetailRequest
{
	@NonNull
	FlatrateTermId callOrderContractId;

	@Nullable
	I_C_OrderLine orderLine;

	@Nullable
	I_M_InOutLine shipmentLine;

	@Nullable
	I_C_InvoiceLine invoiceLine;

	@Builder
	public UpsertCallOrderDetailRequest(
			@NonNull final FlatrateTermId callOrderContractId,
			@Nullable final I_C_OrderLine orderLine,
			@Nullable final I_M_InOutLine shipmentLine,
			@Nullable final I_C_InvoiceLine invoiceLine)
	{
		final long nonNullSources = Stream.of(orderLine, shipmentLine, invoiceLine)
				.filter(Objects::nonNull)
				.count();

		if (nonNullSources == 0)
		{
			throw new AdempiereException("One of orderLine, shipmentLine, invoiceLine must be provided!");
		}

		if (nonNullSources > 1)
		{
			throw new AdempiereException("Only one of orderLine, shipmentLine, invoiceLine must be provided!");
		}

		this.callOrderContractId = callOrderContractId;
		this.orderLine = orderLine;
		this.shipmentLine = shipmentLine;
		this.invoiceLine = invoiceLine;
	}
}
