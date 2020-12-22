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
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import lombok.NonNull;
import org.springframework.util.SocketUtils;

import java.io.File;

/**
 * Thx to https://medium.com/@hemanthsridhar/global-hooks-in-cucumber-jvm-afc1be13e487 !
 */
public class CucumberLifeCycleSupport implements ConcurrentEventListener
{
	private final EventHandler<TestRunStarted> setup = event -> {
		beforeAll();
	};

	private final EventHandler<TestRunFinished> teardown = event -> {
		afterAll();
	};

	private static String dbHost;
	private static int dbPort;

	@Override
	public void setEventPublisher(@NonNull final EventPublisher eventPublisher)
	{
		eventPublisher.registerHandlerFor(TestRunStarted.class, setup);
		eventPublisher.registerHandlerFor(TestRunFinished.class, teardown);
	}

	private void beforeAll()
	{
		if (true)
		{ // run agaist the dockerized postgrest
			final MetasfreshDBSupport metasfreshDBSupport = new MetasfreshDBSupport();
			metasfreshDBSupport.startMetasfreshDB();
			dbHost = metasfreshDBSupport.getHost();
			dbPort = metasfreshDBSupport.getPort();
		}
		else
		{ // run against your local postgres
			dbHost = "localhost";
			dbPort = 5432;
		}
		final WorkspaceMigrateConfig migrateConfig = WorkspaceMigrateConfig.builder()
				.workspaceDir(new File("../.."))
				.dbUrl("jdbc:postgresql://" + dbHost + ":" + Integer.toString(dbPort) + "/metasfresh")
				.build();
		de.metas.migration.cli.workspace_migrate.Main.main(migrateConfig);

		final int appServerPort = SocketUtils.findAvailableTcpPort(8080);
		System.setProperty("server.port", Integer.toString(appServerPort));
		final String[] args = { //
				"-dbHost", dbHost,
				"-dbPort", Integer.toString(dbPort) };
		ServerBoot.main(args);
	}

	private void afterAll()
	{
		// nothing to do, currently
	}
}
