/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.calendar;

import de.metas.cache.CCache;
import de.metas.calendar.standard.YearId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Period;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.ZoneId;

@Repository
public class PeriodRepo
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final CCache<Integer, I_C_Period> periodCache = CCache.newLRUCache(I_C_Period.Table_Name + "#by#C_Period_ID", 100, 60);

	@NonNull
	public Period getById(@NonNull final PeriodId periodId)
	{
		final I_C_Period record = getRecord(periodId.getRepoId());
		return fromPO(record);
	}

	private Period fromPO(@NonNull final I_C_Period periodRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(periodRecord.getAD_Org_ID());
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final Timestamp startDate = periodRecord.getStartDate();
		final Timestamp endDate = periodRecord.getEndDate();
		final PeriodId periodId = createPeriodId(periodRecord);

		return new Period(periodId,
						  orgId,
						  TimeUtil.asZonedDateTime(startDate, zoneId),
						  TimeUtil.asZonedDateTime(endDate, zoneId));

	}

	/**
	 * To be used only by other repos, which need to get a period referenced by some other record!
	 */
	@NonNull
	public PeriodId getPeriodId(final int periodRepoId)
	{
		final I_C_Period periodRecord = getRecord(periodRepoId);
		return createPeriodId(periodRecord);
	}

	private PeriodId createPeriodId(final I_C_Period record)
	{
		final YearId yearId = YearId.ofRepoId(record.getC_Year_ID());
		return PeriodId.ofRepoId(yearId, record.getC_Period_ID());
	}

	@NonNull
	private I_C_Period getRecord(final int periodRepoId)
	{
		final I_C_Period periodRecord = periodCache.getOrLoad(periodRepoId, id -> InterfaceWrapperHelper.load(id, I_C_Period.class));
		return Check.assumeNotNull(periodRecord, "Missing C_Period record for C_Period_ID={}", periodRepoId);
	}
}
