package de.metas.handlingunits.picking.job.service.commands.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.service.commands.grai.PickingJobGraiTargetService.GraiTuResolution;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link PickingJobGraiTargetService}.
 * <p>
 * Covers {@link PickingJobGraiTargetService#assertTuAllowedOnLu} (B3 TU-LU check),
 * {@link PickingJobGraiTargetService#resolveCapacity} (B3 capacity resolution), and
 * {@link PickingJobGraiTargetService#resolveTuTypeAndCapacity} — the four GRAI error paths
 * (steps 1..4 of the design §4).
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
	/** Another TU PI that is NOT linked to the LU PI. */
	private I_M_HU_PI otherTuPI;

	@BeforeEach
	void setUp()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();

		service = new PickingJobGraiTargetService();

		// Create LU PI
		luPI = huTestHelper.createHUDefinition("LU-PI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		// Create TU PI that will be linked to the LU PI and carries a PIIP for PRODUCT_ID
		tuPI = huTestHelper.createHUDefinition("TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("10"));
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		huTestHelper.assignProduct(miItem, PRODUCT_ID, new BigDecimal("10"), huTestHelper.uomEach);

		// Create a second TU PI that is NOT linked to the LU PI
		otherTuPI = huTestHelper.createHUDefinition("OTHER-TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
	}

	// =====================================================================
	// Tests for assertTuAllowedOnLu
	// =====================================================================

	@Test
	void tuPIIncludedInLuPI_doesNotThrow()
	{
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());
		final LUPickingTarget luTarget = buildNewLuTarget(luPI);

		assertThatCode(() -> service.assertTuAllowedOnLu(tuPIId, luTarget))
				.doesNotThrowAnyException();
	}

	@Test
	void tuPINotIncludedInLuPI_throwsGRAITUNotAllowedOnLU()
	{
		final HuPackingInstructionsId otherTuPIId = HuPackingInstructionsId.ofRepoId(otherTuPI.getM_HU_PI_ID());
		final LUPickingTarget luTarget = buildNewLuTarget(luPI);

		assertThatThrownBy(() -> service.assertTuAllowedOnLu(otherTuPIId, luTarget))
				.isInstanceOf(AdempiereException.class);
	}

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
	void resolveCapacity_piipExistsForProduct_returnsItsId()
	{
		// Arrange: build a TU PI with a Material MI item carrying a PIIP for product P
		final I_M_HU_PI tuPI2 = huTestHelper.createHUDefinition("TU-PI-CAP", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI2);
		final ProductId productId = ProductId.ofRepoId(1001);
		final I_M_HU_PI_Item_Product piip = huTestHelper.assignProduct(miItem, productId, new BigDecimal("10"), huTestHelper.uomEach);

		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI2.getM_HU_PI_ID());

		// Act
		final HUPIItemProductId result = service.resolveCapacity(tuPIId, productId);

		// Assert
		assertThat(result).isEqualTo(HUPIItemProductId.ofRepoId(piip.getM_HU_PI_Item_Product_ID()));
	}

	@Test
	void resolveCapacity_noPiipForProduct_throwsAdempiereException()
	{
		// Arrange: TU PI with a PIIP for product P1, but query is for P2
		final I_M_HU_PI tuPI2 = huTestHelper.createHUDefinition("TU-PI-NOCAP", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI2);
		final ProductId otherProductId = ProductId.ofRepoId(1002);
		huTestHelper.assignProduct(miItem, otherProductId, new BigDecimal("5"), huTestHelper.uomEach);

		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI2.getM_HU_PI_ID());
		final ProductId requestedProductId = ProductId.ofRepoId(9999); // different product — no PIIP

		// Act & Assert
		assertThatThrownBy(() -> service.resolveCapacity(tuPIId, requestedProductId))
				.isInstanceOf(AdempiereException.class);
	}

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
	// Tests for resolveTuTypeAndCapacity (the four GRAI error paths, steps 1..4)
	// The happy-path (step 5: physically create the TU + attach the GRAI) requires a fully built
	// PickingJob + locator and is exercised at integration / Playwright E2E level.
	//
	// Note on hasMessageContaining("...GRAI..."): the assertions match the AD_Message key string
	// because the unit-test environment has no AD_Message rows loaded, so getMessage() returns
	// the key itself (e.g. "de.metas.handlingunits.picking.InvalidGRAIBarcode").
	// =====================================================================

	/** Step 1 — an unparseable scan throws InvalidGRAIBarcode. */
	@Test
	void resolveTuTypeAndCapacity_unparseableScan_throwsInvalidGRAIBarcode()
	{
		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(
				"   ", // blank → GRAI.parse returns null
				Optional.empty(),
				PRODUCT_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("InvalidGRAIBarcode"); // matches AD_Message key (no rows in test env)
	}

	/** Step 2 — a parseable GRAI with no M_HU_PI_GRAI mapping throws GRAINoMatchingTUType. */
	@Test
	void resolveTuTypeAndCapacity_noMapping_throwsGRAINoMatchingTUType()
	{
		// No M_HU_PI_GRAI rows inserted at all.
		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(
				"7613204.00307.999999",
				Optional.empty(),
				PRODUCT_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GRAINoMatchingTUType"); // matches AD_Message key (no rows in test env)
	}

	/** Step 3 — the resolved TU is not includable in the effective LU target → GRAITUNotAllowedOnLU. */
	@Test
	void resolveTuTypeAndCapacity_tuNotAllowedOnLU_throwsGRAITUNotAllowedOnLU()
	{
		// A separate TU PI that is NOT linked to luPI, but IS mapped from the scanned GRAI.
		mapGrai("7613204", "00307", otherTuPI);

		final LUPickingTarget luTarget = LUPickingTarget.ofPackingInstructions(
				HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()),
				luPI.getName());

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(
				"7613204.00307.999999",
				Optional.of(luTarget),
				PRODUCT_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GRAITUNotAllowedOnLU"); // matches AD_Message key (no rows in test env)
	}

	/** Step 4 — the resolved TU has no PIIP for the line's product → GRAINoCapacityForProduct. */
	@Test
	void resolveTuTypeAndCapacity_noCapacityForProduct_throwsGRAINoCapacityForProduct()
	{
		// Map the scanned GRAI to tuPI (which only has a PIIP for PRODUCT_ID),
		// then ask for a different product that has no PIIP.
		mapGrai("7613204", "00307", tuPI);
		final ProductId otherProductId = ProductId.ofRepoId(9999);

		final LUPickingTarget luTarget = LUPickingTarget.ofPackingInstructions(
				HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()),
				luPI.getName());

		assertThatThrownBy(() -> service.resolveTuTypeAndCapacity(
				"7613204.00307.999999",
				Optional.of(luTarget),
				otherProductId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GRAINoCapacityForProduct"); // matches AD_Message key (no rows in test env)
	}

	/** Step 4 happy path — all steps pass and the result holds the expected IDs. */
	@Test
	void resolveTuTypeAndCapacity_allStepsPass_returnsResolution()
	{
		mapGrai("7613204", "00307", tuPI);

		final GraiTuResolution result = service.resolveTuTypeAndCapacity(
				"7613204.00307.999999",
				Optional.empty(),
				PRODUCT_ID);

		assertThat(result.getTuPIId()).isEqualTo(HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID()));
		assertThat(result.getHuPIItemProductId()).isNotNull();
	}

	@NonNull
	private static LUPickingTarget buildNewLuTarget(@NonNull final I_M_HU_PI pi)
	{
		return LUPickingTarget.ofPackingInstructions(
				HuPackingInstructionsId.ofRepoId(pi.getM_HU_PI_ID()),
				pi.getName()
		);
	}

	private void mapGrai(
			@NonNull final String companyPrefix,
			@NonNull final String assetType,
			@NonNull final I_M_HU_PI huPI)
	{
		final I_M_HU_PI_GRAI record = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		record.setGRAI_CompanyPrefix(companyPrefix);
		record.setGRAI_AssetType(assetType);
		record.setM_HU_PI_ID(huPI.getM_HU_PI_ID());
		InterfaceWrapperHelper.save(record);
	}
}
