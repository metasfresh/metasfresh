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

import de.metas.dlm.partitioner.config.TableReferenceDescriptor;

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
 * the "DETAIL" from an error message from the database looks like this:
 *
 *
 * DLM_Referenced_Table_Name = c_payment_tbl; DLM_Referencing_Table_Name = c_bankstatementline_ref; DLM_Referencig_Column_Name = c_payment_id;
 *
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DLMExceptionWrapper implements IExceptionWrapper<DBException>
{
	/**
	 * metasfresh-custom error code.
	 */
	private static final String PG_SQLSTATE_Referencing_Record_Has_Wrong_DLM_Level = "235D3";

	/**
	 * metasfresh-custom error code.
	 */
	private static final String PG_SQLSTATE_Referencing_Table_Has_No_DLM_LEvel = "235N3";

	/**
	 * This pattern is used to parse the postgresql error message. some notes:
	 * <li>we try to be tolerant with minor variations in the string, as long as the important parts are still correct.
	 * <li>for the two groups of <code>([a-zA-Z0-9_]+?)(_tbl)?</code>, and an input of either <code>c_payment</code> or <code>c_payment_tbl</code>, the first group always matches <code>c_payment</code>.<br>
	 * This is what we want, because we are not interested in the <code>_tbl</code> prefix.<br>
	 * It works because the first group is "reluctant" and the second one is "greedy".
	 */
	private static final Pattern DETAIL_REGEXP = Pattern.compile("[;, ]*DLM_Referenced_Table_Name *= *([a-zA-Z0-9_]+?)(_tbl)?[;, ]+DLM_Referencing_Table_Name *= *([a-zA-Z0-9_]+?)(_tbl)?[;, ]+DLM_Referencig_Column_Name *= *([a-zA-Z0-9_]+?)(_tbl)?[;, ]*",
			Pattern.CASE_INSENSITIVE);

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

		final String detail = serverErrorMessage.getDetail();
		Check.errorIf(Check.isEmpty(detail, true), "DETAIL ServerErrorMessage={} from of PSQLException={} may not be null", serverErrorMessage, psqlException);

		final String[] infos = extractInfos(detail);

		return new DLMException(t,  TableReferenceDescriptor.of(infos[0], infos[1], infos[2]), referencingTableHasDLMLevel);
	}

	@VisibleForTesting
	static String[] extractInfos(final String detail)
	{
		final Matcher matcher = DETAIL_REGEXP.matcher(detail);

		final boolean matches = matcher.matches();
		Check.errorUnless(matches, "given string={} does not match our regexp={}", detail, DETAIL_REGEXP);

		final String referencedTableName = matcher.group(1);
		final String referencingTableName = matcher.group(3);
		final String referencingColumnName = matcher.group(5);

		return new String[] { referencedTableName, referencingTableName, referencingColumnName };
	}
}
