/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.shipping.custom;

import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.order.OrderAndLineId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class OxidAdaptor
{
	private final static String SYS_CONFIG_OXID_USER_ID = "de.metas.rest_api.v2.shipping.c_olcand.OxidUserId";

	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public boolean isOxidOrder(@NonNull final OrderAndLineId orderAndLineId)
	{
		final int oxidUserId = sysConfigBL.getIntValue(SYS_CONFIG_OXID_USER_ID, -1);

		if (oxidUserId <= 0)
		{
			return false;
		}

		final List<I_C_OLCand> olCands = olCandDAO.retrieveOLCands(orderAndLineId.getOrderLineId(), I_C_OLCand.class);

		return olCands.size() > 0
				&& olCands.get(0).getCreatedBy() == oxidUserId;
	}

	@Nullable
	public String getContactEmail(@NonNull final ShipmentSchedule shipmentSchedule, @NonNull final BPartnerComposite composite)
	{
		if (shipmentSchedule.getBillContactId() == null)
		{
			return null;
		}

		final BPartnerContact contact = composite.extractContact(shipmentSchedule.getBillContactId())
				.orElseThrow(() -> new AdempiereException("Unable to get the contact info from bPartnerComposite!")
						.appendParametersToMessage()
						.setParameter("composite.C_BPartner_ID", composite.getBpartner().getId())
						.setParameter("AD_User_ID", shipmentSchedule.getBillContactId()));

		return contact.getEmail();
	}
}
