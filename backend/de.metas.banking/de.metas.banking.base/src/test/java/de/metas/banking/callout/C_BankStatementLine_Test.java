package de.metas.banking.callout;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.model.BankStatementLineAmounts;
import de.metas.banking.service.impl.BankStatementBL;
import de.metas.currency.CurrencyRepository;
import de.metas.money.MoneyService;
import lombok.Builder;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ExtendWith(AdempiereTestWatcher.class)
public class C_BankStatementLine_Test
{
	private C_BankStatementLine callout;

	private BankRepository bankRepo;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final BankStatementBL bankStatementBL = new BankStatementBL(
				new BankAccountService(
						bankRepo = new BankRepository(),
						new CurrencyRepository()),
				new MoneyService(new CurrencyRepository()));
		callout = new C_BankStatementLine(bankStatementBL);
	}

	@Builder(builderMethodName = "bankStatementLine", builderClassName = "$BankStatementLineBuilder")
	private I_C_BankStatementLine createBankStatementLine(
			final boolean cashBank,
			final String stmtAmt,
			final String trxAmt,
			final String bankFeeAmt,
			final String chargeAmt,
			final String interestAmt)
	{
		final Bank bank = bankRepo.createBank(BankCreateRequest.builder()
				.bankName("MyBank")
				.routingNo("R")
				.cashBank(cashBank)
				.build());

		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_Bank_ID(bank.getBankId().getRepoId());
		bankAccount.setC_Currency_ID(111);
		saveRecord(bankAccount);

		final I_C_BankStatement bankStatement = newInstance(I_C_BankStatement.class);
		bankStatement.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
		saveRecord(bankStatement);

		final I_C_BankStatementLine bsl = newInstance(I_C_BankStatementLine.class);
		bsl.setC_BankStatement_ID(bankStatement.getC_BankStatement_ID());
		bsl.setStmtAmt(toBigDecimal(stmtAmt));
		bsl.setTrxAmt(toBigDecimal(trxAmt));
		bsl.setBankFeeAmt(toBigDecimal(bankFeeAmt));
		bsl.setChargeAmt(toBigDecimal(chargeAmt));
		bsl.setInterestAmt(toBigDecimal(interestAmt));

		return bsl;
	}

	@Nullable
	private static BigDecimal toBigDecimal(@Nullable final String value)
	{
		return value != null ? new BigDecimal(value) : null;
	}

	@Nested
	public class bankStatementLine
	{
		@Test
		public void onStmtAmtChanged()
		{
			final I_C_BankStatementLine bsl = bankStatementLine()
					.stmtAmt("100")
					.bankFeeAmt("10")
					.build();

			callout.onStmtAmtChanged(bsl);

			assertThat(BankStatementLineAmounts.of(bsl))
					.isEqualTo(BankStatementLineAmounts.builder()
							.stmtAmt(new BigDecimal("100"))
							.trxAmt(new BigDecimal("110"))
							.bankFeeAmt(new BigDecimal("10"))
							.build());
		}

		@Test
		public void onTrxAmtChanged()
		{
			final I_C_BankStatementLine bsl = bankStatementLine()
					.stmtAmt("100")
					.trxAmt("90")
					.build();

			callout.onTrxAmtChanged(bsl);

			assertThat(BankStatementLineAmounts.of(bsl))
					.isEqualTo(BankStatementLineAmounts.builder()
							.stmtAmt(new BigDecimal("100"))
							.trxAmt(new BigDecimal("90"))
							.bankFeeAmt(new BigDecimal("-10"))
							.build());
		}

		@Test
		public void onBankFeeAmtChanged()
		{
			final I_C_BankStatementLine bsl = bankStatementLine()
					.stmtAmt("100")
					.trxAmt("100")
					.bankFeeAmt("10")
					.build();

			callout.onBankFeeAmtChanged(bsl);

			assertThat(BankStatementLineAmounts.of(bsl))
					.isEqualTo(BankStatementLineAmounts.builder()
							.stmtAmt(new BigDecimal("100"))
							.trxAmt(new BigDecimal("110"))
							.bankFeeAmt(new BigDecimal("10"))
							.build());
		}

		@Test
		public void onChargeAmtChanged()
		{
			final I_C_BankStatementLine bsl = bankStatementLine()
					.stmtAmt("100")
					.trxAmt("100")
					.bankFeeAmt("10")
					.chargeAmt("9")
					.build();

			callout.onChargeAmtChanged(bsl);

			assertThat(BankStatementLineAmounts.of(bsl))
					.isEqualTo(BankStatementLineAmounts.builder()
							.stmtAmt(new BigDecimal("100"))
							.trxAmt(new BigDecimal("101"))
							.bankFeeAmt(new BigDecimal("10"))
							.chargeAmt(new BigDecimal("9"))
							.build());
		}

		@Test
		public void onInterestAmtChanged()
		{
			final I_C_BankStatementLine bsl = bankStatementLine()
					.stmtAmt("100")
					.trxAmt("100")
					.bankFeeAmt("10")
					.chargeAmt("9")
					.interestAmt("2")
					.build();

			callout.onInterestAmtChanged(bsl);

			assertThat(BankStatementLineAmounts.of(bsl))
					.isEqualTo(BankStatementLineAmounts.builder()
							.stmtAmt(new BigDecimal("100"))
							.trxAmt(new BigDecimal("99"))
							.bankFeeAmt(new BigDecimal("10"))
							.chargeAmt(new BigDecimal("9"))
							.interestAmt(new BigDecimal("2"))
							.build());
		}
	}

	@Nested
	public class cashJournalLine
	{
		@Test
		public void onStmtAmtChanged()
		{
			final I_C_BankStatementLine bsl = bankStatementLine()
					.stmtAmt("100")
					.build();

			callout.onStmtAmtChanged(bsl);

			assertThat(BankStatementLineAmounts.of(bsl))
					.isEqualTo(BankStatementLineAmounts.builder()
							.stmtAmt(new BigDecimal("100"))
							.trxAmt(new BigDecimal("100"))
							.build());
		}

		@Test
		public void onTrxAmtChanged()
		{
			final I_C_BankStatementLine bsl = bankStatementLine()
					.cashBank(true)
					.stmtAmt("100")
					.trxAmt("90")
					.build();

			callout.onTrxAmtChanged(bsl);

			assertThat(BankStatementLineAmounts.of(bsl))
					.isEqualTo(BankStatementLineAmounts.builder()
							.stmtAmt(new BigDecimal("100"))
							.trxAmt(new BigDecimal("100"))
							.build());
		}

		@Test
		public void onBankFeeAmtChanged()
		{
			final I_C_BankStatementLine bsl = bankStatementLine()
					.cashBank(true)
					.stmtAmt("100")
					.bankFeeAmt("10")
					.build();

			callout.onBankFeeAmtChanged(bsl);

			assertThat(BankStatementLineAmounts.of(bsl))
					.isEqualTo(BankStatementLineAmounts.builder()
							.stmtAmt(new BigDecimal("100"))
							.trxAmt(new BigDecimal("100"))
							.bankFeeAmt(new BigDecimal("0"))
							.build());
		}

	}

	/** Selecting an order on a bank statement line without invoice sets the line partner to the order's bill partner; an invoice on the line wins. */
	@Nested
	public class onC_Order_ID_Changed
	{
		private static final int ORDERING_PARTNER_ID = 1;
		private static final int BILL_PARTNER_ID = 2;

		private I_C_Order createOrder()
		{
			final I_C_Order order = newInstance(I_C_Order.class);
			order.setC_BPartner_ID(ORDERING_PARTNER_ID);
			order.setBill_BPartner_ID(BILL_PARTNER_ID);
			saveRecord(order);
			return order;
		}

		@Test
		public void lineWithoutPartner_gets_billPartnerOfOrder()
		{
			final I_C_BankStatementLine bsl = bankStatementLine().build();
			bsl.setC_Order_ID(createOrder().getC_Order_ID());

			callout.onC_Order_ID_Changed(bsl);

			assertThat(bsl.getC_BPartner_ID()).isEqualTo(BILL_PARTNER_ID);
		}

		@Test
		public void lineWithOrderingPartner_gets_billPartnerOfOrder()
		{
			final I_C_BankStatementLine bsl = bankStatementLine().build();
			bsl.setC_BPartner_ID(ORDERING_PARTNER_ID);
			bsl.setC_Order_ID(createOrder().getC_Order_ID());

			callout.onC_Order_ID_Changed(bsl);

			assertThat(bsl.getC_BPartner_ID()).isEqualTo(BILL_PARTNER_ID);
		}

		@Test
		public void lineWithInvoice_keepsItsPartner()
		{
			final I_C_BankStatementLine bsl = bankStatementLine().build();
			bsl.setC_Invoice_ID(3);
			bsl.setC_BPartner_ID(ORDERING_PARTNER_ID);
			bsl.setC_Order_ID(createOrder().getC_Order_ID());

			callout.onC_Order_ID_Changed(bsl);

			assertThat(bsl.getC_BPartner_ID()).isEqualTo(ORDERING_PARTNER_ID);
		}
	}
}
