package de.metas.shipping.api.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Package;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import lombok.NonNull;

public class ShipperTransportationBL implements IShipperTransportationBL
{
	@Override
	public I_M_ShippingPackage createShippingPackage(final I_M_ShipperTransportation shipperTransportation, final I_M_Package mpackage)
	{
		final I_M_ShippingPackage shippingPackage = InterfaceWrapperHelper.newInstance(I_M_ShippingPackage.class, shipperTransportation);
		shippingPackage.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());

		updateShippingPackageFromPackage(shippingPackage, mpackage);
		InterfaceWrapperHelper.save(shippingPackage);

		return shippingPackage;
	}
	
	private void updateShippingPackageFromPackage(final I_M_ShippingPackage shippingPackage, final I_M_Package mpackage)
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
	}

	@Override
	public void setC_DocType(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		final String docBaseType = de.metas.shipping.util.Constants.C_DocType_DocBaseType_ShipperTransportation;
		final int adClientId = shipperTransportation.getAD_Client_ID();
		final int adOrgId = shipperTransportation.getAD_Org_ID();
		final int docTypeId = Services.get(IDocTypeDAO.class).getDocTypeId(DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(adClientId)
				.adOrgId(adOrgId)
				.build());

		shipperTransportation.setC_DocType_ID(docTypeId);
	}
}
