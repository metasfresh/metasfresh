package de.metas.shipping.api.impl;

import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.impl.ShipperTransportationQuery;
import de.metas.order.OrderId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;

import java.util.Collection;

public class ShipperTransportationBL implements IShipperTransportationBL
{
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	@Override
	public I_M_ShippingPackage createShippingPackage(final ShipperTransportationId shipperTransportationId, final I_M_Package mpackage)
	{
		final I_M_ShippingPackage shippingPackage = InterfaceWrapperHelper.newInstance(I_M_ShippingPackage.class, mpackage);
		shippingPackage.setM_ShipperTransportation_ID(ShipperTransportationId.toRepoId(shipperTransportationId));

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

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(adClientId)
				.adOrgId(adOrgId)
				.build();
		final int docTypeId = docTypeDAO.getDocTypeId(query).getRepoId();

		shipperTransportation.setC_DocType_ID(docTypeId);
	}

	@Override
	public boolean isAnyOrderAssignedToDifferentTransportationOrder(final @NonNull ShipperTransportationId shipperTransportationId, final @NonNull Collection<OrderId> orderIds)
	{
		return shipperTransportationDAO.anyMatch(ShipperTransportationQuery.builder()
				.shipperTransportationToExclude(shipperTransportationId)
				.orderIds(orderIds)
				.build());
	}

	@Override
	public void setShipper(@NonNull final I_M_ShipperTransportation shipperTransportation, @NonNull final ShipperId shipperId)
	{
		shipperTransportation.setM_Shipper_ID(ShipperId.toRepoId(shipperId));
		final I_M_Shipper shipper = shipperDAO.getById(shipperId);
		shipperTransportation.setPickupTimeFrom(shipper.getPickupTimeFrom());
		shipperTransportation.setPickupTimeTo(shipper.getPickupTimeTo());
	}
}
