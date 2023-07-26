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

package de.metas.externalsystem.woocommerce.housekeeping;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.woocommerce.WooCommerceCommand;
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
public class ExternalSystemWooCommerceHouseKeepingTask implements IStartupHouseKeepingTask
{
	private static final Logger logger = LogManager.getLogger(ExternalSystemWooCommerceHouseKeepingTask.class);

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final ExternalSystemConfigRepo externalSystemConfigDAO;

	public ExternalSystemWooCommerceHouseKeepingTask(@NonNull final ExternalSystemConfigRepo externalSystemConfigDAO)
	{
		this.externalSystemConfigDAO = externalSystemConfigDAO;
	}

	@Override
	public void executeTask()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClassIfUnique(ExternalSystemType.WOO.getExternalSystemProcessClassName());

		if (processId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Nothing to do!");
			return;

		}

		final ImmutableList<ExternalSystemParentConfig> parentConfigList = externalSystemConfigDAO.getAllByType(ExternalSystemType.WOO);

		parentConfigList
				.stream()
				.peek(config -> Loggables.withLogger(logger, Level.DEBUG).addLog("Firing process " + processId + " for WooCommerce config " + config.getChildConfig().getId()))
				.forEach((config -> ProcessInfo.builder()
						.setAD_Process_ID(processId)
						.setAD_User_ID(UserId.METASFRESH.getRepoId())
						.addParameter(PARAM_EXTERNAL_REQUEST, WooCommerceCommand.EnableRestAPI.getValue())
						.addParameter(PARAM_CHILD_CONFIG_ID, config.getChildConfig().getId().getRepoId())
						.buildAndPrepareExecution()
						.executeSync()));
	}
}
