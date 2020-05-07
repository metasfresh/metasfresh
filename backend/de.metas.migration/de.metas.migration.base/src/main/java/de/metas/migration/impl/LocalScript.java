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
import de.metas.migration.util.FileUtils;
import lombok.Getter;
import lombok.Setter;

public class LocalScript implements IScript
{
	private final String projectName;
	private final String moduleName;
	private final String filename;
	private final String type;

	private final File localFile;

	@Getter
	@Setter
	private long lastDurationMillis = -1;

	public LocalScript(final String projectName, final String moduleName, final File file)
	{
		this.projectName = projectName;
		this.moduleName = moduleName;
		filename = file.getName();
		type = getScriptType(filename);
		localFile = file.getAbsoluteFile();
	}

	public LocalScript(final String projectName, final String moduleName, final String filename, final InputStream in)
	{
		this.projectName = projectName;
		this.moduleName = moduleName;
		this.filename = filename;
		type = getScriptType(filename);
		localFile = FileUtils.createLocalFile(filename, in);
	}

	private static final String getScriptType(final String filename)
	{
		final String fileExtension = FileUtils.getFileExtension(filename, false);
		return fileExtension == null ? null : fileExtension.toLowerCase();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (filename == null ? 0 : filename.hashCode());
		result = prime * result + (moduleName == null ? 0 : moduleName.hashCode());
		result = prime * result + (projectName == null ? 0 : projectName.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final LocalScript other = (LocalScript)obj;
		if (filename == null)
		{
			if (other.filename != null)
			{
				return false;
			}
		}
		else if (!filename.equals(other.filename))
		{
			return false;
		}
		if (moduleName == null)
		{
			if (other.moduleName != null)
			{
				return false;
			}
		}
		else if (!moduleName.equals(other.moduleName))
		{
			return false;
		}
		if (projectName == null)
		{
			if (other.projectName != null)
			{
				return false;
			}
		}
		else if (!projectName.equals(other.projectName))
		{
			return false;
		}
		if (type == null)
		{
			if (other.type != null)
			{
				return false;
			}
		}
		else if (!type.equals(other.type))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " ["
				+ "projectName=" + projectName
				+ ", moduleName=" + moduleName
				+ ", filename=" + filename
				+ ", type=" + type
				+ ", localFile=" + localFile
				+ "]";
	}

	@Override
	public String getProjectName()
	{
		return projectName;
	}

	@Override
	public String getModuleName()
	{
		return moduleName;
	}

	@Override
	public String getFileName()
	{
		return filename;
	}

	@Override
	public String getType()
	{
		return type;
	}

	@Override
	public File getLocalFile()
	{
		return localFile;
	}
}
