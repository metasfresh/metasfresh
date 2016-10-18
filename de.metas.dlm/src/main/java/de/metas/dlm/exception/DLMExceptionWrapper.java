package de.metas.dlm.exception;

import java.sql.SQLException;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.exceptions.IExceptionWrapper;

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
 * Augments {@link DBException}. With this instance being registered, {@link DBException} can wrap an {@link SQLException} into a {@link DLMException}<br>
 * Registered via {@link DBException#registerExceptionWrapper(IExceptionWrapper)}
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DLMExceptionWrapper implements IExceptionWrapper<DBException>
{
	/**
	 * metasfresh-custom error code.
	 */
	private static final String PG_SQLSTATE_Referencing_Record_Has_Wrong_DLM_Level = "235D5";

	/**
	 * metasfresh-custom error code.
	 */
	private static final String PG_SQLSTATE_Referencing_Table_Has_No_DLM_LEvel = "235N5";

	public static DLMExceptionWrapper INSTANCE = new DLMExceptionWrapper();

	private DLMExceptionWrapper()
	{
	}

	@Override
	public DBException wrapIfNeededOrReturnNull(final Throwable t)
	{
		if (DBException.isSQLState(t, PG_SQLSTATE_Referencing_Record_Has_Wrong_DLM_Level))
		{
			throw new DLMException(t, true);
		}
		if (DBException.isSQLState(t, PG_SQLSTATE_Referencing_Table_Has_No_DLM_LEvel))
		{
			throw new DLMException(t, false);
		}
		return null;
	}
}
