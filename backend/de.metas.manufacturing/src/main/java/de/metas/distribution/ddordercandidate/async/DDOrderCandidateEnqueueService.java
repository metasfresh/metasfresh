package de.metas.distribution.ddordercandidate.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order_Candidate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DDOrderCandidateEnqueueService
{
	private final ILockManager lockManager = Services.get(ILockManager.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final DDOrderCandidateRepository ddOrderCandidateRepository;

	private static final String WP_PARAM_request = "request";
	private static final String LOCK_OWNER_PREFIX = "DDOrderCandidateEnqueueService";

	public void enqueueId(@NonNull final DDOrderCandidateId id)
	{
		enqueueIds(ImmutableSet.of(id));
	}

	public void enqueueIds(@NonNull final Collection<DDOrderCandidateId> ids)
	{
		if (ids.isEmpty())
		{
			return;
		}

		final PInstanceId selectionId = ddOrderCandidateRepository.createSelection(ids);
		enqueueSelection(DDOrderCandidateEnqueueRequest.ofSelectionId(selectionId));
	}

	public void enqueueSelection(@NonNull final PInstanceId selectionId)
	{
		enqueueSelection(DDOrderCandidateEnqueueRequest.ofSelectionId(selectionId));
	}

	public void enqueueSelection(@NonNull final DDOrderCandidateEnqueueRequest request)
	{
		getQueue().newWorkPackage()
				.parameter(WP_PARAM_request, toJsonString(request))
				.setElementsLocker(toWorkPackageElementsLocker(request))
				.bindToThreadInheritedTrx()
				.buildAndEnqueue();
	}

	private IWorkPackageQueue getQueue()
	{
		return workPackageQueueFactory.getQueueForEnqueuing(Env.getCtx(), GenerateDDOrderFromDDOrderCandidate.class);
	}

	private ILockCommand toWorkPackageElementsLocker(final @NonNull DDOrderCandidateEnqueueRequest request)
	{
		final PInstanceId selectionId = request.getSelectionId();
		final LockOwner lockOwner = LockOwner.newOwner(LOCK_OWNER_PREFIX, selectionId.getRepoId());
		return lockManager
				.lock()
				.setOwner(lockOwner)
				.setAutoCleanup(false)
				.setFailIfAlreadyLocked(true)
				.setRecordsBySelection(I_DD_Order_Candidate.class, selectionId);
	}

	private static String toJsonString(@NonNull final DDOrderCandidateEnqueueRequest request)
	{
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(request);
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Cannot convert to json: " + request, e);
		}
	}

	public static DDOrderCandidateEnqueueRequest extractRequest(@NonNull final IParams params)
	{
		final String jsonString = params.getParameterAsString(WP_PARAM_request);
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(jsonString, DDOrderCandidateEnqueueRequest.class);
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Cannot read json: " + jsonString, e);
		}
	}

}
