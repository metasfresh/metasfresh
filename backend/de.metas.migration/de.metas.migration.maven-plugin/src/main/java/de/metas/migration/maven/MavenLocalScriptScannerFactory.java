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


import java.io.File;
import java.io.FileFilter;
import java.util.Properties;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;

import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.CompositeScriptScanner;
import de.metas.migration.scanner.impl.DefaultScriptFactory;
import de.metas.migration.scanner.impl.FileRef;

public class MavenLocalScriptScannerFactory
{
	private static final String PROPERTY_MigrationRootDir = "migration.root.dir";
	private static final String PROPERTY_MigrationRootDir_DefaultValue = "src/main/sql/postgresql/system";
	private static final String PROPERTY_MigrationBaseDir = "migration-sql-basedir";

	private Log log;

	public void setLog(Log log)
	{
		this.log = log;
	}

	public Log getLog()
	{
		if (log == null)
		{
			log = new SystemStreamLog();
		}

		return log;
	}

	protected IScriptScanner createScriptScanner(IScriptScannerFactory scriptScannerFactory, final MavenProject project)
	{
		if (project == null)
		{
			throw new IllegalArgumentException("project is null");
		}
		
		final File projectRootDir = project.getBasedir();
		if (projectRootDir == null)
		{
			throw new IllegalStateException("Project BaseDir was not found for " + project);
		}

		final Properties projectProperties = project.getProperties();

		final String migrationRootDirname = projectProperties.getProperty(PROPERTY_MigrationRootDir, PROPERTY_MigrationRootDir_DefaultValue);
		final File migrationRootDir = new File(projectRootDir, migrationRootDirname);
		getLog().info("Migration Root Dir: " + migrationRootDir);

		final String migrationBaseDirname = projectProperties.getProperty(PROPERTY_MigrationBaseDir);
		getLog().info("Migration Base Dir: " + migrationBaseDirname);

		final CompositeScriptScanner rootScriptScanner = new CompositeScriptScanner();
		rootScriptScanner.setScriptScannerFactory(scriptScannerFactory);

		//
		// Discover migration folders
		if (migrationBaseDirname == null || migrationBaseDirname.trim().isEmpty())
		{
			migrationRootDir.listFiles(new FileFilter()
			{

				@Override
				public boolean accept(final File migrationDir)
				{
					final String projectName = migrationDir.getName();
					createAndAddScriptScannerDir(rootScriptScanner, migrationDir, projectName);

					return false;
				}
			});
		}
		else
		{
			final File migrationDir = new File(migrationRootDir, migrationBaseDirname.trim());
			final String projectName = project.getArtifactId();
			createAndAddScriptScannerDir(rootScriptScanner, migrationDir, projectName);
		}

		return rootScriptScanner;
	}

	private void createAndAddScriptScannerDir(final CompositeScriptScanner rootScriptScanner, final File dir, final String projectName)
	{
		if (!dir.isDirectory())
		{
			return;
		}

		final IFileRef fileRef = new FileRef(dir);

		final IScriptScannerFactory scriptScannerFactory = rootScriptScanner.getScriptScannerFactoryToUse();
		final IScriptScanner scriptScanner = scriptScannerFactory.createScriptScanner(fileRef);
		if (scriptScanner == null)
		{
			return;
		}

		final DefaultScriptFactory scriptFactory = new DefaultScriptFactory(projectName);
		scriptScanner.setScriptFactory(scriptFactory);

		rootScriptScanner.addScriptScanner(scriptScanner);

		getLog().info("Added folder: " + dir
				+ ", projectName=" + projectName
				+ " " + (dir.exists() ? "" : " (NOT EXISTS)"));
	}

}
