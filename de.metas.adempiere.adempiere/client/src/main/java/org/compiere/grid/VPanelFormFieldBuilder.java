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


import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.EventListener;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldLayoutConstraints;
import org.compiere.model.GridFieldVO;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 * Creates and adds a new form field to {@link VPanel}.
 * 
 * To start adding a new form field, please call {@link VPanel#newFormField()}.
 * 
 * When you are done, call {@link #add()} and your field will be added to {@link VPanel}.
 * 
 * @author tsa
 */
public final class VPanelFormFieldBuilder
{
	/** The {@link VPanel} where the new field will be added */
	private final VPanel vpanel;
	private int displayType;
	private String header;
	private String columnName;
	private int displayLength = 0;
	private boolean readOnly = false;

	public static final boolean DEFAULT_SameLine = true; // default=true, there is business logic which depends on this
	private boolean sameLine = DEFAULT_SameLine;

	private static final boolean DEFAULT_Mandatory = false; // default=false, there is business logic which depends on this
	private boolean mandatory = DEFAULT_Mandatory;
	private Boolean autocomplete;
	private int AD_Column_ID = 0;
	private EventListener editorListener;
	private boolean bindEditorToModel = false; // default=false, backward compatibility

	/* package */VPanelFormFieldBuilder(final VPanel vpanel)
	{
		super();

		Check.assumeNotNull(vpanel, "vpanel not null");
		this.vpanel = vpanel;
	}

	/**
	 * Creates and adds the new field.
	 * 
	 * @return newly created field. If you want to get the editor, please use {@link VPanel#getEditor(String)}.
	 */
	public GridField add()
	{
		final int windowNo = vpanel.getWindowNo();

		// Create a value object for the new Field.
		final GridFieldVO fieldVO = GridFieldVO.createStdField(Env.getCtx(),
				windowNo,
				0, // TabNo
				0, // AD_Window_ID
				0, // AD_Tab_ID
				false, // tabReadOnly
				false, // isCreated
				false // isTimestamp
				);
		final GridFieldLayoutConstraints layoutConstraints = fieldVO.getLayoutConstraints();
		
		fieldVO.setFormField(true);
		fieldVO.setHeader(getHeader());
		fieldVO.setIsFieldOnly(false);
		fieldVO.setIsEncryptedField(false);
		fieldVO.setColumnName(getColumnName());
		layoutConstraints.setSameLine(isSameLine());
		fieldVO.setMandatory(isMandatory());
		fieldVO.setAutocomplete(isAutocomplete());
		fieldVO.setDisplayType(getDisplayType());
		fieldVO.setAD_Reference_Value_ID(0); // otherwise buttons with Column_ID = 0 cause errors in VEditorFactory.getEditor(field, false);
		fieldVO.setAD_Column_ID(getAD_Column_ID());
		layoutConstraints.setDisplayLength(displayLength);
		fieldVO.setIsReadOnly(readOnly);
		if (fieldVO.getAD_Column_ID() > 0)
		{
			fieldVO.setLookupLoadFromColumn(true);
		}
		fieldVO.setIsDisplayed(true); // cg: task 05419

		// Create the new field based on the value object.
		final GridField field = new GridField(fieldVO);
		field.setGridTab(null); // There's no GridTab in a form.
		field.setDisplayed(true);
		// field.loadLookup(); // Does nothing if this field is not a lookup. // metas: already called from GridField ctor

		//
		// Create an editor for the field.
		final VEditor editor = Services.get(ISwingEditorFactory.class).getEditor(field, false); // tableEditor=false

		//
		// Add Field -> Editor listener
		field.addPropertyChangeListener(editor); // Field -> Editor

		// Add Editor -> Field automatic binding
		if (bindEditorToModel)
		{
			editor.addVetoableChangeListener(new VetoableChangeListener()
			{

				@Override
				public void vetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException
				{
					if (!Check.equals(field.getColumnName(), evt.getPropertyName()))
					{
						return;
					}
					final Object value = evt.getNewValue();
					if (value == null)
					{
						// NOTE: skip null values because of how editor works.
						// Maybe we will fix that in future, but atm there are to many events triggered with a null new value.
						return;
					}
					field.setValue(value, true);
				}
			});
		}

		//
		// Add provided editor listener (if any)
		final EventListener editorListener = getEditorListener();
		boolean editorListenerInstalled = false;
		if (editorListener instanceof VetoableChangeListener)
		{
			// Editor:VetoableChange -> Form
			editor.addVetoableChangeListener((VetoableChangeListener)editorListener);
			editorListenerInstalled = true;
		}
		if (editorListener instanceof ActionListener && getDisplayType() == DisplayType.Button)
		{
			// Button:Action -> Form
			// Only needed for Buttons because other editors are fully covered by VetoableChange support.
			editor.addActionListener((ActionListener)editorListener);
			editorListenerInstalled = true;
		}
		// Notify the developer if the editor listener could not be installed
		if (editorListener != null && !editorListenerInstalled)
		{
			new AdempiereException("Unsupported editor listener: " + editorListener + " from " + this)
					.throwOrLogWarning(Services.get(IDeveloperModeBL.class).isEnabled(), VPanel.log);
		}

		// Add the new field
		vpanel.addField(editor);

		return field;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	private int getDisplayType()
	{
		Check.assume(displayType > 0, "displayType > 0");
		return displayType;
	}

	public VPanelFormFieldBuilder setDisplayType(int displayType)
	{
		this.displayType = displayType;
		return this;
	}

	private String getHeader()
	{
		// null allowed
		return header;
	}

	/**
	 * @param header String which is Displayed as Label.
	 */
	public VPanelFormFieldBuilder setHeader(String header)
	{
		this.header = header;
		return this;
	}

	private String getColumnName()
	{
		Check.assumeNotEmpty(columnName, "columnName not empty");
		return columnName;
	}

	/**
	 * @param columnName Will be the name of the GridField.
	 */
	public VPanelFormFieldBuilder setColumnName(String columnName)
	{
		this.columnName = columnName;
		return this;
	}

	private boolean isSameLine()
	{
		return sameLine;
	}

	/**
	 * Default: {@link #DEFAULT_SameLine}
	 * 
	 * @param sameline If true, the new Field will be added in the same line.
	 */
	public VPanelFormFieldBuilder setSameLine(boolean sameLine)
	{
		this.sameLine = sameLine;
		return this;
	}

	private boolean isMandatory()
	{
		return mandatory;
	}

	/**
	 * Default: {@link #DEFAULT_Mandatory}
	 * 
	 * @param mandatory true if this field shall be marked as mandatory
	 */
	public VPanelFormFieldBuilder setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
		return this;
	}

	private boolean isAutocomplete()
	{
		if (autocomplete != null)
		{
			return autocomplete;
		}

		// if Search, always auto-complete
		if (DisplayType.Search == displayType)
		{
			return true;
		}

		return false;
	}

	public VPanelFormFieldBuilder setAutocomplete(boolean autocomplete)
	{
		this.autocomplete = autocomplete;
		return this;
	}

	private int getAD_Column_ID()
	{
		// not set is allowed
		return AD_Column_ID;
	}

	/**
	 * @param AD_Column_ID Column for lookups.
	 */
	public VPanelFormFieldBuilder setAD_Column_ID(int AD_Column_ID)
	{
		this.AD_Column_ID = AD_Column_ID;
		return this;
	}

	public VPanelFormFieldBuilder setAD_Column_ID(final String tableName, final String columnName)
	{
		return setAD_Column_ID(Services.get(IADTableDAO.class).retrieveColumn(tableName, columnName).getAD_Column_ID());
	}

	private EventListener getEditorListener()
	{
		// null allowed
		return editorListener;
	}

	/**
	 * @param listener VetoableChangeListener that gets called if the field is changed.
	 */
	public VPanelFormFieldBuilder setEditorListener(EventListener listener)
	{
		this.editorListener = listener;
		return this;
	}

	public VPanelFormFieldBuilder setBindEditorToModel(boolean bindEditorToModel)
	{
		this.bindEditorToModel = bindEditorToModel;
		return this;
	}

	public VPanelFormFieldBuilder setDisplayLength(int displayLength)
	{
		this.displayLength = displayLength;
		return this;
	}

	public VPanelFormFieldBuilder setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly;
		return this;
	}
}
