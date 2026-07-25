package de.metas.distribution.ddorder.replenishment.alloc;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
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
 * cannot pin the repository's own rewrite semantics (delete-then-insert, per-line isolation) precisely — a
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
	void replaceContributors_insertsRows()
	{
		repository.replaceContributors(lineId(1), ImmutableList.of(
				DDOrderLineContributor.of(scheduleId(10), qty(10)),
				DDOrderLineContributor.of(scheduleId(20), qty(2))));

		assertThat(repository.getContributors(lineId(1)))
				.extracting(DDOrderLineContributor::getPickingJobScheduleId, c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(tuple(scheduleId(10), 10), tuple(scheduleId(20), 2));
	}

	@Test
	void replaceContributors_isIdempotentAndDoesNotAccumulate()
	{
		repository.replaceContributors(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceContributors(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(7))));

		assertThat(repository.getContributors(lineId(1)))
				.extracting(c -> c.getQty().toBigDecimal().intValue())
				.containsExactly(7);
	}

	@Test
	void replaceContributors_withEmptyList_removesAll()
	{
		repository.replaceContributors(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceContributors(lineId(1), ImmutableList.of());

		assertThat(repository.getContributors(lineId(1))).isEmpty();
	}

	@Test
	void replaceContributors_doesNotTouchOtherLines()
	{
		repository.replaceContributors(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceContributors(lineId(2), ImmutableList.of(DDOrderLineContributor.of(scheduleId(20), qty(5))));

		repository.replaceContributors(lineId(1), ImmutableList.of());

		assertThat(repository.getContributors(lineId(2))).hasSize(1);
	}

	@Test
	void getLineIdsByPickingJobScheduleId_resolvesTheNavigationDirection()
	{
		repository.replaceContributors(lineId(1), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(10))));
		repository.replaceContributors(lineId(2), ImmutableList.of(DDOrderLineContributor.of(scheduleId(10), qty(3))));
		repository.replaceContributors(lineId(3), ImmutableList.of(DDOrderLineContributor.of(scheduleId(20), qty(3))));

		assertThat(repository.getLineIdsByPickingJobScheduleId(scheduleId(10)))
				.containsExactlyInAnyOrder(lineId(1), lineId(2));
	}
}
