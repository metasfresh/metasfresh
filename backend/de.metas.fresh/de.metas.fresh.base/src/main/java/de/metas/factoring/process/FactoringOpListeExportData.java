/*
 * #%L
 * de.metas.fresh.base
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

package de.metas.factoring.process;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The full set of typed rows that feed the CSV export — a header + a list of detail rows.
 *
 * <p>{@link #contractNo} and {@link #clientAccountId} are the tenant's per-org factoring
 * configuration on the factorer BP ({@code IsFactorer='Y'}, unique per org) and drive the
 * filename + header row of the CSV. Aggregate totals ({@link #sumD} / {@link #sumC}) are
 * computed over the detail rows by {@link FactoringOpListeService#buildExportData}.
 */
@Value
public class FactoringOpListeExportData
{
	@NonNull String contractNo;
	@NonNull String clientAccountId;
	@NonNull String currencyIso;
	@NonNull LocalDate uploadDate;
	@NonNull ImmutableList<FactoringOpListeDetailRow> detailRows;
	/** Sum of GrandTotal over the D (debit) rows. */
	@NonNull BigDecimal sumD;
	/** Sum of GrandTotal over the C (credit) rows. */
	@NonNull BigDecimal sumC;

	/** Total row count in the CSV: 1 header + N detail rows. */
	public int totalRowCount()
	{
		return 1 + detailRows.size();
	}
}
