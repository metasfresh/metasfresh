package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.model.I_M_HU;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Presence of a GRAI on an active top-level HU among the given crates (the "Scan (GRAI)" button rule).
 * Runs on real (POJO) HU / attribute rows, so the query filters (active, top-level, attribute not null) are exercised.
 */
class HUConsolidationGRAIPresenceTest
{
	private static final String GRAI_CODE = "7613204.00307.1234567890";

	private HUConsolidationGRAIPresence presence;
	private GRAICrateTestFixtures fixtures;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		fixtures = new GRAICrateTestFixtures();
		presence = new HUConsolidationGRAIPresence();
	}

	private I_M_HU createTopLevelHU(final String graiValue) {return fixtures.createTopLevelHU(graiValue);}

	private I_M_HU createTUInside(final I_M_HU lu, final String graiValue) {return fixtures.createTUInside(lu, graiValue);}

	@Test
	void topLevelCrateWithGRAI_counts()
	{
		final I_M_HU crate = createTopLevelHU(GRAI_CODE);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(crate)))).isTrue();
	}

	@Test
	void severalCratesWithGRAI_count_withoutException()
	{
		// several GRAI crates in the slots: the check must not demand a unique match
		final I_M_HU crate1 = createTopLevelHU(GRAI_CODE);
		final I_M_HU crate2 = createTopLevelHU("7613204.00307.1234567891");
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(crate1), GRAICrateTestFixtures.id(crate2)))).isTrue();
	}

	@Test
	void dummyGRAI_counts()
	{
		final I_M_HU crate = createTopLevelHU("7613204.00307.DUMMY-1");
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(crate)))).isTrue();
	}

	@Test
	void topLevelLUQueuedInSlotWithGRAI_counts()
	{
		// slot case: the queued HU is a top-level LU that itself carries the GRAI
		final I_M_HU lu = createTopLevelHU(GRAI_CODE);
		assertThat(presence.isAnyGRAIPresent(ImmutableList.of(GRAICrateTestFixtures.id(lu)))).isTrue();
	}

	@Test
	void graiTUInsideQueuedLU_doesNotCount()
	{
		final I_M_HU lu = createTopLevelHU(null);
		createTUInside(lu, GRAI_CODE);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(lu)))).isFalse();
	}

	@Test
	void crateWithoutGRAI_doesNotCount()
	{
		final I_M_HU crate = createTopLevelHU(null);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(crate)))).isFalse();
	}

	@Test
	void inactiveCrateWithGRAI_doesNotCount()
	{
		final I_M_HU crate = createTopLevelHU(GRAI_CODE);
		crate.setIsActive(false);
		save(crate);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(crate)))).isFalse();
	}

	@Test
	void emptyStringValue_isCountedByTheFilter_butNotAGRAIOnTheReadSide()
	{
		// the filter is "attribute value IS NOT NULL", so '' counts as present ...
		final I_M_HU crate = createTopLevelHU("");
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(crate)))).isTrue();
		// ... while the read side (HUGraiSnapshotLoader.readGRAI -> GRAI.ofNullableCanonicalString) sees no GRAI
		assertThat(GRAI.ofNullableCanonicalString("")).isNull();
		assertThat(GRAI.ofNullableCanonicalString("  ")).isNull();
	}

	@Test
	void nonCanonicalValue_isCountedByTheFilter_readSideRejectsIt()
	{
		final I_M_HU crate = createTopLevelHU("not-canonical");
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(crate)))).isTrue();
		assertThrows(RuntimeException.class, () -> GRAI.ofNullableCanonicalString("not-canonical"));
	}

	@Test
	void emptyCrateList_isFalse_withoutQuery_evenIfAnotherActiveTopLevelHUCarriesAGRAI()
	{
		// a HU of another job carries a GRAI: without the guard addOnlyHUIds(empty) would drop the filter and find it
		createTopLevelHU(GRAI_CODE);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of())).isFalse();
	}

	@Test
	void crateOfAnotherJob_isNotSeen()
	{
		final I_M_HU mine = createTopLevelHU(null);
		createTopLevelHU(GRAI_CODE); // other job
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(GRAICrateTestFixtures.id(mine)))).isFalse();
	}
}
