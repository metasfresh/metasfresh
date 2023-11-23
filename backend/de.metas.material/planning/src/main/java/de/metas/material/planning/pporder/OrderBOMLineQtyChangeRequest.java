package de.metas.material.planning.pporder;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.function.UnaryOperator;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2018 metas GmbH
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
@Builder(toBuilder = true)
public class OrderBOMLineQtyChangeRequest
{
	@NonNull
	PPOrderBOMLineId orderBOMLineId;

	boolean usageVariance;

	@NonNull
	Quantity qtyIssuedOrReceivedToAdd;
	@Nullable
	Quantity qtyScrappedToAdd;
	@Nullable
	Quantity qtyRejectedToAdd;
	@Nullable
	Quantity roundToScaleQuantity;

	@NonNull
	@Builder.Default
	AttributeSetInstanceId asiId = AttributeSetInstanceId.NONE;

	@NonNull
	ZonedDateTime date;

	public OrderBOMLineQtyChangeRequest convertQuantities(@NonNull final UnaryOperator<Quantity> converter)
	{
		final UnaryOperator<Quantity> convertNullable = qty -> qty != null ? converter.apply(qty) : null;

		return toBuilder()
				.qtyIssuedOrReceivedToAdd(convertNullable.apply(getQtyIssuedOrReceivedToAdd()))
				.qtyScrappedToAdd(convertNullable.apply(getQtyScrappedToAdd()))
				.qtyRejectedToAdd(convertNullable.apply(getQtyRejectedToAdd()))
				.roundToScaleQuantity(convertNullable.apply(getRoundToScaleQuantity()))
				.build();
	}

}
