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

/**
 * Quantity and Quality, i.e.
 * <ul>
 * <li>total quantity: {@link #getQtyTotal()}
 * <li>quantity with issues: {@link #getQtyWithIssues(int)}
 * <li>quantity without issues: {@link #getQtyWithoutIssues(int)}
 * <li>quality discount percent: {@link #getQualityDiscountPercent()}
 * <li>quality notices: {@link #getQualityNotices()}
 * </ul>
 * 
 * @author tsa
 *
 */
public interface IQtyAndQuality
{
	/** @return a copy of this object */
	IQtyAndQuality copy();

	/**
	 * @return total quantity (with and without issues)
	 */
	BigDecimal getQtyTotal();

	/**
	 * @return true if total quantity is zero
	 */
	boolean isZero();

	/**
	 * @return quantity with issues; i.e. QtyTotal * Quality Discount Percent%
	 */
	BigDecimal getQtyWithIssues(int qtyPrecision);

	/**
	 * @return quantity with issues (precise, high scale value)
	 */
	BigDecimal getQtyWithIssuesExact();

	/**
	 * @return quantity without issues; i.e. QtyTotal - QtyWithIssues
	 */
	BigDecimal getQtyWithoutIssues(int qtyPrecision);

	/**
	 * @return weighted average quality discount percent (between 0..100)
	 */
	BigDecimal getQualityDiscountPercent();

	/**
	 * 
	 * @return quality notices; never null
	 */
	QualityNoticesCollection getQualityNotices();
}
