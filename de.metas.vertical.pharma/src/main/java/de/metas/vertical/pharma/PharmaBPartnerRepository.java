package de.metas.vertical.pharma;

import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_C_BPartner;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class PharmaBPartnerRepository
{
	public PharmaBPartner getById(@NonNull final BPartnerId bpartnerId)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(bpartnersRepo.getById(bpartnerId), I_C_BPartner.class);

		final PharmaCustomerPermissions partnerPermissions = PharmaCustomerPermissions.of(bpartner);

		return PharmaBPartner.builder()
				.bpartnerId(bpartnerId)
				.name(bpartner.getName())
				.hasAtLeastOnePermission(partnerPermissions.hasAtLeastOnePermission())
				.shipmentPermission(extractPharmaShipmentPermission(bpartner))
				.build();
	}

	private static final PharmaShipmentPermission extractPharmaShipmentPermission(final I_C_BPartner bpartner)
	{
		final String shipmentPermissionCode = bpartner.getShipmentPermissionPharma();
		if (shipmentPermissionCode == null)
		{
			return null;
		}

		return PharmaShipmentPermission.forCode(shipmentPermissionCode);
	}
}
