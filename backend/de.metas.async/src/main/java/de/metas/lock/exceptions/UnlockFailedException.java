package de.metas.lock.exceptions;

import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.lock.api.IUnlockCommand;

/**
 * Exception thrown when a lock releasing failed.
 * 
 * @author tsa
 *
 */
public class UnlockFailedException extends LockException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1714489085379778609L;
	
	public static final UnlockFailedException wrapIfNeeded(final Exception e)
	{
		if (e instanceof UnlockFailedException)
		{
			return (UnlockFailedException)e;
		}
		else
		{
			return new UnlockFailedException("Unlock failed: " + e.getLocalizedMessage(), e);
		}
	}

	public UnlockFailedException(String message)
	{
		super(message);
	}

	public UnlockFailedException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UnlockFailedException setUnlockCommand(final IUnlockCommand unlockCommand)
	{
		setParameter("Unlock command", unlockCommand);
		return this;
	}

	@Override
	public UnlockFailedException setSql(final String sql, final Object[] sqlParams)
	{
		super.setSql(sql, sqlParams);
		return this;
	}

	public UnlockFailedException setRecordToLock(final TableRecordReference recordToLock)
	{
		super.setRecord(recordToLock);
		return this;
	}

	@Override
	public UnlockFailedException setParameter(@NonNull String name, Object value)
	{
		super.setParameter(name, value);
		return this;
	}
}
