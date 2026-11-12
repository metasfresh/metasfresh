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
import de.metas.location.LocationId;
import de.metas.payment.sepa.model.I_SEPA_Export;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

	@Value
	@Builder
	public static class CreatedSEPAExport
	{
		@NonNull I_SEPA_Export export;

		/** All lines of this export have this mandate type; {@code null} for a credit transfer or a direct debit without exportable lines. */
		@Nullable DirectDebitMandateType scheme;

		@NonNull ImmutableMap<SEPAExportLineId, DirectDebitMandate> mandatesBySepaExportLineId;

		/** The address captured on each line's invoice ({@code C_BPartner_Location_Value_ID}), not the partner's current location. */
		@NonNull ImmutableMap<SEPAExportLineId, LocationId> debtorLocationIdsBySepaExportLineId;

		/**
		 * @return an export without scheme, mandates and debtor addresses, i.e. a credit transfer or a direct debit without exportable lines
		 */
		@NonNull
		public static CreatedSEPAExport withoutMandates(@NonNull final I_SEPA_Export export)
		{
			return builder()
					.export(export)
					.scheme(null)
					.mandatesBySepaExportLineId(ImmutableMap.of())
					.debtorLocationIdsBySepaExportLineId(ImmutableMap.of())
					.build();
		}
	}
}
