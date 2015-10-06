/**
 * 
 */
package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
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


import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

/**
 * @author teo_sarca
 *
 */
public class SponsorTableRenderer implements RowRenderer
{
	public static final String ATTRIBUTE_Node = "SponsorTreeNode";
	public static final String ATTRIBUTE_ColumnName = "ColumnName";
	
	private static final String CURRENT_ROW_STYLE = "background-color: #6f97d2;";
	
	class RowListener implements EventListener
	{
		public RowListener()
		{
		}
		public void onEvent(Event event) throws Exception
		{
			if (Events.ON_CLICK.equals(event.getName())
					|| Events.ON_DOUBLE_CLICK.equals(event.getName()))
			{
				final Div div = (Div)event.getTarget();
				final Row row = (Row)div.getParent();
				setCurrentRow(row, event);
			}
		}
	}
	
	private RowListener rowListener = null;
	private Row currentRow = null;

	@Override
	public void render(Row row, Object data) throws Exception
	{
		final ListModel listModel = row.getGrid().getModel();
		if (! (listModel instanceof SponsorTableModel) )
		{
			return;
		}
		final SponsorTableModel model = (SponsorTableModel)listModel;
		final SponsorTreeNode node = (SponsorTreeNode)data;
		
		String[] columnNames = SponsorTableModel.getColumnNames();
		Object[] values = model.toArray(node);
		for (int i = 0; i < columnNames.length; i++)
		{
			final String name = columnNames[i]; 
			final Object value = values[i];
			
			Div div = new Div();
			Component cell = getDisplayComponent(value);
			div.appendChild(cell);
			row.appendChild(div);
			
			if (rowListener == null)
				rowListener = new RowListener();
			div.addEventListener(Events.ON_CLICK, rowListener);
			div.addEventListener(Events.ON_DOUBLE_CLICK, rowListener);
			div.setAttribute(ATTRIBUTE_ColumnName, name);
		}
		row.setAttribute(ATTRIBUTE_Node, node);
	}
	
//	@Override
//	public void render(Listitem item, Object data) throws Exception
//	{
//		final ListModel listModel = item.getListbox().getModel();
//		if (! (listModel instanceof SponsorTableModel) )
//		{
//			return;
//		}
//		final SponsorTableModel model = (SponsorTableModel)listModel;
//		final SponsorTreeNode node = (SponsorTreeNode)data;
//		for (Object value : model.toArray(node))
//		{
//			Component cell = getDisplayComponent(value);
//			item.appendChild(cell);
//		}
//		item.setAttribute("node", node);
//	}
	
	private Component getDisplayComponent(Object value)
	{
		Label cell = new Label();
		if (value == null)
		{
			cell.setValue("");
		}
		else if (value instanceof BigDecimal)
		{
			final BigDecimal bd = (BigDecimal)value;
			final DecimalFormat f = DisplayType.getNumberFormat(DisplayType.CostPrice, Env.getLanguage(Env.getCtx()));
			cell.setValue(f.format(bd));
		}
		else
		{
			cell.setValue(value.toString());
		}
		return cell;
	}
	
	public void createHeader(Grid grid)
	{
		Columns head = new Columns();
		head.setSizable(true);
		String[] columnNames = SponsorTableModel.getColumnNames();
		String[] columnSizes = new String[]{"30px", "150px"};
		Class<?>[] columnTypes = new Class<?>[]{Integer.class, String.class, Integer.class, Integer.class, Integer.class,};
		
		for (int i = 0; i < columnNames.length; i++)
		{
			String colname = columnNames[i];
			Column column = new Column();
			column.setLabel(Msg.translate(Env.getCtx(), colname));
//			column.setSortAscending(new SortComparator(i, true, Env.getLanguage(Env.getCtx())));
//			column.setSortDescending(new SortComparator(i, false, Env.getLanguage(Env.getCtx())));
			if (columnSizes.length > i && columnSizes[i] != null)
				column.setWidth(columnSizes[i]);
			column.setSortAscending(new SponsorTableComparator(i, true));
			column.setSortDescending(new SponsorTableComparator(i, false));
			
			String align = "left";
			if (columnTypes.length > i && columnTypes[i] != null)
			{
				if (Number.class.isAssignableFrom(columnTypes[i]))
					align = "right";
			}
			column.setAlign(align);

			head.appendChild(column);
		}
		
		grid.appendChild(head);
//		return head;
	}
	
	public void setCurrentRow(Row row, Event event)
	{
		if (currentRow != null && currentRow.getParent() != null && currentRow != row)
		{
			currentRow.setStyle(null);
		}
		final Row previousRow = currentRow;
		currentRow = row;
		if (currentRow == null)
			return;
		
		currentRow.setStyle(CURRENT_ROW_STYLE);
		
		if (previousRow != currentRow && currentRow != null && event != null)
		{
			Grid grid = row.getGrid();
			Event evt = new Event(Events.ON_CLICK, grid, event.getTarget());
			Events.sendEvent(grid, evt);
		}
		
		Clients.scrollIntoView(currentRow);
	}
	
	public boolean isRowRendered(org.zkoss.zul.Row row)
	{
		if (row.getChildren().size() == 0) {
			return false;
		} else if (row.getChildren().size() == 1) {
			if (!(row.getChildren().get(0) instanceof Div)) {
				return false;
			}
		}
		return true;
	}

}
