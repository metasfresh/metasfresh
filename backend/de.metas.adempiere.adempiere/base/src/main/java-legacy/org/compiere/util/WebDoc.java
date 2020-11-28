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
package org.compiere.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;

import org.apache.ecs.AlignType;
import org.apache.ecs.xhtml.a;
import org.apache.ecs.xhtml.body;
import org.apache.ecs.xhtml.h1;
import org.apache.ecs.xhtml.head;
import org.apache.ecs.xhtml.html;
import org.apache.ecs.xhtml.img;
import org.apache.ecs.xhtml.link;
import org.apache.ecs.xhtml.meta;
import org.apache.ecs.xhtml.table;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.title;
import org.apache.ecs.xhtml.tr;
import org.compiere.Adempiere;


/**
 *  XHTML Document.
 *
 *  @author Jorg Janke
 *  @version  $Id: WebDoc.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class WebDoc
{
	/**
	 *  Create styled Document with Title
	 *  @param plain if true DO NOT add standard.css
	 *  @param title optional header title and h1 
	 *  @param swingClient true if Java Swing Client - browser otherwise
	 *  @return Document
	 */
	public static WebDoc create (boolean plain, String title, boolean swingClient)
	{
		WebDoc doc = new WebDoc();
		doc.setUp (plain, swingClient, title);
		return doc;
	}   //  create
	
	/**
	 *  Create styled web Document with Title
	 *  @param title optional header title and h1 
	 *  @return Document
	 */
	public static WebDoc create (String title)
	{
		final boolean plain = false;
		final boolean swingClient = false;
		return create (plain, title, swingClient);
	}   //  create

	/** Non breaking Space					*/
	public static final String	NBSP	= "&nbsp;";
	
	
	/**************************************************************************
	 *  Create new XHTML Document structure
	 */
	private WebDoc ()
	{
		super();
	}

	private html    m_html = new html();
	private head    m_head = new head();
	private body    m_body = new body();
	private table	m_table = null;
	private tr		m_topRow = null;
	private td		m_topRight = null;
	private td		m_topLeft = null;

	/**
	 *  Set up Document
	 *  @param plain if true then DO NOT add stylesheet
	 *  @param swingClient true if Java Client - browser otherwise
	 *  @param title header title and h1
	 */
	private void setUp (final boolean plain, final boolean swingClient, final String title)
	{
		m_html.addElement(m_head);
		m_html.addElement(m_body);
		m_body.addElement(new a().setName("top"));
		if (title != null)
			m_head.addElement(new title(title));
		
		if (plain)
			return;
		
		//	css, js
		if (swingClient)
		{
			m_head.addElement(new StoredHtmlSrc("STYLE", "org/compiere/images/standard.css"));
		}
		else
		{
			m_head.addElement(new link(WebEnv.getStylesheetURL(), link.REL_STYLESHEET, link.TYPE_CSS));
		}
		m_head.addElement(new meta().setHttpEquiv("Content-Type", "text/html; charset=UTF-8"));
		m_head.addElement(new meta().setName("description", Adempiere.getName() + " HTML UI"));

		m_table = new table("0", "0", "0", "100%", null);	//	spacing 2
		m_topRow = new tr();
		
		// Title
		m_topLeft = new td();
		if (title == null)
		{
			m_topLeft.addElement(NBSP);
		}
		else
		{
			m_topLeft.addElement(new h1(title));
		}
		m_topRow.addElement(m_topLeft);
		
		// Logo
		m_topRight = new td().setAlign("right");
		
		if (swingClient)
		{
			final URL logoURL = Adempiere.getProductLogoLargeURL();
			if (logoURL != null)
			{
				m_topRight.addElement(new img(logoURL.toString())
						.setAlign(AlignType.RIGHT)
						.setAlt(Adempiere.getName()));
			}
		}
		else
		{
			final String logoURL = WebEnv.getLogoURL(300);
			img img = new img(logoURL).setAlign(AlignType.RIGHT);
			m_topRight.addElement(img);
		}
		m_topRow.addElement(m_topRight);
		m_table.addElement(m_topRow);		
		//
		m_body.addElement(m_table);
	}   //  setUp

	/**
	 * 	Set css Classes
	 *	@param tableClass optional class for table
	 *	@param tdClass optional class for left/right td
	 */
	public void setClasses (String tableClass, String tdClass)
	{
		if (m_table != null && tableClass != null)
			m_table.setClass(tableClass);
		if (m_topLeft != null && tdClass != null)
			m_topLeft.setClass(tdClass);
		if (m_topRight != null && tdClass != null)
			m_topRight.setClass(tdClass);
	}	//	setClasses

	
	/**
	 *  Get Body
	 *  @return Body
	 */
	public body getBody()
	{
		return m_body;
	}   //  getBody

	/**
	 *  Get Head
	 *  @return Header
	 */
	public head getHead()
	{
		return m_head;
	}   //  getHead

	/**
	 * 	Get Table (no class set)
	 *	@return table
	 */
	public table getTable()
	{
		return m_table;
	}	//	getTable

	/**
	 * 	Get Table Row (no class set)
	 *	@return table row
	 */
	public tr getTopRow()
	{
		return m_topRow;
	}	//	getTopRow
	/**
	 * 	Get Table Data Left (no class set)
	 *	@return table data
	 */
	public td getTopLeft()
	{
		return m_topLeft;
	}	//	getTopLeft
	
	/**
	 * 	Get Table Data Right (no class set)
	 *	@return table data
	 */
	public td getTopRight()
	{
		return m_topRight;
	}	//	getTopRight
	
	/**
	 *  String representation
	 *  @return String
	 */
	@Override
	public String toString()
	{
		return m_html.toString();
	}   //  toString

	/**
	 *  Output Document
	 *  @param out out
	 */
	public void output (OutputStream out)
	{
		m_html.output(out);
	}   //  output

	/**
	 *  Output Document
	 *  @param out out
	 */
	public void output (PrintWriter out)
	{
		m_html.output(out);
	}   //  output

	/**
	 * 	Add Popup Center
	 * 	@param nowrap set nowrap in td
	 *	@return null or center single td
	 */
	public td addPopupCenter(boolean nowrap)
	{
		if (m_table == null)
			return null;
		//
		td center = new td ("popupCenter", AlignType.CENTER, AlignType.MIDDLE, nowrap);
		center.setColSpan(2);
		m_table.addElement(new tr()
			.addElement(center));
		return center;
	}	//	addPopupCenter
}   //  WDoc
