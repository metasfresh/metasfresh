/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.payment.sepa;

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BP_BankAccount_DirectDebitMandate;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the SEPA direct debit mandates of a debtor bank account ({@code C_BP_BankAccount_DirectDebitMandate}):
 * creating them, validating their fields, and asserting that a business partner has none.
 * The DataTable columns of each step are documented on its method.
 * <pre>
 * And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
 *   | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring |
 *   | mandate                                | debtorAcct          | B2B         | MREF-001         | 2013-09-23  | Y           |
 * Then validate C_BP_BankAccount_DirectDebitMandate:
 *   | C_BP_BankAccount_DirectDebitMandate_ID | DateLastUsed |
 *   | mandate                                | 2026-10-20   |
 * And C_BPartner "copiedPartner" has no C_BP_BankAccount_DirectDebitMandate
 * </pre>
 *
 * @see C_BP_BankAccount_DirectDebitMandate_StepDefData
 */
@RequiredArgsConstructor
public class C_BP_BankAccount_DirectDebitMandate_StepDef
{
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final @NonNull C_BP_BankAccount_DirectDebitMandate_StepDefData mandateTable;
	private final @NonNull C_BP_BankAccount_StepDefData bankAccountTable;
	private final @NonNull C_BPartner_StepDefData bpartnerTable;
	private final @NonNull AD_Org_StepDefData orgTable;

