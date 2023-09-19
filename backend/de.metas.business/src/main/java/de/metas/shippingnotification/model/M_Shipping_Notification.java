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

package de.metas.shippingnotification.model;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.shippingnotification.ShippingNotificationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_Shipping_Notification.class)
@Component
@RequiredArgsConstructor
public class M_Shipping_Notification
{
	private final IDocumentLocationBL documentLocationBL;
	private final ShippingNotificationService shippingNotificationService;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_M_Shipping_Notification.COLUMNNAME_C_BPartner_ID,
					I_M_Shipping_Notification.COLUMNNAME_C_BPartner_Location_ID,
					I_M_Shipping_Notification.COLUMNNAME_AD_User_ID
			})
	public void beforeSave_updateRenderedAddressesAndCapturedLocations(@NonNull final I_M_Shipping_Notification shippingNotificationRecord)
	{
		shippingNotificationService.updateWhileSaving(
				shippingNotificationRecord,
				shippingNotification -> {
					final RenderedAddressAndCapturedLocation renderedLocation = documentLocationBL.computeRenderedAddress(shippingNotification.getLocation());
					shippingNotification.updateBPAddress(renderedLocation.getRenderedAddress());
				}
		);
	}
}
