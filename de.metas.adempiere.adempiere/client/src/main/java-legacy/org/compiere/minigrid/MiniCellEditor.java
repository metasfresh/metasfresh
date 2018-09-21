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

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Properties;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.compiere.apps.search.IInfoColumnController;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.VPAttribute;
import org.compiere.grid.ed.VString;
import org.compiere.model.MPAttributeLookup;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.IClientUI;
import de.metas.util.Services;

/**
 * MiniTable Cell Editor based on class - Timestamp, BigDecimal
 * 
 * @author Jorg Janke
 * @version $Id: MiniCellEditor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class MiniCellEditor extends AbstractCellEditor implements TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4431508736596874253L;

	/**
	 * Default Constructor
	 * 
	 * @param clazz Class
	 */
	public MiniCellEditor(final ColumnInfo columnInfo, final int displayTypeToUse)
	{
		this(columnInfo, displayTypeToUse, columnInfo.getColClass());
	}

	public MiniCellEditor(final ColumnInfo columnInfo, final int displayTypeToUse, final Class<?> clazz)
	{
		super();
		// Date

		this.columnInfo = columnInfo;
		this.modelValueClass = clazz == null ? columnInfo.getColClass() : clazz;
		final boolean mandatory = false;
		final boolean readonly = false;
		final boolean updateable = true;

		final JComponent editorComp;
		if (modelValueClass == Timestamp.class)
		{
			final VDate editor = new VDate();
			editorComp = editor;
			m_editor = editor;
			editorValueClass = Timestamp.class;
		}
		else if (displayTypeToUse == DisplayType.PAttribute)
		{
			final Properties ctx = Env.getCtx();
			final int windowNo = Env.WINDOW_None;
			final MPAttributeLookup attributeLookup = new MPAttributeLookup(ctx, windowNo);
			final VPAttribute editor = new VPAttribute(mandatory, readonly, updateable, windowNo, attributeLookup);
			editorComp = editor;
			m_editor = editor;
			editorValueClass = Integer.class;
		}
		else if (modelValueClass == BigDecimal.class)
		{
			final VNumber editor = new VNumber("Amount", mandatory, readonly, updateable, displayTypeToUse, "Amount");
			editorComp = editor;
			this.m_editor = editor;
			editorValueClass = modelValueClass;
		}
		else if (modelValueClass == Double.class)
		{
			final VNumber editor = new VNumber("Number", mandatory, readonly, updateable, displayTypeToUse, "Number");
			editorComp = editor;
			this.m_editor = editor;
			editorValueClass = modelValueClass;
		}
		else if (modelValueClass == Integer.class)
		{
			final VNumber editor = new VNumber("Integer", mandatory, readonly, updateable, displayTypeToUse, "Integer");
			editorComp = editor;
			this.m_editor = editor;
			editorValueClass = modelValueClass;
		}
		else
		{
			final VString editor = new VString();
			editorComp = editor;
			this.m_editor = editor;
			editorValueClass = modelValueClass;
		}

		final Map<String, Object> editorProperties = columnInfo.getEditorProperties();
		for (final Map.Entry<String, Object> e : editorProperties.entrySet())
		{
			editorComp.putClientProperty(e.getKey(), e.getValue());
		}
	}   // MiniCellEditor

	private final ColumnInfo columnInfo;
	private final VEditor m_editor;
	private final Class<?> modelValueClass;
	private final Class<?> editorValueClass;

	// private MiniTable table;

	@Override
	public Component getTableCellEditorComponent(final JTable table,
			final Object valueModel,
			final boolean isSelected,
			int rowIndexView,
			int columnIndexView)
	{
		final IInfoColumnController columnController = columnInfo.getColumnController();
		
		//
		// Ask Column Controller to customize this editor before it gets activated
		if (columnController != null)
		{
			final int rowIndexModel = table.convertRowIndexToModel(rowIndexView);
			final int columnIndexModel = table.convertColumnIndexToModel(columnIndexView);
			columnController.prepareEditor(m_editor, valueModel, rowIndexModel, columnIndexModel);
		}

		// Set Value
		final Object valueEditor = convertToEditorValue(valueModel);
		m_editor.setValue(valueEditor);

		// Set UI
		m_editor.setBorder(null);
		// m_editor.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		m_editor.setFont(table.getFont());
		return (Component)m_editor;
	}	// getTableCellEditorComponent

	@Override
	public Object getCellEditorValue()
	{
		if (m_editor == null)
		{
			return null;
		}

		final Object valueEditor = m_editor.getValue();

		//
		// Check and convert the value if needed
		final Object valueModel;
		if (valueEditor instanceof Number && modelValueClass == KeyNamePair.class)
		{
			final int key = ((Number)valueEditor).intValue();
			final String name = m_editor.getDisplay();
			valueModel = new KeyNamePair(key, name);
		}
		else
		{
			valueModel = valueEditor;
		}

		return valueModel;
	}

	@Override
	public boolean stopCellEditing()
	{
		try
		{
			return super.stopCellEditing();
		}
		catch (Exception e)
		{
			final Component comp = (Component)m_editor;
			final int windowNo = Env.getWindowNo(comp);
			Services.get(IClientUI.class).warn(windowNo, e);
		}

		return false;
	}

	@Override
	public void cancelCellEditing()
	{
		super.cancelCellEditing();
	}

	private Object convertToEditorValue(final Object valueModel)
	{
		final Object valueEditor;
		if (valueModel instanceof KeyNamePair && editorValueClass == Integer.class)
		{
			valueEditor = ((KeyNamePair)valueModel).getKey();
		}
		else
		{
			valueEditor = valueModel;
		}

		return valueEditor;
	}

}   // MiniCellEditor
