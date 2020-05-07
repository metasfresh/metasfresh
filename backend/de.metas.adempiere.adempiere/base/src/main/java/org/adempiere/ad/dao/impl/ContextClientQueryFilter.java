package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.compiere.util.Env;

/**
 * Retain only those records which had the AD_Client_ID equals with the one from Context.
 *
 * @author tsa
 *
 * @param <T>
 */
public class ContextClientQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	private static final boolean DEFAULT_IncludeSystemClient = false; // default=false for backward compatibility

	private static final String SQL_ADClient = COLUMNNAME_AD_Client_ID + "=?";
	private static final String SQL_ADClientOrSystem = COLUMNNAME_AD_Client_ID + " IN (?, " + IClientDAO.SYSTEM_CLIENT_ID + ")";

	private Properties ctx;
	private final boolean includeSystemClient;
	private final String sql;

	public ContextClientQueryFilter()
	{
		this((Properties)null, DEFAULT_IncludeSystemClient);
	}

	public ContextClientQueryFilter(final Properties ctx)
	{
		this(ctx, DEFAULT_IncludeSystemClient);
	}

	public ContextClientQueryFilter(final Properties ctx, final boolean includeSystemClient)
	{
		this.ctx = ctx;
		this.includeSystemClient = includeSystemClient;
		this.sql = includeSystemClient ? SQL_ADClientOrSystem : SQL_ADClient;
	}

	@Override
	public String toString()
	{
		return includeSystemClient ? "(context client or system)" : "(context client)";
	}

	@Override
	public String getSql()
	{
		return sql;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		final Properties ctxToUse = ctx == null ? this.ctx : ctx;
		final int adClientId = Env.getAD_Client_ID(ctxToUse);
		return Arrays.asList((Object)adClientId);
	}

	public void setContext(final Properties ctx)
	{
		this.ctx = ctx;
	}

	/**
	 * Compared the given model's {@code AD_CLient_ID} with the {@code AD_CLient_ID} from
	 * <ul>
	 * <li>this instances {@code ctx} member if that member is not null (see {@link #ContextClientQueryFilter(Properties)}) and {@link #setContext(Properties)}</li>
	 * <li>the given {@code model}'s own {@code ctx} (see {@link InterfaceWrapperHelper#getCtx(Object)})</li>
	 * </ul>
	 */
	@Override
	public boolean accept(T model)
	{
		final int adClientId = getAD_Client_ID(model);

		final Properties ctxToUse = this.ctx != null ? ctx : InterfaceWrapperHelper.getCtx(model);

		final int contextClientId = Env.getAD_Client_ID(ctxToUse);

		// Context client
		if (adClientId == contextClientId)
		{
			return true;
		}

		// System client
		if (includeSystemClient && adClientId == IClientDAO.SYSTEM_CLIENT_ID)
		{
			return true;
		}

		return false;
	}

	private int getAD_Client_ID(final T model)
	{
		final Object adClientId = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_AD_Client_ID);
		if (adClientId == null)
		{
			return -1;
		}
		else if (adClientId instanceof Number)
		{
			final int adClientIdInt = ((Number)adClientId).intValue();
			return adClientIdInt;
		}
		else
		{
			throw new IllegalArgumentException("Invalid AD_Client_ID value '" + adClientId + "' for " + model);
		}
	}
}
