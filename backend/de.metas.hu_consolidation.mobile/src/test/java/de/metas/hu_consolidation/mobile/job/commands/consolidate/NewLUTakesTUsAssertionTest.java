package de.metas.hu_consolidation.mobile.job.commands.consolidate;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Crates put on a new pallet: the first crate is checked the way the new pallet is created for it, the other crates the way they are put on the
 * pallet created then. One check per crate type, not per crate.
 */
class NewLUTakesTUsAssertionTest
{
	private static final HuPackingInstructionsId PALLET_PI = HuPackingInstructionsId.ofRepoId(1001);
	private static final HuPackingInstructionsId CRATE_PI = HuPackingInstructionsId.ofRepoId(1002);
	private static final HuPackingInstructionsId OTHER_CRATE_PI = HuPackingInstructionsId.ofRepoId(1003);
	private static final BPartnerId CUSTOMER = BPartnerId.ofRepoId(2);

	private IHandlingUnitsBL handlingUnitsBL;
	private HUTransformService huTransformService;
	private NewLUTakesTUsAssertion assertion;
	private I_M_HU pallet;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = mock(IHandlingUnitsBL.class);
		huTransformService = mock(HUTransformService.class);
		when(handlingUnitsBL.getPIName(PALLET_PI)).thenReturn("Einwegpalette");
		when(handlingUnitsBL.getPIName(CRATE_PI)).thenReturn("Kiste");
		when(handlingUnitsBL.getPIName(OTHER_CRATE_PI)).thenReturn("Andere Kiste");
		assertion = new NewLUTakesTUsAssertion(handlingUnitsBL, huTransformService);

		pallet = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.saveRecord(pallet);
		when(handlingUnitsBL.getPIId(pallet)).thenReturn(PALLET_PI);
	}

	private I_M_HU crate(final HuPackingInstructionsId piId)
	{
		final I_M_HU crate = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		crate.setC_BPartner_ID(CUSTOMER.getRepoId());
		InterfaceWrapperHelper.saveRecord(crate);
		when(handlingUnitsBL.getEffectivePackingInstructionsId(crate)).thenReturn(piId);
		return crate;
	}

	@Nested
	class FirstCrate_beforeTheNewPalletExists
	{
		@Test
		void palletTypeTakesTheCrateType_ok()
		{
			when(handlingUnitsBL.isTUTypeTakenByLUType(PALLET_PI, CRATE_PI, CUSTOMER)).thenReturn(true);

			assertThatCode(() -> assertion.assertNewLUTakesTU(PALLET_PI, crate(CRATE_PI))).doesNotThrowAnyException();
		}

		@Test
		void palletTypeDoesNotTakeTheCrateType_failsWithBothTypeNames()
		{
			when(handlingUnitsBL.isTUTypeTakenByLUType(PALLET_PI, CRATE_PI, CUSTOMER)).thenReturn(false);

			assertThatThrownBy(() -> assertion.assertNewLUTakesTU(PALLET_PI, crate(CRATE_PI)))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("de.metas.hu_consolidation.mobile.job.PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE")
					.hasMessageContaining("Einwegpalette")
					.hasMessageContaining("Kiste");
		}

		@Test
		void crateWithoutRealPackingInstructions_notChecked()
		{
			assertThatCode(() -> assertion.assertNewLUTakesTU(PALLET_PI, crate(HuPackingInstructionsId.VIRTUAL))).doesNotThrowAnyException();

			verify(handlingUnitsBL, never()).isTUTypeTakenByLUType(any(), any(), any());
		}
	}

	@Nested
	class OtherCrates_ontoTheNewPallet
	{
		@Test
		void threeCratesOfTwoTypes_oneCheckPerType_withTheMoveOntoAnExistingPallet()
		{
			final I_M_HU crate1 = crate(CRATE_PI);
			final I_M_HU otherCrate = crate(OTHER_CRATE_PI);
			final I_M_HU crate2 = crate(CRATE_PI);
			when(huTransformService.isTUAttachableToExistingLU(any(I_M_HU.class), any(I_M_HU.class))).thenReturn(true);

			assertThatCode(() -> assertion.assertLUTakesTUs(pallet, ImmutableList.of(crate1, otherCrate, crate2))).doesNotThrowAnyException();

			verify(huTransformService, times(2)).isTUAttachableToExistingLU(any(I_M_HU.class), any(I_M_HU.class));
			verify(handlingUnitsBL, never()).isTUTypeTakenByLUType(any(), any(), any());
		}

		/** e.g. the pallet type has only a product-specific item for the second crate type: the move onto the pallet decides, as it would attach it */
		@Test
		void secondCrateTypeRefusedByTheMove_failsWithBothTypeNames()
		{
			final I_M_HU crate1 = crate(CRATE_PI);
			final I_M_HU otherCrate = crate(OTHER_CRATE_PI);
			when(huTransformService.isTUAttachableToExistingLU(crate1, pallet)).thenReturn(true);
			when(huTransformService.isTUAttachableToExistingLU(otherCrate, pallet)).thenReturn(false);

			assertThatThrownBy(() -> assertion.assertLUTakesTUs(pallet, ImmutableList.of(crate1, otherCrate)))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("de.metas.hu_consolidation.mobile.job.PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE")
					.hasMessageContaining("Einwegpalette")
					.hasMessageContaining("Andere Kiste");
		}
	}
}
