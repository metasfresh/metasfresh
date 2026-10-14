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
import de.metas.acct.vatcode.VATCodeAmountType;
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

import java.util.UUID;

/**
 * Step definitions for creating {@link de.metas.acct.model.I_C_VAT_Code} records tied to an existing {@code C_Tax}.
 *
 * @see C_VAT_Code_StepDefData
 * @see IVATCodeDAO#createVATCode
 */
@RequiredArgsConstructor
public class C_VAT_Code_StepDef
{
	@NonNull private final C_Tax_StepDefData taxTable;
	@NonNull private final C_VAT_Code_StepDefData vatCodeTable;

	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final IVATCodeDAO vatCodeDAO = Services.get(IVATCodeDAO.class);

	/**
	 * Create a {@link de.metas.acct.model.I_C_VAT_Code} per data-table row, tied to an existing {@code C_Tax}.
	 *
	 * <p><b>Required columns</b>:
	 * <ul>
	 *     <li>{@code Identifier} — unique identifier used to look the VAT code up later (e.g., in the report step)</li>
	 *     <li>{@code C_Tax_ID} — identifier of an existing {@code C_Tax} record (from {@link C_Tax_StepDefData})</li>
	 *     <li>{@code IsSOTrx} — {@code Y}/{@code N}/{@code true}/{@code false}: sales-side vs purchase-side code</li>
	 * </ul>
	 *
	 * <p><b>Optional columns</b>:
	 * <ul>
	 *     <li>{@code AmountType} — {@code T} (tax amount, default) or {@code N} (net/base amount).
	 *         If omitted, defaults to {@code T}. Use {@code N} only for VAT codes that represent the net
	 *         base amount in reverse-charge scenarios (e.g., KZ 84/85 base-amount buckets).</li>
	 * </ul>
	 *
	 * <p>The {@code C_AcctSchema_ID} is always the primary accounting schema of
	 * {@link StepDefConstants#CLIENT_ID} (resolved via {@link IAcctSchemaBL#getPrimaryAcctSchema}). The
	 * {@code VATCode} value is a 10-char UUID prefix (the {@code C_VAT_Code.VATCode} column is
	 * {@code VARCHAR(10)}) so that two codes created for the same {@code C_Tax} within the same
	 * scenario (sales + purchase) have distinct values and the tax report can group by them.
	 *
	 * <p>Multiple {@code C_VAT_Code} rows per {@code (AcctSchema, C_Tax)} are allowed — one per
	 * {@code IsSOTrx} side. The §13b reverse-charge posting routes T_Due_Acct (output, UStVA KZ 84/85)
	 * to the {@code IsSOTrx=Y} code and T_Credit_Acct (input, KZ 67) to the {@code IsSOTrx=N} code.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And metasfresh contains C_Tax
	 *   | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
	 *   | tax19      | taxCategory      | 19   | DE                       | DE                        |
	 * And metasfresh contains C_VAT_Codes:
	 *   | Identifier      | C_Tax_ID | IsSOTrx | AmountType |
	 *   | vatCode19_sales | tax19    | Y       | T          |
	 *   | vatCode19_buy   | tax19    | N       | T          |
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

		// C_VAT_Code.VATCode is VARCHAR(10) — any longer value is silently truncated by the PO
		// setter, making two codes for the same C_Tax collide on the truncated prefix.
		final String vatCodeValue = UUID.randomUUID().toString().replace("-", "").substring(0, 10);

		// AmountType: optional column; defaults to 'T' (tax amount).
		// getAsOptionalEnum resolves by ReferenceListAwareEnum code ('N'/'T'), so invalid values fail fast
		// at the step level with a clear enum-resolution error rather than a DB constraint violation.
		final VATCodeAmountType amountType = row.getAsOptionalEnum("AmountType", VATCodeAmountType.class)
				.orElse(VATCodeAmountType.Tax);

		final VATCode vatCode = vatCodeDAO.createVATCode(CreateVATCodeRequest.builder()
				.acctSchemaId(acctSchema.getId())
				.taxId(taxId)
				.vatCode(vatCodeValue)
				.isSOTrx(isSOTrx)
				.amountType(amountType)
				.validFrom(StepDefConstants.DEFAULT_ValidFrom)
				.build());

		vatCodeTable.putOrReplace(row.getAsIdentifier(), vatCode);
	}
}
