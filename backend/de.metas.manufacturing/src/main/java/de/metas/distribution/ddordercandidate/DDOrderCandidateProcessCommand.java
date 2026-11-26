package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.distribution.ddorder.DDOrderAndLineId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.event.DDOrderUserNotificationProducer;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.X_C_DocType;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Process {@link DDOrderCandidate}s and creates DD Order(s).
 */
class DDOrderCandidateProcessCommand
{
	//
	// services
	@NonNull private final DDOrderLowLevelService ddOrderLowLevelService;
	@NonNull private final DDOrderCandidateService ddOrderCandidateService;
	@NonNull private final IOrgDAO orgDAO;
	@NonNull private final IDocTypeDAO docTypeDAO;
	@NonNull final IDocumentBL documentBL;
	@NonNull private final IProductPlanningDAO productPlanningDAO;
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL;
	@NonNull private final IWarehouseBL warehouseBL;
	@NonNull final IUOMConversionBL uomConversionBL;
	@NonNull final IOrderLineBL orderLineBL;
	@NonNull final DDOrderUserNotificationProducer ddOrderUserNotificationProducer;

	//
	// Params
	@NonNull private final DDOrderCandidateProcessRequest request;

	//
	// State
	private final LinkedHashMap<HeaderAggregationKey, HeaderAggregate> aggregates = new LinkedHashMap<>();
	private final AggregationConfig aggregationConfig;
	/** memoizes the warehouse default locator so the null-locator fallback resolves once per warehouse, not once per candidate */
	private final HashMap<WarehouseId, LocatorId> defaultLocatorByWarehouse = new HashMap<>();

	@Builder
	private DDOrderCandidateProcessCommand(
			@NonNull final DDOrderLowLevelService ddOrderLowLevelService,
			@NonNull final DDOrderCandidateService ddOrderCandidateService,
			@NonNull final IOrgDAO orgDAO,
			@NonNull final IDocTypeDAO docTypeDAO,
			@NonNull final IDocumentBL documentBL,
			@NonNull final IProductPlanningDAO productPlanningDAO,
			@NonNull final IBPartnerOrgBL bpartnerOrgBL,
			@NonNull final IWarehouseBL warehouseBL,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final IOrderLineBL orderLineBL,
			@NonNull final AggregationConfig aggregationConfig,
			@NonNull final DDOrderCandidateProcessRequest request)
	{
		this.ddOrderLowLevelService = ddOrderLowLevelService;
		this.ddOrderCandidateService = ddOrderCandidateService;
		this.orgDAO = orgDAO;
		this.docTypeDAO = docTypeDAO;
		this.documentBL = documentBL;
		this.productPlanningDAO = productPlanningDAO;
		this.bpartnerOrgBL = bpartnerOrgBL;
		this.warehouseBL = warehouseBL;
		this.uomConversionBL = uomConversionBL;
		this.orderLineBL = orderLineBL;

		this.ddOrderUserNotificationProducer = DDOrderUserNotificationProducer.newInstance();
		this.aggregationConfig = aggregationConfig;
		this.request = request;
	}

	public void execute()
	{
		for (final DDOrderCandidate ddOrderCandidate : request.getCandidates())
		{
			addToAggregates(ddOrderCandidate);
		}

		for (final HeaderAggregate headerAggregate : aggregates.values())
		{
			createDDOrder(headerAggregate, request.getUserId());
		}
	}

	private void addToAggregates(@NonNull final DDOrderCandidate ddOrderCandidate)
	{
		// Resolve the source/target locators once here (candidate's own, else the warehouse default) so the
		// aggregation key always carries concrete, non-null locators and createLine just reads them.
		final LocatorId sourceLocatorId = resolveLocatorId(ddOrderCandidate.getSourceLocatorId(), ddOrderCandidate.getSourceWarehouseId());
		final LocatorId targetLocatorId = resolveLocatorId(ddOrderCandidate.getTargetLocatorId(), ddOrderCandidate.getTargetWarehouseId());

		final HeaderAggregationKey headerAggregationKey = HeaderAggregationKey.of(ddOrderCandidate, aggregationConfig, sourceLocatorId, targetLocatorId);

		aggregates.computeIfAbsent(headerAggregationKey, aggKey -> new HeaderAggregate(aggKey, aggregationConfig))
				.add(ddOrderCandidate, sourceLocatorId, targetLocatorId);
	}

