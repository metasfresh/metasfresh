/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.plan;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * The picking line describes for the picker:
 * <li>which product to pick</li>
 * <li>how much to pick</li>
 * <li>from where to pick it</li>
 */
@Value
@Builder(toBuilder = true)
public class PickingPlanLine
{
	@NonNull PickingPlanLineType type;
	@NonNull SourceDocumentInfo sourceDocumentInfo;

	//
	// What to pick?
	@NonNull ProductId productId;
	@NonNull Quantity qty;

	//
	// From where to pick?
	// ... or where to issue?
	@Nullable PickFromHU pickFromHU;
	@Nullable PickFromPickingOrder pickFromPickingOrder;
	@Nullable IssueToBOMLine issueToBOMLine;

	public PickingPlanLine withQty(@NonNull final Quantity qty)
	{
		return Objects.equals(this.qty, qty)
				? this
				: toBuilder().qty(qty).build();
	}

	public PickingPlanLine withTypeAndQty(@NonNull PickingPlanLineType type, @NonNull final Quantity qty)
	{
		return this.type.equals(type) && Objects.equals(this.qty, qty)
				? this
				: toBuilder().type(type).qty(qty).build();
	}

}

