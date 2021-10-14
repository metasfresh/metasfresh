package de.metas.server.home_page;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

@Configuration
public class DownloadsFolderConfiguration implements WebMvcConfigurer
{
	private static final Logger logger = LogManager.getLogger(DownloadsFolderConfiguration.class);

	@Nullable
	@Value("${metasfresh.serverRoot.downloads:}")
	private String downloadsPath;

	@Override
	public void addResourceHandlers(final @NonNull ResourceHandlerRegistry registry)
	{
		if (Check.isEmpty(downloadsPath, true))
		{
			downloadsPath = defaultDownloadsPath();
		}

		// Make sure the path ends with separator
		// see https://jira.spring.io/browse/SPR-14063
		if (downloadsPath != null && !downloadsPath.endsWith(File.separator))
		{
			downloadsPath += File.separator;
		}

		if (!Check.isEmpty(downloadsPath, true))
		{
			logger.info("Serving static content from " + downloadsPath);
			registry.addResourceHandler("/download/**").addResourceLocations("file:" + downloadsPath);

			// the "binaries" download path is about to be removed soon!
			registry.addResourceHandler("/binaries/**").addResourceLocations("file:" + downloadsPath);
		}
	}

	@Nullable
	private String defaultDownloadsPath()
	{
		try
		{
			final File cwd = new File(".").getCanonicalFile();
			final File downloadsFile = new File(cwd, "download");
			return downloadsFile.getCanonicalPath();
		}
		catch (final IOException ex)
		{
			logger.warn("Failed finding the default downloads path", ex);
			return null;
		}
	}
}
