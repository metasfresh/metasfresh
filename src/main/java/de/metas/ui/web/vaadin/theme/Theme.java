package de.metas.ui.web.vaadin.theme;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.adempiere.images.Images;
import org.apache.activemq.util.ByteArrayInputStream;
import org.compiere.Adempiere;
import org.compiere.model.MTreeNode;
import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vaadin.server.ClassResource;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Component;

import de.metas.logging.LogManager;

public class Theme
{
	/** Theme name */
	public static final String NAME = "metasfresh";

	public static final String ICONNAME_Window = MTreeNode.getIconName(MTreeNode.TYPE_WINDOW);
	public static final String ICONNAME_Report = MTreeNode.getIconName(MTreeNode.TYPE_REPORT);
	
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
	
	public static Resource getIconSmall(final String name)
	{
		final String fileNameWithoutExtension = name + "16";
		return getImageResourceForNameWithoutExt(fileNameWithoutExtension);
	}

	public static Resource getIconBig(final String name)
	{
		final String fileNameWithoutExtension = name + "24";
		return getImageResourceForNameWithoutExt(fileNameWithoutExtension);
	}

	/** Cache: "fileName (without extension)" to {@link Resource} */
	private static final LoadingCache<String, Optional<Resource>> imageNameWithoutExt2resource = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, Optional<Resource>>()
			{

				@Override
				public Optional<Resource> load(final String fileNameWithoutExt) throws Exception
				{
					if (fileNameWithoutExt == null || fileNameWithoutExt.isEmpty())
					{
						return Optional.absent();
					}
					
					String fileNameWithExt = fileNameWithoutExt + ".png";
					InputStream in = Images.RESOURCES_Loader.getResourceAsStream(Images.RESOURCENAME_ImagesDir + fileNameWithExt);
					if (in == null)
					{
						fileNameWithExt = fileNameWithoutExt + ".gif";
						in = Images.RESOURCES_Loader.getResourceAsStream(Images.RESOURCENAME_ImagesDir + fileNameWithExt);
					}
					if (in == null)
					{
						logger.info("GIF/PNG Not found: " + fileNameWithoutExt);
						return Optional.absent();
					}
					
					// FIXME: using StreamResource is not efficient at all because, even if we are caching the resource,
					// a new URL will be generated for each component where this is used.
					// I think a better way would be to implement a custom request handler which can cache and serve our icons dynamically.
					// See: https://vaadin.com/docs/-/part/framework/advanced/advanced-requesthandler.html#advanced.requesthandler

					//
					// Read image bytes
					final ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buff = new byte[4096];
					int len = -1;
					while((len = in.read(buff, 0, buff.length)) > 0)
					{
						baos.write(buff, 0, len);
					}
					final byte[] data = baos.toByteArray();
					if (data == null || data.length <= 0)
					{
						return Optional.absent();
					}

					//
					final Resource resource = new StreamResource(() -> new ByteArrayInputStream(data), fileNameWithExt);
					return Optional.fromNullable(resource);
				}
			});

	private static final String STYLE_HIDDEN = "mf-hidden";
	
	public static void setHidden(final Component comp, final boolean hidden)
	{
		if (comp == null)
		{
			return;
		}
		
		if (hidden)
		{
			comp.addStyleName(STYLE_HIDDEN);
		}
		else
		{
			comp.removeStyleName(STYLE_HIDDEN);
		}
	}

}
