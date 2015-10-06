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

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Properties;

/**
 *	HTML Form Print ELement.
 *  Restrictions:
 *  - Label is not printed
 * 	- Alighnment is ignored
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: HTMLElement.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class HTMLElement extends PrintElement
{
	/**
	 * 	HTML String Constructor
	 * 	@param html html code
	 */
	public HTMLElement (String html)
	{
		if (html == null || html.equals(""))
			throw new IllegalArgumentException("HTMLElement is null");
		log.fine("Length=" + html.length()); 
		//	Create View
		m_renderer = HTMLRenderer.get(html);
	}	//	HTMLElement

	/**	View for Printing						*/
	private HTMLRenderer 	m_renderer;
	
	
	/**************************************************************************
	 * 	Layout and Calculate Size.
	 * 	Set p_width & p_height
	 * 	@return Size
	 */
	protected boolean calculateSize()
	{
		if (p_sizeCalculated)
			return true;
		//
		p_height = m_renderer.getHeight();
		p_width = m_renderer.getWidth();

		//	Limits
		if (p_maxWidth != 0f)
			p_width = p_maxWidth;
		if (p_maxHeight != 0f)
		{
			if (p_maxHeight == -1f)		//	one line only
				p_height = m_renderer.getHeightOneLine();
			else
				p_height = p_maxHeight;
		}
	//	System.out.println("HTMLElement.calculate size - Width="
	//		+ p_width + "(" + p_maxWidth + ") - Height=" + p_height + "(" + p_maxHeight + ")");
		//
		m_renderer.setAllocation((int)p_width, (int)p_height);
		return true;
	}	//	calculateSize

	/*************************************************************************

	/**
	 * 	Paint/Print.
	 *  Calculate actual Size.
	 *  The text is printed in the topmost left position - i.e. the leading is below the line
	 * 	@param g2D Graphics
	 *  @param pageStart top left Location of page
	 *  @param pageNo page number for multi page support (0 = header/footer) - ignored
	 *  @param ctx print context
	 *  @param isView true if online view (IDs are links)
	 */
	public void paint (Graphics2D g2D, int pageNo, Point2D pageStart, Properties ctx, boolean isView)
	{
		//	36.0/137.015625, Clip=java.awt.Rectangle[x=0,y=0,width=639,height=804], Translate=1.0/56.0, Scale=1.0/1.0, Shear=0.0/0.0
	//	log.finest( "HTMLElement.paint", p_pageLocation.x + "/" + p_pageLocation.y
	//		+ ", Clip=" + g2D.getClip()
	//		+ ", Translate=" + g2D.getTransform().getTranslateX() + "/" + g2D.getTransform().getTranslateY()
	//		+ ", Scale=" + g2D.getTransform().getScaleX() + "/" + g2D.getTransform().getScaleY()
	//		+ ", Shear=" + g2D.getTransform().getShearX() + "/" + g2D.getTransform().getShearY());
		//
		Point2D.Double location = getAbsoluteLocation(pageStart);
	//	log.finest( "HTMLElement.paint - PageStart=" + pageStart + ", Location=" + location);
		//
		Rectangle allocation = m_renderer.getAllocation();
		g2D.translate(location.x, location.y);
		m_renderer.paint(g2D, allocation);
		g2D.translate(-location.x, -location.y);
	}	//	paint

	/**
	 * 	String Representation
	 * 	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("HTMLElement[");
		sb.append("Bounds=").append(getBounds())
			.append(",Height=").append(p_height).append("(").append(p_maxHeight)
			.append("),Width=").append(p_width).append("(").append(p_maxHeight)
			.append("),PageLocation=").append(p_pageLocation).append(" - ");
		sb.append("]");
		return sb.toString();
	}	//	toString


	/**************************************************************************
	 * 	Is content HTML
	 *	@param content content
	 *	@return true if HTML
	 */
	public static boolean isHTML (Object content)
	{
		if (content == null)
			return false;
		String s = content.toString();
		if (s.length() < 20)	//	assumption
			return false;
		s = s.trim().toUpperCase();
		if (s.startsWith("<HTML>"))
			return true;
		return false;
	}	//	isHTML

}	//	HTMLElement
