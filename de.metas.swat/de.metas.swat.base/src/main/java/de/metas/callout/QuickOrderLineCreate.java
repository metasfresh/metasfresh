/**
 * 
 */
package de.metas.callout;

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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Order;

import de.metas.form.swing.OrderLineCreate;

/**
 * @author Teo Sarca, teo.sarca@gmail.com
 * 
 */
public class QuickOrderLineCreate extends CalloutEngine {
	public String orderLineQuickInput(Properties ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value) {
		final I_C_Order order = InterfaceWrapperHelper.create(mTab, I_C_Order.class);
		if (order.isProcessed() || order.getC_Order_ID() <= 0)
			return null;
		//
		final OrderLineCreate input = new OrderLineCreate(WindowNo, mTab);
		input.showCenter();
		//
		return null;
	}

}
