package de.metas.workflow.rest_api.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;

public final class WFProcessesIndex
{
	private final HashMap<WFProcessId, WFProcess> byId = new HashMap<>();
	private final ArrayListMultimap<UserId, WFProcess> byInvokerId = ArrayListMultimap.create();

	public synchronized void add(@NonNull final WFProcess wfProcess)
	{
		byId.put(wfProcess.getId(), wfProcess);
		byInvokerId.put(wfProcess.getInvokerId(), wfProcess);
	}

	public synchronized void remove(@NonNull final WFProcess wfProcess)
	{
		byId.remove(wfProcess.getId());
		byInvokerId.remove(wfProcess.getInvokerId(), wfProcess);
	}

	public synchronized WFProcess getById(@NonNull final WFProcessId wfProcessId)
	{
		final WFProcess wfProcess = byId.get(wfProcessId);
		if (wfProcess == null)
		{
			throw new AdempiereException("No WFProcess found for " + wfProcessId);
		}
		return wfProcess;
	}

	public synchronized ImmutableList<WFProcess> getByInvokerId(@NonNull final UserId invokerId)
	{
		return ImmutableList.copyOf(byInvokerId.get(invokerId));
	}
}
