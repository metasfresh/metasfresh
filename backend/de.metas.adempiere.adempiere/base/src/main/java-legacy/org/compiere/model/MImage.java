/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import de.metas.cache.CCache;
import de.metas.image.AdImage;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.MimeType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @deprecated Please use {@link de.metas.image.AdImageRepository}
 */
@Deprecated
public class MImage extends X_AD_Image
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7361463683427300715L;

	public static URL getURLOrNull(final int adImageId)
	{
		if (adImageId <= 0)
		{
			return null;
		}

		final MImage adImage = get(Env.getCtx(), adImageId);
		return adImage != null ? adImage.getURL() : null;
	}

	public static MImage get(final Properties ctx, final int AD_Image_ID)
	{
		if (AD_Image_ID == 0)
		{
			return new MImage(ctx, AD_Image_ID, null);
		}
		//
		MImage retValue = s_cache.get(AD_Image_ID);
		if (retValue != null)
		{
			return retValue;
		}
		retValue = new MImage(ctx, AD_Image_ID, null);
		if (retValue.getAD_Image_ID() > 0 && Ini.isSwingClient())
		{
			s_cache.put(AD_Image_ID, retValue);
		}
		return retValue;
	} // get

	/**
	 * Cache
	 */
	private static final CCache<Integer, MImage> s_cache = new CCache<>("AD_Image", 20);

	/**
	 * Constructor
	 *
	 * @param ctx         context
	 * @param AD_Image_ID image
	 * @param trxName     transaction
	 */
	public MImage(final Properties ctx, final int AD_Image_ID, final String trxName)
	{
		super(ctx, AD_Image_ID, trxName);
		if (AD_Image_ID < 1)
		{
			setName("-");
		}
	}   // MImage

	/**
	 * Load Constructor
	 */
	public MImage(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    // MImage

	/**
	 * The Image
	 */
	private Image m_image = null;
	/**
	 * The Icon
	 */
	private ImageIcon m_icon = null;

	/**
	 * Get Image
	 *
	 * @return image or null
	 */
	public Image getImage()
	{
		if (m_image != null)
		{
			return m_image;
		}

		// Via byte array
		final byte[] data = getBinaryData();
		if (data != null && data.length > 0)
		{
			try
			{
				final Toolkit tk = Toolkit.getDefaultToolkit();
				m_image = tk.createImage(data);
				return m_image;
			}
			catch (final Exception e)
			{
				log.warn("(byteArray)", e);
				return null;
			}
		}
		// Via URL
		final URL url = getURL();
		if (url == null)
		{
			return null;
		}
		try
		{
			final Toolkit tk = Toolkit.getDefaultToolkit();
			m_image = tk.getImage(url);
			return m_image;
		}
		catch (final Exception e)
		{
			log.warn("(URL)", e);
		}
		return null;
	}   // getImage

	/**
	 * Get Icon
	 *
	 * @return icon or null
	 */
	public Icon getIcon()
	{
		return getImageIcon();
	}   // getIcon

	private ImageIcon getImageIcon()
	{
		if (m_icon != null)
		{
			return m_icon;
		}

		try
		{
			final Image image = getImage();
			if (image == null)
			{
				return null;
			}

			m_icon = new ImageIcon(image, getName());
		}
		catch (final Exception e)
		{
			log.warn("Failed creating the icon from image on {}. Returning null.", this);
			m_icon = null;
		}
		return m_icon;
	}

	/**
	 * Gets the icon, resized if needed to fit the given maximum size.
	 */
	public Icon getIcon(final Dimension sizeMax)
	{
		if (sizeMax == null)
		{
			return getImageIcon();
		}

		final ImageIcon icon = getImageIcon();
		if (icon == null)
		{
			return null;
		}

		final Dimension sizeOrig = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		final Dimension sizeNew = AdImage.computeScaledDimension(sizeOrig, sizeMax);
		if (sizeNew.equals(sizeOrig))
		{
			return icon;
		}

		// int type = BufferedImage.TYPE_INT_ARGB;
		final BufferedImage resizedImage = new BufferedImage(sizeNew.width, sizeNew.height, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = resizedImage.createGraphics();

		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final Image image = icon.getImage();
		g.drawImage(image, 0, 0, sizeNew.width, sizeNew.height, null);
		g.dispose();

		return new ImageIcon(resizedImage, getName());
	}

	/**
	 * Get URL
	 *
	 * @return url or null
	 */
	private URL getURL()
	{
		final String str = getImageURL();
		if (Check.isEmpty(str, true))
		{
			return null;
		}

		URL url = null;
		try
		{
			// Try URL directly
			if (str.contains("://"))
			{
				url = new URL(str);
			}
			else
			{
				url = getClass().getResource(str);
			}
			//
			if (url == null)
			{
				log.warn("Not found: " + str);
			}
		}
		catch (final Exception ex)
		{
			log.warn("Not found: {}. Returning nul.", str, ex);
		}
		return url;
	}    // getURL

	/**
	 * Set Image URL
	 *
	 * @param ImageURL url
	 */
	@Override
	public void setImageURL(final String ImageURL)
	{
		m_image = null;
		m_icon = null;
		super.setImageURL(ImageURL);
	}    // setImageURL

	/**
	 * Set Binary Data
	 *
	 * @param BinaryData data
	 */
	@Override
	public void setBinaryData(final byte[] BinaryData)
	{
		m_image = null;
		m_icon = null;
		super.setBinaryData(BinaryData);
	}    // setBinaryData

	public String getContentType()
	{
		return MimeType.getMimeType(getName());
	}

	/**
	 * Get Data
	 *
	 * @return data
	 */
	public byte[] getData()
	{
		byte[] data = super.getBinaryData();
		if (data != null)
		{
			return data;
		}
		// From URL
		final String str = getImageURL();
		if (str == null || str.length() == 0)
		{
			log.info("No Image URL");
			return null;
		}
		// Get from URL
		final URL url = getURL();
		if (url == null)
		{
			log.info("No URL");
			return null;
		}
		try
		{
			final URLConnection conn = url.openConnection();
			conn.setUseCaches(false);
			final InputStream is = conn.getInputStream();
			final byte[] buffer = new byte[1024 * 8];   // 8kB
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			int length;
			while ((length = is.read(buffer)) != -1)
			{
				os.write(buffer, 0, length);
			}
			is.close();
			data = os.toByteArray();
			os.close();

		}
		catch (final Exception e)
		{
			log.info(e.toString());
		}
		return data;
	}    // getData

	@Override
	public String toString()
	{
		return "MImage[ID=" + get_ID() + ",Name=" + getName() + "]";
	}   // toString

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getAD_Org_ID() != OrgId.ANY.getRepoId())
		{
			setAD_Org_ID(OrgId.ANY.getRepoId());
		}
		return true;
	}

}   // MImage
