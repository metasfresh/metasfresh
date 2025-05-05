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

import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.organization.LocalDateAndOrgId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxRunConfig;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Properties;

public class PlainDunningContext extends AbstractDunningContext
{
	private final DunningConfig dunningConfig;
	private final Properties ctx;
	private final ITrxRunConfig trxRunConfig;
	
	private LocalDateAndOrgId dunningDate;
	private I_C_DunningLevel dunningLevel;

	@Nullable
	private final RecomputeDunningCandidatesQuery recomputeDunningCandidatesQuery;

	public PlainDunningContext(final Properties ctx, final ITrxRunConfig trxRunConfig)
	{
		super();
		this.ctx = ctx;
		this.trxRunConfig = trxRunConfig;
		this.dunningConfig = new DunningConfig();
		this.recomputeDunningCandidatesQuery = null;
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public String getTrxName()
	{
		return ITrx.TRXNAME_None;
	}

	@Override
	public I_C_DunningLevel getC_DunningLevel()
	{
		return dunningLevel;
	}

	@Override
	public DunningConfig getDunningConfig()
	{
		return dunningConfig;
	}

	@Override
	@Nullable
	public LocalDateAndOrgId getDunningDate()
	{
		return dunningDate;
	}

	public I_C_DunningLevel getDunningLevel()
	{
		return dunningLevel;
	}

	public void setDunningLevel(I_C_DunningLevel dunningLevel)
	{
		this.dunningLevel = dunningLevel;
	}

	public void setDunningDate(@Nullable LocalDateAndOrgId dunningDate)
	{
		this.dunningDate = dunningDate;
	}

	@Override
	public ITrxRunConfig getTrxRunnerConfig()
	{
		return trxRunConfig;
	}

	@Nullable
	@Override
	public RecomputeDunningCandidatesQuery getRecomputeDunningCandidatesQuery()
	{
		return recomputeDunningCandidatesQuery;
	}
}
