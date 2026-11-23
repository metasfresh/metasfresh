package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.i18n.AdMessageKey;
import de.metas.picking.api.PickingSlotId;
import de.metas.scannable_code.ScannedCode;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Resolver chain "scanned code -> crate in this job's slots".
 * Runs on real (POJO) HU / attribute rows, so the query filters (active, top-level, HU ids of the job, GRAI value) are exercised:
 * a HU of another job that carries the same GRAI is planted and must never be reached.
 */
class HUConsolidationScannedCodeResolversTest
{
	private static final String GRAI = "7613204.00307.1234567890";
	private static final ScannedCode SCANNED_GRAI = ScannedCode.ofString(GRAI);
	private static final String OTHER_GRAI = "7613204.00307.1234567891";

	private static final PickingSlotId SLOT_1 = PickingSlotId.ofRepoId(11);
	private static final PickingSlotId SLOT_2 = PickingSlotId.ofRepoId(12);

	private GRAIScannedCodeResolver graiResolver;
	private HUConsolidationScannedCodeResolvers resolvers;
	private GRAICrateTestFixtures fixtures;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		fixtures = new GRAICrateTestFixtures();
		graiResolver = new GRAIScannedCodeResolver();
		resolvers = new HUConsolidationScannedCodeResolvers(ImmutableList.of(graiResolver));
	}

	private I_M_HU createTopLevelHU(final String graiValue) {return fixtures.createTopLevelHU(graiValue);}

	private I_M_HU createTUInside(final I_M_HU lu, final String graiValue) {return fixtures.createTUInside(lu, graiValue);}

	private static HuId id(final I_M_HU hu) {return GRAICrateTestFixtures.id(hu);}

	private static PickingSlotQueues queues(final I_M_HU hu1, final I_M_HU hu2)
	{
		return PickingSlotQueues.ofList(ImmutableList.of(
				GRAICrateTestFixtures.queue(SLOT_1, id(hu1)),
				GRAICrateTestFixtures.queue(SLOT_2, id(hu2))));
	}

	private static void assertMessage(final Throwable ex, final AdMessageKey expected)
	{
		assertThat(ex).isInstanceOf(AdempiereException.class);
		assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(expected.toAD_Message());
	}

	@Test
	void emptyCrateList_isHuNotFound_evenIfAnotherActiveTopLevelHUCarriesTheGRAI()
	{
		// without the guard the filter on HU ids would vanish and the HU of the other job would be found
		createTopLevelHU(GRAI);

		assertThatThrownBy(() -> resolvers.resolve(SCANNED_GRAI, PickingSlotQueues.EMPTY))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Test
	void zeroMatches_isHuNotFound()
	{
		final I_M_HU crate1 = createTopLevelHU(OTHER_GRAI);
		final I_M_HU crate2 = createTopLevelHU(null);

		assertThatThrownBy(() -> resolvers.resolve(SCANNED_GRAI, queues(crate1, crate2)))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Test
	void twoMatches_isHuAmbiguous()
	{
		final I_M_HU crate1 = createTopLevelHU(GRAI);
		final I_M_HU crate2 = createTopLevelHU(GRAI);

		assertThatThrownBy(() -> resolvers.resolve(SCANNED_GRAI, queues(crate1, crate2)))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_AMBIGUOUS));
	}

	@Test
	void oneMatch_isResolvedWithItsOwnSlot_andTheCrateOfAnotherJobWithTheSameGRAIIsNotReached()
	{
		final I_M_HU crate1 = createTopLevelHU(OTHER_GRAI);
		final I_M_HU crate2 = createTopLevelHU(GRAI);
		createTopLevelHU(GRAI); // another job: same GRAI, not in the job's crates

		final HUConsolidationScannedCrate crate = resolvers.resolve(SCANNED_GRAI, queues(crate1, crate2));

		assertThat(crate).isEqualTo(HUConsolidationScannedCrate.of(id(crate2), SLOT_2));
	}

	@Test
	void inactiveCrateWithGRAI_isHuNotFound()
	{
		final I_M_HU crate1 = createTopLevelHU(GRAI);
		crate1.setIsActive(false);
		save(crate1);
		final I_M_HU crate2 = createTopLevelHU(null);

		assertThatThrownBy(() -> resolvers.resolve(SCANNED_GRAI, queues(crate1, crate2)))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Test
	void graiTUInsideQueuedLU_isHuNotFound()
	{
		final I_M_HU lu = createTopLevelHU(null);
		final I_M_HU tuInsideLU = createTUInside(lu, GRAI);

		// even if its id were among the job's crates, only top-level HUs are crates
		assertThatThrownBy(() -> resolvers.resolve(SCANNED_GRAI, queues(lu, tuInsideLU)))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Nested
	class NonGraiCode
	{
		@Test
		void isNotMyCodeType_emptyResult()
		{
			final I_M_HU crate1 = createTopLevelHU(GRAI);
			final I_M_HU crate2 = createTopLevelHU(null);

			final Optional<HUConsolidationScannedCrate> crate = graiResolver.resolve(ScannedCode.ofString("not-a-grai"), queues(crate1, crate2));

			assertThat(crate).isEmpty();
		}

		@Test
		void throughChain_isInvalidGraiBarcode()
		{
			final I_M_HU crate1 = createTopLevelHU(GRAI);
			final I_M_HU crate2 = createTopLevelHU(null);

			assertThatThrownBy(() -> resolvers.resolve(ScannedCode.ofString("not-a-grai"), queues(crate1, crate2)))
					.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.INVALID_GRAI_BARCODE));
		}
	}

	@Test
	void blankCode_isInvalidGraiBarcode()
	{
		assertThatThrownBy(() -> resolvers.resolve(null, PickingSlotQueues.EMPTY))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.INVALID_GRAI_BARCODE));
	}

	@Test
	void chain_firstNonEmptyResolverWins()
	{
		final HUConsolidationScannedCrate expected = HUConsolidationScannedCrate.of(HuId.ofRepoId(101), SLOT_1);
		final HUConsolidationScannedCodeResolver notMine = (code, crates) -> Optional.empty();
		final HUConsolidationScannedCodeResolver mine = (code, crates) -> Optional.of(expected);
		final HUConsolidationScannedCodeResolver neverReached = (code, crates) -> {throw new AssertionError("must not be called");};

		final HUConsolidationScannedCodeResolvers chain = new HUConsolidationScannedCodeResolvers(ImmutableList.of(notMine, mine, neverReached));

		assertThat(chain.resolve(ScannedCode.ofString("whatever"), PickingSlotQueues.EMPTY)).isEqualTo(expected);
	}
}
