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

package de.metas.handlingunits.picking.plan.model;

import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * The picking line describes for the picker:
 * <li>which product to pick</li>
 * <li>how much to pick</li>
 * <li>from where to pick it</li>
 */
@Value
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
	@With
	@Nullable PickFromHU pickFromHU;
	@Nullable PickFromPickingOrder pickFromPickingOrder;
	@Nullable IssueToBOMLine issueToBOMLine;

	@Builder(toBuilder = true)
	private PickingPlanLine(
			@NonNull final PickingPlanLineType type,
			@NonNull final SourceDocumentInfo sourceDocumentInfo,
			//
			@NonNull final ProductId productId,
			@NonNull final Quantity qty,
			//
			@Nullable final PickFromHU pickFromHU,
			@Nullable final PickFromPickingOrder pickFromPickingOrder,
			@Nullable final IssueToBOMLine issueToBOMLine)
	{
		this.type = type;
		this.sourceDocumentInfo = sourceDocumentInfo;
		this.productId = productId;
		this.qty = qty;

		switch (type)
		{
			case PICK_FROM_HU:
			{
				this.pickFromHU = pickFromHU;
				this.pickFromPickingOrder = null;
				this.issueToBOMLine = null;
				break;
			}
			case PICK_FROM_PICKING_ORDER:
			{
				this.pickFromHU = null;
				this.pickFromPickingOrder = pickFromPickingOrder;
				this.issueToBOMLine = null;
				break;
			}
			case ISSUE_COMPONENTS_TO_PICKING_ORDER:
			{
				this.pickFromHU = null;
				this.pickFromPickingOrder = null;
				this.issueToBOMLine = issueToBOMLine;
				break;
			}
			case UNALLOCABLE:
			{
				this.pickFromHU = null;
				this.pickFromPickingOrder = null;
				this.issueToBOMLine = null;
				break;
			}
			default:
			{
				throw new AdempiereException("Unknown type: " + type);
			}
		}
	}

	public PickingPlanLine withQty(@NonNull final Quantity qty)
	{
		return Objects.equals(this.qty, qty)
				? this
				: toBuilder().qty(qty).build();
	}
}

