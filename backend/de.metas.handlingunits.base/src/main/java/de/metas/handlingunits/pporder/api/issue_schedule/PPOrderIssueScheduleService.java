package de.metas.handlingunits.pporder.api.issue_schedule;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IssueCandidateGeneratedBy;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Service;

@Service
public class PPOrderIssueScheduleService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final PPOrderIssueScheduleRepository issueScheduleRepository;

	public PPOrderIssueScheduleService(
			@NonNull final PPOrderIssueScheduleRepository issueScheduleRepository)
	{
		this.issueScheduleRepository = issueScheduleRepository;
	}

	public ImmutableList<PPOrderIssueSchedule> getByOrderId(final PPOrderId ppOrderId)
	{
		return issueScheduleRepository.getByOrderId(ppOrderId);
	}

	public PPOrderIssueSchedule createSchedule(final PPOrderIssueScheduleCreateRequest request)
	{
		return issueScheduleRepository.createSchedule(request);
	}

	public PPOrderIssueSchedule issue(@NonNull final PPOrderIssueScheduleProcessRequest request)
	{
		final PPOrderIssueScheduleId issueScheduleId = request.getIssueScheduleId();
		PPOrderIssueSchedule issueSchedule = issueScheduleRepository.getById(issueScheduleId);
		if (issueSchedule.getIssued() != null)
		{
			throw new AdempiereException("Already issued");
		}

		//
		// Qty Issued
		final I_C_UOM uom = issueSchedule.getQtyToIssue().getUOM();
		final Quantity qtyIssued = Quantity.of(request.getQtyIssued(), uom);
		if (qtyIssued.signum() != 0)
		{
			final PPOrderId ppOrderId = request.getPpOrderId();
			final ProductId productId = issueSchedule.getProductId();
			final I_M_HU issueFromHU = handlingUnitsBL.getById(issueSchedule.getIssueFromHUId());

			huPPOrderBL.createIssueProducer(ppOrderId)
					.fixedQtyToIssue(qtyIssued)
					.processCandidates(HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy.ALWAYS)
					.generatedBy(IssueCandidateGeneratedBy.ofIssueScheduleId(issueScheduleId))
					.createIssues(
							HUTransformService.newInstance().husToNewCUs(
									HUTransformService.HUsToNewCUsRequest.builder()
											.sourceHU(issueFromHU)
											.productId(productId)
											.qtyCU(qtyIssued)
											.reservedVHUsPolicy(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED)
											.build()
							)
					);
		}

		//
		// Qty Rejected
		final QtyRejectedWithReason qtyRejected = request.getQtyRejectedReasonCode() != null
				? QtyRejectedWithReason.of(Quantity.of(request.getQtyRejected(), uom), request.getQtyRejectedReasonCode())
				: null;

		//
		// Update the issue schedule
		issueSchedule = issueSchedule.withIssued(PPOrderIssueSchedule.Issued.builder()
				.qtyIssued(qtyIssued)
				.qtyRejected(qtyRejected)
				.build());
		issueScheduleRepository.saveChanges(issueSchedule);
		return issueSchedule;
	}
}
