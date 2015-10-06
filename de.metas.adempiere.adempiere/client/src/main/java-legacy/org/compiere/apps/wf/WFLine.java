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
package org.compiere.apps.wf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.font.TextLayout;

import javax.swing.SwingConstants;

import org.compiere.util.CLogger;
import org.compiere.wf.MWFNodeNext;


/**
 *	Work Flow Line between Nodes.
 *	Coordinates based on WFContentPanel.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: WFLine.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WFLine extends Component
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7599996355185021897L;

	/**
	 * 	Create Line
	 * 	@param next model
	 */
	public WFLine (MWFNodeNext next)
	{
		m_next = next;
	//	setOpaque(false);
		setFocusable(false);
		//
		m_description = next.getDescription();
		if (m_description != null && m_description.length() > 0)
			m_description = "{" + String.valueOf(next.getSeqNo()) 
				+ ": " + m_description + "}";
	}	//	WFLine

	/**	Model					*/
	private MWFNodeNext 	m_next = null;
	/**	From Node				*/
	private Rectangle		m_from = null;
	/**	To Node					*/
	private Rectangle		m_to = null;
	/**	Descriprion				*/
	private String 			m_description = null;
	/** Visited value			*/
	private boolean			m_visited = false;
	/**	Logger					*/
	private static CLogger	log = CLogger.getCLogger(WFLine.class);
	
	/**
	 * 	Get From rectangle
	 * 	@return from node rectangle
	 */
	public Rectangle getFrom()
	{
		return m_from;
	}	//	getFrom

	/**
	 * 	Get To rectangle
	 * 	@return to node rectangle
	 */
	public Rectangle getTo()
	{
		return m_to;
	}	//	getTo

	/**
	 * 	Set From/To rectangle.
	 * 	Called from WFLayoutManager.layoutContainer
	 * 	@param from from node rectangle
	 * 	@param to to node rectangle
	 */
	public void setFromTo (Rectangle from, Rectangle to)
	{
		m_from = from;
		m_to = to;
	}	//	setFrom

	/**
	 * 	Get From Node ID
	 * 	@return from node id
	 */
	public int getAD_WF_Node_ID()
	{
		return m_next.getAD_WF_Node_ID();	//	Node ->
	}	//	getAD_WF_Node_ID

	/**
	 * 	Get To Node ID
	 * 	@return to node id
	 */
	public int getAD_WF_Next_ID()
	{
		return m_next.getAD_WF_Next_ID();	//	-> Next
	}	//	getAD_WF_Next_ID

	/**
	 * 	Set Visited.
	 *	@param visited visited
	 */
	public void setVisited (boolean visited)
	{
		m_visited = visited;
	}	//	setVisited

	/**
	 * 	From: Right - To: Top		= left \ down
	 */
	private boolean isRightTop()					//	\
	{
		return (m_from.x+m_from.width <= m_to.x		//	right.bottom - left.top
			&& m_from.y+m_from.height  <= m_to.y);	
	}
	/**
	 *	From: Bottom - To: Top		= top | down 	
	 */
	private boolean isBottomTop()					//	|
	{
		return (m_from.y+m_from.height <= m_to.y);
	}
	/**
	 * 	From: Top - To: Bottom		= bottom / up
	 */
	private boolean isTopBottom()					//	|
	{
		return (m_to.y+m_to.height <= m_from.y);
	}
	/**
	 *	From: Left - To: Right		=	right o <- o left
 	 */
	private boolean isLeftRight()					// 	->
	{
		return (m_to.x+m_to.width <= m_from.x);
	}
	
	
	/**************************************************************************
	 * 	Paint it.
	 *	Coordinates based on WFContentPanel.
	 * 	@param g Graph
	 */
	public void paint (Graphics g)
	{
		if (m_from == null || m_to == null)
			return;
		
		Polygon arrow = new Polygon();
		Point from = null;
		Point to = null;
		
		//	
		if (isRightTop())
		{
			from = addPoint (arrow, m_from, SwingConstants.RIGHT, true);
			to = addPoint (arrow, m_to, SwingConstants.TOP, false);
		}
		else if (isBottomTop())
		{
			from = addPoint (arrow, m_from, SwingConstants.BOTTOM, true);
			to = addPoint (arrow, m_to, SwingConstants.TOP, false);
		}
		//	
		else if (isTopBottom())
		{
			from = addPoint (arrow, m_from, SwingConstants.TOP, true);
			to = addPoint (arrow, m_to, SwingConstants.BOTTOM, false);
		}
		else if (isLeftRight())
		{
			from = addPoint (arrow, m_from, SwingConstants.LEFT, true);
			to = addPoint (arrow, m_to, SwingConstants.RIGHT, false);
		}
		else //	if (isRightLeft())
		{
			from = addPoint (arrow, m_from, SwingConstants.RIGHT, true);
			to = addPoint (arrow, m_to, SwingConstants.LEFT, false);
		}

		/**
		 *	Paint Arrow:
		 * 	Unconditional: no fill - black text
		 *	Conditional: red fill - red text
		 * 	Visited: green line
		 *	NotVisited: black line
		 *	Split/Join: AND: Magenta Dot -- XOR: -
		 */
		if (!m_next.isUnconditional())
		{
			g.setColor(Color.red);	
			g.fillPolygon(arrow);		//	fill
		}
		if (m_visited)
			g.setColor(Color.green);
		else
			g.setColor(Color.black);
		g.drawPolygon(arrow);			//	line
		
		//	Paint Dot for AND From
		if (m_next.isFromSplitAnd())
		{
			g.setColor(Color.magenta);
			g.fillOval(from.x-3, from.y-3, 6, 6);
		}
		//	Paint Dot for AND To
		if (m_next.isToJoinAnd())
		{
			g.setColor(Color.magenta);
			g.fillOval(to.x-3, to.y-3, 6, 6);
		}
		
		//	Paint Description in red
		if (m_description != null)
		{
			Graphics2D g2D = (Graphics2D)g;
			Font font = new Font("Dialog", Font.PLAIN, 9);
			if (m_next.isUnconditional())
				g2D.setColor(Color.black);
			else
				g2D.setColor(Color.red);
			TextLayout layout = new TextLayout (m_description, font, g2D.getFontRenderContext());
			
			//	Mid Point
			int x = 0;
			if (from.x < to.x)
				x = from.x + ((to.x - from.x) / 2);
			else
				x = to.x + ((from.x - to.x) / 2);
			int y = 0;
			if (from.y < to.y)
				y = from.y + ((to.y - from.y) / 2);
			else
				y = to.y + ((from.y - to.y) / 2);

			//	Adjust |
			y -= (layout.getAscent() - 3);		//	above center
			
			//	Adjust -
			x -= (layout.getAdvance() / 2);		//	center
			if (x < 2)
				x = 2;
			
			layout.draw(g2D, x, y);
		}
		
	}	//	paintComponent

	/**
	 * 	Get Point of Rectangle
	 * 	@param arrow Polygon to draw arrow
	 * 	@param rect Rectangle (icon)
	 * 	@param pos SwingConstants.BOTTOM / TOP / RIGHT / LEFT
	 * 	@param from if true from (base) else to (tip) of arrow
	 * 	@return point docking position two point away
	 */
	private Point addPoint (Polygon arrow, Rectangle rect, int pos, boolean from)
	{
		int x = rect.x;
		int y = rect.y;
		Point point = null;

		if (pos == SwingConstants.TOP)
		{
			x += rect.width/2;
			if (from)
			{
				arrow.addPoint(x-2, y);
				arrow.addPoint(x+2, y);
			}
			else
				arrow.addPoint(x, y);
			point = new Point (x, y-2);
		}
		else if (pos == SwingConstants.RIGHT)
		{
			x += rect.width;
			y += rect.height/2;
			if (from)
			{
				arrow.addPoint(x, y-2);
				arrow.addPoint(x, y+2);
			}
			else
				arrow.addPoint(x, y);
			point = new Point (x+2, y);
		}
		else if (pos == SwingConstants.LEFT)
		{
			y += rect.height/2;
			if (from)
			{
				arrow.addPoint(x, y-2);
				arrow.addPoint(x, y+2);
			}
			else
				arrow.addPoint(x, y);
			point = new Point (x-2, y);
		}
		else //	if (pos == SwingConstants.BOTTOM)
		{
			x += rect.width/2;
			y += rect.height;
			if (from)
			{
				arrow.addPoint(x-2, y);
				arrow.addPoint(x+2, y);
			}
			else
				arrow.addPoint(x, y);
			point = new Point (x, y+2);
		}
		return point;
	}	//	getPoint

	/**
	 * 	String Representation
	 * 	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("WFLine[");
		sb.append(getAD_WF_Node_ID()).append("->").append(getAD_WF_Next_ID());
		sb.append("]");
		return sb.toString();
	}	//	toString

}	//	WFLine
