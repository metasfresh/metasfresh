package org.eevolution.api.impl;

import java.math.BigDecimal;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.eevolution.api.IPPOrderNodeBL;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.X_PP_Cost_Collector;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

public class PPOrderNodeBL implements IPPOrderNodeBL
{
	@Override
	public BigDecimal getSetupTimeUsageVariance(final I_PP_Order_Node activity)
	{
		return getVariance(activity,
				X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance,
				I_PP_Cost_Collector.COLUMNNAME_SetupTimeReal);
	}

	@Override
	public BigDecimal getDurationUsageVariance(final I_PP_Order_Node activity)
	{
		return getVariance(activity,
				X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance,
				I_PP_Cost_Collector.COLUMNNAME_DurationReal);
	}

	private BigDecimal getVariance(final I_PP_Order_Node activity, final String costCollectorType, final String columnName)
	{
		final BigDecimal variance = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, activity.getPP_Order_ID())
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_Node_ID, activity.getPP_Order_Node_ID())
				.addInArrayFilter(I_PP_Cost_Collector.COLUMNNAME_DocStatus, X_PP_Cost_Collector.DOCSTATUS_Completed, X_PP_Cost_Collector.DOCSTATUS_Closed)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_CostCollectorType, costCollectorType)
				.create()
				.aggregate(columnName, Aggregate.SUM, BigDecimal.class);

		return variance != null ? variance : BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getQtyToDeliver(final I_PP_Order_Node activity)
	{
		return activity.getQtyRequiered().subtract(activity.getQtyDelivered());
	}

}
