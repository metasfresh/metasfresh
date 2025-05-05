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
import de.metas.common.util.NumberUtils;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.IssuingToleranceSpec;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;
import java.math.BigDecimal;

@Value
public class OrderBOMLineQuantities
{
	@NonNull Quantity qtyRequired;
	@NonNull Quantity qtyRequiredBeforeClose;
	@NonNull Quantity qtyIssuedOrReceived;
	@NonNull Quantity qtyIssuedOrReceivedActual;
	@Nullable IssuingToleranceSpec issuingToleranceSpec;
	@NonNull Quantity qtyReject;
	@NonNull Quantity qtyScrap;

	@NonNull Quantity qtyUsageVariance;
	@NonNull Quantity qtyPost;
	@NonNull Quantity qtyReserved;

	@NonNull UomId uomId;

	boolean doNotRestrictQtyIssued;

	private static final AdMessageKey MSG_CannotIssueLessThan = AdMessageKey.of("OrderBOMLineQuantities.CannotIssueLessThan");
	private static final AdMessageKey MSG_CannotIssueMoreThan = AdMessageKey.of("OrderBOMLineQuantities.CannotIssueMoreThan");

	@Builder(toBuilder = true)
	private OrderBOMLineQuantities(
			@NonNull final Quantity qtyRequired,
			@Nullable final Quantity qtyRequiredBeforeClose,
			@Nullable final Quantity qtyIssuedOrReceived,
			@Nullable final Quantity qtyIssuedOrReceivedActual,
			@Nullable final IssuingToleranceSpec issuingToleranceSpec,
			@Nullable final Quantity qtyReject,
			@Nullable final Quantity qtyScrap,
			@Nullable final Quantity qtyUsageVariance,
			@Nullable final Quantity qtyPost,
			@Nullable final Quantity qtyReserved,
			final boolean doNotRestrictQtyIssued)
	{
		this.qtyRequired = qtyRequired;
		this.qtyRequiredBeforeClose = CoalesceUtil.coalesceNotNull(qtyRequiredBeforeClose, qtyRequired::toZero);
		this.qtyIssuedOrReceived = CoalesceUtil.coalesceNotNull(qtyIssuedOrReceived, qtyRequired::toZero);
		this.qtyIssuedOrReceivedActual = CoalesceUtil.coalesceNotNull(qtyIssuedOrReceivedActual, qtyRequired::toZero);
		this.issuingToleranceSpec = issuingToleranceSpec;
		this.qtyReject = CoalesceUtil.coalesceNotNull(qtyReject, qtyRequired::toZero);
		this.qtyScrap = CoalesceUtil.coalesceNotNull(qtyScrap, qtyRequired::toZero);
		this.qtyUsageVariance = CoalesceUtil.coalesceNotNull(qtyUsageVariance, qtyRequired::toZero);
		this.qtyPost = CoalesceUtil.coalesceNotNull(qtyPost, qtyRequired::toZero);
		this.qtyReserved = CoalesceUtil.coalesceNotNull(qtyReserved, qtyRequired::toZero);

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

		this.doNotRestrictQtyIssued = doNotRestrictQtyIssued;
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

	public OrderBOMLineQuantities reduce(@NonNull final OrderBOMLineQtyChangeRequest request)
	{
		final OrderBOMLineQuantitiesBuilder builder = toBuilder();

		if (!request.getQtyIssuedOrReceivedToAdd().isZero())
		{
			builder.qtyIssuedOrReceived(getQtyIssuedOrReceived().add(request.getQtyIssuedOrReceivedToAdd()));

			if (!request.isUsageVariance())
			{
				final Quantity qtyIssuedOrReceivedActualNew = getQtyIssuedOrReceivedActual().add(request.getQtyIssuedOrReceivedToAdd());
				assertQtyToIssueToleranceIsRespected_UpperBound(qtyIssuedOrReceivedActualNew, request.getRoundToScaleQuantity());

				builder.qtyIssuedOrReceivedActual(qtyIssuedOrReceivedActualNew);
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

	public void assertQtyToIssueToleranceIsRespected(@Nullable final Quantity roundToScale)
	{
		assertQtyToIssueToleranceIsRespected_LowerBound(qtyIssuedOrReceivedActual, roundToScale);
		assertQtyToIssueToleranceIsRespected_UpperBound(qtyIssuedOrReceivedActual, roundToScale);
	}

	private void assertQtyToIssueToleranceIsRespected_LowerBound(final Quantity qtyIssuedOrReceivedActual, @Nullable final Quantity roundToScale)
	{
		if (doNotRestrictQtyIssued || issuingToleranceSpec == null)
		{
			return;
		}

		final Quantity qtyIssuedOrReceivedActualMin = roundToQuantity(issuingToleranceSpec.subtractFrom(qtyRequired), roundToScale);

		if (qtyIssuedOrReceivedActual.compareTo(qtyIssuedOrReceivedActualMin) < 0)
		{
			final ITranslatableString qtyStr = TranslatableStrings.builder()
					.appendQty(qtyIssuedOrReceivedActualMin.toBigDecimal(), qtyIssuedOrReceivedActualMin.getUOMSymbol())
					.append(" (")
					.appendQty(qtyRequired.toBigDecimal(), qtyRequired.getUOMSymbol())
					.append(" - ")
					.append(issuingToleranceSpec.toTranslatableString())
					.append(")")
					.build();
			throw new AdempiereException(MSG_CannotIssueLessThan, qtyStr)
					.markAsUserValidationError();
		}
	}

	private void assertQtyToIssueToleranceIsRespected_UpperBound(final Quantity qtyIssuedOrReceivedActual, @Nullable final Quantity roundToScale)
	{
		if (doNotRestrictQtyIssued || issuingToleranceSpec == null)
		{
			return;
		}

		final Quantity qtyIssuedOrReceivedActualMax = roundToQuantity(issuingToleranceSpec.addTo(qtyRequired), roundToScale);

		if (qtyIssuedOrReceivedActual.compareTo(qtyIssuedOrReceivedActualMax) > 0)
		{
			final ITranslatableString qtyStr = TranslatableStrings.builder()
					.appendQty(qtyIssuedOrReceivedActualMax.toBigDecimal(), qtyIssuedOrReceivedActualMax.getUOMSymbol())
					.append(" (")
					.appendQty(qtyRequired.toBigDecimal(), qtyRequired.getUOMSymbol())
					.append(" + ")
					.append(issuingToleranceSpec.toTranslatableString())
					.append(")")
					.build();
			throw new AdempiereException(MSG_CannotIssueMoreThan, qtyStr)
					.markAsUserValidationError();
		}
	}

	public OrderBOMLineQuantities convertQuantities(@NonNull final UnaryOperator<Quantity> converter)
	{
		return toBuilder()
				.qtyRequired(converter.apply(qtyRequired))
				.qtyRequiredBeforeClose(converter.apply(qtyRequiredBeforeClose))
				.qtyIssuedOrReceived(converter.apply(qtyIssuedOrReceived))
				.qtyIssuedOrReceivedActual(converter.apply(qtyIssuedOrReceivedActual))
				.qtyReject(converter.apply(qtyReject))
				.qtyScrap(converter.apply(qtyScrap))
				.qtyUsageVariance(converter.apply(qtyUsageVariance))
				.qtyPost(converter.apply(qtyPost))
				.qtyReserved(converter.apply(qtyReserved))
				.build();
	}

	@NonNull
	private Quantity roundToQuantity(@NonNull final Quantity qty, @Nullable final Quantity roundingQty)
	{
		if (roundingQty == null)
		{
			return qty;
		}

		if (!qty.getUomId().equals(roundingQty.getUomId()))
		{
			throw new AdempiereException("UOM doesn't match!")
					.appendParametersToMessage()
					.setParameter("qty", qty)
					.setParameter("roundingQty", roundingQty);
		}

		final BigDecimal roundedQtyBD = NumberUtils.roundToBigDecimal(qty.toBigDecimal(), roundingQty.toBigDecimal());

		return Quantitys.of(roundedQtyBD, qty.getUomId());
	}
}
