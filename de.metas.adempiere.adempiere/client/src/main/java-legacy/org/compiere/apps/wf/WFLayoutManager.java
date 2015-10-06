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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;

import org.compiere.util.CLogger;

/**
 *	WorkFlow Layout Manager
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: WFLayoutManager.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WFLayoutManager implements LayoutManager
{
	/**
	 * 	Constructor
	 */
	public WFLayoutManager()
	{
	}	//	WFLayoutManager

	/**	Logger			*/
	private static CLogger	log = CLogger.getCLogger(WFLayoutManager.class);
	/**	Cached Size			*/
	private Dimension	m_size = null;

	/**
	 * If the layout manager uses a per-component string,
	 * adds the component <code>comp</code> to the layout,
	 * associating it
	 * with the string specified by <code>name</code>.
	 *
	 * @param name the string to be associated with the component
	 * @param comp the component to be added
	 * @see java.awt.LayoutManager#addLayoutComponent(String, Component)
	 */
	public void addLayoutComponent (String name, Component comp)
	{
		invalidateLayout();
	}	//	addLayoutComponent

	/**
	 * Removes the specified component from the layout.
	 * @param comp the component to be removed
	 * @see java.awt.LayoutManager#removeLayoutComponent(Component)
	 */
	public void removeLayoutComponent(Component comp)
	{
		if (comp == null)
			return;
		invalidateLayout();
	}	//	removeLayoutComponent

	/**
	 *	Calculates the preferred size dimensions for the specified
	 *	container, given the components it contains.
	 *	@param parent the container to be laid out
	 * 	@return preferred size
	 * 	@see #minimumLayoutSize
	 */
	public Dimension preferredLayoutSize(Container parent)
	{
		if (m_size == null)
			layoutContainer(parent);
		return m_size;
	}	//	preferredLayoutSize

	/**
	 * 	Calculates the minimum size dimensions for the specified
	 * 	container, given the components it contains.
	 * 	@param parent the component to be laid out
	 * 	@return preferred size
	 * 	@see #preferredLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent)
	{
		return preferredLayoutSize(parent);
	}	//	minimumLayoutSize

	
	/**************************************************************************
	 *	Lays out the specified container.
	 *	@param parent the container to be laid out
	 * @see java.awt.LayoutManager#layoutContainer(Container)
	 */
	public void layoutContainer (Container parent)
	{
		Insets insets = parent.getInsets();
		//
		int width = insets.left;
		int height = insets.top;

		//	We need to layout
		if (needLayout(parent))
		{
			int x = 5;
			int y = 5;
			//	Go through all components
			for (int i = 0; i < parent.getComponentCount(); i++)
			{
				Component comp = parent.getComponent(i);
				if (comp.isVisible() && comp instanceof WFNode)
				{
					Dimension ps = comp.getPreferredSize();
					comp.setLocation(x, y);
					comp.setBounds(x, y, ps.width, ps.height);
					//
					width = x + ps.width;
					height = y + ps.height;
					//	next pos
					if (x == 5)
						x = 230;
					else
					{
						x = 5;
						y += 100;
					}
				//	x += ps.width-20;
				//	y += ps.height+20;
				}
			}
		}
		else	//	we have an Layout
		{
			//	Go through all components
			for (int i = 0; i < parent.getComponentCount(); i++)
			{
				Component comp = parent.getComponent(i);
				if (comp.isVisible() && comp instanceof WFNode)
				{
					Dimension ps = comp.getPreferredSize();
					Point loc = comp.getLocation();
					int maxWidth = comp.getX() + ps.width;
					int maxHeight = comp.getY() + ps.height;
					if (width < maxWidth)
						width = maxWidth;
					if (height < maxHeight)
						height = maxHeight;
					comp.setBounds(loc.x, loc.y, ps.width, ps.height);
				}
			}	//	for all components
		}	//	have layout

		
		//	Create Lines
		WFContentPanel panel = (WFContentPanel)parent;
		panel.createLines();

		//	Calculate size
		width += insets.right;
		height += insets.bottom;

		//	return size
		m_size = new Dimension(width, height);
		log.finer("Size=" + m_size);
	}	//	layoutContainer

	/**
	 * 	Need Layout
	 * 	@param parent parent
	 * 	@return true if we need to layout
	 */
	private boolean needLayout (Container parent)
	{
		Point p00 = new Point(0,0);
		//	Go through all components
		for (int i = 0; i < parent.getComponentCount(); i++)
		{
			Component comp = parent.getComponent(i);
			if (comp instanceof WFNode && comp.getLocation().equals(p00))
			{
				log.fine(comp.toString());
				return true;
			}
		}
		return false;
	}	//	needLayout

	/**
	 *	Invalidates the layout, indicating that if the layout manager
	 *	has cached information it should be discarded.
	 */
	private void invalidateLayout()
	{
		m_size = null;
	}	//	invalidateLayout

}	//	WFLayoutManager
