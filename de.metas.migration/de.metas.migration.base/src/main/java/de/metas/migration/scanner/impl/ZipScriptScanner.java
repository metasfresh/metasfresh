package de.metas.migration.scanner.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.metas.migration.exception.ScriptException;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptScanner;

public class ZipScriptScanner extends AbstractRecursiveScriptScanner
{
	public static final transient List<String> SUPPORTED_EXTENSIONS = Arrays.asList("zip", "jar");

	private final IFileRef rootFileRef;
	private final ZipInputStream zin;

	private String projectName;

	public ZipScriptScanner(final IFileRef fileRef)
	{
		super(fileRef.getScriptScanner());

		rootFileRef = fileRef;
		zin = new ZipInputStream(fileRef.getInputStream());
	}

	public void close()
	{
		try
		{
			zin.close();
		}
		catch (final IOException e)
		{
			throw new ScriptException("Error closing " + this, e);
		}
	}

	@Override
	protected IScriptScanner retrieveNextChildScriptScanner()
	{
		try
		{
			for (ZipEntry zipEntry = zin.getNextEntry(); zipEntry != null; zipEntry = zin.getNextEntry())
			{
				if (zipEntry.isDirectory())
				{
					continue;
				}

				final IScriptScanner scriptScanner = createScriptScanner(zipEntry);
				if (scriptScanner == null)
				{
					continue;
				}

				zin.closeEntry();
				return scriptScanner;
			}
		}
		catch (final IOException e)
		{
			throw new ScriptException("Error while reading the next script on " + this, e);
		}

		return null;
	}

	private IScriptScanner createScriptScanner(final ZipEntry zipEntry)
	{
		final String[] nameArr = zipEntry.getName().split("/");

		final String moduleName;
		final String fileName;
		if (nameArr == null || nameArr.length == 0)
		{
			return null;
		}
		else if (nameArr.length == 1)
		{
			moduleName = null;
			fileName = nameArr[0];
		}
		else
		{
			moduleName = nameArr[0];
			fileName = nameArr[nameArr.length - 1];
		}

		final IFileRef fileRef = new FileRef(this, rootFileRef, fileName, zin);
		final IScriptScanner scanner = getScriptScannerFactoryToUse().createScriptScanner(fileRef);
		return scanner;
	}

	@Override
	public String toString()
	{
		return "ZipScriptScanner [rootFileRef=" + rootFileRef + "]";
	}

}
