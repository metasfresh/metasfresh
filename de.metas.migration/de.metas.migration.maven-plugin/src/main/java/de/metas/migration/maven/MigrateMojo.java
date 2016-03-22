package de.metas.migration.maven;

/*
 * #%L
 * de.metas.migration.maven-plugin
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import de.metas.migration.IDatabase;
import de.metas.migration.applier.IScriptsApplier;
import de.metas.migration.applier.IScriptsProvider;
import de.metas.migration.applier.impl.ScriptsApplier;
import de.metas.migration.exception.ScriptException;
import de.metas.migration.executor.IScriptExecutorFactory;
import de.metas.migration.executor.impl.DefaultScriptExecutorFactory;
import de.metas.migration.impl.SQLDatabase;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.ScriptScannerFactory;
import de.metas.migration.scanner.impl.ScriptScannerProviderWrapper;

/**
 * Execute migration scripts from maven project
 * 
 * @author tsa
 * 
 */
@Mojo(name = "migrate")
public class MigrateMojo extends AbstractMojo
{
	//
	// Database config
	@Parameter(property = "migration.db.url", defaultValue = "", required = true)
	private String dbUrl;
	@Parameter(property = "migration.db.user", defaultValue = "adempiere", required = true)
	private String dbUser;
	@Parameter(property = "migration.db.password", defaultValue = "adempiere", required = true)
	private String dbPassword;

    /**
     * POM
     */
    @Component
    protected MavenProject project;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		//
		// Bind slf4j to maven log
//		StaticLoggerBinder.getSingleton().setLog(getLog());

		System.out.println("PROJECT: " + project);

		final IScriptScannerFactory scriptScannerFactory = new ScriptScannerFactory();
		final IScriptExecutorFactory scriptExecutorFactory = new DefaultScriptExecutorFactory();

		//
		// Register Executor to scanner factory, all supported file types
		// NOTE: if there are no file types registered, script scanner will return nothing
		scriptScannerFactory.registerScriptScannerClassesFor(scriptExecutorFactory);

		final IDatabase database = createDatabase();
		final IScriptScanner scriptScanner = createScriptScanner(scriptScannerFactory);

		// execute(scriptExecutorFactory, scriptScanner, database);
	}

	protected IDatabase createDatabase()
	{
		// FIXME: this needs to be elaborated 
		final IDatabase database = new SQLDatabase(dbUrl, dbUser, dbPassword);
		return database;
	}

	protected IScriptScanner createScriptScanner(IScriptScannerFactory scriptScannerFactory)
	{
		final MavenLocalScriptScannerFactory mavenScriptScannerFactory = new MavenLocalScriptScannerFactory();
		mavenScriptScannerFactory.setLog(getLog());
		
		return mavenScriptScannerFactory.createScriptScanner(scriptScannerFactory, project);
	}

	protected void execute(final IScriptExecutorFactory scriptExecutorFactory, final IScriptScanner scriptScanner, final IDatabase database)
	{
		final Log logger = getLog();

		//
		// Convert scriptScanner to scriptProvider
		final IScriptsProvider scriptsProvider = new ScriptScannerProviderWrapper(scriptScanner);

		//
		// Setup Script Applier
		final IScriptsApplier scriptsApplier = new ScriptsApplier(database);
		scriptsApplier.setScriptExecutorFactory(scriptExecutorFactory);
		scriptsApplier.setListener(new MavenScriptsApplierListener(logger)); // always fail on error

		ScriptException error = null;
		try
		{
			scriptsApplier.apply(scriptsProvider);
		}
		catch (ScriptException e)
		{
			error = e;

			// let it fail
			throw e;
		}
		finally
		{
			logger.info("Evaluated " + scriptsApplier.getCountAll() + " scripts");
			logger.info("Applied " + scriptsApplier.getCountApplied() + " scripts");
			logger.info("Ignored " + scriptsApplier.getCountIgnored() + " scripts");

			if (error != null)
			{
				logger.info("================================================================================================================");
				logger.info(error.getLocalizedMessage(), error);
			}
		}
	}

}
