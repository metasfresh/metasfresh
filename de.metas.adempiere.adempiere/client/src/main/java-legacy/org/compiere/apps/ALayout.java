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
package org.compiere.apps;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.Iterator;

/**
 *  Application Layout Manager.
 *  <code>
 * 		panel.setLayout(new ALayout());
 *      panel.add(field11, new ALayoutConstraint(0,0));
 *      panel.add(field12, null);
 *      panel.add(field13, null);
 *      panel.add(field14, null);
 *      panel.add(field21, new ALayoutConstraint(1,0));
 *	</code>
 *
 *  @author Jorg Janke
 *  @version  $Id: ALayout.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class ALayout implements LayoutManager2
{
	/**
	 *  Default Constructor with spacing of 2 and columns filled
	 */
	public ALayout()
	{
		this (2,4, true);
	}   //  ALayout

	/**
	 *  Detail Contructor
	 *  @param spaceH horizontal space (top, between rows, button)
	 *  @param spaceV vertical space (left, between columns, right)
	 *  @param colFill fields are fully filled (rather then preferred size)
	 */
	public ALayout(int spaceH, int spaceV, boolean colFill)
	{
		setSpaceH(spaceH);
		setSpaceV(spaceV);
		m_colFill = colFill;
	}   //  ALayout

	/**  Data Storage               */
	private ALayoutCollection   m_data = new ALayoutCollection();
	/** Horizontal Space            */
	private int                 m_spaceH;
	/** Vertical Space              */
	private int                 m_spaceV;
	/** Column Fill                 */
	private boolean             m_colFill;

	/**
	 *  Add To Layout with NULL constraint
	 *
	 * @param name the string to be associated with the component - ignored
	 * @param comp the component to be added
	 */
	public void addLayoutComponent(String name, Component comp)
	{
		addLayoutComponent (comp, null);
	}   //  addLayoutComponent

	/**
	 *  Adds the specified component to the layout, using the specified
	 *  constraint object. If the constraint is not a ALayoutConstraint
	 *  the component is added with a NULL constraint.
	 *  <p>
	 *  Components with a NULL constraint are added as the next column to the last row
	 *
	 *  @param component the component to be added
	 *  @param constraint  where/how the component is added to the layout.
	 *  @see ALayoutConstraint
	 */
	public void addLayoutComponent(Component component, Object constraint)
	{
		ALayoutConstraint con = null;
		if (constraint instanceof ALayoutConstraint)
			con = (ALayoutConstraint)constraint;
		//
		m_data.put(con, component);
	}   //  addLayoutComponent

	/**
	 * Removes the specified component from the layout.
	 * @param comp the component to be removed
	 */
	public void removeLayoutComponent(Component comp)
	{
		if (!m_data.containsValue(comp))
			return;
		Iterator it = m_data.keySet().iterator();
		while (it.hasNext())
		{
			Object key = it.next();
			if (m_data.get(key).equals(comp))
			{
				m_data.remove(key);
				return;
			}
		}
	}   //  removeLayoutComponent

	/**
	 * Calculates the preferred size dimensions for the specified
	 * container, given the components it contains.
	 * @param parent the container to be laid out
	 * @return Size
	 * @see #minimumLayoutSize
	 */
	public Dimension preferredLayoutSize(Container parent)
	{
		return calculateLayoutSize (parent, 'P');
	}   //  preferredLayoutSize

	/**
	 * Calculates the minimum size dimensions for the specified
	 * container, given the components it contains.
	 * @param parent the component to be laid out
	 * @return Size
	 * @see #preferredLayoutSize
	 */
	public Dimension minimumLayoutSize(Container parent)
	{
		return calculateLayoutSize (parent, 'm');
	}   //  minimumLayoutSize

	/**
	 *  Calculates the maximum size dimensions for the specified container,
	 *  given the components it contains.
	 *  @param parent Parent Container
	 *  @return Size
	 *  @see java.awt.Component#getMaximumSize
	 *  @see LayoutManager
	 */
	public Dimension maximumLayoutSize(Container parent)
	{
		return calculateLayoutSize (parent, 'M');
	}   //  maximumLayoutSize

	/**
	 *  Calculate Layout Size
	 *  @param parent Parent Container
	 *  @param  how P=Preferred - M=Maximum = m=Mimimum
	 *  @return Size
	 */
	private Dimension calculateLayoutSize(Container parent, char how)
	{
		checkComponents(parent);
		//  --  Create 2D Dimension Array
		int rows = getRowCount();
		int cols = getColCount();
		Dimension[][] dim = new Dimension[rows][cols];
		//
		Object[] keys = m_data.keySet().toArray();
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; i++)
		{
			ALayoutConstraint constraint = (ALayoutConstraint)keys[i];
			Component component = (Component)m_data.get(keys[i]);
			Dimension d = null;
			if (how == 'P')
				d = component.getPreferredSize();
			else if (how == 'M')
				d = component.getMaximumSize();
			else
				d = component.getMinimumSize();
			if (component.isVisible())
				dim [constraint.getRow()][constraint.getCol()] = d;
			else
				dim [constraint.getRow()][constraint.getCol()] = null;
		}

		//  --  Calculate 2D Dimension Size
		Insets insets = parent.getInsets();
		Dimension retValue = new Dimension (insets.left+insets.right, insets.top+insets.bottom);
		retValue.height += m_spaceH;
		retValue.width += m_spaceV;
		int maxWidth = 0;
		for (int r = 0; r < rows; r++)
		{
			int height = 0;
			int width = 0;
			for (int c = 0; c < cols; c++)
			{
				Dimension d = dim[r][c];
				if (d != null)
				{
					width += d.width;
					height = Math.max(height, d.height);
				}
				width += m_spaceV;
			}   //  for all columns
			retValue.height += height + m_spaceH;
			maxWidth += Math.max(maxWidth, width);
		}   //  for all rows
		retValue.width += maxWidth;
	//	Log.trace(this,Log.l6_Database, "ALayout.calculateLayoutSize", retValue.toString());
		return retValue;
	}   //  calculateLayoutSize

	/**
	 * Lays out the specified container.
	 * @param parent the container to be laid out
	 */
	public void layoutContainer(Container parent)
	{
		checkComponents(parent);
		//  --  Create 2D Component Array
		int rows = getRowCount();
		int cols = getColCount();
		Component[][] com = new Component[rows][cols];
		//
		Object[] keys = m_data.keySet().toArray();
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; i++)
		{
			ALayoutConstraint constraint = (ALayoutConstraint)keys[i];
			Component component = (Component)m_data.get(keys[i]);
			if (component.isVisible())
				com [constraint.getRow()][constraint.getCol()] = component;
			else
				com [constraint.getRow()][constraint.getCol()] = null;
		}

		//  --  Calculate Column Size
		int[] colWidth = new int[cols];
		int[] rowHeight = new int[rows];
		int columnWidth = m_spaceV;
		for (int c = 0; c < cols; c++)
		{
			int width = 0;
			for (int r = 0; r < rows; r++)
			{
				Component component = com[r][c];
				if (component != null)
				{
					width = Math.max (width, component.getPreferredSize().width);
					rowHeight[r] = Math.max (rowHeight[r], component.getPreferredSize().height);
				}
			}
			colWidth[c] = width;
			columnWidth += width + m_spaceV;
		}

		//  --  Stretch/Squeeze Columns to fit target width
		int parentWidth = parent.getSize().width;
		double multiplier = (double)parentWidth / (double)columnWidth;
		if (multiplier < .5)        //  limit sqeezing
			multiplier = .5;
		for (int c = 0; c < cols; c++)
			colWidth[c] = (int) (colWidth[c] * multiplier);
		int spaceV = (int)(m_spaceV * multiplier);
		//
