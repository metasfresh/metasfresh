package de.metas.handlingunits.grai;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.grai.HUGraiDelta.AttributeChange;
import de.metas.handlingunits.grai.HUGraiSnapshot.AggregateBlock;
import de.metas.handlingunits.grai.HUGraiSnapshot.TU;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HUGraiSnapshotTest
{
	private static final GRAI GRAI_A = GRAI.ofCanonicalString("7613204.00307.1000000001");
	private static final GRAI GRAI_B = GRAI.ofCanonicalString("7613204.00307.1000000002");
	private static final GRAI GRAI_C = GRAI.ofCanonicalString("7613204.00307.1000000003");
	private static final GRAI GRAI_D = GRAI.ofCanonicalString("7613204.00307.1000000004");
	private static final GRAI GRAI_E = GRAI.ofCanonicalString("7613204.00307.1000000005");
	private static final GRAI GRAI_F = GRAI.ofCanonicalString("7613204.00307.1000000006");
	private static final GRAI GRAI_G = GRAI.ofCanonicalString("7613204.00307.1000000007");
	private static final GRAI GRAI_H = GRAI.ofCanonicalString("7613204.00307.1000000008");
	private static final GRAI GRAI_I = GRAI.ofCanonicalString("7613204.00307.1000000009");
	private static final GRAI GRAI_J = GRAI.ofCanonicalString("7613204.00307.1000000010");

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

	@Nested
	class IsAllGraisAssigned
	{
		@Test
		void allAssigned()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), GRAI_B)),
					ImmutableList.of());

			assertThat(snapshot.isAllGraisAssigned()).isTrue();
		}

		@Test
		void someMissing()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of());

			assertThat(snapshot.isAllGraisAssigned()).isFalse();
		}

		@Test
		void aggregatePartiallyAssigned()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(1), QtyTU.ofInt(3), GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B)))));

			assertThat(snapshot.isAllGraisAssigned()).isFalse();
		}

		@Test
		void aggregateFullyAssigned()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(1), QtyTU.ofInt(2), GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B)))));

			assertThat(snapshot.isAllGraisAssigned()).isTrue();
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
	// computeDelta — multiple aggregate blocks (one per product on an LU)
	// -------------------------------------------------------------------

	@Nested
	class ComputeDelta_MultipleAggregateBlocks
	{
		@Test
		void threeBlocks_3_3_3_distributesAcrossBlocks()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(
							AggregateBlock.of(HuId.ofRepoId(10), QtyTU.ofInt(3), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(20), QtyTU.ofInt(3), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(30), QtyTU.ofInt(3), GRAISet.EMPTY)));

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(
					ImmutableList.of(GRAI_A, GRAI_B, GRAI_C, GRAI_D, GRAI_E, GRAI_F, GRAI_G, GRAI_H, GRAI_I)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(10), GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B, GRAI_C))),
					AttributeChange.of(HuId.ofRepoId(20), GRAISet.ofCollection(ImmutableList.of(GRAI_D, GRAI_E, GRAI_F))),
					AttributeChange.of(HuId.ofRepoId(30), GRAISet.ofCollection(ImmutableList.of(GRAI_G, GRAI_H, GRAI_I))));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		/**
		 * Real-world shape from me03 bug report: 3 products on one LU with tuCounts 3 / 5 / 1 = 9 TUs,
		 * 9 GRAIs scanned. Before the fix, all 9 ended up on the first VHU.
		 */
		@Test
		void threeBlocks_3_5_1_distributesByCapacity()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(
							AggregateBlock.of(HuId.ofRepoId(10), QtyTU.ofInt(3), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(20), QtyTU.ofInt(5), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(30), QtyTU.ofInt(1), GRAISet.EMPTY)));

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(
					ImmutableList.of(GRAI_A, GRAI_B, GRAI_C, GRAI_D, GRAI_E, GRAI_F, GRAI_G, GRAI_H, GRAI_I)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(10), GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B, GRAI_C))),
					AttributeChange.of(HuId.ofRepoId(20), GRAISet.ofCollection(ImmutableList.of(GRAI_D, GRAI_E, GRAI_F, GRAI_G, GRAI_H))),
					AttributeChange.of(HuId.ofRepoId(30), GRAISet.of(GRAI_I)));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void threeBlocks_partialFill_lastBlockStaysEmpty()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(
							AggregateBlock.of(HuId.ofRepoId(10), QtyTU.ofInt(3), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(20), QtyTU.ofInt(3), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(30), QtyTU.ofInt(3), GRAISet.EMPTY)));

			// Only 5 GRAIs for 9 slots → block 10 gets 3, block 20 gets 2, block 30 stays empty (no change).
			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(
					ImmutableList.of(GRAI_A, GRAI_B, GRAI_C, GRAI_D, GRAI_E)));

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(10), GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B, GRAI_C))),
					AttributeChange.of(HuId.ofRepoId(20), GRAISet.ofCollection(ImmutableList.of(GRAI_D, GRAI_E))));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void threeBlocks_preservesExistingValidGraisInLaterBlocks()
		{
			// Block 20 already has GRAI_E (correctly assigned). The same desired set must not move it.
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(
							AggregateBlock.of(HuId.ofRepoId(10), QtyTU.ofInt(2), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(20), QtyTU.ofInt(2), GRAISet.of(GRAI_E)),
							AggregateBlock.of(HuId.ofRepoId(30), QtyTU.ofInt(2), GRAISet.EMPTY)));

			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(
					ImmutableList.of(GRAI_A, GRAI_B, GRAI_E, GRAI_C, GRAI_D, GRAI_F)));

			// Block 10: empty → fills from unassigned (A, B).
			// Block 20: keeps existing E, fills one more slot from unassigned (C).
			// Block 30: empty → fills from unassigned (D, F).
			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(10), GRAISet.ofCollection(ImmutableList.of(GRAI_A, GRAI_B))),
					AttributeChange.of(HuId.ofRepoId(20), GRAISet.ofCollection(ImmutableList.of(GRAI_E, GRAI_C))),
					AttributeChange.of(HuId.ofRepoId(30), GRAISet.ofCollection(ImmutableList.of(GRAI_D, GRAI_F))));
			assertThat(delta.hasUnassignedGrais()).isFalse();
		}

		@Test
		void threeBlocks_overflow_reportsUnassigned()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(
							AggregateBlock.of(HuId.ofRepoId(10), QtyTU.ofInt(3), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(20), QtyTU.ofInt(3), GRAISet.EMPTY),
							AggregateBlock.of(HuId.ofRepoId(30), QtyTU.ofInt(3), GRAISet.EMPTY)));

			// 10 GRAIs for 9 slots → 1 unassigned.
			final HUGraiDelta delta = snapshot.computeDelta(GRAISet.ofCollection(
					ImmutableList.of(GRAI_A, GRAI_B, GRAI_C, GRAI_D, GRAI_E, GRAI_F, GRAI_G, GRAI_H, GRAI_I, GRAI_J)));

			assertThat(delta.getChanges()).hasSize(3);
			assertThat(delta.hasUnassignedGrais()).isTrue();
			assertThat(delta.getUnassignedGrais().size()).isEqualTo(1);
		}
	}

	// -------------------------------------------------------------------
	// assertAllGraisAssigned
	// -------------------------------------------------------------------

	@Nested
	class AssertAllGraisAssigned
	{
		@Test
		void allAssigned_noException()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A)),
					ImmutableList.of());

			snapshot.assertAllGraisAssigned(); // should not throw
		}

		@Test
		void someMissing_throws()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of());

			assertThatThrownBy(snapshot::assertAllGraisAssigned)
					.isInstanceOf(AdempiereException.class);
		}
	}

	// -------------------------------------------------------------------
	// findMaxDummyCounter
	// -------------------------------------------------------------------

	@Nested
	class FindMaxDummyCounter
	{
		@Test
		void noDummies_returnsZero()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A)),
					ImmutableList.of());

			assertThat(snapshot.findMaxDummyCounter(DummyGRAITemplate.migros("1234567890"))).isZero();
		}

		@Test
		void withDummies_returnsMax()
		{
			final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
			final GRAI dummy3 = template.buildGRAI(3);
			final GRAI dummy7 = template.buildGRAI(7);

			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), dummy3), TU.of(HuId.ofRepoId(2), dummy7)),
					ImmutableList.of());

			assertThat(snapshot.findMaxDummyCounter(template)).isEqualTo(7);
		}

		@Test
		void mixedRealAndDummy()
		{
			final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
			final GRAI dummy5 = template.buildGRAI(5);

			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), dummy5)),
					ImmutableList.of());

			assertThat(snapshot.findMaxDummyCounter(template)).isEqualTo(5);
		}

		@Test
		void dummiesInAggregateBlock()
		{
			final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
			final GRAI dummy2 = template.buildGRAI(2);
			final GRAI dummy9 = template.buildGRAI(9);

			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(1), QtyTU.ofInt(3), GRAISet.ofCollection(ImmutableList.of(dummy2, dummy9)))));

			assertThat(snapshot.findMaxDummyCounter(template)).isEqualTo(9);
		}
	}

	// -------------------------------------------------------------------
	// generateMissingGRAIs
	// -------------------------------------------------------------------

	@Nested
	class GenerateMissingGRAIs
	{
		@Test
		void allAssigned_emptyDelta()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), GRAI_B)),
					ImmutableList.of());

			final DummyGRAIProvider provider = new DummyGRAIProvider(DummyGRAITemplate.migros("1234567890"), 1);
			final HUGraiDelta delta = snapshot.generateMissingGRAIs(provider);

			assertThat(delta.isEmpty()).isTrue();
		}

		@Test
		void fillsMissingTUs()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), GRAI_A), TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of());

			final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
			final DummyGRAIProvider provider = new DummyGRAIProvider(template, 1);
			final HUGraiDelta delta = snapshot.generateMissingGRAIs(provider);

			assertThat(delta.getChanges()).containsExactly(
					AttributeChange.of(HuId.ofRepoId(2), GRAISet.of(template.buildGRAI(1))));
			assertThat(provider.getNextCounter()).isEqualTo(2);
		}

		@Test
		void fillsMissingInAggregate()
		{
			final HUGraiSnapshot snapshot = snapshotLU(
					ImmutableList.of(),
					ImmutableList.of(AggregateBlock.of(HuId.ofRepoId(1), QtyTU.ofInt(3), GRAISet.of(GRAI_A))));

			final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
			final DummyGRAIProvider provider = new DummyGRAIProvider(template, 1);
			final HUGraiDelta delta = snapshot.generateMissingGRAIs(provider);

			// Block should now contain GRAI_A + 2 dummies
			assertThat(delta.getChanges()).hasSize(1);
			final GRAISet newBlockGrais = delta.getChanges().get(0).getNewValue();
			assertThat(newBlockGrais.size()).isEqualTo(3);
			assertThat(newBlockGrais.contains(GRAI_A)).isTrue();
			assertThat(provider.getNextCounter()).isEqualTo(3);
		}

		@Test
		void providerCounterAdvancesAcrossCalls()
		{
			final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
			final DummyGRAIProvider provider = new DummyGRAIProvider(template, 5);

			final HUGraiSnapshot snapshot1 = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(1), null)),
					ImmutableList.of());
			snapshot1.generateMissingGRAIs(provider);
			assertThat(provider.getNextCounter()).isEqualTo(6);

			final HUGraiSnapshot snapshot2 = snapshotLU(
					ImmutableList.of(TU.of(HuId.ofRepoId(2), null)),
					ImmutableList.of());
			snapshot2.generateMissingGRAIs(provider);
			assertThat(provider.getNextCounter()).isEqualTo(7);
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
