package org.adempiere.webui.editor;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IValuePreferenceBL;
import org.adempiere.util.Services;
import org.adempiere.webui.ValuePreference;
import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.compiere.model.PO;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.api.Menupopup;
import org.zkoss.zul.api.Textbox;
import org.zkoss.zul.impl.api.XulElement;

public final class FieldContextMenuHelper
{
	private static final FieldContextMenuHelper instance = new FieldContextMenuHelper();

	public static FieldContextMenuHelper get()
	{
		return instance;
	}

	private FieldContextMenuHelper()
	{

	}

	public Menupopup enableContextMenu(final String namespace, final String propertyName, final String propertyDisplayName, final Component field)
	{
		return enableContextMenu(namespace, ValuePreference.WINDOW_CustomForm, propertyName, propertyDisplayName, field);
	}
	
	public Menupopup enableContextMenu(final String namespace, final int adWindowId, final String propertyName, final String propertyDisplayName, final Component field)
	{
		if (!(field instanceof XulElement))
		{
			return null;
		}

		final WEditorPopupMenu menu = new WEditorPopupMenu(false, false, true);
		((XulElement)field).setContext(menu.getId());
		// this.appendChild(menu); // needs to be added to a container in order to work

		menu.addMenuListener(new ContextMenuListener()
		{
			@Override
			public void onMenu(ContextMenuEvent evt)
			{
				if (WEditorPopupMenu.ACTION_PREFERENCE.equals(evt.getContextEvent()))
				{
					showValuePreference(namespace, adWindowId, propertyName, propertyDisplayName, field);
				}
			}
		});

		//
		// Add to parent (if possible)
		if (field.getParent() != null)
		{
			field.getParent().appendChild(menu);
		}

		return menu;
	}

	private void showValuePreference(final String namespace, final int adWindowId, final String propertyName, final String propertyDisplayName, final Component field)
	{
		final String attributeName = namespace == null ? propertyName : namespace + "#" + propertyName;
		final String value;
		final String valueDisplay;
		final int displayType;
		final int referenceValueId;
		if (field instanceof Combobox)
		{
			final Combobox combobox = (Combobox)field;
			final Comboitem item = combobox.getSelectedItem();
			if (item == null)
			{
				return;
			}
			valueDisplay = item.getLabel();
			final Object valueObj = item.getValue();
			final PO po = InterfaceWrapperHelper.getPO(valueObj);
			if (po != null)
			{
				value = String.valueOf(po.get_ID());
				displayType = DisplayType.TableDir;
				referenceValueId = 0;
			}
			else if (valueObj instanceof String)
			{
				value = valueObj.toString();
				displayType = DisplayType.List;
				referenceValueId = 0;
			}
			// 04020 : Adding handling for value/key-name pair
			else if (valueObj instanceof ValueNamePair)
			{
				value = ((ValueNamePair)valueObj).getValue();
				displayType = DisplayType.String;
				referenceValueId = 0;
			}
			else if (valueObj instanceof KeyNamePair)
			{
				value = String.valueOf(((KeyNamePair)valueObj).getKey());
				displayType = DisplayType.String;
				referenceValueId = 0;
			}
			else
			{
				return;
			}
		}
		else if (field instanceof Textbox)
		{
			final Textbox tb = (Textbox)field;
			value = tb.getText();
			valueDisplay = value;
			displayType = DisplayType.String;
			referenceValueId = 0;
		}
		else if (field instanceof Listbox)
		{
			final Listbox listbox = (Listbox)field;
			final Listitem item = listbox.getSelectedItem();
			if (item == null)
			{
				return;
			}
			listbox.renderItem(item); // make sure is rendered
			final Object valueObj = item.getValue();
			valueDisplay = item.getLabel();

			final PO po = InterfaceWrapperHelper.getPO(valueObj);
			if (po != null)
			{
				value = String.valueOf(po.get_ID());
				displayType = DisplayType.TableDir;
				referenceValueId = 0;
			}
			else
			{
				return;
			}
		}
		else
		{
			return;
		}

		final Properties ctx = Env.getCtx();

		@SuppressWarnings("unused")
		final ValuePreference vp = new ValuePreference(
				0, // windowNo
				Env.getAD_Client_ID(ctx), // AD_Client_ID
				Env.getAD_Org_ID(ctx), // AD_Org_ID
				Env.getAD_User_ID(ctx), // AD_User_ID
				adWindowId, // AD_Window_ID
				attributeName,
				propertyDisplayName,
				value,
				valueDisplay,
				displayType,
				referenceValueId);
	}

	public <T> T getDefaultValue(String namespace, String propertyName, Class<T> clazz)
	{
		final int adWindowId = -1; // no window
		return getDefaultValue(namespace, adWindowId, propertyName, clazz);
	}

	public <T> T getDefaultValue(final String namespace, final int adWindowId, final String propertyName, final Class<T> clazz)
	{
		final Properties ctx = Env.getCtx();
		final String attribute = namespace == null ? propertyName : namespace + "#" + propertyName;

		return Services.get(IValuePreferenceBL.class).getValue(ctx, attribute, adWindowId, clazz);
	}
}
