package de.metas.dlm.exception;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.exceptions.IExceptionWrapper;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

import com.google.common.annotations.VisibleForTesting;

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
 * the "HINT" from an error message from the database looks like this:
 *
 * <pre>
 * DLM_Referencing_Table_Name = c_bankstatementline_ref;
 * DLM_Referencig_Column_Name = c_payment_id;
 * </pre>
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

	private static final Pattern HINT_REGEXP = Pattern.compile("[;, ]*DLM_Referencing_Table_Name *= *([a-zA-Z0-9_]+)[;, ]+DLM_Referencig_Column_Name *= *([a-zA-Z0-9_]+)[;, ]*");

	public static DLMExceptionWrapper INSTANCE = new DLMExceptionWrapper();

	private DLMExceptionWrapper()
	{
	}

	@Override
	public DBException wrapIfNeededOrReturnNull(final Throwable t)
	{
		final Boolean referencingTableHasDLMLevel;

		if (DBException.isSQLState(t, PG_SQLSTATE_Referencing_Record_Has_Wrong_DLM_Level))
		{
			referencingTableHasDLMLevel = true;
		}
		else if (DBException.isSQLState(t, PG_SQLSTATE_Referencing_Table_Has_No_DLM_LEvel))
		{
			referencingTableHasDLMLevel = false;
		}
		else
		{
			return null;
		}

		final SQLException sqlException = DBException.extractSQLExceptionOrNull(t);
		Check.errorUnless(sqlException instanceof PSQLException, "exception={} needs to be a PSQLExcetion", sqlException);

		final PSQLException psqlException = (PSQLException)sqlException;
		final ServerErrorMessage serverErrorMessage = psqlException.getServerErrorMessage();
		Check.errorIf(serverErrorMessage == null, "ServerErrorMessage of PSQLException={} may not be null", psqlException);

		final String hint = serverErrorMessage.getHint();
		Check.errorIf(Check.isEmpty(hint, true), "HINT ServerErrorMessage={} from of PSQLException={} may not be null", serverErrorMessage, psqlException);

		final String[] infos = extractInfos(hint);

		return new DLMException(t, referencingTableHasDLMLevel, infos[0], infos[1], infos[2]);
	}

	@VisibleForTesting
	static String[] extractInfos(final String hint)
	{
		final Matcher matcher = HINT_REGEXP.matcher(hint);

		final boolean matches = matcher.matches();
		Check.errorUnless(matches, "given string={} does not match our regexp={}", hint, HINT_REGEXP);

		final String referencedTableName = matcher.group(1);
		final String referencingTableName = matcher.group(2);
		final String referencingColumnName = matcher.group(3);

		return new String[] { referencedTableName, referencingTableName, referencingColumnName };
	}
}
