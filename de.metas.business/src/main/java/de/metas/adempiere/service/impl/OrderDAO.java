package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.Query;
import org.compiere.model.X_C_Order;

import de.metas.document.engine.IDocument;

public class OrderDAO extends AbstractOrderDAO
{
	@Override
	public List<I_M_InOut> retrieveInOutsForMatchingOrderLines(final I_C_Order order)
	{
		//
		// TODO 05768 keep legacy logic; this stuff should be upgraded, but it's out of scope here
		//
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);

		final String whereClause = "EXISTS (SELECT 1 FROM M_InOutLine iol, C_OrderLine ol"
				+ " WHERE iol.M_InOut_ID=M_InOut.M_InOut_ID"
				+ " AND iol.C_OrderLine_ID=ol.C_OrderLine_ID"
				+ " AND ol.C_Order_ID=?)";
		return new Query(ctx, org.compiere.model.I_M_InOut.Table_Name, whereClause, trxName)
				.setParameters(order.getC_Order_ID())
				.setOrderBy("M_InOut_ID DESC")
				.list(I_M_InOut.class);
	}

	@Override
	public List<I_C_Order> retrievePurchaseOrdersForPickup(
			final I_C_BPartner_Location bpLoc,
			final Date deliveryDateTime,
			final Date deliveryDateTimeMax)
	{
		// retrieve purchase orders in specified time frame
		final IQueryBuilder<I_C_Order> queryBuilder =
				Services.get(IQueryBL.class).createQueryBuilder(I_C_Order.class, bpLoc)
						.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, false)

						// ts: it seems not really required and might be even counterproductive if we somhow extend
						// purchase order with BP/relations or whatever.
						// .addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, bpLoc.getC_BPartner_ID())

						.addEqualsFilter(I_C_Order.COLUMNNAME_DeliveryViaRule, X_C_Order.DELIVERYVIARULE_Pickup)

						.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_Location_ID, bpLoc.getC_BPartner_Location_ID())
						
						// only orders that are not voided, reversed or closed
						.addEqualsFilter(I_C_Order.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)

						// DatePromised between DeliveryDateTime and DeliveryDateTimeMax
						.addCompareFilter(I_C_Order.COLUMNNAME_DatePromised, Operator.LESS_OR_EQUAL, deliveryDateTimeMax)
						.addCompareFilter(I_C_Order.COLUMNNAME_DatePromised, Operator.GREATER_OR_EQUAL, deliveryDateTime)
						
						.addOnlyActiveRecordsFilter();

		return queryBuilder.create().list();
	}
}
