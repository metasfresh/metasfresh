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
package org.compiere.minigrid;

import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

/**
 *  Read Only Cell Renderer
 *
 *  @author     Jorg Janke
 *  @version    $Id: ROCellEditor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public final class ROCellEditor extends DefaultCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1957123963696065686L;

	/**
	 *  Constructor
	 */
	public ROCellEditor()
	{
		this(-1); // horizontalAignment=-1=nothing (backward compatiblity)
	}
	
	public ROCellEditor(final int horizontalAlignment)
	{
		super(new JTextField());
		
		if (horizontalAlignment >= 0)
		{
			final JTextField textField = (JTextField)editorComponent;
			textField.setHorizontalAlignment(horizontalAlignment);
		}
	}   //  ROCellEditor

	/**
	 *  Indicate RO
	 *  @param anEvent
	 *  @return false
	 */
	@Override
	public boolean isCellEditable(EventObject anEvent)
	{
		return false;
	}   //  isCellEditable
}   //  ROCellEditor
