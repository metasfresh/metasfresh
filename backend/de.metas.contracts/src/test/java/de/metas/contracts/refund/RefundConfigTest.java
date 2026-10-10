package de.metas.contracts.refund;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.InvoiceSchedule.Frequency;
import de.metas.invoice.InvoiceScheduleId;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class RefundConfigTest
{
	private static final InvoiceSchedule INVOICE_SCHEDULE = InvoiceSchedule.builder()
			.id(InvoiceScheduleId.ofRepoId(5))
			.frequency(Frequency.MONTLY)
			.invoiceDayOfMonth(1)
			.build();

	private static RefundConfig amountPerUnitConfig(@NonNull final String amountPerUnit)
	{
		return RefundConfig.builder()
				.conditionsId(ConditionsId.ofRepoId(20))
				.invoiceSchedule(INVOICE_SCHEDULE)
				.refundInvoiceType(RefundInvoiceType.INVOICE)
				.refundBase(RefundBase.AMOUNT_PER_UNIT)
				.refundMode(RefundMode.APPLY_TO_ALL_QTIES)
				.minQty(BigDecimal.ZERO)
				.amount(new BigDecimal(amountPerUnit))
				.build();
	}

	/**
	 * Refund configs are compared and used as map keys (e.g. when assignments are aggregated per config),
	 * so the same amount per unit with a different scale (as loaded from the DB vs. built in code) must be the same config, like with {@link de.metas.money.Money}.
	 */
	@Test
	public void equals_amountPerUnitDifferingOnlyInTrailingZeros()
	{
		final RefundConfig config = amountPerUnitConfig("0.5");
		final RefundConfig configWithTrailingZeros = amountPerUnitConfig("0.50");

		assertThat(configWithTrailingZeros).isEqualTo(config);
		assertThat(configWithTrailingZeros.hashCode()).isEqualTo(config.hashCode());
	}
}
