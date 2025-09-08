/*
 * #%L
 * de.metas.purchasecandidate.base
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

package de.metas.purchasecandidate;

import de.metas.order.OrderLineId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class DeletePurchaseCandidateQuery
{
	boolean onlySimulated;

	@Nullable
	OrderLineId salesOrderLineId;

	@Builder
	public DeletePurchaseCandidateQuery(final boolean onlySimulated, @Nullable final OrderLineId salesOrderLineId)
	{
		if (!onlySimulated && salesOrderLineId == null)
		{
			throw new AdempiereException("Deleting all records is not allowed!");
		}

		this.onlySimulated = onlySimulated;
		this.salesOrderLineId = salesOrderLineId;
	}
}
