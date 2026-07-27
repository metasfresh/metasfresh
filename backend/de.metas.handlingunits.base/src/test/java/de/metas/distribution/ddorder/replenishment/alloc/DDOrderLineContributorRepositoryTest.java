package de.metas.distribution.ddorder.replenishment.alloc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Covers the {@code DD_OrderLine_PickingJobSchedule} write/read mechanics that the group reconcile relies on:
 * a full rewrite of one line's contributor set must not accumulate, must not leak into a sibling line, and must
 * stay navigable in both directions (line → contributors, assignment → lines).
 *
 * <p>Unit-level on purpose: the reconcile's cucumber scenarios assert the resulting contributor set, but they
 * cannot pin the repository's own rewrite semantics (row-level reconcile, per-line isolation) precisely — a
 * repository that accumulated rows would still produce a plausible aggregate in an end-to-end run.</p>
 */
@ExtendWith(AdempiereTestWatcher.class)
class DDOrderLineContributorRepositoryTest
{
	private DDOrderLineContributorRepository repository;
	private UomId uomId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new DDOrderLineContributorRepository();
		uomId = UomId.ofRepoId(BusinessTestHelper.createUOM("PCE").getC_UOM_ID());
	}

	private Quantity qty(final int value) {return Quantitys.of(BigDecimal.valueOf(value), uomId);}

	private static DDOrderLineId lineId(final int repoId) {return DDOrderLineId.ofRepoId(repoId);}

	private static PickingJobScheduleId scheduleId(final int repoId) {return PickingJobScheduleId.ofRepoId(repoId);}

	@Test
	void replaceByLineId_insertsRows()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(
				DDOrderLineContributor.of(scheduleId(10), qty(10)),
				DDOrderLineContributor.of(scheduleId(20), qty(2))));

		assertThat(repository.getByLineId(lineId(1)))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(scheduleId(10), 10), tuple(scheduleId(20), 2));
	}

	@Test
	void replaceByLineId_isIdempotentAndDoesNotAccumulate()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(7))));

		assertThat(repository.getByLineId(lineId(1)))
				.extracting(c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(7);
	}

	@Test
	void replaceByLineId_withEmptyList_removesAll()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceByLineId(lineId(1), ImmutableList.of());

		assertThat(repository.getByLineId(lineId(1))).isEmpty();
	}

	@Test
	void replaceByLineId_doesNotTouchOtherLines()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceByLineId(lineId(2), ImmutableList.of(DDOrderLineContributor.of(scheduleId(20), qty(5))));

		repository.replaceByLineId(lineId(1), ImmutableList.of());

		assertThat(repository.getByLineId(lineId(2))).hasSize(1);
	}

	/**
	 * Row identity is the observable proof that a rewrite reconciles instead of re-creating: a contributor that survives
	 * the rewrite must keep its {@code DD_OrderLine_PickingJobSchedule_ID} (it was UPDATEd), a departed one's row must be
	 * gone, and a joined one must get a row that did not exist before.
	 *
	 * <p>The contributor-set assertions above cannot see this — delete-all-then-insert-all yields the exact same set,
	 * only with every row re-created (a DELETE + INSERT per contributor per reconcile pass, on a table the reconcile
	 * rewrites on every pass).</p>
	 */
	@Test
	void replaceByLineId_keepsTheRowOfASurvivingContributor()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(
				DDOrderLineContributor.of(scheduleId(10), qty(10)),
				DDOrderLineContributor.of(scheduleId(20), qty(2)),
				DDOrderLineContributor.of(scheduleId(40), qty(4))));

		final ImmutableMap<PickingJobScheduleId, Integer> rowIdsBefore = rowIdsByPickingJobScheduleId(lineId(1));
		assertThat(rowIdsBefore).containsOnlyKeys(scheduleId(10), scheduleId(20), scheduleId(40));

		repository.replaceByLineId(lineId(1), ImmutableList.of(
				DDOrderLineContributor.of(scheduleId(10), qty(10)),  // survives with an unchanged qty
				DDOrderLineContributor.of(scheduleId(20), qty(7)),   // survives with a changed qty
				DDOrderLineContributor.of(scheduleId(30), qty(5)))); // joins; scheduleId(40) departed

		final ImmutableMap<PickingJobScheduleId, Integer> rowIdsAfter = rowIdsByPickingJobScheduleId(lineId(1));

		assertThat(rowIdsAfter).as("departed contributor's row is gone, joined one's row is there")
				.containsOnlyKeys(scheduleId(10), scheduleId(20), scheduleId(30));
		assertThat(rowIdsAfter.get(scheduleId(10))).as("unchanged contributor keeps its row")
				.isEqualTo(rowIdsBefore.get(scheduleId(10)));
		assertThat(rowIdsAfter.get(scheduleId(20))).as("re-quantified contributor keeps its row")
				.isEqualTo(rowIdsBefore.get(scheduleId(20)));
		assertThat(rowIdsAfter.get(scheduleId(30))).as("joined contributor gets a new row")
				.isNotIn(rowIdsBefore.values());

		assertThat(repository.getByLineId(lineId(1)))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(scheduleId(10), 10), tuple(scheduleId(20), 7), tuple(scheduleId(30), 5));
	}

	private static ImmutableMap<PickingJobScheduleId, Integer> rowIdsByPickingJobScheduleId(@NonNull final DDOrderLineId lineId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineId)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID()),
						I_DD_OrderLine_PickingJobSchedule::getDD_OrderLine_PickingJobSchedule_ID));
	}

	@Test
	void getLineIdsByPickingJobScheduleId_resolvesTheNavigationDirection()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceByLineId(lineId(2), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(3))));
		repository.replaceByLineId(lineId(3), ImmutableList.of(DDOrderLineContributor.of(scheduleId(20), qty(3))));

		assertThat(repository.getLineIdsByPickingJobScheduleId(scheduleId(10)))
				.containsExactlyInAnyOrder(lineId(1), lineId(2));
	}

	/**
	 * An empty {@code lineIds} collection must yield no contributor ids, even though a row exists that the query would
	 * return if the {@code DD_OrderLine_ID} restriction were dropped. Pins the "empty input selects nothing" contract
	 * every caller relies on — a widening of the restriction (e.g. swapping {@code addInArrayFilter} for
	 * {@code addInArrayOrAllFilter}, which renders an empty IN-list as {@code 1=1} instead of {@code 1=0}) would
	 * otherwise report the assignment of the contributor created below as a contributor of "no lines".
	 */
	@Test
	void getPickingJobScheduleIds_returnsEmptyForEmptyLineIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		assertThat(repository.getPickingJobScheduleIds(ImmutableSet.of())).isEmpty();
	}

	@Test
	void getByLineIds_returnsEveryRowOfEveryGivenLine()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(
				DDOrderLineContributor.of(scheduleId(10), qty(10)),
				DDOrderLineContributor.of(scheduleId(20), qty(2))));
		repository.replaceByLineId(lineId(2), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(3))));
		repository.replaceByLineId(lineId(3), ImmutableList.of(DDOrderLineContributor.of(scheduleId(30), qty(7))));

		assertThat(repository.getByLineIds(ImmutableSet.of(lineId(1), lineId(2))))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactlyInAnyOrder(tuple(scheduleId(10), 10), tuple(scheduleId(20), 2), tuple(scheduleId(10), 3));
	}

	/**
	 * Pins the "empty input selects nothing" contract of the batched contributor read: its caller sums the result into
	 * the demand each assignment is already served, so a widened restriction would net EVERY alloc row in the instance
	 * off the group's demand and plan nothing at all.
	 */
	@Test
	void getByLineIds_returnsEmptyForEmptyLineIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		assertThat(repository.getByLineIds(ImmutableSet.of())).isEmpty();
	}

	@Test
	void getLineIdsByPickingJobScheduleIds_resolvesTheNavigationDirectionForSeveralAssignmentsAtOnce()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceByLineId(lineId(2), ImmutableList.of(DDOrderLineContributor.of(scheduleId(20), qty(3))));
		repository.replaceByLineId(lineId(3), ImmutableList.of(DDOrderLineContributor.of(scheduleId(30), qty(3))));

		assertThat(repository.getLineIdsByPickingJobScheduleIds(ImmutableSet.of(scheduleId(10), scheduleId(20))))
				.containsExactlyInAnyOrder(lineId(1), lineId(2));
	}

	/**
	 * An empty {@code pickingJobScheduleIds} collection must yield no line ids, even though a row exists that the query
	 * would return if the {@code M_Picking_Job_Schedule_ID} restriction were dropped. Pins the "empty input selects
	 * nothing" contract of the batched lookup: its caller feeds the result straight into the obsolete-alloc-row delete,
	 * so a widened restriction would drop every alloc row in the table.
	 */
	@Test
	void getLineIdsByPickingJobScheduleIds_returnsEmptyForEmptyIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		assertThat(repository.getLineIdsByPickingJobScheduleIds(ImmutableSet.of())).isEmpty();
	}

	/**
	 * An empty {@code lineIds} collection must delete nothing, even though a row exists that the delete would hit if the
	 * {@code DD_OrderLine_ID} restriction were dropped. Pins the "empty input deletes nothing" contract: a widening of
	 * the restriction (e.g. swapping {@code addInArrayFilter} for {@code addInArrayOrAllFilter}, which renders an empty
	 * IN-list as {@code 1=1} instead of {@code 1=0}) would wipe every alloc row in the table — including the unrelated
	 * contributor created below.
	 */
	@Test
	void deleteByLineIds_deletesNothingForEmptyLineIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		repository.deleteByLineIds(ImmutableSet.of());

		assertThat(repository.getByLineId(lineId(1)))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId)
				.containsExactly(scheduleId(10));
	}

	/**
	 * The assignment-keyed sibling of the test above, and the one with the widest blast radius: an empty
	 * {@code pickingJobScheduleIds} collection must delete nothing, even though a row exists that the delete would hit
	 * if the {@code M_Picking_Job_Schedule_ID} restriction were dropped. Were it dropped, EVERY alloc row in the table
	 * would go and every consolidated line would serve nobody — silently, with the suite otherwise still green.
	 */
	@Test
	void deleteByPickingJobScheduleIds_deletesNothingForEmptyIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		repository.deleteByPickingJobScheduleIds(ImmutableSet.of());

		assertThat(repository.getByLineId(lineId(1)))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId)
				.containsExactly(scheduleId(10));
	}
}
