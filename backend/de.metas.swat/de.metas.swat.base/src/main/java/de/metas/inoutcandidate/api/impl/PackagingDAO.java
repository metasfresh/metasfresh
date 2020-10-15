package de.metas.inoutcandidate.api.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.freighcost.FreightCostRule;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.Packageable.PackageableBuilder;
import de.metas.inoutcandidate.api.PackageableQuery;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

public class PackagingDAO implements IPackagingDAO
{
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	@Override
	public Stream<Packageable> stream(final PackageableQuery query)
	{
		return createQuery(query)
				.stream(I_M_Packageable_V.class)
				.map(this::toPackageable);
	}

	private IQuery<I_M_Packageable_V> createQuery(@NonNull final PackageableQuery query)
	{
		final IQueryBuilder<I_M_Packageable_V> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.orderBy()
				.addColumn(I_M_Packageable_V.COLUMN_ProductName)
				.addColumn(I_M_Packageable_V.COLUMN_PriorityRule)
				.addColumn(I_M_Packageable_V.COLUMN_DateOrdered)
				.endOrderBy();

		//
		// Filter: Customer
		if (query.getCustomerId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID, query.getCustomerId());
		}

		//
		// Filter: M_Warehouse_Type_ID
		if (query.getWarehouseTypeId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMN_M_Warehouse_Type_ID, query.getWarehouseTypeId());
		}

		//
		// Filter: M_Warehouse_ID
		if (query.getWarehouseId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID, query.getWarehouseId());
		}

		//
		// Filter: DeliveryDate
		if (query.getDeliveryDate() != null)
		{
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_M_Packageable_V.COLUMN_DeliveryDate, query.getDeliveryDate(), DateTruncQueryFilterModifier.DAY)
					.addEqualsFilter(I_M_Packageable_V.COLUMN_DeliveryDate, null);
		}

		//
		// Filter: PreparationDate
		if (query.getPreparationDate() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMN_PreparationDate, query.getPreparationDate(), DateTruncQueryFilterModifier.DAY);
		}

		if (query.getShipperId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_M_Shipper_ID, query.getShipperId());
		}

		//
		// Filter: only those packageables which are created from sales order/lines
		if (query.isOnlyFromSalesOrder())
		{
			queryBuilder.addNotNull(I_M_Packageable_V.COLUMN_C_OrderLineSO_ID);
		}

		//
		// Filter: sales order ID
		if (query.getSalesOrderId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMN_C_OrderSO_ID, query.getSalesOrderId());
		}

		//
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
		return toPackageable(record);
	}

	@Override
	public List<Packageable> getByShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return retrievePackageableRecordsByShipmentScheduleIds(shipmentScheduleIds)
				.stream()
				.map(this::toPackageable)
				.collect(ImmutableList.toImmutableList());
	}

	private Packageable toPackageable(@NonNull final I_M_Packageable_V record)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(record.getC_BPartner_Customer_ID());
		final I_C_UOM uom = uomsRepo.getById(record.getC_UOM_ID());

		final PackageableBuilder packageable = Packageable.builder();
		packageable.customerId(bpartnerId);
		packageable.customerBPValue(record.getBPartnerValue());
		packageable.customerName(record.getBPartnerName());
		packageable.customerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, record.getC_BPartner_Location_ID()));
		packageable.customerBPLocationName(record.getBPartnerLocationName());
		packageable.customerAddress(record.getBPartnerAddress_Override());

		packageable.qtyOrdered(Quantity.of(record.getQtyOrdered(), uom));
		packageable.qtyToDeliver(Quantity.of(record.getQtyToDeliver(), uom));
		packageable.qtyDelivered(Quantity.of(record.getQtyDelivered(), uom));
		packageable.qtyPickedAndDelivered(Quantity.of(record.getQtyPickedAndDelivered(), uom));
		packageable.qtyPickedNotDelivered(Quantity.of(record.getQtyPickedNotDelivered(), uom));
		packageable.qtyPickedPlanned(Quantity.of(record.getQtyPickedPlanned(), uom));

		packageable.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()));
		packageable.warehouseName(record.getWarehouseName());
		packageable.warehouseTypeId(WarehouseTypeId.ofRepoIdOrNull(record.getM_Warehouse_Type_ID()));

		packageable.productId(ProductId.ofRepoId(record.getM_Product_ID()));
		packageable.productName(record.getProductName());
		packageable.asiId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()));

		packageable.deliveryViaRule(DeliveryViaRule.ofNullableCode(record.getDeliveryViaRule()));

		packageable.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()));
		packageable.shipperName(record.getShipperName());

		packageable.deliveryDate(TimeUtil.asZonedDateTime(record.getDeliveryDate())); // 01676
		packageable.preparationDate(TimeUtil.asZonedDateTime(record.getPreparationDate()));

		packageable.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.optionalOfNullableCode(record.getShipmentAllocation_BestBefore_Policy()));

		packageable.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()));

		packageable.displayed(record.isDisplayed());

		packageable.salesOrderId(OrderId.ofRepoIdOrNull(record.getC_OrderSO_ID()));
		packageable.salesOrderDocumentNo(record.getOrderDocumentNo());
		packageable.salesOrderDocSubType(record.getDocSubType());
		packageable.poReference(record.getPOReference());

		packageable.salesOrderLineIdOrNull(OrderLineId.ofRepoIdOrNull(record.getC_OrderLineSO_ID()));

		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID());
		if (currencyId != null)
		{
			packageable.salesOrderLineNetAmt(Money.of(record.getLineNetAmt(), currencyId));
		}

		packageable.freightCostRule(FreightCostRule.ofNullableCode(record.getFreightCostRule()));

		packageable.pickFromOrderId(PPOrderId.ofRepoIdOrNull(record.getPickFrom_Order_ID()));

		final UserId lockedBy = !InterfaceWrapperHelper.isNull(record, I_M_Packageable_V.COLUMNNAME_LockedBy_User_ID) ? UserId.ofRepoId(record.getLockedBy_User_ID()) : null;
		packageable.lockedBy(lockedBy);

		return packageable.build();
	}

	@Override
	public Optional<Quantity> retrieveQtyPickedPlanned(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_Packageable_V record = retrievePackageableRecordByShipmentScheduleId(shipmentScheduleId);
		if (record == null)
		{
			return Optional.empty();
		}

		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(record.getC_UOM_ID());
		final Quantity qtyPickedPlanned = Quantity.of(record.getQtyPickedPlanned(), uom);
		return Optional.of(qtyPickedPlanned);
	}

	private List<I_M_Packageable_V> retrievePackageableRecordsByShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.addInArrayFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.list(I_M_Packageable_V.class);
	}

	private I_M_Packageable_V retrievePackageableRecordByShipmentScheduleId(final ShipmentScheduleId shipmentScheduleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.addEqualsFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.firstOnly(I_M_Packageable_V.class);
	}
}
