package org.adempiere.plaf;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;

/**
 * VEditors related UI settings
 * 
 * @author tsa
 *
 */
public class VEditorUI
{
	static final String KEY_VEditor_Height = "VEditor.Height";
	static final int DEFAULT_VEditor_Height = 23;

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				//
				// Label (and also editor's labels) border
				// NOTE: we add a small space on top in order to have the label text aligned on same same base line as the text from the right text field.
				AdempiereLabelUI.KEY_Border, new BorderUIResource(BorderFactory.createEmptyBorder(3, 0, 0, 0))
				
				//
				// Editor button align (i.e. that small button of an editor field which is opening the Info Window)
				, VEditorDialogButtonAlign.DEFAULT_EditorUI, VEditorDialogButtonAlign.Right
				// , VEditorDialogButtonAlign.createUIKey("VNumber"), VEditorDialogButtonAlign.Hide
				// , VEditorDialogButtonAlign.createUIKey("VDate"), VEditorDialogButtonAlign.Hide
				// , VEditorDialogButtonAlign.createUIKey("VURL"), VEditorDialogButtonAlign.Right // i.e. the online button
				
				//
				// Editor height
				, KEY_VEditor_Height, DEFAULT_VEditor_Height

				//
				// VButton editor
				, "VButton.Action.textColor", AdempierePLAF.createActiveValueProxy("black", Color.BLACK)
				, "VButton.Posted.textColor", new ColorUIResource(Color.MAGENTA)
		};
	}
	
	public static final int getVEditorHeight()
	{
		return AdempierePLAF.getInt(KEY_VEditor_Height, DEFAULT_VEditor_Height);
	}
	
	public static final void installMinMaxSizes(final JComponent comp)
	{
		final int height = VEditorUI.getVEditorHeight();
		comp.setMinimumSize(new Dimension(30, height));
		comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
	}
}
