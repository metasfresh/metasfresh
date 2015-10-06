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
package org.compiere.print.layout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.image.ImageObserver;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MQuery;
import org.compiere.print.MPrintFormatItem;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;

/**
 *  Print Element
 *
 *  @author     Jorg Janke
 *  @version    $Id: PrintElement.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public abstract class PrintElement implements ImageObserver
{
	/**
	 *  Constructor
	 */
	protected PrintElement ()
	{
	}   //  PrintElement

	/**	Link Color					*/
	public static final Color		LINK_COLOR = Color.blue;

	/**	Calculated Size of Element	*/
	protected float					p_width = 0f;
	protected float					p_height = 0f;
	protected boolean				p_sizeCalculated = false;
	/**	Max Size of Element			*/
	protected float					p_maxWidth = 0f;
	protected float					p_maxHeight = 0f;
	/** Field Align Type			*/
	protected String 				p_FieldAlignmentType;
	/**	Location on Page			*/
	protected Point2D.Double		p_pageLocation = null;
	/** Loading Flag				*/
	private boolean					m_imageNotLoaded = true;
	
	/**	Logger			*/
	protected CLogger	log = CLogger.getCLogger(getClass());
	
	
	/**************************************************************************
	 * 	Get Calculated Width
	 * 	@return Width
	 */
	public float getWidth()
	{
		if (!p_sizeCalculated)
			p_sizeCalculated = calculateSize();
		return p_width;
	}	//	getWidth

	/**
	 * 	Get Calculated Height
	 * 	@return Height
	 */
	public float getHeight()
	{
		if (!p_sizeCalculated)
			p_sizeCalculated = calculateSize();
		return p_height;
	}	//	getHeight

	/**
	 * 	Get Calculated Height on page
	 *  @param pageNo page number
	 * 	@return Height
	 */
	public float getHeight (int pageNo)
	{
		return getHeight();
	}	//	getHeight

	/**
	 * 	Get number of pages
	 * 	@return page count (1)
	 */
	public int getPageCount()
	{
		return 1;
	}	//	getPageCount

	/**
	 * 	Layout and Calculate Size
	 * 	Set p_width & p_height
	 * 	@return true if calculated
	 */
	protected abstract boolean calculateSize();

	/**
	 * 	Layout Element
	 * 	@param maxWidth max width
	 * 	@param maxHeight max height
	 * 	@param isHeightOneLine just one line
	 * 	@param FieldAlignmentType alignment type (MPrintFormatItem.FIELD_ALIGN_*)
	 */
	public void layout (float maxWidth, float maxHeight, boolean isHeightOneLine,
		String FieldAlignmentType)
	{
		if (isHeightOneLine)
			p_maxHeight = -1f;
		else if (maxHeight > 0f)
			p_maxHeight = maxHeight;
		p_maxWidth = maxWidth;
		//
		p_FieldAlignmentType = FieldAlignmentType;
		if (p_FieldAlignmentType == null || p_FieldAlignmentType.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_Default))
			p_FieldAlignmentType = MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft;
		//
		p_sizeCalculated = calculateSize();
	}	//	layout

	/**
	 * 	Set Maximum Height
	 * 	@param maxHeight maximum height (0) is no limit
	 */
	public void setMaxHeight (float maxHeight)
	{
		p_maxHeight = maxHeight;
	}	//	setMaxHeight

	/**
	 * 	Set Maximum Width
	 * 	@param maxWidth maximum width (0) is no limit
	 */
	public void setMaxWidth (float maxWidth)
	{
		p_maxWidth = maxWidth;
	}	//	setMaxWidth

	/**
	 * 	Set Location within page.
	 * 	Called from LayoutEngine.layoutForm(), lauout(), createStandardFooterHeader()
	 * 	@param pageLocation location within page
	 */
	public void setLocation (Point2D pageLocation)
	{
		p_pageLocation = new Point2D.Double(pageLocation.getX(), pageLocation.getY());
	}	//	setLocation

	/**
	 * 	Get Location within page
	 * 	@return location within page
	 */
	public Point2D getLocation()
	{
		return p_pageLocation;
	}	//	getLocation

	/**
	 * 	Return Absolute Position
	 * 	@param pageStart start of page
	 * 	@return absolite position
	 */
	protected Point2D.Double getAbsoluteLocation(Point2D pageStart)
	{
		Point2D.Double retValue = new Point2D.Double(
			p_pageLocation.x + pageStart.getX(), p_pageLocation.y + pageStart.getY());
	//	log.finest( "PrintElement.getAbsoluteLocation", "PageStart=" + pageStart.getX() + "/" + pageStart.getY()
	//		+ ",PageLocaton=" + p_pageLocation.x + "/" + p_pageLocation.y + " => " + retValue.x + "/" + retValue.y);
		return retValue;
	}	//	getAbsoluteLocation

	/**
	 * 	Get relative Bounds of Element
	 * 	@return bounds relative position on page
	 */
	public Rectangle getBounds()
	{
		if (p_pageLocation == null)
			return new Rectangle (0,0, (int)p_width, (int)p_height);
		return new Rectangle ((int)p_pageLocation.x, (int)p_pageLocation.y, (int)p_width, (int)p_height);
	}	//	getBounds

	/**
	 * 	Get Drill Down value
	 * 	@param relativePoint relative Point
	 *  @param pageNo page number
	 * 	@return null (subclasses overwrite)
	 */
	public MQuery getDrillDown (Point relativePoint, int pageNo)
	{
		return null;
	}	//	getDrillDown

	/**
	 * 	Get Drill Across value
	 * 	@param relativePoint relative Point
	 *  @param pageNo page number
	 * 	@return null (subclasses overwrite)
	 */
	public MQuery getDrillAcross (Point relativePoint, int pageNo)
	{
		return null;
	}	//	getDrillAcross

	
	/**************************************************************************
	 * 	Translate Context if required.
	 *  If content is translated, the element needs to stay in the bounds
	 *  of the originally calculated size and need to align the field.
	 * 	@param ctx context
	 */
	public void translate (Properties ctx)
	{
	//	noop
	}	//	translate

	/**
	 * 	Content is translated
	 * 	@return false
	 */
	public boolean isTranslated()
	{
		return false;
	}	//	translate

	
	/**************************************************************************
	 * 	Paint/Print.
	 * 	@param g2D Graphics
	 *  @param pageNo page number for multi page support (0 = header/footer)
	 *  @param pageStart top left Location of page
	 *  @param ctx context
	 *  @param isView true if online view (IDs are links)
	 */
	public abstract void paint (Graphics2D g2D, int pageNo, Point2D pageStart, Properties ctx, boolean isView);

	
	/**************************************************************************
	 * 	Image Observer
	 * 	@param img image
	 * 	@param infoflags Observer flags
	 * 	@param x x coordinate
	 * 	@param y y coordinate
	 * 	@param width image width
	 * 	@param height image height
	 * 	@return false if the infoflags indicate that the image is completely loaded; true otherwise
	 */
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
	{
		//	copied from java.awt.component
		m_imageNotLoaded = (infoflags & (ALLBITS|ABORT)) == 0;
		if (CLogMgt.isLevelFinest())
			log.finest("Flags=" + infoflags
				+ ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height 
				+ " - NotLoaded=" + m_imageNotLoaded);
		return m_imageNotLoaded;
	}	//	imageUpdate

	/**
	 * 	Wait until Image is loaded.
	 * 	@param image image
	 * 	@return true if loaded
	 */
	public boolean waitForLoad (Image image)
	{
		long start = System.currentTimeMillis();
		Thread.yield();
		int count = 0;
		try
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			while (!toolkit.prepareImage(image, -1, -1, this))	//	ImageObserver calls imageUpdate
			{
				//	Timeout
				if (count > 1000)	//	about 20+ sec overall
				{
					log.severe (this + " - Timeout - " 
						+ (System.currentTimeMillis()-start) + "ms - #" + count);
					return false;
				}
				try
				{
					if (count < 10)
						Thread.sleep(10);
					else if (count < 100)
						Thread.sleep(15);
					else
						Thread.sleep(20);
				}
				catch (InterruptedException ex)
				{
					log.log(Level.SEVERE, "", ex);
					break;
				}
				count++;
			}
		}
		catch (Exception e)		//	java.lang.SecurityException
		{
			log.log(Level.SEVERE, "", e);
			return false;
		}
		if (count > 0)
			log.fine((System.currentTimeMillis()-start) + "ms - #" + count);
		return true;
	}	//	waitForLoad

	/**
	 * 	Get Detail Info from Sub-Class
	 *	@return detail info
	 */
	protected String getDetailInfo()
	{
		return "";
	}	//	getDetailInfo
	
	/**
	 * 	String Representation
	 * 	@return info
	 */
	public String toString()
	{
		String cn = getClass().getName();
		StringBuffer sb = new StringBuffer();
		sb.append(cn.substring(cn.lastIndexOf('.')+1)).append("[");
		sb.append("Bounds=").append(getBounds())
			.append(",Height=").append(p_height).append("(").append(p_maxHeight)
			.append("),Width=").append(p_width).append("(").append(p_maxHeight)
			.append("),PageLocation=").append(p_pageLocation);
		sb.append("]");
		return sb.toString();
	}	//	toString

}   //  PrintElement
