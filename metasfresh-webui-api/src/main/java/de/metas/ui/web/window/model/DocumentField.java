package de.metas.ui.web.window.model;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;

/*
 * #%L
 * metasfresh-webui-api
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

public class DocumentField
{
	private final DocumentFieldDescriptor descriptor;

	private Object initialValue;
	private Object value;

	private boolean mandatory = false;
	private boolean readonly = false;
	private boolean displayed = false;

	public DocumentField(final DocumentFieldDescriptor descriptor)
	{
		super();
		this.descriptor = descriptor;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", descriptor.getName())
				.add("value", value)
				.add("initalValue", initialValue)
				.add("mandatory", mandatory)
				.add("readonly", readonly)
				.add("displayed", displayed)
				.toString();
	}

	public DocumentFieldDescriptor getDescriptor()
	{
		return descriptor;
	}
	
	public String getName()
	{
		return descriptor.getName();
	}

	public Object getInitialValue()
	{
		return initialValue;
	}

	public void setInitialValue(Object initialValue)
	{
		this.initialValue = initialValue;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}

	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	public boolean isReadonly()
	{
		return readonly;
	}

	public void setReadonly(boolean readonly)
	{
		this.readonly = readonly;
	}

	public boolean isDisplayed()
	{
		return displayed;
	}

	public void setDisplayed(boolean displayed)
	{
		this.displayed = displayed;
	}
}
