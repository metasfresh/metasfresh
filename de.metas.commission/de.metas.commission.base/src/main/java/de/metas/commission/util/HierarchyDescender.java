package de.metas.commission.util;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.util.Services;

import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.service.ISponsorDAO;

/**
 * Helper class that descends the downline from a given sponsor and invokes the abstract {@link #actOnLevel(I_C_Sponsor)} method for every sponsor.
 * 
 * @author ts
 * 
 */
public abstract class HierarchyDescender extends HierarchyClimber
{

	/**
	 * 
	 * @param sponsor the sponsor to begin with. The first {@link #actOnLevel(I_C_Sponsor)} invocation will be with this sponsor.
	 * @param maxLevel the number of levels to climb down. "0" means "only look at the given sponsor". Levels where <code>actOnLevel</code> returns {@link #SKIP} are not counted.
	 */
	@Override
	public final void climb(final I_C_Sponsor sponsor, final int maxLevel)
	{
		final Map<String, Object> contextInfo = new HashMap<String, Object>();

		evalCurrentSponsor(dateFrom, dateTo, maxLevel, 0, 0, contextInfo, sponsor);
	}

	private final void descend(
			final I_C_Sponsor parentSponsor,
			final Result parentResult,
			final Timestamp dateFrom,
			final Timestamp dateTo,
			final int maxLevel,
			final int logicalLevel,
			final int hierarchyLevel,
			final Map<String, Object> contextInfo)
	{
		if (logicalLevel > maxLevel)
		{
			// done
			return;
		}

		final Collection<I_C_Sponsor> sponsorsCurrentLevel;

		if (dateFrom.equals(dateTo))
		{
			sponsorsCurrentLevel = Services.get(ISponsorDAO.class).retrieveChildren(parentSponsor, dateTo);
		}
		else
		{
			sponsorsCurrentLevel = Services.get(ISponsorDAO.class).retrieveChildrenBetween(parentSponsor, dateFrom, dateTo, cAdvComSystemID);
		}

		for (final I_C_Sponsor currentSponsor : sponsorsCurrentLevel)
		{
			contextInfo.put(HierarchyClimber.CTX_PREDECESSOR_RESULT, parentResult);
			contextInfo.put(HierarchyClimber.CTX_PREDECESSOR_SPONSOR, parentSponsor);

			evalCurrentSponsor(dateFrom, dateTo, maxLevel, logicalLevel, hierarchyLevel, contextInfo, currentSponsor);
		}
	}

	private void evalCurrentSponsor(
			final Timestamp dateFrom,
			final Timestamp dateTo,
			final int maxLevel,
			final int logicalLevel,
			final int hierarchyLevel,
			final Map<String, Object> contextInfo,
			final I_C_Sponsor currentSponsor)
	{
		final Result currentResult = actOnLevel(currentSponsor, logicalLevel, hierarchyLevel, contextInfo);

		if (Result.GO_ON.equals(currentResult))
		{
			descend(currentSponsor, currentResult, dateFrom, dateTo, maxLevel, logicalLevel + 1, hierarchyLevel + 1, contextInfo);
		}
		else if (Result.SKIP_EXTEND.equals(currentResult))
		{
			descend(currentSponsor, currentResult, dateFrom, dateTo, maxLevel + 1, logicalLevel + 1, hierarchyLevel + 1, contextInfo);
		}
		else if (Result.SKIP_IGNORE.equals(currentResult))
		{
			descend(currentSponsor, currentResult, dateFrom, dateTo, maxLevel, logicalLevel, hierarchyLevel + 1, contextInfo);
		}
		else if (Result.FINISHED.equals(currentResult))
		{
			// nothing to do
		}
	}

	private int cAdvComSystemID = 0;

	public HierarchyDescender setCommissionSystem(final int cAdvComSystemID)
	{
		this.cAdvComSystemID = cAdvComSystemID;
		return this;
	}

	protected Timestamp dateFrom = CommissionConstants.VALID_RANGE_MIN;

	protected Timestamp dateTo = CommissionConstants.VALID_RANGE_MAX;

	@Override
	public final HierarchyClimber setDate(final Timestamp date)
	{
		setDateFrom(date);
		setDateTo(date);

		return this;
	}

	public final HierarchyDescender setDateFrom(final Timestamp date)
	{
		dateFrom = date;
		return this;
	}

	public final HierarchyDescender setDateTo(final Timestamp date)
	{
		dateTo = date;
		return this;
	}
}
