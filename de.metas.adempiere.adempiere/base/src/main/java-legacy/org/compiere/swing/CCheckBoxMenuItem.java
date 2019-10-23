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
import javax.swing.JCheckBoxMenuItem;

/**
 * 	Adempiere Check Box Menu Item
 *	
 *  @author Jorg Janke
 *  @version $Id: CCheckBoxMenuItem.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CCheckBoxMenuItem extends JCheckBoxMenuItem
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6701152155152356260L;

	public CCheckBoxMenuItem ()
	{
		super ();
	}	//	CCheckBoxMenuItem

	public CCheckBoxMenuItem (Icon icon)
	{
		super (icon);
	}	//	CCheckBoxMenuItem

	public CCheckBoxMenuItem (String text)
	{
		super (text);
	}	//	CCheckBoxMenuItem

	public CCheckBoxMenuItem (Action a)
	{
		super (a);
	}	//	CCheckBoxMenuItem

	public CCheckBoxMenuItem (String text, Icon icon)
	{
		super (text, icon);
	}	//	CCheckBoxMenuItem

	public CCheckBoxMenuItem (String text, boolean b)
	{
		super (text, b);
	}	//	CCheckBoxMenuItem

	public CCheckBoxMenuItem (String text, Icon icon, boolean b)
	{
		super (text, icon, b);
	}	//	CCheckBoxMenuItem
	
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

}	//	CCheckBoxMenuItem
