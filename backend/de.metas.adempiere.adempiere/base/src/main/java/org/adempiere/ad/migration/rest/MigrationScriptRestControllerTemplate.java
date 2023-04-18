/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.migration.rest;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import org.adempiere.ad.migration.logger.MigrationScriptFileLogger;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class MigrationScriptRestControllerTemplate
{
	private static final Logger logger = LogManager.getLogger(MigrationScriptRestControllerTemplate.class);

	public MigrationScriptRestControllerTemplate(
			@NonNull final String tempDirectoryPrefix)
	{
		configureMigrationScriptDirectoryIfNeeded(tempDirectoryPrefix);
	}

	private static void configureMigrationScriptDirectoryIfNeeded(@NonNull final String tempDirectoryPrefix)
	{
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			logger.warn("Use default migration scripts folder because we are running in developer mode");
			return;
		}

		// Change the migration scripts directory to temporary directory,
		// hoping that even in readonly filesystem (like docker) the temporary directory is still writable.
		try
		{
			final Path migrationScriptsDirectory = Files.createTempDirectory(tempDirectoryPrefix + "_migration_scripts_" + LocalDate.now() + "_");
			MigrationScriptFileLogger.setMigrationScriptDirectory(migrationScriptsDirectory);
		}
		catch (final IOException ex)
		{
			logger.warn("Failed to create & change migration scripts temporary directory. Ignoring and using the defaults", ex);
		}
	}

	protected abstract void assertAuth();

	protected abstract String getUserName();

	@GetMapping
	public JSONMigrationScriptsInfo getInfo()
	{
		assertAuth();

		final Path currentScriptPath = MigrationScriptFileLoggerHolder.getCurrentScriptPathOrNull();

		return JSONMigrationScriptsInfo.builder()
				.enabled(Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT))
				.migrationScriptDirectory(toString(getMigrationScriptsDirectoryPath()))
				.currentScript(toString(currentScriptPath))
				.scripts(getMigrationScriptFileNames())
				.build();
	}

	private static String toString(@Nullable final Path path)
	{
		if (path == null)
		{
			return null;
		}

		try
		{
			return path.toRealPath().toString();
		}
		catch (final IOException ex)
		{
			logger.warn("Failed converting {} to real path", path, ex);
			return path.toString();
		}
	}

	private Path getMigrationScriptPath(final String filename)
	{
		final Path migrationScriptDirectory = getMigrationScriptsDirectoryPath();
		final List<String> scriptFileNames = getMigrationScriptFileNames();
		if (!scriptFileNames.contains(filename))
		{
			throw new AdempiereException("File '" + filename + "' is not available. Try one of followings: " + scriptFileNames);
		}
		return migrationScriptDirectory.resolve(filename);
	}

	private Path getCurrentScriptPath()
	{
		final Path currentScriptPath = MigrationScriptFileLoggerHolder.getCurrentScriptPathOrNull();
		if (currentScriptPath == null)
		{
			throw new AdempiereException("No current script file found");
		}
		return currentScriptPath;
	}

	private List<String> getMigrationScriptFileNames()
	{
		final Path migrationScriptDirectory = getMigrationScriptsDirectoryPath();
		try
		{
			return Files.list(migrationScriptDirectory)
					.map(Path::toFile)
					.map(File::getName)
					.filter(filename -> filename.toLowerCase().endsWith(".sql"))
					.collect(ImmutableList.toImmutableList());
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed fetching migration scripts from " + migrationScriptDirectory);
		}
	}

	private Path getMigrationScriptsDirectoryPath()
	{
		return MigrationScriptFileLogger.getMigrationScriptDirectory();
	}

	@GetMapping("/enableIt")
	public void enableLogMigrationScripts()
	{
		assertAuth();

		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, true);
		final boolean enabled = Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT);
		if (!enabled)
		{
			throw new AdempiereException("Failed to enable migration scripts");
		}
	}

	@GetMapping("/disableIt")
	public void disableLogMigrationScripts()
	{
		assertAuth();

		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, false);
		final boolean enabled = Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT);
		if (enabled)
		{
			throw new AdempiereException("Failed to disable migration scripts");
		}
	}

	@GetMapping("/scripts/current")
	public ResponseEntity<byte[]> getCurrentScript(
			@RequestParam(name = "rotate", required = false, defaultValue = "false") final boolean rotate,
			@RequestParam(name = "inline", required = false, defaultValue = "true") final boolean inline)
	{
		assertAuth();

		final Path currentScriptPath = getCurrentScriptPath();
		final ResponseEntity<byte[]> scriptData = getScript(currentScriptPath, inline);

		if (rotate)
		{
			MigrationScriptFileLoggerHolder.closeMigrationScriptFiles();
		}

		return scriptData;
	}

	@DeleteMapping("/scripts/current")
	public void deleteCurrentScript()
	{
		assertAuth();

		final Path currentScriptPath = getCurrentScriptPath();
		deleteScript(currentScriptPath);
	}

	@GetMapping("/scripts/{filename:.*}")
	public ResponseEntity<byte[]> getScript(

			@PathVariable("filename") final String filename,

			@Parameter(description = "Decides over the reponse's " + HttpHeaders.CONTENT_DISPOSITION + " header value; If set to <code>false</code>, the file is returned as attachment (download)") //
			@RequestParam(name = "inline", required = false, defaultValue = "true") final boolean inline)
	{
		assertAuth();

		final Path scriptPath = getMigrationScriptPath(filename);
		return getScript(scriptPath, inline);
	}

	@DeleteMapping("/scripts/{filename:.*}")
	public void deleteScript(@PathVariable("filename") final String filename)
	{
		assertAuth();

		final Path scriptPath = getMigrationScriptPath(filename);
		deleteScript(scriptPath);
	}

	private ResponseEntity<byte[]> getScript(@NonNull final Path scriptPath, final boolean inline)
	{
		final String contentDispositionType = inline ? "inline" : "attachment";
		final byte[] content = getScriptContent(scriptPath);

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.set(HttpHeaders.CONTENT_DISPOSITION, contentDispositionType + "; filename=\"" + scriptPath.getFileName() + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(content, headers, HttpStatus.OK);
	}

	private byte[] getScriptContent(@NonNull final Path scriptPath)
	{
		try
		{
			final ArrayList<String> lines = new ArrayList<>();
			lines.add("--");
			lines.add("-- Script: " + scriptPath);
			lines.add("-- User: " + getUserName());
			lines.add("-- OS user: " + System.getProperty("user.name"));
			lines.add("--");
			lines.add("");
			lines.add("");
			lines.addAll(Files.readAllLines(scriptPath));

			return toByteArray(lines);
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading content of " + scriptPath, ex);
		}
	}

	private static byte[] toByteArray(final List<String> lines)
	{
		try (final ByteArrayOutputStream out = new ByteArrayOutputStream())
		{
			lines.stream()
					.map(s -> s + "\n")
					.map(s -> s.getBytes(StandardCharsets.UTF_8))
					.forEach(bytes -> {
						try
						{
							out.write(bytes);
						}
						catch (IOException ex)
						{
							// shall never happen
							throw AdempiereException.wrapIfNeeded(ex);
						}
					});

			return out.toByteArray();
		}
		catch (final IOException ex)
		{
			// shall never happen
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private void deleteScript(@NonNull final Path scriptPath)
	{
		try
		{
			Files.delete(scriptPath);
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed deleting " + scriptPath, ex);
		}
	}

}
