package de.metas.dlm.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.adempiere.exceptions.DBException;
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

	public abstract int getDlmLevel();

	public abstract int getDlmCoalesceLevel();


	/**
	 * Used to secure ourselves against a {@link StackOverflowError}.
	 */
	private final ThreadLocal<Boolean> inMethod = ThreadLocal.withInitial(() -> Boolean.FALSE);

	@Override
	public void customizeConnection(final Connection c) throws DBException
	{
		if (Adempiere.isUnitTestMode())
		{
			logger.debug("doing nothing because we are in unit test mode");
			return;
		}

		final boolean wasAlreadyInMethod = inMethod.get();
		if (wasAlreadyInMethod)
		{
			return;
		}

		try
		{
			inMethod.set(true);

			DLMConnectionUtils.setSearchPathForDLM(c);

			DLMConnectionUtils.changeDLMLevel(c, getDlmLevel());
			DLMConnectionUtils.changeDLMCoalesceLevel(c, getDlmCoalesceLevel());
		}
		catch (final SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}
		finally
		{
			inMethod.set(false);
		}
	}
}