//		log.fine( "ALayout.layoutContainer",
//			"ParentWidth=" + parentWidth + ", ColumnWidth=" + columnWidth + ", SpaceV=" + spaceV + ",  Multiplier=" + multiplier);

		//  --  Lay out components
		Insets insets = parent.getInsets();
		int posH = insets.top + m_spaceH;
		for (int r = 0; r < rows; r++)
		{
			int posV = insets.left + spaceV;
			int height = 0;
			for (int c = 0; c < cols; c++)
			{
				Component component = com[r][c];
				if (component != null)
				{
					Dimension ps = component.getPreferredSize();
					int w = ps.width;
					if (m_colFill || w > colWidth[c])    //  limit or stretch
						w = colWidth[c];
					int h = ps.height;
					int topSpace = 0;
					if (h < rowHeight[r])	//	push a little bit lower
						topSpace = (rowHeight[r] - h) / 3;
					height = Math.max(height, h);
					component.setBounds(posV, posH+topSpace, w, h);
//					log.fine( "ALayout.layoutContainer",
//						"Row=" + r + ", Col=" + c + ",  PosV=" + posV + ", PosH=" + posH + "/" + topSpace + ",  width=" + w + ", height=" + h);
				}
				posV += colWidth[c] + spaceV;
			}   //  for all columns
			posH += height + m_spaceH;
		}   //  for all rows
	}   //  layoutContainer

	/**
	 * Returns the alignment along the x axis.  This specifies how
	 * the component would like to be aligned relative to other
	 * components.  The value should be a number between 0 and 1
	 * where 0 represents alignment along the origin, 1 is aligned
	 * the furthest away from the origin, 0.5 is centered, etc.
	 *  @param target target
	 *  @return 0f
	 */
	public float getLayoutAlignmentX(Container target)
	{
		return 0f;
	}   //  getLayoutAlignmentX

	/**
	 * Returns the alignment along the y axis.  This specifies how
	 * the component would like to be aligned relative to other
	 * components.  The value should be a number between 0 and 1
	 * where 0 represents alignment along the origin, 1 is aligned
	 * the furthest away from the origin, 0.5 is centered, etc.
	 *  @param target target
	 *  @return 0f
	 */
	public float getLayoutAlignmentY(Container target)
	{
		return 0f;
	}   //  getLayoutAlignmentY

	/**
	 * Invalidates the layout, indicating that if the layout manager
	 * has cached information it should be discarded.
	 *  @param target target
	 */
	public void invalidateLayout(Container target)
	{
	}   //  invalidateLayout

	/*************************************************************************/

	/**
	 *  Check target components and add components, which don't have no constraints
	 *  @param target target
	 */
	private void checkComponents (Container target)
	{
		int size = target.getComponentCount();
		for (int i = 0; i < size; i++)
		{
			Component comp = target.getComponent(i);
			if (!m_data.containsValue(comp))
				m_data.put(null, comp);
		}
	}   //  checkComponents

	/**
	 *  Get Number of Rows
	 *  @return no pf rows
	 */
	public int getRowCount()
	{
		return m_data.getMaxRow()+1;
	}   //  getRowCount

	/**
	 *  Get Number of Columns
	 *  @return no of cols
	 */
	public int getColCount()
	{
		return m_data.getMaxCol()+1;
	}   //  getColCount

	/**
	 *  Set Horizontal Space (top, between rows, button)
	 *  @param spaceH horizontal space (top, between rows, button)
	 */
	public void setSpaceH (int spaceH)
	{
		m_spaceH = spaceH;
	}   //  setSpaceH

	/**
	 *  Get Horizontal Space (top, between rows, button)
	 *  @return spaceH horizontal space (top, between rows, button)
	 */
	public int getSpaceH()
	{
		return m_spaceH;
	}   //  getSpaceH

	/**
	 *  Set Vertical Space (left, between columns, right)
	 *  @param spaceV vertical space (left, between columns, right)
	 */
	public void setSpaceV(int spaceV)
	{
		m_spaceV = spaceV;
	}   //  setSpaceV

	/**
	 *  Get Vertical Space (left, between columns, right)
	 *  @return spaceV vertical space (left, between columns, right)
	 */
	public int getSpaceV()
	{
		return m_spaceV;
	}   //  getSpaceV

}   //  ALayout
