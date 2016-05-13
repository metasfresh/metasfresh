package de.metas.ui.web.vaadin.window.model;

import org.compiere.util.Evaluatee2;

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

public class DataRowItemEditingContext implements Evaluatee2
{
	private final DataRowItem row;

	public DataRowItemEditingContext(final DataRowItem row)
	{
		super();
		this.row = row;
	}

	@Override
	public String get_ValueAsString(String propertyId)
	{
		return row.getPropertyValueAsString(propertyId);
	}

	@Override
	public boolean has_Variable(String propertyId)
	{
		return row.hasPropertyId(propertyId);
	}

	@Override
	public String get_ValueOldAsString(String propertyId)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
