package de.metas.migration.scanner.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.util.FileUtils;
import lombok.ToString;

@ToString(of = "rootFileRef")
public class ZipScriptScanner extends ForwardingScriptScanner
{
	public static final transient List<String> SUPPORTED_EXTENSIONS = Arrays.asList("zip", "jar");

	private static final transient Logger logger = LoggerFactory.getLogger(ZipScriptScanner.class);

	private final IFileRef rootFileRef;
	private DirectoryScriptScanner delegate = null; // lazy

	public ZipScriptScanner(final IFileRef fileRef)
	{
		rootFileRef = fileRef;
	}

	@Override
	protected synchronized IScriptScanner getDelegate()
	{
		DirectoryScriptScanner delegate = this.delegate;
		if (delegate == null)
		{
			delegate = this.delegate = createDirectoryScriptScanner();
		}
		return delegate;
	}

	private DirectoryScriptScanner createDirectoryScriptScanner()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final File zipFile = rootFileRef.getFile();
		final File unzipDir = FileUtils.unzip(zipFile);

		stopwatch.stop();
		logger.info("Unzipped {} to {} in {}", zipFile, unzipDir, stopwatch);

		return new DirectoryScriptScanner(new FileRef(unzipDir));
	}
}
