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
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.model.MQuery;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	Layout Page
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Page.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class Page
{
	/**
	 *	Layout for Page
	 *  @param pageNo page
	 *  @param ctx context
	 */
	public Page (Properties ctx, int pageNo)
	{
		m_ctx = ctx;
		m_pageNo = pageNo;
		if (m_pageInfo == null || m_pageInfo.length() == 0)
			m_pageInfo = String.valueOf(m_pageNo);
	}	//	Page

	/**	Current Page No	(set here)				*/
	private static final String		CONTEXT_PAGE = "*Page";
	/** Page Count (set in Layout Emgine		*/
	public static final String		CONTEXT_PAGECOUNT = "*PageCount";
	/** Multi Page Info (set here)				*/
	private static final String		CONTEXT_MULTIPAGE = "*MultiPageInfo";
	/** Copy Info (set here)				*/
	private static final String		CONTEXT_COPY = "*CopyInfo";

	/** Report Name (set in Layout Engine)		*/
	public static final String		CONTEXT_REPORTNAME = "*ReportName";
	/** Report Header (set in Layout Engine)	*/
	public static final String		CONTEXT_HEADER = "*Header";
	/**	Current Date (set in Layout Engine)		*/
	public static final String		CONTEXT_DATE = "*CurrentDate";
	/**	Current Time (set in Layout Engine)		*/
	public static final String		CONTEXT_TIME = "*CurrentDateTime";

	/**	Page Number					*/
	private int				m_pageNo;
	/**	Page Number					*/
	private int				m_pageCount = 1;
	/** Page Count					*/
	private String			m_pageInfo;
	/**	Context						*/
	private Properties 		m_ctx;
	/** Page content				*/
	private ArrayList<PrintElement>	m_elements = new ArrayList<PrintElement>();
	/** Background Image */
	private Image m_image = null;

	/**
	 * 	Get Page No
	 * 	@return page no
	 */
	public int getPageNo()
	{
		return m_pageNo;
	}	//	getPageNo

	/**
	 * 	Get Page Info
	 * 	@return page info
	 */
	public String getPageInfo()
	{
		return m_pageInfo;
	}	//	getPageInfo

	/**
	 * 	Set Page Info.
	 *  Enhanced pagae no, e.g.,  7(2,3)
	 * 	@param pageInfo page info
	 */
	public void setPageInfo (String pageInfo)
	{
		if (m_pageInfo == null || m_pageInfo.length() == 0)
			m_pageInfo = String.valueOf(m_pageNo);
		m_pageInfo = pageInfo;
	}	//	getPageInfo

	/**
	 * 	Set Page Info
	 * 	@param pageCount page count
	 */
	public void setPageCount (int pageCount)
	{
		m_pageCount = pageCount;
	}	//	setPageCount

	/**
	 * 	Add Print Element to Page
	 * 	@param element print element
	 */
	public void addElement (PrintElement element)
	{
		if (element != null)
			m_elements.add(element);
	}	//	addElement

	/*************************************************************************/

	/**
	 * 	Paint Page on Graphics in Bounds
	 *
	 * 	@param g2D graphics
	 * 	@param bounds page bounds
	 *  @param isView true if online view (IDs are links)
	 *  @param isCopy this print is a copy
	 */
	public void paint (Graphics2D g2D, Rectangle bounds, boolean isView, boolean isCopy)
	{
		Env.setContext(m_ctx, CONTEXT_PAGE, m_pageInfo);
	//	log.trace( "PrintContext", CONTEXT_PAGE + "=" + m_pageInfo);
		//
		StringBuffer sb = new StringBuffer();
		if (m_pageCount != 1)		//	set to "Page 1 of 2"
			sb.append(Msg.getMsg(m_ctx, "Page")).append(" ")
				.append(m_pageNo)
				.append(" ").append(Msg.getMsg(m_ctx, "of")).append(" ")
				.append(m_pageCount);
		else
			sb.append(" ");
		Env.setContext(m_ctx, CONTEXT_MULTIPAGE, sb.toString());
	//	log.trace( "PrintContext", CONTEXT_MULTIPAGE + "=" + sb.toString());
		//
		sb = new StringBuffer();
		if (isCopy)					//	set to "(Copy)"
			sb.append("(")
				.append(Msg.getMsg(m_ctx, "DocumentCopy"))
				.append(")");
		else
			sb.append(" ");
		Env.setContext(m_ctx, CONTEXT_COPY, sb.toString());
	//	log.trace( "PrintContext copy=" + isCopy, CONTEXT_COPY + "=" + sb.toString());

		//	Paint Background
		g2D.setColor(Color.white);
		g2D.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		//
		//backgroundImage
		if(m_image != null)
		{	
			int x = (bounds.width/2) - (m_image.getWidth(null)/2); 
			int y = (bounds.height/2) - (m_image.getHeight(null)/2); 
			g2D.drawImage(m_image, x  ,y ,null);
		}
		//
		Point pageStart = new Point(bounds.getLocation());
		for (int i = 0; i < m_elements.size(); i++)
		{
			PrintElement e = m_elements.get(i);
			e.paint(g2D, m_pageNo, pageStart, m_ctx, isView);
		}
	}	//	paint

	/*************************************************************************/

	/**
	 * 	Get DrillDown value
	 * 	@param relativePoint relative Point
	 * 	@return if found NamePait or null
	 */
	public MQuery getDrillDown (Point relativePoint)
	{
		MQuery retValue = null;
		for (int i = 0; i < m_elements.size() && retValue == null; i++)
		{
			PrintElement element = m_elements.get(i);
			retValue = element.getDrillDown (relativePoint, m_pageNo);
		}
		return retValue;
	}	//	getDrillDown

	/**
	 * 	Get DrillAcross value
	 * 	@param relativePoint relative Point
	 * 	@return if found Query or null
	 */
	public MQuery getDrillAcross (Point relativePoint)
	{
		MQuery retValue = null;
		for (int i = 0; i < m_elements.size() && retValue == null; i++)
		{
			PrintElement element = m_elements.get(i);
			retValue = element.getDrillAcross (relativePoint, m_pageNo);
		}
		return retValue;
	}	//	getDrillAcross
	
	/**
	 * set Background Image
	 * @param image
	 */
	public void setBackgroundImage(Image image)
	{
		m_image = image;
	}
	
	/**
	 * get Background Image
	 * @return
	 */
	public Image getBackgroundImage()
	{
		return m_image;
	}
	
	/**
	 * 	String Representation
	 * 	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("Page[");
		sb.append(m_pageNo).append(",Elements=").append(m_elements.size());
		sb.append("]");
		return sb.toString();
	}	//	toString

}	//	Page
