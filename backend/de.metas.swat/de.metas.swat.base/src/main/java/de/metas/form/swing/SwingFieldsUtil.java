/**
 * 
 */
package de.metas.form.swing;

/*
 * #%L
 * de.metas.swat.base
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


import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CEditor;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

/**
 * @author teo_sarca
 *
 */
public final class SwingFieldsUtil
{
	private static Logger log = LogManager.getLogger(SwingFieldsUtil.class);
	
	public static void setSelectAllOnFocus(final JComponent c)
	{
		if (c instanceof JTextComponent)
		{
			setSelectAllOnFocus((JTextComponent)c);
		}
		else
		{
			for (Component cc : c.getComponents())
			{
				if (cc instanceof JTextComponent)
				{
					setSelectAllOnFocus((JTextComponent)cc);
					break;
				}
			}
		}
	}
	public static void setSelectAllOnFocus(final JTextComponent c)
	{
		c.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				c.selectAll();
			}
		});
	}
	
	public static boolean isEmpty(Object editor)
	{
		if (editor == null)
		{
			return false;
		}
		if (editor instanceof CComboBox)
		{
			final CComboBox c = (CComboBox)editor;
			return c.isSelectionNone();
		}
		else if (editor instanceof VDate)
		{
			VDate c = (VDate)editor;
			return c.getTimestamp() == null;
		}
		else if (editor instanceof VLookup)
		{
			VLookup c = (VLookup)editor;
			return c.getValue() == null;
		}
		else if (editor instanceof JTextComponent)
		{
			JTextComponent c = (JTextComponent)editor;
			return Check.isEmpty(c.getText(), true);
		}
		//
		log.warn("Component type not supported - "+editor.getClass());
		return false;
	}
	
	public static boolean isEditable(Component c)
	{
		if (c == null)
			return false;
		if (!c.isEnabled())
			return false;
		if (c instanceof CEditor)
			return ((CEditor)c).isReadWrite();
		if (c instanceof JTextComponent)
			return ((JTextComponent)c).isEditable();
		//
		log.warn("Unknown component type - "+c.getClass());
		return false;
	}

	public static void focusNextNotEmpty(Component component, Collection<Component> editors)
	{
		boolean found = false;
		Component last = null;
		for (Component c : editors)
		{
			last = c;
			if (found)
			{
				if (isEditable(c) && isEmpty(c))
				{
					c.requestFocus();
					return;
				}
			}
			else if (c == component)
			{
				found = true;
			}
		}
		//
		if (!found)
			log.warn("Component not found - "+component);
		if (found && last != null)
			last.requestFocus();
	}
}
