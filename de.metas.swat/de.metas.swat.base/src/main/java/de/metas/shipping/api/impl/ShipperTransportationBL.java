package de.metas.shipping.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.Properties;

import org.adempiere.document.service.IDocTypeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.interfaces.I_M_Package;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;

public class ShipperTransportationBL implements IShipperTransportationBL
{
	/**
	 * Update {@link I_M_ShippingPackage} by using common values from {@link I_M_Package}.
	 *
	 * @param shippingPackage
	 * @param package package
	 */
	private void setPackage(final I_M_ShippingPackage shippingPackage, final I_M_Package mpackage)
	{
		shippingPackage.setM_Package_ID(mpackage.getM_Package_ID());
		shippingPackage.setC_BPartner_ID(mpackage.getC_BPartner_ID());
		shippingPackage.setC_BPartner_Location_ID(mpackage.getC_BPartner_Location_ID());

		if (mpackage.getM_InOut_ID() > 0)
		{
			shippingPackage.setM_InOut_ID(mpackage.getM_InOut_ID());
			shippingPackage.setC_BPartner_ID(mpackage.getM_InOut().getC_BPartner_ID());
			shippingPackage.setC_BPartner_Location_ID(mpackage.getM_InOut().getC_BPartner_Location_ID());
		}

		// @TODO: Calculate PackageNetTotal and PackageWeight ??
		shippingPackage.setPackageNetTotal(mpackage.getPackageNetTotal());
		shippingPackage.setPackageWeight(mpackage.getPackageWeight());
	}	// setPackage

	@Override
	public I_M_ShippingPackage createShippingPackage(final I_M_ShipperTransportation shipperTransportation, final I_M_Package mpackage)
	{
		final I_M_ShippingPackage shippingPackage = InterfaceWrapperHelper.newInstance(I_M_ShippingPackage.class, shipperTransportation);
		shippingPackage.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());

		setPackage(shippingPackage, mpackage);
		InterfaceWrapperHelper.save(shippingPackage);

		return shippingPackage;
	}

	@Override
	public void setC_DocType(final I_M_ShipperTransportation shipperTransportation)
	{
		Check.assumeNotNull(shipperTransportation, "shipperTransportation not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(shipperTransportation);
		final String docBaseType = de.metas.shipping.util.Constants.C_DocType_DocBaseType_ShipperTransportation;
		final int adClientId = shipperTransportation.getAD_Client_ID();
		final int adOrgId = shipperTransportation.getAD_Org_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(shipperTransportation);
		final int docTypeId = Services.get(IDocTypeDAO.class).getDocTypeId(ctx, docBaseType, adClientId, adOrgId, trxName);

		shipperTransportation.setC_DocType_ID(docTypeId);
	}
}
