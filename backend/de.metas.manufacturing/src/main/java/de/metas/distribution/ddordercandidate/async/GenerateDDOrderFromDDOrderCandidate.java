package de.metas.distribution.ddordercandidate.async;

import ch.qos.logback.classic.Level;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateProcessRequest;
import de.metas.distribution.ddordercandidate.DDOrderCandidateService;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GenerateDDOrderFromDDOrderCandidate extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(GenerateDDOrderFromDDOrderCandidate.class);

	@NonNull
	private final DDOrderCandidateService ddOrderCandidateService = SpringContextHolder.instance.getBean(DDOrderCandidateService.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
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
		final PInstanceId selectionId = enqueueRequest.getSelectionId();

		final List<DDOrderCandidate> candidates = ddOrderCandidateService.getBySelectionId(selectionId);

		logCandidatesToProcess(candidates, selectionId);

		return DDOrderCandidateProcessRequest.builder()
				.candidates(candidates)
				.build();
	}

	/**
	 * There is a weird problem when running this remotely via a cucumber-test..
	 */
	private static void logCandidatesToProcess(@NonNull final List<DDOrderCandidate> candidates, @NonNull final PInstanceId selectionId)
	{
		final List<String> ids = new ArrayList<>();
		for (int i = 0; i < candidates.size() && i < 10; i++)
		{
			ids.add(Integer.toString(candidates.get(i).getIdNotNull().getRepoId()));
		}

		Loggables.withLogger(logger, Level.INFO)
				.addLog("Retrieved {} DDOrderCandidates for SelectionId={}; The first (max 10) selection IDs are: {}",
						candidates.size(), selectionId, String.join(",", ids));
	}
}
