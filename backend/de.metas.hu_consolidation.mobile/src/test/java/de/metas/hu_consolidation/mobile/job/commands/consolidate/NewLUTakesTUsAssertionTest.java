package de.metas.hu_consolidation.mobile.job.commands.consolidate;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Before crates are put on a new pallet: the pallet type must take each crate type. One lookup per crate type and partner, not per crate.
 */
class NewLUTakesTUsAssertionTest
{
	private static final HuPackingInstructionsId PALLET_PI = HuPackingInstructionsId.ofRepoId(1001);
	private static final HuPackingInstructionsId CRATE_PI = HuPackingInstructionsId.ofRepoId(1002);
	private static final HuPackingInstructionsId OTHER_CRATE_PI = HuPackingInstructionsId.ofRepoId(1003);
	private static final BPartnerId CUSTOMER = BPartnerId.ofRepoId(2);

	private IHandlingUnitsBL handlingUnitsBL;
	private NewLUTakesTUsAssertion assertion;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = mock(IHandlingUnitsBL.class);
		when(handlingUnitsBL.getPIName(PALLET_PI)).thenReturn("Einwegpalette");
		when(handlingUnitsBL.getPIName(CRATE_PI)).thenReturn("Kiste");
		when(handlingUnitsBL.getPIName(OTHER_CRATE_PI)).thenReturn("Andere Kiste");
		assertion = new NewLUTakesTUsAssertion(handlingUnitsBL);
	}

	private I_M_HU crate(final HuPackingInstructionsId piId)
	{
		final I_M_HU crate = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		crate.setC_BPartner_ID(CUSTOMER.getRepoId());
		InterfaceWrapperHelper.saveRecord(crate);
		when(handlingUnitsBL.getEffectivePackingInstructionsId(crate)).thenReturn(piId);
		return crate;
	}

	private void givenPalletTakes(final HuPackingInstructionsId crateType, final boolean takes)
	{
		when(handlingUnitsBL.isTUTypeTakenByLUType(PALLET_PI, crateType, CUSTOMER)).thenReturn(takes);
	}

	@Test
	void threeCratesOfOneType_palletTakesIt_oneLookup()
	{
		givenPalletTakes(CRATE_PI, true);

		assertThatCode(() -> assertion.assertNewLUTakesTUs(PALLET_PI, ImmutableList.of(crate(CRATE_PI), crate(CRATE_PI), crate(CRATE_PI))))
				.doesNotThrowAnyException();

		verify(handlingUnitsBL, times(1)).isTUTypeTakenByLUType(any(HuPackingInstructionsId.class), any(HuPackingInstructionsId.class), any());
	}

	@Test
	void secondCrateTypeRefused_failsWithPalletAndCrateTypeNames()
	{
		givenPalletTakes(CRATE_PI, true);
		givenPalletTakes(OTHER_CRATE_PI, false);

		assertThatThrownBy(() -> assertion.assertNewLUTakesTUs(PALLET_PI, ImmutableList.of(crate(CRATE_PI), crate(OTHER_CRATE_PI), crate(CRATE_PI))))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("de.metas.hu_consolidation.mobile.job.PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE")
				.hasMessageContaining("Einwegpalette")
				.hasMessageContaining("Andere Kiste");
	}

	@Test
	void virtualCrate_notChecked()
	{
		assertThatCode(() -> assertion.assertNewLUTakesTUs(PALLET_PI, ImmutableList.of(crate(HuPackingInstructionsId.VIRTUAL))))
				.doesNotThrowAnyException();

		verify(handlingUnitsBL, times(0)).isTUTypeTakenByLUType(any(HuPackingInstructionsId.class), any(HuPackingInstructionsId.class), any());
	}
}
