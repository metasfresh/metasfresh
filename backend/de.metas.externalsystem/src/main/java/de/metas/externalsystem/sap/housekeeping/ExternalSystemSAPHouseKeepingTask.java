/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.sap.housekeeping;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.externalservice.common.ExternalStatus;
import de.metas.externalsystem.externalservice.externalserviceinstance.ExternalSystemServiceInstance;
import de.metas.externalsystem.externalservice.externalserviceinstance.ExternalSystemServiceInstanceRepository;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceModel;
import de.metas.externalsystem.externalservice.model.ExternalSystemServiceRepository;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_CHILD_CONFIG_ID;
import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_EXTERNAL_REQUEST;

@Component
public class ExternalSystemSAPHouseKeepingTask implements IStartupHouseKeepingTask
{
	private static final Logger logger = LogManager.getLogger(ExternalSystemSAPHouseKeepingTask.class);

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final ExternalSystemConfigRepo externalSystemConfigDAO;
	private final ExternalSystemServiceInstanceRepository externalSystemServiceInstanceRepository;
	private final ExternalSystemServiceRepository externalSystemServiceRepository;

	public ExternalSystemSAPHouseKeepingTask(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigDAO,
			@NonNull final ExternalSystemServiceInstanceRepository externalSystemServiceInstanceRepository,
			@NonNull final ExternalSystemServiceRepository externalSystemServiceRepository)
	{
		this.externalSystemConfigDAO = externalSystemConfigDAO;
		this.externalSystemServiceInstanceRepository = externalSystemServiceInstanceRepository;
		this.externalSystemServiceRepository = externalSystemServiceRepository;
	}

	@Override
	public void executeTask()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClassIfUnique(ExternalSystemType.SAP.getExternalSystemProcessClassName());

		if (processId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Nothing to do!");
			return;

		}

		final ImmutableList<ExternalSystemParentConfig> parentConfigList = externalSystemConfigDAO.getActiveByType(ExternalSystemType.SAP);

		for (final ExternalSystemParentConfig config : parentConfigList)
		{
			final List<ExternalSystemServiceModel> serviceModels = externalSystemServiceRepository.getAllByType(ExternalSystemType.SAP);

			serviceModels
					.stream()
					.filter(service -> Check.isNotBlank(service.getEnableCommand()))
					.filter(service -> shouldStartExternalService(service, config.getId()))
					.forEach(service -> ProcessInfo.builder()
							.setAD_Process_ID(processId)
							.setAD_User_ID(UserId.METASFRESH.getRepoId())
							.addParameter(PARAM_EXTERNAL_REQUEST, service.getEnableCommand())
							.addParameter(PARAM_CHILD_CONFIG_ID, config.getChildConfig().getId().getRepoId())
							.buildAndPrepareExecution()
							.executeSync());
		}
	}

	private boolean shouldStartExternalService(
			@NonNull final ExternalSystemServiceModel externalService,
			@NonNull final ExternalSystemParentConfigId configId)
	{
		return externalSystemServiceInstanceRepository.getByConfigAndServiceId(configId, externalService.getId())
				.map(ExternalSystemServiceInstance::getExpectedStatus)
				.map(status -> status == ExternalStatus.ACTIVE)
				.orElse(false);
	}
}
