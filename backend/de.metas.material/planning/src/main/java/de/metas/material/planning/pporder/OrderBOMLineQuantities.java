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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;

@Value
public class OrderBOMLineQuantities
{
	@NonNull Quantity qtyRequired;
	@NonNull Quantity qtyRequiredBeforeClose;
	@NonNull Quantity qtyIssuedOrReceived;
	@NonNull Quantity qtyIssuedOrReceivedActual;
	@NonNull Quantity qtyReject;
	@NonNull Quantity qtyScrap;

	@NonNull Quantity qtyUsageVariance;
	@NonNull Quantity qtyPost;
	@NonNull Quantity qtyReserved;

	@NonNull UomId uomId;

	@Builder(toBuilder = true)
	private OrderBOMLineQuantities(
			@NonNull final Quantity qtyRequired,
			@Nullable final Quantity qtyRequiredBeforeClose,
			@Nullable final Quantity qtyIssuedOrReceived,
			@Nullable final Quantity qtyIssuedOrReceivedActual,
			@Nullable final Quantity qtyReject,
			@Nullable final Quantity qtyScrap,
			@Nullable final Quantity qtyUsageVariance,
			@Nullable final Quantity qtyPost,
			@Nullable final Quantity qtyReserved)
	{
		this.qtyRequired = qtyRequired;
		this.qtyRequiredBeforeClose = CoalesceUtil.coalesce(qtyRequiredBeforeClose, qtyRequired::toZero);
		this.qtyIssuedOrReceived = CoalesceUtil.coalesce(qtyIssuedOrReceived, qtyRequired::toZero);
		this.qtyIssuedOrReceivedActual = CoalesceUtil.coalesce(qtyIssuedOrReceivedActual, qtyRequired::toZero);
		this.qtyReject = CoalesceUtil.coalesce(qtyReject, qtyRequired::toZero);
		this.qtyScrap = CoalesceUtil.coalesce(qtyScrap, qtyRequired::toZero);
		this.qtyUsageVariance = CoalesceUtil.coalesce(qtyUsageVariance, qtyRequired::toZero);
		this.qtyPost = CoalesceUtil.coalesce(qtyPost, qtyRequired::toZero);
		this.qtyReserved = CoalesceUtil.coalesce(qtyReserved, qtyRequired::toZero);

		this.uomId = Quantity.getCommonUomIdOfAll(
				this.qtyRequired,
				this.qtyRequiredBeforeClose,
				this.qtyIssuedOrReceived,
				this.qtyIssuedOrReceivedActual,
				this.qtyReject,
				this.qtyScrap,
				this.qtyUsageVariance,
				this.qtyPost,
				this.qtyReserved);
	}

	public static OrderBOMLineQuantities zero(@NonNull final I_C_UOM uom)
	{
		return ofQtyRequired(Quantity.zero(uom));
	}

	public static OrderBOMLineQuantities ofQtyRequired(@NonNull final Quantity qtyRequired)
	{
		return builder().qtyRequired(qtyRequired).build();
	}

	private void assertValidUOM(@NonNull final Quantity qty)
	{
		if (!UomId.equals(qty.getUomId(), uomId))
		{
			throw new AdempiereException("Quantity " + qty + " shall have uom=" + uomId);
		}
	}

	public OrderBOMLineQuantities withQtyRequired(@NonNull final Quantity qtyRequired)
	{
		assertValidUOM(qtyRequired);
		return toBuilder().qtyRequired(qtyRequired).build();
	}

	public OrderBOMLineQuantities close()
	{
		return toBuilder()
				.qtyRequired(getQtyIssuedOrReceived())
				.qtyRequiredBeforeClose(getQtyRequired())
				.build();
	}

	public OrderBOMLineQuantities unclose()
	{
		return toBuilder()
				.qtyRequired(getQtyRequiredBeforeClose())
				.qtyRequiredBeforeClose(getQtyRequiredBeforeClose().toZero())
				.build();
	}

	public Quantity getRemainingQtyToIssue()
	{
		return getProjectedRemainingQtyToIssue(getQtyRequired());
	}

	public Quantity getProjectedRemainingQtyToIssue(@NonNull final Quantity qtyToIssueTarget)
	{
		return qtyToIssueTarget.subtract(getQtyIssuedOrReceived());
	}

	public boolean isSomethingReported()
	{
		return qtyIssuedOrReceived.signum() != 0
				|| qtyScrap.signum() != 0
				|| qtyReject.signum() != 0;
	}

	public OrderBOMLineQuantities withAdditionalQtyIssuedOrReceived(@NonNull final Quantity qtyToAdd)
	{
		if (qtyToAdd.signum() == 0)
		{
			return this;
		}

		return toBuilder()
				.qtyIssuedOrReceived(getQtyIssuedOrReceived().add(qtyToAdd))
				.qtyIssuedOrReceivedActual(getQtyIssuedOrReceivedActual().add(qtyToAdd))
				.build();
	}

	public OrderBOMLineQuantities withAdditionalQtyUsageVariance(@NonNull final Quantity qtyToAdd)
	{
		if (qtyToAdd.signum() == 0)
		{
			return this;
		}

		return toBuilder()
				.qtyIssuedOrReceived(getQtyIssuedOrReceived().add(qtyToAdd))
				.qtyUsageVariance(getQtyUsageVariance().add(qtyToAdd))
				.build();
	}

	public OrderBOMLineQuantities reduce(@NonNull final OrderBOMLineQtyChangeRequest request)
	{
		final OrderBOMLineQuantitiesBuilder builder = toBuilder();

		if (!request.getQtyIssuedOrReceivedToAdd().isZero())
		{
			builder.qtyIssuedOrReceived(getQtyIssuedOrReceived().add(request.getQtyIssuedOrReceivedToAdd()));

			if (!request.isUsageVariance())
			{
				builder.qtyIssuedOrReceivedActual(getQtyIssuedOrReceivedActual().add(request.getQtyIssuedOrReceivedToAdd()));
			}
		}

		if (request.getQtyScrappedToAdd() != null && request.getQtyScrappedToAdd().signum() != 0)
		{
			builder.qtyScrap(getQtyScrap().add(request.getQtyScrappedToAdd()));
		}

		if (request.getQtyRejectedToAdd() != null && request.getQtyRejectedToAdd().signum() != 0)
		{
			builder.qtyReject(getQtyReject().add(request.getQtyRejectedToAdd()));
		}

		return builder.build();
	}

	public static Quantity adjustCoProductQty(final Quantity qty)
	{
		return qty.negate();
	}

	/**
	 * @return qtyRequired negated, so we assume a positive value will be returned
	 */
	public Quantity getQtyRequired_NegateBecauseIsCOProduct()
	{
		return adjustCoProductQty(getQtyRequired());
	}

	/**
	 * @return co product already received (and processed) quantity, so we assume a positive value will be returned
	 */
	public Quantity getQtyIssuedOrReceived_NegateBecauseIsCOProduct()
	{
		return adjustCoProductQty(getQtyIssuedOrReceived());
	}

}
