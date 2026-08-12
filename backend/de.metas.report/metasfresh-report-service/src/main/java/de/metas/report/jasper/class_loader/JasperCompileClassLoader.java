package de.metas.report.jasper.class_loader;

/*
 * #%L
 * de.metas.report.jasper.server.base
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

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.report.jasper.JasperEngine;
import de.metas.util.FileUtil;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * Alternative class loader to be used when doing dev-tests on a local machine.<br>
 * This class loader will be used by {@link JasperEngine} if we run in developer mode.
 *
 * @author tsa
 */
public class JasperCompileClassLoader extends ClassLoader
{
	private static final Logger logger = LogManager.getLogger(JasperCompileClassLoader.class);

	private static final String jasperExtension = ".jasper";
	private static final String jrxmlExtension = ".jrxml";
	private static final String propertiesExtension = ".properties";
	private static final String xlsExtension = ".xls";

	private final ImmutableSet<File> additionalResourceDirNames;

	@NonNull private final CompliedJaspersCacheFileSystem cacheFileSystem = new CompliedJaspersCacheFileSystem();

	@Builder
	private JasperCompileClassLoader(
			@Nullable final ClassLoader parentClassLoader,
			@NonNull @Singular final List<File> additionalResourceDirNames)
	{
		super(parentClassLoader);
		this.additionalResourceDirNames = ImmutableSet.copyOf(additionalResourceDirNames);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("additionalResourceDirNames", additionalResourceDirNames)
				.add("parent", getParent())
				.toString();
	}

	@Nullable
	@Override
	protected URL findResource(@NonNull final String name)
	{
		final String nameNormalized = StringUtils.trimBlankToNull(name.trim());
		if (nameNormalized == null)
		{
			return null;
		}
		else if (nameNormalized.endsWith(jasperExtension))
		{
			return getJasperResource(nameNormalized);
		}
		else if (nameNormalized.endsWith(xlsExtension))
		{
			return findMiscResource(nameNormalized);
		}
		// handle property files (i.e. resource bundles)
		else if (nameNormalized.endsWith(propertiesExtension))
		{
			return findMiscResource(nameNormalized);
		}
		else
		{
			return findResourceInAdditionalPathsOrNull(nameNormalized);
		}
	}

	@Nullable
	private URL getJasperResource(final String resourceName)
	{
		final String jrxmlPath = toLocalPath(resourceName, jrxmlExtension);
		final File jrxmlFile = toJrxmlLocalFile(jrxmlPath);
		if (jrxmlFile == null)
		{
			return null;
		}

		final JasperEntry entry = cacheFileSystem.getByJrxmlFile(jrxmlFile);
		checkRecompileRequired(entry)
				.ifTrue(recompileReason -> {
					logger.info("Recompiling `{}` because {}", jrxmlPath, recompileReason);
					compile(entry);
				});

		return entry.getJasperUrl();
	}

	@Nullable
	private File toJrxmlLocalFile(@NonNull final String jrxmlPath)
	{
		//
		// Get resource's input stream
		String jrxmlPathNorm = jrxmlPath;
		URL jrxmlUrl = getResource(jrxmlPathNorm);

		// TODO: fix this fucked up
		if (jrxmlUrl == null && jrxmlPath.startsWith("/"))
		{
			jrxmlPathNorm = jrxmlPath.substring(1);
			jrxmlUrl = getResource(jrxmlPathNorm);
		}

		if (jrxmlUrl == null)
		{
			logger.trace("No JRXML resource found for {}", jrxmlPath);
			return null;
		}

		try
		{
			return new File(jrxmlUrl.toURI());
		}
		catch (final URISyntaxException ex)
		{
			throw new AdempiereException("Cannot convert URL to local File: " + jrxmlUrl, ex);
		}
	}

	private static BooleanWithReason checkRecompileRequired(@NonNull final JasperEntry entry)
	{
		final File jrxmlFile = entry.getJrxmlFile();
		if (!jrxmlFile.exists() || !jrxmlFile.canRead())
		{
			return BooleanWithReason.trueBecause(jrxmlFile + " is missing or it cannot be read");
		}

		final File jasperFile = entry.getJasperFile();
		if (!jasperFile.exists() || !jasperFile.canRead())
		{
			return BooleanWithReason.trueBecause(jasperFile + " is missing or it cannot be read");
		}

		final File hashFile = entry.getHashFile();
		if (!hashFile.exists() || !hashFile.canRead())
		{
			return BooleanWithReason.trueBecause(hashFile + " is missing or it cannot be read");
		}

		final String jrxmlFileHashCurrent = hashFileContent(jrxmlFile);
		final String jrxmlFileHashSaved = readFileToString(hashFile);

		if (!Objects.equals(jrxmlFileHashSaved, jrxmlFileHashCurrent))
		{
			return BooleanWithReason.trueBecause(jrxmlFile + " has changed");
		}

		return BooleanWithReason.FALSE;
	}

