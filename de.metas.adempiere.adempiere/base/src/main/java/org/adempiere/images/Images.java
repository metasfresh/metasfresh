package org.adempiere.images;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.ImageIcon;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Helper class to load UI icons.
 *
 * @author tsa
 *
 */
public final class Images
{
	private static final transient Logger logger = LogManager.getLogger(Images.class);

	public static final String RESOURCENAME_ImagesDir = "images/";
	/** Class used for loading the image resources */
	public static final Class<?> RESOURCES_Loader = org.compiere.Adempiere.class;

	/** Cache: "fileNameInImageDir" to {@link Image} */
	private static final LoadingCache<String, Optional<Image>> images = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, Optional<Image>>()
			{
				@Override
				public Optional<Image> load(final String fileNameInImageDir)
				{
					final URL url = RESOURCES_Loader.getResource(RESOURCENAME_ImagesDir + fileNameInImageDir);
					if (url == null)
					{
						logger.error("Not found: " + fileNameInImageDir);
						return Optional.absent();
					}

					return loadImage(url);
				}
			});

	/** Cache: "fileNameInImageDir" to {@link ImageIcon} */
	private static final LoadingCache<String, Optional<ImageIcon>> imageIcons = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, Optional<ImageIcon>>()
			{
				@Override
				public Optional<ImageIcon> load(final String fileNameInImageDir) throws Exception
				{
					final Image image = images.get(fileNameInImageDir).orNull();
					if (image == null)
					{
						return Optional.absent();
					}

					return Optional.fromNullable(new ImageIcon(image));
				}
			});

	/** Cache: "fileName (without extension)" to {@link ImageIcon} */
	private static final LoadingCache<String, Optional<ImageIcon>> imageIcons2 = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, Optional<ImageIcon>>()
			{

				@Override
				public Optional<ImageIcon> load(final String fileName) throws Exception
				{
					URL url = RESOURCES_Loader.getResource(RESOURCENAME_ImagesDir + fileName + ".png");
					if (url == null)
					{
						url = RESOURCES_Loader.getResource(RESOURCENAME_ImagesDir + fileName + ".gif");
					}
					if (url == null)
					{
						logger.info("GIF/PNG Not found: " + fileName);
						return Optional.absent();
					}

					final Image image = loadImage(url).orNull();
					if (image == null)
					{
						return Optional.absent();
					}

					return Optional.fromNullable(new ImageIcon(image));
				}
			});

	private Images()
	{
		super();
	}

	/**
	 * Get Image with File name
	 *
	 * @param fileNameInImageDir full file name in images folder (e.g. Bean16.gif)
	 * @return image
	 */
	public static Image getImage(final String fileNameInImageDir)
	{
		if (fileNameInImageDir == null || fileNameInImageDir.isEmpty())
		{
			return null;
		}

		try
		{
			return images.get(fileNameInImageDir).orNull();
		}
		catch (final ExecutionException e)
		{
			logger.warn("Failed loading image for " + fileNameInImageDir, e);
		}
		return null;
	}

	/**
	 * Get ImageIcon.
	 *
	 * @param fileNameInImageDir full file name in images folder (e.g. Bean16.gif)
	 * @return image or <code>null</code>
	 */
	public static ImageIcon getImageIcon(final String fileNameInImageDir)
	{
		if (fileNameInImageDir == null || fileNameInImageDir.isEmpty())
		{
			return null;
		}

		try
		{
			return imageIcons.get(fileNameInImageDir).orNull();
		}
		catch (final ExecutionException e)
		{
			logger.warn("Failed loading image for " + fileNameInImageDir, e);
		}
		return null;
	}   // getImageIcon

	/**
	 * Get ImageIcon.
	 * 
	 * This method different from {@link #getImageIcon(String)} where the fileName parameter is with extension.
	 * The method will first try .png and then .gif if .png does not exists.
	 *
	 * @param fileNameWithoutExtension file name in images folder without the extension(e.g. Bean16)
	 * @return image or <code>null</code>
	 */
	public static ImageIcon getImageIcon2(final String fileNameWithoutExtension)
	{
		if (fileNameWithoutExtension == null || fileNameWithoutExtension.isEmpty())
		{
			return null;
		}

		try
		{
			return imageIcons2.get(fileNameWithoutExtension).orNull();
		}
		catch (final ExecutionException e)
		{
			logger.warn("Failed loading image for " + fileNameWithoutExtension, e);
		}
		return null;
	}   // getImageIcon2

	/**
	 * Get Image.
	 * 
	 * This method different from {@link #getImage(String)} where the fileName parameter is with extension.
	 * The method will first try .png and then .gif if .png does not exists.
	 *
	 * @param fileNameWithoutExtension file name in images folder without the extension(e.g. Bean16)
	 * @return image or <code>null</code>
	 */
	public static Image getImage2(final String fileNameWithoutExtension)
	{
		final ImageIcon imageIcon = getImageIcon2(fileNameWithoutExtension);
		if(imageIcon == null)
		{
			return null;
		}
		return imageIcon.getImage();
	}
	
	/**
	 * Loads {@link Image} of given <code>url</code> and apply theme's RGB filter if any.
	 * 
	 * @param url
	 * @return {@link Image} or null
	 */
	private static final Optional<Image> loadImage(final URL url)
	{
		if (url == null)
		{
			return Optional.absent();
		}

		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(url);
		if (image == null)
		{
			return Optional.absent();
		}

		return Optional.fromNullable(image);
	}
}
