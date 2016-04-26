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

public class ReadOnlyFieldDependenciesBinder extends AbstractLogicExpressionFieldDependenciesBinder
{
	public ReadOnlyFieldDependenciesBinder()
	{
		super();
	}

	@Override
	protected void updateProperty(final Property<?> property, final boolean readOnly)
	{
		final Field<?> field = getField(property);
		if (field == null)
		{
			return;
		}

		// NOTE: not using ReadOnly because that prevents updating the field from it's property
		final boolean enabled = !readOnly;
		if (enabled == field.isEnabled())
		{
			return;
		}
		field.setEnabled(enabled);
	}

	@Override
	protected ILogicExpression extractLogicExpression(final Property<?> property)
	{
		if (property == null)
		{
			return ILogicExpression.TRUE;
		}

		final DataFieldPropertyDescriptor fieldDescriptor = getFieldDescriptor(property);
		if (fieldDescriptor == null)
		{
			return ILogicExpression.TRUE;
		}
		if (fieldDescriptor.isReadOnly())
		{
			return ILogicExpression.TRUE;
		}

		final Object propertyId = fieldDescriptor.getColumnName();

		ILogicExpression logicExpression = fieldDescriptor.getReadOnlyLogic();

		// Consider field readonly if the row is not active
		if (!PROPERTYID_IsActive.equals(propertyId))
		{
			logicExpression = LOGICEXPRESSION_NotActive.or(logicExpression);
		}

		// Consider field readonly if the row is processed
		if (!fieldDescriptor.isAlwaysUpdateable())
		{
			logicExpression = LOGICEXPRESSION_Processed.or(logicExpression);
		}

		return logicExpression;
	}

}
