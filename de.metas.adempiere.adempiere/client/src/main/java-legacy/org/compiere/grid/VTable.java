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
package org.compiere.grid;

import java.awt.Component;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.table.TableModel;

import org.compiere.grid.ed.VCellEditor;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridTab;
import org.compiere.model.GridTable;
import org.compiere.swing.CColumnControlButton;
import org.compiere.swing.CTable;
import org.compiere.swing.CTableModelRowSorter;
import org.jdesktop.swingx.action.BoundAction;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Table Grid based on CTable.
 * Used in GridController
 *
 * @author  Jorg Janke
 * @version $Id: VTable.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author 	Teo Sarca, SC ARHIPAC SERVICE SRL - FR [ 1753943 ]
 */
public final class VTable extends CTable 
	implements PropertyChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2655102084935019329L;
	private final static String PACK_ALL_COMMAND = CColumnControlButton.COLUMN_CONTROL_MARKER + "packAll";
	
	/**
	 *	Default Constructor
	 */
	public VTable()
	{
		super();
		setAutoscrolls(true);
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		new VTableExcelAdapter(this); // teo_sarca - FR [ 1753943 ]
		addMouseListener(new VTableMouseListener()); // metas-2009_0021_AP1_G140
		
		getActionMap().put(PACK_ALL_COMMAND, createPackAllAction());
	}	//	VTable
	
	
	private Action createPackAllAction() 
	{
		//TODO: localization
		BoundAction action = new BoundAction("Size All Column", PACK_ALL_COMMAND);
		action.setLongDescription("Size all column to fit content");
		action.registerCallback(this, "packAll");
		return action;
	}
	
	/**
	 * Size all column to fit content.
	 * 
	 * @see #PACK_ALL_COMMAND
	 */
	public void packAll() 
	{
		autoSize(true);
	}

	/**	Logger			*/
	private static final transient Logger log = LogManager.getLogger(VTable.class);
	
	/**
	 *  Property Change Listener for CurrentRow.
	 *  - Selects the current row if not already selected
	 *  - Required when navigating via Buttons
	 *  @param evt event
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals(GridTab.PROPERTY))
		{
			final int row = ((Integer)evt.getNewValue()).intValue();
			final int selRow = getSelectedRow();
			if (row == selRow)
				return;
			setRowSelectionInterval(row,row);
			setColumnSelectionInterval(0, 0);
		    Rectangle cellRect = getCellRect(row, 0, false);
		    if (cellRect != null)
		    	scrollRectToVisible(cellRect);
		}
	}   //  propertyChange
	
	@Override
	protected CTableModelRowSorter createModelRowSorter()
	{
		return new CTableModelRowSorter(this)
		{
			@Override
			protected void sort (final int modelColumnIndex)
			{
				final int rows = getRowCount();
				if (rows <= 0)
					return;
				
				//
				final TableModel model = getModel();
				if (!(model instanceof GridTable))
				{
					super.sort(modelColumnIndex);
					return;
				}
				final GridTable gridTable = (GridTable)model;

				sorting = true;
				try
				{
					//  other sort columns
					final boolean sortAscending = addToSortColumnsAndRemoveOtherColumns(modelColumnIndex);
					gridTable.sort(modelColumnIndex, sortAscending);
				}
				finally
				{
					sorting = false;
				}
				//  table model fires "Sorted" DataStatus event which causes MTab to position to row 0
			}   //  sort
		};
	}

	/**
	 *  Transfer focus explicitly to editor due to editors with multiple components
	 *
	 *  @param row row
	 *  @param column column
	 *  @param e event
	 *  @return true if cell is editing
	 */
	@Override
	public boolean editCellAt (int row, int column, java.util.EventObject e)
	{
		if (!super.editCellAt(row, column, e))
			return false;
	//	log.debug( "VTable.editCellAt", "r=" + row + ", c=" + column);

		Object ed = getCellEditor();
		if (ed instanceof VEditor)
			((Component)ed).requestFocus();
		else if (ed instanceof VCellEditor)
		{
			ed = ((VCellEditor)ed).getEditor();
			((Component)ed).requestFocus();
		}
		return true;
	}   //  editCellAt

	/**
	 *  toString
	 *  @return String representation
	 */
	@Override
	public String toString()
	{
		return new StringBuilder("VTable[")
			.append(getModel()).append("]").toString();
	}   //  toString
}	//	VTable
