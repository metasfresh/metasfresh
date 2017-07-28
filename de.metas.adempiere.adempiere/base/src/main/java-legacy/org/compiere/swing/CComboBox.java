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
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.plaf.ComboBoxUI;

import org.adempiere.plaf.AdempiereComboBoxUI;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorUI;
import org.adempiere.ui.editor.ICopyPasteSupportEditor;
import org.adempiere.ui.editor.ICopyPasteSupportEditorAware;
import org.adempiere.ui.editor.JComboBoxCopyPasteSupportEditor;
import org.adempiere.util.Check;
import org.compiere.util.Trace;

/**
 *  Adempiere Colored Combo Box.
 *
 *  @author     Jorg Janke
 *  @version    $Id: CComboBox.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CComboBox<E> extends JComboBox<E>
	implements CEditor
	, ICopyPasteSupportEditorAware
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4605625077881909766L;

	/**
	 * Creates a <code>JComboBox</code> that takes it's items from an
	 * existing <code>ComboBoxModel</code>.  Since the
	 * <code>ComboBoxModel</code> is provided, a combo box created using
	 * this constructor does not create a default combo box model and
	 * may impact how the insert, remove and add methods behave.
	 *
	 * @param aModel the <code>ComboBoxModel</code> that provides the
	 * 		displayed list of items
	 * @see DefaultComboBoxModel
	 */
	public CComboBox(ComboBoxModel<E> aModel)
	{
		super(aModel);
		init();
	}   //  CComboBox

	/**
	 * Creates a <code>JComboBox</code> that contains the elements
	 * in the specified array.  By default the first item in the array
	 * (and therefore the data model) becomes selected.
	 *
	 * @param items  an array of objects to insert into the combo box
	 * @see DefaultComboBoxModel
	 */
	public CComboBox(final E items[])
	{
		super(items);
		init();
	}   //  CComboBox

	/**
	 * Creates a <code>JComboBox</code> that contains the elements
	 * in the specified Vector.  By default the first item in the vector
	 * and therefore the data model) becomes selected.
	 *
	 * @param items  an array of vectors to insert into the combo box
	 * @see DefaultComboBoxModel
	 */
	public CComboBox(Vector<E> items)
	{
		super(items);
		init();
	}   //  CComboBox

	/**
	 * Creates a <code>JComboBox</code> with a default data model.
	 * The default data model is an empty list of objects.
	 * Use <code>addItem</code> to add items.  By default the first item
	 * in the data model becomes selected.
	 *
	 * @see DefaultComboBoxModel
	 */
	public CComboBox()
	{
		super();
		init();
	}   //  CComboBox
	
	public CComboBox(final List<E> items)
	{
		super();
		setModel(new ListComboBoxModel<>(items));
		
		init();
	}   //  CComboBox
	
	@Override
	public void updateUI()
	{
		super.updateUI();
		VEditorUI.installMinMaxSizes(this);
	}
	
	/**
	 *  Common initialization
	 */
	private void init()
	{
	}

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
		if (super.isEnabled() != rw)
			super.setEnabled (rw);
		setBackground(false);
	}   //  setReadWrite

	/**
	 *	Is it possible to edit
	 *  @return true, if editable
	 */
	@Override
	public boolean isReadWrite()
	{
		return super.isEnabled();
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
	 *  @param bg
	 */
	@Override
	public void setBackground (Color bg)
	{
		if (bg.equals(getBackground()))
			return;
		// Set same color for editor component - teo_sarca [ 1735122 ]
		if (getEditor() != null && getEditor().getEditorComponent() != null)
			getEditor().getEditorComponent().setBackground(bg);
		super.setBackground(bg);
	}   //  setBackground

	/**
	 *	Set Editor to value
	 *  @param value value of the editor
	 */
	@Override
	public void setValue (Object value)
	{
		super.setSelectedItem(value);
	}   //  setValue

	/**
	 *	Return Editor value
	 *  @return current value
	 */
	@Override
	public Object getValue()
	{
		return super.getSelectedItem();
	}   //  getValue

	/**
	 *  Return Display Value
	 *  @return displayed String value
	 */
	@Override
	public String getDisplay()
	{
		Object o = super.getSelectedItem();
		if (o == null)
			return "";
		return o.toString();
	}   //  getDisplay
	
	private final JButton getArrowButton()
	{
		final ComboBoxUI ui = getUI();
		if (ui instanceof org.adempiere.plaf.AdempiereComboBoxUI)
		{
			final JButton b = ((org.adempiere.plaf.AdempiereComboBoxUI)ui).getArrowButton();
			return b;
		}

		return null;
	}

	/**
	 *  Add Mouse Listener - 1-4-0 Bug.
	 *  Bug in 1.4.0 Metal: arrowButton gets Mouse Events, so add the JComboBox
	 *  MouseListeners to the arrowButton - No context menu if right-click
	 *  @see AdempiereComboBoxUI#installUI(JComponent)
	 *  @param ml
	 */
	@Override
	public void addMouseListener (MouseListener ml)
	{
		super.addMouseListener(ml);

		final JButton arrowButton = getArrowButton();
		if (arrowButton != null && !Trace.getCallerClass(1).startsWith("javax"))
		{
			arrowButton.addMouseListener(ml);
		}
	}

	/**
	 *  Remove Mouse Listener.
	 *  @param ml
	 */
	@Override
	public void removeMouseListener (MouseListener ml)
	{
		super.removeMouseListener(ml);
		
		final JButton arrowButton = getArrowButton();
		if (arrowButton != null)
		{
			arrowButton.removeMouseListener(ml);
		}
	}   //  removeMouseListener

	/**
	 * 	Set Action Command
	 *	@param actionCommand command 
	 */
	@Override
	public void setActionCommand (String actionCommand)
	{
		super.setActionCommand (actionCommand);
		if (getName() == null && actionCommand != null && actionCommand.length() > 0)
			setName(actionCommand);
	}	//	setActionCommand

	public boolean isSelectionNone()
	{
		final Object selectedItem = getSelectedItem();
		if (selectedItem == null)
		{
			return true;
		}
		else
		{
			return Check.isEmpty(selectedItem.toString(), true);
		}
	}
	
	@Override
	public E getSelectedItem()
	{
		final Object selectedItemObj = super.getSelectedItem();
		
		@SuppressWarnings("unchecked")
		final E selectedItem = (E)selectedItemObj;
		return selectedItem;
	}
	
	/**
	 * Enables auto completion (while user writes) on this combobox.
	 * 
	 * @return combobox's auto-completion instance for further configurations
	 */
	public final ComboBoxAutoCompletion<E> enableAutoCompletion()
	{
		return ComboBoxAutoCompletion.enable(this);
	}
	
	/**
	 * Disable autocompletion on this combobox.
	 * 
	 * If the autocompletion was not enabled, this method does nothing.
	 */
	public final void disableAutoCompletion()
	{
		ComboBoxAutoCompletion.disable(this);
	}

	@Override
	public ICopyPasteSupportEditor getCopyPasteSupport()
	{
		return JComboBoxCopyPasteSupportEditor.ofComponent(this);
	}
}   //  CComboBox
