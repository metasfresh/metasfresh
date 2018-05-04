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


import java.math.BigDecimal;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_PropertiesConfig;
import org.compiere.model.I_M_PropertiesConfig_Line;

/**
 * Provides configuration for Properties Panel attributes
 *
 * @author al
 */
public class PropertiesPanelModelConfigurator implements IPropertiesPanelModelConfigurator
{
	private final IContextAware contextProvider;
	private final I_M_PropertiesConfig propertiesConfig;

	public PropertiesPanelModelConfigurator(final IContextAware contextProvider, final String propertiesConfigValue)
	{
		super();

		this.contextProvider = contextProvider;
		propertiesConfig = getPropertiesConfigOrNull(propertiesConfigValue);
	}

	@Cached(cacheName = I_M_PropertiesConfig.Table_Name + "#By" + "#Value")
	private final I_M_PropertiesConfig getPropertiesConfigOrNull(final String propertiesConfigValue)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PropertiesConfig.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_PropertiesConfig.COLUMNNAME_Value, propertiesConfigValue)
				.create()
				.firstOnly(I_M_PropertiesConfig.class);
	}

	@Cached(cacheName = I_M_PropertiesConfig_Line.Table_Name + "#By" + "#Name_M_Attribute")
	private final String getValueOrNull(final String name, final I_M_Attribute attribute)
	{
		if (propertiesConfig == null)
		{
			return null;
		}
		final IQueryBuilder<I_M_PropertiesConfig_Line> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_PropertiesConfig_Line.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_PropertiesConfig_Line.COLUMNNAME_M_PropertiesConfig_ID, propertiesConfig.getM_PropertiesConfig_ID())
				.addEqualsFilter(I_M_PropertiesConfig_Line.COLUMNNAME_Name, name);
		if (attribute != null)
		{
			queryBuilder.addEqualsFilter(I_M_PropertiesConfig_Line.COLUMNNAME_M_Attribute_ID, attribute.getM_Attribute_ID());
		}

		final I_M_PropertiesConfig_Line propertiesConfigLine = queryBuilder.create()
				.firstOnly(I_M_PropertiesConfig_Line.class);

		if (propertiesConfigLine == null)
		{
			return null;
		}
		return propertiesConfigLine.getValue();
	}

	private final Boolean getBooleanValue(final String value)
	{
		if (value == null)
		{
			return null;
		}

		if ("Y".equals(value))
		{
			return true;
		}
		else if ("N".equals(value))
		{
			return false;
		}
		else
		{
			throw new AdempiereException("Unsupported configuration value for Boolean: {} (Expected Y/N)", new Object[] { value }); // internal error
		}
	}

	@Override
	public String getValueStringOrNull(final String name, final I_M_Attribute attribute)
	{
		return getValueOrNull(name, attribute);
	}

	@Override
	public BigDecimal getValueBigDecimalOrNull(final String name, final I_M_Attribute attribute)
	{
		final String value = getValueOrNull(name, attribute);
		if (value == null)
		{
			return null;
		}
		return new BigDecimal(value);
	}

	@Override
	public Boolean getValueBooleanOrNull(final String name, final I_M_Attribute attribute)
	{
		final String value = getValueOrNull(name, attribute);
		return getBooleanValue(value);
	}

	@Override
	public String getValueStringOrNull(final String name)
	{
		return getValueStringOrNull(name, null);
	}

	@Override
	public BigDecimal getValueBigDecimalOrNull(final String name)
	{
		return getValueBigDecimalOrNull(name, null);
	}

	@Override
	public Boolean getValueBooleanOrNull(final String name)
	{
		return getValueBooleanOrNull(name, null);
	}
}
