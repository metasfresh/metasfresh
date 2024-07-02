/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.bank.C_Bank_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_Product;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

public class C_BP_BankAccount_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

	private final C_BP_BankAccount_StepDefData bpBankAccountTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final CurrencyRepository currencyRepository;
	private final C_Bank_StepDefData bankTable;

	public C_BP_BankAccount_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final C_BP_BankAccount_StepDefData bpBankAccountTable,
			@NonNull final C_Bank_StepDefData bankTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.currencyRepository = currencyRepository;
		this.bpBankAccountTable = bpBankAccountTable;
		this.bankTable = bankTable;
	}

	@And("metasfresh contains C_BP_BankAccount")
	public void addC_BP_BankAccount(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			create_C_BP_BankAccount(dataTableRow);
		}
	}

	@And("load C_BP_BankAccount:")
	public void load_C_BP_BankAccount(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			loadBankAccount(dataTableRow);
		}
	}

	@And("update C_BP_BankAccount:")
	public void update_C_BP_BankAccount(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : rows)
		{
			updateBankAccount(dataTableRow);
		}
	}

	private void updateBankAccount(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_C_BP_BankAccount bankAccountRecord = bpBankAccountTable.get(identifier);

		final String isoCode = DataTableUtil.extractStringForColumnName(row, "OPT." + I_C_Currency.Table_Name + "." + I_C_Currency.COLUMNNAME_ISO_Code);
		if (Check.isNotBlank(isoCode))
		{
			final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCode));
			bankAccountRecord.setC_Currency_ID(currencyId.getRepoId());
		}

		final Boolean isEsrAccount = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_IsEsrAccount, null);
		if (isEsrAccount != null)
		{
			bankAccountRecord.setIsEsrAccount(isEsrAccount);
		}

		final String esrRenderedAccountNo = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_ESR_RenderedAccountNo);
		if (Check.isNotBlank(esrRenderedAccountNo))
		{
			bankAccountRecord.setESR_RenderedAccountNo(esrRenderedAccountNo);
		}

		final String iban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_IBAN);
		if (Check.isNotBlank(iban))
		{
			bankAccountRecord.setIBAN(iban);
		}

		final String bankIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bankIdentifier))
		{
			final I_C_Bank bankRecord = bankTable.get(bankIdentifier);

			bankAccountRecord.setC_Bank_ID(bankRecord.getC_Bank_ID());
		}

		saveRecord(bankAccountRecord);
	}

	private void loadBankAccount(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID);

		if (id != null)
		{
			final I_C_BP_BankAccount bankAccountRecord = InterfaceWrapperHelper.load(id, I_C_BP_BankAccount.class);

			bpBankAccountTable.putOrReplace(identifier, bankAccountRecord);
		}
	}

	private void create_C_BP_BankAccount(@NonNull final Map<String, String> row)
	{

		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer bPartnerId = bpartnerTable.getOptional(bPartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

		final String isoCode = DataTableUtil.extractStringForColumnName(row, I_C_Currency.Table_Name + "." + I_C_Currency.COLUMNNAME_ISO_Code);
		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCode));

		final I_C_BP_BankAccount bpBankAccount = CoalesceUtil.coalesceSuppliersNotNull(
				() -> resolveExitingBankAccount(row),
				() -> newInstance(I_C_BP_BankAccount.class));

		bpBankAccount.setC_BPartner_ID(bPartnerId);
		bpBankAccount.setC_Currency_ID(currencyId.getRepoId());

		final String accountNo = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_AccountNo);
		if (Check.isNotBlank(accountNo))
		{
			bpBankAccount.setAccountNo(accountNo);
		}

		final String bankIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bankIdentifier))
		{
			final I_C_Bank bankRecord = bankTable.get(bankIdentifier);

			bpBankAccount.setC_Bank_ID(bankRecord.getC_Bank_ID());
		}

		final String iban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_IBAN);
		if (Check.isNotBlank(iban))
		{
			bpBankAccount.setIBAN(iban);
		}

		InterfaceWrapperHelper.save(bpBankAccount);

		final String bankAccountIdentifier = DataTableUtil.extractStringForColumnName(row, TABLECOLUMN_IDENTIFIER);
		bpBankAccountTable.putOrReplace(bankAccountIdentifier, bpBankAccount);
	}

	@Nullable
	private I_C_BP_BankAccount resolveExitingBankAccount(@NonNull final Map<String, String> row)
	{
		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer bPartnerId = bpartnerTable.getOptional(bPartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerIdentifier));

		final String isoCode = DataTableUtil.extractStringForColumnName(row, I_C_Currency.Table_Name + "." + I_C_Currency.COLUMNNAME_ISO_Code);
		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCode));

		final IQueryBuilder<I_C_BP_BankAccount> queryBuilder = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_Currency_ID, currencyId.getRepoId());

		final String accountNo = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_AccountNo);
		if (Check.isNotBlank(accountNo))
		{
			queryBuilder.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_AccountNo, accountNo);
		}

		final String bankIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bankIdentifier))
		{
			final I_C_Bank bankRecord = bankTable.get(bankIdentifier);
			queryBuilder.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID, bankRecord.getC_Bank_ID());
		}

		final String iban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_IBAN);
		if (Check.isNotBlank(iban))
		{
			queryBuilder.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IBAN, iban);
		}

		return queryBuilder.create()
				.firstOnly(I_C_BP_BankAccount.class);
	}

	@And("validate C_BP_BankAccount:")
	public void validate_C_BP_BankAccount(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String bpBankAccountIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BP_BankAccount bpBankAccount = bpBankAccountTable.get(bpBankAccountIdentifier);

			final String bpIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartnerRecord = bpartnerTable.get(bpIdentifier);
			softly.assertThat(bpBankAccount.getC_BPartner_ID()).as("C_BPartner_ID").isEqualTo(bPartnerRecord.getC_BPartner_ID());

			final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_Description);
			if (Check.isNotBlank(description))
			{
				softly.assertThat(bpBankAccount.getDescription()).as("Description").isEqualTo(description);
			}

			final String iban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_IBAN);
			if (Check.isNotBlank(iban))
			{
				softly.assertThat(bpBankAccount.getIBAN()).as("IBAN").isEqualTo(iban);
			}

			final String qrIban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_QR_IBAN);
			if (Check.isNotBlank(qrIban))
			{
				softly.assertThat(bpBankAccount.getQR_IBAN()).as("QR_IBAN").isEqualTo(qrIban);
			}

			final String isoCurrencyCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.ISO_Code");
			if (Check.isNotBlank(isoCurrencyCode))
			{
				final CurrencyId currencyId = currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCurrencyCode)).getId();
				softly.assertThat(bpBankAccount.getC_Currency_ID()).as("C_Currency_ID").isEqualTo(currencyId.getRepoId());
			}

			final String name = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_Name);
			if (Check.isNotBlank(name))
			{
				softly.assertThat(bpBankAccount.getName()).as("Name").isEqualTo(name);
			}

			final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_IsActive);
			if (isActive != null)
			{
				softly.assertThat(bpBankAccount.isActive()).as("IsActive").isEqualTo(isActive);
			}

			final Boolean isDefault = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_C_BP_BankAccount.COLUMNNAME_IsDefault);
			if (isDefault != null)
			{
				softly.assertThat(bpBankAccount.isDefault()).as("isDefault").isEqualTo(isDefault);
			}
		}

		softly.assertAll();
	}
}
