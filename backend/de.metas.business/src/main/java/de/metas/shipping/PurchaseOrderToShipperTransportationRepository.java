package de.metas.shipping;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inout.InOutId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.Package;
import de.metas.shipping.mpackage.PackageId;
import de.metas.shipping.mpackage.PackageItem;
import de.metas.sscc18.SSCC18;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Package;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.adempiere.model.InterfaceWrapperHelper.load;
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

	public static PurchaseOrderToShipperTransportationRepository newInstanceForUnitTesting()
	{
		return new PurchaseOrderToShipperTransportationRepository();
	}

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

	public void removeFromShipperTransportation(@NonNull final Collection<PackageId> packageIdsToDelete)
	{
		queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_M_Package_ID, packageIdsToDelete)
				.create()
				.delete();
		queryBL.createQueryBuilder(I_M_Package.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Package.COLUMNNAME_M_Package_ID, packageIdsToDelete)
				.create()
				.delete();
	}

	public ImmutableList<Package> getPackagesByOrderLineIds(@NonNull final Collection<OrderLineId> orderLineIds)
	{
		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_C_OrderLine_ID, orderLineIds)
				.andCollect(I_M_ShippingPackage.COLUMN_M_Package_ID)
				.create()
				.stream()
				.map(PurchaseOrderToShipperTransportationRepository::fromPO)
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<PackageId> getPackageIDsByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_M_ShippingPackage.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_ShippingPackage.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.listDistinct(I_M_ShippingPackage.COLUMNNAME_M_Package_ID, PackageId.class);
	}

	public Package getPackageById(@NonNull final PackageId packageId)
	{
		final I_M_Package mPackage = load(packageId, I_M_Package.class);
		return fromPO(mPackage);
	}

	public static Package fromPO(final I_M_Package mPackage)
	{
		final InOutId inOutId = InOutId.ofRepoIdOrNull(mPackage.getM_InOut_ID());
		return Package.builder()
				.id(PackageId.ofRepoId(mPackage.getM_Package_ID()))
				.inOutId(inOutId)
				.weightInKg(mPackage.getPackageWeight())
				.orgId(OrgId.ofRepoId(mPackage.getAD_Org_ID()))
				.sscc(mPackage.getIPA_SSCC18())
				.packageContents(getHuStorageListOrNull(inOutId))
				.build();
	}

	@Nullable
	private static List<PackageItem> getHuStorageListOrNull(@Nullable final InOutId inOutId)
	{
		if (inOutId == null)
		{
			return ImmutableList.of();
		}
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOutId)
				.create()
				.stream()
				.map(PurchaseOrderToShipperTransportationRepository::toPackageItem)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private static PackageItem toPackageItem(@NonNull final I_M_InOutLine inOutLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(inOutLine.getM_Product_ID());
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inOutLine.getC_OrderLine_ID());
		if (productId == null || orderLineId == null)
		{
			return null;
		}
		return PackageItem.builder()
				.productId(productId)
				.quantity(Quantitys.of(inOutLine.getMovementQty(), UomId.ofRepoId(inOutLine.getC_UOM_ID())))
				.orderAndLineId(OrderAndLineId.of(OrderId.ofRepoId(inOutLine.getC_Order_ID()), orderLineId))
				.build();
	}

}
