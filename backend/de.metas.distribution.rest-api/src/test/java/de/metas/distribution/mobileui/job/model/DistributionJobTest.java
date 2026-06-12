package de.metas.distribution.mobileui.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.mobileui.external_services.warehouse.WarehouseInfo;
import de.metas.user.UserId;
import de.metas.util.lang.SeqNo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers {@link DistributionJob#assertCanEdit(UserId)} — in particular the closed-job guard added for the
 * packing↔picking close-out: a DD_Order-backed distribution job that was closed under the picker (its DD_Order
 * is no longer Completed) must no longer be editable, even by its responsible user.
 */
class DistributionJobTest
{
	private static final UserId PICKER = UserId.ofRepoId(1234);

	private static DistributionJob job(final boolean isClosed, @Nullable final UserId responsibleId)
	{
		final WarehouseInfo wh = WarehouseInfo.builder().warehouseId(WarehouseId.ofRepoId(100)).caption("WH").build();
		final ZonedDateTime when = ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
		return DistributionJob.builder()
				.id(DistributionJobId.ofDDOrderId(DDOrderId.ofRepoId(5555)))
				.documentNo("DD-1")
				.seqNo(SeqNo.ofInt(10))
				.customerId(BPartnerId.ofRepoId(2222))
				.dateRequired(when)
				.pickDate(when)
				.pickFromWarehouse(wh)
				.dropToWarehouse(wh)
				.priority("5")
				.responsibleId(responsibleId)
				.isClosed(isClosed)
				.allowPickingAnyHU(false)
				.lines(ImmutableList.of())
				.build();
	}

	@Test
	void assertCanEdit_refuses_a_closed_job_even_for_its_responsible_user()
	{
		final DistributionJob closed = job(true, PICKER);
		assertThatThrownBy(() -> closed.assertCanEdit(PICKER))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("closed");
	}

	@Test
	void assertCanEdit_allows_an_open_job_for_its_responsible_user()
	{
		final DistributionJob open = job(false, PICKER);
		assertThatCode(() -> open.assertCanEdit(PICKER)).doesNotThrowAnyException();
	}

	@Test
	void assertCanEdit_refuses_an_open_job_not_assigned_to_the_user()
	{
		final DistributionJob open = job(false, UserId.ofRepoId(9999));
		assertThatThrownBy(() -> open.assertCanEdit(PICKER))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("not assigned");
	}
}
