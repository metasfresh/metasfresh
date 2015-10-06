/**
 * 
 */
package de.metas.adempiere.webui.component;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.Columns;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Toolbarbutton;

/**
 * Property Info Panel it's a panel which displays a grid with 2 columns, property name and property value. Very useful in other custom forms.
 * 
 * @author tsa
 * 
 */
public class PropertyInfoPanel extends Grid
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -187954265903812983L;

	private final Rows rows;
	private final Column propertyColumn;
	private final Column valueColumn;

	private final Map<String, Component> fields = new HashMap<String, Component>();

	public PropertyInfoPanel()
	{
		super();
		makeNoStrip();
		{
			rows = newRows();
			final Columns columns = new Columns();
			appendChild(columns);
			propertyColumn = new Column();
			propertyColumn.setWidth("40%");
			columns.appendChild(propertyColumn);
			valueColumn = new Column();
			valueColumn.setWidth("60%");
			columns.appendChild(valueColumn);
		}
	}

	/**
	 * Add a new property (row).
	 * 
	 * @param name property name. It will be automatically translated when displayed
	 * @param value property value
	 */
	public void addProperty(final String name, final String value)
	{
		final org.zkoss.zul.Label valueLabel = new org.zkoss.zul.Label();
		valueLabel.setValue(value);
		addProperty(name, valueLabel);
	}

	/**
	 * @param name
	 * @param valueElement
	 * @see #addProperty(String, String)
	 */
	public void addProperty(final String name, final Component valueElement)
	{
		if (fields.containsKey(name))
		{
			throw new AdempiereException("Property " + name + " already exists");
		}

		final Label l = new Label();
		if (Util.isEmpty(name))
		{
			; // nothing
		}
		else if (name.contains(" "))
		{
			// if name contains space don't try to translate it because probably is not a translateable text
			l.setValue(name);
		}
		else
		{
			l.setValue(Msg.translate(Env.getCtx(), name));
		}

		final Row row = rows.newRow();

		Div div = new Div();
		// div.setStyle("align: right; border: 1px solid red;");
		div.appendChild(l);
		row.appendChild(div);

		div = new Div();
		// div.setStyle("align: left; border: 1px solid red;");
		div.appendChild(valueElement);
		row.appendChild(div);

		fields.put(name, valueElement);
	}

	/**
	 * Reset all property values
	 */
	public void resetValues()
	{
		for (final Component e : fields.values())
		{
			setValue(e, "", null);
		}
	}

	/**
	 * Reset all properties
	 */
	public void resetProperties()
	{
		rows.getChildren().clear();
		fields.clear();
	}

	/**
	 * set the width for columns(property, value)
	 * 
	 * @param propertyWidth
	 * @param valueWidth
	 */
	public void setSize(final String propertyWidth, final String valueWidth)
	{
		propertyColumn.setWidth(propertyWidth);
		valueColumn.setWidth(valueWidth);
	}

	/**
	 * Get the component that is displaying given property
	 * 
	 * @param name
	 * @return
	 */
	public Component getValueComponent(final String name)
	{
		return fields.get(name);
	}

	public void setProperty(final String name, final String value)
	{
		final Component c = getValueComponent(name);
		if (c == null)
		{
			addProperty(name, value);
		}
		else
		{
			setValue(c, value, null);
		}
	}

	/**
	 * Checks if the value of a property is set
	 * 
	 * @param property
	 * @return
	 */
	public boolean isPropertyValueSet(final String name)
	{
		final Component e = getValueComponent(name);
		return e != null;
	}

	protected static final void setValue(final Component e, final String value, final String url)
	{
		if (e instanceof org.zkoss.zul.Label)
		{
			((org.zkoss.zul.Label)e).setValue(value);
		}
		else if (e instanceof Toolbarbutton)
		{
			((Toolbarbutton)e).setLabel(value);
			if (!Util.isEmpty(url, true))
			{
				((Toolbarbutton)e).setHref(url);
			}
		}
	}

	public void setValues(final String property, final String value)
	{
		if (isPropertyValueSet(property))
		{
			setProperty(property, value);
		}
		else
		{
			this.addProperty(property, value);
		}
	}
}
