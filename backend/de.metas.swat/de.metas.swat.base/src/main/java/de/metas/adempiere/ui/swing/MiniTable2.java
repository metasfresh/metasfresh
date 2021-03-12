/**
 * 
 */
package de.metas.adempiere.ui.swing;

/*
 * #%L
 * de.metas.swat.base
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

import java.awt.Container;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.util.DB;

import de.metas.adempiere.ui.MiniTableUtil;

/**
 * @author tsa
 *
 */
public class MiniTable2 extends MiniTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8891696378896964926L;

	public static final String PROPERTY_SelectedId = "SelectedId";

	public static final int ROW_SIZE = 50;

	private final Logger log = LogManager.getLogger(getClass());

	private String sql;

	private int idColumnIndex = -1;

	private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	private final ListSelectionListener selectionListener = new ListSelectionListener()
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			if (!fireSelectionEvent || e.getValueIsAdjusting())
				return;

			int id = getSelectedId();
			listeners.firePropertyChange(PROPERTY_SelectedId, null, id);
		}
	};


	protected MiniTable2()
	{
		super();
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setMultiSelection(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowHeight(ROW_SIZE);
		setAutoResize(false);
		setFocusable(false);
		setFillsViewportHeight(true); // @Trifon
		growScrollbars();
		
		getSelectionModel().addListSelectionListener(selectionListener);
	}

	@Override
	public void setAutoResize(boolean autoResize) 
	{
		super.setAutoResize(autoResize);
	}
	
	public void addListener(PropertyChangeListener listener)
	{
		listeners.addPropertyChangeListener(listener);
	}

	private static final int SCROLL_Size = 30;

	private void growScrollbars()
	{
		// fatter scroll bars
		Container p = getParent();
		if (p instanceof JViewport)
		{
			Container gp = p.getParent();
			if (gp instanceof JScrollPane)
			{
				JScrollPane scrollPane = (JScrollPane)gp;
				scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_Size, 0));
				scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, SCROLL_Size));
			}
		}
	}

	@Override
	public String prepareTable(ColumnInfo[] layout, String from, String where, boolean multiSelection, String tableName)
	{
		for (int i = 0; i < layout.length; i++)
		{
			if (layout[i].getColClass() == IDColumn.class)
			{
				idColumnIndex = i;
				break;
			}
		}
		sql = super.prepareTable(layout, from, where, multiSelection, tableName);
		return sql;
	}

	public String getSQL()
	{
		return sql;
	}

	public void prepareTable(Class<?> cl, String[] columnNames, String sqlWhere, String sqlOrderBy)
	{
		ColumnInfo[] layout = MiniTableUtil.createColumnInfo(cl, columnNames);
		String tableName = MiniTableUtil.getTableName(cl);
		String from = tableName;
		final boolean multiSelection = false;
		prepareTable(layout, from, sqlWhere, multiSelection, tableName);
		if (!Check.isEmpty(sqlOrderBy, true))
		{
			sql += " ORDER BY " + sqlOrderBy;
		}
	}

	public void loadData(Object... sqlParams)
	{
		if (sql == null)
			throw new IllegalStateException("Table not initialized. Please use prepareTable method first");

		int selectedId = getSelectedId();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			this.loadTable(rs);
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		selectById(selectedId);
	}

	public int getSelectedId()
	{
		if (idColumnIndex < 0)
			return -1;

		int row = getSelectedRow();
		if (row != -1)
		{
			Object data = getModel().getValueAt(row, 0);
			if (data != null)
			{
				Integer id = ((IDColumn)data).getRecord_ID();
				return id == null ? -1 : id.intValue();
			}
		}

		return -1;
	}

	public void selectById(int id)
	{
		selectById(id, true);
	}

	public void selectById(int id, boolean fireEvent)
	{
		if (idColumnIndex < 0)
			return;

		boolean fireSelectionEventOld = fireSelectionEvent;
		try
		{
			fireSelectionEvent = fireEvent;
			if (id < 0)
			{
				this.getSelectionModel().removeSelectionInterval(0, this.getRowCount());
				return;
			}

			for (int i = 0; i < this.getRowCount(); i++)
			{
				IDColumn key = (IDColumn)this.getModel().getValueAt(i, idColumnIndex);
				if (key != null && id > 0 && key.getRecord_ID() == id)
				{
					this.getSelectionModel().setSelectionInterval(i, i);
					break;
				}
			}
		}
		finally
		{
			fireSelectionEvent = fireSelectionEventOld;
		}
	}

	private boolean fireSelectionEvent = true;

}
