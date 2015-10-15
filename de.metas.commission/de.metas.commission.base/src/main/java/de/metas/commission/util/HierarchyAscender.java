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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.service.ISponsorBL;

/**
 * Helper class that ascends the downline from a given sponsor and invokes the abstract {@link #actOnLevel(I_C_Sponsor)} method for every sponsor.
 * 
 * @author ts
 * 
 */
public abstract class HierarchyAscender extends HierarchyClimber
{

	private Timestamp date;

	/**
	 * 
	 * @param sponsor the sponsor to begin with. The first {@link #actOnLevel(I_C_Sponsor)} invocation will be with this sponsor.
	 * @param maxLevel the number of levels to climb up. "0" means "only look at the given sponsor". Levels where <code>actOnLevel</code> returns {@link #SKIP} are not counted.
	 */
	@Override
	public final void climb(final I_C_Sponsor sponsor, final int maxLevel)
	{
		sponsorSeen(sponsor);

		final Map<String, Object> contextInfo = new HashMap<String, Object>();

		I_C_Sponsor sponsorCurrentLevel = sponsor;

		int logicalLevel = 0;
		int hierarchyLevel = 0;
		int extendLevels = 0;

		while (logicalLevel <= maxLevel + extendLevels)
		{
			final Result result = actOnLevel(sponsorCurrentLevel, logicalLevel, hierarchyLevel, contextInfo);

			contextInfo.put(HierarchyClimber.CTX_PREDECESSOR_RESULT, result);
			contextInfo.put(HierarchyClimber.CTX_PREDECESSOR_SPONSOR, sponsorCurrentLevel);

			hierarchyLevel++;

			if (Result.GO_ON.equals(result))
			{
				logicalLevel++;
			}
			else if (Result.SKIP_EXTEND.equals(result))
			{
				logicalLevel++;
				if (maxLevel < Integer.MAX_VALUE)
				{
					extendLevels++;
				}

			}
			else if (Result.SKIP_IGNORE.equals(result))
			{
				// nothing to do
			}
			else if (Result.FINISHED.equals(result))
			{
				break;
			}

			final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
			sponsorCurrentLevel = sponsorBL.retrieveParent(
					InterfaceWrapperHelper.getCtx(sponsor),
					sponsorCurrentLevel, date,
					InterfaceWrapperHelper.getTrxName(sponsor));

			if (sponsorCurrentLevel == null)
			{
				break;
			}
			sponsorSeen(sponsorCurrentLevel);
		}
	}

	@Override
	public final HierarchyClimber setDate(final Timestamp date)
	{
		this.date = date;
		return this;
	}

	private final Set<Integer> knownSponsorIds = new HashSet<Integer>();

	private void sponsorSeen(final I_C_Sponsor sponsor)
	{
		Check.assume(knownSponsorIds.add(sponsor.getC_Sponsor_ID()), sponsor + " is iterated for the first time");
	}

}
