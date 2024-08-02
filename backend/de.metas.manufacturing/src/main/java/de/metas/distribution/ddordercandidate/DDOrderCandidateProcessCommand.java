package de.metas.distribution.ddordercandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.distribution.ddorder.DDOrderAndLineId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
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
import de.metas.order.OrderLineId;
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
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

@Builder
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

	//
	// Params
	@NonNull private final DDOrderCandidateProcessRequest request;

	//
	// State
	private final LinkedHashMap<HeaderAggregationKey, HeaderAggregate> aggregates = new LinkedHashMap<>();

	public void execute()
	{
		request.getCandidates().forEach(this::addToAggregates);
		aggregates.values().forEach(this::createDDOrder);
	}

	private void addToAggregates(DDOrderCandidate ddOrderCandidate)
	{
		final HeaderAggregationKey headerAggregationKey = HeaderAggregationKey.of(ddOrderCandidate);

		aggregates.computeIfAbsent(headerAggregationKey, HeaderAggregate::new)
				.add(ddOrderCandidate);
	}

	private void createDDOrder(@NonNull final HeaderAggregate headerAggregate)
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
				headerRecord = createHeaderRecord(headerAggregate.getKey());
			}

			createLine(lineAggregate, headerRecord);
		}

		if (headerRecord == null)
		{
			return;
		}

		documentBL.processEx(headerRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	private I_DD_Order createHeaderRecord(final HeaderAggregationKey key)
	{
		final ProductPlanning productPlanning = productPlanningDAO.getById(key.getProductPlanningId());

		final BPartnerLocationId orgBPartnerLocationId = bpartnerOrgBL.retrieveOrgBPLocationId(key.getOrgId());

		final I_DD_Order record = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		// DDOrderLowLevelDAO.ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID.setValue(record, key.getMaterialDispoGroupId());
		// DDOrderLowLevelDAO.ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID.setValue(record, ddOrderRequestedEventTrace);

		record.setAD_Org_ID(key.getOrgId().getRepoId());
		record.setMRP_Generated(true);
		record.setMRP_AllowCleanup(true);
		record.setPP_Plant_ID(ResourceId.toRepoId(key.getTargetPlantId()));
		record.setC_BPartner_ID(orgBPartnerLocationId != null ? orgBPartnerLocationId.getBpartnerId().getRepoId() : -1);
		record.setC_BPartner_Location_ID(orgBPartnerLocationId != null ? orgBPartnerLocationId.getRepoId() : -1);
		record.setAD_User_ID(UserId.toRepoId(productPlanning.getPlannerId())); // FIXME: improve performances/cache and retrieve Primary BP's User
		record.setSalesRep_ID(UserId.toRepoId(productPlanning.getPlannerId()));

		record.setC_DocType_ID(getDocTypeId(key.getOrgId()).getRepoId());

		final WarehouseId inTransitWarehouseId = warehouseBL.getInTransitWarehouseId(key.getOrgId());
		record.setM_Warehouse_ID(inTransitWarehouseId.getRepoId());

		record.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		record.setDocAction(X_DD_Order.DOCACTION_Complete);
		record.setDateOrdered(Timestamp.from(key.getDateOrdered()));
		record.setDatePromised(Timestamp.from(key.getSupplyDate()));
		record.setM_Shipper_ID(key.getShipperId().getRepoId());
		record.setIsInDispute(false);
		record.setIsInTransit(false);
		record.setIsSimulated(key.isSimulated());

		if (key.isSimulated())
		{
			record.setProcessed(true);
		}

		record.setM_Warehouse_From_ID(key.getSourceWarehouseId().getRepoId());
		record.setM_Warehouse_To_ID(key.getTargetWarehouseId().getRepoId());

		record.setPP_Product_Planning_ID(ProductPlanningId.toRepoId(productPlanning.getId()));

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

	public void createLine(final LineAggregate lineAggregate, final I_DD_Order header)
	{
		final LineAggregationKey key = lineAggregate.getKey();

		// Create DD Order Line
		final I_DD_OrderLine lineRecord = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, header);
		lineRecord.setAD_Org_ID(header.getAD_Org_ID());
		lineRecord.setDD_Order_ID(header.getDD_Order_ID());

		final OrderLineId salesOrderLineId = key.getSalesOrderLineId();
		lineRecord.setC_OrderLineSO_ID(OrderLineId.toRepoId(salesOrderLineId));
		//lineRecord.setC_BPartner_ID(ddOrderLine.getBPartnerId());
		if (salesOrderLineId != null)
		{
			final BPartnerId bpartnerId = orderLineBL.getBPartnerId(salesOrderLineId).orElse(null);
			lineRecord.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		}

		final DistributionNetworkAndLineId distributionNetworkAndLineId = key.getDistributionNetworkAndLineId();
		lineRecord.setDD_NetworkDistribution_ID(distributionNetworkAndLineId != null ? distributionNetworkAndLineId.getNetworkId().getRepoId() : -1);
		lineRecord.setDD_NetworkDistributionLine_ID(distributionNetworkAndLineId != null ? distributionNetworkAndLineId.getLineId().getRepoId() : -1);

		// get supply source warehouse and locator
		final LocatorId locatorFromId = warehouseBL.getOrCreateDefaultLocatorId(WarehouseId.ofRepoId(header.getM_Warehouse_From_ID()));

		// get supply target warehouse and locator
		final LocatorId locatorToId = warehouseBL.getOrCreateDefaultLocatorId(WarehouseId.ofRepoId(header.getM_Warehouse_ID()));

		//
		// Locator From/To
		lineRecord.setM_Locator_ID(locatorFromId.getRepoId());
		lineRecord.setM_LocatorTo_ID(locatorToId.getRepoId());

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
	private static class HeaderAggregationKey
	{
		@NonNull OrgId orgId;

		@NonNull Instant dateOrdered;
		@NonNull Instant supplyDate;

		@NonNull WarehouseId sourceWarehouseId;
		@NonNull WarehouseId targetWarehouseId;
		@Nullable ResourceId targetPlantId;
		@NonNull ShipperId shipperId;

		boolean isSimulated;

		@Nullable PPOrderRef ppOrderRef;

		@Nullable ProductPlanningId productPlanningId;

		public static HeaderAggregationKey of(@NonNull final DDOrderCandidate candidate)
		{
			return builder()
					.orgId(candidate.getOrgId())
					.dateOrdered(candidate.getDateOrdered())
					.supplyDate(candidate.getSupplyDate())
					.sourceWarehouseId(candidate.getSourceWarehouseId())
					.targetWarehouseId(candidate.getTargetWarehouseId())
					.targetPlantId(candidate.getTargetPlantId())
					.shipperId(candidate.getShipperId())
					.isSimulated(candidate.isSimulated())
					.ppOrderRef(candidate.getPpOrderRef())
					.productPlanningId(candidate.getProductPlanningId())
					.build();
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

		public void add(@NonNull final DDOrderCandidate candidate)
		{
			lineAggregates.computeIfAbsent(LineAggregationKey.of(candidate), LineAggregate::new)
					.add(candidate);
		}

		public boolean isEligibleToCreate()
		{
			return !lineAggregates.isEmpty() && lineAggregates.values().stream().anyMatch(LineAggregate::isEligibleToCreate);
		}

		public Collection<LineAggregate> getLines() {return lineAggregates.values();}
	}

	//
	//
	// ------------------------------------------------------------------------------------------
	//
	//

	@Value
	@Builder
	static class LineAggregationKey
	{
		@NonNull ProductId productId;
		@NonNull HUPIItemProductId hupiItemProductId;
		@NonNull AttributeSetInstanceId attributeSetInstanceId;
		@NonNull UomId uomId;
		@Nullable DistributionNetworkAndLineId distributionNetworkAndLineId;
		@Nullable OrderLineId salesOrderLineId;
		boolean isAllowPush;
		boolean isKeepTargetPlant;

		public static LineAggregationKey of(final DDOrderCandidate candidate)
		{
			return builder()
					.productId(candidate.getProductId())
					.hupiItemProductId(candidate.getHupiItemProductId())
					.attributeSetInstanceId(candidate.getAttributeSetInstanceId())
					.uomId(candidate.getQty().getUomId())
					.distributionNetworkAndLineId(candidate.getDistributionNetworkAndLineId())
					.salesOrderLineId(candidate.getSalesOrderLineId())
					.isAllowPush(candidate.isAllowPush())
					.isKeepTargetPlant(candidate.isKeepTargetPlant())
					.build();
		}
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
	static class LineAggregate
	{
		@NonNull private final LineAggregationKey key;
		@NonNull private Quantity qty;
		@NonNull private final ArrayList<DDOrderCandidateAllocCandidate> allocations = new ArrayList<>();

		public LineAggregate(@NonNull final LineAggregationKey key)
		{
			this.key = key;
			this.qty = Quantitys.createZero(key.getUomId());
		}

		public void add(@NonNull final DDOrderCandidate candidate)
		{
			add(DDOrderCandidateAllocCandidate.builder()
					.ddOrderCandidateId(candidate.getIdNotNull())
					.qty(candidate.getQty())
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
