/*
 * #%L
 * de.metas.adempiere.libero.libero
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

package org.eevolution.api;

import de.metas.common.util.CoalesceUtil;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

@Value
public class PPCostCollectorQuantities
{
	@NonNull
	Quantity movementQty;

	@NonNull
	Quantity scrappedQty;

	@NonNull
	Quantity rejectedQty;

	@NonNull
	UomId uomId;

	@Builder(toBuilder = true)
	private PPCostCollectorQuantities(
			@NonNull final Quantity movementQty,
			@Nullable final Quantity scrappedQty,
			@Nullable final Quantity rejectedQty)
	{
		this.movementQty = movementQty;
		this.scrappedQty = CoalesceUtil.coalesce(scrappedQty, movementQty::toZero);
		this.rejectedQty = CoalesceUtil.coalesce(rejectedQty, movementQty::toZero);
		this.uomId = Quantity.getCommonUomIdOfAll(
				this.movementQty,
				this.scrappedQty,
				this.rejectedQty
		);
	}

	public static PPCostCollectorQuantities ofMovementQty(@NonNull final Quantity movementQty)
	{
		return builder().movementQty(movementQty).build();
	}

	public PPCostCollectorQuantities negate()
	{
		return toBuilder()
				.movementQty(getMovementQty().negate())
				.scrappedQty(getScrappedQty().negate())
				.rejectedQty(getRejectedQty().negate())
				.build();
	}
}