	private static String readFileToString(final File file)
	{
		try
		{
			return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private static String hashFileContent(final File file)
	{
		try
		{
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			final byte[] fileContent = Util.readBytes(file);
			md.update(fileContent);

			final byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		}
		catch (NoSuchAlgorithmException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@Nullable
	private URL findMiscResource(final String name)
	{
		final String resourcePath = toLocalPath(name, FileUtil.getFileExtension(name));

		URL url = findResourceInAdditionalPathsOrNull(resourcePath);
		if (url != null)
		{
			return url;
		}

		final ClassLoader parentClassLoader = getParent();
		url = parentClassLoader.getResource(resourcePath);
		if (url != null)
		{
			return url;
		}

		if (resourcePath.startsWith("/"))
		{
			url = parentClassLoader.getResource(resourcePath.substring(1));
		}

		return url;
	}

	@Nullable
	private URL findResourceInAdditionalPathsOrNull(final String resourceName)
	{
		for (final File resourceDir : additionalResourceDirNames)
		{
			final File resourceFile = new File(resourceDir, resourceName);
			if (resourceFile.exists() && resourceFile.isFile())
			{
				try
				{
					return resourceFile.toURI().toURL();
				}
				catch (final MalformedURLException e)
				{
					logger.trace("Not considering resourceFile={} for resourceName={} because it cannot be converted to URL", resourceFile, resourceName, e);
				}
			}
		}

		return null;
	}

	private static String toLocalPath(@NonNull final String resourceName, @Nullable final String fileExtension)
	{
		String resourcePath = resourceName.trim()
				.replace(JasperClassLoader.PLACEHOLDER, "")
				.replace("//", "/");

		if (!resourcePath.startsWith("/"))
		{
			resourcePath = "/" + resourcePath;
		}

		return FileUtil.changeFileExtension(resourcePath, fileExtension);
	}

	private void compile(@NonNull final JasperEntry entry)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final File jasperFile = entry.getJasperFile();
		final File hashFile = entry.getHashFile();
		final File jrxmlFile = entry.getJrxmlFile();
		try (final InputStream jrxmlStream = Files.newInputStream(jrxmlFile.toPath()))
		{
			Files.createDirectories(jasperFile.getParentFile().toPath());

			try (final FileOutputStream jasperStream = new FileOutputStream(jasperFile))
			{
				JasperCompileManager.compileReportToStream(jrxmlStream, jasperStream);
			}

			createHashFile(hashFile, jrxmlFile);

			stopwatch.stop();
			logger.info("Compiled jasper report: {} <- {} and it took {}", jasperFile, jrxmlFile, stopwatch);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed compiling jasper report: " + jrxmlFile, ex);
		}
	}

	private static void createHashFile(final File hashFile, final File jrxmlFile) throws IOException
	{
		final String jrxmlFileHash = hashFileContent(jrxmlFile);
		Files.write(hashFile.toPath(), jrxmlFileHash.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	protected Enumeration<URL> findResources(final String name) throws IOException
	{
		final URL url = findResource(name);
		if (url == null)
		{
			return super.findResources(name);
		}

		return Collections.enumeration(Collections.singletonList(url));
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	private static class JasperEntry
	{
		@NonNull File jrxmlFile;
		@NonNull File jasperFile;
		@NonNull File hashFile;

		public URL getJasperUrl()
		{
			try
			{
				return jasperFile.toURI().toURL();
			}
			catch (final MalformedURLException e)
			{
				throw new AdempiereException("Cannot convert " + jasperFile + " to URL", e);
			}
		}
	}

	//
	//
	//
	//
	//

	@ToString(of = "compiledJaspersDir")
	private static class CompliedJaspersCacheFileSystem
	{
		@NonNull private final Path compiledJaspersDir;

		public CompliedJaspersCacheFileSystem()
		{
			try
			{
				final Path basePath = Paths.get(FileUtil.getTempDir(), "compiled_jaspers");
				this.compiledJaspersDir = Files.createDirectories(basePath);
				logger.info("Using compiled reports cache directory: {}", this.compiledJaspersDir);
			}
			catch (IOException e)
			{
				throw new AdempiereException("Failed to create the base temporary directory for compiled reports.", e);
			}
		}

		public JasperEntry getByJrxmlFile(@NonNull final File jrxmlFile)
		{
			return JasperEntry.builder()
					.jrxmlFile(jrxmlFile)
					.jasperFile(getCompiledAssetFile(jrxmlFile, jasperExtension))
					.hashFile(getCompiledAssetFile(jrxmlFile, ".hash"))
					.build();

		}

		private File getCompiledAssetFile(@NonNull final File jrxmlFile, @NonNull final String extension)
		{
			final Path jrxmlPath = jrxmlFile.toPath().toAbsolutePath();

			// 1. Get the path *relative* to the drive/volume root.
			// Example: For C:\workspaces\...\report.jrxml, this isolates:
			// "workspaces\dt204\metasfresh\backend\de.metas.fresh\...\report_lu.jrxml"
			// We use the full relative path to ensure no collisions.
			final Path relativePath = jrxmlPath.getRoot().relativize(jrxmlPath);

			// 2. Separate the directory structure (parent) from the file name.
			final Path relativeDirPath = relativePath.getParent();

			// 3. Resolve the cache directory path: compiledJaspersDir + relativeDirPath
			final Path cacheDir = compiledJaspersDir.resolve(relativeDirPath);

			// 4. Create the final compiled file name (only changes extension).
			final String compiledFileName = FileUtil.changeFileExtension(jrxmlFile.getName(), extension);

			// 5. Resolve the final cached path.
			final Path cachedPath = cacheDir.resolve(compiledFileName);

			return cachedPath.toFile();
		}

	}
}
