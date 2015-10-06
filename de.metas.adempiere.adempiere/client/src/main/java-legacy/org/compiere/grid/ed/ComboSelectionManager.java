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

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.compiere.util.CLogger;

/**
 *  ComboBox Selection Manager for AuroReduction
 *
 *  @author Jorg Janke
 *  @version  $Id: ComboSelectionManager.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class ComboSelectionManager implements JComboBox.KeySelectionManager
{
	/**
	 *
	 */
	public ComboSelectionManager()
	{
	}   //  ComboSelectionManager

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ComboSelectionManager.class);
	
	/**
	 *  Given <code>aKey</code> and the model, returns the row
	 *  that should become selected. Return -1 if no match was
	 *  found.
	 *
	 *  @param key  a char value, usually indicating a keyboard key that was pressed
	 *  @param model a ComboBoxModel -- the component's data model, containing the list of selectable items
	 *  @return an int equal to the selected row, where 0 is the first item and -1 is none
	 */
	public int selectionForKey (char key, ComboBoxModel model)
	{
		log.fine("Key=" + key);
		//
		int currentSelection = -1;
		Object selectedItem = model.getSelectedItem();


		return 0;
	}   //	selectionForKey


}   //  ComboSelectionManager
