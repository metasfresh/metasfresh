package de.metas.migration.applier.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import de.metas.migration.IScript;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.exception.ScriptExecutionException;

/*
 * #%L
 * de.metas.migration.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ConsoleScriptsApplierListener implements IScriptsApplierListener
{
	public static final transient ConsoleScriptsApplierListener instance = new ConsoleScriptsApplierListener();

	private ConsoleScriptsApplierListener()
	{
	}

	@Override
	public void onScriptApplied(final IScript script)
	{
		// nothing
	}

	@Override
	public ScriptFailedResolution onScriptFailed(final IScript script, final ScriptExecutionException e) throws RuntimeException
	{
		final File file = script.getLocalFile();
		final String exceptionMessage = e.toStringX(false); // printStackTrace=false

		final PrintStream out = System.out;
		final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		out.println("--------------------------------------------------------------------------------------------------");
		out.println("Script failed to run: " + file.toString());
		out.println();
		out.println(exceptionMessage);
		out.println("--------------------------------------------------------------------------------------------------");

		do
		{
			out.println("What shall we do?");
			out.println("F - fail, I - Ignore, R - Retry");
			out.flush();

			String answer;
			try
			{
				answer = in.readLine();
			}
			catch (final IOException ioex)
			{
				out.println("Failed reading from console. Throwing inital error.");
				out.flush();

				e.addSuppressed(ioex);
				throw e;
			}

			if ("F".equalsIgnoreCase(answer))
			{
				return ScriptFailedResolution.Fail;
			}
			else if ("I".equalsIgnoreCase(answer))
			{
				return ScriptFailedResolution.Ignore;
			}
			else if ("R".equalsIgnoreCase(answer))
			{
				return ScriptFailedResolution.Retry;
			}
			else
			{
				out.println("Unknown option: " + answer);
			}
		}
		while (true);
	}

}
