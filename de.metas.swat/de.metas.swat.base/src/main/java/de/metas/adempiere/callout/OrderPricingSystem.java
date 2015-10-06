package de.metas.adempiere.callout;

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

import org.adempiere.model.GridTabWrapper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IOrderBL;

public class OrderPricingSystem extends CalloutEngine {

	private static final String TRX_NAME = null;

	public String cBPartnerId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value) {

		if (isCalloutActive()) {
			return "";
		}

		final I_C_Order order = GridTabWrapper.create(mTab, I_C_Order.class);
		return Services.get(IOrderBL.class).setPricingSystemId(order, false,
				TRX_NAME);
	}

	public String cBPartnerLocationId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value) {

		if (isCalloutActive()) {
			return "";
		}
		final I_C_Order order = GridTabWrapper.create(mTab, I_C_Order.class);
		return Services.get(IOrderBL.class).checkForPriceList(order, false,
				TRX_NAME);
	}

	public String mPricingSystemId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue) {

		if (isCalloutActive()) {
			return "";
		}
		final I_C_Order order = GridTabWrapper.create(mTab, I_C_Order.class);

		if (value == null && oldValue != null) {
			// metas: Fuer einige m.E. i.O. fuer andere Kunden eher nicht.
			mTab.setValue(mField, oldValue);
		}
		return Services.get(IOrderBL.class).setPriceList(order, false, order.getM_PricingSystem_ID(), TRX_NAME);
	}
}
