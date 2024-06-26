package de.metas.picking.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.document.DocumentNoFilter;
import de.metas.freighcost.FreightCostRule;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.lock.api.ILockManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.Packageable.PackageableBuilder;
import de.metas.picking.api.PackageableQuery;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PackagingDAO implements IPackagingDAO
{
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_stream_BufferSize = "de.metas.picking.api.impl.PackagingDAO.stream.BufferSize";
	private static final int DEFAULT_stream_BufferSize = 500;

	@Override
	public Stream<Packageable> stream(@NonNull final PackageableQuery query)
	{
		return createQuery(query)
				.setOption(IQuery.OPTION_IteratorBufferSize, getStreamBufferSize())
				.iterateAndStream()
				.map(this::toPackageable);
	}

	private int getStreamBufferSize()
	{
		final int bufferSize = sysConfigBL.getIntValue(SYSCONFIG_stream_BufferSize, -1);
		return bufferSize > 0 ? bufferSize : DEFAULT_stream_BufferSize;
	}

	private IQuery<I_M_Packageable_V> createQuery(@NonNull final PackageableQuery query)
	{
		final IQueryBuilder<I_M_Packageable_V> queryBuilder = queryBL.createQueryBuilder(I_M_Packageable_V.class);
		setQueryOrderBy(queryBuilder, query.getOrderBys());

		//
		// Filter: Customer
		if (!query.getCustomerIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID, query.getCustomerIds());
		}

		//
		// Filter: Customer Location
		if (query.getDeliveryBPLocationId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID, query.getDeliveryBPLocationId());
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
		// Filter: DeliveryDays
		if (!query.getDeliveryDays().isEmpty())
		{
			final ICompositeQueryFilter<I_M_Packageable_V> deliveryDaysFilter = queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_M_Packageable_V.COLUMN_DeliveryDate, null);

			query.getDeliveryDays().forEach(deliveryDay -> deliveryDaysFilter.addEqualsFilter(I_M_Packageable_V.COLUMN_DeliveryDate, deliveryDay, DateTruncQueryFilterModifier.DAY));
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
		// Filter: sales order document no
		final DocumentNoFilter salesOrderDocumentNo = query.getSalesOrderDocumentNo();
		if (salesOrderDocumentNo != null)
		{
			queryBuilder.filter(salesOrderDocumentNo.toSqlFilter(I_M_Packageable_V.COLUMN_OrderDocumentNo));
		}

		//
		// Filter by Locked By User (via M_ShipmentSchedule_Lock table)
		if (query.getLockedBy() != null)
		{
			if (query.isIncludeNotLocked())
			{
				queryBuilder.addInArrayFilter(I_M_Packageable_V.COLUMNNAME_LockedBy_User_ID, null, query.getLockedBy());
			}
			else
			{
				queryBuilder.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_LockedBy_User_ID, query.getLockedBy());
			}
		}

		//
		// Exclude shipment-schedules that are currently locked for processing/shipment-creation (via T_Lock table)
		if (query.isExcludeLockedForProcessing())
		{
			queryBuilder.filter(lockManager.getNotLockedFilter(
					I_M_ShipmentSchedule.Table_Name,
					I_M_Packageable_V.Table_Name + "." + I_M_Packageable_V.COLUMNNAME_M_ShipmentSchedule_ID));
		}

		//
		// Filter by excludeShipmentScheduleIds
		if (query.getExcludeShipmentScheduleIds() != null && !query.getExcludeShipmentScheduleIds().isEmpty())
		{
			queryBuilder.addNotInArrayFilter(I_M_Packageable_V.COLUMNNAME_M_ShipmentSchedule_ID, query.getExcludeShipmentScheduleIds());
		}

		// Filter: Handover Location
		if (!query.getHandoverLocationIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_Packageable_V.COLUMNNAME_HandOver_Location_ID, query.getHandoverLocationIds());
		}

		return queryBuilder.create();
	}

	private static void setQueryOrderBy(
			@NonNull final IQueryBuilder<I_M_Packageable_V> queryBuilder,
			@NonNull final ImmutableSet<PackageableQuery.OrderBy> orderBys)
	{
		orderBys.forEach(orderBy -> appendOrderBy(queryBuilder, orderBy));
	}

	private static void appendOrderBy(@NonNull final IQueryBuilder<I_M_Packageable_V> queryBuilder, @NonNull final PackageableQuery.OrderBy orderBy)
	{
		switch (orderBy)
		{
			case ProductName:
				queryBuilder.orderBy(I_M_Packageable_V.COLUMNNAME_ProductName);
				break;
			case PriorityRule:
				queryBuilder.orderBy(I_M_Packageable_V.COLUMNNAME_PriorityRule);
				break;
			case DateOrdered:
				queryBuilder.orderBy(I_M_Packageable_V.COLUMNNAME_DateOrdered);
				break;
			case PreparationDate:
				queryBuilder.orderBy(I_M_Packageable_V.COLUMNNAME_PreparationDate);
				break;
			case SalesOrderId:
				queryBuilder.orderBy(I_M_Packageable_V.COLUMNNAME_C_OrderSO_ID);
				break;
			case DeliveryBPLocationId:
				queryBuilder.orderBy(I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID);
				break;
			case WarehouseTypeId:
				queryBuilder.orderBy(I_M_Packageable_V.COLUMNNAME_M_Warehouse_Type_ID);
				break;
			case SetupPlaceNo_Descending:
				queryBuilder.orderBy().addColumn(I_M_Packageable_V.COLUMNNAME_Setup_Place_No, IQueryOrderBy.Direction.Descending, IQueryOrderBy.Nulls.Last);
				break;
			default:
				throw new AdempiereException("Unknown ORDER BY: " + orderBy);
		}
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
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		packageable.orgId(orgId);
		packageable.customerId(bpartnerId);
		packageable.customerBPValue(record.getBPartnerValue());
		packageable.customerName(record.getBPartnerName());
		packageable.customerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, record.getC_BPartner_Location_ID()));
		packageable.customerBPLocationName(record.getBPartnerLocationName());
		packageable.customerAddress(record.getBPartnerAddress_Override());
		packageable.handoverLocationId(BPartnerLocationId.ofRepoId(record.getHandOver_Partner_ID(), record.getHandOver_Location_ID()));

		packageable.qtyOrdered(Quantity.of(record.getQtyOrdered(), uom));
		packageable.qtyToDeliver(Quantity.of(record.getQtyToDeliver(), uom));
		packageable.qtyDelivered(Quantity.of(record.getQtyDelivered(), uom));
		packageable.qtyPickedAndDelivered(Quantity.of(record.getQtyPickedAndDelivered(), uom));
		packageable.qtyPickedNotDelivered(Quantity.of(record.getQtyPickedNotDelivered(), uom));
		packageable.qtyPickedPlanned(Quantity.of(record.getQtyPickedPlanned(), uom));
		packageable.catchWeightUomId(record.isCatchWeight() ? UomId.ofRepoIdOrNull(record.getCatch_UOM_ID()) : null);

		packageable.warehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_ID()));
		packageable.warehouseName(record.getWarehouseName());
		packageable.warehouseTypeId(WarehouseTypeId.ofRepoIdOrNull(record.getM_Warehouse_Type_ID()));

		packageable.productId(ProductId.ofRepoId(record.getM_Product_ID()));
		packageable.productName(record.getProductName());
		packageable.asiId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()));

		packageable.deliveryViaRule(DeliveryViaRule.ofNullableCode(record.getDeliveryViaRule()));

		packageable.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()));
		packageable.shipperName(record.getShipperName());

		packageable.deliveryDate(record.getDeliveryDate() != null ? InstantAndOrgId.ofTimestamp(record.getDeliveryDate(), orgId) : null); // 01676
		packageable.preparationDate(record.getPreparationDate() != null ? InstantAndOrgId.ofTimestamp(record.getPreparationDate(), orgId) : null);

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

		//
		// Packing
		packageable.packToHUPIItemProductId(HUPIItemProductId.ofRepoIdOrNone(record.getPackTo_HU_PI_Item_Product_ID()));

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

		final I_C_UOM uom = uomsRepo.getById(record.getC_UOM_ID());
		final Quantity qtyPickedPlanned = Quantity.of(record.getQtyPickedPlanned(), uom);
		return Optional.of(qtyPickedPlanned);
	}

	private List<I_M_Packageable_V> retrievePackageableRecordsByShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL
				.createQueryBuilder(I_M_Packageable_V.class)
				.addInArrayFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.list(I_M_Packageable_V.class);
	}

	@Nullable
	private I_M_Packageable_V retrievePackageableRecordByShipmentScheduleId(final ShipmentScheduleId shipmentScheduleId)
	{
		return queryBL
				.createQueryBuilder(I_M_Packageable_V.class)
				.addEqualsFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleId)
				.create()
				.firstOnly(I_M_Packageable_V.class);
	}
}
