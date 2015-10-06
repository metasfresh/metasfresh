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

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.compiere.util.CLogger;

/**
 *	HTML Renderer View
 *	
 *  @author Jorg Janke
 *  @version $Id: HTMLRenderer.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 */
public class HTMLRenderer extends View
{
	/**
	 * 	Get View from HTML String
	 *	@param html html string
	 *	@return renderer view
	 */
	public static HTMLRenderer get (String html)
	{
		HTMLEditorKit kit = new HTMLEditorKit();
		HTMLDocument doc = (HTMLDocument)kit.createDefaultDocument();
		try
		{
			doc.remove(0, doc.getLength());
			Reader r = new StringReader(html);
			kit.read(r, doc, 0);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
		//	Create Renderer
		Element element = doc.getDefaultRootElement();
		ViewFactory factory = kit.getViewFactory();
		View view = factory.create(element);	//	Y_AXIS is main
		HTMLRenderer renderer = new HTMLRenderer (factory, view);
		renderer.preferenceChanged (null, true, true);
		return renderer;
	}	//	get
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(HTMLRenderer.class);
	
	/**************************************************************************
	 * 	Constructor
	 *	@param f factory
	 *	@param v root view
	 */
	public HTMLRenderer (ViewFactory f, View v) 
	{
		super(null);
		m_factory = f;
		m_view = v;
		m_view.setParent(this);
		// initially layout to the preferred size
		setSize(m_view.getPreferredSpan(X_AXIS), m_view.getPreferredSpan(Y_AXIS));
	}	//	HTMLRenderer

	private int 			m_width;
	private View 			m_view;
	private ViewFactory 	m_factory;
	private Rectangle		m_allocation;


	/**
	 * 	Get Width
	 *	@return width
	 */
	public float getWidth()
	{
		return getPreferredSpan(javax.swing.text.View.X_AXIS);
	}	//	getWidth
	
	/**
	 * 	Get Height
	 *	@return height
	 */
	public float getHeight()
	{
		return getPreferredSpan(javax.swing.text.View.Y_AXIS);
	}	//	getHeight

	/**
	 * 	Get Height for one line
	 *	@return height
	 */
	public float getHeightOneLine()
	{
		return 30f;		//	HARDCODED
	}	//	getHeightOneLine

	/**
	 * 	Set Allocation (actual print size)
	 *	@param width actual print width
	 *	@param height actual print height
	 */
	public void setAllocation (int width, int height)
	{
		setAllocation (new Rectangle(width, height));
	}	//	setAllocation

	/**
	 * 	Set Allocation (actual size)
	 *	@param allocation actual print size
	 */
	public void setAllocation(Rectangle allocation)
	{
		m_allocation = allocation;
	}	//	setAllocation

	/**
	 * 	Get Allocation
	 *	@return actual print size or if not defined the renderer size 
	 */
	public Rectangle getAllocation()
	{
		if (m_allocation == null)
			return new Rectangle((int)getWidth(), (int)getHeight());
		return m_allocation;
	}	//	getAllocation


	/**
	 * Fetches the attributes to use when rendering.  At the root
	 * level there are no attributes.  If an attribute is resolved
	 * up the view hierarchy this is the end of the line.
	 * 	@return attribute set
	 */
	public AttributeSet getAttributes() 
	{
		return null;
	}
	
	/**
	 * Determines the preferred span for this view along an axis.
	 * @param axis may be either X_AXIS or Y_AXIS
	 * @return the span the view would like to be rendered into.
	 *         Typically the view is told to render into the span
	 *         that is returned, although there is no guarantee.
	 *         The parent may choose to resize or break the view.
	 */
	public float getPreferredSpan(int axis) 
	{
		if (axis == X_AXIS) 
		{
			// width currently laid out to
			return m_width;
		}
		return m_view.getPreferredSpan(axis);
	}
	
	/**
	 * Determines the minimum span for this view along an axis.
	 *
	 * @param axis may be either X_AXIS or Y_AXIS
	 * @return the span the view would like to be rendered into.
	 *         Typically the view is told to render into the span
	 *         that is returned, although there is no guarantee.
	 *         The parent may choose to resize or break the view.
	 */
	public float getMinimumSpan(int axis) 
	{
		return m_view.getMinimumSpan(axis);
	}
	
	/**
	 * Determines the maximum span for this view along an axis.
	 *
	 * @param axis may be either X_AXIS or Y_AXIS
	 * @return the span the view would like to be rendered into.
	 *         Typically the view is told to render into the span
	 *         that is returned, although there is no guarantee.
	 *         The parent may choose to resize or break the view.
	 */
	public float getMaximumSpan(int axis) 
	{
		return Integer.MAX_VALUE;
	}


	/**
	 * Determines the desired alignment for this view along an axis.
	 *
	 * @param axis may be either X_AXIS or Y_AXIS
	 * @return the desired alignment, where 0.0 indicates the origin
	 *     and 1.0 the full span away from the origin
	 */
	public float getAlignment(int axis) 
	{
		return m_view.getAlignment(axis);
	}

	/**
	 * Renders the view.
	 *
	 * @param g the graphics context
	 * @param allocation the region to render into
	 */
	public void paint(Graphics g, Shape allocation) 
	{
		Rectangle alloc = allocation.getBounds();
		m_view.setSize(alloc.width, alloc.height);	//	layout
		Shape oldClip = g.getClip();
		g.setClip(alloc);							// limit print
		m_view.paint(g, allocation);
		g.setClip(oldClip);
	}	//	paint
        
	/**
	 * Sets the view parent.
	 *
	 * @param parent the parent view
	 */
	public void setParent(View parent) 
	{
		throw new Error("Can't set parent on root view");
	}

	/** 
	 * Returns the number of views in this view.  Since
	 * this view simply wraps the root of the view hierarchy
	 * it has exactly one child.
	 *
	 * @return the number of views
	 * @see #getView
	 */
	public int getViewCount() 
	{
		return 1;
	}

	/** 
	 * Gets the n-th view in this container.
	 *
	 * @param n the number of the view to get
	 * @return the view
	 */
	public View getView(int n) 
	{
		return m_view;
	}

	/**
	 * Provides a mapping from the document model coordinate space
	 * to the coordinate space of the view mapped to it.
	 *
	 * @param pos the position to convert
	 * @param a the allocated region to render into
	 * @param b position
	 * @return the bounding box of the given position
	 * @throws BadLocationException
	 */
	public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException 
	{
		return m_view.modelToView(pos, a, b);
	}

	/**
	 * Provides a mapping from the document model coordinate space
	 * to the coordinate space of the view mapped to it.
	 *
	 * @param p0 the position to convert >= 0
	 * @param b0 the bias toward the previous character or the
	 *  next character represented by p0, in case the 
	 *  position is a boundary of two views. 
	 * @param p1 the position to convert >= 0
	 * @param b1 the bias toward the previous character or the
	 *  next character represented by p1, in case the 
	 *  position is a boundary of two views. 
	 * @param a the allocated region to render into
	 * @return the bounding box of the given position is returned
	 * @exception BadLocationException  if the given position does
	 *   not represent a valid location in the associated document
	 * @exception IllegalArgumentException for an invalid bias argument
	 * @see javax.swing.text.View#viewToModel(float, float, java.awt.Shape, javax.swing.text.Position.Bias[])
	 */
	public Shape modelToView(int p0, Position.Bias b0, int p1, 
				 Position.Bias b1, Shape a) throws BadLocationException 
	{
		return m_view.modelToView(p0, b0, p1, b1, a);
	}

	/**
	 * Provides a mapping from the view coordinate space to the logical
	 * coordinate space of the model.
	 *
	 * @param x x coordinate of the view location to convert
	 * @param y y coordinate of the view location to convert
	 * @param a the allocated region to render into
	 * @param bias bias
	 * @return the location within the model that best represents the
	 *    given point in the view
	 */
	public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) 
	{
		return m_view.viewToModel(x, y, a, bias);
	}

