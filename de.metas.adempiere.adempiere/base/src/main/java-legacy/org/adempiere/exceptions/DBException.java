/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.adempiere.exceptions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.exceptions.IExceptionWrapper;
import org.compiere.util.DB;

import de.metas.logging.LogManager;

/**
 * This RuntimeException is used to pass SQLException up the chain of calling methods to determine what to do where needed.
 *
 * @author Vincent Harcq
 * @version $Id: DBException.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * @author Armen Rizal, GOODWILL CONSULTING FR [2789943] Better DBException handling for PostgreSQL https://sourceforge.net/tracker/?func=detail&aid=2789943&group_id=176962&atid=879335
 */
public class DBException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 4264201718343118625L;

	/**
	 * Wraps given throwable to {@link DBException} if is not already an {@link DBException}.
	 *
	 * @param throwable
	 * @return {@link DBException} or null if throwable is null
	 */
	public static DBException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}

		if (throwable instanceof DBException)
		{
			return (DBException)throwable; // no wrapping required
		}

		if (DBException.isDeadLockDetected(throwable))
		{
			return new DBDeadLockDetectedException(throwable, null); // connection=null; don't provide a connection as we don't have it.
		}

		if (DBException.isForeignKeyViolation(throwable))
		{
			return new DBForeignKeyConstraintException(throwable);
		}

		if (DBException.isUniqueContraintError(throwable))
		{
			return new DBUniqueConstraintException(throwable);
		}

		for (final IExceptionWrapper<DBException> exceptionWrapper : exceptionWrappers)
		{
			final DBException ex = exceptionWrapper.wrapIfNeededOrReturnNull(throwable);
			if (ex != null)
			{
				return ex;
			}
		}

		return new DBException(throwable);
	}

	private static List<IExceptionWrapper<DBException>> exceptionWrappers = new ArrayList<>();

	public static void registerExceptionWrapper(IExceptionWrapper<DBException> wrapper)
	{
		exceptionWrappers.add(wrapper);
	}

	//
	// PostgreSQL relevant SQL State codes
	// see http://www.postgresql.org/docs/9.1/static/errcodes-appendix.html
	private static final String PG_SQLSTATE_foreign_key_violation = "23503";

	/**
	 * Typical log error message for this case:
	 *
	 * <pre>
	 * <2015-06-24 17:19:54 CEST | 5976 | adempiere | 23503> ERROR:  insert or update on table "m_receiptschedule_alloc" violates foreign key constraint "mreceiptschedule_mreceiptsched"
	 * <2015-06-24 17:19:54 CEST | 5976 | adempiere | 23503> DETAIL:  Key (m_receiptschedule_id)=(1075806) is not present in table "m_receiptschedule".
	 * </pre>
	 */
	private static final String PG_SQLSTATE_unique_violation = "23505";

	private static final String PG_SQLSTATE_deadlock_detected = "40P01";
	private static final String PG_SQLSTATE_query_canceled = "57014";
	// private static final String PG_SQLSTATE_ = "";

	private String m_sql = null;
	private Object[] m_params = null;

	/**
	 * Create a new DBException based on a SQLException
	 *
	 * @param e Specicy the Exception cause
	 */
	public DBException(final Throwable e)
	{
		super(e);
		if (LogManager.isLevelFinest())
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create a new DBException based on a SQLException and SQL Query
	 *
	 * @param e exception
	 * @param sql sql query
	 */
	public DBException(final Exception e, final String sql)
	{
		this(e, sql, (Object[])null);
	}

	public DBException(final String msg, final Throwable cause)
	{
		super(msg, cause);
	}

	/**
	 * Create a new DBException based on a SQLException and SQL Query
	 *
	 * @param e exception
	 * @param sql sql query
	 * @param sqlParams sql parameters
	 */
	public DBException(final Exception e, final String sql, final Object[] sqlParams)
	{
		this(e);
		m_sql = sql;
		if (sqlParams != null)
		{
			m_params = Arrays.copyOf(sqlParams, sqlParams.length);
		}
	}

	/**
	 * Create a new DBException based on a SQLException and SQL Query
	 *
	 * @param e exception
	 * @param sql sql query
	 * @param sqlParams sql parameters
	 */
	public DBException(final Exception e, final String sql, final List<Object> sqlParams)
	{
		this(e);
		m_sql = sql;
		if (sqlParams != null)
		{
			m_params = sqlParams.toArray();
		}
	}

	/**
	 * Create a new DBException
	 *
	 * @param msg Message
	 */
	public DBException(final String msg)
	{
		super(msg);
	}

	/**
	 * @return SQL Query or null
	 */
	public String getSQL()
	{
		return m_sql;
	}

	public DBException setSqlIfAbsent(final String sql, final List<Object> sqlParams)
	{
		if (m_sql == null)
		{
			m_sql = sql;
			m_params = sqlParams == null ? null : sqlParams.toArray();
		}
		return this;
	}

	public DBException setSqlIfAbsent(final String sql, final Object[] sqlParams)
	{
		if (m_sql == null)
		{
			m_sql = sql;
			m_params = sqlParams;

			resetMessageBuilt();
		}
		return this;
	}

	/**
	 * @return Wrapped SQLException or null
	 */
	public SQLException getSQLException()
	{
		final Throwable cause = getCause();
		if (cause instanceof SQLException)
		{
			return (SQLException)cause;
		}
		return null;
	}

	/**
	 * @see java.sql.SQLException#getErrorCode()
	 */
	public int getErrorCode()
	{
		final SQLException e = getSQLException();
		return e != null ? e.getErrorCode() : -1;
	}

	/**
	 * @see java.sql.SQLException#getNextException()
	 */
	public SQLException getNextException()
	{
		final SQLException e = getSQLException();
		return e != null ? e.getNextException() : null;
	}

	/**
	 * @see java.sql.SQLException#getSQLState()
	 */
	public String getSQLState()
	{
		final SQLException e = getSQLException();
		return e != null ? e.getSQLState() : null;
	}

	/**
	 * Return SQL query parameters
	 *
	 * @return
	 */
	public Object[] getSQLParams()
	{
		return m_params;
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder sb = new StringBuilder();
		final String msg = super.buildMessage();
		if (!Check.isEmpty(msg))
		{
			sb.append(msg);
		}

		if (!Check.isEmpty(m_sql))
		{
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append("\tSQL: ").append(m_sql);
			if (m_params != null && m_params.length > 0)
			{
				sb.append("\n\tSQL params: ").append(Arrays.toString(m_params));
			}
		}
		return sb.toString();
	}

	private static final boolean isErrorCode(final Throwable e, final int errorCode)
	{
		if (e == null)
		{
			return false;
		}
		else if (e instanceof SQLException)
		{
			return ((SQLException)e).getErrorCode() == errorCode;
		}
		else if (e instanceof DBException)
		{
			final SQLException sqlEx = ((DBException)e).getSQLException();
			if (sqlEx != null)
			{
				return sqlEx.getErrorCode() == errorCode;
			}
			else
			{
				return false;
			}
		}
		return false;
	}

	/**
	 * @return true if exception contains and SQLState which equals to given one
	 */
	public static final boolean isSQLState(final Throwable e, final String sqlState)
	{
		final String exceptionSQLState = extractSQLStateOrNull(e);
		return Check.equals(exceptionSQLState, sqlState);
	}

	/**
	 * Try to get the {@link SQLException} from the given {@link Throwable}.
	 *
	 * @param Throwable t
	 * @return {@link SQLException} if found or provided exception elsewhere
	 */
	public static final SQLException extractSQLExceptionOrNull(final Throwable t)
	{
		if (t == null)
		{
			return null;
		}

		final Throwable cause = extractCause(t);

		if (cause instanceof SQLException)
		{
			return (SQLException)t;
		}
		else if (cause instanceof DBException)
		{
			final SQLException sqlEx = ((DBException)t).getSQLException();
			if (sqlEx != null)
			{
				return sqlEx;
			}
			else
			{
				return null;
			}
		}
		return null;
	}

	private static final String extractSQLStateOrNull(final Throwable t)
	{
		final SQLException sqlException = extractSQLExceptionOrNull(t);
		if (sqlException == null)
		{
			return null;
		}
		return sqlException.getSQLState();
	}

	/**
	 * Check if Unique Constraint Exception (aka ORA-00001)
	 *
	 * @param e exception
	 */
	public static boolean isUniqueContraintError(final Throwable e)
	{
		if (DB.isPostgreSQL())
		{
			return isSQLState(e, PG_SQLSTATE_unique_violation);
		}
		//
		return isErrorCode(e, 1);
	}

	/**
	 * Check if "integrity constraint (string.string) violated - child record found" exception (aka ORA-02292)
	 *
	 * @param e exception
	 * @see http://ora-02292.ora-code.com/
	 */
	public static boolean isChildRecordFoundError(final Exception e)
	{
		if (DB.isPostgreSQL())
		{
			return isSQLState(e, PG_SQLSTATE_foreign_key_violation);
		}
		return isErrorCode(e, 2292);
	}

	public static boolean isForeignKeyViolation(final Throwable e)
	{
		if (DB.isPostgreSQL())
		{
			return isSQLState(e, PG_SQLSTATE_foreign_key_violation);
		}

		// NOTE: on oracle there are many ORA-codes for foreign key violation
		// below i am adding a few of them, but pls note that it's not tested at all
		return isErrorCode(e, 2291) // integrity constraint (string.string) violated - parent key not found
				|| isErrorCode(e, 2292) // integrity constraint (string.string) violated - child record found
		//
		;
	}

	/**
	 * Check if "invalid identifier" exception (aka ORA-00904)
	 *
	 * @param e exception
	 */
	public static boolean isInvalidIdentifierError(final Exception e)
	{
		return isErrorCode(e, 904);
	}

	/**
	 * Check if "invalid username/password" exception (aka ORA-01017)
	 *
	 * @param e exception
	 */
	public static boolean isInvalidUserPassError(final Exception e)
	{
		return isErrorCode(e, 1017);
	}

	/**
	 * Check if "time out" exception (aka ORA-01013)
	 *
	 * @param e
	 */
	public static boolean isTimeout(final Exception e)
	{
		if (DB.isPostgreSQL())
		{
			return isSQLState(e, PG_SQLSTATE_query_canceled);
		}
		return isErrorCode(e, 1013);
	}

	/**
	 *
	 * @task 08353
	 */
	public static boolean isDeadLockDetected(final Throwable e)
	{
		if (DB.isPostgreSQL())
		{
			return isSQLState(e, PG_SQLSTATE_deadlock_detected);
		}
		return isErrorCode(e, 40001); // not tested for oracle, just did a brief googling
	}

}	// DBException