	/**
	 * Creates SEPA direct debit mandates of bank accounts. The record is looked up by the natural key (MandateReference, AD_Org_ID),
	 * which is also a unique index of the table: a row that already exists with that key (e.g. of an earlier run on the same DB)
	 * is UPDATED and re-pointed to the given bank account, it is not an error. On such a re-used row, an absent
	 * <b>DateLastUsed</b> / <b>IsActive</b> column resets the field to the value of a new row (null / Y); other fields (e.g. MandateStatus) keep their old value.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BP_BankAccount_DirectDebitMandate_ID</b> (or <b>Identifier</b>) - (required) identifier under which the mandate is registered<br>
	 *   <b>C_BP_BankAccount_ID</b> - (required, identifier-ref) the debtor bank account; the partner is taken from it<br>
	 *   <b>MandateType</b> - (required) CORE or B2B<br>
	 *   <b>MandateReference</b> - (required) the mandate reference<br>
	 *   <b>MandateDate</b> - (required) signature date, <code>yyyy-MM-dd</code> or <code>yyyy-MM-dd HH:mm</code>; <code>-</code> = null<br>
	 *   <b>IsRecurring</b> - (required) Y/N<br>
	 *   <b>AD_Org_ID</b> - (optional) AD_Org identifier, or <code>0</code> for org *; absent or blank = the login org<br>
	 *   <b>DateLastUsed</b> - (optional) same formats; <code>-</code> or absent = null<br>
	 *   <b>IsActive</b> - (optional) Y/N; absent = Y
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_BP_BankAccount_DirectDebitMandate:
	 *   | C_BP_BankAccount_DirectDebitMandate_ID | C_BP_BankAccount_ID | MandateType | MandateReference | MandateDate | IsRecurring | DateLastUsed | AD_Org_ID |
	 *   | mandate                                | debtorAcct          | B2B         | MREF-001         | 2013-09-23  | Y           | 2025-09-04   | sellerOrg |
	 * </pre>
	 */
	@Given("metasfresh contains C_BP_BankAccount_DirectDebitMandate:")
	public void metasfresh_contains_C_BP_BankAccount_DirectDebitMandate(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_C_BP_BankAccount_DirectDebitMandate_ID)
				.forEach(this::createMandate);
	}

	private void createMandate(@NonNull final DataTableRow row)
	{
		final BPartnerBankAccountId bankAccountId = row.getAsIdentifier(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_C_BP_BankAccount_ID)
				.lookupNotNullIdIn(bankAccountTable);

		final String mandateReference = row.getAsString(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateReference);
		final OrgId orgId = row.getAsOptionalString(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_AD_Org_ID)
				.filter(orgStr -> !orgStr.trim().isEmpty()) // blank cell = same as absent column: login org
				.map(this::resolveOrgId)
				.orElseGet(Env::getOrgId);

		// upsert by the natural key (MandateReference, AD_Org_ID), so that a re-run on the same DB does not collide with the rows of the earlier run
		final I_C_BP_BankAccount_DirectDebitMandate mandate = queryBL.createQueryBuilder(I_C_BP_BankAccount_DirectDebitMandate.class)
				.addEqualsFilter(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateReference, mandateReference)
				.addEqualsFilter(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnly(I_C_BP_BankAccount_DirectDebitMandate.class);
		final I_C_BP_BankAccount_DirectDebitMandate mandateRecord = mandate != null ? mandate : newInstance(I_C_BP_BankAccount_DirectDebitMandate.class);
		mandateRecord.setAD_Org_ID(orgId.getRepoId());
		mandateRecord.setC_BP_BankAccount_ID(bankAccountId.getRepoId());
		mandateRecord.setC_BPartner_ID(bankAccountId.getBpartnerId().getRepoId());
		mandateRecord.setMandateType(row.getAsString(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateType));
		mandateRecord.setMandateReference(mandateReference);
		mandateRecord.setMandateDate(parseTimestampOrNull(row.getAsString(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateDate), I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateDate));
		mandateRecord.setIsRecurring(row.getAsBoolean(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_IsRecurring));
		// optional columns: an absent column resets a re-used row to the state of a new one
		mandateRecord.setDateLastUsed(row.getAsOptionalString(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_DateLastUsed)
				.map(str -> parseTimestampOrNull(str, I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_DateLastUsed))
				.orElse(null));
		mandateRecord.setIsActive(row.getAsOptionalBoolean(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_IsActive).orElseTrue());

		saveRecord(mandateRecord);

		row.getAsIdentifier().putOrReplace(mandateTable, mandateRecord);
	}

	/**
	 * Reloads the mandate registered under the identifier and asserts the given columns; columns that are not in the table are not checked.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BP_BankAccount_DirectDebitMandate_ID</b> (or <b>Identifier</b>) - (required) identifier of the mandate<br>
	 *   <b>DateLastUsed</b>, <b>MandateDate</b> - (optional) expected timestamp, <code>yyyy-MM-dd</code> or <code>yyyy-MM-dd HH:mm</code>; <code>-</code> = null<br>
	 *   <b>IsActive</b> - (optional) Y/N<br>
	 *   <b>MandateType</b> - (optional) CORE or B2B
	 * @cucumber.example
	 * <pre>
	 * Then validate C_BP_BankAccount_DirectDebitMandate:
	 *   | C_BP_BankAccount_DirectDebitMandate_ID | DateLastUsed |
	 *   | mandate                                | 2026-11-02   |
	 * </pre>
	 */
	@Then("validate C_BP_BankAccount_DirectDebitMandate:")
	public void validate_C_BP_BankAccount_DirectDebitMandate(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_C_BP_BankAccount_DirectDebitMandate_ID)
				.forEach(this::validateMandate);
	}

	private void validateMandate(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final I_C_BP_BankAccount_DirectDebitMandate mandate = load(
				identifier.lookupNotNullIn(mandateTable).getC_BP_BankAccount_DirectDebitMandate_ID(),
				I_C_BP_BankAccount_DirectDebitMandate.class);

		row.getAsOptionalString(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_DateLastUsed)
				.ifPresent(str -> assertThat(mandate.getDateLastUsed())
						.as(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_DateLastUsed)
						.isEqualTo(parseTimestampOrNull(str, I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_DateLastUsed)));
		row.getAsOptionalString(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateDate)
				.ifPresent(str -> assertThat(mandate.getMandateDate())
						.as(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateDate)
						.isEqualTo(parseTimestampOrNull(str, I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateDate)));
		row.getAsOptionalBoolean(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_IsActive)
				.ifPresent(isActive -> assertThat(mandate.isActive()).as(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_IsActive).isEqualTo(isActive));
		row.getAsOptionalString(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateType)
				.ifPresent(type -> assertThat(mandate.getMandateType()).as(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_MandateType).isEqualTo(type));
	}

	/**
	 * Asserts that the partner has no mandate row at all (active or not), e.g. a copied partner must not carry over a mandate.
	 *
	 * @param bpartnerIdentifier identifier of a registered C_BPartner
	 * @cucumber.example
	 * <pre>
	 * Then C_BPartner "copiedPartner" has no C_BP_BankAccount_DirectDebitMandate
	 * </pre>
	 */
	@Then("C_BPartner {string} has no C_BP_BankAccount_DirectDebitMandate")
	public void bpartner_has_no_C_BP_BankAccount_DirectDebitMandate(@NonNull final String bpartnerIdentifier)
	{
		final BPartnerId bpartnerId = StepDefDataIdentifier.ofString(bpartnerIdentifier).lookupNotNullIdIn(bpartnerTable);

		// all rows, active or not: a copied partner must not carry over any mandate
		final int count = queryBL.createQueryBuilder(I_C_BP_BankAccount_DirectDebitMandate.class)
				.addEqualsFilter(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.count();
		assertThat(count).as("C_BP_BankAccount_DirectDebitMandate count for C_BPartner " + bpartnerIdentifier).isZero();
	}

	/** {@code 0} means org {@code *}, otherwise an AD_Org identifier. */
	private OrgId resolveOrgId(@NonNull final String orgStr)
	{
		return "0".equals(orgStr.trim())
				? OrgId.ANY
				: StepDefDataIdentifier.ofString(orgStr).lookupNotNullIdIn(orgTable);
	}

	/** Accepts {@code yyyy-MM-dd} and {@code yyyy-MM-dd HH:mm}; {@code -} (null placeholder) means null. */
	@Nullable
	private static Timestamp parseTimestampOrNull(@Nullable final String valueStr, @NonNull final String columnName)
	{
		if (DataTableUtil.isNullPlaceholder(valueStr))
		{
			return null;
		}
		final String value = valueStr.trim();
		try
		{
			return value.length() > 10
					? Timestamp.valueOf(LocalDateTime.parse(value, DATE_TIME_FORMAT))
					: Timestamp.valueOf(LocalDate.parse(value).atStartOfDay());
		}
		catch (final Exception ex)
		{
			throw new IllegalArgumentException("Column " + columnName + " has invalid value '" + valueStr + "'; expected yyyy-MM-dd or yyyy-MM-dd HH:mm or -", ex);
		}
	}
}
