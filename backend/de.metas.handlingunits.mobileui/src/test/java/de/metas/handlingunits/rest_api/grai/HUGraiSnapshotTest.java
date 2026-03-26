package de.metas.handlingunits.rest_api.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.rest_api.grai.HUGraiDelta.AttributeChange;
import de.metas.handlingunits.rest_api.grai.HUGraiSnapshot.AggregateBlock;
import de.metas.handlingunits.rest_api.grai.HUGraiSnapshot.TU;
import lombok.NonNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

class HUGraiSnapshotTest
{
	private static final GRAI GRAI_A = GRAI.ofCanonicalString("7613204.00307.1000000001");
	private static final GRAI GRAI_B = GRAI.ofCanonicalString("7613204.00307.1000000002");
	private static final GRAI GRAI_C = GRAI.ofCanonicalString("7613204.00307.1000000003");
	private static final GRAI GRAI_D = GRAI.ofCanonicalString("7613204.00307.1000000004");

	// -------------------------------------------------------------------
	// GET path
	// -------------------------------------------------------------------

	@Nested
	class GetTUCount
	{
		@Test
		void disaggregatedOnly()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), null), TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of());

			assertThat(snapshot.getTUCount()).isEqualTo(QtyTU.ofInt(2));
		}

		@Test
		void aggregateOnly()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(1), QtyTU.ofInt(5), GRAISet.EMPTY)));

			assertThat(snapshot.getTUCount()).isEqualTo(QtyTU.ofInt(5));
		}

		@Test
		void mixed()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), null), TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(3), QtyTU.ofInt(3), GRAISet.EMPTY)));

			assertThat(snapshot.getTUCount()).isEqualTo(QtyTU.ofInt(5));
		}

		@Test
		void standaloneTU()
		{
			final HUGraiSnapshot snapshot = snapshotTU(HuId.ofRepoId(1), GRAI_A);

			assertThat(snapshot.getTUCount()).isEqualTo(QtyTU.ONE);
		}
	}

	@Nested
	class GetAllGrais
	{
		@Test
		void collectsFromTUsAndAggregateBlocks()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(3), QtyTU.ofInt(3), GRAISet.ofCollection(ImmutableList.of(GRAI_B, GRAI_C)))));

			assertThat(snapshot.getAllGrais().toSet()).containsExactlyInAnyOrder(GRAI_A, GRAI_B, GRAI_C);
		}

		@Test
		void emptyWhenNoGrais()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), null)),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(2), QtyTU.ofInt(2), GRAISet.EMPTY)));

			assertThat(snapshot.getAllGrais().isEmpty()).isTrue();
		}
	}

	// -------------------------------------------------------------------
	// computeDelta
	// -------------------------------------------------------------------

	@Nested
	class ComputeDelta
	{
		@Test
		void emptySnapshotEmptyDesired_noDelta()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), null)),
					ImmutableList.of());

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.EMPTY);

			assertThat(delta.isEmpty()).isTrue();
		}

		@Test
		void alreadyCorrect_noDelta()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), GRAI_B)),
					ImmutableList.of());

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B)));

			assertThat(delta.isEmpty()).isTrue();
		}

		@Test
		void idempotent_calledTwice_sameResult()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of());

			final GRAISet desired = GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B));
			final HUGraiDelta delta1 = snapshot.computeDelta(desired);
			final HUGraiDelta delta2 = snapshot.computeDelta(desired);

			assertThat(delta1).isEqualTo(delta2);
		}

		@Test
		void assignNewGraisToEmptyTUs()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), null), TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of());

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.of(GRAI_A)),
					AttributeChange.of(HuId.ofRepoId(2), GRAISet.of(GRAI_B)));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void clearWrongGrais_assignNewOnes()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), GRAI_B)),
					ImmutableList.of());

			// Replace A,B with C,D
			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(ImmutableList.of(GRAI_C, GRAI_D)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.of(GRAI_C)),
					AttributeChange.of(HuId.ofRepoId(2), GRAISet.of(GRAI_D)));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void keepCorrectGrai_replaceWrongOne()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), GRAI_B)),
					ImmutableList.of());

			// Keep A, replace B with C
			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_C)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(2), GRAISet.of(GRAI_C)));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void fewerGraisThanTUs_clearsExtraAndLeavesEmpty()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), GRAI_B), TU.of(HuId.ofRepoId(3), null)),
					ImmutableList.of());

			// Only keep A
			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.of(GRAI_A));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(2), GRAISet.EMPTY));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void moreGraisThanTUs_noAggregateBlock_reportsUnassigned()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), null)),
					ImmutableList.of());

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.of(GRAI_A)));
			assertThat(delta.hasUnassignedGrais()).isTrue();
			assertThat(delta.getUnassignedGrais().toSet()).containsExactly(GRAI_B);
		}

		@Test
		void overflowAbsorbedByAggregateBlock()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), null)),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(2), QtyTU.ofInt(5), GRAISet.EMPTY)));

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B, GRAI_C)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.of(GRAI_A)),
					AttributeChange.of(HuId.ofRepoId(2), GRAISet.ofCollection(ImmutableList.of(GRAI_B, GRAI_C))));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void aggregateBlock_clearRemovedGrais()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(1), QtyTU.ofInt(3), GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B)))));

			// Keep A, remove B
			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.of(GRAI_A));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.of(GRAI_A)));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void aggregateBlock_alreadyCorrect_noDelta()
		{
			final GRAISet existing = GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B));
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(1), QtyTU.ofInt(3), existing)));

			final HUGraiDelta delta = snapshot.computeDelta(existing);

			assertThat(delta.isEmpty()).isTrue();
		}

		@Test
		void clearAllGrais()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A)),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(2), QtyTU.ofInt(2), GRAISet.of(GRAI_B))));

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.EMPTY);

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.EMPTY),
					AttributeChange.of(HuId.ofRepoId(2), GRAISet.EMPTY));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void standaloneTU_assignGrai()
		{
			final HUGraiSnapshot snapshot = snapshotTU(HuId.ofRepoId(1), null);

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.of(GRAI_A));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.of(GRAI_A)));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void standaloneTU_replaceGrai()
		{
			final HUGraiSnapshot snapshot = snapshotTU(HuId.ofRepoId(1), GRAI_A);

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.of(GRAI_B));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.of(GRAI_B)));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void standaloneTU_twoGrais_reportsUnassigned()
		{
			final HUGraiSnapshot snapshot = snapshotTU(HuId.ofRepoId(1), null);

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(1), GRAISet.of(GRAI_A)));
			assertThat(delta.hasUnassignedGrais()).isTrue();
			assertThat(delta.getUnassignedGrais().toSet()).containsExactly(GRAI_B);
		}
	}

	// -------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------

	private static HUGraiSnapshot snapshotLU(
			@NonNull final ImmutableList<TU> tus,
			@NonNull final ImmutableList<AggregateBlock> aggregateBlocks)
	{
		return HUGraiSnapshot.builder()
				.huId(HuId.ofRepoId(1000))
				.tus(tus)
				.aggregateBlocks(aggregateBlocks)
				.build();
	}

	private static HUGraiSnapshot snapshotTU(@NonNull final HuId tuId, @Nullable final GRAI grai)
	{
		return HUGraiSnapshot.builder()
				.huId(tuId)
				.tus(ImmutableList.of(TU.of(tuId, grai)))
				.aggregateBlocks(ImmutableList.of())
				.build();
	}
}
