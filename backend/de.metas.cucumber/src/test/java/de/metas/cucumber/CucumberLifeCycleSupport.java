/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber;

import de.metas.ServerBoot;
import de.metas.migration.cli.workspace_migrate.WorkspaceMigrateConfig;
import de.metas.migration.cli.workspace_migrate.WorkspaceMigrateConfig.OnScriptFailure;
import lombok.Getter;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.springframework.util.SocketUtils;

import java.io.File;

import static de.metas.async.model.validator.Main.SYSCONFIG_ASYNC_INIT_DELAY_MILLIS;
import static de.metas.async.processor.impl.planner.QueueProcessorPlanner.SYSCONFIG_POLLINTERVAL_MILLIS;
import static de.metas.util.web.audit.ApiAuditService.CFG_INTERNAL_PORT;
import static org.adempiere.ad.housekeeping.HouseKeepingService.SYSCONFIG_SKIP_HOUSE_KEEPING;

public class CucumberLifeCycleSupport
{
	// keep in sync when moving cucumber OR the file {@code backend/.workspace-sql-scripts.properties}
	public static final String RELATIVE_PATH_TO_METASFRESH_ROOT = "../..";

	@Getter
	private boolean beforeAllMethodDone;

	public void beforeAll()
	{
		final InfrastructureSupport infrastructureSupport = new InfrastructureSupport();
		infrastructureSupport.start();

		final String dbHost = infrastructureSupport.getDbHost();
		final String dbPort = Integer.toString(infrastructureSupport.getDbPort());

		if (infrastructureSupport.isRunAgainstDockerizedDatabase())
		{
			final File workspaceDir = new File(RELATIVE_PATH_TO_METASFRESH_ROOT);
			final WorkspaceMigrateConfig migrateConfig = WorkspaceMigrateConfig.builder()
					.workspaceDir(workspaceDir)
					.onScriptFailure(OnScriptFailure.FAIL)
					.dbUrl("jdbc:postgresql://" + dbHost + ":" + dbPort + "/metasfresh")
					.build();
			de.metas.migration.cli.workspace_migrate.Main.main(migrateConfig);
		}

		final int appServerPort = SocketUtils.findAvailableTcpPort(8080);
		System.setProperty("server.port", Integer.toString(appServerPort));

		System.setProperty("java.awt.headless", "true"); // "simulate headless mode
		System.setProperty("app-server-run-headless", "true"); //
		System.setProperty(CFG_INTERNAL_PORT, Integer.toString(appServerPort)); //
		System.setProperty(SYSCONFIG_ASYNC_INIT_DELAY_MILLIS, "0"); // start the async processor right away; we want to get testing, and not wait
		System.setProperty(SYSCONFIG_SKIP_HOUSE_KEEPING, "true"); // skip housekeeping tasks. assume they are not needed because the DB is fresh
		System.setProperty(SYSCONFIG_POLLINTERVAL_MILLIS, "500");
		final String[] args = { //
				"-dbHost", dbHost,
				"-dbPort", dbPort,
				"-rabbitHost", infrastructureSupport.getRabbitHost(),
				"-rabbitPort", Integer.toString(infrastructureSupport.getRabbitPort()),
				"-rabbitUser", infrastructureSupport.getRabbitUser(),
				"-rabbitPassword", infrastructureSupport.getRabbitPassword()
		};
		ServerBoot.main(args);

		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		beforeAllMethodDone = true;
	}

	public void afterAll()
	{
		// nothing to do, currently
	}
}
