package de.metas.web.component.trl;

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


import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.api.Group;

/* package */class TrlElementRowRenderer implements RowRenderer
{

	@Override
	public void render(final Row row, final Object data) throws Exception
	{
		row.setValue(data);
		if (data == null)
		{
			return;
		}

		if (row instanceof Group)
		{
			final Group group = (Group)row;
			final TrlElement element = (TrlElement)data;
			group.setSpans("2");
			group.setLabel(element.getTableName() + ": " + element.getName());
			group.setTooltiptext(element.getDescription());
			group.setOpen(true);
		}
		else if (data instanceof TrlElementItem)
		{
			final TrlElementItem item = (TrlElementItem)data;
			final Label label = new Label();
			label.setValue(item.getColumnName());
			label.setWidth("150px");
			label.setParent(row);

			final Textbox textbox = new Textbox();
			textbox.setValue(item.getValue());
			textbox.setWidth("100%");
			textbox.setParent(row);
			textbox.addEventListener(Events.ON_CHANGE, new EventListener()
			{

				@Override
				public void onEvent(final Event event) throws Exception
				{
					item.setValue(textbox.getValue());
				}
			});
		}
		else
		{
			throw new IllegalStateException("Object " + data + " is not supported");
		}
	}

	public void renderColumns(final Grid grid)
	{
	}
}
