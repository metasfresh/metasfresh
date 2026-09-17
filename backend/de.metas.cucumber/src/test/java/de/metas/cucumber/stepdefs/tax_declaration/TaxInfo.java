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

import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Test-side snapshot of a {@code C_Tax}'s accounting configuration.
 * <p>
 * Resolves the T_Due / T_Credit account lines to the {@code "<value> <name>"} string that
 * {@code de_metas_acct.report_taxaccounts} returns in the {@code AccountName} column (see
 * {@code (accountno || ' ' || accountname)::text AS AccountName} in the function body).
 * <p>
 * That way feature files can assert on the stable conceptual role ({@code T_Due_Acct},
 * {@code T_Credit_Acct}) while still comparing against the raw, environment-dependent account
 * label that the DB function actually emits.
 */
@Value
@Builder
class TaxInfo
{
	@NonNull TaxId taxId;
	@NonNull String taxDueAccountName;
	@NonNull String taxCreditAccountName;
}
