package org.adempiere.mm.attributes.countryattribute;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.model.IContextAware;
import org.compiere.model.I_M_AttributeValue;

/**
 * Implementations are responsible for:
 * <ul>
 * <li>suggesting an M_Attribute_ID to be used, based on {@link ICountryAware}
 * <li>suggesting an {@link I_M_AttributeValue} to be used, based on {@link ICountryAware}
 * </ul>
 * 
 * @author tsa
 *
 */
public interface ICountryAwareAttributeService
{
	/**
	 * Gets the M_Attribute_ID to use for given {@link ICountryAware}.
	 * 
	 * @param countryAware
	 * @return M_Attribute_ID or <code>-1</code>.
	 */
	int getM_Attribute_ID(ICountryAware countryAware);

	/**
	 * Gets/creates the coresponsing {@link I_M_AttributeValue} for given {@link ICountryAware}.
	 * 
	 * @param context
	 * @param countryAware
	 * @return
	 */
	I_M_AttributeValue getCreateAttributeValue(final IContextAware context, final ICountryAware countryAware);
}
