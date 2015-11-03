/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.adempiere.exceptions;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.util.CLogMgt;
import org.compiere.util.DB;

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

		return new DBException(throwable);
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
	public DBException(Throwable e)
	{
		super(e);
		if (CLogMgt.isLevelFinest())
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
	public DBException(Exception e, String sql)
	{
		this(e, sql, (Object[])null);
	}

	/**
	 * Create a new DBException based on a SQLException and SQL Query
	 * 
	 * @param e exception
	 * @param sql sql query
	 * @param params sql parameters
	 */
	public DBException(Exception e, String sql, Object[] params)
	{
		this(e);
		m_sql = sql;
		if (params != null)
			m_params = Arrays.copyOf(params, params.length);
	}

	/**
	 * Create a new DBException based on a SQLException and SQL Query
	 * 
	 * @param e exception
	 * @param sql sql query
	 * @param params sql parameters
	 */
	public DBException(Exception e, String sql, List<Object> params)
	{
		this(e);
		m_sql = sql;
		if (params != null)
			m_params = params.toArray();
	}

	/**
	 * Create a new DBException
	 * 
	 * @param msg Message
	 */
	public DBException(String msg)
	{
		super(msg);
	}

	public DBException(final String msg, final Throwable cause)
	{
		super(msg, cause);
	}

	/**
	 * @return SQL Query or null
	 */
	public String getSQL()
	{
		return m_sql;
	}

	/**
	 * @return Wrapped SQLException or null
	 */
	public SQLException getSQLException()
	{
		Throwable cause = getCause();
		if (cause instanceof SQLException)
			return (SQLException)cause;
		return null;
	}

	/**
	 * @see java.sql.SQLException#getErrorCode()
	 */
	public int getErrorCode()
	{
		SQLException e = getSQLException();
		return (e != null ? e.getErrorCode() : -1);
	}

	/**
	 * @see java.sql.SQLException#getNextException()
	 */
	public SQLException getNextException()
	{
		SQLException e = getSQLException();
		return (e != null ? e.getNextException() : null);
	}

	/**
	 * @see java.sql.SQLException#getSQLState()
	 */
	public String getSQLState()
	{
		SQLException e = getSQLException();
		return (e != null ? e.getSQLState() : null);
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
		final StringBuffer sb = new StringBuffer();
		String msg = super.buildMessage();
		if (!Check.isEmpty(msg))
			sb.append(msg);

		if (!Check.isEmpty(m_sql))
		{
			if (sb.length() > 0)
				sb.append(", ");
			sb.append("SQL=").append(m_sql);
			if (m_params != null)
				sb.append(" -- ").append(Arrays.toString(m_params));
		}
		return sb.toString();
	}

	private static final boolean isErrorCode(Throwable e, int errorCode)
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
			SQLException sqlEx = ((DBException)e).getSQLException();
			if (sqlEx != null)
				return sqlEx.getErrorCode() == errorCode;
			else
				return false;
		}
		return false;
	}

	/**
	 * @return true if exception contains and SQLState which equals to given one
	 */
	private static final boolean isSQLState(final Throwable e, final String sqlState)
	{
		final String exceptionSQLState = extractSQLStateOrNull(e);
		return Check.equals(exceptionSQLState, sqlState);
	}

	private static final String extractSQLStateOrNull(final Throwable e)
	{
		if (e == null)
		{
			return null;
		}
		else if (e instanceof SQLException)
		{
			return ((SQLException)e).getSQLState();
		}
		else if (e instanceof DBException)
		{
			final SQLException sqlEx = ((DBException)e).getSQLException();
			if (sqlEx != null)
			{
				return sqlEx.getSQLState();
			}
			else
			{
				return null;
			}
		}

		return null;
	}

	/**
	 * Check if Unique Constraint Exception (aka ORA-00001)
	 * 
	 * @param e exception
	 */
	public static boolean isUniqueContraintError(Throwable e)
	{
		if (DB.isPostgreSQL())
			return isSQLState(e, PG_SQLSTATE_unique_violation);
		//
		return isErrorCode(e, 1);
	}

	/**
	 * Check if "integrity constraint (string.string) violated - child record found" exception (aka ORA-02292)
	 * 
	 * @param e exception
	 * @see http://ora-02292.ora-code.com/
	 */
	public static boolean isChildRecordFoundError(Exception e)
	{
		if (DB.isPostgreSQL())
			return isSQLState(e, PG_SQLSTATE_foreign_key_violation);
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
	public static boolean isInvalidIdentifierError(Exception e)
	{
		return isErrorCode(e, 904);
	}

	/**
	 * Check if "invalid username/password" exception (aka ORA-01017)
	 * 
	 * @param e exception
	 */
	public static boolean isInvalidUserPassError(Exception e)
	{
		return isErrorCode(e, 1017);
	}

	/**
	 * Check if "time out" exception (aka ORA-01013)
	 * 
	 * @param e
	 */
	public static boolean isTimeout(Exception e)
	{
		if (DB.isPostgreSQL())
			return isSQLState(e, PG_SQLSTATE_query_canceled);
		return isErrorCode(e, 1013);
	}

	/**
	 * 
	 * @task 08353
	 */
	public static boolean isDeadLockDetected(Throwable e)
	{
		if (DB.isPostgreSQL())
		{
			return isSQLState(e, PG_SQLSTATE_deadlock_detected);
		}
		return isErrorCode(e, 40001); // not tested for oracle, just did a brief googling
	}

	/**
	 * Try to get the {@link SQLException} from Exception
	 * 
	 * @param e Exception
	 * @return {@link SQLException} if found or provided exception elsewhere
	 */
	public static Exception getSQLException(final Exception e)
	{
		Throwable e1 = e;
		while (e1 != null)
		{
			if (e1 instanceof SQLException)
				return (SQLException)e1;
			e1 = e1.getCause();
		}
		return e;
	}
}	// DBException
