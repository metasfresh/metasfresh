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
package de.metas.cucumber.stepdefs.tax;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.vatcode.CreateVATCodeRequest;
import de.metas.acct.vatcode.IVATCodeDAO;
import de.metas.acct.vatcode.VATCode;
import de.metas.cucumber.stepdefs.C_Tax_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

/**
 * Step definitions for creating {@link de.metas.acct.model.I_C_VAT_Code} records tied to an existing {@code C_Tax}.
 *
 * @see C_VAT_Code_StepDefData
 * @see IVATCodeDAO#createVATCode
 * @see IVATCodeDAO#existsForAcctSchemaAndTax
 */
@RequiredArgsConstructor
public class C_VAT_Code_StepDef
{
	@NonNull private final C_Tax_StepDefData taxTable;
	@NonNull private final C_VAT_Code_StepDefData vatCodeTable;

	@NonNull private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	@NonNull private final IVATCodeDAO vatCodeDAO = Services.get(IVATCodeDAO.class);

	/**
	 * Create a {@link de.metas.acct.model.I_C_VAT_Code} per data-table row, tied to an existing {@code C_Tax}.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — unique identifier used to look the VAT code up later (e.g., in the report step)</li>
	 *     <li>{@code C_Tax_ID} — identifier of an existing {@code C_Tax} record (from {@link C_Tax_StepDefData})</li>
	 *     <li>{@code IsSOTrx} — {@code Y}/{@code N}/{@code true}/{@code false}: whether this VAT code applies to sales or purchase</li>
	 * </ul>
	 *
	 * <p>The {@code C_AcctSchema_ID} is always the primary accounting schema of {@link StepDefConstants#CLIENT_ID}
	 * (resolved via {@link IAcctSchemaBL#getPrimaryAcctSchema}). The {@code VATCode} value is set to the
	 * referenced {@code C_Tax.Name} — which is itself unique per test run thanks to
	 * {@code suggestValueAndName} — so distinct scenarios cannot collide on the unique VATCode constraint.
	 *
	 * <p><b>Fails fast</b>: if a {@code C_VAT_Code} already exists for the given {@code C_AcctSchema_ID} +
	 * {@code C_Tax_ID}, the step aborts with an {@link AdempiereException}. This guards against silent
	 * duplication and forces the test to be aware of pre-existing state.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And metasfresh contains C_Tax
	 *   | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
	 *   | salesTax19 | salesTaxCategory | 19   | DE                       | DE                        |
	 * And metasfresh contains C_VAT_Codes:
	 *   | Identifier   | C_Tax_ID   | IsSOTrx |
	 *   | salesVat19   | salesTax19 | Y       |
	 * }</pre>
	 */
	@And("metasfresh contains C_VAT_Codes:")
	public void metasfresh_contains_c_vat_codes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createVATCode);
	}

	private void createVATCode(@NonNull final DataTableRow row)
	{
		final Tax tax = taxTable.get(row.getAsIdentifier("C_Tax_ID"));
		final TaxId taxId = tax.getTaxId();
		final boolean isSOTrx = row.getAsBoolean("IsSOTrx");
		final AcctSchema acctSchema = acctSchemaBL.getPrimaryAcctSchema(StepDefConstants.CLIENT_ID);

		if (vatCodeDAO.existsForAcctSchemaAndTax(acctSchema.getId(), taxId))
		{
			throw new AdempiereException("C_VAT_Code already exists for this acct schema + tax, refusing to create a duplicate")
					.appendParametersToMessage()
					.setParameter("C_AcctSchema_ID", acctSchema.getId().getRepoId())
					.setParameter("C_Tax_ID", taxId.getRepoId())
					.setParameter("C_Tax.Name", tax.getName());
		}

		final VATCode vatCode = vatCodeDAO.createVATCode(CreateVATCodeRequest.builder()
				.acctSchemaId(acctSchema.getId())
				.taxId(taxId)
				.vatCode(tax.getName())
				.isSOTrx(isSOTrx)
				.validFrom(StepDefConstants.DEFAULT_ValidFrom)
				.build());

		row.getAsOptionalIdentifier().ifPresent(identifier -> vatCodeTable.putOrReplace(identifier, vatCode));
	}
}
