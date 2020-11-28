package de.metas.vertical.pharma;

import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_C_BPartner;
import lombok.NonNull;

import javax.annotation.Nullable;

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
		final IBPartnerDAO bPartnersRepo = Services.get(IBPartnerDAO.class);

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(bPartnersRepo.getById(bpartnerId), I_C_BPartner.class);

		final PharmaCustomerPermissions customerPermissions = PharmaCustomerPermissions.of(bPartner);
		final PharmaVendorPermissions vendorPermissions = PharmaVendorPermissions.of(bPartner);

		return PharmaBPartner.builder()
				.bpartnerId(bpartnerId)
				.name(bPartner.getName())
				.hasAtLeastOneCustomerPermission(customerPermissions.hasAtLeastOnePermission())
				.shipmentPermission(extractPharmaShipmentPermission(bPartner))
				.hasAtLeastOneVendorPermission(vendorPermissions.hasAtLeastOnePermission())
				.receiptPermission(extractPharmaReceiptPermission(bPartner))
				.build();
	}

	@Nullable private static PharmaShipmentPermission extractPharmaShipmentPermission(final I_C_BPartner bPartner)
	{
		final String shipmentPermissionCode = bPartner.getShipmentPermissionPharma();
		if (shipmentPermissionCode == null)
		{
			return null;
		}

		return PharmaShipmentPermission.forCode(shipmentPermissionCode);
	}

	@Nullable private PharmaReceiptPermission extractPharmaReceiptPermission(final I_C_BPartner bPartner){
		final String receiptPermissionCode = bPartner.getReceiptPermissionPharma();
		if (receiptPermissionCode == null)
		{
			return null;
		}

		return PharmaReceiptPermission.forCode(receiptPermissionCode);
	}
}
