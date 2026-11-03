package de.metas.distribution.mobileui.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.mobileui.external_services.product.ProductInfo;
import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
import de.metas.distribution.mobileui.external_services.warehouse.WarehouseInfo;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.lang.SeqNo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers {@link DistributionJob#assertCanEdit(UserId)} and {@link DistributionJob#getSingleUnitQuantityOrNull()}.
 *
 * <p>The latter must be resilient to a DD_Order whose lines span multiple UOMs: the launcher caption can only show
 * one UOM, so a multi-UOM job must resolve to {@code null} (rendered blank) rather than throw — otherwise the whole
 * distribution launcher fails to load.</p>
 */
@ExtendWith(AdempiereTestWatcher.class)
class DistributionJobTest
{
	private static final UserId PICKER = UserId.ofRepoId(1234);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private static DistributionJob job(final boolean isClosed, @Nullable final UserId responsibleId)
	{
		return jobBuilder(isClosed, responsibleId).lines(ImmutableList.of()).build();
	}

	private static DistributionJob jobWithLines(final ImmutableList<DistributionJobLine> lines)
	{
		return jobBuilder(false, PICKER).lines(lines).build();
	}

	private static DistributionJob.DistributionJobBuilder jobBuilder(final boolean isClosed, @Nullable final UserId responsibleId)
	{
		final ZonedDateTime when = ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
		return DistributionJob.builder()
				.id(DistributionJobId.ofDDOrderId(DDOrderId.ofRepoId(5555)))
				.documentNo("DD-1")
				.seqNo(SeqNo.ofInt(10))
				.customerId(BPartnerId.ofRepoId(2222))
				.dateRequired(when)
				.pickDate(when)
				.pickFromWarehouse(WarehouseInfo.builder().warehouseId(WarehouseId.ofRepoId(100)).caption("PickFromWH").build())
				.dropToWarehouse(WarehouseInfo.builder().warehouseId(WarehouseId.ofRepoId(200)).caption("DropToWH").build())
				.priority("5")
				.responsibleId(responsibleId)
				.isClosed(isClosed)
				.allowPickingAnyHU(false);
	}

	private static DistributionJobLine line(final int lineId, final I_C_UOM uom, final String qty)
	{
		return DistributionJobLine.builder()
				.id(DistributionJobLineId.ofDDOrderLineId(DDOrderLineId.ofRepoId(lineId)))
				.product(ProductInfo.builder()
						.productId(ProductId.ofRepoId(1000000 + lineId))
						.caption(TranslatableStrings.anyLanguage("P" + lineId))
						.build())
				.qtyToMove(Quantity.of(qty, uom))
				.pickFromLocator(locator(200 + lineId))
				.dropToLocator(locator(300 + lineId))
				.steps(ImmutableList.of())
				.build();
	}

	private static LocatorInfo locator(final int locId)
	{
		final LocatorId locatorId = LocatorId.ofRepoId(100, locId);
		return LocatorInfo.builder()
				.locatorId(locatorId)
				.qrCode(LocatorQRCode.builder().locatorId(locatorId).caption("L" + locId).build())
				.caption("L" + locId)
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

	@Test
	void getSingleUnitQuantityOrNull_returnsNull_whenLinesSpanMultipleUOMs()
	{
		final I_C_UOM stk = BusinessTestHelper.createUOM("Stk", 0, 0);
		final I_C_UOM meter = BusinessTestHelper.createUOM("M", 2, 2);

		final DistributionJob job = jobWithLines(ImmutableList.of(line(1, stk, "5"), line(2, meter, "3")));

		// Multi-UOM job has no single caption quantity -> null, never a throw (the launcher must still load).
		assertThat(job.getSingleUnitQuantityOrNull()).isNull();
	}

	@Test
	void getSingleUnitQuantityOrNull_returnsSum_whenAllLinesShareOneUOM()
	{
		final I_C_UOM stk = BusinessTestHelper.createUOM("Stk", 0, 0);

		final DistributionJob job = jobWithLines(ImmutableList.of(line(1, stk, "5"), line(2, stk, "3")));

		final Quantity result = job.getSingleUnitQuantityOrNull();
		assertThat(result).isNotNull();
		assertThat(result.toBigDecimal()).isEqualByComparingTo("8");
		assertThat(result.getUomId()).isEqualTo(UomId.ofRepoId(stk.getC_UOM_ID()));
	}

	@Test
	void getSingleUnitQuantityOrNull_sumsAllLines_evenWhenTwoLinesCarryTheSameQtyAndUOM()
	{
		final I_C_UOM stk = BusinessTestHelper.createUOM("Stk", 0, 0);

		// Two lines each moving 5 Stk must total 10 — they must NOT be collapsed to a single 5 (the .distinct() trap).
		final DistributionJob job = jobWithLines(ImmutableList.of(line(1, stk, "5"), line(2, stk, "5")));

		final Quantity result = job.getSingleUnitQuantityOrNull();
		assertThat(result).isNotNull();
		assertThat(result.toBigDecimal()).isEqualByComparingTo("10");
		assertThat(result.getUomId()).isEqualTo(UomId.ofRepoId(stk.getC_UOM_ID()));
	}
}
