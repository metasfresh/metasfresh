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

package de.metas.banking.impexp;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctions;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.impexp.processing.ImportGroup;
import de.metas.impexp.processing.ImportProcessResult;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_I_BankStatement;
import org.compiere.model.X_I_BankStatement;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
public class BankStatementImportProcessTest
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	private CurrencyId euroCurrencyId;
	private BankAccountId euroOrgBankAccountId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		setupDBFunctionsRepository();
		setupImportTableDescriptorRepository();

		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		euroOrgBankAccountId = createOrgBankAccount(euroCurrencyId);
	}

	private void setupDBFunctionsRepository()
	{
		SpringContextHolder.registerJUnitBean(DBFunctionsRepository.class, new DBFunctionsRepository()
		{
			@Override
			public DBFunctions retrieveByTableName(@NonNull final String tableName)
			{
				return DBFunctions.builder().tableName(tableName).build();
			}
		});
	}

	private void setupImportTableDescriptorRepository()
	{
		final ImportTableDescriptor importTableDescriptor = ImportTableDescriptor.builder()
				.tableName(I_I_BankStatement.Table_Name)
				.keyColumnName(I_I_BankStatement.COLUMNNAME_I_BankStatement_ID)
				.build();

		SpringContextHolder.registerJUnitBean(ImportTableDescriptorRepository.class, new ImportTableDescriptorRepository()
		{
			public ImportTableDescriptor getByTableId(AdTableId adTableId)
			{
				return importTableDescriptor;
			}

			@Override
			public ImportTableDescriptor getByTableName(@NonNull String tableName)
			{
				return importTableDescriptor;
			}
		});
	}

	private BankAccountId createOrgBankAccount(final CurrencyId currencyId)
	{
		final I_C_BPartner metasfreshBPartner = BusinessTestHelper.createBPartner("metasfresh");
		final I_C_BP_BankAccount metasfreshBankAccount = BusinessTestHelper.createBpBankAccount(
				BPartnerId.ofRepoId(metasfreshBPartner.getC_BPartner_ID()),
				currencyId,
				"IBAN");
		return BankAccountId.ofRepoId(metasfreshBankAccount.getC_BP_BankAccount_ID());
	}

	@Builder(builderMethodName = "record", builderClassName = "RecordBuilder")
	private I_I_BankStatement createRecordToImport(
			@Nullable final BankStatementId bankStatementId,
			@Nullable final CurrencyId currencyId,
			@Nullable final String trxAmt,
			@Nullable final String statementLineDate,
			@Nullable final String valutaDate,
			@Nullable final String lineDescription,
			@Nullable final String amtFormat,
			@Nullable final String debitOrCreditIndicator,
			@Nullable final String debitOrCreditAmt,
			@Nullable final String debitStmtAmt,
			@Nullable final String creditStmtAmt)
	{
		final I_I_BankStatement record = newInstance(I_I_BankStatement.class);
		record.setC_BankStatement_ID(bankStatementId != null ? bankStatementId.getRepoId() : -1);

		record.setC_Currency_ID(CurrencyId.toRepoId(currencyId));
		record.setStmtAmt(amtOrNull(trxAmt));
		record.setTrxAmt(amtOrNull(trxAmt));
		record.setLineDescription(lineDescription);
		record.setAmtFormat(amtFormat);
		record.setDebitOrCreditIndicator(debitOrCreditIndicator);
		record.setDebitOrCreditAmt(amtOrNull(debitOrCreditAmt));
		record.setDebitStmtAmt(amtOrNull(debitStmtAmt));
		record.setCreditStmtAmt(amtOrNull(creditStmtAmt));

		if (statementLineDate != null)
		{
			record.setStatementLineDate(TimeUtil.asTimestamp(LocalDate.parse(statementLineDate)));
		}

		if (valutaDate != null)
		{
			record.setValutaDate(TimeUtil.asTimestamp(LocalDate.parse(valutaDate)));
		}

		saveRecord(record);
		return record;
	}

	@SuppressWarnings("ConstantConditions")
	@Nullable
	BigDecimal amtOrNull(@Nullable final String amt)
	{
		if (Check.isNotBlank(amt))
		{
			return new BigDecimal(amt);
		}
		return null;
	}

	private ImportProcessResult importRecords(final I_I_BankStatement... importRecords)
	{
		final ImmutableList<I_I_BankStatement> importRecordsList = ImmutableList.copyOf(importRecords);
		final BankStatementImportProcess importProcess = new BankStatementImportProcess()
		{
			@Override
			protected void updateAndValidateImportRecords()
			{
				System.out.println("updateAndValidateImportRecords() does nothing when running in unit test mode"
										   + "\n\t call trace: " + Trace.toOneLineStackTraceString());
			}

			@Override
			protected void resetStandardColumns()
			{
				System.out.println("resetStandardColumns() does nothing when running in unit test mode"
										   + "\n\t call trace: " + Trace.toOneLineStackTraceString());
			}

			@Override
			protected Iterator<I_I_BankStatement> retrieveRecordsToImport()
			{
				return importRecordsList.iterator();
			}

			@Override
			protected void markAsError(
					@NonNull final ImportGroup<RecordIdGroupKey, I_I_BankStatement> importGroup,
					@NonNull final Throwable exception)
			{
				throw AdempiereException.wrapIfNeeded(exception)
						.setParameter("importGroup", importGroup)
						.appendParametersToMessage();
			}
		};

		importProcess.setCtx(Env.getCtx());
		return importProcess.run();
	}

	@Test
	void importToExistingBankStatement()
	{
		final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(BankStatementCreateRequest.builder()
																							 .orgId(OrgId.ANY)
																							 .orgBankAccountId(euroOrgBankAccountId)
																							 .name("Bank Statement 1")
																							 .statementDate(LocalDate.parse("2020-03-22"))
																							 .build());

		final RecordBuilder recordBuilder = record()
				.bankStatementId(bankStatementId)
				.statementLineDate("2020-03-22")
				.currencyId(euroCurrencyId);

		importRecords(
				recordBuilder.trxAmt("100").lineDescription("description for line 1").build(),
				recordBuilder.trxAmt("33.33").lineDescription("description for line 2").build());

		final List<I_C_BankStatementLine> lines = bankStatementDAO.getLinesByBankStatementId(bankStatementId);
		assertThat(lines).hasSize(2);

		//
		// Line 1
		{
			assertThat(lines.get(0).getC_Currency_ID()).isEqualByComparingTo(euroCurrencyId.getRepoId());
			assertThat(TimeUtil.asLocalDate(lines.get(0).getStatementLineDate())).isEqualTo("2020-03-22");
			assertThat(TimeUtil.asLocalDate(lines.get(0).getValutaDate())).isEqualTo("2020-03-22");
			assertThat(TimeUtil.asLocalDate(lines.get(0).getDateAcct())).isEqualTo("2020-03-22");
			//
			assertThat(lines.get(0).getStmtAmt()).isEqualByComparingTo("100");
			assertThat(lines.get(0).getTrxAmt()).isEqualByComparingTo("100");
			assertThat(lines.get(0).getDescription()).isEqualTo("description for line 1");
		}

		//
		// Line 2
		{
			assertThat(lines.get(1).getC_Currency_ID()).isEqualByComparingTo(euroCurrencyId.getRepoId());
			assertThat(TimeUtil.asLocalDate(lines.get(1).getStatementLineDate())).isEqualTo("2020-03-22");
			assertThat(TimeUtil.asLocalDate(lines.get(1).getValutaDate())).isEqualTo("2020-03-22");
			assertThat(TimeUtil.asLocalDate(lines.get(1).getDateAcct())).isEqualTo("2020-03-22");
			//
			assertThat(lines.get(1).getStmtAmt()).isEqualByComparingTo("33.33");
			assertThat(lines.get(1).getTrxAmt()).isEqualByComparingTo("33.33");
			assertThat(lines.get(1).getDescription()).isEqualTo("description for line 2");
		}
	}

	@Nested
	class GetAmtFormat
	{
		private BankStatementId bankStatementId;

		@BeforeEach
		void beforeEach()
		{
			bankStatementId = bankStatementDAO.createBankStatement(
					BankStatementCreateRequest.builder()
							.orgId(OrgId.ANY)
							.orgBankAccountId(euroOrgBankAccountId)
							.name("Bank Statement 1")
							.statementDate(LocalDate.parse("2020-03-22"))
							.build()
			);
		}

		@Test
		void formatExists()
		{
			final I_I_BankStatement statement = mkRecord().amtFormat("dummy").build();

			assertThat(BankStatementImportProcess.getAmtFormat(statement)).isEqualTo("dummy");
		}

		@Test
		void invalidFormat()
		{
			final I_I_BankStatement statement = mkRecord().build();

			assertThatExceptionOfType(AdempiereException.class)
					.isThrownBy(() -> BankStatementImportProcess.getAmtFormat(statement))
					.withMessageContaining("Invalid")
					.withMessageContaining("format");
		}

		@Test
		void formatNotExists_AmountPlusIndicator()
		{
			final I_I_BankStatement statement = mkRecord().debitOrCreditIndicator("H").debitOrCreditAmt("123.456").build();

			assertThat(BankStatementImportProcess.getAmtFormat(statement)).isEqualTo(X_I_BankStatement.AMTFORMAT_AmountPlusIndicator);
		}

		@Test
		void formatNotExists_DebitPlusCredit_Debit()
		{
			final I_I_BankStatement statement = mkRecord().debitStmtAmt("123.456").build();

			assertThat(BankStatementImportProcess.getAmtFormat(statement)).isEqualTo(X_I_BankStatement.AMTFORMAT_DebitPlusCredit);
		}

		@Test
		void formatNotExists_DebitPlusCredit_Credit()
		{
			final I_I_BankStatement statement = mkRecord().creditStmtAmt("123.456").build();

			assertThat(BankStatementImportProcess.getAmtFormat(statement)).isEqualTo(X_I_BankStatement.AMTFORMAT_DebitPlusCredit);
		}

		@Test
		void formatNotExists_Straight()
		{
			final I_I_BankStatement statement = mkRecord().trxAmt("123.456").build();

			assertThat(BankStatementImportProcess.getAmtFormat(statement)).isEqualTo(X_I_BankStatement.AMTFORMAT_Straight);
		}

		@Test
		void integrationAllAmtFormats()
		{
			importRecords(
					mkRecord().debitOrCreditIndicator("H").debitOrCreditAmt("123.456").lineDescription("1. A+I").build(),
					mkRecord().debitStmtAmt("223.456").lineDescription("2. D+C").build(),
					mkRecord().creditStmtAmt("323.456").lineDescription("3. D+C").build(),
					mkRecord().trxAmt("423.456").lineDescription("4. S").build()
			);

			final List<I_C_BankStatementLine> lines = bankStatementDAO.getLinesByBankStatementId(bankStatementId);
			assertThat(lines)
					.extracting(l -> tuple(l.getStmtAmt().toString(), l.getTrxAmt().toString(), l.getDescription()))
					.containsExactlyInAnyOrder(
							tuple("-123.456", "-123.456", "1. A+I"),
							tuple("-223.456", "-223.456", "2. D+C"),
							tuple("323.456", "323.456", "3. D+C"),
							tuple("423.456", "423.456", "4. S")
					);
		}

		private RecordBuilder mkRecord()
		{
			return record().bankStatementId(bankStatementId).statementLineDate("2020-03-22").currencyId(euroCurrencyId);
		}
	}
}
