package de.metas.lock.exceptions;

import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockCommand;

/**
 * Exception thrown when a lock changing failed.
 * 
 * @author tsa
 *
 */
public class LockChangeFailedException extends LockException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6916683766355333023L;

	public LockChangeFailedException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	@Override
	public LockChangeFailedException setParameter(@NonNull String name, Object value)
	{
		super.setParameter(name, value);
		return this;
	}

	@Override
	public LockChangeFailedException setRecord(final @NonNull TableRecordReference record)
	{
		super.setRecord(record);
		return this;
	}

	@Override
	public LockChangeFailedException setSql(String sql, Object[] sqlParams)
	{
		super.setSql(sql, sqlParams);
		return this;
	}

	public LockChangeFailedException setLock(final ILock lock)
	{
		return setParameter("Lock", lock);
	}

	public LockChangeFailedException setLockCommand(final ILockCommand lockCommand)
	{
		setParameter("Lock command", lockCommand);
		return this;
	}
}
