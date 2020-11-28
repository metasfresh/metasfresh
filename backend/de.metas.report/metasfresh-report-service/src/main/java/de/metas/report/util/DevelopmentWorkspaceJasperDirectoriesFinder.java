package de.metas.report.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * report-service
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

@UtilityClass
public final class DevelopmentWorkspaceJasperDirectoriesFinder
{
	public static List<File> getReportsDirectoriesForWorkspace(final File workspaceDir)
	{
		return getReportsDirectories(workspaceDir);
	}

	private static List<File> getReportsDirectories(@NonNull final File dir)
	{
		final ArrayList<File> result = new ArrayList<>();
		for (final File projectDir : dir.listFiles(f -> isProjectDir(f)))
		{
			final File reportsDir = getReportsDirOfProjectDirIfExists(projectDir);
			if (reportsDir != null)
			{
				result.add(reportsDir);
			}

			result.addAll(getReportsDirectories(projectDir));
		}

		return result;
	}

	private static File getReportsDirOfProjectDirIfExists(final File projectDir)
	{
		final File reportsDir = new File(projectDir, "src//main//jasperreports");
		if (reportsDir.exists() && reportsDir.isDirectory())
		{
			return reportsDir;
		}
		else
		{
			return null;
		}
	}

	private static boolean isProjectDir(final File dir)
	{
		return dir.isDirectory()
				&& new File(dir, "pom.xml").exists()
				&& !containsIgnoreReportsFile(dir);
	}

	public static boolean containsIgnoreReportsFile(final File dir)
	{
		return new File(dir, ".ignore-reports").exists();
	}
}
