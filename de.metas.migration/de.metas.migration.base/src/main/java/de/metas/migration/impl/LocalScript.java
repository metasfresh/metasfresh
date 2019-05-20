package de.metas.migration.impl;

/*
 * #%L
 * de.metas.migration.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.io.InputStream;

import de.metas.migration.IScript;
import de.metas.migration.ScriptType;
import de.metas.migration.util.FileUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(exclude = { "localFile", "lastDurationMillis" })
@ToString
public class LocalScript implements IScript
{
	@Getter
	private final String projectName;
	@Getter
	private final String fileName;
	@Getter
	private final ScriptType type;
	@Getter
	private final File localFile;

	@Getter
	@Setter
	private long lastDurationMillis = -1;

	public LocalScript(final String projectName, final File file)
	{
		this.projectName = projectName;
		fileName = file.getName();
		type = FileUtils.getScriptTypeByFilename(fileName);
		localFile = file.getAbsoluteFile();
	}

	public LocalScript(final String projectName, final String filename, final InputStream in)
	{
		this.projectName = projectName;
		this.fileName = filename;
		type = FileUtils.getScriptTypeByFilename(filename);
		localFile = FileUtils.createLocalFile(filename, in);
	}
}
