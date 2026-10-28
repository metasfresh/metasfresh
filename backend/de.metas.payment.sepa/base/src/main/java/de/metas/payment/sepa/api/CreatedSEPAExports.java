/*
 * #%L
 * de.metas.payment.sepa.base
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

package de.metas.payment.sepa.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.banking.mandate.DirectDebitMandate;
import de.metas.banking.mandate.DirectDebitMandateType;
import de.metas.payment.sepa.model.I_SEPA_Export;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * The SEPA exports created from one pay selection: one for a credit transfer, one per mandate type (scheme) for a direct debit.
 */
@Value
@Builder
public class CreatedSEPAExports
{
	@NonNull
	@Singular
	ImmutableList<CreatedSEPAExport> exports;

	/**
	 * @throws AdempiereException if there is not exactly one export
	 */
	@NonNull
	public CreatedSEPAExport getSingleExport()
	{
		if (exports.size() != 1)
		{
			throw new AdempiereException("Expected exactly one SEPA export but got " + exports.size());
		}
		return exports.get(0);
	}

	@Value
	@Builder
	public static class CreatedSEPAExport
	{
		@NonNull I_SEPA_Export export;

		/**
		 * The mandate type of all lines of this export; {@code null} for a credit transfer.
		 */
		@Nullable DirectDebitMandateType scheme;

		/**
		 * The mandate of each direct debit export line; empty for a credit transfer.
		 */
		@NonNull ImmutableMap<SEPAExportLineId, DirectDebitMandate> mandatesBySepaExportLineId;
	}
}
