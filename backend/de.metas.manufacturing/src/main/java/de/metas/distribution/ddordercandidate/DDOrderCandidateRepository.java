package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.PInstanceId;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order_Candidate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;
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

	public Collection<DDOrderCandidate> getByIds(@NonNull final Set<DDOrderCandidateId> ids)
	{
		return InterfaceWrapperHelper.loadByRepoIdAwares(ids, I_DD_Order_Candidate.class, DDOrderCandidateRepository::fromRecord);
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
		record.setDateOrdered(Timestamp.from(from.getDateOrdered()));
		record.setSupplyDate(Timestamp.from(from.getSupplyDate()));
		record.setDemandDate(Timestamp.from(from.getDemandDate()));

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
		record.setQtyProcessed(from.getQtyProcessed().toBigDecimal());
		record.setQtyToProcess(from.getQtyToProcess().toBigDecimal());

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
		record.setProcessed(from.isProcessed());

		//
		// Forward document references
		record.setC_BPartner_ID(BPartnerId.toRepoId(from.getCustomerId()));
		record.setC_OrderSO_ID(OrderAndLineId.toOrderRepoId(from.getSalesOrderLineId()));
		record.setC_OrderLineSO_ID(OrderAndLineId.toOrderLineRepoId(from.getSalesOrderLineId()));
		updateRecord(record, from.getForwardPPOrderRef());

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
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return DDOrderCandidate.builder()
				.id(DDOrderCandidateId.ofRepoId(record.getDD_Order_Candidate_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				//
				.dateOrdered(record.getDateOrdered().toInstant())
				.supplyDate(record.getSupplyDate().toInstant())
				.demandDate(record.getDemandDate().toInstant())
				//
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.hupiItemProductId(HUPIItemProductId.ofRepoIdOrNone(record.getM_HU_PI_Item_Product_ID()))
<<<<<<< HEAD
				.qty(Quantitys.create(record.getQtyEntered(), uomId))
				.qtyTUs(record.getQtyEnteredTU().intValueExact())
				.qtyProcessed(Quantitys.create(record.getQtyProcessed(), uomId))
=======
				.qty(Quantitys.of(record.getQtyEntered(), uomId))
				.qtyTUs(record.getQtyEnteredTU().intValueExact())
				.qtyProcessed(Quantitys.of(record.getQtyProcessed(), uomId))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
				.processed(record.isProcessed())
				//
				.customerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.salesOrderLineId(OrderAndLineId.ofRepoIdsOrNull(record.getC_OrderSO_ID(), record.getC_OrderLineSO_ID()))
				//
				.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIdsOrNull(record.getDD_NetworkDistribution_ID(), record.getDD_NetworkDistributionLine_ID()))
				.productPlanningId(ProductPlanningId.ofRepoIdOrNull(record.getPP_Product_Planning_ID()))
				//
				.traceId(DYNATTR_TraceId.getValue(record))
				.materialDispoGroupId(DYNATTR_GroupId.getValue(record))
				.forwardPPOrderRef(extractForwardPPOrderRef(record))
				//
				.build();
	}

	@Nullable
	private static PPOrderRef extractForwardPPOrderRef(final I_DD_Order_Candidate record)
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
				.delete(!query.isDeleteEvenIfProceed());
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
		if (query.getExcludePPOrderId() != null)
		{
			queryBuilder.addNotEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_ID, query.getExcludePPOrderId());
		}
		if (query.getPpOrderCandidateId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_Candidate_ID, query.getPpOrderCandidateId());
		}
		if (query.getDdOrderCandidateId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID, query.getDdOrderCandidateId());
		}
		if (query.getPpOrderBOMLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Forward_PP_Order_BOMLine_ID, query.getPpOrderBOMLineId());
		}

		return queryBuilder;
	}

	public void updateByQuery(@NonNull final DDOrderCandidateQuery query, @NonNull UnaryOperator<DDOrderCandidate> updater)
	{
		final List<I_DD_Order_Candidate> records = toSqlQuery(query).create().list();
		for (final I_DD_Order_Candidate record : records)
		{
			final DDOrderCandidate candidate = fromRecord(record);
			final DDOrderCandidate changedCandidate = updater.apply(candidate);
			if (Objects.equals(candidate, changedCandidate))
			{
				continue;
			}

			updateRecord(record, changedCandidate);
			InterfaceWrapperHelper.save(record);
		}
	}

	public PInstanceId createSelection(@NonNull final Collection<DDOrderCandidateId> ids)
	{
		if (ids.isEmpty())
		{
			throw new AdempiereException("no IDs provided");
		}

		return queryBL.createQueryBuilder(I_DD_Order_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID, ids)
				.create()
				.createSelection();
	}

	public List<DDOrderCandidate> getBySelectionId(@NonNull final PInstanceId selectionId)
	{
		return queryBL.createQueryBuilder(I_DD_Order_Candidate.class)
				.orderBy(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID) // just to have a predictable order
				.setOnlySelection(selectionId)
				.create()
				.stream()
				.map(DDOrderCandidateRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}
}
