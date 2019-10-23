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
package org.compiere.plaf;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;

/**
 *  UI utilities
 *
 *  @author     Jorg Janke
 *  @version    $Id: AdempiereUtils.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public final class CompiereUtils
{
	///**	Logger			*/
	//private static Logger log = CLogMgt.getLogger(CompiereUtils.class.getName());

	/** Top Top Color - white 128       the higher the ligher   */
	static public final Color COL_1TOP = new Color(255, 255, 255, 128);
	/** End Top Color - white 0         */
	static public final Color COL_1END = new Color(255, 255, 255, 0);
	/** Top End Color - black 0         */
	static public final Color COL_2TOP = new Color(0, 0, 0, 0);
	/** End End Color - black 64        the higher the darker   */
	static public final Color COL_2END = new Color(0, 0, 0, 64);

	/**
	 *  Paint 3D effect in (lighten in upper half, darken in lower half)
	 *  (called from paint methods)
	 *
	 *  @param g2D Graphics
	 *  @param r Rectangle
	 *  @param round paint round corners
	 *  @param out paint sticking out (not pressed)
	 */
	public static void paint3Deffect (Graphics2D g2D, Rectangle r, boolean round, boolean out)
	{
		// paint upper gradient
		GradientPaint topPaint = null;
		if (out)
			topPaint = new GradientPaint(r.x, r.y, COL_1TOP, r.x, r.y+r.height/2, COL_1END);
		else
			topPaint = new GradientPaint(r.x, r.y, COL_2END, r.x, r.y+r.height/2, COL_2TOP);
		g2D.setPaint(topPaint);
		//
		RectangularShape topRec = null;
		if (round)
			topRec = new RoundRectangle2D.Float(r.x,r.y, r.width,r.height/2, 15,15);
		else
			topRec = new Rectangle(r.x,r.y, r.width,r.height/2);
		g2D.fill(topRec);

		// paint lower gradient
		GradientPaint endPaint = null;	//	upper left corner to lower left
		if (out)
			endPaint = new GradientPaint(r.x, r.y+r.height/2, COL_2TOP, r.x, r.y+r.height, COL_2END);
		else
			endPaint = new GradientPaint(r.x, r.y+r.height/2, COL_1END, r.x, r.y+r.height, COL_1TOP);
		g2D.setPaint(endPaint);
		//
		RectangularShape endRec = null;
		if (round)
			endRec = new RoundRectangle2D.Float(r.x, r.y+r.height/2, r.width, r.height/2, 15,15);
		else
			endRec = new Rectangle(r.x, r.y+r.height/2, r.width, r.height/2);
		g2D.fill(endRec);
	}   //  paint3Deffect
}
