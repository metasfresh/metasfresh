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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.cucumber.stepdefs.bank.C_Bank_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_A_City;
import static org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_A_Country;
import static org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_A_Name;
import static org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_A_Street;
import static org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_A_Zip;
import static org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_AccountNo;
import static org.compiere.model.I_C_Invoice.COLUMNNAME_C_BPartner_ID;

public class C_BP_BankAccount_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);

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
	public void addOrUpdateBPartnerBankAccount(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> createOrUpdateBankAccount(row, false));
	}

	@And("metasfresh contains organization bank accounts")
	public void addOrUpdateOrgBankAccount(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> createOrUpdateBankAccount(row, true));
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

	private void createOrUpdateBankAccount(@NonNull final DataTableRow row, boolean isOrgBankAccount)
	{
		final BPartnerId bpartnerId;
		if (isOrgBankAccount)
		{
			final OrgId orgId = Env.getOrgId();
			bpartnerId = bpartnerOrgBL.retrieveLinkedBPartnerId(orgId).orElseThrow(() -> new AdempiereException("No linked BPartner found for " + orgId));
		}
		else
		{
			bpartnerId = row.getAsIdentifier(COLUMNNAME_C_BPartner_ID).lookupNotNullIdIn(bpartnerTable);
		}

		final CurrencyId currencyId = extractCurrencyId(row);

		final I_C_BP_BankAccount bpBankAccount = retrieveExitingBankAccount(row, bpartnerId)
				.orElseGet(() -> newInstance(I_C_BP_BankAccount.class));

		bpBankAccount.setC_BPartner_ID(bpartnerId.getRepoId());
		bpBankAccount.setC_Currency_ID(currencyId.getRepoId());

		row.getAsOptionalString(COLUMNNAME_AccountNo)
				.map(StringUtils::trimBlankToNull)
				.ifPresent(bpBankAccount::setAccountNo);
		row.getAsOptionalIdentifier(I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID)
				.map(bankTable::getId)
				.ifPresent(bankId -> bpBankAccount.setC_Bank_ID(bankId.getRepoId()));
		row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_IBAN)
				.map(StringUtils::trimBlankToNull)
				.ifPresent(bpBankAccount::setIBAN);

		InterfaceWrapperHelper.save(bpBankAccount);

		final String bankAccountIdentifier = DataTableUtil.extractStringForColumnName(row, TABLECOLUMN_IDENTIFIER);
		bpBankAccountTable.putOrReplace(bankAccountIdentifier, bpBankAccount);
	}

	private CurrencyId extractCurrencyId(final @NonNull DataTableRow row)
	{
		return currencyRepository.getCurrencyIdByCurrencyCode(row.getAsCurrencyCode());
	}

	private Optional<I_C_BP_BankAccount> retrieveExitingBankAccount(@NonNull final DataTableRow row, @NonNull final BPartnerId bpartnerId)
	{
		final CurrencyId currencyId = extractCurrencyId(row);

		final IQueryBuilder<I_C_BP_BankAccount> queryBuilder = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_Currency_ID, currencyId);

		row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_AccountNo)
				.ifPresent(accountNo -> queryBuilder.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_AccountNo, accountNo));

		row.getAsOptionalIdentifier(I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID)
				.ifPresent(bankIdentifier -> queryBuilder.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID, bankTable.getId(bankIdentifier)));

		row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_IBAN)
				.ifPresent(iban -> queryBuilder.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IBAN, iban));

		return queryBuilder.create()
				.firstOnlyOptional(I_C_BP_BankAccount.class);
	}

	@And("validate C_BP_BankAccount:")
	public void validate_C_BP_BankAccount(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String bpBankAccountIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID);
			final I_C_BP_BankAccount bpBankAccount = bpBankAccountTable.get(bpBankAccountIdentifier);

			final String bpIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID);
			final I_C_BPartner bPartnerRecord = bpartnerTable.get(bpIdentifier);
			softly.assertThat(bpBankAccount.getC_BPartner_ID()).as("C_BPartner_ID").isEqualTo(bPartnerRecord.getC_BPartner_ID());

			final String description = DataTableUtil.extractStringOrNullForColumnName(row, I_C_BP_BankAccount.COLUMNNAME_Description);
			if (Check.isNotBlank(description))
			{
				softly.assertThat(bpBankAccount.getDescription()).as("Description").isEqualTo(description);
			}

			final String iban = DataTableUtil.extractStringOrNullForColumnName(row, I_C_BP_BankAccount.COLUMNNAME_IBAN);
			if (Check.isNotBlank(iban))
			{
				softly.assertThat(bpBankAccount.getIBAN()).as("IBAN").isEqualTo(iban);
			}

			final String qrIban = DataTableUtil.extractStringOrNullForColumnName(row, I_C_BP_BankAccount.COLUMNNAME_QR_IBAN);
			if (Check.isNotBlank(qrIban))
			{
				softly.assertThat(bpBankAccount.getQR_IBAN()).as("QR_IBAN").isEqualTo(qrIban);
			}

			final String isoCurrencyCode = DataTableUtil.extractStringOrNullForColumnName(row, "ISO_Code");
			if (Check.isNotBlank(isoCurrencyCode))
			{
				final CurrencyId currencyId = currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCurrencyCode)).getId();
				softly.assertThat(bpBankAccount.getC_Currency_ID()).as("C_Currency_ID").isEqualTo(currencyId.getRepoId());
			}

			final String name = DataTableUtil.extractStringOrNullForColumnName(row, I_C_BP_BankAccount.COLUMNNAME_Name);
			if (Check.isNotBlank(name))
			{
				softly.assertThat(bpBankAccount.getName()).as("Name").isEqualTo(name);
			}

			final Boolean isActive = DataTableUtil.extractBooleanForColumnNameOrNull(row, I_C_BP_BankAccount.COLUMNNAME_IsActive);
			if (isActive != null)
			{
				softly.assertThat(bpBankAccount.isActive()).as("IsActive").isEqualTo(isActive);
			}

			final Boolean isDefault = DataTableUtil.extractBooleanForColumnNameOrNull(row, I_C_BP_BankAccount.COLUMNNAME_IsDefault);
			if (isDefault != null)
			{
				softly.assertThat(bpBankAccount.isDefault()).as("isDefault").isEqualTo(isDefault);
			}

			final String accountName = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_A_Name);
			if (Check.isNotBlank(accountName))
			{
				softly.assertThat(bpBankAccount.getA_Name()).as(COLUMNNAME_A_Name).isEqualTo(DataTableUtil.nullToken2Null(accountName));
			}

			final String accountStreet = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_A_Street);
			if (Check.isNotBlank(accountStreet))
			{
				softly.assertThat(bpBankAccount.getA_Street()).as(COLUMNNAME_A_Street).isEqualTo(DataTableUtil.nullToken2Null(accountStreet));
			}

			final String accountZip = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_A_Zip);
			if (Check.isNotBlank(accountZip))
			{
				softly.assertThat(bpBankAccount.getA_Zip()).as(COLUMNNAME_A_Zip).isEqualTo(DataTableUtil.nullToken2Null(accountZip));
			}

			final String accountCity = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_A_City);
			if (Check.isNotBlank(accountCity))
			{
				softly.assertThat(bpBankAccount.getA_City()).as(COLUMNNAME_A_City).isEqualTo(DataTableUtil.nullToken2Null(accountCity));
			}

			final String accountCountry = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_A_Country);
			if (Check.isNotBlank(accountCountry))
			{
				softly.assertThat(bpBankAccount.getA_Country()).as(COLUMNNAME_A_Country).isEqualTo(DataTableUtil.nullToken2Null(accountCountry));
			}
		}

		softly.assertAll();
	}
}
