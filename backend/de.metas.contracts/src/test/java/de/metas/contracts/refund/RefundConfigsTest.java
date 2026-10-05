package de.metas.contracts.refund;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RefundConfigsTest
{
	private static final InvoiceSchedule INVOICE_SCHEDULE = InvoiceSchedule.builder()
			.id(InvoiceScheduleId.ofRepoId(5))
			.frequency(Frequency.MONTLY)
			.invoiceDayOfMonth(1)
			.build();

	private static RefundConfig config(@Nullable final Integer productId, @Nullable final Integer bonusProductId, final int minQty)
	{
		return RefundConfig.builder()
				.conditionsId(ConditionsId.ofRepoId(20))
				.invoiceSchedule(INVOICE_SCHEDULE)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.PERCENTAGE)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.minQty(BigDecimal.valueOf(minQty))
				.percent(Percent.of(10))
				.productId(productId == null ? null : ProductId.ofRepoId(productId))
				.bonusProductId(bonusProductId == null ? null : ProductId.ofRepoId(bonusProductId))
				.build();
	}

	@Test
	public void assertValid_configsWithDifferentBonusProducts_fails()
	{
		assertThatThrownBy(() -> RefundConfigs.assertValid(ImmutableList.of(config(null, 1, 0), config(null, 2, 10))))
				.hasMessageContaining("Bonus");
	}

	@Test
	public void assertValid_configsWithTheSameBonusProduct_isValid()
	{
		RefundConfigs.assertValid(ImmutableList.of(config(null, 1, 0), config(null, 1, 10), config(null, 1, 20)));
	}

	@Test
	public void extractRefundProductId_prefersTheBonusProduct()
	{
		assertThat(RefundConfigs.extractRefundProductId(ImmutableList.of(config(3, 1, 0), config(3, 1, 10)))).isEqualTo(ProductId.ofRepoId(1));
	}

	@Test
	public void extractRefundProductId_withoutBonusProduct_usesTheProduct()
	{
		assertThat(RefundConfigs.extractRefundProductId(ImmutableList.of(config(3, null, 0), config(3, null, 10)))).isEqualTo(ProductId.ofRepoId(3));
	}

	@Test
	public void extractRefundProductId_withoutAnyProduct_isNull()
	{
		assertThat(RefundConfigs.extractRefundProductId(ImmutableList.of(config(null, null, 0)))).isNull();
	}

	@Test
	public void extractRefundProductId_withDifferentBonusProducts_fails()
	{
		assertThatThrownBy(() -> RefundConfigs.extractRefundProductId(ImmutableList.of(config(null, 1, 0), config(null, 2, 10))))
				.isInstanceOf(RuntimeException.class);
	}

	@Test
	public void extractRefundProductId_withDifferentProducts_fails()
	{
		assertThatThrownBy(() -> RefundConfigs.extractRefundProductId(ImmutableList.of(config(3, null, 0), config(4, null, 10))))
				.isInstanceOf(RuntimeException.class);
	}
}
