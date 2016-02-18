/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.compiere.util.CCache;
import org.compiere.util.Ini;

/**
 *  Image Model
 *  (DisplayType = 32)
 *
 *  @author Jorg Janke
 *  @version $Id: MImage.java,v 1.5 2006/07/30 00:51:02 jjanke Exp $
 */
public class MImage extends X_AD_Image
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7361463683427300715L;

	/**
	 * 	Get MImage from Cache
	 *	@param ctx context
	 *	@param AD_Image_ID id
	 *	@return MImage
	 */
	public static MImage get (Properties ctx, int AD_Image_ID)
	{
		if (AD_Image_ID == 0)
			return new MImage (ctx, AD_Image_ID, null);
		//
		Integer key = new Integer (AD_Image_ID);
		MImage retValue = s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MImage (ctx, AD_Image_ID, null);
		if (retValue.get_ID () != 0 && Ini.isClient())
			s_cache.put (key, retValue);
		return retValue;
	} //	get

	/**	Cache						*/
	private static CCache<Integer,MImage> s_cache = new CCache<Integer,MImage>("AD_Image", 20);
	
	/**
	 *  Constructor
	 *  @param ctx context
	 *  @param AD_Image_ID image
	 *  @param trxName transaction
	 */
	public MImage(Properties ctx, int AD_Image_ID, String trxName)
	{
		super (ctx, AD_Image_ID, trxName);
		if (AD_Image_ID < 1)
			setName("-");
	}   //  MImage

	/**
	 * 	Load Constructor
	 *	@param ctx
	 *	@param rs
	 *  @param trxName transaction
	 */
	public MImage (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MImage

	
	/** The Image                   */
	private Image m_image = null;
	/** The Icon                   */
	private ImageIcon m_icon = null;

	/**
	 * 	Get Image
	 *	@return image or null
	 */
	public Image getImage ()
	{
		if (m_image != null)
		{
			return m_image;
		}
		
		//	Via byte array
		final byte[] data = getBinaryData();
		if (data != null && data.length > 0)
		{
			try
			{
				Toolkit tk = Toolkit.getDefaultToolkit();
				m_image = tk.createImage(data);
				return m_image;
			}
			catch (Exception e)
			{
				log.log(Level.WARNING, "(byteArray)", e);
				return null;
			}
		}
		//	Via URL
		URL url = getURL();
		if (url == null)
			return null;
		try
		{
			Toolkit tk = Toolkit.getDefaultToolkit();
			m_image = tk.getImage(url);
			return m_image;
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, "(URL)", e);
		}
		return null;
	}   //  getImage

	/**
	 * 	Get Icon
	 *	@return icon or null
	 */
	public Icon getIcon()
	{
		return getImageIcon();
	}   //  getIcon
	
	private final ImageIcon getImageIcon()
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
		catch (Exception e)
		{
			log.log(Level.WARNING, "Failed creating the icon from image on {0}. Returning null.", this);
			m_icon = null;
		}
		return m_icon;
	}
	
	/**
	 * Gets the icon, resized if needed to fit the given maximum size.
	 * 
	 * @param sizeMax
	 */
	public Icon getIcon(final Dimension sizeMax)
	{
		if (sizeMax == null)
		{
			return getIcon();
		}
		
		final ImageIcon icon = getImageIcon();
		if (icon == null)
		{
			return null;
		}

		final Dimension sizeOrig = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		final Dimension sizeNew = getScaledDimension(sizeOrig, sizeMax);
		if (sizeNew.equals(sizeOrig))
		{
			return icon;
		}

//		int type = BufferedImage.TYPE_INT_ARGB;
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
	
	private static final Dimension getScaledDimension(final Dimension size, final Dimension maxSize)
	{
		// credits: http://stackoverflow.com/questions/10245220/java-image-resize-maintain-aspect-ratio
		
	    final int widthOrig = size.width;
	    final int heightOrig = size.height;
	    final int widthMax = maxSize.width;
	    final int heightMax = maxSize.height;
	    
	    int widthNew;
	    int heightNew;

	    // first check if we need to scale width
	    if (widthMax > 0 && widthOrig > widthMax)
	    {
	        //scale width to fit
	        widthNew = widthMax;
	        //scale height to maintain aspect ratio
	        heightNew = (widthNew * heightOrig) / widthOrig;
	    }
	    else
	    {
	    	widthNew = widthOrig;
	    	heightNew = heightOrig;
	    }

	    // then check if we need to scale even with the new height
	    if (heightMax > 0 && heightNew > heightMax)
	    {
	        //scale height to fit instead
	        heightNew = heightMax;
	        //scale width to maintain aspect ratio
	        widthNew = (heightNew * widthOrig) / heightOrig;
	    }

	    return new Dimension(widthNew, heightNew);
	}

	/**
	 * 	Get URL
	 *	@return url or null
	 */
	private URL getURL()
	{
		String str = getImageURL();
		if (str == null || str.length() == 0)
			return null;
		
		URL url = null;
		try
		{
			//	Try URL directly
			if (str.indexOf("://") != -1)
				url = new URL(str);
			else	//	Try Resource
				url = getClass().getResource(str);
			//
			if (url == null)
				log.warning("Not found: " + str);
		}
		catch (Exception e)
		{
			log.warning("Not found: " + str + " - " + e.getMessage());
		}
		return url;
	}	//	getURL
	
	/**
	 * 	Set Image URL
	 *	@param ImageURL url
	 */
	@Override
	public void setImageURL (String ImageURL)
	{
		m_image = null;
		m_icon = null;
		super.setImageURL (ImageURL);
	}	//	setImageURL
	
	/**
	 * 	Set Binary Data
	 *	@param BinaryData data
	 */
	@Override
	public void setBinaryData (byte[] BinaryData)
	{
		m_image = null;
		m_icon = null;
		super.setBinaryData (BinaryData);
	}	//	setBinaryData
	
	/**
	 * 	Get Data 
	 *	@return data
	 */
	public byte[] getData()
	{
		byte[] data = super.getBinaryData ();
		if (data != null)
			return data;
		//	From URL
		String str = getImageURL();
		if (str == null || str.length() == 0)
		{
			log.config("No Image URL");
			return null;
		}
		//	Get from URL
		URL url = getURL();
		if (url == null)
		{
			log.config("No URL");
			return null;
		}
		try
		{
			URLConnection conn = url.openConnection();
		    conn.setUseCaches(false);
		    InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024*8];   //  8kB
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int length = -1;
			while ((length = is.read(buffer)) != -1)
				os.write(buffer, 0, length);
			is.close();
			data = os.toByteArray();
			os.close();
		    
		}
		catch (Exception e)
		{
			log.config (e.toString());
		}
		return data;
	}	//	getData
	
	/**
	 *  String Representation
	 *  @return String
	 */
	@Override
	public String toString()
	{
		return "MImage[ID=" + get_ID() + ",Name=" + getName() + "]";
	}   //  toString

	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);
		return true;
	}	//	beforeSave
	
}   //  MImage
