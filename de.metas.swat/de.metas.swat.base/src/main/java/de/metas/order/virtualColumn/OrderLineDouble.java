package de.metas.order.virtualColumn;

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


import static org.adempiere.util.CustomColNames.C_OrderLine_C_ORDERLINE_DOUBLE_ID;
import static org.adempiere.util.CustomColNames.C_OrderLine_C_ORDER_DOUBLE_ID;
import static org.adempiere.util.CustomColNames.C_OrderLine_DATEORDERED_DOUBLE;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.virtualColumn.IVirtualColumnCache;
import org.adempiere.model.virtualColumn.IVirtualSpecificColumn;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderPA;

/**
 * Class computes for given order line IDs whether there are already other open
 * order lines with the same product and bPartnerLocation.
 * 
 * @author tobi42
 * 
 */
/*
 * Berechnung kann mit Exception abstuerzen, ohne seine Datenbank-Locks
 * aufzuraeumen->Spalte in AD wieder geloescht, da sie auch z.Zt. nicht angezeigt wird.
 */
public class OrderLineDouble implements IVirtualSpecificColumn {

	@Override
	public void fillCache(final Collection<Integer> recordIds,
			final IVirtualColumnCache cacheSupport, final String trxName,
			final Map<String, Object> params) {

		final IOrderPA orderPA = Services.get(IOrderPA.class);

		for (final int recordId : recordIds) {

			final I_C_OrderLine thisLine = InterfaceWrapperHelper.create(Env.getCtx(), recordId, I_C_OrderLine.class, trxName);

			final List<I_C_OrderLine> lines = orderPA
					.retrieveOrderLinesForProdAndLoc(
							Env.getCtx(), thisLine.getM_Product_ID(), thisLine
									.getC_BPartner_Location_ID(), I_C_OrderLine.class, trxName);

			for (final I_C_OrderLine potentialDouble : lines) {

				if (potentialDouble.getC_OrderLine_ID() != recordId) {
					// the potential double is a real double.
					cacheSupport.cacheValue(C_OrderLine_C_ORDERLINE_DOUBLE_ID,
							recordId, potentialDouble.getC_OrderLine_ID(),
							trxName);

					cacheSupport
							.cacheValue(C_OrderLine_DATEORDERED_DOUBLE,
									recordId, potentialDouble.getDateOrdered(),
									trxName);

					cacheSupport.cacheValue(C_OrderLine_C_ORDER_DOUBLE_ID,
							recordId, potentialDouble.getC_Order_ID(), trxName);
					break;
				}
			}
		}
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { C_OrderLine_C_ORDERLINE_DOUBLE_ID,
				C_OrderLine_C_ORDER_DOUBLE_ID, C_OrderLine_DATEORDERED_DOUBLE };
	}

	@Override
	public Object[] getDefaultValues() {
		return new Object[] { null, null, null };
	}

	@Override
	public String getTableName() {
		return I_C_OrderLine.Table_Name;
	}

}
