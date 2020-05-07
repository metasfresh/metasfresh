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

import org.compiere.model.I_M_Attribute;

/**
 * Provides configuration for Properties Panel attributes
 *
 * @author al
 */
public interface IPropertiesPanelModelConfigurator
{
	/**
	 * @param name
	 * @param attribute
	 * @return configuration value as {@link String} for the desired property name and attribute or NULL if no configuration is found
	 */
	String getValueStringOrNull(String name, I_M_Attribute attribute);

	/**
	 * @param name
	 * @param attribute
	 * @return configuration value as {@link BigDecimal} for the desired property name and attribute or NULL if no configuration is found
	 */
	BigDecimal getValueBigDecimalOrNull(String name, I_M_Attribute attribute);

	/**
	 * @param name
	 * @param attribute
	 * @return configuration value as {@link Boolean} for the desired property name and attribute or NULL if no configuration is found
	 */
	Boolean getValueBooleanOrNull(String name, I_M_Attribute attribute);

	/**
	 * @param name
	 * @return configuration value as {@link String} for the desired property name or NULL if no configuration is found
	 */
	String getValueStringOrNull(String name);

	/**
	 * @param name
	 * @return configuration value as {@link BigDecimal} for the desired property name or NULL if no configuration is found
	 */
	BigDecimal getValueBigDecimalOrNull(String name);

	/**
	 * @param name
	 * @return configuration value as {@link Boolean} for the desired property name or NULL if no configuration is found
	 */
	Boolean getValueBooleanOrNull(String name);
}
