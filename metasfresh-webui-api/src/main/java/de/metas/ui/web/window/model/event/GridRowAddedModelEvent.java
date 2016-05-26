package de.metas.ui.web.window.model.event;

import com.google.common.base.MoreObjects.ToStringHelper;

import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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

@SuppressWarnings("serial")
public class GridRowAddedModelEvent extends ModelEvent
{
	public static final GridRowAddedModelEvent of(final PropertyName gridPropertyName, final Object rowId, final PropertyValuesDTO rowValues)
	{
		return new GridRowAddedModelEvent(gridPropertyName, rowId, rowValues);
	}

	private final PropertyName gridPropertyName;
	private final Object rowId;
	final PropertyValuesDTO rowValues;

	private GridRowAddedModelEvent(final PropertyName gridPropertyName, final Object rowId, final PropertyValuesDTO rowValues)
	{
		super();
		this.gridPropertyName = gridPropertyName;
		this.rowId = rowId;
		this.rowValues = rowValues;
	}

	@Override
	protected void toString(ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("grid", gridPropertyName)
				.add("rowId", rowId)
				.add("rowValues", rowValues);
	}

	public PropertyName getGridPropertyName()
	{
		return gridPropertyName;
	}

	public Object getRowId()
	{
		return rowId;
	}

	public PropertyValuesDTO getRowValues()
	{
		return rowValues;
	}
}
