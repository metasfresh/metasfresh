package de.metas.handlingunits.pporder.api.issue_schedule;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_PP_Order_IssueSchedule;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Repository
public class PPOrderIssueScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PPOrderIssueSchedule getById(@NonNull final PPOrderIssueScheduleId id)
	{
		final I_PP_Order_IssueSchedule record = InterfaceWrapperHelper.load(id, I_PP_Order_IssueSchedule.class);
		return toPPOrderIssueSchedule(record);
	}

	public PPOrderIssueSchedule createSchedule(@NonNull final PPOrderIssueScheduleCreateRequest request)
	{
		final I_PP_Order_IssueSchedule record = InterfaceWrapperHelper.newInstance(I_PP_Order_IssueSchedule.class);
		record.setPP_Order_ID(request.getPpOrderId().getRepoId());
		record.setPP_Order_BOMLine_ID(request.getPpOrderBOMLineId().getRepoId());
		record.setSeqNo(request.getSeqNo().toInt());

		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setC_UOM_ID(request.getQtyToIssue().getUomId().getRepoId());
		record.setQtyToIssue(request.getQtyToIssue().toBigDecimal());
		record.setIssueFrom_HU_ID(request.getIssueFromHUId().getRepoId());
		record.setIssueFrom_Warehouse_ID(request.getIssueFromLocatorId().getWarehouseId().getRepoId());
		record.setIssueFrom_Locator_ID(request.getIssueFromLocatorId().getRepoId());

		record.setIsAlternativeHU(request.isAlternativeIssue());

		// Issued:
		record.setQtyIssued(BigDecimal.ZERO);
		// record.setQtyReject(null);
		// record.setRejectReason(null);

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
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				//
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyToIssue(Quantitys.create(record.getQtyToIssue(), uomId))
				.issueFromHUId(HuId.ofRepoId(record.getIssueFrom_HU_ID()))
				.issueFromLocatorId(LocatorId.ofRepoId(record.getIssueFrom_Warehouse_ID(), record.getIssueFrom_Locator_ID()))
				.isAlternativeIssue(record.isAlternativeHU())
				//
				.issued(extractIssued(record))
				//
				.build();
	}

	@Nullable
	private static PPOrderIssueSchedule.Issued extractIssued(final I_PP_Order_IssueSchedule record)
	{
		final Quantity qtyIssued = extractQtyIssued(record);
		final QtyRejectedWithReason qtyRejected = extractQtyRejected(record);

		if (qtyIssued.signum() != 0 || qtyRejected != null)
		{
			return PPOrderIssueSchedule.Issued.builder()
					.qtyIssued(qtyIssued)
					.qtyRejected(qtyRejected)
					.build();
		}
		else
		{
			return null;
		}
	}

	private static Quantity extractQtyIssued(final I_PP_Order_IssueSchedule record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		return Quantitys.create(record.getQtyIssued(), uomId);
	}

	@Nullable
	private static QtyRejectedWithReason extractQtyRejected(final I_PP_Order_IssueSchedule record)
	{
		final QtyRejectedReasonCode reasonCode = QtyRejectedReasonCode.ofNullableCode(record.getRejectReason()).orElse(null);
		final BigDecimal qtyReject = record.getQtyReject();

		if (qtyReject.signum() != 0 && reasonCode != null)
		{
			final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
			return QtyRejectedWithReason.of(Quantitys.create(qtyReject, uomId), reasonCode);
		}
		else
		{
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

	public void saveChanges(final PPOrderIssueSchedule issueSchedule)
	{
		final I_PP_Order_IssueSchedule record = InterfaceWrapperHelper.load(issueSchedule.getId(), I_PP_Order_IssueSchedule.class);

		final PPOrderIssueSchedule.Issued issued = issueSchedule.getIssued();
		final boolean processed = issued != null;
		final Quantity qtyIssued = issued != null ? issued.getQtyIssued() : null;
		final QtyRejectedWithReason qtyRejected = issued != null ? issued.getQtyRejected() : null;

		record.setSeqNo(issueSchedule.getSeqNo().toInt());
		record.setProcessed(processed);
		record.setQtyIssued(qtyIssued != null ? qtyIssued.toBigDecimal() : BigDecimal.ZERO);
		record.setQtyReject(qtyRejected != null ? qtyRejected.toBigDecimal() : BigDecimal.ZERO);
		record.setRejectReason(qtyRejected != null ? qtyRejected.getReasonCode().getCode() : null);

		InterfaceWrapperHelper.save(record);
	}

	public void deleteNotProcessedByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		queryBL.createQueryBuilder(I_PP_Order_IssueSchedule.class)
				.addEqualsFilter(I_PP_Order_IssueSchedule.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addEqualsFilter(I_PP_Order_IssueSchedule.COLUMNNAME_Processed, false)
				.create()
				.delete();
	}

	public void deleteNotProcessedById(@NonNull final PPOrderIssueScheduleId issueScheduleId)
	{
		queryBL.createQueryBuilder(I_PP_Order_IssueSchedule.class)
				.addEqualsFilter(I_PP_Order_IssueSchedule.COLUMNNAME_PP_Order_IssueSchedule_ID, issueScheduleId)
				.addEqualsFilter(I_PP_Order_IssueSchedule.COLUMNNAME_Processed, false)
				.create()
				.delete();
	}


	public boolean matchesByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		return queryBL.createQueryBuilder(I_PP_Order_IssueSchedule.class)
				.addEqualsFilter(I_PP_Order_IssueSchedule.COLUMNNAME_PP_Order_ID, ppOrderId)
				.create()
				.anyMatch();
	}
}
