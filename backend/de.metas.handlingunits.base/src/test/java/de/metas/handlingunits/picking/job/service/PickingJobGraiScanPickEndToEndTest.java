package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.grai.HUGraiSnapshot;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Service-layer coverage for the GRAI-scan picking flow, mirroring the Playwright spec
 * {@code e2e/mobile-webui/tests/spec/picking/picking-grai-scan.spec.js} at the
 * {@link PickingJobService#createTUFromGRAI} entry point.
 * <p>
 * The picking job line here is a <b>TU pick</b> (finite PIIP on the shipment schedule). Covered here:
 * <ul>
 *   <li><b>TC1 (happy path)</b> — a valid GRAI scan resolves the TU type, validates it, and stores a <b>new-TU</b>
 *       target carrying the scanned GRAI, which survives the save/reload roundtrip onto
 *       {@code M_Picking_Job_Line.Current_PickTo_TU_GRAI}; the GRAI is then stampable on a materialised TU of the
 *       resolved type (the pick-time stamp done by {@code PickingJobPickCommand#stampGraiIfPresent}).</li>
 *   <li><b>TC2</b> — no GRAI-to-TU mapping → {@code GRAINoMatchingTUType}.</li>
 *   <li><b>TC3</b> — resolved TU not includable on the LU target → {@code GRAITUNotAllowedOnLU}.</li>
 *   <li><b>TC6</b> — resolved TU type has no capacity for the line product → {@code GRAINoCapacityForProduct}.</li>
 *   <li>scan-time fail-loud guard (no Playwright equivalent): the resolved TU type's current PI version does NOT
 *       declare the GRAI HU-attribute slot → {@code GRAIAttributeNotSupportedByTUType}, instead of silently
 *       dropping the GRAI and surfacing a confusing GRAI_COUNT_MISMATCH at pick completion.</li>
 * </ul>
 * The full browser-driven scan→pick→complete flow remains covered end-to-end by the Playwright spec TC1.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobGraiScanPickEndToEndTest
{
	private static final String GRAI_CANONICAL = "7613204.00307.999999";

	private PickingJobTestHelper helper;
	private HUTestHelper huTestHelper;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		// reuse the helper's own HUTestHelper so master data (locator, UOM, GRAI attribute, PIs) live in one context
		huTestHelper = helper.getHuTestHelper();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
	}

	private void mapGrai(final String companyPrefix, final String assetType, final I_M_HU_PI tuPI)
	{
		final I_M_HU_PI_GRAI record = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		record.setGRAI_CompanyPrefix(companyPrefix);
		record.setGRAI_AssetType(assetType);
		record.setM_HU_PI_ID(tuPI.getM_HU_PI_ID());
		InterfaceWrapperHelper.save(record);
	}

	/** Declares the {@code GRAI} HU-attribute on the PI so HUs built from it carry a writable GRAI slot. */
	private void assignGraiAttribute(final I_M_HU_PI tuPI)
	{
		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();
		final I_M_Attribute graiAttribute = attributesTestHelper.createM_Attribute(
				AttributeConstants.ATTR_GRAI.getCode(),
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true);

		huTestHelper.createM_HU_PI_Attribute(
				HUPIAttributeBuilder.newInstance(graiAttribute)
						.setM_HU_PI(tuPI)
						.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
	}

	/**
	 * Builds a finite-capacity TU PI for the product, mapped from GRAI {@code 7613204.00307}.
	 * <p>
	 * When {@code withGraiSlot} is {@code true} the PI also carries the {@code GRAI} HU-attribute slot, so HUs built
	 * from it have a writable GRAI slot. When {@code false} the GRAI {@link I_M_Attribute} still exists (so the
	 * attribute code resolves) but the PI does <b>not</b> declare the slot — modelling a misconfigured TU type whose
	 * materialised HUs cannot store the scanned GRAI.
	 */
	private HUPIItemProductId createGraiTuPI(final ProductId productId, final boolean withGraiSlot)
	{
		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition("GRAI-TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		final I_M_HU_PI_Item_Product piip = huTestHelper.assignProduct(miItem, productId, new BigDecimal("100"), helper.uomEach);
		piip.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(piip);
		mapGrai("7613204", "00307", tuPI);
		if (withGraiSlot)
		{
			assignGraiAttribute(tuPI);
		}
		else
		{
			// Ensure the GRAI M_Attribute itself exists (so the attribute code resolves), but do NOT add the PI slot.
			new AttributesTestHelper().createM_Attribute(
					AttributeConstants.ATTR_GRAI.getCode(),
					X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
					true);
		}
		return HUPIItemProductId.ofRepoId(piip.getM_HU_PI_Item_Product_ID());
	}

	/** Creates a TU-pick picking job (line.pickingUnit == TU) whose pack-to is the given finite PIIP. */
	private PickingJob createTuPickingJob(final ProductId productId, final String qtyToDeliver, final HUPIItemProductId packToPiipId)
	{
		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrderGRAI");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.huPIItemProductId(packToPiipId)
				.qtyToDeliver(qtyToDeliver)
				.build();

		return helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(false)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
	}

	/**
	 * TC1 (happy path, scan→target→persist): scanning a valid GRAI must resolve the TU type, validate it, and store a
	 * <b>new-TU</b> picking target that <b>carries the scanned GRAI</b> — and that GRAI must survive the
	 * save/reload roundtrip onto {@code M_Picking_Job_Line.Current_PickTo_TU_GRAI}.
	 * <p>
	 * This is the load-bearing "the GRAI is not silently dropped" guard. The subsequent stamp-on-materialised-TU step
	 * (which requires the full virtual-inventory document interceptor chain, unavailable in the in-memory harness) is
	 * covered by {@link #graiCarriedByNewTuTarget_isStampedOnTU()} below and end-to-end by the Playwright spec TC1.
	 */
	@Test
	void scanGRAI_happyPath_graiCarriedOnTargetAndPersisted()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId, true); // GRAI-mapped TU PI WITH the GRAI slot
		helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(line.getPickingUnit()).as("line must be a TU pick").isEqualTo(PickingUnit.TU);

		// Scan the GRAI: resolves the TU type, validates, and stores a new-TU target carrying the GRAI.
		pickingJob = helper.pickingJobService.createTUFromGRAI(pickingJob, line.getId(), ScannedCode.ofString(GRAI_CANONICAL));

		// In-memory: the new-TU target carries the scanned GRAI.
		final TUPickingTarget target = pickingJob.getTuPickingTargetEffective(line.getId()).orElse(null);
		assertThat(target).as("TU target after GRAI scan").isNotNull();
		assertThat(target.isNewTU()).as("GRAI scan must produce a new-TU target").isTrue();
		assertThat(target.getGrai())
				.as("the new-TU target must carry the scanned GRAI")
				.isEqualTo(GRAI.parse(GRAI_CANONICAL));

		// Persistence: reload from the repository and assert the GRAI survived the Current_PickTo_TU_GRAI roundtrip.
		final PickingJob reloaded = helper.pickingJobService.getById(pickingJob.getId());
		final TUPickingTarget reloadedTarget = reloaded.getTuPickingTargetEffective(line.getId()).orElse(null);
		assertThat(reloadedTarget).as("reloaded TU target").isNotNull();
		assertThat(reloadedTarget.getGrai())
				.as("the scanned GRAI must survive save/reload (Current_PickTo_TU_GRAI)")
				.isEqualTo(GRAI.parse(GRAI_CANONICAL));
	}

	/**
	 * TC1 (scan→pick→validate seam): after scanning a valid GRAI (which stores a new-TU target carrying the GRAI) and
	 * then <b>picking</b> the line, the scanned GRAI must end up on the materialised picked HU <b>at exactly the HU
	 * the completion-time GRAI validator reads it from</b> — i.e. {@code PickingJobGRAIValidator} resolves
	 * {@code line.getPickedHUIds()}, loads their GRAI snapshots via {@code PickingJobHUService#getGraiSnapshots} and
	 * calls {@code assertAllGraisAssigned()}. This drives the real {@code PickingJobPickCommand#stampGraiIfPresent}
	 * path end-to-end through {@link PickingJobService#processStepEvent} and asserts the validator's own check, closing
	 * the gap the sibling stamp test below leaves (which stamps a hand-materialised TU rather than a picked one).
	 * <p>
	 * NOTE: the in-memory HU harness materialises the pick as a top-level real TU; it does <b>not</b> reproduce the
	 * aggregate-TU-under-LU nesting that the full virtual-inventory document-interceptor chain produces on the running
	 * stack, so this is not a substitute for the Playwright spec TC1 (which exercises that nesting).
	 */
	@Test
	void scanGRAI_thenPick_graiIsStampedOnPickedHU_whereValidatorReadsIt()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId, true); // GRAI-mapped TU PI WITH the GRAI slot
		final HUInfo pickFromVHU = helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);
		PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(line.getPickingUnit()).as("line must be a TU pick").isEqualTo(PickingUnit.TU);

		// Scan the GRAI: stores a new-TU target carrying the scanned GRAI.
		pickingJob = helper.pickingJobService.createTUFromGRAI(pickingJob, line.getId(), ScannedCode.ofString(GRAI_CANONICAL));

		// Pick the line: the framework materialises the TU and PickingJobPickCommand stamps the GRAI on it.
		final PickingJobStepId stepId = CollectionUtils.singleElement(
				pickingJob.streamSteps().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(pickFromVHU.getQrCode().toScannedCode())
				.qtyPicked(BigDecimal.ONE) // 1 TU (capacity 100 CUs/TU)
				.qtyRejectedReasonCode(null)
				.build());

		// The completion-time GRAI validator reads exactly these picked HU ids and calls assertAllGraisAssigned().
		line = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(line.getPickedHUIds()).as("the pick must have produced picked HU(s)").isNotEmpty();
		helper.huService.getGraiSnapshots(line.getPickedHUIds())
				.assertAllGraisAssigned();
	}

	/**
	 * TC1 (stamp step): the GRAI carried by a new-TU target must be writable as a {@code GRAI} HU-attribute on a
	 * materialised TU of the resolved type — i.e. {@link de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService#setGrais}
	 * actually stamps the value and it can be read back. This isolates the stamp logic exercised at pick time by
	 * {@code PickingJobPickCommand#stampGraiIfPresent} without the virtual-inventory materialisation chain.
	 */
	@Test
	void graiCarriedByNewTuTarget_isStampedOnTU()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		createGraiTuPI(productId, true); // TU PI WITH the GRAI slot, mapped from the scanned GRAI
		final GRAI grai = GRAI.parse(GRAI_CANONICAL);
		final HuPackingInstructionsId tuPIId = helper.huService.resolveHuPackingInstructionsId(grai);

		// Materialise a real TU of the resolved type and stamp the scanned GRAI on it.
		// Mirror the production stamp contract exactly: PickingJobPickCommand#execute wraps the pick in a
		// thread-inherited trx (callInThreadInheritedTrx) and the stamp at PickingJobPickCommand#stampGraiIfPresent
		// runs inside huService.temporarySetNewHContextForProcessing(). The facade's setGrais routes the write through
		// that ambient context with setSaveOnChange(true), so it flushes on the trx commit.
		final HuId tuId = helper.createHU(tuPIId, productId, helper.qty("100", productId));
		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			try (final IAutoCloseable ignored = helper.huService.temporarySetNewHContextForProcessing())
			{
				helper.huService.setGrais(tuId, GRAISet.of(grai));
			}
		});

		final HUGraiSnapshot snapshot = CollectionUtils.singleElement(
				ImmutableList.copyOf(helper.huService.getGraiSnapshots(ImmutableSet.of(tuId))));
		assertThat(snapshot.getAllGrais().toSet())
				.as("the scanned GRAI must be stamped on the TU and readable back")
				.containsExactly(grai);
	}

	/**
	 * Header-level (no-line) GRAI scan: scanning a GRAI against the JOB/HEADER-level TU pick-target
	 * (i.e. {@code lineId == null}) must resolve the TU type from the scanned GRAI and store a
	 * <b>job-level</b> new-TU {@link TUPickingTarget} that carries both the resolved TU PI (the
	 * GRAI-mapped type X) and the parsed GRAI.
	 * <p>
	 * Mirrors TC1's masterdata ({@link #createGraiTuPI}) but drives the header path: a job-level LU
	 * picking target is set with {@code lineId == null}, then {@link PickingJobService#createTUFromGRAI}
	 * is invoked with {@code lineId == null} and the result read back via {@code getTuPickingTarget(null)}.
	 * <p>
	 * <b>RED:</b> on the current code {@link PickingJobService#createTUFromGRAI} declares
	 * {@code @NonNull PickingJobLineId lineId}, so passing {@code null} throws a Lombok-generated
	 * {@link NullPointerException} before any header-level logic runs. The later fix makes
	 * {@code lineId} {@code @Nullable} and skips the per-product capacity check at header level.
	 */
	@Test
	void setTUPickingTargetFromGRAI_atHeaderLevel_setsJobLevelTuTargetCarryingGrai()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId, true); // GRAI-mapped TU PI WITH the GRAI slot
		helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);

		// Set a JOB/HEADER-level LU picking target (lineId == null) on an LU PI that DOES INCLUDE the GRAI TU
		// type, so the (always-on) TU-allowed-on-LU check at header level passes (AC-H3) and the scan succeeds.
		final HuPackingInstructionsId graiTuPIId = helper.huService.resolveHuPackingInstructionsId(GRAI.parse(GRAI_CANONICAL));
		final I_M_HU_PI graiTuPI = InterfaceWrapperHelper.load(graiTuPIId.getRepoId(), I_M_HU_PI.class);
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-GRAI-HDR", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, graiTuPI, new BigDecimal("10")); // LU now includes the GRAI TU type
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID());
		pickingJob = helper.pickingJobService.setLUPickingTarget(pickingJob, /*lineId*/ null,
				LUPickingTarget.ofPackingInstructions(luPIId, "LU-GRAI-HDR"));

		// Scan the GRAI at HEADER level (lineId == null): resolves the TU type and stores a job-level new-TU target.
		pickingJob = helper.pickingJobService.createTUFromGRAI(pickingJob, /*lineId*/ null, ScannedCode.ofString(GRAI_CANONICAL));

		// The job-level TU target must carry the resolved TU PI (type X) AND the parsed GRAI.
		final HuPackingInstructionsId expectedTuPIId = helper.huService.resolveHuPackingInstructionsId(GRAI.parse(GRAI_CANONICAL));
		final TUPickingTarget jobLevelTarget = pickingJob.getTuPickingTarget(/*lineId*/ null).orElse(null);
		assertThat(jobLevelTarget).as("job-level TU target after header-level GRAI scan").isNotNull();
		assertThat(jobLevelTarget.isNewTU()).as("header-level GRAI scan must produce a new-TU target").isTrue();
		assertThat(jobLevelTarget.getTuPIIdNotNull())
				.as("the job-level new-TU target must carry the resolved TU PI (type X)")
				.isEqualTo(expectedTuPIId);
		assertThat(jobLevelTarget.getGrai())
				.as("the job-level new-TU target must carry the scanned GRAI")
				.isEqualTo(GRAI.parse(GRAI_CANONICAL));
	}

	/**
	 * Header-level scan→<b>reload</b>→pick→validate seam: scanning a GRAI against the JOB/HEADER-level TU pick-target
	 * (i.e. {@code lineId == null}, as for order-based / {@code SALES_ORDER} aggregation), then <b>saving and
	 * reloading the picking job</b> (the roundtrip the running stack performs around every pick), and then picking,
	 * must end up with the scanned GRAI on the materialised picked HU — i.e. the completion-time
	 * {@code PickingJobGRAIValidator} sees {@code assertAllGraisAssigned()} pass.
	 * <p>
	 * This is the header-level mirror of {@link #scanGRAI_thenPick_graiIsStampedOnPickedHU_whereValidatorReadsIt()}
	 * (which is line-level) and the missing AC-H6 coverage: for the header path the TU pick-target lives on the
	 * {@code M_Picking_Job} header, so unless the header saver/loader round-trips the GRAI the scan is silently
	 * dropped on reload and the pick ships a TU with no GRAI.
	 * <p>
	 * The forced reload via {@code getById} between scan and pick is load-bearing: it is exactly the header-target
	 * GRAI roundtrip under test (the line-level sibling does not reload because the line column already round-trips).
	 */
	@Test
	void scanGRAI_atHeaderLevel_thenReloadAndPick_graiSurvivesAndIsStampedOnPickedHU()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId, true); // GRAI-mapped TU PI WITH the GRAI slot
		final HUInfo pickFromVHU = helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);

		// Set a JOB/HEADER-level LU picking target (lineId == null) on an LU PI that DOES INCLUDE the GRAI TU type,
		// so the TU-allowed-on-LU check at header level passes and the scan succeeds.
		final HuPackingInstructionsId graiTuPIId = helper.huService.resolveHuPackingInstructionsId(GRAI.parse(GRAI_CANONICAL));
		final I_M_HU_PI graiTuPI = InterfaceWrapperHelper.load(graiTuPIId.getRepoId(), I_M_HU_PI.class);
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-GRAI-HDR-PICK", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, graiTuPI, new BigDecimal("10"));
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID());
		pickingJob = helper.pickingJobService.setLUPickingTarget(pickingJob, /*lineId*/ null,
				LUPickingTarget.ofPackingInstructions(luPIId, "LU-GRAI-HDR-PICK"));

		// Scan the GRAI at HEADER level (lineId == null): stores a job-level new-TU target carrying the scanned GRAI.
		pickingJob = helper.pickingJobService.createTUFromGRAI(pickingJob, /*lineId*/ null, ScannedCode.ofString(GRAI_CANONICAL));

		// Save+reload the picking job: this is the header-target GRAI roundtrip under test. Before the fix the header
		// saver/loader drops the GRAI here, so the reloaded job-level target carries grai==null.
		pickingJob = helper.pickingJobService.getById(pickingJob.getId());
		final TUPickingTarget reloadedTarget = pickingJob.getTuPickingTarget(/*lineId*/ null).orElse(null);
		assertThat(reloadedTarget).as("reloaded job-level TU target").isNotNull();
		assertThat(reloadedTarget.getGrai())
				.as("the scanned GRAI must survive the header save/reload (M_Picking_Job.Current_PickTo_TU_GRAI)")
				.isEqualTo(GRAI.parse(GRAI_CANONICAL));

		// Pick the line from the reloaded job: the framework materialises the TU and PickingJobPickCommand stamps the
		// GRAI carried by the (reloaded) header-level target on it.
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		final PickingJobStepId stepId = CollectionUtils.singleElement(
				pickingJob.streamSteps().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(pickFromVHU.getQrCode().toScannedCode())
				.qtyPicked(BigDecimal.ONE) // 1 TU (capacity 100 CUs/TU)
				.qtyRejectedReasonCode(null)
				.build());

		// The completion-time GRAI validator reads exactly these picked HU ids and calls assertAllGraisAssigned().
		final PickingJobLine pickedLine = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(pickedLine.getPickedHUIds()).as("the pick must have produced picked HU(s)").isNotEmpty();
		helper.huService.getGraiSnapshots(pickedLine.getPickedHUIds())
				.assertAllGraisAssigned();
	}

	/**
	 * AC-H3 at header level: a resolved TU type NOT associable to the JOB-LEVEL LU target must fail loud with
	 * {@code GRAITUNotAllowedOnLU} — the TU-allowed-on-LU check is NOT skipped at header level (only the
	 * per-product capacity check is). Mirrors TC3 but with the LU target set at job/header level (lineId == null).
	 */
	@Test
	void setTUPickingTargetFromGRAI_atHeaderLevel_tuNotAllowedOnJobLu_throwsTUNotAllowedOnLU()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId, true);
		helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		// An LU PI that does NOT include the GRAI TU PI, set at JOB/HEADER level (lineId == null).
		final I_M_HU_PI unrelatedLuPI = huTestHelper.createHUDefinition("LU-NO-GRAI-TU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final HuPackingInstructionsId unrelatedLuPIId = HuPackingInstructionsId.ofRepoId(unrelatedLuPI.getM_HU_PI_ID());

		PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);
		pickingJob = helper.pickingJobService.setLUPickingTarget(pickingJob, /*lineId*/ null,
				LUPickingTarget.ofPackingInstructions(unrelatedLuPIId, "LU-NO-GRAI-TU"));

		final PickingJob pickingJobForLambda = pickingJob;
		assertThatThrownBy(() -> helper.pickingJobService.createTUFromGRAI(pickingJobForLambda, /*lineId*/ null, ScannedCode.ofString(GRAI_CANONICAL)))
				.as("header-level GRAI scan whose TU type is not includable on the job-level LU target must fail loud")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("de.metas.handlingunits.picking.GRAITUNotAllowedOnLU");
	}

	/**
	 * TC2: scanning a GRAI with no active GRAI-to-TU mapping must fail loud with {@code GRAINoMatchingTUType}.
	 */
	@Test
	void scanGRAI_noMappingForGrai_throwsNoMatchingTUType()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId, true); // maps GRAI 7613204.00307 — NOT the GRAI we will scan
		helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		final PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());

		// company-prefix 9999999 has no M_HU_PI_GRAI mapping
		final ScannedCode unmappedGrai = ScannedCode.ofString("9999999.00307.000001");
		assertThatThrownBy(() -> helper.pickingJobService.createTUFromGRAI(pickingJob, line.getId(), unmappedGrai))
				.as("scanning a GRAI with no TU-type mapping must fail loud")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("de.metas.handlingunits.picking.GRAINoMatchingTUType");
	}

	/**
	 * TC3: when an LU picking target is set whose PI does not include the resolved TU type, scanning the GRAI must
	 * fail loud with {@code GRAITUNotAllowedOnLU}.
	 */
	@Test
	void scanGRAI_tuNotAllowedOnLu_throwsTUNotAllowedOnLU()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId, true);
		helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		// An LU PI that does NOT include the GRAI TU PI.
		final I_M_HU_PI unrelatedLuPI = huTestHelper.createHUDefinition("LU-NO-GRAI-TU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final HuPackingInstructionsId unrelatedLuPIId = HuPackingInstructionsId.ofRepoId(unrelatedLuPI.getM_HU_PI_ID());

		PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		pickingJob = helper.pickingJobService.setLUPickingTarget(pickingJob, line.getId(),
				LUPickingTarget.ofPackingInstructions(unrelatedLuPIId, "LU-NO-GRAI-TU"));

		final PickingJob pickingJobForLambda = pickingJob;
		assertThatThrownBy(() -> helper.pickingJobService.createTUFromGRAI(pickingJobForLambda, line.getId(), ScannedCode.ofString(GRAI_CANONICAL)))
				.as("scanning a GRAI whose TU type is not includable on the LU target must fail loud")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("de.metas.handlingunits.picking.GRAITUNotAllowedOnLU");
	}

	/**
	 * TC6: when the resolved TU type has no capacity record for the line's product, scanning the GRAI must fail
	 * loud with {@code GRAINoCapacityForProduct}.
	 */
	@Test
	void scanGRAI_noCapacityForProduct_throwsNoCapacityForProduct()
	{
		// The scanned GRAI maps to a TU type whose only capacity (PIIP) is for P-GRAI.
		final ProductId graiTuProductId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		createGraiTuPI(graiTuProductId, true);

		// The line is for a DIFFERENT product, packed to a plain (non-GRAI) TU type, so the GRAI TU type has no
		// capacity for the line's product.
		final ProductId lineProductId = BusinessTestHelper.createProductId("P-OTHER", helper.uomEach);
		final HUPIItemProductId plainPackToPiipId = createPlainTuPIIP(lineProductId);
		helper.createVHUInfo(lineProductId, "100", "QR-VHU-OTHER");

		final PickingJob pickingJob = createTuPickingJob(lineProductId, "100", plainPackToPiipId);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());

		assertThatThrownBy(() -> helper.pickingJobService.createTUFromGRAI(pickingJob, line.getId(), ScannedCode.ofString(GRAI_CANONICAL)))
				.as("scanning a GRAI whose TU type has no capacity for the line product must fail loud")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("de.metas.handlingunits.picking.GRAINoCapacityForProduct");
	}

	/** Builds a plain finite-capacity TU PI (no GRAI mapping, no GRAI slot) for the product and returns its PIIP id. */
	private HUPIItemProductId createPlainTuPIIP(final ProductId productId)
	{
		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition("PLAIN-TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		final I_M_HU_PI_Item_Product piip = huTestHelper.assignProduct(miItem, productId, new BigDecimal("100"), helper.uomEach);
		piip.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(piip);
		return HUPIItemProductId.ofRepoId(piip.getM_HU_PI_Item_Product_ID());
	}

	/**
	 * Fail-loud guard: when the scanned GRAI resolves to a TU type whose current PI version does NOT declare the
	 * GRAI HU-attribute slot, {@link PickingJobService#createTUFromGRAI} must throw immediately at scan time —
	 * instead of silently dropping the GRAI and surfacing a confusing GRAI_COUNT_MISMATCH at pick completion.
	 */
	@Test
	void scanGRAI_tuTypeLacksGraiSlot_throwsAtScanTime()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId, false); // GRAI-mapped TU PI WITHOUT the GRAI slot
		helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		final PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(line.getPickingUnit()).as("line must be a TU pick").isEqualTo(PickingUnit.TU);

		assertThatThrownBy(() -> helper.pickingJobService.createTUFromGRAI(pickingJob, line.getId(), ScannedCode.ofString(GRAI_CANONICAL)))
				.as("scanning a GRAI for a TU type that lacks the GRAI attribute slot must fail loud at scan time")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("de.metas.handlingunits.picking.GRAIAttributeNotSupportedByTUType");
	}
}
