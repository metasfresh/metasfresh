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
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

@Value
public class PPOrderQuantities
{
	@NonNull
	Quantity qtyRequiredToProduce;
	@NonNull
	Quantity qtyRequiredToProduceBeforeClose;

	@NonNull
	Quantity qtyReceived;

	@NonNull
	Quantity qtyScrapped;

	@NonNull
	Quantity qtyRejected;

	@NonNull
	Quantity qtyReserved;

	@NonNull
	UomId uomId;

	@Builder(toBuilder = true)
	private PPOrderQuantities(
			@NonNull final Quantity qtyRequiredToProduce,
			@Nullable final Quantity qtyRequiredToProduceBeforeClose,
			@Nullable final Quantity qtyReceived,
			@Nullable final Quantity qtyScrapped,
			@Nullable final Quantity qtyRejected,
			@Nullable final Quantity qtyReserved)
	{
		this.qtyRequiredToProduce = qtyRequiredToProduce;
		this.qtyRequiredToProduceBeforeClose = CoalesceUtil.coalesce(qtyRequiredToProduceBeforeClose, qtyRequiredToProduce::toZero);
		this.qtyReceived = CoalesceUtil.coalesce(qtyReceived, qtyRequiredToProduce::toZero);
		this.qtyScrapped = CoalesceUtil.coalesce(qtyScrapped, qtyRequiredToProduce::toZero);
		this.qtyRejected = CoalesceUtil.coalesce(qtyRejected, qtyRequiredToProduce::toZero);
		this.qtyReserved = CoalesceUtil.coalesce(qtyReserved, qtyRequiredToProduce::toZero);

		this.uomId = Quantity.getCommonUomIdOfAll(
				this.qtyRequiredToProduce,
				this.qtyRequiredToProduceBeforeClose,
				this.qtyReceived,
				this.qtyScrapped,
				this.qtyRejected,
				this.qtyReserved
		);
	}

	public static PPOrderQuantities ofQtyRequiredToProduce(@NonNull final Quantity qtyRequiredToProduce)
	{
		return builder().qtyRequiredToProduce(qtyRequiredToProduce).build();
	}

	public Quantity getQtyRemainingToProduce()
	{
		return getQtyRequiredToProduce()
				.subtract(getQtyReceived())
				.subtract(getQtyScrapped());
	}

	public PPOrderQuantities reduce(@NonNull final OrderQtyChangeRequest request)
	{
		return toBuilder()
				.qtyReceived(getQtyReceived().add(request.getQtyReceivedToAdd()))
				.qtyScrapped(getQtyScrapped().add(request.getQtyScrappedToAdd()))
				.qtyRejected(getQtyRejected().add(request.getQtyRejectedToAdd()))
				.qtyReserved(getQtyReserved().subtract(request.getQtyReceivedToAdd()))
				.build();
	}

	public PPOrderQuantities close()
	{
		return toBuilder()
				.qtyRequiredToProduce(getQtyReceived())
				.qtyRequiredToProduceBeforeClose(getQtyRequiredToProduce())
				.build();
	}

	public PPOrderQuantities unclose()
	{
		return toBuilder()
				.qtyRequiredToProduce(getQtyRequiredToProduceBeforeClose())
				.qtyRequiredToProduceBeforeClose(getQtyRequiredToProduceBeforeClose().toZero())
				.build();
	}

	public PPOrderQuantities voidQtys()
	{
		return toBuilder()
				.qtyRequiredToProduce(getQtyRequiredToProduce().toZero())
				.build();
	}

	public PPOrderQuantities convertQuantities(@NonNull final UnaryOperator<Quantity> converter)
	{
		return toBuilder()
				.qtyRequiredToProduce(converter.apply(getQtyRequiredToProduce()))
				.qtyRequiredToProduceBeforeClose(converter.apply(getQtyRequiredToProduceBeforeClose()))
				.qtyReceived(converter.apply(getQtyReceived()))
				.qtyScrapped(converter.apply(getQtyScrapped()))
				.qtyRejected(converter.apply(getQtyRejected()))
				.qtyReserved(converter.apply(getQtyReserved()))
				.build();
	}

	public boolean isSomethingReported()
	{
		return getQtyReceived().signum() != 0
				|| getQtyScrapped().signum() != 0
				|| getQtyRejected().signum() != 0;
	}

	public PPOrderQuantities withQtyRequiredToProduce(@NonNull final Quantity qtyRequiredToProduce)
	{
		return toBuilder().qtyRequiredToProduce(qtyRequiredToProduce).build();
	}

}
