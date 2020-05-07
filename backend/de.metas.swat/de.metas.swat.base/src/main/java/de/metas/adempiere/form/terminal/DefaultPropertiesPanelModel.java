package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.NamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;
import de.metas.i18n.IMsgBL;

/**
 * A generic {@link IPropertiesPanelModel} which allows developer to define and customize the properties that are supported.
 * 
 * To create a new property, just call {@link #newProperty(String, int)} which will assist you.
 *
 * @author tsa
 *
 */
public class DefaultPropertiesPanelModel extends AbstractPropertiesPanelModel
{
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final Map<String, DefaultPropertyModel> properties = new LinkedHashMap<>();

	public DefaultPropertiesPanelModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);
	}

	private final DefaultPropertyModel getProperty(final String propertyName)
	{
		final DefaultPropertyModel property = properties.get(propertyName);
		if (property == null)
		{
			throw new AdempiereException("@NotFound@ " + propertyName);
		}
		return property;
	}

	@Override
	public List<String> getPropertyNames()
	{
		return new ArrayList<>(properties.keySet());
	}

	@Override
	public String getPropertyDisplayName(final String propertyName)
	{
		return getProperty(propertyName).getPropertyDisplayName();
	}

	@Override
	public int getDisplayType(final String propertyName)
	{
		return getProperty(propertyName).getDisplayType();
	}

	@Override
	public Object getPropertyValue(final String propertyName)
	{
		return getProperty(propertyName).getValue();
	}

	@Override
	public List<? extends NamePair> getPropertyAvailableValues(final String propertyName)
	{
		return Collections.emptyList();
	}

	@Override
	public ITerminalLookup getPropertyLookup(final String propertyName)
	{
		return null;
	}

	@Override
	public void setPropertyValue(final String propertyName, final Object value)
	{
		final DefaultPropertyModel property = getProperty(propertyName);
		property.setValue(value);
		firePropertyValueChanged(propertyName);
	}

	@Override
	public boolean isEditable(final String propertyName)
	{
		return getProperty(propertyName).isEditable();
	}

	public void setEditable(final String propertyName, final boolean editable)
	{
		final DefaultPropertyModel property = getProperty(propertyName);
		final boolean editableOld = property.isEditable();
		if (editableOld == editable)
		{
			return;
		}

		property.setEditable(editable);

		fireContentChanged();
	}

	@Override
	public ITerminalFieldConstraint<Object> getConstraint(final String propertyName)
	{
		return getProperty(propertyName).getConstraint();
	}

	@Override
	public void commitEdit()
	{
		// nothing
	}

	public DefaultPropertyModelBuilder newProperty(final String propertyName, final int displayType)
	{
		return new DefaultPropertyModelBuilder()
				.setPropertyName(propertyName)
				.setDisplayType(displayType);
	}

	private void addProperty(final DefaultPropertyModel property)
	{
		final String propertyName = property.getPropertyName();
		properties.put(propertyName, property);
		fireContentChanged();
	}

	private static class DefaultPropertyModel
	{
		private final String propertyName;
		private final String propertyDisplayName;
		private final int displayType;
		private final ITerminalFieldConstraint<Object> constraint;
		private Object value;
		private boolean editable;

		private DefaultPropertyModel(final DefaultPropertyModelBuilder builder)
		{
			super();

			propertyName = builder.getPropertyName();
			Check.assumeNotEmpty(propertyName, "propertyName not empty");

			propertyDisplayName = builder.getPropertyDisplayName();

			displayType = builder.getDisplayType();
			Check.assume(displayType > 0, "displayType > 0");

			constraint = builder.getConstraint();

			//
			// Initialize changeable values
			editable = builder.isEditable();
			value = builder.getValue();
		}

		public ITerminalFieldConstraint<Object> getConstraint()
		{
			return constraint;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public String getPropertyName()
		{
			return propertyName;
		}

		public String getPropertyDisplayName()
		{
			return propertyDisplayName;
		}

		public int getDisplayType()
		{
			return displayType;
		}

		public Object getValue()
		{
			return value;
		}

		public void setValue(final Object value)
		{
			this.value = value;
		}

		public boolean isEditable()
		{
			return editable;
		}

		public void setEditable(final boolean editable)
		{
			this.editable = editable;
		}
	}

	public class DefaultPropertyModelBuilder
	{
		private String propertyName;
		private String propertyDisplayName;
		private int displayType;
		private Object value;
		private boolean editable = true;
		private ITerminalFieldConstraint<Object> constraint;

		private DefaultPropertyModelBuilder()
		{
			super();
		}

		public void createAndAdd()
		{
			final DefaultPropertyModel property = new DefaultPropertyModel(this);
			addProperty(property);
		}

		public DefaultPropertyModelBuilder setPropertyName(final String propertyName)
		{
			this.propertyName = propertyName;
			return this;
		}

		public String getPropertyName()
		{
			Check.assumeNotEmpty(propertyName, "propertyName not empty");
			return propertyName;
		}

		public DefaultPropertyModelBuilder setPropertyDisplayName(final String propertyDisplayName)
		{
			this.propertyDisplayName = propertyDisplayName;
			return this;
		}

		public String getPropertyDisplayName()
		{
			if (!Check.isEmpty(propertyDisplayName))
			{
				return propertyDisplayName;
			}

			final String propertyName = getPropertyName();
			return msgBL.translate(getTerminalContext().getCtx(), propertyName);
		}

		public DefaultPropertyModelBuilder setDisplayType(final int displayType)
		{
			this.displayType = displayType;
			return this;
		}

		public int getDisplayType()
		{
			return displayType;
		}

		public DefaultPropertyModelBuilder setEditable(final boolean editable)
		{
			this.editable = editable;
			return this;
		}

		public boolean isEditable()
		{
			return editable;
		}

		public DefaultPropertyModelBuilder setValue(final Object value)
		{
			this.value = value;
			return this;
		}

		public Object getValue()
		{
			return value;
		}

		@SuppressWarnings("unchecked")
		public DefaultPropertyModelBuilder setConstraint(final ITerminalFieldConstraint<?> constraint)
		{
			this.constraint = (ITerminalFieldConstraint<Object>)constraint;
			return this;
		}

		public ITerminalFieldConstraint<Object> getConstraint()
		{
			return constraint;
		}
	}
}
