package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TC21 part 1: resolver chain "scanned code -> crate in this job's slots".
 * The HU query is a mock, so a test can plant "another active top-level HU carries that GRAI" (a HU of another job)
 * and prove that the resolver does not reach it.
 */
class HUConsolidationScannedCodeResolversTest
{
	private static final String GRAI = "7613204.00307.1234567890";

	private static final HuId HU_1 = HuId.ofRepoId(101);
	private static final HuId HU_2 = HuId.ofRepoId(102);
	private static final HuId HU_OF_OTHER_JOB = HuId.ofRepoId(999);

	private static final PickingSlotId SLOT_1 = PickingSlotId.ofRepoId(11);
	private static final PickingSlotId SLOT_2 = PickingSlotId.ofRepoId(12);

	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHUQueryBuilder huQueryBuilder;
	private HUConsolidationScannedCodeResolvers resolvers;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		handlingUnitsDAO = mock(IHandlingUnitsDAO.class);
		huQueryBuilder = mock(IHUQueryBuilder.class, Answers.RETURNS_SELF);
		when(handlingUnitsDAO.createHUQueryBuilder()).thenReturn(huQueryBuilder);
		Services.registerService(IHandlingUnitsDAO.class, handlingUnitsDAO);

		resolvers = new HUConsolidationScannedCodeResolvers(ImmutableList.of(new GRAIScannedCodeResolver()));
	}

	private static HUConsolidationJobCrates jobCrates()
	{
		return HUConsolidationJobCrates.of(ImmutableMap.of(HU_1, SLOT_1, HU_2, SLOT_2));
	}

	private static void assertMessage(final Throwable ex, final de.metas.i18n.AdMessageKey expected)
	{
		assertThat(ex).isInstanceOf(AdempiereException.class);
		assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(expected.toAD_Message());
	}

	@Test
	void emptyCrateList_isHuNotFound_withoutAnyHuQuery()
	{
		// another active top-level HU carries that GRAI: without the guard the filter on HU ids would vanish and this one would be found
		when(huQueryBuilder.listIds()).thenReturn(ImmutableSet.of(HU_OF_OTHER_JOB));

		assertThatThrownBy(() -> resolvers.resolve(GRAI, HUConsolidationJobCrates.EMPTY))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));

		verify(handlingUnitsDAO, never()).createHUQueryBuilder();
	}

	@Test
	void zeroMatches_isHuNotFound()
	{
		when(huQueryBuilder.listIds()).thenReturn(ImmutableSet.of());

		assertThatThrownBy(() -> resolvers.resolve(GRAI, jobCrates()))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Test
	void twoMatches_isHuAmbiguous()
	{
		when(huQueryBuilder.listIds()).thenReturn(ImmutableSet.of(HU_1, HU_2));

		assertThatThrownBy(() -> resolvers.resolve(GRAI, jobCrates()))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_AMBIGUOUS));
	}

	@Test
	void oneMatch_isResolvedWithItsOwnSlot()
	{
		when(huQueryBuilder.listIds()).thenReturn(ImmutableSet.of(HU_2));

		final HUConsolidationScannedCrate crate = resolvers.resolve(GRAI, jobCrates());

		assertThat(crate).isEqualTo(HUConsolidationScannedCrate.of(HU_2, SLOT_2));
		verify(huQueryBuilder).addOnlyHUIds(ImmutableSet.of(HU_1, HU_2));
		verify(huQueryBuilder).addOnlyWithAttribute(AttributeConstants.ATTR_GRAI, GRAI);
	}

	@Test
	void nonGraiCode_isNotMyCodeType_noQuery()
	{
		final Optional<HUConsolidationScannedCrate> crate = new GRAIScannedCodeResolver().resolve("not-a-grai", jobCrates());

		assertThat(crate).isEmpty();
		verify(handlingUnitsDAO, never()).createHUQueryBuilder();
	}

	@Test
	void nonGraiCode_throughChain_isInvalidGraiBarcode()
	{
		assertThatThrownBy(() -> resolvers.resolve("not-a-grai", jobCrates()))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.INVALID_GRAI_BARCODE));
	}

	@Test
	void chain_firstNonEmptyResolverWins()
	{
		final HUConsolidationScannedCrate expected = HUConsolidationScannedCrate.of(HU_1, SLOT_1);
		final HUConsolidationScannedCodeResolver notMine = (code, crates) -> Optional.empty();
		final HUConsolidationScannedCodeResolver mine = (code, crates) -> Optional.of(expected);
		final HUConsolidationScannedCodeResolver neverReached = (code, crates) -> {throw new AssertionError("must not be called");};

		final HUConsolidationScannedCodeResolvers chain = new HUConsolidationScannedCodeResolvers(ImmutableList.of(notMine, mine, neverReached));

		assertThat(chain.resolve("whatever", jobCrates())).isEqualTo(expected);
	}

	@Test
	void loader_emptySlotSet_isEmpty_withoutQueueQuery()
	{
		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.load(ImmutableSet.of(), de.metas.handlingunits.picking.slot.PickingSlotService.newInstanceForUnitTesting());

		assertThat(crates).isSameAs(HUConsolidationJobCrates.EMPTY);
		assertThat(crates.getHuIds()).isEmpty();
	}

	@Test
	void crates_getPickingSlotId()
	{
		assertThat(jobCrates().getPickingSlotId(HU_2)).isEqualTo(SLOT_2);
		assertThat(jobCrates().getHuIds()).containsExactlyInAnyOrder(HU_1, HU_2);
	}
}
