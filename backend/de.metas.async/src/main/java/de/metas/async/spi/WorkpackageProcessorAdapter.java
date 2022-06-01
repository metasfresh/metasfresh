package de.metas.async.spi;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.lock.exceptions.LockFailedException;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.api.IParams;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implement what you want adapter for {@link IWorkpackageProcessor2}.
 */
public abstract class WorkpackageProcessorAdapter implements IWorkpackageProcessor2
{
	private IParams parameters = null;
	private I_C_Queue_WorkPackage workpackage;

	@Override
	public final void setParameters(final IParams parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @return workpackage parameters; never return <code>null</code>
	 */
	protected final IParams getParameters()
	{
		return parameters == null ? IParams.NULL : parameters;
	}

	@Override
	public final void setC_Queue_WorkPackage(I_C_Queue_WorkPackage workpackage)
	{
		this.workpackage = workpackage;
	}

	@NonNull
	protected final I_C_Queue_WorkPackage getC_Queue_WorkPackage()
	{
		Check.assumeNotNull(workpackage, "workpackage not null");
		return this.workpackage;
	}

	/**
	 * @return <code>true</code>, i.e. ask the executor to run this processor in transaction (backward compatibility)
	 */
	@Override
	public boolean isRunInTransaction()
	{
		return true;
	}

	@Override
	public boolean isAllowRetryOnError()
	{
		return true;
	}

	@Override
	public final Optional<ILock> getElementsLock()
	{
		final String elementsLockOwnerName = getParameters().getParameterAsString(PARAMETERNAME_ElementsLockOwner);
		if (Check.isEmpty(elementsLockOwnerName, true))
		{
			return Optional.empty(); // no lock was created for this workpackage
		}

		final LockOwner elementsLockOwner = LockOwner.forOwnerName(elementsLockOwnerName);
		try
		{
			final ILock existingLockForOwner = Services.get(ILockManager.class).getExistingLockForOwner(elementsLockOwner);
			return Optional.of(existingLockForOwner);
		}
		catch (final LockFailedException e)
		{
			// this can happen, if e.g. there was a restart, or if the WP was flaged as error once
			Loggables.addLog("Missing lock for ownerName={}; was probably cleaned up meanwhile", elementsLockOwnerName);
			return Optional.empty();
		}

	}

	/**
	 * Returns the {@link NullLatchStrategy}.
	 */
	@Override
	public ILatchStragegy getLatchStrategy()
	{
		return NullLatchStrategy.INSTANCE;
	}

	public final <T> List<T> retrieveItems(final Class<T> modelType)
	{
		return Services.get(IQueueDAO.class).retrieveAllItemsSkipMissing(getC_Queue_WorkPackage(), modelType);
	}

	/**
	 * Retrieves all active POs, even the ones that are caught in other packages
	 */
	public final <T> List<T> retrieveAllItems(final Class<T> modelType)
	{
		return Services.get(IQueueDAO.class).retrieveAllItems(getC_Queue_WorkPackage(), modelType);
	}

	public final List<I_C_Queue_Element> retrieveQueueElements(final boolean skipAlreadyScheduledItems)
	{
		return Services.get(IQueueDAO.class).retrieveQueueElements(getC_Queue_WorkPackage(), skipAlreadyScheduledItems);
	}

	/**
	 * retrieves all active PO's IDs, even the ones that are caught in other packages
	 */
	public final Set<Integer> retrieveAllItemIds()
	{
		return Services.get(IQueueDAO.class).retrieveAllItemIds(getC_Queue_WorkPackage());
	}
}
