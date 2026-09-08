package de.metas.handlingunits.pporder.api.issue_schedule;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.ReservedHUsPolicy;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.UpdateHUQtyRequest;
import de.metas.handlingunits.attribute.weightable.PlainWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IssueCandidateGeneratedBy;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.weighting.WeightHUCommand;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PPOrderIssueScheduleService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final PPOrderIssueScheduleRepository issueScheduleRepository;
	private final HUQtyService huQtyService;

	public static final AdMessageKey MSG_AlreadyIssued = AdMessageKey.of("de.metas.handlingunits.pporder.AlreadyIssuedError");
	public static final AdMessageKey MSG_EmptiedHUInventoryDescription = AdMessageKey.of("de.metas.handlingunits.pporder.EmptiedHUInventoryDescription");

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
			throw new AdempiereException(MSG_AlreadyIssued)
					.markAsUserValidationError()
					.setParameter("request", request)
					.setParameter("issueSchedule", issueSchedule);
		}

		//
		// Qty Rejected (resolved early: a weight confirmation below, if any, may itself be the call
		// that drains the HU to zero on the EMPTIED reason, so it must carry the same write-off
		// description `bookEmptiedHUToZero` would otherwise apply).
		final I_C_UOM uom = issueSchedule.getQtyToIssue().getUOM();
		final QtyRejectedWithReason qtyRejected = getQtyRejectedWithReason(request, uom);
		final String emptiedHUInventoryDescription = qtyRejected != null && QtyRejectedReasonCode.EMPTIED.equals(qtyRejected.getReasonCode())
				? resolveEmptiedHUInventoryDescription(request.getPpOrderId())
				: null;

		if (request.getHuWeightGrossBeforeIssue() != null)
		{
			weightHU(issueSchedule.getIssueFromHUId(), request.getHuWeightGrossBeforeIssue(), emptiedHUInventoryDescription);
		}

		//
		// Qty Issued
		final Quantity qtyIssued = Quantity.of(request.getQtyIssued(), uom);
		if (qtyIssued.signum() != 0)
		{
			final PPOrderId ppOrderId = request.getPpOrderId();
			final ProductId productId = issueSchedule.getProductId();
			final I_M_HU issueFromHU = handlingUnitsBL.getById(issueSchedule.getIssueFromHUId());

			huPPOrderBL.createIssueProducer(ppOrderId)
					.failIfIssueOnlyForReceived(request.isFailIfIssueOnlyForReceived())
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
		if (emptiedHUInventoryDescription != null)
		{
			bookEmptiedHUToZero(issueSchedule.getIssueFromHUId(), emptiedHUInventoryDescription);
		}

		//
		// Update the issue schedule
		issueSchedule = issueSchedule.withIssued(PPOrderIssueSchedule.Issued.builder()
				.qtyIssued(qtyIssued)
				.qtyRejected(qtyRejected)
				.build());
		issueScheduleRepository.saveChanges(issueSchedule);
		return issueSchedule;
	}

	@Nullable
	private static QtyRejectedWithReason getQtyRejectedWithReason(final @NonNull PPOrderIssueScheduleProcessRequest request, final @NonNull I_C_UOM uom)
	{
		if (request.getQtyRejectedReasonCode() == null)
		{
			return null;
		}

		final Quantity qtyRejected = Quantity.of(Check.assumeNotNull(request.getQtyRejected(), "QtyRejected is set: {}", request), uom);
		return QtyRejectedWithReason.of(qtyRejected, request.getQtyRejectedReasonCode());
	}

	private String resolveEmptiedHUInventoryDescription(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);
		return msgBL.getMsg(Language.getBaseAD_Language(), MSG_EmptiedHUInventoryDescription, new Object[] { ppOrder.getDocumentNo() });
	}

	private void bookEmptiedHUToZero(@NonNull final HuId huId, @NonNull final String description)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final IHUStorage huStorage = handlingUnitsBL.getStorageFactory().getStorage(hu);
		if (huStorage.getProductStorages().isEmpty())
		{
			// Nothing to write off: the HU already carries no product storage at all. This is the normal
			// case for a weight-tracked HU (e.g. a packing-instruction-produced TU): `weightHU` below
			// already drained it to the counted weight, carrying this SAME description, before the
			// ordinary issue consumed the rest.
			// HUQtyService.updateQty(huId=...) requires exactly one M_HU_Storage row (it throws "Empty HU is not handled"
			// for zero storages), so calling it here would fail on an HU that is already effectively empty.
			return;
		}

		final Quantity qtyZero = huStorage.getQtyForProductStorages().toZero();

		huQtyService.updateQty(UpdateHUQtyRequest.builder()
				.huId(huId)
				.qty(qtyZero)
				.description(description)
				.build());
	}

	private void weightHU(@NonNull final HuId huId, @NonNull final BigDecimal weightGross, @Nullable final String description)
	{
		if (weightGross.signum() < 0)
		{
			throw new AdempiereException("Invalid weightGross: " + weightGross)
					.setParameter("huId", huId);
		}

		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext();
		final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		final PlainWeightable targetWeight = Weightables.plainOf(huAttributes);
		if (!targetWeight.isWeightable())
		{
			throw new AdempiereException("HU is not targetWeight: " + handlingUnitsBL.getDisplayName(hu))
					.setParameter("targetWeight", targetWeight);
		}

		targetWeight.setWeightGross(weightGross);
		Weightables.updateWeightNet(targetWeight);

		WeightHUCommand.builder()
				.huQtyService(huQtyService)
				//
				.huId(huId)
				.targetWeight(targetWeight)
				.description(description)
				.build()
				//
				.execute();
	}

	public PPOrderIssueSchedule changeSeqNo(@NonNull final PPOrderIssueSchedule issueSchedule, @NonNull final SeqNo newSeqNo)
	{
		if (SeqNo.equals(issueSchedule.getSeqNo(), newSeqNo))
		{
			return issueSchedule;
		}

		final PPOrderIssueSchedule issueScheduleChanged = issueSchedule.withSeqNo(newSeqNo);
		issueScheduleRepository.saveChanges(issueScheduleChanged);
		return issueScheduleChanged;
	}

	public void updateQtyToIssue(
			@NonNull final PPOrderIssueScheduleId issueScheduleId,
			@NonNull final Quantity qtyToIssue)
	{
		final PPOrderIssueSchedule issueSchedule = issueScheduleRepository.getById(issueScheduleId);
		if (issueSchedule.getQtyToIssue().equals(qtyToIssue))
		{
			return;
		}

		final PPOrderIssueSchedule issueScheduleChanged = issueSchedule.withQtyToIssue(qtyToIssue);
		issueScheduleRepository.saveChanges(issueScheduleChanged);
	}

	public void delete(@NonNull final PPOrderIssueSchedule issueSchedule)
	{
		if (issueSchedule.isIssued())
		{
			throw new AdempiereException("Deleting issued schedules is not allowed");
		}

		issueScheduleRepository.deleteNotProcessedById(issueSchedule.getId());
	}

	public boolean matchesByOrderId(@NonNull final PPOrderId ppOrderId)
	{
		return issueScheduleRepository.matchesByOrderId(ppOrderId);
	}

}
