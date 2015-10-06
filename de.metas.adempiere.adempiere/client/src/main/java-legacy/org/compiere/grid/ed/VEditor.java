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
import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;

import javax.swing.border.Border;

import org.compiere.model.GridField;
import org.compiere.swing.CEditor;

/**
 *	Editor Interface for single Row Editors (also used as TableCellEditors).
 *  <p>
 *  Editors fire VetoableChange to inform about new entered values
 *  and listen to propertyChange (MField.PROPERTY) to receive new values
 *  or to (MField.ATTRIBUTE) in changes of Background or Editability
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VEditor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public interface VEditor extends CEditor, PropertyChangeListener
{
	/**
	 *	Get Column Name
	 * 	@return column name
	 */
	public String getName();

	/**
	 *	Set Column Name
	 * 	@patam columnName name
	 */
	public void setName(String columnName);

	/**
	 *	Change Listener Interface
	 *  @param listener
	 */
	public void addVetoableChangeListener(VetoableChangeListener listener);
	/**
	 *	Change Listener Interface
	 *  @param listener
	 */
	public void removeVetoableChangeListener(VetoableChangeListener listener);
	/**
	 *  Action Listener
	 *  @param listener
	 */
	public void addActionListener(ActionListener listener);
//	public void removeActionListener(ActionListener listener);

	/**
	 *  Used to set border for table editors
	 *  @param border
	 */
	public void setBorder(Border border);

	/**
	 *  Set Font
	 *  @param font
	 */
	public void setFont(Font font);

	/**
	 *	Set Foreground
	 *  @param color
	 */
	public void setForeground(Color color);

	/**
	 *	Set Background
	 *  @param color
	 */
	public void setBackground(Color color);

	/**
	 *  Set Field/WindowNo for ValuePreference
	 *  @param mField
	 */
	public void setField (GridField mField);

	/**
	 *  Get Field/WindowNo for ValuePreference
	 *  @return mField
	 */
	public GridField getField ();

	/**
	 *  Dispose
	 */
	public void dispose();
	
	/**
	 * @return true if we need to commit (propagate the value to editor) right after editor changes
	 */// metas
	public boolean isAutoCommit();
}	//	VEditor
