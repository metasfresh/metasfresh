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
import de.metas.i18n.AdMessageKey;
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

public abstract class InvokeActivateExternalConfig extends JavaProcess implements IProcessPrecondition
{
	private final static AdMessageKey MSG_ERR_MULTIPLE_EXTERNAL_SELECTION = AdMessageKey.of("MultipleExternalSelection");
	private final static AdMessageKey MSG_ERR_EXTERNAL_SYSTEM_CONFIG_ACTIVE = AdMessageKey.of("MSG_ERR_ExternalSystemConfigActive");

	protected final ExternalSystemConfigRepo externalSystemConfigRepo = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);
	protected final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@Override
	protected String doIt()
	{
		final SchedulerEventBusService schedulerEventBusService = SpringContextHolder.instance.getBean(SchedulerEventBusService.class);

		activateRecord();

		final AdProcessId targetProcessId = adProcessDAO.retrieveProcessIdByClassIfUnique(getExternalSystemType().getExternalSystemProcessClassName());

		Check.assumeNotNull(targetProcessId, "There should always be an AD_Process record for classname:" + getExternalSystemType().getExternalSystemProcessClassName());

		schedulerEventBusService.postRequest(ManageSchedulerRequest.builder()
													 .schedulerSearchKey(SchedulerSearchKey.of(targetProcessId))
													 .clientId(Env.getClientId())
													 .schedulerAction(SchedulerAction.ENABLE)
													 .supervisorAction(ManageSchedulerRequest.SupervisorAction.ENABLE)
													 .build());
		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final long selectedRecordsCount = getSelectedRecordCount(context);
		if (selectedRecordsCount > 1)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_ERR_MULTIPLE_EXTERNAL_SELECTION, getExternalSystemType().getName()));
		}
		else if (selectedRecordsCount == 0)
		{
			final Optional<ExternalSystemParentConfig> config = getSelectedExternalSystemConfig(ExternalSystemParentConfigId.ofRepoId(context.getSingleSelectedRecordId()));

			if (!config.isPresent())
			{
				return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_ERR_NO_EXTERNAL_SELECTION, getExternalSystemType().getName()));
			}

			if (config.get().isActive())
			{
				return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_ERR_EXTERNAL_SYSTEM_CONFIG_ACTIVE));
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	@NonNull
	protected Optional<ExternalSystemParentConfig> getSelectedExternalSystemConfig(@NonNull final ExternalSystemParentConfigId externalSystemParentConfigId)
	{
		final ExternalSystemConfigQuery query = ExternalSystemConfigQuery.builder()
				.parentConfigId(externalSystemParentConfigId)
				.build();

		return externalSystemConfigRepo.getByQuery(getExternalSystemType(), query);
	}

	protected abstract void activateRecord();

	protected abstract ExternalSystemType getExternalSystemType();

	protected abstract long getSelectedRecordCount(final IProcessPreconditionsContext context);
}
