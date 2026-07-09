package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.grai.DummyGRAITemplate;
import de.metas.handlingunits.grai.GRAI;
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
	// Tests for the Migros GRAI PO-reference-ownership gate
	// (assertBelongsToCurrentOrderIfMigros, reached before TU-type resolution)
	// =====================================================================

	private static final String MIGROS_MSG_KEY = "de.metas.handlingunits.picking.GRAIPOReferenceMismatch";
	private static final String NO_TU_TYPE_MSG_KEY = "de.metas.handlingunits.picking.GRAINoMatchingTUType";
	private static final String PREFIX_TOO_LONG_MSG_KEY = "de.metas.handlingunits.grai.DummyGRAISerialPrefixTooLong";

	@Test
	void migrosGrai_poReferenceMismatch_throwsPOReferenceMismatch()
	{
		// Migros GRAI whose serial derives from PO reference "12345"; the order carries a different PO reference.
		final ScannedCode migrosGrai = DummyGRAITemplate.migros("12345").buildGRAI(1).toScannedCode();

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(migrosGrai, null, null, /* poReference */ "99999"))
				.as("a Migros GRAI that does not match the order's PO reference must be rejected")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MIGROS_MSG_KEY);
	}

	@Test
	void migrosGrai_overLengthPoReference_throwsPOReferenceMismatchNotPrefixTooLong()
	{
		// Regression guard: an over-length (>10 char) PO reference can never have produced this GRAI's serial,
		// so it must surface as a PO-reference MISMATCH — NOT the unrelated dummy-GRAI "prefix too long" prerequisite
		// (which DummyGRAITemplate.migros(...) would otherwise throw for a non-dummy-GRAI customer's free-text PO ref).
		final ScannedCode migrosGrai = DummyGRAITemplate.migros("12345").buildGRAI(1).toScannedCode();

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(migrosGrai, null, null, /* poReference */ "ABCDEFGHIJKLMNOP"))
				.as("an over-length PO reference on a Migros GRAI scan must be a mismatch, not a prefix-too-long error")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MIGROS_MSG_KEY)
				.hasMessageNotContaining(PREFIX_TOO_LONG_MSG_KEY);
	}

	@Test
	void migrosGrai_poReferenceMatches_passesGateAndResolvesTuType()
	{
		// PO reference matches the GRAI's serial → the ownership gate passes; resolution then continues and fails
		// on the (unrelated) TU-type lookup because this test's PIs are not GRAI-mapped to 7613204.00307.
		final ScannedCode migrosGrai = DummyGRAITemplate.migros("12345").buildGRAI(1).toScannedCode();

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(migrosGrai, null, null, /* poReference */ "12345"))
				.as("a matching PO reference must pass the ownership gate (failure comes later, from TU-type resolution)")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(NO_TU_TYPE_MSG_KEY)
				.hasMessageNotContaining(MIGROS_MSG_KEY);
	}

	@Test
	void migrosGrai_maxLengthPoReference_10chars_isValidAndPassesGate()
	{
		// Boundary: a 10-char PO reference is the max valid dummy-GRAI serial prefix — it must be treated as valid
		// (isValidSerialPrefix == true) and, when it matches, pass the gate rather than be rejected as over-length.
		final ScannedCode migrosGrai = DummyGRAITemplate.migros("1234567890").buildGRAI(1).toScannedCode();

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(migrosGrai, null, null, /* poReference */ "1234567890"))
				.as("a 10-char matching PO reference is valid and passes the gate (failure comes later, from TU-type resolution)")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(NO_TU_TYPE_MSG_KEY)
				.hasMessageNotContaining(MIGROS_MSG_KEY);
	}

	@Test
	void migrosGrai_overBoundaryPoReference_11chars_throwsPOReferenceMismatch()
	{
		// Boundary: 11 chars is one past the valid serial-prefix length — it can never have produced this GRAI's
		// serial, so it is a mismatch, NOT the dummy-GRAI "prefix too long" prerequisite.
		final ScannedCode migrosGrai = DummyGRAITemplate.migros("12345").buildGRAI(1).toScannedCode();

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(migrosGrai, null, null, /* poReference */ "12345678901"))
				.as("an 11-char PO reference (one past the boundary) must be a mismatch, not a prefix-too-long error")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MIGROS_MSG_KEY)
				.hasMessageNotContaining(PREFIX_TOO_LONG_MSG_KEY);
	}

	@Test
	void nonMigrosGrai_neverSubjectToPoReferenceCheck()
	{
		// company-prefix 9999999 is not the Migros structure → the ownership gate is skipped entirely, even with a
		// non-matching PO reference; resolution proceeds and fails only on the TU-type lookup.
		final ScannedCode nonMigrosGrai = GRAI.ofCanonicalString("9999999.00307.000001").toScannedCode();

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(nonMigrosGrai, null, null, /* poReference */ "99999"))
				.as("a non-Migros GRAI is never subject to the PO-reference ownership check")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(NO_TU_TYPE_MSG_KEY)
				.hasMessageNotContaining(MIGROS_MSG_KEY);
	}

}
