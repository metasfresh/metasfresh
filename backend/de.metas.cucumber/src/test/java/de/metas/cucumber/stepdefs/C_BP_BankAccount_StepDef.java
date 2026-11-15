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
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CleanWhitespaceQueryFilterModifier;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
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

	/**
	 * Creates (or re-uses, see below) bank accounts of existing business partners.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> - (required) identifier under which the account is registered<br>
	 *   <b>C_BPartner_ID</b> - (required, identifier-ref) the partner that owns the account<br>
	 *   <b>C_Currency_ID</b> - (required) ISO currency code, e.g. EUR<br>
	 *   <b>AccountNo</b> - (optional) account number<br>
	 *   <b>C_Bank_ID</b> - (optional, identifier-ref; also written <code>C_Bank_ID.Identifier</code>) the bank<br>
	 *   <b>IBAN</b> - (optional) IBAN<br>
	 *   <b>SEPA_CreditorIdentifier</b> - (optional) SEPA creditor identifier (needed for direct debit exports)<br>
	 *   <b>A_Name</b> - (optional) account holder name<br>
	 *   <b>A_City</b> - (optional) account holder city<br>
	 *   An existing active account is re-used if partner, currency and every given AccountNo / C_Bank_ID / IBAN match; otherwise a new account is created.
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_BP_BankAccount
	 *   | Identifier | C_BPartner_ID | C_Currency_ID | IBAN                   | C_Bank_ID  | A_Name              | A_City      |
	 *   | debtorAcct | debtor        | EUR           | DE89370400440532013000 | debtorBank | Account Holder Name | Holder City |
	 * </pre>
	 */
	@And("metasfresh contains C_BP_BankAccount")
	public void addOrUpdateBPartnerBankAccount(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> createOrUpdateBankAccount(row, false));
	}

	/**
	 * Like {@code metasfresh contains C_BP_BankAccount}, but the account is created for the partner linked to the login organization
	 * (e.g. the creditor account, or a POS till's cashbook; so no <b>C_BPartner_ID</b> column); the same optional columns apply.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> - (required) identifier under which the account is registered<br>
	 *   <b>C_Currency_ID</b> - (required) ISO currency code, e.g. EUR<br>
	 *   <b>AccountNo</b>, <b>C_Bank_ID</b>, <b>IBAN</b>, <b>SEPA_CreditorIdentifier</b>, <b>A_Name</b>, <b>A_City</b> - (optional) as above<br>
	 *   <b>IsCashBank</b> - (optional) when {@code Y} and no {@code C_Bank_ID} is given, links a freshly-created
	 *   {@code C_Bank} flagged {@code IsCashBank=Y} - the source of truth {@code MPayment#beforeSave} reads to
	 *   set a payment's {@code TenderType} to Cash rather than DirectDeposit (a credit-card tender type is kept)<br>
	 * @cucumber.depends StepDefData: C_BP_BankAccount_StepDefData, C_Bank_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains organization bank accounts
	 *   | Identifier | C_Currency_ID | IBAN                   | C_Bank_ID    | SEPA_CreditorIdentifier |
	 *   | orgAcct    | EUR           | DE91100000000123456789 | creditorBank | DE98ZZZ09999999999      |
	 * And metasfresh contains organization bank accounts
	 *   | Identifier | C_Currency_ID | IsCashBank |
	 *   | cashbook   | EUR           | Y          |
	 * </pre>
	 */
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

	@And("locate C_BP_BankAccount by IBAN:")
	public void locate_C_BP_BankAccount_by_IBAN(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final String iban = row.getAsString(I_C_BP_BankAccount.COLUMNNAME_IBAN);

			final I_C_BP_BankAccount bankAccountRecord = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
					.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IBAN, iban, CleanWhitespaceQueryFilterModifier.getInstance())
					.create()
					.firstOnlyOptional()
					.orElseThrow(() -> new AdempiereException("No C_BP_BankAccount found for IBAN=" + iban));

			bpBankAccountTable.putOrReplace(row.getAsIdentifier(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID), bankAccountRecord);
		});
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

	private void createOrUpdateBankAccount(@NonNull final DataTableRow row, final boolean isOrgBankAccount)
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

		row.getAsOptionalString(COLUMNNAME_AccountNo).ifPresent(bpBankAccount::setAccountNo);
		row.getAsOptionalIdentifier(I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID)
				.map(bankTable::getId)
				.ifPresent(bankId -> bpBankAccount.setC_Bank_ID(bankId.getRepoId()));
		row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_IBAN).ifPresent(bpBankAccount::setIBAN);
		row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_SEPA_CreditorIdentifier).ifPresent(bpBankAccount::setSEPA_CreditorIdentifier);
		row.getAsOptionalString(COLUMNNAME_A_Name).ifPresent(bpBankAccount::setA_Name);
		row.getAsOptionalString(COLUMNNAME_A_City).ifPresent(bpBankAccount::setA_City);

		// a "cash bank" (e.g. a POS till's own cashbook) is what makes MPayment#beforeSave set TenderType=Cash
		// on a payment booked against it; without a linked C_Bank flagged IsCashBank, TenderType becomes
		// DirectDeposit. Either way, only a credit-card tender type requested by the caller is kept. Auto-creates a minimal C_Bank when none is linked
		// yet, rather than requiring every cash-till scenario to set one up via the (unrelated) bank-statement-import
		// "metasfresh contains C_Bank:" step.
		if (row.getAsOptionalBoolean(I_C_Bank.COLUMNNAME_IsCashBank).orElseFalse() && bpBankAccount.getC_Bank_ID() <= 0)
		{
			final I_C_Bank cashBank = newInstance(I_C_Bank.class);
			cashBank.setName("Cash");
			cashBank.setRoutingNo("000000"); // mandatory column; a cash "bank" has no real routing number
			cashBank.setIsCashBank(true);
			saveRecord(cashBank);
			bpBankAccount.setC_Bank_ID(cashBank.getC_Bank_ID());
		}

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

	/**
	 * Asserts bank accounts registered by an earlier step. Only the columns present in the table (and not blank) are compared.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BP_BankAccount_ID</b> - (required, identifier-ref) the bank account<br>
	 *   <b>C_BPartner_ID</b> - (required, identifier-ref) the partner that owns the account<br>
	 *   <b>Description</b>, <b>IBAN</b>, <b>QR_IBAN</b>, <b>Name</b> - (optional) expected values<br>
	 *   <b>ISO_Code</b> - (optional) ISO code of the account currency, e.g. EUR<br>
	 *   <b>IsActive</b>, <b>IsDefault</b> - (optional) expected flags<br>
	 *   <b>A_Name</b>, <b>A_Street</b>, <b>A_Zip</b>, <b>A_City</b>, <b>A_Country</b> - (optional) expected account holder data; the null placeholder = no value
	 * @cucumber.example
	 * <pre>
	 * And validate C_BP_BankAccount:
	 *   | C_BP_BankAccount_ID | C_BPartner_ID | IBAN                   | ISO_Code | IsActive | A_Name              |
	 *   | debtorAcct          | debtor        | DE89370400440532013000 | EUR      | true     | Account Holder Name |
	 * </pre>
	 */
	@And("validate C_BP_BankAccount:")
	public void validate_C_BP_BankAccount(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_BP_BankAccount bpBankAccount = bpBankAccountTable.get(row.getAsIdentifier(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID));

			final I_C_BPartner bpartner = bpartnerTable.get(row.getAsIdentifier(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID));
			softly.assertThat(bpBankAccount.getC_BPartner_ID()).as("C_BPartner_ID").isEqualTo(bpartner.getC_BPartner_ID());

			row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_Description)
					.ifPresent(description -> softly.assertThat(bpBankAccount.getDescription()).as("Description").isEqualTo(description));
			row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_IBAN)
					.ifPresent(iban -> softly.assertThat(bpBankAccount.getIBAN()).as("IBAN").isEqualTo(iban));
			row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_QR_IBAN)
					.ifPresent(qrIban -> softly.assertThat(bpBankAccount.getQR_IBAN()).as("QR_IBAN").isEqualTo(qrIban));
			row.getAsOptionalString("ISO_Code")
					.map(isoCode -> currencyBL.getByCurrencyCode(CurrencyCode.ofThreeLetterCode(isoCode)).getId())
					.ifPresent(currencyId -> softly.assertThat(bpBankAccount.getC_Currency_ID()).as("C_Currency_ID").isEqualTo(currencyId.getRepoId()));
			row.getAsOptionalString(I_C_BP_BankAccount.COLUMNNAME_Name)
					.ifPresent(name -> softly.assertThat(bpBankAccount.getName()).as("Name").isEqualTo(name));

			row.getAsOptionalBoolean(I_C_BP_BankAccount.COLUMNNAME_IsActive)
					.ifPresent(isActive -> softly.assertThat(bpBankAccount.isActive()).as("IsActive").isEqualTo(isActive));
			row.getAsOptionalBoolean(I_C_BP_BankAccount.COLUMNNAME_IsDefault)
					.ifPresent(isDefault -> softly.assertThat(bpBankAccount.isDefault()).as("IsDefault").isEqualTo(isDefault));

			row.getAsOptionalString(COLUMNNAME_A_Name)
					.ifPresent(accountName -> softly.assertThat(bpBankAccount.getA_Name()).as(COLUMNNAME_A_Name).isEqualTo(DataTableUtil.nullToken2Null(accountName)));
			row.getAsOptionalString(COLUMNNAME_A_Street)
					.ifPresent(accountStreet -> softly.assertThat(bpBankAccount.getA_Street()).as(COLUMNNAME_A_Street).isEqualTo(DataTableUtil.nullToken2Null(accountStreet)));
			row.getAsOptionalString(COLUMNNAME_A_Zip)
					.ifPresent(accountZip -> softly.assertThat(bpBankAccount.getA_Zip()).as(COLUMNNAME_A_Zip).isEqualTo(DataTableUtil.nullToken2Null(accountZip)));
			row.getAsOptionalString(COLUMNNAME_A_City)
					.ifPresent(accountCity -> softly.assertThat(bpBankAccount.getA_City()).as(COLUMNNAME_A_City).isEqualTo(DataTableUtil.nullToken2Null(accountCity)));
			row.getAsOptionalString(COLUMNNAME_A_Country)
					.ifPresent(accountCountry -> softly.assertThat(bpBankAccount.getA_Country()).as(COLUMNNAME_A_Country).isEqualTo(DataTableUtil.nullToken2Null(accountCountry)));
		});

		softly.assertAll();
	}
}
