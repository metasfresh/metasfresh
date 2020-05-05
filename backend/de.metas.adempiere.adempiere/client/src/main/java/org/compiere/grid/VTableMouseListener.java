/**
 * 
 */
package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.adempiere.ui.IContextMenuActionContext;
import org.adempiere.ui.IContextMenuProvider;
import org.compiere.grid.ed.VCellRenderer;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.menu.EditorContextPopupMenu;
import org.compiere.model.GridField;
import org.compiere.model.GridTable;
import org.compiere.util.DisplayType;

import de.metas.util.Services;

/**
 * VTable Mouse Listner
 * 
 * @author teo.sarca@gmail.com
 * @author ts DF018: Aus Provisionsberechnung in Buchauszug springen funktioniert nicht (2010070510000351)
 * 
 */
public class VTableMouseListener extends MouseAdapter
{
	// final private Logger log = CLogMgt.getLogger(getClass());

	private EditorContextPopupMenu contextPopupMenu;

	private String m_columnName = null;
	private GridTable m_gridTable = null;
	private VTable m_vtable = null;

	public VTableMouseListener()
	{
		super();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (!(e.getSource() instanceof VTable))
		{
			return;
		}
		
		//
		m_vtable = (VTable)e.getSource();
		final int rowIndexView = m_vtable.rowAtPoint(e.getPoint());
		final int columnIndexView = m_vtable.columnAtPoint(e.getPoint());

		final TableCellRenderer r = m_vtable.getCellRenderer(rowIndexView, columnIndexView);
		if (!(r instanceof VCellRenderer))
			return;
		//
		m_gridTable = null;
		int displayType = -1;
		if (m_vtable.getModel() instanceof GridTable)
		{
			final int columnIndexModel = m_vtable.getColumnModel().getColumn(columnIndexView).getModelIndex();

			m_gridTable = (GridTable)m_vtable.getModel();
			m_columnName = m_gridTable.getColumnName(columnIndexModel);
			final GridField field = m_gridTable.getGridTab().getField(m_columnName);
			displayType = field.getDisplayType();
		}

		if (e.getClickCount() >= 2)
		{
			// metas: Temp. Ausschalten des Zooms bei Doppelclick
			// actionZoom();
			// metas end
			return;
		}

		final boolean showPopup = SwingUtilities.isRightMouseButton(e);
		if (showPopup)
		{
			contextPopupMenu = getContextMenu(m_vtable.getCellEditor(rowIndexView, columnIndexView), rowIndexView, columnIndexView);
			if (null == contextPopupMenu)
			{
				return;
			}

			contextPopupMenu.show(m_vtable.getComponentAt(e.getPoint()), e.getX(), e.getY());
			return;
		}

		// Make sure context menu is not displayed
		if (contextPopupMenu != null)
		{
			contextPopupMenu.setVisible(false);
			contextPopupMenu = null;
		}

		if (DisplayType.isText(displayType))
		{
			editCellAt(e);
			return;
		}
	}

	private Component editCellAt(MouseEvent e)
	{
		if (m_vtable == null)
		{
			return null;
		}

		final Point p = e.getPoint();
		final boolean canEdit = !m_vtable.isEditing() || m_vtable.getCellEditor().stopCellEditing();

		if (canEdit && m_vtable.editCellAt(m_vtable.rowAtPoint(p), m_vtable.columnAtPoint(p)))
		{
			Component editor = m_vtable.getEditorComponent();
			editor.dispatchEvent(e);
			return editor;
		}
		return null;
	}

	private EditorContextPopupMenu getContextMenu(final TableCellEditor cellEditor, final int rowIndexView, final int columnIndexView)
	{
		if (m_vtable == null)
		{
			// VTable not set yet ?!
			return null;
		}
		
		final Object value = m_vtable.getValueAt(rowIndexView, columnIndexView);
		final Component component = cellEditor.getTableCellEditorComponent(m_vtable, value, true, rowIndexView, columnIndexView); // isSelected=true
		if (component == null)
		{
			// It seems there is no editor for given cell (e.g. one reason could be the cell is not actually displayed)
			return null;
		}

		if (!(component instanceof VEditor))
		{
			return null;
		}
		final VEditor editor = (VEditor)component;

		final IContextMenuActionContext menuCtx = Services.get(IContextMenuProvider.class).createContext(editor, m_vtable, rowIndexView, columnIndexView);
		final EditorContextPopupMenu contextMenu = new EditorContextPopupMenu(menuCtx);
		return contextMenu;
	}
}
