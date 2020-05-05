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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;

import de.metas.migration.executor.impl.DefaultScriptExecutorFactory;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.FileRef;
import de.metas.migration.scanner.impl.ScriptScannerFactory;

public class ScannersTestManual
{
	public static void main(final String[] args)
	{
		final IScriptScannerFactory scriptScannerFactory = new ScriptScannerFactory();
		final IFileRef fileRef = new FileRef(new File("c:\\workspaces\\\\de.metas.adempiere.adempiere\\migration\\src\\main\\sql\\postgresql\\system"));
		final IScriptScanner scanner = scriptScannerFactory.createScriptScanner(fileRef);

		final DefaultScriptExecutorFactory scriptExecutorFactory = new DefaultScriptExecutorFactory();
		scriptScannerFactory.registerScriptScannerClassesFor(scriptExecutorFactory);

		int count = 0;
		while (scanner.hasNext())
		{
			System.out.println("" + scanner.next());
			count++;
		}

		System.out.println("" + count + " files found");
	}
}
