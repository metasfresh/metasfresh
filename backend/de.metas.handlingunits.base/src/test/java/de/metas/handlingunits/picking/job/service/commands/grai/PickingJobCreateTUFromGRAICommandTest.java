package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * TDD test for {@link PickingJobCreateTUFromGRAICommand} — the four GRAI error paths
 * (steps 1..4 of the design §4). The happy path (step 5: physically create the TU + attach the GRAI)
 * requires a fully built {@code PickingJob} + locator and is exercised at integration / cucumber level;
 * here we drive the extracted {@link PickingJobCreateTUFromGRAICommand#resolveTuTypeAndCapacity} directly.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobCreateTUFromGRAICommandTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1001);

	private HUTestHelper huTestHelper;
	private PickingJobCreateTUFromGRAICommand command;

	/** LU PI that contains the TU PI via an M_HU_PI_Item(ItemType=HU). */
	private I_M_HU_PI luPI;
	/** TU PI that IS linked to the LU PI and HAS a PIIP for {@link #PRODUCT_ID}. */
	private I_M_HU_PI tuPI;

	@BeforeEach
	void setUp()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();

		// Wire the command with only the two services that step-1..4 (resolveTuTypeAndCapacity) actually uses.
		// The step-5 collaborators (pickingJobService / huService / huGraiService) and the request inputs
		// (pickingJob / scannedGrai / tuLocatorId) are left null: resolveTuTypeAndCapacity() takes its inputs
		// as parameters and never touches them.
		command = PickingJobCreateTUFromGRAICommand.builder()
				.graiTargetService(new PickingJobGraiTargetService())
				.huPIGraiRepository(new HUPIGraiRepository())
				.build();

		// LU PI containing the TU PI
		luPI = huTestHelper.createHUDefinition("LU-PI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		tuPI = huTestHelper.createHUDefinition("TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("10"));

		// TU PI gets a Material item + a PIIP for PRODUCT_ID (so capacity resolution succeeds for the happy parts)
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		huTestHelper.assignProduct(miItem, PRODUCT_ID, new BigDecimal("10"), huTestHelper.uomEach);
	}

	/** Step 1 — an unparseable scan throws InvalidGRAIBarcode. */
	@Test
	void unparseableScan_throwsInvalidGRAIBarcode()
	{
		assertThatThrownBy(() -> command.resolveTuTypeAndCapacity(
				"   ", // blank → GRAI.parse returns null
				Optional.empty(),
				PRODUCT_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("InvalidGRAIBarcode");
	}

	/** Step 2 — a parseable GRAI with no M_HU_PI_GRAI mapping throws GRAINoMatchingTUType. */
	@Test
	void noMapping_throwsGRAINoMatchingTUType()
	{
		// No M_HU_PI_GRAI rows inserted at all.
		assertThatThrownBy(() -> command.resolveTuTypeAndCapacity(
				"7613204.00307.999999",
				Optional.empty(),
				PRODUCT_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GRAINoMatchingTUType");
	}

	/** Step 3 — the resolved TU is not includable in the effective LU target → GRAITUNotAllowedOnLU. */
	@Test
	void tuNotAllowedOnLU_throwsGRAITUNotAllowedOnLU()
	{
		// A separate TU PI that is NOT linked to luPI, but IS mapped from the scanned GRAI.
		final I_M_HU_PI otherTuPI = huTestHelper.createHUDefinition("OTHER-TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		mapGrai("7613204", "00307", otherTuPI);

		final LUPickingTarget luTarget = LUPickingTarget.ofPackingInstructions(
				HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()),
				luPI.getName());

		assertThatThrownBy(() -> command.resolveTuTypeAndCapacity(
				"7613204.00307.999999",
				Optional.of(luTarget),
				PRODUCT_ID))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GRAITUNotAllowedOnLU");
	}

	/** Step 4 — the resolved TU has no PIIP for the line's product → GRAINoCapacityForProduct. */
	@Test
	void noCapacityForProduct_throwsGRAINoCapacityForProduct()
	{
		// Map the scanned GRAI to tuPI (which only has a PIIP for PRODUCT_ID),
		// then ask for a different product that has no PIIP.
		mapGrai("7613204", "00307", tuPI);
		final ProductId otherProductId = ProductId.ofRepoId(9999);

		final LUPickingTarget luTarget = LUPickingTarget.ofPackingInstructions(
				HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()),
				luPI.getName());

		assertThatThrownBy(() -> command.resolveTuTypeAndCapacity(
				"7613204.00307.999999",
				Optional.of(luTarget),
				otherProductId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("GRAINoCapacityForProduct");
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
