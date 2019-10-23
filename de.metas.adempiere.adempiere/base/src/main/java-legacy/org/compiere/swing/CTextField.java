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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorUI;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.JTextComponentCopyPasteSupportEditor;

/**
 *  Adempiere Text Field
 *
 *  @author     Jorg Janke
 *  @version    $Id: CTextField.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CTextField extends JTextField 
	implements CEditor, KeyListener, ICopyPasteSupportEditorAware

{
	/**
	 * 
	 */
	private static final long serialVersionUID = 460529728891168659L;

	/** Copy/Paste support */
	private ICopyPasteSupportEditor copyPasteSupport;

	/**
	 * Constructs a new <code>TextField</code>.  A default model is created,
	 * the initial string is <code>null</code>,
	 * and the number of columns is set to 0.
	 */
	public CTextField()
	{
		super();
		init();
	}   //  CTextField

	/**
	 * Constructs a new <code>TextField</code> initialized with the
	 * specified text. A default model is created and the number of
	 * columns is 0.
	 *
	 * @param text the text to be displayed, or <code>null</code>
	 */
	public CTextField (String text)
	{
		super (text);
		init();
	}   //  CTextField

	/**
	 * Constructs a new empty <code>TextField</code> with the specified
	 * number of columns.
	 * A default model is created and the initial string is set to
	 * <code>null</code>.
	 *
	 * @param columns  the number of columns to use to calculate
	 *   the preferred width; if columns is set to zero, the
	 *   preferred width will be whatever naturally results from
	 *   the component implementation
	 */
	public CTextField (int columns)
	{
		super (columns);
		init();
	}   //  CTextField

	/**
	 * Constructs a new <code>TextField</code> initialized with the
	 * specified text and columns.  A default model is created.
	 *
	 * @param text the text to be displayed, or <code>null</code>
	 * @param columns  the number of columns to use to calculate
	 *   the preferred width; if columns is set to zero, the
	 *   preferred width will be whatever naturally results from
	 *   the component implementation
	 */
	public CTextField (String text, int columns)
	{
		super (text, columns);
		init();
	}   //  CTextField

	/**
	 * Constructs a new <code>JTextField</code> that uses the given text
	 * storage model and the given number of columns.
	 * This is the constructor through which the other constructors feed.
	 * If the document is <code>null</code>, a default model is created.
	 *
	 * @param doc  the text storage to use; if this is <code>null</code>,
	 *		a default will be provided by calling the
	 *		<code>createDefaultModel</code> method
	 * @param text  the initial string to display, or <code>null</code>
	 * @param columns  the number of columns to use to calculate
	 *   the preferred width >= 0; if <code>columns</code>
	 *   is set to zero, the preferred width will be whatever
	 *   naturally results from the component implementation
	 * @exception IllegalArgumentException if <code>columns</code> < 0
	 */
	public CTextField (Document doc, String text, int columns)
	{
		super (doc, text, columns);
		init();
	}   //  CTextField

	/**
	 *  Initialization
	 */
	private void init()
	{
		setBackground (false);
		
		//
		// Copy/Paste support
		this.copyPasteSupport = JTextComponentCopyPasteSupportEditor.ofComponent(this);
	}   //  init
	
	@Override
	public void updateUI()
	{
		super.updateUI();
		
		VEditorUI.installMinMaxSizes(this);
	}

	/*************************************************************************/

	/** Mandatory (default false)   */
	private boolean m_mandatory = false;

	/**
	 *	Set Editor Mandatory
	 *  @param mandatory true, if you have to enter data
	 */
	@Override
	public void setMandatory (boolean mandatory)
	{
		m_mandatory = mandatory;
		setBackground(false);
	}   //  setMandatory

	/**
	 *	Is Field mandatory
	 *  @return true, if mandatory
	 */
	@Override
	public boolean isMandatory()
	{
		return m_mandatory;
	}   //  isMandatory

	/**
	 *	Enable Editor
	 *  @param rw true, if you can enter/select data
	 */
	@Override
	public void setReadWrite (boolean rw)
	{
		if (super.isEditable() != rw)
			super.setEditable (rw);
		setBackground(false);
	}   //  setEditable

	/**
	 *	Is it possible to edit
	 *  @return true, if editable
	 */
	@Override
	public boolean isReadWrite()
	{
		return super.isEditable();
	}   //  isReadWrite


	/**
	 *  Set Background based on editable / mandatory / error
	 *  @param error if true, set background to error color, otherwise mandatory/editable
	 */
	@Override
	public void setBackground (boolean error)
	{
		if (error)
			setBackground(AdempierePLAF.getFieldBackground_Error());
		else if (!isReadWrite())
			setBackground(AdempierePLAF.getFieldBackground_Inactive());
		else if (m_mandatory)
			setBackground(AdempierePLAF.getFieldBackground_Mandatory());
		else
			setBackground(AdempierePLAF.getFieldBackground_Normal());
	}   //  setBackground

	/**
	 *  Set Background
	 *  @param bg background
	 */
	@Override
	public void setBackground (Color bg)
	{
		if (bg.equals(getBackground()))
			return;
		super.setBackground(bg);
	}   //  setBackground

	/**
	 *	Set Editor to value
	 *  @param value value of the editor
	 */
	@Override
	public void setValue (Object value)
	{
		if (value == null)
			setText("");
		else
			setText(value.toString());
	}   //  setValue

	/**
	 *	Return Editor value
	 *  @return current value
	 */
	@Override
	public Object getValue()
	{
		return getText();
	}   //  getValue

	/**
	 *  Return Display Value
	 *  @return displayed String value
	 */
	@Override
	public String getDisplay()
	{
		return getText();
	}   //  getDisplay


	/**
	 * 	key Pressed
	 *	@see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 *	@param e
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
	}	//	keyPressed

	/**
	 * 	key Released
	 *	@see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 *	@param e
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
	}	//	keyReleased

	/**
	 * 	key Typed
	 *	@see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 *	@param e
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
	}	//	keyTyped

	@Override
	public final ICopyPasteSupportEditor getCopyPasteSupport()
	{
		return copyPasteSupport;
	}
	
	@Override
	public boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed)
	{
		// NOTE: overridden just to make it public
		return super.processKeyBinding(ks, e, condition, pressed);
	}

}   //  CTextField
