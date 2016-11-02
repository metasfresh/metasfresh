package de.metas.dlm.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import de.metas.connection.IConnectionCustomizer;
import de.metas.logging.LogManager;

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
 * Basic class for all DLM connection customizers. All DLM connection customizers have in common that they use {@link DLMConnectionUtils} to get and set the DLM related DB parameters for one DB connection.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class AbstractDLMCustomizer implements IConnectionCustomizer
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	public abstract int getDlmLevel(Connection c);

	public abstract int getDlmCoalesceLevel(Connection c);

	private int dlmLevelBkp = -1;
	private int dlmCoalesceLevelBkp = -1;

	// private AtomicBoolean inMethod = new AtomicBoolean(false);
	private final ThreadLocal<Boolean> inMethod = ThreadLocal.withInitial(() -> Boolean.FALSE);

	@Override
	public void customizeConnection(final Connection c) throws DBException
	{
		if (Adempiere.isUnitTestMode())
		{
			logger.debug("doing nothing because we are in unit test mode");
			return;
		}

		try
		{
			// final boolean wasAlreadyInMethod = inMethod.getAndSet(true);
			final boolean wasAlreadyInMethod = inMethod.get();
			if (wasAlreadyInMethod)
			{
				return;
			}
			inMethod.set(true);

			// https://www.postgresql.org/docs/9.5/static/functions-admin.html
			storeParamBkpValues(c);
			setDBParams(c, getDlmLevel(c), getDlmCoalesceLevel(c));
		}
		catch (final SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}
		finally
		{
			// inMethod.getAndSet(false);
			inMethod.set(false);
		}
	}

	private void storeParamBkpValues(final Connection c) throws SQLException
	{
		dlmLevelBkp = DLMConnectionUtils.retrieveCurrentDLMLevel(c);
		dlmCoalesceLevelBkp = DLMConnectionUtils.retrieveCurrentDLMCoalesceLevel(c);
	}

	private void setDBParams(final Connection c, final int dlmTargetLevel, final int dlmTargetCoalesceLevel) throws SQLException
	{
		if (!Check.equals(dlmTargetLevel, dlmLevelBkp))
		{
			DLMConnectionUtils.changeDLMLevel(c, dlmTargetLevel);
		}

		if (!Check.equals(dlmTargetCoalesceLevel, dlmCoalesceLevelBkp))
		{
			DLMConnectionUtils.changeDLMCoalesceLevel(c, dlmTargetCoalesceLevel);
		}
	}

	protected void restoreParamBkpValues(final Connection connection)
	{
		Check.assumeNotNull(connection, "Param 'connection' is not null");
		try
		{
			setDBParams(connection, dlmLevelBkp, dlmCoalesceLevelBkp);
		}
		catch (final SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}
	}
}
