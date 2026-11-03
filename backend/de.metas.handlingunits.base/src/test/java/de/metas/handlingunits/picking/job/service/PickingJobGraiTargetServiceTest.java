package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.grai.DummyGRAITemplate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link PickingJobGraiTargetService} — only the paths NOT covered by the Playwright
 * spec {@code e2e/mobile-webui/tests/spec/picking/picking-grai-scan.spec.js}:
 * <ul>
 *   <li>{@link PickingJobGraiTargetService#assertTuAllowedOnLu} against an <b>existing-LU</b> target
 *       (the {@code ofExistingHU} branch — all TCs use a new-LU-from-PI target).</li>
 *   <li>{@link PickingJobGraiTargetService#resolveCapacity} default-for-product PIIP selection when
 *       multiple PIIPs match the product (no TC sets up two PIIPs for one product).</li>
 * </ul>
 * The four GRAI error paths and the resolution happy-path are covered end-to-end by TC1–TC6.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobGraiTargetServiceTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1001);

	private HUTestHelper huTestHelper;
	private PickingJobGraiTargetService service;

	/** LU PI that contains the TU PI via an M_HU_PI_Item(ItemType=HU). */
	private I_M_HU_PI luPI;
	/** TU PI that IS linked to the LU PI and HAS a PIIP for {@link #PRODUCT_ID}. */
	private I_M_HU_PI tuPI;

	@BeforeEach
	void setUp()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();

		service = new PickingJobGraiTargetService(PickingJobHUService.newInstanceForUnitTesting());

		// Create LU PI
		luPI = huTestHelper.createHUDefinition("LU-PI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		// Create TU PI that will be linked to the LU PI and carries a PIIP for PRODUCT_ID
		tuPI = huTestHelper.createHUDefinition("TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("10"));
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		huTestHelper.assignProduct(miItem, PRODUCT_ID, new BigDecimal("10"), huTestHelper.uomEach);
	}

	// =====================================================================
	// Tests for assertTuAllowedOnLu
	// =====================================================================

	@Test
	void existingLU_tuPIIncluded_doesNotThrow()
	{
		// Create a minimal existing LU whose PI is luPI
		final I_M_HU_PI_Version luPIVersion = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(luPI);
		final I_M_HU lu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		lu.setM_HU_PI_Version_ID(luPIVersion.getM_HU_PI_Version_ID());
		InterfaceWrapperHelper.save(lu);
		final HuId luId = HuId.ofRepoId(lu.getM_HU_ID());

		// Build a minimal HUQRCode — assertTuAllowedOnLu only uses luId, not the QR code content
		final HUQRCode dummyQRCode = HUQRCode.builder()
				.id(HUQRCodeUniqueId.random())
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.LU)
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()))
						.caption(luPI.getName())
						.build())
				.attributes(ImmutableList.of())
				.build();

		final LUPickingTarget existingLuTarget = LUPickingTarget.ofExistingHU(luId, dummyQRCode);
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());

		assertThatCode(() -> service.assertTuAllowedOnLu(tuPIId, existingLuTarget))
				.doesNotThrowAnyException();
	}

	// =====================================================================
	// Tests for resolveCapacity
	// =====================================================================

	@Test
	void resolveCapacity_defaultForProductPreferredOverNonDefault()
	{
		// Arrange: TU PI with two PIIPs for the same product — one default, one not
		final I_M_HU_PI tuPI2 = huTestHelper.createHUDefinition("TU-PI-DEFAULT", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI2);
		final ProductId productId = ProductId.ofRepoId(1003);

		// Non-default PIIP
		final I_M_HU_PI_Item_Product nonDefaultPiip = huTestHelper.assignProduct(miItem, productId, new BigDecimal("10"), huTestHelper.uomEach);
		nonDefaultPiip.setIsDefaultForProduct(false);
		InterfaceWrapperHelper.save(nonDefaultPiip);

		// Default PIIP
		final I_M_HU_PI_Item_Product defaultPiip = huTestHelper.assignProduct(miItem, productId, new BigDecimal("20"), huTestHelper.uomEach);
		defaultPiip.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(defaultPiip);

		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI2.getM_HU_PI_ID());

		// Act
		final HUPIItemProductId result = service.resolveCapacity(tuPIId, productId);

		// Assert: the default-for-product PIIP should be preferred
		assertThat(result).isEqualTo(HUPIItemProductId.ofRepoId(defaultPiip.getM_HU_PI_Item_Product_ID()));
	}

	// =====================================================================
	// A scanned Migros returnable-asset GRAI is NOT gated against the order's PO reference
	// =====================================================================

	private static final String MIGROS_MSG_KEY = "de.metas.handlingunits.picking.GRAIPOReferenceMismatch";
	private static final String NO_TU_TYPE_MSG_KEY = "de.metas.handlingunits.picking.GRAINoMatchingTUType";

	@Test
	void migrosGrai_poReferenceMismatch_isAccepted()
	{
		// A Migros-structured GRAI (companyPrefix 7613204 / assetType 00307) is not matched against any sales
		// order's PO reference at scan time — Migros GRAIs are dummy GRAIs generated at shipment, so there is
		// nothing authoritative to validate against here. Resolution proceeds straight to TU-type resolution and
		// fails only there (this test's PIs are not GRAI-mapped) — never with a PO-reference-mismatch key.
		final ScannedCode migrosGrai = DummyGRAITemplate.migros("12345").buildGRAI(1).toScannedCode();

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(migrosGrai, null, null))
				.as("a Migros GRAI must NOT be refused as a PO-reference mismatch")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(NO_TU_TYPE_MSG_KEY)
				.hasMessageNotContaining(MIGROS_MSG_KEY);
	}

}
