/**
 * 
 */
package de.metas.web.component;

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


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.Columns;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zhtml.Div;

/**
 * Simple Panel that display Name->Value fields
 * 
 * @author teo_sarca
 * 
 */
public class BeanInfoPanel extends Grid
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6892484096399279457L;

	private final Rows rows;
	private final Map<String, org.zkoss.zul.Label> valueLabels = new HashMap<String, org.zkoss.zul.Label>();

	public BeanInfoPanel()
	{
		super();
		makeNoStrip();
		{
			rows = newRows();
			final Columns columns = new Columns();
			appendChild(columns);
			Column col = new Column();
			col.setStyle("width: 40%");
			columns.appendChild(col);
			col = new Column();
			col.setStyle("width: 60%");
			columns.appendChild(col);
		}
	}

	public void addLine(final String label)
	{
		if (valueLabels.get(label) != null)
		{
			return;
		}

		final Label l = new Label(Msg.translate(Env.getCtx(), label));

		final org.zkoss.zul.Label valueLabel = new org.zkoss.zul.Label();
		valueLabels.put(label, valueLabel);

		final Row row = rows.newRow();

		Div div = new Div();
		// div.setStyle("align: right; border: 1px solid red;");
		div.appendChild(l);
		row.appendChild(div);

		div = new Div();
		// div.setStyle("align: left; border: 1px solid red;");
		div.appendChild(valueLabel);
		row.appendChild(div);
	}

	public void setValue(final String label, final Object value)
	{
		valueLabels.get(label).setValue(value == null ? "" : value.toString());
	}

	public void setValue(final String label, final ResultSet rs) throws SQLException
	{
		final Object value = rs.getObject(label);
		setValue(label, value);
	}

	public void setValues(final ResultSet rs) throws SQLException
	{
		for (final String label : valueLabels.keySet())
		{
			setValue(label, rs);
		}
	}

	/**
	 * Adding method to allow unit tests.
	 * 
	 * @param label
	 * @return
	 */
	public String getValue(final String label)
	{
		return valueLabels.get(label).getValue();
	}
}
