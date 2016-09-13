package org.compiere.apps.search;

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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.grid.ed.VCheckBox;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.VString;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.Lookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CEditor;
import org.compiere.util.DisplayType;

/**
 * @author tsa
 * 
 */
public class InfoQueryCriteriaGeneral extends AbstractInfoQueryCriteriaGeneral
{

	protected final VetoableChangeListener fieldChangedListener = new VetoableChangeListener()
	{

		@Override
		public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
		{
			// We need to do compare the values again because VetoableChangeSupport/PropertyChangeSupport
			// is not covering the case when both values are null.
			// Equality comparison is sufficient because the rest is covered by VetoableChangeSupport.
			if (evt.getOldValue() == evt.getNewValue())
				return;

			onFieldChanged();
		}
	};

	@Override
	protected void initComponents(String searchText)
	{
		final IInfoSimple parent = getParent();
		final I_AD_InfoColumn infoColumn = getAD_InfoColumn();

		final Properties ctx = parent.getCtx();
		final int windowNo = parent.getWindowNo();
		final int displayType = infoColumn.getAD_Reference_ID();
		final String columnName = infoColumn.getAD_Element().getColumnName();

		if (DisplayType.YesNo == displayType)
		{
			VCheckBox cb = new VCheckBox();
			cb.setText(label);
			cb.setSelected(true);
			cb.setFocusable(false);
			cb.setRequestFocusEnabled(false);
			cb.addVetoableChangeListener(fieldChangedListener);
			editor = cb;
			label = null;
		}
		else if (DisplayType.List == displayType
				|| DisplayType.Table == displayType
				|| DisplayType.TableDir == displayType
				|| DisplayType.Search == displayType)
		{

			final MLookup lookup;
			try
			{
				lookup = MLookupFactory.get(ctx,
						windowNo,
						0, // Column_ID,
						infoColumn.getAD_Reference_ID(),
						columnName,
						infoColumn.getAD_Reference_Value_ID(),
						false, // IsParent
						infoColumn.getAD_Val_Rule_ID()
						);
			}
			catch (Exception e)
			{
				throw new AdempiereException(e);
			}
			VLookup vl = new VLookup(columnName, false, false, true, lookup);
			vl.setName(columnName);
			// default value
			final Object defaultValue = parent.getContextVariable(columnName);
			if (defaultValue != null)
				vl.setValue(defaultValue);
			vl.addVetoableChangeListener(fieldChangedListener);
			editor = vl;
			//
			if (infoColumn.isRange())
			{
				VLookup vl2 = new VLookup(columnName, false, false, true, lookup);
				vl2.setName(columnName + "_2");
				vl2.addVetoableChangeListener(fieldChangedListener);
				editor2 = vl2;
			}
		}
		else if (DisplayType.String == displayType)
		{
			VString field = new VString();
			field.setBackground(AdempierePLAF.getInfoBackground());
			field.addVetoableChangeListener(fieldChangedListener);
			editor = field;
			//
			if (infoColumn.isRange())
			{
				VString field2 = new VString();
				field2.setBackground(AdempierePLAF.getInfoBackground());
				field2.addVetoableChangeListener(fieldChangedListener);
				editor2 = field2;
			}
		}
		// Number
		else if (DisplayType.isNumeric(displayType))
		{
			VNumber vn = new VNumber(columnName, false, false, true, infoColumn.getAD_Reference_ID(), infoColumn.getName());
			vn.setName(columnName);
			vn.addVetoableChangeListener(fieldChangedListener);
			editor = vn;
			//
			if (infoColumn.isRange())
			{
				VNumber vn2 = new VNumber(columnName, false, false, true, infoColumn.getAD_Reference_ID(), infoColumn.getName());
				vn2.setName(columnName + "_2");
				vn2.addVetoableChangeListener(fieldChangedListener);
				editor2 = vn2;
			}
		}
		// Date
		else if (DisplayType.isDate(displayType))
		{
			VDate vd = new VDate(columnName, false, false, true, displayType, infoColumn.getName());
			vd.setName(columnName);
			vd.addVetoableChangeListener(fieldChangedListener);
			editor = vd;
			//
			if (infoColumn.isRange())
			{
				VDate vd2 = new VDate(columnName, false, false, true, displayType, infoColumn.getName());
				vd2.setName(columnName + "_2");
				vd2.addVetoableChangeListener(fieldChangedListener);
				editor2 = vd2;
			}
		}
		else
		{
			throw new AdempiereException("Not supported reference " + infoColumn.getAD_Reference_ID());
		}
	}

	@Override
	protected CEditor createCheckboxEditor(final String columnName, final String label)
	{
		final VCheckBox cb = new VCheckBox();
		cb.setText(label);
		cb.setSelected(true);
		cb.setFocusable(false);
		cb.setRequestFocusEnabled(false);
		cb.addVetoableChangeListener(fieldChangedListener);
		return cb;
	}

	@Override
	protected CEditor createLookupEditor(final String columnName, final Lookup lookup, final Object defaultValue)
	{
		final VLookup vl = new VLookup(columnName, false, false, true, lookup);
		vl.setName(columnName);
		if (defaultValue != null)
		{
			vl.setValue(defaultValue);
		}
		vl.addVetoableChangeListener(fieldChangedListener);
		return vl;
	}

	@Override
	protected CEditor createStringEditor(final String columnName, final String defaultValue)
	{
		final VString field = new VString();
		field.setBackground(AdempierePLAF.getInfoBackground());

		if (defaultValue != null)
		{
			field.setText(defaultValue);
		}

		field.addVetoableChangeListener(fieldChangedListener);
		return field;
	}

	@Override
	protected CEditor createNumberEditor(final String columnName, final String title, final int displayType)
	{
		final VNumber vn = new VNumber(columnName, false, false, true, displayType, title);
		vn.setName(columnName);
		vn.addVetoableChangeListener(fieldChangedListener);
		return vn;
	}

	@Override
	protected CEditor createDateEditor(final String columnName, final String title, final int displayType)
	{
		final VDate vd = new VDate(columnName, false, false, true, displayType, title);
		vd.setName(columnName);
		vd.addVetoableChangeListener(fieldChangedListener);
		return vd;
	}
}
