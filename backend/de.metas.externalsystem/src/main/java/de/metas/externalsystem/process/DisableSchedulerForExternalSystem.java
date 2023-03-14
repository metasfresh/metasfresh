/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.externalsystem.process;

import de.metas.externalsystem.ExternalSystemConfigQuery;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.scheduler.SchedulerAction;
import de.metas.scheduler.SchedulerSearchKey;
import de.metas.scheduler.eventbus.ManageSchedulerRequest;
import de.metas.scheduler.eventbus.SchedulerEventBusService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import java.util.Optional;

import static de.metas.externalsystem.process.InvokeExternalSystemProcess.MSG_ERR_NO_EXTERNAL_SELECTION;

public class DisableSchedulerForExternalSystem extends JavaProcess implements IProcessPrecondition
{
	private final ExternalSystemConfigRepo externalSystemConfigRepo = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		else
		{
			final String externalSystemType = externalSystemConfigRepo.getParentTypeById(ExternalSystemParentConfigId.ofRepoId(context.getSingleSelectedRecordId()));

			final ExternalSystemType type = ExternalSystemType.ofCode(externalSystemType);

			final ExternalSystemConfigQuery query = ExternalSystemConfigQuery.builder()
					.parentConfigId(ExternalSystemParentConfigId.ofRepoId(context.getSingleSelectedRecordId()))
					.build();

			final Optional<ExternalSystemParentConfig> config = externalSystemConfigRepo.getByQuery(type, query);

			if (!config.isPresent())
			{
				return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_ERR_NO_EXTERNAL_SELECTION, type.getName()));
			}
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final SchedulerEventBusService schedulerEventBusService = SpringContextHolder.instance.getBean(SchedulerEventBusService.class);

		final String externalSystemType = externalSystemConfigRepo.getParentTypeById(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()));

		final ExternalSystemType type = ExternalSystemType.ofCode(externalSystemType);

		final AdProcessId targetProcessId = adProcessDAO.retrieveProcessIdByClassIfUnique(type.getExternalSystemProcessClassName());

		Check.assumeNotNull(targetProcessId, "There should always be an AD_Process record for classname:" + type.getExternalSystemProcessClassName());

		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
													 .schedulerSearchKey(SchedulerSearchKey.of(targetProcessId))
													 .clientId(Env.getClientId())
													 .schedulerAction(SchedulerAction.DISABLE)
													 .supervisorAction(ManageSchedulerRequest.SupervisorAction.DISABLE)
													 .build());
		return MSG_OK;
	}
}
