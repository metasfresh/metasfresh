package de.metas.migration.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import de.metas.migration.IScript;
import de.metas.migration.ScriptType;
import de.metas.migration.util.FileUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/*
 * #%L
 * de.metas.migration.base
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

@ToString(of = { "scriptContent" })
public class AnonymousScript implements IScript
{
	@Getter
	private final String projectName;
	@Getter
	private final String moduleName;
	@Getter
	private final String fileName;
	@Getter
	private final ScriptType type;

	private final String scriptContent;
	private File localFile;

	@Getter
	@Setter
	private long lastDurationMillis = -1;

	@Builder
	private AnonymousScript(
			@NonNull final String fileName,
			@NonNull final String scriptContent,
			final String projectName,
			final String moduleName)
	{
		this.projectName = projectName != null ? projectName : "000";
		this.moduleName = moduleName != null ? moduleName : "000";
		this.fileName = fileName;
		type = FileUtils.getScriptTypeByFilename(fileName);
		this.scriptContent = scriptContent;
	}

	@Override
	public File getLocalFile()
	{
		File localFile = this.localFile;
		if (localFile == null)
		{
			localFile = this.localFile = createLocalFile();
		}
		return localFile;
	}

	private File createLocalFile()
	{
		final InputStream in = new ByteArrayInputStream(scriptContent.getBytes());
		return FileUtils.createLocalFile(getFileName(), in);
	}
}
