package org.compiere.grid.ed.api;

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


import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.swing.CLabel;

import de.metas.util.ISingletonService;

/**
 * Swing factory used for easier creation of adempiere-specific fields in custom layouts. This interface is/was meant to replace <code>VEditorFactory</code>.
 *
 * @author al
 */
public interface ISwingEditorFactory extends ISingletonService
{
	String DEFAULT_FIELD_CONSTRAINTS = "growx, pushx, width 170, wrap";
	String DEFAULT_LABEL_CONSTRAINTS = "growx, pushx";

	/**
	 * Create Editor for MField. The Name is set to the column name for dynamic display management
	 *
	 * @param mField MField
	 * @param tableEditor true if table editor
	 * @return grid editor
	 */
	VEditor getEditor(GridField mField, boolean tableEditor);

	/**
	 * Create Editor for MField. The Name is set to the column name for dynamic display management
	 *
	 * @param mTab MTab
	 * @param mField MField
	 * @param tableEditor true if table editor
	 * @return grid editor
	 */
	VEditor getEditor(GridTab mTab, GridField mField, boolean tableEditor);

	/**
	 * Gets the swing component of given editor.
	 * 
	 * @param editor
	 * @return swing component of given editor or null if the editor was null
	 */
	JComponent getEditorComponent(VEditor editor);

	/**
	 * Create Label for MField. (null for YesNo/Button) The Name is set to the column name for dynamic display management
	 *
	 * @param mField MField
	 * @return Label
	 */
	CLabel getLabel(GridField mField);

	/**
	 * Note: this method will apply DEFAULT field & label constraints (which will place field+label one beneath the other in the panel).<br>
	 * To configure this, see {@link #bindFieldAndLabel(JPanel, JComponent, String, JLabel, String, String)}
	 *
	 * @param panel
	 * @param field
	 * @param label
	 * @param columnName
	 */
	void bindFieldAndLabel(JPanel panel, JComponent field, JLabel label, String columnName);

	/**
	 * Bind field and label to given panel and apply layout constraints
	 *
	 * @param panel
	 * @param field
	 * @param fieldConstraints
	 * @param label
	 * @param labelConstraints
	 * @param columnName
	 */
	void bindFieldAndLabel(JPanel panel, JComponent field, String fieldConstraints, JLabel label, String labelConstraints, String columnName);

	/**
	 * Create new instance of {@link VLookup} using the translated <code>columnName</code> as title
	 *
	 * @param windowNo
	 * @param tabNo (can be 0 if the window only has one tab)
	 * @param displayType
	 * @param tableName
	 * @param columnName
	 * @param mandatory
	 * @param autocomplete
	 * @return lookup field which was created
	 */
	VLookup getVLookup(int windowNo, int tabNo, int displayType, String tableName, String columnName, boolean mandatory, boolean autocomplete);

	/**
	 * Create new instance of {@link VNumber} using the translated <code>columnName</code> as title
	 *
	 * @param columnName
	 * @param mandatory
	 * @return number field which was created
	 */
	VNumber getVNumber(String columnName, boolean mandatory);

	/**
	 * Display an error message with fields which cannot be filled with data, then dispose of the given <code>dialog</code>. If columnNames is empty, this method shall do nothing.
	 *
	 * @param columnNames
	 */
	void disposeDialogForColumnData(int windowNo, JDialog dialog, List<String> columnNames);
}
