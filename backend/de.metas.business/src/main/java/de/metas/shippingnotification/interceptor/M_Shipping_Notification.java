/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.shippingnotification.interceptor;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;
import de.metas.shippingnotification.location.adapter.ShippingNotificationDocumentLocationAdapterFactory;

@Interceptor(I_M_Shipping_Notification.class)
@Component
public class M_Shipping_Notification
{
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = {
					I_M_Shipping_Notification.COLUMNNAME_C_BPartner_ID,
					I_M_Shipping_Notification.COLUMNNAME_C_BPartner_Location_ID,
					I_M_Shipping_Notification.COLUMNNAME_AD_User_ID },
			skipIfCopying = true)
	public void updateBPartnerAddress(final I_M_Shipping_Notification shippingNotification)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(ShippingNotificationDocumentLocationAdapterFactory.locationAdapter(shippingNotification));
	}


	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = {
					I_M_Shipping_Notification.COLUMNNAME_ShipFrom_Partner_ID,
					I_M_Shipping_Notification.COLUMNNAME_ShipFrom_Location_ID,
					I_M_Shipping_Notification.COLUMNNAME_ShipFrom_User_ID },
			skipIfCopying = true)
	public void updateShipFromPartnerAddress(final I_M_Shipping_Notification shippingNotification)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(ShippingNotificationDocumentLocationAdapterFactory.shipFromLocationAdapter(shippingNotification));
	}


	// @ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
	// 		ifColumnsChanged = { I_M_Shipping_Notification.COLUMNNAME_ShipFrom_Partner_ID,
	// 				I_M_Shipping_Notification.COLUMNNAME_ShipFrom_Location_ID,
	// 				I_M_Shipping_Notification.COLUMNNAME_ShipFrom_User_ID },
	// 		skipIfCopying = true
	// )
	// public void updateNextLocation(final I_C_BPartner_Location bpLocation)
	// {
	// 	documentLocationBL.updateCapturedLocation(ShippingNotificationDocumentLocationAdapterFactory.billLocationAdapter(order));
	// }

}
