package de.metas;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.BooleanWithReason;
import lombok.NonNull;

/*
 * #%L
 * de.metas.fresh.base
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

public class AllMigrationScriptsTest
{
	@Test
	public void test()
	{
		final Path projectPath = Paths.get(".").toAbsolutePath();
		System.out.println("CWD: " + projectPath);

		final Path workspaceDir = findWorkspacePath(projectPath);
		System.out.println("workspacePath: " + workspaceDir);

		// final List<Path> scriptDirs = getScriptDirectories(workspaceDir);
		// scriptDirs.forEach(path -> System.out.println("scripts dir " + path));

		final ImmutableList<Path> sqlScriptFiles = getWorkspaceSqlScriptFiles(workspaceDir);
		for (final Path sqlScriptFile : sqlScriptFiles)
		{
			final BooleanWithReason valid = checkValidSqlScriptFile(sqlScriptFile);
			if (valid.isFalse())
			{
				System.out.println("Invalid " + sqlScriptFile + ": " + valid.getReason().getDefaultValue());
			}
		}

		System.out.println("Checked " + sqlScriptFiles.size() + " script files");
	}

	private static ImmutableList<Path> getWorkspaceSqlScriptFiles(final Path workspaceDir)
	{
		final List<Path> scriptDirs = getScriptDirectories(workspaceDir);

		return scriptDirs.stream()
				.flatMap(scriptDir -> streamSqlFiles(scriptDir))
				.collect(ImmutableList.toImmutableList());
	}

	private static Path findWorkspacePath(final Path path)
	{
		Path currentPath = path;
		while (currentPath != null && !isWorkspacePath(currentPath))
		{
			currentPath = currentPath.getParent();
		}

		if (isWorkspacePath(currentPath))
		{
			return currentPath;
		}
		else
		{
			throw new AdempiereException("Cannot detect the workspace path for " + path);
		}
	}

	private static boolean isWorkspacePath(final Path path)
	{
		if (path == null)
		{
			return false;
		}

		final Path jenkinsFilePath = path.resolve("Jenkinsfile");
		return Files.isRegularFile(jenkinsFilePath);
	}

	private static boolean isProjectDir(final Path path)
	{
		return Files.isDirectory(path)
				&& Files.isRegularFile(path.resolve("pom.xml"));
	}

	private static ImmutableSet<Path> list(
			@NonNull final Path dir,
			@NonNull final DirectoryStream.Filter<Path> filter)
	{
		try (final DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter))
		{
			return ImmutableSet.copyOf(stream);
		}
		catch (final IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private static List<Path> getScriptDirectories(@NonNull final Path workspaceDir)
	{
		final ArrayList<Path> result = new ArrayList<>();
		for (final Path projectDir : list(workspaceDir, path -> isProjectDir(path)))
		{
			final Path scriptsDir = getScriptsDirectoryOrNull(projectDir);
			if (scriptsDir != null)
			{
				result.add(scriptsDir);
			}

			result.addAll(getScriptDirectories(projectDir));
		}

		return result;
	}

	private static Path getScriptsDirectoryOrNull(final Path projectDir)
	{
		final Path scriptsDir = projectDir.resolve("src/main/sql/postgresql/system");
		if (Files.exists(scriptsDir) && Files.isDirectory(scriptsDir))
		{
			return scriptsDir;
		}
		else
		{
			return null;
		}
	}

	private static Stream<Path> streamSqlFiles(final Path scriptsDir)
	{
		return streamChildren(scriptsDir)
				.flatMap(path -> streamChildren(path))
				.filter(path -> isSqlFile(path));
	}

	private static Stream<Path> streamChildren(final Path path)
	{
		try
		{
			return Files.isDirectory(path)
					? Files.list(path)
					: Stream.of(path);
		}
		catch (IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private static boolean isSqlFile(final Path path)
	{
		return path.getFileName().toString().toLowerCase().endsWith(".sql");
	}

	private static BooleanWithReason checkValidSqlScriptFile(final Path scriptFile)
	{
		try
		{
			if (Files.size(scriptFile) == 0)
			{
				return BooleanWithReason.falseBecause("empty file");
			}
			// TODO
			return BooleanWithReason.TRUE;
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
