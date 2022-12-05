package de.metas.migration.sql.postgresql;

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

import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PgPassFile
{
	public static final PgPassFile instance = new PgPassFile();

	public static final String ANY = "*";

	private final File configFile;
	private List<PgPassEntry> entries;

	private PgPassFile()
	{
		super();

		configFile = findPgPassFile();
	}

	public File getConfigFile()
	{
		return configFile;
	}

	private static File findPgPassFile()
	{
		// see http://www.postgresql.org/docs/9.1/static/libpq-pgpass.html

		final File pgPassFile;
		if (SystemUtils.IS_OS_WINDOWS)
		{
			final String appDataDir = System.getenv("APPDATA");
			pgPassFile = new File(appDataDir, "postgresql\\pgpass.conf");
		}
		else
		{
			final String userHome = System.getProperty("user.home");
			pgPassFile = new File(userHome, ".pgpass");
		}

		return pgPassFile;
	}

	public void markStale()
	{
		entries = null;
	}

	public List<PgPassEntry> getEntries()
	{
		if (entries == null)
		{
			entries = fetchEntries();
		}
		return entries;
	}

	private List<PgPassEntry> fetchEntries()
	{
		final File pgPassFile = getConfigFile();
		if (pgPassFile == null || !pgPassFile.isFile() || !pgPassFile.canRead())
		{
			// TODO: log warning or throw exception
			return Collections.emptyList();
		}

		int lineNo = 0;
		final List<PgPassEntry> entries = new ArrayList<>();

		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(pgPassFile));
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				lineNo++;
				final PgPassEntry entry = parseEntry(line);
				if (entry == null)
				{
					continue;
				}
				entries.add(entry);
			}
		}
		catch (final Exception e)
		{
			throw PgPassParseException.convert(e)
					.setConfigFile(pgPassFile)
					.setConfigLineNo(lineNo);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return entries;
	}

	private PgPassEntry parseEntry(final String line)
	{
		// Skip empty lines
		if (line == null || line.isEmpty())
		{
			return null;
		}

		// Skip comment lines
		if (line.startsWith("#"))
		{
			return null;
		}

		final List<String> tokens = new ArrayList<String>(5);

		StringBuilder currentToken = null;
		final int len = line.length();
		boolean escaped = false;
		for (int i = 0; i < len; i++)
		{
			final char ch = line.charAt(i);
			if (ch == ':' && !escaped)
			{
				tokens.add(currentToken.toString());
				currentToken = null;
			}
			else if (ch == '\\' && !escaped)
			{
				escaped = true;
			}
			else
			{
				escaped = false;

				if (currentToken == null)
				{
					currentToken = new StringBuilder();
				}
				currentToken.append(ch);
			}
		}

		// Case: last char was '\'
		// This is not a valid case, but let's add that char anyway
		if (escaped)
		{
			if (currentToken == null)
			{
				// add it directly
				tokens.add("\\");
			}
			else
			{
				currentToken.append("\\");
			}
		}

		// Add the last token
		if (currentToken != null)
		{
			tokens.add(currentToken.toString());
			currentToken = null;
		}

		if (tokens.size() != 5)
		{
			throw new PgPassParseException("Invalid syntax '" + line + "'. We expected 5 tokens but we got: " + tokens);
		}

		final String host = normalizedTokenEntry(tokens.get(0));
		final String port = normalizedTokenEntry(tokens.get(1));
		final String dbName = normalizedTokenEntry(tokens.get(2));
		final String user = normalizedTokenEntry(tokens.get(3));
		final String password = tokens.get(4);
		final PgPassEntry entry = new PgPassEntry(host, port, dbName, user, password);
		return entry;
	}

	private static String normalizedTokenEntry(final String token)
	{
		if (token == null || token.isEmpty())
		{
			return ANY;
		}
		return token;
	}

	/**
	 * Gets Password for given configuration.
	 *
	 * @return password or null if configuration was not found
	 */
	public String getPassword(final String hostname, final String port, final String dbName, final String username)
	{
		final PgPassEntry entryLookup = new PgPassEntry(hostname, port, dbName, username, null); // password=null
		for (final PgPassEntry entry : getEntries())
		{
			if (matches(entry, entryLookup))
			{
				return entry.getPassword();
			}
		}

		return null;
	}

	private boolean matches(final PgPassEntry entry, final PgPassEntry entryLookup)
	{
		return matchesToken(entry.getHost(), entryLookup.getHost())
				&& matchesToken(entry.getPort(), entryLookup.getPort())
				&& matchesToken(entry.getDbName(), entryLookup.getDbName())
				&& matchesToken(entry.getUser(), entryLookup.getUser());
	}

	private static boolean matchesToken(final String token, final String tokenLookup)
	{
		if (token == null)
		{
			// shall not happen, development error
			throw new IllegalStateException("token shall not be null");
		}
		if (ANY.equals(tokenLookup))
		{
			throw new IllegalStateException("token lookup shall not be ANY");
		}

		//
		if (token == tokenLookup)
		{
			return true;
		}
		if (ANY.equals(token))
		{
			return true;
		}
		if (token.equals(tokenLookup))
		{
			return true;
		}

		return false;
	}
}
