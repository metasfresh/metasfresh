package de.metas.handlingunits.picking.job.service.commands.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * TDD test for {@link PickingJobGraiTargetService#assertTuAllowedOnLu}.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobGraiTargetServiceTest
{
	private HUTestHelper huTestHelper;
	private PickingJobGraiTargetService service;

	/** LU PI that contains the TU PI via an M_HU_PI_Item(ItemType=HU). */
	private I_M_HU_PI luPI;
	/** TU PI that is linked to the LU PI. */
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

		// Create TU PI that will be linked to the LU PI
		tuPI = huTestHelper.createHUDefinition("TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		// Create a second TU PI that is NOT linked to the LU PI
		otherTuPI = huTestHelper.createHUDefinition("OTHER-TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		// Link tuPI to luPI via an M_HU_PI_Item(ItemType='HU', Included_HU_PI_ID=tuPI)
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("10"));
	}

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

	@NonNull
	private static LUPickingTarget buildNewLuTarget(@NonNull final I_M_HU_PI pi)
	{
		return LUPickingTarget.ofPackingInstructions(
				HuPackingInstructionsId.ofRepoId(pi.getM_HU_PI_ID()),
				pi.getName()
		);
	}
}