	/**
	 * Returns the document model underlying the view.
	 *
	 * @return the model
	 */
	public Document getDocument() 
	{
		return m_view.getDocument();
	}
        
	/**
	 * Returns the starting offset into the model for this view.
	 *
	 * @return the starting offset
	 */
	public int getStartOffset() 
	{
		return m_view.getStartOffset();
	}

	/**
	 * Returns the ending offset into the model for this view.
	 *
	 * @return the ending offset
	 */
	public int getEndOffset() 
	{
		return m_view.getEndOffset();
	}

	/**
	 * Gets the element that this view is mapped to.
	 *
	 * @return the view
	 */
	public Element getElement() 
	{
		return m_view.getElement();
	}

	/**
	 * Sets the view size.
	 *
	 * @param width the width
	 * @param height the height
	 */
	public void setSize(float width, float height) 
	{
		this.m_width = (int) width;
		m_view.setSize(width, height);
	}
        
	/**
	 * Fetches the factory to be used for building the
	 * various view fragments that make up the view that
	 * represents the model.  This is what determines
	 * how the model will be represented.  This is implemented
	 * to fetch the factory provided by the associated
	 * EditorKit.
	 *
	 * @return the factory
	 */
	public ViewFactory getViewFactory() 
	{
		return m_factory;
	}

}	//	HTMLRenderer
