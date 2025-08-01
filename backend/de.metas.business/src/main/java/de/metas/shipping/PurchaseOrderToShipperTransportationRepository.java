package de.metas.shipping;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inout.InOutId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.Package;
import de.metas.shipping.mpackage.PackageId;
import de.metas.sscc18.SSCC18;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Package;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class PurchaseOrderToShipperTransportationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public boolean purchaseOrderNotInShipperTransportation(@NonNull final OrderId purchaseOrderId)
	{
		return !queryBL
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_C_Order_ID, purchaseOrderId)
				.create()
				.anyMatch();
	}

	public void addPurchaseOrderToShipperTransportation(@NonNull final PurchaseShippingPackageCreateRequest request)
	{
		final I_M_Package mpackage = newInstance(I_M_Package.class);
		mpackage.setM_Shipper_ID(ShipperId.toRepoId(request.getShiperId()));
		mpackage.setShipDate(TimeUtil.asTimestamp(request.getDatePromised()));
		mpackage.setC_BPartner_ID(request.getBPartnerLocationId().getBpartnerId().getRepoId());
		mpackage.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(request.getBPartnerLocationId()));
		mpackage.setAD_Org_ID(OrgId.toRepoId(request.getOrgId()));
		mpackage.setIPA_SSCC18(SSCC18.toString(request.getSscc()));
		save(mpackage);

		final I_M_ShippingPackage shippingPackage = InterfaceWrapperHelper.newInstance(I_M_ShippingPackage.class, mpackage);
		shippingPackage.setM_ShipperTransportation_ID(ShipperTransportationId.toRepoId(request.getShipperTransportationId()));
		shippingPackage.setM_Package_ID(mpackage.getM_Package_ID());
		shippingPackage.setC_BPartner_ID(mpackage.getC_BPartner_ID());
		shippingPackage.setC_BPartner_Location_ID(mpackage.getC_BPartner_Location_ID());
		shippingPackage.setC_Order_ID(OrderId.toRepoId(request.getOrderId()));
		shippingPackage.setC_OrderLine_ID(OrderLineId.toRepoId(request.getOrderLineId()));
		shippingPackage.setIsToBeFetched(true);
		shippingPackage.setAD_Org_ID(OrgId.toRepoId(request.getOrgId()));
		save(shippingPackage);
	}

	public ImmutableList<Package> getPackagesByOrderLineIds(@NonNull final Collection<OrderLineId> orderLineIds)
	{
		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_C_OrderLine_ID, orderLineIds)
				.andCollect(I_M_ShippingPackage.COLUMN_M_Package_ID)
				.create()
				.stream()
				.map(this::fromDB)
				.collect(ImmutableList.toImmutableList());
	}

	private Package fromDB(@NonNull final I_M_Package mPackage)
	{
		//TODO IT2 unify logic with de.metas.handlingunits.shipping.InOutPackageRepository.fromPO
		final PackageId packageId = PackageId.ofRepoId(mPackage.getM_Package_ID());
		return Package.builder()
				.id(packageId)
				.inOutId(InOutId.ofRepoIdOrNull(mPackage.getM_InOut_ID()))
				.weightInKg(mPackage.getPackageWeight())
				.sscc(mPackage.getIPA_SSCC18())
				.orgId(OrgId.ofRepoId(mPackage.getAD_Org_ID()))
				.packageContents(Collections.emptyList())
				.build();
	}

}
