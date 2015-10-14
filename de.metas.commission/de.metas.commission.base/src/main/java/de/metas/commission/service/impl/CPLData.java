/**
 * 
 */
package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.metas.commission.model.MCAdvCommissionFact;

/**
 * Helper class for {@link CommissionPayrollBL} to make sure that everything is aggregated as desired.
 * 
 * @author ts
 * 
 */
class CPLData
{

	final List<CPLDataItem> items = new ArrayList<CPLDataItem>();

	void addItem(final MCAdvCommissionFact fact, final BigDecimal points)
	{
		items.add(new CPLDataItem(fact, points));
	}

	List<AggregatedCPLDataItem> aggregateItems()
	{
		final List<AggregatedCPLDataItem> result = new ArrayList<AggregatedCPLDataItem>();

		final AggregatedCPLDataItem data = new AggregatedCPLDataItem();
		result.add(data);

		for (final CPLDataItem item : items)
		{
			data.facts.add(item.fact);
			data.addCommissionAmtBase(item.commissionAmtBase);
			data.addCommissionPoints(item.commissionPoints);
			data.addCommissionPointsSum(item.fact.getCommissionPointsSum());
			data.addQty(item.fact.getQty());
		}

		return result;
	}

	class AggregatedCPLDataItem
	{
		private BigDecimal commissionPoints = BigDecimal.ZERO;
		private BigDecimal commissionPointsSum = BigDecimal.ZERO;
		private BigDecimal commissionAmtBase = BigDecimal.ZERO;
		private BigDecimal qtySum = BigDecimal.ZERO;

		private final List<MCAdvCommissionFact> facts = new ArrayList<MCAdvCommissionFact>();

		private AggregatedCPLDataItem()
		{
		}

		private void addCommissionPoints(final BigDecimal augent)
		{
			commissionPoints = commissionPoints.add(augent);
		}

		private void addCommissionPointsSum(final BigDecimal augent)
		{
			commissionPointsSum = commissionPointsSum.add(augent);
		}

		private void addCommissionAmtBase(final BigDecimal augent)
		{
			commissionAmtBase = commissionAmtBase.add(augent);
		}

		private void addQty(final BigDecimal augent)
		{
			qtySum = qtySum.add(augent);
		}

		BigDecimal getCommissionPoints()
		{
			return commissionPoints;
		}

		BigDecimal getCommissionPointsSum()
		{
			return commissionPointsSum;
		}

		BigDecimal getCommissionAmtBase()
		{
			return commissionAmtBase;
		}

		BigDecimal getQtySum()
		{
			return qtySum;
		}

		List<MCAdvCommissionFact> getFacts()
		{
			return Collections.unmodifiableList(facts);
		}
	}

	private class CPLDataItem
	{
		final BigDecimal commissionPoints;
		final BigDecimal commissionAmtBase;
		final BigDecimal qty;
		final MCAdvCommissionFact fact;

		public CPLDataItem(final MCAdvCommissionFact fact, final BigDecimal points)
		{
			this.fact = fact;
			commissionPoints = points;

			qty = fact.getQty();

			if (qty.signum() >= 0)
			{
				commissionAmtBase = fact.getCommissionAmtBase();
			}
			else
			{
				commissionAmtBase = fact.getCommissionAmtBase().negate();
			}
		}
	}
}
