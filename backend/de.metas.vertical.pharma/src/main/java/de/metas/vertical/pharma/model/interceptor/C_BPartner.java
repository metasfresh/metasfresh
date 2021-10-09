package de.metas.vertical.pharma.model.interceptor;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.pharma.PharmaCustomerPermission;
import de.metas.vertical.pharma.PharmaCustomerPermissions;
import de.metas.vertical.pharma.PharmaModulo11Validator;
import de.metas.vertical.pharma.PharmaVendorPermission;
import de.metas.vertical.pharma.PharmaVendorPermissions;
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
	private static final AdMessageKey ERR_NarcoticPermissions_Valid_BTM = AdMessageKey.of("de.metas.vertical.pharma.model.interceptor.C_BPartner.NarcoticPermissions_Valid_BTM");
	private static final AdMessageKey ERR_InvalidBTM = AdMessageKey.of("de.metas.vertical.pharma.model.interceptor.C_BPartner.Invalid_BTM");

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_C_BPartner.COLUMNNAME_IsCustomer,
			I_C_BPartner.COLUMNNAME_IsPharmaAgentPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaciePermission,
			I_C_BPartner.COLUMNNAME_IsPharmaManufacturerPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaWholesalePermission,
			I_C_BPartner.COLUMNNAME_IsVeterinaryPharmacyPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaCustomerNarcoticsPermission
	})
	public void onPharmaPermissionChanged_Customer(final I_C_BPartner customer)
	{
		final PharmaCustomerPermissions permissions = PharmaCustomerPermissions.of(customer);
		if (!customer.isCustomer())
		{
			customer.setShipmentPermissionPharma(null);
			customer.setShipmentPermissionChangeDate(null);
		}
		else if (permissions.hasPermission(PharmaCustomerPermission.PHARMA_NARCOTICS))
		{
			customer.setShipmentPermissionPharma(I_C_BPartner.ShipmentPermissionPharma_TypeC);
			customer.setShipmentPermissionChangeDate(de.metas.common.util.time.SystemTime.asTimestamp());
		}
		else if (permissions.hasAtLeastOnePermission())
		{
			customer.setShipmentPermissionPharma(I_C_BPartner.ShipmentPermissionPharma_TypeA);
			customer.setShipmentPermissionChangeDate(de.metas.common.util.time.SystemTime.asTimestamp());
		}
		else
		{
			customer.setShipmentPermissionPharma(I_C_BPartner.ShipmentPermissionPharma_TypeB);
			customer.setShipmentPermissionChangeDate(null);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_C_BPartner.COLUMNNAME_IsVendor,
			I_C_BPartner.COLUMNNAME_IsPharmaVendorAgentPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaVendorManufacturerPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaVendorWholesalePermission,
			I_C_BPartner.COLUMNNAME_IsPharmaVendorNarcoticsPermission
	})
	public void onPharmaPermissionChanged_Vendor(final I_C_BPartner vendor)
	{
		final PharmaVendorPermissions permissions = PharmaVendorPermissions.of(vendor);
		if (!vendor.isVendor())
		{
			vendor.setReceiptPermissionPharma(null);
			vendor.setReceiptPermissionChangeDate(null);
		}
		else if (permissions.hasPermission(PharmaVendorPermission.PHARMA_NARCOTICS))
		{
			vendor.setReceiptPermissionPharma(I_C_BPartner.ReceiptPermissionPharma_TypeC);
			vendor.setReceiptPermissionChangeDate(SystemTime.asTimestamp());
		}
		else if (permissions.hasAtLeastOnePermission())
		{
			vendor.setReceiptPermissionPharma(I_C_BPartner.ReceiptPermissionPharma_TypeA);
			vendor.setReceiptPermissionChangeDate(de.metas.common.util.time.SystemTime.asTimestamp());
		}
		else
		{
			vendor.setReceiptPermissionPharma(I_C_BPartner.ReceiptPermissionPharma_TypeB);
			vendor.setReceiptPermissionChangeDate(null);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_C_BPartner.COLUMNNAME_BTM,
			I_C_BPartner.COLUMNNAME_IsPharmaCustomerNarcoticsPermission,
			I_C_BPartner.COLUMNNAME_IsPharmaVendorNarcoticsPermission })
	public void validateBTM(final I_C_BPartner partner)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final String btm = partner.getBTM();
		final boolean hasNarcoticPermission = partner.isPharmaCustomerNarcoticsPermission() || partner.isPharmaVendorNarcoticsPermission();

		if (Check.isEmpty(btm))
		{
			if (hasNarcoticPermission)
			{

				final ITranslatableString validBTMRequiredMessage = msgBL.getTranslatableMsgText(ERR_NarcoticPermissions_Valid_BTM, partner);

				throw new AdempiereException(validBTMRequiredMessage)
						.markAsUserValidationError();
			}

			// If the partner doesn't have permissions, BTM is not relevant.
			return;
		}

		final boolean isValidBTM = PharmaModulo11Validator.isValid(btm);

		if (!isValidBTM)
		{

			final ITranslatableString invalidBTMMessage = msgBL.getTranslatableMsgText(ERR_InvalidBTM, btm);

			throw new AdempiereException(invalidBTMMessage)
					.markAsUserValidationError();
		}

	}
}
