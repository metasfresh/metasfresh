/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component;

/**
 * @author Andrew Kimball
 *
 */
public class WTableColumn
{
    /** The width of the column. */
    protected int width;

    /** The minimum width of the column. */
    protected int minWidth;

    /** The preferred width of the column. */
    private int preferredWidth;

    /** The maximum width of the column. */
    protected int maxWidth;
    
    /** If true, the user is allowed to resize the column; the default is true. */
    protected boolean	isResizable;

    /** The header value of the column. */
    protected Object		headerValue;
    
    protected Class columnClass;

    /** 
     *  Cover method, using a default width of 75 
     *  @see #WTableColumn(int)
     */
    public WTableColumn() {
    	this(75);
    }
    
    public WTableColumn(int width) 
    {
		this.width = width;
		this.preferredWidth = width;
		
		// Set other instance variables to default values.
		minWidth = 15;
		maxWidth = Integer.MAX_VALUE;
		isResizable = true;
		headerValue = null;
    }
    

    /**
     * Sets the <code>Object</code> whose string representation will be
     * used as the value for the <code>headerRenderer</code>.  When the
     * <code>WTableColumn</code> is created, the default <code>headerValue</code>
     * is <code>null</code>.
     * 
     * @param headerValue  the new headerValue
     * @see	  #getHeaderValue
     */
    public void setHeaderValue(Object headerValue) 
    {
		this.headerValue = headerValue;
		
		return;
    }
    

    /**
     * Returns the <code>Object</code> used as the value for the header
     * renderer.
     *
     * @return	the <code>headerValue</code> property
     * @see	#setHeaderValue
     */
    public Object getHeaderValue() 
    {
    	return headerValue;
    }
    

    /**
     * This method should not be used to set the widths of columns in the 
     * <code>WListbox</code>, use <code>setPreferredWidth</code> instead.
     * This method sets this column's width to <code>width</code>.  
     * If <code>width</code> exceeds the minimum or maximum width, 
     * it is adjusted to the appropriate limiting value.
     * <p>
     * @param  width  the new width
     * @see	#getWidth
     * @see	#setMinWidth
     * @see	#setMaxWidth
     * @see	#setPreferredWidth
     */
    public void setWidth(int width) 
    {
		this.width = Math.min(Math.max(width, minWidth), maxWidth);
		
		return;
    }

    /**
     * Returns the width of the <code>TableColumn</code>. The default width is
     * 75.
     *
     * @return	the <code>width</code> property
     * @see	#setWidth
     */
    public int getWidth() 
    {
    	return width;
    }

    /**
     * Sets this column's preferred width to <code>preferredWidth</code>.  
     * If <code>preferredWidth</code> exceeds the minimum or maximum width, 
     * it is adjusted to the appropriate limiting value. 
     *
     * @param  preferredWidth the new preferred width
     * @see	#getPreferredWidth
     */
    public void setPreferredWidth(int preferredWidth) 
    { 
		this.preferredWidth = Math.min(Math.max(preferredWidth, minWidth), maxWidth);
    }

    /**
     * Returns the preferred width of the <code>WTableColumn</code>. 
     * The default preferred width is 75.
     *
     * @return	the <code>preferredWidth</code> property
     * @see	#setPreferredWidth
     */
    public int getPreferredWidth() 
    {
    	return preferredWidth;
    }

    /**
     * Sets the <code>WTableColumn</code>'s minimum width to
     * <code>minWidth</code>; also adjusts the current width
     * and preferred width if they are less than this value.
     *
     * @param minWidth  the new minimum width
     * @see	#getMinWidth
     * @see	#setPreferredWidth
     * @see	#setMaxWidth     
     */
    public void setMinWidth(int minWidth) 
    { 	
		this.minWidth = Math.max(minWidth, 0);
		
		if (width < minWidth) 
		{
		    setWidth(minWidth);
		}
		
		if (preferredWidth < minWidth) 
		{
		    setPreferredWidth(minWidth);
		}
		
		return;
    }

    /**
     * Returns the minimum width for the <code>WTableColumn</code>. The
     * <code>WTableColumn</code>'s width can't be made less than this either
     * by the user or programmatically.  The default minWidth is 15.
     *
     * @return	the <code>minWidth</code> property
     * @see	#setMinWidth
     */
    public int getMinWidth() 
    {
    	return minWidth;
    }

    /**
     * Sets the <code>WTableColumn</code>'s maximum width to
     * <code>maxWidth</code>; also adjusts the width and preferred
     * width if they are greater than this value.
     *
     * @param maxWidth  the new maximum width
     * @see	#getMaxWidth
     * @see	#setPreferredWidth
     * @see	#setMinWidth     
     */
    public void setMaxWidth(int maxWidth) 
    {	
		this.maxWidth = Math.max(minWidth, maxWidth);
		if (width > maxWidth) 
		{
		    setWidth(maxWidth);
		}
		if (preferredWidth > maxWidth) 
		{
		    setPreferredWidth(maxWidth);
		}
		
		return;
    }

    /**
     * Returns the maximum width for the <code>WTableColumn</code>. The
     * <code>WTableColumn</code>'s width can't be made larger than this
     * either by the user or programmatically.  The default maxWidth
     * is Integer.MAX_VALUE.
     *
     * @return	the <code>maxWidth</code> property
     * @see	#setMaxWidth
     */
    public int getMaxWidth() 
    {
    	return maxWidth;
    }

    /**
     * Sets whether this column can be resized.
     *
     * @param isResizable  if true, resizing is allowed; otherwise false
     * @see	#getResizable
     */
    public void setResizable(boolean isResizable) 
    {	
    	this.isResizable = isResizable;	
    }

    /**
     * Returns true if the user is allowed to resize the
     * <code>WTableColumn</code>'s
     * width, false otherwise. You can change the width programmatically
     * regardless of this setting.  The default is true.
     *
     * @return	the <code>isResizable</code> property
     * @see	#setResizable
     */
    public boolean getResizable() 
    {
    	return isResizable;
    }

    /**
     * 
     * @return Class
     */
	public Class getColumnClass() 
	{
		return columnClass;
	}

	/**
	 * 
	 * @param columnClass
	 */
	public void setColumnClass(Class columnClass) 
	{
		this.columnClass = columnClass;
	}
}
