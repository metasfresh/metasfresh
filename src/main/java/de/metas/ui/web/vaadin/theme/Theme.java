package de.metas.ui.web.vaadin.theme;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.adempiere.images.Images;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vaadin.server.ClassResource;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;

import de.metas.logging.LogManager;

public class Theme
{
	public static final String NAME = "metasfresh";

	private static final Logger logger = LogManager.getLogger(Theme.class);

	public static Resource getImageResourceForNameWithoutExt(final String fileNameWithoutExtension)
	{
		if (fileNameWithoutExtension == null || fileNameWithoutExtension.isEmpty())
		{
			return null;
		}

		try
		{
			return imageNameWithoutExt2resource.get(fileNameWithoutExtension).orNull();
		}
		catch (final ExecutionException e)
		{
			logger.warn("Failed loading image for " + fileNameWithoutExtension, e);
		}
		return null;
	}

	public static Resource getProductLogoLargeResource()
	{
		final String resourceName = Adempiere.getProductLogoLargeResourceName();
		return new ClassResource(Images.RESOURCES_Loader, resourceName);
	}

	/** Cache: "fileName (without extension)" to {@link Resource} */
	private static final LoadingCache<String, Optional<Resource>> imageNameWithoutExt2resource = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, Optional<Resource>>()
			{

				@Override
				public Optional<Resource> load(final String fileName) throws Exception
				{
					String fileNameWithExt = Images.RESOURCENAME_ImagesDir + fileName + ".png";
					InputStream in = Images.RESOURCES_Loader.getResourceAsStream(fileNameWithExt);
					if (in == null)
					{
						fileNameWithExt = Images.RESOURCENAME_ImagesDir + fileName + ".gif";
						in = Images.RESOURCES_Loader.getResourceAsStream(fileNameWithExt);
					}
					if (in == null)
					{
						logger.info("GIF/PNG Not found: " + fileName);
						return Optional.absent();
					}

					final InputStream inputStream = in;
					final Resource resource = new StreamResource(new StreamResource.StreamSource()
					{
						private static final long serialVersionUID = 1L;

						@Override
						public InputStream getStream()
						{
							return inputStream;
						}
					}, fileNameWithExt);

					return Optional.fromNullable(resource);
				}
			});

}
