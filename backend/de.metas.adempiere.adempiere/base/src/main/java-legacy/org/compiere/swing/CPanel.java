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
package org.compiere.swing;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.adempiere.plaf.AdempiereLookAndFeel;
import org.adempiere.plaf.AdempierePLAF;

import de.metas.util.MFColor;

/**
 *  Adempiere Panel supporting colored Backgrounds
 *
 *  @author     Jorg Janke
 *  @version    $Id: CPanel.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4153588317643163582L;


	/**
	 * Creates a new AdempierePanel with the specified layout manager
	 * and buffering strategy.
	 * @param layout  the LayoutManager to use
	 * @param isDoubleBuffered  a boolean, true for double-buffering, which
	 *        uses additional memory space to achieve fast, flicker-free updates
	 */
	public CPanel (LayoutManager layout, boolean isDoubleBuffered)
	{
		super (layout, isDoubleBuffered);
		init();
	}   //  CPanel

	/**
	 * Create a new buffered CPanel with the specified layout manager
	 * @param layout  the LayoutManager to use
	 */
	public CPanel (LayoutManager layout)
	{
		super (layout);
		init();
	}   //  CPanel

	/**
	 * Creates a new <code>CPanel</code> with <code>FlowLayout</code>
	 * and the specified buffering strategy.
	 * If <code>isDoubleBuffered</code> is true, the <code>CPanel</code>
	 * will use a double buffer.
	 * @param isDoubleBuffered  a boolean, true for double-buffering, which
	 *        uses additional memory space to achieve fast, flicker-free updates
	 */
	public CPanel (boolean isDoubleBuffered)
	{
		super (isDoubleBuffered);
		init();
	}   //  CPanel

	/**
	 * Creates a new <code>CPanel</code> with a double buffer and a flow layout.
	 */
	public CPanel()
	{
		super ();
		init();
	}   //  CPanel

	/**
	 * Creates a new <code>CPanel</code> with a double buffer and a flow layout.
	 * @param bc Initial Background Color
	 */
	public CPanel(MFColor bc)
	{
		this ();
		init();
		setBackgroundColor (bc);
	}   //  CPanel

	/**
	 *  Common init.
	 *  Adempiere background requires that for the base, background is set explicitly.
	 *  The additional panels should be transparent.
	 */
	private void init()
	{
		setOpaque(false);	//	transparent
	}

	
	/**************************************************************************
	 *  Set Background - ignored by UI -
	 *  @param bg ignored
	 */
	@Override
	public void setBackground (Color bg)
	{
		if (bg.equals(getBackground()))
			return;
		super.setBackground (bg);
		//  ignore calls from javax.swing.LookAndFeel.installColors(LookAndFeel.java:61)
		//if (!Trace.getCallerClass(1).startsWith("javax"))
		setBackgroundColor (MFColor.ofFlatColor(bg));
	}   //  setBackground

	/**
	 *  Set Background
	 *  @param bg AdempiereColor for Background, if null set standard background
	 */
	public void setBackgroundColor (MFColor bg)
	{
		if (bg == null)
			bg = MFColor.ofFlatColor(AdempierePLAF.getFormBackground());
		setOpaque(true);	//	not transparent
		putClientProperty(AdempiereLookAndFeel.BACKGROUND, bg);
		super.setBackground (bg.getFlatColor());
	}   //  setBackground

	/**
	 *  Get Background
	 *  @return Color for Background
	 */
	public MFColor getBackgroundColor ()
	{
		try
		{
			return (MFColor)getClientProperty(AdempiereLookAndFeel.BACKGROUND);
		}
		catch (Exception e)
		{
			System.err.println("CPanel - ClientProperty: " + e.getMessage());
		}
		return null;
	}   //  getBackgroundColor

	/*************************************************************************/

	/**
	 *  Set Tab Hierarchy Level.
	 *  Has only effect, if tabs are on left or right side
	 *
	 *  @param level
	 */
	public void setTabLevel (int level)
	{
		if (level == 0)
			putClientProperty(AdempiereLookAndFeel.TABLEVEL, null);
		else
			putClientProperty(AdempiereLookAndFeel.TABLEVEL, new Integer(level));
	}   //  setTabLevel

	/**
	 *  Get Tab Hierarchy Level
	 *  @return Tab Level
	 */
	public int getTabLevel()
	{
		try
		{
			Integer ll = (Integer)getClientProperty(AdempiereLookAndFeel.TABLEVEL);
			if (ll != null)
				return ll.intValue();
		}
		catch (Exception e)
		{
			System.err.println("ClientProperty: " + e.getMessage());
		}
		return 0;
	}   //  getTabLevel

	
	/**************************************************************************
	 *  String representation
	 *  @return String representation
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("CPanel [");
		sb.append(super.toString());
		MFColor bg = getBackgroundColor();
		if (bg != null)
			sb.append(bg.toString());
		sb.append("]");
		return sb.toString();
	}   //  toString
}   //  CPanel
