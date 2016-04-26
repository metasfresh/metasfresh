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

public class MandatoryFieldDependenciesBinder extends AbstractLogicExpressionFieldDependenciesBinder
{
	@Override
	protected void updateProperty(final Property<?> property, final boolean mandatory)
	{
		final Field<?> field = getField(property);
		if (field == null)
		{
			return;
		}
		if (mandatory == field.isRequired())
		{
			return;
		}

		field.setRequired(mandatory);
	}

	@Override
	protected ILogicExpression extractLogicExpression(final Property<?> property)
	{
		final DataFieldPropertyDescriptor fieldDescriptor = getFieldDescriptor(property);
		if (fieldDescriptor == null)
		{
			return ILogicExpression.FALSE; // not mandatory
		}
		if (fieldDescriptor.isMandatory())
		{
			return ILogicExpression.TRUE; // always mandatory
		}

		final ILogicExpression mandatoryLogic = fieldDescriptor.getMandatoryLogic();
		return mandatoryLogic;
	}
}
