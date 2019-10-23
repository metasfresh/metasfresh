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

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 * 	Adempiere Menu Item
 *	
 *  @author Jorg Janke
 *  @version $Id: CMenuItem.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CMenuItem extends JMenuItem
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5241542923170919340L;

	public CMenuItem ()
	{
		super ();
	}	//	CMenuItem

	public CMenuItem (Icon icon)
	{
		super (icon);
	}	//	CMenuItem

	public CMenuItem (String text)
	{
		super (text);
	}	//	CMenuItem

	public CMenuItem (Action a)
	{
		super (a);
	}	//	CMenuItem

	public CMenuItem (String text, Icon icon)
	{
		super (text, icon);
	}	//	CMenuItem

	public CMenuItem (String text, int mnemonic)
	{
		super (text, mnemonic);
	}	//	CMenuItem
	
	/**
	 * 	Set Text
	 *	@param text text
	 */
	@Override
	public void setText (String text)
	{
		if (text == null)
		{
			super.setText(text);
			return;
		}
		int pos = text.indexOf('&');
		if (pos != -1 && text.length() > pos)	//	We have a nemonic - creates ALT-_
		{
			int mnemonic = text.toUpperCase().charAt(pos+1);
			if (mnemonic != ' ')
			{
				setMnemonic(mnemonic);
				text = text.substring(0, pos) + text.substring(pos+1);
			}
		}
		super.setText (text);
		if (getName() == null)
			setName (text);
	}	//	setText
	
}	//	CMenuItem
