package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Quantity and Quality, i.e.
 * <ul>
 * <li>total quantity: {@link #getQtyTotal()}
 * <li>quantity with issues: {@link #getQtyWithIssues(int)}
 * <li>quantity without issues: {@link #getQtyWithoutIssues(int)}
 * <li>quality discount percent: {@link #getQualityDiscountPercent()}
 * <li>quality notices: {@link #getQualityNotices()}
 * </ul>
 */
public final class ReceiptQty
{
	public static final int QualityDiscountPercent_Precision = 2;
	public static final RoundingMode QualityDiscountPercent_RoundingMode = RoundingMode.HALF_DOWN;
	public static final RoundingMode QtyTotal_RoundingMode = RoundingMode.HALF_UP;
	public static final RoundingMode QtyWithIssues_RoundingMode = RoundingMode.HALF_DOWN;

	private static final transient Logger logger = LogManager.getLogger(ReceiptQty.class);

	public static ReceiptQty newWithoutCatchWeight(@NonNull final ProductId productId)
	{
		return new ReceiptQty(productId, null/* catchUomId */);
	}

	public static ReceiptQty newWithCatchWeight(@NonNull final ProductId productId, @NonNull final UomId catchUomId)
	{
		return new ReceiptQty(productId, catchUomId);
	}

	/** Precision used to store internal quantities */
	/* package */static final int INTERNAL_PRECISION = 12;

	private final ProductId productId;

	/** might be null, if no catch-quantities are involved */
	private final UomId catchUomId;

	/** Total Quantity (high precision, i.e. the precision that we got on input) */
	private StockQtyAndUOMQty qtyTotal;

	/** Quantity with issues (high precision) */
	private StockQtyAndUOMQty qtyWithIssues;

	/**
	 * Quality Notices.
	 *
	 * NOTE: always return a copy of this value, because qualityNotices is not immutable
	 */
	private QualityNoticesCollection qualityNotices = new QualityNoticesCollection();

	private ReceiptQty(
			@NonNull final ProductId productId,
			@Nullable final UomId catchUomId)
	{
		this.productId = productId;
		this.catchUomId = catchUomId;
		this.qtyTotal = StockQtyAndUOMQtys.createZero(productId, catchUomId);
		this.qtyWithIssues = StockQtyAndUOMQtys.createZero(productId, catchUomId);
	}

	/** @return a copy of this object */
	public ReceiptQty copy()
	{
		final ReceiptQty copy = new ReceiptQty(productId, catchUomId);
		copy.copyFrom(this);
		return copy;
	}

	protected void copyFrom(@NonNull final ReceiptQty from)
	{
		this.qtyTotal = from.qtyTotal;
		this.qtyWithIssues = from.qtyWithIssues;
		this.qualityNotices = from.qualityNotices.copy();
	}

	@Override
	public ReceiptQty clone()
	{
		return copy();
	}

	public void addQtyAndQualityDiscountPercent(
			@NonNull final Quantity qty,
			@NonNull final Percent qualityDiscountPercent)
	{
		final StockQtyAndUOMQty qtyTotalToAdd = StockQtyAndUOMQtys.createConvert(
				qty,
				productId,
				(Quantity)null/* uomQty */
		);
		qtyTotal = qtyTotal.add(qtyTotalToAdd);

		final BigDecimal stockQtyWithIssuesToAdd = qty.toBigDecimal()
				.multiply(qualityDiscountPercent.toBigDecimal())
				.divide(Env.ONEHUNDRED, INTERNAL_PRECISION, QtyWithIssues_RoundingMode);

		final StockQtyAndUOMQty qtyWithIssuesToAdd = StockQtyAndUOMQtys.createConvert(
				Quantitys.of(stockQtyWithIssuesToAdd, qty.getUomId()),
				productId,
				(Quantity)null/* uomQty */
		);
		qtyWithIssues = qtyWithIssues.add(qtyWithIssuesToAdd);
	}

	/**
	 * @param qty its UOMQty may not be null and contains the catch-weight
	 * @param qualityDiscountPercent
	 */
	public void addQtyAndQualityDiscountPercent(
			@NonNull final StockQtyAndUOMQty qty,
			@NonNull final Percent qualityDiscountPercent)
	{
		qtyTotal = qtyTotal.add(qty);

		final BigDecimal stockQtyWithIssuesToAdd = qty.getStockQty().toBigDecimal()
				.multiply(qualityDiscountPercent.toBigDecimal())
				.divide(Env.ONEHUNDRED, INTERNAL_PRECISION, QtyWithIssues_RoundingMode);

		final BigDecimal uomQtyWithIssuesToAdd = qty.getUOMQtyNotNull().toBigDecimal()
				.multiply(qualityDiscountPercent.toBigDecimal())
				.divide(Env.ONEHUNDRED, INTERNAL_PRECISION, QtyWithIssues_RoundingMode);

		final StockQtyAndUOMQty qtyWithIssuesToAdd = StockQtyAndUOMQtys.create(
				stockQtyWithIssuesToAdd, qty.getProductId(),
				uomQtyWithIssuesToAdd, qty.getUOMQtyNotNull().getUomId());

		qtyWithIssues = qtyWithIssues.add(qtyWithIssuesToAdd);
	}

	public void addQtyAndQtyWithIssues(
			@NonNull final StockQtyAndUOMQty qtyToAdd,
			@NonNull final StockQtyAndUOMQty qtyWithIssuesToAdd)
	{
		qtyTotal = qtyTotal.add(qtyToAdd);
		qtyWithIssues = qtyWithIssues.add(qtyWithIssuesToAdd);
	}

	public void add(@NonNull final ReceiptQty qtyAndQualityToAdd)
	{
		final StockQtyAndUOMQty qtyTotalToAdd = qtyAndQualityToAdd.getQtyTotal();
		final StockQtyAndUOMQty qtyWithIssuesToAdd = qtyAndQualityToAdd.getQtyWithIssuesExact();
		final QualityNoticesCollection qualityNoticesToAdd = qtyAndQualityToAdd.getQualityNotices();

		addQtyAndQtyWithIssues(qtyTotalToAdd, qtyWithIssuesToAdd);
		addQualityNotices(qualityNoticesToAdd);
	}

	public void subtractQtys(final ReceiptQty qtysToRemove)
	{
		add(qtysToRemove.negateQtys());
	}

	public ReceiptQty negateQtys()
	{
		if (isZero())
		{
			return this;
		}

		final ReceiptQty thisNegated = copy();
		thisNegated.qtyTotal = thisNegated.qtyTotal.negate();
		thisNegated.qtyWithIssues = thisNegated.qtyWithIssues.negate();
		return thisNegated;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " ["
				+ "qtyTotal=" + qtyTotal
				+ ", qtyWithIssues=" + qtyWithIssues
				+ ", notices=" + qualityNotices
				+ "]";
	}

	/**
	 * @return total quantity (with and without issues)
	 */
	public StockQtyAndUOMQty getQtyTotal()
	{
		return qtyTotal;
	}

	public boolean isZero()
	{
		return qtyTotal.signum() == 0 && qtyWithIssues.signum() == 0;
	}

	/**
	 * @return weighted average quality discount percent, based on the UOM-quantities.
	 *         I.e. in case of catch weight, the percentage is based on the actually weighed stuff.
	 */
	public Percent getQualityDiscountPercent()
	{
		final StockQtyAndUOMQty qtyTotalExact = getQtyTotal();
		final StockQtyAndUOMQty qtyWithIssuesExact = getQtyWithIssuesExact();

		if (qtyTotalExact.signum() == 0)
		{
			if (qtyWithIssuesExact.signum() == 0)
			{
				return Percent.ZERO;
			}
			// Case: qtyTotal is ZERO but we have qtyWithIssues.
			// => this could be an issue
			else
			{
				final AdempiereException ex = new AdempiereException("We are asked to calculate QualityDiscountPercent when QtyTotal=0 and QtyWithIssues>0."
						+ "\nThis could be an error but we are returning ZERO by now."
						+ "\nQtyAndQuality: " + this);
				// just log it for now
				logger.warn(ex.getLocalizedMessage(), ex);
				return Percent.ZERO;
			}
		}

		final BigDecimal qtyWithIssuesExactBD;
		final BigDecimal qtyTotalExactBD;
		if (catchUomId != null)
		{
			qtyWithIssuesExactBD = qtyWithIssuesExact.getUOMQtyNotNull().toBigDecimal();
			qtyTotalExactBD = qtyTotalExact.getUOMQtyNotNull().toBigDecimal();
		}
		else
		{
			qtyWithIssuesExactBD = qtyWithIssuesExact.getStockQty().toBigDecimal();
			qtyTotalExactBD = qtyTotalExact.getStockQty().toBigDecimal();
		}

		if (qtyWithIssuesExactBD.signum() == 0 || qtyTotalExact.signum() == 0)
		{
			return Percent.ZERO;
		}

		final BigDecimal qualityDiscountPercent = Env.ONEHUNDRED
				.multiply(qtyWithIssuesExactBD)
				.divide(qtyTotalExactBD,
						QualityDiscountPercent_Precision,
						QualityDiscountPercent_RoundingMode);
		return Percent.of(qualityDiscountPercent);
	}

	/**
	 * @return quantity with issues (precise, high scale value)
	 */
	public StockQtyAndUOMQty getQtyWithIssuesExact()
	{
		return qtyWithIssues;
	}

	/**
	 * @return quantity without issues; i.e. QtyTotal - QtyWithIssues
	 */
	public StockQtyAndUOMQty getQtyWithoutIssues()
	{
		return qtyTotal.subtract(qtyWithIssues);
	}

	public void addQualityNotices(final QualityNoticesCollection qualityNoticesToAdd)
	{
		this.qualityNotices.addQualityNotices(qualityNoticesToAdd);
	}

	/**
	 * @return quality notices; never null
	 */
	public QualityNoticesCollection getQualityNotices()
	{
		// always return a copy, because qualityNotices is not immutable
		return qualityNotices.copy();
	}
}
