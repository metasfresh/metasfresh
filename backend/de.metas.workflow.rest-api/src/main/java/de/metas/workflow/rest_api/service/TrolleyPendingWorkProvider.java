package de.metas.workflow.rest_api.service;

import de.metas.user.UserId;
import de.metas.workflow.rest_api.controller.v2.json.JsonTrolleyPendingWorkContribution;
import lombok.NonNull;

public interface TrolleyPendingWorkProvider
{
	JsonTrolleyPendingWorkContribution getPendingWork(@NonNull UserId userId);
}
