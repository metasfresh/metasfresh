package de.metas.distribution.ddordercandidate.async;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateProcessRequest;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.List;

public class GenerateDDOrderFromDDOrderCandidate extends WorkpackageProcessorAdapter
{
	@NonNull private final DDOrderCandidateService ddOrderCandidateService = SpringContextHolder.instance.getBean(DDOrderCandidateService.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		final DDOrderCandidateProcessRequest processRequest = getProcessRequest();
		Loggables.addLog("{}", processRequest);

		ddOrderCandidateService.process(processRequest);
		Loggables.addLog("Done.");
		return Result.SUCCESS;
	}

	private DDOrderCandidateProcessRequest getProcessRequest()
	{
		final DDOrderCandidateEnqueueRequest enqueueRequest = DDOrderCandidateEnqueueService.extractRequest(getParameters());
		final List<DDOrderCandidate> candidates = ddOrderCandidateService.getBySelectionId(enqueueRequest.getSelectionId());
		return DDOrderCandidateProcessRequest.builder()
				.candidates(candidates)
				.build();
	}
}
