package de.metas.manufacturing.job.service.commands.issue;

import com.google.common.annotations.VisibleForTesting;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleProcessRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaver;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaverSupportingServices;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class IssueRawMaterialsCommand
{
	private static final AdMessageKey MSG_EmptyingNotAllowedForHU = AdMessageKey.of("de.metas.manufacturing.job.service.EmptyingNotAllowedForHU");

	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(IssueRawMaterialsCommand.class);
	@NonNull private final ITrxManager trxManager;
	@NonNull private final PPOrderIssueScheduleService ppOrderIssueScheduleService;
	@NonNull private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;
	@NonNull private final IHUPPOrderBL ppOrderBL;
	@NonNull private final MobileUIManufacturingConfigRepository mobileUIManufacturingConfigRepository;

	// Params
	@NonNull private final PPOrderIssueScheduleProcessRequest request;

	// State
	@NonNull private ManufacturingJob job;
	private boolean processed;

	@Builder
	private IssueRawMaterialsCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final PPOrderIssueScheduleService ppOrderIssueScheduleService,
			@NonNull final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices,
			@NonNull final IHUPPOrderBL ppOrderBL,
			@NonNull final MobileUIManufacturingConfigRepository mobileUIManufacturingConfigRepository,
			//
			@NonNull final ManufacturingJob job,
			@NonNull final PPOrderIssueScheduleProcessRequest request)
	{
		this.trxManager = trxManager;
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
		this.loadingAndSavingSupportServices = loadingAndSavingSupportServices;
		this.ppOrderBL = ppOrderBL;
		this.mobileUIManufacturingConfigRepository = mobileUIManufacturingConfigRepository;

		this.job = job;
		this.request = request;
	}

	public ManufacturingJob execute()
	{
		trxManager.runInThreadInheritedTrx(this::execute0);
		return job;
	}

	private void execute0()
	{
		job = job.withChangedRawMaterialsIssueStep(
				request.getActivityId(),
				request.getIssueScheduleId(),
				(step) -> {
					if (processed)
					{
						// shall not happen
						logger.warn("Ignoring request because was already processed: request={}, step={}", request, step);
						return step;
					}

					return issueToStep(step);
				});
		if (!processed)
		{
			throw new AdempiereException("Failed fulfilling issue request")
					.setParameter("request", request)
					.setParameter("job", job);
		}

		save();
	}

	private RawMaterialsIssueStep issueToStep(final RawMaterialsIssueStep step)
	{
		step.assertNotIssued();

		// Re-check server-side: a replayed/crafted request could carry EMPTIED for a step whose
		// HU is not eligible (e.g. an LU, an aggregate HU, a multi-product HU) or while the
		// client config no longer offers emptying. Config is resolved only when the reason is
		// actually EMPTIED -- no extra DB round-trip for every other raw-materials issue.
		final QtyRejectedReasonCode reasonCode = request.getQtyRejectedReasonCode();
		if (QtyRejectedReasonCode.EMPTIED.equals(reasonCode))
		{
			assertEmptyingAllowed(step, resolveEmptyingHUsConfig(), reasonCode);
		}

		// Cap the issued qty at the BOM line's remaining open demand (the same ceiling the mobile UI
		// enforces via JsonRawMaterialsIssueLineStep.qtyToIssueMax), so a replayed/crafted request cannot
		// over-issue from any HU -- a primary step or an engaged alternative (non-FEFO) one. The remaining
		// demand already includes the issuing tolerance, so a legitimate within-tolerance issue is untouched.
		assertWithinRemainingDemand(step, remainingQtyToIssueMaxForStep(step), request.getQtyIssued());

		final PPOrderIssueSchedule issueSchedule = ppOrderIssueScheduleService.issue(request);
		this.processed = true;

		return step.withIssued(issueSchedule.getIssued());
	}

	/**
	 * The BOM line's remaining open demand (incl. issuing tolerance), converted to {@code step}'s stocking
	 * UOM -- i.e. the ceiling the mobile UI caps the Qty input at. The step's own issue schedule is not yet
	 * issued, so this is the amount still free for the whole line.
	 */
	private Quantity remainingQtyToIssueMaxForStep(@NonNull final RawMaterialsIssueStep step)
	{
		final RawMaterialsIssueLine line = job.streamRawMaterialsIssueLines()
				.filter(l -> l.getSteps().stream().anyMatch(s -> Objects.equals(s.getId(), step.getId())))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No raw-materials issue line found for step")
						.setParameter("step", step)
						.setParameter("job", job));

		return line.getRemainingQtyToIssueMaxInUOM(step.getQtyToIssue().getUomId());
	}

	/**
	 * Pure guard for the write path: throws when the requested issued qty exceeds the BOM line's remaining
	 * open demand (both in the step's stocking UOM). The mobile UI already caps the input at this ceiling;
	 * this stops a replayed/crafted request over-issuing from any HU. A within-ceiling qty is a no-op.
	 */
	@VisibleForTesting
	public static void assertWithinRemainingDemand(
			@NonNull final RawMaterialsIssueStep step,
			@NonNull final Quantity remainingQtyToIssueMax,
			@NonNull final BigDecimal qtyIssued)
	{
		// Round the requested qty to the UOM's precision (HALF_UP) before comparing: an on-scale weightable
		// issue is submitted as an unrounded JS subtraction (e.g. 5.200000000000001), so an exact compare
		// would wrongly refuse a legitimate issue sitting exactly on the remaining-demand boundary. HALF_UP
		// absorbs that sub-precision float noise while a genuine over-issue (at the UOM precision) still
		// exceeds the ceiling. (Quantity.roundToUOMPrecision rounds UP, which would not absorb the noise.)
		final int uomPrecision = remainingQtyToIssueMax.getUOM().getStdPrecision();
		final BigDecimal qtyIssuedRounded = qtyIssued.setScale(uomPrecision, RoundingMode.HALF_UP);
		if (qtyIssuedRounded.compareTo(remainingQtyToIssueMax.toBigDecimal()) > 0)
		{
			throw new AdempiereException("Cannot issue more than the line's remaining demand")
					.markAsUserValidationError()
					.setParameter("qtyIssued", qtyIssued)
					.setParameter("remainingQtyToIssueMax", remainingQtyToIssueMax)
					.setParameter("step", step);
		}
	}

	/**
	 * Pure guard for the write path: throws unless the step's HU shape ({@link RawMaterialsIssueStep#isAllowEmptying()},
	 * decided at job-load time from the HU itself) AND the resolved client config both allow the "empty
	 * (auto. inventory)" reason. A no-op for every other reason code -- the emptying rule applies only to
	 * {@link QtyRejectedReasonCode#EMPTIED}.
	 */
	@VisibleForTesting
	public static void assertEmptyingAllowed(
			@NonNull final RawMaterialsIssueStep step,
			@NonNull final MobileUIManufacturingConfig config,
			@Nullable final QtyRejectedReasonCode reasonCode)
	{
		if (!QtyRejectedReasonCode.EMPTIED.equals(reasonCode))
		{
			return;
		}

		if (!step.isAllowEmptying() || !config.getIsAllowEmptyingHUs().isTrue())
		{
			throw new AdempiereException(MSG_EmptyingNotAllowedForHU)
					.markAsUserValidationError()
					.setParameter("step", step)
					.setParameter("config", config);
		}

		// A non-primary (alternative, non-FEFO) HU may be emptied only when it is a free-standing top-level
		// HU -- mirroring the mobile gate: a pallet-nested TU keeps its locked zero-target placeholder (it is
		// issued/emptied via its pallet, not from this line), so a replayed/crafted EMPTIED on such an
		// alternative step is refused. A primary step (isAlternativeIssue=false) is unaffected.
		if (step.isAlternativeIssue() && !step.isIssueFromTopLevelHU())
		{
			throw new AdempiereException(MSG_EmptyingNotAllowedForHU)
					.markAsUserValidationError()
					.setParameter("step", step)
					.setParameter("config", config);
		}
	}

	/**
	 * Resolved once per issue request (from the job's own {@code AD_Client_ID}), mirroring
	 * {@code RawMaterialsIssueActivityHandler.resolveEmptyingHUsConfig} on the render path -- both derive
	 * the offer flag from the same client config, so the write path enforces exactly what the render path offered.
	 */
	private MobileUIManufacturingConfig resolveEmptyingHUsConfig()
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(job.getPpOrderId());
		final ClientId clientId = ClientId.ofRepoId(ppOrder.getAD_Client_ID());
		final UserId responsibleId = job.getResponsibleId() != null ? job.getResponsibleId() : Env.getLoggedUserId();

		return mobileUIManufacturingConfigRepository.getConfig(responsibleId, clientId);
	}

	private void save()
	{
		newSaver().saveActivityStatuses(job);
	}

	@NonNull
	private ManufacturingJobLoaderAndSaver newSaver() {return new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices);}
}
