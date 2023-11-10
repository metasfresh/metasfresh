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

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import javax.swing.LookAndFeel;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.swing.CTextPane;

import de.metas.letters.model.MADBoilerPlate;

/**
 *	Long Text (CBLOB) Editor 	
 *	
 *  @author Jorg Janke
 *  @version $Id: VTextLong.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class VTextLong extends CTextPane
	implements VEditor, KeyListener
{
	/*****************************************************************************/

	/**
	 * 
	 */
	private static final long serialVersionUID = 6484690202241390248L;
	
	/**
	 *	Standard Constructor
	 *  @param columnName column name
	 *  @param mandatory mandatory
	 *  @param isReadOnly read only
	 *  @param isUpdateable updateable
	 *  @param displayLength display length
	 *  @param fieldLength field length
	 */
	public VTextLong (String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable, int displayLength, int fieldLength)
	{
		super ("text");
		super.setName(columnName);
		LookAndFeel.installBorder(this, "TextField.border");
		//setPreferredSize(new Dimension (500,80)); // metas-tsa: NEVER set the preferred size, but let the layout do that

		//  Create Editor
		setForeground(AdempierePLAF.getTextColor_Normal());
		setBackground(AdempierePLAF.getFieldBackground_Normal());

		setMandatory(mandatory);
		m_columnName = columnName;

		if (isReadOnly || !isUpdateable)
			setReadWrite(false);
		addKeyListener(this);
		
		// Create and bind the context menu
		new EditorContextPopupMenu(this);
	}	//	VText

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
	}   //  dispose

	private String				m_columnName;
	private String				m_oldText;
	private String				m_initialText;
	private volatile boolean	m_setting = false;
	private GridField m_mField;
	
//	/**	Logger			*/
//	private static Logger log = CLogMgt.getLogger(VTextLong.class);

	/**
	 *	Set Editor to value
	 *  @param value value
	 */
	@Override
	public void setValue(Object value)
	{
		if (value == null)
			m_oldText = "";
		else
			m_oldText = MADBoilerPlate.getPlainText(value.toString());
		if (m_setting)
			return;
		super.setValue(m_oldText);
		m_initialText = m_oldText;
		//	Always position Top 
		setCaretPosition(0);
	}	//	setValue

	/**
	 *  Property Change Listener
	 *  @param evt event
	 */
	@Override
	public void propertyChange (PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
			setValue(evt.getNewValue());
		// metas: request focus (2009_0027_G131)
		if (evt.getPropertyName().equals(org.compiere.model.GridField.REQUEST_FOCUS))
			requestFocus();
		// metas end
	}   //  propertyChange

	/**
	 *  Action Listener Interface - NOP
	 *  @param listener listener
	 */
	@Override
	public void addActionListener(ActionListener listener)
	{
	}   //  addActionListener

	/**************************************************************************
	 *	Key Listener Interface
	 *  @param e event
	 */
	@Override
	public void keyTyped(KeyEvent e)	{}
	@Override
	public void keyPressed(KeyEvent e)	{}

	/**
	 * 	Key Released
	 *	if Escape restore old Text.
	 *  @param e event
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		//  ESC
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			setText(m_initialText);
		m_setting = true;
		try
		{
			fireVetoableChange(m_columnName, m_oldText, getText());
		}
		catch (PropertyVetoException pve)	{}
		m_setting = false;
	}	//	keyReleased

	/**
	 *  Set Field/WindowNo for ValuePreference
	 *  @param mField field model
	 */
	@Override
	public void setField (org.compiere.model.GridField mField)
	{
		m_mField = mField;

		EditorContextPopupMenu.onGridFieldSet(this);
	}   //  setField

	@Override
	public GridField getField() {
		return m_mField;
	}

	// metas
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}
}	//	VTextLong
