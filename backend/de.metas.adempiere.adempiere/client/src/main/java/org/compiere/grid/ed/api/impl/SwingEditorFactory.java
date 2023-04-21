package org.compiere.grid.ed.api.impl;

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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ADialog;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VEditorFactory;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Column;
import org.compiere.model.Lookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CLabel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.util.Services;

/**
 * @author al
 */
@SuppressWarnings("deprecation")
public final class SwingEditorFactory implements ISwingEditorFactory
{
	@Override
	public final VEditor getEditor(final GridField mField, final boolean tableEditor)
	{
		final VEditor editor = VEditorFactory.getEditor(mField, tableEditor);
		return editor;
	}

	@Override
	public final VEditor getEditor(final GridTab mTab, final GridField mField, final boolean tableEditor)
	{
		final VEditor editor = VEditorFactory.getEditor(mTab, mField, tableEditor);
		return editor;
	}

	@Override
	public final JComponent getEditorComponent(final VEditor editor)
	{
		if (editor == null)
		{
			return null;
		}
		else if (editor instanceof JComponent)
		{
			return (JComponent)editor;
		}
		else
		{
			throw new AdempiereException("Cannot extract the swing component of " + editor + " (" + editor.getClass() + ")");
		}
	}

	@Override
	public final CLabel getLabel(final GridField mField)
	{
		final CLabel label = VEditorFactory.getLabel(mField);
		return label;
	}
	
	@Override
	public final void bindFieldAndLabel(final JPanel panel, final JComponent field, final JLabel label, final String columnName)
	{
		bindFieldAndLabel(panel,
				field, DEFAULT_FIELD_CONSTRAINTS,
				label, DEFAULT_LABEL_CONSTRAINTS,
				columnName);
	}

	@Override
	public final void bindFieldAndLabel(final JPanel panel,
			final JComponent field, final String fieldConstraints,
			final JLabel label, final String labelConstraints,
			final String columnName)
	{
		final Properties ctx = Env.getCtx();
		final String labelText = Services.get(IMsgBL.class).translate(ctx, columnName);
		label.setText(labelText);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(label, labelConstraints);

		panel.add(field, fieldConstraints);
	}

	@Override
	public final VLookup getVLookup(final int windowNo,
			final int tabNo,
			final int displayType,
			final String tableName,
			final String columnName,
			final boolean mandatory,
			final boolean autocomplete)
	{
		final Properties ctx = Env.getCtx();

		final I_AD_Column column = Services.get(IADTableDAO.class).retrieveColumn(tableName, columnName);
		final Lookup lookup = MLookupFactory.newInstance().get(ctx, windowNo, tabNo, column.getAD_Column_ID(), displayType);
		lookup.setMandatory(mandatory);

		final VLookup lookupField = new VLookup(columnName,
				mandatory, // mandatory
				false, // isReadOnly
				true, // isUpdateable
				lookup);

		if (autocomplete)
		{
			lookupField.enableLookupAutocomplete();
		}

		//
		// For firing validation rules
		lookupField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				final Object value = lookupField.getValue();
				Env.setContext(ctx, windowNo, columnName, value == null ? "" : value.toString());
			}
		});

		return lookupField;
	}

	@Override
	public final VNumber getVNumber(final String columnName, final boolean mandatory)
	{
		final Properties ctx = Env.getCtx();
		final String title = Services.get(IMsgBL.class).translate(ctx, columnName);
		return new VNumber(columnName,
				mandatory,
				false, // isReadOnly
				true, // isUpdateable
				DisplayType.Number, // displayType
				title);
	}

	@Override
	public final void disposeDialogForColumnData(final int windowNo, final JDialog dialog, final List<String> columnNames)
	{
		if (columnNames == null || columnNames.isEmpty())
		{
			return;
		}

		final StringBuilder missingColumnsBuilder = new StringBuilder();

		final Iterator<String> columnNamesIt = columnNames.iterator();
		while (columnNamesIt.hasNext())
		{
			final String columnName = columnNamesIt.next();
			missingColumnsBuilder.append("@").append(columnName).append("@");
			if (columnNamesIt.hasNext())
			{
				missingColumnsBuilder.append(", ");
			}
		}

		final Exception ex = new AdempiereException("@NotFound@ " + missingColumnsBuilder.toString());
		ADialog.error(windowNo, dialog.getContentPane(), ex.getLocalizedMessage());

		dialog.dispose();
	}
}
