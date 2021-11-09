package de.metas.manufacturing.order;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_IssueSchedule;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Repository
public class PPOrderIssueScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PPOrderIssueSchedule createSchedule(@NonNull final PPOrderIssueScheduleCreateRequest request)
	{
		final I_PP_Order_IssueSchedule record = InterfaceWrapperHelper.newInstance(I_PP_Order_IssueSchedule.class);
		record.setPP_Order_ID(request.getPpOrderId().getRepoId());
		record.setPP_Order_BOMLine_ID(request.getPpOrderBOMLineId().getRepoId());
		record.setSeqNo(request.getSeqNo());

		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setC_UOM_ID(request.getQtyToIssue().getUomId().getRepoId());
		record.setQtyToIssue(request.getQtyToIssue().toBigDecimal());
		record.setIssueFrom_HU_ID(request.getIssueFromHUId().getRepoId());
		record.setIssueFrom_Warehouse_ID(request.getIssueFromLocatorId().getWarehouseId().getRepoId());
		record.setIssueFrom_Locator_ID(request.getIssueFromLocatorId().getRepoId());

		// Issued:
		record.setQtyIssued(BigDecimal.ZERO);
		// record.setRejectReason(null);
		// record.setIssued_HU_ID(-1);
		// record.setPP_Cost_Collector_ID(-1);

		InterfaceWrapperHelper.save(record);
		return toPPOrderIssueSchedule(record);
	}

	private static PPOrderIssueSchedule toPPOrderIssueSchedule(final I_PP_Order_IssueSchedule record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return PPOrderIssueSchedule.builder()
				.id(PPOrderIssueScheduleId.ofRepoId(record.getPP_Order_IssueSchedule_ID()))
				.ppOrderId(PPOrderId.ofRepoId(record.getPP_Order_ID()))
				.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoId(record.getPP_Order_BOMLine_ID()))
				.seqNo(record.getSeqNo())
				//
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyToIssue(Quantitys.create(record.getQtyToIssue(), uomId))
				.issueFromHUId(HuId.ofRepoId(record.getIssueFrom_HU_ID()))
				.issueFromLocatorId(LocatorId.ofRepoId(record.getIssueFrom_Warehouse_ID(), record.getIssueFrom_Locator_ID()))
				//
				.issued(extractIssued(record))
				//
				.build();
	}

	@Nullable
	private static PPOrderIssueSchedule.Issued extractIssued(final I_PP_Order_IssueSchedule record)
	{
		final PPCostCollectorId ppCostCollectorId = PPCostCollectorId.ofRepoIdOrNull(record.getPP_Cost_Collector_ID());
		if (ppCostCollectorId != null)
		{
			final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

			return PPOrderIssueSchedule.Issued.builder()
					.qtyIssued(Quantitys.create(record.getQtyIssued(), uomId))
					.qtyRejectedReasonCode(QtyRejectedReasonCode.ofNullableCode(record.getRejectReason()).orElse(null))
					.actualIssuedHUId(HuId.ofRepoId(record.getIssued_HU_ID()))
					.ppCostCollectorId(ppCostCollectorId)
					.build();
		}
		else
		{
			final HuId actualIssuedHUId = HuId.ofRepoIdOrNull(record.getIssued_HU_ID());
			Check.assumeNull(actualIssuedHUId, "actualIssuedHUId shall be null for {}", record);

			return null;
		}

	}

	public ImmutableList<PPOrderIssueSchedule> getByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		return queryBL.createQueryBuilder(I_PP_Order_IssueSchedule.class)
				.addEqualsFilter(I_PP_Order_IssueSchedule.COLUMNNAME_PP_Order_ID, ppOrderId)
				.orderBy(I_PP_Order_IssueSchedule.COLUMNNAME_SeqNo)
				.create()
				.stream()
				.map(PPOrderIssueScheduleRepository::toPPOrderIssueSchedule)
				.collect(ImmutableList.toImmutableList());
	}
}
