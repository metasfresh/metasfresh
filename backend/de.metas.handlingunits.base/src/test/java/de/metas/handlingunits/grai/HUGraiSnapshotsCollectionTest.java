package de.metas.handlingunits.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.HUGraiSnapshot.TU;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HUGraiSnapshotsCollectionTest
{
	private static final GRAI GRAI_A = GRAI.ofCanonicalString("7613204.00307.1000000001");
	private static final GRAI GRAI_B = GRAI.ofCanonicalString("7613204.00307.1000000002");

	@Test
	void empty()
	{
		assertThat(HUGraiSnapshotsCollection.EMPTY.isEmpty()).isTrue();
	}

	@Test
	void assertAllGraisAssigned_allOk()
	{
		final HUGraiSnapshotsCollection collection = HUGraiSnapshotsCollection.ofCollection(ImmutableList.of(
				snapshot(HuId.ofRepoId(1), GRAI_A),
				snapshot(HuId.ofRepoId(2), GRAI_B)));

		collection.assertAllGraisAssigned(); // should not throw
	}

	@Test
	void assertAllGraisAssigned_oneMissing_throws()
	{
		final HUGraiSnapshotsCollection collection = HUGraiSnapshotsCollection.ofCollection(ImmutableList.of(
				snapshot(HuId.ofRepoId(1), GRAI_A),
				snapshot(HuId.ofRepoId(2), null)));

		assertThatThrownBy(collection::assertAllGraisAssigned)
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void findMaxExistingDummyCounter_acrossSnapshots()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");

		final HUGraiSnapshotsCollection collection = HUGraiSnapshotsCollection.ofCollection(ImmutableList.of(
				snapshotWithGrai(HuId.ofRepoId(1), template.buildGRAI(3)),
				snapshotWithGrai(HuId.ofRepoId(2), template.buildGRAI(7))));

		assertThat(collection.findMaxExistingDummyCounter(template)).isEqualTo(7);
	}

	@Test
	void findMaxExistingDummyCounter_noDummies()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");

		final HUGraiSnapshotsCollection collection = HUGraiSnapshotsCollection.ofCollection(ImmutableList.of(
				snapshot(HuId.ofRepoId(1), GRAI_A)));

		assertThat(collection.findMaxExistingDummyCounter(template)).isZero();
	}

	@Test
	void collect_fromStream()
	{
		final HUGraiSnapshotsCollection collection = ImmutableList.of(
						snapshot(HuId.ofRepoId(1), GRAI_A),
						snapshot(HuId.ofRepoId(2), GRAI_B))
				.stream()
				.collect(HUGraiSnapshotsCollection.collect());

		assertThat(collection.isEmpty()).isFalse();
		int count = 0;
		for (@SuppressWarnings("unused") final HUGraiSnapshot ignored : collection)
		{
			count++;
		}
		assertThat(count).isEqualTo(2);
	}

	private static HUGraiSnapshot snapshot(final HuId huId, final GRAI grai)
	{
		return HUGraiSnapshot.builder()
				.huId(huId)
				.tus(ImmutableList.of(TU.of(huId, grai)))
				.aggregateBlocks(ImmutableList.of())
				.build();
	}

	private static HUGraiSnapshot snapshotWithGrai(final HuId huId, final GRAI grai)
	{
		return snapshot(huId, grai);
	}
}
