/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.adempiere.webui.apps;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.process.IProcessParameter;
import org.adempiere.ad.security.asp.IASPFiltersFactory;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.Columns;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WEditorPopupMenu;
import org.adempiere.webui.editor.WebEditorFactory;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldVO;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.MLookup;
import org.compiere.model.MPInstancePara;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;

/**
 * Process Parameter Panel, based on existing ProcessParameter dialog.
 * - Embedded in ProcessDialog
 * - checks, if parameters exist and inquires and saves them
 *
 * @author Low Heng Sin
 * @version 2006-12-01
 */
public class ProcessParameterPanel extends Panel
		implements ValueChangeListener, IProcessParameter
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3372945363384709062L;
	private final String width;

	/**
	 * Dynamic generated Parameter panel.
	 * 
	 * @param WindowNo window
	 * @param pi process info
	 */
	public ProcessParameterPanel(int WindowNo, ProcessInfo pi)
	{
		this(WindowNo, pi, "100%");
	}	// ProcessParameterPanel

	/**
	 * Dynamic generated Parameter panel.
	 * 
	 * @param WindowNo window
	 * @param pi process info
	 */
	public ProcessParameterPanel(int WindowNo, ProcessInfo pi, String width)
	{
		super();
		this.m_WindowNo = WindowNo;
		this.adProcessId = pi.getAD_Process_ID();
		this.width = width;
		//
		initComponent();
	}	// ProcessParameterPanel

	private void initComponent()
	{
		centerPanel = GridFactory.newGridLayout();
		centerPanel.setInnerWidth(width);
		this.appendChild(centerPanel);

		// setup columns
		Columns columns = new Columns();
		centerPanel.appendChild(columns);
		Column col = new Column();
		col.setWidth("30%");
		columns.appendChild(col);
		col = new Column();
		col.setWidth("65%");
		columns.appendChild(col);
		col = new Column();
		col.setWidth("5%");
		columns.appendChild(col);
	}

	private final int m_WindowNo;
	private final int adProcessId;
