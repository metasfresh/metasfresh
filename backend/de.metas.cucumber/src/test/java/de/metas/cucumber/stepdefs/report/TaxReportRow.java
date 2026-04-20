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

package de.metas.cucumber.stepdefs.report;

import de.metas.currency.Amount;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * One row returned by {@code de_metas_acct.report_taxaccounts(...)}.
 * <p>
 * Depending on the {@link #level}, either the per-document fields ({@link #taxAmt} / {@link #netAmt})
 * or the summed fields ({@link #taxAmtSum} / {@link #netAmtSum}) are populated — never both.
 * <p>
 * Level semantics:
 * <ul>
 *     <li>{@code 1} — summed per vatcode</li>
 *     <li>{@code 2} — summed per vatcode + accountname</li>
 *     <li>{@code 3} — summed per vatcode + accountname + taxname</li>
 *     <li>{@code 4} — per-document detail rows</li>
 *     <li>{@code ReCap} — summed per vatcode + taxname (subtotal)</li>
 * </ul>
 */
@Value
@Builder
class TaxReportRow
{
	@Nullable String level;
	@Nullable String vatCode;
	@Nullable String accountName;
	@Nullable String taxName;
	@Nullable Amount taxAmt;
	@Nullable Amount netAmt;
	@Nullable Amount taxAmtSum;
	@Nullable Amount netAmtSum;
	@Nullable String documentNo;
	@Nullable String bpartnerName;
}
