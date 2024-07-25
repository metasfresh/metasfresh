package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order_Candidate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class DDOrderCandidateRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private static final ModelDynAttributeAccessor<I_DD_Order_Candidate, String> DYNATTR_TraceId = new ModelDynAttributeAccessor<>("de.metas.material", "TraceId", String.class);
	private static final ModelDynAttributeAccessor<I_DD_Order_Candidate, MaterialDispoGroupId> DYNATTR_GroupId = new ModelDynAttributeAccessor<>("de.metas.material", "GroupId", MaterialDispoGroupId.class);

	public DDOrderCandidate getById(@NonNull final DDOrderCandidateId id)
	{
		final I_DD_Order_Candidate record = InterfaceWrapperHelper.load(id, I_DD_Order_Candidate.class);
		return fromRecord(record);
	}

	public void save(@NonNull final DDOrderCandidate candidate)
	{
		final I_DD_Order_Candidate record = candidate.getId() != null
				? InterfaceWrapperHelper.load(candidate.getId(), I_DD_Order_Candidate.class)
				: InterfaceWrapperHelper.newInstance(I_DD_Order_Candidate.class);

		updateRecord(record, candidate);
		InterfaceWrapperHelper.save(record);
		candidate.setId(DDOrderCandidateId.ofRepoId(record.getDD_Order_Candidate_ID()));
	}

	private static void updateRecord(final I_DD_Order_Candidate record, DDOrderCandidate from)
	{
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setDateOrdered(TimeUtil.asTimestamp(from.getDateOrdered()));
		record.setDatePromised(TimeUtil.asTimestamp(from.getDatePromised()));

		record.setM_Warehouse_From_ID(from.getSourceWarehouseId().getRepoId());
		record.setM_WarehouseTo_ID(from.getTargetWarehouseId().getRepoId());
		record.setPP_Plant_To_ID(ResourceId.toRepoId(from.getTargetPlantId()));

		//
		// Product, ASI, UOM, Qty
		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setM_HU_PI_Item_Product_ID(from.getHupiItemProductId().getRepoId());
		record.setC_UOM_ID(from.getQty().getUomId().getRepoId());
		record.setQtyOrdered(from.getQty().toBigDecimal());
		record.setQtyEntered(from.getQty().toBigDecimal());
		record.setQtyEnteredTU(BigDecimal.valueOf(from.getQtyTUs()));

		record.setM_AttributeSetInstance_ID(from.getAttributeSetInstanceId().getRepoId());

		//
		// From/To
		record.setM_Warehouse_From_ID(from.getSourceWarehouseId().getRepoId());
		record.setM_WarehouseTo_ID(from.getTargetWarehouseId().getRepoId());
		record.setM_Shipper_ID(from.getShipperId().getRepoId());

		//
		// Flags
		record.setIsActive(true);
		record.setIsSimulated(from.isSimulated());

		//
		// Forward document references
		record.setC_OrderLineSO_ID(OrderLineId.toRepoId(from.getSalesOrderLineId()));
		updateRecord(record, from.getPpOrderRef());

		//
		// Planning master data references
		final DistributionNetworkAndLineId distributionNetworkAndLineId = from.getDistributionNetworkAndLineId();
		record.setDD_NetworkDistribution_ID(distributionNetworkAndLineId != null ? distributionNetworkAndLineId.getNetworkId().getRepoId() : -1);
		record.setDD_NetworkDistributionLine_ID(distributionNetworkAndLineId != null ? distributionNetworkAndLineId.getLineId().getRepoId() : -1);
		record.setPP_Product_Planning_ID(ProductPlanningId.toRepoId(from.getProductPlanningId()));

		DYNATTR_TraceId.setValue(record, from.getTraceId());
		DYNATTR_GroupId.setValue(record, from.getMaterialDispoGroupId());
	}

	private static void updateRecord(@NonNull final I_DD_Order_Candidate record, @Nullable final PPOrderRef from)
	{
		record.setForward_PP_Order_ID(from != null ? PPOrderId.toRepoId(from.getPpOrderId()) : -1);
		record.setForward_PP_Order_BOMLine_ID(from != null ? PPOrderBOMLineId.toRepoId(from.getPpOrderBOMLineId()) : -1);
		record.setForward_PP_Order_Candidate_ID(from != null ? from.getPpOrderCandidateId() : -1);
		record.setForward_PP_OrderLine_Candidate_ID(from != null ? from.getPpOrderLineCandidateId() : -1);
	}

	public static DDOrderCandidate fromRecord(final I_DD_Order_Candidate record)
	{
		return DDOrderCandidate.builder()
				.id(DDOrderCandidateId.ofRepoId(record.getDD_Order_Candidate_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				//
				.dateOrdered(record.getDateOrdered().toInstant())
				.datePromised(record.getDatePromised().toInstant())
				//
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.hupiItemProductId(HUPIItemProductId.ofRepoIdOrNone(record.getM_HU_PI_Item_Product_ID()))
				.qty(Quantitys.of(record.getQtyEntered(), UomId.ofRepoId(record.getC_UOM_ID())))
				.qtyTUs(record.getQtyEnteredTU().intValueExact())
				//
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				//
				.sourceWarehouseId(WarehouseId.ofRepoId(record.getM_Warehouse_From_ID()))
				.targetWarehouseId(WarehouseId.ofRepoId(record.getM_WarehouseTo_ID()))
				.targetPlantId(ResourceId.ofRepoIdOrNull(record.getPP_Plant_To_ID()))
				.shipperId(ShipperId.ofRepoId(record.getM_Shipper_ID()))
				//
				.isSimulated(record.isSimulated())
				// isAllowPush
				// isKeepTargetPlant
				//
				.salesOrderLineId(OrderLineId.ofRepoIdOrNull(record.getC_OrderLineSO_ID()))
				//
				.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIdsOrNull(record.getDD_NetworkDistribution_ID(), record.getDD_NetworkDistributionLine_ID()))
				.productPlanningId(ProductPlanningId.ofRepoIdOrNull(record.getPP_Product_Planning_ID()))
				//
				.traceId(DYNATTR_TraceId.getValue(record))
				.materialDispoGroupId(DYNATTR_GroupId.getValue(record))
				.ppOrderRef(extractPPOrderRef(record))
				//
				.build();
	}

	private static PPOrderRef extractPPOrderRef(final I_DD_Order_Candidate record)
	{
		final int ppOrderCandidateId = record.getForward_PP_Order_Candidate_ID();
		final PPOrderId ppOrderId = PPOrderId.ofRepoIdOrNull(record.getForward_PP_Order_ID());
		if (ppOrderCandidateId <= 0 && ppOrderId == null)
		{
			return null;
		}

		return PPOrderRef.builder()
				.ppOrderCandidateId(ppOrderCandidateId)
				.ppOrderLineCandidateId(record.getForward_PP_OrderLine_Candidate_ID())
				.ppOrderId(ppOrderId)
				.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(record.getForward_PP_Order_BOMLine_ID()))
				.build();
	}

	public List<DDOrderCandidate> list(@NonNull final DDOrderCandidateQuery query)
	{
		return stream(query).collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Stream<DDOrderCandidate> stream(final @NonNull DDOrderCandidateQuery query)
	{
		return toSqlQuery(query)
				.create()
				.stream()
				.map(DDOrderCandidateRepository::fromRecord);
	}

	public void delete(@NonNull final DDOrderCandidateQuery query)
	{
		toSqlQuery(query)
				.create()
				.delete();
	}

	private IQueryBuilder<I_DD_Order_Candidate> toSqlQuery(@NonNull final DDOrderCandidateQuery query)
	{
		final IQueryBuilder<I_DD_Order_Candidate> queryBuilder = queryBL.createQueryBuilder(I_DD_Order_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID);

		if (query.getProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_M_Product_ID, query.getProductId());
		}
		if (query.getSourceWarehouseId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_M_Warehouse_From_ID, query.getSourceWarehouseId());
		}
		if (query.getTargetWarehouseId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_M_WarehouseTo_ID, query.getTargetWarehouseId());
		}
		if (query.getSalesOrderLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_C_OrderLineSO_ID, query.getSalesOrderLineId());
		}
		if (query.getProcessed() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Processed, query.getProcessed());
		}
		if (query.getIsSimulated() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_IsSimulated, query.getIsSimulated());
		}

		return queryBuilder;
	}
}
