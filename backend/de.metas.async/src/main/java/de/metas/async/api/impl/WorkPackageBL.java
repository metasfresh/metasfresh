package de.metas.async.api.impl;

import de.metas.async.api.IWorkPackageBL;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.impl.QueueProcessorDescriptorIndex;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;

import java.util.Optional;

public class WorkPackageBL implements IWorkPackageBL
{
	private final QueueProcessorDescriptorIndex queueProcessorDescriptorIndex = QueueProcessorDescriptorIndex.getInstance();

	@Override
	public Optional<UserId> getUserIdInCharge(@NonNull final I_C_Queue_WorkPackage workPackage)
	{
		if (workPackage.getAD_User_InCharge_ID() > 0)
		{
			return UserId.optionalOfRepoId(workPackage.getAD_User_InCharge_ID());
		}

		final QueuePackageProcessorId queuePackageProcessorId = QueuePackageProcessorId.ofRepoId(workPackage.getC_Queue_PackageProcessor_ID());
		final I_C_Queue_PackageProcessor packageProcessor = queueProcessorDescriptorIndex.getPackageProcessor(queuePackageProcessorId);
		Check.assumeNotNull(packageProcessor, "C_Queue_PackageProcessor is not null for 'workPackage'={}", workPackage);

		if (Check.isEmpty(packageProcessor.getInternalName()))
		{
			return Optional.empty();
		}

		final String internalName = packageProcessor.getInternalName();

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final int userInChargeId = sysConfigBL.getIntValue("de.metas.async.api." + internalName + ".AD_User_InCharge_ID", -1,
				workPackage.getAD_Client_ID(),
				workPackage.getAD_Org_ID());

		return UserId.optionalOfRepoId(userInChargeId);
	}
}
