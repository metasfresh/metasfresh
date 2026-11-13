package de.metas.banking.payment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaySelectionTrxTypeTest
{
	@Test
	void isCreditTransfer()
	{
		assertThat(PaySelectionTrxType.CREDIT_TRANSFER.isCreditTransfer()).isTrue();
		assertThat(PaySelectionTrxType.DIRECT_DEBIT.isCreditTransfer()).isFalse();
	}

	@Test
	void isDirectDebit()
	{
		assertThat(PaySelectionTrxType.DIRECT_DEBIT.isDirectDebit()).isTrue();
		assertThat(PaySelectionTrxType.CREDIT_TRANSFER.isDirectDebit()).isFalse();
	}
}
