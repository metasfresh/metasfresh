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

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

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

	/** Precision used to store internal quantities */
	/* package */static final int INTERNAL_PRECISION = 12;

	/** Total Quantity (high presicion, i.e. the precision that we got on input) */
	private BigDecimal qtyTotal = BigDecimal.ZERO;
	/** Quantity with issues (high precision) */
	private BigDecimal qtyWithIssues = BigDecimal.ZERO;
	/**
	 * Quality Notices.
	 *
	 * NOTE: always return a copy of this value, because qualityNotices is not immutable
	 */
	private QualityNoticesCollection qualityNotices = new QualityNoticesCollection();

	/** @return a copy of this object */
	public ReceiptQty copy()
	{
		final ReceiptQty copy = new ReceiptQty();
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

	/**
	 *
	 * @param qty
	 * @param qualityDiscountPercent percent between 0...100
	 */
	public void addQtyAndQualityDiscountPercent(final BigDecimal qty, final BigDecimal qualityDiscountPercent)
	{
		final BigDecimal qtyWithIssuesToAdd = qty.multiply(qualityDiscountPercent)
				.divide(Env.ONEHUNDRED, INTERNAL_PRECISION, QtyWithIssues_RoundingMode);

		qtyTotal = qtyTotal.add(qty);
		qtyWithIssues = qtyWithIssues.add(qtyWithIssuesToAdd);
	}

	public void addQtyAndQtyWithIssues(final BigDecimal qtyToAdd, final BigDecimal qtyWithIssuesToAdd)
	{
		qtyTotal = qtyTotal.add(qtyToAdd);
		qtyWithIssues = qtyWithIssues.add(qtyWithIssuesToAdd);
	}

	public void add(@NonNull final ReceiptQty qtyAndQualityToAdd)
	{
		final BigDecimal qtyTotalToAdd = qtyAndQualityToAdd.getQtyTotal();
		final BigDecimal qtyWithIssuesToAdd = qtyAndQualityToAdd.getQtyWithIssuesExact();
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
	public BigDecimal getQtyTotal()
	{
		return qtyTotal;
	}

	/**
	 * @return true if total quantity is zero
	 */
	public boolean isZero()
	{
		return qtyTotal.signum() == 0 && qtyWithIssues.signum() == 0;
	}

	private BigDecimal getQtyTotal(final int qtyPrecision)
	{
		BigDecimal qtyTotal = getQtyTotal();
		if (qtyTotal.signum() == 0)
		{
			return BigDecimal.ZERO;
		}
		qtyTotal = qtyTotal.setScale(qtyPrecision, QtyTotal_RoundingMode);
		return qtyTotal;
	}

	/**
	 * @return weighted average quality discount percent (between 0..100)
	 */
	public BigDecimal getQualityDiscountPercent()
	{
		final BigDecimal qtyTotalExact = getQtyTotal();
		final BigDecimal qtyWithIssuesExact = getQtyWithIssuesExact();

		if (qtyTotalExact.signum() == 0)
		{
			if (qtyWithIssuesExact.signum() == 0)
			{
				return BigDecimal.ZERO;
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
				return BigDecimal.ZERO;
			}
		}

		final BigDecimal qualityDiscountPercent = Env.ONEHUNDRED
				.multiply(qtyWithIssuesExact)
				.divide(qtyTotalExact, QualityDiscountPercent_Precision, QualityDiscountPercent_RoundingMode);

		return qualityDiscountPercent;
	}

	/**
	 * @return quantity with issues; i.e. QtyTotal * Quality Discount Percent%
	 */
	public BigDecimal getQtyWithIssues(final int qtyPrecision)
	{
		return getQtyWithIssuesExact()
				.setScale(qtyPrecision, QtyWithIssues_RoundingMode);
	}

	/**
	 * @return quantity with issues (precise, high scale value)
	 */
	public BigDecimal getQtyWithIssuesExact()
	{
		return qtyWithIssues;
	}

	/**
	 * @return quantity without issues; i.e. QtyTotal - QtyWithIssues
	 */
	public BigDecimal getQtyWithoutIssues(final int qtyPrecision)
	{
		final BigDecimal qtyTotal = getQtyTotal(qtyPrecision);
		final BigDecimal qtyWithIssues = getQtyWithIssues(qtyPrecision);
		final BigDecimal qtyWithoutIssues = qtyTotal.subtract(qtyWithIssues);
		return qtyWithoutIssues;
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
