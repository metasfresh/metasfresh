package org.compiere.db;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.slf4j.Logger;

import com.mchange.v2.c3p0.AbstractConnectionCustomizer;

import de.metas.logging.LogManager;

/**
 * Use this class to add specific behavior to c3p0 connection handling.
 *
 * Added for task 04006.
 *
 * @author ts
 * @see http://www.mchange.com/projects/c3p0/#connection_customizers
 */
public class DB_PostgreSQL_ConnectionCustomizer extends AbstractConnectionCustomizer
{

	private static final String CLIENTINFO_ApplicationName = "ApplicationName";
	private static Logger log = LogManager.getLogger(DB_PostgreSQL_ConnectionCustomizer.class);

	/**
	 * When a new connection is acquired from the underlying postgres JDBC driver, this method sets the connections log limit to "WARNING".
	 * That way, only warning messages (and above) will be send to
	 * the client to prevent an OutOfMemoryError due to too many messages being send from verbose and long-running DB functions.
	 *
	 * Added for task 04006.
	 */
	@Override
	public void onAcquire(Connection c, String parentDataSourceIdentityToken) throws Exception
	{
		log.debug("Attempting to set client_min_messages=WARNING for pooled connection: {} ", c);
		c.prepareStatement("SET client_min_messages=WARNING").execute();

		c.setClientInfo(CLIENTINFO_ApplicationName, "metasfresh");
	}

	@Override
	public void onCheckIn(Connection c, String parentDataSourceIdentityToken) throws Exception
	{
		// NOTE: it's much more efficient to reset the ApplicationName here because this method is called in anthoer thread
		c.setClientInfo(CLIENTINFO_ApplicationName, "adempiere/CLOSED"); // task 08353
	}

	@Override
	public void onCheckOut(Connection c, String parentDataSourceIdentityToken) throws Exception
	{
		final boolean perTrx = false;

		// https://www.postgresql.org/docs/9.5/static/functions-admin.html
		final PreparedStatement ps = c.prepareStatement("select set_config('metasfresh.DLM_Level', ?, ?)");
		ps.setString(1, "1");
		ps.setBoolean(2, perTrx);
		ps.execute();
		final PreparedStatement ps2 = c.prepareStatement("select set_config('metasfresh.DLM_Coalesce_Level', ?, ?)");
		ps2.setString(1, "2");
		ps2.setBoolean(2, perTrx);
		ps2.execute();

	}

}
