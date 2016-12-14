package de.metas.dlm.connection;

import java.sql.Connection;

import javax.annotation.concurrent.Immutable;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;

import de.metas.connection.IConnectionCustomizer;
import de.metas.dlm.migrator.IMigratorService;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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
/**
 * Customizes the connection using {@link ISysConfigBL}.
 * For this it is important that the table {@link org.compiere.model.I_AD_SysConfig#Table_Name} itself is never DLM'ed.
 * Otherwise we can't guarantee that the data is found.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
public class DLMPermanentSysConfigCustomizer extends AbstractDLMCustomizer
{
	private static final String SYSCONFIG_DLM_COALESCE_LEVEL = "de.metas.dlm.DLM_Coalesce_Level";
	private static final String SYSCONFIG_DLM_LEVEL = "de.metas.dlm.DLM_Level";

	public static IConnectionCustomizer PERMANENT_INSTANCE = new DLMPermanentSysConfigCustomizer();

	private DLMPermanentSysConfigCustomizer()
	{
	}

	/**
	 * Returns the DLM_Level set in the SysConfig. If none is set, it returns {@link IMigratorService#DLM_Level_TEST},
	 * so by default, only records that are "live/operational" and records that are currently processed by {@link IMigratorService#testMigratePartition(de.metas.dlm.Partition)}
	 * are visible to the system.
	 */
	@Override
	public int getDlmLevel(final Connection c)
	{
		return Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_DLM_LEVEL, IMigratorService.DLM_Level_TEST);
	}

	/**
	 * Returns the DLM_Coalesce_Level set in the SysConfig. If none is set, it returns {@link IMigratorService#DLM_Level_LIVE} to make sure that by default,
	 * everything that was not yet explicitly moved to a DLM level is visible.
	 */
	@Override
	public int getDlmCoalesceLevel(final Connection c)
	{
		return Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_DLM_COALESCE_LEVEL, IMigratorService.DLM_Level_LIVE);
	}
}
