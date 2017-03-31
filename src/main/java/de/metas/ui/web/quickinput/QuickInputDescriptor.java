package de.metas.ui.web.quickinput;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;

import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class QuickInputDescriptor
{
	public static final QuickInputDescriptor of( //
			final DocumentEntityDescriptor entityDescriptor //
			, final QuickInputLayoutDescriptor layout //
			, final Class<? extends IQuickInputProcessor> processorClass //
	)
	{
		return new QuickInputDescriptor(entityDescriptor, layout, processorClass);
	}

	private final DocumentEntityDescriptor entityDescriptor;
	private final QuickInputLayoutDescriptor layout;
	private final Class<? extends IQuickInputProcessor> processorClass;

	private QuickInputDescriptor(final DocumentEntityDescriptor entityDescriptor, final QuickInputLayoutDescriptor layout, final Class<? extends IQuickInputProcessor> processorClass)
	{
		Check.assumeNotNull(entityDescriptor, "Parameter entityDescriptor is not null");
		Check.assumeNotNull(layout, "Parameter layout is not null");
		Check.assumeNotNull(processorClass, "Parameter processorClass is not null");

		this.entityDescriptor = entityDescriptor;
		this.layout = layout;
		this.processorClass = processorClass;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("processorClass", processorClass)
				.add("entityDescriptor", entityDescriptor)
				.toString();
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	public QuickInputLayoutDescriptor getLayout()
	{
		return layout;
	}

	public IQuickInputProcessor createProcessor()
	{
		try
		{
			return processorClass.newInstance();
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

	public DetailId getDetailId()
	{
		return entityDescriptor.getDetailId();
	}
}
