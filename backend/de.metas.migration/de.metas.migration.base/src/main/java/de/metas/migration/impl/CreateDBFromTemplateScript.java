package de.metas.migration.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import de.metas.migration.IScript;
import de.metas.migration.ScriptType;
import de.metas.migration.util.FileUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * #%L
 * de.metas.migration.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CreateDBFromTemplateScript implements IScript
{
	private final String newOwner;
	private final String newDBName;
	private final String templateDBName;

	@Getter
	@Setter
	private long lastDurationMillis = -1;

	@Builder
	private CreateDBFromTemplateScript(
			final String newOwner,
			final String newDBName,
			final String templateDBName)
	{
		this.newOwner = newOwner;
		this.newDBName = newDBName;
		this.templateDBName = templateDBName;
	}

	@Override
	public String getProjectName()
	{
		return "000";
	}

	@Override
	public String getFileName()
	{

		return "create_db_" + newDBName + "_with_template_" + templateDBName + ".sql";
	}

	@Override
	public ScriptType getType()
	{
		return ScriptType.SQL;
	}

	@Override
	public File getLocalFile()
	{
		final String command = "COMMIT; CREATE DATABASE " + newDBName + " WITH OWNER " + newOwner + " TEMPLATE " + templateDBName;
		final InputStream stream = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

		return FileUtils.createLocalFile(getFileName(), stream);
	}
}
