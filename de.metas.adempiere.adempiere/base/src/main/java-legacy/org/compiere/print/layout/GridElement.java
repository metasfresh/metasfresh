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

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Properties;

/**
 *	Grid Element.
 *  Simple Table with Rows/Columns, but no Headers
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: GridElement.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 */
public class GridElement extends PrintElement
{
	/**
	 *	Grid Element Constructor
	 *  Call setData to initialize content
	 *  @param rows max rows
	 *  @param cols max cols
	 */
	public GridElement(int rows, int cols)
	{
		m_rows = rows;
		m_cols = cols;
		m_textLayout = new TextLayout[rows][cols];
		m_iterator = new AttributedCharacterIterator[rows][cols];
		m_rowHeight = new int[rows];
		m_colWidth = new int[cols];
		//	explicit init
		for (int r = 0; r < m_rows; r++)
		{
			m_rowHeight[r] = 0;
			for (int c = 0; c < m_cols; c++)
			{
				m_textLayout[r][c] = null;
				m_iterator[r][c] = null;
			}
		}
		for (int c = 0; c < m_cols; c++)
			m_colWidth[c] = 0;
	}	//	GridElement

	/**	Gap between Rows		*/
	private int			m_rowGap = 3;
	/**	Gap between Columns		*/
	private int			m_colGap = 5;

	/** Rows				*/
	private int			m_rows;
	/**	Columns				*/
	private int			m_cols;
	/**	The Layout Data			*/
	private TextLayout[][] 	m_textLayout = null;
	/** Character Iterator		*/
	private AttributedCharacterIterator[][] m_iterator = null;	
	
	/**	Row Height			*/
	private int[]		m_rowHeight = null;
	/**	Column Width		*/
	private int[]		m_colWidth = null;
	/** Context				*/
	private FontRenderContext	m_frc = new FontRenderContext(null, true, true);

	/**
	 * 	Create TextLayout from Data and calculate size.
	 * 	Called from ParameterElement and Location
	 *  @param row row
	 *  @param col column
	 *  @param stringData info element
	 *  @param font font
	 *  @param foreground color for foreground
	 */
	public void setData (int row, int col, String stringData, Font font, Paint foreground)
	{
		if (stringData == null || stringData.length() == 0)
			return;
		//
	//	log.fine("setData - " + row + "/" + col + " - " + stringData);
		AttributedString aString = new AttributedString(stringData);
		aString.addAttribute(TextAttribute.FONT, font);
		aString.addAttribute(TextAttribute.FOREGROUND, foreground);
		AttributedCharacterIterator iter = aString.getIterator();
		TextLayout layout = new TextLayout(iter, m_frc);
		setData (row, col, layout, iter);
	}	//	setData

	/**
	 * 	Create TextLayout from Data and calculate size
	 *  @param row row
	 *  @param col column
	 *  @param layout single line layout
	 *  @param iter character iterator
	 */
	private void setData (int row, int col, TextLayout layout, AttributedCharacterIterator iter)
	{
		if (layout == null)
			return;
		if (p_sizeCalculated)
			throw new IllegalStateException("Size already calculated");
		if (row < 0 || row >= m_rows)
			throw new ArrayIndexOutOfBoundsException("Row Index=" + row + " Rows=" + m_rows);
		if (col < 0 || col >= m_cols)
			throw new ArrayIndexOutOfBoundsException("Column Index=" + col + " Cols=" + m_cols);
		//
		m_textLayout[row][col] = layout;
		m_iterator[row][col] = iter;
		//	Set Size
		int height = (int)(layout.getAscent() + layout.getDescent() + layout.getLeading())+1;
		int width = (int)layout.getAdvance()+1;
		if (m_rowHeight[row] < height)
			m_rowHeight[row] = height;
		if (m_colWidth[col] < width)
			m_colWidth[col] = width;
	}	//	setData

	/**
	 * 	Set Rpw/Column gap
	 * 	@param rowGap row gap
	 * 	@param colGap column gap
	 */
	public void setGap (int rowGap, int colGap)
	{
		m_rowGap = rowGap;
		m_colGap = colGap;
	}	//	setGap

	
	/**************************************************************************
	 * 	Layout & Calculate Image Size.
	 * 	Set p_width & p_height
	 * 	@return true if calculated
	 */
	protected boolean calculateSize()
	{
		p_height = 0;
		for (int r = 0; r < m_rows; r++)
		{
			p_height += m_rowHeight[r];
			if (m_rowHeight[r] > 0)
				p_height += m_rowGap;
		}
		p_height -= m_rowGap;	//	remove last
		p_width = 0;
		for (int c = 0; c < m_cols; c++)
		{
			p_width += m_colWidth[c];
			if (m_colWidth[c] > 0)
				p_width += m_colGap;
		}
		p_width -= m_colGap;		//	remove last
		return true;
	}	//	calculateSize

	/**
	 * 	Paint it
	 * 	@param g2D Graphics
	 *  @param pageStart top left Location of page
	 *  @param pageNo page number for multi page support (0 = header/footer) - ignored
	 *  @param ctx print context
	 *  @param isView true if online view (IDs are links)
	 */
	public void paint(Graphics2D g2D, int pageNo, Point2D pageStart, Properties ctx, boolean isView)
	{
		Point2D.Double location = getAbsoluteLocation(pageStart);
		float y = (float)location.y;
		//
		for (int row = 0; row < m_rows; row++)
		{
			float x = (float)location.x;
			for (int col = 0; col < m_cols; col++)
			{
				if (m_textLayout[row][col] != null)
				{
					float yy = y + m_textLayout[row][col].getAscent();
				//	if (m_iterator[row][col] != null)
				//		g2D.drawString(m_iterator[row][col], x, yy);
				//	else
						m_textLayout[row][col].draw(g2D, x, yy);
				}
				x += m_colWidth[col];
				if (m_colWidth[col] > 0)
					x += m_colGap;
			}
			y += m_rowHeight[row];
			if (m_rowHeight[row] > 0)
				y += m_rowGap;
		}
	}	//	paint

}	//	GridElement
