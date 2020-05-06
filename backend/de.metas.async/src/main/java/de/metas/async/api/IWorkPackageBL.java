package de.metas.async.api;

import java.util.Optional;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;

public interface IWorkPackageBL extends ISingletonService
{
	Optional<UserId> getUserIdInCharge(I_C_Queue_WorkPackage workPackage);
}
