package de.metas.acct.open_items;

import de.metas.acct.AccountConceptualName;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.invoice.InvoiceId;
import de.metas.payment.PaymentId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FAOpenItemKeyTest
{
	@Nested
	class getAsString_then_parse
	{
		void test(final FAOpenItemKey openItemKey, final String expectedString)
		{
			final String string = openItemKey.getAsString();
			assertThat(string).isEqualTo(expectedString);
			assertThat(openItemKey.getAsString()).isSameAs(string); // make sure getAsString is computed once!

			final FAOpenItemKey openItemKey2 = FAOpenItemKey.parse(string);
			assertThat(openItemKey2).usingRecursiveComparison().isEqualTo(openItemKey);
			assertThat(openItemKey2).isEqualTo(openItemKey);
			assertThat(openItemKey2.getAsString()).isSameAs(string); // make sure the string used as parse param is used
		}

		@Test
		void invoice()
		{
			test(FAOpenItemKey.invoice(InvoiceId.ofRepoId(1234567), AccountConceptualName.ofString("V_Liability_Acct")),
					"V_Liability_Acct#C_Invoice#1234567");
		}

		@Test
		void payment()
		{
			test(FAOpenItemKey.payment(PaymentId.ofRepoId(1234567), AccountConceptualName.ofString("B_PaymentSelect_Acct")),
					"B_PaymentSelect_Acct#C_Payment#1234567");
		}

		@Test
		void bankStatement()
		{
			final BankStatementId bsId = BankStatementId.ofRepoId(1000001);
			final BankStatementLineId bslId = BankStatementLineId.ofRepoId(1000002);
			final BankStatementLineRefId bslRefId = BankStatementLineRefId.ofRepoId(1000003);

			test(FAOpenItemKey.bankStatementLine(bsId, bslId, null, AccountConceptualName.ofString("B_InTransit_Acct")),
					"B_InTransit_Acct#C_BankStatement#1000001#1000002");
			test(FAOpenItemKey.bankStatementLine(bsId, bslId, bslRefId, AccountConceptualName.ofString("B_InTransit_Acct")),
					"B_InTransit_Acct#C_BankStatement#1000001#1000002#1000003");
		}

		@Test
		void missing_AccountConceptualName()
		{
			test(FAOpenItemKey.ofTableRecordLineAndSubLineId(null, "MyTableName", 123, -1, -1),
					"-#MyTableName#123");
		}

		@Test
		void generic()
		{
			test(FAOpenItemKey.ofTableRecordLineAndSubLineId(AccountConceptualName.ofString("Account"), "MyTableName", 1, -1, -1),
					"Account#MyTableName#1");
			test(FAOpenItemKey.ofTableRecordLineAndSubLineId(AccountConceptualName.ofString("Account"), "MyTableName", 1, 2, -1),
					"Account#MyTableName#1#2");
			test(FAOpenItemKey.ofTableRecordLineAndSubLineId(AccountConceptualName.ofString("Account"), "MyTableName", 1, 2, 3),
					"Account#MyTableName#1#2#3");
		}
	}

	@Test
	void getInvoiceId()
	{
		assertThat(FAOpenItemKey.invoice(InvoiceId.ofRepoId(1), AccountConceptualName.ofString("Acct")).getInvoiceId())
				.contains(InvoiceId.ofRepoId(1));
		assertThat(FAOpenItemKey.payment(PaymentId.ofRepoId(1), AccountConceptualName.ofString("Acct")).getInvoiceId())
				.isEmpty();
	}

	@Test
	void getPaymentId()
	{
		assertThat(FAOpenItemKey.payment(PaymentId.ofRepoId(1), AccountConceptualName.ofString("Acct")).getPaymentId())
				.contains(PaymentId.ofRepoId(1));
		assertThat(FAOpenItemKey.invoice(InvoiceId.ofRepoId(1), AccountConceptualName.ofString("Acct")).getPaymentId())
				.isEmpty();
	}

	@Nested
	class getBankStatementId
	{
		final BankStatementId bsId = BankStatementId.ofRepoId(1000001);
		final BankStatementLineId bslId = BankStatementLineId.ofRepoId(1000002);
		final BankStatementLineRefId bslRefId = BankStatementLineRefId.ofRepoId(1000003);
		final AccountConceptualName acct = AccountConceptualName.ofString("B_InTransit_Acct");

		@Test
		void bankStatementLine()
		{
			final FAOpenItemKey openItemKey = FAOpenItemKey.bankStatementLine(bsId, bslId, null, acct);
			assertThat(openItemKey.getBankStatementId()).contains(bsId);
			assertThat(openItemKey.getBankStatementLineId()).contains(bslId);
			assertThat(openItemKey.getBankStatementLineRefId()).isEmpty();
		}

		@Test
		void bankStatementLineRef()
		{
			final FAOpenItemKey openItemKey = FAOpenItemKey.bankStatementLine(bsId, bslId, bslRefId, acct);
			assertThat(openItemKey.getBankStatementId()).contains(bsId);
			assertThat(openItemKey.getBankStatementLineId()).contains(bslId);
			assertThat(openItemKey.getBankStatementLineRefId()).contains(bslRefId);
		}

		@Test
		void payment()
		{
			final FAOpenItemKey openItemKey = FAOpenItemKey.payment(PaymentId.ofRepoId(1), acct);
			assertThat(openItemKey.getBankStatementId()).isEmpty();
			assertThat(openItemKey.getBankStatementLineId()).isEmpty();
			assertThat(openItemKey.getBankStatementLineRefId()).isEmpty();
		}
	}

}