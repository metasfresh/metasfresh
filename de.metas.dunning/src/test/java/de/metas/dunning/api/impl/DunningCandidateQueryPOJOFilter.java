package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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


import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.wrapper.IPOJOFilter;
import org.compiere.util.Env;

import de.metas.dunning.api.IDunningCandidateQuery;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_Dunning_Candidate;

public class DunningCandidateQueryPOJOFilter implements IPOJOFilter<I_C_Dunning_Candidate>
{
	private static final transient Logger logger = LogManager.getLogger(DunningCandidateQueryPOJOFilter.class);

	private final IDunningContext context;
	private final IDunningCandidateQuery query;

	public DunningCandidateQueryPOJOFilter(final IDunningContext context, final IDunningCandidateQuery query)
	{
		this.context = context;
		this.query = query;
	}

	@Override
	public boolean accept(final I_C_Dunning_Candidate candidate)
	{
		if (query.getAD_Table_ID() > 0 && query.getRecord_ID() >= 0)
		{
			if (candidate.getAD_Table_ID() != query.getAD_Table_ID())
			{
				return false;
			}
			if (candidate.getRecord_ID() != query.getRecord_ID())
			{
				return false;
			}
		}

		final List<I_C_DunningLevel> dunningLevels = query.getC_DunningLevels();
		if (dunningLevels != null && !dunningLevels.isEmpty())
		{
			boolean levelFound = false;
			for (I_C_DunningLevel level : dunningLevels)
			{
				if (candidate.getC_DunningLevel_ID() == level.getC_DunningLevel_ID())
				{
					levelFound = true;
					break;
				}
			}
			if (!levelFound)
			{
				return false;
			}
		}

		if (query.getProcessed() != null && candidate.isProcessed() != query.getProcessed())
		{
			return false;
		}

		if (query.isActive() && !candidate.isActive())
		{
			return false;
		}

		if (query.isApplyClientSecurity())
		{
			final int adClientId = Env.getAD_Client_ID(context.getCtx());
			if (adClientId <= 0)
			{
				logger.warn("Context AD_Client_ID is possible to not be configured. Please check it.");
			}
			if (candidate.getAD_Client_ID() != adClientId)
			{
				return false;
			}
		}

		if (query.getWriteOff() != null && candidate.isWriteOff() != query.getWriteOff())
		{
			return false;
		}

		return true;
	}

}
