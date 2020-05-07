package de.metas.migration.scanner.impl;

import java.io.File;

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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.migration.ScriptType;
import de.metas.migration.exception.ScriptException;
import de.metas.migration.executor.IScriptExecutorFactory;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.util.FileUtils;

public class ScriptScannerFactory implements IScriptScannerFactory
{
	private static final transient Logger logger = LoggerFactory.getLogger(ScriptScannerFactory.class.getName());

	private final Map<String, Class<? extends IScriptScanner>> scriptScannerClasses = new HashMap<String, Class<? extends IScriptScanner>>();

	private Class<? extends IScriptScanner> directoryScannerClass;

	private final IScriptFactory scriptFactoryDefault = new DefaultScriptFactory();
	private IScriptFactory scriptFactory = null;

	public ScriptScannerFactory()
	{
		super();
		initDefaults();
	}

	private void initDefaults()
	{
		setDirectoryScannerClass(DirectoryScriptScanner.class);
		for (final String extension : ZipScriptScanner.SUPPORTED_EXTENSIONS)
		{
			registerScriptScannerClass(extension, ZipScriptScanner.class);
		}
	}

	public void setDirectoryScannerClass(final Class<? extends IScriptScanner> directoryScannerClass)
	{
		if (directoryScannerClass == null)
		{
			throw new IllegalArgumentException("directoryScannerClass is null");
		}

		final Class<? extends IScriptScanner> directoryScannerClassOld = this.directoryScannerClass;
		this.directoryScannerClass = directoryScannerClass;

		if (directoryScannerClassOld != null)
		{
			logger.info("Unregistering directory scanner " + directoryScannerClassOld);
		}
		logger.info("Registering directory scanner " + directoryScannerClass);
	}

	private Class<? extends IScriptScanner> getScriptScannerClassOrNull(final IFileRef fileRef)
	{
		// Check if is Directory
		if (fileRef.getFile() != null && fileRef.getFile().isDirectory())
		{
			return directoryScannerClass;
		}

		final String fileExtension = FileUtils.getFileExtension(fileRef.getFileName(), false); // includeDot=false
		final String fileExtensionNorm = normalizeFileExtension(fileExtension);

		final Class<? extends IScriptScanner> scannerClass = scriptScannerClasses.get(fileExtensionNorm);
		return scannerClass;
	}

	@Override
	public IScriptScanner createScriptScannerByFilename(final String filename)
	{
		final IFileRef fileRef = new FileRef(new File(filename));
		final IScriptScanner scriptScanner = createScriptScanner(fileRef);
		if (scriptScanner == null)
		{
			throw new RuntimeException("No script scanner found for " + filename);
		}
		return scriptScanner;
	}

	@Override
	public IScriptScanner createScriptScanner(final IFileRef fileRef)
	{
		final Class<? extends IScriptScanner> scannerClass = getScriptScannerClassOrNull(fileRef);
		if (scannerClass == null)
		{
			return null;
		}

		try
		{
			final IScriptScanner scriptScanner = scannerClass.getConstructor(IFileRef.class).newInstance(fileRef);
			scriptScanner.setScriptScannerFactory(this);
			return scriptScanner;
		}
		catch (final Exception e)
		{
			throw new ScriptException("Cannot instantiate scanner class: " + scannerClass, e);
		}
	}

	@Override
	public void registerScriptScannerClass(final String fileExtension, final Class<? extends IScriptScanner> scannerClass)
	{
		final String fileExtensionNorm = normalizeFileExtension(fileExtension);
		final Class<? extends IScriptScanner> scannerClassOld = scriptScannerClasses.put(fileExtensionNorm, scannerClass);

		if (scannerClassOld != null)
		{
			logger.info("Unregistering scanner " + scannerClassOld + " for fileExtension=" + fileExtension);
		}
		logger.info("Registering scanner " + scannerClass + " for fileExtension=" + fileExtension);
	}

	@Override
	public void registerScriptScannerClassesFor(final IScriptExecutorFactory scriptExecutorFactory)
	{
		for (final ScriptType scriptType : scriptExecutorFactory.getSupportedScriptTypes())
		{
			registerScriptScannerClass(scriptType.getFileExtension(), SingletonScriptScanner.class);
		}
	}

	private static final String normalizeFileExtension(final String fileExtension)
	{
		return fileExtension.trim().toLowerCase();
	}

	@Override
	public void setScriptFactory(final IScriptFactory scriptFactory)
	{
		this.scriptFactory = scriptFactory;
	}

	@Override
	public IScriptFactory getScriptFactoryToUse()
	{
		if (scriptFactory != null)
		{
			return scriptFactory;
		}

		return scriptFactoryDefault;
	}
}
