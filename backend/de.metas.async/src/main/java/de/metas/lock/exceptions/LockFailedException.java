package de.metas.lock.exceptions;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.lock.api.ILockCommand;

/**
 * Exception thrown when a lock aquiring failed.
 * 
 * @author tsa
 *
 */
public class LockFailedException extends LockException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -343950791566871398L;

	public static final LockFailedException wrapIfNeeded(final Exception e)
	{
		if (e instanceof LockFailedException)
		{
			return (LockFailedException)e;
		}
		else
		{
			return new LockFailedException("Lock failed: " + e.getLocalizedMessage(), e);
		}
	}

	public LockFailedException(final String message)
	{
		super(message);
	}

	public LockFailedException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public LockFailedException setLockCommand(final ILockCommand lockCommand)
	{
		setParameter("Lock command", lockCommand);
		return this;
	}

	@Override
	public LockFailedException setSql(final String sql, final Object[] sqlParams)
	{
		super.setSql(sql, sqlParams);
		return this;
	}

	public LockFailedException setRecordToLock(final TableRecordReference recordToLock)
	{
		super.setRecord(recordToLock);
		return this;
	}
}
