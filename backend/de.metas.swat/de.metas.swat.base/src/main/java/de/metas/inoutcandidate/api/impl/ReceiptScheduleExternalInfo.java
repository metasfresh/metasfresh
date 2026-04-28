/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.inoutcandidate.api.impl;

import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Value
public class ReceiptScheduleExternalInfo
{
	@Nullable
	LocalDate movementDate;

	@Nullable
	LocalDate dateAcct;

	@Nullable
	ZonedDateTime dateReceived;

	@Nullable
	String externalId;

	@Builder
	public ReceiptScheduleExternalInfo(@Nullable final LocalDate movementDate,
			@Nullable final LocalDate dateAcct,
			@Nullable final ZonedDateTime dateReceived,
			@Nullable final String externalId)
	{
		if (movementDate == null && dateReceived == null && Check.isBlank(externalId))
		{
			throw new AdempiereException("Empty object!");
		}

		this.movementDate = movementDate;
		this.dateAcct = dateAcct;
		this.dateReceived = dateReceived;
		this.externalId = externalId;
	}
}