	@NonNull
	private LocatorId resolveLocatorId(@Nullable final LocatorId candidateLocatorId, @NonNull final WarehouseId warehouseId)
	{
		if (candidateLocatorId != null)
		{
			return candidateLocatorId;
		}
		return defaultLocatorByWarehouse.computeIfAbsent(warehouseId, warehouseBL::getOrCreateDefaultLocatorId);
	}

	private void createDDOrder(@NonNull final HeaderAggregate headerAggregate,
	                           @NonNull final UserId userId)
	{
		if (!headerAggregate.isEligibleToCreate())
		{
			return;
		}

		I_DD_Order headerRecord = null;

		for (final LineAggregate lineAggregate : headerAggregate.getLines())
		{
			if (!lineAggregate.isEligibleToCreate())
			{
				continue;
			}

			if (headerRecord == null)
			{
				headerRecord = createHeaderRecord(headerAggregate.getKey(), headerAggregate.getUniqueSalesOrderIdOrNull());
			}

			createLine(lineAggregate, headerRecord);
		}

		if (headerRecord == null)
		{
			return;
		}

		documentBL.processEx(headerRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		ddOrderUserNotificationProducer.notifyGenerated(headerRecord);
	}

	private I_DD_Order createHeaderRecord(
			@NonNull final HeaderAggregationKey key,
			@Nullable final OrderId salesOrderId)
	{
		final ProductPlanningId productPlanningId = key.getProductPlanningId();
		final ProductPlanning productPlanning = productPlanningId != null ? productPlanningDAO.getById(productPlanningId) : null;
		final UserId plannerId = productPlanning != null ? productPlanning.getPlannerId() : null;

		final BPartnerLocationId orgBPartnerLocationId = bpartnerOrgBL.retrieveOrgBPLocationId(key.getOrgId());

		final I_DD_Order record = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		//DDOrderLowLevelDAO.ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID.setValue(record, key.getMaterialDispoGroupId());
		DDOrderLowLevelDAO.ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID.setValue(record, key.getTraceId());

		record.setAD_Org_ID(key.getOrgId().getRepoId());
		record.setMRP_Generated(true);
		record.setMRP_AllowCleanup(true);
		record.setPP_Plant_ID(ResourceId.toRepoId(key.getTargetPlantId()));
		record.setC_BPartner_ID(orgBPartnerLocationId != null ? orgBPartnerLocationId.getBpartnerId().getRepoId() : -1);
		record.setC_BPartner_Location_ID(orgBPartnerLocationId != null ? orgBPartnerLocationId.getRepoId() : -1);
		record.setAD_User_ID(UserId.toRepoId(plannerId)); // FIXME: improve performances/cache and retrieve Primary BP's User
		record.setSalesRep_ID(UserId.toRepoId(plannerId));

		record.setC_DocType_ID(getDocTypeId(key.getOrgId()).getRepoId());

		record.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		record.setDocAction(X_DD_Order.DOCACTION_Complete);
		record.setDateOrdered(Timestamp.from(key.getDateOrdered()));
		record.setDatePromised(Timestamp.from(key.getSupplyDate()));
		record.setPickDate(Timestamp.from(key.getDemandDate()));
		record.setM_Shipper_ID(key.getShipperId().getRepoId());
		record.setIsInDispute(false);
		record.setIsInTransit(false);
		record.setIsSimulated(key.isSimulated());

		if (key.isSimulated())
		{
			record.setProcessed(true);
		}

		final WarehouseId inTransitWarehouseId = warehouseBL.getInTransitWarehouseId(key.getOrgId());
		record.setM_Warehouse_ID(inTransitWarehouseId.getRepoId());
		record.setM_Warehouse_From_ID(key.getSourceWarehouseId().getRepoId());
		record.setM_Warehouse_To_ID(key.getTargetWarehouseId().getRepoId());

		record.setPP_Product_Planning_ID(ProductPlanningId.toRepoId(productPlanningId));

		final PPOrderRef forwardPPOrderRef = key.getForwardPPOrderRef();
		if (forwardPPOrderRef != null)
		{
			record.setForward_PP_Order_ID(PPOrderId.toRepoId(forwardPPOrderRef.getPpOrderId()));
			record.setForward_PP_Order_BOMLine_ID(PPOrderBOMLineId.toRepoId(forwardPPOrderRef.getPpOrderBOMLineId()));
		}

		record.setC_Order_ID(OrderId.toRepoId(salesOrderId));

		ddOrderLowLevelService.save(record);

		return record;
	}

	private DocTypeId getDocTypeId(final OrgId orgId)
	{
		final ClientId clientId = orgDAO.getClientIdByOrgId(orgId);

		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
				.adClientId(clientId.getRepoId())
				.adOrgId(orgId.getRepoId())
				.build());
	}

