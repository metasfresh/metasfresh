package de.metas.acct.open_items;

import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FAOpenItemKeyTest
{
	@Test
	void invoice()
	{
		assertThat(FAOpenItemKey.invoice(InvoiceId.ofRepoId(1234567)))
				.isEqualTo(FAOpenItemKey.ofString("C_Invoice#1234567"));
	}

	@Test
	void payment()
	{
		assertThat(FAOpenItemKey.payment(PaymentId.ofRepoId(1234567)))
				.isEqualTo(FAOpenItemKey.ofString("C_Payment#1234567"));
	}

	@Test
	void bankStatementLine()
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoId(1000001);
		final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(1000002);
		assertThat(FAOpenItemKey.bankStatementLine(bankStatementId, bankStatementLineId, null))
				.isEqualTo(FAOpenItemKey.ofString("C_BankStatement#1000001#1000002"));

		final BankStatementLineRefId bankStatementLineRefId = BankStatementLineRefId.ofRepoId(1000003);
		assertThat(FAOpenItemKey.bankStatementLine(bankStatementId, bankStatementLineId, bankStatementLineRefId))
				.isEqualTo(FAOpenItemKey.ofString("C_BankStatement#1000001#1000002#1000003"));

	}

}