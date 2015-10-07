package org.adempiere.plaf;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
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
				// Editor button align (i.e. that small button of an editor field which is opening the Info Window)
				VEditorDialogButtonAlign.DEFAULT_EditorUI, VEditorDialogButtonAlign.Right
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