	private void createLine(final LineAggregate lineAggregate, final I_DD_Order header)
	{
		final LineAggregationKey key = lineAggregate.getKey();

		// Create DD Order Line
		final I_DD_OrderLine lineRecord = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, header);
		lineRecord.setAD_Org_ID(header.getAD_Org_ID());
		lineRecord.setDD_Order_ID(header.getDD_Order_ID());

		final OrderAndLineId salesOrderAndLineId = key.getSalesOrderLineId();
		lineRecord.setC_OrderLineSO_ID(OrderAndLineId.toOrderLineRepoId(salesOrderAndLineId));
		//lineRecord.setC_BPartner_ID(ddOrderLine.getBPartnerId());
		if (salesOrderAndLineId != null)
		{
			final BPartnerId bpartnerId = orderLineBL.getBPartnerId(salesOrderAndLineId).orElse(null);
			lineRecord.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		}

		final DistributionNetworkAndLineId distributionNetworkAndLineId = key.getDistributionNetworkAndLineId();
		lineRecord.setDD_NetworkDistribution_ID(distributionNetworkAndLineId != null ? distributionNetworkAndLineId.getNetworkId().getRepoId() : -1);
		lineRecord.setDD_NetworkDistributionLine_ID(distributionNetworkAndLineId != null ? distributionNetworkAndLineId.getLineId().getRepoId() : -1);

		//
		// Locator From/To — resolved (candidate's own, else warehouse default) already in the key
		lineRecord.setM_Locator_ID(key.getSourceLocatorId().getRepoId());
		lineRecord.setM_LocatorTo_ID(key.getTargetLocatorId().getRepoId());

		//
		// Product, UOM, Qty
		final ProductId productId = key.getProductId();
		final Quantity qty = lineAggregate.getQty();
		final Quantity qtyInStockUOM = uomConversionBL.convertToProductUOM(qty, productId);

		lineRecord.setM_Product_ID(productId.getRepoId());
		lineRecord.setC_UOM_ID(qty.getUomId().getRepoId());
		lineRecord.setQtyEntered(qty.toBigDecimal());
		lineRecord.setQtyOrdered(qtyInStockUOM.toBigDecimal());
		lineRecord.setTargetQty(qtyInStockUOM.toBigDecimal());
		lineRecord.setM_AttributeSetInstance_ID(key.getAttributeSetInstanceId().getRepoId());
		lineRecord.setM_AttributeSetInstanceTo_ID(key.getAttributeSetInstanceId().getRepoId());

		//
		// Dates
		lineRecord.setDateOrdered(header.getDateOrdered());
		lineRecord.setDatePromised(header.getDatePromised());

		//
		// Other flags
		lineRecord.setIsInvoiced(false);
		lineRecord.setDD_AllowPush(key.isAllowPush());
		lineRecord.setIsKeepTargetPlant(key.isKeepTargetPlant());

		//
		// Save DD Order Line
		ddOrderLowLevelService.save(lineRecord);
		final DDOrderAndLineId ddOrderAndLineId = DDOrderAndLineId.ofRepoIds(lineRecord.getDD_Order_ID(), lineRecord.getDD_OrderLine_ID());

		final DDOrderCandidateAllocList allocations = lineAggregate.getAllocations()
				.stream()
				.map(allocCandidate -> allocCandidate.toDDOrderCandidateAlloc()
						.ddOrderAndLineId(ddOrderAndLineId)
						.build())
				.collect(DDOrderCandidateAllocList.collect());

