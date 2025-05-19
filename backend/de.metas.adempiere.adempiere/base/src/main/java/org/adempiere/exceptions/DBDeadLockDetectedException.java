/**
 * 
 */
package org.adempiere.exceptions;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.io.Serial;
import java.sql.Connection;

/**
 * Dedicated exception to handle the case that the DB detected a deadlock and killed one of the participants.
 * 
 * @author ts
 * @task 08353
 */
public class DBDeadLockDetectedException extends DBException
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -1436774241410586947L;

	private String ownBackEndId;

	/**
	 * 
	 * @param e the "orginal" exception from which we know that it is a deadlock. Note that this is usually a SQLException.
	 * @param connection the connection that was used. may be <code>null</code>.
	 */
	public DBDeadLockDetectedException(final Throwable e, @Nullable final Connection connection)
	{
		super(e);
		setDeadLockInfo(connection);
	}

	private void setDeadLockInfo(@Nullable final Connection connection)
	{
		if (DB.isPostgreSQL() && connection != null)
		{
			final boolean throwDBException = false; // in case of e.g. a closed connection or other problems, don't make a fuss and throw further exceptions
			ownBackEndId = DB.getDatabase().getConnectionBackendId(connection, throwDBException);
		}
		else
		{
			// FIXME implement for Oracle
		}

		resetMessageBuilt();
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		// NOTE: completely override super's message because it's not relevant for our case

		final TranslatableStringBuilder message = TranslatableStrings.builder();
		message.append("Own Backend/Process ID =");
		message.append(ownBackEndId);
		message.append("; ExceptionMessage: ");
		message.append(getOriginalMessage());

		return message.build();
	}
}
