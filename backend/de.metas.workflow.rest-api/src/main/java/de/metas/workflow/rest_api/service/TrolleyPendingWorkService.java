package de.metas.workflow.rest_api.service;

import de.metas.user.UserId;
import de.metas.workflow.rest_api.controller.v2.json.JsonTrolleyPendingWorkContribution;
import de.metas.workflow.rest_api.controller.v2.json.JsonTrolleyPendingWorkResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrolleyPendingWorkService
{
	@NonNull private final List<TrolleyPendingWorkProvider> providers;

	public JsonTrolleyPendingWorkResponse summarize(@NonNull final UserId userId)
	{
		final List<JsonTrolleyPendingWorkContribution> items = new ArrayList<>(providers.size());
		for (final TrolleyPendingWorkProvider provider : providers)
		{
			items.add(provider.getPendingWork(userId));
		}
		return JsonTrolleyPendingWorkResponse.aggregate(items);
	}
}
