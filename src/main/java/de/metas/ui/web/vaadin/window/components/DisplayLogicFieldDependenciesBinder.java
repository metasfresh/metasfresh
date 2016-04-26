package de.metas.ui.web.vaadin.window.components;

import org.adempiere.ad.expression.api.ILogicExpression;

import com.vaadin.data.Property;
import com.vaadin.ui.Field;

import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;

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

public class DisplayLogicFieldDependenciesBinder extends AbstractLogicExpressionFieldDependenciesBinder
{
	@Override
	protected void updateProperty(final Property<?> property, final boolean visible)
	{
		final Field<?> field = getField(property);
		if (field == null)
		{
			return;
		}
		
		if (visible == field.isVisible())
		{
			return;
		}

		field.setVisible(visible);
		System.out.println("DisplayLogic=" + visible + " -- " + getFieldDescriptor(field).getColumnName());
	}

	@Override
	protected ILogicExpression extractLogicExpression(final Property<?> field)
	{
		final DataFieldPropertyDescriptor fieldDescriptor = getFieldDescriptor(field);
		if (fieldDescriptor == null)
		{
			return ILogicExpression.FALSE; // not displayed
		}
		if (!fieldDescriptor.isDisplayable())
		{
			return ILogicExpression.FALSE; // not displayed
		}

		final ILogicExpression displayLogic = fieldDescriptor.getDisplayLogic();
		return displayLogic;
	}

}
