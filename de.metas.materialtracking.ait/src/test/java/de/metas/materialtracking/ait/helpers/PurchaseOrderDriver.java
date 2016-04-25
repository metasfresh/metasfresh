package de.metas.materialtracking.ait.helpers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;

import de.metas.adempiere.model.I_M_Product;
import de.metas.materialtracking.model.I_M_Material_Tracking;

/*
 * #%L
 * de.metas.materialtracking.ait
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PurchaseOrderDriver
{
	public static void setupPurchaseOrders(final String nameCountry, final String strDate, final String valueProduct, final String strQty, final String strTuQty, final String lotMaterialTracking)
			throws ParseException
	{
		final Timestamp orderDate = Helper.parseTimestamp(strDate);
		final I_M_Product product = Helper.retrieveExisting(valueProduct, I_M_Product.class);
		final I_C_BPartner_Location bpl = BPartnerDriver.getCreateBPartnerLocation(nameCountry);

		final I_C_Order o = InterfaceWrapperHelper.newInstance(I_C_Order.class, Helper.context);
		o.setDateOrdered(orderDate);
		o.setC_BPartner_Location(bpl);
		InterfaceWrapperHelper.save(o);

		final de.metas.handlingunits.model.I_C_OrderLine ol = InterfaceWrapperHelper.newInstance(de.metas.handlingunits.model.I_C_OrderLine.class, Helper.context);
		ol.setC_Order(o);
		ol.setM_Product(product);
		ol.setQtyOrdered(new BigDecimal(strQty));
		ol.setQtyEnteredTU(new BigDecimal(strTuQty));
		InterfaceWrapperHelper.save(ol);

		Helper.link(
				Helper.retrieveExisting(lotMaterialTracking, I_M_Material_Tracking.class),
				ol);
	}

}
