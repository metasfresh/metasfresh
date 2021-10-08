package de.metas.workflow.rest_api.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.SynchronizedMutable;

import java.util.HashMap;
import java.util.function.UnaryOperator;

public final class WFProcessesIndex
{
	private final Object mutex = new Object();
	private final HashMap<WFProcessId, SynchronizedMutable<WFProcess>> byId = new HashMap<>();
	private final ArrayListMultimap<UserId, WFProcess> byInvokerId = ArrayListMultimap.create();

	public void add(@NonNull final WFProcess wfProcess)
	{
		final WFProcessId wfProcessId = wfProcess.getId();

		synchronized (mutex)
		{
			if (byId.containsKey(wfProcessId))
			{
				throw new AdempiereException("An WFProcess with " + wfProcessId + " already exists");
			}

			byId.putIfAbsent(wfProcessId, SynchronizedMutable.of(wfProcess));
			byInvokerId.put(wfProcess.getInvokerId(), wfProcess);
		}
	}

	public void remove(@NonNull final WFProcess wfProcess)
	{
		synchronized (mutex)
		{
			byId.remove(wfProcess.getId());
			byInvokerId.remove(wfProcess.getInvokerId(), wfProcess);
		}
	}

	/**
	 * @return new value
	 */
	public WFProcess compute(@NonNull final WFProcessId wfProcessId, UnaryOperator<WFProcess> remappingFunction)
	{
		final SynchronizedMutable<WFProcess> wfProcessHolder = byId.get(wfProcessId);
		if (wfProcessHolder == null)
		{
			throw new AdempiereException("No WFProcess found for " + wfProcessId);
		}

		final SynchronizedMutable.OldAndNewValues<WFProcess> remappingResult = wfProcessHolder.computeReturningOldAndNew(remappingFunction);
		if (!remappingResult.isSameValue())
		{
			final WFProcess wfProcessNew = remappingResult.getNewValue();
			synchronized (mutex)
			{
				byInvokerId.put(wfProcessNew.getInvokerId(), wfProcessNew);
			}
			return wfProcessNew;
		}
		else
		{
			return remappingResult.getOldValue();
		}
	}

	public WFProcess getById(@NonNull final WFProcessId wfProcessId)
	{
		final WFProcess wfProcess;
		synchronized (mutex)
		{
			final SynchronizedMutable<WFProcess> wfProcessHolder = byId.get(wfProcessId);
			wfProcess = wfProcessHolder != null ? wfProcessHolder.getValue() : null;
		}

		if (wfProcess == null)
		{
			throw new AdempiereException("No WFProcess found for " + wfProcessId);
		}
		return wfProcess;
	}

	public ImmutableList<WFProcess> getByInvokerId(@NonNull final UserId invokerId)
	{
		synchronized (mutex)
		{
			return ImmutableList.copyOf(byInvokerId.get(invokerId));
		}
	}
}
