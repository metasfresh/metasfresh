package de.metas.ui.web.vaadin.window.components;

import java.util.IdentityHashMap;
import java.util.List;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.impl.CalloutExecutor;
import org.adempiere.util.Check;
import org.compiere.util.Env;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
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

public class CalloutFieldDependenciesBinder extends AbstractFieldDependenciesBinder
{
	private CalloutExecutor calloutExecutor = null;

	private final IdentityHashMap<Field<?>, Optional<ICalloutField>> calloutFields = new IdentityHashMap<>();
	
	@Override
	protected void afterUnbind()
	{
		calloutExecutor = null;
		calloutFields.clear();
	}

	public ICalloutExecutor getCalloutExecutor()
	{
		if (calloutExecutor == null)
		{
			final IFieldGroupContext context = getFieldGroupContext();
			Check.assumeNotNull(context, "context not null");

			calloutExecutor = new CalloutExecutor(Env.getCtx(), context.getWindowNo());
		}
		return calloutExecutor;
	}

	@Override
	protected void updateProperty(final Property<?> property)
	{
		final ICalloutField calloutField = getCalloutField(property);
		if (calloutField == null)
		{
			return;
		}

		final ICalloutExecutor calloutExecutor = getCalloutExecutor();
		if (calloutExecutor == null)
		{
			return;
		}

		try
		{
			calloutExecutor.execute(calloutField);
		}
		catch (Exception e)
		{
			e.printStackTrace(); // TODO
		}
	}

	@Override
	protected List<? extends Object> getDependsOnPropertyIds(final Property<?> field, final Object propertyId)
	{
		return ImmutableList.of(propertyId);
	}

	private ICalloutField getCalloutField(final Property<?> property)
	{
		final Field<?> field = getField(property);
		if (field == null)
		{
			return null;
		}

		Optional<ICalloutField> calloutFieldOptional = calloutFields.get(field);
		if (calloutFieldOptional == null)
		{
			final ICalloutField calloutField = createCalloutField(field);
			calloutFieldOptional = Optional.fromNullable(calloutField);
			calloutFields.put(field, calloutFieldOptional);
		}

		return calloutFieldOptional.orNull();
	}

	private ICalloutField createCalloutField(final Field<?> field)
	{
		final DataFieldPropertyDescriptor descriptor = getFieldDescriptor(field);
		if (descriptor == null)
		{
			return null;
		}
		return new CalloutField(this, field, descriptor);
	}
}
