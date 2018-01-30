package de.metas.migration.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import de.metas.migration.IScript;
import de.metas.migration.util.FileUtils;
import lombok.Getter;
import lombok.NonNull;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
	private String newOwner;
	private String newDBName;
	private String templateDBName;

	@Getter
	@Setter
	private long lastDurationMillis = -1;

	private CreateDBFromTemplateScript(@NonNull final Builder builder)
	{
		this.newOwner = builder.newOwner;
		this.newDBName = builder.newDBName;
		this.templateDBName = builder.templateDBName;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private String newOwner;
		private String newDBName;
		private String templateDBName;

		public Builder newOwner(String newOwner)
		{
			this.newOwner = newOwner;
			return this;
		}

		public Builder newDBName(String newDBName)
		{
			this.newDBName = newDBName;
			return this;
		}

		public Builder templateDBName(String templateDBName)
		{
			this.templateDBName = templateDBName;
			return this;
		}

		public CreateDBFromTemplateScript build()
		{
			return new CreateDBFromTemplateScript(this);
		}
	}

	@Override
	public String getProjectName()
	{
		return "000";
	}

	@Override
	public String getModuleName()
	{
		return "000";
	}

	@Override
	public String getFileName()
	{

		return "create_db_" + newDBName + "_with_template_" + templateDBName + ".sql";
	}

	@Override
	public String getType()
	{
		return "sql";
	}

	@Override
	public File getLocalFile()
	{
		final String command = "COMMIT; CREATE DATABASE " + newDBName + " WITH OWNER " + newOwner + " TEMPLATE " + templateDBName;
		final InputStream stream = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));

		return FileUtils.createLocalFile(getFileName(), stream);
	}
}
