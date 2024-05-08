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


import java.util.Date;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.compiere.util.TimeUtil;

import de.metas.dunning.api.IDunningConfig;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.util.Check;

/**
 * Dunning context
 * 
 * @author tsa
 * 
 */
public class DunningContext extends AbstractDunningContext
{
	private final IDunningConfig dunningConfig;
	private final Properties ctx;
	private final Date dunningDate;
	private final I_C_DunningLevel dunningLevel;
	
	private final String trxName;
	private final ITrxRunConfig trxRunnerConfig;
	
	public DunningContext(final Properties ctx, final IDunningConfig config, final I_C_DunningLevel dunningLevel, final Date dunningDate, final ITrxRunConfig trxRunnerConfig, final String trxName)
	{
		super();

		Check.assume(ctx != null, "ctx is not null");
		Check.assume(config != null, "config is not null");

		this.dunningConfig = config;
		this.ctx = ctx;
		this.dunningLevel = dunningLevel;
		this.dunningDate = dunningDate == null ? null : TimeUtil.trunc(dunningDate, TimeUtil.TRUNC_DAY);
		this.trxName = trxName;
		this.trxRunnerConfig = trxRunnerConfig;
	}

	public DunningContext(final IDunningContext context, final String trxName)
	{
		super(context);

		this.dunningConfig = context.getDunningConfig();
		this.ctx = context.getCtx();
		this.dunningLevel = context.getC_DunningLevel();
		this.dunningDate = context.getDunningDate();
		this.trxName = trxName;
		this.trxRunnerConfig = context.getTrxRunnerConfig();
	}

	@Override
	public String toString()
	{
		return "DunningContext ["
				+ "dunningLevel=" + dunningLevel
				+ ", dunningDate=" + dunningDate
				+ ", trxName=" + trxName
				+ ", config=" + dunningConfig
				+ ", ctx=" + ctx
				+ "]";
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public String getTrxName()
	{
		return trxName;
	}

	@Override
	public ITrxRunConfig getTrxRunnerConfig()
	{
		return trxRunnerConfig;
	}
	
	@Override
	public I_C_DunningLevel getC_DunningLevel()
	{
		return dunningLevel;
	}

	@Override
	public IDunningConfig getDunningConfig()
	{
		return dunningConfig;
	}

	@Override
	public Date getDunningDate()
	{
		if (dunningDate == null)
		{
			return dunningDate;
		}
		return (Date)dunningDate.clone();
	}

}
