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
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
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
		final HuId tuId = helper.createHU(tuPIId, productId, helper.qty("100", productId));
		helper.huService.setGrais(tuId, GRAISet.of(grai));

		final HUGraiSnapshot snapshot = CollectionUtils.singleElement(
				ImmutableList.copyOf(helper.huService.getGraiSnapshots(ImmutableSet.of(tuId))));
		assertThat(snapshot.getAllGrais().toSet())
				.as("the scanned GRAI must be stamped on the TU and readable back")
				.containsExactly(grai);
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
