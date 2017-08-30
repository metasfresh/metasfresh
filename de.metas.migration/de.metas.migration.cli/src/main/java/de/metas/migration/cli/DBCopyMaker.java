package de.metas.migration.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.IDatabase;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.applier.impl.NullScriptsApplierListener;
import de.metas.migration.executor.IScriptExecutorFactory;
import de.metas.migration.impl.AbstractScriptsApplierTemplate;
import de.metas.migration.impl.CreateDBFromTemplateScript;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.SingletonScriptScanner;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Creates a copy of the DB {@link #getOriginalDbName()} (also called the template elsewhere).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
public class DBCopyMaker
{

	private static final transient Logger logger = LoggerFactory.getLogger(DBCopyMaker.class);

	@NonNull
	private final String orgiginalDbName;

	@NonNull
	private final String copyDbName;

	@NonNull
	private final IDatabase dbConnection;

	@NonNull
	private final String copyDbOwner;

	public void prepareNewDBCopy()
	{
		logger.info("Going to create a new database {} from the original/template database {}", copyDbName, orgiginalDbName);
		// in accordance with the command line parameters, we now create a new database
		// before actually applying any migration script

		final CreateDBFromTemplateScript createDBFromTemplateScript = CreateDBFromTemplateScript.builder()
				.templateDBName(orgiginalDbName)
				.newDBName(copyDbName)
				.newOwner(copyDbOwner)
				.build();

		final AbstractScriptsApplierTemplate prepareNewDBCopy = createDummyScriptsApplier(dbConnection, createDBFromTemplateScript);
		prepareNewDBCopy.run();
	}

	private AbstractScriptsApplierTemplate createDummyScriptsApplier(final IDatabase db, final CreateDBFromTemplateScript dbScript)
	{
		final AbstractScriptsApplierTemplate prepareNewDBCopy = new AbstractScriptsApplierTemplate()
		{
			@Override
			protected IScriptFactory createScriptFactory()
			{
				return new RolloutDirScriptFactory();
			}

			@Override
			protected void configureScriptExecutorFactory(final IScriptExecutorFactory scriptExecutorFactory)
			{
				scriptExecutorFactory.setDryRunMode(false);
			}

			@Override
			protected IScriptScanner createScriptScanner(final IScriptScannerFactory scriptScannerFactory)
			{

				final IScriptScanner result = new SingletonScriptScanner(dbScript);
				return result;
			}

			@Override
			protected IScriptsApplierListener createScriptsApplierListener()
			{
				return NullScriptsApplierListener.instance;
			}

			@Override
			protected IDatabase createDatabase()
			{
				return db;
			};
		};
		return prepareNewDBCopy;
	}
}
