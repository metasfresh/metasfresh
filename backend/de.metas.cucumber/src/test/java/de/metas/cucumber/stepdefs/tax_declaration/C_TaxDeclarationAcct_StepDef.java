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

package de.metas.cucumber.stepdefs.tax_declaration;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.tax.C_VAT_Code_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class C_TaxDeclarationAcct_StepDef
{
	@NonNull private final C_TaxDeclaration_StepDefData taxDeclarationTable;
	@NonNull private final C_VAT_Code_StepDefData vatCodeTable;
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	/**
	 * Assert that {@link I_C_TaxDeclarationAcct} contains rows for the given documents with correct fields.
	 * Other rows in the snapshot (for documents not listed in the DataTable) are ignored.
	 *
	 * <p><b>Required DataTable columns:</b>
	 * <ul>
	 *   <li>{@code Identifier} — document identifier; resolved via
	 *       {@link IdentifiersResolver#getTableRecordReference(StepDefDataIdentifier)} to get
	 *       {@code AD_Table_ID + Record_ID}</li>
	 *   <li>{@code C_VAT_Code_ID} — identifier of a {@link de.metas.acct.vatcode.VATCode}
	 *       in {@link C_VAT_Code_StepDefData}</li>
	 *   <li>{@code AmountType} — expected AmountType value ({@code T} or {@code N})</li>
	 *   <li>{@code Amount} — expected Amount (AmtAcctDr - AmtAcctCr), e.g. {@code -190}</li>
	 * </ul>
	 *
	 * <p><b>Example:</b>
	 * <pre>{@code
	 * Then the C_TaxDeclarationAcct for declaration 'td1' contains entries for documents:
	 *   | Identifier | C_VAT_Code_ID | AmountType | Amount |
	 *   | invoice    | sales19       | T          | -190   |
	 * }</pre>
	 */
	@Then("the C_TaxDeclarationAcct for declaration {string} contains entries for documents:")
	public void c_tax_declaration_acct_contains_entries(
			@NonNull final String declarationIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_C_TaxDeclaration decl = taxDeclarationTable.get(StepDefDataIdentifier.ofString(declarationIdentifier));
		final TaxDeclarationId declId = TaxDeclarationId.ofRepoId(decl.getC_TaxDeclaration_ID());

		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach(row -> assertAcctRow(row, declId, softly));

		softly.assertAll();
	}

	private void assertAcctRow(
			@NonNull final DataTableRow row,
			@NonNull final TaxDeclarationId declId,
			@NonNull final SoftAssertions softly)
	{
		final StepDefDataIdentifier docIdentifier = row.getAsIdentifier("Identifier");
		final TableRecordReference ref = identifiersResolver.getTableRecordReference(docIdentifier);

		final int adTableId = adTableDAO.retrieveAdTableId(ref.getTableName()).getRepoId();
		final int recordId = ref.getRecord_ID();

		final I_C_TaxDeclarationAcct acctRow = queryBL.createQueryBuilder(I_C_TaxDeclarationAcct.class)
				.addEqualsFilter(I_C_TaxDeclarationAcct.COLUMNNAME_C_TaxDeclaration_ID, declId.getRepoId())
				.addEqualsFilter(I_C_TaxDeclarationAcct.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_C_TaxDeclarationAcct.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnly(I_C_TaxDeclarationAcct.class);

		final String context = "C_TaxDeclarationAcct for declaration=" + declId.getRepoId()
				+ ", document=" + ref.getTableName() + "/" + recordId;

		softly.assertThat(acctRow)
				.as(context + ": row must exist")
				.isNotNull();

		if (acctRow == null)
		{
			return; // skip field assertions — SoftAssertions will report the null
		}

		final int expectedVatCodeId = vatCodeTable.get(row.getAsIdentifier("C_VAT_Code_ID")).getVatCodeId().getRepoId();
		softly.assertThat(acctRow.getC_VAT_Code_ID())
				.as(context + ": C_VAT_Code_ID")
				.isEqualTo(expectedVatCodeId);

		softly.assertThat(acctRow.getAmountType())
				.as(context + ": AmountType")
				.isEqualTo(row.getAsString("AmountType"));

		final BigDecimal expectedAmount = new BigDecimal(row.getAsString("Amount"));
		softly.assertThat(acctRow.getAmount())
				.as(context + ": Amount")
				.isEqualByComparingTo(expectedAmount);
	}
}
