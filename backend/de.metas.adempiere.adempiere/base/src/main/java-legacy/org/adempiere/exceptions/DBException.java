package org.adempiere.exceptions;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.exceptions.IExceptionWrapper;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This RuntimeException is used to pass SQLException up the chain of calling methods to determine what to do where needed.
 *
 * @author Vincent Harcq
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * @author Armen Rizal, GOODWILL CONSULTING FR [2789943] Better DBException handling for PostgreSQL https://sourceforge.net/tracker/?func=detail&aid=2789943&group_id=176962&atid=879335
 * @version $Id: DBException.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
@SuppressWarnings("serial")
public class DBException extends AdempiereException
{
	/**
	 * Wraps given throwable to {@link DBException} if is not already an {@link DBException}.
	 *
	 * @return {@link DBException} or null if throwable is null
	 */
	@Nullable
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

	private static final List<IExceptionWrapper<DBException>> exceptionWrappers = new ArrayList<>();

	public static void registerExceptionWrapper(final IExceptionWrapper<DBException> wrapper)
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

	@Nullable
	private String m_sql = null;
	@Nullable
	private Object[] m_params = null;

	public DBException(final Throwable e)
	{
		super(extractMessage(e), e);
		if (LogManager.isLevelFinest())
		{
			e.printStackTrace();
		}
	}

	public DBException(final Exception e, final CharSequence sql)
	{
		this(e, sql, (Object[])null);
	}

	public DBException(final String msg, final Throwable cause)
	{
		super(msg, cause);
	}

	public DBException(final Exception e, final CharSequence sql, @Nullable final Object... sqlParams)
	{
		this(e);
		m_sql = sql != null ? sql.toString() : null;
		if (sqlParams != null)
		{
			m_params = Arrays.copyOf(sqlParams, sqlParams.length);
		}
	}

	public DBException(final Exception e, final CharSequence sql, final List<Object> sqlParams)
	{
		this(e);
		m_sql = sql != null ? sql.toString() : null;
		if (sqlParams != null)
		{
			m_params = sqlParams.toArray();
		}
	}

	public DBException(final String msg)
	{
		super(msg);
	}

	@Nullable
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
	@Nullable
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
	@Nullable
	public SQLException getNextException()
	{
		final SQLException e = getSQLException();
		return e != null ? e.getNextException() : null;
	}

	/**
	 * @see java.sql.SQLException#getSQLState()
	 */
	@Nullable
	public String getSQLState()
	{
		final SQLException e = getSQLException();
		return e != null ? e.getSQLState() : null;
	}

	@Nullable
	public Object[] getSQLParams()
	{
		return m_params;
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();

		message.append(super.buildMessage());

		if (!Check.isEmpty(m_sql))
		{
			message.append("\n\tSQL: ").append(m_sql);

			if (m_params != null && m_params.length > 0)
			{
				message.append("\n\tSQL params: ").append(Arrays.toString(m_params));
			}
		}

		return message.build();
	}

	private static boolean isErrorCode(final Throwable e, final int errorCode)
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
	public static boolean isSQLState(final Throwable e, final String sqlState)
	{
		final String exceptionSQLState = extractSQLStateOrNull(e);
		return Objects.equals(exceptionSQLState, sqlState);
	}

	/**
	 * Try to get the {@link SQLException} from the given {@link Throwable}.
	 *
	 * @return {@link SQLException} if found or provided exception elsewhere
	 */
	@Nullable
	public static SQLException extractSQLExceptionOrNull(final Throwable t)
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
			return ((DBException)t).getSQLException();
		}
		return null;
	}

	@Nullable
	private static String extractSQLStateOrNull(final Throwable t)
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
	 * Task 08353
	 */
	public static boolean isDeadLockDetected(final Throwable e)
	{
		if (DB.isPostgreSQL())
		{
			return isSQLState(e, PG_SQLSTATE_deadlock_detected);
		}
		return isErrorCode(e, 40001); // not tested for oracle, just did a brief googling
	}

}    // DBException
