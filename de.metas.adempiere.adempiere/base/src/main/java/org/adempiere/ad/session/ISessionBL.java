package org.adempiere.ad.session;

import java.util.Properties;

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

import org.adempiere.util.ISingletonService;

public interface ISessionBL extends ISingletonService
{
	MFSession getSessionById(final Properties ctx, final int AD_Session_ID);

	/**
	 * @param ctx
	 * @return current session or null
	 */
	MFSession getCurrentSession(Properties ctx);

	/**
	 * Gets current session if exists. If not, creates a new session
	 * 
	 * @param ctx
	 * @return current session (existing or new); never returns null
	 */
	MFSession getCurrentOrCreateNewSession(Properties ctx);

	void logoutCurrentSession();

	/**
	 * 
	 * @return true if record change log system is enabled
	 */
	boolean isChangeLogEnabled();

	/**
	 * Disable change log system on current thread.
	 * 
	 * Useful if for example, you are running a process which is creating/changing a lot of master data which has change log enabled but in your case you don't want to do change logs because of
	 * performance issues.
	 * 
	 * @param disable true if it shall be disabled on current thread
	 */
	void setDisableChangeLogsForThread(final boolean disable);

}
