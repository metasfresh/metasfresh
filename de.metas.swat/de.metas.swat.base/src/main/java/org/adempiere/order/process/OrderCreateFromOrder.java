package org.adempiere.order.process;

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
import java.util.logging.Level;

import org.adempiere.order.service.IOrderPA;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.MOrder;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

public final class OrderCreateFromOrder extends SvrProcess {

	private int originalOrderId;

	private static CLogger log = CLogger.getCLogger(OrderCreateFromOrder.class);


	@Override
	protected final String doIt() throws Exception {

		final MOrder origial = new MOrder(Env.getCtx(), originalOrderId, null);

		final I_C_Order copy = Services.get(IOrderPA.class).copyOrder(origial,true,
				get_TrxName());
		return copy.getDocumentNo();
	}

	@Override
	protected final void prepare() {

		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(I_C_Order.COLUMNNAME_C_Order_ID))
				originalOrderId = ((BigDecimal) para[i].getParameter())
						.intValue();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

}
