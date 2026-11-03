package de.metas.handlingunits.picking.job.service.shelflife;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link PickingShelfLifeCheck}.
 * <p>
 * Undercut condition:
 * {@code bestBeforeDate != null && guaranteedDays > 0 && bestBeforeDate.isBefore(deliveryDate.plusDays(guaranteedDays))}
 * <p>
 * Guaranteed days resolver:
 * C_BPartner_Product.ShelfLifeMinDays when > 0, else M_Product.GuaranteeDaysMin.
 */
class PickingShelfLifeCheckTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1);
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(2);
	private static final OrgId ORG_ID = OrgId.ofRepoId(3);

	private PickingJobProductService productService;
	private PickingJobBPartnerService bpartnerService;
	private PickingShelfLifeCheck pickingShelfLifeCheck;

	@BeforeEach
	void setUp()
	{
		productService = mock(PickingJobProductService.class);
		bpartnerService = mock(PickingJobBPartnerService.class);
		pickingShelfLifeCheck = new PickingShelfLifeCheck(productService, bpartnerService);
	}

	/** Sets up mock guaranteed days for the given product and bpartner. */
	private void givenGuaranteedDays(final int bpProductShelfLifeMinDays, final int productGuaranteeDaysMin)
	{
		when(bpartnerService.getBPartnerProductShelfLifeMinDays(BPARTNER_ID, PRODUCT_ID, ORG_ID))
				.thenReturn(bpProductShelfLifeMinDays);
		when(productService.getGuaranteeDaysMin(PRODUCT_ID))
				.thenReturn(productGuaranteeDaysMin);
	}

	private boolean check(@Nullable final LocalDate bestBeforeDate, final LocalDate deliveryDate)
	{
		return pickingShelfLifeCheck.isRemainingShelfLifeTooShort(PRODUCT_ID, BPARTNER_ID, ORG_ID, bestBeforeDate, deliveryDate);
	}

	@Nested
	class undercut_true
	{
		@Test
		void bestBeforeDate_is_before_deliveryDate_plus_guaranteedDays()
		{
			// guaranteedDays = 10 (from bp-product)
			// deliveryDate = 2025-01-10, so threshold = 2025-01-20
			// bestBeforeDate = 2025-01-15 < threshold → undercut
			givenGuaranteedDays(10, 5);

			final LocalDate deliveryDate = LocalDate.of(2025, 1, 10);
			final LocalDate bestBeforeDate = LocalDate.of(2025, 1, 15);

			assertThat(check(bestBeforeDate, deliveryDate)).isTrue();
		}
	}

	@Nested
	class undercut_false
	{
		@Test
		void bestBeforeDate_on_or_after_threshold()
		{
			// guaranteedDays = 10 (from bp-product)
			// deliveryDate = 2025-01-10, threshold = 2025-01-20
			// bestBeforeDate = 2025-01-20 (equal to threshold) → NOT undercut (isBefore is strict)
			givenGuaranteedDays(10, 5);

			final LocalDate deliveryDate = LocalDate.of(2025, 1, 10);
			final LocalDate bestBeforeDate = LocalDate.of(2025, 1, 20);

			assertThat(check(bestBeforeDate, deliveryDate)).isFalse();
		}
	}

	@Nested
	class guaranteed_days_resolution
	{
		@Test
		void bp_product_ShelfLifeMinDays_wins_over_product_when_greater_than_zero()
		{
			// BP-product ShelfLifeMinDays=10, product GuaranteeDaysMin=5
			// Effective guaranteedDays = 10
			// deliveryDate=2025-01-10, threshold=2025-01-20
			// bestBeforeDate=2025-01-15 < threshold → undercut (proves 10 was used, not 5)
			givenGuaranteedDays(10, 5);

			final LocalDate deliveryDate = LocalDate.of(2025, 1, 10);
			final LocalDate bestBeforeDate = LocalDate.of(2025, 1, 15);

			// With guaranteedDays=10: threshold=Jan 20 → bestBefore Jan 15 < Jan 20 → undercut
			// With guaranteedDays=5:  threshold=Jan 15 → bestBefore Jan 15 NOT < Jan 15 → not undercut
			// So isTrue proves that 10 was used.
			assertThat(check(bestBeforeDate, deliveryDate)).isTrue();
		}

		@Test
		void fallback_to_product_GuaranteeDaysMin_when_bp_value_is_zero_or_negative()
		{
			// BP-product ShelfLifeMinDays=0 → fallback to product GuaranteeDaysMin=5
			// Effective guaranteedDays = 5
			// deliveryDate=2025-01-10, threshold=2025-01-15
			// bestBeforeDate=2025-01-13 < threshold → undercut (proves 5 was used, not 10)
			givenGuaranteedDays(0, 5);

			final LocalDate deliveryDate = LocalDate.of(2025, 1, 10);
			final LocalDate bestBeforeDate = LocalDate.of(2025, 1, 13);

			// With guaranteedDays=5:  threshold=Jan 15 → bestBefore Jan 13 < Jan 15 → undercut
			// With guaranteedDays=0:  guaranteedDays <= 0 → never undercut
			// So isTrue proves that fallback to 5 occurred.
			assertThat(check(bestBeforeDate, deliveryDate)).isTrue();

			// A negative BP value (not > 0) likewise falls through to the product value.
			givenGuaranteedDays(-1, 5);
			assertThat(check(bestBeforeDate, deliveryDate)).isTrue();
		}
	}

	@Nested
	class no_undercut_special_cases
	{
		@Test
		void guaranteed_days_zero_or_negative_never_undercuts()
		{
			// Both BP and product return 0 → guaranteedDays=0 → condition is false
			givenGuaranteedDays(0, 0);

			final LocalDate deliveryDate = LocalDate.of(2025, 1, 10);
			final LocalDate bestBeforeDate = LocalDate.of(2025, 1, 1); // far in the past

			assertThat(check(bestBeforeDate, deliveryDate)).isFalse();
		}

		@Test
		void null_bestBeforeDate_never_undercuts()
		{
			// bestBeforeDate is null → condition is false regardless of guaranteedDays
			givenGuaranteedDays(30, 20);

			final LocalDate deliveryDate = LocalDate.of(2025, 1, 10);

			assertThat(check(null, deliveryDate)).isFalse();
		}
	}
}
