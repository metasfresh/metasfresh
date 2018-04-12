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

import java.awt.Component;

import javax.swing.JScrollPane;

import org.adempiere.plaf.AdempiereLookAndFeel;
import org.adempiere.plaf.AdempierePLAF;

import de.metas.util.MFColor;

/**
 *	Adempiere Srcoll Pane.
 *	
 *  @author Jorg Janke
 *  @version $Id: CScrollPane.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CScrollPane extends JScrollPane
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2941967111871448295L;


	/**
	 * 	Adempiere ScollPane
	 */
	public CScrollPane ()
	{
		this (null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}	//	CScollPane

	/**
	 * 	Adempiere ScollPane
	 *	@param vsbPolicy vertical policy
	 *	@param hsbPolicy horizontal policy
	 */
	public CScrollPane (int vsbPolicy, int hsbPolicy)
	{
		this (null, vsbPolicy, hsbPolicy);
	}	//	CScollPane

	/**
	 * 	Adempiere ScollPane
	 *	@param view view
	 */
	public CScrollPane (Component view)
	{
		this (view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}	//	CScollPane

	/**
	 * 	Adempiere ScollPane
	 *	@param view view
	 *	@param vsbPolicy vertical policy
	 *	@param hsbPolicy horizontal policy
	 */
	public CScrollPane (Component view, int vsbPolicy, int hsbPolicy)
	{
		super (view, vsbPolicy, hsbPolicy);
		setOpaque(false);
		getViewport().setOpaque(false);
	}	//	CScollPane
	
	
	/**
	 *  Set Background
	 *  @param bg AdempiereColor for Background, if null set standard background
	 */
	public void setBackgroundColor (MFColor bg)
	{
		if (bg == null)
			bg = MFColor.ofFlatColor(AdempierePLAF.getFormBackground());
		putClientProperty(AdempiereLookAndFeel.BACKGROUND, bg);
	}   //  setBackground
	
}	//	CScollPane
