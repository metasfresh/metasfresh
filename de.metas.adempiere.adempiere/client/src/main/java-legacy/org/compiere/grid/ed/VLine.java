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
package org.compiere.grid.ed;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.border.AbstractBorder;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.util.Language;

/**
 *  Horizontal Line with Text
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VLine.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VLine extends AbstractBorder
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3510090015856709962L;

	/**
	 *  IDE Bean Constructor
	 */
	public VLine()
	{
		this("");
	}   //  VLine

	/**
	 *	Constructor
	 *  @param header
	 */
	public VLine(String header)
	{
		super();
		setHeader(header);
	}	//	VLine

	/** Header          */
	private String  m_header = "";

	private Font    m_font = AdempierePLAF.getFont_Label();
	private Color   m_color = AdempierePLAF.getTextColor_Label();

	/** Gap between element     */
	public final static int    GAP = 5;
	/** space for characters below line (y) */
	public final static int    SPACE = 4;       //  used in VPanel

	/**
	 * Paint Border
	 * @param c the component for which this border is being painted
	 * @param g the paint graphics
	 * @param x the x position of the painted border
	 * @param y the y position of the painted border
	 * @param w the width of the painted border
	 * @param h the height of the painted border
	 */
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
	{
		Graphics copy = g.create();
		if (copy != null)
		{
			try
			{
				copy.translate(x, y);
				paintLine(c, copy, w, h);
			}
			finally
			{
				copy.dispose();
			}
		}
	}	//	paintBorder

	/**
	 *	Paint Line with Header
	 *  @param c
	 *  @param g
	 *  @param w
	 *  @param h
	 */
	private void paintLine (Component c, Graphics g, int w, int h)
	{
		int y = h-SPACE;
		//	Line
		g.setColor(Color.darkGray);
		g.drawLine(GAP, y, w-GAP, y);
		g.setColor(Color.white);
		g.drawLine(GAP, y+1, w-GAP, y+1);       //	last part of line

		if (m_header == null || m_header.length() == 0)
			return;

		//	Header Text
		g.setColor(m_color);
		g.setFont(m_font);
		int x = GAP;
		if (!Language.getLoginLanguage().isLeftToRight())
		{
		}
		g.drawString(m_header, GAP, h-SPACE-1);
	}	//	paintLine

	/**
	 *  Set Header
	 *  @param newHeader String - '_' are replaced by spaces
	 */
	public void setHeader(String newHeader)
	{
		m_header = newHeader.replace('_', ' ');
	}   //  setHeader

	/**
	 *  Get Header
	 *  @return header string
	 */
	public String getHeader()
	{
		return m_header;
	}   //  getHeader

}	//	VLine
