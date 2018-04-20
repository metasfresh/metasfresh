package de.metas.vertical.pharma.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.PharmaCustomerPermissions;
import de.metas.vertical.pharma.model.I_C_BPartner;

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

@Interceptor(I_C_BPartner.class)
@Component
public class C_BPartner
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_C_BPartner.COLUMNNAME_IsPharmaAgentPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaciePermission,
			I_C_BPartner.COLUMNNAME_IsPharmaManufacturerPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaWholesalePermission,
			I_C_BPartner.COLUMNNAME_IsVeterinaryPharmacyPermission
	})
	public void onPharmaPermissionChanged_Customer(final I_C_BPartner customer)
	{
		if (!customer.isCustomer())
		{
			// nothing to do
			return;
		}

		final PharmaCustomerPermissions permissions = PharmaCustomerPermissions.of(customer);
		if (permissions.hasAtLeastOnePermission())
		{
			customer.setShipmentPermissionPharma(I_C_BPartner.ShipmentPermissionPharma_TypeA);
			customer.setShipmentPermissionChangeDate(SystemTime.asTimestamp());
		}

		customer.setShipmentPermissionPharma(I_C_BPartner.ShipmentPermissionPharma_TypeB);
		customer.setShipmentPermissionChangeDate(null);

	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_C_BPartner.COLUMNNAME_IsPharmaVendorAgentPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaVendorManufacturerPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaVendorWholesalePermission
	})
	public void onPharmaPermissionChanged_Vendor(final I_C_BPartner vendor)
	{
		if (!vendor.isVendor())
		{
			// nothing to do
			return;
		}

		if (hasAnyPharmaPermission_Vendor(vendor))
		{
			vendor.setReceiptPermissionPharma(I_C_BPartner.ReceiptPermissionPharma_TypeA);
			vendor.setReceiptPermissionChangeDate(SystemTime.asTimestamp());
		}

		vendor.setReceiptPermissionPharma(I_C_BPartner.ReceiptPermissionPharma_TypeB);
		vendor.setReceiptPermissionChangeDate(null);
	}

	private boolean hasAnyPharmaPermission_Vendor(final I_C_BPartner vendor)
	{
		return vendor.isPharmaVendorAgentPermission()
				|| vendor.isPharmaVendorManufacturerPermission()
				|| vendor.isPharmaVendorWholesalePermission();
	}

}
