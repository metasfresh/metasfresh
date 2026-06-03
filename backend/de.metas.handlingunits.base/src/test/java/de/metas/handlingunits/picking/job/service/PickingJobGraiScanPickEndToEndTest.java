package de.metas.handlingunits.picking.job.service;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingUnit;
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
 * Scan-time fail-loud guard for the GRAI-scan picking flow.
 * <p>
 * The picking job line here is a <b>TU pick</b> (finite PIIP on the shipment schedule). This test covers the
 * scan-time guard that has <b>no Playwright TC equivalent</b>: when the scanned GRAI resolves to a TU type whose
 * current PI version does NOT declare the GRAI HU-attribute slot, {@link PickingJobService#createTUFromGRAI} must
 * throw immediately at scan time (keyed {@code GRAIAttributeNotSupportedByTUType}) — instead of silently dropping
 * the GRAI and surfacing a confusing GRAI_COUNT_MISMATCH at pick completion.
 * <p>
 * The happy-path scan→reload→pick→GRAI-attribute-stamp flow is covered end-to-end by the Playwright spec
 * {@code e2e/mobile-webui/tests/spec/picking/picking-grai-scan.spec.js} TC1.
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
