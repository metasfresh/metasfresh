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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

public final class MutableQtyAndQuality implements IQtyAndQuality
{
	public static final int QualityDiscountPercent_Precision = 2;
	public static final RoundingMode QualityDiscountPercent_RoundingMode = RoundingMode.HALF_DOWN;
	public static final RoundingMode QtyTotal_RoundingMode = RoundingMode.HALF_UP;
	public static final RoundingMode QtyWithIssues_RoundingMode = RoundingMode.HALF_DOWN;

	private static final transient Logger logger = LogManager.getLogger(MutableQtyAndQuality.class);

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

	public MutableQtyAndQuality()
	{
		super();
	}

	@Override
	public MutableQtyAndQuality copy()
	{
		final MutableQtyAndQuality copy = new MutableQtyAndQuality();
		copy.copyFrom(this);
		return copy;
	}

	protected void copyFrom(final MutableQtyAndQuality from)
	{
		this.qtyTotal = from.qtyTotal;
		this.qtyWithIssues = from.qtyWithIssues;
		this.qualityNotices = from.qualityNotices.copy();
	}

	@Override
	public MutableQtyAndQuality clone()
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

	public void add(final IQtyAndQuality qtyAndQualityToAdd)
	{
		Check.assumeNotNull(qtyAndQualityToAdd, "qtyAndQualityToAdd not null");

		final BigDecimal qtyTotalToAdd = qtyAndQualityToAdd.getQtyTotal();
		final BigDecimal qtyWithIssuesToAdd = qtyAndQualityToAdd.getQtyWithIssuesExact();
		final QualityNoticesCollection qualityNoticesToAdd = qtyAndQualityToAdd.getQualityNotices();

		addQtyAndQtyWithIssues(qtyTotalToAdd, qtyWithIssuesToAdd);
		addQualityNotices(qualityNoticesToAdd);
	}

	public void subtractQtys(final MutableQtyAndQuality qtysToRemove)
	{
		add(qtysToRemove.negateQtys());
	}

	public IQtyAndQuality negateQtys()
	{
		if (isZero())
		{
			return this;
		}

		final MutableQtyAndQuality thisNegated = copy();
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

	@Override
	public final BigDecimal getQtyTotal()
	{
		return qtyTotal;
	}

	@Override
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

	@Override
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
						+ "\nQtyAndQuality: " + this
						);
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

	@Override
	public BigDecimal getQtyWithIssues(final int qtyPrecision)
	{
		return getQtyWithIssuesExact()
				.setScale(qtyPrecision, QtyWithIssues_RoundingMode);
	}

	@Override
	public final BigDecimal getQtyWithIssuesExact()
	{
		return qtyWithIssues;
	}

	@Override
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

	@Override
	public QualityNoticesCollection getQualityNotices()
	{
		// always return a copy, because qualityNotices is not immutable
		return qualityNotices.copy();
	}
}
