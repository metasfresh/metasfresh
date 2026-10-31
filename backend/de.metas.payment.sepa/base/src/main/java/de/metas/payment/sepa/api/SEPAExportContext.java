/*
 * #%L
 * de.metas.payment.sepa.base
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

package de.metas.payment.sepa.api;

import com.google.common.collect.ImmutableMap;
import de.metas.banking.mandate.DirectDebitMandate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
@Builder
public class SEPAExportContext
{
	boolean referenceAsEndToEndId;

	@Singular
	ImmutableMap<SEPAExportLineId, DirectDebitMandate> directDebitMandates;

	public DirectDebitMandate getDirectDebitMandate(@NonNull final SEPAExportLineId sepaExportLineId)
	{
		final DirectDebitMandate mandate = directDebitMandates.get(sepaExportLineId);
		if (mandate == null)
		{
			throw new AdempiereException("No direct debit mandate for SEPA_Export_Line_ID=" + sepaExportLineId.getRepoId());
		}
		return mandate;
	}
}
