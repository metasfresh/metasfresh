package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Orchestration command for the GRAI-scan picking flow.
 * <p>
 * Given a scanned GRAI barcode and a picking-job line, this command:
 * <ol>
 *   <li>parses the scanned string ({@link GRAI#parse(String)}); unparseable → {@code InvalidGRAIBarcode};</li>
 *   <li>resolves the TU type ({@code M_HU_PI}) from the global {@code M_HU_PI_GRAI} mapping (B2 — {@link HUPIGraiRepository});
 *       no mapping → {@code GRAINoMatchingTUType};</li>
 *   <li>verifies the resolved TU is allowed on the effective LU picking target (B3 —
 *       {@link PickingJobGraiTargetService#assertTuAllowedOnLu}); not allowed → {@code GRAITUNotAllowedOnLU}.
 *       When there is <b>no</b> effective LU target the check is skipped — a loose TU with no LU target has
 *       nothing to be checked against;</li>
 *   <li>derives the capacity ({@code M_HU_PI_Item_Product}) for the line's product (B3 —
 *       {@link PickingJobGraiTargetService#resolveCapacity}); no capacity → {@code GRAINoCapacityForProduct};</li>
 *   <li>eagerly creates one empty physical TU of that type, attaches the scanned GRAI to it
 *       ({@link HUGraiService#setGrais}) and sets it as the line's <i>existing-TU</i> picking target.</li>
 * </ol>
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
	private static final AdMessageKey MSG_INVALID_GRAI_BARCODE = AdMessageKey.of("de.metas.handlingunits.picking.InvalidGRAIBarcode");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	// Required for step 5 (create empty TU, attach GRAI, store target). Nullable on the constructor so the
	// step-1..4 resolution can be unit-tested without these heavier collaborators.
	@Nullable private final PickingJobService pickingJobService;
	@Nullable private final PickingJobHUService huService;
	@Nullable private final HUGraiService huGraiService;

	// Required for steps 1..4 (resolve).
	@NonNull private final PickingJobGraiTargetService graiTargetService;
	@NonNull private final HUPIGraiRepository huPIGraiRepository;

	// Request inputs. Nullable on the constructor so the step-1..4 logic ({@link #resolveTuTypeAndCapacity})
	// can be unit-tested in isolation; the full {@link #execute()} flow requires them and asserts so.
	@Nullable private final PickingJob initialPickingJob;
	@Nullable private final PickingJobLineId lineId;
	@Nullable private final String scannedGrai;
	@Nullable private final LocatorId tuLocatorId;

	@Builder
	private PickingJobCreateTUFromGRAICommand(
			@Nullable final PickingJobService pickingJobService,
			@Nullable final PickingJobHUService huService,
			@NonNull final PickingJobGraiTargetService graiTargetService,
			@NonNull final HUPIGraiRepository huPIGraiRepository,
			@Nullable final HUGraiService huGraiService,
			//
			@Nullable final PickingJob pickingJob,
			@Nullable final PickingJobLineId lineId,
			@Nullable final String scannedGrai,
			@Nullable final LocatorId tuLocatorId)
	{
		this.pickingJobService = pickingJobService;
		this.huService = huService;
		this.graiTargetService = graiTargetService;
		this.huPIGraiRepository = huPIGraiRepository;
		this.huGraiService = huGraiService;

		this.initialPickingJob = pickingJob;
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
		Check.assumeNotNull(initialPickingJob, "pickingJob shall be set");
		Check.assumeNotNull(scannedGrai, "scannedGrai shall be set");
		Check.assumeNotNull(tuLocatorId, "tuLocatorId shall be set");
		Check.assumeNotNull(pickingJobService, "pickingJobService shall be set");
		Check.assumeNotNull(huService, "huService shall be set");
		Check.assumeNotNull(huGraiService, "huGraiService shall be set");
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		final PickingJob pickingJob = Check.assumeNotNull(initialPickingJob, "pickingJob shall be set");

		// Steps 1..4 — parse, resolve TU type, LU check, capacity (reuses B1/B2/B3).
		final Optional<LUPickingTarget> luTargetOpt = pickingJob.getLuPickingTargetEffective(lineId);
		final ResolvedGRAITU resolved = resolveTuTypeAndCapacity(
				Check.assumeNotNull(scannedGrai, "scannedGrai shall be set"),
				luTargetOpt,
				getLineProductId());

		// Step 5 — create one empty physical TU, attach the GRAI, set it as the line's existing-TU target.
		final GRAI grai = resolved.getGrai();
		final HuId tuHuId = createEmptyTU(resolved.getTuPIId());
		Check.assumeNotNull(huGraiService, "huGraiService shall be set").setGrais(tuHuId, GRAISet.of(grai));

		final HUQRCode tuQRCode = getSingleQRCode(tuHuId);
		final TUPickingTarget tuTarget = TUPickingTarget.ofExistingHU(tuHuId, tuQRCode);

		return Check.assumeNotNull(pickingJobService, "pickingJobService shall be set")
				.setTUPickingTarget(pickingJob, lineId, tuTarget);
	}

	/**
	 * Steps 1..4 of the GRAI flow. Extracted so the four error paths can be exercised without a physical TU /
	 * locator / full picking job. Does <b>not</b> create any HU.
	 *
	 * @param scannedGrai   the raw scanned barcode (step 1)
	 * @param luTargetOpt   the effective LU picking target; empty → the TU-LU check (step 3) is skipped
	 * @param lineProductId the line's product, used to derive capacity (step 4)
	 */
	@NonNull
	ResolvedGRAITU resolveTuTypeAndCapacity(
			@NonNull final String scannedGrai,
			@NonNull final Optional<LUPickingTarget> luTargetOpt,
			@NonNull final ProductId lineProductId)
	{
		// 1. Parse
		final GRAI grai = GRAI.parse(scannedGrai);
		if (grai == null)
		{
			throw new AdempiereException(MSG_INVALID_GRAI_BARCODE, scannedGrai);
		}

		// 2. Resolve TU type (B2) — propagates GRAINoMatchingTUType
		final HuPackingInstructionsId tuPIId = huPIGraiRepository.resolveHuPackingInstructionsId(grai);

		// 3. TU-LU association check (B3) — only when there is an effective LU target.
		// A loose TU with no LU target has nothing to be checked against, so the check is skipped.
		luTargetOpt.ifPresent(luTarget -> graiTargetService.assertTuAllowedOnLu(tuPIId, luTarget));

		// 4. Capacity for the line's product (B3) — propagates GRAINoCapacityForProduct
		final HUPIItemProductId huPIItemProductId = graiTargetService.resolveCapacity(tuPIId, lineProductId);

		return ResolvedGRAITU.builder()
				.grai(grai)
				.tuPIId(tuPIId)
				.huPIItemProductId(huPIItemProductId)
				.build();
	}

	@NonNull
	private ProductId getLineProductId()
	{
		final PickingJob pickingJob = Check.assumeNotNull(initialPickingJob, "pickingJob shall be set");
		final PickingJobLineId lineIdEff = Check.assumeNotNull(lineId, "lineId shall be set to resolve the line product");
		final PickingJobLine line = pickingJob.getLineById(lineIdEff);
		return line.getProductId();
	}

	/**
	 * Creates a single empty TU of the given packing instruction at {@link #tuLocatorId}, Active status.
	 * The TU carries no product — the product is picked into it later via the normal pick flow.
	 */
	@NonNull
	private HuId createEmptyTU(@NonNull final HuPackingInstructionsId tuPIId)
	{
		final I_M_HU_PI tuPI = handlingUnitsDAO.getPackingInstructionById(tuPIId);

		final IMutableHUContext huContext = Check.assumeNotNull(huService, "huService shall be set")
				.createMutableHUContextForProcessing();
		final IHUBuilder huBuilder = handlingUnitsDAO.createHUBuilder(huContext);
		huBuilder.setLocatorId(Check.assumeNotNull(tuLocatorId, "tuLocatorId shall be set"));
		huBuilder.setHUStatus(X_M_HU.HUSTATUS_Active);

		final I_M_HU tu = huBuilder.create(tuPI);
		return HuId.ofRepoId(tu.getM_HU_ID());
	}

	@NonNull
	private HUQRCode getSingleQRCode(@NonNull final HuId tuHuId)
	{
		final List<HUQRCode> qrCodes = Check.assumeNotNull(huService, "huService shall be set")
				.getOrCreateQRCodesByHuId(tuHuId);
		if (qrCodes.isEmpty())
		{
			throw new AdempiereException("No QR code could be created for the new TU: " + tuHuId);
		}
		return qrCodes.get(0);
	}

	@Value
	@Builder
	static class ResolvedGRAITU
	{
		@NonNull GRAI grai;
		@NonNull HuPackingInstructionsId tuPIId;
		@NonNull HUPIItemProductId huPIItemProductId;
	}
}
