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

import de.metas.util.Check;
import de.metas.workflow.WFNodeId;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextLayout;

/**
 * Work Flow Line between Nodes.
 * Coordinates based on WFContentPanel.
 *
 * @author Jorg Janke
 * @version $Id: WFLine.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WFLine extends Component
{
	private static final long serialVersionUID = 7599996355185021897L;

	private final WorkflowNodeTransitionModel model;
	private Boolean _toJoinAnd;

	public WFLine(@NonNull final WorkflowNodeTransitionModel model)
	{
		this.model = model;
		//	setOpaque(false);
		setFocusable(false);
		//
		m_description = model.getDescription();
		if (m_description != null && m_description.length() > 0)
			m_description = "{" + model.getSeqNo() + ": " + m_description + "}";
	}    //	WFLine

	/**
	 * From Node
	 */
	private Rectangle m_from = null;
	/**
	 * To Node
	 */
	private Rectangle m_to = null;
	/**
	 * Description
	 */
	private String m_description;
	/**
	 * Visited value
	 */
	private boolean m_visited = false;

	/**
	 * Set From/To rectangle.
	 * Called from WFLayoutManager.layoutContainer
	 *
	 * @param from from node rectangle
	 * @param to   to node rectangle
	 */
	public void setFromTo(final Rectangle from, final Rectangle to)
	{
		m_from = from;
		m_to = to;
	}

	public WFNodeId getFromNodeId()
	{
		return model.getFromNodeId();    //	Node ->
	}

	public WFNodeId getToNodeId()
	{
		return model.getNextNodeId();    //	-> Next
	}

	/**
	 * Set Visited.
	 *
	 * @param visited visited
	 */
	public void setVisited(final boolean visited)
	{
		m_visited = visited;
	}    //	setVisited

	/**
	 * From: Right - To: Top		= left \ down
	 */
	private boolean isRightTop()                    //	\
	{
		return (m_from.x + m_from.width <= m_to.x        //	right.bottom - left.top
				&& m_from.y + m_from.height <= m_to.y);
	}

	/**
	 * From: Bottom - To: Top		= top | down
	 */
	private boolean isBottomTop()                    //	|
	{
		return (m_from.y + m_from.height <= m_to.y);
	}

	/**
	 * From: Top - To: Bottom		= bottom / up
	 */
	private boolean isTopBottom()                    //	|
	{
		return (m_to.y + m_to.height <= m_from.y);
	}

	/**
	 * From: Left - To: Right		=	right o <- o left
	 */
	private boolean isLeftRight()                    // 	->
	{
		return (m_to.x + m_to.width <= m_from.x);
	}

	/**************************************************************************
	 * 	Paint it.
	 *	Coordinates based on WFContentPanel.
	 *    @param g Graph
	 */
	public void paint(final Graphics g)
	{
		if (m_from == null || m_to == null)
			return;

		final Polygon arrow = new Polygon();
		Point from = null;
		Point to = null;

		//	
		if (isRightTop())
		{
			from = addPoint(arrow, m_from, SwingConstants.RIGHT, true);
			to = addPoint(arrow, m_to, SwingConstants.TOP, false);
		}
		else if (isBottomTop())
		{
			from = addPoint(arrow, m_from, SwingConstants.BOTTOM, true);
			to = addPoint(arrow, m_to, SwingConstants.TOP, false);
		}
		//	
		else if (isTopBottom())
		{
			from = addPoint(arrow, m_from, SwingConstants.TOP, true);
			to = addPoint(arrow, m_to, SwingConstants.BOTTOM, false);
		}
		else if (isLeftRight())
		{
			from = addPoint(arrow, m_from, SwingConstants.LEFT, true);
			to = addPoint(arrow, m_to, SwingConstants.RIGHT, false);
		}
		else //	if (isRightLeft())
		{
			from = addPoint(arrow, m_from, SwingConstants.RIGHT, true);
			to = addPoint(arrow, m_to, SwingConstants.LEFT, false);
		}

		/**
		 *	Paint Arrow:
		 * 	Unconditional: no fill - black text
		 *	Conditional: red fill - red text
		 * 	Visited: green line
		 *	NotVisited: black line
		 *	Split/Join: AND: Magenta Dot -- XOR: -
		 */
		if (!model.isUnconditional())
		{
			g.setColor(Color.red);
			g.fillPolygon(arrow);        //	fill
		}
		if (m_visited)
			g.setColor(Color.green);
		else
			g.setColor(Color.black);
		g.drawPolygon(arrow);            //	line

		//	Paint Dot for AND From
		if (model.getFromSplitType().isAND())
		{
			g.setColor(Color.magenta);
			g.fillOval(from.x - 3, from.y - 3, 6, 6);
		}
		//	Paint Dot for AND To
		if (isToJoinAnd())
		{
			g.setColor(Color.magenta);
			g.fillOval(to.x - 3, to.y - 3, 6, 6);
		}

		//	Paint Description in red
		if (!Check.isBlank(m_description))
		{
			Graphics2D g2D = (Graphics2D)g;
			Font font = new Font("Dialog", Font.PLAIN, 9);
			if (model.isUnconditional())
				g2D.setColor(Color.black);
			else
				g2D.setColor(Color.red);
			TextLayout layout = new TextLayout(m_description, font, g2D.getFontRenderContext());

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
			y -= (layout.getAscent() - 3);        //	above center

			//	Adjust -
			x -= (layout.getAdvance() / 2);        //	center
			if (x < 2)
				x = 2;

			layout.draw(g2D, x, y);
		}

	}    //	paintComponent

	/**
	 * Get Point of Rectangle
	 *
	 * @param arrow Polygon to draw arrow
	 * @param rect  Rectangle (icon)
	 * @param pos   SwingConstants.BOTTOM / TOP / RIGHT / LEFT
	 * @param from  if true from (base) else to (tip) of arrow
	 * @return point docking position two point away
	 */
	private Point addPoint(Polygon arrow, Rectangle rect, int pos, boolean from)
	{
		int x = rect.x;
		int y = rect.y;
		Point point = null;

		if (pos == SwingConstants.TOP)
		{
			x += rect.width / 2;
			if (from)
			{
				arrow.addPoint(x - 2, y);
				arrow.addPoint(x + 2, y);
			}
			else
				arrow.addPoint(x, y);
			point = new Point(x, y - 2);
		}
		else if (pos == SwingConstants.RIGHT)
		{
			x += rect.width;
			y += rect.height / 2;
			if (from)
			{
				arrow.addPoint(x, y - 2);
				arrow.addPoint(x, y + 2);
			}
			else
				arrow.addPoint(x, y);
			point = new Point(x + 2, y);
		}
		else if (pos == SwingConstants.LEFT)
		{
			y += rect.height / 2;
			if (from)
			{
				arrow.addPoint(x, y - 2);
				arrow.addPoint(x, y + 2);
			}
			else
				arrow.addPoint(x, y);
			point = new Point(x - 2, y);
		}
		else //	if (pos == SwingConstants.BOTTOM)
		{
			x += rect.width / 2;
			y += rect.height;
			if (from)
			{
				arrow.addPoint(x - 2, y);
				arrow.addPoint(x + 2, y);
			}
			else
				arrow.addPoint(x, y);
			point = new Point(x, y + 2);
		}
		return point;
	}    //	getPoint

	public String toString()
	{
		return "WFLine[" + getFromNodeId() + "->" + getToNodeId() + "]";
	}

	private boolean isToJoinAnd()
	{
		if (_toJoinAnd == null)
		{
			final WorkflowNodeModel next = model.getNextNode();
			_toJoinAnd = next.getJoinType().isAnd();
		}
		if (_toJoinAnd != null)
		{
			return _toJoinAnd;
		}
		return false;
	}

}    //	WFLine
