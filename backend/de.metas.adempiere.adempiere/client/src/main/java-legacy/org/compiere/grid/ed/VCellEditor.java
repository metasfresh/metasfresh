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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.util.EventObject;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Services;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.GridRowCtx;
import org.compiere.grid.VTable;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridTabLayoutMode;
import org.compiere.model.GridTable;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 *  Cell Editor.
 *  <pre>
 *		Sequence of events:
 *			isCellEditable
 *			getTableCellEditor
 *			shouldSelectCell
 *			getCellEditorValue
 *  </pre>
 *  A new Editor is created for editable columns.
 *  @author 	Jorg Janke
 *  @version 	$Id: VCellEditor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public final class VCellEditor extends AbstractCellEditor
	implements TableCellEditor, VetoableChangeListener, ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1406944397509282968L;

	/**
	 *	Constructor for Grid
	 *  @param mField
	 */
	public VCellEditor (final VTable table, final GridField mField)
	{
		super();
		m_table = table;
		m_mField = mField;
	}	//	VCellEditor

	// services
	private static final transient Logger log = LogManager.getLogger(VCellEditor.class);
	private final transient ISwingEditorFactory editorFactory = Services.get(ISwingEditorFactory.class);

	/** The Field               */
	private final GridField m_mField;
	/** The Table Editor        */
	private VEditor m_editor = null;
	
	/** Table                   */
	private final JTable m_table;
	private ActionListener actionListener;
	
	// metas: current editing cell
	private int	editingRowIndexModel = -1;
	private int	editingColumnIndexModel = -1;
	private int	editingKeyId = -100;

	
	/** ClickCount              */
	private static final int CLICK_TO_START = 1;
	
	/**
	 *  Create Editor
	 */
	private void createEditor()
	{
		m_editor = editorFactory.getEditor(m_mField, true); // tableEditor=true
		m_editor.addVetoableChangeListener(this);
		m_editor.addActionListener(this);
				
	}   //  createEditor

	/**
	 *	Ask the editor if it can start editing using anEvent.
	 *	If editing can be started this method returns true.
	 *	Previously called: MTable.isCellEditable
	 *  @param anEvent event
	 *  @return true if editable
	 */
	@Override
	public boolean isCellEditable (final EventObject anEvent)
	{
		//
		// Avoid start editing on ESC or some ActionKey press (e.g. caps-lock)
		// NOTE: to understand how editing is automatically triggered or key-press, see javax.swing.JTable.processKeyBinding(KeyStroke, KeyEvent, int, boolean)
		if(anEvent instanceof KeyEvent)
		{
			final KeyEvent keyEvent = (KeyEvent)anEvent;
			
			// Don't start editing when use is pressing ESCAPE because that's contra-intuitive.
			final int keyCode = keyEvent.getKeyCode();
			if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_CANCEL)
			{
				return false;
			}
			
			// Don't start editing on action key press (e.g. caps-lock, num-lock)
			if (keyEvent.isActionKey())
			{
				return false;
			}
			
			// Don't start editing on other key codes which are not visually representable (i.e. user is not actually typing)
			// e.g. of keys we try to avoid this this condition are VolumeUp/Down etc 
			final char keyChar = keyEvent.getKeyChar();
			if (!Character.isDefined(keyChar))
			{
				return false;
			}
		}

		//
		// Extract the row index
		final int rowIndexModel;
		if (anEvent instanceof MouseEvent)
		{
			final MouseEvent me = (MouseEvent)anEvent;
			
			//	not enough mouse clicks
			if (me.getClickCount() < CLICK_TO_START)
			{
				return false;
			}
			
			final int rowIndexView = m_table.rowAtPoint(me.getPoint());
			rowIndexModel = m_table.convertRowIndexToModel(rowIndexView);
		}
		// Other events:
		// java.awt.event.KeyEvent - when user navigate with keyboard arrows to a cell and then presses a key to type
		else
		{
			rowIndexModel = m_mField.getGridTab().getCurrentRow();
		}

		//
		// Make sure the GridField is editable
		final Properties rowCtx = new GridRowCtx(Env.getCtx(), m_mField.getGridTab(), rowIndexModel); // metas-2009_0021_AP1_CR053
		if (!m_mField.isEditable (rowCtx))	//	row data is in context
		{
			return false;
		}

		//
		// Create editor if not already created
		if (m_editor == null)
		{
			createEditor();
		}
		
		return true;
	}	//	isCellEditable

	/**
	 *	Sets an initial value for the editor. This will cause the editor to
	 *	stopEditing and lose any partially edited value if the editor is editing
	 *	when this method is called.
	 *	Returns the component that should be added to the client's Component hierarchy.
	 *	Once installed in the client's hierarchy this component
	 *	will then be able to draw and receive user input.
	 *
	 *  @param table
	 *  @param value
	 *  @param isSelected
	 *  @param row
	 *  @param col
	 *  @return component
	 */
	@Override
	public Component getTableCellEditorComponent (final JTable table, final Object value, final boolean isSelected, final int rowIndexView, final int columnIndexView)
	{
		if(log.isTraceEnabled())
			log.trace(m_mField.getColumnName() + ": Value=" + value + ", row(view)=" + rowIndexView + ", col(view)=" + columnIndexView);
		
		if (rowIndexView >= 0)
			table.setRowSelectionInterval(rowIndexView, rowIndexView);     //  force moving to new row
		
		if (m_editor == null) 
			createEditor();
		
		final int rowIndexModel = table.convertRowIndexToModel(rowIndexView);
		final int columnIndexModel = table.convertColumnIndexToModel(columnIndexView);
		
		final Properties rowCtx = new GridRowCtx(Env.getCtx(), m_mField.getGridTab(), rowIndexModel); // metas-2009_0021_AP1_CR053
		
		// If the current cell is not displayed we shall have no editor
		if (!m_mField.isDisplayed(rowCtx, GridTabLayoutMode.Grid))
		{
			return null;
		}

		
		// metas: begin: set current cell position info
		editingColumnIndexModel = columnIndexModel;
		editingRowIndexModel = rowIndexModel;
		editingKeyId = ((GridTable)table.getModel()).getKeyID(rowIndexModel);
		// metas: end
		
		if (m_editor instanceof VLookup) 
		{
			((VLookup)m_editor).setStopEditing(false);
		}
		
		m_editor.setReadWrite(m_mField.isEditable (rowCtx));

		//	Set Value
		m_editor.setValue(value);
		
		//	Set Background/Foreground to "normal" (unselected) colors
		m_editor.setBackground(m_mField.isError());
		m_editor.setForeground(AdempierePLAF.getTextColor_Normal());

		//  Other UI
		m_editor.setFont(table.getFont());
		if ( m_editor instanceof VLookup)
		{
			final VLookup lookup = (VLookup)m_editor;
			if (lookup.getComponents()[0] instanceof JComboBox)
			{
				lookup.setBorder(BorderFactory.createEmptyBorder());
			}
			else
			{
				lookup.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			}
		}
		else
		{
			m_editor.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		}
		//
		return editorFactory.getEditorComponent(m_editor);
	}	//	getTableCellEditorComponent

	/**
	 *	The editing cell should be selected or not
	 *  @param e event
	 *  @return true (constant)
	 */
	@Override
	public final boolean shouldSelectCell(EventObject e)
	{
		return true;
	}	//	shouldSelectCell

	/**
	 * Returns the value contained in the editor
	 * 
	 * @return value
	 */
	@Override
	public Object getCellEditorValue()
	{
		if(m_editor == null)
		{
			return null;
		}
		
		if(log.isTraceEnabled())
			log.trace(m_mField.getColumnName() + ": " + m_editor.getValue());
		
		return m_editor.getValue();
	}	//	getCellEditorValue

	/**
	 *  VEditor Change Listener (property name is columnName).
	 *  - indicate change  (for String/Text/..) <br>
	 *  When editing is complete the value is retrieved via getCellEditorValue
	 *  @param e event
	 */
	@Override
	public void vetoableChange(final PropertyChangeEvent e)
	{
		if (m_table == null)
		{
			return;
		}
		
		log.debug("{}", e);
		//
		final GridTable gridTable = (GridTable)m_table.getModel();
		
		gridTable.setChanged(true);
		
		// metas: update value now
		final int tableEditingRowIndexView = m_table.getEditingRow();
		final int tableEditingRowIndexModel = m_table.convertRowIndexToModel(tableEditingRowIndexView);
		final int tableEditingColumnIndexView = m_table.getEditingColumn();
		final int tableEditingColumnIndexModel = m_table.convertColumnIndexToModel(tableEditingColumnIndexView);
		
		final Object valueToSet = e.getNewValue();
		
		if (m_editor != null
				&& m_editor.isAutoCommit()
				&& m_editor == m_table.getEditorComponent()
				&& isValid()
				&& valueToSet != null
				// make sure we are still editing the save cell as we started
				// i.e. table model did not change in meantime, record order did not change
				// TODO: handle compose primary key tables like M_Replenish
				&& editingColumnIndexModel == tableEditingColumnIndexModel
				&& editingRowIndexModel == tableEditingRowIndexModel
				&& editingKeyId == gridTable.getKeyID(tableEditingRowIndexModel)
			)
		{
			m_table.setValueAt(valueToSet, tableEditingRowIndexView, tableEditingColumnIndexView);
		}
	}   //  vetoableChange

	
	/**
	 * @return true if the editor contains a valid value which can be propagated to underlying table model
	 */
	public boolean isValid()
	{
		if (m_editor == null)
		{
			return false;
		}
		
		if (m_editor instanceof VDate)
		{
			final VDate dateEditor = (VDate)m_editor;
			return dateEditor.isValidDate();
		}
		return true;
	}

	/**
	 * Get Actual Editor.
	 * 
	 * @return VEditor
	 */
	public VEditor getEditor()
	{
		return m_editor;
	}   //  getEditor

	/**
	 * {@link VEditor}'s action performed:
	 * <ul>
	 * <li>automatically stop editing (for some editors)
	 * <li>call delegated {@link #actionListener}
	 * </ul>
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed (final ActionEvent e)
	{
		if(m_editor == null)
		{
			return;
		}
		
		if (e.getSource() != m_editor)
		{
			return;
		}
		
		if(log.isTraceEnabled())
			log.trace(m_mField.getColumnName() + ": Value=" + m_editor.getValue());
		
		//
		// When user presses enter, automatically stop editing (for some basic text fields)
		final int displayType = m_mField.getDisplayType();
		if (DisplayType.isText(displayType) || DisplayType.isNumeric(displayType) || DisplayType.isDate(displayType))
		{
			stopCellEditing();
		}

		//
		// Fire delegated action listener 
		if (actionListener != null)
		{
			actionListener.actionPerformed(e);
		}
	}   //  actionPerformed

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		if (m_editor != null)
		{
			m_editor.dispose();
			m_editor = null;
		}
		
		actionListener = null;
		
//		m_mField = null;
//		m_table = null;
	}	//	dispose
	
	/**
	 * @return true if we can stop current editor
	 */
	private boolean canStopEditing()
	{
		// nothing atm
		return true;
	}
	
	@Override
	public boolean stopCellEditing()
	{
		if (!canStopEditing())
		{
			return false;
		}
		
		if (!super.stopCellEditing())
		{
			return false;
		}
		
		clearCurrentEditing();
		
		return true;
	}

	@Override
	public void cancelCellEditing()
	{
		if (!canStopEditing())
		{
			return ;
		}

		clearCurrentEditing();
		
		super.cancelCellEditing();
	}
	
	private void clearCurrentEditing()
	{
		// metas: reset editing coordinates
		editingRowIndexModel = -1;
		editingColumnIndexModel = -1;
		editingKeyId = -100;
		
		if (m_editor instanceof VLookup)
		{
			((VLookup)m_editor).setStopEditing(true);
		}
	}

	public void setActionListener(final ActionListener listener)
	{
		actionListener = listener;
	}
}	//	VCellEditor