//	private ProcessInfo m_processInfo;
	/** Logger */
	private static Logger log = LogManager.getLogger(ProcessParameterPanel.class);

	//
	private ArrayList<WEditor> m_wEditors = new ArrayList<WEditor>();
	private ArrayList<WEditor> m_wEditors2 = new ArrayList<WEditor>();		// for ranges
	private ArrayList<GridField> m_mFields = new ArrayList<GridField>();
	private ArrayList<GridField> m_mFields2 = new ArrayList<GridField>();
	private ArrayList<Label> m_separators = new ArrayList<Label>();
	//
	private Grid centerPanel = null;

	/**
	 * Dispose
	 */
	public void dispose()
	{
		m_wEditors.clear();
		m_wEditors2.clear();
		m_mFields.clear();
		m_mFields2.clear();

	}   // dispose

	/**
	 * Read Fields to display
	 * 
	 * @return true if loaded OK
	 */
	public boolean init()
	{
		log.info("");

		// ASP
		// NOTE: if you are going to change the "p." alias for AD_Process_Para, pls check the ASPFilters implementation.
		final String ASPFilter = Services.get(IASPFiltersFactory.class)
				.getASPFiltersForClient(Env.getAD_Client_ID(Env.getCtx()))
				.getSQLWhereClause(I_AD_Process_Para.class);

		//
		String sql = null;
		if (Env.isBaseLanguage(Env.getCtx(), "AD_Process_Para"))
			sql = "SELECT p.Name, p.Description, p.Help, "
					+ "p.AD_Reference_ID, p.AD_Process_Para_ID, "
					+ "p.FieldLength, p.IsMandatory, p.IsRange, p.ColumnName, "
					+ "p.DefaultValue, p.DefaultValue2, p.VFormat, p.ValueMin, p.ValueMax, "
					+ "p.SeqNo, p.AD_Reference_Value_ID, vr.Code AS ValidationCode, "
					+ "p.ReadOnlyLogic, p.DisplayLogic "
					+ ", p.IsEncrypted " // metas: tsa: US745
					+ ", p.AD_Val_Rule_ID " // metas: 03577
					+ ", p.IsAutoComplete " // 05887
					+ "FROM AD_Process_Para p"
					+ " LEFT OUTER JOIN AD_Val_Rule vr ON (p.AD_Val_Rule_ID=vr.AD_Val_Rule_ID) "
					+ "WHERE p.AD_Process_ID=?"		// 1
					+ " AND p.IsActive='Y' "
					+ ASPFilter + " ORDER BY SeqNo";
		else
			sql = "SELECT t.Name, t.Description, t.Help, "
					+ "p.AD_Reference_ID, p.AD_Process_Para_ID, "
					+ "p.FieldLength, p.IsMandatory, p.IsRange, p.ColumnName, "
					+ "p.DefaultValue, p.DefaultValue2, p.VFormat, p.ValueMin, p.ValueMax, "
					+ "p.SeqNo, p.AD_Reference_Value_ID, vr.Code AS ValidationCode, "
					+ "p.ReadOnlyLogic, p.DisplayLogic "
					+ ", p.IsEncrypted " // metas: tsa: US745
					+ ", p.AD_Val_Rule_ID " // metas: 03577
					+ ", p.IsAutoComplete " // 05887
					+ "FROM AD_Process_Para p"
					+ " INNER JOIN AD_Process_Para_Trl t ON (p.AD_Process_Para_ID=t.AD_Process_Para_ID)"
					+ " LEFT OUTER JOIN AD_Val_Rule vr ON (p.AD_Val_Rule_ID=vr.AD_Val_Rule_ID) "
					+ "WHERE p.AD_Process_ID=?"		// 1
					+ " AND t.AD_Language='" + Env.getAD_Language(Env.getCtx()) + "'"
					+ " AND p.IsActive='Y' "
					+ ASPFilter + " ORDER BY SeqNo";

		// Create Fields
		boolean hasFields = false;
		Rows rows = new Rows();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, adProcessId);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				hasFields = true;
				createField(rs, rows);
			}
		}
		catch (SQLException e)
		{
			// metas: just logging the error won't be sufficient because can not see the problem
			// log.error(sql, e);
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		// both vectors the same?
		if (m_mFields.size() != m_mFields2.size()
				|| m_mFields.size() != m_wEditors.size()
				|| m_mFields2.size() != m_wEditors2.size())
			log.error("View & Model vector size is different");

		// clean up
		if (hasFields)
		{
			centerPanel.appendChild(rows);
			dynamicDisplay();
		}
		else
			dispose();
		return hasFields;
	}	// initDialog

	/**
	 * Create Field.
	 * - creates Fields and adds it to m_mFields list
	 * - creates Editor and adds it to m_vEditors list
	 * Handeles Ranges by adding additional mField/vEditor.
	 * <p>
	 * mFields are used for default value and mandatory checking; vEditors are used to retrieve the value (no data binding)
	 *
	 * @param rs result set
	 */
	private void createField(ResultSet rs, Rows rows)
	{
		// Create Field
		GridFieldVO voF = GridFieldVO.createParameter(Env.getCtx(), m_WindowNo, Env.TAB_None, rs);
		GridField mField = new GridField(voF);
		m_mFields.add(mField);                      // add to Fields

		Row row = new Row();

		// The Editor
		WEditor editor = WebEditorFactory.getEditor(mField, false);
		editor.addValueChangeListener(this);
		editor.dynamicDisplay();
		// MField => VEditor - New Field value to be updated to editor
		mField.addPropertyChangeListener(editor);
		// Set Default
		Object defaultObject = mField.getDefault();
		mField.setValue(defaultObject, true);
		// streach component to fill grid cell
		editor.fillHorizontal();
		// setup editor context menu
		WEditorPopupMenu popupMenu = editor.getPopupMenu();
		if (popupMenu != null)
		{
			popupMenu.addMenuListener((ContextMenuListener)editor);
			this.appendChild(popupMenu);
		}
		//
		m_wEditors.add(editor);                   // add to Editors

		Div div = new Div();
		div.setAlign("right");
		org.adempiere.webui.component.Label label = editor.getLabel();
		div.appendChild(label);
		if (label.getDecorator() != null)
			div.appendChild(label.getDecorator());
		row.appendChild(div);

		//
		if (voF.isRange)
		{
			Hbox box = new Hbox();
			box.appendChild(editor.getComponent());
			//
			GridFieldVO voF2 = GridFieldVO.createParameterTo(voF);
			GridField mField2 = new GridField(voF2);
			m_mFields2.add(mField2);
			// The Editor
			WEditor editor2 = WebEditorFactory.getEditor(mField2, false);
			// New Field value to be updated to editor
			mField2.addPropertyChangeListener(editor2);
			editor2.dynamicDisplay();
			editor2.fillHorizontal();
			// setup editor context menu
			popupMenu = editor2.getPopupMenu();
			if (popupMenu != null)
			{
				popupMenu.addMenuListener((ContextMenuListener)editor2);
				this.appendChild(popupMenu);
			}
			// Set Default
			Object defaultObject2 = mField2.getDefault();
			mField2.setValue(defaultObject2, true);
			//
			m_wEditors2.add(editor2);
			Label separator = new Label(" - ");
			m_separators.add(separator);
			box.appendChild(separator);
			box.appendChild(editor2.getComponent());
			row.appendChild(box);
		}
		else
		{
			row.appendChild(editor.getComponent());
			m_mFields2.add(null);
			m_wEditors2.add(null);
			m_separators.add(null);
		}
		rows.appendChild(row);
	}	// createField

	/**
	 * Validate Parameter values
	 * 
	 * @return true if parameters saved
	 */
	public boolean validateParameters()
	{
		log.info("");

		/**
		 * Mandatory fields
		 * see - MTable.getMandatory
		 */
		StringBuffer sb = new StringBuffer();
		int size = m_mFields.size();
		for (int i = 0; i < size; i++)
		{
			GridField field = m_mFields.get(i);
			if (field.isMandatory(true))        // check context
			{
				WEditor wEditor = m_wEditors.get(i);
				Object data = wEditor.getValue();
				if (data == null || data.toString().length() == 0)
				{
					field.setInserting(true);  // set editable (i.e. updateable) otherwise deadlock
					field.setError(true);
					if (sb.length() > 0)
						sb.append(", ");
					sb.append(field.getHeader());
				}
				else
					field.setError(false);
				// Check for Range
				WEditor wEditor2 = m_wEditors2.get(i);
				if (wEditor2 != null)
				{
					Object data2 = wEditor.getValue();
					GridField field2 = m_mFields2.get(i);
					if (data2 == null || data2.toString().length() == 0)
					{
						field.setInserting(true);  // set editable (i.e. updateable) otherwise deadlock
						field2.setError(true);
						if (sb.length() > 0)
							sb.append(", ");
						sb.append(field.getHeader());
					}
					else
						field2.setError(false);
				}   // range field
			}   // mandatory
		}   // field loop

		if (sb.length() != 0)
		{
			FDialog.error(m_WindowNo, this, "FillMandatory", sb.toString());
			return false;
		}

		return true;
	}	// validateParameters

	/**
	 * Save Parameter values
	 * 
	 * @return true if parameters saved
	 */
	@Override
	public boolean saveParameters(final int adPInstanceId)
	{
		log.info("");

		if (!validateParameters())
			return false;

		/**********************************************************************
		 * Save Now
		 */
		for (int i = 0; i < m_mFields.size(); i++)
		{
			// Get Values
			WEditor editor = m_wEditors.get(i);
			WEditor editor2 = m_wEditors2.get(i);
			Object result = editor.getValue();
			Object result2 = null;
			if (editor2 != null)
				result2 = editor2.getValue();

			// Create Parameter
			MPInstancePara para = new MPInstancePara(Env.getCtx(), adPInstanceId, i);
			GridField mField = m_mFields.get(i);
			para.setParameterName(mField.getColumnName());

			// Date
			if (result instanceof Timestamp || result2 instanceof Timestamp)
			{
				para.setP_Date((Timestamp)result);
				if (editor2 != null && result2 != null)
					para.setP_Date_To((Timestamp)result2);
			}
			// Integer
			else if (result instanceof Integer || result2 instanceof Integer)
			{
				if (result != null)
				{
					Integer ii = (Integer)result;
					para.setP_Number(ii.intValue());
				}
				if (editor2 != null && result2 != null)
				{
					Integer ii = (Integer)result2;
					para.setP_Number_To(ii.intValue());
				}
			}
			// BigDecimal
			else if (result instanceof BigDecimal || result2 instanceof BigDecimal)
			{
				para.setP_Number((BigDecimal)result);
				if (editor2 != null && result2 != null)
					para.setP_Number_To((BigDecimal)result2);
			}
			// Boolean
			else if (result instanceof Boolean)
			{
				Boolean bb = (Boolean)result;
				String value = bb.booleanValue() ? "Y" : "N";
				para.setP_String(value);
				// to does not make sense
			}
			// String
			else
			{
				if (result != null)
					para.setP_String(result.toString());
				if (editor2 != null && result2 != null)
					para.setP_String_To(result2.toString());
			}

			// Info
			para.setInfo(editor.getDisplay());
			if (editor2 != null)
				para.setInfo_To(editor2.getDisplay());
			//
			para.save();
			log.debug(para.toString());
		}	// for every parameter

		return true;
	}	// saveParameters

	/**
	 * Editor Listener
	 *
	 * @param evt ValueChangeEvent
	 */
	@Override
	public void valueChange(ValueChangeEvent evt)
	{
		if (evt.getSource() instanceof WEditor)
		{
			GridField changedField = ((WEditor)evt.getSource()).getGridField();
			if (changedField != null)
			{
				processDependencies(changedField);
				// future processCallout (changedField);
			}
		}
		processNewValue(evt.getNewValue(), evt.getPropertyName());
	}

	/**
	 * Evaluate Dependencies
	 * 
	 * @param changedField changed field
	 */
	private void processDependencies(GridField changedField)
	{
		String columnName = changedField.getColumnName();

		for (GridField field : m_mFields)
		{
			if (field == null || field == changedField)
				continue;
			verifyChangedField(field, columnName);
		}
		for (GridField field : m_mFields2)
		{
			if (field == null || field == changedField)
				continue;
			verifyChangedField(field, columnName);
		}
	}   // processDependencies

	private void verifyChangedField(GridField field, String columnName)
	{
		List<String> list = field.getDependentOn();
		if (list.contains(columnName))
		{
			if (field.getLookup() instanceof MLookup)
			{
				MLookup mLookup = (MLookup)field.getLookup();
				// if the lookup is dynamic (i.e. contains this columnName as variable)
				// metas: tsa: sw01_01029: check if validation rule contains our columnName:
				// if (mLookup.getValidation().indexOf("@"+columnName+"@") != -1)
				// if (CtxName.containsName(columnName, mLookup.getValidation()))
				if (mLookup.getParameters().contains(columnName))
				{
					log.debug(columnName + " changed - "
							+ field.getColumnName() + " set to null");
					// invalidate current selection
					field.setValue(null, true);
				}
			}
		}
	}

	private void processNewValue(Object value, String name)
	{
		if (value == null)
			value = new String("");

		if (value instanceof String)
			Env.setContext(Env.getCtx(), m_WindowNo, name, (String)value);
		else if (value instanceof Integer)
			Env.setContext(Env.getCtx(), m_WindowNo, name, ((Integer)value)
					.intValue());
		else if (value instanceof Boolean)
			Env.setContext(Env.getCtx(), m_WindowNo, name, ((Boolean)value)
					.booleanValue());
		else if (value instanceof Timestamp)
			Env.setContext(Env.getCtx(), m_WindowNo, name, (Timestamp)value);
		else
			Env.setContext(Env.getCtx(), m_WindowNo, name, value.toString());

		dynamicDisplay();
	}

	private void dynamicDisplay()
	{
		for (int i = 0; i < m_wEditors.size(); i++)
		{
			WEditor editor = m_wEditors.get(i);
			GridField mField = editor.getGridField();
			if (mField.isDisplayed(true))
			{
				if (!editor.isVisible())
				{
					editor.setVisible(true);
					if (mField.getVO().isRange)
					{
						m_separators.get(i).setVisible(true);
						m_wEditors2.get(i).setVisible(true);
					}
				}
				boolean rw = mField.isEditablePara(true); // r/w - check if field is Editable
				editor.setReadWrite(rw);
				editor.dynamicDisplay();
				if (mField.getVO().isRange)
				{
					m_wEditors2.get(i).setReadWrite(rw);
					m_wEditors2.get(i).dynamicDisplay();
				}
			}
			else if (editor.isVisible())
			{
				editor.setVisible(false);
				if (mField.getVO().isRange)
				{
					m_separators.get(i).setVisible(false);
					m_wEditors2.get(i).setVisible(false);
				}
			}
		}
	}

	/**
	 * Restore window context.
	 * 
	 * @author teo_sarca [ 1699826 ]
	 * @see org.compiere.model.GridField#restoreValue()
	 */
	protected void restoreContext()
	{
		for (GridField f : m_mFields)
		{
			if (f != null)
				f.restoreValue();
		}
		for (GridField f : m_mFields2)
		{
			if (f != null)
				f.restoreValue();
		}
	}
}	// ProcessParameterPanel

