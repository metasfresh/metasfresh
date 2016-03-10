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

import java.io.File;
import java.util.Arrays;

import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptScanner;

public class DirectoryScriptScanner extends AbstractRecursiveScriptScanner
{
	private final IFileRef rootFileRef;

	private final File[] children;
	private int currentIndex;

	public DirectoryScriptScanner(final IFileRef fileRef)
	{
		super(fileRef.getScriptScanner());

		rootFileRef = fileRef;
		children = retrieveChildren(fileRef);
		currentIndex = -1;
	}

	/**
	 * Convenient constructor
	 *
	 * @param file
	 */
	public DirectoryScriptScanner(final File file)
	{
		this(new FileRef(file));
	}

	private static final File[] retrieveChildren(final IFileRef fileRef)
	{
		final File file = fileRef.getFile();
		if (file == null)
		{
			return new File[] {};
		}

		if (!file.isDirectory())
		{
			return new File[] {};
		}

		final File[] children = file.listFiles();
		if (children == null)
		{
			return new File[] {};
		}

		Arrays.sort(children); // make sure the files are sorted lexicographically
		return children;
	}

	@Override
	protected IScriptScanner retrieveNextChildScriptScanner()
	{
		while (currentIndex < children.length - 1)
		{
			currentIndex++;
			final File child = children[currentIndex];

			final IScriptScanner childScanner = createScriptScanner(child);
			if (childScanner != null)
			{
				return childScanner;
			}
		}

		return null;
	}

	private IScriptScanner createScriptScanner(final File file)
	{
		final FileRef fileRef = new FileRef(this, rootFileRef, file);
		final IScriptScanner scanner = getScriptScannerFactoryToUse().createScriptScanner(fileRef);
		return scanner;
	}

	@Override
	public String toString()
	{
		return "DirectoryScriptScanner [rootFileRef=" + rootFileRef + "]";
	}
}
