package de.metas.distribution.ddorder.replenishment.alloc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
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
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.ModelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(AdempiereTestWatcher.class)
class DDOrderLineContributorRepositoryTest
{
	private DDOrderLineContributorRepository repository;
	private UomId uomId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// The in-memory harness does not enforce Postgres indexes; this stands in for ddorderline_pjs_active_uidx.
		POJOLookupMap.get().addModelValidator(new DD_OrderLine_PickingJobSchedule_ActiveUniqueIndexEmulator());
		repository = new DDOrderLineContributorRepository();
		uomId = UomId.ofRepoId(BusinessTestHelper.createUOM("PCE").getC_UOM_ID());
	}

	/**
	 * Emulates the DB's partial unique index {@code ddorderline_pjs_active_uidx} — created by migration {@code 5816200} on
	 * {@code (DD_OrderLine_ID, M_Picking_Job_Schedule_ID) WHERE IsActive = 'Y'}. A {@code CREATE UNIQUE INDEX} is not
	 * deferrable, so Postgres checks it per statement; that transient state is invisible to a post-condition assertion,
	 * hence this save-time stand-in.
	 */
	@Interceptor(I_DD_OrderLine_PickingJobSchedule.class)
	static class DD_OrderLine_PickingJobSchedule_ActiveUniqueIndexEmulator
	{
		@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
		public void rejectASecondActiveRowForTheSamePair(@NonNull final I_DD_OrderLine_PickingJobSchedule record)
		{
			if (!record.isActive())
			{
				return;
			}

			final boolean conflicts = Services.get(IQueryBL.class).createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, record.getDD_OrderLine_ID())
					.addEqualsFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID, record.getM_Picking_Job_Schedule_ID())
					.create()
					.stream()
					.anyMatch(other -> other.getDD_OrderLine_PickingJobSchedule_ID() != record.getDD_OrderLine_PickingJobSchedule_ID());
			if (conflicts)
			{
				throw new AdempiereException("ddorderline_pjs_active_uidx violated:"
						+ " a second active row for DD_OrderLine_ID=" + record.getDD_OrderLine_ID()
						+ ", M_Picking_Job_Schedule_ID=" + record.getM_Picking_Job_Schedule_ID());
			}
		}
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
				DDOrderLineContributor.of(scheduleId(10), qty(10)),
				DDOrderLineContributor.of(scheduleId(20), qty(7)),
				DDOrderLineContributor.of(scheduleId(30), qty(5))));

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

	/**
	 * An inactive row alongside its active sibling for the same {@code (line, assignment)} pair is a state the PARTIAL unique
	 * index permits, and one {@code retrieveRecordsByLineId} deliberately reads (it omits the active filter). Reconciling it
	 * must never put two active rows in the table at once — the index rejects that per statement, not at commit.
	 */
	@Test
	void replaceByLineId_reconcilesAnInactiveRowAndItsActiveSibling_withoutEverHoldingTwoActiveRows()
	{
		createRow(lineId(1), scheduleId(10), 3, false);
		createRow(lineId(1), scheduleId(10), 10, true);

		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(7))));

		assertThat(rowsByLineId(lineId(1)))
				.as("reconciled to exactly one row, active, carrying the new qty")
				.extracting(
						record -> PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID()),
						I_DD_OrderLine_PickingJobSchedule::isActive,
						record -> record.getQty().intValue())
				.containsExactly(tuple(scheduleId(10), true, 7));
	}

	@Test
	void replaceByLineId_reactivatesAPreExistingInactiveRow_insteadOfInsertingASecondOne()
	{
		final int inactiveRowId = createRow(lineId(1), scheduleId(10), 3, false).getDD_OrderLine_PickingJobSchedule_ID();

		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		assertThat(rowsByLineId(lineId(1)))
				.extracting(
						I_DD_OrderLine_PickingJobSchedule::getDD_OrderLine_PickingJobSchedule_ID,
						I_DD_OrderLine_PickingJobSchedule::isActive,
						record -> record.getQty().intValue())
				.containsExactly(tuple(inactiveRowId, true, 10));
	}

	@Test
	void replaceByLineId_deletesTheInactiveRowOfADepartedContributor()
	{
		createRow(lineId(1), scheduleId(99), 3, false);

		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		assertThat(rowsByLineId(lineId(1)))
				.as("an inactive row of a departed contributor is deleted, not left behind")
				.extracting(record -> PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID()))
				.containsExactly(scheduleId(10));
	}

	/**
	 * Two shares of ONE assignment cannot be persisted: the pair has a single {@code Qty} column, so whether the row should
	 * carry the sum or the last share is the caller's decision, not this repository's.
	 */
	@Test
	void replaceByLineId_rejectsTwoSharesOfTheSameAssignment()
	{
		assertThatThrownBy(() -> repository.replaceByLineId(lineId(1), ImmutableList.of(
				DDOrderLineContributor.of(scheduleId(10), qty(4)),
				DDOrderLineContributor.of(scheduleId(10), qty(6)))))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("contributes more than once");

		assertThat(rowsByLineId(lineId(1))).as("a rejected input writes nothing").isEmpty();
	}

	/**
	 * Writes a row directly: an INACTIVE row is a legacy/deactivated state that no repository method can produce, so there is
	 * no domain operation to drive it through.
	 */
	private I_DD_OrderLine_PickingJobSchedule createRow(
			@NonNull final DDOrderLineId lineId,
			@NonNull final PickingJobScheduleId pickingJobScheduleId,
			final int qty,
			final boolean active)
	{
		final I_DD_OrderLine_PickingJobSchedule record = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_PickingJobSchedule.class);
		record.setDD_OrderLine_ID(lineId.getRepoId());
		record.setM_Picking_Job_Schedule_ID(pickingJobScheduleId.getRepoId());
		record.setQty(BigDecimal.valueOf(qty));
		record.setC_UOM_ID(uomId.getRepoId());
		record.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}

	/** Every row of the line, inactive ones included — {@link DDOrderLineContributorRepository#getByLineId(DDOrderLineId)} hides those. */
	private static ImmutableList<I_DD_OrderLine_PickingJobSchedule> rowsByLineId(@NonNull final DDOrderLineId lineId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addEqualsFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineId)
				.orderBy(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.create()
				.listImmutable(I_DD_OrderLine_PickingJobSchedule.class);
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

	@Test
	void getByLineIds_returnsEmptyForEmptyLineIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		assertThat(repository.getByLineIds(ImmutableSet.of())).isEmpty();
	}

	@Test
	void getPickingJobScheduleIdsByLineId_groupsPerLine_andLeavesOutALineWithoutContributor()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(
				DDOrderLineContributor.of(scheduleId(10), qty(10)),
				DDOrderLineContributor.of(scheduleId(20), qty(2))));
		repository.replaceByLineId(lineId(2), ImmutableList.of(DDOrderLineContributor.of(scheduleId(30), qty(3))));
		repository.replaceByLineId(lineId(4), ImmutableList.of(DDOrderLineContributor.of(scheduleId(40), qty(4))));

		final ImmutableSetMultimap<DDOrderLineId, PickingJobScheduleId> actual =
				repository.getPickingJobScheduleIdsByLineId(ImmutableSet.of(lineId(1), lineId(2), lineId(3)));

		assertThat(actual.keySet()).as("only the given lines that have a contributor row")
				.containsExactlyInAnyOrder(lineId(1), lineId(2));
		assertThat(actual.get(lineId(1))).containsExactlyInAnyOrder(scheduleId(10), scheduleId(20));
		assertThat(actual.get(lineId(2))).containsExactly(scheduleId(30));
		assertThat(actual.get(lineId(3))).as("a line with no contributor row resolves to nothing, not to a foreign line's set")
				.isEmpty();
	}

	@Test
	void getPickingJobScheduleIdsByLineId_returnsEmptyForEmptyLineIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		assertThat(repository.getPickingJobScheduleIdsByLineId(ImmutableSet.of()).entries()).isEmpty();
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

	@Test
	void getLineIdsByPickingJobScheduleIds_returnsEmptyForEmptyIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		assertThat(repository.getLineIdsByPickingJobScheduleIds(ImmutableSet.of())).isEmpty();
	}

	@Test
	void deleteByLineIds_deletesNothingForEmptyLineIds_evenWhenMatchingContributorExists()
	{
		repository.replaceByLineId(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));

		repository.deleteByLineIds(ImmutableSet.of());

		assertThat(repository.getByLineId(lineId(1)))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId)
				.containsExactly(scheduleId(10));
	}

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
