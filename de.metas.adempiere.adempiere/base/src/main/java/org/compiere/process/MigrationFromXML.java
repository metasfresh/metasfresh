package org.compiere.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.io.FilenameFilter;

import org.adempiere.ad.migration.xml.XMLLoader;
import org.adempiere.exceptions.AdempiereException;

public class MigrationFromXML extends SvrProcess
{

	private String fileName;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameterName().equals("FileName"))
			{
				fileName = (String)para.getParameter();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final File file = new File(fileName);
		if (!file.exists())
		{
			throw new AdempiereException("@FileNotFound@");
		}

		final File[] migrationFiles;
		if (file.isDirectory())
		{
			migrationFiles = file.listFiles(new FilenameFilter()
			{
				@Override
				public boolean accept(File dir, String name)
				{
					return name.endsWith(".xml");
				}
			});
		}
		else
		{
			migrationFiles = new File[] { file };
		}

		for (final File migrationFile : migrationFiles)
		{
			final XMLLoader loader = new XMLLoader(migrationFile.getAbsolutePath());
			loader.load(get_TrxName());
		}

		return "Import complete";
	}
}
