/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.inout.callout;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

@Callout(I_M_InOut.class)
@Component
public class M_InOut
{
	private final IDocumentLocationBL documentLocationBL;

	public M_InOut(@NonNull final IDocumentLocationBL documentLocationBL)
	{
		this.documentLocationBL = documentLocationBL;

		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = {
			I_M_InOut.COLUMNNAME_C_BPartner_ID,
			I_M_InOut.COLUMNNAME_C_BPartner_Location_ID,
			I_M_InOut.COLUMNNAME_AD_User_ID },
			skipIfCopying = true)
	public void updateBPartnerAddress(final I_M_InOut inout)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(InOutDocumentLocationAdapterFactory.locationAdapter(inout));
	}

	@CalloutMethod(columnNames = {
			I_M_InOut.COLUMNNAME_IsDropShip,
			I_M_InOut.COLUMNNAME_DropShip_BPartner_ID,
			I_M_InOut.COLUMNNAME_DropShip_Location_ID,
			I_M_InOut.COLUMNNAME_DropShip_User_ID,
			I_M_InOut.COLUMNNAME_M_Warehouse_ID },
			skipIfCopying = true)
	public void updateDeliveryToAddress(final I_M_InOut inout)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(InOutDocumentLocationAdapterFactory.deliveryLocationAdapter(inout));
	}
}
