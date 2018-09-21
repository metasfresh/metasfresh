package de.metas.inoutcandidate.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.IPackageable;
import de.metas.inoutcandidate.api.IPackageableQuery;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.api.impl.Packageable.PackageableBuilder;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

public class PackagingDAO implements IPackagingDAO
{
	@Override
	public IPackageableQuery createPackageableQuery()
	{
		return new PackageableQuery();
	}

	@Override
	public List<IPackageable> retrievePackableLines(final IPackageableQuery query)
	{
		final IQueryBuilder<I_M_Packageable_V> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.orderBy()
				.addColumn(I_M_Packageable_V.COLUMN_ProductName)
				.addColumn(I_M_Packageable_V.COLUMN_PriorityRule)
				.addColumn(I_M_Packageable_V.COLUMN_DateOrdered)
				.endOrderBy();

		//
		// Filter: M_Warehouse_ID
		queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMN_M_Warehouse_ID, query.getWarehouseId());

		//
		// Filter: today's entries only
		if (query.isDisplayTodayEntriesOnly())
		{
			final Timestamp deliveryDateDay = SystemTime.asDayTimestamp();
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_M_Packageable_V.COLUMN_DeliveryDate, deliveryDateDay, DateTruncQueryFilterModifier.DAY)
					.addEqualsFilter(I_M_Packageable_V.COLUMN_DeliveryDate, null);
		}

		//
		return queryBuilder.create()
				.stream(I_M_Packageable_V.class)
				.map(this::createPackageable)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public IPackageable getByShipmentScheduleId(
			@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_Packageable_V record = Services.get(IQueryBL.class).createQueryBuilder(I_M_Packageable_V.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.firstOnly(I_M_Packageable_V.class);
		return createPackageable(record);
	}

	private IPackageable createPackageable(final I_M_Packageable_V record)
	{
		final PackageableBuilder packageable = Packageable.builder();
		packageable.bpartnerId(record.getC_BPartner_Customer_ID());
		packageable.bpartnerValue(record.getBPartnerValue());
		packageable.bpartnerName(record.getBPartnerName());
		packageable.bpartnerLocationId(record.getC_BPartner_Location_ID());
		packageable.bpartnerLocationName(record.getBPartnerLocationName());
		packageable.bpartnerAddress(record.getBPartnerAddress_Override());

		packageable.qtyToDeliver(record.getQtyToDeliver());

		packageable.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()));
		packageable.warehouseName(record.getWarehouseName());

		packageable.productId(ProductId.ofRepoId(record.getM_Product_ID()));
		packageable.productName(record.getProductName());
		packageable.asiId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()));

		packageable.deliveryVia(record.getDeliveryViaRule());

		packageable.shipperId(record.getM_Shipper_ID());
		packageable.shipperName(record.getShipperName());

		packageable.deliveryDate(record.getDeliveryDate()); // 01676
		packageable.preparationDate(record.getPreparationDate());

		packageable.shipmentScheduleId(ShipmentScheduleId.offRepoId(record.getM_ShipmentSchedule_ID()));

		packageable.displayed(record.isDisplayed());

		packageable.orderId(record.getC_OrderSO_ID());
		packageable.docSubType(record.getDocSubType());

		packageable.orderLineIdOrNull(OrderLineId.ofRepoIdOrNull(record.getC_OrderLineSO_ID()));

		packageable.freightCostRule(record.getFreightCostRule());

		return packageable.build();
	}

	@Override
	public BigDecimal retrieveQtyPickedPlannedOrNull(final I_M_ShipmentSchedule sched)
	{
		final I_M_Packageable_V packageableEntry = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_M_ShipmentSchedule_ID, sched.getM_ShipmentSchedule_ID())
				.create()
				.firstOnly(I_M_Packageable_V.class);

		if (packageableEntry == null)
		{
			return null;
		}
		return packageableEntry.getQtyPickedPlanned();

	}
}
