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
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Position;

/**
 *	Overwrite Caret
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: VOvrCaret.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VOvrCaret extends DefaultCaret
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1513269384035685427L;

	/**
	 *	Constructor
	 */
	public VOvrCaret()
	{
		super();
	}	//	VOvrCaret

	/**
	 * Renders the caret as a top and button bracket.
	 *
	 * @param g the graphics context
	 * @see #damage
	 */
	public void paint(Graphics g)
	{
		boolean dotLTR = true;			//	left-to-right
		Position.Bias dotBias = Position.Bias.Forward;

		//
		if (isVisible())
		{
			try
			{
				TextUI mapper = getComponent().getUI();
				Rectangle r = mapper.modelToView(getComponent(), getDot(), dotBias);
				Rectangle e = mapper.modelToView(getComponent(), getDot()+1, dotBias);
			//	g.setColor(getComponent().getCaretColor());
				g.setColor(Color.blue);
				//
				int cWidth = e.x-r.x;
				int cHeight = 4;
				int cThick = 2;
				//
				g.fillRect(r.x-1, r.y, cWidth, cThick);						//	 top
				g.fillRect(r.x-1, r.y, cThick, cHeight);					//	|
				g.fillRect(r.x-1+cWidth, r.y, cThick, cHeight);				//	  |
				//
				int yStart = r.y+r.height;
				g.fillRect(r.x-1, yStart-cThick, cWidth, cThick);			//	 button
				g.fillRect(r.x-1, yStart-cHeight, cThick, cHeight);			//	|
				g.fillRect(r.x-1+cWidth, yStart-cHeight, cThick, cHeight);	//	  |
			}
			catch (BadLocationException e)
			{
				//	can't render
			//	System.err.println("Can't render cursor");
			}
		}	//	isVisible
	}	//	paint

	/**
	 * Damages the area surrounding the caret to cause
	 * it to be repainted in a new location.
	 * This method should update the caret bounds (x, y, width, and height).
	 *
	 * @param r  the current location of the caret
	 * @see #paint
	 */
	protected synchronized void damage(Rectangle r)
	{
		if (r != null)
		{
			x = r.x - 4;		//	start 4 pixles before	(one required)
			y = r.y;
			width = 18;			//	sufficent for standard font (18-4=14)
			height = r.height;
			repaint();
		}
	}	//	damage

}	//	VOvrCaret
