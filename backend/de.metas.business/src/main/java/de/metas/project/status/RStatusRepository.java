/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.project.status;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_R_Status;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RStatusRepository
{
	@NonNull
	public RStatus getById(@NonNull final RStatusId statusId)
	{
		return Optional.ofNullable(InterfaceWrapperHelper.load(statusId, I_R_Status.class))
				.map(RStatusRepository::ofRecord)
				.orElseThrow(() -> new AdempiereException("Couldn't find any I_R_Status record for provided statusId = " + statusId.getRepoId()));
	}

	@NonNull
	private static RStatus ofRecord(@NonNull final I_R_Status record)
	{
		return RStatus.builder()
				.id(RStatusId.ofRepoId(record.getR_Status_ID()))
				.calendarColor(record.getCalendarColor())
				.build();
	}
}
