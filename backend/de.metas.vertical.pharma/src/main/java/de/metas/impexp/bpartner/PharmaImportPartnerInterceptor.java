/**
 *
 */
package de.metas.impexp.bpartner;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.IImportProcess;
import de.metas.util.Check;
import de.metas.vertical.pharma.model.I_AD_User;
import de.metas.vertical.pharma.model.I_C_BPartner;
import de.metas.vertical.pharma.model.I_I_BPartner;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PharmaImportPartnerInterceptor implements IImportInterceptor
{
	public static final PharmaImportPartnerInterceptor instance = new PharmaImportPartnerInterceptor();

	private PharmaImportPartnerInterceptor()
	{

	}

	@Override
	public void onImport(IImportProcess<?> process, Object importModel, Object targetModel, int timing)
	{
		if (timing != IImportInterceptor.TIMING_AFTER_IMPORT )
		{
			return;
		}

		final I_I_BPartner ibpartner = InterfaceWrapperHelper.create(importModel, I_I_BPartner.class);

		if (targetModel instanceof org.compiere.model.I_C_BPartner)
		{
			importBPartnerPharmaFields(ibpartner, targetModel);
		}
		else if (targetModel instanceof I_AD_User)
		{
			importContactPharmaFields(ibpartner, targetModel);
		}
	}

	private void importBPartnerPharmaFields(@NonNull final I_I_BPartner ibpartner, @NonNull final Object targetModel)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(targetModel, I_C_BPartner.class);
		bpartner.setIsPharmaciePermission(ibpartner.isPharmaciePermission());
		if (!Check.isEmpty(ibpartner.getPharmaproductpermlaw52(),true))
		{
			bpartner.setPharmaproductpermlaw52(ibpartner.getPharmaproductpermlaw52());
		}
		//
		if (!Check.isEmpty(ibpartner.getRegion(),true))
		{
			bpartner.setRegion(ibpartner.getRegion());
		}
		//
		if (!Check.isEmpty(ibpartner.getSalesResponsible(),true))
		{
			bpartner.setSalesResponsible(ibpartner.getSalesResponsible());
		}
		//
		if (!Check.isEmpty(ibpartner.getPurchaseGroup(),true))
		{
			bpartner.setPurchaseGroup(ibpartner.getPurchaseGroup());
		}
		//
		if (!Check.isEmpty(ibpartner.getAssociationMembership(),true))
		{
			bpartner.setAssociationMembership(ibpartner.getAssociationMembership());
		}
		//
		if (!Check.isEmpty(ibpartner.getShipmentPermissionPharma_Old(),true))
		{
			bpartner.setShipmentPermissionPharma_Old(ibpartner.getShipmentPermissionPharma_Old());
		}
		//
		if (!Check.isEmpty(ibpartner.getPermissionPharmaType(),true))
		{
			bpartner.setPermissionPharmaType(ibpartner.getPermissionPharmaType());
		}
		//
		if (!Check.isEmpty(ibpartner.getWeekendOpeningTimes(),true))
		{
			bpartner.setWeekendOpeningTimes(ibpartner.getWeekendOpeningTimes());
		}
		//
		if (!Check.isEmpty(ibpartner.getVendorResponsible(),true))
		{
			bpartner.setVendorResponsible(ibpartner.getVendorResponsible());
		}
		//
		if (!Check.isEmpty(ibpartner.getMinimumOrderValue(),true))
		{
			bpartner.setMinimumOrderValue(ibpartner.getMinimumOrderValue());
		}
		//
		if (!Check.isEmpty(ibpartner.getRetourFax(),true))
		{
			bpartner.setRetourFax(ibpartner.getRetourFax());
		}
		//
		if (!Check.isEmpty(ibpartner.getPharma_Phone(),true))
		{
			bpartner.setPharma_Phone(ibpartner.getPharma_Phone());
		}
		//
		if (!Check.isEmpty(ibpartner.getContacts(),true))
		{
			bpartner.setContacts(ibpartner.getContacts());
		}
		//
		if (!Check.isEmpty(ibpartner.getPharma_Fax(),true))
		{
			bpartner.setPharma_Fax(ibpartner.getPharma_Fax());
		}
		//
		if (!Check.isEmpty(ibpartner.getStatusInfo(),true))
		{
			bpartner.setStatusInfo(ibpartner.getStatusInfo());
		}

		bpartner.setShelfLifeMinDays(ibpartner.getShelfLifeMinDays());

		de.metas.invoicecandidate.model.I_C_BPartner partner = InterfaceWrapperHelper.create(bpartner, de.metas.invoicecandidate.model.I_C_BPartner.class);
		partner.setSO_InvoiceLine_Aggregation_ID(ibpartner.getC_Aggregation_ID());

		save(bpartner);
	}

	private void importContactPharmaFields(@NonNull final I_I_BPartner ibpartner, @NonNull final Object targetModel)
	{
		final I_AD_User contact = InterfaceWrapperHelper.create(targetModel, I_AD_User.class);
		contact.setIsDecider(ibpartner.isDecider());
		contact.setIsManagement(ibpartner.isManagement());
		contact.setIsMultiplier(ibpartner.isMultiplier());

		save(contact);
	}
}
