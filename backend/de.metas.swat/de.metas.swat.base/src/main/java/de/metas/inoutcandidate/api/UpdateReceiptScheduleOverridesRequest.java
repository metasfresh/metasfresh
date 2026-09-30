/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.api;

import de.metas.i18n.AdMessageKey;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Value
public class UpdateReceiptScheduleOverridesRequest
{
	public static final AdMessageKey MSG_VALIDATION_ERROR = AdMessageKey.of("receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError");

	@NonNull
	PInstanceId pinstanceId;

	@Nullable
	LocalDateTime datePromisedOverride;

	@Nullable
	String poReference;

	@Nullable
	Boolean isConfirmedBySupplier;

	@Builder
	public UpdateReceiptScheduleOverridesRequest(
			@NonNull final PInstanceId pinstanceId,
			@Nullable final LocalDateTime datePromisedOverride,
			@Nullable final String poReference,
			@Nullable final Boolean isConfirmedBySupplier)
	{
		if (datePromisedOverride == null
				&& Check.isBlank(poReference)
				&& isConfirmedBySupplier == null)
		{
			throw new AdempiereException(MSG_VALIDATION_ERROR)
					.markAsUserValidationError();
		}

		this.pinstanceId = pinstanceId;
		this.datePromisedOverride = datePromisedOverride;
		this.poReference = poReference;
		this.isConfirmedBySupplier = isConfirmedBySupplier;
	}
}
