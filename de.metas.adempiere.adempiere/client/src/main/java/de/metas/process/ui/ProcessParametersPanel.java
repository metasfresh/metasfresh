/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package de.metas.process.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JLabel;

import org.adempiere.util.Services;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VImage;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ui.ProcessParameterPanelModel;

/**
 * Process Parameter Panel, based on existing ProcessParameter dialog. - Embedded in ProcessDialog - checks, if parameters exist and inquires and saves them
 *
 * @author Low Heng Sin
 * @author Juan David Arboleda (arboleda), GlobalQSS, [ 1795398 ] Process Parameter: add display and readonly logic
 * @author Teo Sarca, www.arhipac.ro
 *         <li>BF [ 2548216 ] Process Param Panel is not showing any parameter if error
 * @version 2006-12-01
 */
public class ProcessParametersPanel extends CPanel // implements IProcessParameter
{
	private static final long serialVersionUID = -4802635610434891695L;

	private static final Logger log = LogManager.getLogger(ProcessParametersPanel.class);

	// Services
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);
	// private final transient IClientUI clientUI = Services.get(IClientUI.class);

	private final ProcessParameterPanelModel model;
	private final List<JLabel> fieldLabels = new ArrayList<JLabel>();
	private final List<VEditor> fieldEditors = new ArrayList<VEditor>();
	private final List<VEditor> fieldEditorsTo = new ArrayList<VEditor>();		// for ranges
	private final List<VEditor> fieldEditorsAll = new ArrayList<VEditor>();
	private final List<JLabel> fieldSeparators = new ArrayList<JLabel>();
	//
	// UI
	private final CPanel centerPanel = new CPanel();
	private final GridBagConstraints gbc = new GridBagConstraints();
	private final Insets nullInset = new Insets(0, 0, 0, 0);
	private final Insets labelInset = new Insets(2, 12, 2, 0);		// top,left,bottom,right
	private final Insets fieldInset = new Insets(2, 5, 2, 0);		// top,left,bottom,right
	private final Insets fieldInsetRight = new Insets(2, 5, 2, 12);		// top,left,bottom,right
	private int m_line = 0;

	/**
	 * Data Binding: Editor(view) to GridField(model)
	 */
	private final VetoableChangeListener viewToModelBinding = new VetoableChangeListener()
	{

		@Override
		public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
		{
			GridField changedField = null;
			if (evt.getSource() instanceof VEditor)
			{
				final VEditor editor = (VEditor)evt.getSource();
				changedField = editor.getField();
			}

			final String columnName = evt.getPropertyName();
			final Object valueNew = evt.getNewValue();
			model.notifyValueChanged(columnName, valueNew, changedField);

			dynamicDisplay();
		}
	};

	/**
	 * @param pi process info, used ONLY to get AD_Process_ID, WindowNo and TabNo; no reference is stored to it
	 */
	public ProcessParametersPanel(final ProcessInfo pi)
	{
		super();
		model = new ProcessParameterPanelModel(Env.getCtx(), pi);
		model.setDisplayValueProvider((gridField) -> {
			final VEditor editor = getEditor(gridField);
			if (editor == null)
			{
				return null;
			}
			return editor.getDisplay();
		});

		try
		{
			initEditors();
			initLayout();

			if (!hasFields())
			{
				dispose();
				return;
			}

			model.setDefaultValues();

			dynamicDisplay();
		}
		catch (final Exception ex)
		{
			log.error("Failed initializing process parameters panel", ex);
			dispose();
		}
	}

	/**
	 *
	 * @return true if panel has any process parameters
	 */
	public boolean hasFields()
	{
		return !fieldEditors.isEmpty();
	}

	public void dispose()
	{
		fieldEditors.clear();
		fieldEditorsTo.clear();
		fieldEditorsAll.clear();

		model.dispose();

		fieldSeparators.clear();
		this.removeAll();
	}

	private void initEditors()
	{
		final int fieldsCount = model.getFieldCount();
		for (int index = 0; index < fieldsCount; index++)
		{
			final GridField field = model.getField(index);
			final GridField fieldTo = model.getFieldTo(index);
			createEditors(field, fieldTo);
		}
	}

	private void initLayout()
	{
		final BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);

		final GridBagLayout centerLayout = new GridBagLayout();
		centerPanel.setLayout(centerLayout);
		this.add(centerPanel, BorderLayout.CENTER);

		// Prepare panel
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridy = m_line++;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.insets = nullInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		centerPanel.add(Box.createVerticalStrut(10), gbc);    	// top gap 10+2=12

		final int fieldCount = model.getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			final GridField gridField = model.getField(fieldIndex);
			final VEditor editor = fieldEditors.get(fieldIndex);
			final VEditor editorTo = fieldEditorsTo.get(fieldIndex);

			addLine(gridField, editor, editorTo);
		}

		// clean up
		if (model.getFieldCount() > 0)
		{
			gbc.gridy = m_line++;
			centerPanel.add(Box.createVerticalStrut(10), gbc);    	// bottom gap
			gbc.gridx = 3;
			centerPanel.add(Box.createHorizontalStrut(12), gbc);   	// right gap
		}

	}

	private void createEditors(final GridField field, final GridField fieldTo)
	{
		// The Editor
		final VEditor editor = createEditorForField(field);
		editor.addVetoableChangeListener(viewToModelBinding);
		// MField => VEditor - New Field value to be updated to editor
		field.addPropertyChangeListener(editor);
		fieldEditors.add(editor);                   // add to Editors
		fieldEditorsAll.add(editor);

		//
		final VEditor editorTo;
		if (fieldTo != null)
		{
			editorTo = createEditorForField(fieldTo);
			editorTo.addVetoableChangeListener(viewToModelBinding);
		}
		else
		{
			editorTo = null;
		}
		fieldEditorsTo.add(editorTo);

		if (editorTo != null)
		{
			fieldEditorsAll.add(editorTo);
		}
	}

	/** Creates the editor for given field, but it does NOT add it to any of our internal maps */
	private VEditor createEditorForField(final GridField gridField)
	{
		final boolean tableEditor = false;
		final VEditor editor = swingEditorFactory.getEditor(gridField, tableEditor);
		if (editor instanceof VImage)
		{
			final VImage imageEditor = (VImage)editor;
			// Enforce a maximum size for image preview, because else it looks very crappy in process parameters panel, mainly when using big images
			imageEditor.setPreviewMaxSize(new Dimension(300, 100));
		}

		return editor;
	}

	private void addLine(final GridField gridField, final VEditor editor, final VEditor editorTo)
	{
		// Defaults for all components of this line
		gbc.weightx = 0;
		gbc.weighty = 0;

		//
		// Label Preparation
		gbc.gridy = m_line++;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;	// required for right justified
		gbc.gridx = 0;
		gbc.weightx = 0;
		final JLabel label = swingEditorFactory.getLabel(gridField);
		fieldLabels.add(label);
		if (label == null)
		{
			gbc.insets = nullInset;
			centerPanel.add(Box.createHorizontalStrut(12), gbc);   	// left gap
		}
		else
		{
			gbc.insets = labelInset;
			centerPanel.add(label, gbc);
		}

		//
		// Field Preparation
		gbc.insets = fieldInset;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.weightx = 1;

		final Component editorComp = getComponent(editor);
		centerPanel.add(editorComp, gbc);

		//
		// Value To Editor
		final JLabel separator;
		if (editorTo != null)
		{
			separator = new JLabel(ProcessParameterPanelModel.FIELDSEPARATOR_TEXT);

			// To Label
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			centerPanel.add(separator, gbc);
			fieldSeparators.add(separator);
			// To Field
			gbc.gridx = 3;
			gbc.insets = fieldInsetRight;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.WEST;
			//
			centerPanel.add(getComponent(editorTo), gbc);
		}
		else
		{
			separator = null;
		}
		fieldSeparators.add(separator);
	}

	private Component getComponent(final VEditor editor)
	{
		return swingEditorFactory.getEditorComponent(editor);
	}

	private VEditor getEditor(final GridField gridField)
	{
		if (gridField == null)
		{
			return null;
		}

		for (final VEditor editor : fieldEditorsAll)
		{
			if (editor.getField() == gridField)
			{
				return editor;
			}
		}

		return null;
	}

	/**
	 * Update fields UI properties
	 *
	 * @see #dynamicDisplay(int)
	 */
	private void dynamicDisplay()
	{
		final int fieldCount = model.getFieldCount();
		for (int fieldIndex = 0; fieldIndex < fieldCount; fieldIndex++)
		{
			dynamicDisplay(fieldIndex);
		}
	}

	/**
	 * Update field's UI properties: Displayed, Read-Write
	 *
	 * @param fieldIndex
	 */
	private void dynamicDisplay(final int fieldIndex)
	{
		final Properties ctx = model.getCtx();

		final GridField gridField = model.getField(fieldIndex); // allways not null
		final VEditor editor = fieldEditors.get(fieldIndex);
		final VEditor editorTo = fieldEditorsTo.get(fieldIndex);
		final JLabel separator = fieldSeparators.get(fieldIndex);
		final JLabel label = fieldLabels.get(fieldIndex);

		// Visibility
		final boolean editorVisible = (editor != null) && gridField.isDisplayed(ctx);
		final boolean editorToVisible = (editorTo != null) && editorVisible;
		final boolean labelVisible = editorVisible || editorToVisible;
		final boolean separatorVisible = editorVisible || editorToVisible;

		// Read-Only/Read-Write
		final boolean editorRW = editorVisible && gridField.isEditablePara(ctx);
		final boolean editorToRW = editorToVisible && editorRW;

		// Update fields
		if (label != null)
		{
			label.setVisible(labelVisible);
		}
		if (editor != null)
		{
			editor.setVisible(editorVisible);
			editor.setReadWrite(editorRW);
		}
		if (separator != null)
		{
			separator.setVisible(separatorVisible);
		}
		if (editorTo != null)
		{
			editorTo.setVisible(editorToVisible);
			editorTo.setReadWrite(editorToRW);
		}
	}

	public List<ProcessInfoParameter> createParameters()
	{
		//
		// Make sure all editor values are pushed back to model (GridFields)
		for (final VEditor editor : fieldEditorsAll)
		{
			final GridField gridField = editor.getField();
			if (gridField == null)
			{
				// guard agaist null, shall not happen
				continue;
			}

			final Object value = editor.getValue();
			model.setFieldValue(gridField, value);
		}

		//
		// Ask the model to create the parameters
		return model.createProcessInfoParameters();
	}

	/**
	 * #782 Request focus on the first process parameter (if possible)
	 */
	public void focusFirstParameter()
	{
		if (fieldEditors.isEmpty())
		{
			// there are no parameters in this process. Nothing to focus
			return;
		}

		for (int i = 0; i <= fieldEditors.size(); i++)
		{
			final VEditor editor = fieldEditors.get(i);
			final boolean focusGained = getComponent(editor).requestFocusInWindow();
			
			if(focusGained)
			{
				return;
			}
		}

	}
}	// ProcessParameterPanel
