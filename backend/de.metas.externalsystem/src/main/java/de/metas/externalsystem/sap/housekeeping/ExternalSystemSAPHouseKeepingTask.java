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
import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.externalservice.common.ExternalStatus;
import de.metas.externalsystem.externalservice.externalserviceinstance.ExternalSystemServiceInstanceRepository;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_CHILD_CONFIG_ID;
import static de.metas.externalsystem.process.InvokeExternalSystemProcess.PARAM_EXTERNAL_REQUEST;

@Component
public class ExternalSystemSAPHouseKeepingTask implements IStartupHouseKeepingTask
{
	private static final Logger logger = LogManager.getLogger(ExternalSystemSAPHouseKeepingTask.class);
	private static final String SAP_COMMAND = "startProductsSync";

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final ExternalSystemConfigRepo externalSystemConfigDAO;
	private final ExternalSystemServiceInstanceRepository externalSystemServiceInstanceRepository;

	public ExternalSystemSAPHouseKeepingTask(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigDAO,
			@NonNull final ExternalSystemServiceInstanceRepository externalSystemServiceInstanceRepository)
	{
		this.externalSystemConfigDAO = externalSystemConfigDAO;
		this.externalSystemServiceInstanceRepository = externalSystemServiceInstanceRepository;
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

		parentConfigList
				.stream()
				.filter(this::isExternalSystemStartupStatusActive)
				.peek(config -> Loggables.withLogger(logger, Level.DEBUG).addLog("Firing process " + processId + " for SAP config " + config.getChildConfig().getId()))
				.forEach((config -> ProcessInfo.builder()
						.setAD_Process_ID(processId)
						.setAD_User_ID(UserId.METASFRESH.getRepoId())
						.addParameter(PARAM_EXTERNAL_REQUEST, SAP_COMMAND)
						.addParameter(PARAM_CHILD_CONFIG_ID, config.getChildConfig().getId().getRepoId())
						.buildAndPrepareExecution()
						.executeSync()));
	}

	private boolean isExternalSystemStartupStatusActive(@NonNull final ExternalSystemParentConfig config)
	{
		return externalSystemServiceInstanceRepository.streamByConfigIds(ImmutableSet.of(config.getId()))
				.noneMatch(externalSystemServiceInstance -> externalSystemServiceInstance.getExpectedStatus().equals(ExternalStatus.INACTIVE));
	}
}
