package de.metas.commission.custom.config;

/*
 * #%L
 * de.metas.commission.base
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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.compiere.util.DisplayType;

import de.metas.adempiere.service.IParameterizable;
import de.metas.adempiere.util.Parameter;

public class BaseConfig implements IParameterizable
{

	private final List<Parameter> parameters = new ArrayList<Parameter>();

	private final Map<String, Parameter> name2param = new HashMap<String, Parameter>();

	/**
	 * Creates a new empty instance
	 */
	public BaseConfig()
	{
	}

	/**
	 * Creates an instance containing the given parameters
	 * 
	 * @param params
	 */
	public BaseConfig(final List<Parameter> params)
	{
		for (final Parameter param : params)
		{
			parameters.add(param);
			name2param.put(param.name, param);
		}

	}

	protected void overrideParam(final String name, final String displayName,
			final String description, final int seqNo, final String value)
	{
		addOrOverrideParam(name, displayName, description, seqNo, value, DisplayType.String, true);
	}

	public void addNewParam(final String name, final String displayName,
			final String description, final int seqNo, final String value)
	{
		addOrOverrideParam(name, displayName, description, seqNo, value, DisplayType.String, false);
	}

	protected void overrideParam(final String name, final String displayName,
			final String description, final int seqNo, final boolean value)
	{
		addOrOverrideParam(name, displayName, description, seqNo, value, DisplayType.YesNo, true);
	}

	public void addNewParam(final String name, final String displayName,
			final String description, final int seqNo, final boolean value)
	{
		addOrOverrideParam(name, displayName, description, seqNo, value, DisplayType.YesNo, false);
	}

	protected void overrideParam(final String name, final String displayName,
			final String description, final int seqNo, final BigDecimal value)
	{
		addOrOverrideParam(name, displayName, description, seqNo, value, DisplayType.Number, true);
	}

	public void addNewParam(final String name, final String displayName,
			final String description, final int seqNo, final BigDecimal value)
	{
		addOrOverrideParam(name, displayName, description, seqNo, value, DisplayType.Number, false);
	}

	protected void overrideParam(final String name, final String displayName,
			final String description, final int seqNo, final int value)
	{
		addOrOverrideParam(name, displayName, description, seqNo, value, DisplayType.Integer, true);
	}

	public void addNewParam(final String name, final String displayName,
			final String description, final int seqNo, final int value)
	{
		addOrOverrideParam(name, displayName, description, seqNo, value, DisplayType.Integer, false);
	}

	private void addOrOverrideParam(final String name,
			final String displayName, final String description,
			final int seqNo, final Object value, final int displayType,
			final boolean override)
	{
		if (!override && name2param.containsKey(name))
		{
			throw new IllegalArgumentException("Parameter with name " + name
					+ " exists already: " + name2param.get(name));
		}
		if (override && !name2param.containsKey(name))
		{
			throw new IllegalArgumentException("Parameter with name " + name
					+ " doesn't exist: " + name2param.get(name));
		}

		final Parameter newParam = new Parameter(name, displayName, description, displayType, seqNo);

		addParam(newParam, seqNo, value);
	}

	protected void addParam(final Parameter newParam, final int seqNo, final Object value)
	{
		newParam.setValue(value);
		newParam.setSeqNo(seqNo);
		parameters.add(newParam);
		name2param.put(newParam.name, newParam);
	}

	@Override
	public List<Parameter> getParameters()
	{
		return parameters;
	}

	public Map<String, Parameter> getName2Param()
	{
		return name2param;
	}

	@Override
	public Parameter getParameter(final String name)
	{
		final Parameter parameter = name2param.get(name);

		if (parameter == null)
		{
			throw new IllegalArgumentException(this.getClass().getSimpleName() + ": Illegal parameter name " + name);
		}
		return parameter;
	}

	@Override
	public boolean hasParameter(final String name)
	{
		return name2param.containsKey(name);
	}

}