		ddOrderCandidateService.saveAndUpdateCandidates(allocations);
	}

	//
	//
	// ------------------------------------------------------------------------------------------
	//
	//
	@Value
	@Builder
	public static class AggregationConfig
	{
		boolean aggregateBySalesOrderId;
		boolean aggregateByPPOrderRef;
		boolean aggregateBySalesOrderLineId;
		boolean aggregateByProductId;
		boolean aggregateByLocatorFrom;
		boolean aggregateByLocatorTo;
	}
	//
	//
	// ------------------------------------------------------------------------------------------
	//
	//

	@Value
	@Builder
	private static class HeaderAggregationKey
	{
		@NonNull OrgId orgId;

		@NonNull Instant dateOrdered;
		@NonNull Instant demandDate;
		@NonNull Instant supplyDate;

		@NonNull WarehouseId sourceWarehouseId;
		@NonNull WarehouseId targetWarehouseId;
		@Nullable ResourceId targetPlantId;
		@NonNull ShipperId shipperId;

		boolean isSimulated;

		@Nullable OrderId salesOrderId;
		@Nullable PPOrderRef forwardPPOrderRef;

		@Nullable ProductPlanningId productPlanningId;

		@Nullable String traceId;

		@Nullable ProductId productId;

		// Locators are part of the header key only when the corresponding sysconfig is enabled
		// (DDOrderAggregation.header.byLocatorFrom / .byLocatorTo). When disabled they stay null here,
		// so candidates that differ only by locator share one DD_Order header. The DD_OrderLine still
		// carries the resolved locator unconditionally — that comes from LineAggregationKey, not this key.
		@Nullable LocatorId sourceLocatorId;
		@Nullable LocatorId targetLocatorId;

		public static HeaderAggregationKey of(
				@NonNull final DDOrderCandidate candidate,
				@NonNull final AggregationConfig aggregationConfig,
				@NonNull final LocatorId sourceLocatorId,
				@NonNull final LocatorId targetLocatorId)
		{
			final HeaderAggregationKeyBuilder keyBuilder = builder()
					.orgId(candidate.getOrgId())
					.dateOrdered(candidate.getDateOrdered())
					.demandDate(candidate.getDemandDate())
					.supplyDate(candidate.getSupplyDate())
					.sourceWarehouseId(candidate.getSourceWarehouseId())
					.targetWarehouseId(candidate.getTargetWarehouseId())
					.targetPlantId(candidate.getTargetPlantId())
					.shipperId(candidate.getShipperId())
					.isSimulated(candidate.isSimulated())
					.productPlanningId(candidate.getProductPlanningId())
					.traceId(candidate.getTraceId());
			if (aggregationConfig.isAggregateByLocatorFrom())
			{
				keyBuilder.sourceLocatorId(sourceLocatorId);
			}
			if (aggregationConfig.isAggregateByLocatorTo())
			{
				keyBuilder.targetLocatorId(targetLocatorId);
			}
			if (aggregationConfig.isAggregateBySalesOrderId())
			{
				keyBuilder.salesOrderId(candidate.getSalesOrderId());
			}
			if (aggregationConfig.isAggregateByPPOrderRef())
			{
				keyBuilder.forwardPPOrderRef(candidate.getForwardPPOrderRef());
			}
			if (aggregationConfig.isAggregateByProductId())
			{
				keyBuilder.productId(candidate.getProductId());
			}
			return keyBuilder.build();
		}
	}

	//
	//
	// ------------------------------------------------------------------------------------------
	//
	//

	@RequiredArgsConstructor
	private static class HeaderAggregate
	{
		@NonNull @Getter private final HeaderAggregationKey key;
		@NonNull private final LinkedHashMap<LineAggregationKey, LineAggregate> lineAggregates = new LinkedHashMap<>();
		@NonNull private final AggregationConfig aggregationConfig;

		public void add(@NonNull final DDOrderCandidate candidate, @NonNull final LocatorId sourceLocatorId, @NonNull final LocatorId targetLocatorId)
		{
			lineAggregates.computeIfAbsent(LineAggregationKey.of(candidate, aggregationConfig, sourceLocatorId, targetLocatorId), LineAggregate::new)
					.add(candidate);
		}

		public boolean isEligibleToCreate()
		{
			return !lineAggregates.isEmpty() && lineAggregates.values().stream().anyMatch(LineAggregate::isEligibleToCreate);
		}

		public Collection<LineAggregate> getLines() {return lineAggregates.values();}

		@Nullable
		public OrderId getUniqueSalesOrderIdOrNull()
		{
			final ImmutableSet<OrderId> orderIds = lineAggregates.keySet()
					.stream()
					.map(LineAggregationKey::getSalesOrderId)
					.filter(Objects::nonNull)
					.collect(ImmutableSet.toImmutableSet());

			return orderIds.size() == 1 ? orderIds.iterator().next() : null;
		}
	}

	//
	//
	// ------------------------------------------------------------------------------------------
	//
	//

	@Value
	@Builder
	private static class LineAggregationKey
	{
		@NonNull ProductId productId;
		@NonNull HUPIItemProductId hupiItemProductId;
		@NonNull AttributeSetInstanceId attributeSetInstanceId;
		@NonNull UomId uomId;
		@Nullable DistributionNetworkAndLineId distributionNetworkAndLineId;
		@Nullable OrderAndLineId salesOrderLineId;
		boolean isAllowPush;
		boolean isKeepTargetPlant;

		@NonNull LocatorId sourceLocatorId;
		@NonNull LocatorId targetLocatorId;

		public static LineAggregationKey of(
				final DDOrderCandidate candidate,
				final @NonNull AggregationConfig aggregationConfig,
				final @NonNull LocatorId sourceLocatorId,
				final @NonNull LocatorId targetLocatorId)
		{
			final LineAggregationKeyBuilder lineKeyBuilder = builder()
					.productId(candidate.getProductId())
					.hupiItemProductId(candidate.getHupiItemProductId())
					.attributeSetInstanceId(candidate.getAttributeSetInstanceId())
					.uomId(candidate.getQtyEntered().getUomId())
					.distributionNetworkAndLineId(candidate.getDistributionNetworkAndLineId())
					.isAllowPush(candidate.isAllowPush())
					.isKeepTargetPlant(candidate.isKeepTargetPlant())
					.sourceLocatorId(sourceLocatorId)
					.targetLocatorId(targetLocatorId);
			if (aggregationConfig.isAggregateBySalesOrderLineId())
			{
				lineKeyBuilder.salesOrderLineId(candidate.getSalesOrderLineId());
			}
			return lineKeyBuilder
					.build();
		}

		@Nullable
		public OrderId getSalesOrderId() {return salesOrderLineId != null ? salesOrderLineId.getOrderId() : null;}
	}

	//
	//
	// ------------------------------------------------------------------------------------------
	//
	//

	@Value
	@Builder
	private static class DDOrderCandidateAllocCandidate
	{
		@NonNull DDOrderCandidateId ddOrderCandidateId;
		@NonNull Quantity qty;

		public DDOrderCandidateAlloc.DDOrderCandidateAllocBuilder toDDOrderCandidateAlloc()
		{
			return DDOrderCandidateAlloc.builder()
					.ddOrderCandidateId(ddOrderCandidateId)
					.qty(qty);
		}
	}

	@Getter
	@RequiredArgsConstructor
	private static class LineAggregate
	{
		@NonNull private final LineAggregationKey key;
		@NonNull private Quantity qty;
		@NonNull private final ArrayList<DDOrderCandidateAllocCandidate> allocations = new ArrayList<>();

		public LineAggregate(@NonNull final LineAggregationKey key)
		{
			this.key = key;
			this.qty = Quantitys.zero(key.getUomId());
		}

		public void add(@NonNull final DDOrderCandidate candidate)
		{
			add(DDOrderCandidateAllocCandidate.builder()
					.ddOrderCandidateId(candidate.getIdNotNull())
					.qty(candidate.getQtyToProcess())
					.build());
		}

		private void add(@NonNull final DDOrderCandidateAllocCandidate alloc)
		{
			this.qty = this.qty.add(alloc.getQty());

			this.allocations.add(alloc);
		}

		public boolean isEligibleToCreate()
		{
			return qty.signum() != 0;
		}
	}
}
