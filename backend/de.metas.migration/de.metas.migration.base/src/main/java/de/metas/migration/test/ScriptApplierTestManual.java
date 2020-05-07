package de.metas.migration.test;

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

import de.metas.migration.IDatabase;
import de.metas.migration.applier.IScriptsApplier;
import de.metas.migration.applier.IScriptsProvider;
import de.metas.migration.applier.impl.ScriptsApplier;
import de.metas.migration.applier.impl.SwingUIScriptsApplierListener;
import de.metas.migration.exception.ScriptException;
import de.metas.migration.executor.impl.DefaultScriptExecutorFactory;
import de.metas.migration.impl.SQLDatabase;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.DefaultScriptFactory;
import de.metas.migration.scanner.impl.FileRef;
import de.metas.migration.scanner.impl.ScriptScannerFactory;
import de.metas.migration.scanner.impl.ScriptScannerProviderWrapper;

public class ScriptApplierTestManual
{

	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		final DefaultScriptExecutorFactory scriptExecutorFactory = new DefaultScriptExecutorFactory();
		// scriptExecutorFactory.clear();
		// scriptExecutorFactory.registerScriptExecutorClass(IScriptExecutorFactory.TYPE_ANY, IScriptExecutorFactory.TYPE_ANY, NullScriptExecutor.class);

		final IScriptScannerFactory scriptScannerFactory = new ScriptScannerFactory();
		scriptScannerFactory.setScriptFactory(new DefaultScriptFactory("test"));
		scriptScannerFactory.registerScriptScannerClassesFor(scriptExecutorFactory);

		final IFileRef rootFileRef = new FileRef(new File("d:\\tmp\\testScripts"));
		final IScriptScanner scriptScanner = scriptScannerFactory.createScriptScanner(rootFileRef);
		if (scriptScanner == null)
		{
			throw new RuntimeException("No script scanner created for " + rootFileRef);
		}

		final IScriptsProvider scriptsProvider = new ScriptScannerProviderWrapper(scriptScanner);

		final IDatabase database = new SQLDatabase("postgresql", "127.0.0.1", "5432", "metasfresh", "metasfresh", "metasfresh");
		System.out.println("Database: " + database);
		final IScriptsApplier scriptsApplier = new ScriptsApplier(database);

		scriptsApplier.setScriptExecutorFactory(scriptExecutorFactory);

		scriptsApplier.setListener(new SwingUIScriptsApplierListener());

		ScriptException error = null;
		try
		{
			scriptsApplier.apply(scriptsProvider);
		}
		catch (final ScriptException e)
		{
			error = e;
		}
		finally
		{
			System.out.println("Applied " + scriptsApplier.getCountApplied() + " scripts");
			System.out.println("Ignored " + scriptsApplier.getCountIgnored() + " scripts");

			if (error != null)
			{
				System.out.println("\n\n");
				System.out.println("================================================================================================================");
				error.print(System.out);
			}
		}
	}

}
