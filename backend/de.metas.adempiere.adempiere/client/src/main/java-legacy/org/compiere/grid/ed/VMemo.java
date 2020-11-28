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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.swing.CTextArea;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 *  Text Control (JTextArea embedded in JScrollPane)
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VMemo.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class VMemo extends CTextArea
	implements VEditor, KeyListener, FocusListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1589654941310687511L;
	
	/**
	 *	IDE Baan Constructor
	 */
	public VMemo()
	{
		this("", false, false, true, 60, 4000);
	}	//	VMemo

	/**
	 *	Standard Constructor
	 *  @param columnName
	 *  @param mandatory
	 *  @param isReadOnly
	 *  @param isUpdateable
	 *  @param displayLength
	 *  @param fieldLength
	 */
	VMemo (String columnName, boolean mandatory, boolean isReadOnly, boolean isUpdateable, int displayLength, int fieldLength)
	{
		super (fieldLength/80, 50);
		super.setName(columnName);
		LookAndFeel.installBorder(this, "TextField.border");
		this.addFocusListener(this);    //  to activate editor

		//  Create Editor
		setColumns(displayLength>VString.MAXDISPLAY_LENGTH ? VString.MAXDISPLAY_LENGTH : displayLength);	//  46
		setForeground(AdempierePLAF.getTextColor_Normal());
		setBackground(AdempierePLAF.getFieldBackground_Normal());

		setLineWrap(true);
		setWrapStyleWord(true);
		addFocusListener(this);
		setInputVerifier(new CInputVerifier()); //Must be set AFTER addFocusListener in order to work
		setMandatory(mandatory);
		m_columnName = columnName;
//		m_fieldLength = fieldLength;

		if (isReadOnly || !isUpdateable)
			setReadWrite(false);
		addKeyListener(this);


		// Create and bind the context menu
		new EditorContextPopupMenu(this);
	}	//	VMemo

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
	}   //  dispose

//	private int			m_fieldLength;

	private String		m_columnName;
	private String		m_oldText = "";
	private boolean		m_firstChange;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VMemo.class);

	/**
	 *	Set Editor to value
	 *  @param value
	 */
	@Override
	public void setValue(Object value)
	{
		super.setValue(value);
		m_firstChange = true;
		//	Always position Top 
		setCaretPosition(0);
	}	//	setValue

	/**
	 *  Property Change Listener
	 *  @param evt
	 */
	@Override
	public void propertyChange (PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(org.compiere.model.GridField.PROPERTY))
			setValue(evt.getNewValue());
	}   //  propertyChange

	/**
	 *  Action Listener Interface - NOP
	 *  @param listener
	 */
	@Override
	public void addActionListener(ActionListener listener)
	{
	}   //  addActionListener

	/**************************************************************************
	 *	Key Listener Interface
	 *  @param e
	 */
	@Override
	public void keyTyped(KeyEvent e)	{}
	@Override
	public void keyPressed(KeyEvent e)	{}

	/**
	 *	Escape 	- Restore old Text.
	 *  Indicate Change
	 *  @param e
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		//  ESC
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !getText().equals(m_oldText))
		{
			log.debug( "VMemo.keyReleased - ESC");
			setText(m_oldText);
			return;
		}
		//  Indicate Change
		if (m_firstChange && !m_oldText.equals(getText()))
		{
			log.debug( "VMemo.keyReleased - firstChange");
			m_firstChange = false;
			try
			{
				String text = getText();
//				fireVetoableChange (m_columnName, text, null); //  No data committed - done when focus lost !!!
				// metas: Korrektur null fuehrt dazu, dass eine aenderung nicht erkannt wird.
				fireVetoableChange(m_columnName, text, getText());
			}
			catch (PropertyVetoException pve)	{}
		}	//	firstChange
	}	//	keyReleased

	/**
	 *	Focus Gained	- Save for Escape
	 *  @param e
	 */
	@Override
	public void focusGained (FocusEvent e)
	{
		log.info(e.paramString());
		if (e.getSource() instanceof VMemo)
			requestFocus();
		else
			m_oldText = getText();
	}	//	focusGained

	/**
	 *	Data Binding to MTable (via GridController)
	 *  @param e
	 */
	@Override
	public void focusLost (FocusEvent e)
	{
		//log.info( "VMemo.focusLost " + e.getSource(), e.paramString());
		//	something changed?
		return;

	}	//	focusLost

	/*************************************************************************/

	//	Field for Value Preference
	private GridField          m_mField = null;
	/**
	 *  Set Field/WindowNo for ValuePreference (NOP)
	 *  @param mField
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


private class CInputVerifier extends InputVerifier {

	 @Override
	public boolean verify(JComponent input) {


		//NOTE: We return true no matter what since the InputVerifier is only introduced to fireVetoableChange in due time
		if (getText() == null && m_oldText == null)
			return true;
		else if (getText().equals(m_oldText))
			return true;
		//
		try
		{
			String text = getText();
			fireVetoableChange(m_columnName, null, text);
			m_oldText = text;
			return true;
		}
		catch (PropertyVetoException pve)	{}
		return true;

	 } // verify

   } // CInputVerifier

	// metas
	@Override
	public boolean isAutoCommit()
	{
		return true;
	}

}	//	VMemo
