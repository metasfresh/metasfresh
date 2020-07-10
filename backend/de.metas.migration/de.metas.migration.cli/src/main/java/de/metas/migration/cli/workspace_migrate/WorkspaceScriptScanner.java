package de.metas.migration.cli.workspace_migrate;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.migration.IScript;
import de.metas.migration.ScriptType;
import de.metas.migration.impl.LocalScript;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.util.FileUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2019 metas GmbH
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

class WorkspaceScriptScanner implements IScriptScanner
{
	private static final Logger logger = LoggerFactory.getLogger(WorkspaceScriptScanner.class);

	private final File workspaceDir;
	private final ImmutableSet<String> supportedFileExtensionsLC;
	private final ImmutableSet<Label> acceptLabels;

	private Iterator<IScript> scriptsIterator; // lazy

	@Getter
	@Setter
	private IScriptScannerFactory scriptScannerFactory;

	@Getter
	@Setter
	private IScriptFactory scriptFactory;

	@Builder
	private WorkspaceScriptScanner(
			@NonNull final File workspaceDir,
			@NonNull Set<ScriptType> supportedScriptTypes,
			@NonNull final Set<Label> acceptLabels)
	{
		if (supportedScriptTypes.isEmpty())
		{
			throw new IllegalArgumentException("No supported script types");
		}

		if (acceptLabels.isEmpty())
		{
			throw new IllegalArgumentException("No labels specified");
		}

		this.workspaceDir = workspaceDir;
		this.supportedFileExtensionsLC = supportedScriptTypes.stream()
				.map(scriptType -> scriptType.getFileExtension().toLowerCase())
				.collect(ImmutableSet.toImmutableSet());
		this.acceptLabels = ImmutableSet.copyOf(acceptLabels);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("workspaceDir", workspaceDir)
				.add("supportedFileExtensions", supportedFileExtensionsLC)
				.toString();
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactoryToUse()
	{
		return getScriptScannerFactory();
	}

	@Override
	public IScriptFactory getScriptFactoryToUse()
	{
		return getScriptFactory();
	}

	@Override
	public boolean hasNext()
	{
		return getWorkspaceScriptsIterator().hasNext();
	}

	@Override
	public IScript next()
	{
		return getWorkspaceScriptsIterator().next();
	}

	private Iterator<IScript> getWorkspaceScriptsIterator()
	{
		if (scriptsIterator == null)
		{
			scriptsIterator = getScriptFilesRecursivelly(workspaceDir, DirectoryProperties.NONE)
					.iterator();
		}
		return scriptsIterator;
	}

	private List<IScript> getScriptFilesRecursivelly(@NonNull final File dir, @NonNull final DirectoryProperties dirProps)
	{
		final ArrayList<IScript> result = new ArrayList<>();
		for (final File childDir : dir.listFiles(f -> isEligibleForCheckingScriptFilesRecursive(f)))
		{
			final DirectoryProperties projectDirProps = DirectoryProperties.ofDirectory(childDir)
					.mergeFromParent(dirProps);
			if (!projectDirProps.hasAnyOfLabels(acceptLabels))
			{
				logger.info("Skip scanning {} because labels '{}' are not accepted ones: {}", childDir, projectDirProps.getLabels(), acceptLabels);
				continue;
			}

			if (isMavenProjectDir(childDir))
			{
				final String defaultProjectName = getDefaultProjectName(childDir);
				result.addAll(getScriptFilesFromProjectDir(childDir, defaultProjectName));
			}

			result.addAll(getScriptFilesRecursivelly(childDir, projectDirProps));
		}

		return result;
	}

	private static boolean isEligibleForCheckingScriptFilesRecursive(final File file)
	{
		return isGitRepositoryRoot(file) || isProjectsGroupDir(file) || isMavenProjectDir(file);
	}

	private static boolean isGitRepositoryRoot(final File dir)
	{
		if (!dir.isDirectory())
		{
			return false;
		}

		final File gitDir = new File(dir, ".git");
		return gitDir.exists() && gitDir.isDirectory();
	}

	private static boolean isProjectsGroupDir(final File dir)
	{
		return dir.isDirectory() && "backend".equals(dir.getName()); // metasfresh/backend
	}

	private static boolean isMavenProjectDir(final File dir)
	{
		return dir.isDirectory() && new File(dir, "pom.xml").exists();
	}

	private List<IScript> getScriptFilesFromProjectDir(final File projectDir, final String defaultProjectName)
	{
		final File scriptsRootDir = new File(projectDir, "src/main/sql/postgresql/system");
		if (!scriptsRootDir.isDirectory())
		{
			return ImmutableList.of();
		}

		final ArrayList<IScript> scripts = new ArrayList<>();

		//
		// Script files in subfolders
		// e.g. .../src/main/sql/postgresql/system/10-de.metas.adempiere
		for (final File subProjectDir : scriptsRootDir.listFiles(File::isDirectory))
		{
			final String projectName = subProjectDir.getName();
			for (final File scriptFile : subProjectDir.listFiles(this::isSupportedScriptFile))
			{
				scripts.add(new LocalScript(projectName, scriptFile));
			}
		}

		//
		// Script files directly in .../src/main/sql/postgresql/system/ folder
		for (final File scriptFile : scriptsRootDir.listFiles(File::isFile))
		{
			scripts.add(new LocalScript(defaultProjectName, scriptFile));
		}

		logger.info("Considering {} ({} scripts)", projectDir, scripts.size());

		return scripts;
	}

	private boolean isSupportedScriptFile(final File file)
	{
		if (!file.exists() || !file.isFile())
		{
			return false;
		}

		final String fileExtLC = FileUtils.getFileExtension(file.getName(), false).toLowerCase();
		return supportedFileExtensionsLC.contains(fileExtLC);
	}

	private static String getDefaultProjectName(final File projectDir)
	{
		final File mavenPOMFile = new File(projectDir, "pom.xml");
		if (mavenPOMFile.exists())
		{
			final Document xmlDocument = XmlUtils.loadDocument(mavenPOMFile);

			try
			{
				return XmlUtils.getString("/project/properties/migration-sql-basedir", xmlDocument);
			}
			catch (Exception ex)
			{
				ex.printStackTrace(); // FIXME remove
				return null;
			}
		}
		else
		{
			return null;
		}
	}
}
