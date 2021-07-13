/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.material.planning.pporder;

import de.metas.common.util.CoalesceUtil;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.function.UnaryOperator;

@Value
public class OrderQtyChangeRequest
{
	@NonNull PPOrderId ppOrderId;
	@NonNull Quantity qtyReceivedToAdd;
	@NonNull Quantity qtyScrappedToAdd;
	@NonNull Quantity qtyRejectedToAdd;
	@NonNull ZonedDateTime date;

	@Builder(toBuilder = true)
	private OrderQtyChangeRequest(
			@NonNull final PPOrderId ppOrderId,
			@Nullable final Quantity qtyReceivedToAdd,
			@Nullable final Quantity qtyScrappedToAdd,
			@Nullable final Quantity qtyRejectedToAdd,
			@NonNull final ZonedDateTime date)
	{
		final Quantity firstNonNullQty = CoalesceUtil.coalesce(qtyReceivedToAdd, qtyScrappedToAdd, qtyRejectedToAdd);
		if (firstNonNullQty == null)
		{
			throw new AdempiereException("At least one of the qtys shall be non-null");
		}

		this.ppOrderId = ppOrderId;
		this.qtyReceivedToAdd = qtyReceivedToAdd != null ? qtyReceivedToAdd : firstNonNullQty.toZero();
		this.qtyScrappedToAdd = qtyScrappedToAdd != null ? qtyScrappedToAdd : firstNonNullQty.toZero();
		this.qtyRejectedToAdd = qtyRejectedToAdd != null ? qtyRejectedToAdd : firstNonNullQty.toZero();
		this.date = date;
	}

	public OrderQtyChangeRequest convertQuantities(@NonNull final UnaryOperator<Quantity> converter)
	{
		return toBuilder()
				.qtyReceivedToAdd(converter.apply(getQtyReceivedToAdd()))
				.qtyScrappedToAdd(converter.apply(getQtyScrappedToAdd()))
				.qtyRejectedToAdd(converter.apply(getQtyRejectedToAdd()))
				.build();
	}
}
