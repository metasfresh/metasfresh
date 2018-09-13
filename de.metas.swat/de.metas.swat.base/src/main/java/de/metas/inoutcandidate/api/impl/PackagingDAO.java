package de.metas.inoutcandidate.api.impl;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.Packageable.PackageableBuilder;
import de.metas.inoutcandidate.api.PackageableQuery;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import lombok.NonNull;

public class PackagingDAO implements IPackagingDAO
{
	@Override
	public List<Packageable> retrievePackableLines(final PackageableQuery query)
	{
		return createQuery(query)
				.stream(I_M_Packageable_V.class)
				.map(this::createPackageable)
				.collect(ImmutableList.toImmutableList());
	}

	private IQuery<I_M_Packageable_V> createQuery(final PackageableQuery query)
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
		// Filter: DeliveryDate
		if (query.getDeliveryDate() != null)
		{
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_M_Packageable_V.COLUMN_DeliveryDate, query.getDeliveryDate(), DateTruncQueryFilterModifier.DAY)
					.addEqualsFilter(I_M_Packageable_V.COLUMN_DeliveryDate, null);
		}

		return queryBuilder.create();
	}

	@Override
	public Packageable getByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_Packageable_V record = retrievePackageableRecordByShipmentScheduleId(shipmentScheduleId);
		if (record == null)
		{
			throw new AdempiereException("@NotFound@ @M_Packageable_V@ (@M_ShipmentSchedule_ID@=" + shipmentScheduleId + ")");
		}
		return createPackageable(record);
	}

	private Packageable createPackageable(@NonNull final I_M_Packageable_V record)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(record.getC_BPartner_Customer_ID());

		final PackageableBuilder packageable = Packageable.builder();
		packageable.bpartnerId(bpartnerId);
		packageable.bpartnerValue(record.getBPartnerValue());
		packageable.bpartnerName(record.getBPartnerName());
		packageable.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, record.getC_BPartner_Location_ID()));
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

		packageable.deliveryDate(TimeUtil.asLocalDateTime(record.getDeliveryDate())); // 01676
		packageable.preparationDate(TimeUtil.asLocalDateTime(record.getPreparationDate()));

		packageable.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()));

		packageable.displayed(record.isDisplayed());

		packageable.orderId(OrderId.ofRepoIdOrNull(record.getC_OrderSO_ID()));
		packageable.docSubType(record.getDocSubType());

		packageable.orderLineIdOrNull(OrderLineId.ofRepoIdOrNull(record.getC_OrderLineSO_ID()));

		packageable.freightCostRule(record.getFreightCostRule());

		return packageable.build();
	}

	@Override
	public BigDecimal retrieveQtyPickedPlannedOrNull(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_Packageable_V record = retrievePackageableRecordByShipmentScheduleId(shipmentScheduleId);
		if (record == null)
		{
			return null;
		}
		return record.getQtyPickedPlanned();

	}

	private I_M_Packageable_V retrievePackageableRecordByShipmentScheduleId(final ShipmentScheduleId shipmentScheduleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.firstOnly(I_M_Packageable_V.class);
	}
}
