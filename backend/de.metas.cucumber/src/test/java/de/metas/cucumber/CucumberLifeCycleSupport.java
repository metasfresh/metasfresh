/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
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
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import lombok.NonNull;
import org.springframework.util.SocketUtils;

import java.io.File;

import static de.metas.async.model.validator.Main.SYSCONFIG_ASYNC_INIT_DELAY_MILLIS;
import static de.metas.async.processor.impl.planner.QueueProcessorPlanner.SYSCONFIG_POLLINTERVAL_MILLIS;
import static de.metas.util.web.audit.ApiAuditService.CFG_INTERNAL_PORT;
import static org.adempiere.ad.housekeeping.HouseKeepingService.SYSCONFIG_SKIP_HOUSE_KEEPING;

/**
 * Thx to https://medium.com/@hemanthsridhar/global-hooks-in-cucumber-jvm-afc1be13e487 !
 */
public class CucumberLifeCycleSupport implements ConcurrentEventListener
{
	// keep in sync when moving cucumber OR the file {@code backend/.workspace-sql-scripts.properties}
	public static final String RELATIVE_PATH_TO_METASFRESH_ROOT = "../..";

	private final EventHandler<TestRunStarted> setup = event -> beforeAll();

	private final EventHandler<TestRunFinished> teardown = event -> afterAll();

	@Override
	public void setEventPublisher(@NonNull final EventPublisher eventPublisher)
	{
		eventPublisher.registerHandlerFor(TestRunStarted.class, setup);
		eventPublisher.registerHandlerFor(TestRunFinished.class, teardown);
	}

	public static void beforeAll()
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
	}

	private void afterAll()
	{
		// nothing to do, currently
	}
}
