package org.compiere.grid;

/*
 * #%L
 * ADempiere ERP - Desktop Client
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


import javax.swing.JComponent;

import org.compiere.grid.ed.VEditor;

public class VPaymentPanelUIHelper
{
	private static final String ComponentProperty_ReadOnly = VPaymentPanelUIHelper.class.getName() + ".isComponentReadOnly";

	private final IVPaymentPanel panel;
	private final JComponent[] fields;

	public VPaymentPanelUIHelper(IVPaymentPanel panel, JComponent[] fields)
	{
		this.panel = panel;
		this.fields = fields;
	}

	/**
	 * Update Read-write access to all fields
	 */
	public void updateFieldRO()
	{
		for (JComponent field : fields)
		{
			updateFieldRO(field);
		}
	}

	/**
	 * Update Readwrite access to given field
	 * 
	 * @param field
	 */
	public void updateFieldRO(JComponent field)
	{
		boolean enabled = panel.isEnabled() && !panel.isReadOnly();
		if (enabled)
		{
			enabled = !isFieldReadOnly(field);
		}

		field.setEnabled(enabled);

		if (field instanceof VEditor)
		{
			VEditor editor = (VEditor)field;
			editor.setReadWrite(enabled);
		}
	}

	public boolean isFieldReadOnly(JComponent field)
	{
		Boolean ro = (Boolean)field.getClientProperty(ComponentProperty_ReadOnly);
		return ro == null ? false : ro.booleanValue();
	}

	public void setFieldReadOnly(JComponent field, boolean readOnly)
	{
		field.putClientProperty(ComponentProperty_ReadOnly, readOnly);
		updateFieldRO(field);
	}

}
