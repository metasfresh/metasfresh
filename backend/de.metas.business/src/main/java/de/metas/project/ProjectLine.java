/*
 * #%L
 * de.metas.business
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

package de.metas.project;

import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class ProjectLine
{
	@NonNull
	private final ProjectAndLineId id;

	@NonNull
	private final ProductId productId;

	@NonNull
	private final Quantity plannedQty;

	@NonNull
	private Quantity committedQty;

	@Nullable
	private final String description;

	@Nullable
	private final OrderAndLineId salesOrderLineId;

	public Quantity getPlannedQtyButNotCommitted()
	{
		return getPlannedQty().subtract(getCommittedQty());
	}

	public void addCommittedQty(@NonNull final Quantity committedQtyToAdd)
	{
		this.committedQty = this.committedQty.add(committedQtyToAdd);
	}
}
