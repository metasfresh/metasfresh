/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

import de.metas.CommandLineParser;
import de.metas.ServerBoot;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_EXP_Processor;
import org.compiere.model.I_IMP_Processor;
import org.compiere.util.Env;
import org.springframework.util.SocketUtils;

import static de.metas.async.Async_Constants.SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION;
import static de.metas.async.model.validator.Main.SYSCONFIG_ASYNC_INIT_DELAY_MILLIS;
import static de.metas.async.model.validator.Main.SYSCONFIG_DEBOUNCER_DELAY_MILLIS;
import static de.metas.async.processor.impl.planner.QueueProcessorPlanner.SYSCONFIG_POLLINTERVAL_MILLIS;
import static de.metas.salesorder.interceptor.C_Order_AutoProcess_Async.SYS_Config_AUTO_SHIP_AND_INVOICE;
import static de.metas.util.web.audit.ApiAuditService.CFG_INTERNAL_PORT;
import static org.adempiere.ad.housekeeping.HouseKeepingService.SYSCONFIG_SKIP_HOUSE_KEEPING;

/**
 * Starts - via {@link InfrastructureSupport} - the database and other docker-containers.
 * Then starts {@link ServerBoot} (i.e. metasfresh {@code app}).
 * Generally invoked by {@link de.metas.cucumber.stepdefs.CucumberLifeCycleSupport_StepDef}.
 */
public class CucumberLifeCycleSupport
{
	// keep in sync when moving cucumber OR the file {@code backend/.workspace-sql-scripts.properties}
	public static final String RELATIVE_PATH_TO_METASFRESH_ROOT = "../..";

	private static boolean beforeAllMethodDone;

	public static void beforeAll()
			{
		synchronized (CucumberLifeCycleSupport.class)
		{
			if (beforeAllMethodDone)
			{
				return; // nothing to do; we need to start metasfresh only once
			}
			final InfrastructureSupport infrastructureSupport = new InfrastructureSupport();
			infrastructureSupport.start();

			final String dbHost = infrastructureSupport.getDbHost();
			final String dbPort = Integer.toString(infrastructureSupport.getDbPort());

			final int appServerPort = SocketUtils.findAvailableTcpPort(8080);
			System.setProperty("server.port", Integer.toString(appServerPort));

			System.setProperty("java.awt.headless", "true"); // "simulate headless mode
			System.setProperty("app-server-run-headless", "true"); //
			System.setProperty(CFG_INTERNAL_PORT, Integer.toString(appServerPort)); //
			System.setProperty(SYSCONFIG_ASYNC_INIT_DELAY_MILLIS, "0"); // start the async processor right away; we want to get testing, and not wait
			System.setProperty(SYSCONFIG_SKIP_HOUSE_KEEPING, "true"); // skip housekeeping tasks. assume they are not needed because the DB is fresh
			System.setProperty(SYSCONFIG_POLLINTERVAL_MILLIS, "500");
			System.setProperty(SYSCONFIG_DEBOUNCER_DELAY_MILLIS, "100");
			System.setProperty(SYS_Config_SKIP_WP_PROCESSOR_FOR_AUTOMATION, "true");
			System.setProperty(SYS_Config_AUTO_SHIP_AND_INVOICE, "false");

			// This is a workaround;
			// Apparently, backend/metasfresh-dist/serverRoot/src/main/resources/c3p0.properties is not found in the classpass when we run this on github.
			// See https://www.mchange.com/projects/c3p0/#c3p0_properties for where in the classpath it needs to be
			System.setProperty("c3p0.maxPoolSize", "99"); // set to a value different from c3p0.properties so it's clear from where the value is taken.

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

			update_ReplicationProcessors();

			beforeAllMethodDone = true;
		}
	}

	/**
	 * Make sure to avoid irrelevant but still distracting errors like
	 * <pre>
	 * Cause: java.net.UnknownHostException: No such host is known (rabbitmq)"
	 * [...]
	 * </pre>
	 */
	private static void update_ReplicationProcessors()
	{
		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineParser.CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();

		final String rabbitPassword = commandLineOptions.getRabbitPassword();
		final String rabbitUser = commandLineOptions.getRabbitUser();
		final Integer rabbitPort = commandLineOptions.getRabbitPort();
		final String rabbitHost = commandLineOptions.getRabbitHost();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		queryBL.createQueryBuilder(I_IMP_Processor.class).addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_IMP_Processor.COLUMNNAME_Host, rabbitHost)
				.create()
				.updateDirectly().addSetColumnValue(I_IMP_Processor.COLUMNNAME_Host, rabbitHost)
				.execute();
		queryBL.createQueryBuilder(I_IMP_Processor.class).addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_IMP_Processor.COLUMNNAME_Port, rabbitPort)
				.create()
				.updateDirectly().addSetColumnValue(I_IMP_Processor.COLUMNNAME_Port, rabbitPort)
				.execute();
		queryBL.createQueryBuilder(I_IMP_Processor.class).addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_IMP_Processor.COLUMNNAME_Account, rabbitUser)
				.create()
				.updateDirectly().addSetColumnValue(I_IMP_Processor.COLUMNNAME_Account, rabbitUser)
				.execute();
		queryBL.createQueryBuilder(I_IMP_Processor.class).addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_IMP_Processor.COLUMNNAME_PasswordInfo, rabbitPassword)
				.create()
				.updateDirectly().addSetColumnValue(I_IMP_Processor.COLUMNNAME_PasswordInfo, rabbitPassword)
				.execute();

		queryBL.createQueryBuilder(I_EXP_Processor.class).addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_EXP_Processor.COLUMNNAME_Host, rabbitHost)
				.create()
				.updateDirectly().addSetColumnValue(I_EXP_Processor.COLUMNNAME_Host, rabbitHost)
				.execute();
		queryBL.createQueryBuilder(I_EXP_Processor.class).addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_EXP_Processor.COLUMNNAME_Port, rabbitPort)
				.create()
				.updateDirectly().addSetColumnValue(I_EXP_Processor.COLUMNNAME_Port, rabbitPort)
				.execute();
		queryBL.createQueryBuilder(I_EXP_Processor.class).addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_EXP_Processor.COLUMNNAME_Account, rabbitUser)
				.create()
				.updateDirectly().addSetColumnValue(I_EXP_Processor.COLUMNNAME_Account, rabbitUser)
				.execute();
		queryBL.createQueryBuilder(I_EXP_Processor.class).addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_EXP_Processor.COLUMNNAME_PasswordInfo, rabbitPassword)
				.create()
				.updateDirectly().addSetColumnValue(I_EXP_Processor.COLUMNNAME_PasswordInfo, rabbitPassword)
				.execute();
	}

	public void afterAll()
	{
		// nothing to do, currently
	}
}
