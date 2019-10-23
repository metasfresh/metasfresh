package de.metas.adempiere.service;

import de.metas.adempiere.util.GlobalLock;
import de.metas.util.ISingletonService;

/**
 * Global Locking System.
 * Used to synchronize ADempiere processes across multiple instances.
 * @author tsa
 * 
 */
@Deprecated
public interface IGlobalLockSystem extends ISingletonService
{

	public static final long TIMEOUT_NEVER = -1;

	public GlobalLock createLock(String type, int id);

	public boolean isLocked(GlobalLock lock);

	boolean acquire(GlobalLock lock, long timeoutMillis) throws InterruptedException;

	void release(GlobalLock lock);
}
