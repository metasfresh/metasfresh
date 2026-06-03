package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
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
import de.metas.util.Services;
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
 * End-to-end reproduction of the GRAI-scan picking flow.
 * <p>
 * The picking job line here is a <b>TU pick</b> (finite PIIP on the shipment schedule). It drives the real
 * two-step REST flow as closely as the in-memory {@link PickingJobTestHelper} infra allows:
 * <ol>
 *     <li><b>scan:</b> {@link PickingJobService#createTUFromGRAI} sets a <i>new-TU</i> {@link TUPickingTarget}
 *     carrying the parsed GRAI and persists {@code Current_PickTo_TU_GRAI}.</li>
 *     <li><b>reload:</b> the {@link PickingJob} is re-read from the repository — modelling that scan and pick are
 *     separate HTTP requests with no shared in-memory state.</li>
 *     <li><b>pick:</b> the pick materialises a physical TU; {@code stampGraiIfPresent} must attach the scanned
 *     GRAI onto it via {@link HUGraiService} (else {@code PickingJobGRAIValidator} throws GRAI_COUNT_MISMATCH).</li>
 * </ol>
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobGraiScanPickEndToEndTest
{
	private static final String GRAI_CANONICAL = "7613204.00307.999999";

	private PickingJobTestHelper helper;
	private HUTestHelper huTestHelper;
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

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
	 * Builds a finite-capacity TU PI for the product, mapped from GRAI {@code 7613204.00307} and carrying the
	 * GRAI HU-attribute. Returns its PIIP id so the caller can make the picking-job line a TU pick.
	 */
	private HUPIItemProductId createGraiTuPI(final ProductId productId)
	{
		return createGraiTuPI(productId, true);
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
	 * HALF 1 — persistence: after a GRAI scan, save + RELOAD the job from the repo and assert the line's
	 * new-TU picking target still carries the scanned GRAI.
	 */
	@Test
	void scanGRAI_thenReload_targetStillCarriesGRAI()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId);
		helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(line.getPickingUnit()).as("line must be a TU pick").isEqualTo(PickingUnit.TU);

		// scan
		pickingJob = helper.pickingJobService.createTUFromGRAI(pickingJob, line.getId(), ScannedCode.ofString(GRAI_CANONICAL));

		// reload (separate request)
		final PickingJob reloaded = helper.pickingJobService.getById(pickingJob.getId());

		final TUPickingTarget reloadedTarget = reloaded.getTuPickingTargetEffective(line.getId()).orElse(null);
		assertThat(reloadedTarget).as("reloaded TU picking target").isNotNull();
		assertThat(reloadedTarget.isNewTU()).as("reloaded target must be a new-TU GRAI target").isTrue();
		assertThat(reloadedTarget.getGrai())
				.as("reloaded target must still carry the scanned GRAI")
				.isEqualTo(GRAI.ofCanonicalString(GRAI_CANONICAL));
	}

	/**
	 * HALF 2 — pick-&-stamp: scan GRAI on a TU-pick line, reload, then pick. The materialised
	 * picked TU must carry the scanned GRAI attribute.
	 */
	@Test
	void scanGRAI_reload_thenPick_materialisedTUCarriesGRAI()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-GRAI", helper.uomEach);
		final HUPIItemProductId piipId = createGraiTuPI(productId);
		final HUInfo vhu = helper.createVHUInfo(productId, "100", "QR-VHU-GRAI");

		PickingJob pickingJob = createTuPickingJob(productId, "100", piipId);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(line.getPickingUnit()).as("line must be a TU pick").isEqualTo(PickingUnit.TU);
		final PickingJobStepId stepId = CollectionUtils.singleElement(
				line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		// scan
		pickingJob = helper.pickingJobService.createTUFromGRAI(pickingJob, line.getId(), ScannedCode.ofString(GRAI_CANONICAL));

		// reload (separate request) — pick proceeds from the reloaded job
		pickingJob = helper.pickingJobService.getById(pickingJob.getId());

		// pick (qty in CUs; one TU of capacity 100)
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(vhu.getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal("1")) // 1 TU
				.qtyRejectedReasonCode(null)
				.build());

		// Assert: every picked TU carries the scanned GRAI (the exact thing PickingJobGRAIValidator checks).
		final ImmutableSet<HuId> pickedHUIds = pickingJob.getLineById(line.getId()).getPickedHUIds();
		assertThat(pickedHUIds).as("the pick must have produced at least one picked HU").isNotEmpty();

		final GRAI expectedGrai = GRAI.ofCanonicalString(GRAI_CANONICAL);
		assertThat(pickedHUIds)
				.as("at least one picked TU must carry the scanned GRAI (else PickingJobGRAIValidator throws GRAI_COUNT_MISMATCH)")
				.anyMatch(pickedHUId -> {
					final I_M_HU pickedHU = huTestHelper.handlingUnitsBL().getById(pickedHUId);
					final String graiAttr = huAttributesBL.getHUAttributeValue(pickedHU, AttributeConstants.ATTR_GRAI);
					return graiAttr != null && expectedGrai.equals(GRAI.ofNullableCanonicalString(graiAttr));
				});
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
