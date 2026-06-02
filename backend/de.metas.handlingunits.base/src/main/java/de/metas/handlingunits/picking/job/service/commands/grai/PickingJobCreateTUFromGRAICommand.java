package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.commands.grai.PickingJobGraiTargetService.GraiTuResolution;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import java.util.List;
import java.util.Optional;

/**
 * Orchestration command for the GRAI-scan picking flow.
 * <p>
 * Given a scanned GRAI barcode and a picking-job line, this command:
 * <ol>
 *   <li>parses the scanned string ({@link de.metas.handlingunits.grai.GRAI#parse(String)}); unparseable → {@code InvalidGRAIBarcode};</li>
 *   <li>resolves the TU type ({@code M_HU_PI}) from the global {@code M_HU_PI_GRAI} mapping;
 *       no mapping → {@code GRAINoMatchingTUType};</li>
 *   <li>verifies the resolved TU is allowed on the effective LU picking target;
 *       not allowed → {@code GRAITUNotAllowedOnLU}.
 *       When there is <b>no</b> effective LU target the check is skipped;</li>
 *   <li>derives the capacity ({@code M_HU_PI_Item_Product}) for the line's product;
 *       no capacity → {@code GRAINoCapacityForProduct};</li>
 *   <li>eagerly creates one empty physical TU of that type, attaches the scanned GRAI to it
 *       ({@link HUGraiService#setGrais}) and sets it as the line's <i>existing-TU</i> picking target.</li>
 * </ol>
 * Steps 1..4 are delegated to {@link PickingJobGraiTargetService#resolveTuTypeAndCapacity}.
 * Everything runs in a single thread-inherited transaction, so any failure rolls back and no orphan TU is left behind.
 *
 * <h3>Why the TU is created eagerly (and as an <i>existing</i>-TU target)</h3>
 * The manual per-type button path stores only the {@code tuPIId} on the picking job (a <i>new-TU</i> target);
 * the physical TU is materialized later at pick time by {@code PackToHUsProducer}, which then converts the
 * target into an <i>existing-TU</i> target ({@code TUPickingTarget.ofExistingHU}). The GRAI flow needs an
 * {@code M_HU} to attach the scanned GRAI to, so it materializes the (empty) TU now and stores it directly
 * as an existing-TU target — the same end state the manual path reaches at pick time. The product is picked
 * into this existing TU afterwards via the normal pick flow.
 */
public class PickingJobCreateTUFromGRAICommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final HUGraiService huGraiService;
	@NonNull private final PickingJobGraiTargetService graiTargetService;

	@NonNull private final PickingJob pickingJob;
	@NonNull private final PickingJobLineId lineId;
	@NonNull private final String scannedGrai;
	@NonNull private final LocatorId tuLocatorId;

	@Builder
	private PickingJobCreateTUFromGRAICommand(
			@NonNull final PickingJobService pickingJobService,
			@NonNull final PickingJobHUService huService,
			@NonNull final HUGraiService huGraiService,
			@NonNull final PickingJobGraiTargetService graiTargetService,
			//
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingJobLineId lineId,
			@NonNull final String scannedGrai,
			@NonNull final LocatorId tuLocatorId)
	{
		this.pickingJobService = pickingJobService;
		this.huService = huService;
		this.huGraiService = huGraiService;
		this.graiTargetService = graiTargetService;

		this.pickingJob = pickingJob;
		this.lineId = lineId;
		this.scannedGrai = scannedGrai;
		this.tuLocatorId = tuLocatorId;
	}

	@SuppressWarnings("unused")
	public static class PickingJobCreateTUFromGRAICommandBuilder
	{
		public PickingJob execute() {return build().execute();}
	}

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		// Steps 1..4 — parse, resolve TU type, LU check, capacity.
		final Optional<LUPickingTarget> luTargetOpt = pickingJob.getLuPickingTargetEffective(lineId);
		final GraiTuResolution resolved = graiTargetService.resolveTuTypeAndCapacity(
				scannedGrai,
				luTargetOpt,
				getLineProductId());

		// Step 5 — create one empty physical TU, attach the GRAI, set it as the line's existing-TU target.
		final HuId tuHuId = createEmptyTU(resolved.getTuPIId());
		huGraiService.setGrais(tuHuId, GRAISet.of(resolved.getGrai()));

		final HUQRCode tuQRCode = getSingleQRCode(tuHuId);
		final TUPickingTarget tuTarget = TUPickingTarget.ofExistingHU(tuHuId, tuQRCode);

		return pickingJobService.setTUPickingTarget(pickingJob, lineId, tuTarget);
	}

	@NonNull
	private ProductId getLineProductId()
	{
		final PickingJobLine line = pickingJob.getLineById(lineId);
		return line.getProductId();
	}

	/**
	 * Creates a single empty TU of the given packing instruction at {@link #tuLocatorId}, Active status.
	 * The TU carries no product — the product is picked into it later via the normal pick flow.
	 */
	@NonNull
	private HuId createEmptyTU(@NonNull final de.metas.handlingunits.HuPackingInstructionsId tuPIId)
	{
		final I_M_HU_PI tuPI = handlingUnitsDAO.getPackingInstructionById(tuPIId);

		final IMutableHUContext huContext = huService.createMutableHUContextForProcessing();
		final IHUBuilder huBuilder = handlingUnitsDAO.createHUBuilder(huContext);
		huBuilder.setLocatorId(tuLocatorId);
		huBuilder.setHUStatus(X_M_HU.HUSTATUS_Active);

		final I_M_HU tu = huBuilder.create(tuPI);
		return HuId.ofRepoId(tu.getM_HU_ID());
	}

	@NonNull
	private HUQRCode getSingleQRCode(@NonNull final HuId tuHuId)
	{
		final List<HUQRCode> qrCodes = huService.getOrCreateQRCodesByHuId(tuHuId);
		if (qrCodes.isEmpty())
		{
			throw new AdempiereException("No QR code could be created for the new TU: " + tuHuId);
		}
		return qrCodes.get(0);
	}
}
