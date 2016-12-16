package de.metas.dlm.exception;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.exceptions.IExceptionWrapper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
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
 * Augments {@link DBException}. With this instance being registered, {@link DBException} can wrap an {@link SQLException} into a {@link DLMReferenceException}<br>
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
public class DLMReferenceExceptionWrapper implements IExceptionWrapper<DBException>
{
	/**
	 * metasfresh-custom error code.
	 */
	@VisibleForTesting
	static final String PG_SQLSTATE_Referencing_Record_Has_Wrong_DLM_Level = "235D3";

	/**
	 * metasfresh-custom error code.
	 */
	@VisibleForTesting
	static final String PG_SQLSTATE_Referencing_Table_Has_No_DLM_LEvel = "235N3";

	/**
	 * This pattern is used to parse the postgresql error message. some notes:
	 * <li>we try to be tolerant with minor variations in the string, as long as the important parts are still correct.
	 * <li>for the two groups of <code>([a-zA-Z0-9_]+?)(_tbl)?</code>, and an input of either <code>c_payment</code> or <code>c_payment_tbl</code>, the first group always matches <code>c_payment</code>.<br>
	 * This is what we want, because we are not interested in the <code>_tbl</code> prefix.<br>
	 * It works because the first group is "reluctant" and the second one is "greedy".
	 */
	private static final Pattern DETAIL_REGEXP = Pattern.compile("[;, ]*DLM_Referenced_Table_Name *= *([a-zA-Z0-9_]+?)(_tbl)?[;, ]+DLM_Referenced_Record_ID *= *([0-9]+?)[;, ]+DLM_Referencing_Table_Name *= *([a-zA-Z0-9_]+?)(_tbl)?[;, ]+DLM_Referencig_Column_Name *= *([a-zA-Z0-9_]+?)(_tbl)?[;, ]*",
			Pattern.CASE_INSENSITIVE);

	public static DLMReferenceExceptionWrapper INSTANCE = new DLMReferenceExceptionWrapper();

	private DLMReferenceExceptionWrapper()
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

		//
		// parse the exception detail and extract the infos
		final SQLException sqlException = DBException.extractSQLExceptionOrNull(t);
		Check.errorUnless(sqlException instanceof PSQLException, "exception={} needs to be a PSQLExcetion", sqlException);

		final PSQLException psqlException = (PSQLException)sqlException;
		final ServerErrorMessage serverErrorMessage = psqlException.getServerErrorMessage();
		Check.errorIf(serverErrorMessage == null, "ServerErrorMessage of PSQLException={} may not be null", psqlException);

		final String detail = serverErrorMessage.getDetail();
		Check.errorIf(Check.isEmpty(detail, true), "DETAIL ServerErrorMessage={} from of PSQLException={} may not be null", serverErrorMessage, psqlException);

		final String[] infos = extractInfos(detail);

		//
		// the the "real" tables and column from the extracted lowercase infos
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final I_AD_Table referencedTable = adTableDAO.retrieveTable(infos[0]);
		Check.errorIf(referencedTable == null, "Unable to retrieve an AD_Table for referencedTable name={}", infos[0]);

		final I_AD_Table referencingTable = adTableDAO.retrieveTable(infos[2]);
		Check.errorIf(referencingTable == null, "Unable to retrieve an AD_Table for referencingTable name={}", infos[2]);

		final I_AD_Column referencingColumn = adTableDAO.retrieveColumn(referencingTable.getTableName(), infos[3]);
		Check.errorIf(referencingTable == null, "Unable to retrieve an AD_Column for referencingTable name={} and referencingColumn name={}", infos[2], infos[3]);

		return new DLMReferenceException(t,
				TableReferenceDescriptor.of(
						referencingTable.getTableName(),
						referencingColumn.getColumnName(),
						referencedTable.getTableName(),
						Integer.parseInt(infos[1])),
				referencingTableHasDLMLevel);
	}

	@VisibleForTesting
	static String[] extractInfos(final String detail)
	{
		final Matcher matcher = DETAIL_REGEXP.matcher(detail);

		final boolean matches = matcher.matches();
		Check.errorUnless(matches, "given string={} does not match our regexp={}", detail, DETAIL_REGEXP);

		final String referencedTableName = matcher.group(1);
		final String referencedRecordId = matcher.group(3);
		final String referencingTableName = matcher.group(4);
		final String referencingColumnName = matcher.group(6);

		return new String[] { referencedTableName, referencedRecordId, referencingTableName, referencingColumnName };
	}
}
